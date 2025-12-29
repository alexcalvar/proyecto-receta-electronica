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
    private FarmaciaDAO farmaciaDAO; // ¡Nuevo!
    
    @Autowired
    private MedicoDAO medicoDAO;
    
    @Autowired
    private PacienteDAO pacienteDAO;
    
    @Autowired
    private MedicamentoDAO medicamentoDAO;

    @Override
    public void run(String... args) throws Exception {
        // Solo cargamos datos si no hay Centros de Salud (indicador de BD vacía)
        if (centroSaludDAO.count() == 0) {
            
            System.out.println(" Iniciando carga de datos de prueba...");

            // 1. Crear Administrador
            Administrador admin = new Administrador();
            admin.setLogin("admin");
            admin.setPassword("admin"); 
            admin.setNombre("Super Admin");
            admin.setEmail("admin@dagss.com");
            administradorDAO.save(admin);
            
            // 2. Crear Centro de Salud
            CentroDeSalud centro = new CentroDeSalud();
            centro.setNombre("Centro de Salud Teis");
            centro.setActivo(true);
            centro.setTelefono("986123456");
            centro.setEmail("cs.teis@sergas.es");
            // Asumiendo que Direccion es @Embeddable. Si es @Entity, cuidado.
            centro.setDireccion(new Direccion("Av. Galicia, 1", "Vigo", "36207", "Pontevedra"));
            centroSaludDAO.save(centro); // IMPORTANTE: Guardar centro primero para tener ID

            // 3. Crear Farmacia (¡Faltaba esto!)
            Farmacia farmacia = new Farmacia();
            farmacia.setNombreEstablecimiento("Farmacia Ldo. Martinez");
            farmacia.setNombreFarmaceutico("Luis Martinez");
            farmacia.setLogin("farmacia1");
            farmacia.setPassword("farmacia1");
            farmacia.setNif("B12345678");
            farmacia.setActivo(true); // Si tienes campo activo
            farmacia.setDireccion(new Direccion("C/ Sanjurjo Badía, 100", "Vigo", "36207", "Pontevedra"));
            farmaciaDAO.save(farmacia);

            // 4. Crear Médico
            Medico medico = new Medico();
            medico.setLogin("medico1");
            medico.setPassword("medico1");
            medico.setNombre("Juan");
            medico.setApellidos("Pérez");
            medico.setDni("12345678A");
            medico.setNumColegiado("36001");
            medico.setCentroDeSalud(centro); // Relación ManyToOne
            medicoDAO.save(medico);

            // 5. Crear Paciente
            Paciente paciente = new Paciente();
            paciente.setLogin("paciente1");
            paciente.setPassword("paciente1");
            paciente.setNombre("Ana");
            paciente.setApellidos("García");
            paciente.setDni("87654321B");
            paciente.setNumTarjetaSanitaria("TSI-0001"); // Dato clave para buscar recetas
            paciente.setNss("123456789012");
            paciente.setFechaNacimiento(LocalDate.of(1990, 5, 15));
            paciente.setDireccion(new Direccion("C/ Urzaiz, 20", "Vigo", "36201", "Pontevedra"));
            paciente.setCentroSalud(centro); // Su centro asignado
            paciente.setMedico(medico);      // Su médico asignado
            pacienteDAO.save(paciente);

            // 6. Crear Medicamentos
            crearMedicamento("Paracetamol 1g", "Paracetamol", "Cinfa", "Analgésicos");
            crearMedicamento("Ibuprofeno 600mg", "Ibuprofeno", "Kern Pharma", "Antiinflamatorios");
            crearMedicamento("Amoxicilina 500mg", "Amoxicilina", "Normon", "Antibióticos");
            crearMedicamento("Omeprazol 20mg", "Omeprazol", "Cinfa", "Antiácidos");

            System.out.println("✅ Datos de prueba cargados correctamente en la Base de Datos.");
        }
    }

    // Método auxiliar para no repetir código creando medicamentos
    private void crearMedicamento(String nombre, String principio, String fab, String familia) {
        Medicamento m = new Medicamento();
        m.setNombreComercial(nombre);
        m.setPrincipioActivo(principio);
        m.setFabricante(fab);
        m.setFamilia(familia);
        m.setNumeroDosis(30);
        medicamentoDAO.save(m);
    }
}