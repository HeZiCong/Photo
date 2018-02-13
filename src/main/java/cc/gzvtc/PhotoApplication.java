package cc.gzvtc;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@MapperScan("cc.gzvtc.mappers")
@ServletComponentScan
@SpringBootApplication
@EnableTransactionManagement
public class PhotoApplication {

	public static void main(String[] args) {
		SpringApplication.run(PhotoApplication.class, args);
	}
}
