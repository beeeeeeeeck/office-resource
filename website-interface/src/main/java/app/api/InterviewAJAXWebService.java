package app.api;

import app.api.interview.CreateIntervieweeResumeAJAXRequest;
import app.api.interview.CreateIntervieweeResumeAJAXResponse;
import app.api.interview.GetIntervieweeResumeAJAXResponse;
import app.api.interview.IntervieweeLoginAJAXRequest;
import app.api.interview.IntervieweeLoginAJAXResponse;
import app.api.interview.RegisterIntervieweeAJAXRequest;
import app.api.interview.RegisterIntervieweeAJAXResponse;
import core.framework.api.http.HTTPStatus;
import core.framework.api.web.service.GET;
import core.framework.api.web.service.POST;
import core.framework.api.web.service.Path;
import core.framework.api.web.service.ResponseStatus;

/**
 * @author beckl
 */
public interface InterviewAJAXWebService {
    @POST
    @Path("/interviewee/register")
    @ResponseStatus(HTTPStatus.CREATED)
    RegisterIntervieweeAJAXResponse registerAsInterviewee(RegisterIntervieweeAJAXRequest request);

    @POST
    @Path("/interviewee/resume")
    @ResponseStatus(HTTPStatus.CREATED)
    CreateIntervieweeResumeAJAXResponse createIntervieweeResume(CreateIntervieweeResumeAJAXRequest request);

    @GET
    @Path("/interviewee/resume")
    GetIntervieweeResumeAJAXResponse getIntervieweeResume();

    @POST
    @Path("/interviewee/login")
    IntervieweeLoginAJAXResponse login(IntervieweeLoginAJAXRequest request);

    @GET
    @Path("/interviewee/logout")
    @ResponseStatus(HTTPStatus.OK)
    void logout();
}
