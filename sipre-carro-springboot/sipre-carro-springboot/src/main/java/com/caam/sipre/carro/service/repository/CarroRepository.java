package com.caam.sipre.carro.service.repository;

import java.util.List;

import com.caam.sipre.carro.service.entity.Carro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface CarroRepository extends JpaRepository<Carro, Integer>{

   List<Carro> findByUsuarioId(int usuarioId);

}
