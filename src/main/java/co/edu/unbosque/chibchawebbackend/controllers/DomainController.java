package co.edu.unbosque.chibchawebbackend.controllers;

import co.edu.unbosque.chibchawebbackend.dtos.DomainDto;
import co.edu.unbosque.chibchawebbackend.services.DomainService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RequiredArgsConstructor
@RequestMapping("/api/domains")
@RestController
public class DomainController {

    private final DomainService domainService;

    @GetMapping("/{domain}/validate")
    public ResponseEntity validateDomain(@PathVariable String domain){
        return ResponseEntity.ok(domainService.validateDomain(domain));
    }


    @PutMapping("/{domain}")
    public ResponseEntity updateDomainDto(@RequestBody DomainDto domainDto){

        DomainDto domain = domainService.updateDomain(domainDto);
        return ResponseEntity.created(URI.create("/domain/"+domain.getId())).body(domain);
    }


    @GetMapping("/")
    public ResponseEntity<List<DomainDto>> getDomainsList(){
        List<DomainDto> domainDtoList = domainService.getDomainsDtoList();
        return ResponseEntity.ok(domainDtoList);
    }

    @GetMapping("/user")
    public List<DomainDto> getDomainsForCurrentUser() {
        return domainService.getDomainsForCurrentUser();
    }
}
