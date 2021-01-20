package app.schedule.service;

import app.api.StaffWebService;
import app.api.staff.SearchStaffRequest;
import app.api.staff.SearchStaffResponse;
import core.framework.inject.Inject;

/**
 * @author beckl
 */
public class StaffService {
    @Inject
    StaffWebService staffWebService;

    public SearchStaffResponse listBulkStaffs(int skip, int limit) {
        SearchStaffRequest searchStaffRequest = new SearchStaffRequest();
        searchStaffRequest.isActive = Boolean.TRUE;
        searchStaffRequest.skip = skip;
        searchStaffRequest.limit = limit;
        return staffWebService.search(searchStaffRequest);
    }
}
