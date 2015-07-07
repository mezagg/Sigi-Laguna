/**
 * Nombre del Programa : ConsultarTipoSolicitudAudienciaServiceImpl.java
 * Autor                            : GustavoBP
 * Compania                    : Ultrasist
 * Proyecto                      : NSJP                    Fecha: 20/10/2011
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
package mx.gob.segob.nsjp.service.solicitud.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import mx.gob.segob.nsjp.comun.enums.audiencia.TipoAudiencia;
import mx.gob.segob.nsjp.comun.enums.catalogo.EntidadFederativa;
import mx.gob.segob.nsjp.comun.enums.configuracion.Parametros;
import mx.gob.segob.nsjp.comun.enums.excepciones.CodigoError;
import mx.gob.segob.nsjp.comun.excepcion.NSJPNegocioException;
import mx.gob.segob.nsjp.dao.catalogo.ValorDAO;
import mx.gob.segob.nsjp.dao.parametro.ParametroDAO;
import mx.gob.segob.nsjp.dto.catalogo.CatalogoDTO;
import mx.gob.segob.nsjp.model.Parametro;
import mx.gob.segob.nsjp.model.Valor;
import mx.gob.segob.nsjp.service.solicitud.ConsultarTipoSolicitudAudienciaService;

import org.apache.commons.lang.math.NumberUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Describir el objetivo de la clase con punto al final.
 * 
 * @version 1.0
 * @author GustavoBP
 * 
 */
