package app.api;

import app.api.interview.GetInterviewResponse;
import app.api.interview.SearchInterviewRequest;
import app.api.interview.SearchInterviewResponse;
import app.api.interview.UpdateInterviewRequest;
import core.framework.api.web.service.GET;
import core.framework.api.web.service.PUT;
import core.framework.api.web.service.Path;
import core.framework.api.web.service.PathParam;

/**
 * @author beckl
 */
public interface InterviewWebService {
    @PUT
    @Path("/interview/:id")
    void update(@PathParam("id") Long id, UpdateInterviewRequest request);

    @GET
    @Path("/interview/:id")
    GetInterviewResponse get(@PathParam("id") Long id);

    @PUT
    @Path("/interview")
    SearchInterviewResponse search(SearchInterviewRequest request);
}
