package com.sky.detector;

public interface HackerDetector {

    public static final Integer ACTIVITY_COUNT_TRIGGER = 5;

    public String parseLine(String line);

}
