package com.sky.detector;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class HackerActivityCleaner implements Runnable {

    private static final Log log = LogFactory.getLog(HackerActivityCleaner.class);

    private HackerActivity activity;

    public static final Integer CLEANUP_INTERVAL = 10000;

    public HackerActivityCleaner(HackerActivity activity) {
        this.activity = activity;

    }

    private boolean stop = false;

    @Override
    public void run() {
        try {
            while (true) {
                log.info("Sleeping " + CLEANUP_INTERVAL + "ms...zzz");
                Thread.sleep(CLEANUP_INTERVAL);
                activity.purgeOldEntries();

                if (stop) {
                    break;

                }
            }

        } catch (InterruptedException e) {
            e.printStackTrace();

        }
    }

    /**
     *
     * little switch to allow running of thread once
     *
     * @param stop
     */
    public void setStop(boolean stop) {
        this.stop = stop;
    }
}
