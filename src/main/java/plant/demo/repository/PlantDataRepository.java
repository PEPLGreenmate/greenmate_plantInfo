package plant.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import plant.demo.service.PlantData;

public interface PlantDataRepository extends JpaRepository<PlantData, String> {
}
