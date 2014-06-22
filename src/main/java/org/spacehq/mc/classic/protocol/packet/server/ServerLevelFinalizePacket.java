package org.spacehq.mc.classic.protocol.packet.server;

import org.spacehq.packetlib.io.NetInput;
import org.spacehq.packetlib.io.NetOutput;
import org.spacehq.packetlib.packet.Packet;

import java.io.IOException;

public class ServerLevelFinalizePacket implements Packet {
	private int width;
	private int height;
	private int depth;

	@SuppressWarnings("unused")
	private ServerLevelFinalizePacket() {
	}

	public ServerLevelFinalizePacket(int width, int height, int depth) {
		this.width = width;
		this.height = height;
		this.depth = depth;
	}

	public int getWidth() {
		return this.width;
	}

	public int getHeight() {
		return this.height;
	}

	public int getDepth() {
		return this.depth;
	}

	@Override
	public void read(NetInput in) throws IOException {
		this.width = in.readShort();
		this.height = in.readShort();
		this.depth = in.readShort();
	}

	@Override
	public void write(NetOutput out) throws IOException {
		out.writeShort(this.width);
		out.writeShort(this.height);
		out.writeShort(this.depth);
	}

	@Override
	public boolean isPriority() {
		return false;
	}
}
