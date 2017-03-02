package com.github.steveice10.mc.classic.protocol.packet.server;

import com.github.steveice10.mc.classic.protocol.packet.ClassicPacketUtil;
import com.github.steveice10.packetlib.io.NetInput;
import com.github.steveice10.packetlib.io.NetOutput;
import com.github.steveice10.packetlib.packet.Packet;

import java.io.IOException;

/**
 * Sent by a server to disconnect a client.
 */
public class ServerDisconnectPacket implements Packet {
    private String reason;

    @SuppressWarnings("unused")
    private ServerDisconnectPacket() {
    }

    /**
     * Creates a new ServerDisconnectPacket instance.
     *
     * @param reason Reason for disconnecting.
     */
    public ServerDisconnectPacket(String reason) {
        this.reason = reason;
    }

    /**
     * Gets the reason for disconnecting.
     *
     * @return The reason for disconnecting.
     */
    public String getReason() {
        return this.reason;
    }

    @Override
    public void read(NetInput in) throws IOException {
        this.reason = ClassicPacketUtil.readString(in);
    }

    @Override
    public void write(NetOutput out) throws IOException {
        ClassicPacketUtil.writeString(out, this.reason);
    }

    @Override
    public boolean isPriority() {
        return true;
    }
}
