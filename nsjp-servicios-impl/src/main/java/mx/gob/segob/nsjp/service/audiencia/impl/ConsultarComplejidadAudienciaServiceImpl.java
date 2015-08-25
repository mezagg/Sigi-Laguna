/**
* Nombre del Programa : ConsultarComplejidadAudienciaServiceImpl.java
* Autor                            : adrian
* Compania                    : Ultrasist
* Proyecto                      : NSJP                    Fecha: 29/06/2011
* Marca de cambio        : N/A
* Descripcion General    : Describir el objetivo de la clase de manera breve
* Programa Dependiente  :N/A
* Programa Subsecuente :N/A
* Cond. de ejecucion        :N/A
* Dias de ejecucion          :N/A                             Horario: N/A
*                              MODIFICACIONES
*------------------------------------------------------------------------------
* Autor                       :N/A
* Compania               :N/A
* Proyecto                 :N/A                                 Fecha: N/A
* Modificacion           :N/A
*------------------------------------------------------------------------------
*/
package mx.gob.segob.nsjp.service.audiencia.impl;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import mx.gob.segob.nsjp.comun.enums.excepciones.CodigoError;
import mx.gob.segob.nsjp.comun.excepcion.NSJPNegocioException;
import mx.gob.segob.nsjp.dao.audiencia.AudienciaDAO;
import mx.gob.segob.nsjp.dao.catalogo.ValorDAO;
import mx.gob.segob.nsjp.dto.audiencia.AudienciaDTO;
import mx.gob.segob.nsjp.dto.catalogo.ValorDTO;
import mx.gob.segob.nsjp.model.Audiencia;
import mx.gob.segob.nsjp.model.Valor;
import mx.gob.segob.nsjp.service.audiencia.ConsultarComplejidadAudienciaService;
import mx.gob.segob.nsjp.service.usuario.impl.transformer.ValorTransformer;

/**
 * Describir el objetivo de la clase con punto al final.
 * @version 1.0
 * @author adrian
 *
 */
@Service
@Transactional
public class ConsultarComplejidadAudienciaServiceImpl implements
		ConsultarComplejidadAudienciaService {
	
	public final static Logger logger = 
		Logger.getLogger(ConsultarComplejidadAudienciaServiceImpl.class);
	
	@Autowired
	private AudienciaDAO audDao;
	@Autowired
	private ValorDAO valDao;

	@Override
	public ValorDTO consultarComplejidadAudiencia(AudienciaDTO audienciaDTO)
			throws NSJPNegocioException {
		if (logger.isDebugEnabled())
			logger.debug("/**** SERVICIO PARA CONSULTAR LA COMPLEJIDAD DE UNA AUDIENCIA POR SU TIPO ****/");
		
		/*Verificaci&oacute;n de par�metros*/
		if(audienciaDTO==null)
			throw new NSJPNegocioException(CodigoError.PARAMETROS_INSUFICIENTES);
		else if(audienciaDTO.getId()==null)
			throw new NSJPNegocioException(CodigoError.PARAMETROS_INSUFICIENTES);
		
		/*Operaci&oacute;n*/
		Audiencia aud =audDao.read(audienciaDTO.getId());
		Valor tipo =valDao.read(aud.getTipo().getValorId());
		
		Valor complejidad=valDao.consultarComplejidadTipoAudiencia(tipo);
		
		ValorDTO compDTO;
		/*Transformaci&oacute;n*/
		if(complejidad != null){
			compDTO = new ValorDTO(complejidad.getValorId(), complejidad.getValor(), complejidad.getCampoCatalogo().getNombreCampo());			
		}else{
			compDTO = new ValorDTO();
		}
		
		return compDTO;
	}
	
	public void actualizarComplejidadAudiencia(Long tipoAudiencia, Long complejidad){
		Valor v = valDao.read(tipoAudiencia);
		Long regId = v.getRegistro().getRegistroId();
		int i = valDao.actualizarComplejidadTipoAudiencia(regId, complejidad);
		
	}

	/* (non-Javadoc)
	 * @see mx.gob.segob.nsjp.service.audiencia.ConsultarComplejidadAudienciaService#consultarComplejidadTipoAudiencia(mx.gob.segob.nsjp.dto.catalogo.ValorDTO)
	 */
	@Override
	public ValorDTO consultarComplejidadTipoAudiencia(ValorDTO tipoAudiencia)
			throws NSJPNegocioException {
		
		if (tipoAudiencia == null
				|| tipoAudiencia.getIdCampo() == null
				|| tipoAudiencia.getIdCampo() < 1L){
			throw new NSJPNegocioException(CodigoError.PARAMETROS_INSUFICIENTES);
		}
		
		Valor tipoAudienciaBD = valDao.read(tipoAudiencia.getIdCampo());
		Valor complejidad = valDao.consultarComplejidadTipoAudiencia(tipoAudienciaBD);
		return ValorTransformer.transformar(complejidad);
	}
}
