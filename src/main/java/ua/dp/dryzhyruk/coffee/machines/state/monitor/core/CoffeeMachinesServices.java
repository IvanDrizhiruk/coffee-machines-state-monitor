package ua.dp.dryzhyruk.coffee.machines.state.monitor.core;

import org.jvnet.hk2.annotations.Service;
import org.springframework.beans.factory.annotation.Autowired;
import ua.dp.dryzhyruk.coffee.machines.state.monitor.core.model.Cup;
import ua.dp.dryzhyruk.coffee.machines.state.monitor.storage.api.CoffeeMachineConfigurationStorage;
import ua.dp.dryzhyruk.coffee.machines.state.monitor.storage.api.CoffeeMachineStateStorage;
import ua.dp.dryzhyruk.coffee.machines.state.monitor.storage.api.entity.CoffeeMachineConfiguration;
import ua.dp.dryzhyruk.coffee.machines.state.monitor.storage.api.entity.CoffeeMachineState;

@Service
public class CoffeeMachinesServices {

    private final CoffeeMachineConfigurationStorage coffeeMachineConfigurationStorage;
    private final CoffeeMachineStateStorage coffeeMachineStateStorage;

    @Autowired
    public CoffeeMachinesServices(
            CoffeeMachineConfigurationStorage coffeeMachineConfigurationStorage,
            CoffeeMachineStateStorage coffeeMachineStateStorage) {
        this.coffeeMachineConfigurationStorage = coffeeMachineConfigurationStorage;
        this.coffeeMachineStateStorage = coffeeMachineStateStorage;
    }

    public void registerNewMadeCup(String coffeeMachineId, Cup cup) {
        CoffeeMachineState coffeeMachineState = coffeeMachineStateStorage.find(coffeeMachineId);

        CoffeeMachineState newCoffeeMachineState = calculateNewStateOnCupMade(cup, coffeeMachineState);

        coffeeMachineStateStorage.update(coffeeMachineId, newCoffeeMachineState);
    }

    public void registerLineService(
            String coffeeMachineId, boolean coffeeBeansFilled, boolean milkFilled, boolean trashContainerCleaned) {
        CoffeeMachineState coffeeMachineState = coffeeMachineStateStorage.find(coffeeMachineId);
        CoffeeMachineConfiguration coffeeMachineConfiguration = coffeeMachineConfigurationStorage.find(coffeeMachineId);

        CoffeeMachineState newCoffeeMachineState = calculateNewStateOnLineService(
                coffeeBeansFilled, milkFilled, trashContainerCleaned,
                coffeeMachineState, coffeeMachineConfiguration);

        coffeeMachineStateStorage.update(coffeeMachineId, newCoffeeMachineState);
    }

    private CoffeeMachineState calculateNewStateOnCupMade(Cup cup, CoffeeMachineState coffeeMachineState) {

        int coffeeBeansLeftForNPortions =
                Math.max(coffeeMachineState.getCoffeeBeansLeftForNPortions() - cup.getCoffeePortions(), 0);

        int milkChangeCount = cup.isWithMilk() || coffeeMachineState.getMilkLeftForNPortions() > 0 ? 1 : 0;
        int milkLeftForNPortions = coffeeMachineState.getMilkLeftForNPortions() - milkChangeCount;

        int placeInTrashLeftForNPortions =
                Math.max(coffeeMachineState.getPlaceInTrashContainerLeftForNPortions() - 1, 0);

        return coffeeMachineState.toBuilder()
                .coffeeBeansLeftForNPortions(coffeeBeansLeftForNPortions)
                .milkLeftForNPortions(milkLeftForNPortions)
                .placeInTrashContainerLeftForNPortions(placeInTrashLeftForNPortions)
                .build();
    }

    private CoffeeMachineState calculateNewStateOnLineService(
            boolean coffeeBeansFilled,
            boolean milkFilled,
            boolean trashContainerCleaned,
            CoffeeMachineState coffeeMachineState,
            CoffeeMachineConfiguration coffeeMachineConfiguration) {

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
