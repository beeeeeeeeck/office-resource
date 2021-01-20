package app.device.service;

import app.api.device.BOCreateDeviceProcurementRequest;
import app.api.device.BOCreateDeviceProcurementResponse;
import app.api.device.BOGetDeviceProcurementResponse;
import app.api.device.BOUpdateDeviceRequest;
import app.api.device.DeviceStatusView;
import app.api.device.ListDeviceSummaryRequest;
import app.api.device.ListDeviceSummaryResponse;
import app.api.device.SearchDeviceRequest;
import app.api.device.SearchDeviceResponse;
import app.device.domain.Device;
import app.device.domain.DeviceProcurement;
import app.device.domain.DeviceStatus;
import app.device.domain.DeviceSummary;
import core.framework.db.Database;
import core.framework.db.Query;
import core.framework.db.Repository;
import core.framework.db.Transaction;
import core.framework.inject.Inject;
import core.framework.util.Strings;
import core.framework.web.exception.BadRequestException;
import core.framework.web.exception.NotFoundException;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.LongStream;

/**
 * @author beckl
 */
public class DeviceService {
    @Inject
    Repository<DeviceProcurement> deviceProcurementRepository;
    @Inject
    Repository<Device> deviceRepository;
    @Inject
    Database database;

    public BOCreateDeviceProcurementResponse create(BOCreateDeviceProcurementRequest request) {
        if (request.expiredDate != null && LocalDate.now().isAfter(request.expiredDate)) {
            throw new BadRequestException("Device expired date should be later than today");
        }
        DeviceProcurement deviceProcurement = new DeviceProcurement();
        deviceProcurement.deviceName = request.deviceName;
        deviceProcurement.quantity = request.quantity;
        deviceProcurement.expiredDate = request.expiredDate;
        deviceProcurement.purchasedDate = request.purchasedDate;
        deviceProcurement.createdTime = LocalDateTime.now();
        deviceProcurement.updatedTime = LocalDateTime.now();
        BOCreateDeviceProcurementResponse response = new BOCreateDeviceProcurementResponse();
        try (Transaction transaction = database.beginTransaction()) {
            response.id = deviceProcurementRepository.insert(deviceProcurement).orElseThrow();
            List<Device> devices = LongStream.rangeClosed(1, request.quantity).mapToObj(idx -> {
                Device device = new Device();
                device.name = request.deviceName;
                device.deviceProcurementId = response.id;
                device.status = DeviceStatus.IN_STOCK;
                device.createdTime = LocalDateTime.now();
                device.updatedTime = LocalDateTime.now();
                return device;
            }).collect(Collectors.toList());
            deviceRepository.batchInsert(devices);
            transaction.commit();
        }
        return response;
    }

    public void update(Long id, BOUpdateDeviceRequest request) {
        Device device = getDevice(id);
        device.status = DeviceStatus.valueOf(request.status.name());
        device.updatedTime = LocalDateTime.now();
        deviceRepository.update(device);
    }

    private Device getDevice(Long id) {
        return deviceRepository.get(id).orElseThrow(() -> new NotFoundException("Device not found by id = " + id));
    }

    public BOGetDeviceProcurementResponse get(Long id) {
        DeviceProcurement deviceProcurement = deviceProcurementRepository.get(id).orElseThrow(() -> new NotFoundException("Device procurement not found by id = " + id));
        BOGetDeviceProcurementResponse response = new BOGetDeviceProcurementResponse();
        response.id = deviceProcurement.id;
        response.deviceName = deviceProcurement.deviceName;
        response.quantity = deviceProcurement.quantity;
        response.purchasedDate = deviceProcurement.purchasedDate;
        response.expiredDate = deviceProcurement.expiredDate;
        List<Device> devices = deviceRepository.select("device_procurement_id = ?", deviceProcurement.id);
        response.devices = devices.stream().map(device -> {
            var view = new BOGetDeviceProcurementResponse.DeviceView();
            view.id = device.id;
            view.name = device.name;
            view.status = DeviceStatusView.valueOf(device.status.name());
            return view;
        }).collect(Collectors.toList());
        return response;
    }

