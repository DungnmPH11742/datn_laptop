package com.poly.repo;

import com.poly.entity.Contact;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EnableJpaRepositories
public interface ContactRepository extends JpaRepository<Contact, Integer>, JpaSpecificationExecutor<Contact> {

}