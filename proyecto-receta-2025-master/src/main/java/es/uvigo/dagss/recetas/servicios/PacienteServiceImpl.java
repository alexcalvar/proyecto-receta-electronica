package es.uvigo.dagss.recetas.servicios;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import es.uvigo.dagss.recetas.daos.PacienteDAO;
import es.uvigo.dagss.recetas.entidades.Medico;
import es.uvigo.dagss.recetas.entidades.Paciente;

@Service
public class PacienteServiceImpl implements PacienteService {

    @Autowired
    private PacienteDAO pacienteDAO;

    @Override
    public Paciente crear(Paciente paciente) {
        return pacienteDAO.save(paciente);
    }

    @Override
    public Paciente modificar(Paciente paciente) {
        return pacienteDAO.save(paciente);
    }

    @Override
    public void eliminar(Paciente paciente) {
        paciente.setActivo(false);
        pacienteDAO.save(paciente);
    }

    @Override
    public Optional<Paciente> buscarPorId(Long id) {
        return pacienteDAO.findById(id);
    }

    @Override
    public Optional<Paciente> buscarPorTarjeta(String numTarjeta) {
        return Optional.ofNullable(pacienteDAO.findByNumTarjetaSanitaria(numTarjeta));
    }

    @Override
    public List<Paciente> buscarPorNombre(String nombre) {
        return pacienteDAO.findByNombreContainingIgnoreCase(nombre);
    }

    @Override
    public List<Paciente> buscarPorCds(Long idCentro) {
        return pacienteDAO.findByCentroDeSaludId(idCentro);
    }

    @Override
    public List<Paciente> buscarPorMedico(Medico medico) {
        return pacienteDAO.findByMedicoId(medico.getId());
    }

    @Override
    public List<Paciente> buscarPorLocalidad(String localidad) {
        return pacienteDAO.findByDireccionLocalidadContainingIgnoreCase(localidad);
    }

    @Override
    public List<Paciente> buscarTodos() {
        return pacienteDAO.findAll();
    }
    
}
