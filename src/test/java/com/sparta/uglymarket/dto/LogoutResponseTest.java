//package com.sparta.uglymarket.dto;
//
//import org.junit.jupiter.api.Test;
//import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.junit.jupiter.api.Assertions.assertNotNull;
//
//public class LogoutResponseTest {
//
//    @Test
//    void testLogoutResponseConstructorAndGetters() {
//        // given
//        String expectedMessage = "Logout successful";
//
//        // when
//        LogoutResponse response = new LogoutResponse(expectedMessage);
//
//        // then
//        assertNotNull(response);
//        assertEquals(expectedMessage, response.getMessage());
//    }
//
//    @Test
//    void testLogoutResponseNoArgsConstructor() {
//        // when
//        LogoutResponse response = new LogoutResponse();
//
//        // then
//        assertNotNull(response);
//        assertEquals(null, response.getMessage()); // No-args constructor should initialize message to null
//    }
//}
