package org.spacehq.mc.classic.protocol.packet.server;

import org.spacehq.mc.classic.protocol.data.game.UserType;
import org.spacehq.packetlib.io.NetInput;
import org.spacehq.packetlib.io.NetOutput;
import org.spacehq.packetlib.packet.Packet;

import java.io.IOException;

public class ServerUpdateUserTypePacket implements Packet {
	private UserType userType;

	@SuppressWarnings("unused")
	private ServerUpdateUserTypePacket() {
	}

	public ServerUpdateUserTypePacket(UserType userType) {
		this.userType = userType;
	}

	public UserType getUserType() {
		return this.userType;
	}

	@Override
	public void read(NetInput in) throws IOException {
		this.userType = UserType.values()[in.readUnsignedByte()];
	}

	@Override
	public void write(NetOutput out) throws IOException {
		out.writeByte(this.userType.ordinal());
	}

	@Override
	public boolean isPriority() {
		return false;
	}
}
