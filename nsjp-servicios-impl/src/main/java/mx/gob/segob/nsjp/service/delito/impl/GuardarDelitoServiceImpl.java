/**
 * Nombre del Programa : GuardarDelitoServiceImpl.java
 * Autor                            : Jacob Lobaco
 * Compania                         : Ultrasist
 * Proyecto                         : NSJP                    Fecha: 04-jul-2011
 * Marca de cambio        : N/A
 * Descripcion General    : N/A
 * Programa Dependient    :N/A
 * Programa Subsecuente   :N/A
 * Cond. de ejecucion     :N/A
 * Dias de ejecucion      :N/A                                Horario: N/A
 *                              MODIFICACIONES
 *------------------------------------------------------------------------------
 * Autor                            :N/A
 * Compania                         :N/A
 * Proyecto                         :N/A                      Fecha: N/A
 * Modificacion           :N/A
 *------------------------------------------------------------------------------
 */
package mx.gob.segob.nsjp.service.delito.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import mx.gob.segob.nsjp.comun.enums.excepciones.CodigoError;
import mx.gob.segob.nsjp.comun.excepcion.NSJPNegocioException;
import mx.gob.segob.nsjp.dao.catalogo.CatDelitoDAO;
import mx.gob.segob.nsjp.dao.delito.DelitoDAO;
import mx.gob.segob.nsjp.dao.persona.DelitoPersonaDAO;
import mx.gob.segob.nsjp.dto.expediente.DelitoDTO;
import mx.gob.segob.nsjp.dto.expediente.ExpedienteDTO;
import mx.gob.segob.nsjp.model.CatDelito;
import mx.gob.segob.nsjp.model.Delito;
import mx.gob.segob.nsjp.model.DelitoPersona;
import mx.gob.segob.nsjp.model.Expediente;
import mx.gob.segob.nsjp.service.delito.GuardarDelitoService;
import mx.gob.segob.nsjp.service.delito.impl.transform.DelitoTransfromer;
import mx.gob.segob.nsjp.service.expediente.impl.transform.ExpedienteTransformer;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 
 * @version 1.0
 * @author Jacob Lobaco
 */
@Service
@Transactional
public class GuardarDelitoServiceImpl implements GuardarDelitoService {

	/**
	 * Logger de la clase.
	 */
	private final static Logger logger = Logger
			.getLogger(GuardarDelitoServiceImpl.class);

