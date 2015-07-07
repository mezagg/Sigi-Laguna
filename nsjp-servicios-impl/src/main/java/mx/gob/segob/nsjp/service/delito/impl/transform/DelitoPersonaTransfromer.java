/**
* Nombre del Programa : DelitoPersonaTransfromer.java
* Autor               : rgama
* Compania            : Ultrasist
* Proyecto            : NSJP                    Fecha: 23 Jun 2011
* Marca de cambio     : N/A
* Descripcion General : Transfroma el objeto DelitoPersona a DelitoPersonaDTO y viceversa
* Programa Dependiente: N/A
* Programa Subsecuente: N/A
* Cond. de ejecucion  : N/A
* Dias de ejecucion   : N/A                             Horario: N/A
*                              MODIFICACIONES
*------------------------------------------------------------------------------
* Autor               : AlejandroGA
* Compania            : Ultrasist
* Proyecto            : NSJP                     Fecha: 18 Abril 2013
* Modificacion        : Se agrega el campo folioDelitoPersona y se reestructuran
* 						los transformers de DTO a entity y viceversa
*------------------------------------------------------------------------------
*/
package mx.gob.segob.nsjp.service.delito.impl.transform;

import mx.gob.segob.nsjp.dto.expediente.DelitoDTO;
import mx.gob.segob.nsjp.dto.expediente.DelitoPersonaDTO;
import mx.gob.segob.nsjp.model.Delito;
import mx.gob.segob.nsjp.model.DelitoPersona;
import mx.gob.segob.nsjp.model.Involucrado;
import mx.gob.segob.nsjp.service.involucrado.impl.transform.InvolucradoTransformer;
import mx.gob.segob.nsjp.service.usuario.impl.transformer.ValorTransformer;
import mx.gob.segob.nsjp.ws.cliente.mandamiento.DelitoPersonaWSDTO;

/**
 * Transfroma el objeto DelitoPersona a DelitoPersonaDTO y viceversa.
 * @version 1.0
 * @author rgama
 *
 */
public class DelitoPersonaTransfromer {

	
	/**
	 * 
	 * @param delitoDTO
	 * @return
	 */
	public static DelitoDTO transformarDelitoPersona(DelitoPersona delitoPersona) {
		DelitoDTO delitoDTO = new DelitoDTO();
//		delitoDTO.setNombreDelito(delitoPersona.getDelito().getNombreDelito());
		delitoDTO.setDelitoId(delitoPersona.getDelito().getDelitoId());
		delitoDTO.setEsProbable(delitoPersona.getDelito().getEsProbable());
		delitoDTO.setEsPrincipal(delitoPersona.getDelito().getEsPrincipal());
		return delitoDTO;		
	}
	
	
	public static DelitoPersonaDTO transformarDelitoPersonaDTO(
			DelitoPersona delitoPersona) {

		if (delitoPersona == null) {
			return null;
		}

		DelitoPersonaDTO delitoPersonaDTO = new DelitoPersonaDTO();

		delitoPersonaDTO.setDelitoPersonaId(delitoPersona.getDelitoPersonaId());

		delitoPersonaDTO.setVictima(InvolucradoTransformer
				.transformarInvolucradoBasico(delitoPersona.getVictima()));

		delitoPersonaDTO.setProbableResponsable(InvolucradoTransformer
				.transformarInvolucradoBasico(delitoPersona
						.getProbableResponsable()));

		delitoPersonaDTO.setDelito(DelitoTransfromer
				.transformarDelito(delitoPersona.getDelito()));

		delitoPersonaDTO.setBienTutelado(ValorTransformer
				.transformar(delitoPersona.getBienTutelado()));

		delitoPersonaDTO.setFormaParticipacion(ValorTransformer
				.transformar(delitoPersona.getFormaParticipacion()));

		delitoPersonaDTO.setCatDelitoClasificacionId(delitoPersona
				.getCatDelitoClasificacionId());

		delitoPersonaDTO.setCatDelitoLugarId(delitoPersona
				.getCatDelitoLugarId());

		delitoPersonaDTO.setCatDelitoModalidadId(delitoPersona
				.getCatDelitoModalidadId());

		delitoPersonaDTO.setCatDelitoModusId(delitoPersona
				.getCatDelitoModusId());

		delitoPersonaDTO.setCatDelitoCausaId(delitoPersona
				.getCatDelitoCausaId());

		delitoPersonaDTO.setEsActivo(delitoPersona.getEsActivo());

		delitoPersonaDTO.setFolioDelitoPersona(delitoPersona
				.getFolioDelitoPersona());
		
		delitoPersonaDTO.setCatDelitoEstadisticaId(delitoPersona.getCatDelitoEstadisticaId());
		delitoPersonaDTO.setCatDelitoTipoId(delitoPersona.getCatDelitoTipoId());
		delitoPersonaDTO.setCatDelitoCalificacionId(delitoPersona.getCatDelitoCalificacionId());
		delitoPersonaDTO.setCatDelitoConcursoId(delitoPersona.getCatDelitoConcursoId());
		delitoPersonaDTO.setCatDelitoOrdenResId(delitoPersona.getCatDelitoOrdenResId());

		return delitoPersonaDTO;
	}
	
