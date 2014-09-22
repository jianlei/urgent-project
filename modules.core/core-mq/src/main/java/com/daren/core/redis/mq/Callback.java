package com.daren.core.redis.mq;

public interface Callback {
    public void onMessage(String message);
}
