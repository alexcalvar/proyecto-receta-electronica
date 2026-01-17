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
import es.uvigo.dagss.recetas.dtos.CentroDeSaludDTO; 
import es.uvigo.dagss.recetas.entidades.CentroDeSalud;
import es.uvigo.dagss.recetas.servicios.CentroDeSaludService;
import jakarta.validation.Valid;

@RestController
@RequestMapping(path = "/api/centros", produces = MediaType.APPLICATION_JSON_VALUE)
@CrossOrigin(origins = "*")
public class CentroDeSaludController {

    @Autowired
    private CentroDeSaludService centroService;

    // GET /api/centros
    @GetMapping
    public ResponseEntity<List<CentroDeSaludDTO>> buscarTodos() { // <--- Devuelve DTOs
        List<CentroDeSalud> centros = centroService.buscarTodos();
        
        // Conversión a DTOs usando bucle FOR
        List<CentroDeSaludDTO> dtos = new ArrayList<>();
        for (CentroDeSalud c : centros) {
            dtos.add(new CentroDeSaludDTO(c));
        }
        
        return new ResponseEntity<>(dtos, HttpStatus.OK);
    }

    // GET /api/centros/{id}
    @GetMapping(path = "{id}")
    public ResponseEntity<CentroDeSaludDTO> buscarPorId(@PathVariable("id") Long id) { // <--- Devuelve DTO
        Optional<CentroDeSalud> centro = centroService.buscarPorId(id);
        
        if (centro.isEmpty()) {
            throw new ResourceNotFoundException("Centro de salud no encontrado");
        }
        
        return new ResponseEntity<>(new CentroDeSaludDTO(centro.get()), HttpStatus.OK);
    }

    // GET /api/centros?localidad=Vigo
    @GetMapping(params = "localidad")
    public ResponseEntity<List<CentroDeSaludDTO>> buscarPorLocalidad(@RequestParam String localidad) { // <--- Devuelve DTOs
        List<CentroDeSalud> centros = centroService.buscarPorLocalidad(localidad);
        
        // Conversión a DTOs
        List<CentroDeSaludDTO> dtos = new ArrayList<>();
        for (CentroDeSalud c : centros) {
            dtos.add(new CentroDeSaludDTO(c));
        }

        return new ResponseEntity<>(dtos, HttpStatus.OK);
    }

    // POST /api/centros
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CentroDeSaludDTO> crear(@Valid @RequestBody CentroDeSalud centro) { // <--- Devuelve DTO
        CentroDeSalud nuevo = centroService.crear(centro);
        
        URI uri = ServletUriComponentsBuilder.fromCurrentRequestUri()
                .path("/{id}")
                .buildAndExpand(nuevo.getId())
                .toUri();
        
        return ResponseEntity.created(uri).body(new CentroDeSaludDTO(nuevo));
    }

    // PUT /api/centros/{id} (IMPLEMENTADO)
    @PutMapping(path = "{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CentroDeSaludDTO> modificar(@PathVariable("id") Long id, 
                                                    @Valid @RequestBody CentroDeSalud centro) {
        
        // 1. Verificamos que existe antes de tocar nada
        Optional<CentroDeSalud> existente = centroService.buscarPorId(id);
        if (existente.isEmpty()) {
             throw new ResourceNotFoundException("Centro de salud no encontrado");
        }
        
        // 2. Asignamos el ID de la URL al objeto para asegurar que modificamos el correcto
        centro.setId(id);
        
        // 3. Llamamos al servicio
        CentroDeSalud modificado = centroService.modificar(centro);
        
        // 4. Devolvemos el DTO
        return new ResponseEntity<>(new CentroDeSaludDTO(modificado), HttpStatus.OK);
    }

    // DELETE /api/centros/{id} (IMPLEMENTADO)
    @DeleteMapping(path = "{id}")
    public ResponseEntity<HttpStatus> eliminar(@PathVariable("id") Long id) {
        Optional<CentroDeSalud> centro = centroService.buscarPorId(id);
        
        if (centro.isEmpty()) {
            throw new ResourceNotFoundException("Centro de salud no encontrado");
        }
        
        // Asumiendo que tu servicio tiene un método eliminar que acepta la entidad o el ID
        centroService.eliminar(centro.get());
        
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}