package com.sky.detector;

import com.sky.detector.pojo.IPAddress;
import com.sky.detector.pojo.LogLine;
import org.apache.commons.lang.time.DateUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

public class HackerActivity {

    private static final Log log = LogFactory.getLog(HackerActivity.class);

    private Map<IPAddress, Set<LogLine>> activity = new HashMap<IPAddress, Set<LogLine>>();

    public synchronized void setIpActivity(LogLine logLine) {
        Set logLines = activity.get(logLine.getIpAddress());

        if (logLines==null) {
            logLines = new TreeSet();

        }

        logLines.add(logLine);
        activity.put(logLine.getIpAddress(), logLines);

    }

    public synchronized Integer getActivityCount(IPAddress ipAddress) {
        if (activity.get(ipAddress)==null) {
            return 0;

        }

        return activity.get(ipAddress).size();

    }

    /**
     *
     * remove old entries
     *
     */
    public synchronized void purgeOldEntries() {
        Iterator it = activity.values().iterator();
        while (it.hasNext()) {
            Set logLines = (TreeSet)it.next();

            Iterator it2 = logLines.iterator();
            int count = 0;
            while (it2.hasNext()) {
                count++;
                LogLine logLine = (LogLine)it2.next();
                if (DateUtils.addMinutes(logLine.getEpochDate(), HackerDetector.ACTIVITY_COUNT_TRIGGER).before(new Date())) {
                    // remove entry
                    it2.remove();

                }
            }

            log.info(count + " entries in activity map");
            // remove ip entry if no activity
            Iterator it3 = logLines.iterator();
            if (!it3.hasNext()) {
                it.remove();

            }
        }
    }
}
