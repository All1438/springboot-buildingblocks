package com.stacksimplify.restservices.entities;

import java.util.List;

import org.springframework.hateoas.RepresentationModel;

import com.fasterxml.jackson.annotation.JsonFilter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonView;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;



@Entity
@Table(name = "users")
// @JsonIgnoreProperties({"firstname", "lastname"}) // permet d'ignorer ces 2 fichiers
// @JsonFilter(value = "userFilter") // qui sera connecté avec le JacksonController -- Used for MappingJacksonValue filtering section
public class User extends RepresentationModel<User>{

    @Id
    @GeneratedValue
    @JsonView(Views.External.class)
    private Long id;

    @NotEmpty(message = "Username is Mandatory field. Please provide username")
    @Column(name = "USER_NAME", length = 50, nullable = false, unique = true)
    @JsonView(Views.External.class)
    private String username;

    @Size(min = 2, message = "FirstName should have atleast 2 characters")
    @Column(name = "FIRST_NAME", length = 50, nullable = false)
    @JsonView(Views.External.class)
    private String firstname;

    @Column(name = "LAST_NAME", length = 50, nullable = false)
    @JsonView(Views.External.class)
    private String lastname;

    @Column(name = "EMAIL_ADRESS", length = 50, nullable = false)
    @JsonView(Views.Internal.class)
    private String email;

    @Column(name = "ROLE", length = 50, nullable = false)
    @JsonView(Views.Internal.class)
    private String role;

    @Column(name = "SSN", length = 50, nullable = false, unique = true)
    // @JsonIgnore // @JsonIgnore = seront ignorés lors de la sérialisation ou de la désérialisation JSON
    // une façon de faire le filtrage: pour ne pas afficher
    // sérialisation ou = est un processus de conversion d'un objet en un format de données qui peur être facilement stocké ou transmis. conversion un objet en un flux de bytes
    // si il y a quelque chose envoyer depuis la requête, alors il sera ignorer car c'est un json converti en objet
    @JsonView(Views.Internal.class)
    private String ssn;

    // One to Many
    @OneToMany(mappedBy="user") // qui veut dire mapper avec Order avec son attribut user
    @JsonView(Views.Internal.class)
    private List<Order> orders;

    
    // No Argument Constructor
    public User() {
    }

    // Field Constructor
    public User(Long id, String username, String firstname, String lastname, String email, String role, String ssn) {
        this.id = id;
        this.username = username;
        this.firstname = firstname;
        this.lastname = lastname;
        this.email = email;
        this.role = role;
        this.ssn = ssn;
    }
    
    // Getters and Setters
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

    public String getFirstname() {
        return firstname;
    }
    
    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }
    
    public String getLastname() {
        return lastname;
    }
    
    public void setLastname(String lastname) {
        this.lastname = lastname;
    }
    
    public String getEmail() {
        return email;
    }
    
    public void setEmail(String email) {
        this.email = email;
    }
    
    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
    
    public String getSsn() {
        return ssn;
    }
    
    public void setSsn(String ssn) {
        this.ssn = ssn;
    }
    
    public List<Order> getOrders() {
        return orders;
    }

    public void setOrders(List<Order> orders) {
        this.orders = orders;
    }

    // To String
    @Override
    public String toString() {
        return "User [id=" + id + ", username=" + username + ", firstname=" + firstname + ", lastname=" + lastname
                + ", email=" + email + ", role=" + role + ", ssn=" + ssn + "]";
    }

}
