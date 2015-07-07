/**
 * Nombre del Programa : SentenciaDocumentoDTO.java
 * Autor                            : AntonioBV
 * Compania                    : Ultrasist
 * Proyecto                      : NSJP                    Fecha: 16/07/2012
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
package mx.gob.segob.nsjp.dto.sentencia;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import mx.gob.segob.nsjp.comun.util.DateUtils;
import mx.gob.segob.nsjp.dto.detencion.PertenenciaDTO;
import mx.gob.segob.nsjp.dto.programas.ActoBuenaConductaDTO;
import mx.gob.segob.nsjp.dto.programas.AsignacionProgramaDTO;
import mx.gob.segob.nsjp.dto.programas.CatCursoDTO;
import mx.gob.segob.nsjp.dto.programas.CatProgramaDTO;
import mx.gob.segob.nsjp.dto.programas.CatTrabajoDTO;
import mx.gob.segob.nsjp.dto.programas.CursoDTO;
import mx.gob.segob.nsjp.dto.programas.PeriodoAcumulacionPuntosDTO;
import mx.gob.segob.nsjp.dto.programas.ProgramaDTO;
import mx.gob.segob.nsjp.dto.programas.TrabajoDTO;

/**
 * Describir el objetivo de la clase con punto al final.
 * 
 * @version 1.0
 * @author AntonioBV
 * 
 */
public class SentenciaDocumentoDTO {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3086295204165985596L;
	
	private static final String EXTERNO = "Externo";
	private static final String INTERNO = "Interno";
	private static final String NO_APLICA = "N/A";

	private SentenciaDTO sentenciaDTO;

	/**
	 * M&eacute;todo de acceso al campo sentenciaDTO.
	 * 
	 * @return El valor del campo sentenciaDTO
	 */
	public SentenciaDTO getSentenciaDTO() {
		return sentenciaDTO;
	}

	/**
	 * Asigna el valor al campo sentenciaDTO.
	 * 
	 * @param sentenciaDTO
	 *            el valor sentenciaDTO a asignar
	 */
	public void setSentenciaDTO(SentenciaDTO sentenciaDTO) {
		this.sentenciaDTO = sentenciaDTO;
	}

	public String getLstProgramasDTO() {
		StringBuilder resultado = new StringBuilder("");
		
		if (this.sentenciaDTO != null) {
			if (this.sentenciaDTO.getAsignacionProgramas() != null) {
				List<ProgramaDTO> programasAsignados = new ArrayList<ProgramaDTO>();
				List<CursoDTO> cursosAsignados = new ArrayList<CursoDTO>();
				List<TrabajoDTO> trabajosAsignados = new ArrayList<TrabajoDTO>();
				for (AsignacionProgramaDTO asignacionProgramaDTO : this.sentenciaDTO
						.getAsignacionProgramas()) {
					if (asignacionProgramaDTO.getProgramaDTO() != null) {
						programasAsignados.add(asignacionProgramaDTO.getProgramaDTO());
						cursosAsignados.addAll(asignacionProgramaDTO.getProgramaDTO().getCursos());
						trabajosAsignados.addAll(asignacionProgramaDTO.getProgramaDTO().getTrabajos());
					}
				}
				resultado.append(crearTablaProgramas(programasAsignados));
				resultado.append("<br>");
				resultado.append(crearTablaCursos(cursosAsignados));
				resultado.append("<br>");
				resultado.append(crearTablaTrabajos(trabajosAsignados));
				resultado.append("<br>");
			}
		}
		return resultado.toString();
	}
	
