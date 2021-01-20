package app.staff;

import core.framework.test.module.AbstractTestModule;

/**
 * @author beckl
 */
public class TestModule extends AbstractTestModule {
    @Override
    protected void initialize() {
        load(new StaffServiceApp());
        initDB().createSchema();
    }
}
