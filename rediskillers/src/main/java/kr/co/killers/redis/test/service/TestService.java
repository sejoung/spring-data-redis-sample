package kr.co.killers.redis.test.service;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestParam;

public interface TestService {

	void redisSessonInsertSample(HttpSession session, HttpServletRequest request, HttpServletResponse response, ModelMap modelMap, @RequestParam Map<String, Object> params)throws Exception;

	void redisSessionDeleteSample(HttpSession session, HttpServletRequest request, HttpServletResponse response, ModelMap modelMap, @RequestParam Map<String, Object> params)throws Exception;

	void redisSessionUpdateSample(HttpSession session, HttpServletRequest request, HttpServletResponse response, ModelMap modelMap, @RequestParam Map<String, Object> params)throws Exception;

	void redisSessionSelectSample(HttpSession session, HttpServletRequest request, HttpServletResponse response, ModelMap modelMap, @RequestParam Map<String, Object> params)throws Exception;
	
	void redisNonceTypeAInsertSample(HttpSession session, HttpServletRequest request, HttpServletResponse response, ModelMap modelMap, @RequestParam Map<String, Object> params)throws Exception;

	void redisNonceTypeADeleteSample(HttpSession session, HttpServletRequest request, HttpServletResponse response, ModelMap modelMap, @RequestParam Map<String, Object> params)throws Exception;

	void redisNonceTypeAUpdateSample(HttpSession session, HttpServletRequest request, HttpServletResponse response, ModelMap modelMap, @RequestParam Map<String, Object> params)throws Exception;

	void redisNonceTypeASelectSample(HttpSession session, HttpServletRequest request, HttpServletResponse response, ModelMap modelMap, @RequestParam Map<String, Object> params)throws Exception;

	void redisNonceTypeOInsertSample(HttpSession session, HttpServletRequest request, HttpServletResponse response, ModelMap modelMap, @RequestParam Map<String, Object> params)throws Exception;

	void redisNonceTypeODeleteSample(HttpSession session, HttpServletRequest request, HttpServletResponse response, ModelMap modelMap, @RequestParam Map<String, Object> params)throws Exception;

	void redisNonceTypeOUpdateSample(HttpSession session, HttpServletRequest request, HttpServletResponse response, ModelMap modelMap, @RequestParam Map<String, Object> params)throws Exception;

	void redisNonceTypeOSelectSample(HttpSession session, HttpServletRequest request, HttpServletResponse response, ModelMap modelMap, @RequestParam Map<String, Object> params)throws Exception;

	void redisSessionCountSample(HttpSession session, HttpServletRequest request, HttpServletResponse response, ModelMap modelMap, Map<String, Object> params) throws Exception;

}
