package org.spacehq.mc.classic.protocol.packet.client;

import org.spacehq.mc.classic.protocol.data.game.PlayerIds;
import org.spacehq.mc.classic.protocol.packet.ClassicPacketUtil;
import org.spacehq.packetlib.io.NetInput;
import org.spacehq.packetlib.io.NetOutput;
import org.spacehq.packetlib.packet.Packet;

import java.io.IOException;

/**
 * Sent by a client to submit a chat message.
 */
public class ClientChatPacket implements Packet {
    private int unused;
    private String message;

    @SuppressWarnings("unused")
    private ClientChatPacket() {
    }

    /**
     * Creates a new ClientChatPacket instance.
     *
     * @param message Message being sent.
     */
    public ClientChatPacket(String message) {
        this(PlayerIds.SELF, message);
    }

    /**
     * Creates a new ClientChatPacket instance.
     *
     * @param unused  Unused byte.
     * @param message Message being sent.
     */
    public ClientChatPacket(int unused, String message) {
        this.unused = unused;
        this.message = message;
    }

    /**
     * Gets the unused byte of this packet.
     *
     * @return The packet's unused byte.
     */
    public int getUnused() {
        return this.unused;
    }

    /**
     * Gets the message sent by the client.
     *
     * @return The packet's message.
     */
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
