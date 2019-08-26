package com.meizu.util;

public class IpNumberConverter {
    
    public long toLong(String ip) {
        long ipNum = 0;
        String[] segments = ip.split("\\.");
        for (int i = 3; i >= 0; i--) {
            long seg = Long.parseLong(segments[i]);
            ipNum |= seg << (8 * (3 - i));
        }
        return ipNum;
    }
    
    public String toString(long l) {
        return new StringBuilder().append(l >>> 24 & 0xff).append(".")
                           .append(l >>> 16 & 0Xff).append(".")
                           .append(l >>> 8 & 0Xff).append(".")
                           .append(l & 0xff).toString();
    }
    public static void main(String[] args) {
        IpNumberConverter convert = new IpNumberConverter();
        long l = convert.toLong("255.255.255.254");
        System.out.println(l);
        System.out.println(convert.toString(l));
    }
}
