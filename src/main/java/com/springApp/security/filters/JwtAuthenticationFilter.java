package com.springApp.security.filters;

import com.fasterxml.jackson.core.exc.StreamReadException;
import com.fasterxml.jackson.databind.DatabindException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.springApp.entity.UserEntity;
import com.springApp.security.jwt.JwtUtils;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
    private JwtUtils jwtUtils;

    //inyección de dependencia por constructor
    public JwtAuthenticationFilter(JwtUtils jwtUtils){
        this.jwtUtils = jwtUtils;
    }

    //método para autenticación
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request,
                                                HttpServletResponse response) throws AuthenticationException {
        UserEntity userEntity = null;
        String username;
        String password;

        try {
            userEntity = new ObjectMapper().readValue(request.getInputStream(), UserEntity.class);
            //obtenemos el usuario y la contraseña
            username = userEntity.getUsername();
            password = userEntity.getPassword();


        } catch (StreamReadException e) {
            throw new RuntimeException(e);
        } catch (DatabindException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        //si no ningun error al pasar el username y el password no vamos a autenticar
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(username, password);

        //le enviamos el authenticationToken
        return getAuthenticationManager().authenticate(authenticationToken);
    }

    //si la autenticacion es exitosa, generamos el token
    @Override
    protected void successfulAuthentication(HttpServletRequest request,
                                            HttpServletResponse response,
                                            FilterChain chain,
                                            Authentication authResult) throws IOException, ServletException {
        //recuperamos los datos del usuario logueado
        User user = (User) authResult.getPrincipal();

        //generar el token
        String token =  jwtUtils.generateAccessToken(user.getUsername());

        //respondemos ala solicitud de login
        response.addHeader("Authorization", token);

        //mapeamos la respuesta y la convertimos a un json
        Map<String, Object> httpResponse = new HashMap<>();
        httpResponse.put("token", token);  //token
        httpResponse.put("Message", "Autenticacion Correcta");  //mensaje
        httpResponse.put("Username", user.getUsername());   //username

        //convertimos el map a un json
        response.getWriter().write(new ObjectMapper().writeValueAsString(httpResponse));
        //estado de la respuesta
        response.setStatus(HttpStatus.OK.value());
        //contenido de la respuesta
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        //aseguramos que todo se escriva correctamente
        response.getWriter().flush();

        super.successfulAuthentication(request, response, chain, authResult);
    }
}
