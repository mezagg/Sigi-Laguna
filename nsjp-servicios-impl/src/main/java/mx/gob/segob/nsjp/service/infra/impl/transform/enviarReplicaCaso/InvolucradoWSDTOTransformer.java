/**
 * Nombre del Programa		:InvolucradoWSDTOTransformer.java
 * Autor                    :AlejandroGA
 * Compania                 :Ultrasist
 * Proyecto                 :NSJP                    Fecha: 15 Feb 2013
 * Marca de cambio			:N/A
 * Descripcion General		:Implementacion para transformar objetos WSDTO a DTO y viceversa
 * 							 relacionados con la replica del caso
 * Programa Dependiente		:N/A
 * Programa Subsecuente		:N/A
 * Cond. de ejecucion		:N/A
 * Dias de ejecucion		:N/A                      Horario: N/A
 *                              MODIFICACIONES
 *------------------------------------------------------------------------------
 * Autor					:N/A
 * Compania					:N/A
 * Proyecto					:N/A                      Fecha: N/A
 * Modificacion				:N/A
 *------------------------------------------------------------------------------
 */
package mx.gob.segob.nsjp.service.infra.impl.transform.enviarReplicaCaso;

import java.util.ArrayList;
import java.util.List;

import mx.gob.segob.nsjp.comun.enums.calidad.Calidades;
import mx.gob.segob.nsjp.dto.catalogo.CatDelitoDTO;
import mx.gob.segob.nsjp.dto.catalogo.ValorDTO;
import mx.gob.segob.nsjp.dto.elemento.CalidadDTO;
import mx.gob.segob.nsjp.dto.expediente.DelitoDTO;
import mx.gob.segob.nsjp.dto.expediente.DelitoWSDTO;
import mx.gob.segob.nsjp.dto.involucrado.AliasInvolucradoDTO;
import mx.gob.segob.nsjp.dto.involucrado.InvolucradoDTO;
import mx.gob.segob.nsjp.dto.involucrado.InvolucradoWSDTO;
import mx.gob.segob.nsjp.dto.organizacion.OrganizacionDTO;
import mx.gob.segob.nsjp.dto.organizacion.OrganizacionWSDTO;
import mx.gob.segob.nsjp.dto.persona.NombreDemograficoDTO;
import mx.gob.segob.nsjp.dto.persona.NombreDemograficoWSDTO;
import mx.gob.segob.nsjp.service.infra.impl.transform.WsTransformer;

/**
 * @author AlejandroGA
 * 
 */
public class InvolucradoWSDTOTransformer {

	private static final Long ES_VIVO = 1L;
	private static final Long ES_MUERTO = 0L;

	/**
	 * TRANSFORMACIONES DE DTO A WSDTO
	 */

	/**
	 * Transforma la lista de InvolucradosDTO a InvolucradosWSDTO ´s
	 * 
	 * @param listaInvolucradosDTO
	 * @return List<InvolucradoWSDTO>
	 */

	public static List<mx.gob.segob.nsjp.ws.cliente.registarreplicacaso.InvolucradoWSDTO> transformarListaInvolucradoDTOAInvolucradoWSDTO(
			List<InvolucradoDTO> listaInvolucradosDTO) {

		if (listaInvolucradosDTO == null || listaInvolucradosDTO.isEmpty()) {
			return null;
		}

		List<mx.gob.segob.nsjp.ws.cliente.registarreplicacaso.InvolucradoWSDTO> listaInvolucradosWSDTO = new ArrayList<mx.gob.segob.nsjp.ws.cliente.registarreplicacaso.InvolucradoWSDTO>();

		for (InvolucradoDTO involucradoDTO : listaInvolucradosDTO) {
			listaInvolucradosWSDTO.add(transformar(involucradoDTO));
		}

		return listaInvolucradosWSDTO;
	}

