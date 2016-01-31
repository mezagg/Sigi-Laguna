package mx.gob.segob.nsjp.service.documento.impl.tranform;

import static mx.gob.segob.nsjp.comun.enums.calidad.Calidades.DENUNCIANTE;
import static mx.gob.segob.nsjp.comun.enums.calidad.Calidades.DENUNCIANTE_ORGANIZACION;
import static mx.gob.segob.nsjp.comun.enums.calidad.Calidades.PROBABLE_RESPONSABLE_ORGANIZACION;
import static mx.gob.segob.nsjp.comun.enums.calidad.Calidades.PROBABLE_RESPONSABLE_PERSONA;
import static mx.gob.segob.nsjp.comun.enums.calidad.Calidades.QUIEN_DETUVO;
import static mx.gob.segob.nsjp.comun.enums.calidad.Calidades.TESTIGO;
import static mx.gob.segob.nsjp.comun.enums.calidad.Calidades.TRADUCTOR;
import static mx.gob.segob.nsjp.comun.enums.calidad.Calidades.VICTIMA_PERSONA;
import static mx.gob.segob.nsjp.comun.enums.objeto.Objetos.AERONAVE;
import static mx.gob.segob.nsjp.comun.enums.objeto.Objetos.ANIMAL;
import static mx.gob.segob.nsjp.comun.enums.objeto.Objetos.ARMA;
import static mx.gob.segob.nsjp.comun.enums.objeto.Objetos.DOCUMENTO_OFICIAL;
import static mx.gob.segob.nsjp.comun.enums.objeto.Objetos.EMBARCACION;
import static mx.gob.segob.nsjp.comun.enums.objeto.Objetos.EQUIPO_DE_COMPUTO;
import static mx.gob.segob.nsjp.comun.enums.objeto.Objetos.EQUIPO_TELEFONICO;
import static mx.gob.segob.nsjp.comun.enums.objeto.Objetos.EXPLOSIVO;
import static mx.gob.segob.nsjp.comun.enums.objeto.Objetos.JOYA;
import static mx.gob.segob.nsjp.comun.enums.objeto.Objetos.NUMERARIO;
import static mx.gob.segob.nsjp.comun.enums.objeto.Objetos.OBRA_DE_ARTE;
import static mx.gob.segob.nsjp.comun.enums.objeto.Objetos.OTRO;
import static mx.gob.segob.nsjp.comun.enums.objeto.Objetos.SUSTANCIA;
import static mx.gob.segob.nsjp.comun.enums.objeto.Objetos.VEGETAL;
import static mx.gob.segob.nsjp.comun.enums.objeto.Objetos.VEHICULO;
import static mx.gob.segob.nsjp.comun.enums.objeto.Objetos.PERICIAL;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.util.StringUtils;

import mx.gob.segob.nsjp.comun.constants.ConstantesGenerales;
import mx.gob.segob.nsjp.comun.enums.comun.Meses;
import mx.gob.segob.nsjp.comun.enums.objeto.Objetos;
import mx.gob.segob.nsjp.comun.util.DateUtils;
import mx.gob.segob.nsjp.dto.audiencia.AudienciaDTO;
import mx.gob.segob.nsjp.dto.documento.GrupoObjetosExpedienteDTO;
import mx.gob.segob.nsjp.dto.documento.ObjetoResumenDTO;
import mx.gob.segob.nsjp.dto.expediente.ExpedienteDTO;
import mx.gob.segob.nsjp.dto.expediente.ParametrosDocumentoDTO;
import mx.gob.segob.nsjp.dto.objeto.AeronaveDTO;
import mx.gob.segob.nsjp.dto.objeto.AnimalDTO;
import mx.gob.segob.nsjp.dto.objeto.ArmaDTO;
import mx.gob.segob.nsjp.dto.objeto.DocumentoOficialDTO;
import mx.gob.segob.nsjp.dto.objeto.EmbarcacionDTO;
import mx.gob.segob.nsjp.dto.objeto.EquipoComputoDTO;
import mx.gob.segob.nsjp.dto.objeto.ExplosivoDTO;
import mx.gob.segob.nsjp.dto.objeto.JoyaDTO;
import mx.gob.segob.nsjp.dto.objeto.NumerarioDTO;
import mx.gob.segob.nsjp.dto.objeto.ObjetoDTO;
import mx.gob.segob.nsjp.dto.objeto.ObjetoPericialDTO;
import mx.gob.segob.nsjp.dto.objeto.ObraArteDTO;
import mx.gob.segob.nsjp.dto.objeto.SustanciaDTO;
import mx.gob.segob.nsjp.dto.objeto.TelefoniaDTO;
import mx.gob.segob.nsjp.dto.objeto.VegetalDTO;
import mx.gob.segob.nsjp.dto.objeto.VehiculoDTO;

public class ParametrosDocumentoTransformer {

	public static ParametrosDocumentoDTO cargarParametrosDocumentoFrom(ExpedienteDTO expediente){
		if(expediente == null){
			return null;
		}
		
		ParametrosDocumentoDTO parametrosDocumento = new ParametrosDocumentoDTO();
		Date fechaActual = new Date();
		Calendar cal = Calendar.getInstance();
		cal.setTime(fechaActual);
		parametrosDocumento.setExpedienteId(expediente.getExpedienteId());
		parametrosDocumento.setNumeroExpediente(expediente.getNumeroExpediente());
		parametrosDocumento.setFechaApertura(expediente.getFechaApertura());
		parametrosDocumento.setFechaCierre(expediente.getFechaCierre());
		parametrosDocumento.setNarrativa(expediente.getHechoDTO()!=null?expediente.getHechoDTO().getDescNarrativa():null);
		parametrosDocumento.setStrFechaApertura(expediente.getStrFechaApertura());
		parametrosDocumento.setStrHoraApertura(expediente.getStrHoraApertura());
		parametrosDocumento.setStrFechaCierre(expediente.getStrFechaCierre());
		parametrosDocumento.setStrHoraCierre(expediente.getStrHoraCierre());
		parametrosDocumento.setDelitoPrincipal(expediente.getDelitoPrincipal());
		parametrosDocumento.setDelitos(expediente.getDelitos());
		parametrosDocumento.setListaDelitos(expediente.getListaDelitos());
		parametrosDocumento.setResponsableTramite(expediente.getResponsableTramite());
		
		parametrosDocumento.setDenunciantes(expediente.getInvolucradoByCalidad(DENUNCIANTE));
		parametrosDocumento.setVictimasPersona(expediente.getInvolucradoByCalidad(VICTIMA_PERSONA));
		parametrosDocumento.setVictimasOrganizaciones(expediente.getInvolucradoByCalidad(DENUNCIANTE_ORGANIZACION));
		parametrosDocumento.setProbablesResponsablesPersona(expediente.getInvolucradoByCalidad(PROBABLE_RESPONSABLE_PERSONA));
		parametrosDocumento.setProbablesResponsablesOrganizacion(expediente.getInvolucradoByCalidad(PROBABLE_RESPONSABLE_ORGANIZACION));
		parametrosDocumento.setTestigos(expediente.getInvolucradoByCalidad(TESTIGO));
		parametrosDocumento.setTraductores(expediente.getInvolucradoByCalidad(TRADUCTOR));
		parametrosDocumento.setQuienDetuvo(expediente.getInvolucradoByCalidad(QUIEN_DETUVO));
		
		parametrosDocumento.setStrFechaActual(DateUtils.formatear(fechaActual));
		parametrosDocumento.setStrHoraActual(DateUtils.formatearHora(fechaActual));
		parametrosDocumento.setDiaActual(String.valueOf(cal.get(Calendar.DAY_OF_MONTH)));
		parametrosDocumento.setNombreMesActual(Meses.getNombre(cal.get(Calendar.MONTH)));
		parametrosDocumento.setAnioActual(String.valueOf(cal.get(Calendar.YEAR)));
		
		//recuperar las organizaciones denunciantes
		parametrosDocumento.setDenunciantesOrganizacion(expediente.getInvolucradoByCalidad(DENUNCIANTE_ORGANIZACION));
		
		//llenar objetos
		parametrosDocumento.setGrupoObjetosExpediente(new ArrayList<GrupoObjetosExpedienteDTO>());
		llenarGruposDeObjetos(parametrosDocumento,expediente);
		if(expediente.getCasoDTO()!=null)
                    parametrosDocumento.setNuc(expediente.getCasoDTO().getNumeroGeneralCaso());
                
		return parametrosDocumento;
	}
	
