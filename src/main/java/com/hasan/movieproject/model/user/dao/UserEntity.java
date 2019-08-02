package com.hasan.movieproject.model.user.dao;

import com.hasan.movieproject.model.user.dto.RegisterDto;
import com.hasan.movieproject.model.user.dto.UserAddRequestDto;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;

@Entity
@Table(name = "Users")
public class UserEntity implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false, unique = true)
    @GeneratedValue(strategy = GenerationType.AUTO)
    private String username;
    @Column
    private String email;
    @Column
    private String name;
    @Column
    private String surname;
    @Column
    private String password;
    @Column
    private String status;

    @ManyToMany(cascade={CascadeType.PERSIST, CascadeType.MERGE, CascadeType.DETACH})
    @JoinTable(
            name = "ListsOfUsers",
            joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "list_id", referencedColumnName = "id")
    )
    private List<ListEntity> listEntities;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "users_roles",
            joinColumns = @JoinColumn(
                    name = "user_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(
                    name = "role_id", referencedColumnName = "id"))
    private List<RoleEntity> roles;

    public UserEntity() {
    }

    public UserEntity(RegisterDto registerDto){
        this.id = registerDto.getId();
        this.username = registerDto.getUsername();
        this.email = registerDto.getEmail();
        this.name = registerDto.getName();
        this.surname = registerDto.getSurname();
        this.password = registerDto.getPassword();
        this.status = "enabled"; //for now set this as enabled
    }

    public UserEntity(UserAddRequestDto userAddRequestDto){
        this.id = userAddRequestDto.getId();
        this.username = userAddRequestDto.getUsername();
        this.email = userAddRequestDto.getEmail();
        this.name = userAddRequestDto.getName();
        this.surname = userAddRequestDto.getSurname();
        this.password = userAddRequestDto.getPassword();
        this.status = "enabled";
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<ListEntity> getListEntities() {
        return listEntities;
    }

    public void setListEntities(List<ListEntity> listEntities) {
        this.listEntities = listEntities;
    }

    public List<RoleEntity> getRoles() {
        return roles;
    }

    public void setRoles(List<RoleEntity> roles) {
        this.roles = roles;
    }

    @Override
    public String toString() {
        return "UserEntity{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", email='" + email + '\'' +
                ", name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", password='" + password + '\'' +
                ", status='" + status + '\'' +
                ", listEntities=" + listEntities +
                ", roles=" + roles +
                '}';
    }
}
