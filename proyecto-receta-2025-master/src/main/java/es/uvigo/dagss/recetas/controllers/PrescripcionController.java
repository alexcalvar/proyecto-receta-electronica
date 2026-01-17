package es.uvigo.dagss.recetas.controllers;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import es.uvigo.dagss.recetas.controllers.excepciones.ResourceNotFoundException;
import es.uvigo.dagss.recetas.dtos.PrescripcionDTO; 
import es.uvigo.dagss.recetas.entidades.Prescripcion;
import es.uvigo.dagss.recetas.servicios.PrescripcionService;
import jakarta.validation.Valid;

@RestController
@RequestMapping(path = "/api/prescripciones", produces = MediaType.APPLICATION_JSON_VALUE)
@CrossOrigin(origins = "*")
public class PrescripcionController {

    @Autowired
    PrescripcionService prescripcionService;

    // GET /api/prescripciones/{id}
    @GetMapping(path = "{id}")
    public ResponseEntity<PrescripcionDTO> buscarPorId(@PathVariable("id") Long id) { 
        Optional<Prescripcion> prescripcion = prescripcionService.buscarPorId(id);

        if (prescripcion.isEmpty()) {
            throw new ResourceNotFoundException("Prescripcion no encontrada");
        } 
        
        
        PrescripcionDTO dto = new PrescripcionDTO(prescripcion.get());
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }

    // GET /api/prescripciones?pacienteId=5 
    @RequestMapping(params = "pacienteId", method = RequestMethod.GET)
    public ResponseEntity<List<PrescripcionDTO>> buscarHistorialPorPaciente( 
            @RequestParam(name = "pacienteId", required = true) Long pacienteId) {
        
        
        List<Prescripcion> entidades = prescripcionService.buscarHistorialPorPaciente(pacienteId);
        
       
        List<PrescripcionDTO> dtos = new ArrayList<>();
        for (Prescripcion p : entidades) {
            dtos.add(new PrescripcionDTO(p));
        }

        return new ResponseEntity<>(dtos, HttpStatus.OK);
    }

    // GET /api/prescripciones?pacienteId=5&soloActivas=true 
    @RequestMapping(params = {"pacienteId", "soloActivas"}, method = RequestMethod.GET)
    public ResponseEntity<List<PrescripcionDTO>> buscarVigentesPorPaciente( 
            @RequestParam(name = "pacienteId", required = true) Long pacienteId,
            @RequestParam(name = "soloActivas", required = true) Boolean soloActivas) {
        
        List<Prescripcion> entidades;
        
   
        if (Boolean.TRUE.equals(soloActivas)) {
            entidades = prescripcionService.buscarVigentesPorPaciente(pacienteId);
        } else {
            entidades = prescripcionService.buscarHistorialPorPaciente(pacienteId);
        }
    
        List<PrescripcionDTO> dtos = new ArrayList<>();
        for (Prescripcion p : entidades) {
            dtos.add(new PrescripcionDTO(p));
        }

        return new ResponseEntity<>(dtos, HttpStatus.OK);
    }

    // POST /api/prescripciones
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<PrescripcionDTO> crear(@Valid @RequestBody Prescripcion prescripcion) { 
        
        
        Prescripcion nuevaPrescripcion = prescripcionService.crear(prescripcion);
        
        URI uri = crearURIPrescripcion(nuevaPrescripcion);

      
        PrescripcionDTO dto = new PrescripcionDTO(nuevaPrescripcion);

        return ResponseEntity.created(uri).body(dto);
    }

    // DELETE /api/prescripciones/{id}

    @DeleteMapping(path = "{id}")
    public ResponseEntity<HttpStatus> eliminar(@PathVariable("id") Long id) {
        Optional<Prescripcion> prescripcion = prescripcionService.buscarPorId(id);

        if (prescripcion.isEmpty()) {
            throw new ResourceNotFoundException("Prescripcion no encontrada");
        } else {
            prescripcionService.eliminar(prescripcion.get());
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
    }

    private URI crearURIPrescripcion(Prescripcion prescripcion) {
        return ServletUriComponentsBuilder.fromCurrentRequestUri().path("/{id}")
                .buildAndExpand(prescripcion.getId())
                .toUri();
    }
}