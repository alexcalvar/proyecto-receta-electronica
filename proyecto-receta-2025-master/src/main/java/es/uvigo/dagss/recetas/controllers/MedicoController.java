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


    @GetMapping
    public ResponseEntity<List<Medico>> buscarTodos() {
        return new ResponseEntity<>(medicoService.buscarTodos(), HttpStatus.OK);
    }

    @GetMapping(path = "{id}")
    public ResponseEntity<Medico> buscarPorId(@PathVariable("id") Long id) {
        Optional<Medico> medico = medicoService.buscarPorId(id); // Asegúrate de tener este método en el servicio o DAO
        if (medico.isEmpty()) {
            throw new ResourceNotFoundException("Médico no encontrado");
        }
        return new ResponseEntity<>(medico.get(), HttpStatus.OK);
    }

    // GET /api/medicos?centroId=1
    @GetMapping(params = "centroId")
    public ResponseEntity<List<Medico>> buscarPorCentro(@RequestParam Long centroId) {
        Optional<CentroDeSalud> centroDeSalud = cdsService.buscarPorId(centroId);
        CentroDeSalud cds = centroDeSalud.get();
        List<Medico> centro = medicoService.buscarPorCds(cds);
        return new ResponseEntity<>(centro, HttpStatus.OK);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Medico> crear(@Valid @RequestBody Medico medico) {
        Medico nuevo = medicoService.crear(medico);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequestUri().path("/{id}").buildAndExpand(nuevo.getId()).toUri();
        return ResponseEntity.created(uri).body(nuevo);
    }
    
    // Puedes añadir PUT y DELETE similar a Paciente si lo necesitas
}