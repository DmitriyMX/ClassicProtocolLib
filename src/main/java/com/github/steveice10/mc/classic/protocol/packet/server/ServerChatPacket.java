package com.github.steveice10.mc.classic.protocol.packet.server;

import com.github.steveice10.mc.classic.protocol.packet.ClassicPacketUtil;
import com.github.steveice10.packetlib.io.NetInput;
import com.github.steveice10.packetlib.io.NetOutput;
import com.github.steveice10.packetlib.packet.Packet;

import java.io.IOException;

/**
 * Sent by a server to display a chat message on a client.
 */
public class ServerChatPacket implements Packet {
    private int playerId;
    private String message;

    @SuppressWarnings("unused")
    private ServerChatPacket() {
    }

    /**
     * Creates a new ServerChatPacket instance.
     *
     * @param playerId ID of the player sending the chat message.
     * @param message  Message to send.
     */
    public ServerChatPacket(int playerId, String message) {
        this.playerId = playerId;
        this.message = message;
    }

    /**
     * Gets the ID of the player sending the chat message.
     *
     * @return The player's ID.
     */
    public int getPlayerId() {
        return this.playerId;
    }

    /**
     * Gets the message being sent.
     *
     * @return The message being sent.
     */
    public String getMessage() {
        return this.message;
    }

    @Override
    public void read(NetInput in) throws IOException {
        this.playerId = in.readUnsignedByte();
        this.message = ClassicPacketUtil.readString(in);
    }

    @Override
    public void write(NetOutput out) throws IOException {
        out.writeByte(this.playerId);
        ClassicPacketUtil.writeString(out, this.message);
    }

    @Override
    public boolean isPriority() {
        return false;
    }
}
