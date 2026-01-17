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
import es.uvigo.dagss.recetas.dtos.FarmaciaDTO; 
import es.uvigo.dagss.recetas.entidades.Farmacia;
import es.uvigo.dagss.recetas.servicios.FarmaciaService;
import jakarta.validation.Valid;

@RestController
@RequestMapping(path = "/api/farmacias", produces = MediaType.APPLICATION_JSON_VALUE)
@CrossOrigin(origins = "*")
public class FarmaciaController {

    @Autowired
    private FarmaciaService farmaciaService;

    // GET /api/farmacias
    @GetMapping
    public ResponseEntity<List<FarmaciaDTO>> buscarTodos() { 
        List<Farmacia> farmacias = farmaciaService.buscarTodos();
        
        List<FarmaciaDTO> dtos = new ArrayList<>();
        for (Farmacia f : farmacias) {
            dtos.add(new FarmaciaDTO(f));
        }

        return new ResponseEntity<>(dtos, HttpStatus.OK);
    }

    // GET /api/farmacias/{id}
    @GetMapping(path = "{id}")
    public ResponseEntity<FarmaciaDTO> buscarPorId(@PathVariable("id") Long id) { 
       //llamar al servicio real 
        Optional<Farmacia> farmacia = farmaciaService.buscarPorId(id);
        
        if (farmacia.isEmpty()) {
            throw new ResourceNotFoundException("Farmacia no encontrada");
        }
        
        return new ResponseEntity<>(new FarmaciaDTO(farmacia.get()), HttpStatus.OK);
    }

    // GET /api/farmacias?localidad=Vigo
    @GetMapping(params = "localidad")
    public ResponseEntity<List<FarmaciaDTO>> buscarPorLocalidad(@RequestParam String localidad) { 
        List<Farmacia> farmacias = farmaciaService.buscarPorLocalidad(localidad);
        
        List<FarmaciaDTO> dtos = new ArrayList<>();
        for (Farmacia f : farmacias) {
            dtos.add(new FarmaciaDTO(f));
        }

        return new ResponseEntity<>(dtos, HttpStatus.OK);
    }

    // POST /api/farmacias (Crear)
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<FarmaciaDTO> crear(@Valid @RequestBody Farmacia farmacia) { 

        Farmacia nueva = farmaciaService.crear(farmacia); 
        
        URI uri = ServletUriComponentsBuilder.fromCurrentRequestUri()
                .path("/{id}")
                .buildAndExpand(nueva.getId())
                .toUri();
        
        
        return ResponseEntity.created(uri).body(new FarmaciaDTO(nueva));
    }

    // PUT /api/farmacias/{id} 
    @PutMapping(path = "{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<FarmaciaDTO> modificar(@PathVariable("id") Long id, 
                                                 @Valid @RequestBody Farmacia farmacia) {
        
        if (farmaciaService.buscarPorId(id).isEmpty()) {
            throw new ResourceNotFoundException("Farmacia no encontrada");
        }
        
        farmacia.setId(id); 
        Farmacia modificada = farmaciaService.modificar(farmacia);
        
        return new ResponseEntity<>(new FarmaciaDTO(modificada), HttpStatus.OK);
    }

    // DELETE /api/farmacias/{id} 
    @DeleteMapping(path = "{id}")
    public ResponseEntity<HttpStatus> eliminar(@PathVariable("id") Long id) {
        Optional<Farmacia> farmacia = farmaciaService.buscarPorId(id);
        
        if (farmacia.isEmpty()) {
            throw new ResourceNotFoundException("Farmacia no encontrada");
        }
        
        farmaciaService.eliminar(farmacia.get()); 
        
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}