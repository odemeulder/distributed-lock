package us.demeulder.redislock;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.*;

import java.util.concurrent.TimeUnit;


@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class HelloControllerTest {
  
  @MockBean
  private RedissonClient redisson;

  @MockBean
  private RLock lock;

  @LocalServerPort
	private int port;

	@Autowired
  private TestRestTemplate restTemplate;
  
  private final String LOCKNAME = "ODM";

  @Test
  @DisplayName("Hello Controller returns expected result when not locked.")
  public void TestHello() throws InterruptedException {
    // Arrange
    given(redisson.getLock(LOCKNAME))
      .willReturn(lock);
    given(lock.tryLock(Mockito.anyLong(),Mockito.anyLong(), Mockito.any(TimeUnit.class)))
      .willReturn(true);
    // Act
    String rv = this.restTemplate.getForObject("http://localhost:" + port + "/", String.class);
    // Assert
    assertThat(rv).contains("Greetings from Spring Boot");
  }

  @Test
  @DisplayName("Hello Controller returns expected result when locked.")
  public void TestHelloLocked() throws InterruptedException {
    // Arrange
    given(redisson.getLock(LOCKNAME))
      .willReturn(lock);
    given(lock.tryLock(Mockito.anyLong(),Mockito.anyLong(),Mockito.any(TimeUnit.class)))
      .willReturn(false);
    // Act
    String rv = this.restTemplate.getForObject("http://localhost:" + port + "/", String.class);
    // Assert
    assertThat(rv).contains("Could not obtain lock");
  }


}
