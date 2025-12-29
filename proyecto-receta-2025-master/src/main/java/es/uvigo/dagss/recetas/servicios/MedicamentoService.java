package es.uvigo.dagss.recetas.servicios;

import java.util.List;
import java.util.Optional;

import es.uvigo.dagss.recetas.entidades.Medicamento;


public interface MedicamentoService {
    
    public Medicamento crear(Medicamento Medicamento);
    public Medicamento modificar(Medicamento Medicamento);
    public void eliminar(Medicamento Medicamento);
    public List<Medicamento> filtrarPorNombreComercial(String nombre);
    public Optional<Medicamento> buscarPorId(Long id);
	public List<Medicamento> buscarPorFabricante(String localidad);
    public List<Medicamento> buscarPorFamilia(String familia);
    public List<Medicamento> buscarPorPrincipioActivo(String principio);
	public List<Medicamento> buscarTodos();
    
}
