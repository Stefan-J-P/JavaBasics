package com.stefan.carstore.service;

import com.stefan.carstore.exceptions.MyException;
import com.stefan.carstore.model.Car;
import com.stefan.carstore.model.Cars;
import com.stefan.carstore.model.enums.Color;
import com.stefan.carstore.model.enums.StatisticType;
import com.stefan.carstore.service.converters.CarsJsonConverter;
import com.stefan.carstore.service.sorting.SortType;
import lombok.*;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;


@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
class CarService
{
    private List<Car> cars;

    CarService(List<Car> cars)
    {
        this.cars = cars;
    }

    // CONSTRUCTOR =====================================
    CarService(String jSonFilename)
    {
        //cars = /*converter Json, ktory pobiera dane z pliku*/ new ArrayList<>();

        CarsJsonConverter carsJsonConverter = new CarsJsonConverter(jSonFilename);
        Optional<Cars> carsFromJsonFile = carsJsonConverter.fromJson();
        //Cars carsJson = carsFromJsonFile.get();
        cars = carsFromJsonFile.get().getCars();
    }

    // TO STRING =======================================
    @Override
    public String toString()
    {
        //return "CarService{" + "cars=" + cars + '}';
        return cars
                .stream()
                .map(Car::toString)
                .collect(Collectors.joining("\n"));
    }

    // SORTED BY USER ----------------------------------------------------------------------------------------------------------------------------------
    List<Car> sort(SortType type, boolean descending)
    {
        if (type == null)
        {
            throw new MyException("Sort type is not correct");
        }

        Stream<Car> carStream = null;
        switch (type)
        {
            case MODEL:
                carStream = cars.stream().sorted(Comparator.comparing(Car::getModel));
                break;
            case PRICE:
                carStream = cars.stream().sorted(Comparator.comparing(Car::getPrice));
                break;
            case MILEAGE:
                carStream = cars.stream().sorted(Comparator.comparing(Car::getMileage));
                break;
            case COLOR:
                carStream = cars.stream().sorted(Comparator.comparing(Car::getColor));
                break;

        }
        List<Car> sortedCars = carStream.collect(Collectors.toList());

        if (descending == false)
        {
            Collections.reverse(sortedCars);
        }
        return sortedCars;
    }


    // SHOW CARS WITH MILEAGE GREATER THAN: -------------------------------------------------------------------------------------------------------------
    public List<Car> showCarsWithMileageGreaterThan(Integer mileageLimit)
    {
        List<Car> carsList = cars;
        //carsList.stream().filter(c-> c.getMileage() > mileageLimit).forEach(System.out::println);
        return carsList.stream().filter(c -> c.getMileage() > mileageLimit).collect(Collectors.toList());
    }


