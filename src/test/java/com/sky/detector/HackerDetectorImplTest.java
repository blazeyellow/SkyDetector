package com.sky.detector;

import junit.framework.TestCase;

import java.util.Date;

public class HackerDetectorImplTest extends TestCase {
    public void testParseLine_no_unusual_activity() throws Exception {
        HackerDetector detector = new HackerDetectorImpl();

        String now = String.valueOf(new Date().getTime()/1000);
        assertTrue(detector.parseLine("80.238.9.179,"+now+",SIGNIN_FAILURE,Dave.Branning").isEmpty());

    }

    public void testParseLine_unusual_activity() throws Exception {
        HackerDetector detector = new HackerDetectorImpl();

        String now = String.valueOf(new Date().getTime()/1000);
        detector.parseLine("90.238.9.179,"+now+",SIGNIN_FAILURE,Dave.Branning");
        detector.parseLine("90.238.9.179,"+now+",SIGNIN_FAILURE,Dave.Branning");
        detector.parseLine("90.238.9.179,"+now+",SIGNIN_FAILURE,Dave.Branning");
        detector.parseLine("90.238.9.179,"+now+",SIGNIN_FAILURE,Dave.Branning");
        assertTrue(!detector.parseLine("90.238.9.179,"+now+",SIGNIN_FAILURE,Dave.Branning").isEmpty());

    }
}
