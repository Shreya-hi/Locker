package com.tsuitech.Locker.controller;

import com.tsuitech.Locker.dao.*;
import com.tsuitech.Locker.repository.RetailerTableRepository;
import com.tsuitech.Locker.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.sql.ResultSet;
import java.util.*;

@Controller
@RestController
public class AppController {

    @Autowired
    UserService userService;

    @PostMapping("/save")
    public void saveData(@RequestBody RegistrationDao registrationDao)
    {
    userService.saveData(registrationDao);
    }

    @PostMapping("/insertData")
    public void addCustomerData(@RequestBody AddCustomer addCustomer)
    {
        userService.addCustomerData(addCustomer);
    }

    @GetMapping("/customer-list")
    public List<AddCustomer> getCustomerListPage(AddCustomer addCustomer)
            //we can also pass (Model model) as parameter in above method. In that case no need to create addAttribute
    {
        List<AddCustomer> customers = userService.getAllCustomers();
        addCustomer.addAttribute("al", customers);
        return customers;
    }

    @PostMapping("/viewByName")
    public AddCustomer viewByName(@RequestParam String name)
    {
        return userService.getDetailsByUserName(name);
    }

    @PostMapping("/newProfile")
    public void addProfile(@RequestBody Profile profile)
    {
        userService.addProfile(profile);
    }

    @PostMapping("/addRetailer")
    public RetailerTable addData(@RequestBody RetailerTable retailerTable)
    {
        return userService.add(retailerTable);
    }


    @PostMapping("/loginRetailer")
    public String login(@RequestParam(required = false) String email,String password, @RequestParam(required = false) String mobile) {
        if (email != null) {
            return userService.authenticateByEmail(email, password) ;
        } else if (mobile != null) {
            return userService.authenticateByMobile(mobile, password) ;
        }
        return "failure"; // Default to failure if neither email nor mobile is provided
    }

    @PostMapping("/sendOTP")
    public String sendOTP(@RequestParam String mobile) {
        // Generate OTP
        return userService.generateOTP(mobile);
    }

    @PostMapping("/verifyOTP")
    public ResponseEntity<String> verifyOTP(@RequestParam String mobile, @RequestParam String otp) {
        if (userService.verifyOTP(mobile, otp)) {
            return ResponseEntity.ok("OTP verified successfully");
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid OTP");
        }
    }

    @PostMapping("/emi")
    public double emiDetails(@RequestBody LoanDetails loanDetails){
        return userService.calculateEmi(loanDetails);
    }

    @PostMapping("/upload")
    public ResponseEntity<String> uploadImage(@RequestParam("file") MultipartFile file) {
        try {
            userService.saveImage(file);
            return ResponseEntity.ok("Image uploaded successfully.");
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body("Failed to upload image.");
        }
    }

   /* @Autowired
    private ImageRepository imageRepo;

    @PostMapping("/add")
    public ResponseEntity<String> addImage(@RequestParam("name") String name,
                                              @RequestParam("image") MultipartFile image) {
        try {
            Image img = new Image();
           img.setName(name);
            img.setImageData(image.getBytes());
            imageRepo.save(img);
            return ResponseEntity.ok("Customer added successfully.");
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body("Error adding customer.");
        }
    }*/

    @GetMapping("/fetchImage/{id}")
    public ResponseEntity<byte[]> getImage(@PathVariable Integer id) {
        byte[] imageData = userService.getImageById(id);
        if (imageData != null) {
            return ResponseEntity.ok(imageData);
        } else {
            return ResponseEntity.notFound().build();
        }
    }


    @PostMapping("/insertLoanDetails")
    public void addLoanDetails(@RequestBody LoanDetails loanDetails)
    {
        userService.addLoanData(loanDetails);
    }

    @Autowired
    private RetailerTableRepository retailerRepo;
    @GetMapping("/totalLoan/{retailerId}")
    public ResponseEntity<Double> getTotalLoanByRetailer(@PathVariable Integer retailerId) {
        if (!retailerRepo.existsById(retailerId)) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        double totalLoan = userService.calculateTotalLoanByRetailerId(retailerId);
        return new ResponseEntity<>(totalLoan, HttpStatus.OK);
    }

    @GetMapping("/totalBalance/{retailerId}")
    public ResponseEntity<Double> getTotalBalanceByRetailer(@PathVariable Integer retailerId) {
        if (!retailerRepo.existsById(retailerId)) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        double totalBalance = userService.calculateTotalBalanceByRetailerId(retailerId);
        return new ResponseEntity<>(totalBalance, HttpStatus.OK);
    }

    @GetMapping("/totalEMI/{retailerId}")
    public ResponseEntity<Double> getTotalEMIByRetailer(@PathVariable Integer retailerId) {
        if (!retailerRepo.existsById(retailerId)) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        double totalEMI = userService.calculateTotalEMIByRetailerId(retailerId);
        return new ResponseEntity<>(totalEMI, HttpStatus.OK);
    }


    @PostMapping("/updateCustomerLocation")
    public ResponseEntity<Map<String, Object>> updateCustomerLocation(@RequestBody Map<String, Object> requestMap) {
        Map<String, Object> response = new HashMap<>();
        response.put("result", 0);
        response.put("message", "");

        try {
            Long userId = (Long) requestMap.get("user_id");
            Long id = (Long) requestMap.get("id");
            Double lat = (Double) requestMap.get("lat");
            Double lon = (Double) requestMap.get("lon");
            String address = (String) requestMap.get("address");

            if (userId == null || id == null || lat == null || lon == null) {
                response.put("message", "Please pass all parameters");
            } else {
                String where = "id = " + id;
                Map<String, Object> updateArr = new HashMap<>();
                updateArr.put("latitude", lat);
                updateArr.put("longitude", lon);
                updateArr.put("address", address);

                boolean detailData = userService.updateCustomerLocation(updateArr, where);

                if (detailData) {
                    response.put("result", 1);
                    response.put("message", "Location data updated");
                } else {
                    response.put("result", 0);
                    response.put("message", "Sorry! Something went wrong. Please try again");
                }
            }
        } catch (Exception e) {
            response.put("result", 0);
            response.put("message", "An error occurred: " + e.getMessage());
        }

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/getCustomerLocation")
    public ResponseEntity<Map<String, Object>> getCustomerLocation(@RequestParam Long customerId) {
        Map<String, Object> response = new HashMap<>();
        response.put("result", 0);
        response.put("message", "");

        try {
            // Retrieve the location for the specified family member
            CustomerLocation customerLocation = userService.getCustomerLocation(customerId);

            if (customerLocation != null) {
                response.put("result", 1);
                response.put("message", "Location data retrieved successfully");
                response.put("location", customerLocation);
            } else {
                response.put("result", 0);
                response.put("message", "Family member location not found");
            }
        } catch (Exception e) {
            response.put("result", 0);
            response.put("message", "An error occurred: " + e.getMessage());
        }

        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
