package ru.eliseev.exchangeratedemo.model.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Set;
import java.util.UUID;

@Data
@Entity
@Table(name = "usr")
@NoArgsConstructor
public class User {

    @Id
    private UUID id;
    private String username;
    private String password;
    private boolean active;

    public User(String username, String userPassword, boolean active, Set<Role> role) {
        this.id = UUID.randomUUID();
        this.username = username;
        this.password = userPassword;
        this.active = active;
        this.role = role;
    }

    @ElementCollection(targetClass = Role.class, fetch = FetchType.EAGER)
    @CollectionTable(name = "user_role", joinColumns = @JoinColumn(name = "user_id"))
    @Enumerated(EnumType.STRING)
    private Set<Role> role;

}