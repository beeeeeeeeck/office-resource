package app.interview.domain;

import core.framework.db.DBEnumValue;

/**
 * @author beckl
 */
public enum InterviewStatus {
    @DBEnumValue("UNASSIGNED")
    UNASSIGNED,
    @DBEnumValue("ASSIGNED")
    ASSIGNED,
    @DBEnumValue("PASSED")
    PASSED,
    @DBEnumValue("FAILED")
    FAILED,
    @DBEnumValue("CANCELLED")
    CANCELLED
}
