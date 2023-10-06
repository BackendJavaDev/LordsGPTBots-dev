package LordsGPTbot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class LordGPTBotApp {

    public static void main(String[] args) {
/*       SpringApplication app = new SpringApplication(LordsGPTBotApplication.class);
      app.setDefaultProperties(Collections.singletonMap("server.port", "8080"));
        app.run(args);*/

        SpringApplication.run(LordGPTBotApp.class, args);
    }

}
