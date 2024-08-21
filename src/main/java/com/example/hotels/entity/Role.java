package com.example.hotels.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.ToString;

import java.util.UUID;

@Data
@Entity
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    @Enumerated(value = EnumType.STRING)
    private RoleType roleType;

    @ManyToOne
    @JoinColumn(name = "username")
    @ToString.Exclude
    private User user;

//    public GrantedAuthority toRole() {
//        return new SimpleGrantedAuthority(roleType.name());
//    }

    public static Role from(RoleType type) {
        Role role = new Role();
        role.setRoleType(type);

        return role;
    }
}
