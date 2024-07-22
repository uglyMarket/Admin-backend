package com.sparta.uglymarket;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;


// @SpringBootTest 어노테이션을 사용하여 애플리케이션 컨텍스트를 로드할 때
// SecurityConfig 클래스의 설정이 자동으로 포함되기 때문에 SecurityConfigTest를 따로
// 작성하지 않아도 간접적으로 테스트받고 있어서 따로 작성하지 않아도 되긴 하다.
@SpringBootTest
class UglyMarketApplicationTests {

    @Test
    void contextLoads() {
    }

}
