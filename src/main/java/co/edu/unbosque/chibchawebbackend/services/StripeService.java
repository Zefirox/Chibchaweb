package co.edu.unbosque.chibchawebbackend.services;

import co.edu.unbosque.chibchawebbackend.dtos.ClientDto;
import co.edu.unbosque.chibchawebbackend.dtos.UserDto;
import co.edu.unbosque.chibchawebbackend.entities.ClienteEntity;
import co.edu.unbosque.chibchawebbackend.entities.MetodoPagoClienteEntity;
import co.edu.unbosque.chibchawebbackend.exceptions.AppException;
import co.edu.unbosque.chibchawebbackend.repositories.ClientRepository;
import co.edu.unbosque.chibchawebbackend.repositories.PayMethodClientRepository;
import co.edu.unbosque.chibchawebbackend.repositories.PaymentMethodRepository;
import co.edu.unbosque.chibchawebbackend.repositories.PaymentRepository;
import com.stripe.Stripe;
import com.stripe.exception.CardException;
import com.stripe.exception.StripeException;
import com.stripe.model.*;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.util.*;

@RequiredArgsConstructor
@Component
public class StripeService {
    @Autowired
    StripeService(@Value("${stripe.api.key}") String apiKey) {
        Stripe.apiKey = apiKey;
    }

    @Autowired
    private UserService userService;
    @Autowired
    private ClientService clientService;
    @Autowired
    private ClientRepository clientRepository;
    @Autowired
    private PaymentRepository paymentRepository;
    @Autowired
    private PaymentMethodRepository paymentMethodRepository;
    @Autowired
    private PayMethodClientRepository payMethodClientRepository;

    public Charge chargeNewCard(String token, double amount) {
        try {
            Map<String, Object> chargeParams = new HashMap<>();
            chargeParams.put("amount", (int)(amount*100));
            chargeParams.put("currency", "USD");
            chargeParams.put("source", token);
            Charge charge = Charge.create(chargeParams);
            return charge;
        } catch (Exception e) {
            throw new AppException("Error Stripe", HttpStatus.BAD_GATEWAY);
        }
    }


    /**
     * Queda en espera el pago- revisar
     * @param paymentMethod_id
     * @param amount
     * @return
     */
    public PaymentIntent automaticPaymentMethod(String paymentMethod_id, double amount){

        try {
            Map<String, Object> automaticPaymentMethods = new HashMap<>();
            automaticPaymentMethods.put("enabled", true);
            automaticPaymentMethods.put("allow_redirects", "never");

            Map<String, Object> params = new HashMap<>();
            params.put("amount", (long)(amount * 100));
            params.put("currency", "usd");
            params.put("automatic_payment_methods",automaticPaymentMethods);
            params.put("payment_method", paymentMethod_id);

            //Confirmación del pago de la tarjeta
            PaymentIntent paymentIntent = PaymentIntent.create(params);

            Map<String, Object> paymentParams = new HashMap<>();
            paymentParams.put("payment_method", paymentMethod_id);

            PaymentIntent updatedPaymentIntent = paymentIntent.confirm(paymentParams);

            return updatedPaymentIntent;

        } catch (StripeException e) {
            throw new AppException("Payment attempt error",HttpStatus.BAD_GATEWAY);
        }

    }

