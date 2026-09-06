package com.finder.api.corsConfig;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;


@Configuration
public class CorsConfig {

    /*
      * Por padrão, o navegador bloqueia requisições entre origens diferentes por segurança.
      * Esta configuração de CORS (Cross-Origin Resource Sharing) permite que o frontend, 
      * que provavelmente está rodando em um domínio diferente (como http://localhost:3000), 
      * possa fazer requisições para a API Spring Boot sem ser bloqueado pelo navegador.
      * A configuração permite todas as origens ("*") e todos os métodos HTTP ("*"), 
      * o que é útil durante o desenvolvimento. Em produção, 
      * é recomendado restringir as origens e métodos permitidos para aumentar a segurança.
      * 
      * @return Um WebMvcConfigurer que configura as permissões de CORS para a aplicação.
      */
    @Bean
    public WebMvcConfigurer corsConfigurer() {
        
        return new WebMvcConfigurer() {

            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**")
                        .allowedOrigins("http://localhost:3000") //Aqui você pode especificar o domínio do frontend
                        .allowedMethods("*");
            }

        };

    }

}
