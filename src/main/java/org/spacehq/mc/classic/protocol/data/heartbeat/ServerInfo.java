package org.spacehq.mc.classic.protocol.data.heartbeat;

public class ServerInfo {
	private int port;
	private boolean pub;
	private String name;
	private int players;
	private int maxPlayers;

	public ServerInfo(int port, boolean pub, String name, int players, int maxPlayers) {
		this.port = port;
		this.pub = pub;
		this.name = name;
		this.players = players;
		this.maxPlayers = maxPlayers;
	}

	public int getPort() {
		return this.port;
	}

	public boolean isPublic() {
		return this.pub;
	}

	public String getName() {
		return this.name;
	}

	public int getPlayers() {
		return this.players;
	}

	public int getMaxPlayers() {
		return this.maxPlayers;
	}
}
