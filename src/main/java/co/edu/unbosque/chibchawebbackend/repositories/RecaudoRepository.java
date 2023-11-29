package co.edu.unbosque.chibchawebbackend.repositories;

import co.edu.unbosque.chibchawebbackend.dtos.RecaudoDto;
import co.edu.unbosque.chibchawebbackend.entities.RecaudoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RecaudoRepository extends JpaRepository<RecaudoEntity, Long> {
    List<RecaudoEntity> findByIdCliente_Id(Long id);
}
