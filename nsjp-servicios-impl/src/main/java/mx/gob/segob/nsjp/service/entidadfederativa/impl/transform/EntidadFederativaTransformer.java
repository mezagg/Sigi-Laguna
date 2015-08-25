package mx.gob.segob.nsjp.service.entidadfederativa.impl.transform;


import mx.gob.segob.nsjp.dto.domicilio.EntidadFederativaDTO;
import mx.gob.segob.nsjp.model.EntidadFederativa;
import org.apache.log4j.Logger;

/**
 * Created by israel on 8/4/15.
 */
public class EntidadFederativaTransformer {

    private final static Logger logger = Logger
            .getLogger(EntidadFederativaTransformer.class);

    public static EntidadFederativaDTO transformaEntidadFedertiva(EntidadFederativa entidadFederativa){
        if ( entidadFederativa == null){
            return null;
        }
        EntidadFederativaDTO entidadFederativaDTO = new EntidadFederativaDTO();

        entidadFederativaDTO.setAbreviacion(entidadFederativa.getAbreviacion());
        entidadFederativaDTO.setEntidadFederativaId(entidadFederativa.getEntidadFederativaId());

        entidadFederativaDTO.setNombreEntidad(entidadFederativa.getNombre());
        entidadFederativaDTO.setCzonaGeografica(entidadFederativa.getZonaGeografica());

        return entidadFederativaDTO;
    }
}
