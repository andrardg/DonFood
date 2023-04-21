package com.example.donfood.service.ongService;

import com.example.donfood.dto.ongDTO.ONGRequestDTO;
import com.example.donfood.dto.ongDTO.ONGResponseDTO;
import com.example.donfood.dto.ongDTO.ONGUpdateDTO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface IONGService {
    ONGResponseDTO register(ONGRequestDTO ongRequestDTO);
    ONGResponseDTO update(Integer id, ONGUpdateDTO ongUpdateDTO);
    ONGResponseDTO addFavRestaurant(Integer ongId, Integer restaurantId);
    ONGResponseDTO removeFavRestaurant(Integer ongId, Integer restaurantId);
    void delete(Integer id);
    List<ONGResponseDTO> getAll();
    ONGResponseDTO getById(Integer id);
    List<ONGResponseDTO> getByFullName(String fullName);
}
