<%@page import="mx.gob.segob.nsjp.comun.enums.calidad.Calidades"%>
<%@page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@page import="mx.gob.segob.nsjp.dto.usuario.UsuarioDTO"%>
<%@page import="mx.gob.segob.nsjp.dto.configuracion.ConfiguracionDTO"%>
<%@page import="mx.gob.segob.nsjp.comun.enums.seguridad.Roles"%>

<%@taglib uri="/WEB-INF/tld/struts-bean.tld" prefix="bean"%>

<script type="text/javascript">
		$.jgrid.no_legacy_api = true;
	</script>

<%
	UsuarioDTO usuario = (UsuarioDTO) request.getSession()
			.getAttribute("KEY_SESSION_USUARIO_FIRMADO");
	Long rolId = 0L;
	Boolean esCoordinadorAmpGeneral = false;

	if (usuario != null && usuario.getRolACtivo() != null
			&& usuario.getRolACtivo().getRol() != null
			&& usuario.getRolACtivo().getRol().getRolId() != null) {
		rolId = usuario.getRolACtivo().getRol().getRolId();
	}

	if (rolId.equals(Roles.COORDINADORAMPGENERAL.getValorId())) {
		esCoordinadorAmpGeneral = true;
	}
%>

<script type="text/javascript">

    /**
    *Variables Globales
    */
    var contDelitosGraves=0;
	var idExpedienteop="";
	var idsDelitos="";
	var numeroExpedienteId=0;
	var esCoordinadorAmpGeneral = <%=esCoordinadorAmpGeneral%>;
    var msjProResConfigurable = '<bean:message key="msjProbableResponsable"/>';


    //ON READY
	$(document).ready(function(){

		//Configura los elementos de la ventana
		configurarCamposPantalla();

		/**
		*Recepcion de parametros
		*/
		if(parseInt(idExpedienteop) == 0){
			idExpedienteop='<%=request.getParameter("idExpedienteop")%>';
		}
		numeroExpedienteId='<%=request.getParameter("idNumeroExpediente")%>';

		if(numeroExpedienteId=='null'){
			numeroExpedienteId = '<%=request.getParameter("idNumeroExpedienteop")%>';
		}
		if(idExpedienteop=='null'){
			idExpedienteop='<%=request.getSession().getAttribute("idExpedienteConsulop")%>';
		}
		//asigna a expediente op
		if(idExpedienteop == 'null' && numeroExpedienteId == 'null'){
			idExpedienteop=idExpediente;
			numeroExpedienteId=numeroExpediente;
		}

		//Carga el grid con los delitos del expediente
		cargaGridDelitosAgraviados();
		//Carga grid con el catalogo de los delitos
		//cargaGridDelitos();
		//consultamos las actividades dependiendo de los delitos del expediente
   		//muestraActividadesSugeridasEnConsultaExpediente();

		//Carga grids de los ivolucrados del expediente
		gridImputados();
		gridVictimas();

		try{
			if(deshabilitarCamposPM == true){
				$("#idTblEstablecerRelacionesDelitoPersona :enabled").attr('disabled','disabled');
			}
		}catch(e){};

	});
		//FIN ON READY


		/*
		*Funcion para configurar la pantalla en base al usuario
		*firmado y la institucion
		*/
		function configurarCamposPantalla(){

			if(deshabilitarCampos == true){

				$("#pasar").hide();
				$("#pasarD").hide();
				$("#btnGuardarDelitosAg").hide();
				$("#btnGuardarRelacionesDelitoPersona").hide();
				$("#btnAnulaRelacionDelPerPg").hide();
				$("#btnAttrRelacionDelPer").hide();
			}

			//Oculta campos
			if(esCoordinadorAmpGeneral == true){
				$("#pasar").hide();
				$("#pasarD").hide();
				$("#btnGuardarDelitosAg").hide();
				$("#btnGuardarRelacionesDelitoPersona").hide();
				$("#btnAnulaRelacionDelPerPg").hide();
				$("#btnAttrRelacionDelPer").hide();

				$('#cbxFormasParticipacionRelDelPer').attr('disabled', 'disabled');
				$('#cbxClasificacionRelDelPer').attr('disabled', 'disabled');
				$('#cbxLugarRelDelPer').attr('disabled', 'disabled');
				$('#cbxModalidadRelDelPer').attr('disabled', 'disabled');
				$('#cbxModusRelDelPer').attr('disabled', 'disabled');
				$('#cbxCausaRelDelPer').attr('disabled', 'disabled');
			}
		}


		/**
		*Funcion para cargar el grid de los delitos agraviados, o los delitos del expediente
		*/
		var firstGridDelitosAgraviados = true;

		function cargaGridDelitosAgraviados(){

			if(firstGridDelitosAgraviados){
				jQuery("#gridDelitosAgraviados").jqGrid({
					url:'<%=request.getContextPath()%>/ConsultaDelitoPorExpedienteGrid.do?idNumeroExpediente='+idExpedienteop+'&numeroExpedienteId='+numeroExpedienteId+'',
					datatype: "xml",
					ajaxGridOptions : {
		                   async:false
		            },
					colNames:['Clave','Clave','Delito', '&iquest;Es grave?','&iquest;Es grave?','&iquest;Es principal?','Tipo','DelitoId'],
					colModel:[ 	{name:'Clave',				sortable: false,	index:'clave',				width:20,	align:'center',	hidden:true},
					           	{name:'ClaveBD',			sortable: false,	index:'claveDB',			width:50,	align:'center'	},
					           	{name:'Delito',				sortable: false,	index:'delito',				width:150,	align:'left'	},
								{name:'Gravedad',			sortable: false,	index:'gravedad',			width:0,	align:'center',	hidden:true, formatter:'checkbox'},
								{name:'GravedadFormateada',	sortable: false,	index:'gravedadFormateada',	width:60,	align:'center'},
								{name:'Principal',			sortable: false,	index:'principal',			width:75,	align:'center'},
								{name:'Tipo',				sortable: false,	index:'tipo',				width:0,	align:'center',	hidden: true},
								{name:'DelitoId',			sortable: false,	index:'delitoId',			width:0,	align:'center',	hidden: true}
							],
					pager: jQuery('#pagerGridDelitosAgraviados'),
					width:570,
					rowNum:50,
					rowList:[50,100,150,200],
					caption:"LISTA DE DELITOS DENUNCIADOS",
					sortname: 'Clave',
					viewrecords: true,
					afterInsertRow:function(){
						//Obtiene la lista (de Ids) de delitos agraviados
						idsDelitos=obtenerDelitosDenunciados();
						cargaGridDelitos();
						return true;
					},
					onSelectRow: function(rowID) {
						//Buscamos el delito id de la tabla delito, NO  el catDelitoId
						if(rowID){
							rowDataObtenido = $(this).jqGrid('getRowData',rowID);

							var columaTipo = rowDataObtenido.Tipo

							delitoIdExpediente = columaTipo.split('_')[1];
							//Cuando el delito, no ha sido guardado tiene un cero
							if(parseInt(delitoIdExpediente)==0){
								customAlert("El delito no ha sido guardado","Aviso:");
							}else{
								gridRelacionesDelitoPersona(delitoIdExpediente);
							}
						}
					},
					sortorder: "desc"
				});
				$("#gview_gridDelitosAgraviados .ui-jqgrid-bdiv").css('height', '110px');
				firstGridDelitosAgraviados = false;
			}else{
				jQuery("#gridDelitosAgraviados").jqGrid('setGridParam', {url:'<%=request.getContextPath()%>/ConsultaDelitoPorExpedienteGrid.do?idNumeroExpediente='+idExpedienteop+'&numeroExpedienteId='+numeroExpedienteId+'',datatype: "xml"});
				$("#gridDelitosAgraviados").trigger("reloadGrid");
			}
		}


		/*
		*Funcion para obtener en forma de arreglo id1,id2,....,idn
		*los id de los delitos Agraviados
		*/
		function obtenerDelitosDenunciados(){

			var arrayIDs = new Array() ;
			var arrayIDs = jQuery("#gridDelitosAgraviados").getDataIDs();

			var arrayDelitosDenunciados;
			arrayDelitosDenunciados="";

			for (i=0;i<arrayIDs.length;i++){

				var row = jQuery("#gridDelitosAgraviados").jqGrid('getRowData',arrayIDs[i]);

				if(arrayDelitosDenunciados.length>0){
					arrayDelitosDenunciados = arrayDelitosDenunciados + "," + row.DelitoId;
				}
				else{
					arrayDelitosDenunciados = row.DelitoId;
				}
			}
			return arrayDelitosDenunciados;
		}



		//variable para la carga del grid
		var firstGridDelitos=true;

		/**
		*Funcion que carga el catalogo de delitos a excepcion de los delitos que ya se encuentran
		*relacionados al expediente
		*/
		function cargaGridDelitos(){
			if(firstGridDelitos){

				jQuery("#gridCatDelitos").jqGrid({
		            url:'<%=request.getContextPath()%>/cargarDelitosFiltrados.do?idNumeroExpediente='+idExpedienteop+'&idsDelitos='+idsDelitos+'',
					ajaxGridOptions : {
                        	            	  async:false,
                        	            	  contentType:"application/x-www-form-urlencoded; charset=UTF-8"
                            	    },
					datatype: "xml",
					colNames:['Clave','Clave','Delito', '&iquest;Es grave?','&iquest;Es grave?','Delito Principal','Tipo','DelitoId'],

					colModel:[ 	{name:'Clave',				index:'1',			width:20,	align:'center',		hidden:true},
					        	{name:'ClaveBD',			index:'claveDB',	width:50,	align:'center'},
								{name:'Delito',				index:'2',			width:150,	align:'left'},
								{name:'Gravedad',			index:'3',			width:45,	align:'center',		hidden:true,	formatter:'checkbox'},
								{name:'GravedadFormateada',						index:'4',			width:50,	align:'center', 	searchoptions:{sopt : ['eq'],	multipleSearch:false}},
								//{name:'GravedadFormateada',	sortable: false,	index:'4',			width:50,	align:'center'	 				},
								{name:'Principal',			index:'5',			width:50,	align:'center',		hidden: true},
								{name:'Tipo',				index:'6',			width:50,	align:'center',		hidden: true},
								{name:'DelitoId',			index:'7',			width:50,	align:'center',		hidden: true}
							],
					pager: jQuery('#pagergridCatDelitos'),
					rowNum:25,
					rowList:[25,50,75,100],
					width:290,
					caption:"LISTA DE DELITOS",
					sortname: 'Clave',
					viewrecords: true,
					id: 'pagergridCatDelitos',
					sortorder: "desc"
				}).navGrid('#pagergridCatDelitos',{edit:false,add:false,del:false},{}, {}, {}, { sopt : ['eq','cn'],multipleSearch:false, } );
				$("#gview_gridCatDelitos .ui-jqgrid-bdiv").css('height', '565px');
				firstGridDelitos=false;

			}else{
				jQuery("#gridCatDelitos").jqGrid('setGridParam', {
				url:'<%=request.getContextPath()%>/cargarDelitosFiltrados.do?idNumeroExpediente='+idExpedienteop+'&idsDelitos='+idsDelitos+'',datatype: "xml",
				ajaxGridOptions : {
                    	            	  async:false,
                    	            	  contentType:"application/x-www-form-urlencoded; charset=UTF-8"
                        	    }
				});
				$("#gridCatDelitos").trigger("reloadGrid");
			}
		}


		/*
		*Agrega un registro del catalogo de delitos al gird de delitos agraviados
		*/
		function addRowRigthToLeft(){

			var row = jQuery("#gridCatDelitos").jqGrid('getGridParam','selrow');

			if(row){
				var ret = jQuery("#gridCatDelitos").jqGrid('getRowData',row);
				jQuery("#gridDelitosAgraviados").jqGrid('addRowData',ret.DelitoId,ret);
				jQuery("#gridCatDelitos").jqGrid('delRowData',ret.DelitoId);

			}
			else{
				customAlert("Por favor seleccione un rengl&oacute;n");
			}
			idsDelitos=obtenerDelitosDenunciados();
		}


		/*
		*Agrega un registro del grid de delitos Agraviados al grid del Catalogo de delitos
		*/
		function addRowLeftToRigth(){

			var rowd = jQuery("#gridDelitosAgraviados").jqGrid('getGridParam','selrow');

			if (rowd) {
				var retd = jQuery("#gridDelitosAgraviados").jqGrid('getRowData',rowd);

				if(validaRelacionDelitos(retd.Clave)){
					jQuery("#gridCatDelitos").jqGrid('addRowData',retd.DelitoId,retd);
					jQuery("#gridDelitosAgraviados").jqGrid('delRowData',retd.DelitoId);
					jQuery("#gridDelitosAgraviados").jqGrid(idsDelitos=obtenerDelitosDenunciados(),cargaGridDelitos());
				}else{
					var probableResponsableProp = '<bean:message key="msjProbableResponsable"/>';
					customAlert("Para eliminar este delito del expediente, es necesario eliminar la relaci&oacute;n con el "+probableResponsableProp);
				}
			} else {
				customAlert("Por favor, seleccione un rengl&oacute;n");
			}
			idsDelitos=obtenerDelitosDenunciados();
		}


		/*
		*Verifica que al intentar quitar un delito del grid de delitos del expediente,
		*est&eacute; no se encuentre en una relacion delito persona. Y por lo tanto no pueda ser
		*removido de la lista de delitos agraviados
		*/
		function validaRelacionDelitos(delitoId){

			var relacion=true;

	    	$.ajax({
	    		type: 'POST',
	    		url: '<%=request.getContextPath()%>/consultarRelacionProbRespConDelito.do',
	    		data: 'idExpediente='+idExpedienteop+'&idDelito='+delitoId,
            	async: false,
	    		dataType: 'xml',
	    		success: function(xml){
	    			var regreso="";
    				regreso=$(xml).find('respuesta').text();
	    			if(regreso != false && regreso != "false"){
		    			relacion=false;
	    			}
	    		}
	    	});
			return relacion;
		}


		/*
		*Valida que el delito sea grave, para poder ser clasificado como el principal
		*/
		function delitoPrincipal(){

			var rowd = jQuery("#gridDelitosAgraviados").jqGrid('getGridParam','selrow');

			if (rowd) {
				var retd = jQuery("#gridDelitosAgraviados").jqGrid('getRowData',rowd);
				 document.getElementById('cveDelitoPrincipal').value=retd.Clave;
				 document.getElementById('delitoPrincipal').value=retd.Delito;
				 if(retd.Gravedad == "Yes"){
				 	document.getElementById('graveDelitoPrincipal').value="Si";
				 }
				 else{
					 document.getElementById('graveDelitoPrincipal').value=retd.Gravedad;
				 }
			}
			else {
				customAlert("Por favor seleccione un rengl&oacute;n");
			}
		}


		/*
		*Funcion para limpiar el grid de delitos
		*/
		function limpiar(){
			document.getElementById('cveDelitoPrincipal').value="";
			document.getElementById('delitoPrincipal').value="";
		 	document.getElementById('graveDelitoPrincipal').value="";
		}


		//Variable para controlar la carga del grid de imputados
		var firstGridImputados = true;


		/*
		*Carga el grid de los imputados del expediente
		*/
		function gridImputados(){
			var calidadId="<%=Calidades.PROBABLE_RESPONSABLE_PERSONA.getValorId()%>";

			if(firstGridImputados){

				var tituloGridImputados = "LISTA DE "+ '<bean:message key="msjProbableResponsable"/>';

				jQuery("#gridImputadosPG").jqGrid({
					url:'<%=request.getContextPath()%>/consultarInvolucradoPorCalidad.do?expedienteId='+idExpedienteop+'&calidadId='+calidadId+'',
					datatype: "xml",
					colNames:['Nombre'],

					colModel:[ 	{name:'Nombre',	index:'1',	width:200,	align:'center'}
					 		],
					pager: jQuery('#pagerGridImputadosPG'),
					rowNum:5,
					rowList:[5,10,20,30,50,100],
					width:570,
					//autowidth: true,
					caption:tituloGridImputados.toUpperCase(),
					sortname: 'Nombre',
					viewrecords: true,
					multiselect: true,
					toolbar: [true,"top"],
					sortorder: "desc",
					async:true,
					gridComplete: function () {
						seleccionarItems($(this));
						try{
							if(deshabilitarCamposPM == true){
								$("#idTblEstablecerRelacionesDelitoPersona :enabled").attr('disabled','disabled');
							}
						}catch(e){};
					},
					onPaging: function (a) {
						guardarItemsSeleccionados($(this));
					},
					onSortCol: function(){
						eliminarItemsSeleccionados($(this));
					}
				});
				$("#gview_gridImputadosPG .ui-jqgrid-bdiv").css('height', '110px');

				//Agregamos el boton para limpiar las selecciones del multigird
				$("#t_gridImputadosPG").append("<input type='button' class='ui-button ui-corner-all ui-widget' value='Limpiar selecci&oacute;n' />");
				$("input","#t_gridImputadosPG").click(function(){
					eliminarItemsSeleccionados($("#gridImputadosPG"));
				});

				firstGridImputados=false;
			}else{
				jQuery("#gridImputadosPG").jqGrid('setGridParam', {url:'<%=request.getContextPath()%>/consultarInvolucradoPorCalidad.do?expedienteId='+idExpedienteop+'&calidadId='+calidadId+'',datatype: "xml"});
				$("#gridImputadosPG").trigger("reloadGrid");
			}
		}


		//Variable para controlar la carga del grid de victimas
		var firstGridVictimas = true;

		/*
		*Funcion para cargar el grid de victimas del expediente
		*/
		function gridVictimas(){

			//Tambien consulta los denunciantes-victimas en el servicio
			var calidadId="<%=Calidades.VICTIMA_PERSONA.getValorId()%>";

			if(firstGridVictimas){
				jQuery("#gridVictimasPG").jqGrid({
					url:'<%=request.getContextPath()%>/consultarInvolucradoPorCalidad.do?expedienteId='+idExpedienteop+'&calidadId='+calidadId+'',
					datatype: "xml",
					colNames:['Nombre'],

					colModel:[ 	{name:'Nombre',				index:'1',			width:200,	align:'center'}
					 		],
					pager: jQuery('#pagerGridVictimasPG'),
					rowNum:5,
					rowList:[5,10,20,30,50,100],
					width:570,
					caption:"LISTA DE VICTIMAS",
					sortname: 'Nombre',
					viewrecords: true,
					multiselect: true,
					async:true,
					toolbar: [true,"top"],
					sortorder: "desc",
					gridComplete: function () {
						seleccionarItems($(this));
						try{
							if(deshabilitarCamposPM == true){
								$("#idTblEstablecerRelacionesDelitoPersona :enabled").attr('disabled','disabled');
							}
						}catch(e){};
					},
					onPaging: function (a) {
						guardarItemsSeleccionados($(this));
					},
					onSortCol: function(){
						eliminarItemsSeleccionados($(this));
					}
				});
				$("#gview_gridVictimasPG .ui-jqgrid-bdiv").css('height', '110px');

				//Agregamos el boton para limpiar las selecciones del multigird
				$("#t_gridVictimasPG").append("<input type='button' class='ui-button ui-corner-all ui-widget' value='Limpiar selecci&oacute;n' />");
				$("input","#t_gridVictimasPG").click(function(){
					eliminarItemsSeleccionados($("#gridVictimasPG"));
				});

				firstGridVictimas=false;
			}else{
				jQuery("#gridVictimasPG").jqGrid('setGridParam', {url:'<%=request.getContextPath()%>/consultarInvolucradoPorCalidad.do?expedienteId='+idExpedienteop+'&calidadId='+calidadId+'',datatype: "xml"});
				$("#gridVictimasPG").trigger("reloadGrid");
			}
		}


		//Vsriable para controlar la carga del grid de delitos persona
		var firstGridRelacionesDelitoPersona = true;

		/*
		*Funcion para cargar el grid de las relaciones delito persona del expediente
		*/
		function gridRelacionesDelitoPersona(delitoId){

			/*if(delitoId == undefined){
				jQuery("#gridRelacionesDelitoPersonaPG").jqGrid('setGridParam', {url:'<%=request.getContextPath()%>/limpiarGrid.do?numeroColumnas=3',datatype: "xml"});
				$("#gridRelacionesDelitoPersonaPG").trigger("reloadGrid");
                                jQuery("#gridRelacionesDelitoPersonaPG").jqGrid('setGridParam', {url:'<%=request.getContextPath()%>/consultarRelacionDelitoPorExpediente.do?idExpediente='+idExpedienteop+'',datatype: "xml"});
                                $("#gridRelacionesDelitoPersonaPG").trigger("reloadGrid");
			}else{*/

                            if(firstGridRelacionesDelitoPersona){
                                    jQuery("#gridRelacionesDelitoPersonaPG").jqGrid({
                                url:'<%=request.getContextPath()%>/consultarRelacionDelitoPorExpediente.do?idExpediente='+idExpedienteop+'&delitoId='+delitoId+'',
                                            datatype: "xml",
                                            colNames:['Nombre '+'<bean:message key="msjProbableResponsable"/>','Delito','Nombre V&iacute;ctima',],

                                            colModel:[ 	{name:'NombrePR',	sortable: false,	index:'1',	width:250,	align:'center'},
                                                                    {name:'NombreDEL',	sortable: false,	index:'1',	width:250,	align:'center'},
                                                                    {name:'NombreVIC',	sortable: false,	index:'1',	width:250,	align:'center'}
                                                            ],
                                            pager: jQuery('#pagerGridRelacionesDelitoPersonaPG'),
                                            rowNum:5,
                                            rowList:[5,10,15,20],
                                            width:925,
                                            caption:"LISTA DE RELACIONES DELITO-PERSONA POR DELITO",
                                            sortname: 'Nombre',
                                            viewrecords: true,
                                            sortorder: "desc"
                                    });
                                    $("#gview_gridRelacionesDelitoPersonaPG .ui-jqgrid-bdiv").css('height', '150px');
                                    firstGridRelacionesDelitoPersona=false;
                            }else{
                                    jQuery("#gridRelacionesDelitoPersonaPG").jqGrid('setGridParam', {url:'<%=request.getContextPath()%>/consultarRelacionDelitoPorExpediente.do?idExpediente='+idExpedienteop+'&delitoId='+delitoId+'',datatype: "xml"});
                                    $("#gridRelacionesDelitoPersonaPG").trigger("reloadGrid");
                            }
                        //}
		}


		/*
		*Funcion para cargar todos los grids
		*/
		function cargarGridsInvolucradosRelDelitoPersonaPG(){
                        cargaGridDelitosAgraviados();
			gridImputados();
			gridVictimas();
			gridRelacionesDelitoPersona();
		}


		/*
		*Funcion para guardar las relaciones delito persona seleccionadas por el usuario
		*si una relacion, ya existe, NO se guarda de nuevo
		*/
		function guardarRelacionesDelitoPersona(){

			var imputadosIds = obtenerSeleccionados($('#gridImputadosPG'));
			var victimasIds = obtenerSeleccionados($('#gridVictimasPG'));
			//Id del catDelito
			var delitoIdSeleccionado = obtenerIdFilaSeleccionada($('#gridDelitosAgraviados'));
			//Id del delito asociado al expediente
			var delitoId = "";

			//Validamos que seleccione el delito
			if(delitoIdSeleccionado == false || delitoIdSeleccionado == "false"){

				customAlert("Seleccione un delito","Aviso");
				return;
			}

			//Validamos que el delito, ya este guardado en el expediente
			if(delitoIdSeleccionado != false && delitoIdSeleccionado != "false"){
				var rowDelitoObtenido = jQuery("#gridDelitosAgraviados").jqGrid('getRowData',delitoIdSeleccionado);
				var tipoDelito = rowDelitoObtenido.Tipo

				delitoId = tipoDelito.split('_')[1];

				if(parseInt(delitoId)==0){
					customAlert("Guarde los nuevos delitos, antes de realizar la asociaci&oacute;n","Aviso");
					return;
				}
			}

			if(imputadosIds.length <= 0){
				customAlert("Seleccione almenos un "+'<bean:message key="msjProbableResponsable"/>',"Aviso");
				return;
			}

			if(victimasIds.length <= 0){
				customAlert("Seleccione almenos una v&iacute;ctima","Aviso");
				return;
			}

			var parametros = "";

			parametros += '&imputadosIds=' + imputadosIds;
			parametros += '&victimasIds=' + victimasIds;
			parametros += '&delitoId=' + delitoId;

			//Lamada a guardar la asociacion
			ejecutaAction("/asociarDelitosConIvolucrados", function(xmlRespuesta){
				if($(xmlRespuesta).find('body').find('respuesta').text()== "exito"){
					customAlert("Se guardaron correctamente las relaciones delito persona","",function(){
						//Recargamos el gird de relaciones delito persona
						gridRelacionesDelitoPersona(delitoId);
						//Lipiamos la seleccion de los grids
						eliminarItemsSeleccionados($("#gridVictimasPG"));
						eliminarItemsSeleccionados($("#gridImputadosPG"));
					});
				}else{
					customAlert("Imposible relacionar los involucrados con el delito","Error!");
				}
			} , parametros);
		}

