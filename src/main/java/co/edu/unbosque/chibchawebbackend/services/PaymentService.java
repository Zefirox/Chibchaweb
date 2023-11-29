package co.edu.unbosque.chibchawebbackend.services;


import co.edu.unbosque.chibchawebbackend.dtos.RecaudoDto;
import co.edu.unbosque.chibchawebbackend.entities.RecaudoEntity;

import co.edu.unbosque.chibchawebbackend.dtos.*;
import co.edu.unbosque.chibchawebbackend.entities.*;

import co.edu.unbosque.chibchawebbackend.exceptions.AppException;
import co.edu.unbosque.chibchawebbackend.mappers.PaymentMapper;
import co.edu.unbosque.chibchawebbackend.mappers.RecaudoMapper;
import co.edu.unbosque.chibchawebbackend.repositories.*;
import jakarta.validation.ValidationException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.lang.invoke.CallSite;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class PaymentService {

    private final PaymentPlanRepository paymentPlanRepo;
    private final PackagePaymentPlanRepository packagePaymentPlanRepo;
    private final PaymentRepository paymentRepo;
    private final PaymentMapper paymentMapper;
    private final ClientRepository clientRepo;
    private final PaymentMethodRepository paymentMethodRepo;
    private final RecaudoMapper recaudoMapper;
    private final RecaudoRepository recaudoRepo;
    private final CategoryCRepository categoryCRepo;
    private final PayMethodClientRepository payMethodClientRepository;
    private final UserService userService;


    public String getTransaction(Long client){
        RecaudoDto recaudoDto = recaudoMapper.recaudoEntityToRecaudoDto(recaudoRepo.findByIdCliente_Id(client).get(0));
        return recaudoDto.getIdTransferencia();
    }

    @Transactional
    public void registerPayment(Long client, double amount, String transaction){
        RecaudoDto recaudoDto = new RecaudoDto();

        recaudoDto.setValor((int)(amount));
        recaudoDto.setIdTransferencia(transaction);
        recaudoDto.setFecha(LocalDate.now());

        RecaudoEntity payment = recaudoMapper.recaudoDtoToRecaudoEntity(recaudoDto);

        payment.setIdCliente(clientRepo.findById(client).get());

        try {
            RecaudoEntity savedPayment = recaudoRepo.save(payment);

            System.out.println(recaudoMapper.recaudoEntityToRecaudoDto(savedPayment).toString());
        }catch (Exception e){
            throw new AppException("Error while proceding with payment", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public float dailyPayClient(Long packId , Long planId){
        return packagePaymentPlanRepo.findById(packId).get().getPrecio()/(paymentPlanRepo.findById(planId).get().getMeses()*30);
    }

    public float dailyPayDis(Long categoryId){
        return categoryCRepo.findById(categoryId).get().getPrecio()/30;
    }

    @Transactional
    public void createPaymentType(PaymentTypeDto paymentTypeDto) {

        MedioPagoEntity medioPagoEntity = paymentMapper.toPaymentTypeEntity(paymentTypeDto);

        try {
            paymentMethodRepo.save(medioPagoEntity);
        } catch (Exception e) {
            e.printStackTrace();
            throw new AppException("Error while saving payment type", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public List<PaymentTypeDto> getAllPaymentTypes() {
        List<MedioPagoEntity> paymentTypes = paymentMethodRepo.findAll();
        return paymentMapper.toPaymentTypeList(paymentTypes);
    }

    public List<PayMethodClientDto> getPayMethodsForClient(){
        Long currentUser = userService.findByEmail(userService.getCurrentUser().getCorreo()).getId();
        ClienteEntity currentClient = clientRepo.findByIdUsuario_Id(currentUser).
                orElseThrow(()->new AppException("Client not found", HttpStatus.NOT_FOUND));

        List<MetodoPagoClienteEntity> payMethodClientList = payMethodClientRepository.findByIdCliente_Id(currentClient.getId());

        return paymentMapper.toPayMethodClientDtoList(payMethodClientList);
    }

    @Transactional

    public PaymentTypeDto updatePaymentType(Long paymentTypeId, PaymentTypeDto updateDto) {
        MedioPagoEntity existingMedioPagoEntity = paymentMethodRepo.findById(paymentTypeId)
                .orElseThrow(() -> new AppException("Payment type not found by ID: " + paymentTypeId, HttpStatus.INTERNAL_SERVER_ERROR));

        PaymentTypeDto paymentTypeDto = paymentMapper.toPaymentTypeDto(existingMedioPagoEntity);

        paymentTypeDto.setId(paymentTypeDto.getId());
        paymentTypeDto.setEstado(updateDto.getEstado());
        paymentTypeDto.setTipoMedioPago(updateDto.getTipoMedioPago());

        existingMedioPagoEntity = paymentMapper.toPaymentTypeEntity(paymentTypeDto);

        try {

            paymentMethodRepo.save(existingMedioPagoEntity);
            return paymentMapper.toPaymentTypeDto(existingMedioPagoEntity);

        } catch (Exception e) {
            e.printStackTrace();
            throw new AppException("Error updating ticket", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public List<PaymentDTO> getCommissionForDistributor() {
        Long currentUser = userService.findByEmail(userService.getCurrentUser().getCorreo()).getId();
        ClienteEntity currentClient = clientRepo.findByIdUsuario_Id(currentUser).
                orElseThrow(()->new AppException("Client not found", HttpStatus.NOT_FOUND));
        List<PagoEntity> paymentForDistributor = paymentRepo.findByIdCliente_Id(currentClient.getId());

        return paymentMapper.toPayDtoList(paymentForDistributor);
    }
}
