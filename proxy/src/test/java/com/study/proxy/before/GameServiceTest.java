package com.study.proxy.before;

import org.junit.jupiter.api.Test;

public class GameServiceTest {

    @Test
    void test() throws InterruptedException {
        GameService gameService = new GameService();
        gameService.startGame();
    }
}
