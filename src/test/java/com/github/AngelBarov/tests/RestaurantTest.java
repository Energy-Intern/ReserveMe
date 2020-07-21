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
    private static final String COLUMN_SEPARATOR = ",";

    @Test
    @Order(1)
    public void TestSaving() {

        HashSet<Restaurant> restaurants = new HashSet<Restaurant>();

        restaurants.add(new Restaurant(
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
        restaurants.add(new Restaurant(
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
        restaurants.add(new Restaurant(
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
        restaurants.add(new Restaurant(
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

        RestaurantsManager restaurantsManager = new RestaurantsManager();
        restaurantsManager.save(restaurants);

        int savedR = 0;

        try{
            BufferedReader bf = new BufferedReader(new InputStreamReader(new FileInputStream(FILE_PATH), "UTF-8"));

            while(bf.readLine()!=null){
                savedR++;
            }

        }catch (Exception e){

        }
        assertEquals(restaurants.size(), savedR-1);
    }

    @Test
    @Order(2)
    public void TestLoading() {

        int howMuch=0;
        HashSet<Restaurant> restaurants = new HashSet<Restaurant>();

        try {
            BufferedReader br = new BufferedReader(new FileReader(FILE_PATH));
            String line = br.readLine();
            line=br.readLine();

            while(line!=null) {

                howMuch++;

                line = br.readLine();
            }
        }catch (FileNotFoundException e){
            e.printStackTrace();
        }catch (IOException e){
            e.printStackTrace();
        }

        assertEquals(howMuch, 4);
    }

    @Test
    @Order(3)
    public void TestFind(){
        RestaurantsManager restaurantsManager = new RestaurantsManager();

        HashSet<Restaurant> restaurants1 = (HashSet<Restaurant>) restaurantsManager.load();

        Restaurant r = (Restaurant)restaurants1.toArray()[0];

        String name = r.getName();
        String uuid = r.getUuid().toString();

        assertEquals(r.getUuid(), restaurantsManager.find(restaurants1, name).getUuid());
        assertEquals(r.getName(), restaurantsManager.find(restaurants1, uuid).getName());

    }

    @Test
    @Order(4)
    public void TestDelete () {

        RestaurantsManager restaurantsManager = new RestaurantsManager();

        HashSet<Restaurant> restaurants = (HashSet<Restaurant>) restaurantsManager.load();

        int size = restaurants.size();

        restaurantsManager.delete("r1");

         restaurants = (HashSet<Restaurant>) restaurantsManager.load();

        assertEquals(size-1, restaurants.size());

    }

    @Test
    @Order(5)
    public void TestUpdate(){
        HashSet<Restaurant> restaurants = new HashSet<Restaurant>();

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

        try(FileWriter fw = new FileWriter("listOfRestaurants.csv", false);){
            new RestaurantsManager().save(restaurants);
        }catch (IOException e) {
            e.printStackTrace();
        }

    }

}