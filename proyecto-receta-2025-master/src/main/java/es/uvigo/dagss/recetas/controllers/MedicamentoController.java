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
import es.uvigo.dagss.recetas.dtos.MedicamentoDTO; 
import es.uvigo.dagss.recetas.entidades.Medicamento;
import es.uvigo.dagss.recetas.servicios.MedicamentoService;
import jakarta.validation.Valid;

@RestController
@RequestMapping(path = "/api/medicamentos", produces = MediaType.APPLICATION_JSON_VALUE)
@CrossOrigin(origins = "*")
public class MedicamentoController {

    @Autowired
    MedicamentoService medicamentoService;

    // GET /api/medicamentos 
    @GetMapping()
    public ResponseEntity<List<MedicamentoDTO>> buscarTodos() { 
        List<Medicamento> medicamentos = medicamentoService.buscarTodos();
        
        
        List<MedicamentoDTO> dtos = new ArrayList<>();
        for (Medicamento m : medicamentos) {
            dtos.add(new MedicamentoDTO(m));
        }
        
        return new ResponseEntity<>(dtos, HttpStatus.OK);
    }

    // GET /api/medicamentos?busqueda=ibuprofeno
    @RequestMapping(params = "busqueda", method = RequestMethod.GET)
    public ResponseEntity<List<MedicamentoDTO>> buscarPorTexto( 
            @RequestParam(name = "busqueda", required = true) String busqueda) {
        
        List<Medicamento> medicamentos = medicamentoService.filtrarPorNombreComercial(busqueda);
        
   
        List<MedicamentoDTO> dtos = new ArrayList<>();
        for (Medicamento m : medicamentos) {
            dtos.add(new MedicamentoDTO(m));
        }

        return new ResponseEntity<>(dtos, HttpStatus.OK);
    }

    // GET /api/medicamentos/{id}
    @GetMapping(path = "{id}")
    public ResponseEntity<MedicamentoDTO> buscarPorId(@PathVariable("id") Long id) { 
        Optional<Medicamento> medicamento = medicamentoService.buscarPorId(id);

        if (medicamento.isEmpty()) {
            throw new ResourceNotFoundException("Medicamento no encontrado");
        }
        
        return new ResponseEntity<>(new MedicamentoDTO(medicamento.get()), HttpStatus.OK);
    }

    // POST /api/medicamentos 
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<MedicamentoDTO> crear(@Valid @RequestBody Medicamento medicamento) {
        
        Medicamento nuevoMedicamento = medicamentoService.crear(medicamento); 
        
        URI uri = crearURIMedicamento(nuevoMedicamento);

        return ResponseEntity.created(uri).body(new MedicamentoDTO(nuevoMedicamento));
    }

    // PUT /api/medicamentos/{id} 
    @PutMapping(path = "{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<MedicamentoDTO> modificar(@PathVariable("id") Long id, 
                                                    @Valid @RequestBody Medicamento medicamento) { 
        
        Optional<Medicamento> medicamentoOptional = medicamentoService.buscarPorId(id);

        if (medicamentoOptional.isEmpty()) {
            throw new ResourceNotFoundException("Medicamento no encontrado");
        }
        
        medicamento.setId(id);
        Medicamento medicamentoModificado = medicamentoService.modificar(medicamento);
        
        return new ResponseEntity<>(new MedicamentoDTO(medicamentoModificado), HttpStatus.OK);
    }

    // DELETE /api/medicamentos/{id}
    @DeleteMapping(path = "{id}")
    public ResponseEntity<HttpStatus> eliminar(@PathVariable("id") Long id) {
        Optional<Medicamento> medicamento = medicamentoService.buscarPorId(id);

        if (medicamento.isEmpty()) {
            throw new ResourceNotFoundException("Medicamento no encontrado");
        }
        
        medicamentoService.eliminar(medicamento.get());
        
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    private URI crearURIMedicamento(Medicamento medicamento) {
        return ServletUriComponentsBuilder.fromCurrentRequestUri()
                .path("/{id}")
                .buildAndExpand(medicamento.getId())
                .toUri();
    }
}