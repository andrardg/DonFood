package com.example.donfood.service.ongService;

import com.example.donfood.dto.accountDTO.AccountRequestDTO;
import com.example.donfood.dto.ongDTO.ONGRequestDTO;
import com.example.donfood.dto.ongDTO.ONGResponseDTO;
import com.example.donfood.dto.ongDTO.ONGUpdateDTO;
import com.example.donfood.exception.ResourceNotFoundException;
import com.example.donfood.mapper.ONGMapper;
import com.example.donfood.model.ONG;
import com.example.donfood.model.enums.Right;
import com.example.donfood.repository.IONGRepository;
import com.example.donfood.service.accoutService.IAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class ONGService implements IONGService {
    @Autowired
    private IAccountService accountService;

    @Autowired
    private IONGRepository ongRepository;

    @Override
    public ONGResponseDTO register(ONGRequestDTO ongRequestDTO) {

        AccountRequestDTO accountRequestDTO = ongRequestDTO.getAccountRequestDTO();
        if(accountRequestDTO.getPasswordDecoded() == null)
            throw new IllegalArgumentException("Password cannot be empty");
        accountRequestDTO.setAccountVerified(false);
        accountRequestDTO.setCreatedAt(Timestamp.valueOf(LocalDateTime.now()));
        accountRequestDTO.setAccessRights(Right.ONG);

        ongRequestDTO.setAccountRequestDTO(accountRequestDTO);
        ONG ong = ONGMapper.requestToONG(ongRequestDTO);
        ong.setAccountONG(accountService.register(Right.ONG, ongRequestDTO.getAccountRequestDTO()));
        ongRepository.save(ong);

        ONGResponseDTO ongResponseDTO = ONGMapper.ONGToResponse(ong);
        return ongResponseDTO;
    }

    @Override
    public ONGResponseDTO update(Integer id, ONGUpdateDTO ongUpdateDTO) {

        ONG dbOng = ongRepository.findByOngId(id);

        if(dbOng == null)
            throw new ResourceNotFoundException("ONG was not found by id");

        if(ongUpdateDTO.getAddress() != null)
            dbOng.setAddress(ongUpdateDTO.getAddress());

        if(ongUpdateDTO.getNrPeopleHelping() != null)
            dbOng.setNrPeopleHelping(ongUpdateDTO.getNrPeopleHelping());

        if(ongUpdateDTO.getAccountUpdateDTO() != null)
            dbOng.setAccountONG(accountService.update(dbOng.getAccountONG().getEmail(), ongUpdateDTO.getAccountUpdateDTO()));

        ongRepository.save(dbOng);
        ONGResponseDTO ongResponseDTO = ONGMapper.ONGToResponse(dbOng);

        return ongResponseDTO;
    }

    @Override
    public void delete(Integer id) {

        if (id == null)
            throw new IllegalArgumentException("The id is null");
        if (!ongRepository.existsById(id))
            throw new ResourceNotFoundException("The ONG with id " + id + " was not found");

        ongRepository.deleteById(id);
        /*try{
            accountService.delete(email); //has delete cascade
        }
        catch(IllegalArgumentException e){
            throw new IllegalArgumentException("Error while deleting resource");
        }*/
    }

    @Override
    public List<ONGResponseDTO> getAll() {
        List<ONG> ongs =  ongRepository.findAll();
        return ONGMapper.ONGToResponseList(ongs);
    }

    @Override
    public ONGResponseDTO getById(Integer id) {
        if(!ongRepository.existsById(id))
            throw new ResourceNotFoundException("No ONG with that id.");
        ONG ong = ongRepository.getReferenceById(id);
        return ONGMapper.ONGToResponse(ong);
    }

    @Override
    public List<ONGResponseDTO> getByFullName(String fullName) {
        List<ONG> ongs = ongRepository.findByAccountONGFullName(fullName);

        if(ongs.size() == 0)
            throw  new ResourceNotFoundException("No ong found with that name");
        return ONGMapper.ONGToResponseList(ongs);
    }
}
