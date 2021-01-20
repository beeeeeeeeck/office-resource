package app.staff.web;

import app.api.StaffWebService;
import app.api.staff.CreateJobSummaryRequest;
import app.api.staff.CreateJobSummaryResponse;
import app.api.staff.GetStaffResponse;
import app.api.staff.SearchStaffRequest;
import app.api.staff.SearchStaffResponse;
import app.api.staff.StaffLoginRequest;
import app.api.staff.StaffLoginResponse;
import app.api.staff.UpdateJobSummaryRequest;
import app.staff.service.StaffService;
import core.framework.inject.Inject;

/**
 * @author beckl
 */
public class StaffWebServiceImpl implements StaffWebService {
    @Inject
    StaffService staffService;

    @Override
    public SearchStaffResponse search(SearchStaffRequest request) {
        return staffService.search(request);
    }

    @Override
    public GetStaffResponse get(Long id) {
        return staffService.get(id);
    }

    @Override
    public CreateJobSummaryResponse createJobSummary(Long id, CreateJobSummaryRequest request) {
        return staffService.createJobSummary(id, request);
    }

    @Override
    public void updateJobSummary(Long id, String jobSummaryId, UpdateJobSummaryRequest request) {
        staffService.updateJobSummary(id, jobSummaryId, request);
    }

    @Override
    public StaffLoginResponse login(StaffLoginRequest request) {
        return staffService.login(request);
    }
}
