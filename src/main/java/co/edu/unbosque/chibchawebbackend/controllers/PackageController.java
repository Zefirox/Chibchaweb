package co.edu.unbosque.chibchawebbackend.controllers;

import co.edu.unbosque.chibchawebbackend.dtos.PackageDto;
import co.edu.unbosque.chibchawebbackend.services.PackageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/package")
public class PackageController {

    private final PackageService packageService;

    @GetMapping("/")
    public ResponseEntity<List<PackageDto>> getAllPackages(){
        List<PackageDto> packageDtos = packageService.getAllPackages();

        return new ResponseEntity<>(packageDtos, HttpStatus.OK);
    }

    @GetMapping("/active")
    public ResponseEntity<List<PackageDto>> getAllActivePackages(){
        List<PackageDto> packageDtos = packageService.getAllActivePackages();

        return new ResponseEntity<>(packageDtos, HttpStatus.OK);
    }

    @PostMapping("/create")
    public ResponseEntity<String> createPackage(@RequestBody PackageDto newPackage){
        packageService.createPackage(newPackage);

        return ResponseEntity.ok("Package successfully created");
    }

    @GetMapping("/{packageId}")
    public ResponseEntity<PackageDto> getPackageById(@PathVariable Long packageId){
        PackageDto packageDto = packageService.getPackageById(packageId);

        return new ResponseEntity<>(packageDto, HttpStatus.OK);
    }

    @PostMapping("/{packageId}")
    public ResponseEntity<String> updatePackageById(@PathVariable Long packageId, @RequestBody PackageDto newPackage){
        packageService.updatePackageById(packageId, newPackage);

        return ResponseEntity.ok("Package successfully updated");
    }

    @DeleteMapping("/delete/{packageId}")
    public ResponseEntity<String> deletePackageById(@PathVariable Long packageId){
        packageService.deletePackage(packageId);

        return ResponseEntity.ok("Package deleted successfully");
    }
}
