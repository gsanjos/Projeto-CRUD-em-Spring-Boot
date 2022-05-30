package br.com.nava.controllers;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.nava.dtos.ProdutoDTO;
import br.com.nava.services.ProdutoService;

@RestController
@RequestMapping("produtosteste")
public class ProdutoController {

	@Autowired
	private ProdutoService produtoService;
	
	// Método getAll
	@GetMapping("")
	public ResponseEntity<List<ProdutoDTO>> getAll(){
		return ResponseEntity.status(HttpStatus.OK).body(produtoService.getAll());
	}
	
	// Método getOne
	@GetMapping("{id}")
	public ResponseEntity<ProdutoDTO> getOne(@PathVariable Integer id) {		
		return ResponseEntity.status(HttpStatus.OK).body(produtoService.getOne(id));
	}
	
	// Método save
	@PostMapping("")     
	public ResponseEntity<ProdutoDTO> save(@Valid @RequestBody ProdutoDTO produto) {
		return ResponseEntity.status(HttpStatus.OK).body(produtoService.save(produto.toEntity()));
	}
	
	// Método update	
	@PatchMapping("{id}")
	public ResponseEntity<ProdutoDTO> update(@PathVariable int id, @RequestBody ProdutoDTO produto) {
		return ResponseEntity.status(HttpStatus.OK).body(produtoService.update(id, produto.toEntity()));
	}
	
	// Método delete
	@DeleteMapping("{id}")
	public void delete(@PathVariable int id) {		
		produtoService.delete(id);
	}
	
	
}
