package app.api;

import app.api.interview.BOCreateInterviewRequest;
import app.api.interview.BOCreateInterviewResponse;
import app.api.interview.BOSearchInterviewRequest;
import app.api.interview.BOSearchInterviewResponse;
import core.framework.api.http.HTTPStatus;
import core.framework.api.web.service.POST;
import core.framework.api.web.service.PUT;
import core.framework.api.web.service.Path;
import core.framework.api.web.service.ResponseStatus;

/**
 * @author beckl
 */
public interface BOInterviewWebService {
    @POST
    @Path("/bo/interview")
    @ResponseStatus(HTTPStatus.CREATED)
    BOCreateInterviewResponse create(BOCreateInterviewRequest request);

    @PUT
    @Path("/bo/interview")
    BOSearchInterviewResponse search(BOSearchInterviewRequest request);
}
