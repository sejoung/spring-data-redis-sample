package kr.co.killers.redis.test.config;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

import kr.co.killers.redis.util.encryptor.Encryptor;
import kr.co.killers.redis.util.encryptor.impl.AesEncryptor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.StringRedisTemplate;

import redis.clients.jedis.JedisPoolConfig;

@PropertySource("classpath:redis.properties")
@Configuration
public class RedisConfiguration {
	private static final Logger log = LoggerFactory.getLogger(RedisConfiguration.class);
	
	private @Value("${redis.master.session.host-name}") String masterSessionRedisHostName;
	private @Value("${redis.master.session.port}") int masterSessionRedisPort;
	private @Value("${redis.master.session.password}") String masterSessionRedisPassword;
	private @Value("${redis.master.session.timeout}") int masterSessionTimeout;
	private @Value("${redis.master.session.usepool}") boolean masterSessionUsePool;

	private @Value("${redis.master.nonce.host-name}") String masterNonceRedisHostName;
	private @Value("${redis.master.nonce.port}") int masterNonceRedisPort;
	private @Value("${redis.master.nonce.password}") String masterNonceRedisPassword;
	private @Value("${redis.master.nonce.timeout}") int masterNonceTimeout;
	private @Value("${redis.master.nonce.usepool}") boolean masterNonceUsePool;

	private @Value("${redis.master.pool.session.maxTotal}") int masterPoolSessionMaxTotal;
	private @Value("${redis.master.pool.session.maxIdle}") int masterPoolSessionMaxIdle;
	private @Value("${redis.master.pool.session.minIdle}") int masterPoolSessionMinIdle;
	private @Value("${redis.master.pool.session.testWhileIdle}") boolean masterPoolSessionTestWhileIdle;
	private @Value("${redis.master.pool.session.numTestsPerEvictionRun}") int masterPoolSessionNumTestsPerEvictionRun;
	private @Value("${redis.master.pool.session.minEvictableIdleTimeMillis}") long masterPoolSessionMinEvictableIdleTimeMillis;
	private @Value("${redis.master.pool.session.timeBetweenEvictionRunsMillis}") long masterPoolSessionTimeBetweenEvictionRunsMillis;

	private @Value("${redis.master.pool.nonce.maxTotal}") int masterPoolNonceMaxTotal;
	private @Value("${redis.master.pool.nonce.maxIdle}") int masterPoolNonceMaxIdle;
	private @Value("${redis.master.pool.nonce.minIdle}") int masterPoolNonceMinIdle;
	private @Value("${redis.master.pool.nonce.testWhileIdle}") boolean masterPoolNonceTestWhileIdle;
	private @Value("${redis.master.pool.nonce.numTestsPerEvictionRun}") int masterPoolNonceNumTestsPerEvictionRun;
	private @Value("${redis.master.pool.nonce.minEvictableIdleTimeMillis}") long masterPoolNonceMinEvictableIdleTimeMillis;
	private @Value("${redis.master.pool.nonce.timeBetweenEvictionRunsMillis}") long masterPoolNonceTimeBetweenEvictionRunsMillis;

	private @Value("${redis.slave.session.host-name}") String slaveSessionRedisHostName;
	private @Value("${redis.slave.session.port}") int slaveSessionRedisPort;
	private @Value("${redis.slave.session.password}") String slaveSessionRedisPassword;
	private @Value("${redis.slave.session.timeout}") int slaveSessionTimeout;
	private @Value("${redis.slave.session.usepool}") boolean slaveSessionUsePool;

	private @Value("${redis.slave.nonce.host-name}") String slaveNonceRedisHostName;
	private @Value("${redis.slave.nonce.port}") int slaveNonceRedisPort;
	private @Value("${redis.slave.nonce.password}") String slaveNonceRedisPassword;
	private @Value("${redis.slave.nonce.timeout}") int slaveNonceTimeout;
	private @Value("${redis.slave.nonce.usepool}") boolean slaveNonceUsePool;

	private @Value("${redis.slave.pool.session.maxTotal}") int slavePoolSessionMaxTotal;
	private @Value("${redis.slave.pool.session.maxIdle}") int slavePoolSessionMaxIdle;
	private @Value("${redis.slave.pool.session.minIdle}") int slavePoolSessionMinIdle;
	private @Value("${redis.slave.pool.session.testWhileIdle}") boolean slavePoolSessionTestWhileIdle;
	private @Value("${redis.slave.pool.session.numTestsPerEvictionRun}") int slavePoolSessionNumTestsPerEvictionRun;
	private @Value("${redis.slave.pool.session.minEvictableIdleTimeMillis}") long slavePoolSessionMinEvictableIdleTimeMillis;
	private @Value("${redis.slave.pool.session.timeBetweenEvictionRunsMillis}") long slavePoolSessionTimeBetweenEvictionRunsMillis;