	/**
	 * M&eacute;todo que lleva a cabo la tranformaci&oacute;n de un objeto dto
	 * de tipo DelitoPersonaDTO a un objeto entity de tipo DelitoPersona.
	 * @param dto - El DelitoPersonaDTO a transformar en un entity.
	 * @return entity - El DelitoPersona con la informaci&oacute;n que se obtuvo 
	 * 					a partir del DTO. 
	 */
	public static DelitoPersona transformar(DelitoPersonaDTO dto){
		DelitoPersona entity = null;
		if (dto != null){
			entity = new DelitoPersona();
			entity.setDelitoPersonaId(dto.getDelitoPersonaId());
		}
		return entity;
	}
	
	
	/**
	 * Tranforma asumiendo que el DTO, contiene los ID de: -Probable Responsable
	 * -Victima -Delito Para que sean considerados al momento de persistir la
	 * informacion en la BD.
	 * 
	 * @param delitoPersonaDTO
	 * @return
	 */
	public static DelitoPersona transforma(DelitoPersonaDTO delitoPersonaDTO) {

		if (delitoPersonaDTO == null) {
			return null;
		}
		
		DelitoPersona delitoPersona = new DelitoPersona();

		if (delitoPersonaDTO.getProbableResponsable() != null
				&& delitoPersonaDTO.getProbableResponsable().getElementoId() != null
				&& delitoPersonaDTO.getProbableResponsable().getElementoId() > 0L) {
			Involucrado probRespInvolucrado = new Involucrado(delitoPersonaDTO
					.getProbableResponsable().getElementoId());
			delitoPersona.setProbableResponsable(probRespInvolucrado);

		}

		if (delitoPersonaDTO.getVictima() != null
				&& delitoPersonaDTO.getVictima().getElementoId() != null
				&& delitoPersonaDTO.getVictima().getElementoId() > 0L) {
			Involucrado victimaInvolucrado = new Involucrado(delitoPersonaDTO
					.getVictima().getElementoId());
			delitoPersona.setVictima(victimaInvolucrado);
		}

		if (delitoPersonaDTO != null && delitoPersonaDTO.getDelito() != null
				&& delitoPersonaDTO.getDelito().getDelitoId() > 0L) {
			Delito delito = new Delito(delitoPersonaDTO.getDelito()
					.getDelitoId());
			delitoPersona.setDelito(delito);
		}

		delitoPersona.setBienTutelado(ValorTransformer.transformar(delitoPersonaDTO.getBienTutelado()));

		delitoPersona.setFormaParticipacion(ValorTransformer.transformar(delitoPersonaDTO.getFormaParticipacion()));
		

		delitoPersona.setEsActivo(delitoPersonaDTO.getEsActivo());
		
		delitoPersona.setCatDelitoClasificacionId(delitoPersonaDTO
				.getCatDelitoClasificacionId());
		
		delitoPersona.setCatDelitoLugarId(delitoPersonaDTO
				.getCatDelitoLugarId());
		
		delitoPersona.setCatDelitoModalidadId(delitoPersonaDTO
				.getCatDelitoModalidadId());
		
		delitoPersona.setCatDelitoModusId(delitoPersonaDTO
				.getCatDelitoModusId());
		
		delitoPersona.setCatDelitoCausaId(delitoPersonaDTO
				.getCatDelitoCausaId());

		delitoPersona.setFolioDelitoPersona(delitoPersonaDTO
				.getFolioDelitoPersona());

		return delitoPersona;
	}
	
	
	/**
	 * M&eacute;todo que lleva a cabo la transformaci&oacute;n de un 
	 * DelitoPersona a un DelitoPersonaWSDTO que se utiliza dentro 
	 * del web service para llevar a cabo el env&iacute;o de mandamientos
	 * a otras instituciones.
	 * 
	 * @param dto - DelitoPersona con la informaci&oacute;n que se 
	 * 				enviar&aacute; v&iacute;a web service
	 * 
	 * @return wsdto - Objeto de tipo DelitoPersonaWSDTO con la informaci&oacute;n
	 * 				   que se enviar&aacute; a trav&eacute;s del web service.
	 */
	public static DelitoPersonaWSDTO transformarDPMandamiento (DelitoPersona entity){
		DelitoPersonaWSDTO wsdto = null;
		if (entity != null){
			wsdto = new DelitoPersonaWSDTO();
			if (entity.getProbableResponsable() != null){
				wsdto.setFolioProbableResponsable(entity.getProbableResponsable().getFolioElemento());				
			}
			if (entity.getVictima() != null){
				wsdto.setFolioVictima(entity.getVictima().getFolioElemento());				
			}
			if (entity.getDelito() != null){
				wsdto.setClaveInterIntitucionalDelito(entity.getDelito().getCatDelito().getClaveInterInstitucional());				
			}
		}
		return wsdto;
	}
	
	
	/**
	 * M&eacute;todo que lleva a cabo la transformaci&oacute;n de un 
	 * DelitoPersonaDTO a un DelitoPersonaWSDTO que se utiliza dentro 
	 * del web service para llevar a cabo el env&iacute;o de mandamientos
	 * a otras instituciones.
	 * 
	 * @param dto - DelitoPersonaDTO con la informaci&oacute;n que se 
	 * 				enviar&aacute; v&iacute;a web service
	 * 
	 * @return wsdto - Objeto de tipo DelitoPersonaWSDTO con la informaci&oacute;n
	 * 				   que se enviar&aacute; a trav&eacute;s del web service.
	 */
	
