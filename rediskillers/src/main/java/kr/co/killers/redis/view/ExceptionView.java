package kr.co.killers.redis.view;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.view.AbstractView;

public class ExceptionView extends AbstractView {

	@Override
	protected void renderMergedOutputModel(Map<String, Object> modelMap, HttpServletRequest req, HttpServletResponse res) throws Exception {
		HeaderHandler handler = new HeaderHandler(req, res, modelMap);
		handler.setExceptionHeaderData();
	}

}
