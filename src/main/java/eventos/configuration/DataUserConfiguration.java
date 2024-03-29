package eventos.configuration;

import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@EnableWebSecurity
@Configuration
public class DataUserConfiguration{
	
	@Bean
	public UserDetailsManager usersCustom(DataSource dataSource) {

	JdbcUserDetailsManager users = 
			new JdbcUserDetailsManager(dataSource); 
	users.setUsersByUsernameQuery("select username,password,enabled from Usuarios u where username=?"); 
	users.setAuthoritiesByUsernameQuery("select u.username,p.nombre from Usuario_Perfiles up " +
	 "inner join usuarios u on u.username = up.username " +
			"inner join perfiles p on p.id_perfil = up.id_perfil " +
			"where u.username = ?");
	return users;

	}


	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception{
		http
				.csrf(csrf -> csrf.disable());
		// Los recursos estáticos no requieren autenticación
		http.authorizeHttpRequests(authorize -> authorize
						// Las vistas públicas no requieren autenticación
						.requestMatchers("/registro","/", "/login", "/logout", "/eventos/verUno/**").permitAll()
						.requestMatchers("static/**").permitAll()
						.requestMatchers("/rest/encriptar/**").permitAll()
						.requestMatchers("/eventos/verDetalle").permitAll()
						.requestMatchers("/eventos/detalle/**").permitAll()
						.requestMatchers("/eventos/filtrarPorTipo/**").permitAll()
						.requestMatchers("/eventos/verActivos", "/eventos/verDestacados").permitAll()
						.requestMatchers("/login/acceder**").permitAll()
						.requestMatchers("/login/registro**").permitAll()
						//temporal
						.requestMatchers("/reservas/**").permitAll()
						.requestMatchers("/eventos/**").permitAll()
						
						// Todas las demás URLs de la Aplicación requieren autenticación
						// Asignar permisos a URLs por ROLES
						//.requestMatchers("/eventos/**").hasAnyAuthority("ROLE_CLIENTE")
						//.requestMatchers("/reservas/**").hasAnyAuthority("ROLE_CLIENTE")

						.anyRequest().authenticated())
				// El formulario de Login no requiere autenticacion
				.formLogin(form -> form.permitAll()
					.loginPage("/login")
					.defaultSuccessUrl("/home", true));
		return http.build();
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}


}
