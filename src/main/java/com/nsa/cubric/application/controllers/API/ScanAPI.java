package com.nsa.cubric.application.controllers.API;
import com.nsa.cubric.application.controllers.RegistrationAccount;
import com.nsa.cubric.application.domain.*;
import com.nsa.cubric.application.services.AccountServiceStatic;
import com.nsa.cubric.application.services.LoggedUserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import com.nsa.cubric.application.services.ScanService;
import org.springframework.boot.autoconfigure.AutoConfigureOrder;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.nsa.cubric.application.domain.Scan;
import com.nsa.cubric.application.services.UserResponseServiceStatic;

import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.io.File;

@RequestMapping("scans")
@RestController
public class ScanAPI {

    private static final Logger LOG = LoggerFactory.getLogger(RegistrationAccount.class);


    private UserResponseServiceStatic responsesService;

    private Random randomGenerator;

    @Autowired
    private ScanService scanService;

    @Autowired
    LoggedUserService loggedUserService;

    @Autowired
    AccountServiceStatic accountService;

    public static final String imageUploadDirectory = System.getProperty("user.dir") + "/brain_images/";

    @Autowired
    public ScanAPI(ScanService scanService, UserResponseServiceStatic aRepo){
        this.scanService = scanService;
        this.responsesService = aRepo;
    }

    /**
     * This method is used to serve the JSON for the scan to view. It responds to GET
     * requests to /scans/next.
     *
     * @return ResponseEntity object containing scan JSON.
     */
    @RequestMapping(value = "next", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity getNextScan() {
        Optional<Scan> scan = scanService.getNext();
        return new ResponseEntity<>(scan, null, HttpStatus.OK);
    }

    /**
     * This method is used to serve the JSON for all the scans in the database. It responds to GET requests to /scans/.
     *
     * @return      ResponseEntity object containing scans JSON.
     */
    @RequestMapping(value = "", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity getAllScans() {
        List<Scan> scans = scanService.getAll();
        return new ResponseEntity<>(scans,null, HttpStatus.OK);
    }

    /**
     * This method is used to accepted and store the decision the user has made regarding
     * the scan.
     *
     * @param goodBrain boolean whether the user indicated that the scan was "good" or
     * not
     * @param scanId ID of the scan that the decision was made for
     * @return json object with success attribute and error message if applicable
     */
    @RequestMapping(value = "save", method = RequestMethod.POST, produces = "application/json")
    public Boolean storeDecision(@RequestParam("scanId") Integer scanId,
                                 @RequestParam("goodBrain") Boolean goodBrain) {

        Account loggedInUser = accountService.findByEmail(loggedUserService.getUsername());
        UserResponse response = new UserResponse();
        response.setUserProfileId(loggedInUser.getId());
        response.setScanId(scanId);
        response.setResponse(goodBrain);
        responsesService.storeUserResponse(response);
        return true;
    }

    /**
     * This method is used to upload a new set of 3 images to the database.
     * @param image1 image 1 of the scan
     * @param image2 image 2 of the scan
     * @param image3 image 3 of the scan
     */
    @RequestMapping(value = "/new", method = RequestMethod.POST)
    public void uploadingPost(HttpServletResponse response, @RequestParam("image1") MultipartFile image1, @RequestParam("image2") MultipartFile image2, @RequestParam("image3") MultipartFile image3) throws IOException {

        File file1 = new File(imageUploadDirectory + image1.getOriginalFilename());
        image1.transferTo(file1);

        File file2 = new File(imageUploadDirectory + image2.getOriginalFilename());
        image2.transferTo(file2);

        File file3 = new File(imageUploadDirectory + image3.getOriginalFilename());
        image3.transferTo(file3);


        Scan scan = new Scan(1, file1.getName(), file2.getName(), file3.getName(), null);
        scanService.insert(scan);

        response.sendRedirect("/admin/");
    }

    /**
     * This method is used to set whether the scan is known to be good or not from the admin interface.
     * @param knownGood boolean containing whether the images is good or not
     */
    @RequestMapping(value = "/{id}/setKnownGood", method = RequestMethod.POST)
    public String updateKnownGood(@PathVariable("id") Long id, @RequestParam("knownGood") Boolean knownGood) {
        scanService.updateKnownGood(id, knownGood);
        return "OK";
    }

    @Configuration
    public class AdditionalResourceWebConfiguration implements WebMvcConfigurer {
        @Override
        public void addResourceHandlers(final ResourceHandlerRegistry registry) {
            registry.addResourceHandler("/brain_images/**").addResourceLocations("file:brain_images/");
        }
    }

    @GetMapping(value = "/getScansFiltered")
    public ResponseEntity getScansFiltered(
            @RequestHeader(value = "filter_min_responses") String filterMinResponses,
            @RequestHeader(value = "filter_percentage_good") String filterPercentageGood,
            @RequestHeader(value = "filter_age_range") String filterAgeRange,
            @RequestHeader(value = "filter_postcode") String filterPostcode,
            @RequestHeader(value = "filter_gender") String filterGender) {

        LOG.debug("Handling get request to /getScansFiltered with headers, " +
                "filter_min_responses: " + filterMinResponses +
                "filter_percentage_good: " + filterPercentageGood +
                "filter_age_range: " + filterAgeRange +
                "filter_postcode: " + filterPostcode +
                "filter_gender" + filterGender);

        List<Scan> scans = scanService.getAll();

        return new ResponseEntity<>(scans, null, HttpStatus.OK);
    }
}
