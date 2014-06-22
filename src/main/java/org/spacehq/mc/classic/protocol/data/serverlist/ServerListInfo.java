package org.spacehq.mc.classic.protocol.data.serverlist;

public class ServerListInfo {
	private String url;
	private String name;
	private int players;
	private int maxPlayers;
	private String uptime;

	public ServerListInfo(String url, String name, int players, int maxPlayers, String uptime) {
		this.url = url;
		this.name = name;
		this.players = players;
		this.maxPlayers = maxPlayers;
		this.uptime = uptime;
	}

	public String getURL() {
		return this.url;
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

	public String getUptime() {
		return this.uptime;
	}
}