@Service
public class ConsultarTipoSolicitudAudienciaServiceImpl implements
		ConsultarTipoSolicitudAudienciaService {

	private final static Logger logger = Logger
			.getLogger(ConsultarTipoSolicitudAudienciaServiceImpl.class);

	@Autowired
	private ValorDAO valorDAO;
	@Autowired
	private ParametroDAO parametroDAO;

	/**
	 * CONFIGURACION DE GURPOS CORRESPONDIENTE AL EDO. DE ZACATECAS
	 */
	private TipoAudiencia[] auZacGrupoA = {
			TipoAudiencia.CONTROL,
			TipoAudiencia.IMPUTACION,
			TipoAudiencia.VINCULACION,
			TipoAudiencia.CATEO,
			TipoAudiencia.APREHENSION,
			TipoAudiencia.AMPLIACION_DE_PLAZO_DE_CIERRE_DEINVESTIGACION,
			TipoAudiencia.AMPLIACION_DE_TERMINO_CONSTITUCIONAL,
			TipoAudiencia.ANTICIPO_DE_PRUEBA_URGENTE,
			TipoAudiencia.AUTORIZACION_JUDICIAL_PARA_PRACTICA_URGENTE_DE_PRUEBA_PERICIAL,
			TipoAudiencia.COMPARECENCIA_ESPONTANEA,
			TipoAudiencia.SOLICITUD_DE_EXTRACCION_DE_MUESTRAS_CORPORALES,
			TipoAudiencia.ORDEN_DE_COMPARECENCIA,
			TipoAudiencia.IMPUGNACION_DE_DETERMINACION_MINISTERIAL };

	private List<TipoAudiencia> audienciaZacGrupoA = Arrays.asList(auZacGrupoA);

	private TipoAudiencia[] auZacGrupoB = { TipoAudiencia.ABREVIADO };

	private List<TipoAudiencia> audienciaZacGrupoB = Arrays.asList(auZacGrupoB);

	private TipoAudiencia[] auZacGrupoC = { TipoAudiencia.INTERMEDIA };

	private List<TipoAudiencia> audienciaZacGrupoC = Arrays.asList(auZacGrupoC);

	private TipoAudiencia[] auZacGrupoD = { TipoAudiencia.JUICIO_ORAL,
			TipoAudiencia.INDIVIDUALIZACION_DE_SANCION, TipoAudiencia.LECTURA,
			TipoAudiencia.FALLO_DELIBERATORIO };

	private List<TipoAudiencia> audienciaZacGrupoD = Arrays.asList(auZacGrupoD);

	private TipoAudiencia[] auZacGrupoE = { TipoAudiencia.SSP,
			TipoAudiencia.VERIFICACION_SSP, TipoAudiencia.MASC,
			TipoAudiencia.VERIFICACION_MASC,
			TipoAudiencia.APROBACION_DE_CONVENIO };

	private List<TipoAudiencia> audienciaZacGrupoE = Arrays.asList(auZacGrupoE);

	private TipoAudiencia[] auZacGrupoF = { TipoAudiencia.EJECUCION };

	private List<TipoAudiencia> audienciaZacGrupoF = Arrays.asList(auZacGrupoF);

	private TipoAudiencia[] auZacGrupoG = { TipoAudiencia.OTRO_TIPO_DE_AUDIENCIA };

	private List<TipoAudiencia> audienciaZacGrupoG = Arrays.asList(auZacGrupoG);

	/**
	 * CONFIGURACION DE GURPOS CORRESPONDIENTE AL EDO. DE YUCATAN
	 */

	private TipoAudiencia[] auYucGrupoA = { TipoAudiencia.CONTROL,
			TipoAudiencia.IMPUTACION, TipoAudiencia.VINCULACION };
	private List<TipoAudiencia> audienciaYucGrupoA = Arrays.asList(auYucGrupoA);

	private TipoAudiencia[] auYucGrupoB = { TipoAudiencia.ABREVIADO };
	private List<TipoAudiencia> audienciaYucGrupoB = Arrays.asList(auYucGrupoB);

	private TipoAudiencia[] auYucGrupoC = { TipoAudiencia.INTERMEDIA };
	private List<TipoAudiencia> audienciaYucGrupoC = Arrays.asList(auYucGrupoC);

	private TipoAudiencia[] auYucGrupoD = { TipoAudiencia.JUICIO_ORAL,
			TipoAudiencia.INDIVIDUALIZACION_DE_SANCION };
	private List<TipoAudiencia> audienciaYucGrupoD = Arrays.asList(auYucGrupoD);

	private TipoAudiencia[] auYucGrupoE = { TipoAudiencia.LECTURA,
			TipoAudiencia.SSP, TipoAudiencia.VERIFICACION_SSP,
			TipoAudiencia.MASC, TipoAudiencia.VERIFICACION_MASC };
	private List<TipoAudiencia> audienciaYucGrupoE = Arrays.asList(auYucGrupoE);

	private TipoAudiencia[] auYucGrupoF = { TipoAudiencia.EJECUCION };
	private List<TipoAudiencia> audienciaYucGrupoF = Arrays.asList(auYucGrupoF);

	private TipoAudiencia[] auYucGrupoG = { TipoAudiencia.CATEO,
			TipoAudiencia.APREHENSION };
	private List<TipoAudiencia> audienciaYucGrupoG = Arrays.asList(auYucGrupoG);

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * mx.gob.segob.nsjp.service.solicitud.ConsultarTipoSolicitudAudienciaService
	 * #consultarTipoSolicitudAudienciaSiguientes(mx.gob.segob.nsjp.comun.enums.
	 * audiencia.TipoAudiencia)
	 */
	@Override
	public List<CatalogoDTO> consultarTipoSolicitudAudienciaSiguientes(
			TipoAudiencia audienciaActual) throws NSJPNegocioException {

		logger.info(" Servicio :: consultarTipoSolicitudAudienciaSiguientes - AudienciaActual:"
				+ audienciaActual);

		if (audienciaActual == null) {
			throw new NSJPNegocioException(CodigoError.PARAMETROS_INSUFICIENTES);
		}

		Parametro entidadDespliegue = parametroDAO
				.obtenerPorClave(Parametros.ENTIDAD_FEDERATIVA_DESPLIEGUE);

		if (entidadDespliegue == null
				|| NumberUtils.toLong(entidadDespliegue.getValor(), 0L) <= 0L) {
			throw new NSJPNegocioException(
					CodigoError.INFORMACION_PARAMETROS_ERRONEA);
		}

		Long idEntidadFederativa = NumberUtils.toLong(entidadDespliegue
				.getValor());
		Set<CatalogoDTO> tiposSolicitudes = new HashSet<CatalogoDTO>();

		if (idEntidadFederativa
				.equals(EntidadFederativa.ZACATECAS.getValorId())) {

			// Al Grupo A => A, C, E, G Excepto Acutal
			if (audienciaZacGrupoA.contains(audienciaActual)) {
				tiposSolicitudes.addAll(this
						.convertEnumToCatalogo(audienciaZacGrupoA));
				tiposSolicitudes.addAll(this
						.convertEnumToCatalogo(audienciaZacGrupoC));
				tiposSolicitudes.addAll(this
						.convertEnumToCatalogo(audienciaZacGrupoE));
				tiposSolicitudes.addAll(this
						.convertEnumToCatalogo(audienciaZacGrupoG));
				// tiposSolicitudes.remove(new
				// CatalogoDTO(audienciaActual.getValorId(),
				// audienciaActual.toString()));
				return new ArrayList<CatalogoDTO>(tiposSolicitudes);
			}

			// Al Grupo B => B, E, F, G
			if (audienciaZacGrupoB.contains(audienciaActual)) {
				tiposSolicitudes.addAll(this
						.convertEnumToCatalogo(audienciaZacGrupoB));
				tiposSolicitudes.addAll(this
						.convertEnumToCatalogo(audienciaZacGrupoE));
				tiposSolicitudes.addAll(this
						.convertEnumToCatalogo(audienciaZacGrupoF));
				tiposSolicitudes.addAll(this
						.convertEnumToCatalogo(audienciaZacGrupoG));
				return new ArrayList<CatalogoDTO>(tiposSolicitudes);
			}

			// Al Grupo C => C, D, G
			if (audienciaZacGrupoC.contains(audienciaActual)) {
				tiposSolicitudes.addAll(this
						.convertEnumToCatalogo(audienciaZacGrupoC));
				tiposSolicitudes.addAll(this
						.convertEnumToCatalogo(audienciaZacGrupoD));
				tiposSolicitudes.addAll(this
						.convertEnumToCatalogo(audienciaZacGrupoG));
				return new ArrayList<CatalogoDTO>(tiposSolicitudes);
			}

			// Al Grupo D => D, E, F, G
			if (audienciaZacGrupoD.contains(audienciaActual)) {
				tiposSolicitudes.addAll(this
						.convertEnumToCatalogo(audienciaZacGrupoD));
				tiposSolicitudes.addAll(this
						.convertEnumToCatalogo(audienciaZacGrupoE));
				tiposSolicitudes.addAll(this
						.convertEnumToCatalogo(audienciaZacGrupoF));
				tiposSolicitudes.addAll(this
						.convertEnumToCatalogo(audienciaZacGrupoG));
				return new ArrayList<CatalogoDTO>(tiposSolicitudes);
			}

			// Al Grupo E => E, F, G
			if (audienciaZacGrupoE.contains(audienciaActual)) {
				tiposSolicitudes.addAll(this
						.convertEnumToCatalogo(audienciaZacGrupoE));
				tiposSolicitudes.addAll(this
						.convertEnumToCatalogo(audienciaZacGrupoF));
				tiposSolicitudes.addAll(this
						.convertEnumToCatalogo(audienciaZacGrupoG));
				return new ArrayList<CatalogoDTO>(tiposSolicitudes);
			}

			// Al Grupo F => F, G
			if (audienciaZacGrupoF.contains(audienciaActual)) {
				tiposSolicitudes.addAll(this
						.convertEnumToCatalogo(audienciaZacGrupoF));
				tiposSolicitudes.addAll(this
						.convertEnumToCatalogo(audienciaZacGrupoG));
				return new ArrayList<CatalogoDTO>(tiposSolicitudes);
			}

			// Al Grupo G => A, B, C, D, E, F, G
			if (audienciaZacGrupoG.contains(audienciaActual)) {
				tiposSolicitudes.addAll(this
						.convertEnumToCatalogo(audienciaZacGrupoA));
				tiposSolicitudes.addAll(this
						.convertEnumToCatalogo(audienciaZacGrupoB));
				tiposSolicitudes.addAll(this
						.convertEnumToCatalogo(audienciaZacGrupoC));
				tiposSolicitudes.addAll(this
						.convertEnumToCatalogo(audienciaZacGrupoD));
				tiposSolicitudes.addAll(this
						.convertEnumToCatalogo(audienciaZacGrupoE));
				tiposSolicitudes.addAll(this
						.convertEnumToCatalogo(audienciaZacGrupoF));
				tiposSolicitudes.addAll(this
						.convertEnumToCatalogo(audienciaZacGrupoG));
				return new ArrayList<CatalogoDTO>(tiposSolicitudes);
			}
			
		} else if (idEntidadFederativa.equals(EntidadFederativa.YUCATAN
				.getValorId())) {

			// Al Grupo A => A, C, E, G Excepto Acutal
			if (audienciaYucGrupoA.contains(audienciaActual)) {
				tiposSolicitudes.addAll(this
						.convertEnumToCatalogo(audienciaYucGrupoA));
				tiposSolicitudes.addAll(this
						.convertEnumToCatalogo(audienciaYucGrupoC));
				tiposSolicitudes.addAll(this
						.convertEnumToCatalogo(audienciaYucGrupoE));
				tiposSolicitudes.addAll(this
						.convertEnumToCatalogo(audienciaYucGrupoG));
				tiposSolicitudes.remove(new CatalogoDTO(audienciaActual
						.getValorId(), audienciaActual.toString()));
				return new ArrayList<CatalogoDTO>(tiposSolicitudes);
			}

			// Al Grupo B => B, E, F Excepto Acutal
			if (audienciaYucGrupoB.contains(audienciaActual)) {
				tiposSolicitudes.addAll(this
						.convertEnumToCatalogo(audienciaYucGrupoB));
				tiposSolicitudes.addAll(this
						.convertEnumToCatalogo(audienciaYucGrupoE));
				tiposSolicitudes.addAll(this
						.convertEnumToCatalogo(audienciaYucGrupoF));
				tiposSolicitudes.remove(new CatalogoDTO(audienciaActual
						.getValorId(), audienciaActual.toString()));
				return new ArrayList<CatalogoDTO>(tiposSolicitudes);
			}

			// Al Grupo C => C, D Excepto Acutal
			if (audienciaYucGrupoC.contains(audienciaActual)) {
				tiposSolicitudes.addAll(this
						.convertEnumToCatalogo(audienciaYucGrupoC));
				tiposSolicitudes.addAll(this
						.convertEnumToCatalogo(audienciaYucGrupoD));
				tiposSolicitudes.remove(new CatalogoDTO(audienciaActual
						.getValorId(), audienciaActual.toString()));
				return new ArrayList<CatalogoDTO>(tiposSolicitudes);
			}

			// Al Grupo D => D, E, F Excepto Acutal
			if (audienciaYucGrupoD.contains(audienciaActual)) {
				tiposSolicitudes.addAll(this
						.convertEnumToCatalogo(audienciaYucGrupoD));
				tiposSolicitudes.addAll(this
						.convertEnumToCatalogo(audienciaYucGrupoE));
				tiposSolicitudes.addAll(this
						.convertEnumToCatalogo(audienciaYucGrupoF));
				tiposSolicitudes.remove(new CatalogoDTO(audienciaActual
						.getValorId(), audienciaActual.toString()));
				return new ArrayList<CatalogoDTO>(tiposSolicitudes);
			}

			// Al Grupo E => E, F Excepto Acutal
			if (audienciaYucGrupoE.contains(audienciaActual)) {
				tiposSolicitudes.addAll(this
						.convertEnumToCatalogo(audienciaYucGrupoE));
				tiposSolicitudes.addAll(this
						.convertEnumToCatalogo(audienciaYucGrupoF));
				tiposSolicitudes.remove(new CatalogoDTO(audienciaActual
						.getValorId(), audienciaActual.toString()));
				return new ArrayList<CatalogoDTO>(tiposSolicitudes);
			}

			// Al Grupo F => F Excepto Acutal
			if (audienciaYucGrupoF.contains(audienciaActual)) {
				tiposSolicitudes.addAll(this
						.convertEnumToCatalogo(audienciaYucGrupoF));
				tiposSolicitudes.remove(new CatalogoDTO(audienciaActual
						.getValorId(), audienciaActual.toString()));
				return new ArrayList<CatalogoDTO>(tiposSolicitudes);
			}

			// Al Grupo G => G Excepto Acutal
			if (audienciaYucGrupoG.contains(audienciaActual)) {
				tiposSolicitudes.addAll(this
						.convertEnumToCatalogo(audienciaYucGrupoG));
				tiposSolicitudes.remove(new CatalogoDTO(audienciaActual
						.getValorId(), audienciaActual.toString()));
				return new ArrayList<CatalogoDTO>(tiposSolicitudes);
			}
		}else{

			// Al Grupo A => A, C, E, G Excepto Acutal
			if (audienciaZacGrupoA.contains(audienciaActual)) {
				tiposSolicitudes.addAll(this
						.convertEnumToCatalogo(audienciaZacGrupoA));
				tiposSolicitudes.addAll(this
						.convertEnumToCatalogo(audienciaZacGrupoC));
				tiposSolicitudes.addAll(this
						.convertEnumToCatalogo(audienciaZacGrupoE));
				tiposSolicitudes.addAll(this
						.convertEnumToCatalogo(audienciaZacGrupoG));
				// tiposSolicitudes.remove(new
				// CatalogoDTO(audienciaActual.getValorId(),
				// audienciaActual.toString()));
				return new ArrayList<CatalogoDTO>(tiposSolicitudes);
			}

			// Al Grupo B => B, E, F, G
			if (audienciaZacGrupoB.contains(audienciaActual)) {
				tiposSolicitudes.addAll(this
						.convertEnumToCatalogo(audienciaZacGrupoB));
				tiposSolicitudes.addAll(this
						.convertEnumToCatalogo(audienciaZacGrupoE));
				tiposSolicitudes.addAll(this
						.convertEnumToCatalogo(audienciaZacGrupoF));
				tiposSolicitudes.addAll(this
						.convertEnumToCatalogo(audienciaZacGrupoG));
				return new ArrayList<CatalogoDTO>(tiposSolicitudes);
			}

			// Al Grupo C => C, D, G
			if (audienciaZacGrupoC.contains(audienciaActual)) {
				tiposSolicitudes.addAll(this
						.convertEnumToCatalogo(audienciaZacGrupoC));
				tiposSolicitudes.addAll(this
						.convertEnumToCatalogo(audienciaZacGrupoD));
				tiposSolicitudes.addAll(this
						.convertEnumToCatalogo(audienciaZacGrupoG));
				return new ArrayList<CatalogoDTO>(tiposSolicitudes);
			}

			// Al Grupo D => D, E, F, G
			if (audienciaZacGrupoD.contains(audienciaActual)) {
				tiposSolicitudes.addAll(this
						.convertEnumToCatalogo(audienciaZacGrupoD));
				tiposSolicitudes.addAll(this
						.convertEnumToCatalogo(audienciaZacGrupoE));
				tiposSolicitudes.addAll(this
						.convertEnumToCatalogo(audienciaZacGrupoF));
				tiposSolicitudes.addAll(this
						.convertEnumToCatalogo(audienciaZacGrupoG));
				return new ArrayList<CatalogoDTO>(tiposSolicitudes);
			}

			// Al Grupo E => E, F, G
			if (audienciaZacGrupoE.contains(audienciaActual)) {
				tiposSolicitudes.addAll(this
						.convertEnumToCatalogo(audienciaZacGrupoE));
				tiposSolicitudes.addAll(this
						.convertEnumToCatalogo(audienciaZacGrupoF));
				tiposSolicitudes.addAll(this
						.convertEnumToCatalogo(audienciaZacGrupoG));
				return new ArrayList<CatalogoDTO>(tiposSolicitudes);
			}

			// Al Grupo F => F, G
			if (audienciaZacGrupoF.contains(audienciaActual)) {
				tiposSolicitudes.addAll(this
						.convertEnumToCatalogo(audienciaZacGrupoF));
				tiposSolicitudes.addAll(this
						.convertEnumToCatalogo(audienciaZacGrupoG));
				return new ArrayList<CatalogoDTO>(tiposSolicitudes);
			}

			// Al Grupo G => A, B, C, D, E, F, G
			if (audienciaZacGrupoG.contains(audienciaActual)) {
				tiposSolicitudes.addAll(this
						.convertEnumToCatalogo(audienciaZacGrupoA));
				tiposSolicitudes.addAll(this
						.convertEnumToCatalogo(audienciaZacGrupoB));
				tiposSolicitudes.addAll(this
						.convertEnumToCatalogo(audienciaZacGrupoC));
				tiposSolicitudes.addAll(this
						.convertEnumToCatalogo(audienciaZacGrupoD));
				tiposSolicitudes.addAll(this
						.convertEnumToCatalogo(audienciaZacGrupoE));
				tiposSolicitudes.addAll(this
						.convertEnumToCatalogo(audienciaZacGrupoF));
				tiposSolicitudes.addAll(this
						.convertEnumToCatalogo(audienciaZacGrupoG));
				return new ArrayList<CatalogoDTO>(tiposSolicitudes);
			}
			
		}

		return new ArrayList<CatalogoDTO>(tiposSolicitudes);
	}

	private Set<CatalogoDTO> convertEnumToCatalogo(
			List<TipoAudiencia> listaAudiencias) {
		Set<CatalogoDTO> listaCatalogo = new HashSet<CatalogoDTO>();
		for (TipoAudiencia tipoAudiencia : listaAudiencias) {
			Valor valor = valorDAO.read(tipoAudiencia.getValorId());
			listaCatalogo.add(new CatalogoDTO(valor.getValorId(), valor
					.getValor()));
		}
		return listaCatalogo;
	}
}
