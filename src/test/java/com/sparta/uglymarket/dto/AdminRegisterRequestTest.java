//package com.sparta.uglymarket.dto;
//
//import jakarta.validation.ConstraintViolation;
//import jakarta.validation.Validation;
//import jakarta.validation.Validator;
//import jakarta.validation.ValidatorFactory;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//
//import java.lang.reflect.Field;
//import java.util.Set;
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.junit.jupiter.api.Assertions.assertTrue;
//
//public class AdminRegisterRequestTest {
//
//    private Validator validator;
//
//    @BeforeEach
//    public void setUp() {
//        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
//        validator = factory.getValidator();
//    }
//
//    @Test
//    void testValidAdminRegisterRequest() throws NoSuchFieldException, IllegalAccessException {
//        // given
//        AdminRegisterRequest request = new AdminRegisterRequest();
//        setFieldValue(request, "farmName", "Happy Farm");
//        setFieldValue(request, "introMessage", "Welcome to Happy Farm!");
//        setFieldValue(request, "profileImageUrl", "http://example.com/profile.jpg");
//        setFieldValue(request, "phoneNumber", "010-1234-5678");
//        setFieldValue(request, "leaderName", "John Doe");
//        setFieldValue(request, "businessId", "123-45-67890");
//        setFieldValue(request, "openingDate", "2021-01-01");
//        setFieldValue(request, "minOrderAmount", 10000L);
//        setFieldValue(request, "password", "password123");
//
//        // when
//        Set<ConstraintViolation<AdminRegisterRequest>> violations = validator.validate(request);
//
//        // then
//        assertTrue(violations.isEmpty());
//    }
//
//    @Test
//    void testInvalidAdminRegisterRequest() throws NoSuchFieldException, IllegalAccessException {
//        // given
//        AdminRegisterRequest request = new AdminRegisterRequest();
//        // 일부 필드를 비워둬서 유효성 검사에 실패하도록 설정
//        setFieldValue(request, "farmName", "");
//        setFieldValue(request, "introMessage", "");
//        setFieldValue(request, "profileImageUrl", "");
//        setFieldValue(request, "phoneNumber", "123-456-789");
//        setFieldValue(request, "leaderName", "");
//        setFieldValue(request, "businessId", "");
//        setFieldValue(request, "openingDate", "");
//        setFieldValue(request, "minOrderAmount", null);
//        setFieldValue(request, "password", "short");
//
//        // when
//        Set<ConstraintViolation<AdminRegisterRequest>> violations = validator.validate(request);
//
//        // then
//        assertEquals(9, violations.size());
//    }
//
//    private void setFieldValue(Object object, String fieldName, Object value) throws NoSuchFieldException, IllegalAccessException {
//        Field field = object.getClass().getDeclaredField(fieldName);
//        field.setAccessible(true);
//        field.set(object, value);
//    }
//}
