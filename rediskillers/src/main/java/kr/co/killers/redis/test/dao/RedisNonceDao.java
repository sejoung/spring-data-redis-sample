package kr.co.killers.redis.test.dao;

import java.util.Map;

import kr.co.killers.redis.exception.RedisException;

/**
 * Redis Doa 인터페이스
 *  
 * @author sanaes 
 */
public interface RedisNonceDao {
	

	/**
	 * onetime nonce 생성 
	 * 
	 * @param nonceId
	 * @param nonceMap
	 *
	 * 
	 * @throws RedisException {1000: redis서버에 접속이 안됨,  9999:알수없는 오류}
	 */
	void createOneNonce(String nonceId, Map<String, String> nonceMap) throws RedisException;

	/**
	 * adjustable nonce 생성
	 * @param nonceId
	 * @param nonceMap
	 * 	
	 * 
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
