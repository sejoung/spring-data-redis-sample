package kr.co.killers.redis.test.controller;

import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import kr.co.killers.redis.test.service.TestService;
import kr.co.killers.redis.view.JsonView;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.View;

@Controller
public class TestController {
	private static final Logger log = LoggerFactory.getLogger(TestController.class);

	@Resource(name = "TestService")
	private TestService testService;

	@RequestMapping(value = "/createSession.do")
	public View redisSessonInsertSample(HttpSession session, HttpServletRequest request, HttpServletResponse response, @RequestParam Map<String, Object> params, ModelMap modelMap) throws Exception {
		log.info("parameters : " + params);
		testService.redisSessonInsertSample(session, request, response, modelMap, params);
		return new JsonView();
	}

	@RequestMapping(value = "/getSession.do")
	public View redisSessionSelectSample(HttpSession session, HttpServletRequest request, HttpServletResponse response, @RequestParam Map<String, Object> params, ModelMap modelMap) throws Exception {
		log.info("parameters : " + params);
		testService.redisSessionSelectSample(session, request, response, modelMap, params);
		return new JsonView();
	}

	@RequestMapping(value = "/getSessionCount.do")
	public View redisSessionCountSample(HttpSession session, HttpServletRequest request, HttpServletResponse response, @RequestParam Map<String, Object> params, ModelMap modelMap) throws Exception {
		log.info("parameters : " + params);
		testService.redisSessionCountSample(session, request, response, modelMap, params);
		return new JsonView();
	}

	@RequestMapping(value = "/delSessionDelete.do")
	public View redisSessionDeleteSample(HttpSession session, HttpServletRequest request, HttpServletResponse response, @RequestParam Map<String, Object> params, ModelMap modelMap) throws Exception {
		log.info("parameters : " + params);
		testService.redisSessionDeleteSample(session, request, response, modelMap, params);
		return new JsonView();
	}

	@RequestMapping(value = "/setSessionUpdate.do")
	public View redisSessionUpdateSample(HttpSession session, HttpServletRequest request, HttpServletResponse response, @RequestParam Map<String, Object> params, ModelMap modelMap) throws Exception {
		log.info("parameters : " + params);
		testService.redisSessionUpdateSample(session, request, response, modelMap, params);
		return new JsonView();
	}

	@RequestMapping(value = "/delNonceTypeA.do")
	public View redisNonceTypeADeleteSample(HttpSession session, HttpServletRequest request, HttpServletResponse response, @RequestParam Map<String, Object> params, ModelMap modelMap) throws Exception {
		log.info("parameters : " + params);
		testService.redisNonceTypeADeleteSample(session, request, response, modelMap, params);
		return new JsonView();
	}

	@RequestMapping(value = "/delNonceTypeO.do")
	public View redisNonceTypeODeleteSample(HttpSession session, HttpServletRequest request, HttpServletResponse response, @RequestParam Map<String, Object> params, ModelMap modelMap) throws Exception {
		log.info("parameters : " + params);
		testService.redisNonceTypeODeleteSample(session, request, response, modelMap, params);
		return new JsonView();
	}

	@RequestMapping(value = "/createNonceTypeA.do")
	public View redisNonceTypeAInsertSample(HttpSession session, HttpServletRequest request, HttpServletResponse response, @RequestParam Map<String, Object> params, ModelMap modelMap) throws Exception {
		log.info("parameters : " + params);
		testService.redisNonceTypeAInsertSample(session, request, response, modelMap, params);
		return new JsonView();
	}

	@RequestMapping(value = "/createNonceTypeO.do")
	public View redisNonceTypeOInsertSample(HttpSession session, HttpServletRequest request, HttpServletResponse response, @RequestParam Map<String, Object> params, ModelMap modelMap) throws Exception {
		log.info("parameters : " + params);
		testService.redisNonceTypeOInsertSample(session, request, response, modelMap, params);
		return new JsonView();
	}

	@RequestMapping(value = "/getNonceTypeA.do")
	public View redisNonceTypeASelectSample(HttpSession session, HttpServletRequest request, HttpServletResponse response, @RequestParam Map<String, Object> params, ModelMap modelMap) throws Exception {
		log.info("parameters : " + params);
		testService.redisNonceTypeASelectSample(session, request, response, modelMap, params);
		return new JsonView();
	}

	@RequestMapping(value = "/getNonceTypeO.do")
	public View redisNonceTypeOSelectSample(HttpSession session, HttpServletRequest request, HttpServletResponse response, @RequestParam Map<String, Object> params, ModelMap modelMap) throws Exception {
		log.info("parameters : " + params);
		testService.redisNonceTypeOSelectSample(session, request, response, modelMap, params);
		return new JsonView();
	}

	@RequestMapping(value = "/setNonceTypeA.do")
	public View redisNonceTypeAUpdateSample(HttpSession session, HttpServletRequest request, HttpServletResponse response, @RequestParam Map<String, Object> params, ModelMap modelMap) throws Exception {
		log.info("parameters : " + params);
		testService.redisNonceTypeAUpdateSample(session, request, response, modelMap, params);
		return new JsonView();
	}

	@RequestMapping(value = "/setNonceTypeO.do")
	public View redisNonceTypeOUpdateSample(HttpSession session, HttpServletRequest request, HttpServletResponse response, @RequestParam Map<String, Object> params, ModelMap modelMap) throws Exception {
		log.info("parameters : " + params);
		testService.redisNonceTypeOUpdateSample(session, request, response, modelMap, params);
		return new JsonView();
	}

}
