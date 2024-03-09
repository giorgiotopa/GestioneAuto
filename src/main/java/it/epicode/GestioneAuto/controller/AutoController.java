package it.epicode.GestioneAuto.controller;

import com.cloudinary.Cloudinary;
import it.epicode.GestioneAuto.exception.BadRequestException;
import it.epicode.GestioneAuto.exception.CustomResponse;
import it.epicode.GestioneAuto.model.Auto;
import it.epicode.GestioneAuto.model.Utente;
import it.epicode.GestioneAuto.request.AutoRequest;
import it.epicode.GestioneAuto.request.UtenteRequest;
import it.epicode.GestioneAuto.service.AutoService;
import it.epicode.GestioneAuto.service.UtenteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.HashMap;

@RestController
@RequestMapping("/auto")
public class AutoController {

    @Autowired
    private AutoService autoService;
    @Autowired
    private Cloudinary cloudinary;

    @GetMapping("")
    public ResponseEntity<CustomResponse> getAll(Pageable pageable){
        return CustomResponse.success(HttpStatus.OK.toString(), autoService.getAll(pageable), HttpStatus.OK);
    }
    @GetMapping("/{id}")
    public ResponseEntity<CustomResponse> getAutoById(@PathVariable int id){
        return CustomResponse.success(HttpStatus.OK.toString(), autoService.getAutoById(id), HttpStatus.OK);
    }
    @PostMapping("/create")
    public ResponseEntity<CustomResponse> saveAuto(@RequestBody @Validated AutoRequest autoRequest, BindingResult bindingResult){
        if(bindingResult.hasErrors()) throw new BadRequestException(bindingResult.getAllErrors().stream().map(DefaultMessageSourceResolvable::getDefaultMessage).toList().toString());
        return CustomResponse.success(HttpStatus.OK.toString(), autoService.saveAuto(autoRequest), HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CustomResponse> updateAuto(@PathVariable int id, @RequestBody @Validated AutoRequest autoRequest, BindingResult bindingResult ){
        if(bindingResult.hasErrors()) throw new BadRequestException(bindingResult.getAllErrors().stream().map(DefaultMessageSourceResolvable::getDefaultMessage).toList().toString());
        return CustomResponse.success(HttpStatus.OK.toString(), autoService.updateAuto(id, autoRequest), HttpStatus.OK);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<CustomResponse> deleteAuto(@PathVariable int id){
        autoService.deleteAuto(id);
        return CustomResponse.emptyResponse("L'auto con id = " + id + " è stata cancellata", HttpStatus.OK);
    }

    @PatchMapping("/{id}/upload")
    public ResponseEntity<CustomResponse> uploadAvatar(@PathVariable int id,@RequestParam("upload") MultipartFile file){
        try {
            Auto auto = autoService.uploadFoto(id, (String)cloudinary.uploader().upload(file.getBytes(), new HashMap()).get("url"));
            return CustomResponse.success(HttpStatus.OK.toString(), auto, HttpStatus.OK);
        }
        catch (IOException e){
            return CustomResponse.error(e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
