package mx.gob.segob.nsjp.delegate.region;

import mx.gob.segob.nsjp.comun.excepcion.NSJPNegocioException;
import mx.gob.segob.nsjp.dto.domicilio.RegionDTO;

import java.util.List;

/**
 * Created by israel on 9/22/15.
 */
public interface RegionDelegate {

    List<RegionDTO> consultarTodos()throws NSJPNegocioException;
}
