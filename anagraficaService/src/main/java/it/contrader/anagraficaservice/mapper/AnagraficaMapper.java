package it.contrader.anagraficaservice.mapper;

import it.contrader.anagraficaservice.dto.AnagraficaDTO;
import it.contrader.anagraficaservice.model.Anagrafica;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AnagraficaMapper {

    Anagrafica toAnagrafica(AnagraficaDTO anagraficaDTO);

    AnagraficaDTO toAnagraficaDTO(Anagrafica anagrafica);
}
