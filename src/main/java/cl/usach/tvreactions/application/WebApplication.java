//package cl.citiaps.spring.backend.application;
package cl.usach.tvreactions.application;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@ComponentScan({"cl.usach.tvreactions.application", "cl.usach.tvreactions.rest"})
@EntityScan("cl.usach.tvreactions.entities")
@EnableJpaRepositories("cl.usach.tvreactions.repository")
public class WebApplication extends SpringBootServletInitializer{
	
	@Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(WebApplication.class);
    }

	public static void main(String[] args) {
		SpringApplication.run(WebApplication.class, args);
	}
}