package cc.gzvtc.config.mybatis;

import java.util.Properties;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import cc.gzvtc.common.dao.BaseMapper;
import tk.mybatis.mapper.code.Style;
import tk.mybatis.spring.mapper.MapperScannerConfigurer;

/**
 * MyBatis扫描接口
 * 
 * @author liuzh
 * @since 2015-12-19 14:46
 */
@Configuration
//TODO 注意，由于MapperScannerConfigurer执行的比较早，所以必须有下面的注解
public class MyBatisMapperScannerConfig {

	@Bean(name="mapperHelper")  
    public MapperScannerConfigurer mapperHelper(){  
        Properties properties = new Properties();  
        properties.setProperty("mappers",BaseMapper.class.getName());  
        properties.setProperty("IDENTITY","MYSQL"); // 数据库方言（主要用于：取回主键的方式）  
        properties.setProperty("notEmpty","false"); // insert和update中，是否判断字符串类型!=''，少数方法会用到  
        properties.setProperty("style", Style.camelhump.name());  

        MapperScannerConfigurer scan = new MapperScannerConfigurer();  
        scan.setSqlSessionFactoryBeanName("sqlSessionFactory"); // 多数据源时，必须配置  
        scan.setBasePackage("cc.gzvtc.*.dao");//mapper.java文件的路径  
        scan.setMarkerInterface(BaseMapper.class); // 直接继承了BaseDao接口的才会被扫描，basePackage可以配置的范围更大。  
        scan.setProperties(properties);  

        return scan;  
    }  

}
