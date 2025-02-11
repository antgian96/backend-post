package com.example.blog.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BlogPost {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String categoria;
    private String titolo;
    private String cover;

    @Column(columnDefinition = "TEXT")
    private String contenuto;

    private int tempoDiLettura;

    @ManyToOne
    @JoinColumn(name = "author_id")
    private Author autore;
}
