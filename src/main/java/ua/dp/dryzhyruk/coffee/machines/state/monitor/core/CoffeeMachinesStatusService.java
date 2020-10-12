package ua.dp.dryzhyruk.coffee.machines.state.monitor.core;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.dp.dryzhyruk.coffee.machines.state.monitor.core.model.CoffeeMachineState;
import ua.dp.dryzhyruk.coffee.machines.state.monitor.storage.api.CoffeeMachineConfigurationStorage;
import ua.dp.dryzhyruk.coffee.machines.state.monitor.storage.api.CoffeeMachineStateStorage;
import ua.dp.dryzhyruk.coffee.machines.state.monitor.storage.api.entity.CoffeeMachineConfiguration;
import ua.dp.dryzhyruk.coffee.machines.state.monitor.storage.api.entity.CoffeeMachineStateEntity;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class CoffeeMachinesStatusService {

    private final CoffeeMachineStateStorage coffeeMachineStateStorage;
    private final CoffeeMachineConfigurationStorage coffeeMachineConfigurationStorage;
    private final CoffeeMachinesStatusCalculator coffeeMachinesStatusCalculator;

    @Autowired
    public CoffeeMachinesStatusService(
            CoffeeMachineStateStorage coffeeMachineStateStorage,
            CoffeeMachineConfigurationStorage coffeeMachineConfigurationStorage,
            CoffeeMachinesStatusCalculator coffeeMachinesStatusCalculator) {
        this.coffeeMachineStateStorage = coffeeMachineStateStorage;
        this.coffeeMachineConfigurationStorage = coffeeMachineConfigurationStorage;
        this.coffeeMachinesStatusCalculator = coffeeMachinesStatusCalculator;
    }

    public List<CoffeeMachineState> getAllCoffeeMachinesStatus() {
        Map<String, CoffeeMachineStateEntity> stateByCoffeeMachineId = coffeeMachineStateStorage.findAll().stream()
                .collect(Collectors.toMap(
                        CoffeeMachineStateEntity::getCoffeeMachineId,
                        Function.identity()));

        Map<String, CoffeeMachineConfiguration> configurationByCoffeeMachineId = coffeeMachineConfigurationStorage.findAll().stream()
                .collect(Collectors.toMap(
                        CoffeeMachineConfiguration::getCoffeeMachineId,
                        Function.identity()));

        return stateByCoffeeMachineId.keySet().stream()
                .map(coffeeMachineId -> {
                    CoffeeMachineStateEntity coffeeMachineState = stateByCoffeeMachineId.get(coffeeMachineId);
                    CoffeeMachineConfiguration coffeeMachineConfiguration = configurationByCoffeeMachineId.get(coffeeMachineId);

                    return coffeeMachinesStatusCalculator.calculate(coffeeMachineState, coffeeMachineConfiguration);
                })
                .collect(Collectors.toList());
    }

    public CoffeeMachineState getCoffeeMachinesStatus(String coffeeMachineId) {
        CoffeeMachineStateEntity coffeeMachineState = coffeeMachineStateStorage.find(coffeeMachineId);
        CoffeeMachineConfiguration coffeeMachineConfiguration = coffeeMachineConfigurationStorage.find(coffeeMachineId);

        return coffeeMachinesStatusCalculator.calculate(coffeeMachineState, coffeeMachineConfiguration);
    }
}
