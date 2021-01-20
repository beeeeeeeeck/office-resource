package app.api;

import app.api.interviewee.BOGetIntervieweeResponse;
import app.api.interviewee.BOGetResumeResponse;
import app.api.interviewee.BOSearchIntervieweeRequest;
import app.api.interviewee.BOSearchIntervieweeResponse;
import app.api.interviewee.BOUpdateIntervieweeRequest;
import core.framework.api.web.service.GET;
import core.framework.api.web.service.PUT;
import core.framework.api.web.service.Path;
import core.framework.api.web.service.PathParam;

/**
 * @author beckl
 */
public interface BOIntervieweeWebService {
    @PUT
    @Path("/bo/interviewee")
    BOSearchIntervieweeResponse search(BOSearchIntervieweeRequest request);

    @GET
    @Path("/bo/interviewee/:id")
    BOGetIntervieweeResponse get(@PathParam("id") Long id);

    @PUT
    @Path("/bo/interviewee/:id")
    void update(@PathParam("id") Long id, BOUpdateIntervieweeRequest request);

    @GET
    @Path("/bo/interviewee/:id/resume")
    BOGetResumeResponse getResume(@PathParam("id") Long id);
}