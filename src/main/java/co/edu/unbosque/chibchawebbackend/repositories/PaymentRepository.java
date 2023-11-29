package co.edu.unbosque.chibchawebbackend.repositories;

import co.edu.unbosque.chibchawebbackend.dtos.PaymentDTO;
import co.edu.unbosque.chibchawebbackend.entities.PagoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PaymentRepository extends JpaRepository<PagoEntity, Long> {

    List<PagoEntity> findByIdCliente_Id(Long id);
}
