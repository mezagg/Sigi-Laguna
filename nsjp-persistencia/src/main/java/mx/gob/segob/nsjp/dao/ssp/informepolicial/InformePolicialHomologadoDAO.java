package mx.gob.segob.nsjp.dao.ssp.informepolicial;

import java.util.Date;
import java.util.List;

import mx.gob.segob.nsjp.dao.base.GenericDao;
import mx.gob.segob.nsjp.dto.usuario.UsuarioDTO;
import mx.gob.segob.nsjp.model.DelitoIph;
import mx.gob.segob.nsjp.model.FaltaAdministrativaIph;
import mx.gob.segob.nsjp.model.ssp.InformePolicialHomologado;
import mx.gob.segob.nsjp.model.ssp.TurnoLaboralIph;

public interface InformePolicialHomologadoDAO 
	   extends
	             GenericDao<InformePolicialHomologado,Long> {	
	public List<Object[]> ObtenerFolioIPH();
	public InformePolicialHomologado consultaInformePorFolio(Long folio);
	public InformePolicialHomologado consultarInformePorId(Long Id);
	public List<DelitoIph> consultarDelitosDeIPH(Long idInforme);
	public List<FaltaAdministrativaIph> consultarFaltaAdministrativaDeIPH(Long idInforme);
	public List<InformePolicialHomologado> consultarInformes();
	public void eliminarTurnosByInformePolicialHomologadoId(Long idTurno, Long iFolio);
	public Long consultaIdOperativoByFolioIph(Long iFolio);
	/**
	 * Obtiene el numero de IPH o IP resgistrados dentro de un rango de fechas.
	 * @author cesarAgustin
	 * @param fechaInicio
	 * @param fechaFin
	 * @param condicion
	 * @return
	 */
	public Long obtenerIPHPorFechas(Date fechaInicio, Date fechaFin, Boolean condicion);
	
	public List<InformePolicialHomologado> consultarInformesPorUsuario(UsuarioDTO usuario);
}
