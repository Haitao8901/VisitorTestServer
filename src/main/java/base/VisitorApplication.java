package base;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {"base", "visitor", "websocket"})
public class VisitorApplication {
    public static void main(String[] args){
        SpringApplication.run(VisitorApplication.class);
    }
}
