package sanotesnotebookservice;

import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.oauth2.resource.OAuth2ResourceServerProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.security.oauth2.server.resource.introspection.NimbusOpaqueTokenIntrospector;
import org.springframework.security.oauth2.server.resource.introspection.OpaqueTokenIntrospector;
import sanotesnotebookservice.config.KeycloakTokenInstrospector;
import sanotesnotebookservice.security.JwtAuthenticationFilter;

import javax.annotation.PostConstruct;
import java.util.TimeZone;

@EnableJpaRepositories
@SpringBootApplication
public class SanotesNotebookServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(SanotesNotebookServiceApplication.class, args);
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
	public OpaqueTokenIntrospector keycloakIntrospector(OAuth2ResourceServerProperties props) {

		NimbusOpaqueTokenIntrospector delegate = new NimbusOpaqueTokenIntrospector(
				props.getOpaquetoken().getIntrospectionUri(),
				props.getOpaquetoken().getClientId(),
				props.getOpaquetoken().getClientSecret());

		return new KeycloakTokenInstrospector(delegate);
	}

}
