/**
 * Nombre del Programa : ExpedienteTransformer.java
 * Autor                            : cesarAgustin
 * Compania                    : Ultrasist
 * Proyecto                      : NSJP                    Fecha: 18 Apr 2011
 * Marca de cambio        : N/A
 * Descripcion General    : Clase para convertir objetos Expediente a ExpedienteDTO y viceversa
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
package mx.gob.segob.nsjp.service.expediente.impl.transform;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import mx.gob.segob.nsjp.comun.enums.caso.EstatusCaso;
import mx.gob.segob.nsjp.comun.util.DateUtils;
import mx.gob.segob.nsjp.dto.audiencia.AudienciaDTO;
import mx.gob.segob.nsjp.dto.caso.CasoDTO;
import mx.gob.segob.nsjp.dto.catalogo.CatDelitoDTO;
import mx.gob.segob.nsjp.dto.catalogo.CatDiscriminanteDTO;
import mx.gob.segob.nsjp.dto.catalogo.CatDistritoDTO;
import mx.gob.segob.nsjp.dto.catalogo.ValorDTO;
import mx.gob.segob.nsjp.dto.expediente.BitacoraEstatusNumExpedienteDTO;
import mx.gob.segob.nsjp.dto.expediente.BitacoraPermisoExpedienteDTO;
import mx.gob.segob.nsjp.dto.expediente.DelitoDTO;
import mx.gob.segob.nsjp.dto.expediente.ExpedienteDTO;
import mx.gob.segob.nsjp.dto.funcionario.FuncionarioDTO;
import mx.gob.segob.nsjp.dto.institucion.AreaDTO;
import mx.gob.segob.nsjp.dto.usuario.UsuarioDTO;
import mx.gob.segob.nsjp.model.*;
import mx.gob.segob.nsjp.service.audiencia.impl.transform.EventoTransformer;
import mx.gob.segob.nsjp.service.catalogo.impl.transform.CatDiscriminanteTransformer;
import mx.gob.segob.nsjp.service.catalogo.impl.transform.CatDistritoTransformer;
import mx.gob.segob.nsjp.service.catalogo.impl.transform.CatEtapaTransformer;
import mx.gob.segob.nsjp.service.catalogo.impl.transform.CatUIEspecializadaTransformer;
import mx.gob.segob.nsjp.service.delito.impl.transform.DelitoTransfromer;
import mx.gob.segob.nsjp.service.documento.impl.tranform.AvisoDesignacionTransformer;
import mx.gob.segob.nsjp.service.funcionario.impl.transform.FuncionarioTransformer;
import mx.gob.segob.nsjp.service.involucrado.impl.transform.InvolucradoTransformer;
import mx.gob.segob.nsjp.service.objeto.impl.transform.AlmacenTransformer;
import mx.gob.segob.nsjp.service.solicitud.impl.transform.ConfInstitucionTransformer;
import mx.gob.segob.nsjp.service.usuario.impl.transformer.ValorTransformer;

/**
 * Clase para convertir objetos Expediente a ExpedienteDTO y viceversa.
 * 
 * @version 1.0
 * @author cesarAgustin
 * 
 */
public class ExpedienteTransformer {

	/**
	 * Convierte una lista de objetos Expediente a una lista de objetos
	 * ExpedientesDTO.
	 * 
	 * @param expedientes
	 * @return Lista de objetos ExpedientesDTO
	 */
	public static List<ExpedienteDTO> transformarExpediente(
			List<Expediente> expedientes) {
		List<ExpedienteDTO> expedientesDto = new ArrayList<ExpedienteDTO>();

		for (Expediente expediente : expedientes) {
			CasoDTO casoDTO = new CasoDTO();

			ExpedienteDTO expedienteDto = new ExpedienteDTO();
			expedienteDto.setExpedienteId(expediente.getExpedienteId());
			expedienteDto.setNumeroExpediente(expediente.getNumeroExpediente());
            expedienteDto.setNumeroExpedienteId(expediente.getNumeroExpedienteId());
            expedienteDto.setNombreUIE(expediente.getCatUIEspecializada().getNombreUIE());
			if (expediente.getEstatus() != null) {
				expedienteDto.setEstatus(ValorTransformer.transformar(expediente.getEstatus()));
			}
            expedienteDto.setEstatusNumeroExpediente(ValorTransformer.transformar(expediente.getEstatus()));
			
			expedienteDto.setFechaApertura(expediente.getFechaCreacion());
			expedienteDto.setFechaCierre(expediente.getFechaCierre());
			expedienteDto
					.setNarrativa(expediente.getDescNarrativa() != null ? expediente
							.getDescNarrativa() : "");
			if (expediente.getOrigen() != null)
				expedienteDto.setOrigen(new ValorDTO(expediente.getOrigen()
						.getValorId(), expediente.getOrigen().getValor()));



			Iterator<Delito> itDelito = expediente.getDelitos().iterator();
			//Solo el primer delito se mostrará
			if(itDelito.hasNext()){
				expedienteDto.setDelitoPrincipal(DelitoTransfromer.transformarDelito(itDelito.next()));
			}

			if (expediente.getCaso() != null) {
				Caso caso = expediente.getCaso();

				casoDTO.setCasoId(caso.getCasoId());
				if(caso.getEstatus() != null){
					casoDTO.setEstatus(EstatusCaso.values()[caso.getEstatus().intValue()]);
				}
				casoDTO.setFechaApertura(caso.getFechaApertura());
				casoDTO.setFechaCierre(caso.getFechaCierre());
				casoDTO.setNumeroGeneralCaso(caso.getNumeroGeneralCaso());
				casoDTO.setImputado(caso.getImputado());
				casoDTO.setVictima(caso.getVictima());

				expedienteDto.setCasoDTO(casoDTO);
			}
            //expedienteDto.setEstatus(ValorTransformer.transformar(ne.getEstatus()));
			if (expediente.getNumeroExpedientes()!=null) {
				Iterator<NumeroExpediente> itNe = expediente.getNumeroExpedientes().iterator();
				NumeroExpediente ne = null;
				if(itNe.hasNext()){
					ne= itNe.next();
					expedienteDto.setResponsableTramite(FuncionarioTransformer.transformarFuncionario(ne.getFuncionario()));

				}
			}

			Iterator<Elemento> it = expediente.getElementos().iterator();
			Elemento e;
			Involucrado involucrado = null;
			while (it.hasNext()){
				e = it.next();
				if(Involucrado.class.isInstance(e)) {
					expedienteDto.addInvolucradoDTO(InvolucradoTransformer.transformarInvolucradoBasico(involucrado));
				}
			}


			
			if(expediente.getDiscriminante() != null && expediente.getDiscriminante().getCatDiscriminanteId() != null && expediente.getDiscriminante().getClave() != null){
				expedienteDto.setDiscriminante(new CatDiscriminanteDTO(expediente.getDiscriminante().getCatDiscriminanteId(), expediente.getDiscriminante().getClave()));
			}

			expedienteDto.setEsReplicado(expediente.getEsReplicado()==null? false: expediente.getEsReplicado());
			expedientesDto.add(expedienteDto);
		}
		return expedientesDto;
	}

