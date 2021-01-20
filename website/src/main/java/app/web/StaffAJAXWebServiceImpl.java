package app.web;

import app.api.StaffAJAXWebService;
import app.api.device.DeviceApplicationStatusView;
import app.api.device.SearchDeviceApplicationRequest;
import app.api.device.SearchDeviceApplicationResponse;
import app.api.interview.SearchInterviewRequest;
import app.api.interview.SearchInterviewResponse;
import app.api.interview.UpdateInterviewRequest;
import app.api.staff.CreateJobSummaryAJAXRequest;
import app.api.staff.CreateJobSummaryAJAXResponse;
import app.api.staff.CreateJobSummaryRequest;
import app.api.staff.CreateJobSummaryResponse;
import app.api.staff.FinishInterviewAJAXRequest;
import app.api.staff.GetAssignedInterviewAJAXRequest;
import app.api.staff.GetAssignedInterviewAJAXResponse;
import app.api.staff.GetStaffInfoAJAXResponse;
import app.api.staff.GetStaffResponse;
import app.api.staff.StaffLoginAJAXRequest;
import app.api.staff.StaffLoginAJAXResponse;
import app.api.staff.StaffLoginRequest;
import app.api.staff.StaffLoginResponse;
import app.api.staff.UpdateJobSummaryAJAXRequest;
import app.api.staff.UpdateJobSummaryRequest;
import app.common.web.interceptor.LoginRequired;
import app.exception.ResourceNotAccessibleException;
import app.service.DeviceService;
import app.service.InterviewService;
import app.service.SessionService;
import app.service.StaffService;
import core.framework.inject.Inject;
import core.framework.web.WebContext;

import java.util.Optional;
import java.util.stream.Collectors;

import static app.service.SessionService.SESSION_LOGIN_STAFF_ID_KEY;

/**
 * @author beckl
 */
public class StaffAJAXWebServiceImpl implements StaffAJAXWebService {
    @Inject
    StaffService staffService;
    @Inject
    DeviceService deviceService;
    @Inject
    InterviewService interviewService;
    @Inject
    SessionService sessionService;
    @Inject
    WebContext webContext;

    @LoginRequired(SESSION_LOGIN_STAFF_ID_KEY)
    @Override
    public CreateJobSummaryAJAXResponse createJobSummary(CreateJobSummaryAJAXRequest request) {
        String staffIdValue = sessionService.getLoginStaffId().orElseThrow(ResourceNotAccessibleException::new);
        CreateJobSummaryRequest createJobSummaryRequest = new CreateJobSummaryRequest();
        createJobSummaryRequest.title = request.title;
        createJobSummaryRequest.summary = request.summary;
        CreateJobSummaryResponse createJobSummaryResponse = staffService.createJobSummary(Long.valueOf(staffIdValue), createJobSummaryRequest);
        CreateJobSummaryAJAXResponse response = new CreateJobSummaryAJAXResponse();
        response.jobSummaryId = createJobSummaryResponse.jobSummaryId;
        return response;
    }

    @LoginRequired(SESSION_LOGIN_STAFF_ID_KEY)
    @Override
    public void updateJobSummary(String jobSummaryId, UpdateJobSummaryAJAXRequest request) {
        String staffIdValue = sessionService.getLoginStaffId().orElseThrow(ResourceNotAccessibleException::new);
        UpdateJobSummaryRequest updateJobSummaryRequest = new UpdateJobSummaryRequest();
        updateJobSummaryRequest.title = request.title;
        updateJobSummaryRequest.summary = request.summary;
        staffService.updateJobSummary(Long.valueOf(staffIdValue), jobSummaryId, updateJobSummaryRequest);
    }

