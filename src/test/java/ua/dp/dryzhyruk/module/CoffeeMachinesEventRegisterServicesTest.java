package ua.dp.dryzhyruk.module;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import ua.dp.dryzhyruk.coffee.machines.state.monitor.core.CoffeeMachinesEventRegisterServices;
import ua.dp.dryzhyruk.coffee.machines.state.monitor.core.model.Cup;
import ua.dp.dryzhyruk.coffee.machines.state.monitor.storage.api.CoffeeMachineConfigurationStorage;
import ua.dp.dryzhyruk.coffee.machines.state.monitor.storage.api.CoffeeMachineStateStorage;
import ua.dp.dryzhyruk.coffee.machines.state.monitor.storage.api.entity.CoffeeMachineConfiguration;
import ua.dp.dryzhyruk.coffee.machines.state.monitor.storage.api.entity.CoffeeMachineStateEntity;

@ExtendWith(MockitoExtension.class)
class CoffeeMachinesEventRegisterServicesTest {

    @Mock
    private CoffeeMachineConfigurationStorage coffeeMachineConfigurationStorageMock;

    @Mock
    private CoffeeMachineStateStorage coffeeMachineStateStorageMock;

    @InjectMocks
    private CoffeeMachinesEventRegisterServices coffeeMachinesEventRegisterServices;

    @Test
    void coffeeMachineStateShouldBeUpdatedOnRegistrationOfNewCup() {
        //given
        String coffeeMachineId = "#1";

        Cup cup = Cup.builder()
                .coffeePortions(2)
                .withMilk(true)
                .build();

        CoffeeMachineStateEntity coffeeMachineStateEntity = CoffeeMachineStateEntity.builder()
                .coffeeMachineId(coffeeMachineId)
                .coffeeBeansLeftForNPortions(100)
                .milkLeftForNPortions(3)
                .placeInTrashContainerLeftForNPortions(100)
                .build();

        Mockito.when(coffeeMachineStateStorageMock.find(coffeeMachineId))
                .thenReturn(coffeeMachineStateEntity);

        CoffeeMachineStateEntity expected = CoffeeMachineStateEntity.builder()
                .coffeeMachineId(coffeeMachineId)
                .coffeeBeansLeftForNPortions(98)
                .milkLeftForNPortions(2)
                .placeInTrashContainerLeftForNPortions(99)
                .build();

        //when
        coffeeMachinesEventRegisterServices.registerNewMadeCup(coffeeMachineId, cup);

        //then
        Mockito.verify(coffeeMachineStateStorageMock).update(coffeeMachineId, expected);
    }

    @Test
    void exceptionShouldRethrownInCaseMissingStateForCoffeeMachine() {
        //given
        String coffeeMachineId = "#1";

        Cup cup = Cup.builder()
                .coffeePortions(2)
                .withMilk(true)
                .build();

        Mockito.when(coffeeMachineStateStorageMock.find(coffeeMachineId))
                .thenReturn(null);


        //when
        //then exception should happened
        Assertions.assertThrows(IllegalArgumentException.class, () ->
                coffeeMachinesEventRegisterServices.registerNewMadeCup(coffeeMachineId, cup));
    }

    @Test
    void coffeeMachineStateShouldBeCleanedOnRegistrationOfLineServiceAction() {
        //given
        String coffeeMachineId = "#1";
        boolean coffeeBeansFilled = true;
        boolean milkFilled = true;
        boolean trashContainerCleaned = true;

        CoffeeMachineStateEntity coffeeMachineStateEntity = CoffeeMachineStateEntity.builder()
                .coffeeMachineId(coffeeMachineId)
                .coffeeBeansLeftForNPortions(100)
                .milkLeftForNPortions(3)
                .placeInTrashContainerLeftForNPortions(100)
                .build();

        Mockito.when(coffeeMachineStateStorageMock.find(coffeeMachineId))
                .thenReturn(coffeeMachineStateEntity);

        CoffeeMachineConfiguration coffeeMachineConfiguration = CoffeeMachineConfiguration.builder()
                .coffeeMachineId(coffeeMachineId)
                .maxNumberCoffeeBeansPortions(100)
                .coffeeBeansWarningNPortionLeft(30)
                .coffeeBeansCriticalNPortionLeft(10)
                .maxNumberMilkPortions(30)
                .milkWarningNPortionLeft(15)
                .milkCriticalNPortionLeft(5)
                .maxNumberPlacesInTrashContainer(100)
                .placesInTrashContainerWarningNPortionLeft(30)
                .PlacesInTrashContainerCriticalNPortionLeft(10)
                .build();
        Mockito.when(coffeeMachineConfigurationStorageMock.find(coffeeMachineId))
                .thenReturn(coffeeMachineConfiguration);

        CoffeeMachineStateEntity expected = CoffeeMachineStateEntity.builder()
                .coffeeMachineId(coffeeMachineId)
                .coffeeBeansLeftForNPortions(100)
                .milkLeftForNPortions(30)
                .placeInTrashContainerLeftForNPortions(100)
                .build();
        //when
        coffeeMachinesEventRegisterServices.registerLineService(
                coffeeMachineId, coffeeBeansFilled, milkFilled, trashContainerCleaned);

        //then
        Mockito.verify(coffeeMachineStateStorageMock).update(coffeeMachineId, expected);
    }

    @Test
    void exceptionShouldRethrownInCaseMissingStateOnRegistrationOfLineServiceAction() {
        //given
        String coffeeMachineId = "#1";

        Mockito.when(coffeeMachineStateStorageMock.find(coffeeMachineId))
                .thenReturn(null);

        //when
        //then exception should happened
        Assertions.assertThrows(IllegalArgumentException.class, () ->
                coffeeMachinesEventRegisterServices.registerLineService(
                        coffeeMachineId, true, true, true));
    }

    @Test
    void exceptionShouldBeThrownInCaseMissingConfigurationOnRegistrationOfLineServiceAction() {
        //given
        String coffeeMachineId = "#1";


        CoffeeMachineStateEntity coffeeMachineStateEntity = CoffeeMachineStateEntity.builder()
                .coffeeMachineId(coffeeMachineId)
                .coffeeBeansLeftForNPortions(100)
                .milkLeftForNPortions(3)
                .placeInTrashContainerLeftForNPortions(100)
                .build();

        Mockito.when(coffeeMachineStateStorageMock.find(coffeeMachineId))
                .thenReturn(coffeeMachineStateEntity);

        Mockito.when(coffeeMachineConfigurationStorageMock.find(coffeeMachineId))
                .thenReturn(null);

        //when
        //then exception should happened
        Assertions.assertThrows(IllegalArgumentException.class, () ->
                coffeeMachinesEventRegisterServices.registerLineService(
                        coffeeMachineId, true, true, true));
    }
}