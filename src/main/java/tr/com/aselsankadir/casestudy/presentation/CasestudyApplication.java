package tr.com.aselsankadir.casestudy.presentation;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication(scanBasePackages = {
		"tr.com.aselsankadir.casestudy.application",
		"tr.com.aselsankadir.casestudy.presentation",
		"tr.com.aselsankadir.casestudy.infrastructure",
})
@EnableJpaRepositories(basePackages = "tr.com.aselsankadir.casestudy.infrastructure.jpa")
@EntityScan(basePackages = "tr.com.aselsankadir.casestudy.infrastructure.jpa")
public class CasestudyApplication {

	public static void main(String[] args) {
		SpringApplication.run(CasestudyApplication.class, args);
	}

}
