package es.uvigo.dagss.recetas.daos;

import es.uvigo.dagss.recetas.entidades.Medicamento;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface MedicamentoDAO extends JpaRepository<Medicamento, Long> {
    // (HU-M4 + HU-A8)
    //  ContainingIgnoreCase para hacer un LIKE sin q importen lsa mayúsculas
    
    List<Medicamento> findByNombreComercialContainingIgnoreCase(String texto);
    List<Medicamento> findByPrincipioActivoContainingIgnoreCase(String texto);
    List<Medicamento> findByFabricanteContainingIgnoreCase(String texto);
    List<Medicamento> findByFamiliaContainingIgnoreCase(String texto);

    //necesario por el borrado logico, findAll mostraria la lista con todos 
    //el medico tiene q salirle una lista de los activos para poder seleccionar para la prescripcion
    List<Medicamento> findByActivoTrue();
}