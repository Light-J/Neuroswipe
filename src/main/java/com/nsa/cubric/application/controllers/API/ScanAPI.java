package com.nsa.cubric.application.controllers.API;
import com.nsa.cubric.application.domain.*;
import com.nsa.cubric.application.services.*;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import com.nsa.cubric.application.services.ScanService;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import javax.servlet.http.HttpServletResponse;
import java.io.*;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.nsa.cubric.application.domain.Scan;

import java.util.*;
import java.util.zip.ZipOutputStream;

@RequestMapping("scans")
@RestController
public class ScanAPI {

    private ScanServiceStatic scanService;
    private AccountServiceStatic accountService;

    public static final String imageUploadDirectory = System.getProperty("user.dir") + "/brain_images/";

    @Autowired
    public ScanAPI(ScanService scanService, AccountServiceStatic accountService){
        this.scanService = scanService;
        this.accountService = accountService;
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

    @RequestMapping(value = "next/practice", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity getNextPracticeScan() {
        Optional<Scan> scan = scanService.getNextPractice();
        return new ResponseEntity<>(scan, null, HttpStatus.OK);
    }

    /**
     * This method is used to serve the JSON for all the scans in the database. It responds to GET requests to /scans/.
     *
     * @return      ResponseEntity object containing scans JSON.
     */
    @RequestMapping(value = "", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity getAllScans(
            @RequestParam(value = "page") int page) {

        List<Scan> scans = scanService.getAll(page);
        return new ResponseEntity<>(scans,null, HttpStatus.OK);
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
    public String updateKnownGood(@PathVariable("id") Long id,
                                  @RequestParam("knownGood") Boolean knownGood) {
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
            @RequestParam(value = "filter_min_responses") Integer filterMinResponses,
            @RequestParam(value = "filter_percentage_good") Integer filterPercentageGood,
            @RequestParam(value = "page") int page) {

        List<Scan> scans = scanService.getScansFilteredPaginated(filterMinResponses, filterPercentageGood, page);

        return new ResponseEntity<>(scans, null, HttpStatus.OK);
    }

    @RequestMapping(value = "/downloadScansFiltered", produces = "application/zip")
    public byte[] downloadScansFiltered(
            HttpServletResponse response,
            @RequestParam(value = "filter_min_responses") Integer filterMinResponses,
            @RequestParam(value = "filter_percentage_good") Integer filterPercentageGood) throws IOException {


        response.setContentType("application/octet-stream");
        response.setHeader("Content-Disposition", "attachment;filename=download.zip");
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(byteArrayOutputStream);
        ZipOutputStream zipOutputStream = new ZipOutputStream(bufferedOutputStream);

        List<Scan> allScans = scanService.getScansFiltered(filterMinResponses, filterPercentageGood);

        ArrayList<File> top_files = new ArrayList<>();
        ArrayList<File> side_files = new ArrayList<>();
        ArrayList<File> front_files = new ArrayList<>();

        for (Scan scan:allScans) {
            front_files.add(new File("brain_images\\" + scan.getPath1()));
            side_files.add(new File("brain_images\\" + scan.getPath2()));
            top_files.add(new File("brain_images\\" + scan.getPath3()));
        }
        //packing files
        for (int i = 0; i<front_files.size(); i++) {
            FileHelper.addFileToOutputStream(zipOutputStream, front_files.get(i), front_files.get(i).getName().replace(".jpg", "")+"_front.jpg");
            FileHelper.addFileToOutputStream(zipOutputStream, top_files.get(i), top_files.get(i).getName().replace(".jpg", "")+"_top.jpg");
            FileHelper.addFileToOutputStream(zipOutputStream, side_files.get(i), side_files.get(i).getName().replace(".jpg", "")+"_side.jpg");
            }

        if (zipOutputStream != null) {
            zipOutputStream.finish();
            zipOutputStream.flush();
            IOUtils.closeQuietly(zipOutputStream);
        }
        IOUtils.closeQuietly(bufferedOutputStream);
        IOUtils.closeQuietly(byteArrayOutputStream);
        return byteArrayOutputStream.toByteArray();

    }

}
