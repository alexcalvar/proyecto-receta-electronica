package es.uvigo.dagss.recetas.servicios;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import es.uvigo.dagss.recetas.daos.FarmaciaDAO;
import es.uvigo.dagss.recetas.entidades.Farmacia;

@Service
public class FarmaciaServiceImpl implements FarmaciaService {

    @Autowired
    private FarmaciaDAO farmaciaDAO;

    @Override
    public Farmacia crear(Farmacia farmacia) {
        return farmaciaDAO.save(farmacia);
    }

    @Override
    public Farmacia modificar(Farmacia farmacia) {
        return farmaciaDAO.save(farmacia);
    }

    @Override
    public void eliminar(Farmacia farmacia) {
        farmacia.setActivo(false);
        farmaciaDAO.save(farmacia);
    }

    @Override
    public List<Farmacia> buscarPorNombre(String nombre) {
        return farmaciaDAO.findByNombreEstablecimientoContainingIgnoreCase(nombre);
    }

    @Override
    public Optional<Farmacia> buscarPorId(Long id) {
        return farmaciaDAO.findById(id);
    }

    @Override
    public List<Farmacia> buscarPorLocalidad(String localidad) {
        return farmaciaDAO.buscarPorLocalidad(localidad);
    }

    @Override
    public List<Farmacia> buscarTodos() {
        return farmaciaDAO.findByActivoTrue();
    }
     
}
