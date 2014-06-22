package org.spacehq.mc.classic.protocol.packet.server;

import org.spacehq.packetlib.io.NetInput;
import org.spacehq.packetlib.io.NetOutput;
import org.spacehq.packetlib.packet.Packet;

import java.io.IOException;

public class ServerRotationUpdatePacket implements Packet {
	private int playerId;
	private float yaw;
	private float pitch;

	@SuppressWarnings("unused")
	private ServerRotationUpdatePacket() {
	}

	public ServerRotationUpdatePacket(int playerId, float yaw, float pitch) {
		this.playerId = playerId;
		this.yaw = yaw;
		this.pitch = pitch;
	}

	public int getPlayerId() {
		return this.playerId;
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
