package ua.dp.dryzhyruk.coffee.machines.state.monitor.rest;

import lombok.extern.slf4j.Slf4j;
import ua.dp.dryzhyruk.coffee.machines.state.monitor.rest.dto.CoffeeCup;

import javax.ws.rs.*;
import javax.ws.rs.core.Response;

@Slf4j
@Path("/event-registrar")
@Produces("application/json")
@Consumes("application/json")
public class EventRegistrarResource {

    @PATCH
    @Path("/coffee-machine/{coffeeMachineId}/coffee-cap-produced")
    public void registerCoffee(
            @PathParam("coffeeMachineId") String coffeeMachineId,
            CoffeeCup coffeeCup) {

        log.info("Coffee machine: {} ==> {}", coffeeMachineId, coffeeCup);
    }

    @GET
    @Path("/coffee-machine/{coffeeMachineId}/coffee-cap-produced")
    public Response registerCoffee(
            @PathParam("coffeeMachineId") String coffeeMachineId) {

        log.info("Coffee machine: {}", coffeeMachineId);

        return Response
                .status(200)
                .entity(coffeeMachineId)
                .build();
    }
}
