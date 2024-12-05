package com.djccnt15.spring_board;

import com.djccnt15.spring_board.db.repository.AnswerRepository;
import com.djccnt15.spring_board.db.entity.QuestionEntity;
import com.djccnt15.spring_board.db.repository.QuestionRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
class SpringBoardApplicationTests {
	
	@Autowired  // annotation for dependency injection from spring container
	private QuestionRepository questionRepository;
	
	@Autowired
	private AnswerRepository answerRepository;
	
	private final String questionTitle1 = "스프링이 무엇인가요?";
	private final String questionContent1 = "스프링에 대해서 알고 싶습니다.";
	private final String questionTitle2 = "스프링부트 모델 질문입니다.";
	private final String questionContent2 = "id는 자동으로 생성되나요?";
	private final String answerContent = "네 자동으로 생성됩니다.";

	@Test
	void contextLoads() {
	}

	@Test
	@Transactional
	void testJpa() {
		createQuestions();
		
		List<QuestionEntity> all = questionRepository.findAll();
		assertEquals(2, all.size());
		
		Optional<QuestionEntity> question = questionRepository.findById(1L);
		if (question.isPresent()) {
			QuestionEntity q = question.get();
			assertEquals(questionTitle1, q.getSubject());
		}
		
		QuestionEntity q = questionRepository.findBySubject(questionTitle1);
		assertEquals(1, q.getId());
		
		QuestionEntity qFindBySubjectAndContent = questionRepository.findBySubjectAndContent(
			questionTitle1, questionContent1);
		assertEquals(1, qFindBySubjectAndContent.getId());
		
		List<QuestionEntity> qFindBySubjectLike = questionRepository.findBySubjectLike("스프링%");
		QuestionEntity qFindBySubjectLikeFirst = qFindBySubjectLike.get(0);
		assertEquals(questionTitle1, qFindBySubjectLikeFirst.getSubject());
		
		assertTrue(question.isPresent());
		QuestionEntity updatedQuestionEntity = question.get();
		updatedQuestionEntity.setSubject("수정된 제목");
		questionRepository.save(updatedQuestionEntity);
	}
	
	void createQuestions() {
		var q1 = QuestionEntity.builder()
			.subject(questionTitle1)
			.content(questionContent1)
			.createDateTime(LocalDateTime.now())
			.build();
		
		questionRepository.save(q1);
		
		var q2 = QuestionEntity.builder()
			.subject(questionTitle2)
			.content(questionContent2)
			.createDateTime(LocalDateTime.now())
			.build();
		
		questionRepository.save(q2);
	}
}