    /**
     * Falta guardar en la BD
     * @param token
     * @return
     */
    public PaymentMethod saveNewCard(String token, String tarjetaPredeterminada){

        try {
            // Utiliza el token en lugar de detalles de la tarjeta
            Map<String, Object> cardParams = new HashMap<>();
            cardParams.put("token", token);

            Map<String, Object> params = new HashMap<>();
            params.put("type", "card");
            params.put("card", cardParams);

            PaymentMethod paymentMethod = PaymentMethod.create(params);

            validateCardList(paymentMethod);

            //Falta que se guarde en la BD
            saveCardDataBase(paymentMethod, tarjetaPredeterminada );

            return paymentMethod;
        } catch (CardException e) {
            throw new AppException("Error processing credit card. Please ensure the information is correct and try again.",HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (StripeException e) {
            throw new AppException("Error processing credit card. Please try again later or contact customer support.",HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    public void saveCardDataBase(PaymentMethod paymentMethod, String tarjetaPredeterminada){

        MetodoPagoClienteEntity metodoPagoClienteEntity = new MetodoPagoClienteEntity();

        Long currentUser = userService.findByEmail(userService.getCurrentUser().getCorreo()).getId();
        ClienteEntity currentClient = clientRepository.findByIdUsuario_Id(currentUser).
                orElseThrow(()->new AppException("Client not found", HttpStatus.NOT_FOUND));

        metodoPagoClienteEntity.setIdCliente(currentClient);
        metodoPagoClienteEntity.setPredMetodo(tarjetaPredeterminada);
        metodoPagoClienteEntity.setIdMetodoPagoStripe(paymentMethod.getId());
        metodoPagoClienteEntity.setIdMedioPago(paymentMethodRepository.findByMedioPago(paymentMethod.getCard().getBrand()));
        metodoPagoClienteEntity.setUltimosNumeroTarjeta(paymentMethod.getCard().getLast4());

        payMethodClientRepository.save(metodoPagoClienteEntity);
    }

    public void deleteCard(Long idMetodo) {
        try{
            Optional<MetodoPagoClienteEntity> optionalPayMethodClient = payMethodClientRepository.findById(idMetodo);

            if (optionalPayMethodClient.isPresent()){
                payMethodClientRepository.deleteById(idMetodo);
            }else{
                throw new AppException("Payment method not found with id: " + idMetodo, HttpStatus.NOT_FOUND);
            }
        }catch (Exception e){
            throw new AppException("Error deleting payment method. Please try again later or contact customer support.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Se tiene que validar las tarjetas no-staticamente
     * @param paymentMethod
     */
    public void validateCardList(PaymentMethod paymentMethod){

        //Tarjetas validas
        List<String> validCardList = Arrays.asList("diners", "visa", "mastercard");

        if(!validCardList.contains(paymentMethod.getCard().getBrand().toLowerCase())){
            throw new AppException("Only Cards of type "+validCardList.toString() , HttpStatus.BAD_REQUEST);
        }
    }


    //Metodos que no se usan, Pero estaban en la documentación
    public Customer createCustomer(String token, String email) throws Exception {
        Map<String, Object> customerParams = new HashMap<String, Object>();
        customerParams.put("email", email);
        customerParams.put("source", token);
        return Customer.create(customerParams);
    }
    private Customer getCustomer(String id) throws Exception {
        return Customer.retrieve(id);
    }

    public Charge chargeCustomerCard(String customerId, int amount) throws Exception {
        String sourceCard = getCustomer(customerId).getDefaultSource();
        Map<String, Object> chargeParams = new HashMap<String, Object>();
        chargeParams.put("amount", amount);
        chargeParams.put("currency", "USD");
        chargeParams.put("customer", customerId);
        chargeParams.put("source", sourceCard);
        Charge charge = Charge.create(chargeParams);
        return charge;
    }

    public double comission(String correo, float price){
        UserDto user = userService.findByEmail(correo);
        ClientDto client = clientService.findByUser(user.getId());
        double comission = 0;

        comission = (price*client.getIdCategoria().getComision())/100;

        return comission;
    }

    public Refund refund(String chargeId, float amount){

        try {
            Map<String, Object> refundParams = new HashMap<>();
            refundParams.put("charge", chargeId);
            refundParams.put("amount",(int) (amount*100));


            return Refund.create(refundParams);
        } catch (Exception e) {
            throw new AppException("Error Stripe", HttpStatus.BAD_GATEWAY);
        }
    }

}
