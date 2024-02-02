package com.project.shopapp.controllers;

import com.project.shopapp.dtos.ProductDTO;
import jakarta.validation.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("${api.prefix}/products")
public class ProductController {

    @PostMapping(value = "", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> createProduct(@Valid @ModelAttribute ProductDTO productDTO,
                                           BindingResult result) {
        try {
//                                           @RequestPart("file") MultipartFile file,
            if(result.hasErrors()) {
                List<String> errorMessages = result.getFieldErrors()
                        .stream()
                        .map(FieldError::getDefaultMessage)
                        .toList();
                return ResponseEntity.badRequest().body(errorMessages);
            }
            System.out.println(productDTO);
            List<MultipartFile> files = productDTO.getFiles();
            files = files == null ? new ArrayList<MultipartFile>() : files;

            for (MultipartFile file : files) {
                // to check size and format
                if(file.getSize() > 10 * 1024 * 1024) {
                    if(file.getSize() == 0) {
                        continue;
                    }
                    return ResponseEntity.status(HttpStatus.PAYLOAD_TOO_LARGE)
                            .body("File is too large! Maximun size is 10MB");
                }
                // get file format
                String contentType = file.getContentType();
                if(contentType == null || !contentType.startsWith("image/")) {
                    return ResponseEntity.status(HttpStatus.UNSUPPORTED_MEDIA_TYPE)
                            .body("File must be an image");
                }
                // save file and update thumbnail in DTO;
                String filename = storeFile(file); // replace function with your code to save file
                // save into product object un db -> will be done later
                // save into product_images table
            }
            return ResponseEntity.ok("Product created successfully");
        }catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    private String storeFile(MultipartFile file)  throws IOException {
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());

        // add UUID before file name to ensure file name is unique
        String uniqueFilename = UUID.randomUUID().toString() + "_" + fileName;

        // Path to the folder that you want to save
        Path uploadDir = Paths.get("uploads");

        // to check and create folder if it's not exist
        if(!Files.exists(uploadDir)) {
            Files.createDirectories(uploadDir);
        }
        // full path to file
        Path destination = Paths.get(uploadDir.toString(), uniqueFilename);
        // copy file into dest folder
        Files.copy(file.getInputStream(), destination, StandardCopyOption.REPLACE_EXISTING);

        return uniqueFilename;

    }

    @GetMapping("")
    public ResponseEntity<String> getProducts (@RequestParam("page") int page,
                                                    @RequestParam("limit") int limit) {
        return ResponseEntity.ok("getProducts");
    }

    @GetMapping("/{id}")
    public ResponseEntity<String> getProductByID (@PathVariable(name = "id") String productId) {
        return ResponseEntity.ok("Product with id = " + productId);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteProduct(@PathVariable long id) {
//        return ResponseEntity.status(HttpStatus.OK).body("Product deleted successfully");
        return ResponseEntity.ok("Product with id = " + id);
    }
}
