/**
 * 
 */
package mx.gob.segob.nsjp.delegate.programa.impl;

import java.util.List;

import mx.gob.segob.nsjp.comun.enums.excepciones.CodigoError;
import mx.gob.segob.nsjp.comun.excepcion.NSJPNegocioException;
import mx.gob.segob.nsjp.delegate.programa.AsignacionProgramaDelegate;
import mx.gob.segob.nsjp.dto.programas.ActoBuenaConductaDTO;
import mx.gob.segob.nsjp.dto.programas.AsignacionCentroDetencionDTO;
import mx.gob.segob.nsjp.dto.programas.AsignacionMedidaAlternaDTO;
import mx.gob.segob.nsjp.dto.programas.AsignacionProgramaDTO;
import mx.gob.segob.nsjp.dto.programas.CursoDTO;
import mx.gob.segob.nsjp.dto.programas.PeriodoAcumulacionPuntosDTO;
import mx.gob.segob.nsjp.dto.programas.ProgramaDTO;
import mx.gob.segob.nsjp.dto.programas.RemisionDTO;
import mx.gob.segob.nsjp.dto.programas.TrabajoDTO;
import mx.gob.segob.nsjp.dto.sentencia.SentenciaDTO;
import mx.gob.segob.nsjp.service.programa.AsignacionProgramaService;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author AntonioBV
 *
 */
