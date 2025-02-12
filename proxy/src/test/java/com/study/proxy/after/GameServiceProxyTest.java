package com.study.proxy.after;

import org.junit.jupiter.api.Test;

public class GameServiceProxyTest {

    @Test
    void test() {
        GameService gameService = new GameServiceProxy();
        gameService.startGame();
    }
}
