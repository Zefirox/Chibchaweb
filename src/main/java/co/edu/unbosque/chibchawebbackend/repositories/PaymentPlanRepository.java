package co.edu.unbosque.chibchawebbackend.repositories;

import co.edu.unbosque.chibchawebbackend.entities.PlanPagoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PaymentPlanRepository extends JpaRepository<PlanPagoEntity,Long> {
    List<PlanPagoEntity> findByEstadoContains(String estado);

    Optional<PlanPagoEntity> findById(Long id);

}
