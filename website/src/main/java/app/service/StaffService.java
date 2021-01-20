package app.service;

import app.api.StaffWebService;
import app.api.staff.CreateJobSummaryRequest;
import app.api.staff.CreateJobSummaryResponse;
import app.api.staff.GetStaffResponse;
import app.api.staff.StaffLoginRequest;
import app.api.staff.StaffLoginResponse;
import app.api.staff.UpdateJobSummaryRequest;
import core.framework.inject.Inject;

/**
 * @author beckl
 */
public class StaffService {
    @Inject
    StaffWebService staffWebService;

    public StaffLoginResponse login(StaffLoginRequest request) {
        return staffWebService.login(request);
    }

    public CreateJobSummaryResponse createJobSummary(Long id, CreateJobSummaryRequest request) {
        return staffWebService.createJobSummary(id, request);
    }

    public void updateJobSummary(Long id, String jobSummaryId, UpdateJobSummaryRequest request) {
        staffWebService.updateJobSummary(id, jobSummaryId, request);
    }

    public GetStaffResponse getStaff(Long id) {
        return staffWebService.get(id);
    }
}
