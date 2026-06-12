package com.pluxurydolo.threads.flow.oauth;

import com.pluxurydolo.threads.properties.ThreadsAuthProperties;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpHeaders;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.web.server.ServerWebExchange;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ThreadsAuthorizationCodeFlowTests {

    @Mock
    private ThreadsAuthProperties threadsAuthProperties;

    @Mock
    private ServerWebExchange serverWebExchange;

    @Mock
    private ServerHttpResponse serverHttpResponse;

    @Mock
    private HttpHeaders httpHeaders;

    @InjectMocks
    private ThreadsAuthorizationCodeFlow threadsAuthorizationCodeFlow;

    @BeforeEach
    void setUp() {
        when(threadsAuthProperties.appId())
            .thenReturn("appId");
        when(threadsAuthProperties.redirectUri())
            .thenReturn("redirectUri");
    }

    @Test
    void testGetResponse() {
        doNothing()
            .when(httpHeaders).setLocation(any());
        when(serverWebExchange.getResponse())
            .thenReturn(serverHttpResponse);
        when(serverHttpResponse.setStatusCode(any()))
            .thenReturn(true);
        when(serverHttpResponse.getHeaders())
            .thenReturn(httpHeaders);

        ServerHttpResponse result = threadsAuthorizationCodeFlow.getResponse(serverWebExchange);

        assertThat(result)
            .isEqualTo(serverHttpResponse);
    }

    @Test
    void testGetResponseWhenExceptionOccurred() {
        doThrow(RuntimeException.class)
            .when(serverWebExchange).getResponse();

        assertThrows(
            RuntimeException.class,
            () -> threadsAuthorizationCodeFlow.getResponse(serverWebExchange)
        );
    }
}
