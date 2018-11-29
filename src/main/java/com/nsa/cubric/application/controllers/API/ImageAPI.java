package com.nsa.cubric.application.controllers.API;
import com.nsa.cubric.application.domain.Image;
import com.nsa.cubric.application.domain.PracticeImage;
import com.nsa.cubric.application.services.QuizServicesStatic;
import org.springframework.beans.factory.annotation.Autowired;
import com.nsa.cubric.application.services.ImageService;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.nsa.cubric.application.domain.Image;
import com.nsa.cubric.application.domain.UserResponse;
import com.nsa.cubric.application.services.UserResponseServiceStatic;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.io.File;

@RequestMapping("images")
@RestController
public class ImageAPI {

    private UserResponseServiceStatic responsesService;

    @Autowired
    QuizServicesStatic quizServices;

    private Random randomGenerator;
    private ImageService imageService;

    public static final String imageUploadDirectory = System.getProperty("user.dir") + "/brain_images/";

    @Autowired
    public ImageAPI(ImageService imageService, UserResponseServiceStatic aRepo){
        this.imageService = imageService;
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

        List<Image> images = Arrays.asList(new Image(1, "1.jpg", null),
                new Image(2, "2.jpg", null),
                new Image(3, "3.jpg", null),
                new Image(4, "4.jpg", null),
                new Image(5, "5.jpg", null),
                new Image(6, "6.jpg", null),
                new Image(7, "7.jpg", null),
                new Image(8, "8.jpg", null),
                new Image(9, "9.jpg", null),
                new Image(10, "10.jpg", null));

        randomGenerator = new Random();
        int index = randomGenerator.nextInt(images.size());

        return new ResponseEntity<>(images.get(index), null, HttpStatus.OK);
    }

    /**
<<<<<<< HEAD
     * This method is used to serve the JSON for all the images in the database. It responds to GET requests to /images/.
     *
     * @return      ResponseEntity object containing images JSON.
     */
    @RequestMapping(value = "", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity getAllImages() {
        List<Image> images = imageService.getAll();
        return new ResponseEntity<>(images,null, HttpStatus.OK);
    }

    /**
     * This method is used to accepted and store the decision the user has made regarding the image.
=======
     * This method is used to accepted and store the decision the user has made regarding
     * the image.
>>>>>>> dd065617ecfd55798b0c9fa783dc7ee6db6eeb20
     *
     * @param userProfileId ID of the user profile to which user made decision
     * @param goodBrain boolean whether the user indicated that the image was "good" or
     * not
     * @param imageId ID of the image that the decision was made for
     * @return json object with success attribute and error message if applicable
     */
    @RequestMapping(value = "save", method = RequestMethod.POST, produces = "application/json")
    public Boolean storeDecision(@RequestParam("userProfileId") Integer userProfileId,
                                 @RequestParam("imageId") Integer imageId, @RequestParam("goodBrain") Boolean goodBrain) {

        UserResponse responses = new UserResponse();
        responses.setUserProfileId(userProfileId);
        responses.setImageId(imageId);
        responses.setResponse(goodBrain);
        responsesService.storeUserResponses(responses);
        return true;
    }

    /**
     * This method is used in the quiz section in order to obtain images related to question numbers
     * @param questionNumber integer of the question being requested
     * @return ResponseEntity object containing image JSON.
     */
    @GetMapping(value = "/quiz", produces = "application/json")
    public ResponseEntity getQuizImages(
            @RequestParam(value = "question_number") int questionNumber) {
        List<PracticeImage> images = quizServices.getQuizImages();

        return new ResponseEntity<>(images.get(questionNumber), null, HttpStatus.OK);

    }

    @RequestMapping(value = "/new", method = RequestMethod.POST)
    public void uploadingPost(HttpServletResponse response, @RequestParam("images") MultipartFile[] uploadedImages) throws IOException {
        for(MultipartFile uploadedImage : uploadedImages) {
            File file = new File(imageUploadDirectory + uploadedImage.getOriginalFilename());
            uploadedImage.transferTo(file);
            Image image = new Image(1, file.getName(), null);
            imageService.insert(image);
        }
        response.sendRedirect("/admin/");
    }

    @RequestMapping(value = "/{id}/setKnownGood", method = RequestMethod.POST)
    public String updateKnownGood(@PathVariable("id") Long id, @RequestParam("knownGood") Boolean knownGood) {
        imageService.updateKnownGood(id, knownGood);
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
