package ru.tylyakov.urfuproject.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Table(name = "birthdays")
@Data
@NoArgsConstructor
public class Birthday {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Имя не может быть пустым")
    @Column(nullable = false)
    private String personName;

    @NotNull(message = "Дата рождения обязательна")
    @Column(nullable = false)
    private LocalDate birthdayDate;

    @Column(nullable = false)
    private String createdBy;
}
