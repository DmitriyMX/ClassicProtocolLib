package org.spacehq.mc.classic.protocol.data.serverlist;

public class ServerURLInfo {
	private String host;
	private int port;
	private String username;
	private String verificationKey;

	public ServerURLInfo(String host, int port, String username, String verificationKey) {
		this.host = host;
		this.port = port;
		this.username = username;
		this.verificationKey = verificationKey;
	}

	public String getHost() {
		return this.host;
	}

	public int getPort() {
		return this.port;
	}

	public String getUsername() {
		return this.username;
	}

	public String getVerificationKey() {
		return this.verificationKey;
	}
}