	public static ParametrosDocumentoDTO cargarParametrosDocumentoFrom(AudienciaDTO audiencia){
		ParametrosDocumentoDTO parametrosDocumento = null; 
		parametrosDocumento = cargarParametrosDocumentoFrom(audiencia.getExpediente());
		parametrosDocumento.setAudiencia(audiencia);
		return parametrosDocumento;
	}
	
	
	/**
	 * Llena los grupos de objetos por cada tipo de objeto que exista en el expediente
	 * @param resumen DTO destino
	 * @param expediente Expediente con la información de sus objetos
	 */
	private static void llenarGruposDeObjetos(ParametrosDocumentoDTO resumen,
			ExpedienteDTO expediente) {

		Objetos[] listado = new Objetos[]{VEHICULO, EQUIPO_DE_COMPUTO, EQUIPO_TELEFONICO,
										  ARMA, EXPLOSIVO, AERONAVE, ANIMAL, 
										  DOCUMENTO_OFICIAL,EMBARCACION,
										  JOYA,NUMERARIO,OBRA_DE_ARTE,SUSTANCIA,
										  VEGETAL,OTRO,PERICIAL};
		
		GrupoObjetosExpedienteDTO grupo = null;
		
		for(Objetos tipoActual:listado){
			grupo = new GrupoObjetosExpedienteDTO();
			grupo.setTipoObjeto(tipoActual);
			grupo.setDescripcionGrupo(tipoActual.getNombreEntity());
			grupo.setObjetos(obtenerResumenObjetos(tipoActual,expediente));
			resumen.getGrupoObjetosExpediente().add(grupo);
			
		}		
	}

