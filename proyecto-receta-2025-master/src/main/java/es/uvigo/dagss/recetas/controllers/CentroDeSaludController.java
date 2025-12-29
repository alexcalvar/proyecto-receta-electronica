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
import es.uvigo.dagss.recetas.entidades.CentroDeSalud;
import es.uvigo.dagss.recetas.servicios.CentroDeSaludService;
import jakarta.validation.Valid;

@RestController
@RequestMapping(path = "/api/centros", produces = MediaType.APPLICATION_JSON_VALUE)
@CrossOrigin(origins = "*")
public class CentroDeSaludController {

    @Autowired
    private CentroDeSaludService centroService;

    @GetMapping
    public ResponseEntity<List<CentroDeSalud>> buscarTodos() {
        return new ResponseEntity<>(centroService.buscarTodos(), HttpStatus.OK);
    }

    @GetMapping(path = "{id}")
    public ResponseEntity<CentroDeSalud> buscarPorId(@PathVariable("id") Long id) {
        Optional<CentroDeSalud> centro = centroService.buscarPorId(id);
        if (centro.isEmpty()) {
            throw new ResourceNotFoundException("Centro de salud no encontrado");
        }
        return new ResponseEntity<>(centro.get(), HttpStatus.OK);
    }

    // GET /api/centros?localidad=Vigo
    @GetMapping(params = "localidad")
    public ResponseEntity<List<CentroDeSalud>> buscarPorLocalidad(@RequestParam String localidad) {
        return new ResponseEntity<>(centroService.buscarPorLocalidad(localidad), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<CentroDeSalud> crear(@Valid @RequestBody CentroDeSalud centro) {
        CentroDeSalud nuevo = centroService.crear(centro);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequestUri().path("/{id}").buildAndExpand(nuevo.getId()).toUri();
        return ResponseEntity.created(uri).body(nuevo);
    }
    
    // Añade PUT y DELETE siguiendo el mismo patrón que PacienteController
}