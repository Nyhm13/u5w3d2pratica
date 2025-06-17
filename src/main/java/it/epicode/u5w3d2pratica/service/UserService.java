package it.epicode.u5w3d2pratica.service;

import it.epicode.u5w3d2pratica.dto.UserDto;
import it.epicode.u5w3d2pratica.exception.EntitaGiaEsistente;
import it.epicode.u5w3d2pratica.exception.NotFoundException;
import it.epicode.u5w3d2pratica.model.User;
import it.epicode.u5w3d2pratica.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public User saveUser(UserDto userDto) throws EntitaGiaEsistente {
        User user= new User();
        if (userRepository.existsByUsername(userDto.getUsername())){
            throw new EntitaGiaEsistente("Username gia esistente");
        }
        if (userRepository.existsByEmail(userDto.getEmail())){
            throw new EntitaGiaEsistente("Email gia esistente");
        }

        user.setNome(userDto.getNome());
        user.setCognome(userDto.getCognome());
        user.setEmail(userDto.getEmail());
        user.setPassword(userDto.getPassword());
        user.setUsername(userDto.getUsername());

        return  userRepository.save(user);

    }

    public List<User> getAllUser(){

        return userRepository.findAll();
    }

    public User getUser(int id) throws NotFoundException {
        return userRepository.findById(id).
                orElseThrow(() -> new NotFoundException("User con id " + id + " non trovato"));
    }

    public User updateUser(int id, UserDto userDto) throws NotFoundException {
        User userDaAggiornare = getUser(id);

        userDaAggiornare.setNome(userDto.getNome());
        userDaAggiornare.setCognome(userDto.getCognome());
        userDaAggiornare.setEmail(userDto.getEmail());
        userDaAggiornare.setPassword(userDto.getPassword());
        return userRepository.save(userDaAggiornare);

    }
    public void deleteUser(int id) throws NotFoundException {
        User userDaCancellare = getUser(id);

        userRepository.delete(userDaCancellare);
    }
}
