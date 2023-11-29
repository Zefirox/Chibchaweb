package co.edu.unbosque.chibchawebbackend.controllers;

import co.edu.unbosque.chibchawebbackend.dtos.RoleDto;
import co.edu.unbosque.chibchawebbackend.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RequestMapping("/api/filter-data")
@RequiredArgsConstructor
@RestController
public class FilterDataController {

    private final UserService userService;

    @GetMapping("/roles")
    public ResponseEntity getRoles(){
        List<RoleDto> roleDtoList = userService.getRoles();
        return ResponseEntity.ok(roleDtoList);
    }

}
