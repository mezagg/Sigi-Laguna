<%@page import="mx.gob.segob.nsjp.dto.usuario.UsuarioRolDTO"%>
<%@page import="mx.gob.segob.nsjp.dto.configuracion.ConfInstitucionDTO"%>
<%@page import="mx.gob.segob.nsjp.comun.enums.actividad.ActividadesRS"%>
<%@page import="mx.gob.segob.nsjp.dto.usuario.UsuarioDTO"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<% 
   	UsuarioDTO usuario = (UsuarioDTO) request.getSession().getAttribute("KEY_SESSION_USUARIO_FIRMADO"); 
    UsuarioRolDTO rolActivo = usuario.getRolACtivo();
    String idInstitucionActual = "";
    if (rolActivo != null
    		&& rolActivo.getRol() != null
    		&& rolActivo.getRol().getInstitucionPertenece() != null
    		&& rolActivo.getRol().getInstitucionPertenece().getConfInstitucionId() != null){
    	idInstitucionActual = rolActivo.getRol().getInstitucionPertenece().getConfInstitucionId().toString();
    }
%>
<script type="text/javascript">
	var idInstitucionActual = '<%=idInstitucionActual%>';

	function enviarDocumentosAlJuez(actividadId) {		
		bloquearPantalla();
		jsonParam = 
			{
				"destinatarios"				: obtenIdEInstitucionDestinatario(),
				"documentos" 				: obtenDocAdjuntos(actividadId), 
				"sentenciaId"				: sentenciaId,
		    	"institucionSolicitante" 	: idInstitucionActual,
			    "solicitante" 				: "",
			    "numeroExpediente" 			: numeroUnicoExpediente,
			    "idSolicitud"				: $('#idSolicitud').val(),
			    "idTipoSolicitud"			: idTipoSolicitud,
			    "idSolicitudOriginal"		: solicitudOriginalId
			}
		;

		$.ajax({
			type: 'POST',
			url:  '<%=request.getContextPath()%>/enviarSolicitudOtrasInstituciones.do',
			async: false,
			contentType: "application/json; charset=utf-8",
        	dataType: "json",
        	data: JSON.stringify(jsonParam),
			success: function(json){
				try {
					if (json.exito != undefined && json.exito != "undefined"){
						customAlert(json.exito, "Exito", cerrarCustomVentana);
					} else if (json.error != undefined && json.error != "undefined"){
						customAlert(json.error);
					}
				}catch(e){
					console.error(e);
				}
			}
		});
	}

	function obtenIdEInstitucionDestinatario(){					
		var lista = jQuery("#gridUsuarios").getDataIDs();
		var resultado =[];
		for(var i in lista ) {
			var ret = jQuery("#gridUsuarios").jqGrid('getRowData',lista[i]);
			var destinatario = {
				idFuncionario	: ret.id, 
				instId			: ret.instId,
				areaDestinoId	: ret.areaDestinoId,
				esOtraInst		: ret.esOtraInst,
				nombreCompleto 	: ret.nombre
				};
			resultado.push(destinatario); 
		}
		return resultado;
	}

	function obtenDocAdjuntos(actividadId){
		var docsAdjuntos = [];
		if (actividadId == <%=ActividadesRS.ELABORAR_INFORME_FINAL_DE_REINSERCION_SOCIAL_DEL_SENTENCIADO.getValorId()%>){
			docsAdjuntos = obtenerSeleccionados($("#gridDocumentosDigitalesPropios"));
		}
		docsAdjuntos.push(documentoParcialGuardado);
		
		if (docsAdjuntos.length <=0){
			return false;
		}
		var documentos = [];
		for(var i in docsAdjuntos){
			var doc = {
				idDocumento : docsAdjuntos[i]
			};
			documentos.push(doc);
		}
		return documentos;
	}
	
</script>