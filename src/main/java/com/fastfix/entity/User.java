package com.fastfix.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "user")
@Getter
@Setter
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Column(name = "first_name",nullable = false)
    @Size(min = 2, max = 50)
    private String firstName;

    @NotBlank
    @Column(name = "last_name",nullable = false)
    @Size(min = 2, max = 50)
    private String lastName;

    @NotBlank
    @Size(max = 254)
    @Column(name = "email",nullable = false,unique = true)
    private String email;

    @NotBlank
    @Size(min = 8, max = 72)
    @Column(name = "password",nullable = false)
    private String password;

    @Column(columnDefinition = "MEDIUMBLOB")
    private byte[] picture;

    @Column(name = "alternative_email")
    private String alternativeEmail;

    @Column(name = "verification_code")
    private String verificationCode;

    @Column(name = "verification_token")
    private String verificationToken;

    @Column(name = "create_at")
    private LocalDateTime createAt;

    @Column(name = "expires_at")
    private LocalDateTime expiresAt;

    @ManyToMany(fetch = FetchType.EAGER,cascade = CascadeType.DETACH)
    @JoinTable(name = "users_roles", joinColumns = @JoinColumn(name = "user_id"),
    inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> role = new HashSet<>();


    public User() {
    }

    public User(String firstName, String lastName, String email, String password, byte[] picture,
                String alternativeEmail, String verificationCode, String verificationToken, LocalDateTime createAt,
                LocalDateTime expiresAt, Set<Role> role) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.picture = picture;
        this.alternativeEmail = alternativeEmail;
        this.verificationCode = verificationCode;
        this.verificationToken = verificationToken;
        this.createAt = createAt;
        this.expiresAt = expiresAt;
        this.role = role;
    }

    public void createRole(Role role){
        this.role.add(role);
    }
}
