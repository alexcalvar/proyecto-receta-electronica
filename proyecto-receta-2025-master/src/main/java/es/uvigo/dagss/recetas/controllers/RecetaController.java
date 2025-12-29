package es.uvigo.dagss.recetas.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import es.uvigo.dagss.recetas.controllers.excepciones.ResourceNotFoundException;
import es.uvigo.dagss.recetas.entidades.Receta;
import es.uvigo.dagss.recetas.servicios.RecetaService;

@RestController
@RequestMapping(path = "/api/recetas", produces = MediaType.APPLICATION_JSON_VALUE)
@CrossOrigin(origins = "*")
public class RecetaController {

    @Autowired
    private RecetaService recetaService;

    // GET /api/recetas?tarjeta=TSI-0001
    // (HU-F2) Farmacia busca recetas disponibles por tarjeta sanitaria
    @GetMapping(params = "tarjeta")
    public ResponseEntity<List<Receta>> buscarDisponibles(
            @RequestParam(name = "tarjeta", required = true) String tarjeta) {
        
        List<Receta> recetas = recetaService.buscarDisponibles(tarjeta);
        return new ResponseEntity<>(recetas, HttpStatus.OK);
    }
    
    // GET /api/recetas?pacienteId=5
    // (HU-P4) Paciente ve su historial
    @GetMapping(params = "pacienteId")
    public ResponseEntity<List<Receta>> historialPaciente(
            @RequestParam(name = "pacienteId", required = true) Long pacienteId) {
        
        List<Receta> recetas = recetaService.listarPorPaciente(pacienteId);
        return new ResponseEntity<>(recetas, HttpStatus.OK);
    }

    // GET /api/recetas/{id}
    @GetMapping(path = "{id}")
    public ResponseEntity<Receta> buscarPorId(@PathVariable("id") Long id) {
        Optional<Receta> receta = recetaService.buscarPorId(id);
        if (receta.isEmpty()) {
            throw new ResourceNotFoundException("Receta no encontrada");
        }
        return new ResponseEntity<>(receta.get(), HttpStatus.OK);
    }

    // POST /api/recetas/{id}/dispensar
    // (HU-F3) Acción de Vender/Dispensar la receta
    @PostMapping(path = "{id}/dispensar")
    public ResponseEntity<Void> dispensar(@PathVariable("id") Long id) {
        // Verificamos existencia
        if (recetaService.buscarPorId(id).isEmpty()) {
             throw new ResourceNotFoundException("Receta no encontrada");
        }
        
        // Realizamos la acción
        recetaService.dispensar(id);
        
        // Devolvemos OK (200) o NO_CONTENT (204)
        return new ResponseEntity<>(HttpStatus.OK);
    }
}