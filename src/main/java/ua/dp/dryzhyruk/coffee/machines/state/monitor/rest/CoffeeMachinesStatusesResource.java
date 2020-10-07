package ua.dp.dryzhyruk.coffee.machines.state.monitor.rest;

import lombok.extern.slf4j.Slf4j;
import ua.dp.dryzhyruk.coffee.machines.state.monitor.rest.dto.CoffeeMachinesStatus;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Path("/coffee-machine")
@Produces("application/json")
@Consumes("application/json")
public class CoffeeMachinesStatusesResource {

    @GET
    @Path("/all/status")
    public List<CoffeeMachinesStatus> getAllCoffeeMachinesStatuses() {
        Map<String, CoffeeMachinesStatus> coffeeMachinesStatuses = new HashMap<>();

        CoffeeMachinesStatus coffeeMachine1 = CoffeeMachinesStatus.builder()
                .coffeeMachineName("#1")
                .build();

        return Collections.singletonList(coffeeMachine1);
    }
}