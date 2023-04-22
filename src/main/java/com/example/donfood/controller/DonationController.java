package com.example.donfood.controller;

import com.example.donfood.dto.donationDTO.DonationRequestDTO;
import com.example.donfood.model.Donation;
import com.example.donfood.model.enums.Measure;
import com.example.donfood.service.donationService.DonationService;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@RestController
@RequestMapping("/api/donation")
@Slf4j
public class DonationController {
    @Autowired
    private DonationService donationService;

    @GetMapping
    //@PreAuthorize("hasAuthority('ROLE_ADMIN')")
    //public ResponseEntity<List<Donation>> getAll() {
    public ModelAndView getAll(@RequestParam("page") Optional<Integer> page,
                               @RequestParam("size") Optional<Integer> size){
        int currentPage = page.orElse(1);
        int pageSize = size.orElse(5);
        Page<Donation> donationPage = donationService.findPaginated(PageRequest.of(currentPage - 1, pageSize));

        ModelAndView modelAndView = new ModelAndView("donationList");
        List<Donation> donations = donationService.getAll();
        modelAndView.addObject("donations",donations);
        modelAndView.addObject("donationPage", donationPage);
        modelAndView.addObject("portion", Measure.portion);
        int totalPages = donationPage.getTotalPages();
        if (totalPages > 0) {
            List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages)
                    .boxed()
                    .collect(Collectors.toList());
            modelAndView.addObject("pageNumbers", pageNumbers);
        }
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

    @GetMapping("/delete/{id}")
    public void delete(@PathVariable Integer id, HttpServletResponse response) throws IOException {
        donationService.delete(id);
        response.sendRedirect("/api/donation");
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
