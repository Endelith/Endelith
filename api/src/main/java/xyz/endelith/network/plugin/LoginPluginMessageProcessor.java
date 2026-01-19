package xyz.endelith.network.plugin;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

public interface LoginPluginMessageProcessor { 
    CompletableFuture<LoginPlugin.Response> request(String channel, byte[] requestPayload);
    void handleResponse(int messageId, byte[] responseData) throws Exception;
    void awaitReplies(long timeout, TimeUnit timeUnit) throws Exception;
}
