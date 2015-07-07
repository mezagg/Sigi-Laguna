/**
 * Nombre del Programa : DocumentoDAOImplTest.java
 * Autor                            : vaguirre
 * Compania                    : Ultrasist
 * Proyecto                      : NSJP                    Fecha: 18 May 2011
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
package mx.gob.segob.nsjp.dao.test.documento.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import mx.gob.segob.nsjp.comun.enums.actividad.Actividades;
import mx.gob.segob.nsjp.comun.enums.documento.TipoDocumento;
import mx.gob.segob.nsjp.comun.enums.documento.TipoForma;
import mx.gob.segob.nsjp.comun.enums.institucion.Areas;
import mx.gob.segob.nsjp.comun.excepcion.NSJPNegocioException;
import mx.gob.segob.nsjp.dao.archivo.ArchivoDigitalDAO;
import mx.gob.segob.nsjp.dao.documento.DocumentoDAO;
import mx.gob.segob.nsjp.dao.documento.IndiceEstructuradoDAO;
import mx.gob.segob.nsjp.dao.forma.FormaDAO;
import mx.gob.segob.nsjp.dao.test.base.BaseTestPersistencia;
import mx.gob.segob.nsjp.model.Actividad;
import mx.gob.segob.nsjp.model.ArchivoDigital;
import mx.gob.segob.nsjp.model.ConfInstitucion;
import mx.gob.segob.nsjp.model.Documento;
import mx.gob.segob.nsjp.model.Expediente;
import mx.gob.segob.nsjp.model.Forma;
import mx.gob.segob.nsjp.model.Funcionario;
import mx.gob.segob.nsjp.model.IndiceEstructurado;
import mx.gob.segob.nsjp.model.MedidaCautelar;
import mx.gob.segob.nsjp.model.NumeroExpediente;
import mx.gob.segob.nsjp.model.Valor;

import org.springframework.beans.factory.annotation.Autowired;

/**
 * Describir el objetivo de la clase con punto al final.
 * 
 * @version 1.0
 * @author vaguirre
 * 
 */
public class DocumentoDAOImplTest extends BaseTestPersistencia<DocumentoDAO> {

	@Autowired
	private ArchivoDigitalDAO archivoDigitalDAO;
	@Autowired
	private FormaDAO formaDAO;
	@Autowired
	private IndiceEstructuradoDAO indiceEstructuradoDAO;

	public void testRead() {
		//Long id = daoServcice.create(crearDocumentoPrueba());
		Long id = 487L;
		Documento doc = daoServcice.read(id);
		assertNotNull("Se esperaba un documento", doc);

		//daoServcice.delete(doc);
		//archivoDigitalDAO.delete(doc.getArchivoDigital());
	}

	public void testWrite() {

		Long idNuevo = daoServcice.create(crearDocumentoPrueba());
//		Documento doc = daoServcice.read(idNuevo);
		assertNotNull("Documento no pudo ser creado, se esperaba un ID",
				idNuevo);

//		daoServcice.delete(doc);
//		archivoDigitalDAO.delete(doc.getArchivoDigital());
	}

	private Documento crearDocumentoPrueba() {
		ArchivoDigital ad = new ArchivoDigital();
		ad.setNombreArchivo("d2.pdf");
		ad.setTipoArchivo("pdf");
		ad.setContenido(obtenerArchivo("/archivos/d2.pdf"));
		Long id = archivoDigitalDAO.create(ad);
		ad.setArchivoDigitalId(id);
		Forma forma = formaDAO.read(new Long(1));
		Documento doc = new Documento();
		doc.setForma(forma);
		doc.setArchivoDigital(ad);
		doc.setFechaCreacion(new Date());
		doc.setFolioDocumento("5335");
		doc.setNombreDocumento("Acuerdos de Defensa");
		doc.setNumeroFojas("500");
		doc.setOrigenDocumento((short) 1);

//		doc.setTextoParcial("<p>Hola Mundo</p>");
		doc.setVersion(1.5);
		doc.setTipoDocumento(new Valor(new Long(82)));
		return doc;
	}

