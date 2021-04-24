package ua.dp.dryzhyruk.e2e.jbehave.steps;

import org.jbehave.core.annotations.Given;
import org.jbehave.core.annotations.Then;
import org.jbehave.core.annotations.When;
import org.jbehave.core.model.ExamplesTable;
import org.springframework.beans.factory.annotation.Autowired;
import ua.dp.dryzhyruk.coffee.machines.state.monitor.storage.api.CoffeeMachineConfigurationStorage;

public class CoffeeMachineConfigurationStorageSteps {

    @Autowired
    private CoffeeMachineConfigurationStorage coffeeMachineConfigurationStorage;

    @Given("cleanup coffee machine configurations")
    public void cleanupCoffeeMachineConfigurations() {
        System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!! " + coffeeMachineConfigurationStorage);
        coffeeMachineConfigurationStorage.removeAll();
    }
}