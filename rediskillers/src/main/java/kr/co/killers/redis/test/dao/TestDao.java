package kr.co.killers.redis.test.dao;

import java.util.Map;

public interface TestDao {
	Map<String, Object> selectTest( Map<String, Object> params );

}
