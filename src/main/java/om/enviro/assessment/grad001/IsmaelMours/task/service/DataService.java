package om.enviro.assessment.grad001.IsmaelMours.task.service;

import lombok.RequiredArgsConstructor;
import om.enviro.assessment.grad001.IsmaelMours.task.exception.DuplicateDataException;
import om.enviro.assessment.grad001.IsmaelMours.task.exception.InvalidFileFormatException;
import om.enviro.assessment.grad001.IsmaelMours.task.model.ProcessedData;
import om.enviro.assessment.grad001.IsmaelMours.task.repository.ProcessedDataRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class DataService {


    private final ProcessedDataRepository processedDataRepository;

    public void processFile(MultipartFile file) throws IOException {
        // Validate file format
        if (!Objects.requireNonNull(file.getOriginalFilename()).endsWith(".txt")) {
            throw new InvalidFileFormatException("Only .txt files are allowed.");
        }

        // Define the date format
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        // Read data from the file
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(file.getInputStream()))) {
            String line;
            boolean isFirstLine = true;
            while ((line = reader.readLine()) != null) {
                if (isFirstLine) {
                    isFirstLine = false;
                    continue; // Skip the first line
                }
                String[] dataParts = line.split(",");
                if (dataParts.length == 5) {
                    // Parse the data
                    LocalDate date = LocalDate.parse(dataParts[0], dateFormatter);
                    String location = dataParts[1];
                    Double temperature = Double.parseDouble(dataParts[2]);
                    Integer humidity = Integer.parseInt(dataParts[3]);
                    Integer airQualityIndex = Integer.parseInt(dataParts[4]);

                    // Check if data already exists
                    if (!processedDataRepository.existsByDateAndLocation(date, location)) {
                        // If data doesn't exist, save it
                        ProcessedData processedData = new ProcessedData();
                        processedData.setDate(date);
                        processedData.setLocation(location);
                        processedData.setTemperature(temperature);
                        processedData.setHumidity(humidity);
                        processedData.setAirQualityIndex(airQualityIndex);
                        processedDataRepository.save(processedData);
                    } else {
                        // If data already exists, throw an exception
                        throw new DuplicateDataException("Data for date " + date + " and location " + location + " already exists.");
                    }
                } else {
                    throw new InvalidFileFormatException("Invalid data format in the file.");
                }
            }
        }
    }

    public Optional<ProcessedData> getDataById(Long id) {
        // Retrieve data by ID from the database
        return processedDataRepository.findById(id);
    }

    public List<ProcessedData> getAllData() {
        // Return all processed data from the database
        return processedDataRepository.findAll();
    }
}