	/**
	 * Transfroma el InvolucradoDTO a InvolucradoWSDTO
	 * 
	 * @param involucradoDTO
	 * @return InvolucradoWSDTO
	 */
	public static mx.gob.segob.nsjp.ws.cliente.registarreplicacaso.InvolucradoWSDTO transformar(
			InvolucradoDTO involucradoDTO) {

		if (involucradoDTO == null) {
			return null;
		}

		mx.gob.segob.nsjp.ws.cliente.registarreplicacaso.InvolucradoWSDTO involucradoWSDTO = new mx.gob.segob.nsjp.ws.cliente.registarreplicacaso.InvolucradoWSDTO();

		involucradoWSDTO
				.setCalidad(transformar(involucradoDTO.getCalidadDTO()));
		involucradoWSDTO.setTipoPersona(involucradoDTO.getTipoPersona());
		involucradoWSDTO.setFolioElemento(involucradoDTO.getFolioElemento());
		involucradoWSDTO.setDesconocido(involucradoDTO.getDesconocido());
		involucradoWSDTO.setCondicion(involucradoDTO.getCondicion());

		if (involucradoDTO.getNombresDemograficoDTO() != null
				&& !involucradoDTO.getNombresDemograficoDTO().isEmpty()) {
			involucradoWSDTO.getNombresDemograficos().addAll(
					transformarListaNombDemDTOANomDemWSDTO(involucradoDTO
							.getNombresDemograficoDTO()));
		}

		if (involucradoDTO.getDelitosCometidos() != null
				&& !involucradoDTO.getDelitosCometidos().isEmpty()) {

			involucradoWSDTO.getDelitosCometidos().addAll(
					transformarListaDelitoDTOADelitoWSDTO(involucradoDTO
							.getDelitosCometidos()));
		}
		if (involucradoDTO.getAliasInvolucradoDTO() != null
				&& !involucradoDTO.getAliasInvolucradoDTO().isEmpty()) {
			involucradoWSDTO.getAliasInvolucrado().addAll(
					transformarListaAliasInvoDTOAString(involucradoDTO
							.getAliasInvolucradoDTO()));
		}

		if (involucradoDTO.getEsVivo() != null
				&& involucradoDTO.getEsVivo().equals(true)) {
			involucradoWSDTO.setEsVivo(ES_VIVO);
		} else {
			involucradoWSDTO.setEsVivo(ES_MUERTO);
		}

		// Organizacion para Persona Moral
		if (involucradoDTO.getTipoPersona() != null
				&& involucradoDTO.getTipoPersona().equals(0L)) {
			involucradoWSDTO.setOrganizacion(transformar(involucradoDTO
					.getOrganizacionDTO()));
		}

		return involucradoWSDTO;
	}

	/**
	 * Transforma la calidadDTO del involucrado a CalidadWSDTO
	 * 
	 * @param calidadDTO
	 * @return CalidadWSDTO
	 */
	public static mx.gob.segob.nsjp.ws.cliente.registarreplicacaso.CalidadWSDTO transformar(
			CalidadDTO calidadDTO) {
		if (calidadDTO == null) {
			return null;
		}
		mx.gob.segob.nsjp.ws.cliente.registarreplicacaso.CalidadWSDTO calidadWSDTO = new mx.gob.segob.nsjp.ws.cliente.registarreplicacaso.CalidadWSDTO();

		calidadWSDTO.setValorIdCalidad(calidadDTO.getValorIdCalidad()
				.getIdCampo());
		calidadWSDTO.setDescripcionEstadoFisico(calidadDTO
				.getDescripcionEstadoFisico());
		calidadWSDTO.setCalidades(calidadDTO.getCalidades().getValorId());

		return calidadWSDTO;
	}

