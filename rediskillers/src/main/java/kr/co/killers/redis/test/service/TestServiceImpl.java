package kr.co.killers.redis.test.service;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import kr.co.killers.redis.exception.RedisException;
import kr.co.killers.redis.test.dao.RedisNonceDao;
import kr.co.killers.redis.test.dao.RedisSessionDao;
import kr.co.killers.redis.test.dao.TestDao;
import kr.co.killers.redis.util.CommonUtil;
import kr.co.killers.redis.util.KeyUtils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.ui.ModelMap;

@Service("TestService")
public class TestServiceImpl implements TestService {
	private static final Logger log = LoggerFactory.getLogger(TestServiceImpl.class);

	@Resource(name = "TestDao")
	private TestDao testDao;

	@Resource(name = "RedisNonceDao")
	private RedisNonceDao redisNonceDao;
	
	@Resource(name = "RedisSessionDao")
	private RedisSessionDao redisSessionDao;
	
	
	String imoryId = "imory123";

	@Override
	public void redisSessonInsertSample(HttpSession session, HttpServletRequest request, HttpServletResponse response, ModelMap modelMap, Map<String, Object> params) throws Exception {
		String json = null;
		String sessionId = KeyUtils.getSessionId();
		Map<String, String> sessionMap = new HashMap<String, String>();
		sessionMap.put("session_id",sessionId);
		sessionMap.put("test", "test");
		try {
			redisSessionDao.createSession(sessionId, imoryId, sessionMap);
			json = "{\"code\":\"0000\",\"msg\":\"success\"}";
		} catch (RedisException e) {
			json = "{\"code\":\""+e.getErrorCode()+"\",\"msg\":\""+e.getErrorMsg()+"\"}";
		}
		modelMap.put("json", json);
	}

	@Override
	public void redisSessionDeleteSample(HttpSession session, HttpServletRequest request, HttpServletResponse response, ModelMap modelMap, Map<String, Object> params) throws Exception {
		String json = null;
		try {
			String sessionId = (String) params.get("sessionId");
			redisSessionDao.deleteSession(sessionId);
			json = "{\"code\":\"0000\",\"msg\":\"success\"}";
		} catch (RedisException e) {
			json = "{\"code\":\""+e.getErrorCode()+"\",\"msg\":\""+e.getErrorMsg()+"\"}";
		}
		modelMap.put("json", json);
	}

	@Override
	public void redisSessionUpdateSample(HttpSession session, HttpServletRequest request, HttpServletResponse response, ModelMap modelMap, Map<String, Object> params) throws Exception {
		String json = null;
		try {
			String sessionId = (String) params.get("sessionId");
			redisSessionDao.updateSessionExpriedTime(sessionId, 1);
			json = "{\"code\":\"0000\",\"msg\":\"success\"}";
		} catch (RedisException e) {
			json = "{\"code\":\""+e.getErrorCode()+"\",\"msg\":\""+e.getErrorMsg()+"\"}";
		}
		modelMap.put("json", json);
	}

	@Override
	public void redisSessionSelectSample(HttpSession session, HttpServletRequest request, HttpServletResponse response, ModelMap modelMap, Map<String, Object> params) throws Exception {
		String json = null;
		try {
			String sessionId = (String) params.get("sessionId");
			json = CommonUtil.generateJson(redisSessionDao.selectSession(sessionId));
		} catch (RedisException e) {
			json = "{\"code\":\""+e.getErrorCode()+"\",\"msg\":\""+e.getErrorMsg()+"\"}";
		}
		modelMap.put("json", json);
	}

	@Override
	public void redisSessionCountSample(HttpSession session, HttpServletRequest request, HttpServletResponse response, ModelMap modelMap, Map<String, Object> params) throws Exception {
		String json = null;
		try {
			json = "{\"count\":\""+redisSessionDao.selectSessionCount(imoryId)+"\"}";
		} catch (RedisException e) {
			json = "{\"code\":\""+e.getErrorCode()+"\",\"msg\":\""+e.getErrorMsg()+"\"}";
		}
		modelMap.put("json", json);
	}
	
	@Override
	public void redisNonceTypeAInsertSample(HttpSession session, HttpServletRequest request, HttpServletResponse response, ModelMap modelMap, Map<String, Object> params) throws Exception {
		String json = null;
		String nonceId = KeyUtils.getNonceId();
		Map<String, String> nonceMap = new HashMap<String, String>();
		nonceMap.put("nonceId", nonceId);
		nonceMap.put("test", "noncetest");
		nonceMap.put("status", "1");
		try {
			redisNonceDao.createAdjNonce(nonceId, nonceMap);
			json = "{\"code\":\"0000\",\"msg\":\"success\"}";
		} catch (RedisException e) {
			json = "{\"code\":\""+e.getErrorCode()+"\",\"msg\":\""+e.getErrorMsg()+"\"}";
		}
		modelMap.put("json", json);
	}