	/**
	 * Convierte un objeto Expediente a un objeto ExpedienteDTO.
	 * 
	 * @param expediente
	 * @return objeto ExpedienteDTO
	 */
	public static ExpedienteDTO transformaExpediente(Expediente expediente) {
		if (expediente == null) {
			return null;
		}
		ExpedienteDTO expedienteDto = new ExpedienteDTO();
		CasoDTO casoDTO = new CasoDTO();

		expedienteDto.setExpedienteId(expediente.getExpedienteId());
		expedienteDto.setNumeroExpediente(expediente.getNumeroExpediente());
		expedienteDto.setNumeroExpedienteId(expediente.getNumeroExpedienteId());
		expedienteDto.setFechaApertura(expediente.getFechaCreacion());
		expedienteDto.setFechaCierre(expediente.getFechaCierre());
		expedienteDto.setNarrativa(expediente.getDescNarrativa());
				
		if (expediente.getEstatus() != null) {
			expedienteDto.setEstatus(new ValorDTO(expediente.getEstatus().getValorId(), 
					expediente.getEstatus().getValor()));
		}
		
		//Agregamos el campo agencias
		if(expediente.getDiscriminante() != null && expediente.getDiscriminante().getCatDiscriminanteId() != null && expediente.getDiscriminante().getClave() != null){
			expedienteDto.setDiscriminante(new CatDiscriminanteDTO(expediente.getDiscriminante().getCatDiscriminanteId(),expediente.getDiscriminante().getClave()));
		}
		
		if (expediente.getOrigen() != null)
			expedienteDto.setOrigen(new ValorDTO(expediente.getOrigen()
					.getValorId(), expediente.getOrigen().getValor()));

		if (expediente.getCaso() != null) {
			Caso caso = expediente.getCaso();

			casoDTO.setCasoId(caso.getCasoId());
			if (caso.getEstatus() != null){
				casoDTO.setEstatus(EstatusCaso.values()[caso.getEstatus().intValue()]);
			}
			casoDTO.setFechaApertura(caso.getFechaApertura());
			casoDTO.setFechaCierre(caso.getFechaCierre());
			casoDTO.setNumeroGeneralCaso(caso.getNumeroGeneralCaso());
			casoDTO.setImputado(caso.getImputado());
			casoDTO.setVictima(caso.getVictima());
			expedienteDto.setCasoDTO(casoDTO);
		}

		if (expediente.getDelitos() != null
				&& !expediente.getDelitos().isEmpty()) {
			final List<DelitoDTO> dels = new ArrayList<DelitoDTO>();
			DelitoDTO delDto = null;
			for (Delito row : expediente.getDelitos()) {
				delDto = new DelitoDTO();
				delDto.setDelitoId(row.getDelitoId());
				// delDto.setNombreDelito(row.getNombreDelito());
				delDto.setEsProbable(row.getEsProbable());
				delDto.setEsPrincipal(row.getEsPrincipal());
				if(row.getCatDelito() != null){
					CatDelitoDTO catDelito = new CatDelitoDTO();
					catDelito.setCatDelitoId(row.getCatDelito().getCatDelitoId());
					catDelito.setNombre(row.getCatDelito().getNombre());
					catDelito.setClaveDelito(row.getCatDelito().getClaveDelito());
					if(row.getCatDelito() != null && row.getCatDelito().getUnidadIEspecializada() != null)
						catDelito.setUnidadIEspecializada(CatUIEspecializadaTransformer.transformarCatUIEspecializada(row.getCatDelito().getUnidadIEspecializada()));
					delDto.setCatDelitoDTO(catDelito);
					if(row.getCatDelito().getClaveInterInstitucional() != null){
						catDelito.setClaveInterInstitucional(row.getCatDelito().getClaveInterInstitucional());
					}
				}
				dels.add(delDto);
				if (row.getEsPrincipal()) {
					expedienteDto.setDelitoPrincipal(delDto);
				}
			}
			expedienteDto.setDelitos(dels);
		}
		if (expediente.getPertenceConfInst() != null)
			expedienteDto.setPertenceConfInst(ConfInstitucionTransformer
					.transformarInstitucion(expediente.getPertenceConfInst()));
		
		expedienteDto.setEsReplicado(expediente.getEsReplicado()==null? false: expediente.getEsReplicado());
		return expedienteDto;
	}

