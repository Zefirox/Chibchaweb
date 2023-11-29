package co.edu.unbosque.chibchawebbackend.controllers;

import co.edu.unbosque.chibchawebbackend.dtos.CategoryCDto;
import co.edu.unbosque.chibchawebbackend.dtos.CredentialsDto;
import co.edu.unbosque.chibchawebbackend.dtos.UserDto;
import co.edu.unbosque.chibchawebbackend.services.CategoryCService;
import co.edu.unbosque.chibchawebbackend.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;
    private final CategoryCService categoryCService;

    @GetMapping("/")
    public ResponseEntity<List<UserDto>> getUsersList(){
        List<UserDto> userDtoList = userService.getUsersDtoList();
        return ResponseEntity.ok(userDtoList);
    }

    @PutMapping("/{idUser}/info")
    public ResponseEntity<UserDto> updateInfoUser(@PathVariable Long idUser,
                                                  @RequestBody UserDto updateUserDto){
        UserDto userDto = userService.updateInfoUser(idUser,updateUserDto);

        return ResponseEntity.ok().body(userDto);
    }

    @PutMapping("/{idUser}/credentials")
    public ResponseEntity<UserDto> updateCredentialsUser(@PathVariable Long idUser,
                                                         @RequestBody CredentialsDto credentialsDto){
        UserDto userUpdated = userService.updateCredentialsUser(idUser, credentialsDto);
        return ResponseEntity.ok().body(userUpdated);
    }

    @GetMapping("/categories")
    public ResponseEntity<List<CategoryCDto>> getAllCategories(){
        List<CategoryCDto> list = categoryCService.getAllCategories();

        return ResponseEntity.ok(list);
    }

    @GetMapping("/category/{idCategory}")
    public ResponseEntity<CategoryCDto> getCategoryById(@PathVariable Long idCategory){
        CategoryCDto category = categoryCService.getCategoryById(idCategory);

        return ResponseEntity.ok(category);
    }

    @PostMapping("/newCategory")
    public ResponseEntity<String> registerCategory(@RequestBody CategoryCDto newCategory){
        categoryCService.createCategory(newCategory);

        return ResponseEntity.ok("Category created successfully");
    }

    @PutMapping("/category/{idCategory}")
    public ResponseEntity<String> updateCategory(@PathVariable Long idCategory,
                                                 @RequestBody CategoryCDto categoryCDto){
        boolean update = categoryCService.updateCategory(idCategory, categoryCDto);

        if (update) return ResponseEntity.ok("Category Updated Sucessfully");
        else return ResponseEntity.badRequest().body("Category not found");
    }

    @DeleteMapping("/category/{idCategory}")
    public ResponseEntity<String> deleteCategory(@PathVariable Long idCategory){
        boolean delete = categoryCService.deleteCategory(idCategory);

        if (delete) return ResponseEntity.ok("Category deleted successfully");
        else return ResponseEntity.notFound().build();
    }

}
