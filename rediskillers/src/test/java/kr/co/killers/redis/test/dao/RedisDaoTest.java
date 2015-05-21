package kr.co.killers.redis.test.dao;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import kr.co.killers.redis.exception.RedisException;
import kr.co.killers.redis.test.config.ApplicationContext;
import kr.co.killers.redis.test.dao.RedisDao;
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
public class RedisDaoTest {

	@Resource(name = "RedisDao")
	private RedisDao testRedisDao;

	@Resource(name = "masterSessionRedisTemplate")
	private StringRedisTemplate masterSessionRedisTemplate;

	@Resource(name = "masterNonceRedisTemplate")
	private StringRedisTemplate masterNonceRedisTemplate;

	@Resource(name = "slaveSessionRedisTemplate")
	private StringRedisTemplate slaveSessionRedisTemplate;

	@Resource(name = "slaveNonceRedisTemplate")
	private StringRedisTemplate slaveNonceRedisTemplate;

	String imoryId = "imory123";

	String sessionId = "session123";

	String nonceId = "nonce123";

	Map<String, String> sessionMap = null;

	Map<String, String> nonceMap = null;

	@Before
	public void init() {
		sessionMap = new HashMap<String, String>();
		sessionMap.put("session_id", sessionId);
		sessionMap.put("test", "sessiontest");
		sessionMap.put("status", "1");

		nonceMap = new HashMap<String, String>();

		nonceMap.put("nonceId", nonceId);
		nonceMap.put("test", "noncetest");
		nonceMap.put("status", "1");
	}

	/**
	 * 세션 생성 및 세션 카운트 정보 생성 테스트
	 */
	@Test
	public void aTest() {
		try {
			testRedisDao.createSession(sessionId, imoryId, sessionMap);
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
			//위에서 생성된 세션 정보를 조회
			Map<String, String> selectSessionMap = testRedisDao.selectSession(sessionId);
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
			testRedisDao.createSession(newSessionId, imoryId, newsessionMap);
			// 새로운 imoryId에 세션을 추가
			testRedisDao.createSession(newSessionId2, newImoryId, newsessionMap2);

			// 기존에 생성한 imoryId에 세션수를 조회
			int count = testRedisDao.selectSessionCount(imoryId);
			assertTrue(count == 2);
			// 기존에 생성한 imoryId에 위에서 생성한 세션을 삭제
			testRedisDao.deleteSession(newSessionId);
			// 기존에 생성한 imoryId에 세션수를 조회
			int count2 = testRedisDao.selectSessionCount(imoryId);
			assertTrue(count2 == 1);

			// 새로운 imoryId에 세션수를 조회
			int count3 = testRedisDao.selectSessionCount(newImoryId);
			assertTrue(count3 == 1);

			// 새로운 imoryId에 세션을 삭제
			testRedisDao.deleteSession(newSessionId2);

			// 새로운 imoryId에 세션수를 조회
			int count4 = testRedisDao.selectSessionCount(newImoryId);
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
			//세션 만료시간을 확인
			long oldTime = slaveSessionRedisTemplate.getExpire(sessionKey);
			
			//세션 만료시간을 수정
			testRedisDao.updateSessionExpriedTime(sessionId, 1);

			//세션 만료시간을 확인
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
			//세션을 삭제
			testRedisDao.deleteSession(sessionId);
			//삭제된 세션수를 확인
			int count = testRedisDao.selectSessionCount(imoryId);
			assertTrue(count == 0);
			//삭제된 세션 확인
			Map<String, String> selectSessionMap = testRedisDao.selectSession(sessionId);
			assertTrue(selectSessionMap.isEmpty());
		} catch (RedisException e) {
			fail("deleteSession fail" + e.getErrorCode() + " " + e.getErrorMsg());
		} catch (RedisConnectionFailureException e) {
			fail("deleteSession fail" + e.getMessage());
		} catch (Exception e) {
			fail("deleteSession fail" + e.getMessage());
		}
	}

