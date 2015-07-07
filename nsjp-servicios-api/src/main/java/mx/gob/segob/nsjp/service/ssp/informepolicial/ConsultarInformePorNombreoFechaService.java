package mx.gob.segob.nsjp.service.ssp.informepolicial;

import java.util.Date;
import java.util.List;

import mx.gob.segob.nsjp.comun.excepcion.NSJPNegocioException;
import mx.gob.segob.nsjp.dto.ssp.informepolicial.InformePolicialHomologadoDTO;

public interface ConsultarInformePorNombreoFechaService {
	public List<InformePolicialHomologadoDTO> ConsultarInformePorFechaOPersona(Date fechaInicio, Date fechaFin, String nombreCompleto) throws NSJPNegocioException;
}
