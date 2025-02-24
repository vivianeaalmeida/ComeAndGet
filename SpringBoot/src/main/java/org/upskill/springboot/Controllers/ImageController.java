package org.upskill.springboot.Controllers;

import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.nio.file.Path;
import java.nio.file.Paths;

/**
 *  * REST Controller responsible for serving image files from the uploads directory.
 */
@RestController
@RequestMapping("/uploads")
public class ImageController {

    /**
     * Directory where uploaded images are stored.
     */
    private static final String IMAGE_UPLOAD_DIR = "uploads/images/";

    /**
     * Handles GET requests to retrieve an image file.
     *
     * @param filename Name of the file to be retrieved
     * @return ResponseEntity containing the image resource if found, otherwise 404 Not Found
     */
    @GetMapping("/{filename:.+}")
    public ResponseEntity<Resource> getFile(@PathVariable String filename) {
        try {
            Path filePath = Paths.get(IMAGE_UPLOAD_DIR).resolve(filename).normalize();
            Resource resource = new UrlResource(filePath.toUri());

            if (resource.exists() || resource.isReadable()) {
                return ResponseEntity.ok()
                        .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + resource.getFilename() + "\"")
                        .body(resource);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }
}
