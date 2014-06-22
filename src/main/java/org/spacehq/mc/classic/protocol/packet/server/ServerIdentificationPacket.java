package org.spacehq.mc.classic.protocol.packet.server;

import org.spacehq.mc.classic.protocol.data.game.UserType;
import org.spacehq.mc.classic.protocol.ClassicConstants;
import org.spacehq.mc.classic.protocol.packet.ClassicPacketUtil;
import org.spacehq.packetlib.io.NetInput;
import org.spacehq.packetlib.io.NetOutput;
import org.spacehq.packetlib.packet.Packet;

import java.io.IOException;

public class ServerIdentificationPacket implements Packet {
	private int protocolVersion;
	private String name;
	private String motd;
	private UserType userType;

	@SuppressWarnings("unused")
	private ServerIdentificationPacket() {
	}

	public ServerIdentificationPacket(String name, String motd, UserType userType) {
		this.protocolVersion = ClassicConstants.PROTOCOL_VERSION;
		this.name = name;
		this.motd = motd;
		this.userType = userType;
	}

	public int getProtocolVersion() {
		return this.protocolVersion;
	}

	public String getName() {
		return this.name;
	}

	public String getMotd() {
		return this.motd;
	}

	public UserType getUserType() {
		return this.userType;
	}

	@Override
	public void read(NetInput in) throws IOException {
		this.protocolVersion = in.readUnsignedByte();
		this.name = ClassicPacketUtil.readString(in);
		this.motd = ClassicPacketUtil.readString(in);
		this.userType = in.readUnsignedByte() == 0x64 ? UserType.OP : UserType.NOT_OP;
	}

	@Override
	public void write(NetOutput out) throws IOException {
		out.writeByte(this.protocolVersion);
		ClassicPacketUtil.writeString(out, this.name);
		ClassicPacketUtil.writeString(out, this.motd);
		out.writeByte(this.userType == UserType.OP ? 0x64 : 0x00);
	}

	@Override
	public boolean isPriority() {
		return false;
	}
}
