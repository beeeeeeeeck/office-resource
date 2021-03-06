package app.bo.api.staff;

import core.framework.api.json.Property;
import core.framework.api.validate.NotBlank;
import core.framework.api.validate.NotNull;

import java.time.LocalDate;
import java.util.List;

/**
 * @author beckl
 */
public class SearchStaffAJAXResponse {
    @NotNull
    @Property(name = "total")
    public Long total;

    @Property(name = "staffs")
    public List<StaffAJAXView> staffs;

    public static class StaffAJAXView {
        @NotNull
        @Property(name = "id")
        public Long id;

        @NotNull
        @NotBlank
        @Property(name = "email")
        public String email;

        @NotNull
        @NotBlank
        @Property(name = "first_name")
        public String firstName;

        @NotNull
        @NotBlank
        @Property(name = "last_name")
        public String lastName;

        @NotNull
        @Property(name = "dob")
        public LocalDate dob;

        @NotNull
        @Property(name = "active")
        public Boolean active;

        @NotNull
        @Property(name = "started_date")
        public LocalDate startedDate;
    }
}
