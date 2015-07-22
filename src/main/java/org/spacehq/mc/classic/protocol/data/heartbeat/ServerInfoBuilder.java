package org.spacehq.mc.classic.protocol.data.heartbeat;

import org.spacehq.packetlib.Server;

/**
 * Callback used to retrieve server information when sending a heartbeat.
 */
public interface ServerInfoBuilder {
    /**
     * Builds a server's information.
     *
     * @param server Server to build information for.
     * @return The server's information.
     */
    public ServerInfo build(Server server);
}
