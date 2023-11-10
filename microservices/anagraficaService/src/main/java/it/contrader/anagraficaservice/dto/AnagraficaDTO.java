package it.contrader.anagraficaservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class AnagraficaDTO implements Serializable {

    private Long id;

    private String nome;

    private String cognome;

    private String codiceFiscale;

    private String nazionalita;

    private String indirizzo;

    private LocalDate dataDiNascita;

    private Long userId;
}
