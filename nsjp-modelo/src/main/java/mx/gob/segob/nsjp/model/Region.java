package mx.gob.segob.nsjp.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.io.Serializable;

/**
 *
 */
@SuppressWarnings("serial")
@Entity
@Table(name = "Region")
public class Region implements Serializable{

    //Fields
    private Long regionId;
    private String claveRegion;
    private String nombre;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "region_id", unique = true, nullable = false, precision = 18, scale = 0)
    public Long getRegionId() {
        return regionId;
    }

    @Column(name = "cNombre", nullable = false, length = 50)
    public String getNombre() {
        return nombre;
    }

    @Column(name = "cClaveRegion", nullable = false, length = 3)
    public String getClaveRegion() {
        return claveRegion;
    }

    public void setRegionId(Long regionId) {
        this.regionId = regionId;
    }

    public void setClaveRegion(String claveRegion) {
        this.claveRegion = claveRegion;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
}
