package mx.gob.segob.nsjp.dto.domicilio;

import mx.gob.segob.nsjp.dto.base.GenericDTO;

/**
 * Created by israel on 9/22/15.
 */
public class RegionDTO extends GenericDTO {

    private static final long serialVersionUID = 634345603647402740L;

    private Long regionId;
    private String claveRegion;
    private String nombre;

    public Long getRegionId() {
        return regionId;
    }

    public void setRegionId(Long regionId) {
        this.regionId = regionId;
    }

    public String getClaveRegion() {
        return claveRegion;
    }

    public void setClaveRegion(String claveRegion) {
        this.claveRegion = claveRegion;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
}
