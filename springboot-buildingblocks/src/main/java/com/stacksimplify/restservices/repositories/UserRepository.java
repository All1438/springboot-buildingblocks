package com.stacksimplify.restservices.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.stacksimplify.restservices.entities.User;

@Repository
// JpaRepository = qui étend les interfaces 'CrudRepository' elle offre des
// méthodes CRUD
public interface UserRepository extends JpaRepository<User, Long> {

    User findByUsername(String username); // on ne mets pas liste parceque username est unique
}
