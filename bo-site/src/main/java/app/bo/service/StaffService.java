package app.bo.service;

import app.api.BOIntervieweeWebService;
import app.api.BOStaffWebService;
import app.api.interviewee.BOGetIntervieweeResponse;
import app.api.interviewee.BOGetResumeResponse;
import app.api.interviewee.InterviewStatusView;
import app.api.staff.BOCreateStaffRequest;
import app.api.staff.BOCreateStaffResponse;
import app.api.staff.BOSearchStaffRequest;
import app.api.staff.BOSearchStaffResponse;
import app.api.staff.BOUpdateStaffRequest;
import app.bo.api.staff.CreateStaffAJAXRequest;
import app.bo.api.staff.CreateStaffAJAXResponse;
import app.bo.exception.InvalidRequestValuesException;
import core.framework.inject.Inject;

/**
 * @author beckl
 */
public class StaffService {
    @Inject
    BOStaffWebService staffWebService;
    @Inject
    BOIntervieweeWebService intervieweeWebService;

    public BOSearchStaffResponse search(BOSearchStaffRequest request) {
        return staffWebService.search(request);
    }

    public void update(Long id, BOUpdateStaffRequest request) {
        staffWebService.update(id, request);
    }

    public CreateStaffAJAXResponse createStaff(CreateStaffAJAXRequest request) {
        BOGetIntervieweeResponse getIntervieweeResponse = intervieweeWebService.get(request.intervieweeId);
        if (InterviewStatusView.PASSED != getIntervieweeResponse.status) {
            throw new InvalidRequestValuesException("Interviewee is not passed");
        }
        BOGetResumeResponse getResumeResponse = intervieweeWebService.getResume(request.intervieweeId);
        BOCreateStaffRequest createStaffRequest = new BOCreateStaffRequest();
        createStaffRequest.email = request.email;
        createStaffRequest.dob = getResumeResponse.dob;
        createStaffRequest.firstName = getResumeResponse.firstName;
        createStaffRequest.lastName = getResumeResponse.lastName;
        createStaffRequest.password = getIntervieweeResponse.password;
        createStaffRequest.passwordSalt = getIntervieweeResponse.passwordSalt;
        createStaffRequest.startedDate = request.startedDate;
        BOCreateStaffResponse createStaffResponse = staffWebService.create(createStaffRequest);
        CreateStaffAJAXResponse response = new CreateStaffAJAXResponse();
        response.id = createStaffResponse.id;
        response.email = createStaffRequest.email;
        response.dob = createStaffRequest.dob;
        response.firstName = createStaffRequest.firstName;
        response.lastName = createStaffRequest.lastName;
        response.active = createStaffResponse.active;
        response.startedDate = createStaffRequest.startedDate;
        return response;
    }
}
