package com.springApp.services.impl;

import com.springApp.entity.UserEntity;
import com.springApp.entity.states.UserState;
import com.springApp.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        //busca si existe un usuario
        UserEntity userEntity =  userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("El usuario "+ username + "no existe"));

        //verifica si el usuario esta activo
        // Verificar si el usuario está activo
        if (userEntity.getState() != UserState.ACTIVE) {
            throw new UsernameNotFoundException("Usuario inactivo o bloqueado");
        }

        // Convertir el rol a GrantedAuthority
        Collection<? extends GrantedAuthority> authorities =
                List.of(new SimpleGrantedAuthority("ROLE_" + userEntity.getRoles().getName())); // Ojo con el prefijo ROLE_

        return new User(
                userEntity.getUsername(),
                userEntity.getPassword(),
                true,
                true,
                true,
                true,
                authorities);
    }
}
