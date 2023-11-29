package co.edu.unbosque.chibchawebbackend.controllers;


import co.edu.unbosque.chibchawebbackend.dtos.*;
import co.edu.unbosque.chibchawebbackend.repositories.PayMethodClientRepository;
import co.edu.unbosque.chibchawebbackend.services.*;
import co.edu.unbosque.chibchawebbackend.services.PaymentService;
import co.edu.unbosque.chibchawebbackend.services.StripeService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.stripe.model.Charge;
import com.stripe.model.PaymentMethod;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api/payments")
public class PaymentController {

    private final StripeService stripeService;
    private final UserService userService;
    private final ClientService clientService;
    private final MailService mailService;
    private final PaymentService paymentService;

    @PostMapping("/charge")
    public ResponseEntity<String> chargeCard(@RequestHeader(value="token") String token,
                                             @RequestBody Double amount,
                                             @RequestBody boolean renovacion,
                                             @RequestBody Long packageId){

        Charge charge = stripeService.chargeNewCard(token, amount);

        if(charge!=null){
            Long id = userService.getCurrentUser().getId();
            Long cliente = clientService.findByUser(id).getId();

            paymentService.registerPayment(cliente, charge.getAmount(), charge.getId());

            clientService.activatePackage(cliente, packageId);

            double comission = stripeService.comission(charge.getBillingDetails().getName(), charge.getAmount());
            if(comission!=0){
                String[] info = {charge.getBillingDetails().getName(), userService.findByEmail(charge.getBillingDetails().getName()).getNombre()+" "+userService.findByEmail(charge.getBillingDetails().getName()).getApellido()};
                mailService.sendMessage("Comision de su Pago: "+comission, info, "notificationReg.html");
            }else{
                String[] info = {charge.getBillingDetails().getName(), userService.findByEmail(charge.getBillingDetails().getName()).getNombre()+" "+userService.findByEmail(charge.getBillingDetails().getName()).getApellido()};
                mailService.sendMessage("Su pago se ha realizado, Valor del pago: "+charge.getAmount(), info, "notificationReg.html");
            }

            if (renovacion){
                clientService.activateRenovation(cliente);
            }
            return ResponseEntity.ok(charge.toJson());
        }else return ResponseEntity.internalServerError().body("Payment Failed");
    }

    @PostMapping("/savecard")
    public ResponseEntity<String> saveCard(@RequestHeader(value="token") String token, @RequestBody String tarjetaPredeterminada) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();

        JsonNode nameNode = mapper.readTree(tarjetaPredeterminada);
        String res = nameNode.get("tarjetaPredeterminada").asText();

        PaymentMethod paymentMethod = stripeService.saveNewCard(token, res);
        return ResponseEntity.ok(paymentMethod.toJson());
    }

    @DeleteMapping("/payment/card/{methodId}")
    ResponseEntity<String> deleteCard(@PathVariable String methodId){
        try {
            stripeService.deleteCard(Long.valueOf(methodId));
            return ResponseEntity.ok("Your pay method was succesfully delete");
        }catch (Exception e){
            return new ResponseEntity<>("Your card can't be delete: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @PostMapping("/paymentType")
    ResponseEntity<String> createPaymentType (@RequestBody PaymentTypeDto paymentTypeDto) {
        paymentService.createPaymentType(paymentTypeDto);
        return ResponseEntity.ok("Your payment type was created succesfully! ");
    }

    @GetMapping("/")
    public ResponseEntity<List<PaymentTypeDto>> getAllPaymentTypes(){
        List<PaymentTypeDto> paymentTypeDtos = paymentService.getAllPaymentTypes();
        return new ResponseEntity<>(paymentTypeDtos, HttpStatus.OK);
    }


    @GetMapping("/commission")
    ResponseEntity<List<PaymentDTO>> getCommissionForDistribuitor(){
        List<PaymentDTO> commissionList = paymentService.getCommissionForDistributor();
        return new ResponseEntity<>(commissionList, HttpStatus.OK);
    }

    @GetMapping("/client")
    public ResponseEntity<List<PayMethodClientDto>> getPaymentMethodsClientList() {
            List<PayMethodClientDto> paymentMethods = paymentService.getPayMethodsForClient();
            return ResponseEntity.ok(paymentMethods);
    }
}
