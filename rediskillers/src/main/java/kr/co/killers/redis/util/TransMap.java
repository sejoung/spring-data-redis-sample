package kr.co.killers.redis.util;


import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TransMap {
	private static final Logger log = LoggerFactory.getLogger(TransMap.class);
	
	
	public static Map<String,String> transMap(Map<Object,Object> param) {
		log.debug("transMap start");
		Map<String,String> map = new HashMap<String, String>();
		for(Object key : param.keySet()){
			map.put(String.valueOf(key), String.valueOf(param.get(key)));
		}
		log.debug("transMap end");
		return map;
	}
	

}
