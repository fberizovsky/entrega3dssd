package com.example.demo.models;

import com.example.demo.models.enums.Material;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity(name = "material_deposito_comunal")
public class MaterialComunalDeposit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Material material;

    @ManyToOne
    @JoinColumn(name = "deposito_comunal_id")
    private ComunalDeposit comunalDeposit ;

    @ManyToOne
    @JoinColumn(name = "deposito_principal_id")
    private PrincipalDeposit depositoPrincipal;


    public MaterialComunalDeposit() { }

    public MaterialComunalDeposit(Material material, ComunalDeposit comunalDeposit, PrincipalDeposit depositoPrincipal) {
        this.material = material;
        this.comunalDeposit = comunalDeposit;
        this.depositoPrincipal = depositoPrincipal;
    }


    // Getters y setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Material getMaterial() { return material; }
    public void setMaterial(Material material) { this.material = material; }

    public ComunalDeposit getComunalDeposit() { return comunalDeposit; }
    public void setComunalDeposit(ComunalDeposit comunalDeposit) { this.comunalDeposit = comunalDeposit; }

    public PrincipalDeposit getDepositoPrincipal() { return depositoPrincipal; }
    public void setDepositoPrincipal(PrincipalDeposit depositoPrincipal) { this.depositoPrincipal = depositoPrincipal; }
}
