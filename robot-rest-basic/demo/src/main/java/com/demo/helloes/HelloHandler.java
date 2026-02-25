package com.demo.helloes;

import org.springframework.data.rest.core.annotation.HandleAfterCreate;
import org.springframework.data.rest.core.annotation.HandleBeforeCreate;
import org.springframework.data.rest.core.annotation.RepositoryEventHandler;
import org.springframework.stereotype.Component;

@Component
@RepositoryEventHandler
public class HelloHandler {

    // 중간 과정 catch
    @HandleBeforeCreate
    public void beforeCreate(Hello hello) {

        // // 강제로 데이터 셋팅
        // hello.setEmail("1234");

        // 밑에 처럼 하면 Create 요청 막힘
        // throw new RuntimeException();
    }

    @HandleAfterCreate
    public void afterCreate(Hello hello) {

        System.out.println("111111111111111111111111111111111");

    }

}