	/**
	 * 
	 * @param expedientes
	 * @return
	 */
	public static List<ExpedienteDTO> transformarExpedientesBasico(
			List<Expediente> expedientes) {
		List<ExpedienteDTO> expedientesDto = new ArrayList<ExpedienteDTO>();

		for (Expediente expediente : expedientes) {

			expedientesDto.add(ExpedienteTransformer
					.transformarExpedienteBasico(expediente));
		}

		return expedientesDto;
	}

	/**
	 * 
	 *
	 * @return
	 */
	public static ExpedienteDTO transformarExpedienteBasico(
			Expediente expedienteIn) {

		if (expedienteIn == null) {
			return null;
		}

		ExpedienteDTO expedienteDto = new ExpedienteDTO();
		
		NumeroExpediente numExpBD = null;
		if (expedienteIn.getNumeroExpedientes() != null
				&& !expedienteIn.getNumeroExpedientes().isEmpty()) {
			numExpBD = expedienteIn.getNumeroExpedientes().iterator().next();
		}
		
		// ------------------------------------------------------------------------------------------------
        // Ojo!!! si se va a realizar una referencia al estatus del expediente, realizarlo por medio de 
        // expedienteDto.getEstatus();
        // Pero si la referencia es al estatus del número de expediente asociado a ese expediente:
        // expedienteDto.getEstatusNumeroExpediente();
        		
		if(numExpBD != null && numExpBD.getEstatus()!=null){
			expedienteDto.setEstatusNumeroExpediente(new ValorDTO(numExpBD.getEstatus().getValorId(),
					numExpBD.getEstatus().getValor()));
		}
		
		if (expedienteIn.getEstatus() != null) {
			expedienteDto.setEstatus(new ValorDTO(expedienteIn.getEstatus().getValorId(), 
					expedienteIn.getEstatus().getValor()));
		}
		
		// ------------------------------------------------------------------------------------------------
		
		// se agrega el responsable
		if (numExpBD != null){
			FuncionarioDTO funcionarioDTO = FuncionarioTransformer.transformarFuncionario(numExpBD.getFuncionario());
			expedienteDto.setResponsableTramite(funcionarioDTO);
		}
		
		expedienteDto.setExpedienteId(expedienteIn.getExpedienteId());
		expedienteDto.setNumeroExpediente(numExpBD != null ? numExpBD
				.getNumeroExpediente() : expedienteIn.getNumeroExpediente());
		expedienteDto
				.setNumeroExpedienteId(numExpBD != null ? numExpBD
						.getNumeroExpedienteId() : expedienteIn
						.getNumeroExpedienteId());
		expedienteDto.setIdNumeroExpedienteBusquedaATP(expedienteIn.getIdNumeroExpedienteBusquedaATP());
		expedienteDto.setFechaApertura(expedienteIn.getFechaCreacion());
		if (expedienteIn.getOrigen() != null)
			expedienteDto.setOrigen(new ValorDTO(expedienteIn.getOrigen()
					.getValorId(), expedienteIn.getOrigen().getValor()));

		if (expedienteIn.getCaso() != null) {
			expedienteDto
					.setCasoDTO(new CasoDTO(expedienteIn.getCaso().getCasoId(),
							expedienteIn.getCaso().getNumeroGeneralCaso()));
			expedienteDto.getCasoDTO().setFechaApertura(expedienteIn.getCaso().getFechaApertura());
		}
		
		//Agregamos el campo agencia
		if (expedienteIn.getDiscriminante() != null
				&& expedienteIn.getDiscriminante().getCatDiscriminanteId() != null
				&& expedienteIn.getDiscriminante().getClave() != null) {

			expedienteDto.setDiscriminante(new CatDiscriminanteDTO(expedienteIn
					.getDiscriminante().getCatDiscriminanteId(), expedienteIn
					.getDiscriminante().getClave(), expedienteIn
					.getDiscriminante().getNombre()));

			//Distrito del discrimiante
			if (expedienteIn.getDiscriminante().getDistrito() != null) {
				CatDistritoDTO catDistritoDTO = CatDistritoTransformer
						.transformarDistritoSimple(expedienteIn
								.getDiscriminante().getDistrito());
				expedienteDto.getDiscriminante().setDistrito(catDistritoDTO);
			}
		}
		
		if(expedienteIn.getFechaCreacion() != null){
			expedienteDto.setStrFechaApertura(DateUtils.formatear( expedienteIn.getFechaCreacion()));	
			expedienteDto.setFechaApertura(expedienteIn.getFechaCreacion());
		}
		
		if (expedienteIn.getPertenceConfInst() != null)
			expedienteDto
					.setPertenceConfInst(ConfInstitucionTransformer
							.transformarInstitucion(expedienteIn
									.getPertenceConfInst()));
		
		expedienteDto.setEsReplicado(expedienteIn.getEsReplicado()==null? false: expedienteIn.getEsReplicado());
		
		return expedienteDto;
	}

	/**
	 * 
	 * @param expedienteDTO
	 * @return
	 */

