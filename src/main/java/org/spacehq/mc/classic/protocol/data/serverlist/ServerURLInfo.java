package org.spacehq.mc.classic.protocol.data.serverlist;

/**
 * Information about a server obtained from the server's URL.
 */
public class ServerURLInfo {
    private String host;
    private int port;
    private String username;
    private String verificationKey;

    /**
     * Creates a new ServerURLInfo instance.
     *
     * @param host            Host that the server is listening on.
     * @param port            Port that the server is listening on.
     * @param username        Username of the player used to get the information.
     * @param verificationKey Verification key for logging into the server with the account used to get the information.
     */
    public ServerURLInfo(String host, int port, String username, String verificationKey) {
        this.host = host;
        this.port = port;
        this.username = username;
        this.verificationKey = verificationKey;
    }

    /**
     * Gets the host that the server is listening on.
     *
     * @return The server's host.
     */
    public String getHost() {
        return this.host;
    }

    /**
     * Gets the port that the server is listening on.
     *
     * @return The server's port.
     */
    public int getPort() {
        return this.port;
    }

    /**
     * Gets the username of the player used to get the information.
     *
     * @return The username used to get the information.
     */
    public String getUsername() {
        return this.username;
    }

    /**
     * Gets the verification key for logging into the server with the account used to get the information.
     *
     * @return The verification key for logging into the server.
     */
    public String getVerificationKey() {
        return this.verificationKey;
    }
}
