/**
 * Nombre del Programa : ConfActividadDocumentoDAOImplTest.java
 * Autor                            : Jacob Lobaco
 * Compania                         : Ultrasist
 * Proyecto                         : NSJP                    Fecha: 28-jul-2011
 * Marca de cambio        : N/A
 * Descripcion General    : N/A
 * Programa Dependient    :N/A
 * Programa Subsecuente   :N/A
 * Cond. de ejecucion     :N/A
 * Dias de ejecucion      :N/A                                Horario: N/A
 *                              MODIFICACIONES
 *------------------------------------------------------------------------------
 * Autor                            :N/A
 * Compania                         :N/A
 * Proyecto                         :N/A                      Fecha: N/A
 * Modificacion           :N/A
 *------------------------------------------------------------------------------
 */
package mx.gob.segob.nsjp.dao.test.confactividad;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

import mx.gob.segob.nsjp.comun.enums.expediente.EstatusExpediente;
import mx.gob.segob.nsjp.comun.enums.institucion.Areas;
import mx.gob.segob.nsjp.dao.actividad.ConfActividadDocumentoDAO;
import mx.gob.segob.nsjp.dao.catalogo.ValorDAO;
import mx.gob.segob.nsjp.dao.test.base.BaseTestPersistencia;
import mx.gob.segob.nsjp.model.ConfActividadDocumento;
import mx.gob.segob.nsjp.model.Funcionario;
import mx.gob.segob.nsjp.model.JerarquiaOrganizacional;
import mx.gob.segob.nsjp.model.NumeroExpediente;
import mx.gob.segob.nsjp.model.Rol;
import mx.gob.segob.nsjp.model.Valor;

import org.springframework.beans.factory.annotation.Autowired;

/**
 *
 * @version 1.0
 * @author Jacob Lobaco
 */
public class ConfActividadDocumentoDAOImplTest extends BaseTestPersistencia<ConfActividadDocumentoDAO> {

    @Autowired
    private ValorDAO valorDao;
    
    @Autowired
    private ConfActividadDocumentoDAO cadDAO; 

	
    
