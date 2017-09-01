package com.github.steveice10.mc.classic.protocol;

import com.github.steveice10.mc.classic.protocol.packet.client.ClientIdentificationPacket;
import com.github.steveice10.mc.classic.protocol.packet.server.ServerDisconnectPacket;
import com.github.steveice10.packetlib.event.session.DisconnectingEvent;
import com.github.steveice10.packetlib.event.session.PacketReceivedEvent;
import com.github.steveice10.packetlib.event.session.SessionAdapter;

import java.math.BigInteger;
import java.security.MessageDigest;

/**
 * Listener used on Minecraft Classic server sessions.
 */
public class ServerListener extends SessionAdapter {
    @Override
    public void packetReceived(PacketReceivedEvent event) {
        if(event.getPacket() instanceof ClientIdentificationPacket) {
            ClientIdentificationPacket packet = event.getPacket();
            event.getSession().setFlag(ClassicConstants.USERNAME_KEY, packet.getUsername());
            if(event.getSession().hasFlag(ClassicConstants.SALT_KEY) && !packet.getVerificationKey().equals(md5(event.getSession().getFlag(ClassicConstants.SALT_KEY) + packet.getUsername()))) {
                event.getSession().disconnect("Failed to verify username.");
            }
        }
    }

    @Override
    public void disconnecting(DisconnectingEvent event) {
        event.getSession().send(new ServerDisconnectPacket(event.getReason()));
    }

    private static String md5(String str) {
        try {
            MessageDigest digest = MessageDigest.getInstance("MD5");
            return new BigInteger(1, digest.digest(str.getBytes("UTF-8"))).toString(16);
        } catch(Exception e) {
            System.err.println("Failed to use MD5 to check verification key.");
            e.printStackTrace();
            return "";
        }
    }
}