	private @Value("${redis.slave.pool.nonce.maxTotal}") int slavePoolNonceMaxTotal;
	private @Value("${redis.slave.pool.nonce.maxIdle}") int slavePoolNonceMaxIdle;
	private @Value("${redis.slave.pool.nonce.minIdle}") int slavePoolNonceMinIdle;
	private @Value("${redis.slave.pool.nonce.testWhileIdle}") boolean slavePoolNonceTestWhileIdle;
	private @Value("${redis.slave.pool.nonce.numTestsPerEvictionRun}") int slavePoolNonceNumTestsPerEvictionRun;
	private @Value("${redis.slave.pool.nonce.minEvictableIdleTimeMillis}") long slavePoolNonceMinEvictableIdleTimeMillis;
	private @Value("${redis.slave.pool.nonce.timeBetweenEvictionRunsMillis}") long slavePoolNonceTimeBetweenEvictionRunsMillis;

	private static Encryptor ENCRYPTOR = new AesEncryptor();
	
	@Bean
	public static PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer() {
		return new PropertySourcesPlaceholderConfigurer();
	}

	@Bean
	JedisConnectionFactory masterSessionJedisConnectionFactory() {
		JedisConnectionFactory factory = new JedisConnectionFactory();
		JedisPoolConfig poolConfig = new JedisPoolConfig();
		poolConfig.setMaxTotal(masterPoolSessionMaxTotal);
		poolConfig.setMaxIdle(masterPoolSessionMaxIdle);
		poolConfig.setMinIdle(masterPoolSessionMinIdle);
		poolConfig.setMinEvictableIdleTimeMillis(masterPoolSessionMinEvictableIdleTimeMillis);
		poolConfig.setTimeBetweenEvictionRunsMillis(masterPoolSessionTimeBetweenEvictionRunsMillis);
		poolConfig.setNumTestsPerEvictionRun(masterPoolSessionNumTestsPerEvictionRun);
		factory.setPoolConfig(poolConfig);
		factory.setHostName(masterSessionRedisHostName);
		factory.setPort(masterSessionRedisPort);
		try {
			
			String password = ENCRYPTOR.decrypt(masterSessionRedisPassword);
			log.debug("masterSessionRedisPassword : "+password);
			factory.setPassword(password);
			
		} catch (InvalidKeyException | IllegalBlockSizeException | BadPaddingException | NoSuchAlgorithmException | NoSuchPaddingException e) {
			throw new RuntimeException(e);
		}
		factory.setUsePool(masterSessionUsePool);
		factory.setTimeout(masterSessionTimeout);

		return factory;
	}

	@Bean
	StringRedisTemplate masterSessionRedisTemplate() {
		StringRedisTemplate redisTemplate = new StringRedisTemplate();
		redisTemplate.setConnectionFactory(masterSessionJedisConnectionFactory());
		// explicitly enable transaction support
		redisTemplate.setEnableTransactionSupport(true);
		return redisTemplate;
	}

	@Bean
	JedisConnectionFactory masterNonceJedisConnectionFactory() {
		JedisConnectionFactory factory = new JedisConnectionFactory();
		JedisPoolConfig poolConfig = new JedisPoolConfig();
		poolConfig.setMaxTotal(masterPoolNonceMaxTotal);
		poolConfig.setMaxIdle(masterPoolNonceMaxIdle);
		poolConfig.setMinIdle(masterPoolNonceMinIdle);
		poolConfig.setMinEvictableIdleTimeMillis(masterPoolNonceMinEvictableIdleTimeMillis);
		poolConfig.setTimeBetweenEvictionRunsMillis(masterPoolNonceTimeBetweenEvictionRunsMillis);
		poolConfig.setNumTestsPerEvictionRun(masterPoolNonceNumTestsPerEvictionRun);
		factory.setPoolConfig(poolConfig);
		factory.setHostName(masterNonceRedisHostName);
		factory.setPort(masterNonceRedisPort);
		try {
			String password = ENCRYPTOR.decrypt(masterNonceRedisPassword);
			log.debug("masterNonceRedisPassword : "+password);
			factory.setPassword(password);
			
		} catch (InvalidKeyException | IllegalBlockSizeException | BadPaddingException | NoSuchAlgorithmException | NoSuchPaddingException e) {
			throw new RuntimeException(e);
		}
		factory.setUsePool(masterNonceUsePool);
		factory.setTimeout(masterNonceTimeout);

		return factory;
	}

