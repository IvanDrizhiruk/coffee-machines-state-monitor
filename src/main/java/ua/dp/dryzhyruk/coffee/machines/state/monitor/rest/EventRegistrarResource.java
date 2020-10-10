package ua.dp.dryzhyruk.coffee.machines.state.monitor.rest;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import ua.dp.dryzhyruk.coffee.machines.state.monitor.core.CoffeeMachinesEventRegisterServices;
import ua.dp.dryzhyruk.coffee.machines.state.monitor.core.model.Cup;
import ua.dp.dryzhyruk.coffee.machines.state.monitor.rest.dto.CoffeeMachineLineServiceDTO;
import ua.dp.dryzhyruk.coffee.machines.state.monitor.rest.dto.CupDTO;

import javax.ws.rs.*;

@Slf4j
@Path("/event-registrar")
@Produces("application/json")
@Consumes("application/json")
public class EventRegistrarResource {

    private final CoffeeMachinesEventRegisterServices coffeeMachinesEventRegisterServices;

    @Autowired
    public EventRegistrarResource(CoffeeMachinesEventRegisterServices coffeeMachinesEventRegisterServices) {
        this.coffeeMachinesEventRegisterServices = coffeeMachinesEventRegisterServices;
    }

    @PATCH
    @Path("/coffee-machine/{coffeeMachineId}/cup-produced")
    public void onCoffeeCupProduced(
            @PathParam("coffeeMachineId") String coffeeMachineId,
            CupDTO cupDTO) {

        Cup cup = Cup.builder()
                .coffeePortions(cupDTO.getCoffeePortions())
                .withMilk(cupDTO.isWithMilk())
                .build();

        coffeeMachinesEventRegisterServices.registerNewMadeCup(coffeeMachineId, cup);
    }

    @PATCH
    @Path("/coffee-machine/{coffeeMachineId}/line-service")
    public void onLineService(
            @PathParam("coffeeMachineId") String coffeeMachineId,
            CoffeeMachineLineServiceDTO coffeeMachineLineServiceDTO) {

        coffeeMachinesEventRegisterServices.registerLineService(
                coffeeMachineId,
                coffeeMachineLineServiceDTO.isCoffeeBeansFilled(),
                coffeeMachineLineServiceDTO.isMilkFilled(),
                coffeeMachineLineServiceDTO.isTrashContainerCleaned());
    }
}
