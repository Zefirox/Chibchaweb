package co.edu.unbosque.chibchawebbackend.repositories;

import co.edu.unbosque.chibchawebbackend.entities.PaisEntity;
import co.edu.unbosque.chibchawebbackend.entities.RegionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RegionRepository extends JpaRepository<RegionEntity,Long> {
    public List<RegionEntity> findByIdPais(PaisEntity idPais);
}
