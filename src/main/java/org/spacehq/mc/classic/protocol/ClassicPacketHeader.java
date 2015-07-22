package org.spacehq.mc.classic.protocol;

import org.spacehq.packetlib.io.NetInput;
import org.spacehq.packetlib.io.NetOutput;
import org.spacehq.packetlib.packet.PacketHeader;

import java.io.IOException;

/**
 * Packet header used by Minecraft Classic packets.
 */
public class ClassicPacketHeader implements PacketHeader {
    private static final int LENGTHS[] = new int[] { 130, 0, 0, 1027, 6, 8, 7, 73, 9, 6, 4, 3, 1, 65, 65, 1 };

    @Override
    public boolean isLengthVariable() {
        return false;
    }

    @Override
    public int getLengthSize() {
        return 0;
    }

    @Override
    public int getLengthSize(int length) {
        return 0;
    }

    @Override
    public int readLength(NetInput in, int available) throws IOException {
        return available;
    }

    @Override
    public void writeLength(NetOutput out, int length) throws IOException {
    }

    @Override
    public int readPacketId(NetInput in) throws IOException {
        int id = in.readUnsignedByte();
        if(id >= 0 && id < LENGTHS.length && LENGTHS[id] > in.available()) {
            return -1;
        }

        return id;
    }

    @Override
    public void writePacketId(NetOutput out, int packetId) throws IOException {
        out.writeByte(packetId);
    }
}
