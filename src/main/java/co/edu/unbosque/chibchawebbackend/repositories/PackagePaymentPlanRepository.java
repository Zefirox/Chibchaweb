package co.edu.unbosque.chibchawebbackend.repositories;

import co.edu.unbosque.chibchawebbackend.entities.PaquetePlanpagoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PackagePaymentPlanRepository extends JpaRepository<PaquetePlanpagoEntity, Long> {

    Optional<PaquetePlanpagoEntity> findById(Long id);

}
