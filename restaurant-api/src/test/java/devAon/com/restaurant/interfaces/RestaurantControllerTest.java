package devAon.com.restaurant.interfaces;

import devAon.com.restaurant.application.RestaurantService;
import devAon.com.restaurant.domain.*;

import org.junit.Test;
/*import org.junit.jupiter.api.Test;*/

import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.containsString;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@WebMvcTest(RestaurantController.class)
public class RestaurantControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private RestaurantService restaurantService;
    //가짜로 바뀌게 됨. SpyBean 모두 제거.

    /* @SpyBean(RestaurantService.class)
    private RestaurantService restaurantService;
    */

   /* @SpyBean(RestaurantRepositoryImpl.class)
    private RestaurantRepository restaurantRepository;

    @SpyBean(MenuItemRepositoryImpl.class)
    private MenuItemRepository menuItemRepository;
*/

    //가게 목록
    @Test
    public void list() throws Exception {
        List<Restaurant> restaurants = new ArrayList<>();
        restaurants.add( Restaurant.builder().id(1004L).name("Bob zip").address("Seoul").build());

        given(restaurantService.getRestaurants()).willReturn(restaurants);

        mvc.perform(get("/restaurants"))
                .andExpect(status().isOk())
                .andExpect(content().string(
                        containsString("\"id\":1004")
                ))
                .andExpect(content().string(
                        containsString("\"name\":\"Bob zip\"")
                ));

    }


    //가게 상세
    @Test
    public void detailWithExisted() throws Exception {
        Restaurant restaurant =  Restaurant.builder().id(1004L).name("Bob zip").address("Seoul").build();

        given(restaurantService.getRestaurant(1004L)).willReturn(restaurant);


        mvc.perform(get("/restaurants/1004"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("\"id\":1004")))
                .andExpect(content().string(containsString("\"name\":\"Bob zip\"")))
                .andExpect(content().string(containsString("kimchi")));
        mvc.perform(get("/restaurants/2020"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("\"id\":2020")))
                .andExpect(content().string(containsString("\"name\":\"Cyber Food\"")));
    }

    @Test
    public void detailWithNotExisted() throws Exception {

            given(restaurantService.getRestaurant(1004L))
                    .willThrow(new RestaurantNotFoundException(1004L));


            mvc.perform(get("/restaurants/1004"))
                    .andExpect(status().isNotFound())
                    .andExpect(content().string("{}"));
        }


//가게추가
        //{"name" : "BeRyong", "address" : "Busan"}
        @Test
        public void createWithValidData() throws Exception {
            given(restaurantService.addRestaurant(any())).will(invocation -> {
                Restaurant restaurant = invocation.getArgument(0);
                return Restaurant.builder()
                        .id(1234L)
                        .name(restaurant.getName())
                        .address(restaurant.getAddress())
                        .build();
    });

    mvc.perform(post("/restaurants")
            .contentType(MediaType.APPLICATION_JSON)
            .content("{\"name\" : \"BeRyong\", \"address\" : \"Busan\"}"))
            .andExpect(status().isCreated())
            .andExpect(header().string("location", "/restaurants/1234"))
            .andExpect(content().string("{}"));

    verify(restaurantService).addRestaurant(any());
}

   /* @Test
    public void createWithInvalidData() throws Exception {
        mvc.perform(post("/restaurants")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"name\":\"\",\"address\":\"\"}"))
                .andExpect(status().isBadRequest());
    }*/

    @Test
    public void create() throws Exception {
        //Restaurant restaurant = new Restaurant(1234L, "BeRyong", "Busan");
        mvc.perform(post("/restaurants")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"name\" : \"BeRyong\", \"address\" : \"Busan\"}"))
                .andExpect(status().isCreated())
                .andExpect((header().string("location", "/restaurants/1234")))
                .andExpect(content().string("{}"));
    }


    @Test
    public void update() throws Exception {
        mvc.perform(patch("/restaurants/1004")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"name\" : \"BeRyong\", \"address\" : \"Busan\"}"))
                .andExpect(status().isOk());

        verify(restaurantService)
                .updateRestaurant(1004L, 1L, "JOKER Bar", "Busan");
    }





}