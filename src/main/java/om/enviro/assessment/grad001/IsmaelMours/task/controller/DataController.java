package om.enviro.assessment.grad001.IsmaelMours.task.controller;

import lombok.RequiredArgsConstructor;
import om.enviro.assessment.grad001.IsmaelMours.task.exception.InvalidFileFormatException;
import om.enviro.assessment.grad001.IsmaelMours.task.model.ProcessedData;
import om.enviro.assessment.grad001.IsmaelMours.task.service.DataService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/file")
@RequiredArgsConstructor
public class DataController {

    private final DataService dataService;

    @PostMapping("/upload")
    public ResponseEntity<String> uploadFile(@RequestParam("file") MultipartFile file) {
        try {
            dataService.processFile(file);
            return ResponseEntity.status(HttpStatus.OK).body("File uploaded successfully.");
        } catch (InvalidFileFormatException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to upload file: " + e.getMessage());
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getDataById(@PathVariable Long id) {
        // Retrieve data by ID
        Optional<ProcessedData> optionalData = dataService.getDataById(id);

        // Check if data exists
        if (optionalData.isPresent()) {
            // Data found, return it
            return ResponseEntity.ok(optionalData.get());
        } else {
            // Data not found, return 404 Not Found status
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Data with ID " + id + " not found.");
        }
    }


    @GetMapping("/results")
    public ResponseEntity<List<ProcessedData>> getAllData() {
        List<ProcessedData> processedDataList = dataService.getAllData();
        return ResponseEntity.ok(processedDataList);
    }
}
