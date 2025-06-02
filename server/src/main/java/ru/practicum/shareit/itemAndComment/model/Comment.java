package ru.practicum.shareit.itemAndComment.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import ru.practicum.shareit.user.model.User;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Table(name = "comments")
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "comment")
    private String text;

    @Column(name = "created")
    private LocalDateTime created = LocalDateTime.now();

    private String authorName;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnore
    private Item item;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnore
    private User user;
}
