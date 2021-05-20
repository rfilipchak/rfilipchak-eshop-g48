package ua.mainacademy;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EnableSwagger2
public class EshopApplication {

    public static void main(String[] args) {
        SpringApplication.run(EshopApplication.class, args);
    }

}
