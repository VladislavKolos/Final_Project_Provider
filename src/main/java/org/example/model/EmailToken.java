package org.example.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.time.LocalDateTime;

/**
 * An entity for storing email verification tokens.
 */
@Entity
@Table(name = "email_token")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EmailToken {

    @Id
    @Column(name = "email_token_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "token")
    private String token;

    @Email
    @Column(name = "email")
    private String email;

    @Size(min = 3, max = 32)
    @Column(name = "username")
    private String username;

    @Size(min = 10, max = 18)
    @Column(name = "phone")
    private String phone;

    @Column(name = "expiry_date")
    private LocalDateTime expiryDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;
}
