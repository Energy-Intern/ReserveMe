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

        restaurants.add(new Restaurant("r1", "a1", "0885674563", 13, true, true, 23.239, 45.2837,  null));
        restaurants.add(new Restaurant("r2", "a2", null, 13, true, true, 23.239, 45.2837,  null));

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

                List<String> values = Arrays.asList(line.split(COLUMN_SEPARATOR));

                if (Boolean.parseBoolean(values.get(2))){
                    restaurants.add(new Restaurant(
                            values.get(0),
                            values.get(1),
                            values.get(3),
                            Integer.parseInt(values.get(4)),
                            Boolean.parseBoolean(values.get(5)),
                            Boolean.parseBoolean(values.get(6)),
                            Double.parseDouble(values.get(7)),
                            Double.parseDouble(values.get(8)),
                            UUID.fromString(values.get(9))
                    ));
                } else {
                    restaurants.add(new Restaurant(
                            values.get(0),
                            values.get(1),
                            null,
                            Integer.parseInt(values.get(4)),
                            Boolean.parseBoolean(values.get(5)),
                            Boolean.parseBoolean(values.get(6)),
                            Double.parseDouble(values.get(7)),
                            Double.parseDouble(values.get(8)),
                            UUID.fromString(values.get(9))
                    ));
                }

                line = br.readLine();
            }
        }catch (FileNotFoundException e){
            e.printStackTrace();
        }catch (IOException e){
            e.printStackTrace();
        }

        assertEquals(howMuch, 2);
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

        restaurants.forEach(i -> {
            if(i.getName().equals("r1")){
                restaurantsManager.delete(i.getName());
            }
        });

        HashSet<Restaurant> restaurants1 = (HashSet<Restaurant>) restaurantsManager.load();

        assertEquals(size-1, restaurants1.size());

    }

    /*@Test
    @Order(5)
    public void TestUpdate(){
        HashSet<com.github.AngelBarov.Restaurant> restaurants = new HashSet<com.github.AngelBarov.Restaurant>();

        restaurants.add(new com.github.AngelBarov.Restaurant("n1", "b1", null, 13, true, true, 23.239, 45.2837, null));
        restaurants.add(new com.github.AngelBarov.Restaurant("djfndj", "ndjfn", null, 35, true, true, 3.239, 45.2837, null));

        try(FileWriter fw = new FileWriter("listOfRestaurants.csv", false);){
            new com.github.AngelBarov.RestaurantsManager().save(restaurants);
        }catch (IOException e) {
            e.printStackTrace();
        }

    }*/

}