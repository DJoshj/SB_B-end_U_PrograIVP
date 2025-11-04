package com.springApp.security.filters;

import com.springApp.security.jwt.JwtUtils;
import com.springApp.services.impl.UserDetailsServiceImpl;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

/*==============================================
 *     FILTRO PARA VALIDAR EL TOKEN
 * ==============================================*/
@Component
public class JwtAuthorizationFilter extends OncePerRequestFilter {
    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
    private UserDetailsServiceImpl userDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        //extraemos el token de la petición
        String tokenHeader = request.getHeader("Authorization");

        if(tokenHeader != null && tokenHeader.startsWith("Bearer")){
            //quitamos el bearer que viene en el interior del token
            String token = tokenHeader.substring(7);

            //validamos si el token es valido
            if(jwtUtils.isTokenValid(token)){
                //obtenemos los datos del usuario
                String username = jwtUtils.getUsernameFromToken(token);
                /*con el userDetailService va a obtener el usuario de la base de datos, va a obtener todos los permisos, despues va a retornar
                 * un usuario propio de spring security con las autorizaciones, con el usuario y todos los demas datos*/
                UserDetails userDetails = userDetailsService.loadUserByUsername(username);

                //autenticacion
                UsernamePasswordAuthenticationToken authenticationToken =
                        new UsernamePasswordAuthenticationToken(username, null, userDetails.getAuthorities());

                //SecurityContextHolder -> contiene la autenticacion propia en la aplicacion
                //obtenemos el contexto y le seteamos la autenticaciónnueva del usuario
                SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            }
        }

        /*si el token es null o no comienza con bearer, va a continuar con el filtro de validacion, y va a verificar
         * que no tiene un token, y automaticamente nos va a bloquear el acceso*/
        filterChain.doFilter(request, response);
    }


}
