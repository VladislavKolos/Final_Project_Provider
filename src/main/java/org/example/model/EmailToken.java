package org.example.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

/**
 * An entity for storing email verification tokens.
 */
@Entity
@Table(name = "email_token")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class EmailToken {

    @Id
    @Column(name = "email_token_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String token;

    @Email
    private String email;

    private LocalDateTime expiryDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;
}
