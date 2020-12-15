package me.arhariss.application.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import me.arhariss.application.entities.Adherent;

@Repository
public interface AdherentRepository extends JpaRepository<Adherent, Long> {
	
	List<Adherent> findByFirstNameContainsOrLastNameContainsAllIgnoreCase(String firstName, String lastName);
	
}
