package app.bo.api.interview;

import core.framework.api.json.Property;

/**
 * @author beckl
 */
public enum InterviewStatusAJAXView {
    @Property(name = "UNASSIGNED")
    UNASSIGNED,
    @Property(name = "ASSIGNED")
    ASSIGNED,
    @Property(name = "PASSED")
    PASSED,
    @Property(name = "FAILED")
    FAILED,
    @Property(name = "CANCELLED")
    CANCELLED
}