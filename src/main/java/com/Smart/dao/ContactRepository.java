package com.Smart.dao;
import com.Smart.entities.Contact;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ContactRepository extends JpaRepository<Contact,Integer>{
	
	@Query("from Contact as c where c.user.id =:userId")
	public Page<Contact> findContactByUser( @Param("userId") int UserId, Pageable pagable);
	
	
	
}
