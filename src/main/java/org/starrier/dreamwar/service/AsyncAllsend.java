package org.starrier.dreamwar.service;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * @author Starrier
 * @date 2018/12/15.
 */
@Component
public class AsyncAllsend {

    @Async
    public void sendInfo(String message)throws IOException {
        for (MyWebSocket item : MyWebSocket.webSocketSet) {
            try {
                item.sendMessage(message);

            } catch (IOException e) {

                continue;
            }
        }
    }
}