	/**
	 * M&eacute;todo utilitario que lleva a cabo la creaci&oacute;n de una tabla 
	 * con la informaci&oacute;n de los programas que se encuentran contenidos 
	 * dentro de la lista pasada como par&aacute;metro.
	 * @param programas - List<ProgramaDTO> con los programas a partir de los 
	 * 					  cuales se generar&aacute; la tabla.
	 * @return String - Cadena con la representaci&oacute;n en HTML de la tabla 
	 * 					con la informaci&oacute;n de los programas.
	 */
	private String crearTablaProgramas(List<ProgramaDTO> programas){
		StringBuilder tabla = new StringBuilder("");
		if (programas != null && !programas.isEmpty()){
			tabla.append("<fieldset>");
			tabla.append("	<legend>Programas</legend>");
			tabla.append("	<table border='0' width='100%'>");
			tabla.append("		<tr>");
			tabla.append("			<td align='center'>Nombre</td>");
			tabla.append("			<td align='center'>Tipo de Programa</td>");
			tabla.append("			<td align='center'>Descripci&oacute;n</td>");
			tabla.append("			<td align='center'>Total De Puntos</td>");
			tabla.append("			<td align='center'>Fecha de Inicio</td>");
			tabla.append("			<td align='center'>Fecha de T&eacute;rmino</td>");
			tabla.append("		</tr>");
			for (ProgramaDTO programaDTO : programas){
				CatProgramaDTO 	catProgramaDTO = programaDTO.getCatProgramaDTO();
				tabla.append("	<tr>");
				tabla.append(crearCeldaTabla(catProgramaDTO.getNombre()));
				if(catProgramaDTO.getCatTipoProgramaDTO()!= null) {
					tabla.append(crearCeldaTabla(catProgramaDTO.getCatTipoProgramaDTO().getDescripcion()));
				} else {
					tabla.append(crearCeldaTabla(null));
				}
				tabla.append(crearCeldaTabla(catProgramaDTO.getDescripcion()));
				if(catProgramaDTO.getTotalPuntosCursos() != null){
					tabla.append(crearCeldaTabla(catProgramaDTO.getTotalPuntosPrograma()));
				} else {
						tabla.append(crearCeldaTabla(null));
				}
				tabla.append(crearCeldaTabla(programaDTO.getDfechaIngreso()));
				tabla.append(crearCeldaTabla(programaDTO.getDfechaTermino()));
				tabla.append("	</tr>");
			}
			tabla.append("	</table>");
			tabla.append("</fieldset>");
		}
		return tabla.toString();
	}
	
	/**
	 * M&eacute;todo utilitario que lleva a cabo la creaci&oacute;n de una tabla 
	 * con la informaci&oacute;n de los cursos que se encuentran contenidos 
	 * dentro de la lista pasada como par&aacute;metro.
	 * @param programas - List<CursoDTO> con los cursos a partir de los 
	 * 					  cuales se generar&aacute; la tabla.
	 * @return String - Cadena con la representaci&oacute;n en HTML de la tabla 
	 * 					con la informaci&oacute;n de los cursos.
	 */
	private String crearTablaCursos(List<CursoDTO> cursos){
		StringBuilder tabla = new StringBuilder("");
		if (cursos != null && !cursos.isEmpty()) {
			tabla.append("<fieldset>");
			tabla.append("	<legend>Cursos Asignados</legend>");
			tabla.append("	<table border='0' width='100%'>");
			tabla.append("		<tr>");
			tabla.append("			<td align='center'>Nombre</td>");
			tabla.append("			<td align='center'>Descripci&oacute;n</td>");
			tabla.append("			<td align='center'>Tipo</td>");
			tabla.append("			<td align='center'>Categor&iacute;a</td>");
			tabla.append("			<td align='center'>Nivel Acad&eacute;mico</td>");
			tabla.append("			<td align='center'>N&uacute;mero De Puntos</td>");
			tabla.append("			<td align='center'>N&uacute;mero De Puntos Obtenidos</td>");
			tabla.append("			<td align='center'>Duraci&oacute;n</td>");
			tabla.append("		</tr>");
			for (CursoDTO cursoDTO : cursos) {
				CatCursoDTO catCursoDTO = cursoDTO.getCatCursoDTO();
				tabla.append("	<tr>");
				tabla.append(crearCeldaTabla(catCursoDTO.getCnombre()));
				tabla.append(crearCeldaTabla(catCursoDTO.getCdescripcion()));
				tabla.append(crearCeldaTabla(catCursoDTO.getCatTipoCursoDTO().getDescripcion()));
				tabla.append(crearCeldaTabla(catCursoDTO.getCatCategoriaCursoDTO().getDescripcion()));
				if(catCursoDTO.getCatTipoNivelAcademicoDTO() != null){
					tabla.append(crearCeldaTabla(catCursoDTO.getCatTipoNivelAcademicoDTO().getDescripcion()));
				}else{
					tabla.append(crearCeldaTabla(null));
				}
				tabla.append(crearCeldaTabla(catCursoDTO.getIpuntos()));
				if(cursoDTO.getIpuntosObtenidos() != null && cursoDTO.getIpuntosObtenidos() > 0){
					tabla.append(crearCeldaTabla(cursoDTO.getIpuntosObtenidos()));
				}else{
					tabla.append(crearCeldaTabla(new Integer(0)));
				}
				tabla.append(crearCeldaTabla(catCursoDTO.getCduracion()));
				tabla.append("	</tr>");											
			}
			tabla.append("	</table>");
			tabla.append("</fieldset>");
		}
		return tabla.toString();
	}
	
