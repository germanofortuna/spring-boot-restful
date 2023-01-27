package io.github.germanofortuna.services.impl;

import io.github.germanofortuna.domain.entity.Usuario;
import io.github.germanofortuna.domain.repository.UsuarioRepository;
import io.github.germanofortuna.exception.PedidoNaoEncontradoException;
import io.github.germanofortuna.exception.SenhaInvalidaException;
import io.github.germanofortuna.rest.dto.AtualizacaoUsuarioDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class UsuarioServiceImpl implements UserDetailsService {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UsuarioRepository repository;

    @Transactional
    public Usuario salvar(Usuario usuario) {
        return repository.save(usuario);
    }

    public UserDetails autenticar(Usuario usuario) {
        UserDetails user = loadUserByUsername(usuario.getLogin());
        boolean senhasBatem = passwordEncoder.matches(usuario.getSenha(), user.getPassword());

        if(senhasBatem) return user;

        throw new SenhaInvalidaException();
    }

    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        Usuario usuario = repository.findByLogin(userName)
                .orElseThrow(() -> new UsernameNotFoundException(("Usuário não encontrado na base de dados.")));

        String[] roles = usuario.isAdmin() ?
                new String[]{"ADMIN", "USER"} : new String[]{"USER"};

        return User
                .builder()
                .username(usuario.getLogin())
                .password(usuario.getSenha())
                .roles(roles)
                .build();
    }

    public void atualizaRole(Integer id, AtualizacaoUsuarioDTO dto) {
        Usuario usuario = repository.findById(id)
                .map(user -> {
                    if("ADMIN".equalsIgnoreCase(dto.getNewRole())) {
                        user.setAdmin(true);
                    } else {
                        user.setAdmin(false);
                    }
                    return repository.save(user);
                }).orElseThrow( () -> new UsernameNotFoundException("Usuário não encontrado"));
    }

    public List<Usuario> buscarTodos() {
        return repository.findAll();
    }
}