	public static Expediente transformarExpediente(ExpedienteDTO expedienteDTO) {
		Expediente expediente = new Expediente();
		if (expedienteDTO.getExpedienteId() != null)
			expediente.setExpedienteId(expedienteDTO.getExpedienteId());
		expediente.setFechaCreacion(expedienteDTO.getFechaApertura());
		expediente.setFechaCierre(expedienteDTO.getFechaCierre());
		expediente.setDescNarrativa(expedienteDTO.getNarrativa());
		
		//Agregamos el campo para agencias
		if(expedienteDTO.getDiscriminante() != null && expedienteDTO.getDiscriminante().getCatDiscriminanteId() != null && expedienteDTO.getDiscriminante().getClave() != null){
			expediente.setDiscriminante(new CatDiscriminante(expedienteDTO.getDiscriminante().getCatDiscriminanteId(),expedienteDTO.getDiscriminante().getClave()));
		}
		
		if(expedienteDTO.getOrigen() != null  && expedienteDTO.getOrigen().getIdCampo() != null)
			expediente.setOrigen(new Valor(expedienteDTO.getOrigen().getIdCampo()));
		// Dado los servicios asociarNumExpediente(Expediente, Usuario)
		// es necesario conservar el campo numeroExpediente dentro de las clases
		// ExpedienteDTO y Expediente por lo que en las transformaciones lo
		// agregamos
		expediente.setNumeroExpediente(expedienteDTO.getNumeroExpediente());
		if (expedienteDTO.getEstatus() != null) {
			expediente.setEstatus(new Valor(expedienteDTO.getEstatus().getIdCampo()));
		}
		
		expediente.setEsReplicado(expedienteDTO.getEsReplicado()==null?false:expedienteDTO.getEsReplicado());
		
		return expediente;
	}


	public static Expediente transformarExpedienteBasicoDefensoria(ExpedienteDTO input) {
		Expediente expediente = new Expediente();
		if (input.getExpedienteId() != null){
			expediente.setExpedienteId(input.getExpedienteId());
		}
		if (input.getFechaApertura() != null){
			expediente.setFechaCreacion(input.getFechaApertura());
		}
		if (input.getFechaCierre() != null){
			expediente.setFechaCierre(input.getFechaCierre());
		}
		if (input.getNarrativa() != null){
			expediente.setDescNarrativa(input.getNarrativa());
		}
		if (input.getOrigen() != null && input.getOrigen().getIdCampo() != null
				&& input.getOrigen().getIdCampo() > 0L) {
			expediente.setOrigen(new Valor(input.getOrigen().getIdCampo()));
		}
		if(input.getNumeroExpediente() != null){
			expediente.setNumeroExpediente(input.getNumeroExpediente());
		}
		if (input.getEstatus() != null) {
			expediente.setEstatus(new Valor(input.getEstatus().getIdCampo()));
		}
		expediente.setEsReplicado(input.getEsReplicado()==null? false: input.getEsReplicado());
		
		if (input.getDiscriminante() != null) {
			expediente.setDiscriminante(CatDiscriminanteTransformer
					.transformarCatDiscriminanteDTO(input.getDiscriminante()));
		}
			
		return expediente;
	}


	
	
	/**
	 * 
	 * @param numeroExpediente
	 * @return ExpedienteDTO
	 */
	public static ExpedienteDTO transformarExpediente(
			NumeroExpediente numeroExpediente) {
		if (numeroExpediente == null)
			return null;
		ExpedienteDTO expedienteDto = new ExpedienteDTO();

		expedienteDto.setNumeroExpedienteId(numeroExpediente
				.getNumeroExpedienteId());
		expedienteDto.setNumeroExpediente(numeroExpediente
				.getNumeroExpediente());
				
		if(numeroExpediente != null && numeroExpediente.getFuncionario() != null && numeroExpediente.getFuncionario().getNombreCompleto() != null){
			UsuarioDTO usuario = new UsuarioDTO();
			FuncionarioDTO loFuncionario = new FuncionarioDTO();
			loFuncionario.setNombreCompleto( numeroExpediente.getFuncionario().getNombreCompleto());			
			usuario.setFuncionario(loFuncionario);
			expedienteDto.setUsuario(usuario);
		}
		
		if(numeroExpediente.getJerarquiaOrganizacional() != null )
			expedienteDto.setArea(new AreaDTO(numeroExpediente.getJerarquiaOrganizacional().getJerarquiaOrganizacionalId(), numeroExpediente.getJerarquiaOrganizacional().getNombre()));
		
		if (numeroExpediente.getExpediente() != null) {
			expedienteDto.setExpedienteId(numeroExpediente.getExpediente()
					.getExpedienteId());
			expedienteDto.setFechaApertura(numeroExpediente.getExpediente()
					.getFechaCreacion());
			expedienteDto.setFechaAperturaNumeroExp(numeroExpediente.getFechaApertura());
			if (numeroExpediente.getExpediente().getCaso() != null) {
				Long idCaso = numeroExpediente.getExpediente().getCaso()
						.getCasoId();
				String nCaso = numeroExpediente.getExpediente().getCaso()
						.getNumeroGeneralCaso();
				expedienteDto.setCasoDTO(new CasoDTO(idCaso, nCaso));
			}
			//Agregamos el campo para agencias
			if (numeroExpediente.getExpediente().getDiscriminante() != null
					&& numeroExpediente.getExpediente().getDiscriminante()
							.getCatDiscriminanteId() != null){
				expedienteDto.setDiscriminante(new CatDiscriminanteDTO(numeroExpediente.getExpediente().getDiscriminante().getCatDiscriminanteId(),numeroExpediente.getExpediente().getDiscriminante().getClave()));
			}
		}

		// ------------------------------------------------------------------------------------------------
        // Ojo!!! si se va a realizar una referencia al estatus del expediente, realizarlo por medio de 
        // expedienteDto.getEstatus();
        // Pero si la referencia es al estatus del número de expediente asociado a ese expediente:
        // expedienteDto.getEstatusNumeroExpediente();        
		if(numeroExpediente.getEstatus()!=null){
			expedienteDto.setEstatusNumeroExpediente(new ValorDTO(numeroExpediente.getEstatus().getValorId(), 
					numeroExpediente.getEstatus().getValor()));
		}
		
		if(numeroExpediente.getExpediente()!=null &&
		   numeroExpediente.getExpediente().getEstatus()!=null){
			expedienteDto.setEstatus(new ValorDTO(numeroExpediente.getExpediente().getEstatus().getValorId(), 
					numeroExpediente.getExpediente().getEstatus().getValor()));
		}
		// ------------------------------------------------------------------------------------------------

		if (numeroExpediente.getEtapa() != null) {
			expedienteDto.setEtapa(new ValorDTO(numeroExpediente.getEtapa()
					.getValorId(), numeroExpediente.getEtapa().getValor()));
		}

		if (numeroExpediente.getAlmacen() != null) {
			expedienteDto.setAlmacenDTO(AlmacenTransformer
					.transformarAlmacenBasico(numeroExpediente.getAlmacen()));
		}
		
		return expedienteDto;
	}

