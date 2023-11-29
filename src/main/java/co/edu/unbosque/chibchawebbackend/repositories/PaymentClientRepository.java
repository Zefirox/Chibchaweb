package co.edu.unbosque.chibchawebbackend.repositories;

import co.edu.unbosque.chibchawebbackend.entities.ClienteEntity;
import co.edu.unbosque.chibchawebbackend.entities.MetodoPagoClienteEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PaymentClientRepository extends JpaRepository<MetodoPagoClienteEntity, Long> {
    @Query("select m from MetodoPagoClienteEntity m where m.idCliente = ?1 and m.predMetodo = 'A'")
    Optional<MetodoPagoClienteEntity> getPredMethod(ClienteEntity id);
}
