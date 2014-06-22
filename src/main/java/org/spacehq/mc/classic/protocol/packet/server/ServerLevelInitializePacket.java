package org.spacehq.mc.classic.protocol.packet.server;

import org.spacehq.packetlib.io.NetInput;
import org.spacehq.packetlib.io.NetOutput;
import org.spacehq.packetlib.packet.Packet;

import java.io.IOException;

public class ServerLevelInitializePacket implements Packet {
	public ServerLevelInitializePacket() {
	}

	@Override
	public void read(NetInput in) throws IOException {
	}

	@Override
	public void write(NetOutput out) throws IOException {
	}

	@Override
	public boolean isPriority() {
		return false;
	}
}