@Service
@Transactional
public class AsignacionProgramaDelegateImpl implements
		AsignacionProgramaDelegate {

	private static final Logger LOG  = Logger.getLogger(AsignacionProgramaDelegateImpl.class);
	
	@Autowired
	AsignacionProgramaService asignacionProgramaService;
	
	/* (non-Javadoc)
	 * @see mx.gob.segob.nsjp.delegate.programa.AsignacionProgramaDelegate#consultarAsignacionCentroDetencionPorId(mx.gob.segob.nsjp.dto.programas.AsignacionCentroDetencionDTO)
	 */
	@Override
	public AsignacionCentroDetencionDTO consultarAsignacionCentroDetencionPorId(
			AsignacionCentroDetencionDTO asignacionCentroDetencionDTO)
			throws NSJPNegocioException {
		return asignacionProgramaService.consultarAsignacionCentroDetencionPorId(asignacionCentroDetencionDTO);
	}

	/* (non-Javadoc)
	 * @see mx.gob.segob.nsjp.delegate.programa.AsignacionProgramaDelegate#consultarAsignacionMedidaAlternaPorId(mx.gob.segob.nsjp.dto.programas.AsignacionMedidaAlternaDTO)
	 */
	@Override
	public AsignacionMedidaAlternaDTO consultarAsignacionMedidaAlternaPorId(
			AsignacionMedidaAlternaDTO asignacionMedidaAlternaDTO)
			throws NSJPNegocioException {
		return asignacionProgramaService.consultarAsignacionMedidaAlternaPorId(asignacionMedidaAlternaDTO);
	}

	/* (non-Javadoc)
	 * @see mx.gob.segob.nsjp.delegate.programa.AsignacionProgramaDelegate#consultarAsignacionProgramaPorId(mx.gob.segob.nsjp.dto.programas.AsignacionProgramaDTO)
	 */
	@Override
	public AsignacionProgramaDTO consultarAsignacionProgramaPorId(
			AsignacionProgramaDTO asignacionProgramaDTO)
			throws NSJPNegocioException {
		return asignacionProgramaService.consultarAsignacionProgramaPorId(asignacionProgramaDTO);
	}

	/* (non-Javadoc)
	 * @see mx.gob.segob.nsjp.delegate.programa.AsignacionProgramaDelegate#consultarCursoPorId(mx.gob.segob.nsjp.dto.programas.CursoDTO)
	 */
	@Override
	public CursoDTO consultarCursoPorId(CursoDTO cursoDTO)
			throws NSJPNegocioException {
		return asignacionProgramaService.consultarCursoPorId(cursoDTO);
	}

	/* (non-Javadoc)
	 * @see mx.gob.segob.nsjp.delegate.programa.AsignacionProgramaDelegate#actualizarCurso(mx.gob.segob.nsjp.dto.programas.CursoDTO)
	 */
	@Override
	public void actualizarCurso(CursoDTO cursoDTO) throws NSJPNegocioException{
		try{
			asignacionProgramaService.actualizarCurso(cursoDTO);
		} catch(Exception e) {
			LOG.error(e.getMessage(), e);
			throw new NSJPNegocioException (CodigoError.EJCUCION_OPERACION_ESTADO_INCORRECTO, e); 
		}
	}		
	
	/* (non-Javadoc)
	 * @see mx.gob.segob.nsjp.delegate.programa.AsignacionProgramaDelegate#consultarProgramaPorId(mx.gob.segob.nsjp.dto.programas.ProgramaDTO)
	 */
	@Override
	public ProgramaDTO consultarProgramaPorId(ProgramaDTO programaDTO)
			throws NSJPNegocioException {
		return asignacionProgramaService.consultarProgramaPorId(programaDTO);
	}

	/* (non-Javadoc)
	 * @see mx.gob.segob.nsjp.delegate.programa.AsignacionProgramaDelegate#consultarRemisionPorId(mx.gob.segob.nsjp.dto.programas.RemisionDTO)
	 */
	@Override
	public RemisionDTO consultarRemisionPorId(RemisionDTO remisionDTO)
			throws NSJPNegocioException {
		return asignacionProgramaService.consultarRemisionPorId(remisionDTO);
	}

	/* (non-Javadoc)
	 * @see mx.gob.segob.nsjp.delegate.programa.AsignacionProgramaDelegate#consultarSentencia(mx.gob.segob.nsjp.dto.sentencia.SentenciaDTO)
	 */
	@Override
	public SentenciaDTO consultarSentencia(SentenciaDTO sentenciaDTO)
			throws NSJPNegocioException {
		return asignacionProgramaService.consultarSentencia(sentenciaDTO);
	}

	/* (non-Javadoc)
	 * @see mx.gob.segob.nsjp.delegate.programa.AsignacionProgramaDelegate#actualizarTrabajo(mx.gob.segob.nsjp.dto.sentencia.SentenciaDTO)
	 */
	@Override
	public void actualizarTrabajo(TrabajoDTO trabajoDTO) throws NSJPNegocioException{
		asignacionProgramaService.actualizarTrabajo(trabajoDTO);
	}
	
	/* (non-Javadoc)
	 * @see mx.gob.segob.nsjp.delegate.programa.AsignacionProgramaDelegate#consultarTrabajoPorId(mx.gob.segob.nsjp.dto.programas.TrabajoDTO)
	 */
	@Override
	public TrabajoDTO consultarTrabajoPorId(TrabajoDTO trabajoDTO)
			throws NSJPNegocioException {
		return asignacionProgramaService.consultarTrabajoPorId(trabajoDTO);
	}
	
	/* (non-Javadoc)
	 * @see mx.gob.segob.nsjp.service.programa.AsignacionProgramaDelegate#consultarSentencias(mx.gob.segob.nsjp.dto.sentencia.SentenciaDTO)
	 */
	@Override
	public List<SentenciaDTO> consultarSentencias(SentenciaDTO filtroDTO) throws NSJPNegocioException {
		return asignacionProgramaService.consultarSentencias(filtroDTO);
	}

	/* (non-Javadoc)
	 * @see mx.gob.segob.nsjp.service.programa.AsignacionProgramaDelegate#consultarSentencias(mx.gob.segob.nsjp.dto.sentencia.SentenciaDTO)
	 */
	@Override
	public List<SentenciaDTO> consultarSentenciasXEstado(SentenciaDTO sentenciaDTO)
		throws NSJPNegocioException {
		return asignacionProgramaService.consultarSentenciasXEstado(sentenciaDTO);
	}	
	
	/* (non-Javadoc)
	 * @see mx.gob.segob.nsjp.delegate.programa.AsignacionProgramaDelegate#registrarSentencia(mx.gob.segob.nsjp.dto.sentencia.SentenciaDTO)
	 */
	@Override
	public void registrarSentencia(SentenciaDTO sentenciaDTO) {
		asignacionProgramaService.registrarSentencia(sentenciaDTO);
	}

	/* (non-Javadoc)
	 * @see mx.gob.segob.nsjp.delegate.programa.AsignacionProgramaDelegate#crearPrograma(mx.gob.segob.nsjp.dto.programas.ProgramaDTO)
	 */
	@Override
	public ProgramaDTO crearPrograma(ProgramaDTO programaDTO) {
		return asignacionProgramaService.crearPrograma(programaDTO);
	}

	/* (non-Javadoc)
	 * @see mx.gob.segob.nsjp.delegate.programa.AsignacionProgramaDelegate#crearCurso(mx.gob.segob.nsjp.dto.programas.CursoDTO)
	 */
	@Override
	public CursoDTO crearCurso(CursoDTO cursoDTO) {
		return asignacionProgramaService.crearCurso(cursoDTO);
	}

	/* (non-Javadoc)
	 * @see mx.gob.segob.nsjp.delegate.programa.AsignacionProgramaDelegate#crearCursos(java.util.List)
	 */
	@Override
	public void crearCursos(List<CursoDTO> cursosDTO) {
		asignacionProgramaService.crearCursos(cursosDTO);
	}

	/* (non-Javadoc)
	 * @see mx.gob.segob.nsjp.delegate.programa.AsignacionProgramaDelegate#crearTrabajo(mx.gob.segob.nsjp.dto.programas.TrabajoDTO)
	 */
	@Override
	public TrabajoDTO crearTrabajo(TrabajoDTO trabajoDTO) {
		return asignacionProgramaService.crearTrabajo(trabajoDTO);
	}

	/* (non-Javadoc)
	 * @see mx.gob.segob.nsjp.delegate.programa.AsignacionProgramaDelegate#crearTrabajos(java.util.List)
	 */
	@Override
	public void crearTrabajos(List<TrabajoDTO> trabajosDTO) {
		asignacionProgramaService.crearTrabajos(trabajosDTO);
	}

	/* (non-Javadoc)
	 * @see mx.gob.segob.nsjp.delegate.programa.AsignacionProgramaDelegate#consultarActoBuenaConductaPorId(mx.gob.segob.nsjp.dto.programas.ActoBuenaConductaDTO)
	 */
	@Override
	public ActoBuenaConductaDTO consultarActoBuenaConductaPorId(
			ActoBuenaConductaDTO actoBuenaConductaDTO) {
		return asignacionProgramaService.consultarActoBuenaConductaPorId(actoBuenaConductaDTO);
	}

	/* (non-Javadoc)
	 * @see mx.gob.segob.nsjp.delegate.programa.AsignacionProgramaDelegate#consultarActosBuenaConductaPorSentencia(mx.gob.segob.nsjp.dto.sentencia.SentenciaDTO)
	 */
	@Override
	public List<ActoBuenaConductaDTO> consultarActosBuenaConductaPorSentencia(
			SentenciaDTO sentenciaDTO) throws NSJPNegocioException {
		return asignacionProgramaService.consultarActosBuenaConductaPorSentencia(sentenciaDTO);
	}

	/* (non-Javadoc)
	 * @see mx.gob.segob.nsjp.delegate.programa.AsignacionProgramaDelegate#crearActoBuenaConducta(mx.gob.segob.nsjp.dto.programas.ActoBuenaConductaDTO)
	 */
	@Override
	public ActoBuenaConductaDTO crearActoBuenaConducta(
			ActoBuenaConductaDTO actoBuenaConductaDTO) {
		return asignacionProgramaService.crearActoBuenaConducta(actoBuenaConductaDTO);
	}

	/* (non-Javadoc)
	 * @see mx.gob.segob.nsjp.delegate.programa.AsignacionProgramaDelegate#consultarPeriodoAcumulacionPuntosPorId(mx.gob.segob.nsjp.dto.programas.PeriodoAcumulacionPuntosDTO)
	 */
	@Override
	public PeriodoAcumulacionPuntosDTO consultarPeriodoAcumulacionPuntosPorId(
			PeriodoAcumulacionPuntosDTO periodoAcumulacionPuntosDTO) {
		return asignacionProgramaService.consultarPeriodoAcumulacionPuntosPorId(periodoAcumulacionPuntosDTO);
	}

	/* (non-Javadoc)
	 * @see mx.gob.segob.nsjp.delegate.programa.AsignacionProgramaDelegate#consultarPeriodosAcumulacionPuntosPorSentencia(mx.gob.segob.nsjp.dto.sentencia.SentenciaDTO)
	 */
	@Override
	public List<PeriodoAcumulacionPuntosDTO> consultarPeriodosAcumulacionPuntosPorSentencia(
			SentenciaDTO sentenciaDTO) throws NSJPNegocioException {
		return asignacionProgramaService.consultarPeriodosAcumulacionPuntosPorSentencia(sentenciaDTO);
	}

	/* (non-Javadoc)
	 * @see mx.gob.segob.nsjp.delegate.programa.AsignacionProgramaDelegate#crearPeriodoAcumulacionPuntos(mx.gob.segob.nsjp.dto.programas.PeriodoAcumulacionPuntosDTO)
	 */
	@Override
	public PeriodoAcumulacionPuntosDTO crearPeriodoAcumulacionPuntos(
			PeriodoAcumulacionPuntosDTO periodoAcumulacionPuntosDTO) {
		return asignacionProgramaService.crearPeriodoAcumulacionPuntos(periodoAcumulacionPuntosDTO);
	}

	/* (non-Javadoc)
	 * @see mx.gob.segob.nsjp.delegate.programa.AsignacionProgramaDelegate#actualizarActoBuenaConducta(mx.gob.segob.nsjp.dto.programas.ActoBuenaConductaDTO)
	 */
	@Override
	public void actualizarActoBuenaConducta(ActoBuenaConductaDTO actoBuenaConductaDTO) {
		asignacionProgramaService.actualizarActoBuenaConducta(actoBuenaConductaDTO);
	}

	/* (non-Javadoc)
	 * @see mx.gob.segob.nsjp.delegate.programa.AsignacionProgramaDelegate#eliminarActoBuenaConducta(mx.gob.segob.nsjp.dto.programas.ActoBuenaConductaDTO)
	 */
	@Override
	public void eliminarActoBuenaConducta(ActoBuenaConductaDTO actoBuenaConductaDTO) {
		asignacionProgramaService.eliminarActoBuenaConducta(actoBuenaConductaDTO);
	}

	/* (non-Javadoc)
	 * @see mx.gob.segob.nsjp.delegate.programa.AsignacionProgramaDelegate#consultarActosBuenaConductaSinAcumular(mx.gob.segob.nsjp.dto.sentencia.SentenciaDTO)
	 */
	@Override
	public List<ActoBuenaConductaDTO> consultarActosBuenaConductaSinAcumular(SentenciaDTO sentenciaDTO) throws NSJPNegocioException {
		return asignacionProgramaService.consultarActosBuenaConductaSinAcumular(sentenciaDTO);
	}
	/* (non-Javadoc)
	 * @see mx.gob.segob.nsjp.delegate.programa.AsignacionProgramaDelegate#asignarCentroDetencionaSentencia(mx.gob.segob.nsjp.dto.programas.AsignacionCentroDetencionDTO)
	 */
	@Override
	public AsignacionCentroDetencionDTO asignarCentroDetencionaSentencia(AsignacionCentroDetencionDTO asignacionCentroDetencionDTO)
			throws NSJPNegocioException {
		return asignacionProgramaService.asignarCentroDetencionaSentencia(asignacionCentroDetencionDTO);
	}
	/* (non-Javadoc)
	 * @see mx.gob.segob.nsjp.delegate.programa.AsignacionProgramaDelegate#actualizarCentroDetencion(mx.gob.segob.nsjp.dto.programas.AsignacionCentroDetencionDTO)
	 */
	@Override
	public void actualizarCentroDetencion(
			AsignacionCentroDetencionDTO asignacionCentroDetencionDTO)
			throws NSJPNegocioException {
		asignacionProgramaService.actualizarCentroDetencion(asignacionCentroDetencionDTO);
	}

	/* (non-Javadoc)
	 * @see mx.gob.segob.nsjp.delegate.programa.AsignacionProgramaDelegate#actualizarAsignacionPrograma(mx.gob.segob.nsjp.dto.programas.AsignacionProgramaDTO)
	 */
	@Override
	public void actualizarAsignacionPrograma(
			AsignacionProgramaDTO asignacionProgramaDTO)
			throws NSJPNegocioException {
		asignacionProgramaService.actualizarAsignacionPrograma(asignacionProgramaDTO);
	}

	/* (non-Javadoc)
	 * @see mx.gob.segob.nsjp.delegate.programa.AsignacionProgramaDelegate#actualizarResumenPeriodosAcumulacion(mx.gob.segob.nsjp.dto.sentencia.SentenciaDTO)
	 */
	@Override
	public void actualizarResumenPeriodosAcumulacion(SentenciaDTO sentenciaDTO)
			throws NSJPNegocioException {
		asignacionProgramaService.actualizarResumenPeriodosAcumulacion(sentenciaDTO);
	}
	
	

}
