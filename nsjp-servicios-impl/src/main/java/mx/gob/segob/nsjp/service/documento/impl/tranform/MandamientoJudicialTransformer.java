/**
 * Nombre del Programa : MandamientoJudicialTransformer.java
 * Autor                            : Emigdio
 * Compania                    : Ultrasist
 * Proyecto                      : NSJP                    Fecha: 24/08/2011
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
package mx.gob.segob.nsjp.service.documento.impl.tranform;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import mx.gob.segob.nsjp.dto.catalogo.ValorDTO;
import mx.gob.segob.nsjp.dto.configuracion.ConfInstitucionDTO;
import mx.gob.segob.nsjp.dto.documento.MandamientoDTO;
import mx.gob.segob.nsjp.dto.expediente.DelitoPersonaDTO;
import mx.gob.segob.nsjp.dto.mandamiento.MandamientoPersonaDTO;
import mx.gob.segob.nsjp.model.DelitoPersona;
import mx.gob.segob.nsjp.model.JerarquiaOrganizacional;
import mx.gob.segob.nsjp.model.Mandamiento;
import mx.gob.segob.nsjp.model.MandamientoPersona;
import mx.gob.segob.nsjp.model.Resolutivo;
import mx.gob.segob.nsjp.service.archivo.impl.transform.ArchivoDigitalTransformer;
import mx.gob.segob.nsjp.service.audiencia.impl.transform.ResolutivoTransformer;
import mx.gob.segob.nsjp.service.catalogo.impl.transform.CatalogoTransformer;
import mx.gob.segob.nsjp.service.delito.impl.transform.DelitoPersonaTransfromer;
import mx.gob.segob.nsjp.service.forma.impl.transform.FormaTransformer;
import mx.gob.segob.nsjp.service.mandamiento.impl.MandamientoPersonaTransformer;

/**
 * Clase de transformaciones para el mandamiento judicial
 * 
 * @version 1.0
 * @author Emigdio
 * 
 */
public class MandamientoJudicialTransformer {

	/**
	 * Tranforma un objeto del tipo MandamientoDTO en su equivalente objeto del
	 * modelo de entidades de BD
	 * 
	 * @param src
	 * @return
	 */
	public static Mandamiento transformarMandamientoDTO(MandamientoDTO src) {

		if (src == null) {
			return null;
		}

		Mandamiento dest = transformarMandamientoBasico(src);

		if (src.getResolutivo() != null) {
			dest.setResolutivo(new Resolutivo());
			dest.getResolutivo().setResolutivoId(
					src.getResolutivo().getResolutivoId());
		}

		if (src.getDelitosPersona() != null
				&& !src.getDelitosPersona().isEmpty()) {

			Set<DelitoPersona> delitosEntity = new HashSet<DelitoPersona>(0);

			for (DelitoPersonaDTO dto : src.getDelitosPersona()) {
				delitosEntity.add(DelitoPersonaTransfromer.transformar(dto));
			}
			dest.setDelitosPersona(delitosEntity);
		}

		if (src.getMandamientosPersona() != null
				&& !src.getMandamientosPersona().isEmpty()) {

			Set<MandamientoPersona> mandamientoPersona = new HashSet<MandamientoPersona>(
					0);

			for (MandamientoPersonaDTO dto : src.getMandamientosPersona()) {
				mandamientoPersona.add(MandamientoPersonaTransformer
						.transformar(dto));
			}
		}

		return dest;
	}

	public static Mandamiento transformarMandamientoBasico(MandamientoDTO src) {

		if (src == null) {
			return null;
		}

		Mandamiento dest = new Mandamiento();

		// Atributos de documento
		dest.setFechaCreacion(src.getFechaCreacion());
		dest.setFolioDocumento(src.getFolioDocumento());
		dest.setEsGuardadoParcial(src.getEsGuardadoParcial());
		dest.setArchivoDigital(ArchivoDigitalTransformer
				.transformarArchivoDigitalDTO(src.getArchivoDigital()));
		if (src.getJerarquiaOrganizacional() != null) {
			JerarquiaOrganizacional jerarquia = new JerarquiaOrganizacional(
					src.getJerarquiaOrganizacional());
			dest.setJerarquiaOrganizacional(jerarquia);
		}

		dest.setForma(FormaTransformer.transformarFormaDTO(src.getFormaDTO()));
		dest.setNombreDocumento(src.getNombreDocumento());
		dest.setTipoDocumento(CatalogoTransformer.transformValor(src
				.getTipoDocumentoDTO()));
		dest.setDescripcion(src.getDescripcion());

		// atributos del mandamiento
		dest.setEstatus(CatalogoTransformer.transformValor(src.getEstatus()));
		dest.setTipoMandamiento(CatalogoTransformer.transformValor(src
				.getTipoMandamiento()));
		dest.setNumeroCausa(src.getNumeroCausa());

		return dest;
	}

