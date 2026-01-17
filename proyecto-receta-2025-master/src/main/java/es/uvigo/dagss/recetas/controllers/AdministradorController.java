package es.uvigo.dagss.recetas.controllers;

import java.net.URI;
import java.util.ArrayList; 
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import es.uvigo.dagss.recetas.controllers.excepciones.ResourceNotFoundException;
import es.uvigo.dagss.recetas.dtos.AdministradorDTO; 
import es.uvigo.dagss.recetas.entidades.Administrador;
import es.uvigo.dagss.recetas.servicios.AdministradorService;
import jakarta.validation.Valid;

@RestController
@RequestMapping(path = "/api/administradores", produces = MediaType.APPLICATION_JSON_VALUE)
@CrossOrigin(origins = "*")
public class AdministradorController {

    @Autowired
    private AdministradorService administradorService;

    // GET /api/administradores 
    @GetMapping
    public ResponseEntity<List<AdministradorDTO>> buscarTodos() { 
        List<Administrador> administradores = administradorService.buscarTodos();
        
        
        List<AdministradorDTO> dtos = new ArrayList<>();
        for (Administrador admin : administradores) {
            dtos.add(new AdministradorDTO(admin));
        }

        return new ResponseEntity<>(dtos, HttpStatus.OK);
    }

    // GET /api/administradores/{id}
    @GetMapping(path = "{id}")
    public ResponseEntity<AdministradorDTO> buscarPorId(@PathVariable("id") Long id) { 
        Optional<Administrador> admin = administradorService.buscarPorId(id);
        
        if (admin.isEmpty()) {
            throw new ResourceNotFoundException("Administrador no encontrado");
        }
        
        // se devuelve el DTO 
        return new ResponseEntity<>(new AdministradorDTO(admin.get()), HttpStatus.OK);
    }

    // POST /api/administradores 
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<AdministradorDTO> crear(@Valid @RequestBody Administrador admin) { 
        
      
        Administrador nuevo = administradorService.crear(admin);
        
        URI uri = ServletUriComponentsBuilder.fromCurrentRequestUri()
                .path("/{id}")
                .buildAndExpand(nuevo.getId())
                .toUri();
        
        
        return ResponseEntity.created(uri).body(new AdministradorDTO(nuevo));
    }

    // PUT /api/administradores/{id} 
    @PutMapping(path = "{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<AdministradorDTO> modificar(@PathVariable("id") Long id, 
                                                      @Valid @RequestBody Administrador admin) { 
        Optional<Administrador> adminExistente = administradorService.buscarPorId(id);
        
        if (adminExistente.isEmpty()) {
            throw new ResourceNotFoundException("Administrador no encontrado");
        }
        
        admin.setId(id);
        
  
        Administrador modificado = administradorService.modificar(admin);

        return new ResponseEntity<>(new AdministradorDTO(modificado), HttpStatus.OK);
    }

    // DELETE /api/administradores/{id}

    @DeleteMapping(path = "{id}")
    public ResponseEntity<HttpStatus> eliminar(@PathVariable("id") Long id) {
        Optional<Administrador> admin = administradorService.buscarPorId(id);

        if (admin.isEmpty()) {
            throw new ResourceNotFoundException("Administrador no encontrado");
        }
        
        Administrador administrador = admin.get();
        administradorService.eliminar(administrador);
        
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}