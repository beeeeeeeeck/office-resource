package app.api;

import app.api.staff.CreateJobSummaryRequest;
import app.api.staff.CreateJobSummaryResponse;
import app.api.staff.GetStaffResponse;
import app.api.staff.SearchStaffRequest;
import app.api.staff.SearchStaffResponse;
import app.api.staff.StaffLoginRequest;
import app.api.staff.StaffLoginResponse;
import app.api.staff.UpdateJobSummaryRequest;
import core.framework.api.web.service.GET;
import core.framework.api.web.service.POST;
import core.framework.api.web.service.PUT;
import core.framework.api.web.service.Path;
import core.framework.api.web.service.PathParam;

/**
 * @author beckl
 */
public interface StaffWebService {
    @PUT
    @Path("/staff")
    SearchStaffResponse search(SearchStaffRequest request);

    @GET
    @Path("/staff/:id")
    GetStaffResponse get(@PathParam("id") Long id);

    @POST
    @Path("/staff/:id/job-summary")
    CreateJobSummaryResponse createJobSummary(@PathParam("id") Long id, CreateJobSummaryRequest request);

    @PUT
    @Path("/staff/:id/job-summary/:summaryId")
    void updateJobSummary(@PathParam("id") Long id, @PathParam("summaryId") String jobSummaryId, UpdateJobSummaryRequest request);

    @POST
    @Path("/staff/login")
    StaffLoginResponse login(StaffLoginRequest request);
}
