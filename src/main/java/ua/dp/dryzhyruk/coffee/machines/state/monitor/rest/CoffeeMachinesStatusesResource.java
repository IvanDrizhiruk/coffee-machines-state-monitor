package ua.dp.dryzhyruk.coffee.machines.state.monitor.rest;

import lombok.extern.slf4j.Slf4j;
import ua.dp.dryzhyruk.coffee.machines.state.monitor.rest.dto.CoffeeMachinesStatus;

import javax.ws.rs.*;
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

        CoffeeMachinesStatus coffeeMachine1 = CoffeeMachinesStatus.builder()
                .coffeeMachineName("#1")
                .build();

        return Collections.singletonList(coffeeMachine1);
    }

    @GET
    @Path("/{coffeeMachineId}/status")
    public CoffeeMachinesStatus getCoffeeMachinesStatus(
            @PathParam("coffeeMachineId") String coffeeMachineId) {

        CoffeeMachinesStatus coffeeMachine = CoffeeMachinesStatus.builder()
                .coffeeMachineName(coffeeMachineId)
                .build();

        return coffeeMachine;
    }
}