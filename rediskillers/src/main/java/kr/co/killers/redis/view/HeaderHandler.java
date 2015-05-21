package kr.co.killers.redis.view;

import java.io.IOException;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

public class HeaderHandler {
	private static Logger logger = LoggerFactory.getLogger(HeaderHandler.class);

	private HttpServletRequest req;
	private HttpServletResponse res;
	private Map<String, Object> modelMap;
	private Map<String, Object> resultMap;
	private String reqUrl = "";

	public HeaderHandler(HttpServletRequest req, HttpServletResponse res, Map<String, Object> modelMap) {
		this.req = req;
		this.res = res;
		this.modelMap = modelMap;
		this.reqUrl = (String) req.getAttribute("adpUrl");

		if (!StringUtils.isEmpty(modelMap)) {
			resultMap = (Map<String, Object>) modelMap.get("resultMap");
		}
	}

	/**
	 * 정상처리시
	 * 
	 * @throws Exception
	 */
	public void setHeaderData() throws Exception {
		this.setSucessHeader();
	}

	/**
	 * Exception 발생시
	 * 
	 * @throws Exception
	 */
	public void setExceptionHeaderData() throws Exception {
		this.setExceptionHeader();
	}

	/**
	 * Result Success
	 */
	private void setSucessHeader() {
		res.setHeader("Result-Code", "0000");
		res.setHeader("Result-Desc", "성공");
		res.addHeader("Access-Control-Allow-Origin", "*");
	}

	private void SetExceptionCommon() throws IOException {
		String resultCode = (String) req.getAttribute("resultCode");
		String json = "";

		json = "error|" + resultCode;

		res.setContentType("text/html;charset=UTF-8");
		res.setHeader("Cache-Control", "no-cache"); // HTTP 1.1
		res.setHeader("Pragma", "no-cache"); // HTTP 1.0

		res.setContentLength(json.getBytes("utf-8").length);
		res.getWriter().print(json.toString());

	}

	/**
	 * Result Error
	 */
	private void setExceptionHeader() {
		String resultCode = req.getAttribute("resultCode").toString();
		logger.debug("##############################");
		res.setHeader("Result-Code", "error");
		res.setHeader("Result-Desc", "error");
		res.addHeader("Access-Control-Allow-Origin", "*");
	}

}
