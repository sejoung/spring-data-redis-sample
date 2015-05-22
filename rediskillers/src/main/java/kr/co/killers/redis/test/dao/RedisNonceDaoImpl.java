package kr.co.killers.redis.test.dao;

import java.util.Map;
import java.util.concurrent.TimeUnit;

import javax.annotation.Resource;

import kr.co.killers.redis.exception.RedisException;
import kr.co.killers.redis.util.RedisKeyUtils;
import kr.co.killers.redis.util.TransMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.redis.RedisConnectionFailureException;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 * Redis Dao 인터페이스 구현체
 * @author sanaes
 *
 */
@Repository("RedisNonceDao")
@PropertySource("classpath:redis.properties")
public class RedisNonceDaoImpl implements RedisNonceDao {
	private static final Logger log = LoggerFactory.getLogger(RedisNonceDaoImpl.class);

	@Resource(name = "masterNonceRedisTemplate")
	private StringRedisTemplate masterNonceRedisTemplate;

	@Resource(name = "slaveNonceRedisTemplate")
	private StringRedisTemplate slaveNonceRedisTemplate;

	private @Value("${redis.nonce.expiretime}") int nonceExpiretime;

	private static final String NONCESTATUSHASHKEY = "status";

	@Override
	@Transactional(rollbackFor = RedisException.class)
	public void createOneNonce(String nonceId, Map<String, String> nonceMap) throws RedisException {
		try {
			String nonceKey = RedisKeyUtils.scnNonceOne(nonceId);
			masterNonceRedisTemplate.opsForHash().putAll(nonceKey, nonceMap);
			masterNonceRedisTemplate.expire(nonceKey, nonceExpiretime, TimeUnit.HOURS);
		} catch (RedisConnectionFailureException e) {
			log.error("createOneNonce RedisConnectionFailureException 발생 ",e);
			throw new RedisException("1000", e.getMessage());
		} catch (Exception e) {
			log.error("createOneNonce Exception 발생 ",e);
			throw new RedisException("9999", e.getMessage());
		}
	}

	@Override
	@Transactional(rollbackFor = RedisException.class)
	public void createAdjNonce(String nonceId, Map<String, String> nonceMap) throws RedisException {
		try {
			String nonceKey = RedisKeyUtils.scnNonceAdj(nonceId);
			masterNonceRedisTemplate.opsForHash().putAll(nonceKey, nonceMap);
			masterNonceRedisTemplate.expire(nonceKey, nonceExpiretime, TimeUnit.HOURS);
		} catch (RedisConnectionFailureException e) {
			log.error("createAdjNonce RedisConnectionFailureException 발생 ",e);
			throw new RedisException("1000", e.getMessage());
		} catch (Exception e) {
			log.error("createAdjNonce Exception 발생 ",e);
			throw new RedisException("9999", e.getMessage());
		}

	}

	@Override
	public Map<String, String> selectOneNonce(String nonceId) throws RedisException {
		try {
			String nonceKey = RedisKeyUtils.scnNonceOne(nonceId);
			Map<Object, Object> result = slaveNonceRedisTemplate.opsForHash().entries(nonceKey);
			return TransMap.transMap(result);
		} catch (RedisConnectionFailureException e) {
			log.error("selectOneNonce RedisConnectionFailureException 발생 ",e);
			throw new RedisException("1000", e.getMessage());
		} catch (Exception e) {
			log.error("selectOneNonce Exception 발생 ",e);
			throw new RedisException("9999", e.getMessage());
		}
	}

	@Override
	public Map<String, String> selectAdjNonce(String nonceId) throws RedisException {
		try {
			String nonceKey = RedisKeyUtils.scnNonceAdj(nonceId);
			Map<Object, Object> result = slaveNonceRedisTemplate.opsForHash().entries(nonceKey);
			return TransMap.transMap(result);
		} catch (RedisConnectionFailureException e) {
			log.error("selectAdjNonce RedisConnectionFailureException 발생 ",e);
			throw new RedisException("1000", e.getMessage());
		} catch (Exception e) {
			log.error("selectAdjNonce Exception 발생 ",e);
			throw new RedisException("9999", e.getMessage());
		}
	}

	@Override
	public void deleteAdjNonce(String nonceId) throws RedisException {
		try {
			String nonceKey = RedisKeyUtils.scnNonceAdj(nonceId);
			masterNonceRedisTemplate.delete(nonceKey);
		} catch (RedisConnectionFailureException e) {
			log.error("deleteAdjNonce RedisConnectionFailureException 발생 ",e);
			throw new RedisException("1000", e.getMessage());
		} catch (Exception e) {
			log.error("deleteAdjNonce Exception 발생 ",e);
			throw new RedisException("9999", e.getMessage());
		}
	}

	@Override
	public void deleteOneNonce(String nonceId) throws RedisException {
		try {
			String nonceKey = RedisKeyUtils.scnNonceOne(nonceId);
			masterNonceRedisTemplate.delete(nonceKey);
		} catch (RedisConnectionFailureException e) {
			log.error("deleteOneNonce RedisConnectionFailureException 발생 ",e);
			throw new RedisException("1000", e.getMessage());
		} catch (Exception e) {
			log.error("deleteOneNonce Exception 발생 ",e);
			throw new RedisException("9999", e.getMessage());
		}
	}

	@Override
	@Transactional(rollbackFor = RedisException.class)
	public void updateOneNonceStatus(String nonceId, String status) throws RedisException {
		try {
			String nonceKey = RedisKeyUtils.scnNonceOne(nonceId);
			masterNonceRedisTemplate.opsForHash().put(nonceKey, NONCESTATUSHASHKEY, status);
		} catch (RedisConnectionFailureException e) {
			log.error("updateOneNonceStatus RedisConnectionFailureException 발생 ",e);
			throw new RedisException("1000", e.getMessage());
		} catch (Exception e) {
			log.error("updateOneNonceStatus Exception 발생 ",e);
			throw new RedisException("9999", e.getMessage());
		}
	}

	@Override
	public void updateAdjNonceStatus(String nonceId, String status) throws RedisException {
		try {
			String nonceKey = RedisKeyUtils.scnNonceAdj(nonceId);
			masterNonceRedisTemplate.opsForHash().put(nonceKey, NONCESTATUSHASHKEY, status);
		} catch (RedisConnectionFailureException e) {
			log.error("updateAdjNonceStatus RedisConnectionFailureException 발생 ",e);
			throw new RedisException("1000", e.getMessage());
		} catch (Exception e) {
			log.error("updateAdjNonceStatus Exception 발생 ",e);
			throw new RedisException("9999", e.getMessage());
		}
	}

}
