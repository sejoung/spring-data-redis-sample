package kr.co.killers.redis.util;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class CommonUtil {
	
	private static Gson GSON = new GsonBuilder().disableHtmlEscaping().create();
	
	/**
	 * Vo Object 직렬화
	 * @param obj
	 * @return
	 */
	public static String generateJson(Object obj) {
		return GSON.toJson(obj);
	}
	
    /**
     * requestBody를 객체로 변환한다.
     * 
     * @param body
     * @return
     */
    public static Object parseRequestJson(String body,  Class<?> classzz) {
        return GSON.fromJson(body, classzz);
    }	
	
	

}
