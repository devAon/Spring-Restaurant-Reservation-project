package devAon.com.restaurant.domain;

import org.junit.Test;

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.is;

public class RestaurantRepositoryImplTest {

    @Test
    public void save(){
        RestaurantRepository restaurantRepository = new RestaurantRepositoryImpl();

        int oldCount = restaurantRepository.findAll().size();

        Restaurant restaurant = new Restaurant("BeRyong", "Busan");
        restaurantRepository.save(restaurant);

        assertThat(restaurant.getId(), is(1234L));

        int newCount = restaurantRepository.findAll().size();

        assertThat(newCount - oldCount, is(1));
    }

}