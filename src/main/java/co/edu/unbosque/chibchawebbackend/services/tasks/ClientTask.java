package co.edu.unbosque.chibchawebbackend.services.tasks;

import co.edu.unbosque.chibchawebbackend.dtos.ClientDto;
import co.edu.unbosque.chibchawebbackend.entities.CategoriaClienteEntity;
import co.edu.unbosque.chibchawebbackend.entities.ClienteEntity;
import co.edu.unbosque.chibchawebbackend.entities.PaquetePlanpagoEntity;
import co.edu.unbosque.chibchawebbackend.mappers.ClientMapper;
import co.edu.unbosque.chibchawebbackend.mappers.DomainMapper;
import co.edu.unbosque.chibchawebbackend.repositories.CategoryCRepository;
import co.edu.unbosque.chibchawebbackend.repositories.ClientRepository;
import co.edu.unbosque.chibchawebbackend.repositories.DomainRepository;
import co.edu.unbosque.chibchawebbackend.repositories.PackagePaymentPlanRepository;
import co.edu.unbosque.chibchawebbackend.services.MailService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

@AllArgsConstructor
@EnableScheduling
@Service
public class ClientTask {

    private final ClientMapper clientMapper;
    private final ClientRepository clientRepo;
    private final MailService mailService;
    private final DomainRepository domainRepo;
    private final DomainMapper domainMapper;
    private final PackagePaymentPlanRepository packagePaymentPlanRepo;
    private final CategoryCRepository categoryCRepo;

    //@Scheduled(fixedRate = 10000)
    public HttpStatus tareaProgramada() {
        // Lógica de la tarea programada
        System.out.println("Ejecutando tarea programada cada 10 segundos...");
        return HttpStatus.OK;
    }

    @Transactional
    @Scheduled(cron = "0 0 0 * * ?") // Ejecutar todos los días a medianoche
    public void manageInfoDomainExpire() {

        List<ClientDto> clientDtoList =
                clientMapper.toClientDtoList(
                        clientRepo.findByEstadoContains("A")
                );

        for(ClientDto client: clientDtoList){
            if(verifyTimeToExpire(client))
                sendMailNotification(client);

            if(client.getCambioProducto()!=null && Objects.equals(client.getVigencia(), LocalDate.now())){
                if (client.getIdCategoria().getId()==1L){
                    changePackageCliente(client);
                }else {
                    changePackageDis(client);
                }
            }

        }
    }

    public void sendMailNotification(ClientDto clientDto){
        String email = clientDto.getIdUsuario().getCorreo();
        String name = clientDto.getIdUsuario().getNombre();
        String[] info = {email,name};
        //Crear archivo de notificación
        mailService.sendMessage("Your product will expire soon",info,"notificationReg.html");
    }
    public boolean verifyTimeToExpire(ClientDto clientDto){
        LocalDate expirate = clientDto.getVigencia();
        LocalDate nowTime = LocalDate.now();
        if(nowTime.isEqual(expirate.minusDays(15)))  return true;
        return false;
    }

    public void changePackageCliente(ClientDto clientDto){
        ClienteEntity clienteEntity = clientMapper.toClientEntity(clientDto);

        PaquetePlanpagoEntity paquetePlanpagoEntity = packagePaymentPlanRepo.findById(Long.valueOf(clienteEntity.getCambioProducto())).get();

        clienteEntity.setIdPaquetePlanpago(paquetePlanpagoEntity);
        clienteEntity.setCambioProducto(null);

        clientRepo.save(clienteEntity);
    }

    public void changePackageDis(ClientDto clientDto){
        ClienteEntity clienteEntity = clientMapper.toClientEntity(clientDto);

        CategoriaClienteEntity categoriaCliente = categoryCRepo.findById(Long.valueOf(clienteEntity.getCambioProducto())).get();

        clienteEntity.setIdCategoria(categoriaCliente);
        clienteEntity.setCambioProducto(null);

        clientRepo.save(clienteEntity);
    }
}
