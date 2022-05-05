package sanotesnoteservice;

import org.apache.catalina.connector.Connector;
import org.apache.coyote.http11.AbstractHttp11Protocol;
import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.oauth2.resource.OAuth2ResourceServerProperties;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.security.oauth2.server.resource.introspection.NimbusOpaqueTokenIntrospector;
import org.springframework.security.oauth2.server.resource.introspection.OpaqueTokenIntrospector;
import sanotesnoteservice.config.KeycloakTokenInstrospector;
import sanotesnoteservice.security.JwtAuthenticationFilter;
import sanotesnoteservice.security.RequestJwtInterceptor;

import javax.annotation.PostConstruct;
import java.util.TimeZone;

@EnableMongoRepositories
@EnableJpaRepositories
@EnableFeignClients
@SpringBootApplication
public class SanotesNoteServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(SanotesNoteServiceApplication.class, args);
    }

    @Bean
    public TomcatServletWebServerFactory containerFactory() {
        return new TomcatServletWebServerFactory() {
            protected void customizeConnector(Connector connector) {
                super.customizeConnector(connector);
                if (connector.getProtocolHandler() instanceof AbstractHttp11Protocol) {
                    ((AbstractHttp11Protocol<?>) connector.getProtocolHandler()).setMaxHeaderCount(300);
                }
            }
        };
    }

    @PostConstruct
    void init() {
        TimeZone.setDefault(TimeZone.getTimeZone("UTC"));
    }

    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }

    @Bean
    public JwtAuthenticationFilter jwtAuthenticationFilter() {
        return new JwtAuthenticationFilter();
    }

    @Bean
    public RequestJwtInterceptor requestJwtInterceptor() {
        return new RequestJwtInterceptor();
    }

    @Bean
    public OpaqueTokenIntrospector keycloakIntrospector(OAuth2ResourceServerProperties props) {

        NimbusOpaqueTokenIntrospector delegate = new NimbusOpaqueTokenIntrospector(
                props.getOpaquetoken().getIntrospectionUri(),
                props.getOpaquetoken().getClientId(),
                props.getOpaquetoken().getClientSecret());

        return new KeycloakTokenInstrospector(delegate);
    }

}
