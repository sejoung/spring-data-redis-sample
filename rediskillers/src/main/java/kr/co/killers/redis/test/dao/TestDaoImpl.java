package kr.co.killers.redis.test.dao;

import java.util.Map;

import javax.annotation.Resource;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Repository;

@Repository("TestDao")
public class TestDaoImpl implements TestDao {

	@Resource(name = "sqlSession")
	private SqlSessionTemplate testSqlSession; // inject the template


	@Override
	public Map<String, Object> selectTest(Map<String, Object> params) {
		return testSqlSession.selectOne("testDao.selectTest");
	}
	
}
