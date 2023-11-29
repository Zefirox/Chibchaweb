package co.edu.unbosque.chibchawebbackend.repositories;

import co.edu.unbosque.chibchawebbackend.entities.PaqueteEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PackageRepository extends JpaRepository<PaqueteEntity, Long> {

    @Modifying
    @Query("update PaqueteEntity p set p=?1 where p.id=?2")
    void updatePackage(PaqueteEntity newPackage, Long id);

    @Modifying
    @Query("update PaqueteEntity p set p.estado='I' where p.id=?1")
    void deactivatePackage(Long id);

    List<PaqueteEntity> findAllByEstado(String estado);
}