	/**
	 * M&eacute;todo utilitario que lleva a cabo la creaci&oacute;n de una tabla 
	 * con la informaci&oacute;n de los trabajos que se encuentran contenidos 
	 * dentro de la lista pasada como par&aacute;metro.
	 * @param programas - List<TrabajoDTO> con los trabajos a partir de los 
	 * 					  cuales se generar&aacute; la tabla.
	 * @return String - Cadena con la representaci&oacute;n en HTML de la tabla 
	 * 					con la informaci&oacute;n de los trabajos.
	 */
	private String crearTablaTrabajos(List<TrabajoDTO> trabajos){
		StringBuilder tabla = new StringBuilder("");
		if (trabajos != null && !trabajos.isEmpty()) {
			tabla.append("<fieldset>");
			tabla.append("	<legend>Trabajos Asignados</legend>");
			tabla.append("	<table border='0' width='100%'>");
			tabla.append("		<tr>");
			tabla.append("			<td align='center'>Nombre:</td>");
			tabla.append("			<td align='center'>Descripci&oacute;n:</td>");
			tabla.append("			<td align='center'>Tipo:</td>");
			tabla.append("			<td align='center'>N&uacute;mero De Convenio:</td>");
			tabla.append("			<td align='center'>N&uacute;mero De Puntos</td>");
			tabla.append("			<td align='center'>N&uacute;mero De Puntos Obtenidos</td>");
			tabla.append("		</tr>");
			for (TrabajoDTO trabajoDTO : trabajos) {
				CatTrabajoDTO catTrabajoDTO = trabajoDTO.getCatTrabajoDTO();
				tabla.append("	<tr>");
				tabla.append(crearCeldaTabla(catTrabajoDTO.getCnombre()));
				tabla.append(crearCeldaTabla(catTrabajoDTO.getCdescripcion()));
				if(catTrabajoDTO.getBesExterno()!= null){
					if(catTrabajoDTO.getBesExterno()){
						if(catTrabajoDTO.getCatTipoTrabajoExterno() != null){
							tabla.append(crearCeldaTabla(EXTERNO + "-" + catTrabajoDTO.getCatTipoTrabajoExterno().getDescripcion()));
						}else{
							tabla.append(crearCeldaTabla(EXTERNO));
						}
						tabla.append(crearCeldaTabla(catTrabajoDTO.getCnumeroConvenio()));
					}else{
						tabla.append(crearCeldaTabla(INTERNO));
						tabla.append(crearCeldaTabla(NO_APLICA));
					}
				}else {
						tabla.append(crearCeldaTabla(INTERNO));
						tabla.append(crearCeldaTabla(NO_APLICA));
				}
				if(catTrabajoDTO.getIpuntos() != null){
					tabla.append(crearCeldaTabla(catTrabajoDTO.getIpuntos()));
				}
				if(trabajoDTO.getIpuntosObtenidos() != null 
						&& trabajoDTO.getIpuntosObtenidos() > 0){
					tabla.append(crearCeldaTabla(trabajoDTO.getIpuntosObtenidos()));
				}else{
					tabla.append(crearCeldaTabla(new Integer(0)));					
				}
				tabla.append("	</tr>");
			}
			tabla.append("	</table>");
			tabla.append("</fieldset>");
		}
		return tabla.toString();
	}
	
