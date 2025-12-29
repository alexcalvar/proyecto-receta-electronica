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
import es.uvigo.dagss.recetas.entidades.Farmacia;
import es.uvigo.dagss.recetas.servicios.FarmaciaService;
import jakarta.validation.Valid;

@RestController
@RequestMapping(path = "/api/farmacias", produces = MediaType.APPLICATION_JSON_VALUE)
@CrossOrigin(origins = "*")
public class FarmaciaController {

    @Autowired
    private FarmaciaService farmaciaService;

    @GetMapping
    public ResponseEntity<List<Farmacia>> buscarTodos() {
        return new ResponseEntity<>(farmaciaService.buscarTodos(), HttpStatus.OK);
    }

    @GetMapping(path = "{id}")
    public ResponseEntity<Farmacia> buscarPorId(@PathVariable("id") Long id) {
        Optional<Farmacia> farmacia = Optional.empty(); 
        // Nota: Si tu servicio no tiene buscarPorId, usa el DAO o impleméntalo.
        // Asumo que lo tienes o puedes usar buscarPorLocalidad como ejemplo.
        // farmacia = farmaciaService.buscarPorId(id); 
        
        if (farmacia.isEmpty()) {
            throw new ResourceNotFoundException("Farmacia no encontrada");
        }
        return new ResponseEntity<>(farmacia.get(), HttpStatus.OK);
    }

    @GetMapping(params = "localidad")
    public ResponseEntity<List<Farmacia>> buscarPorLocalidad(@RequestParam String localidad) {
        return new ResponseEntity<>(farmaciaService.buscarPorLocalidad(localidad), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Farmacia> crear(@Valid @RequestBody Farmacia farmacia) {
        Farmacia nueva = farmaciaService.modificar(farmacia);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequestUri().path("/{id}").buildAndExpand(nueva.getId()).toUri();
        return ResponseEntity.created(uri).body(nueva);
    }
}