    public void testDummy(){
        BufferedReader br = null;
        Set<String> nombreEstatusPorDarDeAlta = new HashSet<String>();
        try {
            logger.info("testDummy:");
            assert daoServcice != null;
            br = new BufferedReader(new FileReader("/Users/sawbona/trabajo/nsjp/documentos/Datos ConfActividadV2utf.csv"));
            String linea = null;
            int numeroLinea = 1;
            while((linea = br.readLine()) != null){
                String[] campos = linea.split(",");
                if (campos.length != 7) {
                    System.out.println("numeroLinea = " + numeroLinea);
                    System.out.println("campos.length = " + campos.length + "\n");
                }else{
//                    String jerarquiaOrganizacionalNombre = campos[0];
                    String estatusExpediente = campos[1];
                    // Buscamos los estatus que haya que dar de alta.
                    verificaSiExisteEstatusEnLaBase(estatusExpediente, nombreEstatusPorDarDeAlta);
//                    String nombreTipoActividadVal = campos[2];
//                    String muestraCombo = campos[3];
//                    String nombreTipoDocumentoVal = campos[4];
//                    String nombreTipoFormaVal = campos[5];
//                    String usaEditos = campos[6];
                }
                ++numeroLinea;
            }
            System.out.println("NOMBRE POR DAR DE ALTA: " + nombreEstatusPorDarDeAlta.size());
            for (String valor : nombreEstatusPorDarDeAlta) {
                System.out.println("valor = " + valor);
            }
            System.out.println("");
        } catch (IOException ex) {
            Logger.getLogger(ConfActividadDocumentoDAOImplTest.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                br.close();
            } catch (IOException ex) {
                Logger.getLogger(ConfActividadDocumentoDAOImplTest.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public void testConsultarActuacionesPorRol(){
    	Rol rol = new Rol(7L);
    	List<ConfActividadDocumento> lstCAD = null;
    	rol.setJerarquiaOrganizacional(new JerarquiaOrganizacional(10L));
    	lstCAD= cadDAO.consultarActuacionesPorRol(rol);
    	if (lstCAD!=null){
    		for (int i=0;i<lstCAD.size();i++){
    			System.out.println("CAD Id No " + i +" : "+ lstCAD.get(i).getConfActividadDocumentoId());
    		}
    		for (ConfActividadDocumento cad : lstCAD){
    			System.out.print("CAD Id No : "+ cad.getConfActividadDocumentoId());
    			System.out.print(" Actuacion "+ cad.getTipoActividad().getValor());
    			System.out.print(" Edo Inicial "+ cad.getGrupoActividad());
    			System.out.println(" Estad Final "+ cad.getEstadoCambioExpediente().getValor());
    		}
    		
    	}
    
    	
    	
    }
    
    private void verificaSiExisteEstatusEnLaBase(String estatusExpedienteVal, Set<String> nombresPorDarDeAlta){
        System.out.println("procesando = " + estatusExpedienteVal);
//        Valor valor = new Valor();
        List<Valor> matchs = valorDao.existeNombreValor(estatusExpedienteVal);
        if (matchs.isEmpty(	)) {
            nombresPorDarDeAlta.add(estatusExpedienteVal);
        }
    }
   
    public void testConsultaConfActividadDocumentoPorIdActividad(){
    	
    	Long idTipoActividad = 2237L;
    	ConfActividadDocumento confActividadDocumento = daoServcice.consultaConfActividadDocumentoPorIdActividad(idTipoActividad);
    	assertNotNull("El servicio debe debe regresar un valor", confActividadDocumento);
    	if(confActividadDocumento!= null){
    		logger.info("ConfActividadDocumentoId:"+ confActividadDocumento.getConfActividadDocumentoId());
    		logger.info("EstadoCambioExpediente:"+ confActividadDocumento.getEstadoCambioExpediente());
    		if(confActividadDocumento.getForma()!= null){
    			logger.info("Forma:"+ confActividadDocumento.getForma().getFormaId());
    			logger.info("FormaDesc:"+ confActividadDocumento.getForma().getDescForma());
    			
    		}
    	}
    }
    
   public void testConsultarConfActividadDocumento(){
   	NumeroExpediente numeroExpediente = new NumeroExpediente();
   	Valor estatus = new Valor(EstatusExpediente.ABIERTO.getValorId()); 
   	numeroExpediente.setEstatus(estatus);
   	
	Funcionario funcionario =new Funcionario();
	JerarquiaOrganizacional area = new JerarquiaOrganizacional(10L); 
	funcionario.setArea(area);
	
	Long jerarquiaOrganizacionalId = 10L;
	
	Long idCategoriaActidad = 2597L;
        
	List<ConfActividadDocumento> listaConfActividadDocumento = daoServcice.consultarConfActividadDocumento(jerarquiaOrganizacionalId, numeroExpediente, idCategoriaActidad);
   	assertFalse("La lista debe de regresar almenos un valor", listaConfActividadDocumento.isEmpty());
   	logger.info(" #: "+ listaConfActividadDocumento.size());
   	for (ConfActividadDocumento confActividadDocumento : listaConfActividadDocumento) {
		logger.info(" ConfActividadDocumento - Id: "+ confActividadDocumento.getConfActividadDocumentoId());
		logger.info(" ConfActividadDocumento - TipoActividad: "+ confActividadDocumento.getTipoActividad());
		logger.info(" ConfActividadDocumento - TipoActividad: "+ confActividadDocumento.getTipoActividad().getValor());
		logger.info(" ConfActividadDocumento - GrupoActividad: "+ confActividadDocumento.getGrupoActividad());
		logger.info(" ConfActividadDocumento - CategoriaActividad: "+ confActividadDocumento.getCategoriaActividad());
		if(confActividadDocumento.getCategoriaActividad()!= null)
			logger.info(" ConfActividadDocumento - CategoriaActividad: "+ confActividadDocumento.getCategoriaActividad().getValor());
		
		logger.info(" ConfActividadDocumento - JerarquiaOrganizacional: "+ confActividadDocumento.getJerarquiaOrganizacional());
	}
   	
   }
   
   public void testConsultarEstadosDistintosPorJerarquiaOrganizacional( ){
	   
	   Long idJerarquiaOrganizacional = 10L;
	
	   List<Valor> valores = daoServcice.consultarEstadosDistintosPorJerarquiaOrganizacional(idJerarquiaOrganizacional );
	   assertFalse("La lista debe de regresar almenos un valor", valores.isEmpty());
	   logger.info(" #: "+ valores.size());
	   
	   for (Valor valor : valores) {
		   logger.info(" Estatus: "+ valor.getValorId());
		   logger.info(" Estatus: "+ valor.getValor());
	
	   }
   }
   
   public void testConsultarTodas() {
	   List<ConfActividadDocumento> actus = this.cadDAO.findAll("tipoActividad.valor",true);
       logger.debug("formas.size() :: "+actus.size());
       for (ConfActividadDocumento cad : actus) {
   		logger.info(" ID :  "+ cad.getConfActividadDocumentoId());
   		logger.info(" tipoActividadValor: "+ cad.getTipoActividad().getValor());
//   		logger.info(" categoriaActividadValor: "+ cad.getCategoriaActividad().getValor());
       }
   }
   
   public void testConsultarConfActividadDocumentoFiltro(){
		Long categoriaActividadId = null; //CategoriasActividad.DEFENSORIA_PENAL.getValorId();
		Long jerarquiaOrganizacionalId = new Long(
				Areas.DEFENSORIA.ordinal());
		Boolean muestraCombo = null;//new Boolean(true);
		Integer grupoActividad = 2484;
		
	   
//	   Long categoriaActividadId = CategoriasActividad.UAVD.getValorId();
//	   Long jerarquiaOrganizacionalId = 44L;
//	   Boolean muestraCombo = new Boolean(false);

		ConfActividadDocumento filtroConfActividadDocumento = new ConfActividadDocumento();
		filtroConfActividadDocumento.setCategoriaActividad(new Valor(
				categoriaActividadId));
		filtroConfActividadDocumento
				.setJerarquiaOrganizacional(new JerarquiaOrganizacional(
						jerarquiaOrganizacionalId));
		filtroConfActividadDocumento.setMuestraEnCombo(muestraCombo);
		
		filtroConfActividadDocumento.setGrupoActividad(grupoActividad);
		
		List<ConfActividadDocumento> lista = this.cadDAO
				.consultarConfActividadDocumentoFiltro(filtroConfActividadDocumento);
		
		assertFalse("La lista debe de regresar almenos un valor", lista.isEmpty());
		logger.debug("lista.size() :: " + lista.size());
		for (ConfActividadDocumento cad : lista) {
			
			logger.info(" ID :  " + cad.getConfActividadDocumentoId());
			if (cad.getTipoActividad() != null) {
				logger.info(" tipoActividadValor: "
						+ cad.getTipoActividad().getValor());
			}
			if (cad.getJerarquiaOrganizacional() != null) {
				logger.info(" �rea:"
						+ cad.getJerarquiaOrganizacional().getNombre());
			}
			if (cad.getCategoriaActividad() != null) {
				logger.info(" categoriaActividadValor: "
						+ cad.getCategoriaActividad().getValor());
			}
			logger.info(" MuestraEnCombo: "
					+ cad.getMuestraEnCombo());
		}
   }
   
   public void testConsultarProximoEstatusDeNumExp(){
	Long idTipoActividad = 3752L;
	Long idEstatusActualNumExp = 1712L;
	Long jeraquiaOrganizacional = 10L;
	ConfActividadDocumento resultado = daoServcice.consultarProximoEstatusDeNumExp(idTipoActividad, idEstatusActualNumExp, jeraquiaOrganizacional);
	assertNotNull(resultado);
	System.out.println("Id confActividad: " + resultado.getConfActividadDocumentoId());
	System.out.println("El proximo estatus del numero de expediente es: " + resultado.getEstadoCambioExpediente().getValorId());
	
   }
   
   public void testConsultarConfActividadDocumentoObjeto(){
	   ConfActividadDocumento filtro = new ConfActividadDocumento();

	   JerarquiaOrganizacional jerOrg = new JerarquiaOrganizacional();
	   jerOrg.setJerarquiaOrganizacionalId(72L);

	   filtro.setGrupoActividad(2492);
	   filtro.setJerarquiaOrganizacional(jerOrg);

		List<ConfActividadDocumento> actividades = daoServcice
				.consultarConfActividadDocumentoFiltro(filtro);
		if (actividades != null) {
			logger.info("Tamanio de la lista: " + actividades.size());
			for (ConfActividadDocumento act : actividades) {
				logger.info("Id ConfActividad: "
						+ act.getConfActividadDocumentoId());
				logger.info("iGrupoActividad: " + act.getGrupoActividad());
				logger.info("Id Jerarquia Organizacional: "
						+ act.getJerarquiaOrganizacional()
								.getJerarquiaOrganizacionalId());
			}
		}
   }
}
