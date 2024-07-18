package ge.bog.hw22;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling

public class Hw22Application {

	public static void main(String[] args) {
		SpringApplication.run(Hw22Application.class, args);
	}

}