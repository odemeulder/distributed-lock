package us.demeulder.redislock;

import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UnlockController {

  @Value("${spring.lock.name}")
  private String LOCKNAME;

  @Autowired
  private RedissonClient redisson;

  @RequestMapping("/unlock")
  public String unlock() {
    RLock lock = redisson.getLock(LOCKNAME);
    lock.forceUnlock();
    return "Unlocked";
  }
  
}
