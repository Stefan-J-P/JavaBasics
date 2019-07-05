package com.stefan.carstore;
import com.stefan.carstore.service.MenuService;

public class App
{
    public static void main( String[] args )
    {
        new MenuService().mainMenu();

        //System.out.println("1=============================================================");
//        List<Car> carsSortedAsc = carServiceList.sortedByUser(SortType.MODEL, false);
//        carsSortedAsc.forEach(System.out::println);

        //System.out.println("2=============================================================");
//        List<Car> carsSortedDesc = carServiceList.sortedByUser(SortType.MODEL, true);
//        carsSortedDesc.forEach(System.out::println);

        // CARS WITH MILEAGE OVER LIMIT -----------------------------------------------------
//        List<Car> carsWithMileageLimit = carServiceList.showCarsWithMileageGreaterThan(5000);

        // HOW MANY CARS WITH COLOR ---------------------------------------------------------
//        Set<Car> carResult = carServiceList.howManyCarsWithOneColor(RED);
//        Integer colorInt = carServiceList.howManyCarsWithColorInteger(BLACK);

        // CAR STATISTICS -------------------------------------------------------------------
//        carServiceList.priceAndMileageStatistics(StatisticType.MILEAGE);
//        carServiceList.priceAndMileageStatistics(StatisticType.PRICE);

        // THE MOST EXPENSIVE CARS ----------------------------------------------------------
//        List<Car> theMostExp = carServiceList.theMostExpensiveCar();
//        System.out.println("THE MOST EXPENSIVE CAR/CARS: ");
//        theMostExp.forEach(System.out::println);

        // CARS WITH SORTED COMPONENTS ------------------------------------------------------
//        List<Car> sortedComp = carServiceList.carsWithSortedComponents();
//        sortedComp.forEach(System.out::println);

        // CARS WITH PRICE IN RANGE ---------------------------------------------------------
//        List<Car> carsPriceInRange = carServiceList.carsWithPriceInRange(new BigDecimal(30000), new BigDecimal(100000));
//        carsPriceInRange.forEach(System.out::println);
    }
}
