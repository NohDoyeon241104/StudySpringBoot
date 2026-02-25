package com.demo;

import static io.u2ware.common.docs.MockMvcRestDocs.*;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

// 실제 코드보다 테스트 코드를 더 중점적으로 
// Test Driven Design
import com.demo.helloes.Hello;
import com.demo.helloes.HelloRepository;


// Test만 실행할 때 터미널 명령어 
// ./mvnw test              # 테스트만 실행

// @TestMethodOrder(MethodOrderer.OrderAnnotation.class) : 테스트 메소드의 실행 순서를 지정하는 어노테이션
// @TestMethodOrder는 메서드 단위 순서를 지정하는 것입니다. 즉 contextLoads1(), contextLoads2(), contextLoads3() 이 세 개의 @Test 메서드들 간의 실행 순서를 정하는 것입니다.
@SpringBootTest
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation.class) 			// 내부의 @Test 메소드 밑에 @Order 어노테이션을 사용하여 실행 순서를 지정할 수 있습니다. 숫자가 낮을수록 먼저 실행됩니다.
class DemoApplicationTests {

	@Autowired
	private HelloRepository helloRepository;

	@Autowired
	private MockMvc mockMvc;

	// Repository Test
	@Test
	@Order(1) // @Order 어노테이션을 사용하여 테스트 메소드의 실행 순서를 지정할 수 있습니다. 숫자가 낮을수록 먼저 실행됩니다.
	void contextLoads1() throws Exception {

		Hello hello = new Hello();
		hello.setMessage("유비샘");
		hello.setEmail("ubisam@ubisam.com");

		helloRepository.save(hello);
	}

	// 웹 요청 Test
	// 숙제 : CRUD 테스트
	@Test
	@Order(2) // @Order 어노테이션을 사용하여 테스트 메소드의 실행 순서를 지정할 수 있습니다. 숫자가 낮을수록 먼저 실행됩니다.
	void contextLoads2() throws Exception {

		// save
		Hello hello = new Hello();
		hello.setMessage("유비샘12341234");
		hello.setEmail("ubisam1234@ubisam.com");

		// Request Body Post
		mockMvc.perform(post("/helloes").content(hello)).andDo(print()).andExpect(is2xx());

		// perform() : 요청 구간
		// andDo() : 결과가 나오고 나서 뭔가를 하고 싶을 때
		// andDo(print()) : 결과 출력
		// andExpect() : 결과를 기대
		// andExpect(is2xx()) : 2xx대면 성공함
		mockMvc.perform(get("/helloes")).andDo(print()).andExpect(is2xx());

	}

	// 웹 요청 Test 
	// CRUD 테스트
	@Test
	@Order(3) // @Order 어노테이션을 사용하여 테스트 메소드의 실행 순서를 지정할 수 있습니다. 숫자가 낮을수록 먼저 실행됩니다.
	void contextLoads3() throws Exception {

		//CRUD 테스트는 순서가 중요하다. Create 생성 -> Read 조회 -> Update 수정 -> Delete 삭제
		//위와 같은 순서대로 해야 데이터가 존재하는 상태에서 테스트가 가능하다.

		// === Create === post 요청으로 생성
		Hello hello = new Hello();
		hello.setMessage("CRUD 테스트");
		hello.setEmail("crud@ubisam.com");

		mockMvc.perform(post("/helloes").content(hello)).andDo(print()).andExpect(is2xx());

		// === Read === get 요청으로 조회 (전체 조회)
		mockMvc.perform(get("/helloes")).andDo(print()).andExpect(is2xx());

		
		// === Read === get 요청으로 조회 (단건  인덱스 1 조회)
		mockMvc.perform(get("/helloes/1")).andDo(print()).andExpect(is2xx());

		// === Update === put 요청으로 수정
		Hello updated = new Hello();
		updated.setMessage("수정된 메시지");
		updated.setEmail("updated@ubisam.com");

		mockMvc.perform(put("/helloes/1").content(updated)).andDo(print()).andExpect(is2xx());

		// === Delete === delete 요청으로 삭제
		mockMvc.perform(delete("/helloes/1")).andDo(print()).andExpect(is2xx());
	}

}