	//TODO AGA pasarlo a su respectuvo transfomer de WDTO
	public static DelitoPersonaWSDTO transformarDPMandamiento(
			DelitoPersonaDTO delitoPersonaDTO) {

		if (delitoPersonaDTO == null) {
			return null;
		}

		DelitoPersonaWSDTO wsdto = new DelitoPersonaWSDTO();

		if (delitoPersonaDTO.getProbableResponsable() != null) {
			wsdto.setFolioProbableResponsable(delitoPersonaDTO
					.getProbableResponsable().getFolioElemento());
		}
		if (delitoPersonaDTO.getVictima() != null) {
			wsdto.setFolioVictima(delitoPersonaDTO.getVictima()
					.getFolioElemento());
		}
		if (delitoPersonaDTO.getDelito() != null
				&& delitoPersonaDTO.getDelito().getCatDelitoDTO() != null) {
			wsdto.setClaveInterIntitucionalDelito(delitoPersonaDTO.getDelito()
					.getCatDelitoDTO().getClaveInterInstitucional());
		}
		return wsdto;
	}

	
	/**
	 * M&eacute;todo que lleva a cabo la transformaci&oacute;n de un 
	 * DelitoPersona a un DelitoPersonaWSDTO que se utiliza dentro 
	 * del web service para llevar a cabo el env&iacute;o de sentencias
	 * a otras instituciones.
	 * 
	 * @param entity - DelitoPersona con la informaci&oacute;n que se 
	 * 				   enviar&aacute; v&iacute;a web service
	 * 
	 * @return wsdto - Objeto de tipo DelitoPersonaWSDTO con la informaci&oacute;n
	 * 				   que se enviar&aacute; a trav&eacute;s del web service.
	 */
	//TODO AGA pasarlo a su respectuvo transfomer de WDTO Y la trasformacion de dto y de dto a wsdto
	public static mx.gob.segob.nsjp.ws.cliente.sentencia.DelitoPersonaWSDTO transformarDPSentencia (DelitoPersona entity){
		
		if(entity == null){
			return null;
		}
		
		mx.gob.segob.nsjp.ws.cliente.sentencia.DelitoPersonaWSDTO wsdto = new mx.gob.segob.nsjp.ws.cliente.sentencia.DelitoPersonaWSDTO();
		
		if (entity.getProbableResponsable() != null) {
			wsdto.setFolioProbableResponsable(entity.getProbableResponsable()
					.getFolioElemento());
		}
		if (entity.getVictima() != null) {
			wsdto.setFolioVictima(entity.getVictima().getFolioElemento());
		}
		if (entity.getDelito() != null) {
			wsdto.setClaveInterIntitucionalDelito(entity.getDelito()
					.getCatDelito().getClaveInterInstitucional());
		}

		return wsdto;
	}
}
