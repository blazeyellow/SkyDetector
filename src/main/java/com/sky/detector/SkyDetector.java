package com.sky.detector;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.vfs2.FileObject;
import org.apache.commons.vfs2.FileSystemException;
import org.apache.commons.vfs2.FileSystemManager;
import org.apache.commons.vfs2.VFS;
import org.apache.commons.vfs2.impl.DefaultFileMonitor;

import java.io.FileNotFoundException;

public class SkyDetector {

    private static final Log log = LogFactory.getLog(SkyDetector.class);

    public static void main(String[] args) {

        System.out.println(LogFactory.FACTORY_PROPERTIES);
        String logFile = "/Volumes/HDD2/Users/fuzzy/IdeaProjects/SkyDetector2/src/main/resources/server_failures.log";
        if (args!=null && args.length>0) {
            logFile = args[0];

        }

        FileObject fileObject;
        DefaultFileMonitor fileMonitor;
        try {
            FileSystemManager systemManager = VFS.getManager();
            fileObject = systemManager.resolveFile(logFile);
            log.info("Monitoring: " + fileObject.getName());
            fileMonitor = new DefaultFileMonitor(new LogFileListener(fileObject.getName().getPath()));

        } catch (FileSystemException e) {
            log.error("Error loading logfile");
            return;

        } catch (FileNotFoundException e) {
            log.error("Error setting up listener");
            return;

        }


        fileMonitor.setRecursive(true);
        fileMonitor.addFile(fileObject);
        fileMonitor.start();

        while (true) {}

    }
}
