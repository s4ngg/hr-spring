package kr.co.hr.itcontact.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import kr.co.hr.itcontact.entity.ItContact;

public interface ItContactRepository extends JpaRepository<ItContact, Long> {
}