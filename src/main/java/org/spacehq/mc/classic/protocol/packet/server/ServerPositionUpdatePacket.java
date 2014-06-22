package org.spacehq.mc.classic.protocol.packet.server;

import org.spacehq.packetlib.io.NetInput;
import org.spacehq.packetlib.io.NetOutput;
import org.spacehq.packetlib.packet.Packet;

import java.io.IOException;

public class ServerPositionUpdatePacket implements Packet {
	private int playerId;
	private float changeX;
	private float changeY;
	private float changeZ;

	@SuppressWarnings("unused")
	private ServerPositionUpdatePacket() {
	}

	public ServerPositionUpdatePacket(int playerId, float changeX, float changeY, float changeZ) {
		this.playerId = playerId;
		this.changeX = changeX;
		this.changeY = changeY;
		this.changeZ = changeZ;
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
