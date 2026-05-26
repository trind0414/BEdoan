package com.example.demo.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Generated;


@Table(name = "users")
@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long f0id;
    private String username;
    private String password;
    private String role;

    @Generated
    public User() {
    }

    @Generated
    public void setId(final Long id) {
        this.f0id = id;
    }

    @Generated
    public void setUsername(final String username) {
        this.username = username;
    }

    @Generated
    public void setPassword(final String password) {
        this.password = password;
    }

    @Generated
    public void setRole(final String role) {
        this.role = role;
    }

    @Generated
    public boolean equals(final Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof User)) {
            return false;
        }
        User other = (User) o;
        if (!other.canEqual(this)) {
            return false;
        }
        Object this$id = getId();
        Object other$id = other.getId();
        if (this$id == null) {
            if (other$id != null) {
                return false;
            }
        } else if (!this$id.equals(other$id)) {
            return false;
        }
        Object this$username = getUsername();
        Object other$username = other.getUsername();
        if (this$username == null) {
            if (other$username != null) {
                return false;
            }
        } else if (!this$username.equals(other$username)) {
            return false;
        }
        Object this$password = getPassword();
        Object other$password = other.getPassword();
        if (this$password == null) {
            if (other$password != null) {
                return false;
            }
        } else if (!this$password.equals(other$password)) {
            return false;
        }
        Object this$role = getRole();
        Object other$role = other.getRole();
        return this$role == null ? other$role == null : this$role.equals(other$role);
    }

    @Generated
    protected boolean canEqual(final Object other) {
        return other instanceof User;
    }

    @Generated
    public int hashCode() {
        Object $id = getId();
        int result = (1 * 59) + ($id == null ? 43 : $id.hashCode());
        Object $username = getUsername();
        int result2 = (result * 59) + ($username == null ? 43 : $username.hashCode());
        Object $password = getPassword();
        int result3 = (result2 * 59) + ($password == null ? 43 : $password.hashCode());
        Object $role = getRole();
        return (result3 * 59) + ($role == null ? 43 : $role.hashCode());
    }

    @Generated
    public String toString() {
        return "User(id=" + getId() + ", username=" + getUsername() + ", password=" + getPassword() + ", role=" + getRole() + ")";
    }

    @Generated
    public Long getId() {
        return this.f0id;
    }

    @Generated
    public String getUsername() {
        return this.username;
    }

    @Generated
    public String getPassword() {
        return this.password;
    }

    @Generated
    public String getRole() {
        return this.role;
    }
}