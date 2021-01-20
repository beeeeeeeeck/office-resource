package app.staff.service;

import app.api.staff.BOCreateStaffRequest;
import app.api.staff.BOCreateStaffResponse;
import app.api.staff.BOUpdateStaffRequest;
import app.api.staff.CreateJobSummaryRequest;
import app.api.staff.CreateJobSummaryResponse;
import app.api.staff.GetStaffResponse;
import app.api.staff.SearchStaffRequest;
import app.api.staff.SearchStaffResponse;
import app.api.staff.StaffLoginRequest;
import app.api.staff.StaffLoginResponse;
import app.api.staff.UpdateJobSummaryRequest;
import app.common.util.PasswordUtils;
import app.staff.domain.JobSummary;
import app.staff.domain.Staff;
import com.mongodb.ReadPreference;
import com.mongodb.client.model.Filters;
import core.framework.db.Query;
import core.framework.db.Repository;
import core.framework.inject.Inject;
import core.framework.mongo.MongoCollection;
import core.framework.util.Strings;
import core.framework.web.exception.BadRequestException;
import core.framework.web.exception.ConflictException;
import core.framework.web.exception.NotFoundException;
import org.bson.conversions.Bson;
import org.bson.types.ObjectId;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @author beckl
 */
public class StaffService {
    @Inject
    Repository<Staff> staffRepository;
    @Inject
    MongoCollection<JobSummary> jobSummaryMongoCollection;

    public BOCreateStaffResponse create(BOCreateStaffRequest request) {
        // Make sure email is unique for every staff
        staffRepository.selectOne("email = ?", request.email).ifPresent(staff -> {
            throw new ConflictException("Duplicated email requested");
        });
        Staff staff = new Staff();
        staff.email = request.email;
        if (Strings.isBlank(request.passwordSalt)) {
            staff.passwordSalt = PasswordUtils.getSalt();
            staff.password = PasswordUtils.generateSecurePassword(request.password, staff.passwordSalt);
        } else {
            staff.passwordSalt = request.passwordSalt;
            staff.password = request.password;
        }
        staff.firstName = request.firstName;
        staff.lastName = request.lastName;
        staff.dob = request.dob;
        staff.active = Boolean.TRUE; // staff is active by default
        staff.startedDate = request.startedDate;
        staff.createdTime = LocalDateTime.now();
        staff.updatedTime = LocalDateTime.now();
        BOCreateStaffResponse response = new BOCreateStaffResponse();
        response.id = staffRepository.insert(staff).orElseThrow();
        response.active = staff.active;
        return response;
    }

    public SearchStaffResponse search(SearchStaffRequest request) {
        Query<Staff> query = staffRepository.select();
        query.skip(request.skip);
        query.limit(request.limit);
        if (!Strings.isBlank(request.email)) {
            query.where("email LIKE ?", request.email + "%");
        }
        if (!Strings.isBlank(request.firstName)) {
            query.where("first_name LIKE ?", request.firstName + "%");
        }
        if (!Strings.isBlank(request.lastName)) {
            query.where("last_name LIKE ?", request.lastName + "%");
        }
        if (request.isActive != null) {
            query.where("active = ?", request.isActive ? 1 : 0);
        }
        SearchStaffResponse result = new SearchStaffResponse();
        result.staffs = query.fetch().stream().map(this::recordToView).collect(Collectors.toList());
        result.total = query.count();
        return result;
    }

    private SearchStaffResponse.StaffView recordToView(Staff staff) {
        var view = new SearchStaffResponse.StaffView();
        view.id = staff.id;
        view.email = staff.email;
        view.firstName = staff.firstName;
        view.lastName = staff.lastName;
        view.dob = staff.dob;
        view.active = staff.active;
        view.startedDate = staff.startedDate;
        return view;
    }

    public void update(Long id, BOUpdateStaffRequest request) {
        Staff staff = getStaff(id);
        staff.active = request.active;
        staff.updatedTime = LocalDateTime.now();
        staffRepository.update(staff);
    }

    private Staff getStaff(Long id) {
        return staffRepository.get(id).orElseThrow(() -> new NotFoundException("Staff not found by id = " + id));
    }

