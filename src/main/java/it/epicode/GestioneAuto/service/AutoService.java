package it.epicode.GestioneAuto.service;

import it.epicode.GestioneAuto.exception.NotFoundException;
import it.epicode.GestioneAuto.model.Auto;
import it.epicode.GestioneAuto.model.Utente;
import it.epicode.GestioneAuto.repository.AutoRepository;
import it.epicode.GestioneAuto.request.AutoRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class AutoService {
    @Autowired
    private AutoRepository autoRepository;
    @Autowired
    private UtenteService utenteService;

    public Page<Auto> getAll(Pageable pageable){return autoRepository.findAll(pageable);}

    public Auto getAutoById(int id) throws NotFoundException{
        return autoRepository.findById(id).orElseThrow(() -> new NotFoundException("Auto non trovata"));
    }

    public Auto saveAuto(AutoRequest autoRequest){
        Auto auto = new Auto();
        Utente utente = utenteService.getUtenteById(autoRequest.getUtenteId());

        auto.setMarca(autoRequest.getMarca());
        auto.setModello(autoRequest.getModello());
        auto.setAnnoDiImmatricolazione(autoRequest.getAnnoDiImmatricolazione());
        auto.setCilindrata(autoRequest.getCilindrata());
        auto.setTipoAlimentazione(autoRequest.getTipoAlimentazione());
        auto.setDescrizione(autoRequest.getDescrizione());
        auto.setPrezzo(autoRequest.getPrezzo());
        auto.setUtente(utente);

        return autoRepository.save(auto);
    }

    public Auto updateAuto(int id, AutoRequest autoRequest) throws NotFoundException{
        Auto auto = getAutoById(id);
        Utente utente = utenteService.getUtenteById(autoRequest.getUtenteId());

        auto.setMarca(autoRequest.getMarca());
        auto.setModello(autoRequest.getModello());
        auto.setAnnoDiImmatricolazione(autoRequest.getAnnoDiImmatricolazione());
        auto.setCilindrata(autoRequest.getCilindrata());
        auto.setTipoAlimentazione(autoRequest.getTipoAlimentazione());
        auto.setDescrizione(autoRequest.getDescrizione());
        auto.setPrezzo(autoRequest.getPrezzo());
        auto.setUtente(utente);

        return autoRepository.save(auto);
    }

    public void deleteAuto(int id) throws NotFoundException{
        Auto auto = getAutoById(id);
        autoRepository.delete(auto);
    }
    public Auto uploadFoto(int id, String url) throws NotFoundException{
        Auto auto = getAutoById(id);
        auto.setFoto(url);
        return autoRepository.save(auto);
    }
}
