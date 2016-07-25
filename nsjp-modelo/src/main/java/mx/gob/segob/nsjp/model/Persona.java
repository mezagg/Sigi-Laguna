/**
 * Nombre del Programa		: Persona.java
 * Autor                    : AlejandroGA
 * Compania                 : Ultrasist
 * Proyecto                 : NSJP						Fecha:27/06/2013 
 * Marca de cambio        	: N/A
 * Descripcion General    	: Clase entity para Persona
 * Programa Dependiente  	: N/A
 * Programa Subsecuente 	: N/A
 * Cond. de ejecucion		: N/A
 * Dias de ejecucion		: N/A                       Horario: N/A
 *                              MODIFICACIONES
 *------------------------------------------------------------------------------
 * Autor                    : N/A
 * Compania					: N/A
 * Proyecto                 : N/A						Fecha: N/A
 * Modificacion				: N/A
 *------------------------------------------------------------------------------
 */

package mx.gob.segob.nsjp.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

/**
 * Persona entity. @author MyEclipse Persistence Tools
 */
@SuppressWarnings("serial")
@Entity
@Table(name = "Persona")
@Inheritance(strategy = InheritanceType.JOINED)
@PrimaryKeyJoinColumn(name = "Persona_id")
public class Persona extends Elemento {

    // Fields

    private Valor tipoDoctoId;
    private Boolean esVivo;
    private String folioIdentificacion;
    private Set<MedioDeContacto> medioDeContactos = new HashSet<MedioDeContacto>(0);
    private Set<NombreDemografico> nombreDemograficos = new HashSet<NombreDemografico>(0);
	private Set<MandamientoPersona> mandamientosPersona = new HashSet<MandamientoPersona>(0);

    // Constructors

    /** default constructor */
    public Persona() {
    }

    public Persona(Long personaId) {
        super();
        setElementoId(personaId);
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "TipoDoctoId_val")
    public Valor getTipoDoctoId() {
        return this.tipoDoctoId;
    }

    public void setTipoDoctoId(Valor valor) {
        this.tipoDoctoId = valor;
    }

    @Column(name = "bEsVivo", nullable = false, precision = 1, scale = 0)
    public Boolean getEsVivo() {
        return this.esVivo;
    }

    public void setEsVivo(Boolean esVivo) {
        this.esVivo = esVivo;
    }

    @Column(name = "cFolioIdentificacion", length = 30)
    public String getFolioIdentificacion() {
        return this.folioIdentificacion;
    }

    public void setFolioIdentificacion(String folioIdentificacion) {
        this.folioIdentificacion = folioIdentificacion;
    }

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "persona")
    public Set<MedioDeContacto> getMedioDeContactos() {
        return this.medioDeContactos;
    }

    public void setMedioDeContactos(Set<MedioDeContacto> medioDeContactos) {
        this.medioDeContactos = medioDeContactos;
    }

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "persona")
    public Set<NombreDemografico> getNombreDemograficos() {
        return this.nombreDemograficos;
    }

    public void setNombreDemograficos(Set<NombreDemografico> nombreDemograficos) {
        this.nombreDemograficos = nombreDemograficos;
    }

	/**
	 * @return the mandamientosPersona
	 */
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "persona")
	public Set<MandamientoPersona> getMandamientosPersona() {
		return mandamientosPersona;
	}

	/**
	 * @param mandamientosPersona the mandamientosPersona to set
	 */
	public void setMandamientosPersona(Set<MandamientoPersona> mandamientosPersona) {
		this.mandamientosPersona = mandamientosPersona;
	}

}
