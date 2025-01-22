package com.fra.controller;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import com.fra.service.TestService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;

@Slf4j
@RestController
@RequiredArgsConstructor
public class TestController {

    private final TestService testService;

    @GetMapping("/test")
    public String test() {
        testService.test();
        return "";
    }

    @GetMapping("/test2")
    public String test2(@RequestBody BodyDto bodyDto) {
        return "Received month: " + bodyDto.getMonth();
    }

    @Getter
    static public class BodyDto {
        private Month month;
    }

    @Getter
    enum Month {
        ONE_MONTH("1"),
        TWO_MONTH("2"),
        RANDOM("99");

        private final String value;

        Month(String value) {
            this.value = value;
        }

        // Object -> Json 직렬화 시, 해당 값을 사용
        @JsonValue
        public String getValue() {
            return value;
        }

        // Json -> Object 역직렬화 시, 해당값을 기반으로 객체 매핑
        @JsonCreator
        public static Month fromValue(String value) {
            return Arrays.stream(Month.values())
                    .filter(month -> month.value.equals(value))
                    .findFirst()
                    .orElse(RANDOM); // 기본값을 설정
        }

    }

    @GetMapping("/test3")
    public String test3(@RequestParam String Test) {
        return Test;
    }

}
