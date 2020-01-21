package devAon.com.restaurant.interfaces;

import devAon.com.restaurant.application.RestaurantService;
import devAon.com.restaurant.domain.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

@CrossOrigin
@RestController
public class RestaurantController {

    /*
    RestaurantService에서
    RestaurantRepository 와 MenuItemRepository에 대한
    정보를 줄 것이기 때문에 주석처리

    @Autowired
    private RestaurantRepository restaurantRepository;

    @Autowired
    private MenuItemRepository menuItemRepository;

    */


    @Autowired
    private RestaurantService restaurantService;


    @GetMapping("/restaurants")
    public List<Restaurant> list(){
        /*List<Restaurant> restaurants = restaurantRepository.findAll();*/
        List<Restaurant> restaurants = restaurantService.getRestaurants();

        return restaurants;
    }

    @GetMapping("/restaurants/{id}")
    public Restaurant detail(@PathVariable("id") Long id)
    {

        //기본 정보 + 메뉴 정보
        Restaurant restaurant = restaurantService.getRestaurant(id);

        //Restaurant restaurant = restaurantRepository.findById(id);
        //List<MenuItem> menuItems = menuItemRepository.findAllByRestaurantId(id);
        //restaurant.setMenuItems(menuItems);

        return restaurant;
    }

    @PostMapping("/restaurants")
    public ResponseEntity<?> create(@RequestBody Restaurant resource) throws URISyntaxException {
        /*String name = "BeRyong";
        String address = "Busan";*/

        String name = resource.getName();
        String address = resource.getAddress();

        Restaurant restaurant = new Restaurant(1234L, name, address);
        //Restaurant restaurant = new Restaurant(name, address);
        restaurantService.addRestaurant(restaurant);

        URI location = new URI("/restaurants/" + restaurant.getId());
        return ResponseEntity.created(location).body("{}");
    }
}
