package com.ubisam.example1.Demo.Helloes;

import org.springframework.data.rest.core.annotation.RepositoryRestResource;

// JpaRepository <Entity, Primary Key Type> 지정 이유: Hello 엔티티를 Long 타입의 기본 키로 관리하기 위해
// # JpaRepository를 상속받아 HelloRepository 인터페이스 정의
// # CoC 원칙에 따라 구현체는 Spring Data JPA가 자동으로 생성
// # CoC 원어: Convention over Configuration 뜻 : 관례에 따른 설정 우선
// # Hello 엔티티에 대한 CRUD 및 페이징, 정렬 기능 제공
// # 별도의 구현 클래스 없이도 데이터 액세스 계층 구성 가능
// # Spring Data JPA의 강력한 기능 활용 가능
// #JpaRepository를 상속받는 것만으로도 저장, 조회, 삭제 기능이 자동으로 제공됨

@RepositoryRestResource(path = "hellos")    // RESTful API 경로를 사용자가 직접 "hellos"로 지정 
public interface HelloRepository extends org.springframework.data.jpa.repository.JpaRepository<Hello, Long> {
    
}
