/**
 * Nombre del Programa : ActualizaEtapaExpedienteServiceImpl.java
 * Autor                            : Hugo Serrano
 * Compania                    : Ultrasist
 * Proyecto                      : NSJP                    Fecha: 29 Jun 2011
 * Marca de cambio        : N/A
 * Descripcion General    : Servicio para la actualziacion de la etapa de un expediente
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

import java.util.Date;
import java.util.List;

import mx.gob.segob.nsjp.comun.enums.excepciones.CodigoError;
import mx.gob.segob.nsjp.comun.enums.expediente.EtapasExpediente;
import mx.gob.segob.nsjp.comun.enums.expediente.TipoMovimiento;
import mx.gob.segob.nsjp.comun.excepcion.NSJPNegocioException;
import mx.gob.segob.nsjp.dao.catalogo.CatEtapaDAO;
import mx.gob.segob.nsjp.dao.expediente.NumeroExpedienteDAO;
import mx.gob.segob.nsjp.dao.involucrado.InvolucradoDAO;
import mx.gob.segob.nsjp.dto.expediente.ExpedienteDTO;
import mx.gob.segob.nsjp.model.CatEtapa;
import mx.gob.segob.nsjp.model.Expediente;
import mx.gob.segob.nsjp.model.Involucrado;
import mx.gob.segob.nsjp.model.NumeroExpediente;
import mx.gob.segob.nsjp.model.RegistroBitacora;
import mx.gob.segob.nsjp.model.Valor;
import mx.gob.segob.nsjp.service.bitacora.RegistrarBitacoraService;
import mx.gob.segob.nsjp.service.expediente.ActualizarEtapaExpedienteService;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Servicio para la actualziacion de la etapa de un expediente
 * 
 * @version 1.0
 * @author Tattva-IT
 * 
 */

@Service
@Transactional
public class ActualizaEtapaExpedienteServiceImpl implements
		ActualizarEtapaExpedienteService {
	private static final Logger logger = Logger
			.getLogger(ActualizaEtapaExpedienteServiceImpl.class);

	@Autowired
	private NumeroExpedienteDAO numeroExpedienteDAO;
	@Autowired
	private RegistrarBitacoraService registrarBitacoraService;
	@Autowired
	private InvolucradoDAO involucradoDAO;
	@Autowired
	private CatEtapaDAO catEtapaDAO;
	
	@Override
	public void actualizaEtapaExpediente(ExpedienteDTO expDTO,
			EtapasExpediente etExp) throws NSJPNegocioException {
		logger.debug("SERVICIO QUE ACTUALIZA LA ETAPA DE UN EXPEDIENTE");
		if (expDTO == null || expDTO.getNumeroExpedienteId() == null)
			throw new NSJPNegocioException(CodigoError.PARAMETROS_INSUFICIENTES);

		NumeroExpediente in = this.numeroExpedienteDAO.read(expDTO
				.getNumeroExpedienteId());

		logger.debug("ACTUALIZANDO LA ETAPA DEL EXPEDIENTE NUMERO ID "
				+ in.getNumeroExpedienteId() + " CON LA ETAPA " + etExp);

		in.setEtapa(new Valor(etExp.getValorId()));
		this.numeroExpedienteDAO.update(in);

		RegistroBitacora regBitEta = new RegistroBitacora();

		regBitEta.setFechaInicio(new Date());
		regBitEta.setTipoMovimiento(new Valor(
				TipoMovimiento.CAMBIO_DE_ETAPA_DE_EXPEDIENTE.getValorId()));
		regBitEta.setNuevo(String.valueOf(etExp.getValorId()));
		regBitEta.setNumeroExpediente(in);
		registrarBitacoraService
				.registrarMovimientoDeExpedienteEnBitacora(regBitEta);
	}

	public void actualizarEtapaInvolucradoExpediente(Long involucradoId,
			Long etapaValorId) throws NSJPNegocioException {

		logger.debug("SERVICIO QUE ACTUALIZA LA ETAPA DEL INVOLUCRADO Y EXPEDIENTE");
		logger.debug("involucradoId:"+involucradoId+"-etapaValorId:"+etapaValorId);
		
		if (involucradoId == null || involucradoId <= 0 || etapaValorId == null
				|| etapaValorId <= 0) {
			throw new NSJPNegocioException(CodigoError.PARAMETROS_INSUFICIENTES);
		}

		//Actualizar la etapa del involucrado
		Involucrado involucradoBD = involucradoDAO.read(involucradoId);
		CatEtapa filtro = new CatEtapa();
		filtro.setCatEtapaValor(new Valor(etapaValorId));
		List<CatEtapa> catEtapasBD = catEtapaDAO.consultarEtapaPorFiltro(filtro);
		
		if (involucradoBD == null || catEtapasBD.isEmpty()
				|| catEtapasBD.get(0) == null) {
			throw new NSJPNegocioException(CodigoError.PARAMETROS_INSUFICIENTES);
		}
		
		CatEtapa catEtapaBD = catEtapasBD.get(0); 
		involucradoBD.setEtapaInvolucrado(catEtapaBD);
		involucradoDAO.update(involucradoBD);
		
		// Actualizar la etapa del expediente
		Expediente expedienteBD = involucradoBD.getExpediente();
		Long etapaMaximaId = involucradoDAO
				.consultarEtapaMaximaInvolucradosDelExpediente(expedienteBD
						.getExpedienteId());

		//Consultar los numeros de Expediente a actualizar
		List<NumeroExpediente> numerosExpediente = numeroExpedienteDAO
				.consultarNumeroExpedientesXExpediente(expedienteBD
						.getExpedienteId());
		if (!numerosExpediente.isEmpty()) {
			CatEtapa catEtapaNueva = catEtapaDAO.read(etapaMaximaId);
			for (NumeroExpediente numeroExpediente : numerosExpediente) {
				boolean requiereCambio =false;
				
				//Se valida si es nueva etapa
				if(numeroExpediente.getCatEtapa()==null || numeroExpediente.getCatEtapa().getCatEtapaId()==null){
					requiereCambio = true;
				}else{
					//Si hay un cambio de etapa, se registra el cambio y en bitacora 
					if(!numeroExpediente.getCatEtapa().getCatEtapaId().equals(catEtapaNueva.getCatEtapaId())){
						requiereCambio = true;
					}
				}
				
				if(requiereCambio){
					//Cambio de etapa
					numeroExpediente.setCatEtapa(catEtapaNueva);
					numeroExpedienteDAO.update(numeroExpediente);
					logger.info("Etapa actualizada del expediente:"
							+ numeroExpediente.getNumeroExpedienteId() + "-"
							+ numeroExpediente.getNumeroExpediente());
					//Registro en Bitacora
					RegistroBitacora regBitEta = new RegistroBitacora();
					regBitEta.setFechaInicio(new Date());
					regBitEta.setTipoMovimiento(new Valor(
							TipoMovimiento.CAMBIO_DE_ETAPA_DE_EXPEDIENTE.getValorId()));
					regBitEta.setNuevo(String.valueOf(catEtapaNueva.getCatEtapaValor().getValorId()));
					regBitEta.setNumeroExpediente(numeroExpediente);
					registrarBitacoraService
							.registrarMovimientoDeExpedienteEnBitacora(regBitEta);
					logger.info("Se registra en Bitacora :"
							+ numeroExpediente.getNumeroExpedienteId() + "-"
							+ catEtapaNueva.getCatEtapaValor().getValor());
				}
			}
		}
	}
	
	
}
