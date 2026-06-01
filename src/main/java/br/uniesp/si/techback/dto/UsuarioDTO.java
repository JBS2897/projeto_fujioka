package br.uniesp.si.techback.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class UsuarioDTO {

    private Long id;

    @NotBlank(message = "O nome é obrigatório e não pode ficar em branco!")
    private String nome;

    @NotBlank(message = "O e-mail é obrigatório!")
    @Email(message = "Por favor, insira um e-mail válido!")
    private String email;

    @NotBlank(message = "A senha é obrigatória!")
    @Size(min = 6, message = "A senha deve ter no mínimo 6 caracteres!")
    private String senha;

    @NotBlank(message = "O CEP é obrigatório!")
    private String cep;

    // Esses campos abaixo não precisam de @NotBlank porque o ViaCEP vai preencher sozinho depois!
    private String logradouro;
    private String bairro;
    private String city; // ou cidade, dependendo de como você colocou no model
    private String estado;
}