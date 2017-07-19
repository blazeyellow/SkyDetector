package com.sky.detector.pojo;

import java.nio.ByteBuffer;

public class IPAddress {

    private byte[] ipAddress;

    public IPAddress(byte[] ipAddress) {
        this.ipAddress = ipAddress;
    }

    public byte[] getIpAddress() {
        return ipAddress;
    }

    @Override
    public int hashCode() {
        ByteBuffer bb = ByteBuffer.allocate(4);
        bb.put(ipAddress[0]);
        bb.put(ipAddress[1]);
        bb.put(ipAddress[2]);
        bb.put(ipAddress[3]);

        return bb.getInt(0);

    }

    @Override
    public boolean equals(Object o) {
        return (hashCode() == o.hashCode());
    }

    @Override
    public String toString() {
        return new StringBuffer()
                .append((int)ipAddress[0] & 0xff)
                .append(".")
                .append((int)ipAddress[1] & 0xff)
                .append(".")
                .append((int)ipAddress[2] & 0xff)
                .append(".")
                .append((int)ipAddress[3] & 0xff).toString();

    }
}
