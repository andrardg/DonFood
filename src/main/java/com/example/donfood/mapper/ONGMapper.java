package com.example.donfood.mapper;

import com.example.donfood.dto.ongDTO.ONGRequestDTO;
import com.example.donfood.dto.ongDTO.ONGResponseDTO;
import com.example.donfood.dto.ongDTO.ONGUpdateDTO;
import com.example.donfood.model.ONG;
import org.modelmapper.ModelMapper;

import java.util.List;
import java.util.stream.Collectors;

public class ONGMapper {
    public static ONG requestToONG(ONGRequestDTO ongRequestDTO){

        ModelMapper modelMapper = new ModelMapper();
        ONG ong = modelMapper.map(ongRequestDTO, ONG.class);
        return ong;
    }
    public static ONGResponseDTO ONGToResponse(ONG ong){
        ModelMapper modelMapper = new ModelMapper();
        ONGResponseDTO ongResponseDTO = modelMapper.map(ong, ONGResponseDTO.class);
        return ongResponseDTO;
    }
    public List<ONG> requestToONGList(List<ONGRequestDTO> ongRequestDTOS) {
        return ongRequestDTOS.stream()
                .map(ONGMapper::requestToONG)
                .collect(Collectors.toList());
    }
    public static List<ONGResponseDTO> ONGToResponseList(List<ONG> ongs) {
        return ongs.stream()
                .map(ONGMapper::ONGToResponse)
                .collect(Collectors.toList());
    }
    public static ONGUpdateDTO responseToUpdate(ONGResponseDTO ongResponseDTO){
        ModelMapper modelMapper = new ModelMapper();
        ONGUpdateDTO ongUpdateDTO = modelMapper.map(ongResponseDTO, ONGUpdateDTO.class);
        ongUpdateDTO.setAccountUpdateDTO(AccountMapper.accountToUpdate(ongResponseDTO.getAccountONG()));
        return ongUpdateDTO;
    }
}
