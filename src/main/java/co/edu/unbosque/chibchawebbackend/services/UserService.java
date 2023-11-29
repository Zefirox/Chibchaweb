package co.edu.unbosque.chibchawebbackend.services;

import co.edu.unbosque.chibchawebbackend.dtos.*;
import co.edu.unbosque.chibchawebbackend.entities.*;
import co.edu.unbosque.chibchawebbackend.exceptions.AppException;
import co.edu.unbosque.chibchawebbackend.mappers.UserMapper;
import co.edu.unbosque.chibchawebbackend.repositories.*;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.nio.CharBuffer;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class UserService {
    private final UserRepository userRepo;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepo;
    private final RegionRepository regionRepo;
    private final ClientService clientService;


    @Transactional
    public UserDto findByEmail(String correo) {
        UserEntity userEntity = userRepo.findByCorreo(correo)
                .orElseThrow(() -> new AppException("Unknown user", HttpStatus.NOT_FOUND));
        if(userEntity == null) throw new UsernameNotFoundException("No user was found");
        return  userMapper.toUserDto(userEntity);
    }

    public UserDto login(CredentialsDto credentialsDto){
        UserEntity userEntity = userRepo.findByCorreo(credentialsDto.getCorreo())
                .orElseThrow(() -> new AppException("Unknown user", HttpStatus.NOT_FOUND));

        if(passwordEncoder.matches(CharBuffer.wrap(credentialsDto.getClave()), userEntity.getClave())){

            return userMapper.toUserDto(userEntity);
        }
        throw new AppException("Invalid password", HttpStatus.BAD_REQUEST);
    }

    @Transactional
    public UserDto registerUser(SignUpDto userDto){
        Optional<UserEntity> optionalUser = userRepo.findByCorreo(userDto.getCorreo());

        if (optionalUser.isPresent()){
            throw new AppException("The email wrote already exist", HttpStatus.BAD_REQUEST);
        }

        UserEntity userEntity = userMapper.signUpToUser(userDto);

        userEntity.setClave(passwordEncoder.encode(CharBuffer.wrap(userDto.getClave())));

        /*
         *"Codigo Quemado por efectos de ejemplo"
         */
        userEntity.setIdRol(roleRepo.findById(6L).get());
        userEntity.setIdRegion(regionRepo.findById(1L).get());
        try {
            UserEntity savedUserEntity = userRepo.save(userEntity);


            if(userEntity.getIdRol().getNombre().contains("DISTRIBUIDOR") ||
                    userEntity.getIdRol().getNombre().contains("CLIENTE"))
                clientService.registerClient(userDto, savedUserEntity);

            return userMapper.toUserDto(savedUserEntity);
        } catch (Exception e) {
            throw new AppException("Error while saving user", HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }
    public List<UserDto> getUsersDtoList(){
        return userMapper.toUserDtoList(userRepo.findAll());
    }
    public List<RoleDto> getRoles(){
        List<RoleEntity> roles = roleRepo.findAll();
        return userMapper.toRoleDtoList(roles);
    }

    public UserDto getCurrentUser(){
        return (UserDto) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }

    @Transactional
    public UserDto updateInfoUser(Long idUser, UserDto updateUserDto) {

        UserEntity userEntity = userRepo.findById(idUser).orElseThrow(() -> {
            throw new AppException("User not found", HttpStatus.NOT_FOUND);
        });

        try{

            String rolName = getCurrentUser().getIdRol().getNombre();

            userEntity.setTelefono(updateUserDto.getTelefono());
            userEntity.setDireccion(updateUserDto.getDireccion());
            userEntity.setCodPostal(updateUserDto.getCodPostal());
            userEntity.setNombre(updateUserDto.getNombre());
            userEntity.setApellido(updateUserDto.getApellido());

            if(rolName.contains("ADMIN")) {
                userEntity.setCorreo(updateUserDto.getCorreo());
                userEntity.setIdRol(roleRepo.findById(updateUserDto.getIdRol().getId()).
                        orElseThrow( ()-> new AppException("Setting Role Not Found",HttpStatus.NOT_FOUND)));
            }

            userRepo.save(userEntity);
        } catch (DataIntegrityViolationException e){
            throw new AppException("Error updating User Foreign Key", HttpStatus.BAD_REQUEST);
        } catch (Exception e){
            throw new AppException("Error updating User", HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return userMapper.toUserDto(userEntity);

    }

    @Transactional
    public UserDto updateCredentialsUser(Long idUser, CredentialsDto credentialsDto) {

        UserEntity userEntity = userRepo.findById(idUser).orElseThrow(
            () -> new AppException("It doesnt exist the user", HttpStatus.BAD_REQUEST)
        );

        //Se compara los dos correos para validar que no exista
        if(!credentialsDto.getCorreo().equals(userEntity.getCorreo())){
            if(userRepo.findByCorreo(credentialsDto.getCorreo()).isPresent())
                throw new AppException("The email wrote already exist", HttpStatus.BAD_REQUEST);
            //Si no existe correo alguno setea el user
            userEntity.setCorreo(credentialsDto.getCorreo());
        }

        if(credentialsDto.getClave() != null){
            String password =passwordEncoder.encode(CharBuffer.wrap(credentialsDto.getClave()));
            if(!password.equals(userEntity.getClave()))
                userEntity.setClave(password);
        }


        try {
            userRepo.save(userEntity);
        }catch (Exception e){
            throw new  AppException("User cannot be updated",HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return userMapper.toUserDto(userEntity);
    }

}