//**********************************************FUNCIONES PARA CONSERVAR LA SELECCION DE LOS GRIDS MULTISELECT***********************************************/

		/*
		*Funcion para seleccionar en el gird multiselect
		*/
		function  seleccionarItems(grid){

			var currentPage = grid.getGridParam('page').toString();

			//retrieve any previously stored rows for this page and re-select them
			var retrieveSelectedRows = grid.data(currentPage);

			if (retrieveSelectedRows) {
				 $.each(retrieveSelectedRows, function (index, value) {
					 grid.setSelection(value, false);
				});
			}
		}


		/*
		*Funcion para guardar en el gird multiselect
		*/
		function guardarItemsSeleccionados(grid) {

			var pagerId = grid.getGridParam('pager').toString();

			if(pagerId.indexOf("#") != -1){
				pagerId = pagerId.substring(1, pagerId.length);
			}
			// ger paper id like "pager"
			var pageValue = $('input.ui-pg-input', "#pg_" + $.jgrid.jqID(pagerId)).val();
			var saveSelectedRows = grid.getGridParam('selarrrow');
			//Store any selected rows
			grid.data(pageValue.toString(), saveSelectedRows);
		}


		/*
		*Funcion para borrar la seleccion en el gird multiselect
		*/
		function eliminarItemsSeleccionados(grid){

			guardarItemsSeleccionados(grid);
			grid.jqGrid('resetSelection');
			var retrieveSelectedRows = grid.data();
			if (retrieveSelectedRows) {
				$.each(retrieveSelectedRows, function (index, value) {
					$.each(value, function (sub_index, sub_value) {
						if(typeof(sub_value)=='string'){
							grid.data(index, "");
						}
					});
				});
			}

		}


		/*
		*Funcion para obtener los ids seleccionados del grid multiselect
		*/
		function obtenerSeleccionados(grid){

			guardarItemsSeleccionados(grid);

			var retrieveSelectedRows = grid.data();
			var arr_values = new Array();

			if (retrieveSelectedRows) {
				$.each(retrieveSelectedRows, function (index, value) {
					$.each(value, function (index, sub_value) {
						if(typeof(sub_value)=='string')
						arr_values.push(sub_value);
					});
				});
			}

			return arr_values;
		}


		/*
		*Funcion para obtener el id de la fila seleccionada
		*/
		function obtenerIdFilaSeleccionada(grid){
			var id = grid.jqGrid('getGridParam','selrow');
			if(id) {
				return id;
			} else {
				return false;
			}
		}

	</script>


