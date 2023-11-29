package co.edu.unbosque.chibchawebbackend.repositories;

import co.edu.unbosque.chibchawebbackend.entities.ClienteEntity;
import co.edu.unbosque.chibchawebbackend.entities.PaquetePlanpagoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ClientRepository extends JpaRepository<ClienteEntity,Long> {

    List<ClienteEntity> findByEstadoContains(String estado);

    Optional<ClienteEntity> findByIdUsuario_Id(Long id);

    @Modifying
    @Query("update ClienteEntity c set c.idPaquetePlanpago=null, c.estado='I' where c.id=?1")
    void unsuscribeClient(Long id);

    @Modifying
    @Query("update ClienteEntity c set c.renovacionAutomatica=true where c.id=?1")
    void activateRenovation(Long id);

    @Modifying
    @Query("update ClienteEntity c set c.idPaquetePlanpago=?2 where c.id=?1")
    void updatePackagePayment(Long idCliente, PaquetePlanpagoEntity idPackage);

    @Modifying
    @Query("update ClienteEntity c set c.cambioProducto=?2 where c.id=?1")
    void updatePackage(Long idCliente, Long idPackage);
}
