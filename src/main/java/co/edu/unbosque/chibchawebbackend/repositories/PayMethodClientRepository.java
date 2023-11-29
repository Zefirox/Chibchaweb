package co.edu.unbosque.chibchawebbackend.repositories;

import co.edu.unbosque.chibchawebbackend.entities.MetodoPagoClienteEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PayMethodClientRepository extends JpaRepository<MetodoPagoClienteEntity, Long> {
    List<MetodoPagoClienteEntity> findByIdCliente_Id(Long id);

}
