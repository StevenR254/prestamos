package com.example.prestamos.security;

import com.example.prestamos.Controller.LogoutHandler;
import com.example.prestamos.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import java.util.ArrayList;
import java.util.List;


@Configuration
@EnableWebSecurity
public class WebSecurityConfig implements UserDetailsService{

    private final LogoutHandler logoutHandler;
    public WebSecurityConfig(LogoutHandler logoutHandler) {
        this.logoutHandler = logoutHandler;
    }

    //@Autowired me crea el constructor de la inyeccion de dependencias que tenga activas
    @Autowired
    private UserService service;

    /*Esta clase nos sirve como filtro aqui configuramos las
      -URL que son permitidas sin autenticación
      -URL no permitidas sin autenticación
      -URL que me servira de LOGIN*/

    /*El funcionamiento por debajo de todo esto es que se generan Token csrf por peticiones que se hacen dentro de su sesion*/

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        /*Inicialmente se deja como http.csrf().disable() a fin de que podamos hacer peticiones POST de lo contrario nos saldra error 401 */
        http.csrf().disable()
                .authorizeHttpRequests()
                        // Esta primera linea me autoriza usar libremente sin limitaciones los archivos con terminaciones y controladores de css + js + registro + postregistro
                        .antMatchers("/", "/home","/**/*.js", "/**/*.css", "/login/registro", "/login/postregistro", "/error").permitAll()

                        //y esta linea hace entender que para cualquier otro archivo debe estar autenticado
                        .anyRequest().authenticated().and()
                //Aqui estamos configurando un doble factor de autenticacion el primero por OAUTH y (and) con el formLogin, si queremos agregar mas solo se debe poner and.
                .oauth2Login().loginPage("/login/login").defaultSuccessUrl("/inicio", true)
                .and()
                .formLogin()
                        //en este punto pondremos la ruta de nuestro controlador de login
                        .loginPage("/login/login")
                        //esta parte define la URL a seguir despues de un login existoso, para este caso es nuetro index o la pagina inicial.
                        .permitAll().defaultSuccessUrl("/inicio", true)

                //y finalmente aqui pondremos nuestra ruta de logout
                .and()
                .logout().logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                .addLogoutHandler(logoutHandler);

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        //Trae el usuario de mi base de datos por el correo electronico
        com.example.prestamos.entities.User userObjetc = this.service.selectByUserName(username);
        System.out.println(username);
        if(userObjetc != null){
            List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
            //Authorities.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
            return new User(
                    userObjetc.getEmail(),
                    userObjetc.getPassword(),
                    authorities
            );
        }
        throw new UsernameNotFoundException(
                "User '" + username + "' not found."
        );
    }

    /*No lo usamos pero sirve ejemplo de user y password generico*/
    /*@Bean
    public UserDetailsService userDetailsService() {
        UserDetails user =
                User.withDefaultPasswordEncoder()
                        .username("user")
                        .password("password")
                        .roles("USER")
                        .build();

        return new InMemoryUserDetailsManager(user);
    }*/

}
