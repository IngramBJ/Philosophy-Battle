package com.philosophy.config;


import com.philosophy.websocket.WebSocketHandshakeInterceptor;


import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.*;



@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig
        implements WebSocketMessageBrokerConfigurer {



    private final WebSocketHandshakeInterceptor interceptor;



    public WebSocketConfig(
            WebSocketHandshakeInterceptor interceptor
    ){

        this.interceptor = interceptor;

    }






    @Override
    public void configureMessageBroker(
            MessageBrokerRegistry registry
    ){


        registry.enableSimpleBroker(
                "/topic"
        );


        registry.setApplicationDestinationPrefixes(
                "/app"
        );


    }








    @Override
    public void registerStompEndpoints(
            StompEndpointRegistry registry
    ){


        registry.addEndpoint(
                "/ws"
        )
        .addInterceptors(
                interceptor
        )
        .setAllowedOriginPatterns("*");


    }



}