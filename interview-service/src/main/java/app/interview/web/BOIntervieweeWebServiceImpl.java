package app.interview.web;

import app.api.BOIntervieweeWebService;
import app.api.interviewee.BOGetIntervieweeResponse;
import app.api.interviewee.BOGetResumeResponse;
import app.api.interviewee.BOSearchIntervieweeRequest;
import app.api.interviewee.BOSearchIntervieweeResponse;
import app.api.interviewee.BOUpdateIntervieweeRequest;
import app.api.interviewee.GetIntervieweeResponse;
import app.api.interviewee.GetResumeResponse;
import app.interview.service.IntervieweeService;
import core.framework.inject.Inject;

/**
 * @author beckl
 */
public class BOIntervieweeWebServiceImpl implements BOIntervieweeWebService {
    @Inject
    IntervieweeService intervieweeService;

    @Override
    public BOSearchIntervieweeResponse search(BOSearchIntervieweeRequest request) {
        return intervieweeService.search(request);
    }

    @Override
    public BOGetIntervieweeResponse get(Long id) {
        GetIntervieweeResponse getIntervieweeResponse = intervieweeService.get(id);
        BOGetIntervieweeResponse response = new BOGetIntervieweeResponse();
        response.id = getIntervieweeResponse.id;
        response.password = getIntervieweeResponse.password;
        response.passwordSalt = getIntervieweeResponse.passwordSalt;
        response.status = getIntervieweeResponse.status;
        return response;
    }

    @Override
    public void update(Long id, BOUpdateIntervieweeRequest request) {
        intervieweeService.update(id, request);
    }

    @Override
    public BOGetResumeResponse getResume(Long id) {
        GetResumeResponse getResumeResponse = intervieweeService.getResume(id);
        BOGetResumeResponse response = new BOGetResumeResponse();
        response.firstName = getResumeResponse.firstName;
        response.lastName = getResumeResponse.lastName;
        response.dob = getResumeResponse.dob;
        response.jobPosition = getResumeResponse.jobPosition;
        response.intervieweeId = getResumeResponse.intervieweeId;
        response.resumeId = getResumeResponse.resumeId;
        response.mobilePhone = getResumeResponse.mobilePhone;
        response.jobExperiences = getResumeResponse.jobExperiences;
        response.projectExperiences = getResumeResponse.projectExperiences;
        return response;
    }
}
