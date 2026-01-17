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
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import es.uvigo.dagss.recetas.controllers.excepciones.ResourceNotFoundException;
import es.uvigo.dagss.recetas.dtos.CitaDTO; 
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
    //  Calcular huecos libres
    @GetMapping(path = "/huecos")
    public ResponseEntity<List<LocalTime>> obtenerHuecosDisponibles(
            @RequestParam(name = "medicoId") Long medicoId,
            @RequestParam(name = "fecha") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fecha) {
        
        Optional<Medico> medicoOpt = medicoService.buscarPorId(medicoId);
        if (medicoOpt.isEmpty()) {
            throw new ResourceNotFoundException("Médico no encontrado con ID: " + medicoId);
        }

        List<LocalTime> huecos = citaService.obtenerHuecosDisponibles(medicoOpt.get(), fecha);
        
        return new ResponseEntity<>(huecos, HttpStatus.OK);
    }

    // GET /api/citas/{id}
    @GetMapping(path = "{id}")
    public ResponseEntity<CitaDTO> buscarPorId(@PathVariable("id") Long id) {
        Optional<Cita> cita = citaService.buscarPorId(id);
        
        if (cita.isEmpty()) {
            throw new ResourceNotFoundException("Cita no encontrada");
        }
        
        return new ResponseEntity<>(new CitaDTO(cita.get()), HttpStatus.OK);
    }

    // POST /api/citas (Crear Cita)
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CitaDTO> crear(@Valid @RequestBody Cita cita) {
        
        Cita nuevaCita = citaService.crear(cita);
        
        
        Cita citaCompleta = citaService.buscarPorId(nuevaCita.getId()).get();

        URI uri = crearURICita(citaCompleta);
        
        
        return ResponseEntity.created(uri).body(new CitaDTO(citaCompleta));
    }

    // PATCH /api/citas/{id}?estado=REALIZADA
    @PatchMapping(path = "{id}")
    public ResponseEntity<CitaDTO> cambiarEstado(@PathVariable("id") Long id, 
                                                 @RequestParam("estado") String estado) {
        
        Optional<Cita> citaOptional = citaService.buscarPorId(id);

        if (citaOptional.isEmpty()) {
            throw new ResourceNotFoundException("Cita no encontrada");
        }

        // convertir el string  al enum correspondiente

        try {
            citaService.modificar(id, es.uvigo.dagss.recetas.utils.EstadoCita.valueOf(estado));
        } catch (IllegalArgumentException e) {
            throw new RuntimeException("Estado no válido. Valores posibles: COMPLETADA, ANULADA, REALIZADA...");
        }

        Optional<Cita> cita = citaService.buscarPorId(id);


        // se devuelve el dto actualizado
        return new ResponseEntity<>(new CitaDTO(cita.get()), HttpStatus.OK);
    }

    private URI crearURICita(Cita cita) {
        return ServletUriComponentsBuilder.fromCurrentRequestUri()
                .path("/{id}")
                .buildAndExpand(cita.getId())
                .toUri();
    }
}