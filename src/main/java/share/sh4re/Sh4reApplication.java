package share.sh4re;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class Sh4reApplication {
	public static void main(String[] args) {
		SpringApplication.run(Sh4reApplication.class, args);
	}

}
