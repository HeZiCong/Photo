package cc.gzvtc.config.mybatis;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.github.abel533.sql.SqlMapper;

@Configuration
@ConditionalOnClass({SqlMapper.class})
public class SqlMapperConfiguration {

	@Autowired
	private SqlSession sqlSession;
	
	public SqlMapperConfiguration(){
	}
	
	@Bean
	@ConditionalOnMissingBean
	public SqlMapper sqlMapper(){
		return new SqlMapper(sqlSession);
	}
}