	@Bean
	StringRedisTemplate masterNonceRedisTemplate() {
		StringRedisTemplate redisTemplate = new StringRedisTemplate();
		redisTemplate.setConnectionFactory(masterNonceJedisConnectionFactory());

		// explicitly enable transaction support
		redisTemplate.setEnableTransactionSupport(true);

		return redisTemplate;
	}

	@Bean
	JedisConnectionFactory slaveSessionJedisConnectionFactory() {
		JedisConnectionFactory factory = new JedisConnectionFactory();
		JedisPoolConfig poolConfig = new JedisPoolConfig();
		poolConfig.setMaxTotal(slavePoolSessionMaxTotal);
		poolConfig.setMaxIdle(slavePoolSessionMaxIdle);
		poolConfig.setMinIdle(slavePoolSessionMinIdle);
		poolConfig.setMinEvictableIdleTimeMillis(slavePoolSessionMinEvictableIdleTimeMillis);
		poolConfig.setTimeBetweenEvictionRunsMillis(slavePoolSessionTimeBetweenEvictionRunsMillis);
		poolConfig.setNumTestsPerEvictionRun(slavePoolSessionNumTestsPerEvictionRun);
		factory.setPoolConfig(poolConfig);
		factory.setHostName(slaveSessionRedisHostName);
		factory.setPort(slaveSessionRedisPort);
		try {
			String password = ENCRYPTOR.decrypt(slaveSessionRedisPassword);
			log.debug("slaveSessionRedisPassword : "+password);
			factory.setPassword(password);
		} catch (InvalidKeyException | IllegalBlockSizeException | BadPaddingException | NoSuchAlgorithmException | NoSuchPaddingException e) {
			throw new RuntimeException(e);
		}
		factory.setUsePool(slaveSessionUsePool);
		factory.setTimeout(slaveSessionTimeout);

		return factory;
	}

	@Bean
	StringRedisTemplate slaveSessionRedisTemplate() {
		StringRedisTemplate redisTemplate = new StringRedisTemplate();
		redisTemplate.setConnectionFactory(slaveSessionJedisConnectionFactory());
		// explicitly enable transaction support
		redisTemplate.setEnableTransactionSupport(true);
		return redisTemplate;
	}

	@Bean
	JedisConnectionFactory slaveNonceJedisConnectionFactory() {
		JedisConnectionFactory factory = new JedisConnectionFactory();
		JedisPoolConfig poolConfig = new JedisPoolConfig();
		poolConfig.setMaxTotal(slavePoolNonceMaxTotal);
		poolConfig.setMaxIdle(slavePoolNonceMaxIdle);
		poolConfig.setMinIdle(slavePoolNonceMinIdle);
		poolConfig.setMinEvictableIdleTimeMillis(slavePoolNonceMinEvictableIdleTimeMillis);
		poolConfig.setTimeBetweenEvictionRunsMillis(slavePoolNonceTimeBetweenEvictionRunsMillis);
		poolConfig.setNumTestsPerEvictionRun(slavePoolNonceNumTestsPerEvictionRun);
		factory.setPoolConfig(poolConfig);
		factory.setHostName(slaveNonceRedisHostName);
		factory.setPort(slaveNonceRedisPort);
		try {
			String password = ENCRYPTOR.decrypt(slaveNonceRedisPassword);
			log.debug("slaveNonceRedisPassword : "+password);
			factory.setPassword(password);

		} catch (InvalidKeyException | IllegalBlockSizeException | BadPaddingException | NoSuchAlgorithmException | NoSuchPaddingException e) {
			throw new RuntimeException(e);
		}
		factory.setUsePool(slaveNonceUsePool);
		factory.setTimeout(slaveNonceTimeout);

		return factory;
	}

	@Bean
	StringRedisTemplate slaveNonceRedisTemplate() {
		StringRedisTemplate redisTemplate = new StringRedisTemplate();
		redisTemplate.setConnectionFactory(slaveNonceJedisConnectionFactory());

		// explicitly enable transaction support
		redisTemplate.setEnableTransactionSupport(true);

		return redisTemplate;
	}

}
