package app.staff;

import core.framework.module.App;
import core.framework.module.SystemModule;

/**
 * @author beckl
 */
public class StaffServiceApp extends App {
    @Override
    protected void initialize() {
        load(new SystemModule("sys.properties"));

        // biz modules loading
        load(new StaffModule());
    }
}