    // MAPA ---------------------------------------------------------------------------------------------------------------------------------------------
    // mapa ---> klucz: kolor, wartość: liczba samochodow z tym kolorem
    public Map<Color, Long> carsWithChosenColor(/*Color myColor*/)
    {
        return cars
                .stream()
                //.filter(c-> c.getColor().equals(myColor))
                .collect(Collectors.groupingBy(Car::getColor, Collectors.counting()))
                .entrySet()
                .stream()
                .sorted(Comparator.comparing(/*Map.Entry::getKey*/e -> e.getKey().toString(), Comparator.reverseOrder()))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (v1, v2) -> v1, LinkedHashMap::new));
    }


    // MAP: Key: model, Value: Car ---------------------------------------------------------------------------------------------------------
    public Map<String, Car> theMostExpensiveModel()
    {
        return cars
                .stream()
                .collect(Collectors.groupingBy(Car::getModel))
                .entrySet()
                .stream()
                .collect(Collectors.toMap(
                        e -> e.getKey(),
                        e -> e.getValue().stream().max(Comparator.comparing(Car::getPrice)).orElseThrow(() -> new MyException("CAR WITH MODEL NAME " + e.getKey() + " NOT FOUND"))
                ));
    }


    // PRICE & MILEAGE STATISTICS -----------------------------------------------------------------------------------------------------------------------
    public List<Double> mileageStatistics()
    {
        /*Integer minMileage = cars.stream().map(c-> c.getMileage()).reduce((m1, m2) -> Integer.min(m1, m2)).orElse(0);
        Integer maxMileage = cars.stream().map(c-> c.getMileage()).reduce((m1, m2) -> Integer.max(m1, m2)).orElse(0);

        Integer totalMileage = cars.stream().map(c-> c.getMileage()).mapToInt(i-> i.intValue()).sum();
        Integer mileageAvg = totalMileage / cars.size();
*/
        IntSummaryStatistics iss = cars.stream().collect(Collectors.summarizingInt(Car::getMileage));
        return Arrays.asList((double) iss.getMin(), (double) iss.getMax(), iss.getAverage());

    }

    public List<BigDecimal> priceStatistics()
    {
        BigDecimal minPrice = cars.stream().map(c -> c.getPrice()).reduce((p1, p2) -> p1.compareTo(p2) < 0 ? p1 : p2)
                .orElseThrow(() -> new NullPointerException("STATISTICS: MIN PRICE CANNOT BE FIND"));

        BigDecimal maxPrice = cars.stream().map(c -> c.getPrice()).reduce((p1, p2) -> p1.compareTo(p2) > 0 ? p1 : p2)
                .orElseThrow(() -> new NullPointerException("STATISTICS: MAX PRICE CANNOT BE FIND"));

        BigDecimal totalPrice = cars.stream().map(c -> c.getPrice()).reduce(BigDecimal.ZERO, (p1, p2) -> p1.add(p2));
        BigDecimal priceAvg = totalPrice.divide(new BigDecimal(cars.size()), 2, RoundingMode.DOWN);

        return Arrays.asList(minPrice, maxPrice, priceAvg);
    }

    public void priceAndMileageStatistics(StatisticType type)
    {
        switch (type)
        {
            case MILEAGE:
                List<Double> statMileage = mileageStatistics();
                //statMileage.forEach(System.out::println);
                System.out.println("MIN MILEAGE: " + statMileage.get(0));
                System.out.println("MAX MILEAGE: " + statMileage.get(1));
                System.out.println("AVG MILEAGE: " + statMileage.get(2));
                break;

            case PRICE:
                List<BigDecimal> statPrice = priceStatistics();
                System.out.println("MIN PRICE: " + statPrice.get(0));
                System.out.println("MAX PRICE: " + statPrice.get(1));
                System.out.println("AVG PRICE: " + statPrice.get(2));
                break;
        }
    }

    // THE MOST EXPENSIVE CAR ----------------------------------------------------------------------------------------------------------------------------
    public List<Car> theMostExpensiveCar()
    {
        // szukam najdroższego auta
        //Car theMostExpCar = cars.stream().sorted((p1, p2) -> p2.getPrice().compareTo(p1.getPrice())).findFirst().orElse(null);

        // sprawdzam najwyższą cenę
        BigDecimal maxPrice = cars.stream().map(c -> c.getPrice()).reduce((p1, p2) -> p1.compareTo(p2) > 0 ? p1 : p2)
                .orElseThrow(() -> new NullPointerException("STATISTICS: MAX PRICE CANNOT BE FIND"));

        List<Car> theMostExpCars = new ArrayList<>();

        // przeglądam kolekcję aby sprawdzić czy jest więcej aut z ceną równą cenie maksymalnej
        for (int i = 0; i < cars.size(); ++i)
        {
            // jeżeli jest więcej niż jeden to dodaj do kolekcji
            //cars.get(i).getPrice().compareTo(maxPrice) == 0
            if (maxPrice.compareTo(cars.get(i).getPrice()) == 0)
            {
                theMostExpCars.add(cars.get(i));
            }
        }
        return theMostExpCars;
    }

    // CARS WITH SORTED COMPONENTS -----------------------------------------------------------------------------------------------------------------------
    public List<Car> carsWithSortedComponents()
    {
        List<Car> sortedComponents = cars;

        for (int i = 0; i < cars.size(); ++i)
        {
            sortedComponents.get(i).getComponents().sort((c1, c2) -> c1.compareTo(c2));
        }
        return sortedComponents;
    }

    // CARS WITH CHOSEN COMPONENTS -----------------------------------------------------------------------------------------------------------------------
    public Map<String, List<Car>> carsWithChosenComponent()
    {
        //List<Car> myCars = cars.stream().filter(c-> c.getComponents().contains(myComponent)).collect(Collectors.toList());
        return cars
                .stream()
                .map(car -> car.getComponents())
                .flatMap(components -> components.stream())
                .distinct()
                .collect(Collectors.toMap(
                        component -> component,
                        component -> cars.stream().filter(car -> car.getComponents().contains(component)).collect(Collectors.toList())
                ))
                .entrySet()
                .stream()
                .sorted(Comparator.comparingInt(e -> e.getValue().size()))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (v1, v2) -> v1, LinkedHashMap::new));

    }

    // CARS WITH PRICE IN RANGE --------------------------------------------------------------------------------------------------------------------------
    public List<Car> carsWithPriceInRange(BigDecimal minPrice, BigDecimal maxPrice)
    {
        return cars.stream().
                filter(car ->
                        car.getPrice().compareTo(minPrice) >= 0 &&
                                car.getPrice().compareTo(maxPrice) <= 0)
                .sorted(Comparator.comparing(Car::getModel))
                .collect(Collectors.toList());
    }


}
















