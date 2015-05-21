package kr.co.killers.redis.constants;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;

@PropertySource("classpath:redis.properties")
public class RedisConstants {

	@Autowired
	private Environment env;
	
	
 

}
