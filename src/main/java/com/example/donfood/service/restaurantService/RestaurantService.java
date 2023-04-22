package com.example.donfood.service.restaurantService;

import com.example.donfood.dto.accountDTO.AccountRequestDTO;
import com.example.donfood.dto.restaurantDTO.RestaurantRequestDTO;
import com.example.donfood.dto.restaurantDTO.RestaurantResponseDTO;
import com.example.donfood.dto.restaurantDTO.RestaurantUpdateDTO;
import com.example.donfood.exception.ResourceNotFoundException;
import com.example.donfood.mapper.RestaurantMapper;
import com.example.donfood.model.ONG;
import com.example.donfood.model.Restaurant;
import com.example.donfood.model.enums.Right;
import com.example.donfood.repository.IONGRepository;
import com.example.donfood.repository.IRestaurantRepository;
import com.example.donfood.service.accoutService.IAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class RestaurantService implements IRestaurantService {
    @Autowired
    private IAccountService accountService;

    @Autowired
    private IRestaurantRepository restaurantRepository;

    @Autowired
    private IONGRepository ongRepository;

    @Override
    public RestaurantResponseDTO register(RestaurantRequestDTO restaurantRequestDTO) {
        AccountRequestDTO accountRequestDTO = restaurantRequestDTO.getAccountRequestDTO();
        if(accountRequestDTO.getPasswordDecoded() == null || accountRequestDTO.getPasswordDecoded().equals(""))
            throw new IllegalArgumentException("Password cannot be empty");
        accountRequestDTO.setAccountVerified(false);
        accountRequestDTO.setCreatedAt(Timestamp.valueOf(LocalDateTime.now()));
        accountRequestDTO.setAccessRights(Right.RESTAURANT);

        restaurantRequestDTO.setAccountRequestDTO(accountRequestDTO);
        Restaurant restaurant = RestaurantMapper.requestToRestaurant(restaurantRequestDTO);
        restaurant.setAccountRest(accountService.register(Right.RESTAURANT, restaurantRequestDTO.getAccountRequestDTO()));
        restaurantRepository.save(restaurant);

        RestaurantResponseDTO restaurantResponseDTO = RestaurantMapper.restaurantToResponse(restaurant);
        return restaurantResponseDTO;
    }

    @Override
    public RestaurantResponseDTO update(Integer id, RestaurantUpdateDTO restaurantUpdateDTO) {
        Restaurant dbRestaurant = restaurantRepository.findByRestaurantId(id);
        if(dbRestaurant == null)
            throw new ResourceNotFoundException("Restaurant was not found by id");

        if(restaurantUpdateDTO.getSocialScore() != null)
            dbRestaurant.setSocialScore(restaurantUpdateDTO.getSocialScore());

        if(restaurantUpdateDTO.getFiscalIdCode() != null)
            dbRestaurant.setFiscalIdCode(restaurantUpdateDTO.getFiscalIdCode());

        if(restaurantUpdateDTO.getAccountUpdateDTO() != null)
            dbRestaurant.setAccountRest(accountService.update(dbRestaurant.getAccountRest().getEmail(), restaurantUpdateDTO.getAccountUpdateDTO()));

        restaurantRepository.save(dbRestaurant);
        RestaurantResponseDTO restaurantResponseDTO = RestaurantMapper.restaurantToResponse(dbRestaurant);
        return restaurantResponseDTO;
    }

    @Override
    public void delete(Integer id) {
        if (id == null)
            throw new IllegalArgumentException("The id is null");
        if (!restaurantRepository.existsById(id))
            throw new ResourceNotFoundException("The Restaurant with id " + id + " was not found");

        restaurantRepository.deleteById(id);
    }

    @Override
    public List<RestaurantResponseDTO> getAll() {
        List<Restaurant> restaurants =  restaurantRepository.findAll();
        return RestaurantMapper.restaurantToResponseList(restaurants);
    }

    @Override
    public RestaurantResponseDTO getById(Integer id) {
        if(!restaurantRepository.existsById(id))
            throw new ResourceNotFoundException("No Restaurant with that id.");
        Restaurant restaurant = restaurantRepository.getReferenceById(id);
        return RestaurantMapper.restaurantToResponse(restaurant);
    }

    @Override
    public List<RestaurantResponseDTO> getByFullName(String fullname) {
        List<Restaurant> restaurants =  restaurantRepository.findByAccountRestFullName(fullname);
        if(restaurants.size() == 0)
            throw  new ResourceNotFoundException("No restaurant found with that name");
        return RestaurantMapper.restaurantToResponseList(restaurants);
    }

    @Override
    public RestaurantResponseDTO addFav(Integer id, String ongEmail) {
        if (id == null)
            throw new IllegalArgumentException("The id is null");
        if (!restaurantRepository.existsById(id))
            throw new ResourceNotFoundException("The Restaurant with id " + id + " was not found");

        if(ongEmail.isEmpty())
            throw new IllegalArgumentException("The logged in ONG email is null");
        if(!ongRepository.existsByAccountONG_Email(ongEmail))
            throw new ResourceNotFoundException("The ONG with email " + ongEmail + " was not found");

        Restaurant restaurant = restaurantRepository.findByRestaurantId(id);
        ONG ong = ongRepository.findByAccountONGEmail(ongEmail);
        if(restaurant.getFavOngs().stream().filter(x-> Objects.equals(x.getOngId(), ong.getOngId())).toList().size() > 0)
            throw new IllegalArgumentException("This was already added in the favourites");
        restaurant.addFav(ong);
        restaurantRepository.save(restaurant);
        return RestaurantMapper.restaurantToResponse(restaurant);
    }

    @Override
    public RestaurantResponseDTO removeFav(Integer id, String ongEmail) {
        if (id == null)
            throw new IllegalArgumentException("The id is null");
        if (!restaurantRepository.existsById(id))
            throw new ResourceNotFoundException("The Restaurant with id " + id + " was not found");

        if(ongEmail.isEmpty())
            throw new IllegalArgumentException("The logged in ONG email is null");
        if(!ongRepository.existsByAccountONG_Email(ongEmail))
            throw new ResourceNotFoundException("The ONG with email " + ongEmail + " was not found");

        Restaurant restaurant = restaurantRepository.findByRestaurantId(id);
        ONG ong = ongRepository.findByAccountONGEmail(ongEmail);
        if(restaurant.getFavOngs().stream().filter(x-> Objects.equals(x.getOngId(), ong.getOngId())).toList().size() == 0)
            throw new IllegalArgumentException("This did not exist in the favourites");
        restaurant.removeFav(ong);
        restaurantRepository.save(restaurant);
        return RestaurantMapper.restaurantToResponse(restaurant);
    }
}
