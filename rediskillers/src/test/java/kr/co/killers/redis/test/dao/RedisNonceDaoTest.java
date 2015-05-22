package kr.co.killers.redis.test.dao;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import kr.co.killers.redis.exception.RedisException;
import kr.co.killers.redis.test.config.ApplicationContext;

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
public class RedisNonceDaoTest {

	@Resource(name = "RedisNonceDao")
	private RedisNonceDao redisNonceDao;

	@Resource(name = "masterNonceRedisTemplate")
	private StringRedisTemplate masterNonceRedisTemplate;

	@Resource(name = "slaveNonceRedisTemplate")
	private StringRedisTemplate slaveNonceRedisTemplate;

	String imoryId = "imory123";


	String nonceId = "nonce123";


	Map<String, String> nonceMap = null;

	@Before
	public void init() {

		nonceMap = new HashMap<String, String>();

		nonceMap.put("nonceId", nonceId);
		nonceMap.put("test", "noncetest");
		nonceMap.put("status", "1");
	}


	/**
	 * A Type Nonce 생성
	 */
	@Test
	public void gTest() {
		try {
			redisNonceDao.createAdjNonce(nonceId, nonceMap);
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
			Map<String, String> selectNonceMap = redisNonceDao.selectAdjNonce(nonceId);
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
			redisNonceDao.updateAdjNonceStatus(nonceId, "3");
			Map<String, String> selectNonceMap = redisNonceDao.selectAdjNonce(nonceId);
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
			redisNonceDao.deleteAdjNonce(nonceId);
			Map<String, String> selectNonceMap = redisNonceDao.selectAdjNonce(nonceId);
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
			redisNonceDao.createOneNonce(nonceId, nonceMap);
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
			Map<String, String> selectNonceMap = redisNonceDao.selectOneNonce(nonceId);
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
			redisNonceDao.updateOneNonceStatus(nonceId, "3");
			Map<String, String> selectNonceMap = redisNonceDao.selectOneNonce(nonceId);
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
			redisNonceDao.deleteOneNonce(nonceId);
			Map<String, String> selectNonceMap = redisNonceDao.selectOneNonce(nonceId);
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
