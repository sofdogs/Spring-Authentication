/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */

package com.sofia.security.user;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * @author sofiaschnurrenberger
 */

 // find user by unique email 
public interface UserRepository extends JpaRepository<User, Integer> {
    Optional<User> findByEmail(String email); 
}