    @LoginRequired(SESSION_LOGIN_STAFF_ID_KEY)
    @Override
    public GetStaffInfoAJAXResponse getStaffInfo() {
        String staffIdValue = sessionService.getLoginStaffId().orElseThrow(ResourceNotAccessibleException::new);
        Long staffId = Long.valueOf(staffIdValue);
        GetStaffResponse staffResponse = staffService.getStaff(staffId);
        GetStaffInfoAJAXResponse response = new GetStaffInfoAJAXResponse();
        response.id = staffResponse.id;
        response.email = staffResponse.email;
        response.firstName = staffResponse.firstName;
        response.lastName = staffResponse.lastName;
        response.dob = staffResponse.dob;
        response.startedDate = staffResponse.startedDate;
        Optional.ofNullable(staffResponse.jobSummaries).ifPresent(jobSummaries -> response.jobSummaries = jobSummaries.stream().map(summary -> {
            var summaryView = new GetStaffInfoAJAXResponse.JobSummaryAJAXView();
            summaryView.id = summary.id;
            summaryView.title = summary.title;
            summaryView.summary = summary.summary;
            return summaryView;
        }).collect(Collectors.toList()));
        SearchDeviceApplicationRequest searchDeviceApplicationRequest = new SearchDeviceApplicationRequest();
        searchDeviceApplicationRequest.staffId = staffId;
        searchDeviceApplicationRequest.status = DeviceApplicationStatusView.APPROVED;
        SearchDeviceApplicationResponse searchDeviceApplicationResponse = deviceService.searchDevice4Staff(searchDeviceApplicationRequest);
        Optional.ofNullable(searchDeviceApplicationResponse.applications).ifPresent(applications -> response.devices = applications.stream().map(application -> {
            var device = new GetStaffInfoAJAXResponse.AppliedDeviceAJAXView();
            device.id = application.id;
            device.deviceId = application.deviceId;
            device.deviceName = application.deviceName;
            device.purpose = application.purpose;
            return device;
        }).collect(Collectors.toList()));
        return response;
    }

    @LoginRequired(SESSION_LOGIN_STAFF_ID_KEY)
    @Override
    public GetAssignedInterviewAJAXResponse listAssignedInterview(GetAssignedInterviewAJAXRequest request) {
        String staffIdValue = sessionService.getLoginStaffId().orElseThrow(ResourceNotAccessibleException::new);
        SearchInterviewRequest searchInterviewRequest = new SearchInterviewRequest();
        searchInterviewRequest.staffId = Long.valueOf(staffIdValue);
        searchInterviewRequest.jobPosition = request.jobPosition;
        searchInterviewRequest.isCompleted = request.isCompleted;
        searchInterviewRequest.skip = request.skip;
        searchInterviewRequest.limit = request.limit;
        SearchInterviewResponse searchInterviewResponse = interviewService.search(searchInterviewRequest);
        GetAssignedInterviewAJAXResponse response = new GetAssignedInterviewAJAXResponse();
        response.total = searchInterviewResponse.total;
        Optional.ofNullable(searchInterviewResponse.interviews).ifPresent(interviews -> response.interviews = interviews.stream().map(interview -> {
            var interviewView = new GetAssignedInterviewAJAXResponse.InterviewAJAXView();
            interviewView.id = interview.id;
            interviewView.jobPosition = interview.jobPosition;
            interviewView.intervieweeId = interview.intervieweeId;
            interviewView.appointedTime = interview.appointedTime;
            interviewView.comment = interview.comment;
            interviewView.completed = interview.completed;
            return interviewView;
        }).collect(Collectors.toList()));
        return response;
    }

    @LoginRequired(SESSION_LOGIN_STAFF_ID_KEY)
    @Override
    public void finishInterview(FinishInterviewAJAXRequest request) {
        UpdateInterviewRequest updateInterviewRequest = new UpdateInterviewRequest();
        updateInterviewRequest.comment = request.comment;
        interviewService.update(request.interviewId, updateInterviewRequest);
    }

    @Override
    public StaffLoginAJAXResponse login(StaffLoginAJAXRequest request) {
        StaffLoginRequest staffLoginRequest = new StaffLoginRequest();
        staffLoginRequest.email = request.email;
        staffLoginRequest.password = request.password;
        StaffLoginResponse staffLoginResponse = staffService.login(staffLoginRequest);
        if (staffLoginResponse.success && staffLoginResponse.staffId != null) {
            webContext.request().session().set(SESSION_LOGIN_STAFF_ID_KEY, String.valueOf(staffLoginResponse.staffId));
        }
        StaffLoginAJAXResponse response = new StaffLoginAJAXResponse();
        response.success = staffLoginResponse.success;
        response.staffId = staffLoginResponse.staffId;
        response.errorMessage = staffLoginResponse.errorMessage;
        return response;
    }

    @LoginRequired(SESSION_LOGIN_STAFF_ID_KEY)
    @Override
    public void logout() {
        webContext.request().session().invalidate();
    }
}