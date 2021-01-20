package app.staff;

import org.junit.jupiter.api.Test;

import static core.framework.test.Assertions.assertConfDirectory;


/**
 * @author beckl
 */
class ConfigTest extends StaffIntegrationTest {
    @Test
    void conf() {
        assertConfDirectory().overridesDefaultResources();
    }
}
