package org.spacehq.mc.classic.protocol.data.heartbeat;

/**
 * Information about a server sent in a heartbeat.
 */
public class ServerInfo {
    private int port;
    private boolean pub;
    private String name;
    private int players;
    private int maxPlayers;

    /**
     * Creates a new ServerInfo instance.
     *
     * @param name       Name of the server.
     * @param port       Port that the server is listening on.
     * @param pub        Whether the server should be shown on the server list.
     * @param players    Number of players currently logged in to the server.
     * @param maxPlayers Maximum number of players that can be logged in at once.
     */
    public ServerInfo(String name, int port, boolean pub, int players, int maxPlayers) {
        this.port = port;
        this.pub = pub;
        this.name = name;
        this.players = players;
        this.maxPlayers = maxPlayers;
    }

    /**
     * Gets the name of the server.
     *
     * @return The server's name.
     */
    public String getName() {
        return this.name;
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
     * Gets whether the server should be shown on the server list.
     *
     * @return Whether the server is public.
     */
    public boolean isPublic() {
        return this.pub;
    }

    /**
     * Gets the number of players currently logged in to the server.
     *
     * @return The server's current number of players.
     */
    public int getPlayers() {
        return this.players;
    }

    /**
     * Gets the maximum number of players that can be logged in at once.
     *
     * @return The server's maximum number of players.
     */
    public int getMaxPlayers() {
        return this.maxPlayers;
    }
}
