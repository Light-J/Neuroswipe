package com.nsa.cubric.application.controllers.API;
import org.springframework.web.bind.annotation.*;
@RequestMapping("images")
@RestController
public class Image {
    /**
     * This method is used to serve the JSON for the image to view. It responds to GET requests to /images/next.
     *
     * @return      List of Charity objects, returned as JSON
     */
    @RequestMapping(value = "next", method = RequestMethod.GET, produces = "application/json")
    public Image getNextImage() {
        return null;
    }

    /**
     * This method is used to accepted and store the decision the user has made regarding the image.
     *
     * @param good boolean whether the user indicated that the image was "good" or not
     * @param imageId ID of the image that the decision was made for
     * @return json object with success attribute and error message if applicable
     */
    @RequestMapping(value = "", method = RequestMethod.POST, produces = "application/json")
    public Boolean storeDecision(@RequestParam("good") String good, @RequestParam("imageId") String imageId) {
        return true;
    }
}
