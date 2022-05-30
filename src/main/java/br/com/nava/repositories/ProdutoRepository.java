package br.com.nava.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.nava.entities.ProdutoEntity;

public interface ProdutoRepository extends JpaRepository<ProdutoEntity, Integer>{

}
