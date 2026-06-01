package br.uniesp.si.techback.controller;

import br.uniesp.si.techback.dto.UsuarioDTO;
import br.uniesp.si.techback.service.SessaoService;
import br.uniesp.si.techback.service.UsuarioService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/sessoes")
@RequiredArgsConstructor
@Slf4j
public class SessaoController {

    private final SessaoService sessaoService;

    // ROTA 1: Botão para Cadastrar um Usuário Novo
    @PostMapping
    public ResponseEntity<UsuarioDTO> salvar(@Valid @RequestBody UsuarioDTO dto) {
        log.info("Recebendo requisição no Controller para cadastrar usuário: {}", dto.getEmail());

        UsuarioDTO usuarioSalvo = sessaoService.salvar(dto);

        // Retorna o status 211 (Created) que significa "Criado com Sucesso"
        return new ResponseEntity<>(usuarioSalvo, HttpStatus.CREATED);
    }

    // ROTA 2: Botão para Listar Todos os Usuários
    @GetMapping
    public ResponseEntity<List<UsuarioDTO>> listar() {
        log.info("Recebendo requisição no Controller para listar todos os usuários.");

        List<UsuarioDTO> listaUsuarios = sessaoService.listar();

        // Retorna a lista com o status 200 (OK)
        return ResponseEntity.ok(listaUsuarios);
    }
}