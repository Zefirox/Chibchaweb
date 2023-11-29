package co.edu.unbosque.chibchawebbackend.mappers;


import co.edu.unbosque.chibchawebbackend.dtos.*;
import co.edu.unbosque.chibchawebbackend.entities.*;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;


@Mapper(componentModel = "Spring")
public interface PaymentMapper {

    @Mapping(target = "id", source = "id")
    @Mapping(target = "idCliente", source = "idCliente")
    PaymentDTO toPaymentDto(PagoEntity pagoEntity);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "idCliente", ignore = true)
    PagoEntity registerPayment(PaymentDTO paymentDTO);

    @Mapping(target = "id", source = "id")
    @Mapping(target = "medioPago", source = "tipoMedioPago")
    @Mapping(target = "estado", source = "estado")
    MedioPagoEntity toPaymentTypeEntity(PaymentTypeDto paymentTypeDto);

    @Mapping(target = "id", source = "id")
    @Mapping(target = "tipoMedioPago", source = "medioPago")
    @Mapping(target = "estado", source = "estado")
    PaymentTypeDto toPaymentTypeDto (MedioPagoEntity medioPagoEntity);

    List<PaymentTypeDto> toPaymentTypeList (List<MedioPagoEntity> medioPagoEntities);

    List<PaymentDTO> toPayDtoList(List<PagoEntity> payEntityList);

    @Mapping(target = "id")
    @Mapping(target = "idCliente")
    @Mapping(target = "ultimosNumeroTarjeta")
    @Mapping(target = "predMetodo")
    @Mapping(target = "idMedioPago")
    @Mapping(target = "idMetodoPagoStripe")
    MetodoPagoClienteEntity toPayMethodClientEntity(PayMethodClientDto payMethodClientDto);

    @Mapping(target = "id", source = "id")
    @Mapping(target = "idCliente.id", source = "idCliente.id")
    @Mapping(target = "ultimosNumeroTarjeta", source = "ultimosNumeroTarjeta")
    @Mapping(target = "predMetodo", source = "predMetodo")
    @Mapping(target = "idMedioPago.id", source = "idMedioPago.id")
    @Mapping(target = "idMetodoPagoStripe", source = "idMetodoPagoStripe")
    PayMethodClientDto toPayMethodClientDTO (MetodoPagoClienteEntity metodoPagoClienteEntity);

    @Mapping(target = "idCliente", source = "idCliente")
    @Mapping(target = "ultimosNumeroTarjeta", source = "ultimosNumeroTarjeta")
    @Mapping(target = "predMetodo", source = "predMetodo")
    @Mapping(target = "idMedioPago", source = "idMedioPago")
    @Mapping(target = "idMetodoPagoStripe", source = "idMetodoPagoStripe")
    MetodoPagoClienteEntity toUpdatePayMethodClientEntity(PayMethodClientDto payMethodClientDto);

    List<PayMethodClientDto> toPayMethodClientDtoList(List<MetodoPagoClienteEntity> payMethodEntityList);


}
