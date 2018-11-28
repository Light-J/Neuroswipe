package com.nsa.cubric.application.controllers.API;
import com.nsa.cubric.application.domain.Image;
import com.nsa.cubric.application.domain.PracticeImage;
import com.nsa.cubric.application.services.QuizServicesStatic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

@RequestMapping("images")
@RestController
public class ImageAPI {

    private UserResponseServiceStatic responsesService;

    @Autowired
    public ImageAPI(UserResponseServiceStatic aRepo) {
        responsesService = aRepo;
    }

    @Autowired
    QuizServicesStatic quizServices;

    private Random randomGenerator;

    /**
     * This method is used to serve the JSON for the image to view. It responds to GET
     * requests to /images/next.
     *
     * @return ResponseEntity object containing image JSON.
     */
    @RequestMapping(value = "next", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity getNextImage() {
        List<Image> images = Arrays.asList(new Image(1, "1.jpg"), new Image(2, "2.jpg"), new Image(3, "3.jpg"),
                new Image(4, "4.jpg"), new Image(5, "5.jpg"), new Image(6, "6.jpg"), new Image(7, "7.jpg"),
                new Image(8, "8.jpg"), new Image(9, "9.jpg"), new Image(10, "10.jpg"));

        randomGenerator = new Random();
        int index = randomGenerator.nextInt(images.size());

        return new ResponseEntity<>(images.get(index), null, HttpStatus.OK);
    }

    /**
     * This method is used to accepted and store the decision the user has made regarding
     * the image.
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
            @RequestParam(value = "question_number") int questionNumber){
        List<PracticeImage> images = quizServices.getQuizImages();

        return new ResponseEntity<>(images.get(questionNumber),null, HttpStatus.OK);


    }
}
