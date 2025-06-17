package it.epicode.u5w3d2pratica.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;

// anche questa classe possiamo riutilizzare in altri progetti facendo semplicemente copy-paste
// ma prestando attenzione a eventuali modifiche necessarie di autorizzazione
@Configuration
@EnableWebSecurity // abilita la classe  ad essere responsabile della sicurezza dei servizi
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {

        // form Login, server per creare in automatico una pagina di login. A noi non serve, perche non usiamo pagine
        httpSecurity.formLogin(http->http.disable());
        // csrf server per evitare la possibilita di utilizzi di sessioni aperte da parte di terzi, per evitare attacchi CSRF cross-site request forgery
        // ma i servizzi ReST non usano sessioni, quindi possiamo disabilitarlo
        httpSecurity.csrf(http->http.disable());
        // session management, serve per gestire le sessioni degli utenti, ma i servizi ReST non usano sessioni, quindi possiamo disabilitarlo
        httpSecurity.sessionManagement(http->http.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
        // i cors sono una protezione che impedisce a un sito web di fare richieste a un altro dominio, ma i servizi ReST possono essere chiamati da altri domini
        httpSecurity.cors(Customizer.withDefaults());

        // autorizzazione delle richieste, in questo caso permettiamo a tutti di accedere alle risorse con endpoint /auth/** e a tutte le richieste GET e POST

        httpSecurity.authorizeHttpRequests(http->http.requestMatchers("/auth/**").permitAll());
//        httpSecurity.authorizeHttpRequests(http->http.requestMatchers(HttpMethod.GET,"/studenti/**").permitAll());
        httpSecurity.authorizeHttpRequests(http->http.requestMatchers("/dipendenti/**").permitAll());
        httpSecurity.authorizeHttpRequests(http->http.requestMatchers("/viaggi/**").permitAll());
        httpSecurity.authorizeHttpRequests(http->http.requestMatchers("/prenotazioni/**").permitAll());
//        httpSecurity.authorizeHttpRequests(http->http.requestMatchers(HttpMethod.GET).permitAll());
//        httpSecurity.authorizeHttpRequests(http->http.requestMatchers(HttpMethod.POST).permitAll());
//        httpSecurity.authorizeHttpRequests(http->http.requestMatchers(HttpMethod.PUT).permitAll());
//        httpSecurity.authorizeHttpRequests(http->http.requestMatchers(HttpMethod.PATCH).permitAll());
//        httpSecurity.authorizeHttpRequests(http->http.requestMatchers(HttpMethod.DELETE).permitAll());

        httpSecurity.authorizeHttpRequests(http->http.anyRequest().denyAll());

        return httpSecurity.build();
    }
}
