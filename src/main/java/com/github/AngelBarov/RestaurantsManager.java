package com.github.AngelBarov;

import java.io.*;
import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Stream;

import org.apache.commons.csv.*;

public class RestaurantsManager {
    private static final String FILE_PATH = "listOfRestaurants.csv";
    private static final String[] HEADER = {
            "Name",
            "Address",
            "Has Telephone Number?",
            "Telephone Number",
            "Places",
            "Outside sitting",
            "Lunch Menu",
            "Longtitude",
            "Latitude",
            "Id"
    };
    private Set<Restaurant> restaurants = new HashSet<Restaurant>();

    public Set<Restaurant> getRestaurants() {
        return restaurants;
    }

    public void update(Collection<Restaurant> restaurants1){
        this.restaurants = (Set) restaurants1;
        try(FileWriter fw = new FileWriter(FILE_PATH, false);){
            save();
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    public Optional<Restaurant> findByName(String name){
        return find(restaurant -> restaurant.getName().equals(name));
    }

    public Optional<Restaurant> findById(String id){
        return find(restaurant -> restaurant.getId().equals(id));
    }

    private Optional<Restaurant> find(Predicate<Restaurant> predicate){
         return this.restaurants.stream()
                .filter(predicate)
                .findFirst();
    }

    public void load() {
        try {
            restaurants.clear();
            File file = new File(FILE_PATH);
            InputStreamReader input = new InputStreamReader(new FileInputStream(file));
            CSVParser csvParser = CSVFormat.EXCEL.withFirstRecordAsHeader().parse(input);
            csvParser.forEach(i->
                this.restaurants.add(new Restaurant(
                        i.get("Name"),
                        i.get("Address"),
                        i.get("Telephone Number"),
                        Integer.parseInt(i.get("Places")),
                        Boolean.parseBoolean(i.get("Outside sitting")),
                        Boolean.parseBoolean(i.get("Lunch Menu")),
                        Double.parseDouble(i.get("Longtitude")),
                        Double.parseDouble(i.get("Latitude")),
                        i.get("Id")
                ))
            );
        }catch (IOException e){
            e.printStackTrace();
        }

    }

    public void add(Restaurant restaurant){
        this.restaurants.add(restaurant);
    }

    public void delete(String id){
        load();

        this.restaurants.removeIf(restaurant -> restaurant.getId().equals(id));

        save();
    }

    public void save(){
        try(CSVPrinter csvPrinter = new CSVPrinter(new FileWriter(FILE_PATH), CSVFormat.DEFAULT.withHeader(HEADER))){

            this.restaurants.forEach(restaurant->{
                    try{
                        csvPrinter.printRecord(RestaurantToString(restaurant));
                    }catch (IOException e){
                        e.printStackTrace();
                    }
            });
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public String[] RestaurantToString(Restaurant restaurant){
        try{
            String[] restaurantString = {
                    restaurant.getName().trim(),
                    restaurant.getAddress().trim(),
                    String.valueOf(restaurant.isNumber()),
                    restaurant.getTelephoneNumber().trim(),
                    String.valueOf(restaurant.getPlaces()),
                    String.valueOf(restaurant.isOutside()),
                    String.valueOf(restaurant.isLunchMenu()),
                    String.valueOf(restaurant.getLongtitude()),
                    String.valueOf(restaurant.getLatitude()),
                    restaurant.getId()
            };
            return restaurantString;
        }catch (NullPointerException e){
            String[] restaurantString = {
                    restaurant.getName().trim(),
                    restaurant.getAddress().trim(),
                    String.valueOf(restaurant.isNumber()),
                    null,
                    String.valueOf(restaurant.getPlaces()),
                    String.valueOf(restaurant.isOutside()),
                    String.valueOf(restaurant.isLunchMenu()),
                    String.valueOf(restaurant.getLongtitude()),
                    String.valueOf(restaurant.getLatitude()),
                    restaurant.getId()
            };

            return restaurantString;
        }
    }

}
