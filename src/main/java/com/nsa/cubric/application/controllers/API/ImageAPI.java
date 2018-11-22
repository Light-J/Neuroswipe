package com.nsa.cubric.application.controllers.API;
import com.nsa.cubric.application.domain.Image;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
@RequestMapping("images")
@RestController
public class ImageAPI {
    /**
     * This method is used to serve the JSON for the image to view. It responds to GET requests to /images/next.
     *
     * @return      ResponseEntity object containing image JSON.
     */
    @RequestMapping(value = "next", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity getNextImage() {
        Image image = new Image(1, "1.jpg");
        return new ResponseEntity<>(image,null, HttpStatus.OK);
    }

    /**
     * This method is used to accepted and store the decision the user has made regarding the image.
     *
     * @param good boolean whether the user indicated that the image was "good" or not
     * @param imageId ID of the image that the decision was made for
     * @return json object with success attribute and error message if applicable
     */
    @RequestMapping(value = "save", method = RequestMethod.POST, produces = "application/json")
    public Boolean storeDecision(@RequestParam("goodBrain") String goodBrain, @RequestParam("imageId") String imageId) {
        return true;
    }
}
