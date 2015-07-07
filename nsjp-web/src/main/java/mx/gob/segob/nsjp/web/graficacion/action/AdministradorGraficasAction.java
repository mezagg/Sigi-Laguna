/**
 * 
 */
package mx.gob.segob.nsjp.web.graficacion.action;

import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import mx.gob.segob.nsjp.comun.excepcion.NSJPNegocioException;
import mx.gob.segob.nsjp.comun.indicador.Indicadores;
import mx.gob.segob.nsjp.delegate.indicador.IndicadorDelegate;
import mx.gob.segob.nsjp.web.base.action.GenericAction;

import org.apache.commons.lang.math.NumberUtils;
import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.CategoryLabelPositions;
import org.jfree.chart.labels.ItemLabelAnchor;
import org.jfree.chart.labels.ItemLabelPosition;
import org.jfree.chart.labels.StandardCategoryItemLabelGenerator;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.chart.renderer.category.CategoryItemRenderer;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.ui.TextAnchor;
import org.springframework.beans.factory.annotation.Autowired;


/**
 * Action para administrar la graficas.
 * Una administracion del action es utilizada para obtener la grafica de 
 * los indicadores.
 * 
 * @version 1.0
 * @author GustavoBP
 *
 */
public class AdministradorGraficasAction extends GenericAction {
	public static final Logger LOGGER = Logger.getLogger(GraficaDenunciasVSTipoDelitoAction.class);
	//Leyenda para mostrar en la grafica cuando no se tienen datos
	private static final String SIN_DATOS = "Sin Datos a Mostrar";
	//Constante que multiplica el ancho de la grafica - Vertical
	private static final int CONSTANTE_WIDTH = 70;
	//Constante que multiplica el alto de la grafica - Vertical
	private static final int CONSTANTE_HEIGHT = 8;
	//Constante que se suma para el ancho y alto minimo - Vertical
	private static final int CONSTANTE_MINIMA = 200;
	//Valor maximo de Y
	private int maxValor = 0;
	//Cantidad de datos del resultado de la consulta del indicador.
	private int valoresResultadoSize = 0;
	private static final String ETIQUETA_AGENCIA = "Agencia";
	
	@Autowired
    private IndicadorDelegate indicadorDelegate;
	
	/**
	 * Action que hace los procedimientos necesarios para obtener los datos
	 * del indicador y obtener la grafica. 
	 *  
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 */
	public ActionForward graficaParaIndicador(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		try {
			response.setContentType("text/xml; charset=UTF-8");
			response.setHeader("Cache-Control", "no-cache");
			
			String fechaInicial = request.getParameter("tiempoI");
			String fechaFin = request.getParameter("tiempoF");
			String etiquetaAgencia = request.getParameter("etiquetaAgencia");
			if ( etiquetaAgencia==null || etiquetaAgencia.trim().equals("") ){
            	etiquetaAgencia = ETIQUETA_AGENCIA;
            }
			
			Long tipoIndicador = NumberUtils.toLong(
					request.getParameter("tipoGraficaIndicador"), 0L);
			if(tipoIndicador.equals(0L)){
				return null;
			}
			Indicadores indicador = Indicadores.getByValor(tipoIndicador);
			indicador.cambiarEtiquetaAgencia(etiquetaAgencia);
			Boolean mostrarSerie = (indicador.getIdClasificacion() == -1);
			
			LOGGER.info("Tiempo inicial::" + fechaInicial);
            LOGGER.info("Tiempo Final::" + fechaFin);
            LOGGER.info("Tipo Indicador::" + indicador);
            LOGGER.info("EtiquetaAgencia::" + etiquetaAgencia);
            
            
			DefaultCategoryDataset dataset = null;
			if (indicador.getIndicadorId().equals(
					Indicadores.INDICADOR_66.getIndicadorId())
					|| indicador.getIndicadorId().equals(
							Indicadores.INDICADOR_67.getIndicadorId())) {
				// Solo para estos indicadores se requiere de otro tipo de
				// algoritmo para obtener la informacion para la grafica 
				dataset = this.obtenerDatosIndicadorDatosCompartidos(indicador,
						fechaInicial, fechaFin, mostrarSerie);
			} else {
				// Manejo generico de datos de indicador
				dataset = this.obtenerDatosIndicador(indicador, fechaInicial,
						fechaFin, mostrarSerie);
			}
			
			//Generar la grafica del indicador
			JFreeChart chart = this.generaGraficaIndicador(indicador,
					mostrarSerie, dataset);
			
			OutputStream out = response.getOutputStream();
	        response.setContentType("image/png");
	        
	        //Calcula el tamanio de la grafica
	        int tamanio[] = this.calculaTamanioGrafica(indicador);

	        //Generar la grafica como imagen.
	        ChartUtilities.writeChartAsPNG(out, chart, tamanio[0], tamanio[1]);
//			ChartFrame frame = new ChartFrame("First", chart);
//			frame.pack();
//			frame.setVisible(true);							
		} catch (NSJPNegocioException e) {
			LOGGER.error(e);
		}			
		return null;
	}
	
