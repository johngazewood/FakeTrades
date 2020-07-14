package faketrades.persistence;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class FakeTradesPersistenceStartup {

	@Autowired
	Listener listener;

	public static void main(String[] args) {
		SpringApplication.run(FakeTradesPersistenceStartup.class, args);
	}

	@Bean
	public Boolean listening() {
		listener.listen();
		return true;
	}

}
