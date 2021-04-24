package ua.dp.sys.gears.app;

import lombok.extern.slf4j.Slf4j;
import ua.dp.sys.gears.core.ConvertService;
import ua.dp.sys.gears.core.model.ConvertingConfiguration;
import ua.dp.sys.gears.core.model.Distance;
import ua.dp.sys.gears.parser.JsonProcessor;
import ua.dp.sys.gears.parser.entities.ConvertRequest;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

@Slf4j
public class MainClass {
    public static void main(String[] args) throws IOException {
        String path = args[0];
        String convertRequestAsJson = Files.readString(Paths.get(path), StandardCharsets.UTF_8);

        JsonProcessor jsonProcessor = new JsonProcessor();

        String convertingConfigurationAsJson = Files.readString(Paths.get("./configuration.json"), StandardCharsets.UTF_8);
        ConvertingConfiguration configuration = jsonProcessor.jsonAsObject(convertingConfigurationAsJson, ConvertingConfiguration.class);

        ConvertService convertService = new ConvertService(configuration);


        ConvertRequest request = jsonProcessor.jsonAsObject(convertRequestAsJson, ConvertRequest.class);

        Distance distance = new Distance(request.distance.getUnit(), request.distance.getValue());
        String convertToUnit = request.convertTo;
        Distance result = convertService.convert(distance, convertToUnit);

        String resultAsJson = jsonProcessor.objectAsJson(result);

        log.info("Result : {}", resultAsJson);
    }
}
