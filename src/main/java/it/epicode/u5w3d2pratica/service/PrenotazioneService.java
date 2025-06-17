package it.epicode.u5w3d2pratica.service;

import it.epicode.u5w3d2pratica.dto.PrenotazioneDto;
import it.epicode.u5w3d2pratica.exception.ConflictException;
import it.epicode.u5w3d2pratica.exception.NotFoundException;
import it.epicode.u5w3d2pratica.model.Dipendente;
import it.epicode.u5w3d2pratica.model.Prenotazione;
import it.epicode.u5w3d2pratica.model.Viaggio;
import it.epicode.u5w3d2pratica.repository.DipendenteRepository;
import it.epicode.u5w3d2pratica.repository.PrenotazioneRepository;
import it.epicode.u5w3d2pratica.repository.ViaggioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
public class PrenotazioneService {

    @Autowired
    private PrenotazioneRepository prenotazioneRepository;

    @Autowired
    private DipendenteRepository dipendenteRepository;

    @Autowired
    private DipendenteService dipendenteService;

    @Autowired
    private ViaggioRepository viaggioRepository;
    @Autowired
    private ViaggioService viaggioService;


    // creo prenotazione e controllo se ce gia una prenotazione di un dipendente per la stessa data

    public Prenotazione savePrenotazione(PrenotazioneDto prenotazioneDto) throws NotFoundException, ConflictException {
//        Dipendente dipendente= dipendenteRepository.findById(prenotazioneDto.getDipendenteId())
//                .orElseThrow(() -> new NotFoundException("Dipendente non trovato"));
//        Viaggio viaggio=viaggioRepository.findById(prenotazioneDto.getViaggioId())
//                .orElseThrow(() -> new NotFoundException("Viaggio non trovato"));
        Dipendente dipendente = dipendenteService.getDipendente(prenotazioneDto.getDipendenteId());
        Viaggio viaggio = viaggioService.getViaggio(prenotazioneDto.getViaggioId());

        // controllo se  ce gia una prenotazione di un dipendente per la stessa data

        boolean esiste= prenotazioneRepository.existsByDipendenteAndDataRichiesta(dipendente,prenotazioneDto.getDataRichiesta());
        if (esiste){
            throw new ConflictException("Il dipendente ha gia una prenotazione per la data "+prenotazioneDto.getDataRichiesta());
        }
        // se non esiste creo una nuova prenotazione per l`utente recuperato precedentemente tramite l`id della prenotazioneDto
        Prenotazione prenotazioneNuova=new Prenotazione();
        prenotazioneNuova.setDipendente(dipendente);
        prenotazioneNuova.setViaggio(viaggio);
        prenotazioneNuova.setDataRichiesta(prenotazioneDto.getDataRichiesta());
        prenotazioneNuova.setNote(prenotazioneDto.getNote());

        return prenotazioneRepository.save(prenotazioneNuova);

    }
    //get di singola prenotazione get+id
    public Prenotazione getPrenotazione(int id) throws NotFoundException {
        return prenotazioneRepository.findById(id).orElseThrow(() -> new NotFoundException("Prenotazione con " + id+
                " non trovata"));
    }

    //get di tutte le prenotazione nel db

   public Page<Prenotazione> getAllPrenotazioni(int page,int size,String sortBy){
        Pageable pageable= PageRequest.of(page,size, Sort.by(sortBy));
        return prenotazioneRepository.findAll(pageable);
    }


    // metodo per aggiornare una prenotazione

    public Prenotazione updatePrenotazione(int id,PrenotazioneDto prenotazioneDto) throws NotFoundException, ConflictException {
        //recupero dal db la prenotazione gia esistente
        Prenotazione prenotazioneDaAggiornare=getPrenotazione(id);

        // controllo se cambia la data o il dipendente
        if (!prenotazioneDaAggiornare.getDataRichiesta().equals(prenotazioneDto.getDataRichiesta()) ||
                prenotazioneDaAggiornare.getDipendente().getId() != prenotazioneDto.getDipendenteId()
        ) {
            // recupero il dipendente dal db grazie al id preso dal dto
            Dipendente dipendenteRecuperato = dipendenteService.getDipendente(prenotazioneDto.getDipendenteId());
            // controllo se il dipendenteRecuperato ha gia una prenotazione nella stessa data se la ha lancio l`errore se no vado avanti
            if (prenotazioneRepository.existsByDipendenteAndDataRichiesta(dipendenteRecuperato, prenotazioneDto.getDataRichiesta())) {
                throw new ConflictException("il dipendente ha gia una prenotazione per questa data");
            }
        }
            //aggiorno i campi base della prenotazione se non ho errori
            prenotazioneDaAggiornare.setNote(prenotazioneDto.getNote());
            prenotazioneDaAggiornare.setDataRichiesta(prenotazioneDto.getDataRichiesta());
            //controllo se il dipendente della prenotazione è diverso dal dipendente passato dal dto, se si, aggiorno
            //la prenotazione con il nuovo dipendente passato dal dto
            if(prenotazioneDaAggiornare.getDipendente().getId()!= prenotazioneDto.getDipendenteId()){
                Dipendente dipendente=dipendenteService.getDipendente(prenotazioneDto.getDipendenteId());
                prenotazioneDaAggiornare.setDipendente(dipendente);
            }
            //controllo se il viaggio della prenotazione e diverso dal viaggio passato dal dto, se lo è aggiorno
            //la prenotazione con il nuovo viaggio passato dal dto
            if (prenotazioneDaAggiornare.getViaggio().getId() != prenotazioneDto.getViaggioId()) {
                Viaggio viaggio = viaggioService.getViaggio(prenotazioneDto.getViaggioId());
                prenotazioneDaAggiornare.setViaggio(viaggio);
            }

        //infine salvo la prenotazione
        return prenotazioneRepository.save(prenotazioneDaAggiornare);


    }
    // metodo delete per una prenotazione

    public void deletePrenotazione(int id) throws NotFoundException {
        Prenotazione prenotazioneDaCancellare=getPrenotazione(id);
        prenotazioneRepository.delete(prenotazioneDaCancellare);
    }

}
