package Api.proyectoFinalDWSDIW;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

@SpringBootApplication
public class ProyectoFinalDwsdiwApplication extends SpringBootServletInitializer {

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(ProyectoFinalDwsdiwApplication.class);
    }

    public static void main(String[] args) {
        SpringApplication.run(ProyectoFinalDwsdiwApplication.class, args);
    }
}
