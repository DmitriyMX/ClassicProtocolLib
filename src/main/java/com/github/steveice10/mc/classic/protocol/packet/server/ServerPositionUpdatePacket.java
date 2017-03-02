package com.github.steveice10.mc.classic.protocol.packet.server;

import com.github.steveice10.packetlib.io.NetInput;
import com.github.steveice10.packetlib.io.NetOutput;
import com.github.steveice10.packetlib.packet.Packet;

import java.io.IOException;

/**
 * Sent by a server to update a client's position.
 */
public class ServerPositionUpdatePacket implements Packet {
    private int playerId;
    private float changeX;
    private float changeY;
    private float changeZ;

    @SuppressWarnings("unused")
    private ServerPositionUpdatePacket() {
    }

    /**
     * Creates a new ServerPositionUpdatePacket instance.
     *
     * @param playerId ID of the player to update the position of.
     * @param changeX  Change in the client's X.
     * @param changeY  Change in the client's Y.
     * @param changeZ  Change in the client's Z.
     */
    public ServerPositionUpdatePacket(int playerId, float changeX, float changeY, float changeZ) {
        this.playerId = playerId;
        this.changeX = changeX;
        this.changeY = changeY;
        this.changeZ = changeZ;
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
     * Gets the change in the client's X.
     *
     * @return The change in the client's X.
     */
    public double getChangeX() {
        return this.changeX;
    }

    /**
     * Gets the change in the client's Y.
     *
     * @return The change in the client's Y.
     */
    public double getChangeY() {
        return this.changeY;
    }

    /**
     * Gets the change in the client's Z.
     *
     * @return The change in the client's Z.
     */
    public double getChangeZ() {
        return this.changeZ;
    }

    @Override
    public void read(NetInput in) throws IOException {
        this.playerId = in.readUnsignedByte();
        this.changeX = (float) in.readByte() / 32;
        this.changeY = (float) in.readByte() / 32;
        this.changeZ = (float) in.readByte() / 32;
    }

    @Override
    public void write(NetOutput out) throws IOException {
        out.writeByte(this.playerId);
        out.writeByte((byte) (this.changeX * 32));
        out.writeByte((byte) (this.changeY * 32));
        out.writeByte((byte) (this.changeZ * 32));
    }

    @Override
    public boolean isPriority() {
        return false;
    }
}
