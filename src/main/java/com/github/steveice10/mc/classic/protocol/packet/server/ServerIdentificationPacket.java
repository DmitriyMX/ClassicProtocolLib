package com.github.steveice10.mc.classic.protocol.packet.server;

import com.github.steveice10.mc.classic.protocol.ClassicConstants;
import com.github.steveice10.mc.classic.protocol.data.game.UserType;
import com.github.steveice10.mc.classic.protocol.packet.ClassicPacketUtil;
import com.github.steveice10.packetlib.io.NetInput;
import com.github.steveice10.packetlib.io.NetOutput;
import com.github.steveice10.packetlib.packet.Packet;

import java.io.IOException;

/**
 * Sent by a server to identify itself to a client.
 */
public class ServerIdentificationPacket implements Packet {
    private int protocolVersion;
    private String name;
    private String motd;
    private UserType userType;

    @SuppressWarnings("unused")
    private ServerIdentificationPacket() {
    }

    /**
     * Creates a new ServerIdentificationPacket instance.
     *
     * @param name     Name of the server.
     * @param motd     MOTD of the server.
     * @param userType User type of the client receiving the packet.
     */
    public ServerIdentificationPacket(String name, String motd, UserType userType) {
        this.protocolVersion = ClassicConstants.PROTOCOL_VERSION;
        this.name = name;
        this.motd = motd;
        this.userType = userType;
    }

    /**
     * Gets the protocol version of the server.
     *
     * @return The server's protocol version.
     */
    public int getProtocolVersion() {
        return this.protocolVersion;
    }

    /**
     * Gets the name of the server.
     *
     * @return The server's name.
     */
    public String getName() {
        return this.name;
    }

    /**
     * Gets the MOTD of the server.
     *
     * @return The server's MOTD.
     */
    public String getMotd() {
        return this.motd;
    }

    /**
     * Gets the user type of the client receiving the packet.
     *
     * @return The client's user type.
     */
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
