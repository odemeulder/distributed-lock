package us.demeulder.redislock;

import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.TimeUnit;

import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;

@RestController
public class CrazyController {

  @Autowired
	RedissonClient redisson;

	@Value("${spring.lock.name}")
	private String LOCKNAME;

	@RequestMapping("/crazy")
	public String index() throws InterruptedException {

		RLock lock = this.redisson.getLock(LOCKNAME);
		if (lock.tryLock(1, 10, TimeUnit.SECONDS)) {
			String rv = "Crazy!";
			return rv;
		}
		return "Crazy not obtain lock";
		
	}
}
