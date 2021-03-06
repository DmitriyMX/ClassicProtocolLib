package com.github.steveice10.mc.classic.protocol.test;

import com.github.steveice10.mc.classic.protocol.ClassicConstants;
import com.github.steveice10.mc.classic.protocol.ClassicProtocol;
import com.github.steveice10.mc.classic.protocol.VerifyUsersListener;
import com.github.steveice10.mc.classic.protocol.data.game.UserType;
import com.github.steveice10.mc.classic.protocol.data.heartbeat.ServerInfo;
import com.github.steveice10.mc.classic.protocol.data.heartbeat.ServerInfoBuilder;
import com.github.steveice10.mc.classic.protocol.data.serverlist.ServerList;
import com.github.steveice10.mc.classic.protocol.data.serverlist.ServerURLInfo;
import com.github.steveice10.mc.classic.protocol.exception.AuthenticationException;
import com.github.steveice10.mc.classic.protocol.packet.client.ClientChatPacket;
import com.github.steveice10.mc.classic.protocol.packet.client.ClientIdentificationPacket;
import com.github.steveice10.mc.classic.protocol.packet.server.ServerChatPacket;
import com.github.steveice10.mc.classic.protocol.packet.server.ServerIdentificationPacket;
import com.github.steveice10.mc.classic.protocol.packet.server.ServerLevelFinalizePacket;
import com.github.steveice10.packetlib.Client;
import com.github.steveice10.packetlib.Server;
import com.github.steveice10.packetlib.event.server.ServerAdapter;
import com.github.steveice10.packetlib.event.server.SessionAddedEvent;
import com.github.steveice10.packetlib.event.server.SessionRemovedEvent;
import com.github.steveice10.packetlib.event.session.DisconnectedEvent;
import com.github.steveice10.packetlib.event.session.PacketReceivedEvent;
import com.github.steveice10.packetlib.event.session.SessionAdapter;
import com.github.steveice10.packetlib.tcp.TcpSessionFactory;

public class MinecraftClassicProtocolTest {
    private static final boolean SPAWN_SERVER = true;
    private static final boolean VERIFY_USERS = false;
    private static final String SERVER_HOST = "127.0.0.1";
    private static final int SERVER_PORT = 25565;
    private static final String USERNAME = "Username";
    private static final String PASSWORD = "Password";

    private static String serverUrl = "URL";

    public static void main(String[] args) {
        if(SPAWN_SERVER) {
            Server server = new Server(SERVER_HOST, SERVER_PORT, ClassicProtocol.class, new TcpSessionFactory());
            if(VERIFY_USERS) {
                server.addListener(new VerifyUsersListener());
                server.setGlobalFlag(ClassicConstants.SERVER_INFO_BUILDER_KEY, new ServerInfoBuilder() {
                    @Override
                    public ServerInfo build(Server server) {
                        return new ServerInfo("Test Server", server.getPort(), true, 0, 20);
                    }
                });
            }

            server.addListener(new ServerAdapter() {
                @Override
                public void sessionAdded(SessionAddedEvent event) {
                    event.getSession().addListener(new SessionAdapter() {
                        @Override
                        public void packetReceived(PacketReceivedEvent event) {
                            if(event.getPacket() instanceof ClientIdentificationPacket) {
                                event.getSession().send(new ServerIdentificationPacket("Test Server", "A test of a server thing.", UserType.NOT_OP));

                                // Only useful for our test; triggers the message.
                                event.getSession().send(new ServerLevelFinalizePacket(128, 128, 128));
                            } else if(event.getPacket() instanceof ClientChatPacket) {
                                ClientChatPacket packet = event.getPacket();
                                String username = event.getSession().getFlag(ClassicConstants.USERNAME_KEY);
                                System.out.println("[SERVER] " + username + ": " + packet.getMessage());
                                event.getSession().send(new ServerChatPacket(0, username + ": " + packet.getMessage()));
                            }
                        }
                    });
                }

                @Override
                public void sessionRemoved(SessionRemovedEvent event) {
                    System.out.println("Closing server.");
                    event.getServer().close();
                }
            });

            server.bind();
            if(VERIFY_USERS) {
                while(!server.hasGlobalFlag(ClassicConstants.SERVER_URL_KEY)) {
                    try {
                        Thread.sleep(250);
                    } catch(InterruptedException e) {
                    }

                    serverUrl = server.getGlobalFlag(ClassicConstants.SERVER_URL_KEY);
                }
            }
        }

        login();
    }

    private static void login() {
        ClassicProtocol protocol;
        String host = SERVER_HOST;
        int port = SERVER_PORT;
        if(VERIFY_USERS) {
            try {
                ServerList.login(USERNAME, PASSWORD);
                System.out.println("Successfully authenticated user.");
            } catch(AuthenticationException e) {
                System.err.println("Failed to authenticate user.");
                e.printStackTrace();
            }

            ServerURLInfo info = ServerList.getServerURLInfo(serverUrl);
            host = info.getHost();
            port = info.getPort();
            protocol = new ClassicProtocol(info);
        } else {
            protocol = new ClassicProtocol(USERNAME.substring(0, USERNAME.contains("@") ? USERNAME.indexOf("@") : USERNAME.length()));
        }

        Client client = new Client(host, port, protocol, new TcpSessionFactory());
        client.getSession().addListener(new SessionAdapter() {
            @Override
            public void packetReceived(PacketReceivedEvent event) {
                if(event.getPacket() instanceof ServerLevelFinalizePacket) {
                    event.getSession().send(new ClientChatPacket("Hello, this is a test of ClassicProtocolLib."));
                } else if(event.getPacket() instanceof ServerChatPacket) {
                    System.out.println("[CLIENT] " + event.<ServerChatPacket>getPacket().getMessage());
                    if(event.<ServerChatPacket>getPacket().getMessage().contains("Hello, this is a test of ClassicProtocolLib.")) {
                        event.getSession().disconnect("Finished");
                    }
                }
            }

            @Override
            public void disconnected(DisconnectedEvent event) {
                System.out.println("Disconnected: " + event.getReason());
            }
        });

        client.getSession().connect(true);
        while(client.getSession().isConnected()) {
            try {
                Thread.sleep(5);
            } catch(InterruptedException e) {
                break;
            }
        }
    }
}
