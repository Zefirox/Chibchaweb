package co.edu.unbosque.chibchawebbackend.services;


import co.edu.unbosque.chibchawebbackend.dtos.LevelTicketDto;
import co.edu.unbosque.chibchawebbackend.dtos.TicketDTO;
import co.edu.unbosque.chibchawebbackend.dtos.UserDto;
import co.edu.unbosque.chibchawebbackend.entities.NivelTicketEntity;
import co.edu.unbosque.chibchawebbackend.entities.TicketEntity;
import co.edu.unbosque.chibchawebbackend.entities.UserEntity;
import co.edu.unbosque.chibchawebbackend.exceptions.AppException;
import co.edu.unbosque.chibchawebbackend.mappers.TicketMapper;
import co.edu.unbosque.chibchawebbackend.repositories.CountryRepository;
import co.edu.unbosque.chibchawebbackend.repositories.TicketRepository;
import co.edu.unbosque.chibchawebbackend.repositories.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.ValidationException;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.spi.ObjectThreadContextMap;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class TicketService {

    private final TicketRepository ticketRepository;
    private final TicketMapper ticketMapper;
    private final UserService userService;
    private final UserRepository userRepository;
    private final MailService mailService;
    private final CountryRepository countryRepository;

    @Transactional
    public void createTicket(TicketDTO ticketDto) {

        TicketEntity ticketEntity = ticketMapper.toTicketEntity(ticketDto);
        //ticketEntity.setIdNivelTicket();

        if (ticketDto.getIdUsuario() == null || ticketDto.getIdCategoria() == null) {
            throw new ValidationException("Required fields cannot be empty");
        }

        try {
            ticketRepository.save(ticketEntity);
        } catch (Exception e) {
            e.printStackTrace();
            throw new AppException("Error while saving ticket", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    //Logueados cualquier rol
    public TicketDTO getTicketById(Long ticketId) {
        TicketEntity ticketEntity = ticketRepository.findById(ticketId)
                .orElseThrow(() -> new EntityNotFoundException("Ticket not found by ID: " + ticketId));

        return ticketMapper.toTicketDTO(ticketEntity);
    }

    //Logueado rol administrador y coordinador
    public List<TicketDTO> getAllTickets() {
        List<TicketEntity> tickets = ticketRepository.findAll();
        return ticketMapper.toTicketDtoList(tickets);
    }

    //Logueado tecnico, admin y coordinador
    @Transactional
    public void updateTicket(Long ticketId, TicketDTO updateDto) {
        TicketEntity existingTicketEntity = ticketRepository.findById(ticketId)
                .orElseThrow(() -> new AppException("Ticket not found by ID: " + ticketId, HttpStatus.INTERNAL_SERVER_ERROR));

        TicketDTO ticketDto= ticketMapper.toTicketDTO(existingTicketEntity);

        ticketDto.setEstado(updateDto.getEstado());
        ticketDto.setIdUsuario(ticketDto.getIdUsuario());

        if(updateDto.getIdNivelTicket() != null){
            ticketDto.getIdNivelTicket().setId(updateDto.getIdNivelTicket().getId());
        }

        if (updateDto.getIdUsuarioSoporte() != null && updateDto.getIdUsuarioSoporte().getId() != 24) {
            ticketDto.getIdUsuarioSoporte().setId(updateDto.getIdUsuarioSoporte().getId());
           Optional<UserEntity> user = userRepository.findById(ticketDto.getIdUsuarioSoporte().getId());
            String[] messageTechnician = {user.get().getCorreo(),user.get().getNombre()};
            mailService.sendMessage("You have a new ticket for solve",messageTechnician,"notificationReg.html");
        }

        existingTicketEntity = ticketMapper.toUpdateTicketEntity(ticketDto);

        try {
            ticketRepository.save(existingTicketEntity);
        } catch (Exception e) {
            e.printStackTrace();
            throw new AppException("Error updating ticket", HttpStatus.INTERNAL_SERVER_ERROR);
        }

        String[] message = {ticketDto.getIdUsuario().getCorreo(),ticketDto.getIdUsuario().getNombre()};
        mailService.sendMessage("Your ticket has been updated",message,"notificationReg.html");
    }


    //Logueados Clientes
    public List<TicketDTO> getTicketsForCurrentUsers() {
        Long currentUser = userService.findByEmail(userService.getCurrentUser().getCorreo()).getId();
        List<TicketEntity> tickets = ticketRepository.findAllByIdUsuario_Id(currentUser);
        return ticketMapper.toTicketDtoList(tickets);
    }

}
