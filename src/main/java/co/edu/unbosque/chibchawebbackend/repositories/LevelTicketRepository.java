package co.edu.unbosque.chibchawebbackend.repositories;

import co.edu.unbosque.chibchawebbackend.dtos.LevelTicketDto;
import co.edu.unbosque.chibchawebbackend.entities.NivelTicketEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LevelTicketRepository extends JpaRepository <NivelTicketEntity, Long> {

}
