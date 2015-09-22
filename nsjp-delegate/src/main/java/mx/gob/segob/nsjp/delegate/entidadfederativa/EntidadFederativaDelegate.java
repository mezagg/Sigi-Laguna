package mx.gob.segob.nsjp.delegate.entidadfederativa;


import mx.gob.segob.nsjp.comun.excepcion.NSJPNegocioException;
import mx.gob.segob.nsjp.dto.domicilio.EntidadFederativaDTO;

import java.util.List;

/**
 * Created by israel on 8/4/15.
 */
public interface EntidadFederativaDelegate {


    List<EntidadFederativaDTO> consultarEndidadesFederativasTodas() throws NSJPNegocioException;
}
