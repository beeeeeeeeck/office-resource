package app.bo.web;

import app.api.staff.BOSearchStaffRequest;
import app.api.staff.BOSearchStaffResponse;
import app.api.staff.BOUpdateStaffRequest;
import app.bo.api.StaffAJAXWebService;
import app.bo.api.staff.CreateStaffAJAXRequest;
import app.bo.api.staff.CreateStaffAJAXResponse;
import app.bo.api.staff.SearchStaffAJAXRequest;
import app.bo.api.staff.SearchStaffAJAXResponse;
import app.bo.api.staff.UpdateStaffAJAXRequest;
import app.bo.service.StaffService;
import app.common.web.interceptor.LoginRequired;
import core.framework.inject.Inject;

import java.util.Optional;
import java.util.stream.Collectors;

import static app.bo.ajax.LoginController.SESSION_USER_LOGIN_KEY;

/**
 * @author beckl
 */
public class StaffAJAXWebServiceImpl implements StaffAJAXWebService {
    @Inject
    StaffService staffService;

    @LoginRequired(SESSION_USER_LOGIN_KEY)
    @Override
    public SearchStaffAJAXResponse search(SearchStaffAJAXRequest request) {
        BOSearchStaffRequest searchStaffRequest = new BOSearchStaffRequest();
        searchStaffRequest.email = request.email;
        searchStaffRequest.firstName = request.firstName;
        searchStaffRequest.lastName = request.lastName;
        searchStaffRequest.isActive = request.isActive;
        searchStaffRequest.skip = request.skip;
        searchStaffRequest.limit = request.limit;
        BOSearchStaffResponse searchStaffResponse = staffService.search(searchStaffRequest);
        SearchStaffAJAXResponse response = new SearchStaffAJAXResponse();
        response.total = searchStaffResponse.total;
        Optional.ofNullable(searchStaffResponse.staffs).ifPresent(staffs -> response.staffs = staffs.stream().map(view -> {
            var staffView = new SearchStaffAJAXResponse.StaffAJAXView();
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

    @LoginRequired(SESSION_USER_LOGIN_KEY)
    @Override
    public void update(Long id, UpdateStaffAJAXRequest request) {
        BOUpdateStaffRequest updateStaffRequest = new BOUpdateStaffRequest();
        updateStaffRequest.active = request.active;
        staffService.update(id, updateStaffRequest);
    }

    @LoginRequired(SESSION_USER_LOGIN_KEY)
    @Override
    public CreateStaffAJAXResponse createStaff(CreateStaffAJAXRequest request) {
        return staffService.createStaff(request);
    }
}
