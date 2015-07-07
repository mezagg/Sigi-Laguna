/**
* Nombre del Programa 		: ActualizacionSentenciaServiceImpl.java
* Autor 					: EdgarAT
* Compania 					: Ultrasist
* Proyecto 					: NSJP 								Fecha: 20/12/2012
* Marca de cambio 			: N/A
* Descripcion General 		: Describir el objetivo de la clase de manera breve
* Programa Dependiente 		: N/A
* Programa Subsecuente 		: N/A
* Cond. de ejecucion 		: N/A
* Dias de ejecucion 		: N/A 								Horario: N/A
*                              MODIFICACIONES
*------------------------------------------------------------------------------
* Autor 					: N/A
* Compania 					: N/A
* Proyecto 					: N/A 								Fecha: N/A
* Modificacion 				: N/A
*------------------------------------------------------------------------------
*/
package mx.gob.segob.nsjp.service.sentencia.impl;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import mx.gob.segob.nsjp.comun.enums.excepciones.CodigoError;
import mx.gob.segob.nsjp.comun.enums.expediente.EstatusExpediente;
import mx.gob.segob.nsjp.comun.enums.institucion.Instituciones;
import mx.gob.segob.nsjp.comun.excepcion.NSJPNegocioException;
import mx.gob.segob.nsjp.dao.expediente.NumeroExpedienteDAO;
import mx.gob.segob.nsjp.dao.institucion.ConfInstitucionDAO;
import mx.gob.segob.nsjp.dao.programa.RemisionDAO;
import mx.gob.segob.nsjp.dao.sentencia.SentenciaDAO;
import mx.gob.segob.nsjp.dto.programas.RemisionWSDTO;
import mx.gob.segob.nsjp.dto.sentencia.SentenciaWSDTO;
import mx.gob.segob.nsjp.model.CatTipoRemision;
import mx.gob.segob.nsjp.model.ConfInstitucion;
import mx.gob.segob.nsjp.model.NumeroExpediente;
import mx.gob.segob.nsjp.model.Remision;
import mx.gob.segob.nsjp.model.Sentencia;
import mx.gob.segob.nsjp.model.Valor;
import mx.gob.segob.nsjp.service.infra.impl.transform.sentencia.RemisionWSDTOTransformer;
import mx.gob.segob.nsjp.service.sentencia.ActualizacionSentenciaService;

/**
 * Describir el objetivo de la clase con punto al final.
 * @version 1.0
 * @author EdgarAT
 *
 */
