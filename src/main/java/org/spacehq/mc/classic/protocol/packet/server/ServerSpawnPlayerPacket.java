package org.spacehq.mc.classic.protocol.packet.server;

import org.spacehq.mc.classic.protocol.packet.ClassicPacketUtil;
import org.spacehq.packetlib.io.NetInput;
import org.spacehq.packetlib.io.NetOutput;
import org.spacehq.packetlib.packet.Packet;

import java.io.IOException;

/**
 * Sent by a server to spawn a player.
 */
public class ServerSpawnPlayerPacket implements Packet {
    private int playerId;
    private String name;
    private float x;
    private float y;
    private float z;
    private float yaw;
    private float pitch;

    @SuppressWarnings("unused")
    private ServerSpawnPlayerPacket() {
    }

    /**
     * Creates a new ServerSpawnPlayerPacket instance.
     *
     * @param playerId ID of the player to spawn.
     * @param name     Name of the player to spawn.
     * @param x        X to spawn the player at.
     * @param y        Y to spawn the player at.
     * @param z        Z to spawn the player at.
     * @param yaw      Yaw to spawn the player with.
     * @param pitch    Pitch to spawn the player with.
     */
    public ServerSpawnPlayerPacket(int playerId, String name, float x, float y, float z, float yaw, float pitch) {
        this.playerId = playerId;
        this.name = name;
        this.x = x;
        this.y = y;
        this.z = z;
        this.yaw = yaw;
        this.pitch = pitch;
    }

    /**
     * Gets the ID of the player to spawn.
     *
     * @return The player's ID.
     */
    public int getPlayerId() {
        return this.playerId;
    }

    /**
     * Gets the name of the player to spawn.
     *
     * @return The player's name.
     */
    public String getName() {
        return this.name;
    }

    /**
     * Gets the X to spawn the player at.
     *
     * @return The player's X.
     */
    public double getX() {
        return this.x;
    }

    /**
     * Gets the Y to spawn the player at.
     *
     * @return The player's Y.
     */
    public double getY() {
        return this.y;
    }

    /**
     * Gets the Z to spawn the player at.
     *
     * @return The player's Z.
     */
    public double getZ() {
        return this.z;
    }

    /**
     * Gets the yaw to spawn the player with.
     *
     * @return The player's yaw.
     */
    public float getYaw() {
        return this.yaw;
    }

    /**
     * Gets the pitch to spawn the player with.
     *
     * @return The player's pitch.
     */
    public float getPitch() {
        return this.pitch;
    }

    @Override
    public void read(NetInput in) throws IOException {
        this.playerId = in.readUnsignedByte();
        this.name = ClassicPacketUtil.readString(in);
        this.x = (float) in.readShort() / 32;
        this.y = (float) in.readShort() / 32;
        this.z = (float) in.readShort() / 32;
        this.yaw = (in.readUnsignedByte() * 360) / 256f;
        this.pitch = (in.readUnsignedByte() * 360) / 256f;
    }

    @Override
    public void write(NetOutput out) throws IOException {
        out.writeByte(this.playerId);
        ClassicPacketUtil.writeString(out, this.name);
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
