package ua.dp.dryzhyruk.coffee.machines.state.monitor.rest;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import ua.dp.dryzhyruk.coffee.machines.state.monitor.core.CoffeeMachinesStatusService;
import ua.dp.dryzhyruk.coffee.machines.state.monitor.core.model.CoffeeMachineState;
import ua.dp.dryzhyruk.coffee.machines.state.monitor.rest.dto.CoffeeMachineStateDTO;

import javax.ws.rs.*;
import java.time.Clock;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Path("/coffee-machine")
@Produces("application/json")
@Consumes("application/json")
public class CoffeeMachinesStatusesResource {

    private final Clock clock;
    private final CoffeeMachinesStatusService coffeeMachinesStatusService;

    @Autowired
    public CoffeeMachinesStatusesResource(Clock clock, CoffeeMachinesStatusService coffeeMachinesStatusService) {
        this.clock = clock;
        this.coffeeMachinesStatusService = coffeeMachinesStatusService;
    }

    @GET
    @Path("/all/status")
    public List<CoffeeMachineStateDTO> getAllCoffeeMachinesStatuses() {
        return coffeeMachinesStatusService.getAllCoffeeMachinesStatus().stream()
                .map(this::convert)
                .collect(Collectors.toList());
    }

    @GET
    @Path("/{coffeeMachineId}/status")
    public CoffeeMachineStateDTO getCoffeeMachinesStatus(
            @PathParam("coffeeMachineId") String coffeeMachineId) {
        CoffeeMachineState coffeeMachinesStatus = coffeeMachinesStatusService.getCoffeeMachinesStatus(coffeeMachineId);

        return convert(coffeeMachinesStatus);
    }

    private CoffeeMachineStateDTO convert(CoffeeMachineState coffeeMachinesStatus) {
        return CoffeeMachineStateDTO.builder()
                .coffeeMachineId(coffeeMachinesStatus.getCoffeeMachineId())

                .coffeeBeansLeftForNPortions(coffeeMachinesStatus.getCoffeeBeansLeftForNPortions())
                .coffeeBeansStatus(coffeeMachinesStatus.getCoffeeBeansStatus())

                .milkLeftForNPortions(coffeeMachinesStatus.getMilkLeftForNPortions())
                .milkStatus(coffeeMachinesStatus.getMilkStatus())

                .placeInTrashContainerLeftForNPortions(coffeeMachinesStatus.getPlaceInTrashContainerLeftForNPortions())
                .placesInTrashContainerStatus(coffeeMachinesStatus.getPlacesInTrashContainerStatus())

                .timeOfCalculation(LocalDateTime.now(clock))

                .build();
    }
}