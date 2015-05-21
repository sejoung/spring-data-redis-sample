package kr.co.killers.redis.test.dao;

import java.util.Map;

import kr.co.killers.redis.exception.RedisException;

public interface RedisDao {
	
	/**
	 * <pre>
	 * 세션 등록
	 * @param sessionId
	 * @param imoryId
	 * @param session { session_id : 세션id, auth_id : 인증 id, userid : 사용자 id, id : LGU+ 사용자 고유 id(imoryid),
	 * 					reg_date : 세션 생성 시간, expire_date : 세션 만료 시간, hold_req_date : 세션 연장 요청 시간,
	 * 					auth_type : 인증 타입 구분(0 : 로그인 api, 1 : oauth) }
	 * @throws RedisException {1000: redis서버에 접속이 안됨,  9999:알수없는 오류}
	 * 
	 * </pre>
	 * 
	 */
	void createSession(String sessionId, String imoryId, Map<String, String> sessionMap) throws RedisException;

	/**
	 * <pre>
	 * 세션 조회
	 * @param sessionId
	 * @return Map { session_id : 세션id, auth_id : 인증 id, userid : 사용자 id, id : LGU+ 사용자 고유 id(imoryid),
	 * 					reg_date : 세션 생성 시간, expire_date : 세션 만료 시간, hold_req_date : 세션 연장 요청 시간,
	 * 					auth_type : 인증 타입 구분(0 : 로그인 api, 1 : oauth) }
	 * @throws RedisException {1000: redis서버에 접속이 안됨,  9999:알수없는 오류}
	 * </pre>
	 */
	Map<String, String> selectSession(String sessionId) throws RedisException;
	
	/**
	 * 발급된 세션 발급 가능 여부 
	 * @param imoryId
	 * @return boolean {true : 발급가능, false : 발급불가능}
	 * @throws RedisException {1000: redis서버에 접속이 안됨 , 9999:알수없는 오류}
	 */
	int selectSessionCount(String imoryId) throws RedisException;
	
	/**
	 * 세션 삭제 count를 DB에서 조회 시 사용 해야 함
	 * @param sessionId
	 * @param imoryId
	 * @throws RedisException {1000: redis서버에 접속이 안됨,  9999:알수없는 오류}
	 */
	void deleteSession(String sessionId, String imoryId) throws RedisException;
	
	
	/**
	 * 세션 삭제
	 * @param sessionId
	 * @throws RedisException {1000: redis서버에 접속이 안됨,  9999:알수없는 오류}
	 */
	void deleteSession(String sessionId) throws RedisException;
	
	/**
	 * 세션연장 
	 * 
	 * @param sessionId
	 * @param hour
	 * @throws RedisException {1000: redis서버에 접속이 안됨,  9999:알수없는 오류}
	 */
	void updateSessionExpriedTime(String sessionId, int hour) throws RedisException;

	/**
	 * onetime nonce 생성 
	 * 
	 * @param nonceId
	 * @param nonce
	 * @throws RedisException {1000: redis서버에 접속이 안됨,  9999:알수없는 오류}
	 */
	void createOneNonce(String nonceId, Map<String, String> nonceMap) throws RedisException;

	/**
	 * adjustable nonce 생성
	 * @param nonceId
	 * @param nonce
	 * @throws RedisException {1000: redis서버에 접속이 안됨,  9999:알수없는 오류}
	 */
	void createAdjNonce(String nonceId, Map<String, String> nonceMap) throws RedisException;

	/**
	 * onetime nonce 조회
	 * @param nonceId
	 * @return
	 * @throws RedisException {1000: redis서버에 접속이 안됨,  9999:알수없는 오류}
	 */
	Map<String, String> selectOneNonce(String nonceId) throws RedisException;

	/**
	 * adjustable nonce 조회
	 * @param nonceId
	 * @return
	 * @throws RedisException {1000: redis서버에 접속이 안됨,  9999:알수없는 오류}
	 */
	Map<String, String> selectAdjNonce(String nonceId) throws RedisException;

	/**
	 * onetime nonce 상태변경
	 * 
	 * @param nonceId
	 * @param status {0:생성, 1:확인, 2:변경, 3:폐기}
	 * @throws RedisException {1000: redis서버에 접속이 안됨,  9999:알수없는 오류}
	 */
	void updateOneNonceStatus(String nonceId, String status) throws RedisException;
	
	/**
	 * adjustable nonce 상태변경
	 * 
	 * @param nonceId
	 * @param status {0:생성, 1:확인, 2:변경, 3:폐기}
	 * @throws RedisException {1000: redis서버에 접속이 안됨,  9999:알수없는 오류}
	 */
	void updateAdjNonceStatus(String nonceId, String status) throws RedisException;

	/**
	 * onetime nonce 삭제
	 * 
	 * @param nonceId
	 * @throws RedisException
	 */
	void deleteOneNonce(String nonceId) throws RedisException;

	/**
	 * adjustable nonce 삭제
	 * @param nonceId
	 * @throws RedisException
	 */
	void deleteAdjNonce(String nonceId) throws RedisException;
	
}
