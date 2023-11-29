package co.edu.unbosque.chibchawebbackend.services;

import co.edu.unbosque.chibchawebbackend.dtos.*;
import co.edu.unbosque.chibchawebbackend.entities.*;
import co.edu.unbosque.chibchawebbackend.exceptions.AppException;
import co.edu.unbosque.chibchawebbackend.mappers.ClientMapper;
import co.edu.unbosque.chibchawebbackend.mappers.RecaudoMapper;
import co.edu.unbosque.chibchawebbackend.mappers.UserMapper;
import co.edu.unbosque.chibchawebbackend.repositories.*;
import co.edu.unbosque.chibchawebbackend.services.tasks.ClientTask;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class ClientService {

    private final UserMapper userMapper;
    private final ClientRepository clientRepo;
    private final ClientMapper clientMapper;
    private final ClientTask clientTask;
    private final CategoryCRepository categoryCRepo;
    private final IdentyTypeRepository identyTypeRepo;
    private final PackagePaymentPlanRepository packagePaymentPlanRepo;
    private final PackageRepository packageRepo;
    private final MailService mailService;
    private final RecaudoRepository recaudoRepo;
    private final RecaudoMapper recaudoMapper;

    public ClientDto findByUser(Long user){
        ClienteEntity clienteEntity = clientRepo.findByIdUsuario_Id(user)
                .orElseThrow(() -> new AppException("Unknown client", HttpStatus.NOT_FOUND));
        if(clienteEntity == null)throw new UsernameNotFoundException("No client was found");
        return clientMapper.toClientDto(clienteEntity);
    }


    @Transactional
    public void registerClient(SignUpDto userDtoSign, UserEntity userEntity){

        ClienteEntity clienteEntity = userMapper.signUpToClient(userDtoSign);
        clienteEntity.setIdUsuario(userEntity);

        /*
         * Codigo quemado
         * */
        clienteEntity.setIdTipo(identyTypeRepo.findById(1L).get());
        clienteEntity.setIdCategoria(categoryCRepo.findById(1L).get());
        clienteEntity.setNumeroId("");
        clienteEntity.setRazonSocial("");
        clienteEntity.setCantidadDominios(0);

        try {
            clientRepo.save(clienteEntity);
            return;
        } catch (Exception e) {

            throw new AppException("Error while saving client", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public List<IdentiTypeDto> getIdentiTypes(){
        List<TipoIdEntity> typeList = identyTypeRepo.findAll();
        return userMapper.toIdentiTypeDtoList(typeList);
    }

    @Transactional
    public void unsuscribeClient(Long client){
        clientRepo.unsuscribeClient(client);
    }

    @Transactional
    public void unsuscribeDistributor(Long client){
        ClienteEntity clienteEntity = clientRepo.findById(client).get();
        CategoriaClienteEntity categoriaCliente = categoryCRepo.findById(1L).get();

        clienteEntity.setIdCategoria(categoriaCliente);

        clientRepo.save(clienteEntity);
    }

    public List<ClientDto> getClientsToExpireList(){
        List<ClientDto> clientDtoList =
                clientMapper.toClientDtoList(
                        clientRepo.findByEstadoContains("A")
                );

           List<ClientDto> clientDtoExpireList = clientDtoList
                .stream()
                .filter(domainDto -> clientTask.verifyTimeToExpire(domainDto))
                .collect(Collectors.toList());

        return clientDtoExpireList;
    }

    @Transactional
    public void activateRenovation(Long client) {clientRepo.activateRenovation(client);}

    @Transactional
    public void activatePackage(Long client, Long idPackage) {clientRepo.updatePackagePayment(client, packagePaymentPlanRepo.findById(idPackage).get());}

    @Transactional
    public boolean updateCLientPackage(Long clientId, Long newPackage){
        ClienteEntity clienteEntity = clientRepo.findById(clientId).get();
        PaquetePlanpagoEntity paquetePlanpagoEntity = packagePaymentPlanRepo.findById(newPackage).get();

        if (clienteEntity.getCantidadDominios()<=paquetePlanpagoEntity.getIdPaquete().getDominiosLimite()){
            clientRepo.updatePackage(clientId, newPackage);
            return true;
        }else return false;


    }

    public void deleteClient(Long client) {

        ClienteEntity clientEntity = clientRepo.findById(client).
                orElseThrow(() -> new AppException("Client not found", HttpStatus.NOT_FOUND));

        if (Objects.equals(clientEntity.getEstado(), "I")) {
            clientEntity.setEstado("D");
            clientEntity.getIdUsuario().setCorreo(null);
        }

        try {
            clientRepo.save(clientEntity);
            String[] messageDeleteAcc = {clientEntity.getIdUsuario().getCorreo(),clientEntity.getIdUsuario().getNombre()};
            mailService.sendMessage("Your ChibchaWeb account was delete. If you want join again, can send us mail message",messageDeleteAcc,"notificationAccountDelete.html");
        } catch (Exception e) {
            throw new AppException("Your account cannot be deleted", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public List<RecaudoDto> getRecaudosByClient(Long id){
        List<RecaudoEntity> recaudoEntities = recaudoRepo.findByIdCliente_Id(id);
        return recaudoMapper.toRecaudoDtoList(recaudoEntities);
    }

}
