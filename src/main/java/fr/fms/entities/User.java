package fr.fms.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Collection;

@Entity
@Data @NoArgsConstructor @AllArgsConstructor @ToString
public class User implements Serializable {

    @Id
    private String username;

    private String password;

    private boolean active;

    @ManyToMany
    private Collection<Role> roles;
}
