package ru.tylyakov.urfuproject.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "gifts")
@Data
@NoArgsConstructor
public class Gift {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Название подарка обязательно")
    private String giftName;

    @NotNull(message = "Стоимость обязательна")
    private Double price;

    @ManyToOne
    @JoinColumn(name = "birthday_id")
    private Birthday birthday;
}

