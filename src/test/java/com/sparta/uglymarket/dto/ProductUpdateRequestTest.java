package com.sparta.uglymarket.dto;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class ProductUpdateRequestTest {

    @Test
    void testDefaultConstructorAndGetters() {
        // given
        ProductUpdateRequest request = new ProductUpdateRequest();

        // then
        assertNull(request.getTitle());
        assertNull(request.getContent());
        assertNull(request.getPrice());
        assertNull(request.getStock());
        assertNull(request.getImageUrl());
        assertNull(request.getCategory());
    }
}
