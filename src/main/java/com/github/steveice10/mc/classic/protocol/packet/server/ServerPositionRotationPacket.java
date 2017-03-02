package com.github.steveice10.mc.classic.protocol.packet.server;

import com.github.steveice10.packetlib.io.NetInput;
import com.github.steveice10.packetlib.io.NetOutput;
import com.github.steveice10.packetlib.packet.Packet;

import java.io.IOException;

/**
 * Sent by a server to set a client's position and rotation.
 */
public class ServerPositionRotationPacket implements Packet {
    private int playerId;
    private float x;
    private float y;
    private float z;
    private float yaw;
    private float pitch;

    @SuppressWarnings("unused")
    private ServerPositionRotationPacket() {
    }

    /**
     * Creates a new ServerPositionRotationPacket instance.
     *
     * @param playerId ID of the player to set the position and rotation of.
     * @param x        X to set.
     * @param y        Y to set.
     * @param z        Z to set.
     * @param yaw      Yaw to set.
     * @param pitch    Pitch to set.
     */
    public ServerPositionRotationPacket(int playerId, float x, float y, float z, float yaw, float pitch) {
        this.playerId = playerId;
        this.x = x;
        this.y = y;
        this.z = z;
        this.yaw = yaw;
        this.pitch = pitch;
    }

    /**
     * Gets the ID of the player to set the position and rotation of.
     *
     * @return The player's ID.
     */
    public int getPlayerId() {
        return this.playerId;
    }

    /**
     * Gets the X to set.
     *
     * @return The X to set.
     */
    public double getX() {
        return this.x;
    }

    /**
     * Gets the Y to set.
     *
     * @return The Y to set.
     */
    public double getY() {
        return this.y;
    }

    /**
     * Gets the Z to set.
     *
     * @return The Z to set.
     */
    public double getZ() {
        return this.z;
    }

    /**
     * Gets the yaw to set.
     *
     * @return The yaw to set.
     */
    public float getYaw() {
        return this.yaw;
    }

    /**
     * Gets the pitch to set.
     *
     * @return The pitch to set.
     */
    public float getPitch() {
        return this.pitch;
    }

    @Override
    public void read(NetInput in) throws IOException {
        this.playerId = in.readUnsignedByte();
        this.x = (float) in.readShort() / 32;
        this.y = (float) in.readShort() / 32;
        this.z = (float) in.readShort() / 32;
        this.yaw = (in.readUnsignedByte() * 360) / 256f;
        this.pitch = (in.readUnsignedByte() * 360) / 256f;
    }

    @Override
    public void write(NetOutput out) throws IOException {
        out.writeByte(this.playerId);
        out.writeShort((short) (this.x * 32));
        out.writeShort((short) (this.y * 32));
        out.writeShort((short) (this.z * 32));
        out.writeByte((byte) ((int) (this.yaw * 256 / 360) & 255));
        out.writeByte((byte) ((int) (this.pitch * 256 / 360) & 255));
    }

    @Override
    public boolean isPriority() {
        return false;
    }
}
