package it.contrader.authenticationservice.feignClient;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import it.contrader.authenticationservice.dto.AnagraficaDTO;
import it.contrader.authenticationservice.security.UserDetailsImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.openfeign.FallbackFactory;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

@FeignClient(name = "anagservice", configuration = FeigClientConfig.class)
public interface AnagraficaFeignClient {

    @PostMapping("/anag/registerAnagrafica")
    AnagraficaDTO register(@RequestBody AnagraficaDTO anagraficaDTO);
}

@Slf4j
@Component
class AnagraficaFallback implements FallbackFactory<AnagraficaFeignClient> {

    @Override
    public AnagraficaFeignClient create(Throwable cause) {
        return new AnagraficaFeignClient() {
            @Override
            public AnagraficaDTO register(AnagraficaDTO anagraficaDTO) {
                throw new RuntimeException("Errore durante la creazione dell'anagrafica - ERROR: " + cause);
            }
        };

    }
}

