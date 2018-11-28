package com.nsa.cubric.application.controllers.API;
import com.nsa.cubric.application.domain.Image;
import com.nsa.cubric.application.repositories.ImageRepository;
import com.nsa.cubric.application.services.ImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.io.File;

@RequestMapping("images")
@RestController
public class ImageAPI {

    private Random randomGenerator;
    private ImageService imageService;

    public static final String imageUploadDirectory = System.getProperty("user.dir") + "/brain_images/";

    @Autowired
    public ImageAPI(ImageService imageService){
        this.imageService = imageService;
    }

    /**
     * This method is used to serve the JSON for the image to view. It responds to GET requests to /images/next.
     *
     * @return      ResponseEntity object containing image JSON.
     */
    @RequestMapping(value = "next", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity getNextImage() {
        List<Image> images = Arrays.asList(new Image(1, "1.jpg"),
                new Image(2, "2.jpg"),
                new Image(3, "3.jpg"),
                new Image(4, "4.jpg"),
                new Image(5, "5.jpg"),
                new Image(6, "6.jpg"),
                new Image(7, "7.jpg"),
                new Image(8, "8.jpg"),
                new Image(9, "9.jpg"),
                new Image(10, "10.jpg"));

        randomGenerator = new Random();
        int index = randomGenerator.nextInt(images.size());

        return new ResponseEntity<>(images.get(index),null, HttpStatus.OK);
    }

    /**
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
     *
     * @param goodBrain boolean whether the user indicated that the image was "good" or not
     * @param imageId ID of the image that the decision was made for
     * @return json object with success attribute and error message if applicable
     */
    @RequestMapping(value = "save", method = RequestMethod.POST, produces = "application/json")
    public Boolean storeDecision(@RequestParam("goodBrain") String goodBrain, @RequestParam("imageId") String imageId) {
        return true;
    }

    @RequestMapping(value = "/new", method = RequestMethod.POST)
    public void uploadingPost(HttpServletResponse response, @RequestParam("images") MultipartFile[] uploadedImages) throws IOException {
        for(MultipartFile uploadedImage : uploadedImages) {
            File file = new File(imageUploadDirectory + uploadedImage.getOriginalFilename());
            uploadedImage.transferTo(file);
            Image image = new Image(1, file.getName());
            imageService.insert(image);
        }
        response.sendRedirect("/admin/");
    }

    @Configuration
    public class AdditionalResourceWebConfiguration implements WebMvcConfigurer {
        @Override
        public void addResourceHandlers(final ResourceHandlerRegistry registry) {
            registry.addResourceHandler("/brain_images/**").addResourceLocations("file:brain_images/");
        }
    }
}
