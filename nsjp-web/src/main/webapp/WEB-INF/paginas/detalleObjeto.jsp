<script type="text/javascript">	
	var primeraVezGridDocumentosDigitales = true;

	jQuery().ready(function(){
			

	});


	/*
	*Funcion que carga el grid con el nombre y tipo de las imagenes asociadas a un elemento
	*/
	function cargaGridArchivosDigitalesXElemento(){
	
		if(primeraVezGridDocumentosDigitales == true){
			jQuery("#gridArchivosDigitales").jqGrid({
				url:'<%=request.getContextPath()%>/consultarArchivosDigitalesXElementoId.do?elementoID='+elementoID+'',
				datatype: "xml", 
				colNames:['Nombre', 'Tipo'],
				colModel:[ 
				           {name:'nombre',index:'2', width:350, sortable:true, align: "center"},
				           {name:'tipo',index:'3', width:200, sortable:true, align: "center"},
						  ],
				autowidth: false,
				pager: jQuery('#pagerGridArchivosDigitales'),
				rowNum:10,
				rowList:[10,20,30],
				width: 550,
				sortname: '2',
				rownumbers: false,
				gridview: true, 
				viewrecords: true, 
				sortorder: "desc",
				ondblClickRow: function(rowid) {
						consultaFoto(rowid);							
				},
				caption:"Im&aacute;genes asociadas" 
			});			
			primeraVezGridDocumentosDigitales= false;
		}
		else{
			jQuery("#gridArchivosDigitales").jqGrid('setGridParam', {url:'<%=request.getContextPath()%>/consultarArchivosDigitalesXElementoId.do?elementoID='+elementoID+'',datatype: "xml" });
			$("#gridArchivosDigitales").trigger("reloadGrid");
		}
	}
	
	
	var accionConsultarPdf="<%= request.getContextPath() %>/ConsultarContenidoArchivoDigital.do";
	function consultaFoto(id){		
		document.frmArchivoDigital.action=accionConsultarPdf+"?archivoDigitalId="+id + "&mostrarTipoArchivo=false";
		document.frmArchivoDigital.submit();
	}
	
	
	function abreVentanaAdjuntarImagen(){
		var extensionesPermitidas = ".gif,.jpg";
		$.newWindow({id:"iframewindowAdjuntarDocumento", statusBar: true, posx:50,posy:50,width:450,height:200,title:"Adjuntar imagen", type:"iframe", modal:true});
		$.updateWindowContent("iframewindowAdjuntarDocumento",'<iframe src="' + contextoPagina + '/adjuntarDocumentoAElementoGenerico.do?extensionesPermitidas=' + extensionesPermitidas + '&elementoID='+elementoID+'" width="450" height="200" />');
	}

 </script>
 
	<br>
	<table width="80%" border="0" align="center">
	  <tr>	  	 
	     <td width="50%" align="center" id="gridCatTipoEstudioTD">
	     	  <!--  Grid tipo de estudio --> 
			  <table id="gridCatTipoEstudio"></table>
			  <div id="paginadorGridCatTipoEstudio"></div>
		</td>
	    <td width="50%" align="center">
	     	  <!--  Grid tipo de estudio --> 
	  <table id="gridArchivosDigitales"></table>
			  <div id="pagerGridArchivosDigitales"></div>
		</td>
      </tr>
	</table> 
 
	<form name="frmArchivoDigital" action="<%= request.getContextPath() %>/ConsultarContenidoArchivoDigital.do" method="post"></form>

