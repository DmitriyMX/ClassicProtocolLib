package com.github.steveice10.mc.classic.protocol.packet.server;

import com.github.steveice10.packetlib.io.NetInput;
import com.github.steveice10.packetlib.io.NetOutput;
import com.github.steveice10.packetlib.packet.Packet;

import java.io.IOException;

/**
 * Sent by a server to set a block.
 */
public class ServerSetBlockPacket implements Packet {
    private int x;
    private int y;
    private int z;
    private int block;

    @SuppressWarnings("unused")
    private ServerSetBlockPacket() {
    }

    /**
     * Creates a new ServerSetBlockPacket instance.
     *
     * @param x     X of the block to set.
     * @param y     Y of the block to set.
     * @param z     Z of the block to set.
     * @param block Block ID to set.
     */
    public ServerSetBlockPacket(int x, int y, int z, int block) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.block = block;
    }

    /**
     * Gets the X of the block to set.
     *
     * @return The block's X.
     */
    public int getX() {
        return this.x;
    }

    /**
     * Gets the Y of the block to set.
     *
     * @return The block's Y.
     */
    public int getY() {
        return this.y;
    }

    /**
     * Gets the Z of the block to set.
     *
     * @return The block's Z.
     */
    public int getZ() {
        return this.z;
    }

    /**
     * Gets the block ID to set.
     *
     * @return The block ID to set.
     */
    public int getBlock() {
        return this.block;
    }

    @Override
    public void read(NetInput in) throws IOException {
        this.x = in.readShort();
        this.y = in.readShort();
        this.z = in.readShort();
        this.block = in.readUnsignedByte();
    }

    @Override
    public void write(NetOutput out) throws IOException {
        out.writeShort(this.x);
        out.writeShort(this.y);
        out.writeShort(this.z);
        out.writeByte(this.block);
    }

    @Override
    public boolean isPriority() {
        return false;
    }
}