	/**
	 * A Type Nonce 생성
	 */
	@Test
	public void gTest() {
		try {
			testRedisDao.createAdjNonce(nonceId, nonceMap);
		} catch (RedisException e) {
			fail("createAdjNonce fail" + e.getErrorCode() + " " + e.getErrorMsg());
		}
	}

	/**
	 * A Type Nonce 조회
	 */
	@Test
	public void hTest() {
		try {
			Map<String, String> selectNonceMap = testRedisDao.selectAdjNonce(nonceId);
			assertTrue(nonceMap.equals(selectNonceMap));
		} catch (RedisException e) {
			fail("selectAdjNonce fail" + e.getErrorCode() + " " + e.getErrorMsg());
		}
	}

	/**
	 * A Type Nonce 상태 수정
	 */
	@Test
	public void iTest() {
		try {
			testRedisDao.updateAdjNonceStatus(nonceId, "3");
			Map<String, String> selectNonceMap = testRedisDao.selectAdjNonce(nonceId);
			assertTrue("3".equals(selectNonceMap.get("status")));
		} catch (RedisException e) {
			fail("updateAdjNonceStatus fail" + e.getErrorCode() + " " + e.getErrorMsg());
		} catch (RedisConnectionFailureException e) {
			fail("updateAdjNonceStatus fail" + e.getMessage());
		} catch (Exception e) {
			fail("updateAdjNonceStatus fail" + e.getMessage());
		}
	}

	/**
	 * A Type Nonce 삭제
	 */
	@Test
	public void jTest() {
		try {
			testRedisDao.deleteAdjNonce(nonceId);
			Map<String, String> selectNonceMap = testRedisDao.selectAdjNonce(nonceId);
			assertTrue(selectNonceMap.isEmpty());
		} catch (RedisException e) {
			fail("deleteAdjNonce fail" + e.getErrorCode() + " " + e.getErrorMsg());
		} catch (RedisConnectionFailureException e) {
			fail("deleteAdjNonce fail" + e.getMessage());
		} catch (Exception e) {
			fail("deleteAdjNonce fail" + e.getMessage());
		}
	}

	/**
	 * O Type Nonce 생성
	 */
	@Test
	public void kTest() {
		try {
			testRedisDao.createOneNonce(nonceId, nonceMap);
		} catch (RedisException e) {
			fail("createOneNonce fail" + e.getErrorCode() + " " + e.getErrorMsg());
		}
	}

	/**
	 * O Type Nonce 조회
	 */
	@Test
	public void lTest() {
		try {
			Map<String, String> selectNonceMap = testRedisDao.selectOneNonce(nonceId);
			assertTrue(nonceMap.equals(selectNonceMap));
		} catch (RedisException e) {
			fail("selectOneNonce fail" + e.getErrorCode() + " " + e.getErrorMsg());
		}
	}

	/**
	 * O Type Nonce 상태 변경
	 */
	@Test
	public void mTest() {
		try {
			testRedisDao.updateOneNonceStatus(nonceId, "3");
			Map<String, String> selectNonceMap = testRedisDao.selectOneNonce(nonceId);
			assertTrue("3".equals(selectNonceMap.get("status")));
		} catch (RedisException e) {
			fail("updateOneNonceStatus fail" + e.getErrorCode() + " " + e.getErrorMsg());
		} catch (RedisConnectionFailureException e) {
			fail("updateOneNonceStatus fail" + e.getMessage());
		} catch (Exception e) {
			fail("updateOneNonceStatus fail" + e.getMessage());
		}
	}

	/**
	 * O Type Nonce 삭제
	 */
	@Test
	public void nTest() {
		try {
			testRedisDao.deleteOneNonce(nonceId);
			Map<String, String> selectNonceMap = testRedisDao.selectOneNonce(nonceId);
			assertTrue(selectNonceMap.isEmpty());
		} catch (RedisException e) {
			fail("deleteOneNonce fail" + e.getErrorCode() + " " + e.getErrorMsg());
		} catch (RedisConnectionFailureException e) {
			fail("deleteOneNonce fail" + e.getMessage());
		} catch (Exception e) {
			fail("deleteOneNonce fail" + e.getMessage());
		}
	}

}
