package es.uvigo.dagss.recetas.servicios;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import es.uvigo.dagss.recetas.daos.MedicoDAO;
import es.uvigo.dagss.recetas.entidades.CentroDeSalud;
import es.uvigo.dagss.recetas.entidades.Medico;

@Service
public class MedicoServiceImpl implements MedicoService {

    @Autowired
    private MedicoDAO medicoDAO;

    @Override
    public Medico crear(Medico medico) {
        return medicoDAO.save(medico);
    }

    @Override
    public Medico modificar(Medico medico) {
        return medicoDAO.save(medico);
    }

    @Override
    public void eliminar(Medico medico) {
        medico.setActivo(false);
        medicoDAO.save(medico);
    }

    @Override
    public List<Medico> buscarPorNombre(String nombre) {
        return medicoDAO.findByNombreContainingIgnoreCase(nombre);
    }

    @Override
    public Optional<Medico> buscarPorId(Long id) {
        return medicoDAO.findById(id);
    }

    @Override
    public List<Medico> buscarPorCds(CentroDeSalud centroDeSalud) {
        return medicoDAO.findByCentroSaludId(centroDeSalud.getId());
    }

    @Override
    public List<Medico> buscarPorLocalidad(String localidad) {
        return medicoDAO.findByLocation(localidad);
    }

    @Override
    public List<Medico> buscarTodos() {
        return medicoDAO.findAll();
    }
    
}
