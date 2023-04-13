package com.example.myawesomepastebin.model;

import lombok.Getter;

import java.time.temporal.ChronoUnit;

@Getter
public enum ExpirationTime {
    TEN_MIN(10, ChronoUnit.MINUTES),
    ONE_HOUR(1, ChronoUnit.HOURS),
    THREE_HOUR(3, ChronoUnit.HOURS),
    ONE_DAY(1, ChronoUnit.DAYS),
    ONE_WEEK(7, ChronoUnit.DAYS),
    ONE_MONTH(30, ChronoUnit.DAYS),
    UNLIMITED(Integer.MAX_VALUE, ChronoUnit.FOREVER);

    private final Integer time;
    private final ChronoUnit chronoUnit;

    ExpirationTime(Integer time, ChronoUnit chronoUnit) {
        this.time = time;
        this.chronoUnit = chronoUnit;
    }
}
