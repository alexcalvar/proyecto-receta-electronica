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
    public ResponseEntity<Prescripcion> buscarPorId(@PathVariable("id") Long id) {
        Optional<Prescripcion> prescripcion = prescripcionService.buscarPorId(id);

        if (prescripcion.isEmpty()) {
            throw new ResourceNotFoundException("Prescripcion no encontrada");
        } else {
            return new ResponseEntity<>(prescripcion.get(), HttpStatus.OK);
        }
    }

    // GET /api/prescripciones?pacienteId=5 (HISTORIAL COMPLETO)
    // Coincide con el estilo de buscarPorArticuloId del ejemplo
    @RequestMapping(params = "pacienteId", method = RequestMethod.GET)
    public ResponseEntity<List<Prescripcion>> buscarHistorialPorPaciente(
            @RequestParam(name = "pacienteId", required = true) Long pacienteId) {
        
        List<Prescripcion> resultado = new ArrayList<>();
        resultado = prescripcionService.buscarHistorialPorPaciente(pacienteId);
        return new ResponseEntity<>(resultado, HttpStatus.OK);
    }

    // GET /api/prescripciones?pacienteId=5&soloActivas=true (SOLO VIGENTES)
    // Sobrecarga de params para filtrar solo las activas
    @RequestMapping(params = {"pacienteId", "soloActivas"}, method = RequestMethod.GET)
    public ResponseEntity<List<Prescripcion>> buscarVigentesPorPaciente(
            @RequestParam(name = "pacienteId", required = true) Long pacienteId,
            @RequestParam(name = "soloActivas", required = true) Boolean soloActivas) {
        
        List<Prescripcion> resultado = new ArrayList<>();
        if (Boolean.TRUE.equals(soloActivas)) {
            resultado = prescripcionService.buscarVigentesPorPaciente(pacienteId);
        } else {
            // Si pone false, devolvemos el historial completo
            resultado = prescripcionService.buscarHistorialPorPaciente(pacienteId);
        }
        return new ResponseEntity<>(resultado, HttpStatus.OK);
    }

    // POST /api/prescripciones (Crear y generar recetas)
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Prescripcion> crear(@Valid @RequestBody Prescripcion prescripcion) {
        // Al llamar a crear del servicio, se generan las recetas automáticamente (Lógica implementada antes)
        Prescripcion nuevaPrescripcion = prescripcionService.crear(prescripcion);
        URI uri = crearURIPrescripcion(nuevaPrescripcion);

        return ResponseEntity.created(uri).body(nuevaPrescripcion);
    }

    // DELETE /api/prescripciones/{id}
    @DeleteMapping(path = "{id}")
    public ResponseEntity<HttpStatus> eliminar(@PathVariable("id") Long id) {
        Optional<Prescripcion> prescripcion = prescripcionService.buscarPorId(id);

        if (prescripcion.isEmpty()) {
            throw new ResourceNotFoundException("Prescripcion no encontrada");
        } else {
            prescripcionService.eliminar(prescripcion.get()); // O eliminar(id)
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
    }

    // Construye la URI del nuevo recurso
    private URI crearURIPrescripcion(Prescripcion prescripcion) {
        return ServletUriComponentsBuilder.fromCurrentRequestUri().path("/{id}").buildAndExpand(prescripcion.getId())
                .toUri();
    }
}