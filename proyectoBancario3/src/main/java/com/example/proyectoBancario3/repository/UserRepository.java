package com.example.proyectoBancario3.repository;

import com.example.proyectoBancario3.entities.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<Client, Long> {

}
