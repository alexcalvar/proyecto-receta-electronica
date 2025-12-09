package es.uvigo.dagss.recetas.daos;

import es.uvigo.dagss.recetas.entidades.Medicamento;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface MedicamentoDAO extends JpaRepository<Medicamento, Long> {
    // HU-M4 y HU-A8: Buscador multicriterio (Nombre, Principio activo, fabricante...)
    // Usamos 'ContainingIgnoreCase' para hacer un LIKE insensible a mayúsculas
    
    List<Medicamento> findByNombreComercialContainingIgnoreCase(String texto);
    List<Medicamento> findByPrincipioActivoContainingIgnoreCase(String texto);
    List<Medicamento> findByFabricanteContainingIgnoreCase(String texto);
    List<Medicamento> findByFamiliaContainingIgnoreCase(String texto);
}