	/**
	 * Llena una lista de resumen de objetos de cierto tipo
	 * @param tipo Tipo de objeto a consultar
	 * @param expediente Expediente con los datos
	 * @return
	 */
	private static List<ObjetoResumenDTO> obtenerResumenObjetos(Objetos tipo,
			ExpedienteDTO expediente) {
		List<ObjetoDTO> objetos = expediente.getObjetosByTipo(tipo);
		List<ObjetoResumenDTO> objetosResumen = new ArrayList<ObjetoResumenDTO>();
		ObjetoResumenDTO resumenActual = null;
		for(ObjetoDTO objeto:objetos){
			resumenActual = new ObjetoResumenDTO();
			resumenActual.setElementoId(objeto.getElementoId());
			resumenActual.setCalidadDTO(objeto.getCalidadDTO());
			resumenActual.setFechaCreacionElemento(objeto.getFechaCreacionElemento());
			
			if(tipo.equals(VEHICULO)){
			    VehiculoDTO temp = (VehiculoDTO)objeto;
			    StringBuffer resumenVehiculo = new StringBuffer();
			    resumenVehiculo.append("<ul>");
			    resumenVehiculo.append("	<li>Color: ").append(temp.getValorByColorVal() != null && temp.getValorByColorVal().getValor() != null ? 
			    		temp.getValorByColorVal().getValor(): "-").append("</li>");
			    resumenVehiculo.append("	<li>Marca: ").append(temp.getValorByMarcaVal() != null && temp.getValorByMarcaVal().getValor() != null ? 
			    		temp.getValorByMarcaVal().getValor(): "-").append("</li>");
			    resumenVehiculo.append("	<li>Modelo: ").append(temp.getModelo() != null ? temp.getModelo(): "-").append("</li>");
			    resumenVehiculo.append("	<li>No. de Motor: ").append(temp.getNoMotor() != null ? temp.getNoMotor(): "-").append("</li>");
			    resumenVehiculo.append("	<li>No. de Serie: ").append(temp.getNoSerie() != null ? temp.getNoSerie(): "-").append("</li>");
			    resumenVehiculo.append("	<li>Placas de circulaci\u00F3n: ").append(temp.getPlaca() != null ? temp.getPlaca(): "-").append("</li>");
			    resumenVehiculo.append("	<li>Procedencia: ").append(temp.getValorByPaisOrigenVal() != null && temp.getValorByPaisOrigenVal().getValor() != null ? 
			    		temp.getValorByPaisOrigenVal().getValor(): "-").append("</li>");
			    resumenVehiculo.append("	<li>Submarca: ").append(temp.getValorBySubmarcaVal() != null && temp.getValorBySubmarcaVal().getValor() != null ?
			    					    		temp.getValorBySubmarcaVal().getValor(): "-").append("</li>");
			    resumenVehiculo.append("	<li>Tipo de veh\u00EDculo: ").append(temp.getValorByTipoVehiculo() != null && temp.getValorByTipoVehiculo().getValor() != null ? 
			    		temp.getValorByTipoVehiculo().getValor(): "-").append("</li>");
			    
			    // Valores agregados REQ6
			    if(expediente.isDatosPlatillaSolicitados()){
			    	
			    	resumenVehiculo.append("    <li>No. de registro P\u00f9blico Vehicular: ").append(StringUtils.hasText(temp.getNrfv()) ? temp.getNrfv(): "-").append("</li>");
			    	resumenVehiculo.append("    <li>No. de Puertas: ").append(temp.getNoPuertas() != null ? temp.getNoPuertas(): "-").append("</li>");
			    	resumenVehiculo.append("    <li>No. de Cilindros: ").append(temp.getNoCilindros() != null ? temp.getNoCilindros(): "-").append("</li>");
			    	resumenVehiculo.append("    <li>Condici\u00f3n del objeto: ").append(temp.getValorByCondicionFisicaVal() != null && StringUtils.hasText(temp.getValorByCondicionFisicaVal().getValor()) ? 
			    			temp.getValorByCondicionFisicaVal().getValor(): "-").append("</li>");
			    	
			    	resumenVehiculo.append("    <li>Causa: ");
			    	if(temp.getCausa() != null && StringUtils.hasText(temp.getCausa().getNombreCausa())){
			    		
			    		resumenVehiculo.append(temp.getCausa().getNombreCausa());
	    				if(temp.getCausaRecuperado() != null && StringUtils.hasText(temp.getCausaRecuperado().getNombreCausa()) && 
	    						temp.getCausa().getCausaId().compareTo(ConstantesGenerales.VEHICULO_CAUSA_RECUPERADO_ID) == 0){
	    					resumenVehiculo.append(" [").append(temp.getCausaRecuperado().getNombreCausa());
	    					resumenVehiculo.append(temp.getCausaRecuperadoOtros() != null && 
	    							StringUtils.hasText(temp.getCausaRecuperadoOtros().getNombreCausa()) &&
	    							temp.getCausaRecuperado().getCausaId().compareTo(ConstantesGenerales.VEHICULO_CAUSA_RECUPERADO_OTROS_ID) == 0 ? 
	    							" (" + temp.getCausaRecuperadoOtros().getNombreCausa() + ")": "");	    					
	    					resumenVehiculo.append("]");
	    				}
			    	}else{
			    		resumenVehiculo.append("-");
			    	}
			    	resumenVehiculo.append("</li>");
			    	
			    	resumenVehiculo.append("    <li>Relaci\u00f3n del hecho: ");
			    	if(temp.getRelacionHechoVal() != null && 
			    			StringUtils.hasText(temp.getRelacionHechoVal().getValor())){
			    		
			    		SimpleDateFormat formating = new SimpleDateFormat("dd/MM/yyyy");
		    			String fechaRecuperado = "-";
		    			String fechaEntregado = "-";
			    		
			    		resumenVehiculo.append(temp.getRelacionHechoVal().getValor());
			    		if(temp.getRelacionHechoVal().getIdCampo().compareTo(ConstantesGenerales.VEHICULO_RELACION_HECHOS_RECUPERADO_ID) == 0){
			    			if(temp.getFechaRecuperado() != null)
			    				fechaRecuperado = formating.format(temp.getFechaRecuperado());
			    			resumenVehiculo.append("<ul>");
			    			resumenVehiculo.append("    <li>Fecha de recuperaci\u00f3n: ").append(fechaRecuperado).append("</li>");
			    			resumenVehiculo.append("    <li>Lugar de recuperaci\u00f3n: ").append(StringUtils.hasText(temp.getLugarRecuperacion()) ? temp.getLugarRecuperacion() : "-").append("</li>");
			    			resumenVehiculo.append("    <li>Autoridad que recupero: ").append(StringUtils.hasText(temp.getAutoridadRecupero()) ? temp.getAutoridadRecupero() : "-").append("</li>");
			    			resumenVehiculo.append("</ul>");
			    		}else if(temp.getRelacionHechoVal().getIdCampo().compareTo(ConstantesGenerales.VEHICULO_RELACION_HECHOS_ENTREGADO_ID) == 0){
			    			if(temp.getFechaEntrega() != null)
			    				fechaEntregado = formating.format(temp.getFechaEntrega());
			    			resumenVehiculo.append("<ul>");
			    			resumenVehiculo.append("    <li>Fecha de entrega: ").append(fechaEntregado).append("</li>");
			    			resumenVehiculo.append("</ul>");
			    		}
			    	}else{
			    		resumenVehiculo.append("-");
			    	}
			    	resumenVehiculo.append("</li>");
			    	
//			    	if(temp.getImagenesAsociadas() != null && !temp.getImagenesAsociadas().isEmpty()){
//			    		StringBuffer imgns = null;
//			    		if(temp.getImagenesAsociadas().size() > 0){
//			    			imgns = new StringBuffer();
//			    			for(ArchivoDigitalDTO dto : temp.getImagenesAsociadas()){
//			    				String im;
//								try {
////									im = new String (dto.getContenido(),"UTF-8");
//									im = Base64.encodeBase64URLSafeString(dto.getContenido());
//									int size = dto.getContenido().length;
//									imgns.append("<img src='data:").append(dto.getTipoArchivo()).append(";base64,")
//									.append(im).append("'/>");
//								} catch (Exception e) {
//									// TODO Auto-generated catch block
//									e.printStackTrace();
//								}
//			    			}
//			    		}
//			    		resumenVehiculo.append("    <li>Imagen: ").append(imgns != null && !imgns.toString().isEmpty() ?
//			    				imgns.toString() : "-").append("</li>");
//			    	}
			    	
			    }
			    
			    resumenVehiculo.append("</ul>");
			    
				resumenActual.setDescripcionResumen(resumenVehiculo.toString());
			}
			if(tipo.equals(EQUIPO_DE_COMPUTO)){
			    EquipoComputoDTO temp = (EquipoComputoDTO)objeto;
			    
			    // Valores agregados REQ6
			    if(expediente.isDatosPlatillaSolicitados()){
			    	StringBuffer resumenEquipoCom = new StringBuffer();
			    	
			    	resumenEquipoCom.append("<ul>");
			    	resumenEquipoCom.append("	<li>Tipo: ").append(temp.getTipoEquipo() != null && StringUtils.hasText(temp.getTipoEquipo().getValor()) ? 
			    			temp.getTipoEquipo().getValor(): "-").append("</li>");
			    	resumenEquipoCom.append("	<li>Marca: ").append(temp.getMarca() != null && StringUtils.hasText(temp.getMarca().getValor()) ? 
			    			temp.getMarca().getValor(): "-").append("</li>");
			    	resumenEquipoCom.append("	<li>Color: ").append(temp.getColor() != null && StringUtils.hasText(temp.getColor().getValor()) ? 
			    			temp.getColor().getValor(): "-").append("</li>");
			    	resumenEquipoCom.append("	<li>Modelo: ").append(StringUtils.hasText(temp.getModelo()) ? 
			    			temp.getModelo(): "-").append("</li>");
			    	resumenEquipoCom.append("	<li>No. de Serie: ").append(StringUtils.hasText(temp.getNoSerie()) ? 
			    			temp.getNoSerie(): "-").append("</li>");
			    	resumenEquipoCom.append("	<li>Condici\u00f3n: ").append(temp.getValorByCondicionFisicaVal() != null && StringUtils.hasText(temp.getValorByCondicionFisicaVal().getValor()) ? 
			    			temp.getValorByCondicionFisicaVal().getValor(): "-").append("</li>");
			    	resumenEquipoCom.append("	<li>Relaci\u00f3n del hecho: ").append(temp.getRelacionHechoVal() != null && StringUtils.hasText(temp.getRelacionHechoVal().getValor()) ? 
			    			temp.getRelacionHechoVal().getValor(): "-").append("</li>");
			    	resumenEquipoCom.append("	<li>Descripci\u00f3n: ").append(StringUtils.hasText(temp.getDescripcion()) ? 
			    			temp.getDescripcion(): "-").append("</li>");
			    	
			    	resumenEquipoCom.append("</ul>");
			    	
			    	resumenActual.setDescripcionResumen(resumenEquipoCom.toString());
			    	
			    	
			    }else{
			    	resumenActual.setDescripcionResumen(
			    			temp.getTipoEquipo()!=null?temp.getTipoEquipo().getValor():""
			    			);
			    }
			}
			if(tipo.equals(EQUIPO_TELEFONICO)){
				TelefoniaDTO temp = (TelefoniaDTO)objeto;
				
				// Valores agregados REQ6
			    if(expediente.isDatosPlatillaSolicitados()){
			    	StringBuffer resumenTelefonia = new StringBuffer();
			    	
			    	resumenTelefonia.append("<ul>");
			    	resumenTelefonia.append("	<li>Tipo: ").append(temp.getTipoTelefono() != null && StringUtils.hasText(temp.getTipoTelefono().getValor()) ? 
			    			temp.getTipoTelefono().getValor(): "-").append("</li>");
			    	resumenTelefonia.append("	<li>Marca: ").append(temp.getMarca() != null && StringUtils.hasText(temp.getMarca().getValor()) ? 
			    			temp.getMarca().getValor(): "-").append("</li>");
			    	resumenTelefonia.append("	<li>Modelo: ").append(StringUtils.hasText(temp.getModelo()) ? 
			    			temp.getModelo(): "-").append("</li>");
			    	resumenTelefonia.append("	<li>Cantidad: ").append(temp.getCantidad() != null ? 
			    			temp.getCantidad() + " piezas" : "-").append("</li>");
			    	resumenTelefonia.append("	<li>Condici\u00f3n: ").append(temp.getValorByCondicionFisicaVal() != null && StringUtils.hasText(temp.getValorByCondicionFisicaVal().getValor()) ? 
			    			temp.getValorByCondicionFisicaVal().getValor(): "-").append("</li>");
			    	resumenTelefonia.append("	<li>Relaci\u00f3n del hecho: ").append(temp.getRelacionHechoVal() != null && StringUtils.hasText(temp.getRelacionHechoVal().getValor()) ? 
			    			temp.getRelacionHechoVal().getValor(): "-").append("</li>");
			    	resumenTelefonia.append("	<li>Descripci\u00f3n: ").append(StringUtils.hasText(temp.getDescripcion()) ? 
			    			temp.getDescripcion(): "-").append("</li>");
			    	
			    	resumenTelefonia.append("</ul>");
			    	
			    	resumenActual.setDescripcionResumen(resumenTelefonia.toString());
			    	
			    }else{
					resumenActual.setDescripcionResumen(
							((TelefoniaDTO)objeto).getModelo()
							);
			    }
			}
			if(tipo.equals(ARMA)){
			    ArmaDTO temp = (ArmaDTO)objeto;
			    
			 // Valores agregados REQ6
			    if(expediente.isDatosPlatillaSolicitados()){
			    	StringBuffer resumenArma = new StringBuffer();
			    	
			    	resumenArma.append("<ul>");
			    	resumenArma.append("	<li>Tipo: ").append(temp.getTipoArma() != null && StringUtils.hasText(temp.getTipoArma().getValor()) ? 
			    			temp.getTipoArma().getValor(): "-").append("</li>");
			    	resumenArma.append("	<li>Marca: ").append(temp.getMarca() != null && StringUtils.hasText(temp.getMarca().getValor()) ? 
			    			temp.getMarca().getValor(): "-").append("</li>");
			    	resumenArma.append("	<li>Modelo: ").append(StringUtils.hasText(temp.getModelo()) ? 
			    			temp.getModelo(): "-").append("</li>");
			    	resumenArma.append("	<li>Calibre: ").append(StringUtils.hasText(temp.getCalibre()) ? 
			    			temp.getCalibre() + " mm." : "-").append("</li>");
			    	resumenArma.append("	<li>Matricula: ").append(StringUtils.hasText(temp.getMatricula()) ? 
			    			temp.getMatricula() : "-").append("</li>");
			    	resumenArma.append("	<li>Condiciones: ").append(temp.getValorByCondicionFisicaVal() != null && StringUtils.hasText(temp.getValorByCondicionFisicaVal().getValor()) ? 
			    			temp.getValorByCondicionFisicaVal().getValor(): "-").append("</li>");
			    	resumenArma.append("	<li>Relaci\u00f3n del hecho: ").append(temp.getRelacionHechoVal() != null && StringUtils.hasText(temp.getRelacionHechoVal().getValor()) ? 
			    			temp.getRelacionHechoVal().getValor(): "-").append("</li>");
			    	resumenArma.append("	<li>Descripci\u00f3n: ").append(StringUtils.hasText(temp.getDescripcion()) ? 
			    			temp.getDescripcion(): "-").append("</li>");
			    	
			    	resumenArma.append("</ul>");
			    	
			    	resumenActual.setDescripcionResumen(resumenArma.toString());
			    	
			    }else{
					resumenActual.setDescripcionResumen(temp.getTipoArma()!=null?temp.getTipoArma().getValor():""
							);
			    }
			}
			if(tipo.equals(EXPLOSIVO)){
			    ExplosivoDTO temp = (ExplosivoDTO)objeto;
			    
			 // Valores agregados REQ6
			    if(expediente.isDatosPlatillaSolicitados()){
			    	StringBuffer resumenExplosivo = new StringBuffer();
			    	
			    	resumenExplosivo.append("<ul>");
			    	resumenExplosivo.append("	<li>Tipo: ").append(temp.getTipoExplosivo() != null && StringUtils.hasText(temp.getTipoExplosivo().getValor()) ? 
			    			temp.getTipoExplosivo().getValor(): "-").append("</li>");
			    	resumenExplosivo.append("	<li>Cantidad: ").append(temp.getCantidad() != null ? 
			    			temp.getCantidad() + " piezas" : "-").append("</li>");
			    	resumenExplosivo.append("	<li>Unidad de medida: ").append(temp.getUnidadMedida() != null && StringUtils.hasText(temp.getUnidadMedida().getValor()) ? 
			    			temp.getUnidadMedida().getValor(): "-").append("</li>");
			    	resumenExplosivo.append("	<li>Condici\u00f3n: ").append(temp.getValorByCondicionFisicaVal() != null && StringUtils.hasText(temp.getValorByCondicionFisicaVal().getValor()) ? 
			    			temp.getValorByCondicionFisicaVal().getValor(): "-").append("</li>");
			    	resumenExplosivo.append("	<li>Relaci\u00f3n del hecho: ").append(temp.getRelacionHechoVal() != null && StringUtils.hasText(temp.getRelacionHechoVal().getValor()) ? 
			    			temp.getRelacionHechoVal().getValor(): "-").append("</li>");
			    	resumenExplosivo.append("	<li>Descripci\u00f3n: ").append(StringUtils.hasText(temp.getDescripcion()) ? 
			    			temp.getDescripcion(): "-").append("</li>");
			    	
			    	resumenExplosivo.append("</ul>");
			    	
			    	resumenActual.setDescripcionResumen(resumenExplosivo.toString());
			    	
			    }else{
					resumenActual.setDescripcionResumen(
					        temp.getTipoExplosivo()!=null?temp.getTipoExplosivo().getValor():""
							);
			    }
			}
			if(tipo.equals(AERONAVE)){
			    AeronaveDTO temp = (AeronaveDTO)objeto;
			    
			 // Valores agregados REQ6
			    if(expediente.isDatosPlatillaSolicitados()){
			    	StringBuffer resumenAeronave = new StringBuffer();
			    	
			    	resumenAeronave.append("<ul>");
			    	resumenAeronave.append("	<li>Tipo: ").append(temp.getTipoAeroNave() != null && StringUtils.hasText(temp.getTipoAeroNave().getValor()) ? 
			    			temp.getTipoAeroNave().getValor(): "-").append("</li>");
			    	resumenAeronave.append("	<li>Marca: ").append(temp.getMarca() != null && StringUtils.hasText(temp.getMarca().getValor()) ? 
			    			temp.getMarca().getValor(): "-").append("</li>");
			    	resumenAeronave.append("	<li>Submarca: ").append(temp.getSubMarca() != null && StringUtils.hasText(temp.getSubMarca().getValor()) ? 
			    			temp.getSubMarca().getValor(): "-").append("</li>");
			    	resumenAeronave.append("	<li>Color: ").append(temp.getColor() != null && StringUtils.hasText(temp.getColor().getValor()) ? 
			    			temp.getColor().getValor(): "-").append("</li>");
			    	resumenAeronave.append("	<li>Pa\u00eds de Origen: ").append(temp.getPaisProcedencia() != null && StringUtils.hasText(temp.getPaisProcedencia().getValor()) ? 
			    			temp.getPaisProcedencia().getValor() : "-").append("</li>");
			    	resumenAeronave.append("	<li>Modelo: ").append(StringUtils.hasText(temp.getModelo()) ? 
			    			temp.getModelo(): "-").append("</li>");
			    	resumenAeronave.append("	<li>Matricula: ").append(StringUtils.hasText(temp.getMatricula()) ? 
			    			temp.getMatricula() : "-").append("</li>");
			    	resumenAeronave.append("	<li>No. de Serie: ").append(StringUtils.hasText(temp.getNoSerie()) ? 
			    			temp.getNoSerie(): "-").append("</li>");
			    	resumenAeronave.append("	<li>Condici\u00f3n: ").append(temp.getValorByCondicionFisicaVal() != null && StringUtils.hasText(temp.getValorByCondicionFisicaVal().getValor()) ? 
			    			temp.getValorByCondicionFisicaVal().getValor(): "-").append("</li>");
			    	resumenAeronave.append("	<li>Relaci\u00f3n del hecho: ").append(temp.getRelacionHechoVal() != null && StringUtils.hasText(temp.getRelacionHechoVal().getValor()) ? 
			    			temp.getRelacionHechoVal().getValor(): "-").append("</li>");
			    	resumenAeronave.append("	<li>Descripci\u00f3n: ").append(StringUtils.hasText(temp.getDescripcion()) ? 
			    			temp.getDescripcion(): "-").append("</li>");
			    	
			    	resumenAeronave.append("</ul>");
			    	
			    	resumenActual.setDescripcionResumen(resumenAeronave.toString());
			    	
			    }else{
					resumenActual.setDescripcionResumen(
					        temp.getTipoAeroNave()!=null?temp.getTipoAeroNave().getValor():""
							);
			    }
			}
			if(tipo.equals(ANIMAL)){
			    AnimalDTO temp = (AnimalDTO)objeto;
			    
			 // Valores agregados REQ6
			    if(expediente.isDatosPlatillaSolicitados()){
			    	StringBuffer resumenAnimal = new StringBuffer();
			    	
			    	resumenAnimal.append("<ul>");
			    	resumenAnimal.append("	<li>Tipo: ").append(temp.getTipoAnimal() != null && StringUtils.hasText(temp.getTipoAnimal().getValor()) ? 
			    			temp.getTipoAnimal().getValor(): "-").append("</li>");
			    	resumenAnimal.append("	<li>Raza: ").append(StringUtils.hasText(temp.getRazaAnimal()) ? 
			    			temp.getRazaAnimal(): "-").append("</li>");
			    	resumenAnimal.append("	<li>Estado del animal: ").append(StringUtils.hasText(temp.getEstadoAnimal()) ? 
			    			temp.getEstadoAnimal(): "-").append("</li>");
			    	resumenAnimal.append("	<li>Condici\u00f3n: ").append(temp.getValorByCondicionFisicaVal() != null && StringUtils.hasText(temp.getValorByCondicionFisicaVal().getValor()) ? 
			    			temp.getValorByCondicionFisicaVal().getValor(): "-").append("</li>");
			    	resumenAnimal.append("	<li>Relaci\u00f3n del hecho: ").append(temp.getRelacionHechoVal() != null && StringUtils.hasText(temp.getRelacionHechoVal().getValor()) ? 
			    			temp.getRelacionHechoVal().getValor(): "-").append("</li>");
			    	resumenAnimal.append("	<li>Descripci\u00f3n: ").append(StringUtils.hasText(temp.getDescripcion()) ? 
			    			temp.getDescripcion(): "-").append("</li>");
			    	
			    	resumenAnimal.append("</ul>");
			    	
			    	resumenActual.setDescripcionResumen(resumenAnimal.toString());
			    	
			    }else{
					resumenActual.setDescripcionResumen(
					        temp.getTipoAnimal()!=null?temp.getTipoAnimal().getValor():""
							);
			    }
			}
			if(tipo.equals(DOCUMENTO_OFICIAL)){
			    DocumentoOficialDTO temp = (DocumentoOficialDTO)objeto;
			    
			 // Valores agregados REQ6
			    if(expediente.isDatosPlatillaSolicitados()){
			    	StringBuffer resumenDocOficial = new StringBuffer();
			    	
			    	resumenDocOficial.append("<ul>");
			    	resumenDocOficial.append("	<li>Tipo: ").append(temp.getTipoDocumento() != null && StringUtils.hasText(temp.getTipoDocumento().getValor()) ? 
			    			temp.getTipoDocumento().getValor(): "-").append("</li>");
			    	resumenDocOficial.append("	<li>Cantidad de Hojas: ").append(temp.getCantidad() != null ? 
			    			temp.getCantidad() : "-").append("</li>");
			    	resumenDocOficial.append("	<li>Condici\u00f3n: ").append(temp.getValorByCondicionFisicaVal() != null && StringUtils.hasText(temp.getValorByCondicionFisicaVal().getValor()) ? 
			    			temp.getValorByCondicionFisicaVal().getValor(): "-").append("</li>");
			    	resumenDocOficial.append("	<li>Relaci\u00f3n del hecho: ").append(temp.getRelacionHechoVal() != null && StringUtils.hasText(temp.getRelacionHechoVal().getValor()) ? 
			    			temp.getRelacionHechoVal().getValor(): "-").append("</li>");
			    	resumenDocOficial.append("	<li>Descripci\u00f3n: ").append(StringUtils.hasText(temp.getDescripcion()) ? 
			    			temp.getDescripcion(): "-").append("</li>");
			    	
			    	resumenDocOficial.append("</ul>");
			    	
			    	resumenActual.setDescripcionResumen(resumenDocOficial.toString());
			    	
			    }else{
					resumenActual.setDescripcionResumen(
					        temp.getTipoDocumento()!=null?temp.getTipoDocumento().getValor():""
							);
			    }
			}
			if(tipo.equals(EMBARCACION)){
			    EmbarcacionDTO temp = (EmbarcacionDTO)objeto;
			    
			 // Valores agregados REQ6
			    if(expediente.isDatosPlatillaSolicitados()){
			    	StringBuffer resumenEmbarcacion = new StringBuffer();
			    	
			    	resumenEmbarcacion.append("<ul>");
			    	resumenEmbarcacion.append("	<li>Tipo: ").append(temp.getTipoEmbarcacion() != null && StringUtils.hasText(temp.getTipoEmbarcacion().getValor()) ? 
			    			temp.getTipoEmbarcacion().getValor(): "-").append("</li>");
			    	resumenEmbarcacion.append("	<li>Marca: ").append(temp.getMarca() != null && StringUtils.hasText(temp.getMarca().getValor()) ? 
			    			temp.getMarca().getValor(): "-").append("</li>");
			    	resumenEmbarcacion.append("	<li>Submarca: ").append(temp.getSubMarca() != null && StringUtils.hasText(temp.getSubMarca().getValor()) ? 
			    			temp.getSubMarca().getValor(): "-").append("</li>");
			    	resumenEmbarcacion.append("	<li>Color: ").append(temp.getColor() != null && StringUtils.hasText(temp.getColor().getValor()) ? 
			    			temp.getColor().getValor(): "-").append("</li>");
			    	resumenEmbarcacion.append("	<li>Pa\u00eds de Origen: ").append(temp.getPaisOrigen() != null && StringUtils.hasText(temp.getPaisOrigen().getValor()) ? 
			    			temp.getPaisOrigen().getValor() : "-").append("</li>");
			    	resumenEmbarcacion.append("	<li>Nombre de la Embarcaci\u00f3n: ").append(StringUtils.hasText(temp.getNombreEmbarcacion()) ? 
			    			temp.getNombreEmbarcacion(): "-").append("</li>");
			    	resumenEmbarcacion.append("	<li>Matricula: ").append(StringUtils.hasText(temp.getMatricula()) ? 
			    			temp.getMatricula() : "-").append("</li>");
			    	resumenEmbarcacion.append("	<li>No. de Motor: ").append(StringUtils.hasText(temp.getNoMotor()) ? 
			    			temp.getNoMotor(): "-").append("</li>");
			    	resumenEmbarcacion.append("	<li>No. de Serie: ").append(StringUtils.hasText(temp.getNoSerie()) ? 
			    			temp.getNoSerie(): "-").append("</li>");
			    	resumenEmbarcacion.append("	<li>Condici\u00f3n: ").append(temp.getValorByCondicionFisicaVal() != null && StringUtils.hasText(temp.getValorByCondicionFisicaVal().getValor()) ? 
			    			temp.getValorByCondicionFisicaVal().getValor(): "-").append("</li>");
			    	resumenEmbarcacion.append("	<li>Relaci\u00f3n del hecho: ").append(temp.getRelacionHechoVal() != null && StringUtils.hasText(temp.getRelacionHechoVal().getValor()) ? 
			    			temp.getRelacionHechoVal().getValor(): "-").append("</li>");
			    	resumenEmbarcacion.append("	<li>Descripci\u00f3n: ").append(StringUtils.hasText(temp.getDescripcion()) ? 
			    			temp.getDescripcion(): "-").append("</li>");
			    	
			    	resumenEmbarcacion.append("</ul>");
			    	
			    	resumenActual.setDescripcionResumen(resumenEmbarcacion.toString());
			    	
			    }else{
					resumenActual.setDescripcionResumen(
					        temp.getTipoEmbarcacion()!=null?temp.getTipoEmbarcacion().getValor():""
							);
			    }
			}
			/*if(tipo.equals(INMUEBLE)){
				resumenActual.setDescripcionResumen(
						((Innm)objeto).getTipoEmbarcacion().getValor()
						);
			}*/
			if(tipo.equals(JOYA)){
			    JoyaDTO temp = (JoyaDTO)objeto;
			    
			 // Valores agregados REQ6
			    if(expediente.isDatosPlatillaSolicitados()){
			    	StringBuffer resumenJoya = new StringBuffer();
			    	
			    	resumenJoya.append("<ul>");
			    	resumenJoya.append("	<li>Tipo: ").append(temp.getTipoJoya() != null && StringUtils.hasText(temp.getTipoJoya().getValor()) ? 
			    			temp.getTipoJoya().getValor(): "-").append("</li>");
			    	resumenJoya.append("	<li>Material de la Joya: ").append(StringUtils.hasText(temp.getMaterial()) ? 
			    			temp.getMaterial(): "-").append("</li>");
			    	resumenJoya.append("	<li>Cantidad: ").append(temp.getCantidad() != null ? 
			    			temp.getCantidad() + " piezas" : "-").append("</li>");
			    	resumenJoya.append("	<li>Condici\u00f3n: ").append(temp.getValorByCondicionFisicaVal() != null && StringUtils.hasText(temp.getValorByCondicionFisicaVal().getValor()) ? 
			    			temp.getValorByCondicionFisicaVal().getValor(): "-").append("</li>");
			    	resumenJoya.append("	<li>Relaci\u00f3n del hecho: ").append(temp.getRelacionHechoVal() != null && StringUtils.hasText(temp.getRelacionHechoVal().getValor()) ? 
			    			temp.getRelacionHechoVal().getValor(): "-").append("</li>");
			    	resumenJoya.append("	<li>Descripci\u00f3n: ").append(StringUtils.hasText(temp.getDescripcion()) ? 
			    			temp.getDescripcion(): "-").append("</li>");
			    	
			    	resumenJoya.append("</ul>");
			    	
			    	resumenActual.setDescripcionResumen(resumenJoya.toString());
			    	
			    }else{
					resumenActual.setDescripcionResumen(
					        temp.getTipoJoya()!=null?temp.getTipoJoya().getValor():""
							);
			    }
			}
			if(tipo.equals(NUMERARIO)){
				NumerarioDTO temp = (NumerarioDTO)objeto;
				
				// Valores agregados REQ6
			    if(expediente.isDatosPlatillaSolicitados()){
			    	StringBuffer resumenNumerario = new StringBuffer();
			    	String fecha = "-";
			    	String hora = "-";
			    	
			    	if(temp.getFechaDeposito() != null){
			    				    			
		    			SimpleDateFormat formating = new SimpleDateFormat("dd/MM/yyyy HH:mm");
		    			String fechaDeposito = formating.format(temp.getFechaDeposito());
		    			String[] fechaHora = fechaDeposito.split(" ");
		    			fecha = fechaHora[0];
		    			hora = fechaHora[1];
			    	}
			    	
			    	resumenNumerario.append("<ul>");
			    	resumenNumerario.append("	<li>Fecha de Dep\u00f3sito: ").append(fecha).append("</li>");
			    	resumenNumerario.append("	<li>Hora de Dep\u00f3sito: ").append(hora).append("</li>");
			    	resumenNumerario.append("	<li>Cantidad: ").append(temp.getCantidad() != null ? 
			    			temp.getCantidad() + " piezas" : "-").append("</li>");
			    	resumenNumerario.append("	<li>Moneda: ").append(StringUtils.hasText(temp.getMoneda())? 
			    			temp.getMoneda() : "-").append("</li>");
			    	resumenNumerario.append("	<li>Cuenta Tesorer\u00eda: ").append(StringUtils.hasText(temp.getCtaTesoreria())? 
					    	temp.getCtaTesoreria() : "-").append("</li>");
			    	resumenNumerario.append("	<li>Condici\u00f3n: ").append(temp.getValorByCondicionFisicaVal() != null && StringUtils.hasText(temp.getValorByCondicionFisicaVal().getValor()) ? 
			    			temp.getValorByCondicionFisicaVal().getValor(): "-").append("</li>");
			    	resumenNumerario.append("	<li>Relaci\u00f3n del hecho: ").append(temp.getRelacionHechoVal() != null && StringUtils.hasText(temp.getRelacionHechoVal().getValor()) ? 
			    			temp.getRelacionHechoVal().getValor(): "-").append("</li>");
			    	resumenNumerario.append("	<li>Descripci\u00f3n: ").append(StringUtils.hasText(temp.getDescripcion()) ? 
			    			temp.getDescripcion(): "-").append("</li>");
			    	
			    	resumenNumerario.append("</ul>");
			    	
			    	resumenActual.setDescripcionResumen(resumenNumerario.toString());
			    	
			    }else{
					resumenActual.setDescripcionResumen(
							((NumerarioDTO)objeto).getMoneda()
							);
			    }
			}
			if(tipo.equals(OBRA_DE_ARTE)){
			    ObraArteDTO temp = (ObraArteDTO)objeto;
			    
			 // Valores agregados REQ6
			    if(expediente.isDatosPlatillaSolicitados()){
			    	StringBuffer resumenObraArte = new StringBuffer();
			    	
			    	resumenObraArte.append("<ul>");
			    	resumenObraArte.append("	<li>Tipo: ").append(temp.getTipoObraArte() != null && StringUtils.hasText(temp.getTipoObraArte().getValor()) ? 
			    			temp.getTipoObraArte().getValor(): "-").append("</li>");
			    	resumenObraArte.append("	<li>Cantidad: ").append(temp.getCantidad() != null ? 
			    			temp.getCantidad() + " piezas" : "-").append("</li>");
			    	resumenObraArte.append("	<li>Condici\u00f3n: ").append(temp.getValorByCondicionFisicaVal() != null && StringUtils.hasText(temp.getValorByCondicionFisicaVal().getValor()) ? 
			    			temp.getValorByCondicionFisicaVal().getValor(): "-").append("</li>");
			    	resumenObraArte.append("	<li>Relaci\u00f3n del hecho: ").append(temp.getRelacionHechoVal() != null && StringUtils.hasText(temp.getRelacionHechoVal().getValor()) ? 
			    			temp.getRelacionHechoVal().getValor(): "-").append("</li>");
			    	resumenObraArte.append("	<li>Descripci\u00f3n: ").append(StringUtils.hasText(temp.getDescripcion()) ? 
			    			temp.getDescripcion(): "-").append("</li>");
			    	
			    	resumenObraArte.append("</ul>");
			    	
			    	resumenActual.setDescripcionResumen(resumenObraArte.toString());
			    	
			    }else{
					resumenActual.setDescripcionResumen(
					        temp.getTipoObraArte()!=null?temp.getTipoObraArte().getValor():""
							);
			    }
			}
			if(tipo.equals(SUSTANCIA)){
			    SustanciaDTO temp = (SustanciaDTO)objeto;
			    
			 // Valores agregados REQ6
			    if(expediente.isDatosPlatillaSolicitados()){
			    	StringBuffer resumenSustancia = new StringBuffer();
			    	
			    	resumenSustancia.append("<ul>");
			    	resumenSustancia.append("	<li>Tipo: ").append(temp.getTipoSustancia() != null && StringUtils.hasText(temp.getTipoSustancia().getValor()) ? 
			    			temp.getTipoSustancia().getValor(): "-").append("</li>");
			    	resumenSustancia.append("	<li>Tipo de empaque: ").append(temp.getEmpaque() != null && StringUtils.hasText(temp.getEmpaque().getValor()) ? 
			    			temp.getEmpaque().getValor(): "-").append("</li>");
			    	resumenSustancia.append("	<li>Cantidad: ").append(temp.getCantidad() != null ? 
			    			temp.getCantidad() : "-").append("</li>");
			    	resumenSustancia.append("	<li>Unidad de medida: ").append(temp.getUnidadMedida() != null && StringUtils.hasText(temp.getUnidadMedida().getValor()) ? 
			    			temp.getUnidadMedida().getValor(): "-").append("</li>");
			    	resumenSustancia.append("	<li>Condici\u00f3n: ").append(temp.getValorByCondicionFisicaVal() != null && StringUtils.hasText(temp.getValorByCondicionFisicaVal().getValor()) ? 
			    			temp.getValorByCondicionFisicaVal().getValor(): "-").append("</li>");
			    	resumenSustancia.append("	<li>Relaci\u00f3n del hecho: ").append(temp.getRelacionHechoVal() != null && StringUtils.hasText(temp.getRelacionHechoVal().getValor()) ? 
			    			temp.getRelacionHechoVal().getValor(): "-").append("</li>");
			    	resumenSustancia.append("	<li>Descripci\u00f3n: ").append(StringUtils.hasText(temp.getDescripcion()) ? 
			    			temp.getDescripcion(): "-").append("</li>");
			    	
			    	resumenSustancia.append("</ul>");
			    	
			    	resumenActual.setDescripcionResumen(resumenSustancia.toString());
			    	
			    }else{
					resumenActual.setDescripcionResumen(
					        temp.getTipoSustancia()!=null?temp.getTipoSustancia().getValor():""
							);
			    }
			}
			if(tipo.equals(VEGETAL)){
			    VegetalDTO temp = (VegetalDTO)objeto;
			    
			 // Valores agregados REQ6
			    if(expediente.isDatosPlatillaSolicitados()){
			    	StringBuffer resumenVegetal = new StringBuffer();
			    	
			    	resumenVegetal.append("<ul>");
			    	resumenVegetal.append("	<li>Tipo: ").append(temp.getTipoVegetal() != null && StringUtils.hasText(temp.getTipoVegetal().getValor()) ? 
			    			temp.getTipoVegetal().getValor(): "-").append("</li>");
			    	resumenVegetal.append("	<li>Cantidad: ").append(temp.getCantidad() != null ? 
			    			temp.getCantidad() + " piezas" : "-").append("</li>");
			    	resumenVegetal.append("	<li>Condici\u00f3n: ").append(temp.getValorByCondicionFisicaVal() != null && StringUtils.hasText(temp.getValorByCondicionFisicaVal().getValor()) ? 
			    			temp.getValorByCondicionFisicaVal().getValor(): "-").append("</li>");
			    	resumenVegetal.append("	<li>Relaci\u00f3n del hecho: ").append(temp.getRelacionHechoVal() != null && StringUtils.hasText(temp.getRelacionHechoVal().getValor()) ? 
			    			temp.getRelacionHechoVal().getValor(): "-").append("</li>");
			    	resumenVegetal.append("	<li>Descripci\u00f3n: ").append(StringUtils.hasText(temp.getDescripcion()) ? 
			    			temp.getDescripcion(): "-").append("</li>");
			    	
			    	resumenVegetal.append("</ul>");
			    	
			    	resumenActual.setDescripcionResumen(resumenVegetal.toString());
			    	
			    }else{
					resumenActual.setDescripcionResumen(
					        temp.getTipoVegetal()!=null?temp.getTipoVegetal().getValor():""
							);
			    }
			}
			if(tipo.equals(OTRO)){
				ObjetoDTO temp = (ObjetoDTO)objeto;
				
				// Valores agregados REQ6
			    if(expediente.isDatosPlatillaSolicitados()){
			    	StringBuffer resumenVegetal = new StringBuffer();
			    	
			    	resumenVegetal.append("<ul>");
			    	resumenVegetal.append("	<li>Nombre: ").append(StringUtils.hasText(temp.getNombreObjeto()) ? 
			    			temp.getNombreObjeto(): "-").append("</li>");
			    	resumenVegetal.append("	<li>Relaci\u00f3n del hecho: ").append(temp.getRelacionHechoVal() != null && StringUtils.hasText(temp.getRelacionHechoVal().getValor()) ? 
			    			temp.getRelacionHechoVal().getValor(): "-").append("</li>");
			    	resumenVegetal.append("	<li>Descripci\u00f3n: ").append(StringUtils.hasText(temp.getDescripcion()) ? 
			    			temp.getDescripcion(): "-").append("</li>");
			    	
			    	resumenVegetal.append("</ul>");
			    	
			    	resumenActual.setDescripcionResumen(resumenVegetal.toString());
			    	
			    }else{
					resumenActual.setDescripcionResumen(
							((ObjetoDTO)objeto).getDescripcion()
							);
			    }
			}
			if(tipo.equals(PERICIAL)){
				ObjetoPericialDTO temp = (ObjetoPericialDTO)objeto;
				
				// Valores agregados REQ6
			    if(expediente.isDatosPlatillaSolicitados()){
			    	StringBuffer resumenPericial = new StringBuffer();
			    	
			    	resumenPericial.append("<ul>");
			    	resumenPericial.append("	<li>Categor\u00eda: ").append(temp.getCategoriaIndicioVal() != null && StringUtils.hasText(temp.getCategoriaIndicioVal().getValor()) ? 
			    			temp.getCategoriaIndicioVal().getValor(): "-").append("</li>");
			    	resumenPericial.append("	<li>Objeto: ").append(temp.getIndicioVal() != null && StringUtils.hasText(temp.getIndicioVal().getValor()) ? 
			    			temp.getIndicioVal().getValor(): "-").append("</li>");
			    	resumenPericial.append("	<li>Descripci\u00f3n: ").append(StringUtils.hasText(temp.getDescripcion()) ? 
			    			temp.getDescripcion(): "-").append("</li>");
			    	
			    	resumenPericial.append("</ul>");
			    	
			    	resumenActual.setDescripcionResumen(resumenPericial.toString());
			    	
			    }else{
					resumenActual.setDescripcionResumen(
							((ObjetoPericialDTO)objeto).getDescripcion()
							);
			    }
			}
			
			objetosResumen.add(resumenActual);
		}
		return objetosResumen;
	}

	
	
}
