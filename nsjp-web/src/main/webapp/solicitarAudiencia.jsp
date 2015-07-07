<%@ taglib uri="/WEB-INF/tld/struts-bean.tld" prefix="bean"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@page import="mx.gob.segob.nsjp.comun.enums.institucion.Instituciones"%>
<%@page import="mx.gob.segob.nsjp.comun.enums.documento.TipoDocumento"%>
<%@ page import="mx.gob.segob.nsjp.comun.enums.calidad.Calidades" %>
<%@ page import="mx.gob.segob.nsjp.dto.configuracion.ConfiguracionDTO"%>
<%@ page import="mx.gob.segob.nsjp.web.base.action.GenericAction"%>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
        <title>Solicitar Audiencia</title>
        <link rel="stylesheet" type="text/css" href="<%= request.getContextPath()%>/resources/css/jquery-ui.css" />
        <link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/resources/css/estilos.css" media="screen" />
        <link rel="stylesheet" type="text/css" href="<%= request.getContextPath()%>/resources/css/ui-lightness/jquery-ui-1.8.11.custom.css" />
        <link rel="stylesheet" type="text/css" href="<%= request.getContextPath()%>/resources/css/jquery.timeentry.css"/>
        <link rel="stylesheet" type="text/css" media="screen" href="<%=request.getContextPath()%>/resources/css/jqgrid/ui.jqgrid.css" />
        <link rel="stylesheet" type="text/css" href="<%= request.getContextPath() %>/resources/css/jquery.windows-engine.css"/>
        <script type="text/javascript" src="<%= request.getContextPath()%>/resources/js/jquery-1.5.1.js"></script>
        <script type="text/javascript" src="<%= request.getContextPath()%>/resources/js/jquery-ui-1.8.10.custom.js"></script>
        <script type="text/javascript" 	src="<%=request.getContextPath()%>/resources/js/jqgrid/i18n/grid.locale-es.js"></script>
        <script type="text/javascript" 	src="<%=request.getContextPath()%>/resources/js/jqgrid/jquery.jqGrid.js"></script>
        <link rel="stylesheet" type="text/css" href="<%= request.getContextPath()%>/resources/css/jquery.timeentry.css"/>
        <script type="text/javascript" src="<%= request.getContextPath()%>/resources/js/jquery.timeentry.js"></script>
        <script type="text/javascript" src="<%= request.getContextPath()%>/resources/js/wdCalendar/Plugins/jquery.ui.datepicker-es.js"></script>
        <script type="text/javascript" 	src="<%=request.getContextPath()%>/js/comun.js?n=1"></script>
		<script type="text/javascript" src="<%= request.getContextPath()%>/resources/js/jquery.blockUI.js"></script>	        
        <script type="text/javascript" 	src="<%=request.getContextPath()%>/js/solicitarAudiencia.js"></script>
        <script type="text/javascript" src="<%= request.getContextPath() %>/resources/js/jquery.windows-engine.js"></script>
        
        <script type="text/javascript" src="<%=request.getContextPath()%>/js/bloqueaTecla.js?n=1"></script>
        
        <script type="text/javascript">
       
 		 // El codigo javascript de este jsp se encuentra en solicitarAudiencia.js
         var CONTEXT_ROOT = '<%= request.getContextPath()%>';
         var idEspedienteSoli='<%= request.getParameter("idExpedienteSoli")%>';
         var idNumeroExpediente='<%= request.getParameter("idNumeroExpediente")%>';
         var distritoId=0;
         var PROBABLE_RESPONSABLE_PERSONA =  '<%= Calidades.PROBABLE_RESPONSABLE_PERSONA.getValorId() %>';
         var PROBABLE_RESPONSABLE_ORGANIZACION =  '<%= Calidades.PROBABLE_RESPONSABLE_ORGANIZACION.getValorId() %>';
         var VICTIMA_PERSONA =  '<%= Calidades.VICTIMA_PERSONA.getValorId() %>';
         var DENUNCIANTE =  '<%= Calidades.DENUNCIANTE.getValorId() %>';
         var probableResponsableString = '';
         var institucionDestinoDocumento = '<%= Instituciones.PJ.getValorId() %>';
         var idDocumentoAdjunto = '';
         var extensionesPermitidasAudio = '<%=((ConfiguracionDTO) request.getSession().getAttribute(GenericAction.KEY_SESSION_CONFIGURACION_GLOBAL)).getExtensionesPermitidasAlAdjuntarAudio() %>';
 		 var extensionesPermitidasImagen = '<%=((ConfiguracionDTO) request.getSession().getAttribute(GenericAction.KEY_SESSION_CONFIGURACION_GLOBAL)).getExtensionesPermitidasAlAdjuntarImagen() %>';
 		 var tipoDocumentoSolicitud = '<%=TipoDocumento.SOLICITUD_DE_AUDIENCIA.getValorId()%>';

         var contextoPagina = "${pageContext.request.contextPath}";
         
         $(document).ready(function() {
        	probableResponsableString = '<bean:message key="probableResponsable"/>';
        	jQuery(document).ajaxStop(jQuery.unblockUI);
         	consultaDatosUsuario();
			$('#btnEnviarSolicitud').click(enviarSolicitudAudiencia);
			$('#btnAdjuntarDocumento').click(ventanaDocumentosAdjuntosASolicitudAudiencia);
			
         });
         
         /*
         *Funcion para traer la fecha y hora del servidor en el formato : YYYY-MM-DD HH:MI:SS
         */
         function consultaFechaHoraMaximaServer()
         {
         	var fecha="";
         	   $.ajax({
         		     type: 'POST',
         		     url: '<%=request.getContextPath()%>/regresaFechaYHoraDelServidor.do',
         			 dataType: 'xml',
         			 async: false,
         			 success: function(xml){
         				fecha= $(xml).find('fecha').text();
         			  }
         			});
         	return fecha;
         }

	        /*
			*Funcion que consulta los datos de la solicitud, nombre del usuario solicitante, puesto y area administrativa
			*/
			function consultaDatosUsuario(){

				$.ajax({
					type: 'POST',
				    url: '<%=request.getContextPath()%>/consultarDatosUsuario.do?idSolAUD=1',
				    data: '',
				    dataType: 'xml',
				    async: false,
				    success: function(xml){
				    	var errorCode;
				    	
						errorCode=$(xml).find('response').find('code').text();				
						if(parseInt(errorCode)==0){
							//Puesto del usuario solicitante
							$('#institucionSolicitanteSolicitud').val($(xml).find('institucion').find('nombreInst').first().text());

							//Distrito del usuario solicitante
							var catDistritoId = $(xml).find('discriminante').find('distrito').find('catDistritoId').first().text();
							var claveDistrito = $(xml).find('discriminante').find('distrito').find('claveDistrito').first().text();
							var nombreDistrito = $(xml).find('discriminante').find('distrito').find('nombreDist').first().text();

							//Se llena el campo del distrito
							$('#distrito').append('<option value="' + catDistritoId + '">' + claveDistrito+"-"+nombreDistrito+ '</option>');							
							//Invocamos la carga de tribuales por el id del distrito
							distritoId = $(xml).find('discriminante').find('distrito').find('catDistritoId').first().text();
							consultarTribunalesXDistrito(distritoId);
			
						}
					}
				});
			}

			 /*
			*Funcion que consulta los tribunales asignadas a un Distrito
			*/
			function consultarTribunalesXDistrito(distritoId){
				
				$.ajax({
					type: 'POST',
				    url: '<%=request.getContextPath()%>/consultarTribunalesXDistritoId.do?distritoId='+distritoId+'',
				    data: '',
				    dataType: 'xml',
				    async: false,
				    success: function(xml){

					    	var contTribunales=0;
					    	$(xml).find('listaDiscriminantes').find('catDiscriminante').each(function(){
								$('#tribunal').append('<option value="' + $(this).find('catDiscriminanteId').text() + '">' + $(this).find('clave').text()+"-"+ $(this).find('nombre').text() + '</option>');
								contTribunales++;
							});
							if(contTribunales == 0){
								customAlert("No existen tribunales asignados a este distrito","Aviso:");
								limpiaComboDestinatario();
							}else{
								$("#tribunal").change(function(e){
									limpiaComboDestinatario();
									cargaDestinatario();
								});
							}
					}
				});
			}

	    /**
	    *Limpia el combo de destinatarios
	    */
		function limpiaComboDestinatario(){
			$('#funcionarioDestinatario').empty();
			$('#funcionarioDestinatario').append('<option value="-1">-Seleccione-</option>');	//Por default seleccione
		}

	    /*
	    *Hash map para almacenar en vista los nombres de los funcionarios destinatarios externos
	    */
	    var hashNombreCompletoDestinatarioInstExterna = new Object();
	    
		 /*
		*Funcion que consulta los funcionarios que recibir�n la solicitud
		* asignados a ese tribunal
		*/
		 function cargaDestinatario(){

			 var tribunalId = $("#tribunal option:selected").val();
			 
			 $.ajax({
					type: 'POST',
				    url: '<%=request.getContextPath()%>/consultarDestinararioXTribunal.do?tribunalId='+tribunalId+'',
				    data: '',
				    dataType: 'xml',
				    async: false,
				    success: function(xml){
					    
					    var contFuncionario=0;
				    	$(xml).find('listaFuncionarios').find('funcionario').each(function(){

							var nombre = $(this).find('nombreFuncionario').first().text();
							var apPat = $(this).find('apellidoPaternoFuncionario').first().text();
							var apMat = $(this).find('apellidoMaternoFuncionario').first().text();
							
					    	$('#funcionarioDestinatario').append('<option value="' + $(this).find('claveFuncionario').text() + '">'+nombre+" "+apPat+" "+apMat+'</option>');
					    	hashNombreCompletoDestinatarioInstExterna[$(this).find('claveFuncionario').text()] = nombre+":"+apPat+":"+apMat;
					    	contFuncionario++;
				    	});
				    	if(contFuncionario==0){
				    		customAlert("Por favor seleccione otro tribunal para la audiencia","Aviso:");
					    }
					}
				});
		}

		//Variable para controlar la carga del grid de imputados
		var firstGridImputados = true;

		 /*
		 *Carga el grid de los imputados del expediente
		 */
		 function gridImputados(){

			 var numExp = obtenParametroDeUrl("numeroExpediente");
			 var tituloGridImputados = '<bean:message key="solicitarAudiencia.CaptionGridProbResp"/>';
		 		
		 	if(firstGridImputados){
			 	
		 		jQuery("#gridImputadosSolicitudDeAudiencia").jqGrid({
		 			url:'<%=request.getContextPath()%>/consultarProbResParaSolicitarAudiencia.do?numeroExpediente='+numExp+'',
		 			datatype: "xml",
		 			colNames:['Nombre'],
		 			colModel:[ 	{name:'Nombre',	index:'1',	width:200,	align:'center'} 
		 			 		],
		 			pager: jQuery('#pagerGridImputadosSolicitudDeAudiencia'),
		 			rowNum:10,
		 			rowList:[10,20,30,40],
		 			width:560,
		 			sortname: 'Nombre',
		 			viewrecords: true,
		 			multiselect: true,
		 			sortorder: "desc",
		 			async:true,
		 			caption:tituloGridImputados,
		 			onSelectRow: function(id){
		 				gridRelacionesDelitoPersonaPorProbResp(id);
					},
		 			gridComplete: function () {
		 				seleccionarItems($(this));
		 			},
		 			onPaging: function (a) {
		 				guardarItemsSeleccionados($(this)); 
		 			},
		 			onSortCol: function(){
		 				eliminarItemsSeleccionados($(this));
		 			}
		 		});
		 		$("#gview_gridImputadosSolicitudDeAudiencia .ui-jqgrid-bdiv").css('height', '110px');	 		
		 		firstGridImputados=false;
		 	}else{														
		 		jQuery("#gridImputadosSolicitudDeAudiencia").jqGrid('setGridParam', {url:'<%=request.getContextPath()%>/consultarProbResParaSolicitarAudiencia.do?numeroExpediente='+numExp+'',datatype: "xml"});
		 		$("#gridImputadosSolicitudDeAudiencia").trigger("reloadGrid");				
		 	}
		 }


		//Variable para controlar la carga del grid de imputados
			var firstReDelPer = true;

			 /*
			 *Carga el grid de los imputados del expediente
			 */
			 function gridRelacionesDelitoPersonaPorProbResp(probRespId){
				 			 		
			 	if(firstReDelPer){
			 		jQuery("#gridRelDelPerXImputadoSolicitudDeAudiencia").jqGrid({
			 			url:'<%= request.getContextPath()%>/limpiarGrid.do',
			 			datatype: "xml",
			 			colNames:['Nombre','Delito','Victima'],
			 			colModel:[ 	{name:'Nombre',	index:'nombre',	width:200,	align:'center'},
			 						{name:'Delito',	index:'delito',	width:200,	align:'center'},
			 						{name:'Victima',index:'victima',width:200,	align:'center'} 
			 			 		],
			 			pager: jQuery('#pagerGridRelDelPerXImputadoSolicitudDeAudiencia'),
			 			rowNum:10,
			 			rowList:[10,20,30,40],
			 			width:560,
			 			sortname: 'Nombre',
			 			viewrecords: true,
			 			sortorder: "desc",
			 			async:true,
			 		});
			 		$("#gview_gridRelDelPerXImputadoSolicitudDeAudiencia .ui-jqgrid-bdiv").css('height', '110px');	 		
			 		firstReDelPer=false;
			 	}else{														
			 		jQuery("#gridRelDelPerXImputadoSolicitudDeAudiencia").jqGrid('setGridParam', {url:'<%=request.getContextPath()%>/consultarRelDelitoPersonaPorProbableResponsable.do?idExpediente='+idEspedienteSoli+'&probRespId='+probRespId+'',datatype: "xml"});
			 		$("#gridRelDelPerXImputadoSolicitudDeAudiencia").trigger("reloadGrid");				
			 	}
			 }
			 
			 function setIdDocumentoAdjunto(id){
				 idDocumentoAdjunto = id;
			 }
			
        </script>
    </head>
    
	<body>
        <div id="tabprincipal">
            <ul>
                <li></li>
            </ul>
            <div id="datosSolicitud">
				<table cellspacing="5" border="0">
					<tr>
						<td colspan="5" align="right">
							<input type="button" id="btnAdjuntarDocumento" value="Adjuntar documentos"  class="back_button"/>
						</td>
						<td align="right">
							<input type="button" id="btnEnviarSolicitud" value="Enviar Solicitud" class="back_button"/>
						</td>
					</tr>
					<tr>
						<td width="189" style="vertical-align: top">
							N&uacute;mero de caso:
						</td>
						<td width="199" style="vertical-align: top;">
							<input id="numeroDeCaso" type="text" maxlength="100" class="parametro" style="width: 100%"/>
						</td>
						<td width="135" style="vertical-align: top">
							Hora de solicitud:
						</td>
						<td width="180" style="vertical-align: top">
							<span id="horaSolicitud"></span>
						</td>
						<td width="90" style="vertical-align: top">
							Distrito:
						</td>
						<td width="169" style="vertical-align: top">						
							<select id="distrito" class="parametro" style="width: 100%"></select>
						</td>
					</tr>
					<tr>
						<td style="vertical-align: top">
							Tipo de audiencia:
						</td>
						<td style="vertical-align: top">
							<select id="tipoDeAudiencia" class="parametro" style="width: 100%">
								<option value="-1">-Seleccione-</option>
							</select>
						</td>
						<td style="vertical-align: top">
							Fecha de la solicitud:
						</td>
						<td style="vertical-align: top">
							<span id="fechaSolicitud"></span>
						</td>
						<td style="vertical-align: top">
							Tribunal:
						</td>
						<td style="vertical-align: top">
							<select id="tribunal" class="parametro" style="width: 100%">
								<option value="-1">-Seleccione-</option>
							</select>
						</td>
					</tr>
					<tr>
						<td>
							Fecha l&iacute;mite para la audiencia:
						</td>
						<td>
							<input id="fechaLimiteAudiencia" type="text" size="20" readonly="true" class="parametro"/>
						</td>
						<td style="vertical-align: top">
							Instituci&oacute;n solicitante:
						</td>
						<td style="vertical-align: top">
							<input id="institucionSolicitanteSolicitud" type="text" maxlength="100" class="parametro" style="width: 100%" readonly="readonly"/>
						</td>
						<td style="vertical-align: top">
							Destinatario:
						</td>
						<td style="vertical-align: top">
							<select id="funcionarioDestinatario" class="parametro" style="width: 100%">
								<option value="-1">-Seleccione-</option>
							</select>
						</td>
					</tr>
					<tr>
						<td>
							Hora l&iacute;mite de la audiencia solicitada:
						</td>
						<td>
							<input type="text" id="horaLimiteAudiencia" size="10" class="timeRange parametro" value="00:00"/>
						</td>
						<td style="vertical-align: top;">
							Nombre del solicitante:		</td>
						<td style="vertical-align: top">
							<input id="nombreDelSolicitante" type="text" maxlength="100" class="parametro" style="width: 100%"/>
						</td>
						<td style="vertical-align: top">&nbsp;</td>
						<td style="vertical-align: top">&nbsp;</td>
					</tr>
					<tr>
						<td style="vertical-align: top" colspan="2">Fundamento de la solicitud:</td>
						<td style="vertical-align: top" colspan="4">
							<!--V�ctimas:-->
							<bean:message key="solicitarAudiencia.LeyedaGridProbResp"/>
						</td>
					</tr>
					<tr>
						<td style="vertical-align: top" colspan="2">
							<textarea cols="" id="fundamentoDeLaSolicitud" rows="8" class="parametro" style="width: 100%"></textarea>
						</td>
						<td style="vertical-align: top" colspan="4" align="right">
							<table id="gridImputadosSolicitudDeAudiencia"></table>
							<div id="pagerGridImputadosSolicitudDeAudiencia" align="center"></div>
						</td>
					</tr>
					<tr>
						<td style="vertical-align: top" colspan="2">
							Situaciones especiales a considerar:		</td>
						<td style="vertical-align: top" colspan="4">
							<bean:message key="solicitarAudiencia.LeyedaGridRelacionesDelitoPersona"/>							
						</td>
					</tr>
					<tr>
						<td colspan="2" style="vertical-align: top">
							<textarea cols="" id="situacionesEspeciales" rows="8" class="parametro" style="width: 100%"></textarea>
						</td>
						<td colspan="4" style="vertical-align: top" align="right">
							<table id="gridRelDelPerXImputadoSolicitudDeAudiencia"></table>
							<div id="pagerGridRelDelPerXImputadoSolicitudDeAudiencia" align="center"></div>
						</td>
					</tr>
				</table>
            </div>
        </div>
	</body>
</html>