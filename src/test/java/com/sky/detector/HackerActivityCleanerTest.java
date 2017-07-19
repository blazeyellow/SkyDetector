package com.sky.detector;

import com.sky.detector.pojo.IPAddress;
import com.sky.detector.pojo.LogLine;
import junit.framework.TestCase;
import sun.net.util.IPAddressUtil;

import java.util.Date;

public class HackerActivityCleanerTest extends TestCase {
    public void testRun() throws Exception {
        HackerActivity hackerActivity = new HackerActivity();

        LogLine logLine_old = new LogLine();
        logLine_old.setIpAddress("100.100.100.100");
        logLine_old.setEpochDate("1319818394");
        hackerActivity.setIpActivity(logLine_old);

        LogLine logLine_new = new LogLine();
        logLine_new.setIpAddress("100.100.100.200");
        String newEpochDate = String.valueOf(new Date().getTime() / 1000);
        logLine_new.setEpochDate(newEpochDate);
        hackerActivity.setIpActivity(logLine_new);

        HackerActivityCleaner cleaner = new HackerActivityCleaner(hackerActivity);
        cleaner.setStop(true);
        cleaner.run();

        assertTrue(hackerActivity.getActivityCount(new IPAddress(IPAddressUtil.textToNumericFormatV4("100.100.100.100")))==0);

    }
}
