package com.ardecs.springbootapp;

import com.ardecs.springbootapp.entities.User;
import com.ardecs.springbootapp.repositories.UserRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)

@DataJpaTest
public class SpringBootAppApplicationTests {

	@Autowired
	private TestEntityManager entityManager;

	@Autowired
	private UserRepository repository;

	@Test
	public void testUser() {
		entityManager.persist(new User("ivan", "1234", "Иван"));
		entityManager.persist(new User("petr", "qwer", "Петр"));
		entityManager.persist(new User("ivanesku", "asdf", "Иванеску"));

		User user = repository.findByLogin("ivan");
		assertThat(user.getLogin()).isEqualTo("ivan");
		assertThat(user.getPassword()).isEqualTo("1234");

		List<User> userList = repository.findByNameStartsWith("Ива");
		assertThat(userList.size()).isEqualTo(2);
	}
}
