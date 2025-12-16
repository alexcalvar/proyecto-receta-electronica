package es.uvigo.dagss.recetas.utils;

import es.uvigo.dagss.recetas.daos.*;
import es.uvigo.dagss.recetas.entidades.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class DatabaseLoader implements CommandLineRunner {

    @Autowired
    private AdministradorDAO administradorDAO;
    
    @Autowired
    private CentroDeSaludDAO centroSaludDAO;
    
    @Autowired
    private MedicoDAO medicoDAO;
    
    @Autowired
    private PacienteDAO pacienteDAO;
    
    @Autowired
    private MedicamentoDAO medicamentoDAO;

    @Override
    public void run(String... args) throws Exception {
        // Solo cargamos datos si la BD está vacía (para evitar duplicados al reiniciar)
        if (centroSaludDAO.count() == 0) {
            
            // 1. Crear Administrador
            Administrador admin = new Administrador();
            admin.setLogin("admin");
            admin.setPassword("adminpass"); // En producción esto iría encriptado
            admin.setNombre("Administrador Principal");
            admin.setEmail("admin@dagss.com");
            administradorDAO.save(admin);
            
            // 2. Crear Centro de Salud
            CentroDeSalud centro = new CentroDeSalud();
            centro.setNombre("Centro de Salud Teis");
            centro.setActivo(true);
            centro.setTelefono("986123456");
            centro.setEmail("cs.teis@sergas.es");
            centro.setDireccion(new Direccion("Av. Galicia, 1", "Vigo", "36207", "Pontevedra"));
            centroSaludDAO.save(centro);

            // 3. Crear Médico
            Medico medico = new Medico();
            medico.setLogin("medico1");
            medico.setPassword("medico1"); // Login y pass iniciales
            medico.setNombre("Juan");
            medico.setApellidos("Pérez");
            medico.setDni("12345678A");
            medico.setNumColegiado("36001");
            medico.setCentroDeSalud(centro); // Vinculamos al centro
            medicoDAO.save(medico);

            // 4. Crear Paciente
            Paciente paciente = new Paciente();
            paciente.setLogin("paciente1");
            paciente.setPassword("paciente1");
            paciente.setNombre("Ana");
            paciente.setApellidos("García");
            paciente.setDni("87654321B");
            paciente.setNumTarjetaSanitaria("TSI-0001");
            paciente.setNss("123456789012");
            paciente.setFechaNacimiento(LocalDate.of(1990, 5, 15));
            paciente.setDireccion(new Direccion("C/ Urzaiz, 20", "Vigo", "36201", "Pontevedra"));
            paciente.setCentroSalud(centro);
            paciente.setMedico(medico); // Asignamos su médico de cabecera
            pacienteDAO.save(paciente);

            // 5. Crear Medicamentos
            Medicamento med1 = new Medicamento();
            med1.setNombreComercial("Paracetamol 1g");
            med1.setPrincipioActivo("Paracetamol");
            med1.setFabricante("Cinfa");
            med1.setFamilia("Analgésicos");
            med1.setNumeroDosis(40);
            medicamentoDAO.save(med1);

            Medicamento med2 = new Medicamento();
            med2.setNombreComercial("Ibuprofeno 600mg");
            med2.setPrincipioActivo("Ibuprofeno");
            med2.setFabricante("Kern Pharma");
            med2.setFamilia("Antiinflamatorios");
            med2.setNumeroDosis(30);
            medicamentoDAO.save(med2);

            System.out.println("✅ Datos de prueba cargados correctamente en la Base de Datos.");
        }
    }
}