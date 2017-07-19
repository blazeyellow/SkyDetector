package com.sky.detector.pojo;

import sun.net.util.IPAddressUtil;

import java.util.Date;

public class LogLine implements Comparable<LogLine>{

    private IPAddress ipAddress;
    private Date epochDate;
    private String name;

    public IPAddress getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(String ipAddressStr) {
        byte[] ipAdd = IPAddressUtil.textToNumericFormatV4(ipAddressStr);
        ipAddress = new IPAddress(ipAdd);

    }

    public Date getEpochDate() {
        return epochDate;
    }

    public void setEpochDate(String epochDate) {
        this.epochDate = new Date(Long.valueOf(epochDate) * 1000);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public int compareTo(LogLine logLine) {
        if (this.epochDate.after(logLine.getEpochDate())) {
            return -1;

        } else {
            return 1;

        }
    }
}
