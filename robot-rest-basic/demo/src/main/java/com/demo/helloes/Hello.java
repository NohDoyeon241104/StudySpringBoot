package com.demo.helloes;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "example_hello") // 실제 물리 DB의 테이블명
public class Hello {

    @Id
    @GeneratedValue
    private Long id;
    private String name;
    private String message;
    private String email;

}
