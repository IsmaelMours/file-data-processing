package om.enviro.assessment.grad001.IsmaelMours.task.repository;

import om.enviro.assessment.grad001.IsmaelMours.task.model.ProcessedData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;

@Repository
public interface ProcessedDataRepository extends JpaRepository<ProcessedData, Long> {
    boolean existsByDateAndLocation(LocalDate date, String location);
}
