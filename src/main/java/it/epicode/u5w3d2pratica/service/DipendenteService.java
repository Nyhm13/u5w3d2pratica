package it.epicode.u5w3d2pratica.service;

import com.cloudinary.Cloudinary;
import it.epicode.u5w3d2pratica.dto.DipendenteDto;
import it.epicode.u5w3d2pratica.exception.NotFoundException;
import it.epicode.u5w3d2pratica.model.Dipendente;
import it.epicode.u5w3d2pratica.repository.DipendenteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

@Service
public class DipendenteService {

    @Autowired
    private DipendenteRepository dipendenteRepository;
    @Autowired
    private Cloudinary cloudinary;

    @Autowired
    private JavaMailSenderImpl javaMailSender;


    //metodo per salvare un dipendente nel db
    public Dipendente saveDipendente(DipendenteDto dipendenteDto){
        Dipendente dipendente= new Dipendente();
        dipendente.setNome(dipendenteDto.getNome());
        dipendente.setCognome(dipendenteDto.getCognome());
        dipendente.setUsername(dipendenteDto.getUsername());
        dipendente.setEmail(dipendenteDto.getEmail());
        dipendente.setAvatar("https://ui-avatars.com/api/?name=" + dipendenteDto.getNome() + "+" + dipendenteDto.getCognome());


        Dipendente savedDipendente= dipendenteRepository.save(dipendente);

        // Invia una email di conferma al dipendente , l`ho commentato per evitare di inviare email ogni volta che creo un dipendente
//        sendMail(savedDipendente.getEmail(),
//                savedDipendente.getUsername(),
//                savedDipendente.getNome(),
//                savedDipendente.getCognome(),
//                savedDipendente.getId());


        return  savedDipendente;
    }

    //metodo per cercare un dipendente per id

    public Dipendente getDipendente(int id) throws NotFoundException {
        return dipendenteRepository.findById(id).orElseThrow(() -> new NotFoundException("Dipendente con ID " +
                id+" non trovato"));
    }

    //Metodo per recuperare tutti i dipendenti nel db

    public List<Dipendente> getAllDipendenti(){
        return dipendenteRepository.findAll();
    }

    // metodo per aggiornare un Dipendente

    public Dipendente updateDipendente(int id,DipendenteDto dipendenteDto) throws NotFoundException {
        Dipendente dipendenteDaAggiornare=getDipendente(id);
        dipendenteDaAggiornare.setNome(dipendenteDto.getNome());
        dipendenteDaAggiornare.setCognome(dipendenteDto.getCognome());
        dipendenteDaAggiornare.setUsername(dipendenteDto.getUsername());
        dipendenteDaAggiornare.setEmail(dipendenteDto.getEmail());
//        dipendenteDaAggiornare.setAvatar("https://ui-avatars.com/api/?name=" + dipendenteDto.getNome() + "+" + dipendenteDto.getCognome());

        return dipendenteRepository.save(dipendenteDaAggiornare);

    }

    // metodo per cancellare un dipendenda dal db

    public void deleteDipendente(int id) throws NotFoundException {
        Dipendente dipendenteDaCancellare=getDipendente(id);

        dipendenteRepository.delete(dipendenteDaCancellare);
    }

    // metodo per patchare un dipendente potendo aggiornare la foto profilo

    public String patchDipendente(int id, MultipartFile file) throws NotFoundException, IOException {
        Dipendente dipendenteDaPatchare=getDipendente(id);

        String url= (String) cloudinary.uploader().upload(file.getBytes(), Collections.emptyMap()).get("url");

        dipendenteDaPatchare.setAvatar(url);
        dipendenteRepository.save(dipendenteDaPatchare);

        return url ;
    }

    private void sendMail(String email,String username,String nome,String cognome,int id) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(email);
        message.setSubject("Registrazione Avvenuta con successo al sito di gestione dipendenti ");
        message.setText("Benvenuto " + nome + " " + cognome + ",\n\n" +
                "La tua registrazione al servizio REST è avvenuta con successo.\n" +
                        "Il tuo ID generato è: " + id + ".\n" +
                        "Il tuo username è: " + username + ".\n\n" +
                        "Ora puoi iniziare a prenotare i tuoi viaggi !\n\n" +
                        "Ricordati di tenere al sicuro le tue credenziali.\n\n"+
                        "Grazie per esserti unito a noi!\n\n" );

        javaMailSender.send(message);
    }

}