	public static List<mx.gob.segob.nsjp.ws.cliente.registarreplicacaso.NombreDemograficoWSDTO> transformarListaNombDemDTOANomDemWSDTO(
			List<NombreDemograficoDTO> listaNombreDemograficoDTO) {

		if (listaNombreDemograficoDTO == null
				|| listaNombreDemograficoDTO.isEmpty()) {
			return null;
		}
		List<mx.gob.segob.nsjp.ws.cliente.registarreplicacaso.NombreDemograficoWSDTO> listaNombreDemograficoWSDTO = new ArrayList<mx.gob.segob.nsjp.ws.cliente.registarreplicacaso.NombreDemograficoWSDTO>();

		for (NombreDemograficoDTO nombreDemograficoDTO : listaNombreDemograficoDTO) {
			listaNombreDemograficoWSDTO.add(transformar(nombreDemograficoDTO));
		}
		return listaNombreDemograficoWSDTO;
	}

	public static mx.gob.segob.nsjp.ws.cliente.registarreplicacaso.NombreDemograficoWSDTO transformar(
			NombreDemograficoDTO nomDemograficoDTO) {

		if (nomDemograficoDTO == null) {
			return null;
		}

		mx.gob.segob.nsjp.ws.cliente.registarreplicacaso.NombreDemograficoWSDTO nomDemograficoWSDTO = new mx.gob.segob.nsjp.ws.cliente.registarreplicacaso.NombreDemograficoWSDTO();

		nomDemograficoWSDTO.setNombre(nomDemograficoDTO.getNombre());
		nomDemograficoWSDTO.setApellidoPaterno(nomDemograficoDTO
				.getApellidoPaterno());
		nomDemograficoWSDTO.setApellidoMaterno(nomDemograficoDTO
				.getApellidoMaterno());
		nomDemograficoWSDTO.setCurp(nomDemograficoDTO.getCurp());
		nomDemograficoWSDTO.setRfc(nomDemograficoDTO.getRfc());
		nomDemograficoWSDTO.setFechaNacimiento(WsTransformer
				.transformFecha(nomDemograficoDTO.getFechaNacimiento()));
		nomDemograficoWSDTO.setLugarNacimiento(nomDemograficoDTO
				.getLugarNacimiento());
		nomDemograficoWSDTO.setEdadAproximada(nomDemograficoDTO
				.getEdadAproximada());
		nomDemograficoWSDTO.setEsVerdadero(nomDemograficoDTO.getEsVerdadero());
		nomDemograficoWSDTO.setSexo(nomDemograficoDTO.getSexo());

		if (nomDemograficoDTO.getPaisValorDTO() != null) {
			nomDemograficoWSDTO.setPaisValorId(nomDemograficoDTO
					.getPaisValorDTO().getIdCampo());
		}
		if (nomDemograficoDTO.getMunicipioDTO() != null) {
			nomDemograficoWSDTO.setMunicipioId(nomDemograficoDTO
					.getMunicipioDTO().getMunicipioId());
		}
		if (nomDemograficoDTO.getEntidadFederativaDTO() != null) {
			nomDemograficoWSDTO.setEntidadFederativaId(nomDemograficoDTO
					.getEntidadFederativaDTO().getEntidadFederativaId());
		}

		return nomDemograficoWSDTO;
	}

	public static List<mx.gob.segob.nsjp.ws.cliente.registarreplicacaso.DelitoWSDTO> transformarListaDelitoDTOADelitoWSDTO(
			List<DelitoDTO> listaDelitoDTO) {

		if (listaDelitoDTO == null || listaDelitoDTO.isEmpty()) {
			return null;
		}

		List<mx.gob.segob.nsjp.ws.cliente.registarreplicacaso.DelitoWSDTO> listaDelitoWSDTO = new ArrayList<mx.gob.segob.nsjp.ws.cliente.registarreplicacaso.DelitoWSDTO>();
		for (DelitoDTO delitoDTO : listaDelitoDTO) {
			listaDelitoWSDTO.add(transformar(delitoDTO));
		}
		return listaDelitoWSDTO;
	}

