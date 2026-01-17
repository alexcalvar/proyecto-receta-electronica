package es.uvigo.dagss.recetas.controllers;

import java.net.URI;
import java.util.ArrayList; // <--- Necesario para listas
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import es.uvigo.dagss.recetas.controllers.excepciones.ResourceNotFoundException;
import es.uvigo.dagss.recetas.dtos.MedicoDTO; // <--- Importamos el DTO
import es.uvigo.dagss.recetas.entidades.CentroDeSalud;
import es.uvigo.dagss.recetas.entidades.Medico;
import es.uvigo.dagss.recetas.servicios.CentroDeSaludService;
import es.uvigo.dagss.recetas.servicios.MedicoService;
import jakarta.validation.Valid;

@RestController
@RequestMapping(path = "/api/medicos", produces = MediaType.APPLICATION_JSON_VALUE)
@CrossOrigin(origins = "*")
public class MedicoController {

    @Autowired
    private MedicoService medicoService;

    @Autowired
    private CentroDeSaludService cdsService;

    // GET /api/medicos (Listar todos)
    @GetMapping
    public ResponseEntity<List<MedicoDTO>> buscarTodos() { // <--- Retorna DTOs
        List<Medico> medicos = medicoService.buscarTodos();
        
        // Conversión con bucle FOR
        List<MedicoDTO> dtos = new ArrayList<>();
        for (Medico m : medicos) {
            dtos.add(new MedicoDTO(m));
        }

        return new ResponseEntity<>(dtos, HttpStatus.OK);
    }

    // GET /api/medicos/{id}
    @GetMapping(path = "{id}")
    public ResponseEntity<MedicoDTO> buscarPorId(@PathVariable("id") Long id) { // <--- Retorna DTO
        Optional<Medico> medico = medicoService.buscarPorId(id);
        
        if (medico.isEmpty()) {
            throw new ResourceNotFoundException("Médico no encontrado");
        }
        
        return new ResponseEntity<>(new MedicoDTO(medico.get()), HttpStatus.OK);
    }

    // GET /api/medicos?centroId=1
    @GetMapping(params = "centroId")
    public ResponseEntity<List<MedicoDTO>> buscarPorCentro(@RequestParam Long centroId) { // <--- Retorna DTOs
        // 1. Verificamos que el Centro existe (Corrección de seguridad)
        Optional<CentroDeSalud> centroDeSalud = cdsService.buscarPorId(centroId);
        
        if (centroDeSalud.isEmpty()) {
            throw new ResourceNotFoundException("Centro de salud no encontrado con ID: " + centroId);
        }

        // 2. Buscamos los médicos
        List<Medico> medicos = medicoService.buscarPorCds(centroDeSalud.get());
        
        // 3. Convertimos a DTOs
        List<MedicoDTO> dtos = new ArrayList<>();
        for (Medico m : medicos) {
            dtos.add(new MedicoDTO(m));
        }

        return new ResponseEntity<>(dtos, HttpStatus.OK);
    }

    // POST /api/medicos (Crear)
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<MedicoDTO> crear(@Valid @RequestBody Medico medico) { // <--- Retorna DTO
        // Aquí recibimos la entidad con el password en claro
        Medico nuevo = medicoService.crear(medico);
        
        URI uri = ServletUriComponentsBuilder.fromCurrentRequestUri()
                .path("/{id}")
                .buildAndExpand(nuevo.getId())
                .toUri();
        
        // Pero devolvemos el DTO (password oculta)
        return ResponseEntity.created(uri).body(new MedicoDTO(nuevo));
    }

    // PUT /api/medicos/{id} (Modificar - Añadido)
    @PutMapping(path = "{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<MedicoDTO> modificar(@PathVariable("id") Long id, 
                                               @Valid @RequestBody Medico medico) {
        
        if (medicoService.buscarPorId(id).isEmpty()) {
            throw new ResourceNotFoundException("Médico no encontrado");
        }
        
        medico.setId(id);
        Medico modificado = medicoService.modificar(medico);
        
        return new ResponseEntity<>(new MedicoDTO(modificado), HttpStatus.OK);
    }

    // DELETE /api/medicos/{id} (Eliminar - Añadido)
    @DeleteMapping(path = "{id}")
    public ResponseEntity<HttpStatus> eliminar(@PathVariable("id") Long id) {
        Optional<Medico> medico = medicoService.buscarPorId(id);
        
        if (medico.isEmpty()) {
            throw new ResourceNotFoundException("Médico no encontrado");
        }
        
        medicoService.eliminar(medico.get());
        
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}