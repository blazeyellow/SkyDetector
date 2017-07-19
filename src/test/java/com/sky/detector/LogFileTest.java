package com.sky.detector;

import junit.framework.TestCase;
import org.apache.commons.vfs2.FileObject;
import org.apache.commons.vfs2.FileSystemException;
import org.apache.commons.vfs2.FileSystemManager;
import org.apache.commons.vfs2.VFS;
import org.apache.commons.vfs2.impl.DefaultFileMonitor;
import sun.net.util.IPAddressUtil;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;

public class LogFileTest extends TestCase {

    public void ignoreTestStuff() {
        FileObject fileObject;
        DefaultFileMonitor fileMonitor;
        try {
            FileSystemManager systemManager = VFS.getManager();
            fileObject = systemManager.resolveFile("/Volumes/HDD2/Users/fuzzy/IdeaProjects/SkyDetector/src/main/resources/server_failures.log");
            System.out.println("Monitoring: " + fileObject.getName());
            fileMonitor = new DefaultFileMonitor(new LogFileListener(fileObject.getName().getPath()));

        } catch (FileSystemException e) {
            System.out.println("Error loading logfile");
            return;

        } catch (FileNotFoundException e) {
            System.out.println("Error setting up listener");
            return;

        }


        fileMonitor.setRecursive(true);
        fileMonitor.addFile(fileObject);
        fileMonitor.start();

        while (true) {}


    }

    public void testStuff2() throws Exception{
        try {
            byte[] b1 = {1, 2, 3};
            byte[] b2 = {1, 2, 3, 4, 5, 6, 7, 8};

            // create a new RandomAccessFile with filename test
            RandomAccessFile raf = new RandomAccessFile("/Volumes/HDD2/Users/fuzzy/IdeaProjects/SkyDetector/src/main/resources/server_failures.log", "rw");

            // write something in the file
            raf.writeUTF("Hello World");

            // set the file pointer at 0 position
            raf.seek(0);

            // read 2 bytes, starting from 1
            System.out.println("" + raf.read(b1, 1, 2));

            // set the file pointer at 0 position
            raf.seek(0);

            // read 3 bytes, starting from 4rth
            System.out.println("" + raf.read(b2, 4, 3));
        } catch (IOException ex) {
            ex.printStackTrace();
        }

    }

    public void testIpAddress() {
        byte[] ipAddress1 = IPAddressUtil.textToNumericFormatV4("1.1.1.1");
        byte[] ipAddress2 = IPAddressUtil.textToNumericFormatV4("001.001.001.001");

        System.out.println();
    }

    public void testIPAddress() {

        byte[] ipAddress2 = IPAddressUtil.textToNumericFormatV4("254.127.19.10");

        ByteBuffer bb = ByteBuffer.allocate(4);
        bb.put(ipAddress2[0]);
        bb.put(ipAddress2[1]);
        bb.put(ipAddress2[2]);
        bb.put(ipAddress2[3]);

        int intVal = bb.getInt(0);

        System.out.println(intVal);

//        System.out.println(bb2.);

        System.out.println((int)ipAddress2[0] & 0xff);
        System.out.println((int)ipAddress2[1] & 0xff);
        System.out.println((int)ipAddress2[2] & 0xff);
        System.out.println((int)ipAddress2[3] & 0xff);

    }
}
