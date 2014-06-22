package org.spacehq.mc.classic.protocol.packet.server;

import org.spacehq.packetlib.io.NetInput;
import org.spacehq.packetlib.io.NetOutput;
import org.spacehq.packetlib.packet.Packet;

import java.io.IOException;

public class ServerDespawnPlayerPacket implements Packet {
	private int playerId;

	@SuppressWarnings("unused")
	private ServerDespawnPlayerPacket() {
	}

	public ServerDespawnPlayerPacket(int playerId) {
		this.playerId = playerId;
	}

	public int getPlayerId() {
		return this.playerId;
	}

	@Override
	public void read(NetInput in) throws IOException {
		this.playerId = in.readUnsignedByte();
	}

	@Override
	public void write(NetOutput out) throws IOException {
		out.writeByte(this.playerId);
	}

	@Override
	public boolean isPriority() {
		return false;
	}
}
