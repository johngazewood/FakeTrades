package faketrades.persistence;

import java.io.IOException;
import java.io.InputStream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
//@PropertySource(value = "classpath:production/application.properties", ignoreResourceNotFound = false)
//@PropertySource(value = "classpath:common.properties", ignoreResourceNotFound = false)
public class FakeTradesPersistenceStartup {

	@Autowired
	Listener listener;

	public static void main(String[] args) {
		prepareProperties(args);
		SpringApplication.run(FakeTradesPersistenceStartup.class, args);
	}

	@Bean
	public Boolean listening() {
		listener.startListening();
		return true;
	}

	static void prepareProperties(String[] args) {
		String env = "production";
		if (args.length > 0) {
			// other options: test, development
			env = args[0];
		}
		//String propertiesFile = "src/main/resources/" + env + "/application.properties";
		String propertiesFile = "/" + env + "/application.properties";
		loadProperties(propertiesFile);

		propertiesFile = "/common.properties";
		loadProperties(propertiesFile);

	}

	private static void loadProperties(String propertiesFile) {
		try {
			InputStream is = FakeTradesPersistenceStartup.class.getResourceAsStream(propertiesFile);
			System.getProperties().load(is);
		} catch (IOException e) {
			e.printStackTrace();
			System.exit(-1);
		}
	}

}
