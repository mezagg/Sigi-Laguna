/**
* Nombre del Programa : CalcularParidadNumeroExpedienteServiceImpl.java
* Autor                            : Emigdio
* Compania                    : Ultrasist
* Proyecto                      : NSJP                    Fecha: 06/07/2011
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
package mx.gob.segob.nsjp.service.expediente.impl;

import mx.gob.segob.nsjp.comun.excepcion.NSJPNegocioException;
import mx.gob.segob.nsjp.comun.util.DateUtils;
import mx.gob.segob.nsjp.dao.expediente.ConclusionNumeroExpedienteDAO;
import mx.gob.segob.nsjp.dao.expediente.NumeroExpedienteDAO;
import mx.gob.segob.nsjp.dto.catalogo.ValorDTO;
import mx.gob.segob.nsjp.dto.hecho.ConclusionNumeroExpedienteDTO;
import mx.gob.segob.nsjp.model.ConclusionNumeroExpediente;
import mx.gob.segob.nsjp.model.NumeroExpediente;
import mx.gob.segob.nsjp.model.Valor;
import mx.gob.segob.nsjp.service.expediente.ConclusionNumeroExpedienteService;
import mx.gob.segob.nsjp.service.usuario.impl.transformer.ValorTransformer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Implementación del servicio de negocio para el cálculo de paridad de un expediente
 * @version 1.0
 * @author Emigdio Hernández
 *
 */
@Service
@Transactional
public class ConclusionNumeroExpedienteServiceImpl implements
	ConclusionNumeroExpedienteService {
	
	@Autowired
	ConclusionNumeroExpedienteDAO conclusionNumeroExpedienteDAO;
	@Autowired
	NumeroExpedienteDAO numeroExpedienteDAO;
	
	
	@Override
	public Boolean guardarConclusion(ConclusionNumeroExpedienteDTO conclusion)
			throws NSJPNegocioException {
		ConclusionNumeroExpediente conclusionNumeroExpediente =new ConclusionNumeroExpediente();
		conclusionNumeroExpediente.setFechaConclusion(conclusion.getFechaConclusion());
		conclusionNumeroExpediente.setTipoConclusion(new Valor(conclusion.getTipoConclusion().getIdCampo()));
		conclusionNumeroExpediente.setTipoSubConclusion(conclusion.getTipoSubConclusion().getIdCampo());
		NumeroExpediente numExpe= numeroExpedienteDAO.read(conclusion.getNumeroExpediente());
		if(numExpe==null){
			return Boolean.FALSE;
		}
		conclusionNumeroExpediente.setNumeroExpediente(numExpe);
		conclusionNumeroExpedienteDAO.create(conclusionNumeroExpediente);
		return Boolean.TRUE;
	}
	
	@Override
	public ConclusionNumeroExpedienteDTO buscarConclicionNumeroExpe(Long idNumeroExpe)throws NSJPNegocioException {

		ConclusionNumeroExpediente conNumeroEx=conclusionNumeroExpedienteDAO.obtenerConclusionNumeroExpediente(idNumeroExpe);
		ConclusionNumeroExpedienteDTO dto=new ConclusionNumeroExpedienteDTO();
		dto.setFechaConclusion(conNumeroEx.getFechaConclusion());
		dto.setNumeroExpediente(conNumeroEx.getNumeroExpediente().getNumeroExpedienteId());
		dto.setTipoConclusion(ValorTransformer.transformar(conNumeroEx.getTipoConclusion()));
		dto.setTipoSubConclusion(new ValorDTO(conNumeroEx.getTipoSubConclusion()));
		dto.setFechaConclusionFortmat(DateUtils.formatear(conNumeroEx.getFechaConclusion()));
		return dto;
	}

}
