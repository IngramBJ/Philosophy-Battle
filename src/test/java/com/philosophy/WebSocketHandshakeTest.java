package com.philosophy;


import com.philosophy.websocket.WebSocketHandshakeInterceptor;


import org.junit.jupiter.api.Test;


import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.socket.WebSocketHandler;


import java.net.URI;
import java.util.HashMap;


import static org.junit.jupiter.api.Assertions.*;

import static org.mockito.Mockito.*;



public class WebSocketHandshakeTest {



    @Test
    public void handshakeShouldExtractPlayerInfo()
            throws Exception{


        WebSocketHandshakeInterceptor interceptor =
                new WebSocketHandshakeInterceptor();



        ServerHttpRequest request =
                mock(ServerHttpRequest.class);



        when(request.getURI())
                .thenReturn(
                        new URI(
                                "ws://localhost/ws?roomId=test&playerId=1"
                        )
                );



        boolean result =
                interceptor.beforeHandshake(
                        request,
                        mock(ServerHttpResponse.class),
                        mock(WebSocketHandler.class),
                        new HashMap<>()
                );



        assertTrue(result);

    }


}