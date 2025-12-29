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
import es.uvigo.dagss.recetas.entidades.Medico; // Import necesario
import es.uvigo.dagss.recetas.entidades.Paciente;
import es.uvigo.dagss.recetas.servicios.MedicoService; // Import necesario
import es.uvigo.dagss.recetas.servicios.PacienteService;
import jakarta.validation.Valid;

@RestController
@RequestMapping(path = "/api/pacientes", produces = MediaType.APPLICATION_JSON_VALUE)
@CrossOrigin(origins = "*")
public class PacienteController {

    @Autowired
    private PacienteService pacienteService;

    @Autowired
    private MedicoService medicoService; 

    // GET /api/pacientes (Listar todos)
    @GetMapping
    public ResponseEntity<List<Paciente>> buscarTodos() {
        return new ResponseEntity<>(pacienteService.buscarTodos(), HttpStatus.OK);
    }

    // GET /api/pacientes/{id}
    @GetMapping(path = "{id}")
    public ResponseEntity<Paciente> buscarPorId(@PathVariable("id") Long id) {
        Optional<Paciente> paciente = pacienteService.buscarPorId(id);
        if (paciente.isEmpty()) {
            throw new ResourceNotFoundException("Paciente no encontrado");
        }
        return new ResponseEntity<>(paciente.get(), HttpStatus.OK);
    }

    // GET /api/pacientes?nombre=Ana
    @GetMapping(params = "nombre")
    public ResponseEntity<List<Paciente>> buscarPorNombre(@RequestParam String nombre) {
        return new ResponseEntity<>(pacienteService.buscarPorNombre(nombre), HttpStatus.OK);
    }

    // GET /api/pacientes?tarjeta=TSI-0001
    @GetMapping(params = "tarjeta")
    public ResponseEntity<Paciente> buscarPorTarjeta(@RequestParam String tarjeta) {
        Optional<Paciente> paciente = pacienteService.buscarPorTarjeta(tarjeta);
        if (paciente.isEmpty()) {
            throw new ResourceNotFoundException("Paciente no encontrado con tarjeta: " + tarjeta);
        }
        return new ResponseEntity<>(paciente.get(), HttpStatus.OK);
    }

    // GET /api/pacientes?medicoId=2 (Pacientes de un médico)
    // ESTE MÉTODO TENÍA MUCHOS ERRORES
    @GetMapping(params = "medicoId")
    public ResponseEntity<List<Paciente>> buscarPorMedico(@RequestParam Long medicoId) {
        // 1. Buscamos el Médico primero
        Optional<Medico> medicoOpt = medicoService.buscarPorId(medicoId);
        
        if (medicoOpt.isEmpty()) {
             throw new ResourceNotFoundException("Médico no encontrado con id: " + medicoId);
        }

        Medico med = medicoOpt.get();
        // 2. Usamos el objeto médico para buscar sus pacientes
        // Asumo que tu servicio se llama buscarPorMedico(Medico m)
        return new ResponseEntity<>(pacienteService.buscarPorMedico(med), HttpStatus.OK);
    }

    // POST /api/pacientes
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Paciente> crear(@Valid @RequestBody Paciente paciente) {
        // Asegúrate de que tu servicio usa 'crear' o 'registrar'. Aquí uso 'crear' que es más estándar.
        Paciente nuevo = pacienteService.crear(paciente); 
        URI uri = ServletUriComponentsBuilder.fromCurrentRequestUri().path("/{id}").buildAndExpand(nuevo.getId()).toUri();
        return ResponseEntity.created(uri).body(nuevo);
    }

    // PUT /api/pacientes/{id}
    @PutMapping(path = "{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Paciente> modificar(@PathVariable Long id, @Valid @RequestBody Paciente paciente) {
        if (pacienteService.buscarPorId(id).isEmpty()) {
            throw new ResourceNotFoundException("Paciente no encontrado");
        }
        paciente.setId(id);
        // Asumiendo que el servicio tiene un método modificar
        return new ResponseEntity<>(pacienteService.modificar(paciente), HttpStatus.OK);
    }
    
    // DELETE /api/pacientes/{id}
    @DeleteMapping(path = "{id}")
    public ResponseEntity<HttpStatus> eliminar(@PathVariable Long id) {
        Optional<Paciente> paciente = pacienteService.buscarPorId(id);
        
        if (paciente.isEmpty()) {
            throw new ResourceNotFoundException("Paciente no encontrado");
        }
        
        // Dependiendo de tu servicio, puede pedir el ID o el objeto entero.
        // Si pide objeto: pacienteService.eliminar(paciente.get());
        // Si pide ID:
        Paciente pac = paciente.get();
        pacienteService.eliminar(pac); 
        
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}