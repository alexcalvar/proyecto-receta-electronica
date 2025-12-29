package es.uvigo.dagss.recetas.controllers;

import java.net.URI;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import es.uvigo.dagss.recetas.controllers.excepciones.ResourceNotFoundException;
import es.uvigo.dagss.recetas.entidades.Administrador;
import es.uvigo.dagss.recetas.servicios.AdministradorService;
import jakarta.validation.Valid;

@RestController
@RequestMapping(path = "/api/administradores", produces = MediaType.APPLICATION_JSON_VALUE)
@CrossOrigin(origins = "*")
public class AdministradorController {

    @Autowired
    private AdministradorService administradorService;

    // GET /api/administradores (Listar todos)
    @GetMapping
    public ResponseEntity<List<Administrador>> buscarTodos() {
        return new ResponseEntity<>(administradorService.buscarTodos(), HttpStatus.OK);
    }

    // GET /api/administradores/{id}
    @GetMapping(path = "{id}")
    public ResponseEntity<Administrador> buscarPorId(@PathVariable("id") Long id) {
        Optional<Administrador> admin = administradorService.buscarPorId(id);
        if (admin.isEmpty()) {
            throw new ResourceNotFoundException("Administrador no encontrado");
        }
        return new ResponseEntity<>(admin.get(), HttpStatus.OK);
    }

    // POST /api/administradores (Crear nuevo admin)
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Administrador> crear(@Valid @RequestBody Administrador admin) {
        // En un caso real, aquí encriptaríamos la contraseña antes de pasarla al servicio
        Administrador nuevo = administradorService.crear(admin); // O crear(admin)
        URI uri = ServletUriComponentsBuilder.fromCurrentRequestUri().path("/{id}").buildAndExpand(nuevo.getId()).toUri();
        return ResponseEntity.created(uri).body(nuevo);
    }

    // PUT /api/administradores/{id} (Modificar datos)
    @PutMapping(path = "{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Administrador> modificar(@PathVariable("id") Long id, @Valid @RequestBody Administrador admin) {
        Optional<Administrador> adminExistente = administradorService.buscarPorId(id);
        
        if (adminExistente.isEmpty()) {
            throw new ResourceNotFoundException("Administrador no encontrado");
        }
        
        // Aseguramos que el ID es el correcto
        admin.setId(id);
        // Ojo: Normalmente no se permite cambiar el login o password en un PUT simple sin validaciones extra
        Administrador modificado = administradorService.modificar(admin);
        return new ResponseEntity<>(modificado, HttpStatus.OK);
    }

    // DELETE /api/administradores/{id} (Baja lógica: poner activo = false)
    @DeleteMapping(path = "{id}")
    public ResponseEntity<HttpStatus> eliminar(@PathVariable("id") Long id) {
        Optional<Administrador> admin = administradorService.buscarPorId(id);

        if (admin.isEmpty()) {
            throw new ResourceNotFoundException("Administrador no encontrado");
        }
        Administrador administrador = admin.get();
        // Llamamos al servicio que hará la baja lógica (setActivo(false)) o física según tu implementación
        administradorService.eliminar(administrador); 
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}