	/**
	 * Metodo que permite obtener los datos del indicador seleccionado.
	 * De acuerdo a la configuración del indicador, se determina los valores 
	 * para:
	 * -Descripcion de los valores de X.
	 * -Clasificacion, si requiere la agrupacion de valores (determinado por el 
	 * 	parametro mostrarSerie)
	 * -Valor en el eje Y.
	 * 
	 * Además se calculan lo valors para:
	 * maxValor: obtiene el maximo valor encontrado para determinar el alto de la grafica.
	 * valoresResultadoSize: numero de datos obtenidos del resultado de la consulta del 
	 *  indicador, es utilizado para determinar el ancho de la grafica. 
	 * 
	 * @param indicador
	 * @param fechaInicial
	 * @param fechaFin
	 * @param mostrarSerie
	 * @return
	 * @throws NSJPNegocioException
	 */
	private DefaultCategoryDataset obtenerDatosIndicador(Indicadores indicador,
			String fechaInicial, String fechaFin, Boolean mostrarSerie) throws NSJPNegocioException {
		 //Obtener datos para la grafica
        HashMap<String, String> valores = new HashMap<String, String>();
		//Nombre de las Columnas
		List<String> nombreParametros = indicador.getParametros();
		valores.put(nombreParametros.get(0).toString(), fechaInicial);
		valores.put(nombreParametros.get(1).toString(), fechaFin);
		
		List<Object[]> valoresResultado = indicadorDelegate
					.consultarIndicador(indicador, valores);

		//Prepara datos para la grafica
		List<Integer> valoresComparar = new ArrayList<Integer>();
		DefaultCategoryDataset dataset = new DefaultCategoryDataset();
		if(!valoresResultado.isEmpty() && valoresResultado.size() > 1){
			Iterator<Object[]> iterador = valoresResultado.iterator();
			// Se descarta el nombre de las columnas
			iterador.next(); 
			
			while(iterador.hasNext()){
				Object[] objects = (Object[]) iterador.next();
				// Total de valores, Nombre de la agrupacion, Clasificacion
				// del eje X de acuerdo a los valores
				
				//Los valores a graficar se obtienen de la configuracion del
				//indicador en idValorNumericoY
				Integer valorIntY = new Integer(objects[indicador.getIdValorNumericoY()].toString());
				
				//idClasificacion - Relacionado a las Series
				String clasificacion ="";
				if(mostrarSerie){
					clasificacion = indicador.getTituloCategoriaEjeX(); 
				}
				else{
					clasificacion = objects[indicador.getIdClasificacion()]
							.toString();
				}
				
				//Valor descripcionX - idValorDescripcionX
				String descripcionX =  objects[indicador.getIdValorDescripcionX()].toString();
				
				dataset.addValue(
						valorIntY,
						clasificacion,  
						descripcionX); 
				valoresComparar.add(valorIntY);
			}
			
			//Se obtiene el valor maximo de los datos recuperados
			Collections.sort(valoresComparar);
			
        	this.maxValor = valoresComparar.get(valoresComparar.size()-1 ) + 1;
        	this.valoresResultadoSize = valoresResultado.size();
		}
		return dataset;
	}
	