	/**
	 * 
	 * @param numeroExpediente
	 * @return ExpedienteDTO
	 */
	public static ExpedienteDTO transformarExpedienteBasico(
			NumeroExpediente numeroExpediente) {
		if (numeroExpediente == null)
			return null;
		ExpedienteDTO expedienteDto = new ExpedienteDTO();
		
		if(numeroExpediente.getJerarquiaOrganizacional() != null){
			expedienteDto.setArea(JerarquiaOrganizacionalTransformer.transformarJerarquiaOrganizacionalArea(numeroExpediente.getJerarquiaOrganizacional()));
		}		
		
		if(numeroExpediente.getExpediente()!=null && numeroExpediente.getExpediente().getOrigen()!=null){
			ValorDTO valorDto = new ValorDTO(numeroExpediente.getExpediente()
					.getOrigen().getValorId(), numeroExpediente.getExpediente()
					.getOrigen().getValor());
			expedienteDto.setOrigen(valorDto);
		}
		
		// ------------------------------------------------------------------------------------------------
        // Ojo!!! si se va a realizar una referencia al estatus del expediente, realizarlo por medio de 
        // expedienteDto.getEstatus();
        // Pero si la referencia es al estatus del número de expediente asociado a ese expediente:
        // expedienteDto.getEstatusNumeroExpediente();
        
		if(numeroExpediente.getEstatus()!=null){
			expedienteDto.setEstatusNumeroExpediente(new ValorDTO(numeroExpediente.getEstatus().getValorId(),
				numeroExpediente.getEstatus().getValor()));
		}
		
		if (numeroExpediente.getExpediente()!=null &&
				numeroExpediente.getExpediente().getEstatus() != null) {
				expedienteDto.setEstatus(new ValorDTO(numeroExpediente.getExpediente().getEstatus().getValorId(),
					numeroExpediente.getExpediente().getEstatus().getValor()));
		}
		
		if (numeroExpediente.getExpediente()!=null &&
				numeroExpediente.getExpediente().getCatUIEspecializada() != null &&
				numeroExpediente.getExpediente().getCatUIEspecializada().getNombreUIE() != null) {
				expedienteDto.setNombreUIE(numeroExpediente.getExpediente().getCatUIEspecializada().getNombreUIE());
		}
		
		// ------------------------------------------------------------------------------------------------
		
		if (numeroExpediente != null){
			FuncionarioDTO funcionarioDTO = FuncionarioTransformer.transformarFuncionario(numeroExpediente.getFuncionario());
			expedienteDto.setResponsableTramite(funcionarioDTO);
		}
		
		expedienteDto.setNumeroExpedienteId(numeroExpediente
				.getNumeroExpedienteId());
		expedienteDto.setNumeroExpediente(numeroExpediente
				.getNumeroExpediente());
		if (numeroExpediente.getExpediente() != null) {
			expedienteDto.setExpedienteId(numeroExpediente.getExpediente()
					.getExpedienteId());
			expedienteDto.setFechaApertura(numeroExpediente.getExpediente()
					.getFechaCreacion());
			expedienteDto.setFechaAperturaNumeroExp(numeroExpediente.getFechaApertura());
			
			if (numeroExpediente.getExpediente().getFechaCreacion() != null) {
				expedienteDto.setStrFechaApertura(DateUtils
						.formatear(numeroExpediente.getExpediente()
								.getFechaCreacion()));
			}
			
			if (numeroExpediente.getExpediente().getCaso() != null) {
				expedienteDto.setCasoDTO(new CasoDTO(numeroExpediente
						.getExpediente().getCaso().getCasoId(),
						numeroExpediente.getExpediente().getCaso()
								.getNumeroGeneralCaso()));
			}
			
			//Agregamos el campo para agencias
			if (numeroExpediente.getExpediente().getDiscriminante() != null
					&& numeroExpediente.getExpediente().getDiscriminante()
							.getCatDiscriminanteId() != null
					&& numeroExpediente.getExpediente().getDiscriminante().getClave() != null){
				expedienteDto.setDiscriminante(new CatDiscriminanteDTO(numeroExpediente.getExpediente().getDiscriminante().getCatDiscriminanteId(),numeroExpediente.getExpediente().getDiscriminante().getClave()));
			}
		}

		if (numeroExpediente.getEtapa() != null) {
			expedienteDto.setEtapa(new ValorDTO(numeroExpediente.getEtapa()
					.getValorId(), numeroExpediente.getEtapa().getValor()));
		}

		if (numeroExpediente.getCatEtapa() != null
				&& numeroExpediente.getCatEtapa().getCatEtapaValor() != null
				&& numeroExpediente.getCatEtapa().getCatEtapaValor()
						.getValorId() != null
				&& numeroExpediente.getCatEtapa().getCatEtapaValor()
						.getValorId() > 0) {
			expedienteDto.setCatEtapaDTO(CatEtapaTransformer
					.transformar(numeroExpediente.getCatEtapa()));
		}
		
		if (numeroExpediente.getAlmacen() != null)
			expedienteDto.setAlmacenDTO(AlmacenTransformer
					.transformarAlmacenBasico(numeroExpediente.getAlmacen()));

		return expedienteDto;
	}

