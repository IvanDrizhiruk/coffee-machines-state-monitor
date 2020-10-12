package ua.dp.dryzhyruk.coffee.machines.state.monitor.core;

import org.springframework.stereotype.Service;
import ua.dp.dryzhyruk.coffee.machines.state.monitor.core.calculator.StateCalculator;
import ua.dp.dryzhyruk.coffee.machines.state.monitor.core.model.CoffeeMachineState;
import ua.dp.dryzhyruk.coffee.machines.state.monitor.core.model.Status;
import ua.dp.dryzhyruk.coffee.machines.state.monitor.storage.api.entity.CoffeeMachineConfiguration;
import ua.dp.dryzhyruk.coffee.machines.state.monitor.storage.api.entity.CoffeeMachineStateEntity;

@Service
public class CoffeeMachinesStatusCalculator {

    public CoffeeMachineState calculate(
            CoffeeMachineStateEntity coffeeMachineState,
            CoffeeMachineConfiguration coffeeMachineConfiguration) {

        Status coffeeBeansStatus = new StateCalculator(
                coffeeMachineConfiguration.getMaxNumberCoffeeBeansPortions(),
                coffeeMachineConfiguration.getCoffeeBeansWarningLevel(),
                coffeeMachineConfiguration.getCoffeeBeansCriticalLevel())
                .calculateState(coffeeMachineState.getCoffeeBeansLeftForNPortions());

        Status milkStatus = new StateCalculator(
                coffeeMachineConfiguration.getMaxNumberMilkPortions(),
                coffeeMachineConfiguration.getMilkWarningLevel(),
                coffeeMachineConfiguration.getMilkCriticalLevel())
                .calculateState(coffeeMachineState.getMilkLeftForNPortions());

        Status placesInTrashContainerStatus = new StateCalculator(
                coffeeMachineConfiguration.getMaxNumberPlacesInTrashContainer(),
                coffeeMachineConfiguration.getPlacesInTrashContainerWarningLevel(),
                coffeeMachineConfiguration.getPlacesInTrashContainerCriticalLevel())
                .calculateState(coffeeMachineState.getPlaceInTrashContainerLeftForNPortions());


        return CoffeeMachineState.builder()
                .coffeeMachineId(coffeeMachineState.getCoffeeMachineId())

                .coffeeBeansLeftForNPortions(coffeeMachineState.getCoffeeBeansLeftForNPortions())
                .coffeeBeansStatus(coffeeBeansStatus)

                .milkLeftForNPortions(coffeeMachineState.getMilkLeftForNPortions())
                .milkStatus(milkStatus)

                .placeInTrashContainerLeftForNPortions(coffeeMachineState.getPlaceInTrashContainerLeftForNPortions())
                .placesInTrashContainerStatus(placesInTrashContainerStatus)

                .build();
    }
}
