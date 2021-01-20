package app.api;

import app.api.staff.CreateJobSummaryAJAXRequest;
import app.api.staff.CreateJobSummaryAJAXResponse;
import app.api.staff.FinishInterviewAJAXRequest;
import app.api.staff.GetAssignedInterviewAJAXRequest;
import app.api.staff.GetAssignedInterviewAJAXResponse;
import app.api.staff.GetStaffInfoAJAXResponse;
import app.api.staff.StaffLoginAJAXRequest;
import app.api.staff.StaffLoginAJAXResponse;
import app.api.staff.UpdateJobSummaryAJAXRequest;
import core.framework.api.http.HTTPStatus;
import core.framework.api.web.service.GET;
import core.framework.api.web.service.POST;
import core.framework.api.web.service.PUT;
import core.framework.api.web.service.Path;
import core.framework.api.web.service.PathParam;
import core.framework.api.web.service.ResponseStatus;

/**
 * @author beckl
 */
public interface StaffAJAXWebService {
    @POST
    @Path("/staff/job-summary")
    @ResponseStatus(HTTPStatus.CREATED)
    CreateJobSummaryAJAXResponse createJobSummary(CreateJobSummaryAJAXRequest request);

    @PUT
    @Path("/staff/job-summary/:id")
    void updateJobSummary(@PathParam("id") String jobSummaryId, UpdateJobSummaryAJAXRequest request);

    @GET
    @Path("/staff/information")
    GetStaffInfoAJAXResponse getStaffInfo();

    @POST
    @Path("/staff/interview")
    GetAssignedInterviewAJAXResponse listAssignedInterview(GetAssignedInterviewAJAXRequest request);

    @PUT
    @Path("/staff/interview")
    void finishInterview(FinishInterviewAJAXRequest request);

    @POST
    @Path("/staff/login")
    StaffLoginAJAXResponse login(StaffLoginAJAXRequest request);

    @GET
    @Path("/staff/logout")
    @ResponseStatus(HTTPStatus.OK)
    void logout();
}
