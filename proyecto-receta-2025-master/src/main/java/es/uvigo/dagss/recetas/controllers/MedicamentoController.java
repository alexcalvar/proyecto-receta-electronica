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
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

// Asegúrate de tener estas excepciones creadas o importarlas
import es.uvigo.dagss.recetas.controllers.excepciones.ResourceNotFoundException;
import es.uvigo.dagss.recetas.entidades.Medicamento;
import es.uvigo.dagss.recetas.servicios.MedicamentoService;
import jakarta.validation.Valid;

@RestController
@RequestMapping(path = "/api/medicamentos", produces = MediaType.APPLICATION_JSON_VALUE)
@CrossOrigin(origins = "*")
public class MedicamentoController {

    @Autowired
    MedicamentoService medicamentoService;

    // GET /api/medicamentos (Listar todos)
    @GetMapping()
    public ResponseEntity<List<Medicamento>> buscarTodos() {
        List<Medicamento> resultado = new ArrayList<>();
        // Asumiendo que añadiste buscarTodos() en tu servicio, si no, usa buscarMedicamentos("")
        resultado = medicamentoService.buscarTodos(); 
        return new ResponseEntity<>(resultado, HttpStatus.OK);
    }

    // GET /api/medicamentos?busqueda=ibuprofeno
    @RequestMapping(params = "busqueda", method = RequestMethod.GET)
    public ResponseEntity<List<Medicamento>> buscarPorTexto(
            @RequestParam(name = "busqueda", required = true) String busqueda) {
        List<Medicamento> resultado = new ArrayList<>();
        resultado = medicamentoService.filtrarPorNombreComercial(busqueda);
        return new ResponseEntity<>(resultado, HttpStatus.OK);
    }

    // GET /api/medicamentos/{id}
    @GetMapping(path = "{id}")
    public ResponseEntity<Medicamento> buscarPorId(@PathVariable("id") Long id) {
        Optional<Medicamento> medicamento = medicamentoService.buscarPorId(id);

        if (medicamento.isEmpty()) {
            throw new ResourceNotFoundException("Medicamento no encontrado");
        } else {
            return new ResponseEntity<>(medicamento.get(), HttpStatus.OK);
        }
    }

    // POST /api/medicamentos (Crear)
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Medicamento> crear(@Valid @RequestBody Medicamento medicamento) {
        Medicamento nuevoMedicamento = medicamentoService.modificar(medicamento);
        URI uri = crearURIMedicamento(nuevoMedicamento);

        return ResponseEntity.created(uri).body(nuevoMedicamento);
    }

    // PUT /api/medicamentos/{id} (Modificar)
    @PutMapping(path = "{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Medicamento> modificar(@PathVariable("id") Long id, @Valid @RequestBody Medicamento medicamento) {
        Optional<Medicamento> medicamentoOptional = medicamentoService.buscarPorId(id);

        if (medicamentoOptional.isEmpty()) {
            throw new ResourceNotFoundException("Medicamento no encontrado");
        } else {
            // Aseguramos que el ID del cuerpo coincide con el de la URL
            medicamento.setId(id);
            Medicamento medicamentoModificado = medicamentoService.modificar(medicamento);
            return new ResponseEntity<>(medicamentoModificado, HttpStatus.OK);
        }
    }

    // DELETE /api/medicamentos/{id}
    @DeleteMapping(path = "{id}")
    public ResponseEntity<HttpStatus> eliminar(@PathVariable("id") Long id) {
        Optional<Medicamento> medicamento = medicamentoService.buscarPorId(id);

        if (medicamento.isEmpty()) {
            throw new ResourceNotFoundException("Medicamento no encontrado");
        } else {
            Medicamento med = medicamento.get();
            medicamentoService.eliminar(med); // O eliminar(medicamento.get()) según tu servicio
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
    }

    // Construye la URI del nuevo recurso
    private URI crearURIMedicamento(Medicamento medicamento) {
        return ServletUriComponentsBuilder.fromCurrentRequestUri().path("/{id}").buildAndExpand(medicamento.getId())
                .toUri();
    }
}