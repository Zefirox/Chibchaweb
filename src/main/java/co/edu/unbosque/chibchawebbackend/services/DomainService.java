package co.edu.unbosque.chibchawebbackend.services;

import co.edu.unbosque.chibchawebbackend.dtos.DomainDto;
import co.edu.unbosque.chibchawebbackend.dtos.TicketDTO;
import co.edu.unbosque.chibchawebbackend.entities.ClienteEntity;
import co.edu.unbosque.chibchawebbackend.entities.DominioEntity;
import co.edu.unbosque.chibchawebbackend.entities.PaquetePlanpagoEntity;
import co.edu.unbosque.chibchawebbackend.entities.TicketEntity;
import co.edu.unbosque.chibchawebbackend.exceptions.AppException;
import co.edu.unbosque.chibchawebbackend.mappers.ClientMapper;
import co.edu.unbosque.chibchawebbackend.mappers.DomainMapper;
import co.edu.unbosque.chibchawebbackend.repositories.ClientRepository;
import co.edu.unbosque.chibchawebbackend.repositories.DomainRepository;
import co.edu.unbosque.chibchawebbackend.repositories.PackagePaymentPlanRepository;
import co.edu.unbosque.chibchawebbackend.repositories.UserRepository;
import co.edu.unbosque.chibchawebbackend.services.tasks.ClientTask;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class DomainService {

    @Value("${ninja.api.url}")
    private String request_url_ninja;

    @Value("${ninja.api.key}")
    private String api_key_ninja;


    private final DomainRepository domainRepo;
    private final DomainMapper domainMapper;
    private final ClientTask clientTask;
    private final UserService userService;
    private final ClientRepository clientRepository;
    private final UserRepository userRepository;
    private final PackagePaymentPlanRepository packagePaymentPlanRepo;

    public ResponseEntity validateDomain(String domainNameIn){

        //Valida en que no se encuentre en la BD
        DominioEntity domainDto = domainRepo.findByNombreAndEstado(domainNameIn, "A");
        if(domainDto !=null) return ResponseEntity.status(HttpStatus.ALREADY_REPORTED).body("Ya se encuentra registrado");

        //Filtro de validación
        String domainName = validateExtension(domainNameIn);

        JsonNode domainRoot = getDomainRegistration(domainName);

        if(domainRoot.size() > 1) return ResponseEntity.status(HttpStatus.ALREADY_REPORTED).body("Ya se encuentra registrado");

        System.out.println(domainRoot);

        return ResponseEntity.status(HttpStatus.OK).body("No se encuentra registrado");

    }

    public String validateExtension(String domainName){

        //Valida que solo sea valido la extensión ".com"
        if (!domainName.matches(".*\\.com$"))
            throw new AppException("Solo se permiten Dominios que terminen por .com", HttpStatus.FORBIDDEN);

        String[] nameParts = domainName.split("\\.");
        if(nameParts.length >3 && nameParts.length<1){
            throw new AppException("La extensión no esta permitida", HttpStatus.FORBIDDEN);
        }else if (nameParts.length ==3){
            if(!nameParts[0].toLowerCase().equals("www"))
                throw new AppException("La extensión no esta permitida", HttpStatus.FORBIDDEN);
            return nameParts[1];
        }else{
            return nameParts[0]+".com";
        }
    }


    public JsonNode getDomainRegistration(String nombreDominio) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_XML);
        headers.set("accept", "application/xml");
        headers.set("X-Api-Key", api_key_ninja);

        String xmlSolicitud = "<domainLookupRequest><domainName>"+nombreDominio+"</domainName></domainLookupRequest>";
        HttpEntity<String> httpEntity = new HttpEntity<>(xmlSolicitud, headers);

        ResponseEntity<String> response = new RestTemplate().exchange(request_url_ninja + nombreDominio, HttpMethod.GET, httpEntity, String.class);

        try {
            ObjectMapper mapper = new ObjectMapper();
            JsonNode root = mapper.readTree(response.getBody());
            return root;
        } catch (Exception e) {
           throw new AppException("Error searching domain",HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @Transactional
    public DomainDto updateDomain(DomainDto updateDomainDto) {

        DominioEntity dominioEntity = domainRepo.findById(updateDomainDto.getId()).orElseThrow(() ->
                new AppException("There's no Domain", HttpStatus.NOT_FOUND)
        );
        if(updateDomainDto.getNombre().equals(dominioEntity.getNombre()))
            throw  new AppException("El nombre es el mismo", HttpStatus.CONFLICT);

        //Valida el nombre del dominio
        validateDomain(updateDomainDto.getNombre());
        dominioEntity.setNombre(updateDomainDto.getNombre());
        domainRepo.save(dominioEntity);

        return domainMapper.toDomainDto(dominioEntity);
    }

    public List<DomainDto> getDomainsDtoList(){
        return domainMapper.toDomainDtoList(domainRepo.findAll());
    }

    public List<DomainDto> getDomainsForCurrentUser() {
        Long currentUser = userService.findByEmail(userService.getCurrentUser().getCorreo()).getId();
         ClienteEntity currentClient = clientRepository.findByIdUsuario_Id(currentUser).
                orElseThrow(()->new AppException("Client not found", HttpStatus.NOT_FOUND));
        List<DominioEntity> domains = domainRepo.findAllByIdCliente_Id(currentClient.getId());
        return domainMapper.toDomainDtoList(domains);
    }

    @Transactional
    public boolean saveDomain(String domain, Long userId){
        ClienteEntity clienteEntity = clientRepository.findByIdUsuario_Id(userId).get();

        if (clienteEntity.getCambioProducto()==null){
            if (clienteEntity.getIdPaquetePlanpago().getIdPaquete().getDominiosLimite()>clienteEntity.getCantidadDominios()){
                DominioEntity dominioEntity = new DominioEntity();

                dominioEntity.setNombre(domain);
                dominioEntity.setIdCliente(clienteEntity);
                dominioEntity.setEstado("A");

                domainRepo.save(dominioEntity);

                return true;
            }else {
                return false;
            }
        }else {

            PaquetePlanpagoEntity paquetePlanpagoEntity = packagePaymentPlanRepo.findById(Long.valueOf(clienteEntity.getCambioProducto())).get();

            if (paquetePlanpagoEntity.getIdPaquete().getDominiosLimite()>clienteEntity.getCantidadDominios()){
                DominioEntity dominioEntity = new DominioEntity();

                dominioEntity.setNombre(domain);
                dominioEntity.setIdCliente(clienteEntity);
                dominioEntity.setEstado("A");

                domainRepo.save(dominioEntity);

                return true;
            }else {
                return false;
            }
        }
    }

    public boolean deleteDomain(Long idDomain){
        DominioEntity domainEntity = domainRepo.findById(idDomain).orElse(null);

        if (domainEntity!=null){
            domainEntity.setEstado("I");

            domainRepo.save(domainEntity);

            return true;
        }else return false;
    }

}
