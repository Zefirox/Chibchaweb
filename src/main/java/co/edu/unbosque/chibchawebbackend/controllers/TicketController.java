package co.edu.unbosque.chibchawebbackend.controllers;

import co.edu.unbosque.chibchawebbackend.dtos.TicketDTO;
import co.edu.unbosque.chibchawebbackend.repositories.TicketRepository;
import co.edu.unbosque.chibchawebbackend.services.TicketService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tickets")
@RequiredArgsConstructor
@Slf4j

public class TicketController {

    private final TicketService ticketService;


    @GetMapping("/")
    public ResponseEntity<List<TicketDTO>> getAllTickets() {
        List<TicketDTO> ticketDtos = ticketService.getAllTickets();
        return new ResponseEntity<>(ticketDtos, HttpStatus.OK);
    }

    @PostMapping("/create")
         ResponseEntity<String> createTicket (@RequestBody TicketDTO ticketDto) {
            ticketService.createTicket(ticketDto);
            return ResponseEntity.ok("Your ticket was created succesfully! ");
    }

    @GetMapping("/{tickedId}")
    public ResponseEntity<TicketDTO> getTicketById(@PathVariable Long tickedId){
        TicketDTO ticketDto = ticketService.getTicketById(tickedId);
        return new ResponseEntity<>(ticketDto, HttpStatus.OK);
    }

    @PutMapping("/{ticketId}")
    ResponseEntity<String> updateTicketStatus(@PathVariable String ticketId, @RequestBody TicketDTO ticketDto ){
        ticketService.updateTicket(Long.valueOf(ticketId), ticketDto);
        return ResponseEntity.ok("State succesfully update:  ");
    }

    @GetMapping("/user")
    public List<TicketDTO> getTicketsForCurrentUsers() {
        return ticketService.getTicketsForCurrentUsers();
    }
}
