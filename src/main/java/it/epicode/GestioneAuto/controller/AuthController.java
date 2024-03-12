package it.epicode.GestioneAuto.controller;

import it.epicode.GestioneAuto.exception.BadRequestException;
import it.epicode.GestioneAuto.exception.CustomResponse;
import it.epicode.GestioneAuto.exception.LoginFaultException;
import it.epicode.GestioneAuto.model.Utente;
import it.epicode.GestioneAuto.request.LoginRequest;
import it.epicode.GestioneAuto.request.UtenteRequest;
import it.epicode.GestioneAuto.security.JwtTools;
import it.epicode.GestioneAuto.service.UtenteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private UtenteService utenteService;
    @Autowired
    private JwtTools jwtTools;
    @Autowired
    private PasswordEncoder encoder;

    @PostMapping("/register")
    public ResponseEntity<CustomResponse> register(@RequestBody @Validated UtenteRequest utenteRequest, BindingResult bindingResult){

        if(bindingResult.hasErrors()){

            throw new BadRequestException(bindingResult.getAllErrors().toString());

        }
        Utente user = utenteService.saveUtente(utenteRequest);
        return CustomResponse.success(HttpStatus.CREATED.toString(),user,HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<CustomResponse> login(@RequestBody @Validated LoginRequest loginRequest, BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            throw new BadRequestException(bindingResult.getAllErrors().toString());
        }
        Utente utente = utenteService.getUtenteByUsername(loginRequest.getUsername());
        if(encoder.matches(loginRequest.getPassword(), utente.getPassword())){
            String token = jwtTools.createToken(utente);

            CustomResponse customResponse = new CustomResponse(token, utente);
            return new ResponseEntity<>(customResponse, HttpStatus.ACCEPTED);

//            return CustomResponse.emptyResponse(token, HttpStatus.ACCEPTED);
        }
        else{
            throw new LoginFaultException("Username/Password errate");
        }
    }

}

