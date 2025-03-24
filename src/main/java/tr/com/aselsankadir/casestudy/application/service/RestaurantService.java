package tr.com.aselsankadir.casestudy.application.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import tr.com.aselsankadir.casestudy.domain.restaurant.AselRestaurant;
import tr.com.aselsankadir.casestudy.domain.restaurant.IRestaurantRepository;

@Service
@RequiredArgsConstructor
public class RestaurantService {

    private final IRestaurantRepository restaurantRepository;

    public Long add(AselRestaurant restaurant) {
        return restaurantRepository.save(restaurant);
    }
}