	/**
	 * Los datos de esté tipo de graficas continene los datos en dos columnas
	 * de acuerdo al genero Mujer/Hombre.
	 * Esto es particular para un tipo de indicador.
	 * 
	 * @param indicador
	 * @param fechaInicial
	 * @param fechaFin
	 * @param mostrarSerie
	 * @return
	 * @throws NSJPNegocioException
	 */
	private DefaultCategoryDataset obtenerDatosIndicadorDatosCompartidos(Indicadores indicador,
			String fechaInicial, String fechaFin, Boolean mostrarSerie) throws NSJPNegocioException {
        HashMap<String, String> valores = new HashMap<String, String>();
		List<String> nombreParametros = indicador.getParametros();
		valores.put(nombreParametros.get(0).toString(), fechaInicial);
		valores.put(nombreParametros.get(1).toString(), fechaFin);
		
		List<Object[]> valoresResultado = indicadorDelegate
					.consultarIndicador(indicador, valores);

		//Prepara datos para la grafica
		List<Integer> valoresComparar = new ArrayList<Integer>();
		DefaultCategoryDataset dataset = new DefaultCategoryDataset();
		if(!valoresResultado.isEmpty() && valoresResultado.size() > 1){
			Iterator<Object[]> iterador = valoresResultado.iterator();
			// Se descarta el nombre de las columnas
			iterador.next(); 
			
			while(iterador.hasNext()){
				Object[] objects = (Object[]) iterador.next();
				
				//Los valores a graficar se obtienen de la configuracion del
				//indicador en idValorNumericoY - MUJER
				Integer valorIntY = new Integer(objects[2].toString());
				
				//Valor descripcionX - idValorDescripcionX  - DELITO 
				String descripcionX =  objects[indicador.getIdValorDescripcionX()].toString();
				
				//SERIE 
				String clasificacion = "Mujer";
				dataset.addValue(
						valorIntY,
						clasificacion,  
						descripcionX); 
				valoresComparar.add(valorIntY);
				
				//Los valores a graficar se obtienen de la configuracion del
				//indicador en idValorNumericoY - HOMBRE
				valorIntY = new Integer(objects[3].toString());
				
				//Valor descripcionX - idValorDescripcionX  - DELITO 
				descripcionX =  objects[indicador.getIdValorDescripcionX()].toString();
				
				//SERIE 
				clasificacion = "Hombre";
				dataset.addValue(
						valorIntY,
						clasificacion,  
						descripcionX); 
				valoresComparar.add(valorIntY);
			}
			//Se obtiene el valor maximo de los datos recuperados
			Collections.sort(valoresComparar);
			
        	this.maxValor = valoresComparar.get(valoresComparar.size()-1 ) + 1;
        	//Doble de datos
        	this.valoresResultadoSize = valoresResultado.size()*2;
		}
		return dataset;
	}
	
