package kr.co.killers.redis.test.dao;

import java.util.Map;
import java.util.Set;
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
 * 
 * @author sanaes
 *
 */
@Repository("RedisDao")
@PropertySource("classpath:redis.properties")
public class RedisSessionDaoImpl implements RedisSessionDao {
	private static final Logger log = LoggerFactory.getLogger(RedisSessionDaoImpl.class);

	@Resource(name = "masterSessionRedisTemplate")
	private StringRedisTemplate masterSessionRedisTemplate;

	@Resource(name = "slaveSessionRedisTemplate")
	private StringRedisTemplate slaveSessionRedisTemplate;

	private @Value("${redis.session.expiretime}") int sessionExpiretime;

	private static final String SESSIONIDHASHKEY = "session_id";

	@Override
	@Transactional(rollbackFor = RedisException.class)
	public void createSession(String sessionId, String imoryId, Map<String, String> sessionMap) throws RedisException {
		try {
			String sessionKey = RedisKeyUtils.scnSessionInfo(sessionId);
			masterSessionRedisTemplate.opsForHash().putAll(sessionKey, sessionMap);
			masterSessionRedisTemplate.expire(sessionKey, sessionExpiretime, TimeUnit.HOURS);
			String sessionCntKey = RedisKeyUtils.scnSessionCnt(imoryId);
			masterSessionRedisTemplate.opsForSet().add(sessionCntKey, sessionKey);
		} catch (RedisConnectionFailureException e) {
			log.error("createSession RedisConnectionFailureException 발생 ", e);
			throw new RedisException("1000", e.getMessage());
		} catch (Exception e) {
			log.error("createSession Exception 발생 ", e);
			throw new RedisException("9999", e.getMessage());
		}
	}

	@Override
	public Map<String, String> selectSession(String sessionId) throws RedisException {
		try {
			String sessionKey = RedisKeyUtils.scnSessionInfo(sessionId);
			Map<Object, Object> result = slaveSessionRedisTemplate.opsForHash().entries(sessionKey);
			return TransMap.transMap(result);
		} catch (RedisConnectionFailureException e) {
			log.error("selectSession RedisConnectionFailureException 발생 ", e);
			throw new RedisException("1000", e.getMessage());
		} catch (Exception e) {
			log.error("selectSession Exception 발생 ", e);
			throw new RedisException("9999", e.getMessage());
		}

	}

	@Override
	@Transactional(rollbackFor = RedisException.class)
	public int selectSessionCount(String imoryId) throws RedisException {
		try {
			int sessionCnt = 0;
			String sessionCntKey = RedisKeyUtils.scnSessionCnt(imoryId);
			Set<String> sessionKeys = slaveSessionRedisTemplate.opsForSet().members(sessionCntKey);
			if (sessionKeys != null) {
				sessionCnt = sessionKeys.size();
				for (String sessionKey : sessionKeys) {
					Map<Object, Object> result = slaveSessionRedisTemplate.opsForHash().entries(sessionKey);
					if (result == null || "".equals(result.get(SESSIONIDHASHKEY)) || result.get(SESSIONIDHASHKEY) == null) {
						masterSessionRedisTemplate.opsForSet().remove(sessionCntKey, sessionKey);
						sessionCnt--;
					} else {
						continue;
					}
				}

			}

			return sessionCnt;

		} catch (RedisConnectionFailureException e) {
			log.error("selectSessionCount RedisConnectionFailureException 발생 ", e);
			throw new RedisException("1000", e.getMessage());
		} catch (Exception e) {
			log.error("selectSessionCount Exception 발생 ", e);
			throw new RedisException("9999", e.getMessage());
		}
	}

	@Override
	@Transactional(rollbackFor = RedisException.class)
	public void deleteSession(String sessionId, String imoryId) throws RedisException {
		try {
			String sessionKey = RedisKeyUtils.scnSessionInfo(sessionId);
			masterSessionRedisTemplate.delete(sessionKey);
			String sessionCntKey = RedisKeyUtils.scnSessionCnt(imoryId);
			masterSessionRedisTemplate.opsForSet().remove(sessionCntKey, sessionKey);
		} catch (RedisConnectionFailureException e) {
			log.error("deleteSession(String sessionId, String imoryId) RedisConnectionFailureException 발생 ", e);
			throw new RedisException("1000", e.getMessage());
		} catch (Exception e) {
			log.error("deleteSession(String sessionId, String imoryId) Exception 발생 ", e);
			throw new RedisException("9999", e.getMessage());
		}
	}

	@Override
	@Transactional(rollbackFor = RedisException.class)
	public void deleteSession(String sessionId) throws RedisException {
		try {
			String sessionKey = RedisKeyUtils.scnSessionInfo(sessionId);
			masterSessionRedisTemplate.delete(sessionKey);
		} catch (RedisConnectionFailureException e) {
			log.error("deleteSession(String sessionId) RedisConnectionFailureException 발생 ", e);
			throw new RedisException("1000", e.getMessage());
		} catch (Exception e) {
			log.error("deleteSession(String sessionId) Exception 발생 ", e);
			throw new RedisException("9999", e.getMessage());
		}
	}

	@Override
	@Transactional(rollbackFor = RedisException.class)
	public void updateSessionExpriedTime(String sessionId, int hour) throws RedisException {
		try {
			String sessionKey = RedisKeyUtils.scnSessionInfo(sessionId);
			masterSessionRedisTemplate.expire(sessionKey, hour, TimeUnit.HOURS);
		} catch (RedisConnectionFailureException e) {
			log.error("updateSessionExpriedTime RedisConnectionFailureException 발생 ", e);
			throw new RedisException("1000", e.getMessage());
		} catch (Exception e) {
			log.error("updateSessionExpriedTime Exception 발생 ", e);
			throw new RedisException("9999", e.getMessage());
		}
	}

}
