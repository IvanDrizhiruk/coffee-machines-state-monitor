package ua.dp.dryzhyruk.coffee.machines.state.monitor.rest;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import ua.dp.dryzhyruk.coffee.machines.state.monitor.core.CoffeeMachinesStatusService;
import ua.dp.dryzhyruk.coffee.machines.state.monitor.rest.dto.CoffeeMachineStateExtended;

import javax.ws.rs.*;
import java.util.List;

@Slf4j
@Path("/coffee-machine")
@Produces("application/json")
@Consumes("application/json")
public class CoffeeMachinesStatusesResource {

    private final CoffeeMachinesStatusService coffeeMachinesStatusService;

    @Autowired
    public CoffeeMachinesStatusesResource(CoffeeMachinesStatusService coffeeMachinesStatusService) {
        this.coffeeMachinesStatusService = coffeeMachinesStatusService;
    }

    @GET
    @Path("/all/status")
    public List<CoffeeMachineStateExtended> getAllCoffeeMachinesStatuses() {
        return coffeeMachinesStatusService.getAllCoffeeMachinesStatus();
    }

    @GET
    @Path("/{coffeeMachineId}/status")
    public CoffeeMachineStateExtended getCoffeeMachinesStatus(
            @PathParam("coffeeMachineId") String coffeeMachineId) {
        return coffeeMachinesStatusService.getCoffeeMachinesStatus(coffeeMachineId);
    }
}