	public void testConsultarDocumentosExpediente() {
		
		List<Long> idsAreas = new ArrayList<Long>();
		//Long idAreaActual = Areas.UNIDAD_INVESTIGACION.parseLong();
		Long idAreaActual = Areas.UNIDAD_INVESTIGACION.parseLong();
		
		idsAreas.add(7L);
		idsAreas.add(Areas.FISCAL_FACILITADOR.parseLong());
		List<Documento> respuesta = daoServcice
				.consultarDocumentoPorExpediente(3537L,278L, null, idAreaActual);
		assertNotNull(respuesta);
		assertTrue("La lista debe tener minimo un registro",
				respuesta.size() > 0);
		for (Documento documento : respuesta) {
			logger.info("Documento Id : " + documento.getDocumentoId());
			logger.info("Fecha creacion Act: " + documento.getActividad().getFechaCreacion());
			logger.info("Nombre Actividad: " + documento.getActividad().getTipoActividad().getValor());
			logger.info("Tipo Documento: " + documento.getTipoDocumento().getValor());
			logger.info("Nombre Documento: " + documento.getNombreDocumento());
			logger.info("Fecha Documento doc: " + documento.getFechaCreacion());
			logger.info("Es guardadoParcial: " + documento.getEsGuardadoParcial());
			logger.info("Area responsable: " + documento.getActividad().getFuncionario().getCatAreaNegocio().getNombreCatAN());
		}
		logger.info("\nDocumentos obtenidos : " + respuesta.size());
	}

	public void testConsultarDocumentosXTipoDocumento() {
		Long tipoDocumento = TipoDocumento.AUTO_INICIO_PROCEDIMIENTO_EJECUCION.getValorId();
		Long numeroExpedienteId = 490L;
		List<Documento> documentos = daoServcice
				.consultarDocumentosXExpedienteYTipoDocumento(numeroExpedienteId,
						tipoDocumento);

		logger.info("Existen " + documentos.size());
		for (Documento doc : documentos) {
			logger.info("************************************************");
			logger.info("Id Docuemnto: "+doc.getDocumentoId()+": "+doc.getNombreDocumento());
			if(doc.getActividad() != null){
				logger.info(" Expediente ID: "+doc.getExpediente().getExpedienteId());
				logger.info(" Numero Expediente: "+doc.getExpediente().getNumeroExpediente());
				logger.info(" Numero Expediente ID: "+doc.getExpediente().getNumeroExpedienteId());
				//logger.info(" Numero de Caso: "+doc.getExpediente().getCaso().getCasoId() +" / "+doc.getExpediente().getCaso().getNumeroGeneralCaso());
			}else
				logger.info("No trae expediente");
			
			String tipo=(doc.getTipoDocumento()!=null)?doc.getTipoDocumento().getValor():"No trae";
			logger.info(" Tipo de Documento: "+ tipo);
			logger.info(" Fecha de Creacion: "+doc.getFechaCreacion());

		}
	}
	
	public void testConsultarDocumentoPorNumeroExpedienteId(){
		
		List<Documento> docs = daoServcice.consultarDocumentosPorNumeroExpedienteId(17L);
		logger.debug("Numero de documentos " + docs.size());
		assertNotNull(docs);
		
	}
	
	public void testConsultarDocumentosAudienciaByTipoForma () {
		List<Documento> respuesta = daoServcice.consultarDocumentosAudienciaByTipoForma(14L, TipoForma.RESOLUCION.getValorId());
		
		assertTrue("La lista debe regresar minimo una lista : ", respuesta.size()>0);
		logger.info("La lista debe regresar minimo una lista : " + respuesta.size());
	}
	
	
	public void testConsultarIndicesEstruturado(){		
		List<IndiceEstructurado> obejtos = indiceEstructuradoDAO.consultarIndicesEstructuradosPorTipoOficio(2L);
		logger.debug("Numero de Indices estructurados " + obejtos.size());
		assertNotNull(obejtos);		
	}
	
	public void consultarDocumentosXTipoDocumento(){
		List<Documento> documentos = daoServcice.consultarDocumentosXTipoDocumento(TipoDocumento.ACTA.getValorId());
		assertNotNull(documentos);
		logger.info("Existen "+documentos.size()+ " documentos");
		for (Documento doc : documentos) {
			logger.info("------------------------------");
//			logger.info("Numero de caso: "+doc.getActividad().getExpediente().getCaso().getCasoId()+" ("+doc.getActividad().getExpediente().getCaso().getNumeroGeneralCaso()+")");
			logger.info("Identificador de la controversia resuelta: "+doc.getDocumentoId());
			logger.info("Nombre completo del fiscal que llevo a cabo la controversia: "+doc.getResponsableDocumento().getNombreCompleto());
			logger.info("Nombre del documento: "+doc.getNombreDocumento());
			logger.info("Bandera si ya ha sido leida la controversia: "+doc.getEsEnviado());
			logger.info("Fecha de envio de la misma(creacion doc): "+doc.getFechaCreacion());
//			logger.info("Fecha de envio de la misma(creacion act): "+doc.getActividad().getFechaCreacion());
			logger.info("Archivo digital: "+doc.getArchivoDigital().getArchivoDigitalId());
		}
	}