	public static mx.gob.segob.nsjp.ws.cliente.registarreplicacaso.DelitoWSDTO transformar(
			DelitoDTO delitoDTO) {

		if (delitoDTO == null) {
			return null;
		}

		mx.gob.segob.nsjp.ws.cliente.registarreplicacaso.DelitoWSDTO delitoWSDTO = new mx.gob.segob.nsjp.ws.cliente.registarreplicacaso.DelitoWSDTO();

		delitoWSDTO.setEsPrincipal(delitoDTO.getEsPrincipal());
		delitoWSDTO.setEsProbable(delitoDTO.getEsProbable());

		if (delitoDTO.getCatDelitoDTO() != null) {
			delitoWSDTO.setClaveDelito(delitoDTO.getCatDelitoDTO()
					.getClaveDelito());
			delitoWSDTO.setEsGrave(delitoDTO.getCatDelitoDTO().getEsGrave());
			delitoWSDTO.setIdCatDelito(delitoDTO.getCatDelitoDTO()
					.getCatDelitoId());
			delitoWSDTO
					.setNombreDelito(delitoDTO.getCatDelitoDTO().getNombre());
			delitoWSDTO.setClaveInterInstitucional(delitoDTO.getCatDelitoDTO()
					.getClaveInterInstitucional());
		}
		return delitoWSDTO;
	}

	public static List<String> transformarListaAliasInvoDTOAString(
			List<AliasInvolucradoDTO> listaAliasInvolucraDTO) {

		if (listaAliasInvolucraDTO == null || listaAliasInvolucraDTO.isEmpty()) {
			return null;
		}

		List<String> listaAliasInvolucradoString = new ArrayList<String>();

		for (AliasInvolucradoDTO aliasInvolucradoDTO : listaAliasInvolucraDTO) {
			listaAliasInvolucradoString.add(transformar(aliasInvolucradoDTO));
		}

		return listaAliasInvolucradoString;
	}

	public static String transformar(AliasInvolucradoDTO aliasInvolucradoDTO) {

		if (aliasInvolucradoDTO == null) {
			return null;
		}

		String aliasInvolucradoString = aliasInvolucradoDTO.getAlias();

		return aliasInvolucradoString;
	}

	public static mx.gob.segob.nsjp.ws.cliente.registarreplicacaso.OrganizacionWSDTO transformar(
			OrganizacionDTO organizacionDTO) {

		if (organizacionDTO == null) {
			return null;
		}

		mx.gob.segob.nsjp.ws.cliente.registarreplicacaso.OrganizacionWSDTO organizacionWSDTO = new mx.gob.segob.nsjp.ws.cliente.registarreplicacaso.OrganizacionWSDTO();

		organizacionWSDTO.setAreaDeInfluencia(organizacionDTO
				.getAreaDeInfluencia());
		organizacionWSDTO.setDireccionInternet(organizacionDTO
				.getDireccionInternet());
		organizacionWSDTO.setGiro(organizacionDTO.getGiro());
		organizacionWSDTO.setNombreCorto(organizacionDTO.getNombreCorto());
		organizacionWSDTO.setNombreOrganizacion(organizacionDTO
				.getNombreOrganizacion());
		organizacionWSDTO.setNumeroActaConstitutiva(organizacionDTO
				.getNumeroActaConstitutiva());
		organizacionWSDTO.setPropositoCiberespacio(organizacionDTO
				.getPropositoCiberespacio());
		organizacionWSDTO.setRfc(organizacionDTO.getRfc());
		organizacionWSDTO.setTipoCiberespacio(organizacionDTO
				.getTipoCiberespacio());
		organizacionWSDTO.setDescripcionDelictiva(organizacionDTO
				.getDescripcionDelictiva());

		if (organizacionDTO.getValorByComunidadVirtualVal() != null
				&& organizacionDTO.getValorByComunidadVirtualVal().getIdCampo() != null) {
			organizacionWSDTO.setValorByComunidadVirtualVal(organizacionDTO
					.getValorByComunidadVirtualVal().getIdCampo());
		}

		if (organizacionDTO.getValorByOrganizacionFormalVal() != null
				&& organizacionDTO.getValorByOrganizacionFormalVal()
						.getIdCampo() != null) {
			organizacionWSDTO.setValorByOrganizacionFormalVal(organizacionDTO
					.getValorByOrganizacionFormalVal().getIdCampo());
		}

		if (organizacionDTO.getValorBySectorGubernamentalVal() != null
				&& organizacionDTO.getValorBySectorGubernamentalVal()
						.getIdCampo() != null) {
			organizacionWSDTO.setValorBySectorGubernamentalVal(organizacionDTO
					.getValorBySectorGubernamentalVal().getIdCampo());
		}

		if (organizacionDTO.getValorByTipoOrganizacionVal() != null
				&& organizacionDTO.getValorByTipoOrganizacionVal().getIdCampo() != null) {
			organizacionWSDTO.setValorByTipoOrganizacionVal(organizacionDTO
					.getValorByTipoOrganizacionVal().getIdCampo());
		}

		if (organizacionDTO.getCalidadDTO() != null
				&& organizacionDTO.getCalidadDTO().getValorIdCalidad() != null
				&& organizacionDTO.getCalidadDTO().getValorIdCalidad()
						.getIdCampo() != null) {

			mx.gob.segob.nsjp.ws.cliente.registarreplicacaso.CalidadWSDTO calidadWSDTO = new mx.gob.segob.nsjp.ws.cliente.registarreplicacaso.CalidadWSDTO();
			calidadWSDTO.setValorIdCalidad(organizacionDTO.getCalidadDTO()
					.getValorIdCalidad().getIdCampo());
			organizacionWSDTO.setCalidad(calidadWSDTO);
		}

		return organizacionWSDTO;
	}

