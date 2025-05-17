package ru.master.service.util;

import inet.ipaddr.IPAddressString;

public class IpUtil {
    public static boolean isIpInCidr(String ip, String cidr) {
        try {
            IPAddressString ipAddressStr = new IPAddressString(ip);
            IPAddressString networkStr = new IPAddressString(cidr);

            return networkStr.getAddress().contains(ipAddressStr.getAddress());
        } catch (Exception e) {
            return false;
        }
    }
}

