package ru.practicum.shareit.itemAndComment.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Entity
@Getter
@Setter
@ToString
@RequiredArgsConstructor
@Table(name = "items", schema = "public")
public class Item {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Название для вещи должно быть задано")
    @Column(name = "name")
    private String name;

    @NotBlank(message = "Описание для вещи должно быть задано")
    @Column(name = "description")
    private String description;

    @NotNull(message = "Статус для вещи должен быть задан")
    @Column(name = "status")
    private Boolean available;

    @OneToMany(mappedBy = "item")
    private List<Comment> comments;

    @Column(name = "user_id")
    private Long ownerId;
}
