package br.uniesp.si.techback.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "tb_usuario")
@Data
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nome;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String senha;

    // Campos para a integração com o ViaCEP que o professor pediu
    private String cep;
    private String logradouro; // Rua
    private String bairro;
    private String cidade;
    private String estado;
}