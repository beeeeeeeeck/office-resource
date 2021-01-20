package app.device.service;

import app.api.device.CreateDeviceApplicationRequest;
import app.api.device.CreateDeviceApplicationResponse;
import app.api.device.DeviceApplicationStatusView;
import app.api.device.GetDeviceApplicationResponse;
import app.api.device.SearchDeviceApplicationRequest;
import app.api.device.SearchDeviceApplicationResponse;
import app.device.domain.Device;
import app.device.domain.DeviceApplication;
import app.device.domain.DeviceApplicationStatus;
import app.device.domain.DeviceStatus;
import app.device.exception.DeviceUnavailableException;
import core.framework.db.Database;
import core.framework.db.Query;
import core.framework.db.Repository;
import core.framework.db.Transaction;
import core.framework.inject.Inject;
import core.framework.util.Strings;
import core.framework.web.exception.BadRequestException;
import core.framework.web.exception.NotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.stream.Collectors;

/**
 * @author beckl
 */
public class DeviceApplicationService {
    public static final String REDIS_LOCK_KEY_TEMPLATE_4_DEVICE = "DEVICE_";
    public static final String REDIS_LOCK_VALUE_TEMPLATE_4_APPLICATION = "DEVICE_APPLICATION_";
    private final Logger logger = LoggerFactory.getLogger(DeviceApplicationService.class);
    @Inject
    Repository<Device> deviceRepository;
    @Inject
    Repository<DeviceApplication> deviceApplicationRepository;
    @Inject
    Database database;
    @Inject
    RedisService redisService;

    public CreateDeviceApplicationResponse create(CreateDeviceApplicationRequest request) {
        Device device = deviceRepository.get(request.deviceId).orElseThrow(() -> new NotFoundException("Device not found by id = " + request.deviceId));
        if (device.status != DeviceStatus.IN_STOCK) {
            throw new DeviceUnavailableException(device.name);
        }
        DeviceApplication application = new DeviceApplication();
        application.purpose = request.purpose;
        application.deviceId = request.deviceId;
        application.staffId = request.staffId;
        application.deviceName = device.name;
        application.status = DeviceApplicationStatus.CREATED;
        application.createdTime = LocalDateTime.now();
        application.updatedTime = LocalDateTime.now();
        CreateDeviceApplicationResponse response = new CreateDeviceApplicationResponse();
        response.id = deviceApplicationRepository.insert(application).orElseThrow();
        response.purpose = application.purpose;
        response.deviceId = application.deviceId;
        response.staffId = application.staffId;
        response.status = DeviceApplicationStatusView.valueOf(application.status.name());
        return response;
    }

    public GetDeviceApplicationResponse get(Long id) {
        DeviceApplication application = getApplication(id);
        GetDeviceApplicationResponse response = new GetDeviceApplicationResponse();
        response.id = application.id;
        response.purpose = application.purpose;
        response.deviceId = application.deviceId;
        response.staffId = application.staffId;
        response.status = DeviceApplicationStatusView.valueOf(application.status.name());
        return response;
    }

    private DeviceApplication getApplication(Long id) {
        return deviceApplicationRepository.get(id).orElseThrow(() -> new NotFoundException("Device application not found by id = " + id));
    }

    public SearchDeviceApplicationResponse search(SearchDeviceApplicationRequest request) {
        Query<DeviceApplication> query = deviceApplicationRepository.select();
        query.skip(request.skip);
        query.limit(request.limit);
        if (!Strings.isBlank(request.name)) {
            query.where("device_name LIKE ?", request.name + "%");
        }
        if (request.staffId != null) {
            query.where("staff_id = ?", request.staffId);
        }
        if (request.status != null) {
            query.where("status = ?", request.status.name());
        }
        if (request.deviceIdList != null && !request.deviceIdList.isEmpty()) {
            query.in("device_id", request.deviceIdList);
        }
        SearchDeviceApplicationResponse result = new SearchDeviceApplicationResponse();
        result.applications = query.fetch().stream().map(this::recordToView).collect(Collectors.toList());
        result.total = query.count();
        return result;
    }

    private SearchDeviceApplicationResponse.DeviceApplicationView recordToView(DeviceApplication application) {
        var view = new SearchDeviceApplicationResponse.DeviceApplicationView();
        view.id = application.id;
        view.purpose = application.purpose;
        view.deviceId = application.deviceId;
        view.staffId = application.staffId;
        view.deviceName = application.deviceName;
        view.status = DeviceApplicationStatusView.valueOf(application.status.name());
        return view;
    }

    public void approve(Long id) {
        DeviceApplication application = getApplication(id);
        if (application.status != DeviceApplicationStatus.CREATED) {
            throw new BadRequestException("Device application is NOT in expected status to approve");
        }
        String lockedKey = REDIS_LOCK_KEY_TEMPLATE_4_DEVICE + application.deviceId;
        String lockedValue = REDIS_LOCK_VALUE_TEMPLATE_4_APPLICATION + application.id;
        redisService.lock(lockedKey, lockedValue, Duration.ofSeconds(3), Duration.ofSeconds(5), () -> {
            Device device = deviceRepository.get(application.deviceId).orElseThrow();
            if (device.status != DeviceStatus.IN_STOCK) {
                logger.warn("Device ({}) is unavailable as its status (Current: {})", device.name, device.status);
                throw new DeviceUnavailableException(device.name);
            }
            device.status = DeviceStatus.IN_SERVE;
            device.updatedTime = LocalDateTime.now();
            application.status = DeviceApplicationStatus.APPROVED;
            application.updatedTime = LocalDateTime.now();
            try (Transaction transaction = database.beginTransaction()) {
                deviceRepository.update(device);
                deviceApplicationRepository.update(application);
                transaction.commit();
            }
            logger.info("Redis Lock completed - Device ({}) - {}", device.name, device.status);
        });
    }

    public void returnDevice(Long id) {
        DeviceApplication application = getApplication(id);
        if (application.status != DeviceApplicationStatus.APPROVED) {
            throw new BadRequestException("Device application is NOT in expected status to return");
        }
        String lockedKey = REDIS_LOCK_KEY_TEMPLATE_4_DEVICE + application.deviceId;
        String lockedValue = REDIS_LOCK_VALUE_TEMPLATE_4_APPLICATION + application.id;
        redisService.lock(lockedKey, lockedValue, Duration.ofSeconds(3), Duration.ofSeconds(5), () -> {
            Device device = deviceRepository.get(application.deviceId).orElseThrow();
            device.status = DeviceStatus.IN_STOCK;
            device.updatedTime = LocalDateTime.now();
            application.status = DeviceApplicationStatus.RETURNED;
            application.updatedTime = LocalDateTime.now();
            try (Transaction transaction = database.beginTransaction()) {
                deviceRepository.update(device);
                deviceApplicationRepository.update(application);
                transaction.commit();
            }
            logger.info("Redis Lock completed - Device ({}) - {}", device.name, device.status);
        });
    }
}
