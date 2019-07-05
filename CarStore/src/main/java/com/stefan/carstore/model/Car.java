package com.stefan.carstore.model;

import com.stefan.carstore.exceptions.MyException;
import com.stefan.carstore.model.enums.Color;
import com.stefan.carstore.model.enums.Components;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import java.math.BigDecimal;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@EqualsAndHashCode
@ToString
@Getter
public class Car
{
    private String model;
    private BigDecimal price;
    private Color color;
    private Integer mileage;
    private List<String> components; // lista String, do walidacji użyj strumienie


    // CONSTRUCTOR -------------------------------------------------------------------------------
    private Car(CarBuilder carBuilder) {
        this.model = carBuilder.model;
        this.price = carBuilder.price;
        this.mileage = carBuilder.mileage;
        this.color = carBuilder.color;
        this.components = carBuilder.components;
    }

    // GETTERS & SETTERS --------------------------------------------------------------------------

    // MODEL -------------------------
    public String getModel()
    {
        return model;
    }

    public void setModel(String model)
    {
        if (!isModelCorrect(model))
        {
            throw new MyException("MODEL IS NOT CORRECT: " + model);
        }

        this.model = model;
    }


    public void setPrice(BigDecimal price)
    {

        if (price.compareTo(BigDecimal.ZERO) > 0)
        {
            this.price = price;
        }
        else
        {
            this.price = new BigDecimal(0.99);
            System.out.println("You entered the wrong value!");
        }


    }

    public void setColor(Color color)
    {
        this.color = color;
    }

    public void setMileage(Integer mileage)
    {
        if (mileage > 0)
        {
            this.mileage = mileage;
        }
        else
        {
            this.mileage = 1;
            System.out.println("You entered the wrong value!");
        }
    }

    public void setComponents(List<String> components)
    {
        this.components = components;
    }

    // WALIDACJA
    // Method validates the String ---------------------------------------------------------------
    public static boolean isModelCorrect(String model)
    {
        Pattern patt = Pattern.compile("^[A-Z ]+$");
        Matcher match = patt.matcher(model);
        boolean res = match.find();
        return res;
        //return model == null || !model.matches("[A-Z ]+");
    }

    // CAR BUILDER ---------------------------------------------------------------------------------
    public static class CarBuilder
    {
        private String model;
        private BigDecimal price;
        private Color color;
        private Integer mileage;
        private List<String> components;

        public CarBuilder() {}


        public CarBuilder model(String model)
        {
            if (isModelCorrect(model))
            {
                this.model = model;
            }
            else
            {
                System.out.println("WRONG SYNTAX: Only block letters and white spaces are allowed");
            }
            return this;
        }

        public CarBuilder price(BigDecimal price)
        {
            if (price.compareTo(BigDecimal.ZERO) > 0)
            {
                this.price = price;
            }
            else
            {
                this.price = new BigDecimal(0.99);
                System.out.println("You entered the wrong value!");
            }
            return this;
        }

        public CarBuilder color(Color color)
        {
            this.color = color;
            return this;
        }

        public CarBuilder mileage(Integer mileage)
        {
            if (mileage > 0)
            {
                this.mileage = mileage;
            }
            else
            {
                this.mileage = 1;
                System.out.println("You entered the wrong value!");
            }
            return this;
        }

        public CarBuilder components(List<String> components)
        {
            for (int i = 0; i < components.size(); ++i)
            {
                 if (isModelCorrect(components.get(i)))
                 {
                     this.components = components;
                 }
                 else
                 {
                     System.out.println("WRONG SYNTAX: Only block letters and white spaces are allowed");
                 }
            }
            return this;
        }

        public Car build()
        {
            return new Car(this);
        }
    }

}


















