package org.spacehq.mc.classic.protocol.packet.server;

import org.spacehq.mc.classic.protocol.packet.ClassicPacketUtil;
import org.spacehq.packetlib.io.NetInput;
import org.spacehq.packetlib.io.NetOutput;
import org.spacehq.packetlib.packet.Packet;

import java.io.IOException;

/**
 * Sent by a server to provide level data to the client.
 */
public class ServerLevelDataPacket implements Packet {
    private byte data[];
    private int percent;

    @SuppressWarnings("unused")
    private ServerLevelDataPacket() {
    }

    /**
     * Creates a new ServerLevelDataPacket instance.
     *
     * @param data    Level data to send.
     * @param percent Percent of level data sent so far.
     */
    public ServerLevelDataPacket(byte data[], int percent) {
        this.data = data;
        this.percent = percent;
    }

    /**
     * Gets the level data being sent.
     *
     * @return The level data being sent.
     */
    public byte[] getData() {
        return this.data;
    }

    /**
     * Gets the percent of level data sent so far.
     *
     * @return The percent of level data sent so far.
     */
    public int getPercent() {
        return this.percent;
    }

    @Override
    public void read(NetInput in) throws IOException {
        this.data = ClassicPacketUtil.readBytes(in);
        this.percent = in.readUnsignedByte();
    }

    @Override
    public void write(NetOutput out) throws IOException {
        ClassicPacketUtil.writeBytes(out, this.data);
        out.writeByte(this.percent);
    }

    @Override
    public boolean isPriority() {
        return false;
    }
}
