package it.epicode.u5w3d2pratica.controller;

import it.epicode.u5w3d2pratica.dto.ViaggioDto;
import it.epicode.u5w3d2pratica.enumerations.StatoViaggio;
import it.epicode.u5w3d2pratica.exception.NotFoundException;
import it.epicode.u5w3d2pratica.exception.ValidationException;
import it.epicode.u5w3d2pratica.model.Viaggio;
import it.epicode.u5w3d2pratica.service.ViaggioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/viaggi")
public class ViaggioController {

    @Autowired
    private ViaggioService viaggioService;

    //creo dipendente
    @PostMapping("")
    public Viaggio creaViaggio(@RequestBody @Validated ViaggioDto viaggioDto, BindingResult bindingResult) throws ValidationException {
        if (bindingResult.hasErrors()){
            throw  new ValidationException(bindingResult.getAllErrors().stream().map(objectError -> objectError.getDefaultMessage()).reduce("",(e,s)->e+s));
        }
        return viaggioService.saveViaggio(viaggioDto);
    }

    @GetMapping("/{id}")
    public Viaggio getViaggio(@PathVariable int id) throws NotFoundException {
        return viaggioService.getViaggio(id);
    }

    @GetMapping("")
    public List<Viaggio> getAllDipendenti(){
        return viaggioService.getAllViaggi();
    }

    @PutMapping("/{id}")
    public Viaggio updateViaggio(@PathVariable int id,@RequestBody @Validated ViaggioDto viaggioDto,BindingResult bindingResult) throws ValidationException, NotFoundException {
        if (bindingResult.hasErrors()){
            throw  new ValidationException(bindingResult.getAllErrors().stream().map(objectError -> objectError.getDefaultMessage()).reduce("",(e,s)->e+s));
        }
        return  viaggioService.updateViaggio(id, viaggioDto);
    }

    @PatchMapping("/{id}")
    public Viaggio cambiaStatoViagio(@PathVariable int id, @RequestBody StatoViaggio statoViaggio) throws NotFoundException, IOException {
        return viaggioService.cambiaStatoViagio(id, statoViaggio);
    }

    @DeleteMapping("/{id}")
    public void deletaViaggio(@PathVariable int id) throws NotFoundException {
        viaggioService.deleteViaggio(id);
    }
}