	/**
	 * M&eacute;todo que lleva a cabo la generaci&oacute;n de una celda de tabla 
	 * en c&oacute;digo HTML en d&oacute;nde se valida el valor que se pasa como
	 * par&aacute;metro, en el caso de que dicho valor corresponda con <code>null</code>
	 * se mete dentro de la tabla la secuencia de escape para un espacio en blanco.
	 * En el caso de que el valor del par&aacute;metro pasado corresponda con un objeto de
	 * tipo date, se parsea la fecha y se regresa como cadena en formato dd/MM/yyyy.
	 * @param valor - El objeto del cual se obtendr&aacute; el valor como cadena 
	 * @return String - Cadena que representa la informaci&oacute;n del objeto pasado 
	 * 					como par&aacute;metro dentro de una celda de tabla de HTML.
	 */
	private String crearCeldaTabla (Object valor){
		StringBuilder celda = new StringBuilder("");
		String dato = "&nbsp;";
		if (valor != null){
			if (valor instanceof Date){
				dato = DateUtils.formatear((Date) valor);
			}else{
				dato = valor.toString();
			}
		}
		celda.append("<td>");
		celda.append(dato);
		celda.append("</td>");
		return celda.toString();
	}
	
	/**
	 * M&eacute;todo que lleva a cabo la generaci&oacute;n del c&oacute;digo en 
	 * HTML para la representaci&oacute;n del inventario de pertenencias en la
	 * plantilla correspondiente.
	 * @return String - Cadena con el HTML para representar la tabla.
	 */
	public String getInventarioPertenencias(){
		StringBuilder tabla = new StringBuilder("");
		if (sentenciaDTO.getInventarioPertenenciaDTO() != null){
			tabla.append("<fieldset>");
				tabla.append("<table border='0' width='100%'>");
					tabla.append("<tr>");
						tabla.append("<td align='center'>Tipo</td>");
						tabla.append("<td align='center'>Descripci&oacute;n</td>");
						tabla.append("<td align='center'>Cantidad</td>");
						tabla.append("<td align='center'>Unidad de Medida</td>");
					tabla.append("</tr>");
			for (PertenenciaDTO pertenencia : sentenciaDTO.getInventarioPertenenciaDTO().getPertenenciasDTO()){
					tabla.append("<tr>");
						tabla.append("<td>");
							tabla.append(pertenencia.getTipoPertenencia() != null ? 
									pertenencia.getTipoPertenencia().getValor() != null ? 
											pertenencia.getTipoPertenencia().getValor() : "&nbsp;"
											: "&nbsp;");
						tabla.append("</td>");
						tabla.append("<td>");
							tabla.append(pertenencia.getDescripcion() != null ? pertenencia.getDescripcion() : "&nbsp;");
						tabla.append("</td>");
						tabla.append("<td>");
							tabla.append(pertenencia.getCantidad() != null ? pertenencia.getCantidad() : "&nbsp;");
						tabla.append("</td>");
						tabla.append("<td>");
							tabla.append(pertenencia.getUnidadMedida() != null ? 
									pertenencia.getUnidadMedida().getValor() != null ? 
											pertenencia.getUnidadMedida().getValor() : "Pieza" 
												: "Pieza");
						tabla.append("</td>");
					tabla.append("</tr>");
			}
				tabla.append("</table>");
			tabla.append("</fieldset>");
		}
		return tabla.toString();
	}
	
