package com.sparta.uglymarket.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.AuthenticationException;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

public class CustomAuthenticationFailureHandlerTest {

    @InjectMocks
    private CustomAuthenticationFailureHandler customAuthenticationFailureHandler;

    @Mock
    private ObjectMapper objectMapper;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        customAuthenticationFailureHandler = new CustomAuthenticationFailureHandler(objectMapper);
    }
    @Test
    public void testOnAuthenticationFailure() throws Exception {
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        AuthenticationException exception = mock(AuthenticationException.class);

        StringWriter stringWriter = new StringWriter();
        PrintWriter writer = new PrintWriter(stringWriter);

        when(response.getWriter()).thenReturn(writer);
        when(exception.getMessage()).thenReturn("Invalid credentials");

        Map<String, String> data = new HashMap<>();
        data.put("status", "error");
        data.put("message", "Invalid credentials");
        String jsonResponse = new ObjectMapper().writeValueAsString(data);
        when(objectMapper.writeValueAsString(any(Map.class))).thenReturn(jsonResponse);

        customAuthenticationFailureHandler.onAuthenticationFailure(request, response, exception);

        verify(response).setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        verify(response).setContentType("application/json");
        writer.flush();

        String responseContent = stringWriter.toString();
        assertEquals(jsonResponse, responseContent);
    }
}