	/**
	 * 
	 * @param numeroExpediente
	 * @return ExpedienteDTO
	 */
	public static ExpedienteDTO transformarExpedienteBasicoDefensoria(
			NumeroExpediente numeroExpediente) {
		if (numeroExpediente == null)
			return null;
		ExpedienteDTO expedienteDto = new ExpedienteDTO();

		expedienteDto.setNumeroExpedienteId(numeroExpediente
				.getNumeroExpedienteId());
		expedienteDto.setNumeroExpediente(numeroExpediente
				.getNumeroExpediente());
		
        // ------------------------------------------------------------------------------------------------
        // Ojo!!! si se va a realizar una referencia al estatus del expediente, realizarlo por medio de 
        // expedienteDto.getEstatus();
        // Pero si la referencia es al estatus del número de expediente asociado a ese expediente:
        // expedienteDto.getEstatusNumeroExpediente();

		if(numeroExpediente.getEstatus()!=null){
			expedienteDto.setEstatusNumeroExpediente(new ValorDTO(numeroExpediente.getEstatus().getValorId(),
				numeroExpediente.getEstatus().getValor()));
		}
		
		if (numeroExpediente.getExpediente()!=null &&
				numeroExpediente.getExpediente().getEstatus() != null) {
				expedienteDto.setEstatus(new ValorDTO(numeroExpediente.getExpediente().getEstatus().getValorId(), 
					numeroExpediente.getExpediente().getEstatus().getValor()));
		}

        // ------------------------------------------------------------------------------------------------

		if (numeroExpediente.getExpediente() != null) {
			Expediente exp = numeroExpediente.getExpediente();
			expedienteDto.setExpedienteId(numeroExpediente.getExpediente()
					.getExpedienteId());
			expedienteDto.setFechaApertura(numeroExpediente.getExpediente()
					.getFechaCreacion());
			if (exp.getCaso() != null) {
				expedienteDto.setCasoDTO(new CasoDTO(exp.getCaso().getCasoId(),
						exp.getCaso().getNumeroGeneralCaso()));
			}
			//Agregamos el campo agencia
			if(exp.getDiscriminante()!=null && exp.getDiscriminante().getClave() != null && exp.getDiscriminante().getCatDiscriminanteId() != null){
				expedienteDto.setDiscriminante(new CatDiscriminanteDTO(exp.getDiscriminante().getCatDiscriminanteId(),exp.getDiscriminante().getClave()));
			}
		
		}
				
		if (numeroExpediente.getEtapa() != null) {
			expedienteDto.setEtapa(new ValorDTO(numeroExpediente.getEtapa()
					.getValorId(), numeroExpediente.getEtapa().getValor()));
		}

		if (numeroExpediente.getCatEtapa() != null
				&& numeroExpediente.getCatEtapa().getCatEtapaValor() != null
				&& numeroExpediente.getCatEtapa().getCatEtapaValor()
						.getValorId() != null
				&& numeroExpediente.getCatEtapa().getCatEtapaValor()
						.getValorId() > 0) {
			expedienteDto.setCatEtapaDTO(CatEtapaTransformer
					.transformar(numeroExpediente.getCatEtapa()));
		}

		if (numeroExpediente.getAudiencias() != null) {
			List<AudienciaDTO> audiencias = new LinkedList<AudienciaDTO>();
			for (Audiencia audiencia : numeroExpediente.getAudiencias()) {
				audiencias.add(EventoTransformer
						.transformarAudienciaBasico(audiencia));
			}
			expedienteDto.setAudiencias(audiencias);
		}

		return expedienteDto;
	}
	
	/**
	 * 
	 * @param numeroExpediente
	 * @return ExpedienteDTO
	 */
	public static ExpedienteDTO transformarExpedienteDefensoria(
			NumeroExpediente numeroExpediente) {
		if (numeroExpediente == null)
			return null;
		ExpedienteDTO expedienteDto = new ExpedienteDTO();

		expedienteDto.setNumeroExpedienteId(numeroExpediente
				.getNumeroExpedienteId());
		expedienteDto.setNumeroExpediente(numeroExpediente
				.getNumeroExpediente());
        // ------------------------------------------------------------------------------------------------
        // Ojo!!! si se va a realizar una referencia al estatus del expediente, realizarlo por medio de 
        // expedienteDto.getEstatus();
        // Pero si la referencia es al estatus del número de expediente asociado a ese expediente:
        // expedienteDto.getEstatusNumeroExpediente();		
		
		if(numeroExpediente.getEstatus()!=null){
			expedienteDto.setEstatusNumeroExpediente(new ValorDTO(numeroExpediente.getEstatus().getValorId(),
				numeroExpediente.getEstatus().getValor()));
		}
		
		if (numeroExpediente.getExpediente()!=null &&
				numeroExpediente.getExpediente().getEstatus() != null) {
				expedienteDto.setEstatus(new ValorDTO(numeroExpediente.getExpediente().getEstatus().getValorId(), 
					numeroExpediente.getExpediente().getEstatus().getValor()));
		}

		// ------------------------------------------------------------------------------------------------

		if (numeroExpediente.getExpediente() != null) {
			Expediente exp = numeroExpediente.getExpediente();
			expedienteDto.setExpedienteId(numeroExpediente.getExpediente()
					.getExpedienteId());
			expedienteDto.setFechaApertura(numeroExpediente.getExpediente()
					.getFechaCreacion());
			if (exp.getCaso() != null) {
				expedienteDto.setCasoDTO(new CasoDTO(exp.getCaso().getCasoId(),
						exp.getCaso().getNumeroGeneralCaso()));
			}
			
			if (exp.getAvisoDesignaciones() != null) {
				for (AvisoDesignacion aviso : exp.getAvisoDesignaciones()) {
					expedienteDto.getAvisosDesignacion().add(AvisoDesignacionTransformer.transformar(aviso));				
				}

			}
			
			//Agregamos el campo agencia
			if(exp.getDiscriminante() != null && exp.getDiscriminante().getClave() != null && exp.getDiscriminante().getCatDiscriminanteId() != null){
				expedienteDto.setDiscriminante(new CatDiscriminanteDTO(exp.getDiscriminante().getCatDiscriminanteId(),exp.getDiscriminante().getClave()));
			}
		}
		
		if (numeroExpediente.getEtapa() != null) {
			expedienteDto.setEtapa(new ValorDTO(numeroExpediente.getEtapa()
					.getValorId(), numeroExpediente.getEtapa().getValor()));
		}

		// Etapa del Expediente de Defensoria
		if (numeroExpediente.getCatEtapa() != null
				&& numeroExpediente.getCatEtapa().getCatEtapaValor() != null) {
			expedienteDto.setCatEtapaDTO(CatEtapaTransformer
					.transformarBasico(numeroExpediente.getCatEtapa()));
		}

		if (numeroExpediente.getAlmacen() != null) {
			expedienteDto.setAlmacenDTO(AlmacenTransformer
					.transformarAlmacenBasico(numeroExpediente.getAlmacen()));
		}

		if (numeroExpediente.getAudiencias() != null) {
			List<AudienciaDTO> audiencias = new LinkedList<AudienciaDTO>();
			for (Audiencia audiencia : numeroExpediente.getAudiencias()) {
				audiencias.add(EventoTransformer
						.transformarAudienciaBasico(audiencia));
			}
			expedienteDto.setAudiencias(audiencias);
		}

		return expedienteDto;
	}