	/**
	 * TRANSFORMACIONES DE WSDTO A DTO
	 */

	public static List<InvolucradoDTO> transformarInvolucradosWSDTOAInvolucradoDTO(
			List<InvolucradoWSDTO> listaInvolucradosWSDTO) {

		if (listaInvolucradosWSDTO == null || listaInvolucradosWSDTO.isEmpty()) {
			return null;
		}

		List<InvolucradoDTO> listaInvolucradosDTO = new ArrayList<InvolucradoDTO>();
		for (InvolucradoWSDTO involucradoWSDTO : listaInvolucradosWSDTO) {
			listaInvolucradosDTO.add(transformar(involucradoWSDTO));
		}

		return listaInvolucradosDTO;
	}

	public static InvolucradoDTO transformar(InvolucradoWSDTO involucradoWSDTO) {

		if (involucradoWSDTO == null) {
			return null;
		}

		InvolucradoDTO involucradoDTO = new InvolucradoDTO();

		involucradoDTO.setFolioElemento(involucradoWSDTO.getFolioElemento());

		if (involucradoWSDTO.getEsVivo() != null
				&& involucradoWSDTO.getEsVivo().equals(ES_VIVO)) {
			involucradoDTO.setEsVivo(true);
		} else {
			involucradoDTO.setEsVivo(false);
		}

		involucradoDTO
				.setDelitosCometidos(transformarListaDelitoWSDTOADelitoDTO(involucradoWSDTO
						.getDelitosCometidos()));

		if (involucradoWSDTO.getCalidad() != null) {
			final CalidadDTO calidadDTO = new CalidadDTO();
			calidadDTO.setValorIdCalidad(new ValorDTO(involucradoWSDTO
					.getCalidad().getValorIdCalidad()));
			calidadDTO.setDescripcionEstadoFisico(involucradoWSDTO.getCalidad()
					.getDescripcionEstadoFisico());
			calidadDTO.setCalidades(Calidades.getByValor(involucradoWSDTO
					.getCalidad().getValorIdCalidad()));
			involucradoDTO.setCalidadDTO(calidadDTO);
		}
		involucradoDTO.setTipoPersona(involucradoWSDTO.getTipoPersona());
		involucradoDTO.setDesconocido(involucradoWSDTO.getDesconocido());
		involucradoDTO.setCondicion(involucradoWSDTO.getCondicion());

		involucradoDTO
				.setNombresDemograficoDTO(transformarNomDemWSDTOANomDemDTO(involucradoWSDTO
						.getNombresDemograficos()));
		involucradoDTO
				.setAliasInvolucradoDTO(transformarAliasInvoStringAAliasInvoDTO(involucradoWSDTO
						.getAliasInvolucrado()));

		// Persona Moral -con organizacion
		if (involucradoWSDTO.getTipoPersona() != null
				&& involucradoWSDTO.getTipoPersona().equals(0L)) {
			involucradoDTO.setOrganizacionDTO(transformar(involucradoWSDTO
					.getOrganizacion()));
		}

		return involucradoDTO;
	}

