package org.spacehq.mc.classic.protocol.packet.server;

import org.spacehq.packetlib.io.NetInput;
import org.spacehq.packetlib.io.NetOutput;
import org.spacehq.packetlib.packet.Packet;

import java.io.IOException;

public class ServerPositionRotationUpdatePacket implements Packet {
	private int playerId;
	private float changeX;
	private float changeY;
	private float changeZ;
	private float yaw;
	private float pitch;

	@SuppressWarnings("unused")
	private ServerPositionRotationUpdatePacket() {
	}

	public ServerPositionRotationUpdatePacket(int playerId, float changeX, float changeY, float changeZ, float yaw, float pitch) {
		this.playerId = playerId;
		this.changeX = changeX;
		this.changeY = changeY;
		this.changeZ = changeZ;
		this.yaw = yaw;
		this.pitch = pitch;
	}

	public int getPlayerId() {
		return this.playerId;
	}

	public double getChangeX() {
		return this.changeX;
	}

	public double getChangeY() {
		return this.changeY;
	}

	public double getChangeZ() {
		return this.changeZ;
	}

	public float getYaw() {
		return this.yaw;
	}

	public float getPitch() {
		return this.pitch;
	}

	@Override
	public void read(NetInput in) throws IOException {
		this.playerId = in.readUnsignedByte();
		this.changeX = (float) in.readByte() / 32;
		this.changeY = (float) in.readByte() / 32;
		this.changeZ = (float) in.readByte() / 32;
		this.yaw = (in.readUnsignedByte() * 360) / 256f;
		this.pitch = (in.readUnsignedByte() * 360) / 256f;
	}

	@Override
	public void write(NetOutput out) throws IOException {
		out.writeByte(this.playerId);
		out.writeByte((byte) (this.changeX * 32));
		out.writeByte((byte) (this.changeY * 32));
		out.writeByte((byte) (this.changeZ * 32));
		out.writeByte((byte) ((int) (this.yaw * 256 / 360) & 255));
		out.writeByte((byte) ((int) (this.pitch * 256 / 360) & 255));
	}

	@Override
	public boolean isPriority() {
		return false;
	}
}
