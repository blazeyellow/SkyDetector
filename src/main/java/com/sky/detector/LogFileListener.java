package com.sky.detector;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.vfs2.FileChangeEvent;
import org.apache.commons.vfs2.FileListener;

import java.io.FileNotFoundException;
import java.io.RandomAccessFile;

public class LogFileListener implements FileListener {

    private static final Log log = LogFactory.getLog(LogFileListener.class);

    private long filePointer = 0;
    private String filename;
    private HackerDetector detector;
    private String oldMessage = "";

    public LogFileListener(String filename) throws FileNotFoundException {
        detector = new HackerDetectorImpl();
        this.filename = filename;

    }

    @Override
    public void fileCreated(FileChangeEvent fileChangeEvent) throws Exception {
        filePointer = 0;

    }

    @Override
    public void fileDeleted(FileChangeEvent fileChangeEvent) throws Exception {
        filePointer = 0;

    }

    @Override
    public void fileChanged(FileChangeEvent fileChangeEvent) throws Exception {

        // annoyingly we have to create a new instance each time as the is no auto detect
        // of external file changes
        RandomAccessFile logFile = new RandomAccessFile(filename, "r");

        logFile.seek(filePointer);

        String line;
        while ((line = logFile.readLine()) != null && !line.isEmpty()) {
//            System.out.println("---" + line);

            String message = detector.parseLine(line);
            if (message!=null && !message.isEmpty() && !message.equals(oldMessage)) {
                oldMessage = message;
                System.out.println("ALERT:" + message);

            }
        }

        filePointer = (int)logFile.length();

        log.info("Log file changed");

        logFile.close();

    }
}
