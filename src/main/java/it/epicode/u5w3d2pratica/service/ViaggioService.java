package it.epicode.u5w3d2pratica.service;

import it.epicode.u5w3d2pratica.dto.ViaggioDto;
import it.epicode.u5w3d2pratica.enumerations.StatoViaggio;
import it.epicode.u5w3d2pratica.exception.NotFoundException;
import it.epicode.u5w3d2pratica.model.Viaggio;
import it.epicode.u5w3d2pratica.repository.ViaggioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ViaggioService {

    @Autowired
    private ViaggioRepository viaggioRepository;

    // metodo per creare un viaggio

    public Viaggio saveViaggio(ViaggioDto viaggioDto){
        Viaggio viaggio=new Viaggio();
        viaggio.setDestinazione(viaggioDto.getDestinazione());
        viaggio.setData(viaggioDto.getData());
        viaggio.setStatoViaggio(StatoViaggio.IN_PROGRAMMA); // setto in programma lo stato del viaggio alla creazione

        return  viaggioRepository.save(viaggio);
    }
    // metodo per cercare un viaggio per id

    public Viaggio getViaggio(int id) throws NotFoundException {
        return viaggioRepository.findById(id).orElseThrow(() -> new NotFoundException("Viaggio con ID " +
                id+ " non trovato "));
    }

    // metodo per cercare tutti i viaggi nel db

    public List<Viaggio> getAllViaggi(){
        return  viaggioRepository.findAll();
    }

    // metodo per aggiornare un viaggio esempio destinazione e data di partenza

    public Viaggio updateViaggio(int id, ViaggioDto viaggioDto) throws NotFoundException {
        Viaggio viaggioDaAggiornate=getViaggio(id);
        viaggioDaAggiornate.setDestinazione(viaggioDto.getDestinazione());
        viaggioDaAggiornate.setData(viaggioDto.getData());
        return  viaggioRepository.save(viaggioDaAggiornate);

    }

    // metodo per cancellare un viaggio
    public void deleteViaggio(int id) throws NotFoundException {
        Viaggio viaggioDaCancellare=getViaggio(id);
        viaggioRepository.delete(viaggioDaCancellare);
    }

    // metodo per aggiornare lo stato di un viaggio

    public Viaggio cambiaStatoViagio(int id, StatoViaggio statoViaggio) throws NotFoundException {
        Viaggio viaggioDaCambiareStato=getViaggio(id);
        viaggioDaCambiareStato.setStatoViaggio(statoViaggio);
        return viaggioRepository.save(viaggioDaCambiareStato);
    }


}
