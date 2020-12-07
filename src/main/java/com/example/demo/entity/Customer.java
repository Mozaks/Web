package com.example.demo.entity;

import com.example.demo.role.Role;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.Collection;
import java.util.Objects;
import java.util.Set;

@Entity
public class Customer implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Integer id;
    private String username;
    private String name;
    private String lastName;
    private String firstName;
    private boolean active;
    private String password;
    private String email;
    private String activationCode;

    @ElementCollection(targetClass = Role.class, fetch = FetchType.EAGER)
    @CollectionTable(
            name = "customer_role",
            joinColumns = @JoinColumn(name = "customer_id"))
    @Enumerated(EnumType.STRING)
    private Set<Role> roles;

    public Customer() {
    }

    public Customer(Customer customerCopy) {
        this.id = customerCopy.id;
        this.username = customerCopy.username;
        this.name = customerCopy.name;
        this.lastName = customerCopy.lastName;
        this.firstName = customerCopy.firstName;
        this.active = customerCopy.active;
        this.password = customerCopy.password;
        this.email = customerCopy.email;
        this.activationCode = customerCopy.activationCode;
        this.roles = customerCopy.roles;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }


    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return isActive();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return getRoles();
    }


    public void setUsername(String username) {
        this.username = username;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }


    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    public String getActivationCode() {
        return activationCode;
    }

    public void setActivationCode(String activationCode) {
        this.activationCode = activationCode;
    }

    public static Builder Builder() {
        return new Customer().Builder();
    }

    public static class Builder {

        Customer customer;

        private Builder() {
            this.customer = new Customer();
        }

        public Builder setUsername(String username) {
            this.customer.setUsername(username);
            return this;
        }

        public Builder setActive(boolean active) {
            this.customer.setActive(active);
            return this;
        }

        public Builder setName(String name) {
            this.customer.setName(name);
            return this;
        }

        public Builder setLastName(String lastName) {
            this.customer.setLastName(lastName);
            return this;
        }

        public Builder setFirstName(String firstName) {
            this.customer.setFirstName(firstName);
            return this;
        }

        public Builder setPassword(String password) {
            this.customer.setPassword(password);
            return this;
        }

        public Builder setEmail(String email) {
            this.customer.setEmail(email);
            return this;
        }

        public Builder setRoles(Set<Role> roles) {
            this.customer.setRoles(roles);
            return this;
        }

        public Builder setActivationCode(String activationCode) {
            this.customer.setActivationCode(activationCode);
            return this;
        }

        public Customer build() {
            return new Customer(this.customer);
        }

    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Customer customer = (Customer) o;
        return active == customer.active &&
                Objects.equals(id, customer.id) &&
                Objects.equals(username, customer.username) &&
                Objects.equals(name, customer.name) &&
                Objects.equals(lastName, customer.lastName) &&
                Objects.equals(firstName, customer.firstName) &&
                Objects.equals(password, customer.password) &&
                Objects.equals(email, customer.email) &&
                Objects.equals(activationCode, customer.activationCode) &&
                Objects.equals(roles, customer.roles);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, username, name, lastName, firstName, active, password, email, activationCode, roles);
    }

    @Override
    public String toString() {
        return "Customer{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", name='" + name + '\'' +
                ", lastName='" + lastName + '\'' +
                ", firstName='" + firstName + '\'' +
                ", active=" + active +
                ", password='" + password + '\'' +
                ", email='" + email + '\'' +
                ", activationCode='" + activationCode + '\'' +
                ", roles=" + roles +
                '}';
    }
}