	@Override
	public void redisNonceTypeADeleteSample(HttpSession session, HttpServletRequest request, HttpServletResponse response, ModelMap modelMap, Map<String, Object> params) throws Exception {
		String json = null;
		try {
			String nonceId = (String) params.get("nonceId");
			redisNonceDao.deleteAdjNonce(nonceId);
			json = "{\"code\":\"0000\",\"msg\":\"success\"}";
		} catch (RedisException e) {
			json = "{\"code\":\""+e.getErrorCode()+"\",\"msg\":\""+e.getErrorMsg()+"\"}";
		}
		modelMap.put("json", json);
	}

	@Override
	public void redisNonceTypeAUpdateSample(HttpSession session, HttpServletRequest request, HttpServletResponse response, ModelMap modelMap, Map<String, Object> params) throws Exception {
		String json = null;
		try {
			String nonceId = (String) params.get("nonceId");
			redisNonceDao.updateAdjNonceStatus(nonceId, "2");
			json = "{\"code\":\"0000\",\"msg\":\"success\"}";
		} catch (RedisException e) {
			json = "{\"code\":\""+e.getErrorCode()+"\",\"msg\":\""+e.getErrorMsg()+"\"}";
		}
		modelMap.put("json", json);
	}

	@Override
	public void redisNonceTypeASelectSample(HttpSession session, HttpServletRequest request, HttpServletResponse response, ModelMap modelMap, Map<String, Object> params) throws Exception {
		String json = null;
		try {
			String nonceId = (String) params.get("nonceId");
			redisNonceDao.selectAdjNonce(nonceId);
			json = "{\"code\":\"0000\",\"msg\":\"success\"}";
		} catch (RedisException e) {
			json = "{\"code\":\""+e.getErrorCode()+"\",\"msg\":\""+e.getErrorMsg()+"\"}";
		}
		modelMap.put("json", json);
	}

	@Override
	public void redisNonceTypeOInsertSample(HttpSession session, HttpServletRequest request, HttpServletResponse response, ModelMap modelMap, Map<String, Object> params) throws Exception {
		String json = null;
		String nonceId = KeyUtils.getNonceId();
		Map<String, String> nonceMap = new HashMap<String, String>();
		nonceMap.put("nonceId", nonceId);
		nonceMap.put("test", "noncetest");
		nonceMap.put("status", "1");
		try {
			redisNonceDao.createOneNonce(nonceId, nonceMap);
			json = "{\"code\":\"0000\",\"msg\":\"success\"}";
		} catch (RedisException e) {
			json = "{\"code\":\""+e.getErrorCode()+"\",\"msg\":\""+e.getErrorMsg()+"\"}";
		}
		modelMap.put("json", json);
	}

	@Override
	public void redisNonceTypeODeleteSample(HttpSession session, HttpServletRequest request, HttpServletResponse response, ModelMap modelMap, Map<String, Object> params) throws Exception {
		String json = null;
		try {
			String nonceId = (String) params.get("nonceId");
			redisNonceDao.deleteOneNonce(nonceId);
			json = "{\"code\":\"0000\",\"msg\":\"success\"}";
		} catch (RedisException e) {
			json = "{\"code\":\""+e.getErrorCode()+"\",\"msg\":\""+e.getErrorMsg()+"\"}";
		}
		modelMap.put("json", json);
	}

	@Override
	public void redisNonceTypeOUpdateSample(HttpSession session, HttpServletRequest request, HttpServletResponse response, ModelMap modelMap, Map<String, Object> params) throws Exception {
		String json = null;
		try {
			String nonceId = (String) params.get("nonceId");
			redisNonceDao.updateOneNonceStatus(nonceId, "2");
			json = "{\"code\":\"0000\",\"msg\":\"success\"}";
		} catch (RedisException e) {
			json = "{\"code\":\""+e.getErrorCode()+"\",\"msg\":\""+e.getErrorMsg()+"\"}";
		}
		modelMap.put("json", json);
	}

	@Override
	public void redisNonceTypeOSelectSample(HttpSession session, HttpServletRequest request, HttpServletResponse response, ModelMap modelMap, Map<String, Object> params) throws Exception {
		String json = null;
		try {
			String nonceId = (String) params.get("nonceId");
			redisNonceDao.selectOneNonce(nonceId);
			json = "{\"code\":\"0000\",\"msg\":\"success\"}";
		} catch (RedisException e) {
			json = "{\"code\":\""+e.getErrorCode()+"\",\"msg\":\""+e.getErrorMsg()+"\"}";
		}
		modelMap.put("json", json);
	}

}
