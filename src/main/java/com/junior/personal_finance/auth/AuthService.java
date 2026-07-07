package com.junior.personal_finance.auth;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {
    private UsuarioRepository usuarioRepository;
    private JwtService jwtService;
    
    public AuthService(UsuarioRepository usuarioRepository, JwtService jwtService) {
        this.usuarioRepository = usuarioRepository;
        this.jwtService = jwtService;
    }

    public String registrar(UsuarioRequest request) {
        String senhaCriptografada = new BCryptPasswordEncoder().encode(request.getSenha());
        Usuario usuario = new Usuario(
            request.getNome(),
            request.getEmail(),
            senhaCriptografada
        );
        Usuario usuarioSalvo = usuarioRepository.save(usuario);
        return jwtService.gerarToken(usuarioSalvo);
    }

    public String login(UsuarioRequest request) {
        Usuario usuario = usuarioRepository.findByEmail(request.getEmail())
            .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));
        
        boolean senhaCorreta = new BCryptPasswordEncoder()
            .matches(request.getSenha(), usuario.getSenha());

        if (!senhaCorreta) {
            throw new RuntimeException("Senha incorreta");
        }

        return jwtService.gerarToken(usuario);
    }
}