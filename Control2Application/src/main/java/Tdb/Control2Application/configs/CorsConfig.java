package Tdb.Control2Application.configs;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;

@Configuration
public class CorsConfig {
    @Bean
    CorsConfigurationSource corsConfigurationSource(){
        CorsConfiguration corsConfiguration = new CorsConfiguration();

        //Connection with the frontend
        corsConfiguration.setAllowedOrigins(Arrays.asList("http://localhost:${DB_PORT}"));

        // This is to make petitions with the methods in the list
        corsConfiguration.setAllowedMethods(Arrays.asList("GET","POST","PUT","DELETE"));

        // Permite que se puedan enviar los headers que tengas dentro de la lista
        // Por lo general se coloca "*", que significa que se pueden enviar todos los headers
        corsConfiguration.setAllowedHeaders(Arrays.asList("*"));

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", corsConfiguration);

        return source;

    }
}