	public void testConsultarDocumentoPorId() {
		Long documentoId = 4640L;
		Documento  doc = daoServcice
				.consultarDocumentoPorId(documentoId);
		assertNotNull(" El documento consultado no existe.ID:"+ documentoId,doc);

		logger.info("Id Docuemnto: "+doc.getDocumentoId()+": "+doc.getNombreDocumento());
		if(doc.getActividad() != null){
			logger.info(" Numero de Expediente: "+doc.getExpediente().getExpedienteId());
			logger.info(" Numero de Caso: "+doc.getExpediente().getCaso().getCasoId() +" / "+doc.getExpediente().getCaso().getNumeroGeneralCaso());
		}else
			logger.info("No trae expediente");
		String tipo=(doc.getTipoDocumento()!=null)?doc.getTipoDocumento().getValor():"No trae";
		logger.info(" Tipo de Documento: "+ tipo);
		logger.info(" Fecha de Recepcion: "+doc.getFechaCreacion());
		logger.info(" ArchivoDigital: "+doc.getArchivoDigital());
		logger.info(" Actividad: "+doc.getActividad());
	}
	
	public void testArchDigID(){
		Long archivoDigitalID=480L;
		Documento respuesta = daoServcice.consultarDocumentoPorArchivoDigital(archivoDigitalID);
		logger.debug(respuesta.getArchivoDigital().getNombreArchivo());
		logger.debug(respuesta.getArchivoDigital().getArchivoDigitalId().longValue());		
		logger.debug(respuesta.getDocumentoId());
	}
	
	
	public void testConsultarDocumentosReinsercionSocial() {
		
		
		Funcionario funcionario = new Funcionario(48L);
		Documento documento = new Documento();
		NumeroExpediente numeroExpediente = new NumeroExpediente();
		numeroExpediente.setNumeroExpedienteId(1L);
		List<Documento> respuesta = null;
			try{
			
			respuesta = daoServcice
				.consultarDocumentosReinsercionSocial(funcionario, documento, numeroExpediente );
			}catch(Exception exception){
			}
		assertNotNull(respuesta);
		assertTrue("La lista debe tener minimo un registro",
				respuesta.size() > 0);
		logger.info("Documentos obtenidos : " + respuesta.size());
	}
	

	public void testConsultarDocumentoPorFolio(){
		try {
			Long institucionOrigen = 2L;
			String folio ="1800";
			List<Documento>  documentos = daoServcice.consultarDocumentosPorFolioInstitucion(folio, institucionOrigen );
			
			if(documentos!=null && !documentos.isEmpty()){
				for (Documento documento : documentos) {
					logger.info("Documento:"+ documento.getDocumentoId());
					logger.info("Documento:"+ documento.getTipoDocumento().getValor());
					logger.info("Documento - es Medida :"+ (documento instanceof MedidaCautelar));
				}
			}
			
		} catch (Exception e) {
			logger.info(e.getMessage(), e);
		}
	}
	
	public void testsConsultarDocumentosPorTipoActividadYNumExpedienteId() {

		
		try {
			List<Documento> documentos = daoServcice
					.consultarDocumentosPorTipoActividadYNumExpedienteId(
							403L,
							Actividades.GENERAR_DOCUMENTO_DE_CAMBIO_DE_ESTATUS_DE_MEDIDA_CAUTELAR
									.getValorId());

			if (documentos != null && documentos.size() > 0L) {
				logger.info("EXISTEN " + documentos.size() + " DOCUMENTOS");

				for (Documento doc : documentos) {
					logger.info("-----------------------------------------");
					logger.info("DOCUMENTO ID: "
							+ doc.getDocumentoId());
					logger.info("ARCHIVO DIGITAL ID: "
							+ doc.getArchivoDigital().getArchivoDigitalId());
				}
			} else {
				logger.info("NO EXISTEN DOCUMENTOS");
			}
		} catch (NSJPNegocioException e) {
			logger.info(e.getMessage(), e);
		}
	}
	
