package app.bo.api;

import app.bo.api.interview.AssignInterviewAJAXRequest;
import app.bo.api.interview.AssignInterviewAJAXResponse;
import app.bo.api.interview.SearchInterviewAJAXRequest;
import app.bo.api.interview.SearchInterviewAJAXResponse;
import app.bo.api.interview.SearchIntervieweeAJAXRequest;
import app.bo.api.interview.SearchIntervieweeAJAXResponse;
import app.bo.api.interview.UpdateIntervieweeStatusAJAXRequest;
import core.framework.api.http.HTTPStatus;
import core.framework.api.web.service.POST;
import core.framework.api.web.service.PUT;
import core.framework.api.web.service.Path;
import core.framework.api.web.service.PathParam;
import core.framework.api.web.service.ResponseStatus;

/**
 * @author beckl
 */
public interface InterviewAJAXWebService {
    @PUT
    @Path("/interviewee")
    SearchIntervieweeAJAXResponse search(SearchIntervieweeAJAXRequest request);

    @POST
    @Path("/interviewee/:id/assignment")
    @ResponseStatus(HTTPStatus.CREATED)
    AssignInterviewAJAXResponse assign(@PathParam("id") Long id, AssignInterviewAJAXRequest request);

    @PUT
    @Path("/interviewee/:id/interview")
    SearchInterviewAJAXResponse searchInterview4SpecifiedInterviewee(@PathParam("id") Long id, SearchInterviewAJAXRequest request);

    @PUT
    @Path("/interviewee/:id/status")
    void updateIntervieweeStatus(@PathParam("id") Long id, UpdateIntervieweeStatusAJAXRequest request);
}
