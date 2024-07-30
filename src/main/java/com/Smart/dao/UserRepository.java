package com.Smart.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.Smart.entities.User;

public interface UserRepository extends JpaRepository<User, Integer>{

}
