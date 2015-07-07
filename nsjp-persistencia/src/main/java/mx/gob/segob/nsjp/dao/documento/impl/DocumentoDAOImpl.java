/**
 * Nombre del Programa : DocumentoDAOImpl.java
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
package mx.gob.segob.nsjp.dao.documento.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import mx.gob.segob.nsjp.comun.enums.actividad.Actividades;
import mx.gob.segob.nsjp.comun.enums.excepciones.CodigoError;
import mx.gob.segob.nsjp.comun.enums.forma.Formas;
import mx.gob.segob.nsjp.comun.enums.institucion.Areas;
import mx.gob.segob.nsjp.comun.excepcion.NSJPNegocioException;
import mx.gob.segob.nsjp.comun.util.ConsecutivosUtil;
import mx.gob.segob.nsjp.comun.util.tl.PaginacionThreadHolder;
import mx.gob.segob.nsjp.dao.base.impl.GenericDaoHibernateImpl;
import mx.gob.segob.nsjp.dao.documento.DocumentoDAO;
import mx.gob.segob.nsjp.dto.base.PaginacionDTO;
import mx.gob.segob.nsjp.model.Actividad;
import mx.gob.segob.nsjp.model.ArchivoDigital;
import mx.gob.segob.nsjp.model.CatAreasNegocio;
import mx.gob.segob.nsjp.model.ConfInstitucion;
import mx.gob.segob.nsjp.model.Documento;
import mx.gob.segob.nsjp.model.Expediente;
import mx.gob.segob.nsjp.model.Funcionario;
import mx.gob.segob.nsjp.model.NumeroExpediente;
import mx.gob.segob.nsjp.model.Rol;
import mx.gob.segob.nsjp.model.Usuario;
import mx.gob.segob.nsjp.model.UsuarioRol;
import mx.gob.segob.nsjp.model.Valor;

import org.hibernate.Query;
import org.springframework.stereotype.Repository;

/**
 * Describir el objetivo de la clase con punto al final.
 * 
 * @version 1.0
 * @author vaguirre
 * 
 */
