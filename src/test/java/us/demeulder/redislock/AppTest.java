package us.demeulder.redislock;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

@SpringBootTest
class AppTests {

	@MockBean
	private RedissonClient redisson;

	@Autowired
	private HelloController controller;

	@Test
	void contextLoads() {
		assertThat(controller).isNotNull();
	}

}
