/**
 * Nombre del Programa : ArchivoDigitalDAOImpl.java
 * Autor                            : vaguirre
 * Compania                    : Ultrasist
 * Proyecto                      : NSJP                    Fecha: 18 May 2011
 * Marca de cambio        : N/A
 * Descripcion General    : Implementaci�n para accesar a la entidad archivo digital
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
package mx.gob.segob.nsjp.dao.archivo.impl;

import java.util.List;

import mx.gob.segob.nsjp.comun.enums.actividad.Actividades;
import mx.gob.segob.nsjp.comun.excepcion.NSJPNegocioException;
import mx.gob.segob.nsjp.dao.archivo.ArchivoDigitalDAO;
import mx.gob.segob.nsjp.dao.base.impl.GenericDaoHibernateImpl;
import mx.gob.segob.nsjp.dto.catalogo.ValorDTO;
import mx.gob.segob.nsjp.model.ArchivoDigital;

import org.hibernate.Query;
import org.springframework.stereotype.Repository;

/**
 * Implementaci�n para accesar a la entidad archivo digital.
 * 
 * @version 1.0
 * @author vaguirre
 * 
 */
@Repository
public class ArchivoDigitalDAOImpl
        extends
            GenericDaoHibernateImpl<ArchivoDigital, Long>
        implements
            ArchivoDigitalDAO {
	
	private final int RANGO_ARCHIVOS=50;
	
	/*
	 * (non-Javadoc)
	 * @see mx.gob.segob.nsjp.dao.archivo.ArchivoDigitalDAO#consultarContenidoPorDocumentoOArchivo(java.lang.Long, boolean)
	 */
	@SuppressWarnings("rawtypes")
	@Override
	public byte[] consultarContenidoPorDocumentoOArchivo(Long id,
			boolean esArchivo) throws NSJPNegocioException{
		String query = null;
		byte []contenido = null;		
		if(esArchivo){
			query = "select archivo.contenido from ArchivoDigital archivo where archivo.archivoDigitalId = ?";
		}else{
			query = "select doc.archivoDigital.contenido from Documento doc where doc.documentoId = ?";
		}
		
		List res = getHibernateTemplate().find(query,id);
		if(res != null && !res.isEmpty() && res.get(0)!=null){
			contenido = (byte[])res.get(0);
		}
		
		return contenido;
	}

	@Override
	public ArchivoDigital consultarArchivoDigitalPorDocumento(Long idDocumento) throws NSJPNegocioException{
	
		final StringBuffer hqlQuery = new StringBuffer();
		
		hqlQuery.append(" SELECT new ArchivoDigital(d.archivoDigital.archivoDigitalId, d.archivoDigital.nombreArchivo, d.archivoDigital.tipoArchivo, d.archivoDigital.contenido, d.archivoDigital.esEnviadoAOtraInstitucion, d.archivoDigital.rutaArchivo) ");
		hqlQuery.append(" FROM Documento d");
		hqlQuery.append(" WHERE d.documentoId="+idDocumento);

		Query hbq = super.getSession().createQuery(hqlQuery.toString());
		return (ArchivoDigital) hbq.uniqueResult();
	}

	public ArchivoDigital consultarArchivoDitalSinContenidoPorActividad(Long idExpediente,
			final Actividades actividad) throws NSJPNegocioException {
		final StringBuffer hqlQuery = new StringBuffer();
		hqlQuery.append("select new ArchivoDigital(a.archivoDigitalId, a.nombreArchivo, a.tipoArchivo) ");
		hqlQuery.append("from ArchivoDigital a ");
		hqlQuery.append("where a.documento.actividads.tipoActividad = "+actividad.getValorId()+" ");
		hqlQuery.append("and a.documento.expediente.expedienteId= "+idExpediente);

		Query hbq = super.getSession().createQuery(hqlQuery.toString());
		return (ArchivoDigital) hbq.uniqueResult();

	}	
	
	@Override
	public ArchivoDigital consultarArchivoDigitalXElementoId(Long idElemento)
			throws NSJPNegocioException {
		if (idElemento == null || idElemento <= 0L) {
			return null;
		}

		final StringBuffer hqlQuery = new StringBuffer();
		hqlQuery.append("select a ");
		hqlQuery.append(" from ArchivoDigital a");
		hqlQuery.append(" where a.archivoDigitalId = ");
		hqlQuery.append("(select e.archivoDigital.archivoDigitalId FROM Elemento e where e.elementoId= "
				+ idElemento + ")");

		Query hbq = super.getSession().createQuery(hqlQuery.toString());
		return (ArchivoDigital) hbq.uniqueResult();
	}

	@Override
	public boolean actualizarColumnaDeEnvioAOtraInstitucion(
			List<Long> idsArchivosDigitales) throws NSJPNegocioException {
		StringBuffer hqlQuery = new StringBuffer();
        hqlQuery.append(" UPDATE ArchivoDigital SET esEnviadoAOtraInstitucion = 1 WHERE archivoDigitalId IN (:listaIdentificadores) ");
        
        int  res = super.getSession().createQuery(hqlQuery.toString()).
        							  setParameterList("listaIdentificadores", idsArchivosDigitales).
        							  executeUpdate();
        if(res == 0){
        	return Boolean.FALSE;
        }
        return Boolean.TRUE;
	}

	@Override
	public ArchivoDigital leeIdIdentificador(int operadorMinMax)
			throws NSJPNegocioException {
		final StringBuffer hqlQuery = new StringBuffer();
		hqlQuery.append(" from ArchivoDigital a");
		switch (operadorMinMax) {
		case 0:
			hqlQuery.append(" WHERE a.archivoDigitalId= (Select MIN(ad.archivoDigitalId) FROM ArchivoDigital ad) ");
			break;

		default:
			hqlQuery.append(" WHERE a.archivoDigitalId= (Select MAX(ad.archivoDigitalId) FROM ArchivoDigital ad) ");
			break;
		}
		Query hbq = super.getSession().createQuery(hqlQuery.toString());
		return (ArchivoDigital) hbq.uniqueResult();
	}
	
	@Override
	public List<ArchivoDigital> leeRangosArchivosDigitales(Long inicio)
			throws NSJPNegocioException {
		final StringBuffer hqlQuery = new StringBuffer();
//		hqlQuery.append(" Select TOP("+RANGO_ARCHIVOS+") a ");
		hqlQuery.append(" from ArchivoDigital a");
		hqlQuery.append(" WHERE a.archivoDigitalId >= "+inicio);
		hqlQuery.append(" ORDER BY a.archivoDigitalId ASC ");
		Query hbq = super.getSession().createQuery(hqlQuery.toString());
		hbq.setMaxResults(RANGO_ARCHIVOS);
		return hbq.list();
	}
	
	public void modificaArchivosDigitales(List<ValorDTO> identificadorRutaArchivoDigital)
			throws NSJPNegocioException {
		for (ValorDTO valorDTO : identificadorRutaArchivoDigital) {
			final StringBuffer hqlQuery = new StringBuffer();
			hqlQuery.append(" UPDATE ArchivoDigital a ");
			hqlQuery.append(" SET a.contenido=null, a.rutaArchivo='"+valorDTO.getValor()+"'");
			hqlQuery.append(" WHERE a.archivoDigitalId = "+valorDTO.getIdCampo());
			Query hbq = super.getSession().createQuery(hqlQuery.toString());
			hbq.executeUpdate();
		}
	}

	@Override
	public void eliminaArchivoDigitalPorDocumentoId(Long idDocumento)throws NSJPNegocioException {
		ArchivoDigital archivoDigital= consultarArchivoDigitalPorDocumento(idDocumento);
		this.delete(archivoDigital);

	}

}
