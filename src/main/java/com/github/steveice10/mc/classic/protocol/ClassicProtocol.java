package com.github.steveice10.mc.classic.protocol;

import com.github.steveice10.mc.classic.protocol.data.serverlist.ServerURLInfo;
import com.github.steveice10.mc.classic.protocol.packet.client.ClientChatPacket;
import com.github.steveice10.mc.classic.protocol.packet.client.ClientIdentificationPacket;
import com.github.steveice10.mc.classic.protocol.packet.client.ClientPositionRotationPacket;
import com.github.steveice10.mc.classic.protocol.packet.client.ClientSetBlockPacket;
import com.github.steveice10.mc.classic.protocol.packet.server.ServerChatPacket;
import com.github.steveice10.mc.classic.protocol.packet.server.ServerDespawnPlayerPacket;
import com.github.steveice10.mc.classic.protocol.packet.server.ServerDisconnectPacket;
import com.github.steveice10.mc.classic.protocol.packet.server.ServerIdentificationPacket;
import com.github.steveice10.mc.classic.protocol.packet.server.ServerLevelDataPacket;
import com.github.steveice10.mc.classic.protocol.packet.server.ServerLevelFinalizePacket;
import com.github.steveice10.mc.classic.protocol.packet.server.ServerLevelInitializePacket;
import com.github.steveice10.mc.classic.protocol.packet.server.ServerPingPacket;
import com.github.steveice10.mc.classic.protocol.packet.server.ServerPositionRotationPacket;
import com.github.steveice10.mc.classic.protocol.packet.server.ServerPositionRotationUpdatePacket;
import com.github.steveice10.mc.classic.protocol.packet.server.ServerPositionUpdatePacket;
import com.github.steveice10.mc.classic.protocol.packet.server.ServerRotationUpdatePacket;
import com.github.steveice10.mc.classic.protocol.packet.server.ServerSetBlockPacket;
import com.github.steveice10.mc.classic.protocol.packet.server.ServerSpawnPlayerPacket;
import com.github.steveice10.mc.classic.protocol.packet.server.ServerUpdateUserTypePacket;
import com.github.steveice10.packetlib.Client;
import com.github.steveice10.packetlib.Server;
import com.github.steveice10.packetlib.Session;
import com.github.steveice10.packetlib.crypt.PacketEncryption;
import com.github.steveice10.packetlib.packet.PacketHeader;
import com.github.steveice10.packetlib.packet.PacketProtocol;

/**
 * Minecraft Classic protocol.
 */
public class ClassicProtocol extends PacketProtocol {
    private PacketHeader header = new ClassicPacketHeader();

    private String username;
    private String verificationKey;

    @SuppressWarnings("unused")
    private ClassicProtocol() {
    }

    /**
     * Creates a new ClassicProtocol instance.
     *
     * @param username Username to use when connecting.
     */
    public ClassicProtocol(String username) {
        this(username, "-");
    }

    /**
     * Creates a new ClassicProtocol instance.
     *
     * @param username        Username to use when connecting.
     * @param verificationKey Verification key to use when connecting.
     */
    public ClassicProtocol(String username, String verificationKey) {
        this.username = username.contains("@") ? username.substring(0, username.indexOf("@")) : username;
        this.verificationKey = verificationKey;
    }

    /**
     * Creates a new ClassicProtocol instance.
     *
     * @param server Server URL information to use when connecting.
     */
    public ClassicProtocol(ServerURLInfo server) {
        this(server.getUsername(), server.getVerificationKey());
    }

    @Override
    public String getSRVRecordPrefix() {
        return "_minecraftclassic";
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
