package co.edu.unbosque.chibchawebbackend.services.tasks;

import co.edu.unbosque.chibchawebbackend.dtos.ClientDto;
import co.edu.unbosque.chibchawebbackend.dtos.MetodoPagoClienteDto;
import co.edu.unbosque.chibchawebbackend.entities.MetodoPagoClienteEntity;
import co.edu.unbosque.chibchawebbackend.mappers.ClientMapper;
import co.edu.unbosque.chibchawebbackend.mappers.MetodoPagoClienteMapper;
import co.edu.unbosque.chibchawebbackend.repositories.ClientRepository;
import co.edu.unbosque.chibchawebbackend.repositories.PaymentClientRepository;
import co.edu.unbosque.chibchawebbackend.services.MailService;
import co.edu.unbosque.chibchawebbackend.services.StripeService;
import com.stripe.model.PaymentIntent;
import lombok.AllArgsConstructor;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@AllArgsConstructor
@EnableScheduling
@Service
public class PaymentTask {

    private ClientMapper clientMapper;
    private ClientRepository clientRepo;
    private PaymentClientRepository paymentClientRepo;
    private MetodoPagoClienteMapper metodoPagoClienteMapper;
    private StripeService stripeService;
    private MailService mailService;

    @Transactional
    @Scheduled(cron = "0 0 0 * * ?") // Ejecutar todos los días a medianoche
    public void autoPay(){
        List<ClientDto> clientDtoList =
                clientMapper.toClientDtoList(
                        clientRepo.findByEstadoContains("A")
                );

        for(ClientDto client: clientDtoList){
            if(verifyAutoPay(client))
                Pay(client);
        }
    }

    public boolean verifyAutoPay(ClientDto client){
        return client.isRenovacionAutomatica() && (client.getVigencia().toEpochDay()-LocalDate.now().toEpochDay())==0;
    }

    public void Pay(ClientDto client){
        MetodoPagoClienteEntity metodoPagoClienteEntity = paymentClientRepo.getPredMethod(clientMapper.toClientEntity(client)).get();
        MetodoPagoClienteDto metodoPagoClienteDto = metodoPagoClienteMapper.metodoPagoClienteEntityToMetodoPagoClienteDto(metodoPagoClienteEntity);

        PaymentIntent paymentIntent = stripeService.automaticPaymentMethod(metodoPagoClienteDto.getIdMetodoPagoStripe(), client.getIdPaquetePlanpago().getPrecio());

        if (paymentIntent!=null){
            String[] info = {client.getIdUsuario().getCorreo(),"",""};
            mailService.sendMessage("Su pago automatico se ha realizado", info, "notificationReg.html");
        }
    }
}
