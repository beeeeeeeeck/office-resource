package app.staff;

import core.framework.test.Context;
import core.framework.test.IntegrationExtension;
import org.junit.jupiter.api.extension.ExtendWith;

/**
 * @author beckl
 */
@ExtendWith(IntegrationExtension.class)
@Context(module = TestModule.class)
public class StaffIntegrationTest {
    protected StaffIntegrationTest() {
    }
}
