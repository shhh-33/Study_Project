package itstudy.study;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.filter.HiddenHttpMethodFilter;

//@EnableJpaAuditing
@SpringBootApplication

public class StudyApplication {

	public static void main(String[] args) {


		SpringApplication.run(StudyApplication.class,args);
	}

	@Bean
	public HiddenHttpMethodFilter hiddenHttpMethodFilter() {
		return new HiddenHttpMethodFilter();
	}
}
