package org.spacehq.mc.classic.protocol.data.serverlist;

import org.spacehq.mc.classic.protocol.exception.AuthenticationException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.CookieHandler;
import java.net.CookieManager;
import java.net.CookiePolicy;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

/**
 * Contains methods for communicating with the Minecraft Classic server list.
 */
public class ServerList {
    static {
        CookieHandler.setDefault(new CookieManager(null, CookiePolicy.ACCEPT_ALL));
    }

    /**
     * Logs in to minecraft.net.
     *
     * @param username Username to log in with.
     * @param password Password to log in with.
     * @throws AuthenticationException If an error occurs while authenticating with minecraft.net.
     */
    public static void login(String username, String password) throws AuthenticationException {
        String result;
        try {
            result = fetchUrl("https://minecraft.net/login", "username=" + URLEncoder.encode(username, "UTF-8") + "&password=" + URLEncoder.encode(password, "UTF-8"));
        } catch(UnsupportedEncodingException e) {
            throw new AuthenticationException("UTF-8 encoding not supported.");
        }

        if(!result.contains("Logged in as")) {
            throw new AuthenticationException("Login attempt unsuccessful. Response: \n" + result);
        }
    }

    /**
     * Gets a map of servers' names to their list information from the server list.
     *
     * @return A map of servers' names and list information.
     */
    public static Map<String, ServerListInfo> getServers() {
        Map<String, ServerListInfo> servers = new HashMap<String, ServerListInfo>();
        String data = fetchUrl("https://minecraft.net/classic/list", "");
        int index = data.indexOf("<a href=\"");
        while((index = data.indexOf("classic/play/", index)) != -1) {
            String id = data.substring(index + 13, data.indexOf("\"", index));
            index = data.indexOf(">", index) + 1;
            String name = data.substring(index, data.indexOf("</a>", index)).replaceAll("&amp;", "&").replaceAll("&hellip;", "...");
            index = data.indexOf("<td>", index) + 4;
            String users = data.substring(index, data.indexOf("</td>", index));
            index = data.indexOf("<td>", index) + 4;
            String max = data.substring(index, data.indexOf("</td>", index));
            index = data.indexOf("<td>", index) + 4;
            String uptime = data.substring(index, data.indexOf("</td>", index));
            servers.put(name, new ServerListInfo(name, "https://minecraft.net/classic/play/" + id, Integer.valueOf(users), Integer.valueOf(max), uptime));
        }

        return servers;
    }

    /**
     * Gets a server's list information from the server list.
     *
     * @param name Name of the server.
     * @return The server's information.
     */
    public static ServerListInfo getServerListInfo(String name) {
        return getServers().get(name);
    }

    /**
     * Gets a server's URL information from the server list.
     *
     * @param url URL of the server.
     * @return The server's URL information.
     */
    public static ServerURLInfo getServerURLInfo(String url) {
        String play = fetchUrl(url, "");
        String mppass = getAppletParameter(play, "mppass");
        if(!mppass.isEmpty()) {
            String username = getAppletParameter(play, "username");
            String server = getAppletParameter(play, "server");
            int port = 25565;
            try {
                port = Integer.parseInt(getAppletParameter(play, "port"));
            } catch(NumberFormatException e) {
            }

            return new ServerURLInfo(server, port, username, mppass);
        }

        return null;
    }

    private static String fetchUrl(String url, String params) {
        BufferedReader reader = null;
        try {
            URLConnection conn = new URL(url).openConnection();
            conn.setReadTimeout(30000);
            conn.setConnectTimeout(10000);
            conn.setDoInput(true);
            if(!params.isEmpty()) {
                conn.addRequestProperty("Content-Type", "application/x-www-form-urlencoded");
                conn.addRequestProperty("Content-Length", Integer.toString(params.length()));
                conn.setDoOutput(true);
                OutputStream out = null;
                try {
                    out = conn.getOutputStream();
                    out.write(params.getBytes("UTF-8"));
                    out.flush();
                    out.close();
                } finally {
                    try {
                        out.close();
                    } catch(IOException e) {
                    }
                }
            }

            reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            StringBuilder build = new StringBuilder();
            String line;
            while((line = reader.readLine()) != null) {
                build.append(line);
                build.append("\n");
            }

            return build.toString();
        } catch(IOException e) {
            e.printStackTrace();
        } finally {
            if(reader != null) {
                try {
                    reader.close();
                } catch(IOException e) {
                }
            }
        }

        return "";
    }

    private static String getAppletParameter(String page, String param) {
        String str = "param name=\"" + param + "\" value=\"";
        int index = page.indexOf(str);
        if(index > 0) {
            index += str.length();
            int index2 = page.indexOf("\"", index);
            if(index2 > 0) {
                return page.substring(index, index2);
            }
        }

        return "";
    }
}