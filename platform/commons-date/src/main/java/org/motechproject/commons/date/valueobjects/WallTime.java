package org.motechproject.commons.date.valueobjects;

import org.joda.time.Period;
import org.motechproject.commons.date.util.JodaFormatter;

import java.io.Serializable;

public class WallTime implements Serializable {
    private static final long serialVersionUID = -8883948916682885845L;
    private Period period;

    public WallTime(String userReadableForm) {
        period = new JodaFormatter().parsePeriod(userReadableForm);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        return period.equals(((WallTime) o).asPeriod());
    }

    @Override
    public int hashCode() {
        return period.hashCode();
    }

    public int inDays() {
        return period.toStandardDays().getDays();
    }

    public int getHours() {
        return period.getHours();
    }

    public int getMinutes() {
        return period.getMinutes();
    }

    public int inMinutes() {
        return period.toStandardMinutes().getMinutes();
    }

    public long inMillis() {
        final int millisInSecond = 1000;
        return period.toStandardSeconds().getSeconds() * millisInSecond;
    }

    public Period asPeriod() {
        return period;
    }

    public boolean isLessThanADay() {
        return period.toStandardDays().isLessThan(new Period(0, 0, 0, 1, 0, 0, 0, 0).toStandardDays());
    }
}
