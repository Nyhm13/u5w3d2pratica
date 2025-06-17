package it.epicode.u5w3d2pratica.service;

import it.epicode.u5w3d2pratica.dto.LoginDto;
import it.epicode.u5w3d2pratica.exception.NotFoundException;
import it.epicode.u5w3d2pratica.model.User;
import it.epicode.u5w3d2pratica.repository.UserRepository;
import it.epicode.u5w3d2pratica.security.JwtTool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JwtTool jwtTool;



    public String login (LoginDto loginDto) throws NotFoundException {
        User user =userRepository.findByUsernameAndEmail(loginDto.getUsername(),loginDto.getEmail()).
                orElseThrow(() -> new NotFoundException("Utente con questo username/email non trovato"));

        if (loginDto.getPassword().equals(user.getPassword())){
            return jwtTool.createToken(user);
        } else {
            throw  new NotFoundException("Utente con questo username/email non trovato");
        }
    }
}
