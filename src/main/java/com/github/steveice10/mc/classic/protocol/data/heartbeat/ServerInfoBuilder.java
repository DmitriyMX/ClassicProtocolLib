package com.github.steveice10.mc.classic.protocol.data.heartbeat;

import com.github.steveice10.packetlib.Server;

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
