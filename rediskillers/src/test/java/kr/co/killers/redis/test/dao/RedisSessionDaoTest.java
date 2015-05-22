package kr.co.killers.redis.test.dao;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import kr.co.killers.redis.exception.RedisException;
import kr.co.killers.redis.test.config.ApplicationContext;
import kr.co.killers.redis.util.RedisKeyUtils;

import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.data.redis.RedisConnectionFailureException;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = ApplicationContext.class, loader = AnnotationConfigContextLoader.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class RedisSessionDaoTest {
	
	@Resource(name = "RedisSessionDao")
	private RedisSessionDao redisSessionDao;

	@Resource(name = "masterSessionRedisTemplate")
	private StringRedisTemplate masterSessionRedisTemplate;

	@Resource(name = "slaveSessionRedisTemplate")
	private StringRedisTemplate slaveSessionRedisTemplate;

	String imoryId = "imory123";

	String sessionId = "session123";

	Map<String, String> sessionMap = null;

	@Before
	public void init() {
		sessionMap = new HashMap<String, String>();
		sessionMap.put("session_id", sessionId);
		sessionMap.put("test", "sessiontest");
		sessionMap.put("status", "1");

	}

	/**
	 * 세션 생성 및 세션 카운트 정보 생성 테스트
	 */
	@Test
	public void aTest() {
		try {
			redisSessionDao.createSession(sessionId, imoryId, sessionMap);
		} catch (RedisException e) {
			fail("createSession fail" + e.getErrorCode() + " " + e.getErrorMsg());
		}
	}

	/**
	 * 생성된 세션 조회
	 */
	@Test
	public void bTest() {
		try {
			// 위에서 생성된 세션 정보를 조회
			Map<String, String> selectSessionMap = redisSessionDao.selectSession(sessionId);
			assertTrue(sessionMap.get("session_id").equals(selectSessionMap.get("session_id")));
		} catch (RedisException e) {
			fail("selectSession fail" + e.getErrorCode() + " " + e.getErrorMsg());
		}
	}

	/**
	 * imoryId 별 생성된 세션 갯수 조회
	 */
	@Test
	public void cTest() {
		try {
			String newImoryId = "imory123123123123123";
			String newSessionId = "123";
			Map<String, String> newsessionMap = new HashMap<String, String>();
			newsessionMap.put("session_id", newSessionId);
			newsessionMap.put("test", "sessiontest");
			newsessionMap.put("status", "1");
			String newSessionId2 = "123123123123";
			Map<String, String> newsessionMap2 = new HashMap<String, String>();
			newsessionMap2.put("session_id", newSessionId2);
			newsessionMap2.put("test", "sessiontest");
			newsessionMap2.put("status", "1");

			// 기존에 생성한 imoryId에 세션을 추가
			redisSessionDao.createSession(newSessionId, imoryId, newsessionMap);
			// 새로운 imoryId에 세션을 추가
			redisSessionDao.createSession(newSessionId2, newImoryId, newsessionMap2);

			// 기존에 생성한 imoryId에 세션수를 조회
			int count = redisSessionDao.selectSessionCount(imoryId);
			assertTrue(count == 2);
			// 기존에 생성한 imoryId에 위에서 생성한 세션을 삭제
			redisSessionDao.deleteSession(newSessionId);
			// 기존에 생성한 imoryId에 세션수를 조회
			int count2 = redisSessionDao.selectSessionCount(imoryId);
			assertTrue(count2 == 1);

			// 새로운 imoryId에 세션수를 조회
			int count3 = redisSessionDao.selectSessionCount(newImoryId);
			assertTrue(count3 == 1);

			// 새로운 imoryId에 세션을 삭제
			redisSessionDao.deleteSession(newSessionId2, newImoryId);

			// 새로운 imoryId에 세션수를 조회
			int count4 = redisSessionDao.selectSessionCount(newImoryId);
			assertTrue(count4 == 0);

		} catch (RedisException e) {
			fail("selectSessionCount fail" + e.getErrorCode() + " " + e.getErrorMsg());
		}
	}

	/**
	 * 세션 만료시간 수정 확인
	 */
	@Test
	public void dTest() {
		try {
			String sessionKey = RedisKeyUtils.scnSessionInfo(sessionId);
			// 세션 만료시간을 확인
			long oldTime = slaveSessionRedisTemplate.getExpire(sessionKey);

			// 세션 만료시간을 수정
			redisSessionDao.updateSessionExpriedTime(sessionId, 1);

			// 세션 만료시간을 확인
			long updateTime = slaveSessionRedisTemplate.getExpire(sessionKey);
			assertTrue(updateTime < oldTime);
		} catch (RedisException e) {
			fail("updateSessionExpriedTime fail" + e.getErrorCode() + " " + e.getErrorMsg());
		} catch (RedisConnectionFailureException e) {
			fail("updateSessionExpriedTime fail" + e.getMessage());
		} catch (Exception e) {
			fail("updateSessionExpriedTime fail" + e.getMessage());
		}
	}

	/**
	 * 세션 삭제 및 세션 카운트 정보 삭제
	 */
	@Test
	public void eTest() {
		try {
			// 세션을 삭제
			redisSessionDao.deleteSession(sessionId);
			// 삭제된 세션수를 확인
			int count = redisSessionDao.selectSessionCount(imoryId);
			assertTrue(count == 0);
			// 삭제된 세션 확인
			Map<String, String> selectSessionMap = redisSessionDao.selectSession(sessionId);
			assertTrue(selectSessionMap.isEmpty());
		} catch (RedisException e) {
			fail("deleteSession fail" + e.getErrorCode() + " " + e.getErrorMsg());
		} catch (RedisConnectionFailureException e) {
			fail("deleteSession fail" + e.getMessage());
		} catch (Exception e) {
			fail("deleteSession fail" + e.getMessage());
		}
	}

}
