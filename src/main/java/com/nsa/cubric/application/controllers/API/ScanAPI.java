package com.nsa.cubric.application.controllers.API;
import com.nsa.cubric.application.domain.*;
import com.nsa.cubric.application.services.AccountServiceStatic;
import org.springframework.beans.factory.annotation.Autowired;
import com.nsa.cubric.application.services.ScanService;
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

import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.io.File;

@RequestMapping("scans")
@RestController
public class ScanAPI {

    private UserResponseServiceStatic responsesService;

    private Random randomGenerator;

    @Autowired
    private ScanService scanService;

    @Autowired
    AccountServiceStatic accountService;

    public static final String imageUploadDirectory = System.getProperty("user.dir") + "/brain_images/";

    @Autowired
    public ScanAPI(ScanService scanService, UserResponseServiceStatic aRepo){
        this.scanService = scanService;
        this.responsesService = aRepo;
    }

    /**
     * This method is used to serve the JSON for the image to view. It responds to GET
     * requests to /images/next.
     *
     * @return ResponseEntity object containing image JSON.
     */
    @RequestMapping(value = "next", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity getNextImage() {

        //TODO needs to get these from the database
        //I have left this as this now needs to be changed to 3 scans so logic will need to change
        List<Scan> scans = Arrays.asList(new Scan(1, "1.jpg", "1.jpg", "1.jpg", null),
                new Scan(2, "2.jpg", "2.jpg", "2.jpg", null),
                new Scan(3, "3.jpg", "3.jpg", "3.jpg", null),
                new Scan(4, "4.jpg", "4.jpg", "4.jpg", null),
                new Scan(5, "5.jpg", "5.jpg", "5.jpg", null),
                new Scan(6, "6.jpg", "6.jpg", "6.jpg", null),
                new Scan(7, "7.jpg", "7.jpg", "7.jpg", null),
                new Scan(8, "8.jpg", "8.jpg", "8.jpg", null),
                new Scan(9, "9.jpg", "9.jpg", "9.jpg", null),
                new Scan(10,  "10.jpg", "10.jpg", "10.jpg", null));

        randomGenerator = new Random();
        int index = randomGenerator.nextInt(scans.size());


        return new ResponseEntity<>(scans.get(index), null, HttpStatus.OK);
    }

    /**
     * This method is used to serve the JSON for all the images in the database. It responds to GET requests to /images/.
     *
     * @return      ResponseEntity object containing images JSON.
     */
    @RequestMapping(value = "", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity getAllImages() {
        List<Scan> scans = scanService.getAll();
        return new ResponseEntity<>(scans,null, HttpStatus.OK);
    }

    /**
     * This method is used to accepted and store the decision the user has made regarding
     * the image.
     *
     * @param goodBrain boolean whether the user indicated that the image was "good" or
     * not
     * @param imageId ID of the image that the decision was made for
     * @return json object with success attribute and error message if applicable
     */
    @RequestMapping(value = "save", method = RequestMethod.POST, produces = "application/json")
    public Boolean storeDecision(@RequestParam("imageId") Integer imageId,
                                 @RequestParam("goodBrain") Boolean goodBrain) {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Account loggedInUser = accountService.findByEmail(auth.getName());
        UserResponse responses = new UserResponse();
        responses.setUserProfileId(loggedInUser.getId());
        responses.setImageId(imageId);
        responses.setResponse(goodBrain);
        responsesService.storeUserResponses(responses);
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
}
