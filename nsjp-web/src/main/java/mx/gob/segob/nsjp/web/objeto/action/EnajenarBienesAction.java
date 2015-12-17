/**
 * Nombre del Programa 		: ConsultarBienesPorEnajenarAction.java
 * Autor                     : Adriana S·nchez
 * Compania                  : Draconiantech
 * Proyecto                  : NSJP                    Fecha: 19 Oct 2015
 * Marca de cambio        	: N/A
 * Descripcion General    	: Clase Action para consultar bienes por enajenar
 * Programa Dependiente  	: N/A
 * Programa Subsecuente 		: N/A
 * Cond. de ejecucion        : N/A
 * Dias de ejecucion         : N/A                             Horario: N/A
 *                              MODIFICACIONES
 *------------------------------------------------------------------------------
 * Autor                     : N/A
 * Compania              	: N/A
 * Proyecto                 	: N/A                                 Fecha: N/A
 * Modificacion           	: N/A
 *------------------------------------------------------------------------------
 */
package mx.gob.segob.nsjp.web.objeto.action;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;
import javax.servlet.ServletOutputStream;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import static mx.gob.segob.nsjp.comun.constants.ConstantesGenerales.CONTENT_TYPE_XLS;
import static mx.gob.segob.nsjp.comun.constants.ConstantesGenerales.ENCABEZADO_ATTACH_FILE_NAME;
import static mx.gob.segob.nsjp.comun.constants.ConstantesGenerales.ENCABEZADO_CACHE_CONTROL;
import static mx.gob.segob.nsjp.comun.constants.ConstantesGenerales.ENCABEZADO_CONTENT_DISPOSITION;
import static mx.gob.segob.nsjp.comun.constants.ConstantesGenerales.ENCABEZADO_NOCACHE;
import static mx.gob.segob.nsjp.comun.constants.ConstantesGenerales.ENCABEZADO_PRAGMA;
import static mx.gob.segob.nsjp.comun.constants.ConstantesGenerales.EXTENSION_XLS;
import mx.gob.segob.nsjp.comun.enums.configuracion.Parametros;
import mx.gob.segob.nsjp.comun.enums.objeto.Objetos;

import mx.gob.segob.nsjp.comun.util.DateUtils;
import mx.gob.segob.nsjp.delegate.documento.DocumentoDelegate;
import mx.gob.segob.nsjp.delegate.fecha.ObtenerFechaActualDelegate;
import mx.gob.segob.nsjp.delegate.objeto.ObjetoDelegate;
import mx.gob.segob.nsjp.delegate.parametro.ParametroDelegate;
import mx.gob.segob.nsjp.dto.catalogo.CatDelitoDTO;
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
import mx.gob.segob.nsjp.dto.objeto.ObraArteDTO;
import mx.gob.segob.nsjp.dto.objeto.SustanciaDTO;
import mx.gob.segob.nsjp.dto.objeto.TelefoniaDTO;
import mx.gob.segob.nsjp.dto.objeto.VegetalDTO;
import mx.gob.segob.nsjp.dto.objeto.VehiculoDTO;
import mx.gob.segob.nsjp.web.base.action.GenericAction;
import net.sf.jasperreports.engine.JRException;

import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Clase Action para ingresar pertenencias
 * 
 * @version 1.0
 * @author Cuauhtemoc Paredes
 * 
 */
public class EnajenarBienesAction extends GenericAction {

	/* Log de clase */
	private static final Logger log = Logger
			.getLogger(EnajenarBienesAction.class);

	@Autowired
	private ObjetoDelegate objetoDelegate;
	@Autowired
	private ParametroDelegate parametroDelegate; 
	@Autowired
	public DocumentoDelegate documentoDelegate;
        @Autowired
	public ObtenerFechaActualDelegate obFechaDelegate;
        
