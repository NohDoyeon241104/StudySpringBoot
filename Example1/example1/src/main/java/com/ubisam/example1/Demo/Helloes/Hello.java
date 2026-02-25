package com.ubisam.example1.Demo.Helloes;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Data;

@Entity
@Data
public class Hello {
        @Id
        @GeneratedValue
        private Long id;
        private String name; 
        private String email;
}
