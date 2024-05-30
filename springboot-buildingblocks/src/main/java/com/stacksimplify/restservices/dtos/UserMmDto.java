        // DTOs = les DTOs sont un outil puissant pour structurer et sécuriser les transferts de données 
        // DTOs =  utilisés pour transférer des données entre la couche de service et la couche de contrôleurs, ou entre la couche de contrôleurs et la couche de persistance (entités).

package com.stacksimplify.restservices.dtos;

import java.util.List;

import com.stacksimplify.restservices.entities.Order;

public class UserMmDto {
    // tout les attribut qui sera écrit ici qui seront afficher
    private Long userid;
    private String username;
    private String firstname;
    private List<Order> orders;

    public Long getUserid() {
        return userid;
    }
    public void setUserid(Long userid) {
        this.userid = userid;
    }

    public String getFirstname() {
        return firstname;
    }
    public void setUserid(String firstname) {
        this.firstname = firstname;
    }
    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }
    public List<Order> getOrders() {
        return orders;
    }
    public void setOrders(List<Order> orders) {
        this.orders = orders;
    }
}
