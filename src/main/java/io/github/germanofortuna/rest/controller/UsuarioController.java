package io.github.germanofortuna.rest.controller;

import io.github.germanofortuna.domain.entity.Usuario;
import io.github.germanofortuna.exception.SenhaInvalidaException;
import io.github.germanofortuna.rest.dto.AtualizacaoUsuarioDTO;
import io.github.germanofortuna.rest.dto.CredenciaisDTO;
import io.github.germanofortuna.rest.dto.TokenDTO;
import io.github.germanofortuna.security.jwt.JwtService;
import io.github.germanofortuna.services.impl.UsuarioServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/api/usuarios")
@RequiredArgsConstructor
public class UsuarioController {

    private final UsuarioServiceImpl usuarioService;
    private final JwtService jwtService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Usuario salvar(@RequestBody Usuario usuario) {
        String senhaCritprografada = passwordEncoder.encode(usuario.getSenha());
        usuario.setSenha(senhaCritprografada);
        return usuarioService.salvar(usuario);
    }

    @PostMapping("/auth")
    public TokenDTO autenticar(@RequestBody CredenciaisDTO credenciais) {
        try {
            Usuario usuario = Usuario.builder()
                    .login(credenciais.getLogin())
                    .senha(credenciais.getSenha()).build();
            UserDetails usuarioAutenticado = usuarioService.autenticar(usuario);
            String token = jwtService.gerarToken(usuario);
            return new TokenDTO(usuario.getLogin(),token);
        } catch (UsernameNotFoundException | SenhaInvalidaException e) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, e.getMessage());
        }
    }

    @PatchMapping("{id}")
    public void updateRole(@PathVariable Integer id, @RequestBody AtualizacaoUsuarioDTO newRole) {
        usuarioService.atualizaRole(id, newRole);
    }

    @GetMapping
    public List<Usuario> buscarTodos() {
        return usuarioService.buscarTodos();
    }
}
