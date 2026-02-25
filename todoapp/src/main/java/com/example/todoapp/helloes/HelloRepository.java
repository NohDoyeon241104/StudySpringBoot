package com.example.todoapp.helloes;

import org.springframework.data.jpa.repository.JpaRepository; // JpaRepository 불러오기
import java.util.List; // List 불러오기

public interface HelloRepository extends JpaRepository<Hello, Long> {
    // 완료되지 않은 목록만 조회하는 기능 자동 생성
    List<Hello> findByCompletedFalse();
}