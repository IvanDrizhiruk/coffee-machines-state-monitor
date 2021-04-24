package ua.dp.dryzhyruk.e2e.jbehave.steps;

import org.jbehave.core.annotations.Given;
import org.jbehave.core.annotations.Then;
import org.jbehave.core.annotations.When;
import org.jbehave.core.model.ExamplesTable;
import org.springframework.beans.factory.annotation.Autowired;
import ua.dp.dryzhyruk.coffee.machines.state.monitor.storage.api.CoffeeMachineConfigurationStorage;
import ua.dp.dryzhyruk.coffee.machines.state.monitor.storage.api.CoffeeMachineStateStorage;

public class CoffeeMachineStatusStorageSteps {

    @Autowired
    private CoffeeMachineStateStorage coffeeMachineStateStorage;

    @Given("cleanup coffee machine states")
    public void cleanupCoffeeMachineStates() {
        System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!! " + coffeeMachineStateStorage);
        coffeeMachineStateStorage.removeAll();
    }
}