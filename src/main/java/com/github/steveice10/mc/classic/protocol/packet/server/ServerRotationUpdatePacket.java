package com.github.steveice10.mc.classic.protocol.packet.server;

import com.github.steveice10.packetlib.io.NetInput;
import com.github.steveice10.packetlib.io.NetOutput;
import com.github.steveice10.packetlib.packet.Packet;

import java.io.IOException;

/**
 * Sent by a server to update a client's rotation.
 */
public class ServerRotationUpdatePacket implements Packet {
    private int playerId;
    private float yaw;
    private float pitch;

    @SuppressWarnings("unused")
    private ServerRotationUpdatePacket() {
    }

    /**
     * Creates a new ServerPositionRotationUpdatePacket instance.
     *
     * @param playerId ID of the player to update the rotation of.
     * @param yaw      Yaw to set.
     * @param pitch    Pitch to set.
     */
    public ServerRotationUpdatePacket(int playerId, float yaw, float pitch) {
        this.playerId = playerId;
        this.yaw = yaw;
        this.pitch = pitch;
    }

    /**
     * Gets the ID of the player to update the position and rotation of.
     *
     * @return The player's ID.
     */
    public int getPlayerId() {
        return this.playerId;
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
        this.yaw = (in.readUnsignedByte() * 360) / 256f;
        this.pitch = (in.readUnsignedByte() * 360) / 256f;
    }

    @Override
    public void write(NetOutput out) throws IOException {
        out.writeByte(this.playerId);
        out.writeByte((byte) ((int) (this.yaw * 256 / 360) & 255));
        out.writeByte((byte) ((int) (this.pitch * 256 / 360) & 255));
    }

    @Override
    public boolean isPriority() {
        return false;
    }
}
