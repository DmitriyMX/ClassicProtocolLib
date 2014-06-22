package org.spacehq.mc.classic.protocol.packet.server;

import org.spacehq.mc.classic.protocol.packet.ClassicPacketUtil;
import org.spacehq.packetlib.io.NetInput;
import org.spacehq.packetlib.io.NetOutput;
import org.spacehq.packetlib.packet.Packet;

import java.io.IOException;

public class ServerChatPacket implements Packet {
	private int playerId;
	private String message;

	@SuppressWarnings("unused")
	private ServerChatPacket() {
	}

	public ServerChatPacket(int playerId, String message) {
		this.playerId = playerId;
		this.message = message;
	}

	public int getPlayerId() {
		return this.playerId;
	}

	public String getMessage() {
		return this.message;
	}

	@Override
	public void read(NetInput in) throws IOException {
		this.playerId = in.readUnsignedByte();
		this.message = ClassicPacketUtil.readString(in);
	}

	@Override
	public void write(NetOutput out) throws IOException {
		out.writeByte(this.playerId);
		ClassicPacketUtil.writeString(out, this.message);
	}

	@Override
	public boolean isPriority() {
		return false;
	}
}