@Repository
public class DocumentoDAOImpl extends GenericDaoHibernateImpl<Documento, Long>
        implements
            DocumentoDAO {
	
	public static final String SIGNO_MENOS = "-";
	
    @SuppressWarnings({ "rawtypes", "unchecked" })
	public List<Documento> consultarDocumentoPorExpediente(Long expedienteId, Long idFuncionario, List<Long> idsJerarquiasOrganizacionales, Long idAreaActual) {
    	List<Documento> loDocumentos = new ArrayList<Documento>();
    	
    	String idsElementos = "";
		if(idsJerarquiasOrganizacionales!= null && !idsJerarquiasOrganizacionales.isEmpty() && idsJerarquiasOrganizacionales.size() > 0)
			idsElementos = idsJerarquiasOrganizacionales.toString().substring(1, idsJerarquiasOrganizacionales.toString().length()-1);
		
        StringBuffer queryString = new StringBuffer();
        queryString.append(" SELECT d.Documento_id, a.dFechaCreacion AS fcAct, vta.cValor as actividadValor, vtd.cValor as documentoValor, d.cNombreDocumento, d.dFechaCreacion AS fcDoc, d.esGuardadoParcial, j.cNombre, vtd.Valor_id as documentoValorId, d.iFolioDocumento, d.ConfInstitucion_id, vta.Valor_id as actividadValorId, ad.ctipoArchivo ")
        		.append(" from Documento d	inner join Valor vtd on d.TipoDocumento_val = vtd.Valor_id inner join Actividad a on a.Documento_id = d.Documento_id ")
                .append(" inner join Valor vta on a.TipoActividad_val = vta.Valor_id left outer join JerarquiaOrganizacional j on d.JerarquiaOrganizacional_id = j.	JerarquiaOrganizacional_id")
                .append(" left outer join ArchivoDigital ad on d.ArchivoDigital_id = ad.ArchivoDigital_id")
                .append(" WHERE a.Expediente_id =").append(expedienteId)
                .append(" AND a.Documento_id IS NOT NULL");        
        
        if(idFuncionario != null && idFuncionario > 0){
        	queryString.append(" AND a.iClaveFuncionario=").append(idFuncionario);        	
        }
        
        if(idsJerarquiasOrganizacionales != null && !idsJerarquiasOrganizacionales.isEmpty() && idsJerarquiasOrganizacionales.size() > 0){
        	if(idsJerarquiasOrganizacionales.contains(Areas.FISCAL_FACILITADOR.parseLong())){
        		queryString.append(" AND d.JerarquiaOrganizacional_id IN (").append(idsElementos).append(")"); 
        		//Validacion que permite excluir los documentos parciales de el area diferente a la actual
        		if(idAreaActual != null && idAreaActual > 0){
            		//Filtra aquellos documentos guardados parcialmente, para que no sean visibles por otras areas.
            		queryString.append(" AND d.Documento_id NOT IN (")
        				    				.append(" SELECT d.Documento_id ")
        				    		        .append(" FROM DOCUMENTO d, ACTIVIDAD a")
        				    		        .append(" WHERE a.Documento_id = d.Documento_id ")
        				    		        .append(" AND a.Expediente_id =").append(expedienteId)
        				    		        .append(" AND a.Documento_id IS NOT NULL")
        				    		        .append(" AND d.esGuardadoParcial = 1") //Documentos parciales	
        				    		        .append(" AND d.JerarquiaOrganizacional_id <> ").append(idAreaActual)
            		.append(")");       
            	}
        		queryString.append(" UNION ")
				        		.append(" SELECT d.Documento_id, a.dFechaCreacion AS fcAct, vta.cValor as actividadValor, vtd.cValor as documentoValor, d.cNombreDocumento, d.dFechaCreacion AS fcDoc, d.esGuardadoParcial, j.cNombre, vtd.Valor_id as documentoValorId, d.iFolioDocumento, d.ConfInstitucion_id, vta.Valor_id as actividadValorId, ad.ctipoArchivo ")
				        	    .append(" from Documento d	inner join Valor vtd on d.TipoDocumento_val = vtd.Valor_id inner join Actividad a on a.Documento_id = d.Documento_id ")
				        	    .append(" inner join Valor vta on a.TipoActividad_val = vta.Valor_id left outer join JerarquiaOrganizacional j on d.JerarquiaOrganizacional_id = j.	JerarquiaOrganizacional_id")
 			                    .append(" left outer join ArchivoDigital ad on d.ArchivoDigital_id = ad.ArchivoDigital_id")
				        	    .append(" WHERE a.Expediente_id =").append(expedienteId)
				                .append(" AND a.Documento_id IS NOT NULL")
		                		.append(" AND a.TipoActividad_val IN(").append(Actividades.RECIBIR_CANALIZACION_JAR.getValorId()).append(")");
        	}else{
        		queryString.append(" AND d.JerarquiaOrganizacional_id IN (").append(idsElementos).append(")");
        	}
        }
        
    	if(idAreaActual != null && idAreaActual > 0 && !idAreaActual.equals(Areas.UNIDAD_INVESTIGACION.parseLong())){
    		//Filtra aquellos documentos guardados parcialmente, para que no sean visibles por otras areas.
    		queryString.append(" AND d.Documento_id NOT IN (")
				    				.append(" SELECT d.Documento_id ")
				    		        .append(" FROM DOCUMENTO d, ACTIVIDAD a")
				    		        .append(" WHERE a.Documento_id = d.Documento_id ")
				    		        .append(" AND a.Expediente_id =").append(expedienteId)
				    		        .append(" AND a.Documento_id IS NOT NULL")
				    		        .append(" AND d.esGuardadoParcial = 1")
				    		        .append(" AND d.JerarquiaOrganizacional_id <> ").append(idAreaActual)
    		.append(")");       
    	}
    	
        final PaginacionDTO pag = PaginacionThreadHolder.get();

        if (pag != null && pag.getCampoOrd() != null) {
        	if (pag.getCampoOrd().equals("da")) {
				queryString.append(" order by ");
				// Area del responsable
                queryString.append(" j.cNombre");
				queryString.append(" ").append(pag.getDirOrd());
			}
        	if (pag.getCampoOrd().equals("fechaActividad")) {
        		queryString.append(" order by ");
        		queryString.append(" fcAct");//Fecha actividad
        		queryString.append(" ").append(pag.getDirOrd());
        	}
        	if (pag.getCampoOrd().equals("nombreActividad")) {
				queryString.append(" order by ");
				//Nombre actividad
                queryString.append(" vta.cValor");
				queryString.append(" ").append(pag.getDirOrd());
			}
			if (pag.getCampoOrd().equals("tipo")) {
				queryString.append(" order by ");
				// Tipo del documento
                queryString.append(" vtd.cValor");
				queryString.append(" ").append(pag.getDirOrd());
			}
			if (pag.getCampoOrd().equals("nombre")) {
				queryString.append(" order by ");
				// Nombre del documento
                queryString.append(" d.cNombreDocumento");
				queryString.append(" ").append(pag.getDirOrd());
			}
			if (pag.getCampoOrd().equals("fecha")) {
				queryString.append(" order by ");
				// Fecha del documento
				queryString.append(" fcDoc");
				queryString.append(" ").append(pag.getDirOrd());
			}
			if (pag.getCampoOrd().equals("documento")) {
				queryString.append(" order by ");
				// Guardado parcial y definitivo
				queryString.append(" d.esGuardadoParcial");
				queryString.append(" ").append(pag.getDirOrd());
			}
        }

        Query query = super.getSession().createSQLQuery(queryString.toString());
        
        if (pag != null && pag.getPage() != null) {
            query.setFirstResult(pag.getFirstResult());
            if (pag.getRows() != null & pag.getRows() > 0) {
                query.setMaxResults(pag.getRows());
            } else {
                query.setMaxResults(PaginacionDTO.DEFAULT_MAX_RESULT); // default
            }
        }

        List<Object> resp = query.list();
        logger.debug("resp.size() :: " + resp.size());

        if (pag != null && pag.getPage() != null) {
            query.setFirstResult(0);
            query.setMaxResults(-1);
            final List<Object> temp = query.list();
            logger.debug("temp.size() :: " + temp.size());
            pag.setTotalRegistros(new Long(temp.size()));
            PaginacionThreadHolder.set(pag);
        }
        
        Iterator iterator = resp.iterator();
        
        while(iterator.hasNext()){ 
	        Object []obj = (Object[])iterator.next();
	     	Documento loDocumento = new Documento();
	     	loDocumento.setDocumentoId(((BigDecimal)obj[0]).longValue());
	     	Date fechaCreacionAct = (Date) obj[1];
	     	Actividad loActividad = new Actividad();
	     	loActividad.setFechaCreacion(fechaCreacionAct);
	     	loActividad.setTipoActividad(new Valor(((BigDecimal)obj[11]).longValue(), (String)obj[2]));
	     	Funcionario loFuncionario = new Funcionario();
	     	CatAreasNegocio loCatAreasNegocio = new  CatAreasNegocio();
	     	loCatAreasNegocio.setNombreCatAN(  (String)obj[7] == null ? "-" : (String)obj[7]);
	     	loFuncionario.setCatAreaNegocio(loCatAreasNegocio);
	     	loActividad.setFuncionario(loFuncionario);
	     	loDocumento.setActividad(loActividad);
	     	loDocumento.setTipoDocumento(new Valor(((BigDecimal)obj[8]).longValue(), (String)obj[3]));
        	loDocumento.setNombreDocumento((String)obj[4]);
        	Date fechaCreacionDoc = (Date) obj[5];
        	loDocumento.setFechaCreacion(fechaCreacionDoc);
        	BigDecimal lo = (BigDecimal)obj[6]; 
	     	loDocumento.setEsGuardadoParcial((lo.toString().equals("1") ? true: false));
			loDocumento.setFolioDocumento(obj[9] != null ? (String) obj[9]: null);
			loDocumento.setConfInstitucion(obj[10] != null ? new ConfInstitucion(
							((BigDecimal) obj[10]).longValue()) : null);
			loDocumento.setArchivoDigital(new ArchivoDigital(null, null, (String) obj[12]));
        	loDocumentos.add(loDocumento);
        }
        
        return loDocumentos;
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Documento> consultarDocumentosXExpedienteYTipoDocumento(
            Long numExpId, Long tipoDocumento) {
        StringBuffer queryString = new StringBuffer();

        queryString
                .append("SELECT new Documento(d.documentoId, d.nombreDocumento,e.expedienteId, ne.numeroExpediente, e.caso.casoId, e.caso.numeroGeneralCaso, d.tipoDocumento.valorId, d.tipoDocumento.valor, d.fechaCreacion, d.folioDocumento, d.descripcion,d.esGuardadoParcial)")
                .append(" FROM Documento d LEFT JOIN d.actividad da")
                .append(" LEFT JOIN da.expediente e")
                .append(" LEFT JOIN e.numeroExpedientes ne")
                .append(" WHERE 1=1");
        if (numExpId != null) {
            queryString.append(" AND ne.numeroExpedienteId=").append(numExpId);
        }
        queryString.append(" AND da.documento is not null ")
                .append(" AND d.archivoDigital is not null");
        
        if(tipoDocumento != null && tipoDocumento > 0){
        	queryString.append(" AND d.tipoDocumento=").append(tipoDocumento);
        }
        
        logger.info("\n\r/***** " + queryString.toString());

        final PaginacionDTO pag = PaginacionThreadHolder.get();

		logger.debug("pag :: " + pag);
		if (pag != null && pag.getCampoOrd() != null) {
			if (pag.getCampoOrd().equals("1")) {
				queryString.append(" order by ");
				queryString.append("d.folioDocumento");
				queryString.append(" ").append(pag.getDirOrd());
			}
		}
		if (pag != null && pag.getCampoOrd() != null) {
			if (pag.getCampoOrd().equals("2")) {
				queryString.append(" order by ");
				queryString.append("d.fechaCreacion");
				queryString.append(" ").append(pag.getDirOrd());
			}
		}
		return super.ejecutarQueryPaginado(queryString, pag);   

    }
    
    
    @SuppressWarnings("unchecked")
    @Override
    public List<Documento> consultarDocumentosSinActividadXExpedienteYTipoDocumento(
            Long numExpId, Long tipoDocumento) {
        StringBuffer queryString = new StringBuffer();

        queryString
                .append("SELECT new Documento(m.documentoId,m.nombreDocumento,m.fechaCreacion) ")
                .append(" FROM Medida m")
                .append(" WHERE 1=1");
        if (numExpId != null) {
            queryString.append(" AND m.numeroExpediente.numeroExpedienteId=").append(numExpId);
        }
        queryString.append(" AND m.archivoDigital is not null ");
        
        if(tipoDocumento != null && tipoDocumento > 0){
        	queryString.append(" AND m.tipoDocumento=").append(tipoDocumento);
        }
        
        logger.info("\n\r/***** " + queryString.toString());

        final PaginacionDTO pag = PaginacionThreadHolder.get();

		logger.debug("pag :: " + pag);
		if (pag != null && pag.getCampoOrd() != null) {
			if (pag.getCampoOrd().equals("1")) {
				queryString.append(" order by ");
				queryString.append("m.fechaCreacion");
				queryString.append(" ").append(pag.getDirOrd());
			}
		}
		return super.ejecutarQueryPaginado(queryString, pag);   

    }

    /**
     * {@inheritDoc}
     */
    @SuppressWarnings({"unchecked", "rawtypes"})
    @Override
    public List<Documento> consultarDocumentosPorUsuario(Usuario usuario,
            Long tipoDocumento) {
        if (logger.isDebugEnabled()) {
            logger.debug("ConsultarDocumentosPorUsuario = " + usuario);
        }
        Funcionario funcionario = usuario.getFuncionario();
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT docs FROM Documento docs ")
                .append("WHERE docs.responsableDocumento = ")
                .append(funcionario.getClaveFuncionario())
                .append("AND docs.tipoDocumento = ").append(tipoDocumento);
        Query query = super.getSession().createQuery(sb.toString());
        List list = query.list();
        if (logger.isDebugEnabled()) {
            logger.debug("list.size() = " + list.size());
        }
        return list;
    }

    @Override
    public Documento consultarDocumentoXExpediente(Expediente expediente,
            Long tipoDocumento) {
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT d FROM Documento d ")
                .append("INNER JOIN d.actividad da ")
                .append("INNER JOIN da.expediente e ")
                .append("INNER JOIN e.numeroExpedientes ne ")
                .append("WHERE ne.numeroExpediente = :numeroExpediente ")
                .append("AND d.tipoDocumento = ").append(tipoDocumento);
        Query query = super.getSession().createQuery(sb.toString());
        query.setParameter("numeroExpediente", expediente.getNumeroExpediente());
        return (Documento) query.uniqueResult();
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Documento> consultarDocumentosPorNumeroExpedienteId(
            Long numeroExpedienteId) {

        // StringBuilder sb = new StringBuilder();
        // sb.append("SELECT d FROM Documento d ")
        // .append("INNER JOIN d.actividad da ")
        // .append("INNER JOIN da.expediente e ")
        // .append("INNER JOIN e.numeroExpedientes ne ")
        // .append("WHERE ne.numeroExpedienteId = ? ");
        // return getHibernateTemplate().find(sb.toString(),
        // numeroExpedienteId);

        StringBuffer sb = new StringBuffer();
        sb.append("SELECT d FROM Documento d ")
                .append("INNER JOIN d.actividad da ")
                .append("INNER JOIN da.expediente e ")
                .append("INNER JOIN e.numeroExpedientes ne ")
                .append("WHERE ne.numeroExpedienteId =")
                .append(numeroExpedienteId);

        final PaginacionDTO pag = PaginacionThreadHolder.get();
        logger.debug("pag :: " + pag);
        return super.ejecutarQueryPaginado(sb, pag);
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Documento> consultarDocumentosAudienciaByTipoForma(
            Long audienciaId, Long tipoForma) {
        StringBuffer queryString = new StringBuffer();
        queryString.append("SELECT d FROM Documento d ")
                .append("WHERE d.resolutivo.audiencia=").append(audienciaId)
                .append(" AND d.forma.tipoForma=").append(tipoForma);
        Query query = super.getSession().createQuery(queryString.toString());
        return query.list();
    }

    @SuppressWarnings("unchecked")
    @Override
    public Documento consultarDocumentoPorDocumentoIdLigero(Long documentoId) {

        StringBuffer queryString = new StringBuffer();
        queryString
                .append("SELECT new Documento(d.documentoId, d.nombreDocumento, d.forma.formaId, d.tipoDocumento.valorId, d.tipoDocumento.valor, d.fechaCreacion) ");

        queryString.append("FROM Documento d ").append("WHERE d.documentoId=")
                .append(documentoId);

        logger.info("/***** " + queryString.toString());
        Query query = super.getSession().createQuery(queryString.toString());
        List<Documento> temp = query.list();
        if (temp != null && !temp.isEmpty()) {
            return temp.get(0);
        }
        return null;
    }

    @Override
    public Documento consultarDocumentoPorId(Long documentoId) {
        StringBuffer queryString = new StringBuffer();
        queryString.append("FROM Documento d ").append("WHERE d.documentoId=")
                .append(documentoId);

        Query query = super.getSession().createQuery(queryString.toString());
        return (Documento) query.uniqueResult();
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Documento> consultarDocumentosXTipoDocumento(
            Long idTipoDocumento) {
        StringBuffer queryString = new StringBuffer();

        queryString
                .append("SELECT new Documento(d.documentoId, d.tipoDocumento,")
                .append(" d.archivoDigital, d.nombreDocumento,")
                .append(" d.fechaCreacion, d.responsableDocumento,")
                .append(" d.actividad, d.esEnviado)");
        queryString.append(" FROM Documento d");
        queryString.append(" WHERE d.tipoDocumento=" + idTipoDocumento);

        Query query = super.getSession().createQuery(queryString.toString());
        return query.list();
    }

	@Override
	public String obtenerUltimoFolioDocumento(ConfInstitucion institucion)
			throws NSJPNegocioException {

		final StringBuffer query = new StringBuffer();

		query.append(" SELECT ");
		query.append("	MAX ( ");
		query.append("		SUBSTRING(d.folioDocumento,	");
		query.append("			(LEN(d.folioDocumento) 	")
									.append(SIGNO_MENOS)
									.append(ConsecutivosUtil.LONGITUD_CONSECUTIVO_SOLICITUD_MENOS_UNO)
						.append("),");
		query.append(ConsecutivosUtil.LONGITUD_CONSECUTIVO_SOLICITUD);
		query.append("		) ");
		query.append("	)");
		query.append(" FROM Documento d 				");
		query.append(" WHERE d.folioDocumento IS NOT NULL ");
		query.append(" AND d.confInstitucion.esInstalacionActual = true  ");
		query.append(" AND d.folioDocumento like '"
				+ institucion.getMonograma()
				+ ConsecutivosUtil.SEPARADOR_PREFIJOS
				+ Calendar.getInstance().get(Calendar.YEAR) + "%'");

		Query hbq = super.getSession().createQuery(query.toString());
		return (String) hbq.uniqueResult();
	}

    @SuppressWarnings("unchecked")
	@Override
    public List<Documento> consultarDocumentosByExpedienteIdYForma(
            Long expedienteId, Formas tipoforma) {

        StringBuffer queryString = new StringBuffer();

        queryString.append("SELECT new Documento(d.documentoId)")
                .append(" FROM Documento d LEFT JOIN d.actividad da")
                .append(" LEFT JOIN da.expediente e").append(" WHERE 1=1");
        if (expedienteId != null) {
            queryString.append(" AND e.expedienteId=").append(expedienteId);
        }
        if (tipoforma != null) {
            queryString.append(" AND d.forma.formaId=").append(
                    tipoforma.getValorId());
        }
        queryString.append(" AND da.documento is not null ").append(
                " AND d.archivoDigital is not null");
        logger.info("queryString :: " + queryString.toString());

        Query query = super.getSession().createQuery(queryString.toString());
        return query.list();

    }

    @Override
    public Documento consultarDocumentoPorArchivoDigital(Long archivoDigitalId){

        StringBuffer queryString = new StringBuffer();
        //queryString.append("SELECT d ")
          //      .append("FROM Documento d LEFT JOIN d.actividad da")
            //    .append(" WHERE da.documento is not null ")                
              //  .append("AND d.archivoDigital=").append(archivoDigitalId);                                
     
        
        queryString.append("SELECT d ")
            .append("FROM Documento d" )
            .append(" INNER JOIN d.actividad da ")
            .append(" INNER JOIN d.archivoDigital dd ")            
            .append(" WHERE da.documento is not null ")                      
            .append("AND d.archivoDigital=").append(archivoDigitalId);                                

        Query query = super.getSession().createQuery(queryString.toString());
        return (Documento)query.uniqueResult();

    }



    @SuppressWarnings({"unchecked"})
    @Override
    public List<Documento> consultarDocumentosReinsercionSocial(Funcionario funcionario, 
    		Documento documento,
    		NumeroExpediente numeroExpediente)  throws NSJPNegocioException {
        if (logger.isDebugEnabled()) {
            logger.debug("ConsultarDocumentosPorUsuario = " + funcionario);
        }
        
        StringBuffer  strQuery = new StringBuffer();
        strQuery.append(" SELECT docs FROM Documento docs ")
                .append(" INNER JOIN docs.actividad da ")
                .append(" INNER JOIN da.expediente e ")
                .append(" INNER JOIN e.numeroExpedientes ne ")
                .append(" WHERE docs.responsableDocumento = ")
                .append(" " + funcionario.getClaveFuncionario() + " ")
                .append(" AND ne.numeroExpedienteId = ")
                .append(" " + numeroExpediente.getNumeroExpedienteId() + " ")
                .append(" AND docs.documentoId NOT IN ( ")
                .append(" SELECT sol.documentoId FROM Solicitud sol ")
                .append(" ) ");
        // valida por el rolActivo
        if(funcionario.getUsuario() != null
        		&& funcionario.getUsuario().getUsuarioRoles() != null
        		&& !funcionario.getUsuario().getUsuarioRoles().isEmpty()){
        	
        	Set<UsuarioRol> lstRoles = funcionario.getUsuario().getUsuarioRoles();        	
        	Rol rolActivo =  null;
        	for (UsuarioRol usuarioRol : lstRoles) {
				if(usuarioRol.getEsPrincipal()) {
				 rolActivo = usuarioRol.getRol();	
				 break;
				}
			}
        	if(rolActivo != null){
		        strQuery.append(" AND da.tipoActividad IN ( ")
		        		.append(" SELECT fd.valorId FROM Rol r ")
		        		.append(" LEFT JOIN r.facultadDocumentos fd WHERE r.rolId =  ")
		        		.append(rolActivo.getRolId())
		        		.append(" ) ");
        	}
        }
        
                //.append("AND docs.tipoDocumento = ").append(documento.getTipoDocumento());
                
        final PaginacionDTO pag = PaginacionThreadHolder.get();
        if (pag != null && pag.getCampoOrd() != null && !pag.getCampoOrd().isEmpty()) {
        	strQuery.append(" ORDER BY ");
        	strQuery.append(pag.getCampoOrd());
        	strQuery.append(" ").append(pag.getDirOrd());
        }

        return ejecutarQueryPaginado(strQuery, pag);
    }
    
    @SuppressWarnings("unchecked")
	@Override
	public List<Documento> consultarDocumentosPorFolioInstitucion(String folio,
			Long institucionOrigen) {
    	if(folio==null && institucionOrigen==null){
    		return null;
    	}
		StringBuilder queryString = new StringBuilder();
		queryString.append("SELECT d FROM Documento d WHERE 1=1 ");

		if (folio != null && !folio.trim().isEmpty()) {
			queryString.append(" AND d.folioDocumento = '").append(folio)
					.append("'");
		}
		if (institucionOrigen != null && institucionOrigen > 0) {
			queryString.append(" AND d.confInstitucion.confInstitucionId = '")
					.append(institucionOrigen).append("'");
		}
		Query query = super.getSession().createQuery(queryString.toString());
		return (List<Documento>) query.list();
	}
    
    
    @Override
    public Documento consultarDocumentoFiltro(Documento filtro) throws NSJPNegocioException  {
    	try {
	        StringBuffer queryString = new StringBuffer();
			queryString.append(" SELECT d FROM Documento d ");
			if (filtro.getActividad() != null 
					|| filtro.getExpediente() != null ) {
				queryString.append(" INNER JOIN d.actividad da ");
			}
			
			if(filtro.getExpediente() != null || 
					(filtro.getActividad() != null 
							&& filtro.getActividad().getExpediente() != null
							&& filtro.getActividad().getExpediente().getExpedienteId() != null
							&& filtro.getActividad().getExpediente().getExpedienteId() > 0L)) {
				queryString.append(" INNER JOIN da.expediente e ");
			}
			
			if(filtro.getExpediente() != null 
					&& filtro.getExpediente().getNumeroExpedienteId() != null ) {		
				queryString.append(" INNER JOIN e.numeroExpedientes ne ");
			}
			
			queryString.append(" WHERE 1 =1 ");
	
			if (filtro.getActividad() != null) {
				if(filtro.getActividad().getTipoActividad() != null) {
					queryString.append(" AND da.tipoActividad.valorId = ");
					queryString.append(filtro.getActividad().getTipoActividad().getValorId());
				}
			}
			
			if(filtro.getExpediente() != null 
					&& filtro.getExpediente().getNumeroExpedienteId() != null ) {
				
				queryString.append(" AND  ne.numeroExpedienteId in = ");
				 
				queryString.append(" ").append(filtro.getExpediente().getNumeroExpedienteId()).append(" ");
			}
			
			if (filtro.getActividad() != null 
					&& filtro.getActividad().getExpediente() != null
					&& filtro.getActividad().getExpediente().getExpedienteId() != null
					&& filtro.getActividad().getExpediente().getExpedienteId() > 0L){
				
				queryString.append(" AND  da.expediente.expedienteId = ");
				queryString.append(filtro.getActividad().getExpediente().getExpedienteId() + " ");
			}
			
	        Query query = super.getSession().createQuery(queryString.toString());
	        return (Documento) query.uniqueResult();
    	} catch(Exception e) {
    		logger.error(e.getMessage(), e);
    		throw new NSJPNegocioException(CodigoError.INFORMACION_PARAMETROS_ERRONEA, e);  
    	}
    }

	@SuppressWarnings("unchecked")
	@Override
	public List<Documento> consultarDocumentosPorTipoActividadYNumExpedienteId(
			Long numExpId, Long tipoActividad) throws NSJPNegocioException {

		final PaginacionDTO pag = PaginacionThreadHolder.get();
		
		StringBuffer queryString = new StringBuffer();
		queryString
				.append(" SELECT d FROM Documento d INNER JOIN d.actividad da INNER JOIN da.expediente e INNER JOIN e.numeroExpedientes ne");
		queryString.append(" WHERE da.tipoActividad.valorId = ");
		queryString.append(tipoActividad);
		queryString.append(" AND  ne.numeroExpedienteId = ");
		queryString.append(numExpId);
		queryString.append(" AND d.archivoDigital.archivoDigitalId IS NOT NULL ");
		queryString.append(" ORDER BY d.fechaCreacion DESC ");

		return ejecutarQueryPaginado(queryString, pag);
	}

	/* (non-Javadoc)
	 * @see mx.gob.segob.nsjp.dao.documento.DocumentoDAO#consultarDocumentosPorExpedienteYTipos(mx.gob.segob.nsjp.model.Expediente, java.util.List)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<Documento> consultarDocumentosPorExpedienteYTipos(
			Expediente expediente, List<Valor> tiposDocumento)
			throws NSJPNegocioException {
		
		if (expediente == null
				||expediente.getExpedienteId() == null
				|| expediente.getExpedienteId() < 1L
				|| tiposDocumento == null
				|| tiposDocumento.isEmpty()){
			throw new NSJPNegocioException(CodigoError.PARAMETROS_INSUFICIENTES);
		}
		
		StringBuffer queryString = new StringBuffer("");
		queryString.append(" SELECT d ");
		queryString.append(" FROM Documento d ");
		queryString.append(" LEFT JOIN d.actividad a ");
		queryString.append(" WHERE a.expediente.expedienteId = ");
		queryString.append(expediente.getExpedienteId());
		queryString.append(" AND d.tipoDocumento.valorId IN ( ");
		for (int i=0; i<tiposDocumento.size(); i++){
			if (i > 0){
				queryString.append(", ");
			}
			queryString.append(tiposDocumento.get(i).getValorId());
		}
		queryString.append(" ) ");
		return ejecutarQueryPaginado(queryString, PaginacionThreadHolder.get());
	}

	/* (non-Javadoc)
	 * @see mx.gob.segob.nsjp.dao.documento.DocumentoDAO#consultarDocumentosPorArchivosDigitales(java.util.List)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<Documento> consultarDocumentosPorArchivosDigitales(
			List<Long> lstArchivoDigitalId) throws NSJPNegocioException {
		
		if (lstArchivoDigitalId == null || lstArchivoDigitalId.isEmpty()){
			throw new NSJPNegocioException(CodigoError.PARAMETROS_INSUFICIENTES);
		}
		
		StringBuilder sb = new StringBuilder("");
		sb.append(" SELECT d ");
		sb.append(" FROM Documento d ");
		sb.append(" WHERE d.archivoDigital.archivoDigitalId IN (:lstIds) ");
		sb.append(" ORDER BY d.documentoId ");
		Query query = getSession().createQuery(sb.toString());
		query.setParameterList("lstIds", lstArchivoDigitalId);
		return (List<Documento>) query.list();
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Documento> consultarDocumentosPorTipoActividadYExpedienteId(
			Long expedienteId, Long tipoActividad) throws NSJPNegocioException {
		
		if(expedienteId == null || expedienteId <= 0 || tipoActividad == null || tipoActividad <= 0){
			throw new NSJPNegocioException(CodigoError.PARAMETROS_INSUFICIENTES);
		}

		StringBuffer queryString = new StringBuffer();
		queryString.append(" SELECT d FROM Documento d INNER JOIN d.actividad da");
		queryString.append(" WHERE da.tipoActividad.valorId = ");
		queryString.append(tipoActividad);
		queryString.append(" AND  da.expediente.expedienteId = ");
		queryString.append(expedienteId);
		
		Query query = getSession().createQuery(queryString.toString());
		return (List<Documento>)query.list();
	}
    
}
