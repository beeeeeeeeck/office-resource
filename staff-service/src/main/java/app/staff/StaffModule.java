package app.staff;

import app.api.BOStaffWebService;
import app.api.StaffWebService;
import app.staff.domain.JobSummary;
import app.staff.domain.Staff;
import app.staff.service.StaffService;
import app.staff.web.BOStaffWebServiceImpl;
import app.staff.web.StaffWebServiceImpl;
import core.framework.module.Module;
import core.framework.mongo.module.MongoConfig;

/**
 * @author beckl
 */
public class StaffModule extends Module {
    @Override
    protected void initialize() {
        db().repository(Staff.class);

        configureMongoDB();

        bind(StaffService.class);

        api().service(StaffWebService.class, bind(StaffWebServiceImpl.class));
        api().service(BOStaffWebService.class, bind(BOStaffWebServiceImpl.class));
    }

    private void configureMongoDB() {
        MongoConfig mongoConfig = config(MongoConfig.class);
        mongoConfig.uri(requiredProperty("sys.mongo.uri"));
        mongoConfig.collection(JobSummary.class);
    }
}
