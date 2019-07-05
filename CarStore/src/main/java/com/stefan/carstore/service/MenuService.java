package com.stefan.carstore.service;

import com.stefan.carstore.exceptions.MyException;
import com.stefan.carstore.model.Car;
import com.stefan.carstore.model.Cars;
import com.stefan.carstore.model.enums.Color;
import com.stefan.carstore.model.enums.StatisticType;
import com.stefan.carstore.service.sorting.SortType;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

public class MenuService
{
    private final DataGeneratorService dataGeneratorService = new DataGeneratorService();
    private final String jSonFilename = dataGeneratorService.convertFromCarsToJson();
    private final CarService carService = new CarService(jSonFilename);
    private final ScannerDataService scannerDataService = new ScannerDataService();

    public void mainMenu()
    {
        //Cars myCars = dataGeneratorService.generateData(jSonFilename);
        while (true)
        {
            try {
                System.out.println("1. SHOW ALL CARS");
                System.out.println("2. SORT CARS BY CRITERIA");
                System.out.println("3. CARS WITH MILEAGE IN RANGE");
                System.out.println("4. CARS WITH CHOSEN COLOR");
                System.out.println("5. CAR MODEL: MOST EXPENSIVE");
                System.out.println("6. STATISTICS: MILEAGE AND PRICE");
                System.out.println("7. THE MOST EXPENSIVE CAR ");
                System.out.println("8. CARS WITH SORTED COMPONENTS");
                System.out.println("9. CARS WITH A CHOSEN COMPONENT");
                System.out.println("10. CARS IN A PRICE RANGE");
                System.out.println("11. CLOSE PROGRAM");
                int option = scannerDataService.getInt("Enter the option:");

                switch (option)
                {
                    case 1: // show all cars
                        System.out.println(carService.getCars());
                        System.out.println("\n");
                        break;
                    case 2: // sort cars by criteria
                        sortCarsByCriteria();
                        break;
                    case 3: // cars with mileage in range
                        carsWithMileageInRange();
                        break;

                    case 4: // cars with chosen color
                        carsWithChosenColor();
                        break;

                    case 5: // car model: most expensive
                        theMostExpensiveModel();
                        break;

                    case 6: // statistics: mileage and price
                        statisticsMileagePrice();
                        break;

                    case 7: // the most expensive car
                        theMostExpensiveCars();
                        break;

                    case 8: // cars with sorted components
                        carsWithSortedComponents();
                        break;

                    case 9: // cars with a chosen components
                        carsWithChosenComponent();
                        break;

                    case 10: // cars in a price range
                        carsWithPriceInRange();
                        break;

                    case 11: // close programs
                        scannerDataService.close();
                        System.out.println("\n");
                        return;
                }

            } catch (MyException e) {
                System.err.println(e.getExceptionMessage());
            }
        }
    }

    // OPTION 2 - SORT CARS BY CRITERIA ----------------------------------------------------------------------
    private void sortCarsByCriteria()
    {
        System.out.println("    1. MODEL");
        System.out.println("    2. PRICE");
        System.out.println("    3. COLOR");
        System.out.println("    4. MILEAGE");

        int sortTypeValue = scannerDataService.getInt("Enter sort type");
        if (sortTypeValue < 1 || sortTypeValue > 4)
        {
            throw new MyException("Sort type value is not corect");
        }

        SortType sortType = SortType.values()[sortTypeValue - 1];
        System.out.println("You choosed: " + sortType);
        System.out.println("0 - DESC");
        System.out.println("1 - ASC");

        boolean descending = scannerDataService.getInt("Enter sort direction. Wrong value - ascending") == 0;
        carService.sort(sortType, descending).forEach(System.out::println);
        System.out.println("\n");
    }

    // OPTION 3 - CARS WITH MILEAGE IN RANGE -----------------------------------------------------------------
    private void carsWithMileageInRange()
    {
        int mileageValue = scannerDataService.getInt("Enter the mileage value:");
        List<Car> result =  carService.showCarsWithMileageGreaterThan(mileageValue);
        result.forEach(System.out::println);
        System.out.println("\n");
    }

    // OPTION 4 - CARS WITH CHOSEN COLOR ---------------------------------------------------------------------
    private void carsWithChosenColor()
    {   /*
        System.out.println("    1. BLACK");
        System.out.println("    2. SILVER");
        System.out.println("    3. WHITE");
        System.out.println("    4. RED");
        System.out.println("    5. BLUE");
        System.out.println("    6. GREEN");
        System.out.println("    7. YELLOW");
        System.out.println("    8. GREY");
        */
        //int colorValue = scannerDataService.getInt("Enter color:");
        //Color colors = Color.values()[colorValue - 1];

        Map<Color, Long> carsColor = carService.carsWithChosenColor();
        carsColor.forEach((k, v) -> System.out.println(k + " " + v));

        if (carsColor.isEmpty())
        {
            System.out.println("We don't have a car in this color :-( ");
        }

        System.out.println("\n");
    }

    // OPTION 5 - THE MOST EXPENSIVE MODEL -------------------------------------------------------------------
    private void theMostExpensiveModel()
    {
        carService
                .theMostExpensiveModel()
                .forEach((k, v) -> System.out.println(k + " " + v));
        System.out.println("\n");
    }

    // OPTION 6 - STATISTICS: MILEAGE & PRICE ----------------------------------------------------------------
    private void statisticsMileagePrice()
    {
        System.out.println("1. Mileage statistics");
        System.out.println("2. Price statistics");

        int statValue = scannerDataService.getInt("Enter statistic type:");
        StatisticType type = StatisticType.values()[statValue - 1];
        carService.priceAndMileageStatistics(type);
        System.out.println("\n");
    }

    // OPTION 7 - THE MOST EXPENSIVE CAR / CARS --------------------------------------------------------------
    private void theMostExpensiveCars()
    {
        List<Car> theMostExpCar = carService.theMostExpensiveCar();
        theMostExpCar.forEach(System.out::println);
        System.out.println("\n");
    }

    // OPTION 8 - CARS WITH SORTED COMPONENTS ----------------------------------------------------------------
    private void carsWithSortedComponents()
    {
        List<Car> sortedComponents = carService.carsWithSortedComponents();
        sortedComponents.forEach(System.out::println);
        System.out.println("\n");
    }

    // OPTION 9 - CARS WITH CHOSEN COMPONENT -----------------------------------------------------------------
    private void carsWithChosenComponent()
    {
        //String myComponent = scannerDataService.getString("Enter name of the component:");
        carService.carsWithChosenComponent();
        System.out.println("\n");
    }

    // OPTION 10 - CARS WITH PRICE IN RANGE ------------------------------------------------------------------
    private void carsWithPriceInRange()
    {
        List<Car> carsPriceRange = carService.carsWithPriceInRange(new BigDecimal(70000), new BigDecimal(100000));
        carsPriceRange.forEach(System.out::println);
        System.out.println("\n");
    }
}






















