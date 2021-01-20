package app.api;

import app.api.interviewee.CreateIntervieweeRequest;
import app.api.interviewee.CreateIntervieweeResponse;
import app.api.interviewee.GetIntervieweeResponse;
import app.api.interviewee.GetResumeResponse;
import app.api.interviewee.IntervieweeLoginRequest;
import app.api.interviewee.IntervieweeLoginResponse;
import app.api.interviewee.SaveResumeRequest;
import app.api.interviewee.SaveResumeResponse;
import core.framework.api.http.HTTPStatus;
import core.framework.api.web.service.GET;
import core.framework.api.web.service.POST;
import core.framework.api.web.service.Path;
import core.framework.api.web.service.PathParam;
import core.framework.api.web.service.ResponseStatus;

/**
 * @author beckl
 */
public interface IntervieweeWebService {
    @POST
    @Path("/interviewee")
    @ResponseStatus(HTTPStatus.CREATED)
    CreateIntervieweeResponse create(CreateIntervieweeRequest request);

    @GET
    @Path("/interviewee/:id")
    GetIntervieweeResponse get(@PathParam("id") Long id);

    @POST
    @Path("/interviewee/:id/resume")
    @ResponseStatus(HTTPStatus.CREATED)
    SaveResumeResponse saveResume(@PathParam("id") Long id, SaveResumeRequest request);

    @GET
    @Path("/interviewee/:id/resume")
    GetResumeResponse getResume(@PathParam("id") Long id);

    @POST
    @Path("/interviewee/login")
    IntervieweeLoginResponse login(IntervieweeLoginRequest request);
}
