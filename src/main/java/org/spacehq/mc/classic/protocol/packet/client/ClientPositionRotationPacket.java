package org.spacehq.mc.classic.protocol.packet.client;

import org.spacehq.packetlib.io.NetInput;
import org.spacehq.packetlib.io.NetOutput;
import org.spacehq.packetlib.packet.Packet;

import java.io.IOException;

public class ClientPositionRotationPacket implements Packet {
	private int unused;
	private float x;
	private float y;
	private float z;
	private float yaw;
	private float pitch;

	@SuppressWarnings("unused")
	private ClientPositionRotationPacket() {
	}

	public ClientPositionRotationPacket(float x, float y, float z, float yaw, float pitch) {
		this(255, x, y, z, yaw, pitch);
	}

	public ClientPositionRotationPacket(int unused, float x, float y, float z, float yaw, float pitch) {
		this.unused = unused;
		this.x = x;
		this.y = y;
		this.z = z;
		this.yaw = yaw;
		this.pitch = pitch;
	}

	public int getUnused() {
		return this.unused;
	}

	public double getX() {
		return this.x;
	}

	public double getY() {
		return this.y;
	}

	public double getZ() {
		return this.z;
	}

	public float getYaw() {
		return this.yaw;
	}

	public float getPitch() {
		return this.pitch;
	}

	@Override
	public void read(NetInput in) throws IOException {
		this.unused = in.readUnsignedByte();
		this.x = (float) in.readShort() / 32;
		this.y = (float) in.readShort() / 32;
		this.z = (float) in.readShort() / 32;
		this.yaw = (in.readUnsignedByte() * 360) / 256f;
		this.pitch = (in.readUnsignedByte() * 360) / 256f;
	}

	@Override
	public void write(NetOutput out) throws IOException {
		out.writeByte(this.unused);
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
