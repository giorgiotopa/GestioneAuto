package it.epicode.GestioneAuto.service;

import it.epicode.GestioneAuto.exception.NotFoundException;
import it.epicode.GestioneAuto.model.Auto;
import it.epicode.GestioneAuto.model.Utente;
import it.epicode.GestioneAuto.repository.UtenteRepository;
import it.epicode.GestioneAuto.request.UtenteRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UtenteService {

    @Autowired
    private UtenteRepository utenteRepository;
    @Autowired
    private PasswordEncoder encoder;

    public Page<Utente> getAll(Pageable pageable){
        return  utenteRepository.findAll(pageable);
    }
    public Utente getUtenteById(int id) throws NotFoundException{
        return utenteRepository.findById(id).orElseThrow(() -> new NotFoundException("Utente con id = " + id + " non trovato"));
    }

    public Utente getUtenteByUsername(String username) throws NotFoundException{
        return utenteRepository.findByUsername(username).orElseThrow(()-> new NotFoundException("Username non trovato"));
    }

    public Utente saveUtente(UtenteRequest utenteRequest){
        Utente u = new Utente();
        u.setNome(utenteRequest.getNome());
        u.setCognome(utenteRequest.getCognome());
        u.setEmail(utenteRequest.getEmail());
        u.setUsername(utenteRequest.getUsername());
        u.setPassword(encoder.encode(utenteRequest.getPassword()));

        return utenteRepository.save(u);
    }

    public Utente updateUtente(int id, UtenteRequest utenteRequest) throws NotFoundException{
        Utente u = getUtenteById(id);
        u.setNome(utenteRequest.getNome());
        u.setCognome(utenteRequest.getCognome());
        u.setEmail(utenteRequest.getEmail());
        u.setUsername(utenteRequest.getUsername());
//        if (utenteRequest.getPassword() != null && !utenteRequest.getPassword().isEmpty()) {
//            u.setPassword(encoder.encode(utenteRequest.getPassword()));
//        }

        return utenteRepository.save(u);
    }

    public void deleteUtenteById(int id) throws NotFoundException{
        Utente u = getUtenteById(id);
        utenteRepository.delete(u);
    }

    public void deleteUtenteByUsername(String username) throws NotFoundException{
        Utente u = getUtenteByUsername(username);
        utenteRepository.delete(u);
    }

    public Utente uploadAvatar(int id, String url) throws NotFoundException{
        Utente u = getUtenteById(id);
        u.setAvatar(url);
        return utenteRepository.save(u);
    }
    public List<Auto> getAutoByUtenteId(int utenteId) throws NotFoundException {
        Utente utente = getUtenteById(utenteId);
        return utente.getListaAuto();
    }
}
