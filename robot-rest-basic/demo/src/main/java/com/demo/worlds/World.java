package com.demo.worlds;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Data;

@Data
@Entity
public class World {

    @Id
    @GeneratedValue
    private Long id;
    private String name;

}
