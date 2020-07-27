package com.github.AngelBarov.tests;

import com.github.AngelBarov.Restaurant;
import com.github.AngelBarov.RestaurantsManager;

import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

import java.io.*;
import java.util.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)

class RestaurantTest {
    private static final String FILE_PATH = "listOfRestaurants.csv";
    private final RestaurantsManager restaurantsManager = new RestaurantsManager();
    private RestaurantsManager restaurantsManagerForComparing;

    @Test
    @Order(1)
    public void TestSaving() {

        restaurantsManager.add(new Restaurant(
                "r1",
                "a1",
                "0884824612",
                13,
                true,
                true,
                23.239,
                45.2837,
                null
        ));
        restaurantsManager.add(new Restaurant(
                "r2",
                "a2",
                null,
                13,
                true,
                true,
                23.239,
                45.2837,
                null
        ));
        restaurantsManager.add(new Restaurant(
                "r3",
                "a3",
                "0885468978",
                45,
                false,
                false,
                21.9,
                434.432,
                null
        ));
        restaurantsManager.add(new Restaurant(
                "r4",
                "a4",
                "0885468978",
                45,
                false,
                false,
                21.9,
                434.432,
                null
        ));

        restaurantsManager.save();

        restaurantsManagerForComparing = restaurantsManager;

        int savedR = 0;

        try{
            BufferedReader bf = new BufferedReader(new InputStreamReader(new FileInputStream(FILE_PATH), "UTF-8"));

            while(bf.readLine()!=null){
                savedR++;
            }
        }catch (Exception e){

        }

        assertEquals(4, savedR-1);
        assertEquals(restaurantsManagerForComparing, restaurantsManager);
    }

    @Test
    @Order(2)
    public void TestLoading() {
        restaurantsManagerForComparing = restaurantsManager;

        restaurantsManager.load();

        assertEquals(4, restaurantsManager.getRestaurants().size());
        assertEquals("r1", restaurantsManager.getRestaurants().get(0).getName());
        assertEquals("r2", restaurantsManager.getRestaurants().get(1).getName());
        assertEquals("r3", restaurantsManager.getRestaurants().get(2).getName());
        assertEquals("r4", restaurantsManager.getRestaurants().get(3).getName());
        assertEquals(restaurantsManagerForComparing, restaurantsManager);
    }

    @Test
    @Order(3)
    public void TestFind(){

        restaurantsManager.load();

        Restaurant restaurant = (Restaurant) this.restaurantsManager.getRestaurants().get(0);

        Restaurant restaurant1 = restaurantsManager.findById(restaurant.getId()).get();
        Restaurant restaurant2 = restaurantsManager.findByName(restaurant.getName()).get();

        assertEquals(restaurant, restaurant1);
        assertEquals(restaurant, restaurant2);
    }

    @Test
    @Order(4)
    public void TestDelete () {

        this.restaurantsManager.load();

        Restaurant restaurant = (Restaurant) this.restaurantsManager.getRestaurants().get(0);

        this.restaurantsManager.delete(restaurant.getId());

        assertEquals(3, this.restaurantsManager.getRestaurants().size());
        assertEquals("r2", this.restaurantsManager.getRestaurants().get(0).getName());

    }

    @Test
    @Order(5)
    public void TestUpdate(){

        List<Restaurant> restaurants = new ArrayList<Restaurant>();

        restaurants.add(new Restaurant(
                "n1",
                "b1",
                null,
                13,
                true,
                true,
                23.239,
                45.2837,
                null
        ));
        restaurants.add(new Restaurant(
                "djfndj",
                "ndjfn",
                null,
                35,
                true,
                true,
                3.239,
                45.2837,
                null
        ));

        restaurantsManager.update(restaurants);

        assertEquals(2, restaurantsManager.getRestaurants().size());
        assertEquals(restaurants, restaurantsManager.getRestaurants());
    }

}