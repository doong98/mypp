package com.example.myPortfolio;

import com.example.myPortfolio.board.Board;
import com.example.myPortfolio.repository.BoardRepository;
import com.example.myPortfolio.repository.UserRepository;
import com.example.myPortfolio.service.BoardService;
import com.example.myPortfolio.user.SiteUser;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.query.FluentQuery;
import org.springframework.security.test.context.support.WithMockUser;

import java.security.Principal;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;

@SpringBootTest
class MyPortfolioApplicationTests {
	@Autowired
	private BoardRepository boardRepository;
	@Autowired
	private BoardService boardService;
	@Autowired
	private UserRepository userRepository;

    @Test
	void contextLoads() {
	}
	@Test
	void testJpa(){
		Board b1 = new Board();
		b1.setSubject("만나서 방가워요!");
		b1.setContent("같이 공부해요~");
		this.boardRepository.save(b1);
	}
	@Test
	@WithMockUser(username = "doong98")
	void testJpa2() {
		String userId = "doong98";
		Optional<SiteUser> author = this.userRepository.findByUsername(userId);
		if (author.isPresent()) {
			for (int i = 1; i <= 300; i++) {
				String subject = String.format("테스트 데이터:[%03d]", i);
				String content = String.format("test data:[%03d]", i);
				this.boardService.create(subject, content, author.get());
			}
		} else {
			System.out.println("User not found!");
		}
	}
}
