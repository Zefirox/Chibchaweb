package co.edu.unbosque.chibchawebbackend.repositories;

import co.edu.unbosque.chibchawebbackend.entities.CategoriaTicketEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryTicketRepository extends JpaRepository <CategoriaTicketEntity,Long> {

}
