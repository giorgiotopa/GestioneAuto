package it.epicode.GestioneAuto.controller;

import com.cloudinary.Cloudinary;
import it.epicode.GestioneAuto.exception.BadRequestException;
import it.epicode.GestioneAuto.exception.CustomResponse;
import it.epicode.GestioneAuto.model.Utente;
import it.epicode.GestioneAuto.request.UtenteRequest;
import it.epicode.GestioneAuto.service.UtenteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.HashMap;

@RestController
@RequestMapping("/utenti")
public class UtenteController {
    @Autowired
    private UtenteService utenteService;
    @Autowired
    private Cloudinary cloudinary;

    @GetMapping("")
    public ResponseEntity<CustomResponse> getAll(Pageable pageable){
        return CustomResponse.success(HttpStatus.OK.toString(), utenteService.gettAll(pageable), HttpStatus.OK);
    }
    @GetMapping("/{id}")
    public ResponseEntity<CustomResponse> getUtenteById(@PathVariable int id){
        return CustomResponse.success(HttpStatus.OK.toString(), utenteService.getUtenteById(id), HttpStatus.OK);
    }
//    @GetMapping("/{username}")
//    public ResponseEntity<CustomResponse> getUtenteByUsername(@PathVariable String username){
//        return CustomResponse.success(HttpStatus.OK.toString(), utenteService.getUtenteByUsername(username), HttpStatus.OK);
//    }

    @PostMapping("")
    public ResponseEntity<CustomResponse> saveUtente(@RequestBody @Validated UtenteRequest utenteRequest, BindingResult bindingResult){
        if(bindingResult.hasErrors()) throw new BadRequestException(bindingResult.getAllErrors().stream().map(DefaultMessageSourceResolvable::getDefaultMessage).toList().toString());
        return CustomResponse.success(HttpStatus.OK.toString(), utenteService.saveUtente(utenteRequest), HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CustomResponse> updateUtente(@PathVariable int id, @RequestBody @Validated UtenteRequest utenteRequest, BindingResult bindingResult ){
        if(bindingResult.hasErrors()) throw new BadRequestException(bindingResult.getAllErrors().stream().map(DefaultMessageSourceResolvable::getDefaultMessage).toList().toString());
        return CustomResponse.success(HttpStatus.OK.toString(), utenteService.updateUtente(id, utenteRequest), HttpStatus.OK);
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<CustomResponse> deleteUtenteById(@PathVariable int id){
        utenteService.deleteUtenteById(id);
        return CustomResponse.emptyResponse("L'utente con id = " + id + " è stato cancellato", HttpStatus.OK);
    }
//    @DeleteMapping("/{username}")
//    public ResponseEntity<CustomResponse> deleteUtenteByUsername(@PathVariable String username){
//        utenteService.deleteUtenteByUsername(username);
//        return CustomResponse.emptyResponse("L'utente con id = " + username + " è stato cancellato", HttpStatus.OK);
//    }

    @PatchMapping("/{id}/upload")
    public ResponseEntity<CustomResponse> uploadAvatar(@PathVariable int id,@RequestParam("upload") MultipartFile file){
        try {
            Utente d = utenteService.uploadAvatar(id, (String)cloudinary.uploader().upload(file.getBytes(), new HashMap()).get("url"));
            return CustomResponse.success(HttpStatus.OK.toString(), d, HttpStatus.OK);
        }
        catch (IOException e){
            return CustomResponse.error(e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }



}
