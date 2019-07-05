package com.stefan.carstore.service;

import com.stefan.carstore.model.Car;
import com.stefan.carstore.model.Cars;
import com.stefan.carstore.service.converters.CarsJsonConverter;

import java.lang.reflect.Array;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Optional;
import static com.stefan.carstore.model.enums.Color.*;

public class DataGeneratorService
{
    public Cars generateCars()
    {
        Cars carServiceList = new Cars();
        carServiceList.setCars(Arrays.asList(
                new Car.CarBuilder().model("FIAT").price(new BigDecimal(55347)).color(YELLOW).mileage(4457).components(Arrays.asList("AIR BAG", "GPS", "AIR CONDITION")).build(),
                new Car.CarBuilder().model("MERCEDES").price(new BigDecimal(172000)).color(WHITE).mileage(9963).components(Arrays.asList("AIR CONDITION", "RADIO", "USB")).build(),
                new Car.CarBuilder().model("BMW").price(new BigDecimal(172000)).color(BLACK).mileage(3745).components(Arrays.asList("CRUISE CONTROL", "BLUETOOTH", "HOOK")).build(),
                new Car.CarBuilder().model("AUDI").price(new BigDecimal(111000)).color(BLACK).mileage(5767).components(Arrays.asList("RAIN SENSOR", "ALLOY WHEELS", "AIR BAG")).build(),
                new Car.CarBuilder().model("FIAT").price(new BigDecimal(39000)).color(SILVER).mileage(1123).components(Arrays.asList("ABS", "ASR", "AIR BAG")).build(),
                new Car.CarBuilder().model("OPEL").price(new BigDecimal(77300)).color(SILVER).mileage(2732).components(Arrays.asList("AIR CONDITION", "WEBASTO", "HOOK")).build(),
                new Car.CarBuilder().model("NISSAN").price(new BigDecimal(97540)).color(SILVER).mileage(2346).components(Arrays.asList("HALDEX", "LOCK UP", "WEBASTO")).build(),
                new Car.CarBuilder().model("PEUGEOT").price(new BigDecimal(103456)).color(RED).mileage(7347).components(Arrays.asList("AIR CONDITION", "HEATED SEAT", "RADIO")).build(),
                new Car.CarBuilder().model("TOYOTA").price(new BigDecimal(85346)).color(GREEN).mileage(6321).components(Arrays.asList("ALLOY WHEELS", "RADIO", "GPS")).build(),
                new Car.CarBuilder().model("HONDA").price(new BigDecimal(105345)).color(BLUE).mileage(3379).components(Arrays.asList("AUTOMATIC TRANSMISSION", "AIR BAG", "REVERSE CAMERA")).build(),
                new Car.CarBuilder().model("BMW").price(new BigDecimal(115721)).color(BLUE).mileage(5667).components(Arrays.asList("SIDE AIRBAGS", "PARKING SENSORS", "HEATED SEATS")).build()
        ));


        // ZRÓB CLEAR LISTY  !!!!

        /*
            GPS,
            AIR_CONDITION,
            PARKING_HEATER,
            CRUISE_CONTROL,
            SIDE_AIRBAGS,
            BLUETOOTH,
            REVERSE_CAMERA,
            PARKING_SENSOR,
            RAIN_SENSOR,
            HEATED_MIRRORS,
            HEATED_FRONT_WINDSCREEN,
            HEATED_SEATS,
            START_STOP_SYSTEM,
            ALLOY_WHEEL,
            HOOK
         */

        //System.out.println("MOJA LISTA!!!" + carServiceList);

        /*
        Cars carServiceList = new Cars(Arrays.asList(
                new Car.CarBuilder().model("FIAT").price(new BigDecimal(55347)).color(YELLOW).mileage(4457).components(Arrays.asList("AIR BAG", "GPS", "AIR CONDITION")).build(),
                new Car.CarBuilder().model("MERCEDES").price(new BigDecimal(172000)).color(WHITE).mileage(9963).components(Arrays.asList("AIR CONDITION", "RADIO", "USB")).build(),
                new Car.CarBuilder().model("BMW").price(new BigDecimal(172000)).color(BLACK).mileage(3745).components(Arrays.asList("CRUISE CONTROL", "BLUETOOTH", "HOOK")).build(),
                new Car.CarBuilder().model("AUDI").price(new BigDecimal(111000)).color(BLACK).mileage(5767).components(Arrays.asList("RAIN SENSOR", "ALLOY WHEELS", "AIR BAG")).build(),
                new Car.CarBuilder().model("FIAT").price(new BigDecimal(39000)).color(SILVER).mileage(1123).components(Arrays.asList("ABS", "ASR", "AIR BAG")).build(),
                new Car.CarBuilder().model("OPEL").price(new BigDecimal(77300)).color(SILVER).mileage(2732).components(Arrays.asList("AIR CONDITION", "WEBASTO", "HOOK")).build(),
                new Car.CarBuilder().model("NISSAN").price(new BigDecimal(97540)).color(SILVER).mileage(2346).components(Arrays.asList("HALDEX", "LOCK UP", "WEBASTO")).build(),
                new Car.CarBuilder().model("PEUGEOT").price(new BigDecimal(103456)).color(RED).mileage(7347).components(Arrays.asList("AIR CONDITION", "HEATED SEAT", "RADIO")).build(),
                new Car.CarBuilder().model("TOYOTA").price(new BigDecimal(85346)).color(GREEN).mileage(6321).components(Arrays.asList("ALLOY WHEELS", "RADIO", "GPS")).build()
        )); */
        //System.out.println("MOJA LISTA!!!" + carServiceList);

        return carServiceList;
    }

    public String convertFromCarsToJsonFile(String jSonFilename)
    {
        Cars allCars =  generateCars();
        jSonFilename = "myCars.json";
        CarsJsonConverter carsJsonConverter = new CarsJsonConverter(jSonFilename);
        carsJsonConverter.toJson(allCars);
        return jSonFilename;
    }

    public String convertFromCarsToJson()
    {
        Cars allCars =  generateCars();
        final String jSonFilename = "myCars.json";
        CarsJsonConverter carsJsonConverter = new CarsJsonConverter(jSonFilename);
        carsJsonConverter.toJson(allCars);
        return jSonFilename;
    }

    public Cars getCarsFromJsonFile(String JsonFilename)
    {
        CarsJsonConverter carsJsonConverter = new CarsJsonConverter(JsonFilename);
        Optional<Cars> carsFromJsonFile = carsJsonConverter.fromJson();
        Cars carsJson = carsFromJsonFile.get();
        return  carsJson;
    }

    public Cars generateData(String jSonFilename)
    {
        Cars allCars =  generateCars();
        jSonFilename = "myCars.json";
        CarsJsonConverter carsJsonConverter = new CarsJsonConverter(jSonFilename);
        carsJsonConverter.toJson(allCars);

        Optional<Cars> carsFromJsonFile = carsJsonConverter.fromJson();
        Cars carsJson = carsFromJsonFile.get();
        return  carsJson;
    }






}









