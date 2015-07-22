package org.spacehq.mc.classic.protocol.packet.server;

import org.spacehq.packetlib.io.NetInput;
import org.spacehq.packetlib.io.NetOutput;
import org.spacehq.packetlib.packet.Packet;

import java.io.IOException;

/**
 * Sent by a server to finalize received level data.
 */
public class ServerLevelFinalizePacket implements Packet {
    private int width;
    private int height;
    private int depth;

    @SuppressWarnings("unused")
    private ServerLevelFinalizePacket() {
    }

    /**
     * Creates a new ServerLevelFinalizePacket instance.
     *
     * @param width  Width of the level.
     * @param height Height of the level.
     * @param depth  Depth of the level.
     */
    public ServerLevelFinalizePacket(int width, int height, int depth) {
        this.width = width;
        this.height = height;
        this.depth = depth;
    }

    /**
     * Gets the width of the level.
     *
     * @return The level's width.
     */
    public int getWidth() {
        return this.width;
    }

    /**
     * Gets the height of the level.
     *
     * @return The level's height.
     */
    public int getHeight() {
        return this.height;
    }

    /**
     * Gets the depth of the level.
     *
     * @return The level's depth.
     */
    public int getDepth() {
        return this.depth;
    }

    @Override
    public void read(NetInput in) throws IOException {
        this.width = in.readShort();
        this.height = in.readShort();
        this.depth = in.readShort();
    }

    @Override
    public void write(NetOutput out) throws IOException {
        out.writeShort(this.width);
        out.writeShort(this.height);
        out.writeShort(this.depth);
    }

    @Override
    public boolean isPriority() {
        return false;
    }
}
