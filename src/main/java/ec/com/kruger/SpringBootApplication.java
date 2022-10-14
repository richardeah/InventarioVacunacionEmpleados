package ec.com.kruger;

import ec.com.kruger.configuration.SwaggerConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.context.annotation.Import;

@org.springframework.boot.autoconfigure.SpringBootApplication
//@Import({SwaggerConfiguration.class})
public class SpringBootApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringBootApplication.class, args);
	}

}
