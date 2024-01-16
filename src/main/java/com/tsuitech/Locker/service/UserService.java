package com.tsuitech.Locker.service;

import com.tsuitech.Locker.dao.*;
import com.tsuitech.Locker.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.*;

@Service
public class UserService {

    @Autowired
    private RegistrationRepository registrationRepo;
    public void saveData(RegistrationDao registrationDao)
    {
        registrationRepo.save(registrationDao);
    }

    @Autowired
    private AddCustomerRepository addCustomerRepo;
    public void addCustomerData(AddCustomer addCustomer)
    {
        addCustomerRepo.save(addCustomer);
    }

    public List<AddCustomer> getAllCustomers() {
        return addCustomerRepo.findAll();
    }
    @Autowired
    private ProfileRepository profileRepo;
    public void addProfile(Profile profile)
    {
        profileRepo.save(profile);
    }


    public Profile getData(Login login)
    {
      return  profileRepo.findByEmailAndMobile(login.getEmail(), Long.valueOf(login.getPassword()));

    }

    @Autowired
    private RetailerTableRepository retailerTableRepo;
    public String authenticateByEmail(String email, String password) {
        RetailerTable retailerTable=retailerTableRepo.findByEmail(email);
        if(retailerTable!=null) {
        if(password.equals(retailerTable.getPassword()))
            return "success";
            }
        return "Incorrect username or password";

    }

    public String authenticateByMobile(String mobile, String password) {
        RetailerTable retailerTable=retailerTableRepo.findByMobile(mobile);
        if(retailerTable!=null) {
            if(password.equals(retailerTable.getPassword()))
                return "success";
        }
        return "Incorrect username or password";
    }

    public RetailerTable add(RetailerTable retailerTable)
    {
        return retailerTableRepo.save(retailerTable);
    }

    private static final Map<String, String> otpStorage = new HashMap<>();

    public String generateOTP(String mobile) {
        // Generate a 6-digit OTP
        RetailerTable retailerTable=retailerTableRepo.findByMobile(mobile);
        String otp=String.format("%06d", new Random().nextInt(999999));;
        if(retailerTable!=null) {
            if (mobile.equals(retailerTable.getMobile())) {
                // Store OTP with mobile number
                otpStorage.put(mobile, otp);
                return "Your OTP is: " + otp;
            }
        }
            return "Invalid Number";

    }

    public boolean verifyOTP(String mobileNumber, String otp) {
        // Retrieve stored OTP for the mobile number
        String storedOTP = otpStorage.get(mobileNumber);

        // Verify OTP
        return storedOTP != null && storedOTP.equals(otp);
    }

    public Double calculateEmi(LoanDetails loanDetails)
    {
        Double principal= loanDetails.getLoanAmount();
        Double rate= loanDetails.getInterestRate()/100/12;
        int numberOfPayments=loanDetails.getTenure()*12;

        Double emi=(principal * rate * Math.pow(1 + rate, numberOfPayments))
                / (Math.pow(1 + rate, numberOfPayments) - 1);

        return emi;
    }

    @Autowired
    ImageRepository imageRepository;
    public void saveImage(MultipartFile file) throws IOException {
        Image image = new Image();
        image.setImageData(file.getBytes());
        imageRepository.save(image);
    }


    public byte[] getImageById(Integer id) {
        Image image = new Image();
        if (image != null) {
            if (id.equals(image.getId())) {
                return image.getImageData();
            }
        }
        return null;
    }

    public AddCustomer getDetailsByUserName(String userName)
    {
        AddCustomer addCustomer=  addCustomerRepo.findByUserName(userName);
        if(addCustomer!=null) {
            if (userName.equals(addCustomer.getUserName())) {

                return addCustomer;
            }
        }
        return null;
    }


    @Autowired
    LoanRepository loanRepo;

    public void addLoanData(LoanDetails loanDetails) {
        loanRepo.save(loanDetails);
    }

    public List<LoanDetails> getLoansByRetailerId(Integer retailerId) {
        //return loanRepo.findByRetailerId(retailerId);
        return loanRepo.findByRetailerTable_RetailerId(retailerId);
    }

    public Double calculateTotalLoanByRetailerId(Integer retailerId) {
        List<LoanDetails> loans = getLoansByRetailerId(retailerId);
        return loans.stream()
                .mapToDouble(LoanDetails::getLoanAmount)
                .sum();
    }
    public double calculateTotalBalanceByRetailerId(Integer retailerId) {
        List<LoanDetails> loans = getLoansByRetailerId(retailerId);
        return loans.stream()
                .mapToDouble(LoanDetails::getBalance)
                .sum();
    }

    public double calculateTotalEMIByRetailerId(Integer retailerId) {
        List<LoanDetails> loans = getLoansByRetailerId(retailerId);
        return loans.stream()
                .mapToDouble(LoanDetails::getEmi)
                .sum();
    }

    @Autowired
    CustomerLocationRepository customerLocationRepo;

    public boolean updateCustomerLocation(Map<String, Object> updateArr, String where) {
        // Assuming FamilyLocationRepository has methods findById and save
        // Implement your update logic here
        try {
            Long id = Long.parseLong(where.split("=")[1].trim());
            CustomerLocation customerLocation = customerLocationRepo.findById(id).orElse(null);

            if (customerLocation != null) {
                customerLocation.setLatitude((Double) updateArr.get("latitude"));
                customerLocation.setLongitude((Double) updateArr.get("longitude"));
                customerLocation.setAddress((String) updateArr.get("address"));

                customerLocationRepo.save(customerLocation);
                return true;
            } else {
                return false;
            }
        } catch (NumberFormatException e) {
            return false;
        }
    }


    public CustomerLocation getCustomerLocation(Long customerId) {
        return customerLocationRepo.findById(customerId).orElse(null);
    }


}
