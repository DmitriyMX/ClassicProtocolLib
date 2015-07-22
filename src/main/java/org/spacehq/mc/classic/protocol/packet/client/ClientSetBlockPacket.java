package org.spacehq.mc.classic.protocol.packet.client;

import org.spacehq.mc.classic.protocol.data.game.SetBlockMode;
import org.spacehq.packetlib.io.NetInput;
import org.spacehq.packetlib.io.NetOutput;
import org.spacehq.packetlib.packet.Packet;

import java.io.IOException;

/**
 * Sent by a client to set a block.
 */
public class ClientSetBlockPacket implements Packet {
    private int x;
    private int y;
    private int z;
    private SetBlockMode mode;
    private int block;

    @SuppressWarnings("unused")
    private ClientSetBlockPacket() {
    }

    /**
     * Creates a new ClientSetBlockPacket instance.
     *
     * @param x     X of the block to set.
     * @param y     Y of the block to set.
     * @param z     Z of the block to set.
     * @param mode  Mode used to set the block.
     * @param block Block ID to set.
     */
    public ClientSetBlockPacket(int x, int y, int z, SetBlockMode mode, int block) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.mode = mode;
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
     * Gets the mode used to set the block.
     *
     * @return The mode used to set the block.
     */
    public SetBlockMode getMode() {
        return this.mode;
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
        this.mode = SetBlockMode.values()[in.readUnsignedByte()];
        this.block = in.readUnsignedByte();
    }

    @Override
    public void write(NetOutput out) throws IOException {
        out.writeShort(this.x);
        out.writeShort(this.y);
        out.writeShort(this.z);
        out.writeByte(this.mode.ordinal());
        out.writeByte(this.block);
    }

    @Override
    public boolean isPriority() {
        return false;
    }
}
