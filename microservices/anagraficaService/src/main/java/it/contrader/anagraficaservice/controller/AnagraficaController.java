package it.contrader.anagraficaservice.controller;

import it.contrader.anagraficaservice.dto.AnagraficaDTO;
import it.contrader.anagraficaservice.service.AnagraficaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/anag")
public class AnagraficaController {

    @Autowired
    private AnagraficaService service;

    @PostMapping("/registerAnagrafica")
    public AnagraficaDTO register(@RequestBody AnagraficaDTO anagraficaDTO) {
        return service.save(anagraficaDTO);
    }

}
