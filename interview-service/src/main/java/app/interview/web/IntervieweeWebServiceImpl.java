package app.interview.web;

import app.api.IntervieweeWebService;
import app.api.interviewee.CreateIntervieweeRequest;
import app.api.interviewee.CreateIntervieweeResponse;
import app.api.interviewee.GetIntervieweeResponse;
import app.api.interviewee.GetResumeResponse;
import app.api.interviewee.IntervieweeLoginRequest;
import app.api.interviewee.IntervieweeLoginResponse;
import app.api.interviewee.SaveResumeRequest;
import app.api.interviewee.SaveResumeResponse;
import app.interview.service.IntervieweeService;
import core.framework.inject.Inject;

/**
 * @author beckl
 */
public class IntervieweeWebServiceImpl implements IntervieweeWebService {
    @Inject
    IntervieweeService intervieweeService;

    @Override
    public CreateIntervieweeResponse create(CreateIntervieweeRequest request) {
        return intervieweeService.register(request);
    }

    @Override
    public GetIntervieweeResponse get(Long id) {
        return intervieweeService.get(id);
    }

    @Override
    public SaveResumeResponse saveResume(Long id, SaveResumeRequest request) {
        return intervieweeService.saveResume(id, request);
    }

    @Override
    public GetResumeResponse getResume(Long id) {
        return intervieweeService.getResume(id);
    }

    @Override
    public IntervieweeLoginResponse login(IntervieweeLoginRequest request) {
        return intervieweeService.login(request);
    }
}
