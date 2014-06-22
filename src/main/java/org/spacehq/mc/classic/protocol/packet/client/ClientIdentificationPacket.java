package org.spacehq.mc.classic.protocol.packet.client;

import org.spacehq.mc.classic.protocol.ClassicConstants;
import org.spacehq.mc.classic.protocol.packet.ClassicPacketUtil;
import org.spacehq.packetlib.io.NetInput;
import org.spacehq.packetlib.io.NetOutput;
import org.spacehq.packetlib.packet.Packet;

import java.io.IOException;

public class ClientIdentificationPacket implements Packet {
	private int protocolVersion;
	private String username;
	private String verificationKey;
	private int unused;

	@SuppressWarnings("unused")
	private ClientIdentificationPacket() {
	}

	public ClientIdentificationPacket(String username, String verificationKey, int unused) {
		this.protocolVersion = ClassicConstants.PROTOCOL_VERSION;
		this.username = username;
		this.verificationKey = verificationKey;
		this.unused = unused;
	}

	public int getProtocolVersion() {
		return this.protocolVersion;
	}

	public String getUsername() {
		return this.username;
	}

	public String getVerificationKey() {
		return this.verificationKey;
	}

	public int getUnused() {
		return this.unused;
	}

	@Override
	public void read(NetInput in) throws IOException {
		this.protocolVersion = in.readUnsignedByte();
		this.username = ClassicPacketUtil.readString(in).replaceAll("[^A-Za-z0-9_-]+", "");
		this.verificationKey = ClassicPacketUtil.readString(in);
		this.unused = in.readUnsignedByte();
	}

	@Override
	public void write(NetOutput out) throws IOException {
		out.writeByte(this.protocolVersion);
		ClassicPacketUtil.writeString(out, this.username);
		ClassicPacketUtil.writeString(out, this.verificationKey);
		out.writeByte(this.unused);
	}

	@Override
	public boolean isPriority() {
		return false;
	}
}
