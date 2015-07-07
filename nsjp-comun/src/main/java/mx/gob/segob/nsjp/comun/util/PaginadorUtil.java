/**
 * Nombre del Programa : PaginadorUtil.java
 * Autor                            : GustavoBP
 * Compania                    : Ultrasist
 * Proyecto                      : NSJP                    Fecha: 02 Oct 2012
 * Marca de cambio        : N/A
 * Descripcion General    : Clase de utileria para la obtener la paginacion: Manual o Automatica.
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
package mx.gob.segob.nsjp.comun.util;

import java.util.List;

import mx.gob.segob.nsjp.comun.util.tl.PaginacionThreadHolder;
import mx.gob.segob.nsjp.dto.base.PaginacionDTO;

/**
 * Clase de utileria para la obtener la paginacion: Manual o Automatica.
 * 
 * @author GustavoBP
 */
public class PaginadorUtil {

	/**
	 * M&eacute;todo que es utilizado para obtener la configuraci&oacute;n de la paginaci&oacute;n
	 * una vez que los registros de la lista de resultados obtenidos, se ha aplicado ciertos filtros a nivel
	 * del action o service.
	 * Los atributos de configuraci&oacute;n que son alterados son:
	 * 1) Pagina.
	 * 2) Pagianacion hecha
	 * 3) Total de Registros.
	 * 4) Numero de registros.
	 * 
	 * Previo a la invocaci&oacute;n del servicio que hace la consulta de la informaci&oacute;n se debe de habilitar 
	 * la configuraci&oacute;n de la paginaci&oacute;n en true.
	 * 
	 * 
	 * Buscar etiqueta: CONFIG. INICIAL PAG. MANUAL
	 *    final PaginacionDTO pag = PaginacionThreadHolder.get();
	 *    pag.setPaginacionHecha(true);
	 *    PaginacionThreadHolder.set(pag);
	 * 
	 * Buscar etiqueta: CONFIG. FINAL PAG. MANUAL
	 *    List<InvolucradoDTO> listaResultado = (List<InvolucradoDTO>) PaginadorUtil.obtenerPaginacionManual(listaInvolucradosPersonasFisicas);
	 *    String paginacion = PaginadorUtil.obtenerPaginacionAutomatica();
	 * 
	 * @param listaOriginal depues de aplicar el filtro.
	 * @return sublista de acuerdo a la pagina seleccionada.
	 */
	public static List<?> obtenerPaginacionManual(List<?> listaOriginal){
		
		List<?> listaResultado =  null;
		final PaginacionDTO pag = PaginacionThreadHolder.get();

		if (listaOriginal == null) {
			pag.setPage(0);
			pag.setPaginacionHecha(true);
			pag.setTotalRegistros(0L);
			pag.setRows(0);
		} else {
			if ((pag.getPage() * pag.getRows()) - pag.getRows() > listaOriginal
					.size()) {
				pag.setPage(1);
			}
			int inicio = 0;
			if (pag.getPage() <= 1) {
				inicio = 0;
			} else {
				inicio = ((pag.getPage() - 1) * pag.getRows());
			}
			int indiceFinal = inicio + pag.getRows();
			//Total de datos original
			pag.setTotalRegistros((long) listaOriginal.size());

			//El metodo subList, hace una vista de la lista original, no se crea una nueva referencia.
			if (indiceFinal >= listaOriginal.size()) {
				listaResultado = listaOriginal.subList(inicio,
						listaOriginal.size());
			} else {
				listaResultado = listaOriginal.subList(inicio, indiceFinal);
			}
			pag.setPaginacionHecha(true);
		}
		
		PaginacionThreadHolder.set(pag);
		
		return listaResultado;
	}
	
	/**
	 * M&eacute;todo que es utilizado para obtener la configuraci&oacute;n de la paginaci&oacute;n
	 * donde se obtiene el n&uacute;mero de registros almacenados en el Hilo del paginador
	 * regresa la cadena parseada para ser interpretada por el JSP. 
	 * 
	 * @return
	 */
	public static String obtenerPaginacionAutomatica(){
		StringBuffer resultado = new StringBuffer();
		final PaginacionDTO pag = PaginacionThreadHolder.get();
	    if (pag != null && pag.getTotalRegistros() != null && !pag.getTotalRegistros().equals(0L)) {
	    	resultado.append("<page>" + pag.getPage() + "</page>\n");
	        resultado.append("<total>" + pag.getTotalPaginas() + "</total>\n");
	        resultado.append("<records>" + pag.getTotalRegistros() + "</records>\n");
	    } else {
	    	resultado.append("<page>1</page>\n");
	        resultado.append("<total>1</total>\n");
	        resultado.append("<records>0</records>\n");
	    }
	    return resultado.toString();
	}
	
	/**
	 * 
	 * @param pag  es el paginador de la consulta
	 * @param tabla  es la tabla del campo a buscar
	 * @param esPrimeraClausula  
	 * @param esFecha convierte a fecha
	 * @return clausula de busqueda
	 */
	
