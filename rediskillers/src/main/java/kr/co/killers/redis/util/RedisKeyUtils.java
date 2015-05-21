package kr.co.killers.redis.util;

public class RedisKeyUtils {

	/**
	 * 서비스코드 Session Confirm & Nonce의 약자
	 */
	static final String SCN = "scn:";

	/**
	 * 업무코드 Session 처리 업무
	 */
	static final String SESSION = "sess:";

	/**
	 * Nonce 처리 업무
	 */
	static final String NONCE = "nonce:";

	/**
	 * Information
	 */
	static final String INFORMATION = "info:";

	/**
	 * 개수
	 */
	static final String COUNT = "cnt:";

	/**
	 * OneTime Nonce 처리 업무
	 */
	static final String ONETIMENONCE = "one:";

	/**
	 * Adjustable Nonce 처리 업무
	 */
	static final String ADJUSTABLENONCE = "adj:";

	/**
	 * 세션 정보 조회의 키
	 * 
	 * @param sessionId
	 * @return scn:sess:cnt:{sessionId}
	 */
	public static String scnSessionInfo(String sessionId) {
		return SCN + SESSION + INFORMATION + sessionId;
	}

	/**
	 * ImoryID 세션수 조회
	 * 
	 * @param imoryId
	 * @return scn:sess:cnt:{imoryId}
	 */
	public static String scnSessionCnt(String imoryId) {
		return SCN + SESSION + COUNT + imoryId;
	}

	/**
	 * One Time Nonce 정보
	 * 
	 * @param nonceId
	 * @return scn:nonce:one:{nonceId}
	 */
	public static String scnNonceOne(String nonceId) {
		return SCN + NONCE + ONETIMENONCE + nonceId;
	}

	/**
	 * Adjustable Nonce 정보
	 * 
	 * @param nonceId
	 * @return scn:nonce:adj:{nonceId}
	 */
	public static String scnNonceAdj(String nonceId) {
		return SCN + NONCE + ADJUSTABLENONCE + nonceId;
	}

}
