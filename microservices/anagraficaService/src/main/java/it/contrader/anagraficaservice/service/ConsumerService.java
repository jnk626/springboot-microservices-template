package it.contrader.anagraficaservice.service;

import it.contrader.anagraficaservice.dto.AnagraficaDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

import java.util.function.Consumer;

@Service
public class ConsumerService {
    @Autowired
    private AnagraficaService service;
    private static final Logger LOGGER = LoggerFactory.getLogger(ConsumerService.class);

    // using a modern functional approach, the Consumer is a function that acts whenever the payload of a certain
    // queue matches the class specified
    // extra: it always returns void
    // TODO requires further study
    @Bean
    public Consumer<AnagraficaDTO> anagraphicsSaver() {
        return anagraficaDTO -> {
            service.save(anagraficaDTO);
            LOGGER.info("Anagrafica salvata: {}", anagraficaDTO);

        };
    }
}
