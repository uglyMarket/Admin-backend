package com.sparta.uglymarket.healthCheck;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;
import java.util.TreeMap;

public class HealthCheckController {
    @RestController
    public class HeathCheckController {
        @Value(value = "${server.env}")
        private String env;
        @Value(value = "${server.port}")
        private String serverPort;
        @Value(value = "${server.serveraddress}")
        private String serverAddress;
        @Value(value = "${servername}")
        private String serverName;

        //현재 동작중인 서버 확인(blue, green)
        //http://"serverIP"/hc
        @GetMapping("/hc")
        public ResponseEntity<?> healthCheck() {
            Map<String, String> responeseData = new TreeMap<>();
            responeseData.put("serverName", serverName);
            responeseData.put("serverAddress", serverAddress);
            responeseData.put("serverPort", serverPort);
            responeseData.put("env", env);
            return ResponseEntity.ok(responeseData);
        }

        @GetMapping("/env")
        public ResponseEntity<?> getEnv() {
            return ResponseEntity.ok(env);
        }
    }
}
