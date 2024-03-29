package com.glenfordham.photocompositor;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1")
public class PhotoController {

	private static final Logger logger = LogManager.getLogger();

	@PostMapping(value = "/saveimage")
	public ResponseEntity<String> saveImage(@RequestBody byte[] image) {
		try {
			Files.write(Paths.get(PhotoCompositorApplication.getPhotoPath() + "//" + LocalDateTime.now().toString().replace(":", "-") + "_" + UUID.randomUUID()  + ".png"), image);
			logger.info("Successful upload");
		} catch (Exception e) {
			logger.error("Error with upload attempt", e);
			return ResponseEntity.badRequest().body("Error occurred saving image to disk");
		}
		return ResponseEntity.ok().build();
	}
}
