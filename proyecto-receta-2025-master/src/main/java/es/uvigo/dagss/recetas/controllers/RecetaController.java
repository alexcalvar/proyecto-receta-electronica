package es.uvigo.dagss.recetas.controllers;

import java.util.ArrayList; 
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import es.uvigo.dagss.recetas.controllers.excepciones.ResourceNotFoundException;
import es.uvigo.dagss.recetas.dtos.RecetaDTO;
import es.uvigo.dagss.recetas.entidades.Receta;
import es.uvigo.dagss.recetas.servicios.RecetaService;

@RestController
@RequestMapping(path = "/api/recetas", produces = MediaType.APPLICATION_JSON_VALUE)
@CrossOrigin(origins = "*")
public class RecetaController {

    @Autowired
    private RecetaService recetaService;

    // GET /api/recetas?tarjeta=TSI-0001
    @GetMapping(params = "tarjeta")
    public ResponseEntity<List<RecetaDTO>> buscarDisponibles(
            @RequestParam(name = "tarjeta", required = true) String tarjeta) {
        
        
        List<Receta> entidades = recetaService.buscarDisponibles(tarjeta);
        
        
        List<RecetaDTO> listaDtos = new ArrayList<>(); 
        
        for (Receta recetaEntidad : entidades) {       
            RecetaDTO dto = new RecetaDTO(recetaEntidad); 
            listaDtos.add(dto);                       
        }

  
        return new ResponseEntity<>(listaDtos, HttpStatus.OK);
    }
    
    // GET /api/recetas?pacienteId=5
    @GetMapping(params = "pacienteId")
    public ResponseEntity<List<RecetaDTO>> historialPaciente(
            @RequestParam(name = "pacienteId", required = true) Long pacienteId) {
        
        List<Receta> entidades = recetaService.listarPorPaciente(pacienteId);
        
     
        List<RecetaDTO> listaDtos = new ArrayList<>();
        
        for (Receta recetaEntidad : entidades) {
            listaDtos.add(new RecetaDTO(recetaEntidad));
        }

        return new ResponseEntity<>(listaDtos, HttpStatus.OK);
    }

    // GET /api/recetas/{id} 
    @GetMapping(path = "{id}")
    public ResponseEntity<RecetaDTO> buscarPorId(@PathVariable("id") Long id) {
        Optional<Receta> receta = recetaService.buscarPorId(id);
        
        if (receta.isEmpty()) {
            throw new ResourceNotFoundException("Receta no encontrada");
        }
        
        return new ResponseEntity<>(new RecetaDTO(receta.get()), HttpStatus.OK);
    }

    // POST /api/recetas/{id}/dispensar 
    @PostMapping(path = "{id}/dispensar")
    public ResponseEntity<Void> dispensar(@PathVariable("id") Long id) {
        if (recetaService.buscarPorId(id).isEmpty()) {
             throw new ResourceNotFoundException("Receta no encontrada");
        }
        recetaService.dispensar(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}