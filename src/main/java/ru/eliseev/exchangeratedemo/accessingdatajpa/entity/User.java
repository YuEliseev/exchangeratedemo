package ru.eliseev.exchangeratedemo.accessingdatajpa.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.annotation.processing.Generated;
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
    private String userName;
    private String password;
    private boolean active;

    public User(String userName, String userPassword, boolean active, Set<Role> role) {
        this.userName = userName;
        this.password = userPassword;
        this.active = active;
        this.role = role;
    }

    @ElementCollection(targetClass = Role.class, fetch = FetchType.EAGER)
    @CollectionTable(name = "user_role", joinColumns = @JoinColumn(name = "user_id"))
    @Enumerated(EnumType.STRING)
    private Set<Role> role;

}