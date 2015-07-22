package org.spacehq.mc.classic.protocol.data.serverlist;

/**
 * Information about a server obtained from the server list.
 */
public class ServerListInfo {
    private String url;
    private String name;
    private int players;
    private int maxPlayers;
    private String uptime;

    /**
     * Creates a new ServerListInfo instance.
     *
     * @param name       Name of the server.
     * @param url        URL of the server.
     * @param players    Number of players currently logged in to the server.
     * @param maxPlayers Maximum number of players that can be logged in at once.
     * @param uptime     String describing the amount of time the server has been online.
     */
    public ServerListInfo(String name, String url, int players, int maxPlayers, String uptime) {
        this.name = name;
        this.url = url;
        this.players = players;
        this.maxPlayers = maxPlayers;
        this.uptime = uptime;
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
     * Gets the URL of the server.
     *
     * @return The server's URL.
     */
    public String getURL() {
        return this.url;
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

    /**
     * Gets a string describing the amount of time the server has been online.
     *
     * @return The server's uptime.
     */
    public String getUptime() {
        return this.uptime;
    }
}