@Service("actualizacionSentenciaService")
@Transactional
public class ActualizacionSentenciaServiceImpl implements
		ActualizacionSentenciaService {
	
	@Autowired
	private SentenciaDAO sentenciaDAO;
	
	@Autowired
	private RemisionDAO remisionDAO;
	
	@Autowired
	private ConfInstitucionDAO confInstitucionDAO;
	
	@Autowired
	private NumeroExpedienteDAO numeroExpedienteDAO;

	/* (non-Javadoc)
	 * @see mx.gob.segob.nsjp.service.sentencia.SentenciaService#actualizarSentencia(mx.gob.segob.nsjp.dto.sentencia.SentenciaWSDTO)
	 */
	@Override
	public void actualizarSentencia(SentenciaWSDTO sentenciaWSDTO) throws NSJPNegocioException{
		String cNumeroCausa = sentenciaWSDTO.getCnumeroCausa();
		String nus = sentenciaWSDTO.getCnus();
		
		if (cNumeroCausa == null
				|| cNumeroCausa.trim().isEmpty()
				|| nus == null
				|| nus.trim().isEmpty()){
			throw new NSJPNegocioException(CodigoError.PARAMETROS_INSUFICIENTES);
		}
		
		Sentencia sentencia = new Sentencia();
		sentencia.setCnumeroCausa(cNumeroCausa);
		sentencia.setCnus(nus);
		sentencia = sentenciaDAO.consultarSentenciaPorNusYCausa(sentencia);
		
		ConfInstitucion institucionActual = confInstitucionDAO.consultarInsitucionActual();
		
		if (institucionActual.getConfInstitucionId().equals(Instituciones.PJ.getValorId())){
			if (sentenciaWSDTO.getIdEstatusNumExp() != null
					&& sentenciaWSDTO.getIdEstatusNumExp() > 0L){
				NumeroExpediente numExp = sentencia.getNumeroExpediente();
				if (!numExp.getEstatus().getValorId().equals(sentenciaWSDTO.getIdEstatusNumExp())){
					numExp.setEstatus(new Valor(sentenciaWSDTO.getIdEstatusNumExp()));
					numeroExpedienteDAO.update(numExp);
				}
			}
		}
		else if (institucionActual.getConfInstitucionId().equals(Instituciones.SSP.getValorId())){
			Map<Long, Boolean> remisionesActualizadas = new HashMap<Long,Boolean>();
			if (sentenciaWSDTO.getRemisions() != null
					&& !sentenciaWSDTO.getRemisions().isEmpty()){
				CatTipoRemision tipoRemision = new CatTipoRemision();
				Remision remFiltro = new Remision();
				remFiltro.setSentencia(sentencia);
				for (RemisionWSDTO remWsdto : sentenciaWSDTO.getRemisions()){
					tipoRemision.setCatTipoRemisionId(remWsdto.getCatTipoRemisionId());
					remFiltro.setCatTipoRemision(tipoRemision);
					Remision remisionBD = remisionDAO.consultaRemisionPorFiltros(remFiltro);
					if (remisionBD == null){
						remisionBD = RemisionWSDTOTransformer.transformaEntity(remWsdto);
						remisionBD.setSentencia(sentencia);
						remisionDAO.create(remisionBD);
					}else{
						actualizarRemision(remWsdto, remisionBD);
					}
					remisionesActualizadas.put(remisionBD.getCatTipoRemision().getCatTipoRemisionId(), Boolean.TRUE);
				}
			}
			actualizarInfoSentencia(sentenciaWSDTO, sentencia);
			eliminarRemisionesSentencia(sentencia.getRemisions(), remisionesActualizadas);
		}
	}
	
	/**
	 * M&eacute;todo que lleva a cabo la actualizaci&oacute;n de las remisiones
	 * que se van a actualizar en la base de datos, sobre remisiones que se reciben
	 * desde otras instituciones.
	 * @param wsdto - Objeto de tipo RemisionWSDTO con la informaci&oacute;n que se 
	 * 				  recibe desde otra instituci&oacute;n
	 * @param entity - Entidad que corresponde con la remisi&oacute;n que se encuentra 
	 * 				   registrada dentro de la base de datos local.
	 */
	private void actualizarRemision(RemisionWSDTO wsdto, Remision entity){
		boolean actualizacionRequerida = Boolean.FALSE;
		if (wsdto.getCumplida() != null){
			entity.setBcumplida(wsdto.getCumplida());
			actualizacionRequerida = Boolean.TRUE;
		}
		if (wsdto.getFechaAutorizacion() != null){
			entity.setdFechaAutorizacion(wsdto.getFechaAutorizacion());
			actualizacionRequerida = Boolean.TRUE;
		}
		if (wsdto.getIdiasAcreditados() != null){
			entity.setIdiasAcreditados(wsdto.getIdiasAcreditados());
			actualizacionRequerida = Boolean.TRUE;
		}
		if (wsdto.getImontoDanioReparado() != null){
			entity.setImontoDanioReparado(wsdto.getImontoDanioReparado());
		}
		if (actualizacionRequerida){
			remisionDAO.update(entity);			
		}
	}
	
	/**
	 * M&eacute;todo que lleva a cabo la modificaci&oacute;n de los atributos 
	 * de la sentencia que se va a actualizar en la base de datos, sobre 
	 * informaci&oacute;n que se recibe desde otra instituci&oacute;n.
	 * @param wsdto - Objeto de tipo SentenciaWSDTO con la informaci&oacute;n que se 
	 * 				  recibe desde otra instituci&oacute;n
	 * @param entity - Entidad que corresponde con la sentencia que se encuentra 
	 * 				   registrada dentro de la base de datos local.
	 */
	private void actualizarInfoSentencia(SentenciaWSDTO wsdto, Sentencia entity){
		boolean actualizacionRequerida = Boolean.FALSE;
		
		if (wsdto.getDfechaCreacion() != null && !wsdto.getDfechaCreacion().equals(entity.getDfechaCreacion())){
			entity.setDfechaCreacion(wsdto.getDfechaCreacion());
			actualizacionRequerida = Boolean.TRUE;
		}
		if (wsdto.getDfechaInicioPena() != null && !wsdto.getDfechaInicioPena().equals(entity.getDfechaInicioPena())){
			entity.setDfechaInicioPena(wsdto.getDfechaInicioPena());
			actualizacionRequerida = Boolean.TRUE;
		}
		if (wsdto.getDfechaFinPena() != null && !wsdto.getDfechaFinPena().equals(entity.getDfechaFinPena())){
			entity.setDfechaFinPena(wsdto.getDfechaFinPena());
			actualizacionRequerida = Boolean.TRUE;
		}
		if (wsdto.getIdEstatusNumExp() != null && wsdto.getIdEstatusNumExp().equals(EstatusExpediente.ENVIADO.getValorId())){
			NumeroExpediente numExp = entity.getNumeroExpediente();
			numExp.setEstatus(new Valor(wsdto.getIdEstatusNumExp()));
			numeroExpedienteDAO.update(numExp);
		}
		if (actualizacionRequerida){
			sentenciaDAO.update(entity);			
		}
	}
	
	/**
	 * M&eacute;todo que lleva a cabo la eliminaci&oacute;n de la base de datos local, de aquellas 
	 * remisiones que no se enviaron desde la sentencia en la instituci&oacute;n que lleva a cabo 
	 * la petici&oacute;n.
	 * @param remisiones - Lista de <code>Remision</code> que se encuentran asociadas a una sentencia.
	 * @param remisionesActualizadas - Mapa compuesto por los identificadores de cada uno de los tipos
	 * 								   de las remisiones que se actualizaron previamente y una bandera 
	 * 								   que especifica si fueron actualizadas o no.
	 */
	private void eliminarRemisionesSentencia(Set<Remision> remisiones, Map<Long, Boolean> remisionesActualizadas){
		if (remisiones != null
				&& !remisiones.isEmpty()
				&& remisionesActualizadas != null){
			for (Remision rem : remisiones){
				if (!remisionesActualizadas.containsKey(rem.getCatTipoRemision().getCatTipoRemisionId())){
					remisionDAO.delete(rem);
				}
			}
		}
	}
}
