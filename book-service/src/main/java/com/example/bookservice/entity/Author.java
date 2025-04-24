package com.example.bookservice.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "authors")
@ToString(exclude = "books")
@EqualsAndHashCode(of = "id", callSuper = false)
public class Author extends AuditingEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @OneToMany(mappedBy = "author")
    private List<Book> books = new ArrayList<>();
}