	public static ExpedienteDTO transformarExpedienteDenunciante(
			NumeroExpediente numeroExpediente) {
		ExpedienteDTO expedienteDto = new ExpedienteDTO();

		expedienteDto.setNumeroExpedienteId(numeroExpediente
				.getNumeroExpedienteId());
		
        // ------------------------------------------------------------------------------------------------
        // Ojo!!! si se va a realizar una referencia al estatus del expediente, realizarlo por medio de 
        // expedienteDto.getEstatus();
        // Pero si la referencia es al estatus del número de expediente asociado a ese expediente:
        // expedienteDto.getEstatusNumeroExpediente();
		
		if(numeroExpediente!=null && numeroExpediente.getEstatus()!=null){
			expedienteDto.setEstatusNumeroExpediente(new ValorDTO(numeroExpediente.getEstatus().getValorId(), 
				numeroExpediente.getEstatus().getValor()));
		}

		if (numeroExpediente!=null &&
				numeroExpediente.getExpediente()!=null &&
				numeroExpediente.getExpediente().getEstatus() != null) {
				expedienteDto.setEstatus(new ValorDTO(numeroExpediente.getExpediente().getEstatus().getValorId(),
					numeroExpediente.getExpediente().getEstatus().getValor()));
		}

		// ------------------------------------------------------------------------------------------------

		if (numeroExpediente.getExpediente() != null) {
			expedienteDto.setExpedienteId(numeroExpediente.getExpediente()
					.getExpedienteId());
			expedienteDto.setFechaApertura(numeroExpediente.getExpediente()
					.getFechaCreacion());
			if (numeroExpediente.getExpediente().getDelitos() != null) {
				List<DelitoDTO> delitosDTO = new ArrayList<DelitoDTO>();
				for (Delito delito : numeroExpediente.getExpediente()
						.getDelitos()) {
					delitosDTO.add(DelitoTransfromer.transformarDelito(delito));
					if (delito.getEsPrincipal())
						expedienteDto.setDelitoPrincipal(DelitoTransfromer
								.transformarDelito(delito));
				}

				expedienteDto.setDelitos(delitosDTO);
			}
			//Agregamos el campo para agencia
			if (numeroExpediente.getExpediente().getDiscriminante() != null
					&& numeroExpediente.getExpediente().getDiscriminante().getClave() != null
					&& numeroExpediente.getExpediente().getDiscriminante()
							.getCatDiscriminanteId() != null) {
				 expedienteDto.setDiscriminante(new
				 CatDiscriminanteDTO(numeroExpediente.getExpediente().getDiscriminante().getCatDiscriminanteId(),numeroExpediente.getExpediente().getDiscriminante().getClave()));
			}

		}
		expedienteDto.setNumeroExpediente(numeroExpediente
				.getNumeroExpediente());

		if (numeroExpediente.getExpediente().getCaso() != null) {
			expedienteDto.setCasoDTO(new CasoDTO(numeroExpediente
					.getExpediente().getCaso().getCasoId(), numeroExpediente
					.getExpediente().getCaso().getNumeroGeneralCaso()));
		}

		if (numeroExpediente.getEtapa() != null) {
			expedienteDto.setEtapa(new ValorDTO(numeroExpediente.getEtapa()
					.getValorId(), numeroExpediente.getEtapa().getValor()));
		}

		return expedienteDto;
	}
	
	
	public static CatDiscriminanteDTO transformarDiscriminante(
			CatDiscriminante discriminante) {
		CatDiscriminanteDTO loCatDiscriminanteDTO = null;
		if(discriminante != null){
			loCatDiscriminanteDTO = new CatDiscriminanteDTO();
			loCatDiscriminanteDTO.setCatDiscriminanteId(discriminante.getCatDiscriminanteId());
			loCatDiscriminanteDTO.setClave(discriminante.getClave());
			CatDistritoDTO loCatDistritoDTO = null;
			if(discriminante.getDistrito() != null){
				loCatDistritoDTO = new CatDistritoDTO();
				loCatDistritoDTO.setCatDistritoId(discriminante.getDistrito().getCatDistritoId());
				loCatDistritoDTO.setClaveDistrito(discriminante.getDistrito().getClaveDistrito());
			}
			loCatDiscriminanteDTO.setDistrito(loCatDistritoDTO);			
		}
		return loCatDiscriminanteDTO;
	}

	
	public static NumeroExpediente obtenerDeExpedienteDTO(ExpedienteDTO expedienteDTO){
		NumeroExpediente numeroExpediente = null;
		if(expedienteDTO != null){
			numeroExpediente = new NumeroExpediente();
			numeroExpediente.setExpediente(new Expediente());
			
			numeroExpediente.setNumeroExpedienteId(expedienteDTO.getNumeroExpedienteId());
			numeroExpediente.setNumeroExpediente(expedienteDTO.getNumeroExpediente());
	
			numeroExpediente.getExpediente().setExpedienteId(expedienteDTO.getExpedienteId());
			numeroExpediente.setFechaApertura(expedienteDTO.getFechaAperturaNumeroExp());
			
			if(expedienteDTO.getEstatusNumeroExpediente()!=null){	
				numeroExpediente.setEstatus(ValorTransformer.transformar(expedienteDTO.getEstatusNumeroExpediente()));
			}
			else if(expedienteDTO.getEstatus()!=null){
				numeroExpediente.setEstatus(ValorTransformer.transformar(expedienteDTO.getEstatus()));
			}				
		}
		return numeroExpediente;
	}
	
