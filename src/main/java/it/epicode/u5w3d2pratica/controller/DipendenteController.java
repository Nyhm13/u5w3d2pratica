package it.epicode.u5w3d2pratica.controller;

import it.epicode.u5w3d2pratica.dto.DipendenteDto;
import it.epicode.u5w3d2pratica.exception.NotFoundException;
import it.epicode.u5w3d2pratica.exception.ValidationException;
import it.epicode.u5w3d2pratica.model.Dipendente;
import it.epicode.u5w3d2pratica.service.DipendenteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/dipendenti")
public class DipendenteController {

    @Autowired
    private DipendenteService dipendenteService;

    //creo dipendente
    @PostMapping("")
    public Dipendente creaDipendente(@RequestBody  @Validated DipendenteDto dipendenteDto, BindingResult bindingResult) throws ValidationException {
        if (bindingResult.hasErrors()){
            throw  new ValidationException(bindingResult.getAllErrors().stream().map(objectError -> objectError.getDefaultMessage()).reduce("",(e,s)->e+s));
        }
        return dipendenteService.saveDipendente(dipendenteDto);
    }

    @GetMapping("/{id}")
    public Dipendente getDipendente(@PathVariable int id) throws NotFoundException {
        return dipendenteService.getDipendente(id);
    }

    @GetMapping("")
    public List<Dipendente> getAllDipendenti(){
        return dipendenteService.getAllDipendenti();
    }

    @PutMapping("/{id}")
    public Dipendente updateDipendente(@PathVariable int id,@RequestBody @Validated DipendenteDto dipendenteDto,BindingResult bindingResult) throws ValidationException, NotFoundException {
        if (bindingResult.hasErrors()){
            throw  new ValidationException(bindingResult.getAllErrors().stream().map(objectError -> objectError.getDefaultMessage()).reduce("",(e,s)->e+s));
        }
        return  dipendenteService.updateDipendente(id, dipendenteDto);
    }

    @PatchMapping("/{id}")
    public String aggiornamentoAvatarDipendente(@PathVariable int id, @RequestBody MultipartFile file) throws NotFoundException, IOException {
        return dipendenteService.patchDipendente(id, file);
    }

    @DeleteMapping("/{id}")
    public void deletaDipendente(@PathVariable int id) throws NotFoundException {
        dipendenteService.deleteDipendente(id);
    }

}