<table width="100%%" border="0" id="idTblEstablecerRelacionesDelitoPersona">
  <tr>
    <td width="25%" rowspan="23" align="left" valign="top">
		<table id="gridCatDelitos"></table>
		<div id="pagergridCatDelitos" align="center"></div>	</td>
    <td width="3%">&nbsp;</td>
    <td width="47%" rowspan="7" align="left" valign="top">
		<table id="gridDelitosAgraviados"></table>
		<div id="pagerGridDelitosAgraviados"></div>	</td>
    <td id="tdActividadesSugeridas"  width="25%" rowspan="23" align="left" valign="top">
		<div id="actividadesSugeridas" style="width: 100%;height:27px;" class="ui-jqgrid-titlebar ui-widget-header ui-corner-top ui-helper-clearfix">
			<span class="ui-jqgrid-title">ACTIVIDADES SUGERIDAS :</span>
		</div>
		<div style="overflow:auto; width: 100%; height: 320px" id="actividadesXDelitosDelExpediente">
			<span ></span>
		</div>
	</td>
  </tr>
  <tr>
    <td align="center" valign="top"><input name="button2" type="button"
			class="ui-button ui-corner-all ui-widget"
			id="pasar" onclick="addRowRigthToLeft();" value="&gt;&gt;" /></td>
  </tr>
  <tr>
    <td>&nbsp;</td>
  </tr>
  <tr>
    <td align="center" valign="top"><input name="button3" type="button"
			class="ui-button ui-corner-all ui-widget"
			id="pasarD"  onclick="addRowLeftToRigth();" value="&lt;&lt;" /></td>
  </tr>
  <tr>
    <td>&nbsp;</td>
  </tr>
  <tr>
    <td>&nbsp;</td>
  </tr>
  <tr>
    <td>&nbsp;</td>
  </tr>
  <tr>
    <td>&nbsp;</td>
    <td align="center">
    	<input name="button" type="button"
			class="ui-button ui-corner-all ui-widget" id="btnGuardarDelitosAg" onclick="guardarDelitosAgraviadosExp();"
			value="Guardar Delitos" />
	</td>
  </tr>
  <tr>
    <td>&nbsp;</td>
    <td rowspan="7" align="left" valign="top"><table id="gridImputadosPG"></table>
		<div id="pagerGridImputadosPG" align="center"></div>	</td>
  </tr>
  <tr>
    <td>&nbsp;</td>
  </tr>
  <tr>
    <td>&nbsp;</td>
  </tr>
  <tr>
    <td>&nbsp;</td>
  </tr>
  <tr>
    <td>&nbsp;</td>
  </tr>
  <tr>
    <td>&nbsp;</td>
  </tr>
  <tr>
    <td>&nbsp;</td>
  </tr>
  <tr>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
  </tr>
  <tr>
    <td>&nbsp;</td>
    <td rowspan="7" align="left" valign="top"><table id="gridVictimasPG"></table>
		<div id="pagerGridVictimasPG" align="center"></div>	</td>
  </tr>
  <tr>
    <td>&nbsp;</td>

    <td></td>
  </tr>
  <tr>
    <td>&nbsp;</td>
    <td></td>
  </tr>
  <tr>
    <td>&nbsp;</td>
  </tr>
  <tr>
    <td>&nbsp;</td>
  </tr>
  <tr>
    <td>&nbsp;</td>
  </tr>

  <tr>
    <td>&nbsp;</td>
  </tr>

  <tr>
    <td align="left" valign="top">&nbsp;</td>
    <td>&nbsp;</td>
    <td align="center"><input name="button4" type="button" class="ui-button ui-corner-all ui-widget"
			id="btnGuardarRelacionesDelitoPersona"
			onclick="guardarRelacionesDelitoPersona();"
			value="Relacionar Delito Personas" /></td>
    <td>&nbsp;</td>
  </tr>
  <tr>
    <td colspan="3" rowspan="9" align="center" valign="top">
		<table id="gridRelacionesDelitoPersonaPG">
    </table>		<div id="pagerGridRelacionesDelitoPersonaPG" align="center"></div></td>
    <td>&nbsp;</td>
  </tr>
  <tr>
    <td>&nbsp;</td>
  </tr>
  <tr>
    <td>&nbsp;</td>
  </tr>
  <tr>
    <td>&nbsp;</td>
  </tr>
  <tr>
    <td>&nbsp;</td>
  </tr>
  <tr>
    <td>&nbsp;</td>
  </tr>
  <tr>
    <td>&nbsp;</td>
  </tr>
  <tr>
    <td>&nbsp;</td>
  </tr>
  <tr>
    <td>&nbsp;</td>
  </tr>
  <tr>
    <td colspan="3" align="center" valign="middle"></td>
    <td>&nbsp;</td>
  </tr>
  <tr>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
  </tr>
</table>

