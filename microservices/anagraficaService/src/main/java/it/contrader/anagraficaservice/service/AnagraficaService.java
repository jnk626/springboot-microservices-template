package it.contrader.anagraficaservice.service;

import it.contrader.anagraficaservice.dto.AnagraficaDTO;
import it.contrader.anagraficaservice.mapper.AnagraficaMapper;
import it.contrader.anagraficaservice.repository.AnagraficaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AnagraficaService {

    @Autowired
    private AnagraficaRepository repository;

    @Autowired
    private AnagraficaMapper mapper;

    public AnagraficaDTO save(AnagraficaDTO anagraficaDTO) {
        return mapper.toAnagraficaDTO(repository.save(mapper.toAnagrafica(anagraficaDTO)));
    }
}
