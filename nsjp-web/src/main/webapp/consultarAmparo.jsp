<script type="text/javascript">
        var idNumeroExpediente='<%= request.getParameter("idNumeroExpediente")%>';
       
        /*
      	*Funcion que permite averidguar si existen amparos en un expediente
      	*/
      	function existenAmparosEnExpediente()
      	{
      		var param="numeroExpediente="+numeroExpediente;
      		$.ajax({
      			type: 'POST',
      			url: '<%= request.getContextPath()%>/consultarAmparosPorExpediente.do?idNumeroExpediente='+ idNumeroExpediente,
      			data: param,
      			dataType: 'xml',
      			async: true,
      			success: function(xml){
  					if(parseInt($(xml).find('row').length) >0)
  						$("#lblAmparos").html("Si")
  					else
  						$("#lblAmparos").html("No")

      			}
      		});
      	}
        
         function consultarAmparosPorExpediente(){
             jQuery("#gridAmparos").jqGrid({
                  url:'<%=request.getContextPath()%>'+'/consultarAmparosPorExpediente.do?idNumeroExpediente='+ idNumeroExpediente,
                  datatype: "xml",
                  colNames:['Folio del Amparo','Descripci&oacute;n','Amparo','Estatus'],
                  colModel:[
                      {name:'folio',index:'1',  sortable:true, width:200},
                      {name:'descripcion',index:'2',  sortable:false, width:300},
                      {name:'verDocumento',index:'3',  sortable:false, width:250, align:'center'},
                      {name:'estatus',index:'4',  sortable:false, width:250, align:'center'},
                  ],
                  pager: jQuery('#paginadorAmparos'),
                  rowNum:10,
                  rowList:[10,20,30],
                  caption:"* Amparo(s) asociado(s) al expediente",
                  sortname: '1',
                  viewrecords: true,
                  sortorder: "asc",
              	  gridComplete: function(){
					  var ids = jQuery("#gridAmparos").jqGrid('getDataIDs');
					  for(var i=0;i < ids.length;i++){
						var cl = ids[i];
					 
						el = 
							"<a href='#' title='Ver Documento' onclick=\"consultaPDF("+cl+");\">Ver documento</a>";									 
						jQuery("#gridAmparos").jqGrid('setRowData',ids[i],{verDocumento:el});
					  }	
				    },
                  onSelectRow: function(id){
                   	 	consultarInvolucradosPorAmparo(id);
					},
					ondblClickRow: function(id){
						modificaAmparo(id);
					}
              }).navGrid('#paginadorAmparos',{edit:false,add:false,del:false});
				$("#gridAmparos").trigger("reloadGrid");	
		}
         
         var recargarGridgInvolucradosDelAmparo=false;
         function consultarInvolucradosPorAmparo(idAmparo){
        	 var params;
 			 params="idAmparo=" + idAmparo;      
        	 
        	 if (recargarGridgInvolucradosDelAmparo==false){
        		 jQuery("#gridInvolucradosDelAmparo").jqGrid({
                     url:'<%=request.getContextPath()%>'+'/consultarInvolucradosAsociadosAAmparo.do?'+params,
                     datatype: "xml",
                     colNames:['Nombre completo','Calidad'],
                     colModel:[
                         {name:'nombre',index:'1',  sortable:false, width:200},
                         {name:'calidad',index:'2',  sortable:false, width:300},
                     ],
                     pager: jQuery('#paginadorInvolucradosDelAmparo'),
                     rowNum:10,
                     rowList:[10,20,30],
                     caption:"* Involucrado(s) que ampara",
                     sortname: '2',
                     viewrecords: true,
                     sortorder: "asc"
                 }).navGrid('#paginadorInvolucradosDelAmparo',{edit:false,add:false,del:false});
   				$("#gridInvolucradosDelAmparo").trigger("reloadGrid");
   				recargarGridgInvolucradosDelAmparo=true;
        	 }else{//Recargar el grid
        		 jQuery("#gridInvolucradosDelAmparo").jqGrid('setGridParam', {url:'<%=request.getContextPath()%>/consultarInvolucradosAsociadosAAmparo.do?'+params,datatype: "xml" });
				 $("#gridInvolucradosDelAmparo").trigger("reloadGrid");
        	 }
            
		}
                
      	function modificaAmparo(id){
      		$.newWindow({id:"iframewindowRegistrarAmparo", statusBar: true, posx:20,posy:20,width:550,height:500,title:"Actualiza Amparo", type:"iframe", confirmarCierreVentana:true});
            $.updateWindowContent("iframewindowRegistrarAmparo",
            	'<iframe src="<%= request.getContextPath() %>/registrarAmparo.jsp?idNumeroExpediente=' + idNumeroExpediente + '&idExpedienteSoli='+ idExpediente + '&numeroGeneralCaso=' +numeroGeneralCaso+ '&idAmparo=' +id +'"    width="550" height="500" />');
      	}  		
</script>

	<table border="0" summary="Consulta informaci&oacute;n de amparos">
	  <tr>
	    <td>
	  		<div id="divGridAmparos"> 
		    	<table id="gridAmparos"></table>
		        <div id="paginadorAmparos"></div>
		    </div>
	    </td>
	  </tr>
	  <tr>
	    <td>&nbsp;</td>
	  </tr>
	  <tr>
	    <td>
	    	<div id="divGridInvolucradosDelAmparo"> 
		    	<table id="gridInvolucradosDelAmparo"></table>
		        <div id="paginadorInvolucradosDelAmparo"></div>
		    </div>
	    </td>
	  </tr>
	</table>