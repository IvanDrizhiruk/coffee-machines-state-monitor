package ua.dp.dryzhyruk.coffee.machines.state.monitor.configuration;

import org.glassfish.jersey.server.ResourceConfig;
import org.springframework.stereotype.Component;
import ua.dp.dryzhyruk.coffee.machines.state.monitor.rest.CoffeeMachinesStatusesResource;
import ua.dp.dryzhyruk.coffee.machines.state.monitor.rest.EventRegistrarResource;

@Component
public class JerseyConfig extends ResourceConfig {

    public JerseyConfig() {
        register(EventRegistrarResource.class);
        register(CoffeeMachinesStatusesResource.class);
    }
}