	/**
	 * Metodo utilizado para la generacion de la grafica, de acuerdo a los 
	 * valores recuperados en la consulta del indicador y que se encuentran 
	 * en el dataset.
	 * 
	 * La grafica actualmente es de Barras en 3D, y la orientacion es 
	 * Vertical.
	 * Los titulos del eje X, son mostrados en un angulo de 45°.
	 * El parametro de mostrarSerie, es utilizado, para aquellos indidcadores
	 * que no requieran agrupar o clasificar su informacion y solo tengas datos
	 * de X y Y, para la grafica.
	 *  
	 * @param indicador
	 * @param mostrarSerie
	 * @param dataset
	 * @return
	 */
	private JFreeChart generaGraficaIndicador(Indicadores indicador,
			Boolean mostrarSerie, DefaultCategoryDataset dataset) {
	
		//Titulo, Categoria Eje X, Categoria Eje Y, datos, orientacion
		JFreeChart chart = ChartFactory.createBarChart3D(
				indicador.getTitulo(), indicador.getTituloCategoriaEjeX(),
				indicador.getTituloCategoriaEjeY(),
				dataset, PlotOrientation.VERTICAL,!mostrarSerie, true, !mostrarSerie);
		
		//Cambia las leyendas del eje x 
		CategoryPlot plot = (CategoryPlot) chart.getPlot();
		plot.setNoDataMessage(SIN_DATOS);
		
		CategoryAxis xAxis = (CategoryAxis) plot.getDomainAxis();
		//Inclinar 45 grados las etiquetas del eje X
		xAxis.setCategoryLabelPositions(CategoryLabelPositions.UP_45); 
		
		//Para el Label en la parte superior
//		CategoryItemRenderer renderer = plot.getRenderer();
//		CategoryItemLabelGenerator generator = new StandardCategoryItemLabelGenerator(
//				"{2}", new DecimalFormat("###"));
//		renderer.setBaseItemLabelGenerator(generator);
//		renderer.setBaseItemLabelsVisible(true);
//		renderer.setBaseItemLabelGenerator( new StandardCategoryItemLabelGenerator());
//		renderer.setItemMargin(0.0);

		//Titulo vertical sobre cada barra Categoria
		BarRenderer localBarRenderer = (BarRenderer)plot.getRenderer();
	    localBarRenderer.setDrawBarOutline(false);
	    localBarRenderer.setItemMargin(0.1D);
	    localBarRenderer.setBaseItemLabelGenerator(new LabelGenerator());
	    localBarRenderer.setBaseItemLabelsVisible(true);
	    ItemLabelPosition localItemLabelPosition1 = new ItemLabelPosition(ItemLabelAnchor.INSIDE12, TextAnchor.CENTER_RIGHT, TextAnchor.CENTER_RIGHT, -1.570796326794897D);
	    localBarRenderer.setBasePositiveItemLabelPosition(localItemLabelPosition1);
	    ItemLabelPosition localItemLabelPosition2 = new ItemLabelPosition(ItemLabelAnchor.OUTSIDE12, TextAnchor.CENTER_LEFT, TextAnchor.CENTER_LEFT, -1.570796326794897D);
	    localBarRenderer.setPositiveItemLabelPositionFallback(localItemLabelPosition2);
	    //Titulo vertical sobre cada barra para valor numerico
	    CategoryItemRenderer localCategoryItemRenderer = plot.getRenderer();
	    localCategoryItemRenderer.setBaseItemLabelGenerator(new StandardCategoryItemLabelGenerator());
	    localCategoryItemRenderer.setSeriesItemLabelsVisible(0, Boolean.TRUE);
	    return chart;
	}
	
	/**
	 * Metodo que es utilizado para calcular el tamanio de la grafica
	 * en ancho(width) y alto (height), de acuerdo a:
	 * valoresResultadoSize: numero de datos recuperados de la consulta del indicador.
	 * maxValor: valor maximo de los valores a graficar del eje Y.
	 * 
	 * @param indicador
	 * @return
	 */
	private int[] calculaTamanioGrafica(Indicadores indicador ){
		
		int tamanio[] = new int[2];
		
		  //Calculo para las dimensiones de la grafica
        int width = 0;  
        int height = 0;
        
        if( this.valoresResultadoSize > 1 ){
        	//Calculo para el alto la imagen  en base al numero total de registros
        	int totalRegistros= this.valoresResultadoSize  + 1;
        	//Vertical
        	width = CONSTANTE_WIDTH*totalRegistros +  CONSTANTE_MINIMA;
        	height = CONSTANTE_HEIGHT*this.maxValor + CONSTANTE_MINIMA;
        }
        
        //Ancho minimo del numero de caracteres del titulo
		if (indicador.getTitulo() != null
				&& width < indicador.getTitulo().length()) {
        	width = indicador.getTitulo().length()*10 + 100;
        }
		
		//Alto minimo del numero de caracters del titulo del eje Y
		if (indicador.getTituloCategoriaEjeY() != null
				&& height < indicador.getTituloCategoriaEjeY().length()) {
        	height = indicador.getTituloCategoriaEjeY().length()*10 + 100;
        }
        LOGGER.info(" width:"+width + " height:"+height);
        
        tamanio[0] = width;
        tamanio[1] = height;
        return tamanio;
	}
	
	
	/**
	 * Utilizado para la geneeracion de la leyenda de cada barra
	 * @author GustavoBP
	 */
	static class LabelGenerator extends StandardCategoryItemLabelGenerator
	  {
		private static final long serialVersionUID = -7590719819602399460L;

		public String generateLabel(CategoryDataset paramCategoryDataset, int paramInt1, int paramInt2)
	    {
	      return paramCategoryDataset.getRowKey(paramInt1).toString();
	    }
	  }
}
