package plant.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import plant.demo.entity.PlantInfo;

public interface PlantDataRepository extends JpaRepository<PlantInfo, String> {
}
