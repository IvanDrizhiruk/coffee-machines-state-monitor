package ua.dp.dryzhyruk.e2e.jbehave.steps;

import org.jbehave.core.annotations.Given;
import org.jbehave.core.annotations.Then;
import org.jbehave.core.annotations.When;
import org.jbehave.core.model.ExamplesTable;

public class EventRegistrarSteps {

    @Given("a counter")
    public void aCounter() {
    }

    @When("register in coffee machine with id ${coffeeMachineId} new coffee cup with ${coffeePortions} portions of coffee and milk")
    public void increasesTheCounter(String coffeeMachineId, int coffeePortions) {

    }

    @Then("state of coffee machine with id ${coffeeMachineId} is: ${status}}")
    public void theValueOfTheCounterMustBe1Greater(String coffeeMachineId, ExamplesTable status) {

    }
}