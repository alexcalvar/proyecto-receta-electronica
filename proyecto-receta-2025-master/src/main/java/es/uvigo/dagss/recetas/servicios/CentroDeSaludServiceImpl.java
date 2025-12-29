package es.uvigo.dagss.recetas.servicios;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import es.uvigo.dagss.recetas.daos.CentroDeSaludDAO;
import es.uvigo.dagss.recetas.entidades.CentroDeSalud;


@Service
public class CentroDeSaludServiceImpl implements CentroDeSaludService {

    @Autowired
    private CentroDeSaludDAO centroDeSaludDAO;

    @Override
    @Transactional
    public CentroDeSalud crear(CentroDeSalud centro) {
        // Podrías añadir validaciones aquí (ej: que no tenga el mismo nombre)
        return centroDeSaludDAO.save(centro);
    }

    @Override
    @Transactional
    public CentroDeSalud modificar(CentroDeSalud centro) {
        // El método save de JPA sirve tanto para crear como para actualizar
        // si el objeto ya tiene un ID.
        return centroDeSaludDAO.save(centro);
    }

    @Override
    @Transactional
    public void eliminar(CentroDeSalud centro) {
        // Normalmente borraríamos por ID, pero si tu interfaz pide el objeto:
        centroDeSaludDAO.delete(centro);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<CentroDeSalud> buscarPorId(Long id) {
        return centroDeSaludDAO.findById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<CentroDeSalud> buscarPorNombre(String nombre) {
        // Asumiendo que en tu DAO tienes un método tipo findByNombreContainingIgnoreCase
        List<CentroDeSalud> centros = centroDeSaludDAO.findByNombreContainingIgnoreCase(nombre);
        
        if (centros != null) {
            return centros;
        }
        return Collections.emptyList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<CentroDeSalud> buscarPorLocalidad(String localidad) {
        // Ojo aquí: Asumo que en el DAO creaste la query o el método
        // findByDireccionLocalidadContainingIgnoreCase(localidad)
        List<CentroDeSalud> centros = centroDeSaludDAO.findByLocalidad(localidad);
        
        if (centros != null) {
            return centros;
        }
        return Collections.emptyList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<CentroDeSalud> buscarTodos() {
        List<CentroDeSalud> centros = centroDeSaludDAO.findAll();
        if (centros != null) {
            return centros;
        }
        return Collections.emptyList();
    }
}
