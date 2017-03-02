package com.github.steveice10.mc.classic.protocol.packet.server;

import com.github.steveice10.mc.classic.protocol.data.game.UserType;
import com.github.steveice10.packetlib.io.NetInput;
import com.github.steveice10.packetlib.io.NetOutput;
import com.github.steveice10.packetlib.packet.Packet;

import java.io.IOException;

/**
 * Sent by a server to update a client's user type.
 */
public class ServerUpdateUserTypePacket implements Packet {
    private UserType userType;

    @SuppressWarnings("unused")
    private ServerUpdateUserTypePacket() {
    }

    /**
     * Creates a new ServerUpdateUserTypePacket instance.
     *
     * @param userType User type to update to.
     */
    public ServerUpdateUserTypePacket(UserType userType) {
        this.userType = userType;
    }

    /**
     * Gets the user type to update to.
     *
     * @return The user type to update to.
     */
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
