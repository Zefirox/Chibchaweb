package co.edu.unbosque.chibchawebbackend.controllers;

import co.edu.unbosque.chibchawebbackend.dtos.ClientDto;
import co.edu.unbosque.chibchawebbackend.services.*;
import com.stripe.model.Refund;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RequiredArgsConstructor
@RequestMapping("/api/service")
@RestController
public class ServiceProductController {

    private final UserService userService;
    private final ClientService clientService;
    private final DomainService domainService;
    private final PaymentService paymentService;
    private final StripeService stripeService;
    private final MailService mailService;

    @PostMapping("/unsubscribe")
    public ResponseEntity<String> unsubscribe(){
        ClientDto clientDto = clientService.findByUser(userService.getCurrentUser().getId());
        Long remaining = clientDto.getVigencia().toEpochDay()-LocalDate.now().toEpochDay();

        float amount = 0;
        if (remaining>=15){
            if(clientDto.getIdCategoria().getId()==1){
                amount = remaining*(paymentService.dailyPayClient(clientService.findByUser(userService.getCurrentUser().getId()).getIdPaquetePlanpago().getId(),clientService.findByUser(userService.getCurrentUser().getId()).getIdPaquetePlanpago().getIdPlanPago().getId()));

                String transaction = paymentService.getTransaction(clientDto.getId());
                Refund refund = stripeService.refund(transaction, amount);
                System.out.println(refund.toJson());

                String[] info = {userService.getCurrentUser().getCorreo(), userService.getCurrentUser().getNombre(), userService.getCurrentUser().getApellido()};
                mailService.sendMessage("Se ha desuscrito de su Membresia, Valor Prorrateado: "+amount, info, "notificationReg.html");

                clientService.unsuscribeClient(clientDto.getId());
                return ResponseEntity.ok("Client Unsubscribed Successfully");
            }else {
                amount = remaining*paymentService.dailyPayDis(clientDto.getIdCategoria().getId());

                String transaction = paymentService.getTransaction(clientDto.getId());
                Refund refund = stripeService.refund(transaction, amount);
                System.out.println(refund.toJson());

                String[] info = {userService.getCurrentUser().getCorreo(), userService.getCurrentUser().getNombre(), userService.getCurrentUser().getApellido()};
                mailService.sendMessage("Se ha desuscrito de su Membresia, Valor Prorrateado: "+amount, info, "notificationReg.html");

                clientService.unsuscribeDistributor(clientDto.getId());
                return ResponseEntity.ok("Distributor Unsubscribed Successfully");
            }
        }else
            if(clientDto.getIdCategoria().getId()==1){

            String[] info = {userService.getCurrentUser().getCorreo(), userService.getCurrentUser().getNombre(), userService.getCurrentUser().getApellido()};
            mailService.sendMessage("Se ha desuscrito de su Membresia", info, "notificationReg.html");

            clientService.unsuscribeClient(clientDto.getId());
            return ResponseEntity.ok("Client Unsubscribed Successfully");

            }else {

            String[] info = {userService.getCurrentUser().getCorreo(), userService.getCurrentUser().getNombre(), userService.getCurrentUser().getApellido()};
            mailService.sendMessage("Se ha desuscrito de su Membresia"+amount, info, "notificationReg.html");

            clientService.unsuscribeDistributor(clientDto.getId());
            return ResponseEntity.ok("Distributor Unsubscribed Successfully");
        }
    }

    @PostMapping("/domain/register")
    public ResponseEntity registerDomain(@RequestBody String domain){

        ResponseEntity validateDomain = domainService.validateDomain(domain);

        Long userId = userService.getCurrentUser().getId();

        boolean save = false;
        if (validateDomain.equals(ResponseEntity.status(HttpStatus.OK))){
            save = domainService.saveDomain(domain, userId);
        }

        if(save) return ResponseEntity.ok("Domain registered sucessfully");
        else return ResponseEntity.badRequest().body("Check the info provided and try again");
    }

    @DeleteMapping("/domain/{idDomain}")
    public ResponseEntity<String> deleteDomain(@PathVariable Long idDomain){
        boolean delete = domainService.deleteDomain(idDomain);

        if (delete) return ResponseEntity.ok("Domain Deleted Successfully");
        else return ResponseEntity.notFound().build();
    }
}
