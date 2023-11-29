package co.edu.unbosque.chibchawebbackend.controllers;

import co.edu.unbosque.chibchawebbackend.dtos.ClientDto;
import co.edu.unbosque.chibchawebbackend.dtos.RecaudoDto;
import co.edu.unbosque.chibchawebbackend.entities.ClienteEntity;
import co.edu.unbosque.chibchawebbackend.exceptions.AppException;
import co.edu.unbosque.chibchawebbackend.services.ClientService;
import co.edu.unbosque.chibchawebbackend.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users/clients")
public class ClientController {

    private final ClientService clientService;
    private final UserService userService;

    @GetMapping("/toexpire")
    public ResponseEntity<List<ClientDto>> getClientsToExpire(){
        return ResponseEntity.ok(clientService.getClientsToExpireList());
    }

    @PostMapping("/update/{newPackage}")
    public ResponseEntity<String> updatePackage(@PathVariable Long newPackage){
        Long client = clientService.findByUser(userService.getCurrentUser().getId()).getId();

        boolean change = clientService.updateCLientPackage(client, newPackage);

        if (change){
            return ResponseEntity.ok("Paquete Cambiado con Exito");
        }else return new ResponseEntity<>("Dominios exceden nuevo paquete", HttpStatus.BAD_REQUEST);
    }

     @DeleteMapping("/client/{id}")
    public ResponseEntity<String> deleteClient(@PathVariable String id){
         clientService.deleteClient(Long.valueOf(id));
         return ResponseEntity.ok("Account succesfully delete:  ");
     }

     @GetMapping("/payments/{idCliente}")
    public ResponseEntity<List<RecaudoDto>> getPaymentList(@PathVariable Long idCliente){
        return ResponseEntity.ok(clientService.getRecaudosByClient(idCliente));
     }

}