	/**
	 * Transforma un objeto del model de entidades de BD a su respectivo
	 * equivalente DTO
	 * 
	 * @param src
	 *            Datos fuente
	 * @return DTO equivalente
	 */
	public static MandamientoDTO transformarMandamiento(Mandamiento src) {

		if (src == null) {
			return null;
		}

		MandamientoDTO dest = transformarMandamientoBasico(src);

		if (src.getResolutivo() != null) {
			dest.setResolutivo(ResolutivoTransformer.transformarResolutivo(src
					.getResolutivo()));
		}

		if (src.getConfInstitucion() != null
				&& src.getConfInstitucion().getConfInstitucionId() != null) {
			dest.setConfInstitucion(new ConfInstitucionDTO(src
					.getConfInstitucion().getConfInstitucionId()));
		}

		if (src.getDelitosPersona() != null
				&& !src.getDelitosPersona().isEmpty()) {
			List<DelitoPersonaDTO> delitosPersona = new ArrayList<DelitoPersonaDTO>();
			for (DelitoPersona dp : src.getDelitosPersona()) {
				delitosPersona.add(DelitoPersonaTransfromer
						.transformarDelitoPersonaDTO(dp));
			}
			dest.setDelitosPersona(delitosPersona);
		}

		if (src.getMandamientosPersona() != null
				&& !src.getMandamientosPersona().isEmpty()) {
			List<MandamientoPersonaDTO> mandamientosPersonaDTO = new ArrayList<MandamientoPersonaDTO>();
			for (MandamientoPersona mandamientoPersona : src
					.getMandamientosPersona()) {
				mandamientosPersonaDTO.add(MandamientoPersonaTransformer
						.transformar(mandamientoPersona));
			}
			dest.setMandamientosPersona(mandamientosPersonaDTO);
		}

		return dest;
	}

	public static MandamientoDTO transformarMandamientoBasico(Mandamiento src) {

		if (src == null) {
			return null;
		}

		MandamientoDTO dest = new MandamientoDTO();

		// Atributos de documento
		dest.setDocumentoId(src.getDocumentoId());
		dest.setFechaCreacion(src.getFechaCreacion());
		dest.setFolioDocumento(src.getFolioDocumento());
		dest.setEsGuardadoParcial(src.getEsGuardadoParcial());
		dest.setArchivoDigital(ArchivoDigitalTransformer
				.transformarArchivoDigital(src.getArchivoDigital()));
		if (src.getJerarquiaOrganizacional() != null) {
			dest.setJerarquiaOrganizacional(src.getJerarquiaOrganizacional()
					.getJerarquiaOrganizacionalId());
		}

		//
		if (src.getTipoDocumento() != null
				&& src.getTipoDocumento().getValorId() != null) {
			dest.setTipoDocumentoDTO(new ValorDTO(src.getTipoDocumento()
					.getValorId()));
		}
		dest.setFormaDTO(FormaTransformer.transformarForma(src.getForma()));
		dest.setNombreDocumento(src.getNombreDocumento());
		dest.setDescripcion(src.getDescripcion());

		// Atributos del mandamiento
		dest.setEstatus(CatalogoTransformer.transformValor(src.getEstatus()));
		dest.setTipoMandamiento(CatalogoTransformer.transformValor(src
				.getTipoMandamiento()));
		dest.setNumeroCausa(src.getNumeroCausa());

		return dest;
	}

	public static List<MandamientoDTO> transformarMandamientos(
			List<Mandamiento> src) {

		List<MandamientoDTO> loMandamientos = new ArrayList<MandamientoDTO>();

		if (src == null)
			return null;
		else {
			for (Mandamiento loMandamiento : src) {
				loMandamientos.add(transformarMandamiento(loMandamiento));
			}
			return loMandamientos;
		}
	}
}
