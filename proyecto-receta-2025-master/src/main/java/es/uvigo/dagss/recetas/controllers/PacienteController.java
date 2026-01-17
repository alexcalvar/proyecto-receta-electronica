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
import es.uvigo.dagss.recetas.dtos.PacienteDTO; 
import es.uvigo.dagss.recetas.entidades.Medico;
import es.uvigo.dagss.recetas.entidades.Paciente;
import es.uvigo.dagss.recetas.servicios.MedicoService;
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
    public ResponseEntity<List<PacienteDTO>> buscarTodos() { 
        List<Paciente> pacientes = pacienteService.buscarTodos();
        
        
        List<PacienteDTO> dtos = new ArrayList<>();
        for (Paciente p : pacientes) {
            dtos.add(new PacienteDTO(p));
        }

        return new ResponseEntity<>(dtos, HttpStatus.OK);
    }

    // GET /api/pacientes/{id}
    @GetMapping(path = "{id}")
    public ResponseEntity<PacienteDTO> buscarPorId(@PathVariable("id") Long id) { 
        Optional<Paciente> paciente = pacienteService.buscarPorId(id);
        
        if (paciente.isEmpty()) {
            throw new ResourceNotFoundException("Paciente no encontrado");
        }
        
        return new ResponseEntity<>(new PacienteDTO(paciente.get()), HttpStatus.OK);
    }

    // GET /api/pacientes?nombre=Ana
    @GetMapping(params = "nombre")
    public ResponseEntity<List<PacienteDTO>> buscarPorNombre(@RequestParam String nombre) { 
        List<Paciente> pacientes = pacienteService.buscarPorNombre(nombre);
        

        List<PacienteDTO> dtos = new ArrayList<>();
        for (Paciente p : pacientes) {
            dtos.add(new PacienteDTO(p));
        }

        return new ResponseEntity<>(dtos, HttpStatus.OK);
    }

    // GET /api/pacientes?tarjeta=TSI-0001
    @GetMapping(params = "tarjeta")
    public ResponseEntity<PacienteDTO> buscarPorTarjeta(@RequestParam String tarjeta) { 
        Optional<Paciente> paciente = pacienteService.buscarPorTarjeta(tarjeta);
        
        if (paciente.isEmpty()) {
            throw new ResourceNotFoundException("Paciente no encontrado con tarjeta: " + tarjeta);
        }
        
        return new ResponseEntity<>(new PacienteDTO(paciente.get()), HttpStatus.OK);
    }

    // GET /api/pacientes?medicoId=2 
    @GetMapping(params = "medicoId")
    public ResponseEntity<List<PacienteDTO>> buscarPorMedico(@RequestParam Long medicoId) { 
        
        Optional<Medico> medicoOpt = medicoService.buscarPorId(medicoId);
        
        if (medicoOpt.isEmpty()) {
             throw new ResourceNotFoundException("Médico no encontrado con id: " + medicoId);
        }

        
        List<Paciente> pacientes = pacienteService.buscarPorMedico(medicoOpt.get());

        List<PacienteDTO> dtos = new ArrayList<>();
        for (Paciente p : pacientes) {
            dtos.add(new PacienteDTO(p));
        }
        
        return new ResponseEntity<>(dtos, HttpStatus.OK);
    }

    // POST /api/pacientes
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<PacienteDTO> crear(@Valid @RequestBody Paciente paciente) { 
        Paciente nuevo = pacienteService.crear(paciente); 
        
        URI uri = ServletUriComponentsBuilder.fromCurrentRequestUri()
                .path("/{id}")
                .buildAndExpand(nuevo.getId())
                .toUri();
        
        return ResponseEntity.created(uri).body(new PacienteDTO(nuevo));
    }

    // PUT /api/pacientes/{id}
    @PutMapping(path = "{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<PacienteDTO> modificar(@PathVariable Long id, 
                                                 @Valid @RequestBody Paciente paciente) { 
        if (pacienteService.buscarPorId(id).isEmpty()) {
            throw new ResourceNotFoundException("Paciente no encontrado");
        }
        
        paciente.setId(id);
        Paciente modificado = pacienteService.modificar(paciente);
        
        return new ResponseEntity<>(new PacienteDTO(modificado), HttpStatus.OK);
    }
    
    // DELETE /api/pacientes/{id}
    @DeleteMapping(path = "{id}")
    public ResponseEntity<HttpStatus> eliminar(@PathVariable Long id) {
        Optional<Paciente> paciente = pacienteService.buscarPorId(id);
        
        if (paciente.isEmpty()) {
            throw new ResourceNotFoundException("Paciente no encontrado");
        }
        
        pacienteService.eliminar(paciente.get()); 
        
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}