package es.uvigo.dagss.recetas.servicios;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import es.uvigo.dagss.recetas.daos.MedicamentoDAO;
import es.uvigo.dagss.recetas.entidades.Medicamento;

@Service
public class MedicamentoServiceImpl implements MedicamentoService {

    @Autowired
    private MedicamentoDAO medicamentoDAO;

    @Override
    public Medicamento crear(Medicamento medicamento) {
        return medicamentoDAO.save(medicamento);
    }

    @Override
    public Medicamento modificar(Medicamento medicamento) {
        return medicamentoDAO.save(medicamento);
    }

    @Override
    public void eliminar(Medicamento medicamento) {
        medicamento.setActivo(false);
        medicamentoDAO.save(medicamento);
    }

    @Override
    public List<Medicamento> filtrarPorNombreComercial(String nombre) {
        return medicamentoDAO.findByNombreComercialContainingIgnoreCase(nombre);
    }

    @Override
    public Optional<Medicamento> buscarPorId(Long id) {
        return medicamentoDAO.findById(id);
    }

    @Override
    public List<Medicamento> buscarPorFabricante(String fabricante) {
        return medicamentoDAO.findByFabricanteContainingIgnoreCase(fabricante);
    }

    @Override
    public List<Medicamento> buscarPorFamilia(String familia) {
        return medicamentoDAO.findByFamiliaContainingIgnoreCase(familia);
    }

    @Override
    public List<Medicamento> buscarPorPrincipioActivo(String principio) {
        return medicamentoDAO.findByPrincipioActivoContainingIgnoreCase(principio);
    }

    @Override
    public List<Medicamento> buscarTodos() {
        return medicamentoDAO.findByActivoTrue();
    }
    
}
