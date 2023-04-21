package com.example.donfood.controller;

import com.example.donfood.dto.donationDTO.DonationRequestDTO;
import com.example.donfood.dto.donationDTO.DonationUpdateDTO;
import com.example.donfood.dto.ongDTO.ONGResponseDTO;
import com.example.donfood.dto.restaurantDTO.RestaurantResponseDTO;
import com.example.donfood.dto.restaurantDTO.RestaurantUpdateDTO;
import com.example.donfood.mapper.RestaurantMapper;
import com.example.donfood.model.Donation;
import com.example.donfood.model.enums.Measure;
import com.example.donfood.service.donationService.DonationService;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/api/donation")
@Slf4j
public class DonationController {
    @Autowired
    private DonationService donationService;

    @GetMapping
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    //public ResponseEntity<List<Donation>> getAll() {
    public ModelAndView getAll(){
        ModelAndView modelAndView = new ModelAndView("donationList");
        List<Donation> donations = donationService.getAll();
        modelAndView.addObject("donations",donations);
        modelAndView.addObject("portion", Measure.portion);
        return modelAndView;
        //return ResponseEntity.ok().body(donationService.getAll());
    }

    @GetMapping("/{id}")
    //public ResponseEntity<Donation> getById(@PathVariable Integer id){
    public ModelAndView getById(@PathVariable Integer id){
        ModelAndView modelAndView = new ModelAndView("donationDetails");
        Donation donation = donationService.getById(id);
        modelAndView.addObject("donation",donation);
        log.info("Donation with id " + donation.getDonationId());
        return modelAndView;
        //return ResponseEntity.ok().body(donationService.getById(id));
    }
    @GetMapping("/product/{product}")
    public ResponseEntity<List<Donation>> getByProduct(@PathVariable String product){
        return ResponseEntity.ok().body(donationService.findByProduct(product));
    }

    @GetMapping("/{quantity}/{product}")
    public ResponseEntity<List<Donation>> getByQuantityAndProduct(@PathVariable Double quantity, @PathVariable String product){
        return ResponseEntity.ok().body(donationService.getByQuantityAndProduct(quantity, product));
    }

    @PostMapping
    public ResponseEntity<Donation> create(@RequestBody DonationRequestDTO donationRequestDTO){
        return ResponseEntity.ok().body(donationService.create(donationRequestDTO));
    }
    @RequestMapping("/edit/{id}")
    public ModelAndView donationEdit(@PathVariable Integer id) {
        ModelAndView modelAndView = new ModelAndView("donationForm");
        Donation donation = donationService.getById(id);
        modelAndView.addObject("donation", donation);
        modelAndView.addObject("measures", Arrays.asList(Measure.values()));
        return modelAndView;
    }
    @PostMapping("/{id}")
    public ModelAndView update(@PathVariable Integer id, @Valid @ModelAttribute Donation donation,
                               BindingResult bindingResult){
        if (bindingResult.hasErrors()){
            ModelAndView modelAndView = new ModelAndView("donationForm");
            donation.setDonationId(id);
            modelAndView.addObject("donation", donation);
            modelAndView.addObject("measures", Arrays.asList(Measure.values()));
            return modelAndView;
        }
        donationService.update(id, donation);
        ModelAndView modelAndView = new ModelAndView("donationDetails");
        //donation = donationService.getById(id);
        modelAndView.addObject("donation",donation);
        return modelAndView;
    }
//    @PutMapping("/{id}")
//    public ResponseEntity<Donation> update(@PathVariable Integer id, @RequestBody DonationUpdateDTO donationUpdateDTO){
//        return ResponseEntity.ok().body(donationService.update(id, donationUpdateDTO));
//    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Integer id, HttpServletResponse response) throws IOException {
        donationService.delete(id);
        response.sendRedirect("/api/donations");
    }

//    @GetMapping("/getimage/{id}")
//    public void downloadImage(@PathVariable String id, HttpServletResponse response) throws IOException {
//        Product product = productService.findById(Long.valueOf(id));
//        if (product.getInfo() != null) {
//            Info info = product.getInfo();
//
//            if (product.getInfo().getPhoto() != null) {
//                byte[] byteArray = new byte[info.getPhoto().length];
//                int i = 0;
//                for (Byte wrappedByte : info.getPhoto()) {
//                    byteArray[i++] = wrappedByte;
//                }
//                response.setContentType("image/jpeg");
//                InputStream is = new ByteArrayInputStream(byteArray);
//                try {
//                    IOUtils.copy(is, response.getOutputStream());
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            }
//        }
//    }
}