	public static BitacoraPermisoExpedienteDTO transformarBitacoraPermisoExpediente(BitacoraPermisoExpediente loBitacoraPermisoExpediente){
		if(loBitacoraPermisoExpediente == null)
			return null;
		BitacoraPermisoExpedienteDTO loBitacoraPermisoExpedienteDTO = new BitacoraPermisoExpedienteDTO();
		
		loBitacoraPermisoExpedienteDTO.setBitacoraPermisoExpediente(loBitacoraPermisoExpediente.getBitacoraPermisoExpediente());
		
		if(loBitacoraPermisoExpediente.getNumeroExpediente() != null ){
			loBitacoraPermisoExpedienteDTO.setExpediente(ExpedienteTransformer.transformarExpediente(loBitacoraPermisoExpediente.getNumeroExpediente()));
		}
		if(loBitacoraPermisoExpediente.getFuncionario() != null ){
			loBitacoraPermisoExpedienteDTO.setFuncionario(FuncionarioTransformer.transformarFuncionario(loBitacoraPermisoExpediente.getFuncionario()));
		}
		
		if(loBitacoraPermisoExpediente.getJerarquiaOrganizacional() != null ){
			loBitacoraPermisoExpedienteDTO.setJerarquiaOrganizacional(JerarquiaOrganizacionalTransformer.transformarJerarquiaOrganizacional(loBitacoraPermisoExpediente.getJerarquiaOrganizacional()));
		}
		
		loBitacoraPermisoExpedienteDTO.setFechaMovimiento(loBitacoraPermisoExpediente.getFechaMovimiento());
		loBitacoraPermisoExpedienteDTO.setFechaLimite(loBitacoraPermisoExpediente.getFechaLimite());
		loBitacoraPermisoExpedienteDTO.setEsActivo(loBitacoraPermisoExpediente.getEsActivo());
		
		return loBitacoraPermisoExpedienteDTO;
	}
	
	public static BitacoraEstatusNumExpedienteDTO transformarBitacoraEstatusNumExpediente(BitacoraEstatusNumExpediente loBitacoraEstatusNumExpediente){
		if(loBitacoraEstatusNumExpediente == null)
			return null;
		BitacoraEstatusNumExpedienteDTO loBitacoraEstatusNumExpedienteDTO = new BitacoraEstatusNumExpedienteDTO();

		loBitacoraEstatusNumExpedienteDTO.setBitacoraEstatusNumExpedienteId(loBitacoraEstatusNumExpediente.getBitacoraEstatusNumExpedienteId());
		
		loBitacoraEstatusNumExpedienteDTO.setFechaCreacion(loBitacoraEstatusNumExpediente.getFechaCreacion());
		
		if(loBitacoraEstatusNumExpediente.getJerarquiaOrganizacional() != null ){
			loBitacoraEstatusNumExpedienteDTO.setJerarquiaOrganizacional(JerarquiaOrganizacionalTransformer.transformarJerarquiaOrganizacional(loBitacoraEstatusNumExpediente.getJerarquiaOrganizacional()));
		}
		
		if(loBitacoraEstatusNumExpediente.getNumeroExpediente() != null ){
			loBitacoraEstatusNumExpedienteDTO.setExpediente(ExpedienteTransformer.transformarExpediente(loBitacoraEstatusNumExpediente.getNumeroExpediente()));
		}
		if(loBitacoraEstatusNumExpediente.getFuncionario() != null ){
			loBitacoraEstatusNumExpedienteDTO.setFuncionario(FuncionarioTransformer.transformarFuncionario(loBitacoraEstatusNumExpediente.getFuncionario()));
		}
		if(loBitacoraEstatusNumExpediente.getEstatus() != null ){
			loBitacoraEstatusNumExpedienteDTO.setEstatus(ValorTransformer.transformar(loBitacoraEstatusNumExpediente.getEstatus()));
		}
		if(loBitacoraEstatusNumExpediente.getTipoActividad() != null ){
			loBitacoraEstatusNumExpedienteDTO.setTipoActividad(ValorTransformer.transformar(loBitacoraEstatusNumExpediente.getTipoActividad()));
		}
		if(loBitacoraEstatusNumExpediente.getTipoDocumento() != null ){
			loBitacoraEstatusNumExpedienteDTO.setTipoDocumento(ValorTransformer.transformar(loBitacoraEstatusNumExpediente.getTipoDocumento()));
		}
		return loBitacoraEstatusNumExpedienteDTO;
	}
}
