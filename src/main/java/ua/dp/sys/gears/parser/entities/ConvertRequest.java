package ua.dp.sys.gears.parser.entities;

import lombok.Value;
import ua.dp.sys.gears.core.model.Distance;

@Value
public class ConvertRequest {

    public RequestedDistance distance;
    public String convertTo;
}
