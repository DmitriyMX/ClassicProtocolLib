package org.spacehq.mc.classic.protocol;

import org.spacehq.mc.classic.protocol.data.heartbeat.ServerInfo;
import org.spacehq.mc.classic.protocol.data.heartbeat.ServerInfoBuilder;
import org.spacehq.packetlib.Server;
import org.spacehq.packetlib.event.server.ServerAdapter;
import org.spacehq.packetlib.event.server.ServerBoundEvent;
import org.spacehq.packetlib.event.server.ServerClosingEvent;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.security.SecureRandom;

/**
 * Listener used on Minecraft Classic servers for verifying users and sending heartbeats to the Minecraft Classic server list.
 */
public class VerifyUsersListener extends ServerAdapter {
    private Heartbeat heartbeat;

    @Override
    public void serverBound(ServerBoundEvent event) {
        event.getServer().setGlobalFlag(ClassicConstants.SALT_KEY, String.valueOf(new SecureRandom().nextLong()));
        this.heartbeat = new Heartbeat(event.getServer());
        new Thread(this.heartbeat, "HeartbeatThread").start();
    }

    @Override
    public void serverClosing(ServerClosingEvent event) {
        if(this.heartbeat != null) {
            this.heartbeat.stopBeating();
            this.heartbeat = null;
        }
    }

    private static class Heartbeat implements Runnable {
        private Server server;

        private boolean beating = true;

        public Heartbeat(Server server) {
            this.server = server;
        }

        public void stopBeating() {
            this.beating = false;
        }

        @Override
        public void run() {
            while(this.beating) {
                if(this.server.hasGlobalFlag(ClassicConstants.SERVER_INFO_BUILDER_KEY)) {
                    BufferedReader reader = null;
                    try {
                        ServerInfo info = this.server.<ServerInfoBuilder>getGlobalFlag(ClassicConstants.SERVER_INFO_BUILDER_KEY).build(this.server);
                        StringBuilder build = new StringBuilder("https://minecraft.net/heartbeat.jsp?");
                        build.append("name=").append(URLEncoder.encode(info.getName(), "UTF-8")).append("&");
                        build.append("port=").append(URLEncoder.encode(String.valueOf(info.getPort()), "UTF-8")).append("&");
                        build.append("max=").append(URLEncoder.encode(String.valueOf(info.getMaxPlayers()), "UTF-8")).append("&");
                        build.append("public=").append(URLEncoder.encode(String.valueOf(info.isPublic()), "UTF-8")).append("&");
                        build.append("version=").append(URLEncoder.encode(String.valueOf(ClassicConstants.PROTOCOL_VERSION), "UTF-8")).append("&");
                        build.append("salt=").append(URLEncoder.encode(this.server.<String>getGlobalFlag(ClassicConstants.SALT_KEY), "UTF-8")).append("&");
                        build.append("users=").append(URLEncoder.encode(String.valueOf(info.getPlayers()), "UTF-8"));
                        reader = new BufferedReader(new InputStreamReader(new URL(build.toString()).openStream()));
                        String resp = reader.readLine();
                        try {
                            new URL(resp);
                            this.server.setGlobalFlag(ClassicConstants.SERVER_URL_KEY, resp);
                        } catch(MalformedURLException e) {
                            System.err.println("Error while performing minecraft.net heartbeat: \"" + resp + "\"");
                        }
                    } catch(Exception e) {
                        System.err.println("Failed to perform minecraft.net heartbeat.");
                        e.printStackTrace();
                    } finally {
                        if(reader != null) {
                            try {
                                reader.close();
                            } catch(IOException e) {
                            }
                        }
                    }
                }

                try {
                    Thread.sleep(45000);
                } catch(InterruptedException e) {
                    break;
                }
            }
        }
    }
}
