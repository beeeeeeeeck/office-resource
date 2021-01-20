package app.api;

import app.api.staff.BOCreateStaffRequest;
import app.api.staff.BOCreateStaffResponse;
import app.api.staff.BOSearchStaffRequest;
import app.api.staff.BOSearchStaffResponse;
import app.api.staff.BOUpdateStaffRequest;
import core.framework.api.http.HTTPStatus;
import core.framework.api.web.service.POST;
import core.framework.api.web.service.PUT;
import core.framework.api.web.service.Path;
import core.framework.api.web.service.PathParam;
import core.framework.api.web.service.ResponseStatus;

/**
 * @author beckl
 */
public interface BOStaffWebService {
    @POST
    @Path("/bo/staff")
    @ResponseStatus(HTTPStatus.CREATED)
    BOCreateStaffResponse create(BOCreateStaffRequest request);

    @PUT
    @Path("/bo/staff")
    BOSearchStaffResponse search(BOSearchStaffRequest request);

    @PUT
    @Path("/bo/staff/:id")
    void update(@PathParam("id") Long id, BOUpdateStaffRequest request);
}
