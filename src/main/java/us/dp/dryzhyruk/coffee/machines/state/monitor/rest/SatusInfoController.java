package us.dp.dryzhyruk.coffee.machines.state.monitor.rest;

import org.springframework.web.bind.annotation.*;

import java.util.AbstractMap;
import java.util.Map;

@RestController
public class SatusInfoController {
    private static final String template = "Hello, %s!";

    @GetMapping("/greeting/{name}")
    public Map.Entry<String, String> register(@PathVariable(value = "name") String name) {
        return new AbstractMap.SimpleEntry<>(name, String.format(template, name));
    }
}