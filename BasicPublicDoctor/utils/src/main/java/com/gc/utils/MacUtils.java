package com.gc.utils;

import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;

/**
 * Created by 87914 on 2017/3/30.
 */

public class MacUtils {
    public byte[] getMacAddress() {
        Enumeration<NetworkInterface> interfaces = null;
        try {
            interfaces = NetworkInterface.getNetworkInterfaces();
        } catch (SocketException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }
        while (interfaces.hasMoreElements()) {
            final NetworkInterface ni = interfaces.nextElement();
            try {
                if (ni.isLoopback() || ni.isPointToPoint() || ni.isVirtual())
                    continue;
            } catch (SocketException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            byte[] macAddress = null;
            try {
                macAddress = ni.getHardwareAddress();
            } catch (SocketException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            if (macAddress != null && macAddress.length > 0)
                return macAddress;
        }
        return null;
    }
    public String bytes2HexString(byte[] b) {
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < b.length; i++) {
            String hex = Integer.toHexString(b[ i ] & 0xFF);
            if (hex.length() == 1) {
                hex = '0' + hex;
            }
            sb.append(hex.toUpperCase());
        }
        return sb.toString();
    }


    public String getMacAddressString(){
        return bytes2HexString(getMacAddress());
    }

}