    public SearchDeviceResponse search(SearchDeviceRequest request) {
        Query<Device> query = deviceRepository.select();
        query.orderBy("updated_time DESC");
        query.skip(request.skip);
        query.limit(request.limit);
        if (!Strings.isBlank(request.name)) {
            query.where("name like ?", request.name + "%");
        }
        if (request.status != null) {
            query.where("status = ?", request.status.name());
        }
        if (request.isExpired != null) {
            String subQuerySQL = "EXISTS (SELECT 1 FROM device_procurements dp WHERE device_procurement_id = dp.id AND ("
                                 + (request.isExpired ? "dp.expired_date IS NOT NULL AND dp.expired_date < NOW()" : "dp.expired_date IS NULL OR dp.expired_date >= NOW()")
                                 + "))";
            query.where(subQuerySQL);
        }
        SearchDeviceResponse result = new SearchDeviceResponse();
        result.total = query.count();
        List<Device> devices = query.fetch();
        List<Long> deviceProcurementIds = devices.stream().map(device -> device.deviceProcurementId).distinct().collect(Collectors.toList());
        Query<DeviceProcurement> deviceProcurementQuery = deviceProcurementRepository.select();
        deviceProcurementQuery.in("id", deviceProcurementIds);
        List<DeviceProcurement> deviceProcurements = deviceProcurementQuery.fetch();
        result.devices = devices.stream().map(device -> recordToView(device, deviceProcurements)).collect(Collectors.toList());
        return result;
    }

    private SearchDeviceResponse.DeviceView recordToView(Device device, List<DeviceProcurement> deviceProcurements) {
        var view = new SearchDeviceResponse.DeviceView();
        view.id = device.id;
        view.name = device.name;
        view.status = DeviceStatusView.valueOf(device.status.name());
        DeviceProcurement deviceProcurement = deviceProcurements.stream().filter(procurement -> procurement.id.equals(device.deviceProcurementId)).findAny().orElseThrow();
        view.purchasedDate = deviceProcurement.purchasedDate;
        view.expiredDate = deviceProcurement.expiredDate;
        return view;
    }

    public ListDeviceSummaryResponse listDeviceSummaries(ListDeviceSummaryRequest request) {
        Query<DeviceProcurement> deviceProcurementQuery = deviceProcurementRepository.select();
        List<Object> viewSQLParams = new ArrayList<>();
        viewSQLParams.add(DeviceStatus.IN_STOCK);
        StringBuilder viewSQLWhere = new StringBuilder();
        if (!Strings.isBlank(request.deviceName)) {
            deviceProcurementQuery.where("device_name like ?", request.deviceName + "%");
            viewSQLWhere.append("WHERE device_name like ?");
            viewSQLParams.add(request.deviceName + "%");
        }
        ListDeviceSummaryResponse response = new ListDeviceSummaryResponse();
        response.total = deviceProcurementQuery.count();
        viewSQLParams.add(request.skip);
        viewSQLParams.add(request.limit);
        String sql = "SELECT dp.id, dp.device_name, dp.quantity, dp.purchased_date, dp.expired_date, "
                     + "(SELECT COUNT(1) FROM devices d WHERE d.device_procurement_id = dp.id AND d.status = ?) as quantity_in_stock "
                     + "FROM device_procurements dp " + viewSQLWhere.toString() + " LIMIT ?, ?";
        List<DeviceSummary> summaries = database.select(sql, DeviceSummary.class, viewSQLParams.toArray());
        response.deviceSummaries = summaries.stream().map(summary -> {
            var view = new ListDeviceSummaryResponse.DeviceSummaryView();
            view.id = summary.id;
            view.deviceName = summary.deviceName;
            view.quantity = summary.quantity;
            view.quantityInStock = summary.quantityInStock;
            view.purchasedDate = summary.purchasedDate;
            view.expiredDate = summary.expiredDate;
            return view;
        }).collect(Collectors.toList());
        return response;
    }
}
