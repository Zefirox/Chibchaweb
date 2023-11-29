package co.edu.unbosque.chibchawebbackend.repositories;

import co.edu.unbosque.chibchawebbackend.entities.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserEntity,Long> {

    /**
     * Note: It must contain findBy"<Field_Name>"
     * @param correo
     * @return
     */
    public Optional<UserEntity> findByCorreo(String correo);
}
