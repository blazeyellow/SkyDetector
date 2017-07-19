package com.sky.detector;

import com.sky.detector.pojo.LogLine;
import org.apache.commons.lang.time.DateUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.Date;
import java.util.regex.Pattern;

public class HackerDetectorImpl implements HackerDetector {

    private static final Log log = LogFactory.getLog(HackerDetectorImpl.class);

    private HackerActivity hackerActivity = new HackerActivity();

    public static final String SIGNIN_FAILURE = "SIGNIN_FAILURE";

    public HackerDetectorImpl() {
        Thread cleaner = new Thread(new HackerActivityCleaner(hackerActivity));
        cleaner.start();

    }

    @Override
    public String parseLine(String line) {
        // 80.238.9.179,133612947,SIGNIN_SUCCESS,Dave.Branning

        log.info("Parsing line: " + line);

        String [] parts = line.split(",");

        if (isValid(parts) && parts[2].equalsIgnoreCase(SIGNIN_FAILURE)) {
            LogLine logLine = createLogLine(parts);

            if (isRecentActivity(logLine)) {
                trackActivity(logLine);

                if (hackerActivity.getActivityCount(logLine.getIpAddress())>=ACTIVITY_COUNT_TRIGGER) {
                    return logLine.getIpAddress().toString();

                }
            }
        }

        return "";

    }

    /**
     *
     * checks to see if activity is recent based on ACTIVITY_COUNT_TRIGGER value
     *
     * @param logLine
     * @return
     */
    private boolean isRecentActivity(LogLine logLine) {
        if (DateUtils.addMinutes(logLine.getEpochDate(), ACTIVITY_COUNT_TRIGGER).before(new Date())) {
            return false;

        }

        return true;

    }

    /**
     *
     * track failed logon activity
     *
     * @param untrackedLogLine
     * @return
     */
    private void trackActivity(LogLine untrackedLogLine) {
        hackerActivity.setIpActivity(untrackedLogLine);

    }

    private LogLine createLogLine(String [] parts) {
        LogLine logLine = new LogLine();
        logLine.setIpAddress(parts[0]);
        logLine.setEpochDate(parts[1]);
        return logLine;

    }

    /**
     *
     * checks to see if log line has appropriate constituents, tests to see if ip address and date are valid
     *
     * @param parts
     * @return
     */
    private boolean isValid(String [] parts) {

        if (parts.length != 4) {
            log.error("Log line is invalid");
            return false;

        }

        // validate ip address
        Pattern ipAddressPattern = Pattern.compile("(\\d{1,3})\\.(\\d{1,3})\\.(\\d{1,3})\\.(\\d{1,3})");
        if (!ipAddressPattern.matcher(parts[0]).matches()) {
            log.error("Log line ip address is invalid");
            return false;

        }

        // validate date
        Long epochDate = null;
        try {
            epochDate = Long.valueOf(parts[1]) * 1000;

        } catch (NumberFormatException e) {}

        if (epochDate==null) {
            log.error("Log line date is invalid");
            return false;

        }

        return true;

    }
}
