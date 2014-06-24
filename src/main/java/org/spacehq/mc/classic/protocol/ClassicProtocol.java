package org.spacehq.mc.classic.protocol;

import org.spacehq.mc.classic.protocol.data.serverlist.ServerURLInfo;
import org.spacehq.mc.classic.protocol.packet.client.ClientChatPacket;
import org.spacehq.mc.classic.protocol.packet.client.ClientIdentificationPacket;
import org.spacehq.mc.classic.protocol.packet.client.ClientPositionRotationPacket;
import org.spacehq.mc.classic.protocol.packet.client.ClientSetBlockPacket;
import org.spacehq.mc.classic.protocol.packet.server.*;
import org.spacehq.packetlib.Client;
import org.spacehq.packetlib.Server;
import org.spacehq.packetlib.Session;
import org.spacehq.packetlib.crypt.PacketEncryption;
import org.spacehq.packetlib.packet.PacketHeader;
import org.spacehq.packetlib.packet.PacketProtocol;

public class ClassicProtocol extends PacketProtocol {
	private PacketHeader header = new ClassicPacketHeader();

	private String username;
	private String verificationKey;

	@SuppressWarnings("unused")
	private ClassicProtocol() {
	}

	public ClassicProtocol(String username) {
		this(username, "-");
	}

	public ClassicProtocol(String username, String verificationKey) {
		this.username = username.contains("@") ? username.substring(0, username.indexOf("@")) : username;
		this.verificationKey = verificationKey;
	}

	public ClassicProtocol(ServerURLInfo server) {
		this(server.getUsername(), server.getVerificationKey());
	}

	@Override
	public boolean needsPacketSizer() {
		return false;
	}

	@Override
	public boolean needsPacketEncryptor() {
		return false;
	}

	@Override
	public PacketHeader getPacketHeader() {
		return this.header;
	}

	@Override
	public PacketEncryption getEncryption() {
		return null;
	}

	@Override
	public void newClientSession(Client client, Session session) {
		this.registerIncoming(0x00, ServerIdentificationPacket.class);
		this.registerIncoming(0x01, ServerPingPacket.class);
		this.registerIncoming(0x02, ServerLevelInitializePacket.class);
		this.registerIncoming(0x03, ServerLevelDataPacket.class);
		this.registerIncoming(0x04, ServerLevelFinalizePacket.class);
		this.registerIncoming(0x06, ServerSetBlockPacket.class);
		this.registerIncoming(0x07, ServerSpawnPlayerPacket.class);
		this.registerIncoming(0x08, ServerPositionRotationPacket.class);
		this.registerIncoming(0x09, ServerPositionRotationUpdatePacket.class);
		this.registerIncoming(0x0A, ServerPositionUpdatePacket.class);
		this.registerIncoming(0x0B, ServerRotationUpdatePacket.class);
		this.registerIncoming(0x0C, ServerDespawnPlayerPacket.class);
		this.registerIncoming(0x0D, ServerChatPacket.class);
		this.registerIncoming(0x0E, ServerDisconnectPacket.class);
		this.registerIncoming(0x0F, ServerUpdateUserTypePacket.class);

		this.registerOutgoing(0x00, ClientIdentificationPacket.class);
		this.registerOutgoing(0x05, ClientSetBlockPacket.class);
		this.registerOutgoing(0x08, ClientPositionRotationPacket.class);
		this.registerOutgoing(0x0D, ClientChatPacket.class);
		session.setFlag(ClassicConstants.USERNAME_KEY, this.username);
		session.setFlag(ClassicConstants.VERIFICATION_KEY, this.verificationKey);
		session.addListener(new ClientListener());
	}

	@Override
	public void newServerSession(Server server, Session session) {
		this.registerIncoming(0x00, ClientIdentificationPacket.class);
		this.registerIncoming(0x05, ClientSetBlockPacket.class);
		this.registerIncoming(0x08, ClientPositionRotationPacket.class);
		this.registerIncoming(0x0D, ClientChatPacket.class);

		this.registerOutgoing(0x00, ServerIdentificationPacket.class);
		this.registerOutgoing(0x01, ServerPingPacket.class);
		this.registerOutgoing(0x02, ServerLevelInitializePacket.class);
		this.registerOutgoing(0x03, ServerLevelDataPacket.class);
		this.registerOutgoing(0x04, ServerLevelFinalizePacket.class);
		this.registerOutgoing(0x06, ServerSetBlockPacket.class);
		this.registerOutgoing(0x07, ServerSpawnPlayerPacket.class);
		this.registerOutgoing(0x08, ServerPositionRotationPacket.class);
		this.registerOutgoing(0x09, ServerPositionRotationUpdatePacket.class);
		this.registerOutgoing(0x0A, ServerPositionUpdatePacket.class);
		this.registerOutgoing(0x0B, ServerRotationUpdatePacket.class);
		this.registerOutgoing(0x0C, ServerDespawnPlayerPacket.class);
		this.registerOutgoing(0x0D, ServerChatPacket.class);
		this.registerOutgoing(0x0E, ServerDisconnectPacket.class);
		this.registerOutgoing(0x0F, ServerUpdateUserTypePacket.class);
		session.addListener(new ServerListener());
	}
}
