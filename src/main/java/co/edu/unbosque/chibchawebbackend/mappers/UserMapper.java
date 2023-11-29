package co.edu.unbosque.chibchawebbackend.mappers;

import co.edu.unbosque.chibchawebbackend.dtos.*;
import co.edu.unbosque.chibchawebbackend.entities.*;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "Spring")
public interface UserMapper {

    @Mapping(source = "id", target = "id")
    RoleDto toRoleDto(RoleEntity roleEntity);

    List<RoleDto> toRoleDtoList(List<RoleEntity> roleEntities);

    @Mapping(target = "id",source = "id")
    IdentiTypeDto toIdentiTypeDto(TipoIdEntity tipoIdEntity);
    List<IdentiTypeDto> toIdentiTypeDtoList(List<TipoIdEntity> list);

    @Mapping(target = "id",source = "id")
    CategoryCDto toCategoryCDto(CategoriaClienteEntity categoriaCEntity);
    List<CategoryCDto> toCategoryCDtoList(List<CategoriaClienteEntity> list);


    @Mapping(target = "id", source = "id")
    @Mapping(target = "idRegion", source = "idRegion")
    @Mapping(target = "idRegion.idPais", source = "idRegion.idPais")
    UserDto toUserDto(UserEntity userEntity);

    @Mapping(target = "idRegion", ignore = true)
    @Mapping(target = "clave", ignore = true)
    @Mapping(target = "fechaCreacion", ignore = true)
    @Mapping(target = "estado", ignore = true)
    UserEntity signUpToUser(SignUpDto userDto);


    UserEntity toUserEntity(UserDto userDto);

    @Mapping(target = "idCategoria", ignore = true)
    @Mapping(target = "idTipo",  ignore = true)
    ClienteEntity signUpToClient(SignUpDto userDto);


    List<UserDto> toUserDtoList(List<UserEntity> userEntityList);

}