	public static List<NombreDemograficoDTO> transformarNomDemWSDTOANomDemDTO(
			List<NombreDemograficoWSDTO> listaNombreDemograficoWSDTO) {

		if (listaNombreDemograficoWSDTO == null
				|| listaNombreDemograficoWSDTO.isEmpty()) {
			return null;
		}

		List<NombreDemograficoDTO> listaNombreDemograficoDTO = new ArrayList<NombreDemograficoDTO>();
		for (NombreDemograficoWSDTO nombreDemograficoWSDTO : listaNombreDemograficoWSDTO) {
			listaNombreDemograficoDTO.add(transformar(nombreDemograficoWSDTO));
		}

		return listaNombreDemograficoDTO;
	}

	public static NombreDemograficoDTO transformar(
			NombreDemograficoWSDTO nombreDemograficoWSDTO) {

		if (nombreDemograficoWSDTO == null) {
			return null;
		}

		NombreDemograficoDTO nombreDemograficoDTO = new NombreDemograficoDTO();

		nombreDemograficoDTO.setNombre(nombreDemograficoWSDTO.getNombre());
		nombreDemograficoDTO.setApellidoPaterno(nombreDemograficoWSDTO
				.getApellidoPaterno());
		nombreDemograficoDTO.setApellidoMaterno(nombreDemograficoWSDTO
				.getApellidoMaterno());

		return nombreDemograficoDTO;
	}

	public static List<AliasInvolucradoDTO> transformarAliasInvoStringAAliasInvoDTO(
			List<String> listaAliasInvolucradoString) {

		if (listaAliasInvolucradoString == null
				|| listaAliasInvolucradoString.isEmpty()) {
			return null;
		}

		List<AliasInvolucradoDTO> listaAliasInvolucradoDTO = new ArrayList<AliasInvolucradoDTO>();
		for (String aliasInvoString : listaAliasInvolucradoString) {
			listaAliasInvolucradoDTO.add(transformar(aliasInvoString));
		}
		return listaAliasInvolucradoDTO;

	}

	public static AliasInvolucradoDTO transformar(String aliasInvoString) {

		if (aliasInvoString == null) {
			return null;
		}
		return new AliasInvolucradoDTO(aliasInvoString);
	}

	public static List<DelitoDTO> transformarListaDelitoWSDTOADelitoDTO(
			List<DelitoWSDTO> listaDelitoWSDTO) {
		if (listaDelitoWSDTO == null || listaDelitoWSDTO.isEmpty()) {
			return null;
		}

		List<DelitoDTO> listaDelitoDTO = new ArrayList<DelitoDTO>();
		for (DelitoWSDTO delitoWSDTO : listaDelitoWSDTO) {
			listaDelitoDTO.add(transformar(delitoWSDTO));
		}
		return listaDelitoDTO;
	}