	/**
	 * M&eacute;todo que lleva a cabo la generaci&oacute;n del c&oacute;digo en 
	 * HTML para la representaci&oacute;n del resumen de los per&iacute;odos de 
	 * acumulaci&oacute;n de puntos para la sentencia en la plantilla correspondiente.
	 * @return String - Cadena con el HTML para representar la tabla.
	 */
	public String getPeriodosAcumulacionPuntos(){
		StringBuilder tabla = new StringBuilder("");
		Map<Long,Integer> mapaPuntos = new HashMap<Long,Integer>();
		List<PeriodoAcumulacionPuntosDTO> periodos = obtenerPeriodosSinResumen(mapaPuntos);
		if (periodos != null
				&& !periodos.isEmpty()){
			tabla.append("<fieldset>");
				tabla.append("<table border='1' width='100%'>");
					tabla.append("<tr>");
						tabla.append("<td align='center'>Nombre del per&iacute;odo</td>");
						tabla.append("<td align='center'>Fecha de inicio</td>");
						tabla.append("<td align='center'>Fecha de t&eacute;rmino</td>");
						tabla.append("<td align='center'>Total de puntos</td>");
					tabla.append("</tr>");
			for (PeriodoAcumulacionPuntosDTO periodo : periodos){
					tabla.append("<tr>");
						tabla.append("<td>");
							tabla.append(periodo.getcNombrePeriodo() != null ?  
											periodo.getcNombrePeriodo() : "&nbsp;");
						tabla.append("</td>");
						tabla.append("<td>");
							tabla.append(DateUtils.formatear(periodo.getDfechaInicio()));
						tabla.append("</td>");
						tabla.append("<td>");
							tabla.append(DateUtils.formatear(periodo.getDfechaTermino()));
						tabla.append("</td>");
						tabla.append("<td>");
							tabla.append(mapaPuntos.get(periodo.getPeriodoAcumulacionPuntosId()));
						tabla.append("</td>");
					tabla.append("</tr>");
			}
				tabla.append("</table>");
			tabla.append("</fieldset>");
		}
		return tabla.toString();
	}
	
	/**
	 * M&eacute;todo utilitario que lleva a cabo la creaci&oacute;n de una lista con todos los
	 * per&iacute;odos de acumulaci&oacute;n de puntos cuyo resumen no ha sido emitido asociados
	 * a una sentencia. 
	 * @return List<PeriodoAcumulacionPuntosDTO> - Lista con los per&iacute;odos de acumulaci&oacute;n
	 * 											   de puntos cuyo resumen no ha sido emitido.
	 */
	private List<PeriodoAcumulacionPuntosDTO> obtenerPeriodosSinResumen(Map<Long,Integer> mapaPuntos){
		List<PeriodoAcumulacionPuntosDTO> periodos = null;
		Map<Long,PeriodoAcumulacionPuntosDTO> mapaPeriodos = new HashMap<Long, PeriodoAcumulacionPuntosDTO>();
		if (sentenciaDTO.getActoBuenaConductas() != null
				&& !sentenciaDTO.getActoBuenaConductas().isEmpty()){
			periodos = new ArrayList<PeriodoAcumulacionPuntosDTO>();
			for (ActoBuenaConductaDTO acto : sentenciaDTO.getActoBuenaConductas()){
				if (acto.getPeriodoAcumulacionPuntos() != null
						&& !acto.getPeriodoAcumulacionPuntos().isbResumenEmitido()){
					if (!mapaPeriodos.containsKey(acto.getPeriodoAcumulacionPuntos().getPeriodoAcumulacionPuntosId())){
						mapaPeriodos.put(acto.getPeriodoAcumulacionPuntos().getPeriodoAcumulacionPuntosId(), acto.getPeriodoAcumulacionPuntos());
						mapaPuntos.put(acto.getPeriodoAcumulacionPuntos().getPeriodoAcumulacionPuntosId(), acto.getIpuntosOtorgados().intValue());
					}else{
						Integer ptosObtenidos = mapaPuntos.get(acto.getPeriodoAcumulacionPuntos().getPeriodoAcumulacionPuntosId());
						mapaPuntos.put(acto.getPeriodoAcumulacionPuntos().getPeriodoAcumulacionPuntosId(), ptosObtenidos + acto.getIpuntosOtorgados().intValue());
					}
				}
			}
			periodos.addAll(mapaPeriodos.values());
		}
		return periodos;
	}
}
