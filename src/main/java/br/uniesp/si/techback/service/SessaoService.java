package br.uniesp.si.techback.service;

import br.uniesp.si.techback.dto.UsuarioDTO;
import br.uniesp.si.techback.mapper.UsuarioMapper;
import br.uniesp.si.techback.model.Usuario;
import br.uniesp.si.techback.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class SessaoService {

    private final UsuarioRepository usuarioRepository;
    private final UsuarioMapper usuarioMapper;

    // Função para SALVAR um usuário novo
    public UsuarioDTO salvar(UsuarioDTO dto) {
        log.info("Iniciando o cadastro do usuário com e-mail: {}", dto.getEmail());

        // REGRA DE NEGÓCIO: Verifica se o e-mail já existe no banco
        if (usuarioRepository.findByEmail(dto.getEmail()).isPresent()) {
            log.error("Erro: O e-mail {} já está cadastrado!", dto.getEmail());
            throw new RuntimeException("Este e-mail já está cadastrado no sistema!");
        }

        // INTEGRAÇÃO NOVO: Agora utilizando o BrasilAPI!
        try {
            log.info("Consultando o CEP {} na internet via BrasilAPI...", dto.getCep());
            // Remove traços ou espaços do CEP para mandar o número limpo
            String cepLimpo = dto.getCep().replace("-", "").replaceAll(" ", "");
            String url = "https://brasilapi.com.br/api/cep/v1/" + cepLimpo;

            // O RestTemplate vai até o site do BrasilAPI e traz as informações
            RestTemplate restTemplate = new RestTemplate();
            Map<String, String> dadosEndereco = restTemplate.getForObject(url, Map.class);

            if (dadosEndereco != null) {
                // O BrasilAPI devolve os nomes ligeiramente diferentes do ViaCEP, mudamos aqui:
                dto.setLogradouro(dadosEndereco.get("street"));   // BrasilAPI chama rua de 'street'
                dto.setBairro(dadosEndereco.get("neighborhood")); // BrasilAPI chama bairro de 'neighborhood'
                dto.setCity(dadosEndereco.get("city"));         // BrasilAPI chama cidade de 'city'
                dto.setEstado(dadosEndereco.get("state"));       // BrasilAPI chama estado de 'state'
                log.info("Endereço encontrado com sucesso via BrasilAPI para o CEP {}", dto.getCep());
            }
        } catch (Exception e) {
            log.error("Erro ao consultar o BrasilAPI (o CEP pode ser inválido): {}", e.getMessage());
            // O sistema continua para não travar o cadastro caso o serviço esteja instável
        }

        // Transforma a ficha (DTO) em um usuário real (Model)
        Usuario usuario = usuarioMapper.toEntity(dto);

        // Manda o operário guardar no banco
        Usuario usuarioSalvo = usuarioRepository.save(usuario);

        // Devolve o usuário cadastrado transformado em DTO para a tela
        return usuarioMapper.toDTO(usuarioSalvo);
    }

    // Função para LISTAR todos os usuários cadastrados
    public List<UsuarioDTO> listar() {
        log.info("Buscando todos os usuários cadastrados...");

        List<Usuario> usuarios = usuarioRepository.findAll();

        return usuarios.stream()
                .map(usuarioMapper::toDTO)
                .collect(Collectors.toList());
    }
}