    public GetStaffResponse get(Long id) {
        Staff staff = getStaff(id);
        GetStaffResponse response = new GetStaffResponse();
        response.id = staff.id;
        response.email = staff.email;
        response.firstName = staff.firstName;
        response.lastName = staff.lastName;
        response.dob = staff.dob;
        response.startedDate = staff.startedDate;
        core.framework.mongo.Query jobSummaryFilter = new core.framework.mongo.Query();
        jobSummaryFilter.filter = Filters.eq("staff_id", id);
        jobSummaryFilter.readPreference = ReadPreference.secondary();
        List<JobSummary> jobSummaryList = jobSummaryMongoCollection.find(jobSummaryFilter);
        Optional.ofNullable(jobSummaryList).ifPresent(jobSummaries -> response.jobSummaries = jobSummaries.stream().map(summary -> {
            var jobSummary = new GetStaffResponse.JobSummaryView();
            jobSummary.id = summary.id.toString();
            jobSummary.title = summary.title;
            jobSummary.summary = summary.summary;
            return jobSummary;
        }).collect(Collectors.toList()));
        return response;
    }

    public CreateJobSummaryResponse createJobSummary(Long id, CreateJobSummaryRequest request) {
        Staff staff = getStaff(id);
        Bson filter = Filters.and(Filters.eq("staff_id", staff.id), Filters.eq("title", request.title));
        long count = jobSummaryMongoCollection.count(filter);
        if (count > 0) {
            throw new BadRequestException("Duplicated job summary with same title existed");
        }
        JobSummary newJobSummary = new JobSummary();
        newJobSummary.staffId = staff.id;
        newJobSummary.title = request.title;
        newJobSummary.summary = request.summary;
        newJobSummary.createdTime = LocalDateTime.now();
        newJobSummary.updatedTime = LocalDateTime.now();
        jobSummaryMongoCollection.insert(newJobSummary);
        JobSummary createdJobSummary = jobSummaryMongoCollection.findOne(filter).orElseThrow();
        CreateJobSummaryResponse response = new CreateJobSummaryResponse();
        response.jobSummaryId = createdJobSummary.id.toString();
        return response;
    }

    public void updateJobSummary(Long id, String jobSummaryId, UpdateJobSummaryRequest request) {
        Staff staff = getStaff(id);
        if (!Strings.isBlank(request.title)) {
            Bson filter = Filters.and(Filters.eq("staff_id", staff.id), Filters.eq("title", request.title));
            long count = jobSummaryMongoCollection.count(filter);
            if (count > 0) {
                throw new BadRequestException("Duplicated job summary with same title existed");
            }
        }
        Bson filter = Filters.and(Filters.eq("staff_id", staff.id), Filters.eq("_id", new ObjectId(jobSummaryId)));
        JobSummary jobSummary = jobSummaryMongoCollection.findOne(filter).orElseThrow(() -> new NotFoundException("Staff job summary not found by id = " + jobSummaryId));
        Optional.ofNullable(request.title).ifPresent(requestedTitle -> jobSummary.title = requestedTitle);
        Optional.ofNullable(request.summary).ifPresent(requestedSummary -> jobSummary.summary = requestedSummary);
        jobSummary.updatedTime = LocalDateTime.now();
        jobSummaryMongoCollection.replace(jobSummary);
    }

    public StaffLoginResponse login(StaffLoginRequest request) {
        return staffRepository.selectOne("email = ?", request.email).map(staff -> {
            StaffLoginResponse response = new StaffLoginResponse();
            if (!staff.active) {
                response.success = Boolean.FALSE;
                response.errorMessage = "Staff is inactive";
                return response;
            }
            if (!PasswordUtils.verifyPassword(request.password, staff.password, staff.passwordSalt)) {
                response.success = Boolean.FALSE;
                response.errorMessage = "Incorrect password";
                return response;
            }
            response.success = Boolean.TRUE;
            response.staffId = staff.id;
            return response;
        }).orElseGet(() -> {
            StaffLoginResponse response = new StaffLoginResponse();
            response.success = Boolean.FALSE;
            response.errorMessage = "Incorrect email";
            return response;
        });
    }
}
