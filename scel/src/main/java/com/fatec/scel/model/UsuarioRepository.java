package com.fatec.scel.model;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UsuarioRepository extends CrudRepository<Usuario, Long> {
		
	@Query("SELECT u FROM Usuario u WHERE u.ra = :ra")
    public Usuario findByRa(@Param("ra") String ra);
	public Usuario findByEmail(@Param("email") String email);
		
}