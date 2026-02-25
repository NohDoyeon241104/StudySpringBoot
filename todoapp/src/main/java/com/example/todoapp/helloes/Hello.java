package com.example.todoapp.helloes;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor  
public class Hello {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 할 일 내용 (필수)
    @Column(nullable = false)
    private String title;

    // 완료 여부 (기본값 false)
    private boolean completed = false;

    // 생성 시간 (자동 기록)
    @CreationTimestamp
    private LocalDateTime createdAt;

    // 마감 기한
    private LocalDateTime dueDate;

    // 우선순위
    private Integer priority;
}