	public void testConsultarDocumentoFiltro() {
		Documento filtro = new Documento();
		
		Expediente exp = new Expediente();
		exp.setExpedienteId(595L);
		
		Actividad act = new Actividad();
		act.setTipoActividad(new Valor(408L));
//		act.setExpediente(exp);
		
		filtro.setActividad(act);
		
		try {
			Documento docConsultado = daoServcice.consultarDocumentoFiltro(filtro);
			if (docConsultado != null){
				logger.info("Id del documento: " +docConsultado.getDocumentoId());
			}else{
				logger.info("No se encontro ningun documento con el tipo de actividad: "
						+act.getActividadId()+" para el expediente: "+exp.getExpedienteId());
			}
		} catch (NSJPNegocioException e) {
			logger.error(e.getMessage(), e);
		}

	}
	
	public void testConsultarDocumentosPorExpedienteYTipos(){
		Expediente exp = new Expediente();
		exp.setExpedienteId(540L);
		
		Valor tipoDoc1 = new Valor (7084L);
		Valor tipoDoc2 = new Valor (2L);
		
		List<Valor> tiposDocumento = new ArrayList<Valor>();
		tiposDocumento.add(tipoDoc1);
		tiposDocumento.add(tipoDoc2);
		
		try {
			List<Documento> docsConsultados = daoServcice.consultarDocumentosPorExpedienteYTipos(exp, tiposDocumento);
			if (docsConsultados != null 
					&& !docsConsultados.isEmpty()){
				for (Documento doc : docsConsultados){
					logger.info("Id Documento: " + doc.getDocumentoId());
					logger.info("Tipo de documento: " + doc.getTipoDocumento().getValorId());
					logger.info("Id Archivo Digital: " + doc.getArchivoDigital().getArchivoDigitalId());
				}
			}
		} catch (NSJPNegocioException e) {
			logger.error(e.getMessage(), e);
		}
	}
	
	public void testConsultarDocumentosPorArchivosDigitales(){
		List<Long> listaIds = new ArrayList<Long>();
		listaIds.add(1071L);
		listaIds.add(1072L);
		listaIds.add(1073L);
		listaIds.add(1074L);
		listaIds.add(1075L);
		listaIds.add(1076L);
		listaIds.add(1077L);
		listaIds.add(1078L);
		listaIds.add(1079L);
		listaIds.add(1080L);
		listaIds.add(1081L);
		listaIds.add(1082L);
		
		try {
			List<Documento> documentos = daoServcice.consultarDocumentosPorArchivosDigitales(listaIds);
			if (documentos != null 
					&& !documentos.isEmpty()){
				for (Documento d : documentos){
					logger.info("Id del documento: " + d.getDocumentoId());
					logger.info("Nombre del documento: "+d.getNombreDocumento());
					logger.info("Id del archivo digital: " + d.getArchivoDigital().getArchivoDigitalId());
				}
			}
		} catch (NSJPNegocioException e) {
			logger.error(e.getMessage(), e);
		}
	}
	
	public void testsConsultarDocumentosPorTipoActividadYExpedienteId() {
		try {
			List<Documento> documentos = daoServcice
					.consultarDocumentosPorTipoActividadYExpedienteId(
							4013L, Actividades.GENERAR_DENUNCIA_EN_ATP.getValorId());

			if (documentos != null && documentos.size() > 0L) {
				logger.info("EXISTEN " + documentos.size() + " DOCUMENTOS");

				for (Documento doc : documentos) {
					logger.info("-----------------------------------------");
					logger.info("DOCUMENTO ID: "
							+ doc.getDocumentoId());
					logger.info("ARCHIVO DIGITAL ID: "
							+ doc.getArchivoDigital().getArchivoDigitalId());
				}
			} else {
				logger.info("NO EXISTEN DOCUMENTOS");
			}
		} catch (NSJPNegocioException e) {
			logger.info(e.getMessage(), e);
		}
	}
	
	public void testsObtenerUltimoFolioDocumento(){
		
		try {
			ConfInstitucion institucion = daoServcice.consultarInsitucionActual();
			
			String ultimo = daoServcice.obtenerUltimoFolioDocumento(institucion);
			
			logger.info("ULTIMO FOLIO DEL DOCUMENTO::::"+ultimo);
			
			
		} catch (NSJPNegocioException e) {
			logger.error(e.getMessage());
		}
	}
}