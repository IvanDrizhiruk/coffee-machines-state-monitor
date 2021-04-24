package ua.dp.dryzhyruk.coffee.machines.state.monitor.core;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.dp.dryzhyruk.coffee.machines.state.monitor.core.model.Cup;
import ua.dp.dryzhyruk.coffee.machines.state.monitor.storage.api.CoffeeMachineConfigurationStorage;
import ua.dp.dryzhyruk.coffee.machines.state.monitor.storage.api.CoffeeMachineStateStorage;
import ua.dp.dryzhyruk.coffee.machines.state.monitor.storage.api.entity.CoffeeMachineConfigurationEntity;
import ua.dp.dryzhyruk.coffee.machines.state.monitor.storage.api.entity.CoffeeMachineStateEntity;

import static ua.dp.dryzhyruk.coffee.machines.state.monitor.utils.Preconditions.requireNonNull;

@Service
public class CoffeeMachinesEventRegisterServices {

    private final CoffeeMachineConfigurationStorage coffeeMachineConfigurationStorage;
    private final CoffeeMachineStateStorage coffeeMachineStateStorage;

    @Autowired
    public CoffeeMachinesEventRegisterServices(
            CoffeeMachineConfigurationStorage coffeeMachineConfigurationStorage,
            CoffeeMachineStateStorage coffeeMachineStateStorage) {
        this.coffeeMachineConfigurationStorage = coffeeMachineConfigurationStorage;
        this.coffeeMachineStateStorage = coffeeMachineStateStorage;
    }

    public void registerNewMadeCup(String coffeeMachineId, Cup cup) {
        CoffeeMachineStateEntity coffeeMachineState = coffeeMachineStateStorage.find(coffeeMachineId);

        requireNonNull(coffeeMachineState);

        CoffeeMachineStateEntity newCoffeeMachineState = calculateNewStateOnCupMade(cup, coffeeMachineState);

        coffeeMachineStateStorage.update(newCoffeeMachineState);
    }

    public void registerLineService(
            String coffeeMachineId, boolean coffeeBeansFilled, boolean milkFilled, boolean trashContainerCleaned) {
        CoffeeMachineStateEntity coffeeMachineState = coffeeMachineStateStorage.find(coffeeMachineId);

        requireNonNull(coffeeMachineState);

        CoffeeMachineConfigurationEntity coffeeMachineConfiguration = coffeeMachineConfigurationStorage.find(coffeeMachineId);

        requireNonNull(coffeeMachineConfiguration);

        CoffeeMachineStateEntity newCoffeeMachineState = calculateNewStateOnLineService(
                coffeeBeansFilled, milkFilled, trashContainerCleaned,
                coffeeMachineState, coffeeMachineConfiguration);

        coffeeMachineStateStorage.update(newCoffeeMachineState);
    }

    private CoffeeMachineStateEntity calculateNewStateOnCupMade(Cup cup, CoffeeMachineStateEntity coffeeMachineState) {

        int coffeeBeansLeftForNPortions =
                Math.max(coffeeMachineState.getCoffeeBeansLeftForNPortions() - cup.getCoffeePortions(), 0);

        int milkChangeCount = cup.isWithMilk() && coffeeMachineState.getMilkLeftForNPortions() > 0 ? 1 : 0;
        int milkLeftForNPortions = coffeeMachineState.getMilkLeftForNPortions() - milkChangeCount;

        int placeInTrashLeftForNPortions =
                Math.max(coffeeMachineState.getPlaceInTrashContainerLeftForNPortions() - 1, 0);

        return coffeeMachineState.toBuilder()
                .coffeeBeansLeftForNPortions(coffeeBeansLeftForNPortions)
                .milkLeftForNPortions(milkLeftForNPortions)
                .placeInTrashContainerLeftForNPortions(placeInTrashLeftForNPortions)
                .build();
    }

    private CoffeeMachineStateEntity calculateNewStateOnLineService(
            boolean coffeeBeansFilled,
            boolean milkFilled,
            boolean trashContainerCleaned,
            CoffeeMachineStateEntity coffeeMachineState,
            CoffeeMachineConfigurationEntity coffeeMachineConfiguration) {

        int coffeeBeansLeftForNPortions = coffeeBeansFilled
                ? coffeeMachineConfiguration.getMaxNumberCoffeeBeansPortions()
                : coffeeMachineState.getCoffeeBeansLeftForNPortions();

        int milkLeftForNPortions = milkFilled
                ? coffeeMachineConfiguration.getMaxNumberMilkPortions()
                : coffeeMachineState.getMilkLeftForNPortions();

        int placeInTrashLeftForNPortions = trashContainerCleaned
                ? coffeeMachineConfiguration.getMaxNumberPlacesInTrashContainer()
                : coffeeMachineState.getPlaceInTrashContainerLeftForNPortions();

        return coffeeMachineState.toBuilder()
                .coffeeBeansLeftForNPortions(coffeeBeansLeftForNPortions)
                .milkLeftForNPortions(milkLeftForNPortions)
                .placeInTrashContainerLeftForNPortions(placeInTrashLeftForNPortions)
                .build();
    }
}
