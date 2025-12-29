package es.uvigo.dagss.recetas.controllers;

import java.net.URI;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import es.uvigo.dagss.recetas.controllers.excepciones.ResourceNotFoundException;
import es.uvigo.dagss.recetas.entidades.Cita;
import es.uvigo.dagss.recetas.entidades.Medico;
import es.uvigo.dagss.recetas.servicios.CitaService;
import es.uvigo.dagss.recetas.servicios.MedicoService;
import jakarta.validation.Valid;

@RestController
@RequestMapping(path = "/api/citas", produces = MediaType.APPLICATION_JSON_VALUE)
@CrossOrigin(origins = "*")
public class CitaController {

    @Autowired
    private CitaService citaService;
    
    @Autowired
    private MedicoService medicoService;

    // GET /api/citas/huecos?medicoId=1&fecha=2023-12-01
    // Endpoint ESPECIAL para calcular huecos libres (Algoritmo del Donut)
    @GetMapping(path = "/huecos")
    public ResponseEntity<List<LocalTime>> obtenerHuecosDisponibles(
            @RequestParam(name = "medicoId") Long medicoId,
            @RequestParam(name = "fecha") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fecha) {
        
        // Primero verificamos que el médico existe
        Optional<Medico> medicoOpt = medicoService.buscarPorId(medicoId);
        if (medicoOpt.isEmpty()) {
            throw new ResourceNotFoundException("Médico no encontrado con ID: " + medicoId);
        }

        // Llamamos al servicio que calcula los huecos
        // (Nota: Asegúrate de que tu servicio CitaService use LocalDate en vez de Date como corregimos antes)
        List<LocalTime> huecos = citaService.obtenerHuecosDisponibles(medicoOpt.get(), fecha);
        
        return new ResponseEntity<>(huecos, HttpStatus.OK);
    }

    // POST /api/citas (Crear Cita)
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Cita> crear(@Valid @RequestBody Cita cita) {
        Cita nuevaCita = citaService.crear(cita);
        URI uri = crearURICita(nuevaCita);
        return ResponseEntity.created(uri).body(nuevaCita);
    }

    // DELETE /api/citas/{id} (Anular Cita)
    @DeleteMapping(path = "{id}")
    public ResponseEntity<HttpStatus> anular(@PathVariable("id") Long id) {
        // Verificamos si existe antes de intentar anular
        // (Si tu servicio anularCita ya lanza excepción, puedes quitar el if)
        citaService.anularCita(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    private URI crearURICita(Cita cita) {
        return ServletUriComponentsBuilder.fromCurrentRequestUri().path("/{id}").buildAndExpand(cita.getId()).toUri();
    }
}