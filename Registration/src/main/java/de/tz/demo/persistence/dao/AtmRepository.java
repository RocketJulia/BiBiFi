package de.tz.demo.persistence.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import de.tz.demo.persistence.model.Atm;

public interface AtmRepository  extends JpaRepository<Atm, Long> {
	Atm findByAtmNumber(int atmNumber);

	@Override
	void delete(Atm atm);

}
