package cl.usach.tvreactions.application;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class TvreactionsApplication {

	public static void main(String[] args) {
		SpringApplication.run(TvreactionsApplication.class, args);
	}
}