	public static String obtenerBusquedaPaginacionEnDAO(PaginacionDTO pag, String tabla, Boolean esPrimeraClausula, Boolean esFecha){
		
		StringBuffer queryStr = new StringBuffer();
		if( pag.getSearchOper()!=null && pag.getSearchOper().equals("nu")){
			pag.setSearchString("a");
		}
		if(pag != null && pag.getSearchField() != null && 
        		pag.getSearchOper() != null && pag.getSearchString() != null &&
        		!pag.getSearchField().isEmpty() && !pag.getSearchOper().isEmpty() && !pag.getSearchString().isEmpty()){
			if( pag.getSearchOper()!=null && pag.getSearchOper().equals("nu")){
				pag.setSearchString("");
			}
			
			if(esPrimeraClausula){
				queryStr.append(" WHERE ");
	        }else{
	        	queryStr.append(" AND (");
	        }
			
			if(esFecha){
				queryStr.append("CONVERT (nvarchar, ");
        	}
        	
        	queryStr.append(tabla).append(".");
        	queryStr.append(pag.getSearchField());
        	
        	if(esFecha){
				queryStr.append(" , 112) ");
        	}
        	
        	queryStr = definicionOperandos(queryStr, pag);
        	        	
        	//se usa para valores booleanos, si desde vista vienen como si o no
        	if(pag.getSearchString().equalsIgnoreCase("si") ){
    			pag.setSearchString("1");
    		}else if (pag.getSearchString().equalsIgnoreCase("no") ){
    			pag.setSearchString("0");
    		}
        	
        	if(esFecha){
        		try {
					pag.setSearchString( DateUtils.formatearBD(DateUtils.obtener(pag.getSearchString())));
				} catch (Exception e) {
					e.printStackTrace();
				}
        	}
        	
        	queryStr = asignacionOperandos(queryStr, pag);
        			
        	if(pag.getSearchField().equals("nombreFuncionario")){
        		queryStr.append(" OR ").append(tabla).append(".apellidoPaternoFuncionario");
        		queryStr = definicionOperandos(queryStr, pag);
        		queryStr = asignacionOperandos(queryStr, pag);
        		queryStr.append(" OR ").append(tabla).append(".apellidoMaternoFuncionario");
        		queryStr = definicionOperandos(queryStr, pag);
        		queryStr = asignacionOperandos(queryStr, pag);
        	}
        	
        	queryStr.append(")");
        }
		
		return queryStr.toString();
		
	}
	
	
	private static StringBuffer definicionOperandos(StringBuffer queryStr, PaginacionDTO pag){
		if (pag.getSearchOper().equals("eq")){//equal
    		queryStr.append(" = ");
    	}else if (pag.getSearchOper().equals("ne")){//not equal
    		queryStr.append(" <> ");
    	}else if (pag.getSearchOper().equals("lt")){//less than
    		queryStr.append(" < ");
    	}else if (pag.getSearchOper().equals("le")){//less than or equal
    		queryStr.append(" <= ");
    	}else if (pag.getSearchOper().equals("gt")){//greater than
    		queryStr.append(" > ");
    	}else if (pag.getSearchOper().equals("ge")){//greater than or equal
    		queryStr.append(" >= ");
    	}else if (pag.getSearchOper().equals("bw")){//begins with
    		queryStr.append(" LIKE ");
    	}else if (pag.getSearchOper().equals("bn")){//doesn't begin with
    		queryStr.append(" NOT LIKE ");
    	}else if (pag.getSearchOper().equals("in")){//is in
    		queryStr.append(" LIKE ");
    	}else if (pag.getSearchOper().equals("ni")){//is not in
    		queryStr.append(" NOT LIKE ");
    	}else if (pag.getSearchOper().equals("ew")){//ends with
    		queryStr.append(" LIKE ");
    	}else if (pag.getSearchOper().equals("en")){//doesn't end with
    		queryStr.append(" NOT LIKE ");
    	}else if (pag.getSearchOper().equals("cn")){// contains
    		queryStr.append(" LIKE ");
    	}else if (pag.getSearchOper().equals("nc")){//doesn't contain
    		queryStr.append(" NOT LIKE ");
    	}else if (pag.getSearchOper().equals("nu")){//is null
    		queryStr.append(" IS NULL ");
    	}
		
		return queryStr;
	}
	
	private static StringBuffer asignacionOperandos(StringBuffer queryStr, PaginacionDTO pag){
		if( pag.getSearchOper().equals("bw") || pag.getSearchOper().equals("bn") ){
    		queryStr.append("'").append(pag.getSearchString()).append("%'");
    	}else if( pag.getSearchOper().equals("ew") || pag.getSearchOper().equals("en") ){
    		queryStr.append("'%").append(pag.getSearchString()).append("'");
    	}else if( pag.getSearchOper().equals("cn") || pag.getSearchOper().equals("nc") || pag.getSearchOper().equals("in") 
    			|| pag.getSearchOper().equals("ni")){
    		queryStr.append("'%").append(pag.getSearchString()).append("%'");
    	}else if( pag.getSearchOper().equals("eq") ){
    		queryStr.append("'").append(pag.getSearchString()).append("'");
    	}else{
    		queryStr.append(pag.getSearchString());
    	}
    	
		return queryStr;
	}
}
