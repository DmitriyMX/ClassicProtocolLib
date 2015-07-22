package org.spacehq.mc.classic.protocol;

import org.spacehq.mc.classic.protocol.packet.client.ClientIdentificationPacket;
import org.spacehq.mc.classic.protocol.packet.server.ServerDisconnectPacket;
import org.spacehq.packetlib.event.session.ConnectedEvent;
import org.spacehq.packetlib.event.session.PacketReceivedEvent;
import org.spacehq.packetlib.event.session.SessionAdapter;

/**
 * Listener used on Minecraft Classic client sessions.
 */
public class ClientListener extends SessionAdapter {
    @Override
    public void connected(ConnectedEvent event) {
        event.getSession().send(new ClientIdentificationPacket(event.getSession().<String>getFlag(ClassicConstants.USERNAME_KEY), event.getSession().<String>getFlag(ClassicConstants.VERIFICATION_KEY), 0));
    }

    @Override
    public void packetReceived(PacketReceivedEvent event) {
        if(event.getPacket() instanceof ServerDisconnectPacket) {
            event.getSession().disconnect(event.<ServerDisconnectPacket>getPacket().getReason());
        }
    }
}