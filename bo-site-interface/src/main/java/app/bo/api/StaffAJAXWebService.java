package app.bo.api;

import app.bo.api.staff.CreateStaffAJAXRequest;
import app.bo.api.staff.CreateStaffAJAXResponse;
import app.bo.api.staff.SearchStaffAJAXRequest;
import app.bo.api.staff.SearchStaffAJAXResponse;
import app.bo.api.staff.UpdateStaffAJAXRequest;
import core.framework.api.http.HTTPStatus;
import core.framework.api.web.service.POST;
import core.framework.api.web.service.PUT;
import core.framework.api.web.service.Path;
import core.framework.api.web.service.PathParam;
import core.framework.api.web.service.ResponseStatus;

/**
 * @author beckl
 */
public interface StaffAJAXWebService {
    @PUT
    @Path("/staff")
    SearchStaffAJAXResponse search(SearchStaffAJAXRequest request);

    @PUT
    @Path("/staff/:id/status")
    void update(@PathParam("id") Long id, UpdateStaffAJAXRequest request);

    @POST
    @Path("/staff")
    @ResponseStatus(HTTPStatus.CREATED)
    CreateStaffAJAXResponse createStaff(CreateStaffAJAXRequest request);
}
