package com.softdream.adbdebug;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

/**
 * Created by zewei on 2015-10-19.
 */
public class UDP {
    public static void sendIp() throws IOException {
        DatagramSocket socket = new DatagramSocket();
        String name = android.os.Build.BRAND+android.os.Build.MODEL;
        byte data[] = name.getBytes();
        InetAddress address = InetAddress.getByName("192.168.0.126");
        DatagramPacket packet = new DatagramPacket(data,data.length,address,2015);
        socket.send(packet);
    }
}
