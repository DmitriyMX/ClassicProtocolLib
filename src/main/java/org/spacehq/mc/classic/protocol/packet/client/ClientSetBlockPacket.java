package org.spacehq.mc.classic.protocol.packet.client;

import org.spacehq.mc.classic.protocol.data.game.SetBlockMode;
import org.spacehq.packetlib.io.NetInput;
import org.spacehq.packetlib.io.NetOutput;
import org.spacehq.packetlib.packet.Packet;

import java.io.IOException;

public class ClientSetBlockPacket implements Packet {
	private int x;
	private int y;
	private int z;
	private SetBlockMode mode;
	private int block;

	@SuppressWarnings("unused")
	private ClientSetBlockPacket() {
	}

	public ClientSetBlockPacket(int x, int y, int z, SetBlockMode mode, int block) {
		this.x = x;
		this.y = y;
		this.z = z;
		this.mode = mode;
		this.block = block;
	}

	public int getX() {
		return this.x;
	}

	public int getY() {
		return this.y;
	}

	public int getZ() {
		return this.z;
	}

	public SetBlockMode getMode() {
		return this.mode;
	}

	public int getBlock() {
		return this.block;
	}

	@Override
	public void read(NetInput in) throws IOException {
		this.x = in.readShort();
		this.y = in.readShort();
		this.z = in.readShort();
		this.mode = SetBlockMode.values()[in.readUnsignedByte()];
		this.block = in.readUnsignedByte();
	}

	@Override
	public void write(NetOutput out) throws IOException {
		out.writeShort(this.x);
		out.writeShort(this.y);
		out.writeShort(this.z);
		out.writeByte(this.mode.ordinal());
		out.writeByte(this.block);
	}

	@Override
	public boolean isPriority() {
		return false;
	}
}