	@Autowired
	private DelitoDAO delitoDAO;
	@Autowired
	private DelitoPersonaDAO delPerDAO;
	@Autowired
	private CatDelitoDAO catDelitoDAO;

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void guardarDelito(List<DelitoDTO> delitosDto,
			ExpedienteDTO expedienteDto) throws NSJPNegocioException {
		if (logger.isDebugEnabled())
			logger.debug("/**** SERVICIO PARA PLANCHAR (ALTA, BAJA, CAMBIO) LOS DELITOS DE UN EXPEDIENTE****/");

		if (delitosDto == null || expedienteDto == null
				|| expedienteDto.getExpedienteId() == null) {
			throw new NSJPNegocioException(CodigoError.PARAMETROS_INSUFICIENTES);
		}
		if (!delitosDto.isEmpty()) {
			// Transformamos los delitos
			List<Delito> delitosVista = new LinkedList<Delito>();
			for (DelitoDTO delitoDTO : delitosDto) {
				// validamos esto porque los delitos no pueden tener estos
				// campos nulos, y antes de lanzar una excepcion en la base
				// lanzamos una excepcion de parametros insuficientes.
				// if (delitoDTO.getEsProbable() == null
				// ||
				if (delitoDTO.getEsPrincipal() == null) {
					throw new NSJPNegocioException(
							CodigoError.PARAMETROS_INSUFICIENTES);
				}
				delitoDTO.setEsProbable(true);
				delitoDTO.setExpedienteDTO(expedienteDto);
				Delito delito = DelitoTransfromer.transformarDelito(delitoDTO);
				delitosVista.add(delito);
			}
			// transformamos el expediente
			Expediente expediente = ExpedienteTransformer
					.transformarExpediente(expedienteDto);

			/*
			 * Validaci�n de los dellitos que se desean eliminar pero contien
			 * relaci�n con DelitoPersona
			 */
			List<Delito> persistidos = delitoDAO
					.obtenerDelitoPorExpediente(expediente.getExpedienteId());

			List<Delito> delitosEliminar = new ArrayList<Delito>();
			List<Delito> delitosActualizar = new ArrayList<Delito>();

			// Buscar el delito de Vista en los de BD Y los que no se encuentren
			// ser�n eliminados
			cliclofuera: for (Delito delitoBD : persistidos) {
				for (Delito delitoVista : delitosVista) {
					// Se encuentra un nuevo delito a actualizar
					if (delitoBD.getDelitoId()
							.equals(delitoVista.getDelitoId())) {
						delitoBD.setEsPrincipal(delitoVista.getEsPrincipal());
						delitosActualizar.add(delitoBD);
						delitosVista.remove(delitoVista);
						continue cliclofuera;
					}
				}
				// Si no se encontro se guarda en la lista de delitos a eliminar
				delitosEliminar.add(delitoBD);
			}

			for (Delito delito : delitosActualizar)
				logger.info("Actualizar delito:" + delito.getDelitoId());
			for (Delito delito : delitosVista)
				logger.info("Nuevos delito:" + delito.getDelitoId());
			for (Delito delito : delitosEliminar)
				logger.info("Eliminar delito:" + delito.getDelitoId());

			// Validar la relacionPersona con Delitos a eliminar
			// Recuperar la lista de delitos
			List<Long> idsDelitos = new ArrayList<Long>();
			for (Delito delito : persistidos)
				idsDelitos.add(delito.getDelitoId());

			// Se obtienen los delitos relacionados
			List<DelitoPersona> listaDelitosRelacionados = delPerDAO
					.consultarDelitosPersonaPorExpedienteIdsDelito(idsDelitos,
							expediente.getExpedienteId());

			// Se buscan entre los delitos que se van a eliminar
			for (Delito delito : delitosEliminar) {
				for (DelitoPersona delitoRelacionado : listaDelitosRelacionados) {
					if (delito.getDelitoId().equals(
							delitoRelacionado.getDelito().getDelitoId())) {
						logger.info(" ERROR DE NEGOCIO: No es posible eliminar este delito porque presenta una relaci�n con Persona"
								+ delito.getDelitoId());
						throw new NSJPNegocioException(
								CodigoError.INFORMACION_PARAMETROS_ERRONEA);
					}
				}
			}

			// Eliminar todos los delitos de BD
			for (Delito delitoEliminar : delitosEliminar) {
				logger.info("Eliminar delito:" + delitoEliminar.getDelitoId());
				delitoDAO.delete(delitoEliminar);
			}
			persistidos.removeAll(delitosEliminar);

			// Actualizar los delitos
			delitoDAO.guardarDelito(delitosActualizar, expediente);

			// Guardar los delitos nuevos
			delitoDAO.guardarDelito(delitosVista, expediente);
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public HashMap<String, Long> guardarDelitoExpedienteInterinstitucional(
			List<DelitoDTO> delitosDTO, ExpedienteDTO expedienteDTO)
			throws NSJPNegocioException {

		HashMap<String, Long> hashDelitosExpedientes = null;

		if (expedienteDTO == null || expedienteDTO.getExpedienteId() == null
				|| expedienteDTO.getExpedienteId() < 0) {
			throw new NSJPNegocioException(CodigoError.PARAMETROS_INSUFICIENTES);
		}
		if (delitosDTO == null || delitosDTO.isEmpty()) {
			return hashDelitosExpedientes;
		}
		hashDelitosExpedientes = new HashMap<String, Long>();

		// Consultar los delitos asociados en el expedientes en BD
		List<String> delitosBDExpediente = new ArrayList<String>();
		List<Delito> delitos = delitoDAO
				.obtenerDelitoPorExpediente(expedienteDTO.getExpedienteId());
		for (Delito delito : delitos) {
			delitosBDExpediente.add(delito.getCatDelito()
					.getClaveInterInstitucional().trim());
		}

		CatDelito catDelitoFiltro = new CatDelito();
		List<CatDelito> catDelitosCatalogo = null;

		for (DelitoDTO delitoDTO : delitosDTO) {
			// Revisar si existe en la lista de los delitos del
			// expediente. Si no existe entonces se registra

			if (delitoDTO.getClaveInterInstitucional() != null
					&& !delitosBDExpediente.contains(delitoDTO
							.getClaveInterInstitucional().trim())) {
				// Buscar si se tiene en el catalogo de la
				// instituci&oacute;n actual
				if (delitoDTO.getClaveInterInstitucional() != null) {
					catDelitoFiltro.setClaveInterInstitucional(delitoDTO
							.getClaveInterInstitucional());
					catDelitosCatalogo = catDelitoDAO
							.consultarCatDelitoPorFilro(catDelitoFiltro);

					// Asociar al expediente
					if (catDelitosCatalogo != null
							&& !catDelitosCatalogo.isEmpty()) {
						Delito delito = new Delito();
						delito.setEsProbable(delitoDTO.getEsProbable());
						delito.setExpediente(new Expediente(expedienteDTO
								.getExpedienteId()));
						delito.setEsPrincipal(delitoDTO.getEsPrincipal());
						delito.setCatDelito(new CatDelito(catDelitosCatalogo
								.get(0).getCatDelitoId()));
						Long delitoId = delitoDAO.create(delito);

						hashDelitosExpedientes.put(
								delitoDTO.getClaveInterInstitucional(),
								delitoId);
						//Guardamos los delitos en la lista para no repetirlos
						delitosBDExpediente.add(delitoDTO
								.getClaveInterInstitucional().trim());
						logger.info("Nuevo Delito creado:" + delitoId);
					} else {
						logger.error("No existe el delito en el catalogo. Clave interinstitucional:"
								+ delitoDTO.getClaveInterInstitucional());
						throw new NSJPNegocioException(
								CodigoError.CLAVE_DELITO_INTERINSTITUCIONAL_INEXISTENTE);
					}
				}
			}
		}
		return hashDelitosExpedientes;
	}
}
