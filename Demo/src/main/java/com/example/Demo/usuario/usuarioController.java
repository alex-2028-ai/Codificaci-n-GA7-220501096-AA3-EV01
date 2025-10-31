package com.example.Demo.usuario;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/usuarios")
@AllArgsConstructor
public class usuarioController {

    private final UsuarioRepository repository;

    @GetMapping ("/{id}")
    public ResponseEntity<usuario> getUsuario(@PathVariable Long id) {
        usuario u = repository.findById(id).orElse(null);
        return (u == null) ? ResponseEntity.notFound().build() : ResponseEntity.ok(u);
    }

    @GetMapping("/todos")
    public ResponseEntity<List<usuario>> getusuarios(){
        return ResponseEntity.ok(
                repository.findAll()
        );

    }

    @GetMapping("/delete/{id}")
    public ResponseEntity<?> deleteusuario(@PathVariable Long id) {
        repository.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping
    public ResponseEntity<usuario> crear(@Valid @RequestBody usuario u){
        return ResponseEntity.status(HttpStatus.CREATED).body(
                repository.save(u)
        );
    }

    @PutMapping("/{id}")
    public ResponseEntity<usuario> actualizar(@Valid @RequestBody usuario u, @PathVariable Long id){
        return ResponseEntity.status(HttpStatus.CREATED).body(
                repository.findById(id).map(usuario -> {
                    usuario.setEmail(u.getEmail());
                    usuario.setPassword(u.getPassword());
                    return repository.save(usuario);
                }).orElse(null)
        );
    }



}
