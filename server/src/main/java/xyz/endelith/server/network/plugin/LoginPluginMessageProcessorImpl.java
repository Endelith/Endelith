package xyz.endelith.server.network.plugin;

import java.util.Map;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

import xyz.endelith.network.plugin.LoginPlugin;
import xyz.endelith.network.plugin.LoginPlugin.Response;
import xyz.endelith.network.plugin.LoginPluginMessageProcessor;
import xyz.endelith.server.network.PlayerConnectionImpl;
import xyz.endelith.server.network.packet.server.login.ServerLoginPluginRequestPacket;

public final class LoginPluginMessageProcessorImpl implements LoginPluginMessageProcessor {
    private static final AtomicInteger REQUEST_ID = new AtomicInteger(0);

    private final Map<Integer, LoginPlugin.Request> requestByMsgId = new ConcurrentHashMap<>();
    private final PlayerConnectionImpl connection;

    public LoginPluginMessageProcessorImpl(PlayerConnectionImpl connection) {
        this.connection = Objects.requireNonNull(connection, "connection");
    }

    @Override
    public CompletableFuture<Response> request(String channel, byte[] requestPayload) {
        LoginPlugin.Request request = new LoginPlugin.Request(channel, requestPayload);

        int messageId = nextMessageId();
        requestByMsgId.put(messageId, request);
        connection.sendPacket(new ServerLoginPluginRequestPacket(messageId, request.channel(), request.payload()));

        return request.responseFuture();
    }

    @Override
    public void handleResponse(int messageId, byte[] responseData) throws Exception {
        LoginPlugin.Request request = requestByMsgId.remove(messageId);
        if (request == null) {
            throw new Exception("Received unexpected Login Plugin Response id " + messageId + " of " + responseData.length + " bytes");
        }

        try {
            LoginPlugin.Response response = new LoginPlugin.Response(request.channel(), responseData);
            request.responseFuture().complete(response);
        } catch (Throwable t) {
            throw new Exception("Error handling Login Plugin Response on channel '" + request.channel() + "'", t);
        }
    }

    @Override
    public void awaitReplies(long timeout, TimeUnit timeUnit) throws Exception {
        if (requestByMsgId.isEmpty()) {
            return;
        }
        var futures = requestByMsgId.values().stream()
                .map(LoginPlugin.Request::responseFuture)
                .toArray(CompletableFuture[]::new);
        CompletableFuture.allOf(futures).get(timeout, timeUnit);
    }

    private static int nextMessageId() {
        return REQUEST_ID.getAndIncrement();
    }
}
