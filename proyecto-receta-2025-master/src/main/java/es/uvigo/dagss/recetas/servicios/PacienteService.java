package es.uvigo.dagss.recetas.servicios;

import java.util.List;
import java.util.Optional;

import es.uvigo.dagss.recetas.entidades.CentroDeSalud;
import es.uvigo.dagss.recetas.entidades.Medico;
import es.uvigo.dagss.recetas.entidades.Paciente;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface PacienteService {
    
    public Paciente crear(Paciente paciente);
    public Paciente modificar(Paciente paciente);
    public void eliminar(Paciente paciente);

    public Optional<Paciente> buscarPorId(Long id);
    public Optional<Paciente> buscarPorTarjeta(String numTarjeta);
    
    public List<Paciente> buscarPorNombre(String nombre);
    public List<Paciente> buscarPorCds(Long idCentro);
    public List<Paciente> buscarPorMedico(Medico medico);
	public List<Paciente> buscarPorLocalidad(String localidad);
    
	
    public List<Paciente> buscarTodos();
    
}
