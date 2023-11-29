package co.edu.unbosque.chibchawebbackend.repositories;

import co.edu.unbosque.chibchawebbackend.entities.MedioPagoEntity;
import co.edu.unbosque.chibchawebbackend.entities.MetodoPagoClienteEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PaymentMethodRepository extends JpaRepository<MedioPagoEntity, Long> {
    MedioPagoEntity findByMedioPago(String medioPago);

}
