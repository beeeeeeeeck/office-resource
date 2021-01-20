package app.staff.web;

import app.api.BOStaffWebService;
import app.api.staff.BOCreateStaffRequest;
import app.api.staff.BOCreateStaffResponse;
import app.api.staff.BOSearchStaffRequest;
import app.api.staff.BOSearchStaffResponse;
import app.api.staff.BOUpdateStaffRequest;
import app.api.staff.SearchStaffRequest;
import app.api.staff.SearchStaffResponse;
import app.staff.service.StaffService;
import core.framework.inject.Inject;

import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @author beckl
 */
public class BOStaffWebServiceImpl implements BOStaffWebService {
    @Inject
    StaffService staffService;

    @Override
    public BOCreateStaffResponse create(BOCreateStaffRequest request) {
        return staffService.create(request);
    }

    @Override
    public BOSearchStaffResponse search(BOSearchStaffRequest request) {
        SearchStaffRequest searchStaffRequest = new SearchStaffRequest();
        searchStaffRequest.email = request.email;
        searchStaffRequest.firstName = request.firstName;
        searchStaffRequest.lastName = request.lastName;
        searchStaffRequest.isActive = request.isActive;
        searchStaffRequest.skip = request.skip;
        searchStaffRequest.limit = request.limit;
        SearchStaffResponse searchStaffResponse = staffService.search(searchStaffRequest);
        BOSearchStaffResponse response = new BOSearchStaffResponse();
        response.total = searchStaffResponse.total;
        Optional.ofNullable(searchStaffResponse.staffs).ifPresent(staffs -> response.staffs = staffs.stream().map(view -> {
            var staffView = new BOSearchStaffResponse.BOStaffView();
            staffView.id = view.id;
            staffView.email = view.email;
            staffView.firstName = view.firstName;
            staffView.lastName = view.lastName;
            staffView.dob = view.dob;
            staffView.active = view.active;
            staffView.startedDate = view.startedDate;
            return staffView;
        }).collect(Collectors.toList()));
        return response;
    }

    @Override
    public void update(Long id, BOUpdateStaffRequest request) {
        BOUpdateStaffRequest updateStaffRequest = new BOUpdateStaffRequest();
        updateStaffRequest.active = request.active;
        staffService.update(id, updateStaffRequest);
    }
}
