package com.example.todoapp.helloes;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController

public class HelloController {

    private final HelloRepository helloRepository;

    public HelloController(HelloRepository helloRepository) {
        this.helloRepository = helloRepository;
    }

    // 1. 메인 화면 접속
    @GetMapping("/")
    public String index() {
        return "index";
    }

    // 2. 할 일 목록 조회 (JSON 반환)
    @GetMapping("/api/todos")
    @ResponseBody
    public List<Hello> getTodos() {
        return helloRepository.findAll();
    }

    // 3. 할 일 추가 저장
    @PostMapping("/api/todos")
    @ResponseBody
    public Hello saveTodo(@RequestBody Hello hello) {
        return helloRepository.save(hello);
    }
}