package com.pluxurydolo.threads.flow;

import com.pluxurydolo.threads.properties.ThreadsProperties;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ThreadsAuthorizationCodeFlowTests {

    @Mock
    private ThreadsProperties threadsProperties;

    @InjectMocks
    private ThreadsAuthorizationCodeFlow threadsAuthorizationCodeFlow;

    @Test
    void testGetAuthorizationUrl() {
        when(threadsProperties.appId())
            .thenReturn("appId");
        when(threadsProperties.redirectUri())
            .thenReturn("redirectUri");

        String result = threadsAuthorizationCodeFlow.getAuthorizationUrl();

        assertThat(result)
            .isEqualTo("https://threads.net/oauth/authorize?client_id=appId&redirect_uri=redirectUri&scope=threads_basic,threads_content_publish&response_type=code");
    }
}
