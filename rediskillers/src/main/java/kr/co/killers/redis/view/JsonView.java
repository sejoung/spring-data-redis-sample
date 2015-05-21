package kr.co.killers.redis.view;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import kr.co.killers.redis.constants.CommonConstants;

import org.springframework.web.servlet.view.AbstractView;

public class JsonView extends AbstractView {

	@Override
	protected void renderMergedOutputModel(Map<String, Object> modelMap, HttpServletRequest req, HttpServletResponse res) throws Exception {

		String contentType = CommonConstants.CONTENT_TYPE_JSON;

		String outStr = (String) ("".equals(modelMap.get("json")) ? req.getAttribute("json") : modelMap.get("json"));

		res.setContentType(contentType + ";charset=utf-8");
		res.setHeader("Cache-Control", "no-cache"); // HTTP 1.1
		res.setHeader("Pragma", "no-cache"); // HTTP 1.0

		HeaderHandler handler = new HeaderHandler(req, res, modelMap);
		handler.setHeaderData();
		res.setDateHeader("Expires", 100);

		StringBuffer json = new StringBuffer();
		json.append(outStr);
		res.setContentLength(json.toString().getBytes("utf-8").length);
		res.getWriter().print(json.toString());

	}

}
