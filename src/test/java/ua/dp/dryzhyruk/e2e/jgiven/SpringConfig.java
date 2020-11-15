package ua.dp.dryzhyruk.e2e.jgiven;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import com.tngtech.jgiven.integration.spring.EnableJGiven;

@EnableJGiven
@Configuration
@ComponentScan(basePackages = {"ua.dp.dryzhyruk.e2e.jgiven"})
public class SpringConfig {
}
