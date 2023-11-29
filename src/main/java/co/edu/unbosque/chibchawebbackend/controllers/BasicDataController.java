package co.edu.unbosque.chibchawebbackend.controllers;



import co.edu.unbosque.chibchawebbackend.dtos.*;
import co.edu.unbosque.chibchawebbackend.entities.UserEntity;
import co.edu.unbosque.chibchawebbackend.services.ClientService;
import co.edu.unbosque.chibchawebbackend.services.LocalityService;
import co.edu.unbosque.chibchawebbackend.services.ProductService;
import co.edu.unbosque.chibchawebbackend.services.UserService;
import org.springframework.http.ResponseEntity;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Codigo para eliminar. Solo para ejemplo de conexion Back-Front
 */

@RestController
@RequestMapping("/api/basic-data")
@RequiredArgsConstructor
@Slf4j
public class BasicDataController {

    private final LocalityService localityService;
    private final ProductService productService;
    private final ClientService clientService;


    @GetMapping("/idtypes")
    public ResponseEntity<List<IdentiTypeDto>> getIdTypes(){
        return ResponseEntity.ok(clientService.getIdentiTypes());
    }

    @GetMapping("/countries")
    public ResponseEntity<List<CountryDto>> getCountries(){
        return ResponseEntity.ok(localityService.getCountries());
    }

    @GetMapping("/countries/{country}/regions")
    public ResponseEntity<List<RegionDto>> getRegions(@PathVariable String country){
        return ResponseEntity.ok(localityService.getRegions(Long.parseLong(country)));
    }

    @GetMapping("/categories")
    public ResponseEntity<List<CategoryCDto>> getCategories(){
        return ResponseEntity.ok(productService.getCategoriesC());
    }

    @GetMapping("/packages")
    public ResponseEntity<List<PackageDto>> getPackages(){
        return ResponseEntity.ok(productService.getPackages());
    }

}
