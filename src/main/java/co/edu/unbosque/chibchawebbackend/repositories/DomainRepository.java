package co.edu.unbosque.chibchawebbackend.repositories;

import co.edu.unbosque.chibchawebbackend.dtos.DomainDto;
import co.edu.unbosque.chibchawebbackend.entities.DominioEntity;
import co.edu.unbosque.chibchawebbackend.entities.TicketEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository

public interface DomainRepository extends JpaRepository<DominioEntity,Long> {

    DominioEntity findByNombreAndEstado(String nombre, String estado);



    @Query("select d from DominioEntity d where d.id = ?1 and d.estado like concat('%', ?2, '%')")
    DominioEntity findByIdAndEstadoContains(Long id, String estado);


    List<DominioEntity> findAll();

    List<DominioEntity> findByEstadoContains(String estado);

    List<DominioEntity> findAllByIdCliente_Id(Long id);

    @Query("select d from DominioEntity d where d.idCliente.id = ?1")
    List<DominioEntity> findByIdCliente_Id(Long id);


}
