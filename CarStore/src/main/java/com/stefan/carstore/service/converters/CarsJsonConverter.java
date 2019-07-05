package com.stefan.carstore.service.converters;

import com.stefan.carstore.model.Cars;

public class CarsJsonConverter extends JsonConverter<Cars>
{
    // CONSTRUCTOR
    public CarsJsonConverter(String jSonFilename)
    {
        super(jSonFilename);
    }
}
