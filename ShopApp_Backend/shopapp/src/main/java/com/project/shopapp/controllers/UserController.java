package com.project.shopapp.controllers;

import com.project.shopapp.dtos.UserDTO;
import com.project.shopapp.dtos.UserLoginDTO;
import com.project.shopapp.models.User;
import com.project.shopapp.responses.LoginResponse;
import com.project.shopapp.services.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.support.RequestContextUtils;

import java.util.List;
import java.util.Locale;

@RestController
@RequestMapping("${api.prefix}/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final MessageSource messageSource;
    private final LocaleResolver localeResolver;

    @PostMapping("/register")
    public ResponseEntity<?> createUser(@Valid @RequestBody UserDTO userDTO, BindingResult result) {
        try {
            if (result.hasErrors()) {
                List<String> errorMessages = result
                        .getFieldErrors()
                        .stream()
                        .map(FieldError::getDefaultMessage)
                        .toList();
                return ResponseEntity.badRequest().body(errorMessages);
            }

            if (!userDTO.getPassword().equals(userDTO.getRetypePassword())) {
                return ResponseEntity.badRequest().body("Password does not match");
            }
            User user = userService.createUser(userDTO);
            return ResponseEntity.ok(user);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@Valid @RequestBody UserLoginDTO userLoginDTO,
                                               HttpServletRequest request) {
        try {
            String token = userService.login(userLoginDTO.getPhoneNumber(), userLoginDTO.getPassword());

            Locale locale = localeResolver.resolveLocale(request);

            return ResponseEntity.ok(LoginResponse.builder()
                    .message(messageSource.getMessage("user.login.login_successfully", null, locale))
                    .token(token)
                    .build());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(
                    LoginResponse.builder()
                            .message(e.getMessage())
                            .build());
        }
    }
}