	public static DelitoDTO transformar(DelitoWSDTO delitoWSDTO) {

		if (delitoWSDTO == null) {
			return null;
		}

		DelitoDTO delitoDTO = new DelitoDTO();

		if (delitoWSDTO.isEsPrincipal() != null) {
			delitoDTO.setEsPrincipal(delitoWSDTO.isEsPrincipal());
		}
		if (delitoWSDTO.isEsProbable() != null) {
			delitoDTO.setEsProbable(delitoWSDTO.isEsProbable());
		}
		if (delitoWSDTO.getIdCatDelito() != null
				&& delitoWSDTO.getIdCatDelito() > 0L) {
			delitoDTO.setCatDelitoDTO(new CatDelitoDTO(delitoWSDTO
					.getIdCatDelito()));
		}
		delitoDTO.setClaveInterInstitucional(delitoWSDTO
				.getClaveInterInstitucional());

		return delitoDTO;
	}

	/**
	 * Recupera los datos de la organizacion asociada al involucrado
	 * 
	 * @param organizacionWSDTO
	 * @return organizacionDTO
	 */
	public static OrganizacionDTO transformar(
			OrganizacionWSDTO organizacionWSDTO) {

		if (organizacionWSDTO == null) {
			return null;
		}

		OrganizacionDTO organizacionDTO = new OrganizacionDTO();

		organizacionDTO.setAreaDeInfluencia(organizacionWSDTO
				.getAreaDeInfluencia());
		organizacionDTO.setDireccionInternet(organizacionWSDTO
				.getDireccionInternet());
		organizacionDTO.setGiro(organizacionWSDTO.getGiro());
		organizacionDTO.setNombreCorto(organizacionWSDTO.getNombreCorto());
		organizacionDTO.setNombreOrganizacion(organizacionWSDTO
				.getNombreOrganizacion());
		organizacionDTO.setNumeroActaConstitutiva(organizacionWSDTO
				.getNumeroActaConstitutiva());
		organizacionDTO.setPropositoCiberespacio(organizacionWSDTO
				.getPropositoCiberespacio());
		organizacionDTO.setRfc(organizacionWSDTO.getRfc());
		organizacionDTO.setTipoCiberespacio(organizacionWSDTO
				.getTipoCiberespacio());
		organizacionDTO.setDescripcionDelictiva(organizacionWSDTO
				.getDescripcionDelictiva());

		if (organizacionWSDTO.getValorByComunidadVirtualVal() != null
				&& organizacionWSDTO.getValorByComunidadVirtualVal() > 0L) {
			organizacionDTO.setValorByComunidadVirtualVal(new ValorDTO(
					organizacionWSDTO.getValorByComunidadVirtualVal()));
		}

		if (organizacionWSDTO.getValorByOrganizacionFormalVal() != null
				&& organizacionWSDTO.getValorByOrganizacionFormalVal() > 0L) {
			organizacionDTO.setValorByOrganizacionFormalVal(new ValorDTO(
					organizacionWSDTO.getValorByOrganizacionFormalVal()));
		}

		if (organizacionWSDTO.getValorBySectorGubernamentalVal() != null
				&& organizacionWSDTO.getValorBySectorGubernamentalVal() > 0L) {
			organizacionDTO.setValorBySectorGubernamentalVal(new ValorDTO(
					organizacionWSDTO.getValorBySectorGubernamentalVal()));
		}

		if (organizacionWSDTO.getValorByTipoOrganizacionVal() != null
				&& organizacionWSDTO.getValorByTipoOrganizacionVal() > 0L) {
			organizacionDTO.setValorByTipoOrganizacionVal(new ValorDTO(
					organizacionWSDTO.getValorByTipoOrganizacionVal()));
		}

		// En esta parte obtenermos el valor de la calidad
		// y lo seteamos como calidades[]
		if (organizacionWSDTO.getCalidad() != null
				&& organizacionWSDTO.getCalidad().getValorIdCalidad() != null) {

			CalidadDTO calidadDTO = new CalidadDTO();
			calidadDTO.setCalidades(Calidades.getByValor(organizacionWSDTO
					.getCalidad().getValorIdCalidad()));
			organizacionDTO.setCalidadDTO(calidadDTO);
		}

		return organizacionDTO;
	}

}
