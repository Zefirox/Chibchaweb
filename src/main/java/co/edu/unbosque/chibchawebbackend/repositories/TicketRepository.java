package co.edu.unbosque.chibchawebbackend.repositories;

import co.edu.unbosque.chibchawebbackend.dtos.TicketDTO;
import co.edu.unbosque.chibchawebbackend.entities.TicketEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.lang.NonNull;

import java.util.List;

public interface TicketRepository extends JpaRepository <TicketEntity, Long> {


     List<TicketEntity> findAllByIdUsuario_Id(Long id);

     List<TicketEntity> findByIdUsuarioSoporte_Id(Long id);

}
