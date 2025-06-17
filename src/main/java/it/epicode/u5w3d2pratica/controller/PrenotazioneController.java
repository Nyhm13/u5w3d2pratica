package it.epicode.u5w3d2pratica.controller;

import it.epicode.u5w3d2pratica.dto.PrenotazioneDto;
import it.epicode.u5w3d2pratica.exception.ConflictException;
import it.epicode.u5w3d2pratica.exception.NotFoundException;
import it.epicode.u5w3d2pratica.exception.ValidationException;
import it.epicode.u5w3d2pratica.model.Prenotazione;
import it.epicode.u5w3d2pratica.service.PrenotazioneService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/prenotazioni")
public class PrenotazioneController {

    @Autowired
    private PrenotazioneService prenotazioneService;

    //creo dipendente
    @PostMapping("")
    public Prenotazione creaPrenotazione(@RequestBody @Validated PrenotazioneDto prenotazioneDto, BindingResult bindingResult) throws ValidationException, ConflictException, NotFoundException {
        if (bindingResult.hasErrors()){
            throw  new ValidationException(bindingResult.getAllErrors().stream().map(objectError -> objectError.getDefaultMessage()).reduce("",(e,s)->e+s));
        }
        return prenotazioneService.savePrenotazione(prenotazioneDto);
    }

    @GetMapping("/{id}")
    public Prenotazione getPrenotazione(@PathVariable int id) throws NotFoundException {
        return prenotazioneService.getPrenotazione(id);
    }

    @GetMapping("")
    public Page<Prenotazione> getAllPrenotazioni(@RequestParam(defaultValue = "0") int page,
                                                 @RequestParam(defaultValue = "10")int size,
                                                 @RequestParam(defaultValue = "id")String sortBy){
        return prenotazioneService.getAllPrenotazioni(page,size,sortBy);
    }

    @PutMapping("/{id}")
    public Prenotazione updatePrenotazione(@PathVariable int id,@RequestBody @Validated PrenotazioneDto prenotazioneDto,BindingResult bindingResult) throws ValidationException, NotFoundException, ConflictException {
        if (bindingResult.hasErrors()){
            throw  new ValidationException(bindingResult.getAllErrors().stream().map(objectError -> objectError.getDefaultMessage()).reduce("",(e,s)->e+s));
        }
        return  prenotazioneService.updatePrenotazione(id, prenotazioneDto);
    }



    @DeleteMapping("/{id}")
    public void deletaViaggio(@PathVariable int id) throws NotFoundException {
        prenotazioneService.deletePrenotazione(id);
    }

}
