package org.spacehq.mc.classic.protocol.packet.client;

import org.spacehq.mc.classic.protocol.packet.ClassicPacketUtil;
import org.spacehq.packetlib.io.NetInput;
import org.spacehq.packetlib.io.NetOutput;
import org.spacehq.packetlib.packet.Packet;

import java.io.IOException;

public class ClientChatPacket implements Packet {
	private int unused;
	private String message;

	@SuppressWarnings("unused")
	private ClientChatPacket() {
	}

	public ClientChatPacket(String message) {
		this(255, message);
	}

	public ClientChatPacket(int unused, String message) {
		this.unused = unused;
		this.message = message;
	}

	public int getUnused() {
		return this.unused;
	}

	public String getMessage() {
		return this.message;
	}

	@Override
	public void read(NetInput in) throws IOException {
		this.unused = in.readUnsignedByte();
		this.message = ClassicPacketUtil.readString(in);
	}

	@Override
	public void write(NetOutput out) throws IOException {
		out.writeByte(this.unused);
		ClassicPacketUtil.writeString(out, this.message);
	}

	@Override
	public boolean isPriority() {
		return false;
	}
}
