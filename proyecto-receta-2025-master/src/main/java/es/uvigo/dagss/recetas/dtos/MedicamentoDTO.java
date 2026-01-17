package es.uvigo.dagss.recetas.dtos;

import es.uvigo.dagss.recetas.entidades.Medicamento;

public class MedicamentoDTO {
    private Long id;
    private String nombreComercial;
    private String principioActivo;
    private String fabricante;
    private String familia;
    private Integer numeroDosis;

    public MedicamentoDTO(Medicamento m) {
        this.id = m.getId();
        this.nombreComercial = m.getNombreComercial();
        this.principioActivo = m.getPrincipioActivo();
        this.fabricante = m.getFabricante();
        this.familia = m.getFamilia();
        this.numeroDosis = m.getNumeroDosis();
    }

    // Getters
    public Long getId() {
        return id;
    }

    public String getNombreComercial() {
        return nombreComercial;
    }

    public String getPrincipioActivo() {
        return principioActivo;
    }

    public String getFabricante() {
        return fabricante;
    }

    public String getFamilia() {
        return familia;
    }

    public Integer getNumeroDosis() {
        return numeroDosis;
    }
}