	/**
	 * MÈtodo utilizado para realizar la consulta de bienes por enajenar
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 */
	public ActionForward consultarBienesPorEnajenar(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		try {
	
			log.info("ejecutando Action consultar bienes por enajenar");
			SimpleDateFormat formato= new SimpleDateFormat("dd/MM/yyyy");
			String fecha = request.getParameter("fechaEnajenar");
			Integer diasParaEnajenar=new Integer(parametroDelegate.consultarParametro(Parametros.DIAS_PARA_ENAJENAR).getValor());
			List<ObjetoDTO> objetoDTOs = objetoDelegate.consultarBienesPorEnajenar(DateUtils.obtener(fecha),diasParaEnajenar);
						
			response.setContentType("text/xml; charset=UTF-8");
			response.setHeader("Cache-Control", "no-cache");
                       
			PrintWriter writer = response.getWriter();
			
			writer.print("<rows>");
		
			writer.print("<records>" + objetoDTOs.size() + "</records>");
	
			for (ObjetoDTO objetoDTO : objetoDTOs) {
				
				writer.print("<row id='" + objetoDTO.getElementoId()+ "'>");				
                                writer.print("<cell>" + objetoDTO.getTipoObjeto().getNombreEntity()+ "</cell>");
				writer.print("<cell>" + objetoDTO.getDescripcion()+ "</cell>");
                                if(objetoDTO.getTipoObjeto()==Objetos.DOCUMENTO_OFICIAL){
                                    writer.print("<cell>" + ((DocumentoOficialDTO)objetoDTO).getCantidad()+ "</cell>");	
                                }else if(objetoDTO.getTipoObjeto()==Objetos.EXPLOSIVO){
                                    writer.print("<cell>" + ((ExplosivoDTO)objetoDTO).getCantidad()+ "</cell>");	
                                }else if(objetoDTO.getTipoObjeto()==Objetos.JOYA){
                                    writer.print("<cell>" + ((JoyaDTO)objetoDTO).getCantidad()+ "</cell>");	
                                }else if(objetoDTO.getTipoObjeto()==Objetos.NUMERARIO){
                                    writer.print("<cell>" + ((NumerarioDTO)objetoDTO).getCantidad()+ "</cell>");	
                                }else if(objetoDTO.getTipoObjeto()==Objetos.OBRA_DE_ARTE){
                                    writer.print("<cell>" + ((ObraArteDTO)objetoDTO).getCantidad()+ "</cell>");	
                                }else if(objetoDTO.getTipoObjeto()==Objetos.SUSTANCIA){
                                    writer.print("<cell>" + ((SustanciaDTO)objetoDTO).getCantidad()+ "</cell>");	
                                }else if(objetoDTO.getTipoObjeto()==Objetos.EQUIPO_TELEFONICO){
                                    writer.print("<cell>" + ((TelefoniaDTO)objetoDTO).getCantidad()+ "</cell>");	
                                }else if(objetoDTO.getTipoObjeto()==Objetos.VEGETAL){
                                    writer.print("<cell>" + ((VegetalDTO)objetoDTO).getCantidad()+ "</cell>");	
                                }else{
                                    writer.print("<cell>-</cell>");	
                                }                                
				writer.print("<cell>" + objetoDTO.getExpedienteDTO().getNumeroExpediente() + "</cell>");
                                Calendar c=Calendar.getInstance();
                                c.setTime(objetoDTO.getFechaCreacionElemento());
                                DateUtils.sumarDias(c, diasParaEnajenar);
                                writer.print("<cell>" + formato.format(c.getTime())+ "</cell>");
                                writer.print("<cell>" + formato.format(objetoDTO.getFechaCreacionElemento())+ "</cell>");                                    
				writer.print("</row>");
			}			
			
			writer.print("</rows>");
			writer.flush();
			writer.close();
			
			
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return null;
	}
        
        public ActionForward enajenarBienes(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		try {
                        String fecha = request.getParameter("fechaEnajenar");
			log.info("ejecutando Action enajenar");
			
			String ids = request.getParameter("idsBienes");
                        objetoDelegate.enajenarBienes(ids);                       		    			    	
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return null;
	}
        
        
        public ActionForward generarReporte(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response)
            throws IOException {

        try {
            String ids = request.getParameter("idsBienes");
            ByteArrayOutputStream baos = this.generarReporteExcel(ids);                      
            this.escribirReporteExcel(response, baos,String.valueOf("Reporte Bienes Enajenados"));
            
        } catch (JRException e) {
        	 log.error(e.getMessage(), e);
		}
        return null;
    }

        
        private ByteArrayOutputStream generarReporteExcel(String ids) throws JRException {
    	ByteArrayOutputStream baos = null;
        try {            
            int renglones = 0;

            Workbook wb = new HSSFWorkbook();
            Map<String, CellStyle> styles = createStyles(wb);

            //BIENES MUEBLES
            Sheet sheet = wb.createSheet("MUEBLES");        
            sheet.setFitToPage(true);
            sheet.setHorizontallyCenter(true);    	                
        
            Row ubiRow = sheet.createRow(renglones++);
            ubiRow.setHeightInPoints(26);
            Cell ubiCell=ubiRow.createCell(18);
            ubiCell.setCellValue("DATOS DE UBICACION");
            ubiCell.setCellStyle(styles.get("gris"));
            sheet.addMergedRegion(CellRangeAddress.valueOf("$S$1:$T$1"));

            Row titleRow = sheet.createRow(renglones++);
            titleRow.setHeightInPoints(26);
            String[]encabMuebles={"Nº","A.C.","A.P.P.","FECHA DE ASEGURAMIENTO","TIPO DE OBJETO","DESCRIPCION","CANTIDAD","MARCA","MODELO",
                "SERIE","COLOR","CAPACIDAD","CONDICIONES FISICAS","DELITO","EJERCIO ACCION PENAL SI/NO","ESTADO ACTUAL OBJETO EN EL PROCESO","UBICACIÓN","FECHA","OFICIO"};
            Integer[]anchoMuebles={6,9,9,14,14,13,8,15,15,15,15,15,15,15,15,19,18,15,15};
            for (int i = 0; i < encabMuebles.length; i++) {            
                sheet.setColumnWidth(i+1, anchoMuebles[i]*256);
                Cell titleCell = titleRow.createCell(i+1);            
                titleCell.setCellValue(encabMuebles[i]);
                titleCell.setCellStyle(styles.get("titulo"));
            }

            //BIENES INMUEBLES
            renglones=0;
            sheet = wb.createSheet("INMUEBLES");        
            sheet.setFitToPage(true);
            sheet.setHorizontallyCenter(true);   

            ubiRow = sheet.createRow(renglones++);
            ubiRow.setHeightInPoints(26);
            ubiCell=ubiRow.createCell(13);
            ubiCell.setCellValue("DATOS DE UBICACION");
            ubiCell.setCellStyle(styles.get("gris"));
            sheet.addMergedRegion(CellRangeAddress.valueOf("$N$1:$O$1"));

            titleRow = sheet.createRow(renglones++);
            titleRow.setHeightInPoints(26);
            String[]encabInmuebles={"Nº","A.C.","A.P.P.","FECHA DE ASEGURAMIENTO","TIPO INMUEBLE","UBICACION","MUNICIPIO","SUPERFICIE","DESCRIPCION FISICA",
                "DELITO","EJERCIO ACCION PENAL SI/NO","ESTADO ACTUAL DEL OBJETO EN EL PROCESO","FECHA","OFICIO"};
            Integer[]anchoInmuebles={6,9,9,14,16,37,28,28,37,24,20,22,16,16};
            for (int i = 0; i < encabInmuebles.length; i++) {            
                sheet.setColumnWidth(i+1, anchoInmuebles[i]*256);
                Cell titleCell = titleRow.createCell(i+1);            
                titleCell.setCellValue(encabInmuebles[i]);
                titleCell.setCellStyle(styles.get("titulo"));
            }
            
            //SEMOVIENTES
            renglones=0;
            sheet = wb.createSheet("SEMOVIENTES");        
            sheet.setFitToPage(true);
            sheet.setHorizontallyCenter(true);   

            ubiRow = sheet.createRow(renglones++);
            ubiRow.setHeightInPoints(26);
            ubiCell=ubiRow.createCell(14);
            ubiCell.setCellValue("DATOS DE UBICACION");
            ubiCell.setCellStyle(styles.get("gris"));
            sheet.addMergedRegion(CellRangeAddress.valueOf("$O$1:$P$1"));

            titleRow = sheet.createRow(renglones++);
            titleRow.setHeightInPoints(26);
            String[]encabSemov={"Nº","A.C.","A.P.P.","FECHA DE ASEGURAMIENTO","TIPO DE SEMOVIENTE","DESCRIPCION","CANTIDAD","COLOR","CONDICIONES FISICAS",
                "DELITO","EJERCIO ACCION PENAL SI/NO","ESTADO ACTUAL DEL OBJETO EN EL PROCESO","UBICACION","FECHA","OFICIO"};
            Integer[]anchoSemov={6,10,10,14,24,38,16,16,28,20,16,20,18,18,18};
            for (int i = 0; i < encabSemov.length; i++) {            
                sheet.setColumnWidth(i+1, anchoSemov[i]*256);
                Cell titleCell = titleRow.createCell(i+1);            
                titleCell.setCellValue(encabSemov[i]);
                titleCell.setCellStyle(styles.get("titulo"));
            }
            
            //VALORES
            renglones=0;
            sheet = wb.createSheet("VALORES");        
            sheet.setFitToPage(true);
            sheet.setHorizontallyCenter(true);   

            ubiRow = sheet.createRow(renglones++);
            ubiRow.setHeightInPoints(26);
            ubiCell=ubiRow.createCell(9);
            ubiCell.setCellValue("SECCION DINERO");
            ubiCell.setCellStyle(styles.get("gris"));
            sheet.addMergedRegion(CellRangeAddress.valueOf("$J$1:$M$1"));
            
            ubiCell=ubiRow.createCell(13);
            ubiCell.setCellValue("SECCION JOYAS");
            ubiCell.setCellStyle(styles.get("azul"));
            sheet.addMergedRegion(CellRangeAddress.valueOf("$N$1:$R$1"));
            
            ubiCell=ubiRow.createCell(18);
            ubiCell.setCellValue("SECCION OBRAS DE ARTE");
            ubiCell.setCellStyle(styles.get("verde"));
            sheet.addMergedRegion(CellRangeAddress.valueOf("$S$1:$U$1"));

            ubiCell=ubiRow.createCell(21);
            ubiCell.setCellValue("DATOS DE UBICACION");
            ubiCell.setCellStyle(styles.get("grisObs"));
            sheet.addMergedRegion(CellRangeAddress.valueOf("$V$1:$X$1"));

            titleRow = sheet.createRow(renglones++);
            titleRow.setHeightInPoints(26);
            String[]encabVals={"Nº","A.C.","A.P.P.","FECHA DE ASEGURAMIENTO","TIPO DE VALOR","DELITO","EJERCIO ACCION PENAL SI/NO","ESTADO ACTUAL DEL OBJETO EN EL PROCESO",
                "CANTIDAD TOTAL","TIPO","DENOMINACION","PROCEDENCIA","CANTIDAD","TIPO DE JOYA","MATERIAL","KILATAJE","AVALUO MONETARIO",
                "CANTIDAD","TIPO DE OBRA","DESCRIPCION FISICA","UBICACION FISICA / CIUDAD","FECHA","OFICIO"};
            Integer[]anchoVals={5,8,8,14,12,15,16,22,10,14,14,14,8,14,10,10,12,8,11,22,16,12,12};
            for (int i = 0; i < encabVals.length; i++) {            
                sheet.setColumnWidth(i+1, anchoVals[i]*256);
                Cell titleCell = titleRow.createCell(i+1);            
                titleCell.setCellValue(encabVals[i]);
                titleCell.setCellStyle(styles.get("titulo"));
            }
            
            //VEHICULOS
            renglones=0;
            sheet = wb.createSheet("VEHICULOS");        
            sheet.setFitToPage(true);
            sheet.setHorizontallyCenter(true);   

            ubiRow = sheet.createRow(renglones++);
            ubiRow.setHeightInPoints(26);
            ubiCell=ubiRow.createCell(16);
            ubiCell.setCellValue("DATOS DE UBICACION");
            ubiCell.setCellStyle(styles.get("gris"));
            sheet.addMergedRegion(CellRangeAddress.valueOf("$Q$1:$T$1"));

            titleRow = sheet.createRow(renglones++);
            titleRow.setHeightInPoints(26);
            String[]encabVehiculos={"Nº","A.C.","A.P.P.","FECHA DE ASEGURAMIENTO","MARCA","TIPO","MODELO","N° DE SERIE","COLOR","PROCEDENCIA",
                "CONDICIONES FISICAS","DELITO","EJERCIO ACCION PENAL SI/NO","ESTADO ACTUAL DEL OBJETO EN EL PROCESO","UBICACION","FECHA",
                "N° DE OFICIO","PERSONA A QUIEN SE ENTREGO","PERSONA QUE AUTORIZO"};
            Integer[]anchoVehiculos={6,8,8,16,14,14,8,13,12,12,12,12,14,20,10,10,10,18,19};
            for (int i = 0; i < encabVehiculos.length; i++) {            
                sheet.setColumnWidth(i+1, anchoVehiculos[i]*256);
                Cell titleCell = titleRow.createCell(i+1);            
                titleCell.setCellValue(encabVehiculos[i]);
                titleCell.setCellStyle(styles.get("titulo"));
            }
            
            int noInm=2,noVeh=2,noSemov=2,noVals=2;
            StringTokenizer st=new StringTokenizer(ids, ",");
            Sheet hojaActual=null;
            Row renActual=null;
            Cell celActual=null;
            while(st.hasMoreTokens()){
                ObjetoDTO oDTO=new ObjetoDTO(new Long(st.nextToken()));
                oDTO.setConsultarArchivoDigital(Boolean.TRUE);
                ObjetoDTO obDTO=objetoDelegate.obtenerObjeto(oDTO);
                String valores[]=preparaObjeto(obDTO);
                if(obDTO.getIdTipoObjeto() == Objetos.EQUIPO_DE_COMPUTO.getValorId()
                                    || obDTO.getIdTipoObjeto() == Objetos.EQUIPO_TELEFONICO.getValorId()
                                    || obDTO.getIdTipoObjeto() == Objetos.EXPLOSIVO.getValorId()
                                    || obDTO.getIdTipoObjeto() == Objetos.ARMA.getValorId()
                                    || obDTO.getIdTipoObjeto() == Objetos.SUSTANCIA.getValorId()
                                    || obDTO.getIdTipoObjeto() == Objetos.DOCUMENTO_OFICIAL.getValorId()){
                    hojaActual=wb.getSheet("INMUEBLES");
                    renActual = hojaActual.createRow(noInm++);
                    renActual.setHeightInPoints(26);       
                    celActual = renActual.createCell(1); celActual.setCellValue(noInm-2); celActual.setCellStyle(styles.get("normal"));
                }else if(obDTO.getIdTipoObjeto() == Objetos.VEHICULO.getValorId() 
                        || obDTO.getIdTipoObjeto() == Objetos.EMBARCACION.getValorId()
                        || obDTO.getIdTipoObjeto() == Objetos.AERONAVE.getValorId()){
                    hojaActual=wb.getSheet("VEHICULOS");
                    renActual = hojaActual.createRow(noVeh++);
                    renActual.setHeightInPoints(26);
                    celActual = renActual.createCell(1); celActual.setCellValue(noVeh-2); celActual.setCellStyle(styles.get("normal"));
                }else if(obDTO.getIdTipoObjeto() == Objetos.ANIMAL.getValorId()
                        ||obDTO.getIdTipoObjeto() == Objetos.VEGETAL.getValorId()){
                    hojaActual=wb.getSheet("SEMOVIENTES");
                    renActual = hojaActual.createRow(noSemov++);
                    renActual.setHeightInPoints(26);
                    celActual = renActual.createCell(1); celActual.setCellValue(noSemov-2); celActual.setCellStyle(styles.get("normal"));                    
                }else if(obDTO.getIdTipoObjeto() == Objetos.JOYA.getValorId()
                        || obDTO.getIdTipoObjeto() == Objetos.OBRA_DE_ARTE.getValorId()
                        || obDTO.getIdTipoObjeto() == Objetos.NUMERARIO.getValorId()){
                    hojaActual=wb.getSheet("VALORES");
                    renActual = hojaActual.createRow(noVals++);
                    renActual.setHeightInPoints(26);
                    celActual = renActual.createCell(1); celActual.setCellValue(noVals-2); celActual.setCellStyle(styles.get("normal"));
                }
                
                for(int j=0;j<valores.length;j++){                  
                    celActual = renActual.createCell(j+2); celActual.setCellValue(valores[j]); celActual.setCellStyle(styles.get("normal")); 
                }    
            }                
        
      
		baos = new ByteArrayOutputStream();
		wb.write(baos);
		log.info("Escritura Exitosa!!");
	} catch (Exception e) {
            log.info(e.getMessage(), e);
	}
    	return baos;
    }
        
        private static Map<String, CellStyle> createStyles(Workbook wb){
        Map<String, CellStyle> styles = new HashMap<String, CellStyle>();
        CellStyle style;
        short borderColor = IndexedColors.GREY_50_PERCENT.getIndex();
        
        //Titulo    
        Font titleFont = wb.createFont();
        titleFont.setFontName("Century Gothic");
        titleFont.setFontHeightInPoints((short)8);
        titleFont.setBoldweight(Font.BOLDWEIGHT_BOLD);
        style = wb.createCellStyle();
        style.setAlignment(CellStyle.ALIGN_CENTER);
        style.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
        style.setWrapText(true);
        style.setFont(titleFont);
        style.setBorderBottom(HSSFCellStyle.BORDER_THIN);
        style.setBorderTop(HSSFCellStyle.BORDER_THIN);
        style.setBorderLeft(HSSFCellStyle.BORDER_THIN);
        style.setBorderRight(HSSFCellStyle.BORDER_THIN);
        styles.put("titulo", style);
        
        Font grisFont = wb.createFont();
        grisFont.setFontName("Century Gothic");
        grisFont.setFontHeightInPoints((short)9);
        grisFont.setBoldweight(Font.BOLDWEIGHT_BOLD);
        style = wb.createCellStyle();
        style.setAlignment(CellStyle.ALIGN_CENTER);
        style.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
        style.setFont(grisFont);
        style.setFillForegroundColor(HSSFColor.GREY_25_PERCENT.index);
        style.setFillPattern(CellStyle.SOLID_FOREGROUND);
        styles.put("gris", style);
        
        style = wb.createCellStyle();
        style.setAlignment(CellStyle.ALIGN_CENTER);
        style.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
        style.setFont(grisFont);
        style.setFillForegroundColor(HSSFColor.GREY_40_PERCENT.index);
        style.setFillPattern(CellStyle.SOLID_FOREGROUND);
        styles.put("grisObs", style);
        
        style = wb.createCellStyle();
        style.setAlignment(CellStyle.ALIGN_CENTER);
        style.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
        style.setFont(grisFont);
        style.setFillForegroundColor(HSSFColor.PALE_BLUE.index);
        style.setFillPattern(CellStyle.SOLID_FOREGROUND);
        styles.put("azul", style);
        
        style = wb.createCellStyle();
        style.setAlignment(CellStyle.ALIGN_CENTER);
        style.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
        style.setFont(grisFont);
        style.setFillForegroundColor(HSSFColor.LIGHT_GREEN.index);
        style.setFillPattern(CellStyle.SOLID_FOREGROUND);
        styles.put("verde", style);

        Font normalFont = wb.createFont();
        normalFont.setFontName("Century Gothic");
        normalFont.setFontHeightInPoints((short)8);
        style = wb.createCellStyle();
        style.setAlignment(CellStyle.ALIGN_CENTER);
        style.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
        style.setWrapText(true);
        style.setFont(normalFont);
        style.setBorderBottom(HSSFCellStyle.BORDER_THIN);
        style.setBorderTop(HSSFCellStyle.BORDER_THIN);
        style.setBorderLeft(HSSFCellStyle.BORDER_THIN);
        style.setBorderRight(HSSFCellStyle.BORDER_THIN);
        styles.put("normal", style);
        return styles;
    }

        private void escribirReporteExcel(HttpServletResponse response,
            ByteArrayOutputStream baos, String fileName) {
        try {
        	
        	String fileNameWihtoutSpaces = fileName.replace(' ', '_');
            response.setContentType(CONTENT_TYPE_XLS);
            response.setHeader(ENCABEZADO_CONTENT_DISPOSITION,
            		ENCABEZADO_ATTACH_FILE_NAME
                    + fileNameWihtoutSpaces + EXTENSION_XLS);
            response.setHeader(ENCABEZADO_CACHE_CONTROL, 
            		ENCABEZADO_NOCACHE);
            response.setHeader(ENCABEZADO_PRAGMA,
            		ENCABEZADO_NOCACHE);
            response.setContentLength(baos.size());
            ServletOutputStream sos = response.getOutputStream();
            baos.writeTo(sos);
            sos.flush();
        } catch (IOException e) {
            log.error(e.getMessage(), e);
        }
    }
        
    private String[] preparaObjeto(ObjetoDTO obDTO){
        String celdas[]=new String[22];
        String descripcion=obDTO.getDescripcion()!=null?obDTO.getDescripcion():"NA";
        String ubicacion=obDTO.getAlmacen()!=null?obDTO.getAlmacen().getNombreAlmacen():"NA";
        String expediente=obDTO.getExpedienteDTO()!=null?obDTO.getExpedienteDTO().getNumeroExpediente():"NA";
        CatDelitoDTO delitoDTO=obDTO.getExpedienteDTO().getDelitoPrincipal()!=null?obDTO.getExpedienteDTO().getDelitoPrincipal().getCatDelitoDTO():null;
        String fecha=obDTO.getFechaRecepcion()!=null?obDTO.getFechaRecepcion().toString():"NA";
        String delito="NA",ejercioAP="NA";
        if(delitoDTO!=null){
            delito=delitoDTO.getNombre();
            if(delitoDTO.getEsAccionPenPriv()!=null)
                ejercioAP=delitoDTO.getEsAccionPenPriv()?"SI":"NO";            
            else
                ejercioAP="NA";
        }
        String ac="NA";
        String app="NA";
        String tipo=obDTO.getTipoObjeto().getNombreEntity();
        if(obDTO.getIdTipoObjeto()==Objetos.VEHICULO.getValorId()){
            VehiculoDTO veh=(VehiculoDTO)obDTO;
            celdas[0]=ac;celdas[1]=app;
            celdas[2]=veh.getFechaRecuperado()!=null?veh.getFechaRecuperado().toString():"NA";
            celdas[3]=veh.getValorByMarcaVal()!=null?veh.getValorByMarcaVal().getValor():"NA";            
            celdas[4]=veh.getValorByTipoVehiculo()!=null?veh.getValorByTipoVehiculo().getValor():tipo;
            celdas[5]=veh.getModelo().toString();celdas[6]=veh.getNoSerie();
            celdas[7]=veh.getValorByColorVal()!=null?veh.getValorByColorVal().getValor():"NA";
            celdas[8]="NA";celdas[9]="NA";celdas[10]=delito;celdas[11]=ejercioAP;
            celdas[12]="NA";celdas[13]=ubicacion;celdas[14]=fecha;celdas[15]=expediente;
            celdas[16]="NA";celdas[17]="NA";
        }else if(obDTO.getIdTipoObjeto()==Objetos.EMBARCACION.getValorId()){
            EmbarcacionDTO emb=(EmbarcacionDTO)obDTO;
            celdas[0]=ac;celdas[1]=app;celdas[2]="NA";
            celdas[3]=emb.getMarca()!=null?emb.getMarca().getValor():"NA";            
            celdas[4]=emb.getTipoEmbarcacion()!=null?emb.getTipoEmbarcacion().getValor():tipo;
            celdas[5]="NA";
            celdas[7]=emb.getColor()!=null?emb.getColor().getValor():"NA";
            celdas[8]="NA";celdas[9]="NA";celdas[10]=delito;celdas[11]=ejercioAP;
            celdas[12]="NA";celdas[13]=ubicacion;celdas[14]=fecha;celdas[15]=expediente;
            celdas[16]="NA";celdas[17]="NA";
        }else if(obDTO.getIdTipoObjeto()==Objetos.AERONAVE.getValorId()){
            AeronaveDTO aern=(AeronaveDTO)obDTO;
            celdas[0]=ac;celdas[1]=app;celdas[2]="NA";
            celdas[3]=aern.getMarca()!=null?aern.getMarca().getValor():"NA";            
            celdas[4]=aern.getTipoAeroNave()!=null?aern.getTipoAeroNave().getValor():tipo;
            celdas[5]="NA";
            celdas[7]=aern.getColor()!=null?aern.getColor().getValor():"NA";
            celdas[8]="NA";celdas[9]="NA";celdas[10]=delito;celdas[11]=ejercioAP;
            celdas[12]="NA";celdas[13]=ubicacion;celdas[14]=fecha;celdas[15]=expediente;
            celdas[16]="NA";celdas[17]="NA";
        }else if(obDTO.getIdTipoObjeto()==Objetos.EQUIPO_TELEFONICO.getValorId()){
            //EquipoTelefonicoDTO emb=(EquipoTelefonicoDTO)obDTO;
        }else if(obDTO.getIdTipoObjeto() == Objetos.EQUIPO_DE_COMPUTO.getValorId()){
            EquipoComputoDTO eqCom=(EquipoComputoDTO)obDTO;
        }else if(obDTO.getIdTipoObjeto() == Objetos.EXPLOSIVO.getValorId()){
            ExplosivoDTO exp = (ExplosivoDTO)obDTO;
        }else if(obDTO.getIdTipoObjeto() == Objetos.ARMA.getValorId()){
            ArmaDTO arma = (ArmaDTO)obDTO; 
        }else if(obDTO.getIdTipoObjeto() == Objetos.SUSTANCIA.getValorId()){
            SustanciaDTO sustancia = (SustanciaDTO)obDTO;
        }else if(obDTO.getIdTipoObjeto() == Objetos.DOCUMENTO_OFICIAL.getValorId()){
            DocumentoOficialDTO doc=(DocumentoOficialDTO)obDTO;
        }else if(obDTO.getIdTipoObjeto() == Objetos.ANIMAL.getValorId()){
            AnimalDTO animal = (AnimalDTO)obDTO;
        }else if(obDTO.getIdTipoObjeto() == Objetos.VEGETAL.getValorId()){
            VegetalDTO vegetal = (VegetalDTO)obDTO;     
        }else if(obDTO.getIdTipoObjeto() == Objetos.JOYA.getValorId()){
            JoyaDTO joya = (JoyaDTO)obDTO;
            celdas[0]=ac;celdas[1]=app;
            celdas[2]="NA";celdas[3]=tipo;
            celdas[4]=delito;celdas[5]=ejercioAP;
            celdas[6]="NA";
            for(int i=7;i<11;i++)
                celdas[i]="-";            
            celdas[11]=joya.getCantidad()!=null?joya.getCantidad().toString():"NA";
            celdas[12]=joya.getTipoJoya()!=null?joya.getTipoJoya().getValor():"NA";
            celdas[13]=joya.getMaterial();celdas[14]="NA";celdas[15]="NA";
            for(int i=16;i<19;i++)
                celdas[i]="-";
            celdas[19]=ubicacion;celdas[20]=fecha;celdas[21]=expediente;
        }else if(obDTO.getIdTipoObjeto() == Objetos.OBRA_DE_ARTE.getValorId()){
            ObraArteDTO obra = (ObraArteDTO)obDTO;
            celdas[0]=ac;celdas[1]=app;
            celdas[2]="NA";celdas[3]=tipo;
            celdas[4]=delito;celdas[5]=ejercioAP;
            celdas[6]="NA";
            for(int i=7;i<16;i++)
                celdas[i]="-";
            celdas[16]=obra.getCantidad()!=null?obra.getCantidad().toString():"NA";
            celdas[17]=obra.getTipoObraArte()!=null?obra.getTipoObraArte().getValor():"NA";celdas[18]=obra.getDescripcion();
            celdas[19]=ubicacion;celdas[20]=fecha;celdas[21]=expediente;
        }else if(obDTO.getIdTipoObjeto() == Objetos.NUMERARIO.getValorId()){
            NumerarioDTO num = (NumerarioDTO)obDTO;
            celdas[0]=ac;celdas[1]=app;
            celdas[2]="NA";celdas[3]=tipo;
            celdas[4]=delito;celdas[5]=ejercioAP;
            celdas[6]="NA";celdas[7]=num.getCantidad()!=null?num.getCantidad().toString():"NA";
            celdas[8]="NA";celdas[9]=num.getMoneda();celdas[10]="NA";
            for(int i=11;i<19;i++)
                celdas[i]="-";
            celdas[19]=ubicacion;celdas[20]=fecha;celdas[21]=expediente;
        }
        return celdas;
    } 

}
