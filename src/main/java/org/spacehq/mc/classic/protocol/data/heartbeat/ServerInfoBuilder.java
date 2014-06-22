package org.spacehq.mc.classic.protocol.data.heartbeat;

import org.spacehq.packetlib.Server;

public interface ServerInfoBuilder {
	public ServerInfo build(Server server);
}
