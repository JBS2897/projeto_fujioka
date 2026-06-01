package br.uniesp.si.techback.mapper;

import br.uniesp.si.techback.dto.UsuarioDTO;
import br.uniesp.si.techback.model.Usuario;
import org.springframework.stereotype.Component;

@Component
public class UsuarioMapper {

    // Traduz de MODEL (Banco) para DTO (Ficha do Swagger)
    public UsuarioDTO toDTO(Usuario usuario) {
        if (usuario == null) {
            return null;
        }

        UsuarioDTO dto = new UsuarioDTO();
        dto.setId(usuario.getId());
        dto.setNome(usuario.getNome());
        dto.setEmail(usuario.getEmail());
        dto.setSenha(usuario.getSenha());
        dto.setCep(usuario.getCep());
        dto.setLogradouro(usuario.getLogradouro());
        dto.setBairro(usuario.getBairro());
        dto.setCity(usuario.getCidade()); // Se no seu DTO você escreveu "city", mude aqui para setCity
        dto.setEstado(usuario.getEstado());

        return dto;
    }

    // Traduz de DTO (Ficha do Swagger) para MODEL (Banco)
    public Usuario toEntity(UsuarioDTO dto) {
        if (dto == null) {
            return null;
        }

        Usuario usuario = new Usuario();
        usuario.setId(dto.getId());
        usuario.setNome(dto.getNome());
        usuario.setEmail(dto.getEmail());
        usuario.setSenha(dto.getSenha());
        usuario.setCep(dto.getCep());
        usuario.setLogradouro(dto.getLogradouro());
        usuario.setBairro(dto.getBairro());
        usuario.setCidade(dto.getCity()); // Se no seu DTO você escreveu "city", mude aqui para getCity
        usuario.setEstado(dto.getEstado());

        return usuario;
    }
}