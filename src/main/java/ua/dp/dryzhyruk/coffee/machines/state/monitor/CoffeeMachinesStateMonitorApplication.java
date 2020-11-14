package ua.dp.dryzhyruk.coffee.machines.state.monitor;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.Clock;

@SpringBootApplication
@Configuration
public class CoffeeMachinesStateMonitorApplication {

    public static void main(String[] args) {
        SpringApplication.run(CoffeeMachinesStateMonitorApplication.class, args);
    }

    @Bean
    public Clock newClock() {
        return Clock.systemDefaultZone();
    }
}
