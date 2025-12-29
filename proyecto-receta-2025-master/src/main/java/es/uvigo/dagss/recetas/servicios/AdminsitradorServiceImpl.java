package es.uvigo.dagss.recetas.servicios;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import es.uvigo.dagss.recetas.daos.AdministradorDAO;
import es.uvigo.dagss.recetas.entidades.Administrador;

@Service
public class AdminsitradorServiceImpl implements AdministradorService{

    @Autowired
	private AdministradorDAO administradorDAO;


    @Override
    @Transactional
    public Administrador crear(Administrador admin) {
        
        return administradorDAO.save(admin);
    }

    @Override
    @Transactional
    public Administrador modificar(Administrador admin) {
        return administradorDAO.save(admin);
    }

    @Override
    @Transactional
    public void eliminar(Administrador admin) {
        administradorDAO.delete(admin);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Administrador> buscarPorId(Long id) {
        return administradorDAO.findById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Administrador> buscarTodos() {
        return administradorDAO.findAll();
    }
    
}
