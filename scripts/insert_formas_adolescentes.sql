use [coa-pgsaltillocurso]

--INSERT PARA TODAS LASFORMAS DE ADOLESCENTES, considerando TipoForma_val = 1611 q es Oficio
SELECT * FROM Forma 
SELECT * FROM Valor WHERE valor_id = 1611 
  
INSERT
INTO
  Forma
  (
    cDescForma,
    TipoForma_val,
    cCuerpo,
    cNombre
  )
  VALUES
  (
    'ACTA DE EJECUCION DE ORDEN DE CATEO',
    1611,
    '<p> 
<style type="text/css">
p { margin-bottom: 0.1in; direction: ltr; line-height: 120%; text-align: left; orphans: 2; widows: 2; } </style>
</p>
<p style="margin-bottom: 0.14in; line-height: 150%; text-align: center;"> 
<b><font face="Arial, serif">ACTA DE EJECUCION DE ORDEN DE CATEO </font></b></p>
<p align="right" style="margin-left: 0.98in; margin-top: 0.19in; margin-bottom: 0.19in; line-height: 150%"> 
<br /> 
<font face="Times New Roman, serif"><font size="3" style="font-size: 12pt"><font face="Arial, serif"><font size="2" style="font-size: 11pt">N&deg;. Expediente: _______________</font></font></font></font></p>
<p align="right" style="margin-left: 0.98in; margin-top: 0.19in; margin-bottom: 0.19in; line-height: 150%"> 
<font face="Times New Roman, serif"><font size="3" style="font-size: 12pt"><font face="Arial, serif"><font size="2" style="font-size: 11pt">NUC. &lt;numeroCaso&gt;</font></font></font></font></p>
<p style="margin-left: 0.98in; margin-bottom: 0in; line-height: 150%"> 
&nbsp;</p>
<p style="margin-left: 0.98in; margin-bottom: 0in; line-height: 150%"> 
<font face="Times New Roman, serif"><font size="3" style="font-size: 12pt"><font face="Arial, serif"><font size="2" style="font-size: 11pt">Ciudad: ____________&nbsp;&nbsp; Estado:</font></font><b><font face="Arial, serif"><font size="2" style="font-size: 11pt"> ______________</font></font></b><br /> 
<font face="Arial, serif"><font size="2" style="font-size: 11pt"> Fecha: &lt;<font face="Times New Roman, serif"><font size="3" style="font-size: 12pt"><font face="Arial, serif"><font size="2" style="font-size: 11pt">fechaActual&gt;</font></font></font></font></font></font></font><font size="3" style="font-size: 12pt"><font size="2"><font face="Arial, serif">&nbsp;</font></font><font face="Arial, serif"><font size="2" style="font-size: 11pt"> Hora: <font face="Times New Roman, serif"><font size="3" style="font-size: 12pt"><font face="Arial, serif"><font size="2" style="font-size: 11pt">&lt;horaActual&gt;</font></font></font></font></font></font></font><br /> 
<font size="3" style="font-size: 12pt"> <font face="Arial, serif"><font size="2" style="font-size: 11pt"> Agente del Ministerio P&uacute;blico: </font></font><b><font face="Arial, serif"><font size="2" style="font-size: 11pt">__________________</font></font></b><font face="Arial, serif"><font size="2" style="font-size: 11pt">.<br /> 
Unidad de investigaci&oacute;n: &lt;<span style="display: none;"> </span></font></font><font face="Times New Roman, serif"><font size="3" style="font-size: 12pt"><font face="Arial, serif"><font size="2" style="font-size: 11pt">unidadResponsableTramite&gt;</font></font></font></font><span style="display: none;"> </span><br /> 
<font face="Arial, serif"><font size="2" style="font-size: 11pt"> Domicilio de la unidad: </font></font><font face="Arial, serif"><font size="2" style="font-size: 11pt"><b>________________________</b></font></font><font face="Arial, serif"><font size="2" style="font-size: 11pt">.</font></font></font></font></p>
<p style="margin-left: 0.98in; margin-bottom: 0in; line-height: 150%"> 
<font face="Times New Roman, serif"><font size="3" style="font-size: 12pt"><font face="Arial, serif"><font size="2" style="font-size: 11pt">Lugar de los hechos: ___________________________________________</font></font></font></font></p>
<p align="justify" style="margin-left: 0.49in; margin-top: 0.19in; margin-bottom: 0.19in; line-height: 150%"> 
<a name="_GoBack"></a> <font face="Times New Roman, serif"><font size="3" style="font-size: 12pt"><font face="Arial, serif"><font size="2" style="font-size: 11pt">Con fundamento en lo establecido en los num&eacute;rales 16 y 21 de la Constituci&oacute;n Pol&iacute;tica de los Estados Unidos Mexicanos, correlativos a los art&iacute;culos 1, 2, 6, 7, 9, 61, 62, 63, 132 y 133 de la Ley del Sistema Integral de Justicia para Adolescentes del Estado de Coah</font></font><font face="Arial, serif">uila, en relaci&oacute;n con los art&iacute;culos </font><font color="#000000"><font face="Arial, serif"><font size="2" style="font-size: 11pt">252, fracci&oacute;n II, 282 y 289 del C&oacute;digo Nacional de Procedimientos Penales;&nbsp;</font></font></font><font face="Arial, serif"><font size="2" style="font-size: 11pt"> y toda vez que se encuentran presentes los agentes Especializados de la polic&iacute;a investigadora _________________________________ y __________________________, y al estar constituidos en el exterior del domicilio ubicado en _________________ de la ciudad de&nbsp; _______________________ mismo que&nbsp; corresponde a ___________________________________ y toda vez que el inmueble esta deshabitado. Se procede a hacer del conocimiento a los agentes policiacos que el Juez de Control de este Distrito Judicial, autorizo de manera indistinta a ____________________, para realizar el cateo en el domicilio a efectos de buscar ______________________________, y una vez ubicados en el lugar de referencia se procedi&oacute; a ingresar al mismo y se hace constar que se trata de _________________________________________________________________________________, as&iacute; mismo se hace constar que se solicit&oacute; a los peritos&nbsp; _______________________________ ______________________, realicen el procesamiento del lugar y una vez recolectando los objetos, indicios o evidencias encontradas&nbsp; y previo llenado de la cadena de custodia, para que sean remitidos al laboratorio de ser necesario, para la pr&aacute;ctica de los dict&aacute;menes correspondientes. Al no existir m&aacute;s actuaciones que efectuar se concluye el cateo a las ________________ horas, del d&iacute;a&nbsp; ___________ del de este mes y a&ntilde;o.</font></font></font></font></p>
<p style="margin-left: 0.98in; margin-bottom: 0in; line-height: 150%"> 
<br /> 
<font face="Times New Roman, serif"><font size="3" style="font-size: 12pt"><font face="Arial, serif"><font size="2" style="font-size: 11pt"><b>TESTIGO&nbsp; &lt;nombreCompletoTestigo&gt;<br /> 
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; &nbsp; &nbsp; NOMBRE<br /> 
<br /> 
TESTIGO ____________________________________________________</b></font></font></font></font></p>
<p style="margin-left: 0.98in; margin-bottom: 0in; line-height: 150%"> 
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp;&nbsp; <font face="Times New Roman, serif"><font size="3" style="font-size: 12pt"><font face="Arial, serif"><font size="2" style="font-size: 11pt"><b>NOMBRE</b></font></font></font></font></p>
<p style="margin-left: 0.98in; margin-bottom: 0in; line-height: 150%"> 
&nbsp;</p>
<p style="margin-left: 0.98in; margin-bottom: 0in; line-height: 150%"> 
<font face="Times New Roman, serif"><font size="3" style="font-size: 12pt"><font face="Arial, serif"><font size="2" style="font-size: 11pt"><b>POLICIA INVESTIGADOR_______________________________________</b></font></font></font></font></p>
<p style="margin-left: 0.98in; margin-bottom: 0in; line-height: 150%"> 
&nbsp;</p>
<p style="margin-left: 0.98in; margin-bottom: 0in; line-height: 150%"> 
&nbsp;</p>
<p style="margin-left: 0.98in; margin-bottom: 0in; line-height: 150%"> 
<font face="Times New Roman, serif"><font size="3" style="font-size: 12pt"><font face="Arial, serif"><font size="2" style="font-size: 11pt"><b>POLICIA ESPECIALIZADO INVESTIGADOR_______________________________________</b></font></font></font></font></p>
<p style="margin-left: 0.98in; margin-bottom: 0in; line-height: 150%"> 
&nbsp;</p>
<p style="margin-left: 0.98in; margin-bottom: 0in; line-height: 150%"> 
&nbsp;</p>
<p style="margin-left: 0.98in; margin-bottom: 0in; line-height: 150%"> 
<font face="Times New Roman, serif"><font size="3" style="font-size: 12pt"><font face="Arial, serif"><font size="2" style="font-size: 11pt"><b>PERITO_____________________________________________________</b></font></font></font></font></p>
<p style="margin-left: 0.98in; margin-bottom: 0in; line-height: 150%"> 
&nbsp;</p>
<p style="margin-left: 0.98in; margin-bottom: 0in; line-height: 150%"> 
<br /> 
&nbsp;</p>
<p align="center" style="margin-left: 0.98in; margin-top: 0.19in; margin-bottom: 0.19in; line-height: 150%"> 
<font face="Times New Roman, serif"><font size="3" style="font-size: 12pt"><font face="Arial, serif"><font size="2" style="font-size: 11pt"><b>ATENTAMENTE<br /> 
AGENTE DEL MINISTERIO P&Uacute;BLICO<br /> 
(ADSCRIPCI&Oacute;N, NOMBRE Y FIRMA)</b></font></font></font></font></p>
<p align="center" style="margin-bottom: 0.14in; line-height: 150%"> 
<br /> 
&nbsp;</p>
<p style="margin-bottom: 0.14in; line-height: 150%"> 
<br /> 
&nbsp;</p>'
    ,
    'ACTA DE EJECUCION DE ORDEN DE CATEO'
  )
  ,
  (
    'ACUERDO DE INICIO SIN DETENIDO ADOLESCENTE',
    1611,
    '<p align="right" style="margin-left: 0.49in; margin-bottom: 0.14in; line-height: 115%"> 
<b><font face="Arial, serif">ACUERDO DE INICIO SIN DETENIDO</font></b></p>
<p align="right" style="margin-left: 0.49in; margin-top: 0.19in; margin-bottom: 0.19in; line-height: 100%"> 
<br /> 
&nbsp;</p>
<p align="right" style="margin-left: 0.98in; margin-top: 0.19in; margin-bottom: 0.19in; line-height: 100%"> 
<font face="Times New Roman, serif"><font style="font-size: 12pt"><font face="Arial, serif">N&uacute;m. Carpeta de investigaci&oacute;n.:________________</font></font></font></p>
<p align="right" style="margin-left: 0.98in; margin-top: 0.19in; margin-bottom: 0.19in; line-height: 100%"> 
<br /> 
<font face="Times New Roman, serif"><font style="font-size: 12pt"><font face="Arial, serif">N&deg;. Expediente: </font><b><font face="Arial, serif">___________________</font></b></font></font></p>
<p style="margin-left: 0.39in; margin-bottom: 0in; line-height: 100%"> 
&nbsp;</p>
<p style="margin-left: 0.39in; margin-bottom: 0in; line-height: 100%"> 
<font face="Times New Roman, serif"><font style="font-size: 12pt"><font face="Arial, serif">Ciudad: </font><b><font face="Arial, serif">___________ </font></b><font face="Arial, serif">Estado: </font><b><font face="Arial, serif">____________</font></b></font></font></p>
<p style="margin-left: 0.39in; margin-bottom: 0in; line-height: 100%"> 
<br /> 
<font face="Times New Roman, serif"><font style="font-size: 12pt"><font face="Arial, serif">Fecha: </font><b><font face="Arial, serif">___________________</font></b><font face="Arial, serif">&nbsp; Hora: </font><b><font face="Arial, serif">_________________</font></b></font></font></p>
<p style="margin-left: 0.39in; margin-bottom: 0in; line-height: 100%"> 
<br /> 
<font face="Times New Roman, serif"><font style="font-size: 12pt"><font face="Arial, serif">Agente del Ministerio P&uacute;blico: ____________________________.</font></font></font></p>
<p style="margin-left: 0.39in; margin-bottom: 0in; line-height: 100%"> 
<br /> 
<font face="Times New Roman, serif"><font style="font-size: 12pt"><font face="Arial, serif">Unidad de investigaci&oacute;n: </font><b><font face="Arial, serif">_______________________________</font></b></font></font></p>
<p style="margin-left: 0.39in; margin-bottom: 0in; line-height: 100%"> 
<br /> 
<font face="Times New Roman, serif"><font style="font-size: 12pt"><font face="Arial, serif">Domicilio de la unidad: </font><b><font face="Arial, serif">_________________________________</font></b><font face="Arial, serif">.</font></font></font></p>
<p style="margin-left: 0.39in; margin-bottom: 0in; line-height: 100%"> 
&nbsp;</p>
<p style="margin-left: 0.39in; margin-bottom: 0in; line-height: 100%"> 
<font face="Times New Roman, serif"><font style="font-size: 12pt"><font face="Arial, serif">Lugar de los hechos: ___________________________________________<br /> 
</font></font></font></p>
<p align="justify" style="margin-left: 0.39in; margin-bottom: 0.14in; line-height: 115%"> 
<a name="_GoBack"></a> <font color="#000000"><font face="Arial, serif"><font style="font-size: 12pt">Con fundamento en lo establecido en el numeral 21 de la Constituci&oacute;n Pol&iacute;tica de los Estados Unidos Mexicanos, </font></font></font><font face="Arial, serif"><font style="font-size: 12pt">en ejercicio de las facultades previstas por los art&iacute;culos 1, 2, 6, 7, 9, 61, 62, 63, 132 y 133 de la Ley del Sistema Integral de Justicia para Adolescentes del Estado de Coahuila, </font></font><font color="#000000"><font face="Arial, serif"><font style="font-size: 12pt">en relaci&oacute;n con los art&iacute;culos 131, 221 del C&oacute;digo Nacional de Procedimientos Penales; en atenci&oacute;n a ____________________________, mediante el cual informa que____________________________________________________________________________________________________________________________________________; en vista de lo anterior, se acuerda formar la carpeta de investigaci&oacute;n respectiva en contra de_______________________________________________, en la comisi&oacute;n de una conducta tipificada como el delito de ________________________, cometido en agravio de _____________________________;&nbsp; reg&iacute;strese en el libro de gobierno bajo en n&uacute;mero estad&iacute;stico que por n&uacute;mero progresivo corresponda; g&iacute;rese oficio a la Polic&iacute;a Especializada, a efecto de que se avoquen a investigar los hechos motivo de la presente carpeta de investigaci&oacute;n; as&iacute; mismo,&nbsp; deber&aacute;n recabar todos aquellos datos, indicios y circunstancias que permitan a esta autoridad realizar el esclarecimiento de los hechos; pract&iacute;quense las actuaciones necesarias y conducentes para ello; una vez hecho lo anterior, determine lo procedente. As&iacute; lo acord&oacute; y firma&nbsp; el Licenciado (a) _____________________________, Agente del Ministerio P&uacute;blico Especializado Adscrito a </font></font></font><b><font color="#000000"><font face="Arial, serif"><font style="font-size: 12pt">______________________.</font></font></font></b></p>
<p align="center" style="margin-left: 0.98in; margin-top: 0.19in; margin-bottom: 0.19in; line-height: 100%"> 
<br /> 
<font face="Times New Roman, serif"><font style="font-size: 12pt"><font face="Arial, serif"><b>ATENTAMENTE</b></font></font></font></p>
<p align="center" style="margin-left: 0.98in; margin-top: 0.19in; margin-bottom: 0.19in; line-height: 100%"> 
<br /> 
<font face="Times New Roman, serif"><font style="font-size: 12pt"><font face="Arial, serif"><b>AGENTE DEL MINISTERIO P&Uacute;BLICO</b></font></font></font></p>
<p align="center" style="margin-left: 0.98in; margin-top: 0.19in; margin-bottom: 0.19in; line-height: 100%"> 
<br /> 
<font face="Times New Roman, serif"><font style="font-size: 12pt"><font face="Arial, serif"><b>(ADSCRIPCI&Oacute;N, NOMBRE Y FIRMA)</b></font></font></font></p>
'
    ,
    'ACUERDO DE INICIO SIN DETENIDO ADOLESCENTE'
  )
  ,
  (
    'ACUERDO DE LIBERTAD DEL ADOLESCENTE DELITO NO GRAVE',
    1611,
    '<p align="center" style="margin-bottom: 0in; line-height: 200%"> 
<font face="Arial, serif"><font style="font-size: 14pt">ACUERDO DE LIBERTAD POR CONDUCTA</font></font></p>
<p align="center" style="margin-bottom: 0in; line-height: 200%"> 
<font face="Arial, serif"><font style="font-size: 14pt">TIPIFICADA COMO DELITO NO GRAVE</font></font></p>
<p align="center" style="margin-left: 0.98in; margin-top: 0.19in; margin-bottom: 0.19in; line-height: 200%"> 
<br /> 
&nbsp;</p>
<p align="right" style="margin-left: 0.98in; margin-top: 0.19in; margin-bottom: 0.19in; line-height: 200%"> 
<font face="Times New Roman, serif"><font style="font-size: 12pt"><font face="Arial, serif"><font style="font-size: 14pt">N&uacute;m. Carpeta de investigaci&oacute;n.:________________<br /> 
N&deg;. Expediente: </font></font><b><font face="Arial, serif"><font style="font-size: 14pt">_________________</font></font></b></font></font></p>
<p style="margin-left: 0.39in; margin-bottom: 0in; line-height: 200%"> 
&nbsp;</p>
<p style="margin-left: 0.39in; margin-bottom: 0in; line-height: 200%"> 
<font face="Times New Roman, serif"><font style="font-size: 12pt"><font face="Arial, serif"><font style="font-size: 14pt">Ciudad: </font></font><b><font face="Arial, serif"><font style="font-size: 14pt">___________</font></font></b><font face="Arial, serif"><font style="font-size: 14pt">&nbsp; Estado: ________________<br /> 
Fecha: </font></font><b><font face="Arial, serif"><font style="font-size: 14pt">________________________</font></font></b><font face="Arial, serif"><font style="font-size: 14pt">&nbsp; Hora: __________<br /> 
Agente del Ministerio P&uacute;blico: _________________________________.<br /> 
Unidad de investigaci&oacute;n: </font></font><b><font face="Arial, serif"><font style="font-size: 14pt">________________________</font></font></b><br /> 
<font face="Arial, serif"><font style="font-size: 14pt"> Domicilio de la unidad: ______________________________________.<br /> 
</font></font></font></font></p>
<p align="justify" style="margin-left: 0.39in; margin-top: 0.19in; margin-bottom: 0.17in; line-height: 200%"> 
<font face="Times New Roman, serif"><font style="font-size: 12pt"><font face="Arial, serif"><font style="font-size: 14pt">Con fundamento en lo establecido en los numerales </font></font><font color="#000000"><font face="Arial, serif"><font style="font-size: 14pt">16 y 21 de la</font></font></font><font color="#0070c0"><font face="Arial, serif"><font style="font-size: 14pt"><b> </b></font></font></font><font color="#000000"><font face="Arial, serif"><font style="font-size: 14pt">Constituci&oacute;n Pol&iacute;tica de los Estados Unidos Mexicanos, </font></font></font><font face="Arial, serif"><font style="font-size: 14pt">en ejercicio de las facultades previstas por los art&iacute;culos 1, 2, 6, 7, 9, 61, 62, 63 132, 133 y 135 de la Ley del Sistema Integral de Justicia para Adolescentes del Estado de Coahuila,</font></font><font color="#000000"><font face="Arial, serif"><font style="font-size: 14pt"> en relaci&oacute;n con los art&iacute;culos&nbsp;146, 147, 148 y 149 del C&oacute;digo Nacional de Procedimientos Penales; en atenci&oacute;n a: El Parte Informativo____________________________ (n&uacute;mero) de fecha ___________________, suscrito por______________________________, mediante el cual ponen a disposici&oacute;n en calidad de detenido al adolescente que responde al nombre de _________________________________________________, por su probable participaci&oacute;n en una conducta tipificada como el delito de __________________________; del que se desprende que la conducta que se atribuye fue cometida por el adolescente en cita no se encuentra tipificada como delito grave por el art&iacute;culo 97 de la </font></font></font><font face="Arial, serif"><font style="font-size: 14pt">Ley del Sistema Integral de Justicia para Adolescentes del Estado de Coahuila, ( O EN SU CASO CULPOSO) y con la finalidad de dar cumplimiento a los numerales</font></font><font color="#000000"><font face="Arial, serif"><font style="font-size: 14pt"> 135 cuarto p&aacute;rrafo y 138 de la </font></font></font><font face="Arial, serif"><font style="font-size: 14pt">Ley del Sistema Integral de Justicia para Adolescentes del Estado de Coahuila</font></font><font color="#000000"><font face="Arial, serif"><font style="font-size: 14pt">, se ordena dejar en inmediata libertad al adolescente, para tal efecto g&iacute;rese oficio al Director de la Polic&iacute;a&nbsp; ______________, a fin de que traslade a estas oficinas al adolescente de referencia&nbsp; a fin de notificarle el presente acuerdo. De igual modo, </font></font></font><font face="Arial, serif"><font style="font-size: 14pt">se comunica a los padres o encargados del adolescente el presente acuerdo, as&iacute; como se le hace entrega del adolescente, con la obligaci&oacute;n de que quien lo representa deber&aacute; presentarlo ante la autoridad competente cuando sean requeridos</font></font><font color="#000000"><font face="Arial, serif"><font style="font-size: 14pt">. As&iacute; lo acord&oacute; y firma&nbsp; el Licenciado (a) _____________________________, Agente del Ministerio P&uacute;blico Especializado.</font></font></font></font></font></p>
<p align="center" style="margin-left: 0.39in; margin-top: 0.19in; margin-bottom: 0.17in; line-height: 200%"> 
<br /> 
<font face="Times New Roman, serif"><font style="font-size: 12pt"><font color="#000000"><font face="Arial, serif"><font style="font-size: 14pt">ATENTAMENTE</font></font></font></font></font></p>
<p align="center" style="margin-left: 0.98in; margin-top: 0.19in; margin-bottom: 0.19in; line-height: 200%"> 
<font face="Times New Roman, serif"><font style="font-size: 12pt"><font color="#000000"><font face="Arial, serif"><font style="font-size: 14pt">AGENTE DEL MINISTERIO P&Uacute;BLICO ESPECIALIZADO<br /> 
</font></font></font></font></font><br /> 
&nbsp;</p>
<p align="center" style="margin-left: 0.98in; margin-top: 0.19in; margin-bottom: 0.19in; line-height: 200%"> 
<a name="_GoBack"></a> <font face="Times New Roman, serif"><font style="font-size: 12pt"><font color="#000000"><font face="Arial, serif"><font style="font-size: 14pt">LIC.</font></font></font></font></font></p>
'
    ,
    'ACUERDO DE LIBERTAD DEL ADOLESCENTE DELITO NO GRAVE'
  )
  ,
  (
    'ACUERDO DE LIBERTAD',
    1611,
    '<table align="right" border="0" cellpadding="1" cellspacing="1" height="85" width="622"> 
<tbody>  
<tr>   
<td style="text-align: right;" width="50%">    
<img alt="" src="http://gitlab.lucasianmexico.com:9080/imagenes/imagen/plantilla/logo_pgje.png" style="width: 100px; height: 80px;" /></td>  
</tr> 
</tbody>
</table>
<p> 
&nbsp;</p>
<p> 
&nbsp;</p>
<p> 
&nbsp;</p>
<p> 
&nbsp;</p>
<p> 
&nbsp;</p>
<p style="margin-left: 70.8pt; text-align: right;"> 
<span style="font-family:arial,helvetica,sans-serif;"><span style="font-size: 14px;"><strong>ACUERDO DE LIBERTAD</strong></span></span></p>
<p align="center" style="margin-left:70.8pt;"> 
&nbsp;</p>
<p align="center" style="margin-left:70.8pt;"> 
&nbsp;</p>
<p align="right" style="margin-left:70.8pt;"> 
<span style="font-family:arial,helvetica,sans-serif;"><span style="font-size: 14px;">N.U.C.:<strong>COA/FG/XX/___/___/__________</strong><br /> 
N&deg;. Expediente: <strong>&lt;numeroExpediente&gt; </strong></span></span></p>
<p style="margin-left:70.8pt;"> 
<span style="font-family:arial,helvetica,sans-serif;"><span style="font-size: 14px;">Ciudad: <strong>___________</strong>&nbsp; Estado:<strong>&lt;estado&gt;</strong><br /> 
&nbsp;Fecha:</span><strong><span style="font-size: 14px;">&lt;diaActual&gt; de &lt;nombreMesActual&gt; del &lt;anioActual&gt;</span>&nbsp;</strong><span style="font-size: 14px;"> Hora:<strong>&lt;horaActual&gt;</strong><br /> 
&nbsp;Agente del Ministerio P&uacute;blico: _________________________________.<br /> 
Unidad de investigaci&oacute;n: <strong>________________________</strong><br /> 
Domicilio de la unidad: ______________________________________.<br /> 
Lugar de los hechos: _____________________________________________________</span></span></p>
<p style="margin-left:70.8pt;"> 
&nbsp;</p>
<p style="margin-left: 70.8pt; text-align: justify;"> 
<span style="font-family:arial,helvetica,sans-serif;"><span style="font-size: 14px;">Con fundamento en lo establecido en los numerales 16 y 21 de la Constituci&oacute;n Pol&iacute;tica de los Estados Unidos Mexicanos en relaci&oacute;n con los art&iacute;culos&nbsp;146, 147, 148 y 149 del C&oacute;digo Nacional de Procedimientos Penales; en atenci&oacute;n a: El Parte Informativo____________________________ (n&uacute;mero) de fecha ___________________, suscrito por______________________________, mediante el cual ponen a disposici&oacute;n en calidad de detenido a la persona que corresponde al nombre de <strong>&lt;nombreProbableParticipe&gt; &lt;apellidoPaternoProbableParticipe&gt; &lt;apellidoMaternoProbableParticipe&gt; </strong>, por su probable participaci&oacute;n en hechos que revisten&nbsp; el car&aacute;cter del delito de<strong> &lt;listaDelitos&gt;</strong>; del que se desprende que (est&aacute;n a punto de cumplirse las 48 horas con que cuenta el ministerio p&uacute;blico para resolver la situaci&oacute;n jur&iacute;dica del indiciado, &oacute;&nbsp; se trata de&nbsp; delito que no merece pena privativa de la libertad (reservas de ley), &oacute; no se acredita ninguno de los supuestos de la flagrancia contenidos en el numeral 146 del C&oacute;digo Nacional de Procedimientos Penales, (&oacute; al ser un delito perseguidle por querella en t&eacute;rminos del art&iacute;culo 148 del C&oacute;digo Nacional de Procedimientos Penales, se otorg&oacute; el perd&oacute;n a favor del imputado.)&nbsp;&nbsp;&nbsp;&nbsp; _________________________________________________________________, y con fundamento en lo dispuesto en los art&iacute;culos 16 de la Constituci&oacute;n Pol&iacute;tica de los Estados Unidos Mexicanos, 146 y 149 del C&oacute;digo Nacional de Procedimientos Penales, se ordena dejar sin efecto la detenci&oacute;n del indiciado _______________________,&nbsp;En tal virtud, deber&aacute; de dejarse en inmediata libertad al mismo,&nbsp; para tal efecto g&iacute;rese oficio al Director de la Polic&iacute;a&nbsp; ______________; a fin de que traslade a estas oficinas al indiciado de referencia&nbsp; a fin de notificarle el presente acuerdo.&nbsp; ______________________________, &nbsp;<br /> 
en vista de lo anterior, se acuerda formar la carpeta de investigaci&oacute;n respectiva en contra de_____________________, por la comisi&oacute;n del hecho que la ley se&ntilde;ala como el delito de _______________________, cometido en agravio de _____________________________;&nbsp; reg&iacute;strese en el libro de gobierno bajo en n&uacute;mero estad&iacute;stico que por n&uacute;mero progresivo corresponda; g&iacute;rese oficio al Comandante de la Polic&iacute;a de Investigadora del Estado, a efecto de que ordene a elementos a su mando se avoquen a investigar los hechos motivo de la presente carpeta de investigaci&oacute;n; as&iacute; mismo,&nbsp; deber&aacute;n recabar todos aquellos datos, indicios y circunstancias que permitan a esta autoridad realizar el esclarecimiento de los hechos; pract&iacute;quense las actuaciones necesarias y conducentes para ello; una vez hecho lo anterior, determine lo procedente. As&iacute; lo acord&oacute; y firma&nbsp; el Licenciado (a) <span style="font-size:14px;"><span style="font-family: arial,helvetica,sans-serif;"><strong>&lt;nombreResponsableTramite&gt; &lt;apellidoPaternoResponsableTramite&gt; &lt;apellidoMaternoResponsableTramite&gt;</strong></span></span>, Agente del Ministerio P&uacute;blico Adscrito a <strong>____________________________________</strong>.</span></span><br /> 
<br /> 
&nbsp;</p>
<p align="center" style="margin-left:70.8pt;"> 
<span style="font-family:arial,helvetica,sans-serif;"><span style="font-size: 14px;">ATENTAMENTE</span></span></p>
<p align="center" style="margin-left:70.8pt;"> 
<span style="font-family:arial,helvetica,sans-serif;"><span style="font-size: 14px;">AGENTE DEL MINISTERIO P&Uacute;BLICO<br /> 
(ADSCRIPCI&Oacute;N, NOMBRE Y FIRMA)</span></span></p>
<p align="center" style="margin-left:70.8pt;"> 
&nbsp;</p>
<p align="center" style="margin-left:70.8pt;"> 
<span style="font-size:14px;"><span style="font-family: arial,helvetica,sans-serif;"><strong>LIC.&lt;nombreResponsableTramite&gt; &lt;apellidoPaternoResponsableTramite&gt; &lt;apellidoMaternoResponsableTramite&gt;</strong></span></span></p>
'
    ,
    'ACUERDO DE LIBERTAD'
  )
  ,
  (
    'CITATORIO A VICTIMA',
    1611,
    '<p align="right" style="margin-bottom: 0.14in; line-height: 200%"> 
<font color="#0070c0"><font face="Arial, serif"><font style="font-size: 12pt"><b>CITATORIO A VICTIMA Y/O OFENDIDO</b></font></font></font></p>
<p align="right" style="margin-left: 0.98in; margin-top: 0.19in; margin-bottom: 0.19in; line-height: 200%"> 
<font face="Arial, serif"><font style="font-size: 12pt">N&uacute;m. Carpeta de investigaci&oacute;n.:________________<br /> 
N&deg;. Expediente: </font></font><font face="Arial, serif"><font style="font-size: 12pt"><b>______________________</b></font></font></p>
<p align="right" style="margin-left: 0.98in; margin-top: 0.19in; margin-bottom: 0.17in; line-height: 200%"> 
<br /> 
<font face="Arial, serif"><font style="font-size: 12pt">&nbsp;&nbsp;&nbsp;&nbsp;</font></font><font face="Arial, serif"><font style="font-size: 12pt"><b> ASUNTO: EL QUE SE INDICA</b></font></font></p>
<p style="margin-left: 0.98in; margin-top: 0.19in; margin-bottom: 0.19in; line-height: 200%"> 
<font face="Arial, serif"><font style="font-size: 12pt">C. _________________________<br /> 
DOMICILIO _________________<br /> 
CIUDAD ____________________<br /> 
P R E S E N T E.-</font></font></p>
<p align="right" style="margin-left: 0.98in; margin-top: 0.19in; margin-bottom: 0.19in; line-height: 200%"> 
<br /> 
<font face="Arial, serif"><font style="font-size: 12pt">Lugar y fecha: </font></font><font face="Arial, serif"><font style="font-size: 12pt"><b>_____________________________________</b></font></font></p>
<p align="justify" style="margin-left: 0.98in; margin-top: 0.19in; margin-bottom: 0.17in; line-height: 200%"> 
<br /> 
<font face="Arial, serif"><font style="font-size: 12pt">Se le notifica que deber&aacute; comparecer ante esta autoridad, en las instalaciones&nbsp; ubicadas en </font></font><font face="Arial, serif"><font style="font-size: 12pt"><b>&lt;direcci&oacute;n&gt;</b></font></font><font face="Arial, serif"><font style="font-size: 12pt">, lugar en el que se encuentra la Unidad de Investigaci&oacute;n de </font></font><font face="Arial, serif"><font style="font-size: 12pt"><b>_________________</b></font></font><font face="Arial, serif"><font style="font-size: 12pt"> </font></font><font face="Arial, serif"><font style="font-size: 12pt">de la Procuradur&iacute;a General de Justicia del Estado,</font></font><font face="Arial, serif"><font style="font-size: 12pt"> el d&iacute;a _________________ a las ______________horas,&nbsp; con motivo de la pr&aacute;ctica de una diligencia de car&aacute;cter penal&nbsp; en calidad de </font></font><font face="Arial, serif"><font style="font-size: 12pt"><b>VICTIMA U OFENDIDO</b></font></font><font face="Arial, serif"><font style="font-size: 12pt">, para ello tendr&aacute; que presentar&nbsp; identificaci&oacute;n con fotograf&iacute;a.<br /> 
<br /> 
En caso de impedimento, deber&aacute; comunicarlo puntualmente a esta autoridad por cualquier v&iacute;a, para ello se le proporciona el tel&eacute;fono de la Agencia del Ministerio P&uacute;blico&nbsp; __________________ y justifique inmediatamente el motivo de su incomparecencia. Se le hace saber&nbsp; que en caso de no asistir de manera injustificada, se le impondr&aacute; la medida de apremio&nbsp; que para tal efecto determine la autoridad correspondiente. &nbsp;<br /> 
</font></font><br /> 
<font color="#000000"><font face="Arial, serif"><font style="font-size: 12pt"> Lo&nbsp; anterior con fundamento en lo establecido en el art&iacute;culo 63 fracci&oacute;n XXV de la</font></font></font><font face="Arial, serif"><font style="font-size: 12pt"> Ley del Sistema Integral de Justicia para Adolescentes en relaci&oacute;n con </font></font><font color="#000000"><font face="Arial, serif"><font style="font-size: 12pt">los art&iacute;culos 82, 90 y 104 fracciones I del C&oacute;digo Nacional de Procedimientos Penales.</font></font></font></p>
<p align="center" style="margin-left: 0.98in; margin-top: 0.19in; margin-bottom: 0.19in; line-height: 200%"> 
<br /> 
<br /> 
<font face="Arial, serif"><font style="font-size: 12pt"><b>A T E N T A M E N T E</b></font></font></p>
<p align="center" style="margin-bottom: 0.14in; line-height: 200%"> 
<br /> 
<font face="Arial, serif"><font style="font-size: 12pt"><b>AGENTE DEL MINISTERIO P&Uacute;BLICO<br /> 
<br /> 
(ADSCRIPCI&Oacute;N, NOMBRE Y FIRMA</b></font></font></p>
<p align="center" style="margin-bottom: 0.14in; line-height: 200%"> 
<br /> 
&nbsp;</p>
<p align="center" style="margin-bottom: 0.14in; line-height: 200%"> 
<br /> 
&nbsp;</p>
<p align="center" style="margin-bottom: 0.14in; line-height: 200%"> 
<br /> 
&nbsp;</p>
<p align="center" style="margin-bottom: 0.14in; line-height: 200%"> 
<br /> 
&nbsp;</p>
<p align="center" style="margin-bottom: 0.14in; line-height: 200%"> 
<br /> 
&nbsp;</p>
<p style="margin-bottom: 0.14in; line-height: 200%"> 
<a name="_GoBack"></a> <font face="Arial, serif"><font style="font-size: 12pt"><b>Quien recibe: _______________________________________</b></font></font></p>
<p style="margin-bottom: 0.14in; line-height: 200%"> 
<font face="Arial, serif"><font style="font-size: 12pt"><b>Fecha y hora: _______________________________________</b></font></font></p>
<p style="margin-bottom: 0.14in; line-height: 200%"> 
<font face="Arial, serif"><font style="font-size: 12pt"><b>Incidencias: _________________________________________</b></font></font></p>
<p align="center" style="margin-bottom: 0.14in; line-height: 200%"> 
<br /> 
&nbsp;</p>'
    ,
    'CITATORIO A VICTIMA'
  )
  ,
  (
    'COMPARECENCIA DE OTORGAMIENTO DE PERDON',
    1611,
    '<table align="right" border="0" cellpadding="1" cellspacing="1" height="85" width="622"> 
<tbody>  
<tr>   
<td style="text-align: right;" width="50%">    
<img alt="" src="http://gitlab.lucasianmexico.com:9080/imagenes/imagen/plantilla/logo_pgje.png" style="width: 100px; height: 80px;" /></td>  
</tr> 
</tbody>
</table>
<p style="margin-left: 60pt; text-align: right;"> 
&nbsp;</p>
<p style="margin-left: 60pt; text-align: right;"> 
&nbsp;</p>
<p style="margin-left: 60pt; text-align: right;"> 
&nbsp;</p>
<p style="margin-left: 60pt; text-align: right;"> 
&nbsp;</p>
<p align="center" style="margin-left:60.0pt;"> 
<span style="font-size:14px;"><span style="font-family: arial,helvetica,sans-serif;"><strong>COMPARECENCIA DE OTORGAMIENTO DE PERD&Oacute;N</strong></span></span></p>
<p align="center" style="margin-left:60.0pt;"> 
&nbsp;</p>
<p align="right" style="margin-left:2.0cm;"> 
<span style="font-size:14px;"><span style="font-family: arial,helvetica,sans-serif;">N.U.C: <strong>COA/FG/XX/___/___/__________</strong></span></span></p>
<p align="right" style="margin-left:2.0cm;"> 
<span style="font-size:14px;"><span style="font-family: arial,helvetica,sans-serif;">N&uacute;m. de Expediente:<strong> &lt;numeroExpediente&gt;</strong></span></span></p>
<p align="right" style="margin-left:2.0cm;"> 
&nbsp;</p>
<p style="margin-left:2.0cm;"> 
<span style="font-size:14px;"><span style="font-family: arial,helvetica,sans-serif;">Ciudad: _________ de&nbsp; <strong>&lt;estado&gt;</strong></span></span></p>
<p style="margin-left:2.0cm;"> 
<span style="font-size:14px;"><span style="font-family: arial,helvetica,sans-serif;">Fecha:&nbsp;&nbsp;<strong> &lt;diaActual&gt; de &lt;nombreMesActual&gt; del &lt;anioActual&gt; </strong><br /> 
Hora: <strong>&lt;horaActual&gt;</strong></span></span></p>
<p style="margin-left:2.0cm;"> 
<span style="font-size:14px;"><span style="font-family: arial,helvetica,sans-serif;">Agente del Ministerio P&uacute;blico: <strong>LIC.&lt;nombreResponsableTramite&gt; &lt;apellidoPaternoResponsableTramite&gt; &lt;apellidoMaternoResponsableTramite&gt;</strong></span></span></p>
<p style="margin-left:2.0cm;"> 
<span style="font-size:14px;"><span style="font-family: arial,helvetica,sans-serif;">Unidad de investigaci&oacute;n: _____________________________</span></span></p>
<p style="margin-left:2.0cm;"> 
<span style="font-size:14px;"><span style="font-family: arial,helvetica,sans-serif;">Domicilio de la unidad: _______________________________</span></span></p>
<p style="margin-left:2.0cm;"> 
&nbsp;</p>
<p style="margin-left: 60pt; text-align: justify;"> 
<span style="font-size:14px;"><span style="font-family: arial,helvetica,sans-serif;">Ante el Agente del Ministerio P&uacute;blico Adscrito a ___________________________________, comparece quien dijo llamarse _____________________________ (nombre del testigo), quien se identifica con ________________________________(datos de la identificaci&oacute;n ), mismo que en su margen izquierdo cuenta con fotograf&iacute;a que coincide con todos los rasgos faciales del compareciente y en este acto se le devuelve, previo cotejo con la copia simple que anexa de la misma para que obre dentro de la presente carpeta de investigaci&oacute;n, esta Representaci&oacute;n Social le hace del conocimiento del contenido de los art&iacute;culos 358, 359, 360 y 362 del C&oacute;digo de Nacional de Procedimientos Penales, enseguida se le protesta en t&eacute;rminos del art&iacute;culo 49 del mismo ordenamiento<strong>,</strong> para que se conduzca con verdad en todo lo que va a manifestar y advertido de las penas que se imponen a los que declarar con falsedad ante una autoridad, quien enterado de lo anterior por sus generales manifiesta, llamarse como ha quedado escrito ser originario de ____________________ (poblaci&oacute;n)&nbsp; y vecino de __________________________(poblaci&oacute;n), con domicilio en _________________________________(calle, n&uacute;mero, colonia, poblaci&oacute;n, municipio y estado), quien cuenta con ________________________________(a&ntilde;os de edad), de estado civil ____________________________, de ocupaci&oacute;n ______________________________, que si sabe leer y escribir por haber cursado instrucci&oacute;n _______________________(escolaridad), quien proporciona el n&uacute;mero telef&oacute;nico _______________________________, para ser localizado,&nbsp; y si tiene alg&uacute;n v&iacute;nculo o relaci&oacute;n con el indiciado _______________________________.&nbsp; Sin m&aacute;s generales que agregar en relaci&oacute;n a su comparecencia. As&iacute; mismo, esta Representaci&oacute;n Social le hace del conocimiento del contenido de los art&iacute;culos 20, apartado C, de la Constituci&oacute;n Pol&iacute;tica de los Estados Unidos Mexicanos y 109 del C&oacute;digo Nacional de Procedimientos Penales, que consagra los derechos de la v&iacute;ctima u ofendido, por lo que, <strong>MANIFIESTA</strong>: Comparezco ante esta Representaci&oacute;n Social de manera libre y voluntaria a otorgar el <strong>PERD&Oacute;N</strong> <strong>M&Aacute;S AMPLIO QUE EN DERECHO CORRESPONDA</strong> en favor de <strong>______________________</strong> por su probable comisi&oacute;n o participaci&oacute;n en la comisi&oacute;n de hechos constitutivos del delito de <strong>&lt;listaDelitos&gt;.</strong> En atenci&oacute;n a lo anterior, una vez escuchada la manifestaci&oacute;n del (la) compareciente, &nbsp;se le&nbsp; informa el significado y consecuencias jur&iacute;dicas del otorgamiento del perd&oacute;n en los delitos de querella; como lo es la extinci&oacute;n de la acci&oacute;n penal que motivar&aacute; que el Ministerio P&uacute;blico determine el no ejercicio de la misma, o que el juzgador sobresea el proceso, seg&uacute;n el caso. De conformidad con lo dispuesto en el art&iacute;culo 169 del C&oacute;digo Penal del Estado de Coahuila, en relaci&oacute;n con los art&iacute;culos 109 y 485, fracci&oacute;n IV, del C&oacute;digo Nacional de Procedimientos Penales. Por lo que una vez que se le ha hecho saber al compareciente lo anterior <strong>MANIFIESTA:</strong> Reitero mi deseo de otorgar el perd&oacute;n tal y como qued&oacute; asentado en l&iacute;neas anteriores, tambi&eacute;n me doy por enterado (a) de las consecuencias jur&iacute;dicas que ello implica. Adem&aacute;s solicito se archive definitivamente la presente carpeta de investigaci&oacute;n al ser un asunto totalmente concluido.---------------------------------------------------------------------------------------------------------------</span></span></p>
<p style="margin-left:60.0pt;"> 
&nbsp;</p>
<p align="center" style="margin-left:60.0pt;"> 
<span style="font-size:14px;"><span style="font-family: arial,helvetica,sans-serif;"><strong>AGENTE DEL MINISTERIO P&Uacute;BLICO</strong></span></span></p>
<p align="center" style="margin-left:60.0pt;"> 
<span style="font-size:14px;"><span style="font-family: arial,helvetica,sans-serif;"><strong>UNIDAD INVESTIGADORA DE ________</strong></span></span></p>
<p align="center" style="margin-left:60.0pt;"> 
<span style="font-size:14px;"><span style="font-family: arial,helvetica,sans-serif;"><strong>(NOMBRE Y FIRMA DE LA VICTIMA Y/O OFENDIDO)</strong></span></span></p>
<p> 
&nbsp;</p>'
    ,
    'COMPARECENCIA DE OTORGAMIENTO DE PERDON'
  )
  ,
  (
    'DENUNCIA',
    1611,
    '<p align="center" style="margin-left: 0.98in; margin-top: 0.19in; margin-bottom: 0.19in; line-height: 100%"> 
<font face="Times New Roman, serif"><font style="font-size: 12pt"><b><font face="Arial, serif"><font style="font-size: 11pt">DENUNCIA DE:</font></font></b></font></font></p>
<p align="center" style="margin-left: 0.98in; margin-top: 0.19in; margin-bottom: 0.19in; line-height: 100%"> 
<br /> 
&nbsp;</p>
<p align="right" style="margin-left: 0.98in; margin-top: 0.19in; margin-bottom: 0.19in; line-height: 100%"> 
<font face="Times New Roman, serif"><font style="font-size: 12pt"><font face="Arial, serif"><font style="font-size: 11pt">NUC:&nbsp;________________<br /> 
N&deg; Expediente: </font></font><b><font face="Arial, serif"><font style="font-size: 11pt">________________</font></font></b></font></font></p>
<p style="margin-left: 0.39in; margin-top: 0.19in; margin-bottom: 0.19in; line-height: 100%"> 
<font face="Times New Roman, serif"><font style="font-size: 12pt"><font face="Arial, serif"><font style="font-size: 11pt">Ciudad: </font></font><b><font face="Arial, serif"><font style="font-size: 11pt">______________</font></font></b><font face="Arial, serif"><font style="font-size: 11pt">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; Estado: _____________<br /> 
Fecha: </font></font><b><font face="Arial, serif"><font style="font-size: 11pt">______________________</font></font></b><font face="Arial, serif"><font style="font-size: 11pt"> Hora: </font></font><b><font face="Arial, serif"><font style="font-size: 11pt">_________________</font></font></b><br /> 
<font face="Arial, serif"><font style="font-size: 11pt"> Agente del Ministerio P&uacute;blico: _________________________________.<br /> 
Unidad de investigaci&oacute;n: ____________________________________<br /> 
Domicilio de la unidad: ______________________________________.</font></font></font></font></p>
<p align="justify" style="margin-left: 0.39in; margin-top: 0.19in; margin-bottom: 0.19in; line-height: 100%"> 
<br /> 
<font face="Times New Roman, serif"><font style="font-size: 12pt"><font face="Arial, serif"><font style="font-size: 11pt">Ante el Agente del Ministerio P&uacute;blico Especializado </font></font><b><font face="Arial, serif"><font style="font-size: 11pt">________________________</font></font></b><font face="Arial, serif"><font style="font-size: 11pt">, comparece quien dijo llamarse _____________________________________, quien se identifica con </font></font><b><font face="Arial, serif"><font style="font-size: 11pt">____________________</font></font></b><font face="Arial, serif"><font style="font-size: 11pt">,</font></font><b><font face="Arial, serif"><font style="font-size: 11pt">_______________________</font></font></b><font face="Arial, serif"><font style="font-size: 11pt">, mismo que en su parte frontal margen izquierdo cuenta con fotograf&iacute;a que coincide con todos los rasgos faciales del compareciente y en este acto se le devuelve, previo cotejo con la copia simple que anexa de la misma para que obre dentro de la presente carpeta de investigaci&oacute;n, en ejercicio de las facultades previstas por los art&iacute;culos 1, 2, 6, 7, 9, 61, 62, 63, 132 de la Ley del Sistema Integral de Justicia para Adolescentes del Estado de Coahuila, se procede a enterar de los derechos que </font></font><font face="Arial, serif"><span lang="es-ES">la</span></font><font face="Arial, serif"><font style="font-size: 11pt"><span lang="es-ES"> Constituci&oacute;n </span></font></font><font face="Arial, serif"><span lang="es-ES">Pol&iacute;tica de los Estados Unidos Mexicanos, la Constituci&oacute;n Pol&iacute;tica del Estado de Coahuila, </span></font><font face="Arial, serif"><font style="font-size: 11pt"><span lang="es-ES">los tratados y otras leyes secundarias que de aqu&eacute;l</span></font></font><font face="Arial, serif"><span lang="es-ES">las emanen, as&iacute; como el art&iacute;culo 53 de la </span></font><font face="Arial, serif"><font style="font-size: 11pt">Ley del Sistema Integral de Justicia para Adolescentes del Estado de Coahuila,</font></font><font face="Arial, serif"> la cual le otorgan a la v&iacute;ctima u ofendido los siguientes derechos: </font><font face="Arial, serif"><b>I</b></font><font face="Arial, serif"><font style="font-size: 11pt"><b>.</b></font></font><font face="Arial, serif"><font style="font-size: 11pt"> A ser informados desde la primera ocasi&oacute;n en que se tenga contacto con ellos, acerca de los derechos que en su favor le reconoce la Constituci&oacute;n Pol&iacute;tica de los Estados Unidos Mexicanos, los Tratados Internacionales celebrados y aprobados por el Estado mexicano, esta ley y dem&aacute;s disposiciones aplicables; </font></font><font face="Arial, serif"><font style="font-size: 11pt"><b>II.</b></font></font><font face="Arial, serif"><font style="font-size: 11pt"> A recibir trato sin discriminaci&oacute;n, a fin de evitar que se atente contra la dignidad humana y se anulen o menoscaben sus derechos y libertades, por lo que la protecci&oacute;n de sus derechos se har&aacute; sin distinci&oacute;n alguna; </font></font><font face="Arial, serif"><font style="font-size: 11pt"><b>III.</b></font></font><font face="Arial, serif"><font style="font-size: 11pt"> A acceder a la justicia de manera pronta, gratuita e imparcial respecto de sus denuncias o querellas; </font></font><font face="Arial, serif"><font style="font-size: 11pt"><b>IV.</b></font></font><font face="Arial, serif"><font style="font-size: 11pt"> A ser tratado con respeto y dignidad; </font></font><font face="Arial, serif"><font style="font-size: 11pt"><b>V.</b></font></font><font face="Arial, serif"><font style="font-size: 11pt"> A que se le reconozca su calidad de parte durante todo el procedimiento; </font></font><font face="Arial, serif"><font style="font-size: 11pt"><b>VI.</b></font></font><font face="Arial, serif"><font style="font-size: 11pt"> A que el Ministerio P&uacute;blico y sus auxiliares, as&iacute; como las autoridades jurisdiccionales le faciliten el acceso a la justicia y le presenten los servicios que constitucionalmente tienen encomendados con legalidad, honradez, lealtad, imparcialidad, profesionalismo, eficiencia y eficacia, y con la debida diligencia; </font></font><font face="Arial, serif"><font style="font-size: 11pt"><b>VII.</b></font></font><font face="Arial, serif"><font style="font-size: 11pt"> A contar con informaci&oacute;n sobre los derechos que en su beneficio existan, como ser atendidos por personal del mismo sexo o del sexo que la v&iacute;ctima; </font></font><font face="Arial, serif"><font style="font-size: 11pt"><b>VIII.</b></font></font><font face="Arial, serif"><font style="font-size: 11pt"> A comunicarse, inmediatamente despu&eacute;s de haberse cometido la conducta tipificada como delito, con un familiar y/o con su asesor jur&iacute;dico; </font></font><font face="Arial, serif"><font style="font-size: 11pt"><b>IX. </b></font></font><font face="Arial, serif"><font style="font-size: 11pt">A ser informados, cuando lo soliciten, sobre el tr&aacute;mite del proceso por su asesor jur&iacute;dico, el Ministerio P&uacute;blico o, en su caso, por el juez; </font></font><font face="Arial, serif"><font style="font-size: 11pt"><b>X. </b></font></font><font face="Arial, serif"><font style="font-size: 11pt">A recibir, desde la comisi&oacute;n de la conducta tipificada como delito, atenci&oacute;n m&eacute;dica y psicol&oacute;gica de urgencia; </font></font><font face="Arial, serif"><font style="font-size: 11pt"><b>XI.</b></font></font><font face="Arial, serif"><font style="font-size: 11pt"> A contar con un asesor jur&iacute;dico gratuito en cualquier etapa del proceso, en los t&eacute;rminos de la legislaci&oacute;n aplicable; </font></font><font face="Arial, serif"><font style="font-size: 11pt"><b>XII. </b></font></font><font face="Arial, serif"><font style="font-size: 11pt">A promover y participar en los mecanismos alternos de soluci&oacute;n de controversias; </font></font><font face="Arial, serif"><font style="font-size: 11pt"><b>XIII.</b></font></font><font face="Arial, serif"><font style="font-size: 11pt"> A recibir gratuitamente la asistencia de un int&eacute;rprete o traductor desde la denuncia hasta la conclusi&oacute;n del proceso, cuando la v&iacute;ctima u ofendido pertenezcan a un grupo &eacute;tnico o pueblo ind&iacute;gena o no conozca o no comprenda el idioma espa&ntilde;ol; </font></font><font face="Arial, serif"><font style="font-size: 11pt"><b>XIV.</b></font></font><font face="Arial, serif"><font style="font-size: 11pt"> En caso de tener discapacidad, a que se realicen los ajustes al proceso que sean necesarios para salvaguardar sus derechos; </font></font><font face="Arial, serif"><font style="font-size: 11pt"><b>XV. </b></font></font><font face="Arial, serif"><font style="font-size: 11pt">A que se le proporcione asistencia migratoria cuando tenga otra nacionalidad; </font></font><font face="Arial, serif"><font style="font-size: 11pt"><b>XVI.</b></font></font><font face="Arial, serif"><font style="font-size: 11pt"> A que se le reciban todos los datos o elementos de prueba con los que cuente, tanto en la investigaci&oacute;n como en el proceso y que se desahoguen las actuaciones correspondientes; </font></font><font face="Arial, serif"><font style="font-size: 11pt"><b>XVII. </b></font></font><font face="Arial, serif"><font style="font-size: 11pt">A intervenir en el proceso por s&iacute; o a trav&eacute;s de su asesor jur&iacute;dico, e interponer los recursos correspondientes, aunque no se haya constituido como coadyuvante del Ministerio P&uacute;blico; </font></font><font face="Arial, serif"><font style="font-size: 11pt"><b>XVIII. </b></font></font><font face="Arial, serif"><font style="font-size: 11pt">A constituirse en acusadores coadyuvantes del Ministerio P&uacute;blico en los plazos y condiciones que establece esta ley; </font></font><font face="Arial, serif"><font style="font-size: 11pt"><b>XIX. </b></font></font><font face="Arial, serif"><font style="font-size: 11pt">A que se le provea protecci&oacute;n cuando exista riesgo para su vida o integridad personal; </font></font><font face="Arial, serif"><font style="font-size: 11pt"><b>XX.</b></font></font><font face="Arial, serif"><font style="font-size: 11pt"> A solicitar la realizaci&oacute;n de actos de investigaci&oacute;n que en su caso correspondan, salvo que el Misterio P&uacute;blico considere que no es necesario, debiendo en ese caso fundar y motivar su negativa; </font></font><font face="Arial, serif"><font style="font-size: 11pt"><b>XXI. </b></font></font><font face="Arial, serif"><font style="font-size: 11pt">A recibir atenci&oacute;n m&eacute;dica y psicol&oacute;gica, o a ser canalizado a instituciones que le proporcionen estos servicios, as&iacute; como a recibir protecci&oacute;n especial de su integridad f&iacute;sica y ps&iacute;quica cuando as&iacute; lo solicite, o cuando se trate de delitos que as&iacute; lo requieran; </font></font><font face="Arial, serif"><font style="font-size: 11pt"><b>XXII.</b></font></font><font face="Arial, serif"><font style="font-size: 11pt"> A solicitar las medidas de protecci&oacute;n, providencias precautorias y medidas cautelares que prevea la ley para su seguridad y auxilio; </font></font><font face="Arial, serif"><font style="font-size: 11pt"><b>XXIII. </b></font></font><font face="Arial, serif"><font style="font-size: 11pt">A solicitar el traslado de la autoridad al lugar en donde se encuentre, para ser interrogada o participar en el acto para el cual fue citada, cuando por su edad, enfermedad grave o por alguna imposibilidad f&iacute;sica o psicol&oacute;gica se dificulte su comparecencia, a cuyo fin deber&aacute; requerir la dispensa, por s&iacute; o por un tercero, con anticipaci&oacute;n; </font></font><font face="Arial, serif"><font style="font-size: 11pt"><b>XXIV. </b></font></font><font face="Arial, serif"><font style="font-size: 11pt">A impugnar por s&iacute; o por medio de su representante, las omisiones o negligencias que cometa el Ministerio P&uacute;blico en el desempe&ntilde;o de sus funciones de investigaci&oacute;n; </font></font><font face="Arial, serif"><font style="font-size: 11pt"><b>XXV. </b></font></font><font face="Arial, serif"><font style="font-size: 11pt">A tener acceso a los registros de la investigaci&oacute;n durante el proceso, as&iacute; como a obtener copia gratuita de &eacute;stos, salvo que la informaci&oacute;n est&eacute; sujeta a reserva as&iacute; determinada por el juez; </font></font><font face="Arial, serif"><font style="font-size: 11pt"><b>XXVI. </b></font></font><font face="Arial, serif"><font style="font-size: 11pt">A ser restituido en sus derechos, cuando &eacute;stos est&eacute;n acreditados; </font></font><font face="Arial, serif"><font style="font-size: 11pt"><b>XXVII.</b></font></font><font face="Arial, serif"><font style="font-size: 11pt"> A exigir y recibir la reparaci&oacute;n del da&ntilde;o por el adolescente acusado o terceros obligados, pudiendo solicitarlo directamente al juez, sin perjuicio de que el Ministerio P&uacute;blico lo solicite. En los casos en que sea procedente, el Ministerio P&uacute;blico estar&aacute; obligado a solicitar la reparaci&oacute;n del da&ntilde;o y el juzgador no podr&aacute; absolver al adolescente de dicha reparaci&oacute;n si ha emitido una resoluci&oacute;n definitiva condenatoria; </font></font><font face="Arial, serif"><font style="font-size: 11pt"><b>XXVIII.</b></font></font><font face="Arial, serif"><font style="font-size: 11pt"> Al resguardo de su identidad y dem&aacute;s datos personales cuando sean menores de edad, se trate de delitos de violaci&oacute;n contra la libertad y el normal desarrollo psicosexual, violencia familiar, secuestro, trata de personas o cuando a criterio del juez sea necesario para su protecci&oacute;n, salvaguardando en todo caso los derechos de la defensa; </font></font><font face="Arial, serif"><font style="font-size: 11pt"><b>XXIX. </b></font></font><font face="Arial, serif"><font style="font-size: 11pt">A ser notificado del desistimiento de la acci&oacute;n de remisi&oacute;n y de todas las resoluciones que finalicen el proceso; </font></font><font face="Arial, serif"><font style="font-size: 11pt"><b>XXX. </b></font></font><font face="Arial, serif"><font style="font-size: 11pt">A solicitar la reapertura del proceso cuando se haya decretado su suspensi&oacute;n; </font></font><font face="Arial, serif"><font style="font-size: 11pt"><b>XXXI.</b></font></font><font face="Arial, serif"><font style="font-size: 11pt">A impugnar toda decisi&oacute;n sobre el no ejercicio de la acci&oacute;n de remisi&oacute;n; </font></font><font face="Arial, serif"><font style="font-size: 11pt"><b>XXXII.</b></font></font><font face="Arial, serif"><font style="font-size: 11pt"> A presentar acci&oacute;n de remisi&oacute;n particular conforme a las formalidades previstas y, en su caso, a desistirse de la misma; </font></font><font face="Arial, serif"><font style="font-size: 11pt"><b>XXXIII.</b></font></font><font face="Arial, serif"><font style="font-size: 11pt"> A ser informado del significado y consecuencias jur&iacute;dicas del otorgamiento del perd&oacute;n en los casos que proceda; y </font></font><font face="Arial, serif"><font style="font-size: 11pt"><b>XXXIV.</b></font></font><font face="Arial, serif"><font style="font-size: 11pt"> Las dem&aacute;s que se contengan en la Constituci&oacute;n Pol&iacute;tica de los Estados Unidos Mexicanos, los Tratados Internacionales celebrados y aprobados por el Estado mexicano, esta ley y dem&aacute;s leyes aplicables. Enseguida, de conformidad con lo establecido en</font></font><font color="#000000"><font face="Arial, serif"><font style="font-size: 11pt"> t&eacute;rminos del art&iacute;culo 49 del mismo ordenamiento, se le protesta para que se conduzca con verdad en todo lo que</font></font></font><font face="Arial, serif"><font style="font-size: 11pt"> va a manifestar y advertido de las penas que se imponen a los que declarar con falsedad ante una autoridad, quien enterado de lo anterior por sus generales manifiesta, llamarse como ha quedado escrito ser originario de </font></font><b><font face="Arial, serif"><font style="font-size: 11pt">_________________________</font></font></b><font face="Arial, serif"><font style="font-size: 11pt"> y vecino de _____________________, con domicilio en _______________________________,</font></font><b><font face="Arial, serif"><font style="font-size: 11pt"> </font></font></b><font face="Arial, serif"><font style="font-size: 11pt">quien cuenta con </font></font><b><font face="Arial, serif"><font style="font-size: 11pt">________ </font></font></b><font face="Arial, serif"><font style="font-size: 11pt">a&ntilde;os, de estado civil </font></font><b><font face="Arial, serif"><font style="font-size: 11pt">____________________</font></font></b><font face="Arial, serif"><font style="font-size: 11pt">, de ocupaci&oacute;n ________________, que si sabe leer y escribir por haber cursado instrucci&oacute;n </font></font><b><font face="Arial, serif"><font style="font-size: 11pt">_________________</font></font></b><font face="Arial, serif"><font style="font-size: 11pt">, quien proporciona el n&uacute;mero telef&oacute;nico _________________ para ser localizado,&nbsp; y si tiene alg&uacute;n v&iacute;nculo o relaci&oacute;n con el indiciado </font></font><b><font face="Arial, serif"><font style="font-size: 11pt">_____________________________________. </font></font></b><font face="Arial, serif"><font style="font-size: 11pt">Sin m&aacute;s generales que agregar en relaci&oacute;n a su comparecencia. As&iacute; mismo, esta Representaci&oacute;n Social le hace del conocimiento del contenido del art&iacute;culo 20, apartado C, de la Constituci&oacute;n Pol&iacute;tica de los Estados Unidos Mexicanos, por lo que, </font></font><font face="Arial, serif"><font style="font-size: 11pt"><b>MANIFIESTA</b></font></font><font face="Arial, serif"><font style="font-size: 11pt">: Comparezco ante esta Representaci&oacute;n Social para presentar </font></font><font face="Arial, serif"><font style="font-size: 11pt"><b>DENUNCIA</b></font></font><font face="Arial, serif"><font style="font-size: 11pt">, en contra de </font></font><b><font face="Arial, serif"><font style="font-size: 11pt">_________________________________________&nbsp;</font></font></b><font face="Arial, serif"><font style="font-size: 11pt"> , por hechos&nbsp; que revisten el car&aacute;cter del delito de _____________, toda vez que </font></font><b><font face="Arial, serif"><font style="font-size: 11pt">_________________________________________________________________________________________________________________________________________</font></font></b><font face="Arial, serif"><font style="font-size: 11pt">es todo lo que deseo manifestar y solicito la reparaci&oacute;n del da&ntilde;o.</font></font></font></font></p>
<p style="margin-left: 0.98in; margin-top: 0.19in; margin-bottom: 0.19in; line-height: 100%"> 
<br /> 
<b style="font-size: 11pt; font-family: Arial, serif; color: rgb(0, 0, 0); line-height: 100%;">&nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; ATENTAMENTE</b></p>
<p align="center" style="margin-bottom: 0.14in; line-height: 115%"> 
<br /> 
<font color="#000000"><font face="Arial, serif"><b>AGENTE DEL MINISTERIO P&Uacute;BLICO ESPECIALIZADO</b></font></font></p>
<p align="center" style="margin-bottom: 0.14in; line-height: 115%"> 
<a name="_GoBack"></a> <font color="#000000"><font face="Arial, serif"><b>LIC. </b></font></font></p>
<p align="center" style="margin-bottom: 0.14in; line-height: 115%"> 
<br /> 
&nbsp;</p>
<p align="center" style="margin-bottom: 0.14in; line-height: 115%"> 
<br /> 
&nbsp;</p>
<p align="center" style="margin-bottom: 0.14in; line-height: 115%"> 
<font face="Arial, serif">DENUNCIANTE<br /> 
</font><br /> 
&nbsp;</p>'
    ,
    'DENUNCIA'
  )
  ,
  (
    'ENTREVISTA DE TESTIGO MAYOR DE EDAD (ADOLESCENTES)',
    1611,
    '<p align="center" style="margin-left: 0.83in; margin-top: 0.19in; margin-bottom: 0.19in; line-height: 150%"> 
<font face="Arial, serif"><font style="font-size: 12pt"><b>ENTREVISTA DE TESTIGO</b></font></font></p>
<p align="right" style="margin-left: 0.83in; margin-top: 0.19in; margin-bottom: 0.19in; line-height: 150%"> 
<br /> 
&nbsp;</p>
<p align="right" style="margin-left: 0.83in; margin-top: 0.19in; margin-bottom: 0.19in; line-height: 150%"> 
<font face="Arial, serif"><font style="font-size: 12pt">N&uacute;m. Carpeta de investigaci&oacute;n.:________________<br /> 
N&deg;. Expediente: </font></font><font face="Arial, serif"><font style="font-size: 12pt"><b>_________________________</b></font></font></p>
<p style="margin-left: 0.39in; margin-top: 0.19in; margin-bottom: 0.19in; line-height: 150%"> 
<br /> 
<font face="Arial, serif"><font style="font-size: 12pt">Ciudad: </font></font><font face="Arial, serif"><font style="font-size: 12pt"><b>________ &nbsp;</b></font></font><font face="Arial, serif"><font style="font-size: 12pt">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; Estado: </font></font><font face="Arial, serif"><font style="font-size: 12pt"><b>____________</b></font></font><br /> 
<font face="Arial, serif"><font style="font-size: 12pt"> Fecha:&nbsp;</font></font><font face="Arial, serif"><font style="font-size: 12pt"><b>_________________</b></font></font><font face="Arial, serif"><font style="font-size: 12pt">&nbsp; Hora: </font></font><font face="Arial, serif"><font style="font-size: 12pt"><b>__________</b></font></font><br /> 
<font face="Arial, serif"><font style="font-size: 12pt"> Agente del Ministerio P&uacute;blico: ______________________________.<br /> 
Unidad de investigaci&oacute;n: __________________________________<br /> 
Domicilio de la unidad: ____________________________________.</font></font></p>
<p align="justify" style="margin-left: 0.39in; margin-bottom: 0.14in; line-height: 150%"> 
<br /> 
<font face="Arial, serif"><font style="font-size: 12pt">Ante el Agente del Ministerio P&uacute;blico Especializado _______________, comparece quien dijo llamarse _____________________________ (nombre del testigo), quien se identifica con ________________________________(datos de la identificaci&oacute;n ), mismo que en su margen izquierdo cuenta con fotograf&iacute;a que coincide con todos los rasgos faciales del compareciente y en este acto se le devuelve, previo cotejo con la copia simple que anexa de la misma para que obre dentro de la presente carpeta de investigaci&oacute;n, esta Representaci&oacute;n Social </font></font><font face="Arial, serif"><font style="font-size: 12pt">en ejercicio de las facultades previstas por los art&iacute;culos 1, 2, 6, 7 , 9, 61, 62, 63, 132 y 133 de la Ley del Sistema Integral de Justicia para Adolescentes del Estado de Coahuila, as&iacute; como el numeral 131 del C&oacute;digo Nacional de Procedimientos Penales</font></font><font color="#000000"><font face="Arial, serif"><font style="font-size: 12pt">, enseguida se le protesta en t&eacute;rminos del art&iacute;culo 49 del mismo ordenamiento</font></font></font><font face="Arial, serif"><font style="font-size: 12pt"><b>,</b></font></font><font face="Arial, serif"><font style="font-size: 12pt"> para que se conduzca con verdad en todo lo que va a manifestar y advertido de las penas que se imponen a los que declarar con falsedad ante una autoridad, quien enterado de lo anterior por sus generales manifiesta, llamarse como ha quedado escrito ser originario de ____________________ (poblaci&oacute;n)&nbsp; y vecino de __________________________(poblaci&oacute;n), de estado civil ________________________ con domicilio en _________________________________(calle, n&uacute;mero, colonia, poblaci&oacute;n, municipio y estado), quien cuenta con ________________________________(a&ntilde;os de edad), de ocupaci&oacute;n ______________________________, que si sabe leer y escribir por haber cursado instrucci&oacute;n _______________________(escolaridad), quien proporciona el n&uacute;mero telef&oacute;nico _______________________________, para ser localizado,&nbsp; y si tiene alg&uacute;n v&iacute;nculo o relaci&oacute;n con el indiciado _______________________________.&nbsp; Sin m&aacute;s generales que agregar en relaci&oacute;n a su comparecencia: </font></font><font face="Arial, serif"><font style="font-size: 12pt"><b>MANIFIESTA</b></font></font><font face="Arial, serif"><font style="font-size: 12pt"> (declaraci&oacute;n del testigo) ____________________________________________________________________________________________________________________________</font></font></p>
<p style="margin-left: 0.83in; margin-top: 0.19in; margin-bottom: 0.19in; line-height: 150%"> 
<br /> 
&nbsp;</p>
<p style="margin-left: 0.83in; margin-top: 0.19in; margin-bottom: 0.19in; line-height: 150%"> 
<br /> 
&nbsp;</p>
<p align="center" style="margin-left: 0.83in; margin-top: 0.19in; margin-bottom: 0.19in; line-height: 150%"> 
<font face="Arial, serif"><font style="font-size: 12pt"><b>ATENTAMENTE</b></font></font></p>
<p align="center" style="margin-left: 0.83in; margin-top: 0.19in; margin-bottom: 0.19in; line-height: 150%"> 
<br /> 
&nbsp;</p>
<p align="center" style="margin-left: 0.83in; margin-top: 0.19in; margin-bottom: 0.19in; line-height: 150%"> 
<font face="Arial, serif"><font style="font-size: 12pt"><b>AGENTE DEL MINISTERIO P&Uacute;BLICO ESPECIALIZADO</b></font></font></p>
<p align="center" style="margin-left: 0.83in; margin-top: 0.19in; margin-bottom: 0.19in; line-height: 150%"> 
<font face="Arial, serif"><font style="font-size: 12pt"><b>LIC. </b></font></font></p>
<p align="center" style="margin-left: 0.83in; margin-top: 0.19in; margin-bottom: 0.19in; line-height: 150%"> 
<a name="_GoBack"></a><br /> 
<br /> 
<font face="Arial, serif"><font style="font-size: 12pt"><b> (NOMBRE Y FIRMA DEL TESTIGO)</b></font></font></p>
<p align="center" style="margin-left: 0.83in; margin-top: 0.19in; margin-bottom: 0.19in; line-height: 150%"> 
<br /> 
&nbsp;</p>
<p style="margin-bottom: 0.14in; line-height: 150%"> 
<br /> 
&nbsp;</p>'
    ,
    'ENTREVISTA DE TESTIGO MAYOR DE EDAD (ADOLESCENTES)'
  )
  ,
  (
    'ENTREVISTA  (ADOLESCENTES)',
    1611,
    '<p style="margin-left: 0.83in; margin-top: 0.19in; margin-bottom: 0.19in; line-height: 150%"> 
<br /> 
&nbsp;</p>
<p style="margin-left: 0.83in; margin-top: 0.19in; margin-bottom: 0.19in; line-height: 150%"> 
<font face="Arial, serif" style="font-size: 12pt; line-height: 150%;"><b>ENTREVISTA DE: </b></font><font face="Arial, serif" style="font-size: 12pt; line-height: 150%;">_____________________________</font></p>
<p style="margin-left: 0.83in; margin-top: 0.19in; margin-bottom: 0.19in; line-height: 150%"> 
<br /> 
&nbsp;</p>
<p align="right" style="margin-left: 0.83in; margin-top: 0.19in; margin-bottom: 0.19in; line-height: 150%"> 
<font face="Arial, serif"><font style="font-size: 12pt">Expediente._______________</font></font></p>
<p align="right" style="margin-left: 0.39in; margin-top: 0.19in; margin-bottom: 0.19in; line-height: 150%"> 
<font face="Times New Roman, serif"><font style="font-size: 12pt"><font face="Arial, serif">Numero &Uacute;nico de Caso.__________</font></font></font></p>
<p style="margin-left: 0.39in; margin-top: 0.19in; margin-bottom: 0.19in; line-height: 150%"> 
<font face="Times New Roman, serif"><font style="font-size: 12pt"><font face="Arial, serif">Ciudad: _______________________ Estado:_______________</font></font></font></p>
<p style="margin-left: 0.39in; margin-top: 0.19in; margin-bottom: 0.19in; line-height: 150%"> 
<font face="Times New Roman, serif"><font style="font-size: 12pt"><font face="Arial, serif">Fecha:&nbsp;</font><b><font face="Arial, serif"> ____________________</font></b><font face="Arial, serif">Hora: </font><b><font face="Arial, serif">___________</font></b><br /> 
<font face="Arial, serif"> Agente del Ministerio P&uacute;blico:________________________<br /> 
Unidad de investigaci&oacute;n:</font><b><font face="Arial, serif"> ____________________________</font></b><br /> 
<font face="Arial, serif"> Domicilio de la unidad: ______________________________</font></font></font></p>
<p align="justify" style="margin-left: 0.39in; margin-bottom: 0.14in; line-height: 150%"> 
<br /> 
<br /> 
<font face="Arial, serif"><font style="font-size: 12pt">Ante el Agente del Ministerio P&uacute;blico Especializado, comparece quien dijo llamarse ___________________, quien se identifica con _____________________, mismo que en su margen izquierdo cuenta con fotograf&iacute;a que coincide con todos los rasgos faciales del compareciente y en este acto se le devuelve, previo cotejo con la copia simple que anexa de la misma para que obre dentro de la presente carpeta de investigaci&oacute;n, esta Representaci&oacute;n Social en ejercicio de las facultades previstas por los art&iacute;culos 1, 2, 6, 7, 9, 61, 62, 63 132 y 133 de la Ley del Sistema Integral de Justicia para Adolescentes del Estado de Coahuila, asi como 131 </font></font><font color="#000000"><font face="Arial, serif"><font style="font-size: 12pt"> del C&oacute;digo de Nacional de Procedimientos Penales, enseguida se le protesta en t&eacute;rminos del art&iacute;culo 49 del mismo ordenamiento</font></font></font><font face="Arial, serif"><font style="font-size: 12pt">, para que se conduzca con verdad en todo lo que va a manifestar y advertido de las penas que se imponen a los que declarar con falsedad ante una autoridad, quien enterado de lo anterior por sus generales manifiesta, llamarse como ha quedado escrito ser originario de</font></font><b><font face="Arial, serif"><font style="font-size: 12pt"> ________________________</font></font></b><font face="Arial, serif"><font style="font-size: 12pt">, ______________________ y vecino de </font></font><b><font face="Arial, serif"><font style="font-size: 12pt">__________________________________ </font></font></b><font face="Arial, serif"><font style="font-size: 12pt">, de estado civil </font></font><b><font face="Arial, serif"><font style="font-size: 12pt">__________________ </font></font></b><font face="Arial, serif"><font style="font-size: 12pt">con domicilio en calle ________________,numero </font></font><b><font face="Arial, serif"><font style="font-size: 12pt">__________________</font></font></b><font face="Arial, serif"><font style="font-size: 12pt"> colonia </font></font><b><font face="Arial, serif"><font style="font-size: 12pt">______________________</font></font></b><font face="Arial, serif"><font style="font-size: 12pt"> de ________________, quien cuenta con </font></font><b><font face="Arial, serif"><font style="font-size: 12pt">_________ </font></font></b><font face="Arial, serif"><font style="font-size: 12pt">a&ntilde;os de edad, de ocupaci&oacute;n_____ , que ___ sabe leer y escribir por haber cursado instrucci&oacute;n _____________________, quien proporciona el n&uacute;mero telef&oacute;nico _____________________, para ser localizado,&nbsp; y si tiene alg&uacute;n v&iacute;nculo o relaci&oacute;n con el indiciado _______________________________.&nbsp; Sin m&aacute;s generales que agregar en relaci&oacute;n a su comparecencia: </font></font><font face="Arial, serif"><font style="font-size: 12pt"><b>MANIFIESTA </b></font></font><font face="Arial, serif"><font style="font-size: 12pt">(declaraci&oacute;n del compareciente) __________________________________________________________</font></font></p>
<p align="center" style="margin-left: 0.83in; margin-top: 0.19in; margin-bottom: 0.19in; line-height: 150%"> 
<font face="Times New Roman, serif"><font style="font-size: 12pt"><font face="Arial, serif">AGENTE DEL MINISTERIO P&Uacute;BLICO ESPECIALIZADO</font></font></font></p>
<p align="center" style="margin-left: 0.83in; margin-top: 0.19in; margin-bottom: 0.19in; line-height: 150%"> 
<br /> 
&nbsp;</p>
<p align="center" style="margin-left: 0.83in; margin-top: 0.19in; margin-bottom: 0.19in; line-height: 150%"> 
<a name="_GoBack"></a> <font face="Times New Roman, serif"><font style="font-size: 12pt"><font face="Arial, serif">LIC. </font></font></font></p>
<p align="center" style="margin-left: 0.83in; margin-top: 0.19in; margin-bottom: 0.17in; line-height: 150%"> 
<br /> 
&nbsp;</p>
<p align="center" style="margin-left: 0.83in; margin-top: 0.19in; margin-bottom: 0.19in; line-height: 150%"> 
<br /> 
&nbsp;</p>
<p align="center" style="margin-left: 0.83in; margin-top: 0.19in; margin-bottom: 0.19in; line-height: 150%"> 
<font face="Times New Roman, serif"><font style="font-size: 12pt"><font face="Arial, serif">ENTREVISTADO</font></font></font></p>
<p align="center" style="margin-left: 0.83in; margin-top: 0.19in; margin-bottom: 0.19in; line-height: 150%"> 
<br /> 
&nbsp;</p>
<p align="center" style="margin-left: 0.83in; margin-top: 0.19in; margin-bottom: 0.19in; line-height: 150%"> 
<br /> 
&nbsp;</p>
<p style="margin-bottom: 0.14in; line-height: 150%"> 
<br /> 
&nbsp;</p>'
    ,
    'ENTREVISTA  (ADOLESCENTES)'
  )
  ,
  (
    'FICHA DE CANALIZACION A MEDIOS ALTERNOS',
    1611,
    '<p align="center" style="margin-left: 0.83in; margin-top: 0.19in; margin-bottom: 0.19in; line-height: 150%"> 
<font face="Times New Roman, serif"><font style="font-size: 12pt"><b><font face="Arial, serif">FICHA DE CANALIZACI&Oacute;N A MEDIOS ALTERNOS </font></b></font></font></p>
<p align="right" style="margin-left: 0.83in; margin-top: 0.19in; margin-bottom: 0.19in; line-height: 150%"> 
<font face="Times New Roman, serif"><font style="font-size: 12pt"><font face="Arial, serif">N.U.C. </font><b><font face="Arial, serif">________________________</font></b></font></font></p>
<p align="right" style="margin-top: 0.19in; margin-bottom: 0.19in; line-height: 150%"> 
<font face="Times New Roman, serif"><font style="font-size: 12pt"><font face="Arial, serif">NUMERO EXPEDIENTE:</font><b><font face="Arial, serif"> __________________________</font></b></font></font></p>
<p align="right" style="margin-bottom: 0in; line-height: 150%"> 
&nbsp;</p>
<p align="right" style="margin-bottom: 0in; line-height: 150%"> 
&nbsp;<font face="Arial, serif"><font style="font-size: 12pt">ASUNTO: SE&nbsp; REMITE EXPEDIENTE</font></font></p>
<p align="right" style="margin-bottom: 0in; line-height: 150%"> 
&nbsp;<font face="Arial, serif"><font style="font-size: 12pt">PARA EL SOMETIMIENTO A LOS MEDIOS</font></font></p>
<p align="right" style="margin-bottom: 0in; line-height: 150%"> 
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; <font face="Arial, serif"><font style="font-size: 12pt">ALTERNOS Y SOLUCI&Oacute;N DE CONFLICTOS</font></font></p>
<p style="margin-left: 0.83in; margin-top: 0.19in; margin-bottom: 0.19in; line-height: 150%"> 
&nbsp;</p>
<p style="margin-left: 0.83in; margin-top: 0.19in; margin-bottom: 0.19in; line-height: 150%"> 
<a name="_GoBack"></a> <font face="Times New Roman, serif"><font style="font-size: 12pt"><font face="Arial, serif">COORDINADOR REGIONAL DE MEDIOS ALTERNOS Y SOLUCI&Oacute;N DE CONFLICTOS.</font></font></font></p>
<p style="margin-left: 0.83in; margin-top: 0.19in; margin-bottom: 0.19in; line-height: 150%"> 
<font face="Times New Roman, serif"><font style="font-size: 12pt"><font face="Arial, serif">P R E S E N T E.-</font></font></font></p>
<p align="right" style="margin-left: 0.83in; margin-top: 0.19in; margin-bottom: 0.19in; line-height: 150%"> 
<font face="Times New Roman, serif"><font style="font-size: 12pt"><font face="Arial, serif">(Ciudad), (Estado), (Fecha)</font></font></font></p>
<p align="justify" style="margin-left: 0.83in; margin-top: 0.19in; margin-bottom: 0.19in; line-height: 150%"> 
<font face="Times New Roman, serif"><font style="font-size: 12pt"><font face="Arial, serif">Me permito remitirle el expediente No. </font><b><font face="Arial, serif">_____________ </font></b><font face="Arial, serif">de fecha </font><b><font face="Arial, serif">_____ </font></b><font face="Arial, serif">de</font><b><font face="Arial, serif"> __________ </font></b><font face="Arial, serif">de</font><b><font face="Arial, serif"> ______________,</font></b><font face="Arial, serif">&nbsp; derivada&nbsp; de los hechos denunciados por la ___________________________,en la que aparece como denunciado el adolescente </font><b><font face="Arial, serif">_________________________________, </font></b><font face="Arial, serif">por una conducta tipificada como el delito de</font><b><font face="Arial, serif"> _______________, </font></b><font face="Arial, serif">lo anterior con la finalidad de entablar un dialogo y encontrar una soluci&oacute;n al conflicto mediante la reparaci&oacute;n del da&ntilde;o ocasionado al ofendido, lo anterior con fundamento en el Art&iacute;culos 61, 62, 63, 71, 72, 74, 75, 76, 77, 78, 79, 80 de la Ley del Sistema Integral de Justicia para Adolescentes.</font></font></font></p>
<p align="justify" style="margin-left: 0.83in; margin-top: 0.19in; margin-bottom: 0.19in; line-height: 150%"> 
<font face="Times New Roman, serif"><font style="font-size: 12pt"><font face="Arial, serif">Sin otro asunto en particular&nbsp; me despido de Usted, agradeciendo de antemano las atenciones que brinde a la presente.</font></font></font></p>
<p align="center" style="margin-left: 0.83in; margin-top: 0.19in; margin-bottom: 0.19in; line-height: 150%"> 
<font face="Times New Roman, serif"><font style="font-size: 12pt"><font face="Arial, serif">A T E N T A M E N T E</font></font></font></p>
<p align="center" style="margin-left: 0.83in; margin-top: 0.19in; margin-bottom: 0.19in; line-height: 150%"> 
<font face="Times New Roman, serif"><font style="font-size: 12pt"><font face="Arial, serif">El C. AGENTE DEL MINISTERIO P&Uacute;BLICO ESPECIALIZADO</font></font></font></p>
<p align="center" style="margin-left: 0.83in; margin-top: 0.19in; margin-bottom: 0.19in; line-height: 150%"> 
<br /> 
&nbsp;</p>
<p align="center" style="margin-left: 0.83in; margin-top: 0.19in; margin-bottom: 0.19in; line-height: 150%"> 
<font face="Times New Roman, serif"><font style="font-size: 12pt"><b><font face="Arial, serif">LIC. ______________________________</font></b></font></font></p>
<p align="center" style="margin-bottom: 0.14in; line-height: 150%"> 
<br /> 
&nbsp;</p>
<p style="margin-bottom: 0.14in; line-height: 150%"> 
<br /> 
&nbsp;</p>'
    ,
    'FICHA DE CANALIZACION A MEDIOS ALTERNOS'
  )
  ,
  (
    'NOMBRAMIENTO DEL DEFENSOR Y DECLARACION DEL IMPUTADO DECLARA DEFENSOR PUBLICO'
    ,
    1611,
    '<p align="center" style="margin-top: 0.19in; margin-bottom: 0.19in; line-height: 150%"> 
<font face="Times New Roman, serif"><font style="font-size: 12pt"><b><font face="Arial, serif">ENTREVISTA DEL ADOLESCENTE </font></b></font></font></p>
<p style="margin-left: 0.39in; margin-top: 0.19in; margin-bottom: 0.19in; line-height: 150%"> 
<br /> 
&nbsp;</p>
<p style="margin-left: 0.39in; margin-top: 0.19in; margin-bottom: 0.19in; line-height: 150%"> 
<font face="Times New Roman, serif"><font style="font-size: 12pt"><font face="Arial, serif">N&uacute;m. Carpeta de investigaci&oacute;n:&nbsp;_____________________</font></font></font></p>
<p style="margin-left: 0.39in; margin-top: 0.19in; margin-bottom: 0.19in; line-height: 150%"> 
<font face="Times New Roman, serif"><font style="font-size: 12pt"><font face="Arial, serif">NUC:_____________________________<br /> 
</font><font face="Arial, serif">Ciudad: ________________ Estado: ________________</font><br /> 
<font face="Arial, serif"> Fecha: _____________&nbsp; Hora:</font><b><font face="Arial, serif">____________</font></b><br /> 
<font face="Arial, serif"> Agente del Ministerio P&uacute;blico:_________________________<br /> 
Unidad de investigaci&oacute;n:</font><b><font face="Arial, serif">_____________________________</font></b><br /> 
<font face="Arial, serif"> Domicilio de la unidad:______________________________</font></font></font></p>
<p align="justify" style="margin-bottom: 0in; line-height: 150%"> 
&nbsp;</p>
<p align="justify" style="margin-bottom: 0in; line-height: 150%"> 
<font face="Consolas, serif"><font style="font-size: 10pt"><font face="Arial, serif"><font style="font-size: 12pt">Ante el Agente del Ministerio P&uacute;blico Especializado _______________, se procedi&oacute; a comunicar al adolescente __________________________,&nbsp; quien se identifica con&nbsp; ______________________(acta de nacimiento), documento que presenta en original y en este acto se le devuelve, previo cotejo con la copia simple que anexa de la misma para que obre dentro de la presente carpeta de investigaci&oacute;n, esta Representaci&oacute;n Social le hace del conocimiento del contenido </font></font><font color="#000000"><font face="Arial, serif"><font style="font-size: 12pt">del art&iacute;culo 20 apartado B) de la Constituci&oacute;n Pol&iacute;tica de los Estados Unidos Mexicanos, as&iacute; como tambi&eacute;n se le hace del conocimiento de los siguientes derechos que le otorga la Ley del Sistema Integral de Justicia para Adolescentes:</font></font></font></font></font></p>
<p align="justify" style="margin-left: 0.39in; margin-top: 0.19in; margin-bottom: 0.19in; line-height: 150%"> 
<font face="Times New Roman, serif"><font style="font-size: 12pt"><font face="Arial, serif">: </font><font face="Arial, serif"><i><b>&ldquo;ART&Iacute;CULO 38.- DERECHO A LA INTIMIDAD Y LA PRIVACIDAD.</b></i></font><font face="Arial, serif"><i> En todo proceso se respetar&aacute; el derecho a la intimidad de cualquier persona que intervenga en &eacute;l, asimismo se proteger&aacute; la informaci&oacute;n que se refiere a la vida privada y los datos personales, en los t&eacute;rminos y con las excepciones que fijan la Constituci&oacute;n Pol&iacute;tica de los Estados Unidos Mexicanos, esta ley y la legislaci&oacute;n aplicable</i></font><font face="Arial, serif">. </font><font face="Arial, serif"><i>- - - Todo adolescente tendr&aacute; derecho a que no se divulgue su identidad, ni el nombre de sus padres o cualquier dato que permita su identificaci&oacute;n p&uacute;blica, salvo cuando se encuentre pr&oacute;fugo y atendiendo a la peligrosidad y gravedad del delito, buscando preservar la seguridad de la comunidad.- - - Con la excepci&oacute;n se&ntilde;alada en el p&aacute;rrafo anterior, el juez correspondiente vigilar&aacute; en todo momento que no sea vulnerado el derecho del adolescente a la privacidad.- - A quien divulgue total o parcialmente por cualquier medio de comunicaci&oacute;n, la identidad, el nombre, hecho o documento relativo a una investigaci&oacute;n o a un proceso judicial en cualquier fase en la que &eacute;ste se encuentre y en el que se atribuya al adolescente una conducta tipificada como delito por las leyes penales, se le impondr&aacute; una multa de cien a trescientos d&iacute;as de salario m&iacute;nimo vigente en la entidad. - - - Las autoridades especializadas en justicia para adolescentes deber&aacute;n llevar el registro de los adolescentes que han cometido conductas tipificadas como delitos, con el objeto de definir los lineamientos de la pol&iacute;tica criminal para adolescentes, y s&oacute;lo podr&aacute;n otorgar informaci&oacute;n sobre estad&iacute;sticas siempre que no contravenga el principio de confidencialidad y el derecho a la privacidad consagrado en esta ley. - - - - -Los antecedentes y registros relacionados con adolescentes sometidos a proceso o sancionados conforme a esta ley ser&aacute;n de car&aacute;cter estrictamente confidencial y s&oacute;lo podr&aacute;n ser utilizados por las autoridades judiciales competentes para definir las medidas aplicables cuando se trate de establecer la naturaleza y gravedad de las conductas y la proporcionalidad e idoneidad de la medida. En ning&uacute;n caso podr&aacute;n ser utilizados en otros procesos en los que est&eacute; implicada la misma persona, salvo para establecer que el adolescente se encuentra gozando de la suspensi&oacute;n a prueba en otro proceso. - - - - Cumplida o extinguida la medida impuesta o transcurrido el t&eacute;rmino de la prescripci&oacute;n, el juez de ejecuci&oacute;n decretar&aacute; el cierre del expediente, remiti&eacute;ndolo al Centro de Internaci&oacute;n para que en su debido momento sea destruido, cualquiera que haya sido la determinaci&oacute;n adoptada.- - -</i></font><font face="Arial, serif"><i><b>ART&Iacute;CULO 39.- GARANT&Iacute;A DE SER INFORMADO DE SUS DERECHOS.</b></i></font><font face="Arial, serif"><i> Todas las autoridades que intervengan en los actos iniciales del proceso deber&aacute;n velar porque tanto el adolescente como la v&iacute;ctima u ofendido conozcan los derechos que le reconocen en ese momento procedimental la Constituci&oacute;n Pol&iacute;tica de los Estados Unidos Mexicanos, los Tratados Internacionales celebrados y aprobados por el Estados mexicano, y las leyes que de ellos emanen, en los t&eacute;rminos establecidos en la presente ley. - - - </i></font><font face="Arial, serif"><i><b>ART&Iacute;CULO 40.- DERECHO DE DEFENSA Y ASESOR&Iacute;A JUR&Iacute;DICA ADECUADA E INMEDIATA.</b></i></font><font face="Arial, serif"><i> La carga de la prueba la tiene la parte acusadora. No obstante, el adolescente acusado tendr&aacute; el derecho de presentar las pruebas y los argumentos necesarios para su defensa y de rebatir cuando les sea contrario. En ning&uacute;n caso podr&aacute; juzg&aacute;rsele en ausencia. -- - - - La v&iacute;ctima u ofendido tendr&aacute; derecho a contar con un asesor jur&iacute;dico gratuito en cualquier etapa del proceso, en los t&eacute;rminos de la legislaci&oacute;n aplicable. - - -Corresponde al juez velar sin preferencias ni desigualdades por la defensa adecuada y t&eacute;cnica del adolescente. - - - </i></font><font face="Arial, serif"><i><b>ART&Iacute;CULO 41.- DEFENSA T&Eacute;CNICA.</b></i></font><font face="Arial, serif"><i> Desde el momento de su detenci&oacute;n y a lo largo de todo el proceso, el adolescente deber&aacute; ser asistido por un defensor especializado; si as&iacute; lo desea designar&aacute; a su costa, por s&iacute; o por sus padres, tutores o representantes legales a un defensor privado, con c&eacute;dula profesional que lo acredite como Licenciado en Derecho para que lo asista jur&iacute;dicamente; deber&aacute; estar asistido en todos los actos del proceso, a&uacute;n los de car&aacute;cter ministerial y de ejecuci&oacute;n de las medidas que le impongan. - - - En caso de ser ind&iacute;genas, extranjeros, sordos, mudos o no sepan leer ni escribir, deber&aacute;n ser asistidos de oficio y en todos los actos procesales, por un defensor que comprenda plenamente su idioma, lengua, dialecto, as&iacute; como su cultura o bien, de ser necesario, que su defensor sea auxiliado por un perito traductor o int&eacute;rprete, asignado por la autoridad correspondiente o designado por el adolescente. Cuando el adolescente alegue ser ind&iacute;gena, se acreditar&aacute; s&oacute;lo con su manifestaci&oacute;n. - - - - En todo caso el defensor deber&aacute; poseer conocimientos en materia penal, procesal penal y de justicia para adolescentes suficientes para brindarle al adolescente una defensa adecuada. A la persona que designe el adolescente o representantes legales ser&aacute; interrogado durante la instancia, bajo protesta de decir verdad, si cumple con las condiciones anteriores. Igualmente, protestar&aacute; cumplir el cargo con fidelidad y que le brindar&aacute; al adolescente una defensa adecuada. Se dejar&aacute; constancia de todo lo anterior. En caso de que no elija su propio defensor, se le nombrar&aacute; un defensor p&uacute;blico. - - - - No podr&aacute; recib&iacute;rsele ninguna declaraci&oacute;n sin la asistencia de &eacute;ste, ni por otra autoridad que no sea el juez, bajo pena de nulidad.- - - En las entrevistas que realice el Ministerio P&uacute;blico al adolescente, &eacute;ste deber&aacute; estar asistido por su defensor. - - - El adolescente tendr&aacute; derecho a reunirse oportunamente con su defensor en estricta confidencialidad. - - - La intervenci&oacute;n del defensor no menoscabar&aacute; el derecho del adolescente de intervenir, formular peticiones y hacer las manifestaciones que estime pertinentes. - - - </i></font><font face="Arial, serif"><i><b>ART&Iacute;CULO 42.- PROHIBICI&Oacute;N DE INCOMUNICACI&Oacute;N. Todo</b></i></font><font face="Arial, serif"><i> adolescente, inmediatamente despu&eacute;s de ser detenido, tendr&aacute; derecho a establecer una comunicaci&oacute;n efectiva, por v&iacute;a telef&oacute;nica o por escrito, con su familia, defensor o con la persona o instituci&oacute;n a quien desee informar sobre el hecho de su detenci&oacute;n o privaci&oacute;n de libertad. - - - Durante todas las etapas del proceso, a&uacute;n las de car&aacute;cter ministerial, tendr&aacute; el derecho a ser visitado, entrevistado y a tener comunicaci&oacute;n con su defensor, as&iacute; como con sus padres, tutores o representantes legales, ya sea en forma conjunta o separada. Las entrevistas deber&aacute;n realizarse bajo un r&eacute;gimen de absoluta confidencialidad. Tendr&aacute; derecho tambi&eacute;n, en todo momento, a enviar y recibir correspondencia. - - - </i></font><font face="Arial, serif"><i><b>ART&Iacute;CULO 43.- GARANT&Iacute;AS DE LA DETENCI&Oacute;N.</b></i></font><font face="Arial, serif"><i> Todo adolescente tendr&aacute; derecho a ser presentado inmediatamente y sin demora ante el juez o el Ministerio P&uacute;blico, siempre dentro de los plazos que establece esta ley, as&iacute; como a no ser conducido o apresado de modo que se afecte su dignidad o se le exponga a alg&uacute;n peligro. - - - - </i></font><font face="Arial, serif"><i><b>ART&Iacute;CULO 44.- CONOCIMIENTO DE LA IMPUTACI&Oacute;N.</b></i></font><font face="Arial, serif"><i> Todo adolescente tendr&aacute; derecho a ser informado directamente, sin demora y en un lenguaje claro y accesible sobre las razones por las que se les detiene, juzga o impone una medida, la persona que les atribuye la realizaci&oacute;n de una conducta que la ley tipifique como delito; las consecuencias de la atribuci&oacute;n de un hecho, as&iacute; como de la detenci&oacute;n, juicio y medida; que podr&aacute; disponer de una defensa jur&iacute;dica gratuita, a solicitar la presencia inmediata de sus padres, tutores o representantes y todos los derechos y garant&iacute;as que les asisten respecto de su sujeci&oacute;n al Sistema Integral de Justicia para Adolescentes. - - - La autoridad tiene el deber de localizar e informar de manera inmediata a los padres, tutores o responsables legales acerca de la detenci&oacute;n del adolescente sujeto a su custodia, salvo que sea por su propia seguridad y atendiendo al inter&eacute;s superior del adolescente. - - - - </i></font><font face="Arial, serif"><i><b>ART&Iacute;CULO 45.- DERECHO A SER ESCUCHADO.</b></i></font><font face="Arial, serif"><i> Todo adolescente tendr&aacute; derecho a ser escuchado en cualquier etapa del proceso, desde el inicio de la investigaci&oacute;n hasta que cumpla con la medida que en su caso le sea impuesta. - - - - El adolescente que no comprenda ni pueda darse a entender en espa&ntilde;ol deber&aacute; ser provisto gratuitamente de un traductor o interprete a fin de que pueda expresarse en su propia lengua. Incluso si habla o comprende el espa&ntilde;ol, si se trata de un adolescente ind&iacute;gena, se le nombrar&aacute; un int&eacute;rprete en caso de que as&iacute; lo solicite. - - - - Si se trata de un adolescente mudo, se le har&aacute;n oralmente las preguntas y las responder&aacute; por escrito; si fuere un sordomudo, las preguntas y respuestas ser&aacute;n escritas. Si el adolescente no supiere leer y escribir se le nombrar&aacute; int&eacute;rprete id&oacute;neo. - - - </i></font><font face="Arial, serif"><i><b>ART&Iacute;CULO 46.- DERECHO DE ABSTENERSE DE DECLARAR.</b></i></font><font face="Arial, serif"><i> Todo adolescente tendr&aacute; derecho a abstenerse de declarar, a no auto-incriminarse y a no responder las preguntas que se le formulen. Su silencio no podr&aacute; ser valorado en su contra. El juez, previo a recibir su declaraci&oacute;n, informar&aacute; al adolescente sobre este derecho. - - - - Si consintiera en prestar declaraci&oacute;n, deber&aacute; hacerlo ante el juez y previa entrevista en privado con su defensor. Lo mismo se observar&aacute; para el caso de las entrevistas que el Ministerio P&uacute;blico haga al adolescente. A estar asistido de su Defensor al momento de rendir su declaraci&oacute;n, as&iacute; como en cualquier otra actuaci&oacute;n y a entrevistarse en privado previamente con &eacute;l. En los casos en que el adolescente tenga una edad de entre doce y catorce a&ntilde;os, tambi&eacute;n ser&aacute; necesaria la presencia de sus padres, tutores o quienes ejerzan la patria potestad o custodia, si &eacute;l y su defensa lo estiman conveniente. - - - - En ning&uacute;n caso se le exigir&aacute; protesta de decir verdad. - - - - Se proh&iacute;be el uso de cualquier medio para hacerle declarar en su contra o en contra de otra persona, as&iacute; como formularle cargos evidentemente improcedentes con el prop&oacute;sito de obtener una confesi&oacute;n. - - - - </i></font><font face="Arial, serif"><i><b>ART&Iacute;CULO 47.- DERECHO DE PENSAMIENTO, CONCIENCIA Y RELIGI&Oacute;N.</b></i></font><font face="Arial, serif"><i> Durante la investigaci&oacute;n, la tramitaci&oacute;n del proceso y la ejecuci&oacute;n de las sanciones, se respetar&aacute;n al adolescente sus creencias, su religi&oacute;n y sus pautas culturales y morales. - - - - </i></font><font face="Arial, serif"><i><b>ART&Iacute;CULO 48.- PARTICIPACI&Oacute;N DE LOS PADRES O RESPONSABLES EN EL PROCESO.</b></i></font><font face="Arial, serif"><i> Los padres, tutores o responsables legales del adolescente tendr&aacute;n derecho a participar en las actuaciones, tanto judiciales como ministeriales, y las autoridades competentes podr&aacute;n requerir su presencia en cada una de &eacute;stas. Esta presencia es considerada como una asistencia general al adolescente, de naturaleza psicol&oacute;gica y emotiva, que debe extenderse a lo largo del todo el proceso. Dicha participaci&oacute;n podr&aacute; ser denegada por la autoridad competente cuando existan motivos fundados para presumir que la exclusi&oacute;n es necesaria en defensa del adolescente. - - - En todo caso, en cualquier etapa del proceso, previa vista y consentimiento, la autoridad competente podr&aacute; requerir a los padres, tutores o quienes ejerzan la patria potestad o custodia del adolescente a colaborar en el cumplimiento de las siguientes actividades: - - - </i></font><font face="Arial, serif"><i><b>I.</b></i></font><font face="Arial, serif"><i> Asistir a programas p&uacute;blicos, sociales o privados de protecci&oacute;n y orientaci&oacute;n individual y familiar; </i></font><font face="Arial, serif"><i><b>II.</b></i></font><font face="Arial, serif"><i> Inclusi&oacute;n en programas p&uacute;blicos, sociales o privados de auxilio, orientaci&oacute;n y tratamiento a alcoh&oacute;licos, neur&oacute;ticos y toxic&oacute;manos; </i></font><font face="Arial, serif"><i><b>III.</b></i></font><font face="Arial, serif"><i> Asistir a tratamiento psicol&oacute;gico o psiqui&aacute;trico; </i></font><font face="Arial, serif"><i><b>IV.</b></i></font><font face="Arial, serif"><i> Matricular al adolescente en instituciones educativas y observar su asistencia y aprovechamiento escolar; </i></font><font face="Arial, serif"><i><b>V.</b></i></font><font face="Arial, serif"><i> Encauzar al adolescente a tratamiento especializado, y </i></font><font face="Arial, serif"><i><b>VI.</b></i></font><font face="Arial, serif"><i> Las dem&aacute;s que procedan de acuerdo a esta ley, otras disposiciones legales aplicables y a las circunstancias propias del adolescente. - - -En la resoluci&oacute;n definitiva el juez tomar&aacute; en consideraci&oacute;n, en beneficio del adolescente, la colaboraci&oacute;n que durante el proceso hayan tenido sus padres, tutores o quienes ejerzan la patria potestad o custodia en el cumplimiento de las actividades que en su caso se les hayan recomendado. - - - - </i></font><font face="Arial, serif"><i><b>ART&Iacute;CULO 49.- DERECHO A IMPUGNAR. </b></i></font><font face="Arial, serif"><i>Todo adolescente tendr&aacute; derecho a impugnar ante la autoridad judicial competente, en los supuestos previstos por esta ley, cualquier resoluci&oacute;n definitiva o provisional que le cause un agravio irreparable. - - - - </i></font><font face="Arial, serif"><i><b>ART&Iacute;CULO 50.- ASISTENCIA.</b></i></font><font face="Arial, serif"><i> Los adolescentes y, cuando proceda, sus familiares deber&aacute;n tener acceso a la asistencia de profesionales capacitados, lo que incluye servicios de asistencia y apoyo tales como servicios financieros, jur&iacute;dicos, de orientaci&oacute;n, salud, sociales y educativos, de recuperaci&oacute;n f&iacute;sica y psicol&oacute;gica y dem&aacute;s servicios necesarios para la reinserci&oacute;n del adolescente. En todo caso, cuando el juez constate la necesidad de apoyo especializado para el adolescente, deber&aacute; canalizarlo con la instancia que se determine, a fin de brindar la atenci&oacute;n que requiera para poder participar de manera efectiva en el proceso de justicia. En caso de que la persona especializada que brind&oacute; la atenci&oacute;n al adolescente concluya en su evaluaci&oacute;n que &eacute;ste requiere de tratamiento para poder participar en el juicio, el juez deber&aacute; atender las recomendaciones que se se&ntilde;alen en aquella</i></font><font face="Arial, serif">.&rdquo; Adem&aacute;s de los derechos que le confiere el art&iacute;culo 113 del C&oacute;digo Nacional de Procedimientos Penales.</font></font></font></p>
<p align="justify" style="margin-left: 0.39in; margin-top: 0.19in; margin-bottom: 0.19in; line-height: 150%"> 
<font face="Times New Roman, serif"><font style="font-size: 12pt"><font color="#000000"><font face="Arial, serif">que a la letra dice:&rdquo; </font></font><font color="#000000"><font face="Arial, serif"><i>El imputado tendr&aacute; los siguientes derechos: </i></font></font><font face="Arial, serif"><i><b>I. </b></i></font><font face="Arial, serif"><i>A ser considerado y tratado como inocente hasta que se demuestre su responsabilidad; </i></font><font face="Arial, serif"><i><b>II. </b></i></font><font face="Arial, serif"><i>A comunicarse con un familiar y con su Defensor cuando sea detenido, debiendo brindarle el Ministerio P&uacute;blico todas las facilidades para lograrlo; </i></font><font face="Arial, serif"><i><b>III. </b></i></font><font face="Arial, serif"><i>A declarar o a guardar silencio, en el entendido que su silencio no podr&aacute; ser utilizado en su perjuicio; </i></font><font face="Arial, serif"><i><b>IV. </b></i></font><font face="Arial, serif"><i>A estar asistido de su Defensor al momento de rendir su declaraci&oacute;n, as&iacute; como en cualquier otra actuaci&oacute;n y a entrevistarse en privado previamente con &eacute;l; </i></font><font face="Arial, serif"><i><b>V. </b></i></font><font face="Arial, serif"><i>A que se le informe, tanto en el momento de su detenci&oacute;n como en su comparecencia ante el Ministerio P&uacute;blico o el Juez de control, los hechos que se le imputan y los derechos que le asisten, as&iacute; como, en su caso, el motivo de la privaci&oacute;n de su libertad y el servidor p&uacute;blico que la orden&oacute;, exhibi&eacute;ndosele, seg&uacute;n corresponda, la orden emitida en su contra; </i></font><font face="Arial, serif"><i><b>VI. </b></i></font><font face="Arial, serif"><i>A no ser sometido en ning&uacute;n momento del procedimiento a t&eacute;cnicas ni m&eacute;todos que atenten contra su dignidad, induzcan o alteren su libre voluntad; </i></font><font face="Arial, serif"><i><b>VII. </b></i></font><font face="Arial, serif"><i>A solicitar ante la autoridad judicial la modificaci&oacute;n de la medida cautelar que se le haya impuesto, en los casos en que se encuentre en prisi&oacute;n preventiva, en los supuestos se&ntilde;alados por este C&oacute;digo; </i></font><font face="Arial, serif"><i><b>VIII. </b></i></font><font face="Arial, serif"><i>A tener acceso &eacute;l y su defensa a los registros de la investigaci&oacute;n, as&iacute; como a obtener copia gratuita de los mismos, en t&eacute;rminos del art&iacute;culo 217 de este C&oacute;digo; </i></font><font face="Arial, serif"><i><b>IX. </b></i></font><font face="Arial, serif"><i>A que se le reciban los medios pertinentes de prueba que ofrezca, concedi&eacute;ndosele el tiempo necesario para tal efecto y auxili&aacute;ndosele para obtener la comparecencia de las personas cuyo testimonio solicite y que no pueda presentar directamente, en t&eacute;rminos de lo establecido por este C&oacute;digo; </i></font><font face="Arial, serif"><i><b>X. </b></i></font><font face="Arial, serif"><i>A ser juzgado en audiencia por un Tribunal de enjuiciamiento, antes de cuatro meses si se tratare de delitos cuya pena m&aacute;xima no exceda de dos a&ntilde;os de prisi&oacute;n, y antes de un a&ntilde;o si la pena excediere de ese tiempo, salvo que solicite mayor plazo para su defensa; </i></font><font face="Arial, serif"><i><b>XI. </b></i></font><font face="Arial, serif"><i>A tener una defensa adecuada por parte de un licenciado en derecho o abogado titulado, con c&eacute;dula profesional, al cual elegir&aacute; libremente incluso desde el momento de su detenci&oacute;n y, a falta de &eacute;ste, por el Defensor p&uacute;blico que le corresponda, as&iacute; como a reunirse o entrevistarse con &eacute;l en estricta confidencialidad; </i></font><font face="Arial, serif"><i><b>XII. </b></i></font><font face="Arial, serif"><i>A ser asistido gratuitamente por un traductor o int&eacute;rprete en el caso de que no comprenda o hable el idioma espa&ntilde;ol; cuando el imputado perteneciere a un pueblo o comunidad ind&iacute;gena, el Defensor deber&aacute; tener conocimiento de su lengua y cultura y, en caso de que no fuere posible, deber&aacute; actuar asistido de un int&eacute;rprete de la cultura y lengua de que se trate; </i></font><font face="Arial, serif"><i><b>XIII. </b></i></font><font face="Arial, serif"><i>A ser presentado ante el Ministerio P&uacute;blico o ante el Juez de control, seg&uacute;n el caso, inmediatamente despu&eacute;s de ser detenido o aprehendido; </i></font><font face="Arial, serif"><i><b>XIV. </b></i></font><font face="Arial, serif"><i>A no ser expuesto a los medios de comunicaci&oacute;n; </i></font><font face="Arial, serif"><i><b>XV. </b></i></font><font face="Arial, serif"><i>A no ser presentado ante la comunidad como culpable; </i></font><font face="Arial, serif"><i><b>XVI. </b></i></font><font face="Arial, serif"><i>A solicitar desde el momento de su detenci&oacute;n, asistencia social para los menores de edad o personas con discapacidad cuyo cuidado personal tenga a su cargo; </i></font><font face="Arial, serif"><i><b>XVII. </b></i></font><font face="Arial, serif"><i>A obtener su libertad en el caso de que haya sido detenido, cuando no se ordene la prisi&oacute;n preventiva, u otra medida cautelar restrictiva de su libertad; </i></font><font face="Arial, serif"><i><b>XVIII. </b></i></font><font face="Arial, serif"><i>A que se informe a la embajada o consulado que corresponda cuando sea detenido, y se le proporcione asistencia migratoria cuando tenga nacionalidad extranjera, y </i></font><font face="Arial, serif"><i><b>XIX.</b></i></font><font face="Arial, serif"><i> Los dem&aacute;s que establezca este C&oacute;digo y otras disposiciones aplicables. Los plazos a que se refiere la fracci&oacute;n X de este art&iacute;culo, se contar&aacute;n a partir de la audiencia inicial hasta el momento en que sea dictada la sentencia emitida por el &Oacute;rgano jurisdiccional competente. Cuando el imputado tenga a su cuidado menores de edad, personas con discapacidad, o adultos mayores que dependan de &eacute;l, y no haya otra persona que pueda ejercer ese cuidado, el Ministerio P&uacute;blico deber&aacute; canalizarlos a instituciones de asistencia social que correspondan, a efecto de recibir la protecci&oacute;n&rdquo;</i></font></font></font></p>
<p align="justify" style="margin-left: 0.39in; margin-top: 0.19in; margin-bottom: 0.19in; line-height: 150%"> 
<a name="_GoBack"></a> <font face="Times New Roman, serif"><font style="font-size: 12pt"><font face="Arial, serif">Por lo anterior,&nbsp; y previamente a la pl&aacute;tica con su defensor se le hace saber que existe una investigaci&oacute;n en su contra por hechos que revisten el car&aacute;cter de una conducta que se encuentra tipificada como delito de ______________________________,&nbsp; que se hacen consistir en ________________________<br /> 
____________________________________________________________________________________________________________________________, enterado de lo anterior, y una vez que se le ha advertido que tiene el derecho a No declarar, a guardar silencio, y que el mismo no ser&aacute; utilizado en su perjuicio, pero en caso de declarar todo lo que diga podr&aacute; ser usado en su contra; en este acto se pide que se conduzca con verdad y buena fe en la presente actuaci&oacute;n, hecho lo anterior el adolescente</font><b><font face="Arial, serif">_________________________________________</font></b><font face="Arial, serif"> da por sus generales&nbsp; los siguientes: nacionalidad ______________, de _________a&ntilde;os de edad, estado civil _______________________, originario(a) de _______________________, y vecino(a) de ____________________, con domicilio en ___________________________, con tel&eacute;fono n&uacute;mero ____________, instrucci&oacute;n escolar _______________________, y por lo tanto _____ sabe leer y escribir, de ocupaci&oacute;n _________________, con ingresos econ&oacute;micos semanales de _______________________ pesos, que profesa la religi&oacute;n ________________; que ___ pertenece a un grupo &eacute;tnico; que si habla espa&ntilde;ol; que ___ fuma; que ____ ingiere bebidas embriagantes; que ______ es afecto a las drogas o enervantes, a la ______________; de apodo __________________ ,&nbsp; los nombres de sus padres son ________________________ quienes _________________________ viven, a quien se le hace saber que tiene el derecho a nombrar&nbsp; a un licenciado en derecho&nbsp; que lo defienda t&eacute;cnicamente, y en caso de que no pueda o no quiera hacerlo&nbsp; el Estado le designara un defensor p&uacute;blico, a lo que manifiesta que se da por enterado de tal derecho y que SI desea designar DEFENSOR PUBLICO; al LIC. _________________________, quien en este acto lo acompa&ntilde;a pues previo a la presente entrevista se le hizo del conocimiento detal derecho y se realiz&oacute; la llamada correspondiente a defensor&iacute;a publica para que asistiera al Adolescente a la presente diligencia, adem&aacute;s de que el adolescente y el defensor p&uacute;blico ya tuvieron la pl&aacute;tica en privado correspondiente, por lo que en este momento lo designa como su abogado defensor, identific&aacute;ndose el defensor p&uacute;blico con cedula profesional _________, expedida por _______________________, inspeccionando que los rasgos y caracter&iacute;sticas f&iacute;sicas del (a) compareciente coinciden con los de la fotograf&iacute;a que aparece en el documento con que se identifica y de la cual en este acto se autoriza y ordena a sacar copia fotost&aacute;tica de la identificaci&oacute;n antes mencionada para que&nbsp; obre en autos y una vez hecho lo anterior se devuelva el original a su presentante, quien acepta el nombramiento que se le acaba de conferir y protesta desempe&ntilde;ar el cargo fielmente, as&iacute; como protesta de decir verdad que: poseo t&iacute;tulo y/o cedula profesional, legalmente expedida por instancia competente para el ejercicio de la profesi&oacute;n, as&iacute; mismo, cuento con conocimientos suficientes sobre el sistema acusatorio penal, sobre el C&oacute;digo Nacional de Procedimientos Penales y y conoce el procedimiento que establece la Ley del sistema de Justicia para Adolescentes y dem&aacute;s disposiciones penales aplicables, tal como lo establece el art&iacute;culo </font><font color="#000000"><font face="Arial, serif">118 del C&oacute;digo Nacional de Procedimientos Penales</font></font><font face="Arial, serif"> por lo que dijo llamarse como ha quedado escrito, ser mayor de edad,&nbsp; con domicilio para o&iacute;r y recibir notificaciones en ___________________________,&nbsp; con tel&eacute;fono ________________,&nbsp; con correo electr&oacute;nico _________________________. De igual modo, con fundamento en lo dispuesto por el art&iacute;culo 46 de la Ley del Sistema Integral de Justicia para Adolescentes del Estado de Coahuila de Zaragoza, se cuenta con la presencia de su padre o madre del adolescente, quien en este acto lo acompa&ntilde;a identific&aacute;ndose con _______________ _________, expedida por _______________________, inspeccionando que los rasgos y caracter&iacute;sticas f&iacute;sicas del (a) compareciente coinciden con los de la fotograf&iacute;a que aparece en el documento con que se identifica y adjunta el acta de nacimiento del menor de la cual ya se hab&iacute;a hecho referencia y ordena a sacar copia fotost&aacute;tica de la identificaci&oacute;n antes mencionada para que&nbsp; obre en autos y una vez hecho lo anterior se devuelva el original a su presentante, por sus generales&nbsp; los siguientes: nacionalidad ______________, de _________a&ntilde;os de edad, estado civil _______________________, originario(a) de _______________________, y vecino(a) de ____________________, con domicilio en ___________________________, con tel&eacute;fono n&uacute;mero ____________, instrucci&oacute;n escolar _______________________, y por lo tanto _____ sabe leer y escribir, de ocupaci&oacute;n _________________, __________________________,&nbsp; ACTO CONTINUO, se le pregunta al adolescente____________________________, si es su deseo declarar respecto a los hechos que se le atribuyen y </font><font face="Arial, serif"><b>MANIFIESTA: </b></font><font face="Arial, serif">Que enterado de los derechos que me concede el </font><font color="#000000"><font face="Arial, serif">art&iacute;culo 20 apartado B) de la Constituci&oacute;n Pol&iacute;tica de los Estados Unidos Mexicanos, y el art&iacute;culo 113 del C&oacute;digo Nacional de Procedimientos Penales,</font></font><font face="Arial, serif"> es mi deseo DECLARAR libremente________________________________________________________________________________________________________________________________________se graba la declaraci&oacute;n del adolescente ____________________________________________________________________________________________________________________________________________________________________________siento todo lo que deseo manifestar. EN ESTE ACTO se le concede el uso de la voz al defensor p&uacute;blico por si desea manifestar________________________ As&iacute; mismo se le concede el uso de la voz a su representante :_______________________ en vista de lo anterior se concluye la presente diligencia</font></font></font></p>
<p align="justify" style="margin-left: 0.39in; margin-top: 0.19in; margin-bottom: 0.19in; line-height: 150%"> 
<font face="Times New Roman, serif"><font style="font-size: 12pt"><font face="Arial, serif">. &nbsp;</font></font></font></p>
<p align="center" style="margin-left: 0.83in; margin-top: 0.19in; margin-bottom: 0.19in; line-height: 150%"> 
<font face="Times New Roman, serif"><font style="font-size: 12pt"><font face="Arial, serif">AGENTE DEL MINISTERIO P&Uacute;BLICO ESPECIALIZADO</font></font></font></p>
<p align="center" style="margin-left: 0.83in; margin-top: 0.19in; margin-bottom: 0.19in; line-height: 150%"> 
<font face="Times New Roman, serif"><font style="font-size: 12pt"><font face="Arial, serif">LIC.</font></font></font></p>
<p align="center" style="margin-left: 0.83in; margin-top: 0.19in; margin-bottom: 0.19in; line-height: 150%"> 
&nbsp;</p>
<p align="center" style="margin-left: 0.83in; margin-top: 0.19in; margin-bottom: 0.19in; line-height: 150%"> 
<br /> 
<font face="Times New Roman, serif"><font style="font-size: 12pt"><font face="Arial, serif">ADOLESCENTE</font></font></font></p>
<p align="center" style="margin-left: 0.83in; margin-top: 0.19in; margin-bottom: 0.19in; line-height: 150%"> 
&nbsp;</p>
<p align="center" style="margin-left: 0.83in; margin-top: 0.19in; margin-bottom: 0.19in; line-height: 150%"> 
<font face="Times New Roman, serif"><font style="font-size: 12pt"><font face="Arial, serif">ABOGADO DEFENSOR</font></font></font></p>
<p align="center" style="margin-left: 0.83in; margin-top: 0.19in; margin-bottom: 0.19in; line-height: 150%"> 
<br /> 
&nbsp;</p>
<p align="center" style="margin-left: 0.83in; margin-top: 0.19in; margin-bottom: 0.19in; line-height: 150%"> 
<font face="Times New Roman, serif"><font style="font-size: 12pt"><font face="Arial, serif">MADRE O PADRE</font></font></font></p>
<p align="center" style="margin-bottom: 0.14in; line-height: 150%"> 
<br /> 
&nbsp;</p>
<p style="margin-bottom: 0.14in; line-height: 150%"> 
<br /> 
&nbsp;</p>'
    ,
    'NOMBRAMIENTO DEL DEFENSOR Y DECLARACION DEL IMPUTADO DECLARA DEFENSOR PUBLICO'
  )
  ,
  (
    'NOMBRAMIENTO DEL DEFENSOR Y DECLARACION DEL IMPUTADO SE RESERVA 12 A 14 AÑOS'
    ,
    1611,
    '<p align="center" style="margin-top: 0.19in; margin-bottom: 0.19in; line-height: 150%"> 
<font face="Times New Roman, serif"><font style="font-size: 12pt"><b><font face="Arial, serif">ENTREVISTA DEL ADOLESCENTE </font></b></font></font></p>
<p style="margin-left: 0.39in; margin-top: 0.19in; margin-bottom: 0.19in; line-height: 150%"> 
<br /> 
&nbsp;</p>
<p style="margin-left: 0.39in; margin-top: 0.19in; margin-bottom: 0.19in; line-height: 150%"> 
<font face="Times New Roman, serif"><font style="font-size: 12pt"><font face="Arial, serif">N&uacute;m. Carpeta de investigaci&oacute;n:&nbsp;_____________________</font></font></font></p>
<p style="margin-left: 0.39in; margin-top: 0.19in; margin-bottom: 0.19in; line-height: 150%"> 
<font face="Times New Roman, serif"><font style="font-size: 12pt"><font face="Arial, serif">NUC:_____________________________<br /> 
</font><font face="Arial, serif">Ciudad: ________________ Estado: ________________</font><br /> 
<font face="Arial, serif"> Fecha: _____________&nbsp; Hora:</font><b><font face="Arial, serif">____________</font></b><br /> 
<font face="Arial, serif"> Agente del Ministerio P&uacute;blico:_________________________<br /> 
Unidad de investigaci&oacute;n:</font><b><font face="Arial, serif">_____________________________</font></b><br /> 
<font face="Arial, serif"> Domicilio de la unidad:______________________________</font></font></font></p>
<p align="justify" style="margin-bottom: 0in; line-height: 150%"> 
&nbsp;</p>
<p align="justify" style="margin-bottom: 0in; line-height: 150%"> 
<font face="Consolas, serif"><font style="font-size: 10pt"><font face="Arial, serif"><font style="font-size: 12pt">Ante el Agente del Ministerio P&uacute;blico Especializado _______________, se procedi&oacute; a comunicar al adolescente __________________________,&nbsp; quien se identifica con&nbsp; ______________________(acta de nacimiento), documento que presenta en original y en este acto se le devuelve, previo cotejo con la copia simple que anexa de la misma para que obre dentro de la presente carpeta de investigaci&oacute;n, esta Representaci&oacute;n Social le hace del conocimiento del contenido </font></font><font color="#000000"><font face="Arial, serif"><font style="font-size: 12pt">del art&iacute;culo 20 apartado B) de la Constituci&oacute;n Pol&iacute;tica de los Estados Unidos Mexicanos, as&iacute; como tambi&eacute;n se le hace del conocimiento de los siguientes derechos que le otorga la Ley del Sistema Integral de Justicia para Adolescentes:</font></font></font></font></font></p>
<p align="justify" style="margin-left: 0.39in; margin-top: 0.19in; margin-bottom: 0.19in; line-height: 150%"> 
<font face="Times New Roman, serif"><font style="font-size: 12pt"><font face="Arial, serif">: </font><font face="Arial, serif"><i><b>&ldquo;ART&Iacute;CULO 38.- DERECHO A LA INTIMIDAD Y LA PRIVACIDAD.</b></i></font><font face="Arial, serif"><i> En todo proceso se respetar&aacute; el derecho a la intimidad de cualquier persona que intervenga en &eacute;l, asimismo se proteger&aacute; la informaci&oacute;n que se refiere a la vida privada y los datos personales, en los t&eacute;rminos y con las excepciones que fijan la Constituci&oacute;n Pol&iacute;tica de los Estados Unidos Mexicanos, esta ley y la legislaci&oacute;n aplicable</i></font><font face="Arial, serif">. </font><font face="Arial, serif"><i>- - - Todo adolescente tendr&aacute; derecho a que no se divulgue su identidad, ni el nombre de sus padres o cualquier dato que permita su identificaci&oacute;n p&uacute;blica, salvo cuando se encuentre pr&oacute;fugo y atendiendo a la peligrosidad y gravedad del delito, buscando preservar la seguridad de la comunidad.- - - Con la excepci&oacute;n se&ntilde;alada en el p&aacute;rrafo anterior, el juez correspondiente vigilar&aacute; en todo momento que no sea vulnerado el derecho del adolescente a la privacidad.- - A quien divulgue total o parcialmente por cualquier medio de comunicaci&oacute;n, la identidad, el nombre, hecho o documento relativo a una investigaci&oacute;n o a un proceso judicial en cualquier fase en la que &eacute;ste se encuentre y en el que se atribuya al adolescente una conducta tipificada como delito por las leyes penales, se le impondr&aacute; una multa de cien a trescientos d&iacute;as de salario m&iacute;nimo vigente en la entidad. - - - Las autoridades especializadas en justicia para adolescentes deber&aacute;n llevar el registro de los adolescentes que han cometido conductas tipificadas como delitos, con el objeto de definir los lineamientos de la pol&iacute;tica criminal para adolescentes, y s&oacute;lo podr&aacute;n otorgar informaci&oacute;n sobre estad&iacute;sticas siempre que no contravenga el principio de confidencialidad y el derecho a la privacidad consagrado en esta ley. - - - - -Los antecedentes y registros relacionados con adolescentes sometidos a proceso o sancionados conforme a esta ley ser&aacute;n de car&aacute;cter estrictamente confidencial y s&oacute;lo podr&aacute;n ser utilizados por las autoridades judiciales competentes para definir las medidas aplicables cuando se trate de establecer la naturaleza y gravedad de las conductas y la proporcionalidad e idoneidad de la medida. En ning&uacute;n caso podr&aacute;n ser utilizados en otros procesos en los que est&eacute; implicada la misma persona, salvo para establecer que el adolescente se encuentra gozando de la suspensi&oacute;n a prueba en otro proceso. - - - - Cumplida o extinguida la medida impuesta o transcurrido el t&eacute;rmino de la prescripci&oacute;n, el juez de ejecuci&oacute;n decretar&aacute; el cierre del expediente, remiti&eacute;ndolo al Centro de Internaci&oacute;n para que en su debido momento sea destruido, cualquiera que haya sido la determinaci&oacute;n adoptada.- - -</i></font><font face="Arial, serif"><i><b>ART&Iacute;CULO 39.- GARANT&Iacute;A DE SER INFORMADO DE SUS DERECHOS.</b></i></font><font face="Arial, serif"><i> Todas las autoridades que intervengan en los actos iniciales del proceso deber&aacute;n velar porque tanto el adolescente como la v&iacute;ctima u ofendido conozcan los derechos que le reconocen en ese momento procedimental la Constituci&oacute;n Pol&iacute;tica de los Estados Unidos Mexicanos, los Tratados Internacionales celebrados y aprobados por el Estados mexicano, y las leyes que de ellos emanen, en los t&eacute;rminos establecidos en la presente ley. - - - </i></font><font face="Arial, serif"><i><b>ART&Iacute;CULO 40.- DERECHO DE DEFENSA Y ASESOR&Iacute;A JUR&Iacute;DICA ADECUADA E INMEDIATA.</b></i></font><font face="Arial, serif"><i> La carga de la prueba la tiene la parte acusadora. No obstante, el adolescente acusado tendr&aacute; el derecho de presentar las pruebas y los argumentos necesarios para su defensa y de rebatir cuando les sea contrario. En ning&uacute;n caso podr&aacute; juzg&aacute;rsele en ausencia. -- - - - La v&iacute;ctima u ofendido tendr&aacute; derecho a contar con un asesor jur&iacute;dico gratuito en cualquier etapa del proceso, en los t&eacute;rminos de la legislaci&oacute;n aplicable. - - -Corresponde al juez velar sin preferencias ni desigualdades por la defensa adecuada y t&eacute;cnica del adolescente. - - - </i></font><font face="Arial, serif"><i><b>ART&Iacute;CULO 41.- DEFENSA T&Eacute;CNICA.</b></i></font><font face="Arial, serif"><i> Desde el momento de su detenci&oacute;n y a lo largo de todo el proceso, el adolescente deber&aacute; ser asistido por un defensor especializado; si as&iacute; lo desea designar&aacute; a su costa, por s&iacute; o por sus padres, tutores o representantes legales a un defensor privado, con c&eacute;dula profesional que lo acredite como Licenciado en Derecho para que lo asista jur&iacute;dicamente; deber&aacute; estar asistido en todos los actos del proceso, a&uacute;n los de car&aacute;cter ministerial y de ejecuci&oacute;n de las medidas que le impongan. - - - En caso de ser ind&iacute;genas, extranjeros, sordos, mudos o no sepan leer ni escribir, deber&aacute;n ser asistidos de oficio y en todos los actos procesales, por un defensor que comprenda plenamente su idioma, lengua, dialecto, as&iacute; como su cultura o bien, de ser necesario, que su defensor sea auxiliado por un perito traductor o int&eacute;rprete, asignado por la autoridad correspondiente o designado por el adolescente. Cuando el adolescente alegue ser ind&iacute;gena, se acreditar&aacute; s&oacute;lo con su manifestaci&oacute;n. - - - - En todo caso el defensor deber&aacute; poseer conocimientos en materia penal, procesal penal y de justicia para adolescentes suficientes para brindarle al adolescente una defensa adecuada. A la persona que designe el adolescente o representantes legales ser&aacute; interrogado durante la instancia, bajo protesta de decir verdad, si cumple con las condiciones anteriores. Igualmente, protestar&aacute; cumplir el cargo con fidelidad y que le brindar&aacute; al adolescente una defensa adecuada. Se dejar&aacute; constancia de todo lo anterior. En caso de que no elija su propio defensor, se le nombrar&aacute; un defensor p&uacute;blico. - - - - No podr&aacute; recib&iacute;rsele ninguna declaraci&oacute;n sin la asistencia de &eacute;ste, ni por otra autoridad que no sea el juez, bajo pena de nulidad.- - - En las entrevistas que realice el Ministerio P&uacute;blico al adolescente, &eacute;ste deber&aacute; estar asistido por su defensor. - - - El adolescente tendr&aacute; derecho a reunirse oportunamente con su defensor en estricta confidencialidad. - - - La intervenci&oacute;n del defensor no menoscabar&aacute; el derecho del adolescente de intervenir, formular peticiones y hacer las manifestaciones que estime pertinentes. - - - </i></font><font face="Arial, serif"><i><b>ART&Iacute;CULO 42.- PROHIBICI&Oacute;N DE INCOMUNICACI&Oacute;N. Todo</b></i></font><font face="Arial, serif"><i> adolescente, inmediatamente despu&eacute;s de ser detenido, tendr&aacute; derecho a establecer una comunicaci&oacute;n efectiva, por v&iacute;a telef&oacute;nica o por escrito, con su familia, defensor o con la persona o instituci&oacute;n a quien desee informar sobre el hecho de su detenci&oacute;n o privaci&oacute;n de libertad. - - - Durante todas las etapas del proceso, a&uacute;n las de car&aacute;cter ministerial, tendr&aacute; el derecho a ser visitado, entrevistado y a tener comunicaci&oacute;n con su defensor, as&iacute; como con sus padres, tutores o representantes legales, ya sea en forma conjunta o separada. Las entrevistas deber&aacute;n realizarse bajo un r&eacute;gimen de absoluta confidencialidad. Tendr&aacute; derecho tambi&eacute;n, en todo momento, a enviar y recibir correspondencia. - - - </i></font><font face="Arial, serif"><i><b>ART&Iacute;CULO 43.- GARANT&Iacute;AS DE LA DETENCI&Oacute;N.</b></i></font><font face="Arial, serif"><i> Todo adolescente tendr&aacute; derecho a ser presentado inmediatamente y sin demora ante el juez o el Ministerio P&uacute;blico, siempre dentro de los plazos que establece esta ley, as&iacute; como a no ser conducido o apresado de modo que se afecte su dignidad o se le exponga a alg&uacute;n peligro. - - - - </i></font><font face="Arial, serif"><i><b>ART&Iacute;CULO 44.- CONOCIMIENTO DE LA IMPUTACI&Oacute;N.</b></i></font><font face="Arial, serif"><i> Todo adolescente tendr&aacute; derecho a ser informado directamente, sin demora y en un lenguaje claro y accesible sobre las razones por las que se les detiene, juzga o impone una medida, la persona que les atribuye la realizaci&oacute;n de una conducta que la ley tipifique como delito; las consecuencias de la atribuci&oacute;n de un hecho, as&iacute; como de la detenci&oacute;n, juicio y medida; que podr&aacute; disponer de una defensa jur&iacute;dica gratuita, a solicitar la presencia inmediata de sus padres, tutores o representantes y todos los derechos y garant&iacute;as que les asisten respecto de su sujeci&oacute;n al Sistema Integral de Justicia para Adolescentes. - - - La autoridad tiene el deber de localizar e informar de manera inmediata a los padres, tutores o responsables legales acerca de la detenci&oacute;n del adolescente sujeto a su custodia, salvo que sea por su propia seguridad y atendiendo al inter&eacute;s superior del adolescente. - - - - </i></font><font face="Arial, serif"><i><b>ART&Iacute;CULO 45.- DERECHO A SER ESCUCHADO.</b></i></font><font face="Arial, serif"><i> Todo adolescente tendr&aacute; derecho a ser escuchado en cualquier etapa del proceso, desde el inicio de la investigaci&oacute;n hasta que cumpla con la medida que en su caso le sea impuesta. - - - - El adolescente que no comprenda ni pueda darse a entender en espa&ntilde;ol deber&aacute; ser provisto gratuitamente de un traductor o interprete a fin de que pueda expresarse en su propia lengua. Incluso si habla o comprende el espa&ntilde;ol, si se trata de un adolescente ind&iacute;gena, se le nombrar&aacute; un int&eacute;rprete en caso de que as&iacute; lo solicite. - - - - Si se trata de un adolescente mudo, se le har&aacute;n oralmente las preguntas y las responder&aacute; por escrito; si fuere un sordomudo, las preguntas y respuestas ser&aacute;n escritas. Si el adolescente no supiere leer y escribir se le nombrar&aacute; int&eacute;rprete id&oacute;neo. - - - </i></font><font face="Arial, serif"><i><b>ART&Iacute;CULO 46.- DERECHO DE ABSTENERSE DE DECLARAR.</b></i></font><font face="Arial, serif"><i> Todo adolescente tendr&aacute; derecho a abstenerse de declarar, a no auto-incriminarse y a no responder las preguntas que se le formulen. Su silencio no podr&aacute; ser valorado en su contra. El juez, previo a recibir su declaraci&oacute;n, informar&aacute; al adolescente sobre este derecho. - - - - Si consintiera en prestar declaraci&oacute;n, deber&aacute; hacerlo ante el juez y previa entrevista en privado con su defensor. Lo mismo se observar&aacute; para el caso de las entrevistas que el Ministerio P&uacute;blico haga al adolescente. A estar asistido de su Defensor al momento de rendir su declaraci&oacute;n, as&iacute; como en cualquier otra actuaci&oacute;n y a entrevistarse en privado previamente con &eacute;l. En los casos en que el adolescente tenga una edad de entre doce y catorce a&ntilde;os, tambi&eacute;n ser&aacute; necesaria la presencia de sus padres, tutores o quienes ejerzan la patria potestad o custodia, si &eacute;l y su defensa lo estiman conveniente. - - - - En ning&uacute;n caso se le exigir&aacute; protesta de decir verdad. - - - - Se proh&iacute;be el uso de cualquier medio para hacerle declarar en su contra o en contra de otra persona, as&iacute; como formularle cargos evidentemente improcedentes con el prop&oacute;sito de obtener una confesi&oacute;n. - - - - </i></font><font face="Arial, serif"><i><b>ART&Iacute;CULO 47.- DERECHO DE PENSAMIENTO, CONCIENCIA Y RELIGI&Oacute;N.</b></i></font><font face="Arial, serif"><i> Durante la investigaci&oacute;n, la tramitaci&oacute;n del proceso y la ejecuci&oacute;n de las sanciones, se respetar&aacute;n al adolescente sus creencias, su religi&oacute;n y sus pautas culturales y morales. - - - - </i></font><font face="Arial, serif"><i><b>ART&Iacute;CULO 48.- PARTICIPACI&Oacute;N DE LOS PADRES O RESPONSABLES EN EL PROCESO.</b></i></font><font face="Arial, serif"><i> Los padres, tutores o responsables legales del adolescente tendr&aacute;n derecho a participar en las actuaciones, tanto judiciales como ministeriales, y las autoridades competentes podr&aacute;n requerir su presencia en cada una de &eacute;stas. Esta presencia es considerada como una asistencia general al adolescente, de naturaleza psicol&oacute;gica y emotiva, que debe extenderse a lo largo del todo el proceso. Dicha participaci&oacute;n podr&aacute; ser denegada por la autoridad competente cuando existan motivos fundados para presumir que la exclusi&oacute;n es necesaria en defensa del adolescente. - - - En todo caso, en cualquier etapa del proceso, previa vista y consentimiento, la autoridad competente podr&aacute; requerir a los padres, tutores o quienes ejerzan la patria potestad o custodia del adolescente a colaborar en el cumplimiento de las siguientes actividades: - - - </i></font><font face="Arial, serif"><i><b>I.</b></i></font><font face="Arial, serif"><i> Asistir a programas p&uacute;blicos, sociales o privados de protecci&oacute;n y orientaci&oacute;n individual y familiar; </i></font><font face="Arial, serif"><i><b>II.</b></i></font><font face="Arial, serif"><i> Inclusi&oacute;n en programas p&uacute;blicos, sociales o privados de auxilio, orientaci&oacute;n y tratamiento a alcoh&oacute;licos, neur&oacute;ticos y toxic&oacute;manos; </i></font><font face="Arial, serif"><i><b>III.</b></i></font><font face="Arial, serif"><i> Asistir a tratamiento psicol&oacute;gico o psiqui&aacute;trico; </i></font><font face="Arial, serif"><i><b>IV.</b></i></font><font face="Arial, serif"><i> Matricular al adolescente en instituciones educativas y observar su asistencia y aprovechamiento escolar; </i></font><font face="Arial, serif"><i><b>V.</b></i></font><font face="Arial, serif"><i> Encauzar al adolescente a tratamiento especializado, y </i></font><font face="Arial, serif"><i><b>VI.</b></i></font><font face="Arial, serif"><i> Las dem&aacute;s que procedan de acuerdo a esta ley, otras disposiciones legales aplicables y a las circunstancias propias del adolescente. - - -En la resoluci&oacute;n definitiva el juez tomar&aacute; en consideraci&oacute;n, en beneficio del adolescente, la colaboraci&oacute;n que durante el proceso hayan tenido sus padres, tutores o quienes ejerzan la patria potestad o custodia en el cumplimiento de las actividades que en su caso se les hayan recomendado. - - - - </i></font><font face="Arial, serif"><i><b>ART&Iacute;CULO 49.- DERECHO A IMPUGNAR. </b></i></font><font face="Arial, serif"><i>Todo adolescente tendr&aacute; derecho a impugnar ante la autoridad judicial competente, en los supuestos previstos por esta ley, cualquier resoluci&oacute;n definitiva o provisional que le cause un agravio irreparable. - - - - </i></font><font face="Arial, serif"><i><b>ART&Iacute;CULO 50.- ASISTENCIA.</b></i></font><font face="Arial, serif"><i> Los adolescentes y, cuando proceda, sus familiares deber&aacute;n tener acceso a la asistencia de profesionales capacitados, lo que incluye servicios de asistencia y apoyo tales como servicios financieros, jur&iacute;dicos, de orientaci&oacute;n, salud, sociales y educativos, de recuperaci&oacute;n f&iacute;sica y psicol&oacute;gica y dem&aacute;s servicios necesarios para la reinserci&oacute;n del adolescente. En todo caso, cuando el juez constate la necesidad de apoyo especializado para el adolescente, deber&aacute; canalizarlo con la instancia que se determine, a fin de brindar la atenci&oacute;n que requiera para poder participar de manera efectiva en el proceso de justicia. En caso de que la persona especializada que brind&oacute; la atenci&oacute;n al adolescente concluya en su evaluaci&oacute;n que &eacute;ste requiere de tratamiento para poder participar en el juicio, el juez deber&aacute; atender las recomendaciones que se se&ntilde;alen en aquella</i></font><font face="Arial, serif">.&rdquo; Adem&aacute;s de los derechos que le confiere el art&iacute;culo 113 del C&oacute;digo Nacional de Procedimientos Penales.</font></font></font></p>
<p align="justify" style="margin-left: 0.39in; margin-top: 0.19in; margin-bottom: 0.19in; line-height: 150%"> 
<font face="Times New Roman, serif"><font style="font-size: 12pt"><font color="#000000"><font face="Arial, serif">que a la letra dice:&rdquo; </font></font><font color="#000000"><font face="Arial, serif"><i>El imputado tendr&aacute; los siguientes derechos: </i></font></font><font face="Arial, serif"><i><b>I. </b></i></font><font face="Arial, serif"><i>A ser considerado y tratado como inocente hasta que se demuestre su responsabilidad; </i></font><font face="Arial, serif"><i><b>II. </b></i></font><font face="Arial, serif"><i>A comunicarse con un familiar y con su Defensor cuando sea detenido, debiendo brindarle el Ministerio P&uacute;blico todas las facilidades para lograrlo; </i></font><font face="Arial, serif"><i><b>III. </b></i></font><font face="Arial, serif"><i>A declarar o a guardar silencio, en el entendido que su silencio no podr&aacute; ser utilizado en su perjuicio; </i></font><font face="Arial, serif"><i><b>IV. </b></i></font><font face="Arial, serif"><i>A estar asistido de su Defensor al momento de rendir su declaraci&oacute;n, as&iacute; como en cualquier otra actuaci&oacute;n y a entrevistarse en privado previamente con &eacute;l; </i></font><font face="Arial, serif"><i><b>V. </b></i></font><font face="Arial, serif"><i>A que se le informe, tanto en el momento de su detenci&oacute;n como en su comparecencia ante el Ministerio P&uacute;blico o el Juez de control, los hechos que se le imputan y los derechos que le asisten, as&iacute; como, en su caso, el motivo de la privaci&oacute;n de su libertad y el servidor p&uacute;blico que la orden&oacute;, exhibi&eacute;ndosele, seg&uacute;n corresponda, la orden emitida en su contra; </i></font><font face="Arial, serif"><i><b>VI. </b></i></font><font face="Arial, serif"><i>A no ser sometido en ning&uacute;n momento del procedimiento a t&eacute;cnicas ni m&eacute;todos que atenten contra su dignidad, induzcan o alteren su libre voluntad; </i></font><font face="Arial, serif"><i><b>VII. </b></i></font><font face="Arial, serif"><i>A solicitar ante la autoridad judicial la modificaci&oacute;n de la medida cautelar que se le haya impuesto, en los casos en que se encuentre en prisi&oacute;n preventiva, en los supuestos se&ntilde;alados por este C&oacute;digo; </i></font><font face="Arial, serif"><i><b>VIII. </b></i></font><font face="Arial, serif"><i>A tener acceso &eacute;l y su defensa a los registros de la investigaci&oacute;n, as&iacute; como a obtener copia gratuita de los mismos, en t&eacute;rminos del art&iacute;culo 217 de este C&oacute;digo; </i></font><font face="Arial, serif"><i><b>IX. </b></i></font><font face="Arial, serif"><i>A que se le reciban los medios pertinentes de prueba que ofrezca, concedi&eacute;ndosele el tiempo necesario para tal efecto y auxili&aacute;ndosele para obtener la comparecencia de las personas cuyo testimonio solicite y que no pueda presentar directamente, en t&eacute;rminos de lo establecido por este C&oacute;digo; </i></font><font face="Arial, serif"><i><b>X. </b></i></font><font face="Arial, serif"><i>A ser juzgado en audiencia por un Tribunal de enjuiciamiento, antes de cuatro meses si se tratare de delitos cuya pena m&aacute;xima no exceda de dos a&ntilde;os de prisi&oacute;n, y antes de un a&ntilde;o si la pena excediere de ese tiempo, salvo que solicite mayor plazo para su defensa; </i></font><font face="Arial, serif"><i><b>XI. </b></i></font><font face="Arial, serif"><i>A tener una defensa adecuada por parte de un licenciado en derecho o abogado titulado, con c&eacute;dula profesional, al cual elegir&aacute; libremente incluso desde el momento de su detenci&oacute;n y, a falta de &eacute;ste, por el Defensor p&uacute;blico que le corresponda, as&iacute; como a reunirse o entrevistarse con &eacute;l en estricta confidencialidad; </i></font><font face="Arial, serif"><i><b>XII. </b></i></font><font face="Arial, serif"><i>A ser asistido gratuitamente por un traductor o int&eacute;rprete en el caso de que no comprenda o hable el idioma espa&ntilde;ol; cuando el imputado perteneciere a un pueblo o comunidad ind&iacute;gena, el Defensor deber&aacute; tener conocimiento de su lengua y cultura y, en caso de que no fuere posible, deber&aacute; actuar asistido de un int&eacute;rprete de la cultura y lengua de que se trate; </i></font><font face="Arial, serif"><i><b>XIII. </b></i></font><font face="Arial, serif"><i>A ser presentado ante el Ministerio P&uacute;blico o ante el Juez de control, seg&uacute;n el caso, inmediatamente despu&eacute;s de ser detenido o aprehendido; </i></font><font face="Arial, serif"><i><b>XIV. </b></i></font><font face="Arial, serif"><i>A no ser expuesto a los medios de comunicaci&oacute;n; </i></font><font face="Arial, serif"><i><b>XV. </b></i></font><font face="Arial, serif"><i>A no ser presentado ante la comunidad como culpable; </i></font><font face="Arial, serif"><i><b>XVI. </b></i></font><font face="Arial, serif"><i>A solicitar desde el momento de su detenci&oacute;n, asistencia social para los menores de edad o personas con discapacidad cuyo cuidado personal tenga a su cargo; </i></font><font face="Arial, serif"><i><b>XVII. </b></i></font><font face="Arial, serif"><i>A obtener su libertad en el caso de que haya sido detenido, cuando no se ordene la prisi&oacute;n preventiva, u otra medida cautelar restrictiva de su libertad; </i></font><font face="Arial, serif"><i><b>XVIII. </b></i></font><font face="Arial, serif"><i>A que se informe a la embajada o consulado que corresponda cuando sea detenido, y se le proporcione asistencia migratoria cuando tenga nacionalidad extranjera, y </i></font><font face="Arial, serif"><i><b>XIX.</b></i></font><font face="Arial, serif"><i> Los dem&aacute;s que establezca este C&oacute;digo y otras disposiciones aplicables. Los plazos a que se refiere la fracci&oacute;n X de este art&iacute;culo, se contar&aacute;n a partir de la audiencia inicial hasta el momento en que sea dictada la sentencia emitida por el &Oacute;rgano jurisdiccional competente. Cuando el imputado tenga a su cuidado menores de edad, personas con discapacidad, o adultos mayores que dependan de &eacute;l, y no haya otra persona que pueda ejercer ese cuidado, el Ministerio P&uacute;blico deber&aacute; canalizarlos a instituciones de asistencia social que correspondan, a efecto de recibir la protecci&oacute;n&rdquo;</i></font></font></font></p>
<p align="justify" style="margin-left: 0.39in; margin-top: 0.19in; margin-bottom: 0.19in; line-height: 150%"> 
<a name="_GoBack"></a> <font face="Times New Roman, serif"><font style="font-size: 12pt"><font face="Arial, serif">Por lo anterior,&nbsp; y previamente a la pl&aacute;tica con su defensor se le hace saber que existe una investigaci&oacute;n en su contra por hechos que revisten el car&aacute;cter de una conducta que se encuentra tipificada como delito de ______________________________,&nbsp; que se hacen consistir en ________________________<br /> 
____________________________________________________________________________________________________________________________, enterado de lo anterior, y una vez que se le ha advertido que tiene el derecho a No declarar, a guardar silencio, y que el mismo no ser&aacute; utilizado en su perjuicio, pero en caso de declarar todo lo que diga podr&aacute; ser usado en su contra; en este acto se pide que se conduzca con verdad y buena fe en la presente actuaci&oacute;n, hecho lo anterior el adolescente</font><b><font face="Arial, serif">_________________________________________</font></b><font face="Arial, serif"> da por sus generales&nbsp; los siguientes: nacionalidad ______________, de _________a&ntilde;os de edad, estado civil _______________________, originario(a) de _______________________, y vecino(a) de ____________________, con domicilio en ___________________________, con tel&eacute;fono n&uacute;mero ____________, instrucci&oacute;n escolar _______________________, y por lo tanto _____ sabe leer y escribir, de ocupaci&oacute;n _________________, con ingresos econ&oacute;micos semanales de _______________________ pesos, que profesa la religi&oacute;n ________________; que ___ pertenece a un grupo &eacute;tnico; que si habla espa&ntilde;ol; que ___ fuma; que ____ ingiere bebidas embriagantes; que ______ es afecto a las drogas o enervantes, a la ______________; de apodo __________________ ,&nbsp; los nombres de sus padres son ________________________ quienes _________________________ viven, a quien se le hace saber que tiene el derecho a nombrar&nbsp; a un licenciado en derecho&nbsp; que lo defienda t&eacute;cnicamente, y en caso de que no pueda o no quiera hacerlo&nbsp; el Estado le designara un defensor p&uacute;blico, a lo que manifiesta que se da por enterado de tal derecho y que SI desea designar DEFENSOR PARTICULAR; al LIC. _________________________, quien en este acto lo acompa&ntilde;a identific&aacute;ndose con cedula profesional _________, expedida por _______________________, inspeccionando que los rasgos y caracter&iacute;sticas f&iacute;sicas del (a) compareciente coinciden con los de la fotograf&iacute;a que aparece en el documento con que se identifica y de la cual en este acto se autoriza y ordena a sacar copia fotost&aacute;tica de la identificaci&oacute;n antes mencionada para que&nbsp; obre en autos y una vez hecho lo anterior se devuelva el original a su presentante, quien acepta el nombramiento que se le acaba de conferir y protesta desempe&ntilde;ar el cargo fielmente, as&iacute; como protesta de decir verdad que: poseo t&iacute;tulo y/o cedula profesional, legalmente expedida por instancia competente para el ejercicio de la profesi&oacute;n, as&iacute; mismo, cuento con conocimientos suficientes sobre el sistema acusatorio penal, sobre el C&oacute;digo Nacional de Procedimientos Penales y y conoce el procedimiento que establece la Ley del sistema de Justicia para Adolescentes y dem&aacute;s disposiciones penales aplicables, tal como lo establece el art&iacute;culo </font><font color="#000000"><font face="Arial, serif">118 del C&oacute;digo Nacional de Procedimientos Penales</font></font><font face="Arial, serif"> por lo que dijo llamarse como ha quedado escrito, ser mayor de edad,&nbsp; con domicilio para o&iacute;r y recibir notificaciones en ___________________________,&nbsp; con tel&eacute;fono ________________,&nbsp; con correo electr&oacute;nico _________________________. De igual modo, con fundamento en lo dispuesto por el art&iacute;culo 46 de la Ley del Sistema Integral de Justicia para Adolescentes del Estado de Coahuila de Zaragoza, se cuenta con la presencia de su padre o madre del adolescente, quien en este acto lo acompa&ntilde;a identific&aacute;ndose con _______________ _________, expedida por _______________________, inspeccionando que los rasgos y caracter&iacute;sticas f&iacute;sicas del (a) compareciente coinciden con los de la fotograf&iacute;a que aparece en el documento con que se identifica y adjunta el acta de nacimiento del menor de la cual ya se hab&iacute;a hecho referencia y ordena a sacar copia fotost&aacute;tica de la identificaci&oacute;n antes mencionada para que&nbsp; obre en autos y una vez hecho lo anterior se devuelva el original a su presentante, por sus generales&nbsp; los siguientes: nacionalidad ______________, de _________a&ntilde;os de edad, estado civil _______________________, originario(a) de _______________________, y vecino(a) de ____________________, con domicilio en ___________________________, con tel&eacute;fono n&uacute;mero ____________, instrucci&oacute;n escolar _______________________, y por lo tanto _____ sabe leer y escribir, de ocupaci&oacute;n _________________, __________________________,&nbsp; ACTO CONTINUO, se le pregunta al adolescente____________________________, si es su deseo declarar respecto a los hechos que se le atribuyen y </font><font face="Arial, serif"><b>MANIFIESTA: </b></font><font face="Arial, serif">Que enterado de los derechos que me concede el </font><font color="#000000"><font face="Arial, serif">art&iacute;culo 20 apartado B) de la Constituci&oacute;n Pol&iacute;tica de los Estados Unidos Mexicanos, y el art&iacute;culo 113 del C&oacute;digo Nacional de Procedimientos Penales,</font></font><font face="Arial, serif"> es mi deseo RESERVARME mi derecho a declarar____________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________ EN ESTE ACTO se le da el uso de la voz al abogado defensor quien manifiesta________________ ASI COMO al representante del adolescente quien manifiesta ___________________En vista de lo anterior, se acuerda ___________________ y se concluye la presente diligencia </font></font></font></p>
<p align="center" style="margin-left: 0.83in; margin-top: 0.19in; margin-bottom: 0.19in; line-height: 150%"> 
<font face="Times New Roman, serif"><font style="font-size: 12pt"><font face="Arial, serif">AGENTE DEL MINISTERIO P&Uacute;BLICO ESPECIALIZADO</font></font></font></p>
<p align="center" style="margin-left: 0.83in; margin-top: 0.19in; margin-bottom: 0.19in; line-height: 150%"> 
<font face="Times New Roman, serif"><font style="font-size: 12pt"><font face="Arial, serif">LIC. </font></font></font></p>
<p style="margin-left: 0.83in; margin-top: 0.19in; margin-bottom: 0.19in; line-height: 150%"> 
<br /> 
&nbsp;</p>
<p align="center" style="margin-left: 0.83in; margin-top: 0.19in; margin-bottom: 0.19in; line-height: 150%"> 
<br /> 
<font face="Times New Roman, serif"><font style="font-size: 12pt"><font face="Arial, serif">ADOLESCENTE</font></font></font></p>
<p align="center" style="margin-left: 0.83in; margin-top: 0.19in; margin-bottom: 0.19in; line-height: 150%"> 
<br /> 
&nbsp;</p>
<p align="center" style="margin-left: 0.83in; margin-top: 0.19in; margin-bottom: 0.19in; line-height: 150%"> 
<font face="Times New Roman, serif"><font style="font-size: 12pt"><font face="Arial, serif">ABOGADO DEFENSOR</font></font></font></p>
<p align="center" style="margin-left: 0.83in; margin-top: 0.19in; margin-bottom: 0.19in; line-height: 150%"> 
<br /> 
&nbsp;</p>
<p align="center" style="margin-left: 0.83in; margin-top: 0.19in; margin-bottom: 0.19in; line-height: 150%"> 
<font face="Times New Roman, serif"><font style="font-size: 12pt"><font face="Arial, serif">MADRE O PADRE</font></font></font></p>
<p align="center" style="margin-bottom: 0.14in; line-height: 150%"> 
<br /> 
&nbsp;</p>
<p style="margin-bottom: 0.14in; line-height: 150%"> 
<br /> 
&nbsp;</p>'
    ,
    'NOMBRAMIENTO DEL DEFENSOR Y DECLARACION DEL IMPUTADO SE RESERVA 12 A 14 AÑOS'
  )
  ,
  (
    'OFICIO AL DIRECTOR DEL CENTRO DE ATENCION PARA TERAPIA',
    1611,
    '<p align="right" style="margin-left: 0.83in; margin-top: 0.19in; margin-bottom: 0.19in; line-height: 150%"> 
<br /> 
&nbsp;</p>
<p align="right" style="margin-left: 0.83in; margin-top: 0.19in; margin-bottom: 0.19in; line-height: 150%"> 
<font face="Times New Roman, serif"><font style="font-size: 12pt"><font face="Arial, serif">N&uacute;m. Carpeta de investigaci&oacute;n:&nbsp;_____________________</font></font></font></p>
<p align="right" style="margin-left: 0.83in; margin-top: 0.19in; margin-bottom: 0.19in; line-height: 150%"> 
<font face="Times New Roman, serif"><font style="font-size: 12pt"><font face="Arial, serif">NUC:_____________________________<br /> 
&nbsp;<br /> 
&nbsp;&nbsp;&nbsp;&nbsp; ASUNTO:&nbsp;SE SOLICITA INFORME</font></font></font></p>
<p style="margin-left: 0.83in; margin-top: 0.19in; margin-bottom: 0.19in; line-height: 150%"> 
<br /> 
<br /> 
<font face="Times New Roman, serif"><font style="font-size: 12pt"><font face="Arial, serif"><b>DIRECTOR DEL CENTRO DE ATENCI&Oacute;N A VICTIMAS<br /> 
P R E S E N T E.-</b></font></font></font></p>
<p align="right" style="margin-left: 0.83in; margin-top: 0.19in; margin-bottom: 0.19in; line-height: 150%"> 
<br /> 
<font face="Times New Roman, serif"><font style="font-size: 12pt"><font face="Arial, serif">(Ciudad), (Estado), (Fecha)</font></font></font></p>
<p align="justify" style="margin-left: 0.83in; margin-top: 0.19in; margin-bottom: 0.19in; line-height: 150%"> 
<br /> 
<br /> 
<font face="Times New Roman, serif"><font style="font-size: 12pt"><font face="Arial, serif">Me permito solicitarle&nbsp; de la manera m&aacute;s atenta y de conformidad con lo establecido en el art&iacute;culo 20 Apartado C) de la Constituci&oacute;n Pol&iacute;tica de los Estados Unidos Mexicanos, en relaci&oacute;n con </font><font color="#000000"><font face="Arial, serif">los numerales </font></font><font face="Arial, serif">1, 2,, 6, 7, 9, 53, 61, 62, 132 y 133 de la Ley del Sistema Integral de Justicia para Adolescentes del Estado de Coahuila,</font><font color="#000000"><font face="Arial, serif"> as&iacute; como el art&iacute;culo 7, 8 y dem&aacute;s aplicables de la Ley General de Victimas.</font></font><font color="#0070c0"><font face="Arial, serif"><b> </b></font></font><font color="#000000"><font face="Arial, serif">Proporcione terapia psicol&oacute;gica a</font></font><font color="#0070c0"><font face="Arial, serif"><b>&nbsp; ___________________</b></font></font><font color="#0070c0"><font face="Arial, serif">,</font></font><b><font face="Arial, serif">&nbsp;</font></b><font face="Arial, serif"> por ser v&iacute;ctima de un hecho que reviste el car&aacute;cter considera una conducta tipificada como el delito, y tomando en consideraci&oacute;n el grado de profesionalizaci&oacute;n del personal a su mando, se sirva&nbsp; brindarle el apoyo correspondiente e informar sobre el avance del tratamiento.<br /> 
<br /> 
<br /> 
Sin otro en particular, reitero a Usted la seguridad de mi atenta y distinguida consideraci&oacute;n<br /> 
</font></font></font><br /> 
&nbsp;</p>
<p align="center" style="margin-left: 0.83in; margin-top: 0.19in; margin-bottom: 0.19in; line-height: 150%"> 
<br /> 
<font face="Times New Roman, serif"><font style="font-size: 12pt"><font face="Arial, serif">A T E N T A M E N T E<br /> 
AGENTE DEL MINISTERIO P&Uacute;BLICO ESPECIALIZADO</font></font></font></p>
<p align="center" style="margin-left: 0.83in; margin-top: 0.19in; margin-bottom: 0.19in; line-height: 150%"> 
<a name="_GoBack"></a> <font face="Times New Roman, serif"><font style="font-size: 12pt"><font face="Arial, serif">LIC. </font></font></font></p>
<p align="center" style="margin-left: 0.83in; margin-top: 0.19in; margin-bottom: 0.19in; line-height: 150%"> 
<br /> 
<br /> 
&nbsp;</p>
<p align="center" style="margin-bottom: 0.14in; line-height: 150%"> 
<br /> 
&nbsp;</p>
<p style="margin-bottom: 0.14in; line-height: 150%"> 
<br /> 
&nbsp;</p>'
    ,
    'OFICIO AL DIRECTOR DEL CENTRO DE ATENCION PARA TERAPIA'
  )
  ,
  (
    'OFICIO AL MEDICO DICTAMEN',
    1611,
    '<p style="margin-bottom: 0.14in; line-height: 150%"> 
<br /> 
&nbsp;</p>
<p align="right" style="margin-left: 0.83in; margin-top: 0.19in; margin-bottom: 0.19in; line-height: 150%"> 
<font face="Times New Roman, serif"><font style="font-size: 12pt"><font face="Arial, serif">N&uacute;m. Carpeta de investigaci&oacute;n:&nbsp;_____________________</font></font></font></p>
<p align="right" style="margin-left: 0.83in; margin-top: 0.19in; margin-bottom: 0.19in; line-height: 150%"> 
<font face="Times New Roman, serif"><font style="font-size: 12pt"><font face="Arial, serif">NUC: _____________________________<br /> 
</font></font></font><br /> 
&nbsp;</p>
<p align="right" style="margin-left: 0.83in; margin-top: 0.19in; margin-bottom: 0.19in; line-height: 150%"> 
<font face="Times New Roman, serif"><font style="font-size: 12pt"><font face="Arial, serif">ASUNTO: DESIGNACI&Oacute;N PERITO</font></font></font></p>
<p align="right" style="margin-left: 0.83in; margin-top: 0.19in; margin-bottom: 0.19in; line-height: 150%"> 
<a name="_GoBack"></a><br /> 
&nbsp;</p>
<p style="margin-left: 0.83in; margin-top: 0.19in; margin-bottom: 0.19in; line-height: 150%"> 
<font face="Times New Roman, serif"><font style="font-size: 12pt"><font face="Arial, serif"><b>DR.__________________________________</b></font></font></font></p>
<p style="margin-left: 0.83in; margin-top: 0.19in; margin-bottom: 0.19in; line-height: 150%"> 
<font face="Times New Roman, serif"><font style="font-size: 12pt"><font face="Arial, serif"><b>PERITO EN MATERIA DE MEDICINA FORENSE ADSCRITO</b></font></font></font></p>
<p style="margin-left: 0.83in; margin-top: 0.19in; margin-bottom: 0.19in; line-height: 150%"> 
<font face="Times New Roman, serif"><font style="font-size: 12pt"><font face="Arial, serif"><b>A LA PROCURADUR&Iacute;A GENERAL DEL ESTADO.</b></font></font></font></p>
<p style="margin-left: 0.83in; margin-top: 0.19in; margin-bottom: 0.19in; line-height: 150%"> 
<font face="Times New Roman, serif"><font style="font-size: 12pt"><font face="Arial, serif"><b>REGI&Oacute;N ______________________-</b></font></font></font></p>
<p style="margin-left: 0.83in; margin-top: 0.19in; margin-bottom: 0.19in; line-height: 150%"> 
<br /> 
&nbsp;</p>
<p align="justify" style="margin-left: 0.83in; margin-top: 0.19in; margin-bottom: 0.19in; line-height: 150%"> 
<font face="Times New Roman, serif"><font style="font-size: 12pt"><font face="Arial, serif">Por medio del presente le notific&oacute; a usted que ha sido designado por esta Representaci&oacute;n Social como Perito en materia de </font><b><font face="Arial, serif">MEDICINA FORENSE</font></b><font face="Arial, serif">, a efecto de clasificar el tipo de lesiones que presenta&nbsp; la C. _____________, solicit&aacute;ndole adem&aacute;s que informe si se localiza en dicha&nbsp; persona en forma externa, excoriaciones, equimosis o heridas contusas u otro tipo de lesiones, describiendo su situaci&oacute;n anat&oacute;mica, dimensiones, profundidad, las probables lesiones internas, adem&aacute;s y en caso de ser posible determinar en relaci&oacute;n a las heridas el tipo de objeto utilizado para causar estas. Dictamen que deber&aacute; emitir a la brevedad posible por ser este indispensable para la investigaci&oacute;n de la carpeta de investigaci&oacute;n se&ntilde;alada al rubro</font></font></font></p>
<p align="center" style="margin-left: 0.83in; margin-top: 0.19in; margin-bottom: 0.19in; line-height: 150%"> 
<br /> 
&nbsp;</p>
<p align="center" style="margin-left: 0.83in; margin-top: 0.19in; margin-bottom: 0.19in; line-height: 150%"> 
<font face="Times New Roman, serif"><font style="font-size: 12pt"><font face="Arial, serif"><b>ATENTAMENTE </b></font></font></font></p>
<p align="center" style="margin-left: 0.83in; margin-top: 0.19in; margin-bottom: 0.19in; line-height: 150%"> 
<br /> 
&nbsp;</p>
<p align="center" style="margin-left: 0.83in; margin-top: 0.19in; margin-bottom: 0.19in; line-height: 150%"> 
<font face="Times New Roman, serif"><font style="font-size: 12pt"><font face="Arial, serif"><b>AGENTE DEL MINISTERIO PUBLICO ESPECIALIZADO</b></font></font></font></p>
<p align="center" style="margin-left: 0.83in; margin-top: 0.19in; margin-bottom: 0.19in; line-height: 150%"> 
<br /> 
&nbsp;</p>
<p align="center" style="margin-left: 0.83in; margin-top: 0.19in; margin-bottom: 0.19in; line-height: 150%"> 
<font face="Times New Roman, serif"><font style="font-size: 12pt"><font face="Arial, serif"><b>LIC.___________________________________</b></font></font></font></p>
<p style="margin-left: 0.83in; margin-top: 0.19in; margin-bottom: 0.19in; line-height: 150%"> 
<br /> 
&nbsp;</p>
<p style="margin-left: 0.83in; margin-top: 0.19in; margin-bottom: 0.19in; line-height: 150%"> 
<br /> 
&nbsp;</p>
<p style="margin-bottom: 0.14in; line-height: 150%"> 
<br /> 
&nbsp;</p>'
    ,
    'OFICIO AL MEDICO DICTAMEN'
  )
  ,
  (
    'OFICIO CIERRE DE INV COMPLEMENTARIA',
    1611,
    '<p style="margin-bottom: 0in; line-height: 150%; text-align: center;"> 
<font face="Arial, serif"><font style="font-size: 12pt"><b>ASUNTO: CIERRE DE INVESTIGACI&Oacute;N COMPLEMENTARIA</b></font></font></p>
<p align="right" style="margin-left: 0.83in; margin-top: 0.19in; margin-bottom: 0.19in; line-height: 150%"> 
&nbsp;</p>
<p align="right" style="margin-left: 0.83in; margin-top: 0.19in; margin-bottom: 0.19in; line-height: 150%"> 
<font face="Times New Roman, serif"><font style="font-size: 12pt"><font face="Arial, serif">NUMERO DE CAUSA:</font><b><font face="Arial, serif"> _______________________</font></b><br /> 
<font face="Arial, serif"> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; N.U.C : ______________________<br /> 
&nbsp;&nbsp; &nbsp;<br /> 
&nbsp;&nbsp;&nbsp;&nbsp; ASUNTO: SE NOTIFICA CIERRE DE INVESTIGACI&Oacute;N COMPLEMENTARIA</font></font></font></p>
<p style="margin-left: 0.83in; margin-top: 0.19in; margin-bottom: 0.19in; line-height: 150%"> 
<br /> 
&nbsp;</p>
<p style="margin-left: 0.83in; margin-top: 0.19in; margin-bottom: 0.19in; line-height: 150%"> 
<font face="Times New Roman, serif"><font style="font-size: 12pt"><font face="Arial, serif"><b>H. JUEZ DE CONTROL<br /> 
DEL DISTRITO JUDICIAL DE ____________<br /> 
P R E S E N T E.-</b></font></font></font></p>
<p align="right" style="margin-left: 0.83in; margin-top: 0.19in; margin-bottom: 0.19in; line-height: 150%"> 
<font face="Times New Roman, serif"><font style="font-size: 12pt"><font face="Arial, serif">(Ciudad), (Estado), (Fecha)</font></font></font></p>
<p align="justify" style="margin-left: 0.83in; margin-top: 0.19in; margin-bottom: 0.19in; line-height: 150%"> 
<br /> 
&nbsp;</p>
<p align="justify" style="margin-left: 0.83in; margin-top: 0.19in; margin-bottom: 0.19in; line-height: 150%"> 
<font face="Times New Roman, serif"><font style="font-size: 12pt"><font face="Arial, serif">En mi car&aacute;cter de Agente del Ministerio P&uacute;blico Especializado para la Atenci&oacute;n integral de justicia para Adolescentes __________________, con domicilio para o&iacute;r y recibir notificaciones en __________________________________ dentro de la causa penal se&ntilde;alada al rubro, que se instruye en contra del imputado , </font><b><font face="Arial, serif">_______________________</font></b><font face="Arial, serif">por su probable responsabilidad de hechos que la Ley considera una conducta tipificada como el delito _____________, cometido en agravio de </font><b><font face="Arial, serif">_______________________ </font></b><font face="Arial, serif">(v&iacute;ctima&nbsp; y/o ofendido), con el debido respeto comparezco ante usted para exponer que:<br /> 
<br /> 
Hago de su conocimiento que el plazo de _____________________ (plazo para el cierre de investigaci&oacute;n), que se concedi&oacute; a la Representaci&oacute;n Social en fecha ___________________ (d&iacute;a, mes y a&ntilde;o), para cerrar investigaci&oacute;n complementaria en la carpeta de investigaci&oacute;n al rubro citada, concluy&oacute; el d&iacute;a ____________________(mes y a&ntilde;o). Motivo por el cual esta Representaci&oacute;n Social informa a usted con la finalidad de que se tenga por decretado el cierre del mismo, ello en cumplimiento de lo se&ntilde;alado en </font><font color="#000000"><font face="Arial, serif">el art&iacute;culo </font></font><font face="Arial, serif">150 de la Ley del Sistema Integral de Justicia para Adolescentes del Estado de Coahuila.</font></font></font></p>
<p align="justify" style="margin-left: 0.83in; margin-top: 0.19in; margin-bottom: 0.19in; line-height: 150%"> 
<font face="Times New Roman, serif"><font style="font-size: 12pt"><font face="Arial, serif">Por lo que en base a lo expuesto y fundado solicito a Usted H. Juez de Control, solicito:</font></font></font></p>
<p align="justify" style="margin-left: 0.83in; margin-top: 0.19in; margin-bottom: 0.19in; line-height: 150%"> 
<font face="Times New Roman, serif"><font style="font-size: 12pt"><font face="Arial, serif"><b>UNICO.</b></font><font face="Arial, serif">-&nbsp; Se me tenga&nbsp; compareciendo en tiempo&nbsp; y forma dentro de la causa penal aludida, a efecto de informar a usted, para que a su vez tenga a bien decretar el cierre de la investigaci&oacute;n formalizada y as&iacute; estar en posibilidades de dar cumplimiento a una de las hip&oacute;tesis planteadas en el numeral 151 de la Ley del Sistema Integral de Justicia para Adolescentes.</font></font></font></p>
<p align="justify" style="margin-left: 0.83in; margin-top: 0.19in; margin-bottom: 0.19in; line-height: 150%"> 
<font face="Times New Roman, serif"><font style="font-size: 12pt"><font face="Arial, serif">Sin m&aacute;s por el momento.</font></font></font></p>
<p align="center" style="margin-left: 0.83in; margin-top: 0.19in; margin-bottom: 0.19in; line-height: 150%"> 
<br /> 
<font face="Times New Roman, serif"><font style="font-size: 12pt"><font face="Arial, serif">A T E N T A M E N T E<br /> 
EL AGENTE DEL MINISTERIO P&Uacute;BLICO ESPECIALIZADO</font></font></font></p>
<p align="center" style="margin-left: 0.83in; margin-top: 0.19in; margin-bottom: 0.19in; line-height: 150%"> 
<font face="Times New Roman, serif"><font style="font-size: 12pt"><font face="Arial, serif">LIC.&nbsp;</font></font></font></p>'
    ,
    'OFICIO CIERRE DE INV COMPLEMENTARIA'
  )
  ,
  (
    'OFICIO CUMPLIMENTAR ORDEN DE DETENCION',
    1611,
    '<p align="right" style="margin-bottom: 0.14in; line-height: 150%"> 
<font color="#0070c0"><font face="Arial, serif"><b>ASUNTO: CUMPLIMENTAR ORDEN DE DETENCION </b></font></font></p>
<p align="right" style="margin-left: 0.83in; margin-top: 0.19in; margin-bottom: 0.19in; line-height: 150%"> 
<font face="Times New Roman, serif"><font style="font-size: 12pt"><font face="Arial, serif"><font style="font-size: 11pt">EXPEDIENTE NUMERO: ______________________<br /> 
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; N.U.C : ______________________<br /> 
&nbsp;&nbsp;&nbsp;<br /> 
</font></font></font></font><br /> 
&nbsp;</p>
<p style="margin-left: 0.83in; margin-top: 0.19in; margin-bottom: 0.19in; line-height: 150%"> 
<font face="Times New Roman, serif"><font style="font-size: 12pt"><font face="Arial, serif"><font style="font-size: 11pt">POLICIA ESPECIALIZADO</font></font></font></font></p>
<p style="margin-left: 0.83in; margin-top: 0.19in; margin-bottom: 0.19in; line-height: 150%"> 
<font face="Times New Roman, serif"><font style="font-size: 12pt"><font face="Arial, serif"><font style="font-size: 11pt">P R E S E N T E.-</font></font></font></font></p>
<p align="right" style="margin-left: 0.83in; margin-top: 0.19in; margin-bottom: 0.19in; line-height: 150%"> 
<font face="Times New Roman, serif"><font style="font-size: 12pt"><font face="Arial, serif"><font style="font-size: 11pt">(Ciudad), (Estado), (Fecha)</font></font></font></font></p>
<p align="justify" style="margin-left: 0.83in; margin-top: 0.19in; margin-bottom: 0.17in; line-height: 150%"> 
<a name="_GoBack"></a><br /> 
<br /> 
<font face="Times New Roman, serif"><font style="font-size: 12pt"><font face="Arial, serif"><font style="font-size: 11pt"> Toda vez que en fecha ___________________________________, se libr&oacute; orden de Detenci&oacute;n&nbsp; en contra de ______________________, como probable responsable en la comisi&oacute;n del hecho que la ley se&ntilde;ala como el delito de ___________________, en agravio de ________________________ , deriv&aacute;ndose de la causa n&uacute;mero __________________, orden que fue recibida por esta Representaci&oacute;n Social en fecha ________________________, misma que remito a usted para la ejecuci&oacute;n y cumplimiento, que deber&aacute; realizarse con estricto respeto a las garant&iacute;as fundamentales del adolescente.</font></font></font></font></p>
<p align="justify" style="margin-left: 0.83in; margin-top: 0.19in; margin-bottom: 0.17in; line-height: 150%"> 
<br /> 
<font face="Times New Roman, serif"><font style="font-size: 12pt"><font color="#000000"><font face="Arial, serif"><font style="font-size: 11pt">Lo anterior, con fundamento en los art&iacute;culos 16 y 21 de la Constituci&oacute;n Pol&iacute;tica de los Estados Unidos Mexicanos, con relaci&oacute;n con los numerales los art&iacute;culos 61, 62, 63 , 70, </font></font></font><font face="Arial, serif"><font style="font-size: 11pt">132 y 133 de la Ley del Sistema Integral de Justicia para Adolescentes del Estado de Coahuila</font></font><font color="#000000"><font face="Arial, serif"><font style="font-size: 11pt">.</font></font></font></font></font></p>
<p align="center" style="margin-left: 0.83in; margin-top: 0.19in; margin-bottom: 0.19in; line-height: 150%"> 
<br /> 
&nbsp;</p>
<p align="center" style="margin-left: 0.83in; margin-top: 0.19in; margin-bottom: 0.19in; line-height: 150%"> 
<font face="Times New Roman, serif"><font style="font-size: 12pt"><font face="Arial, serif"><font style="font-size: 11pt">A T E N T A M E N T E<br /> 
AGENTE DEL MINISTERIO P&Uacute;BLICO ESPECIALIZADO</font></font></font></font></p>
<p align="center" style="margin-left: 0.83in; margin-top: 0.19in; margin-bottom: 0.19in; line-height: 150%"> 
<br /> 
&nbsp;</p>
<p align="center" style="margin-left: 0.83in; margin-top: 0.19in; margin-bottom: 0.19in; line-height: 150%"> 
<font face="Times New Roman, serif"><font style="font-size: 12pt"><font face="Arial, serif"><font style="font-size: 11pt">LIC. </font></font></font></font></p>
<p align="center" style="margin-left: 0.83in; margin-top: 0.19in; margin-bottom: 0.19in; line-height: 150%"> 
<br /> 
<br /> 
&nbsp;</p>
<p align="center" style="margin-left: 0.83in; margin-top: 0.19in; margin-bottom: 0.19in; line-height: 150%"> 
<br /> 
&nbsp;</p>
<p style="margin-left: 0.83in; margin-top: 0.19in; margin-bottom: 0.19in; line-height: 150%"> 
<br /> 
&nbsp;</p>'
    ,
    'OFICIO CUMPLIMENTAR ORDEN DE DETENCION'
  )
  ,
  (
    'OFICIO DE INVESTIGACI0N POLICIAL (ADOLESCENTES)',
    1611,
    '<p align="center" style="margin-bottom: 0.14in; line-height: 150%"> 
<font color="#0070c0"><font face="Arial, serif"><font style="font-size: 12pt"><b>OFICIO DE INVESTIGACI&Oacute;N POLICIAL </b></font></font></font></p>
<p align="right" style="margin-left: 0.83in; margin-top: 0.19in; margin-bottom: 0.19in; line-height: 150%"> 
<font face="Times New Roman, serif"><font style="font-size: 12pt"><font face="Arial, serif">EXPEDIENTE NUMERO: ____________________<br /> 
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<br /> 
&nbsp;&nbsp;&nbsp;&nbsp; ASUNTO: SE SOLICITA INVESTIGACI&Oacute;N</font></font></font></p>
<p style="margin-left: 0.83in; margin-top: 0.19in; margin-bottom: 0.19in; line-height: 150%"> 
<br /> 
<br /> 
<font face="Times New Roman, serif"><font style="font-size: 12pt"><font face="Arial, serif"><b>POLICIA ESPECIALIZADO EN INVESTIGACIONES DE JUSTICIA PARA ADOLESCENTES </b></font></font></font></p>
<p style="margin-left: 0.83in; margin-top: 0.19in; margin-bottom: 0.19in; line-height: 150%"> 
<font face="Times New Roman, serif"><font style="font-size: 12pt"><font face="Arial, serif"><b>P R E S E N T E.-</b></font></font></font></p>
<p align="right" style="margin-left: 0.83in; margin-top: 0.19in; margin-bottom: 0.19in; line-height: 150%"> 
<br /> 
<font face="Times New Roman, serif"><font style="font-size: 12pt"><font face="Arial, serif">(Ciudad), (Estado), (Fecha)</font></font></font></p>
<p align="justify" style="margin-left: 0.83in; margin-top: 0.19in; margin-bottom: 0.17in; line-height: 150%"> 
<a name="_GoBack"></a><br /> 
<br /> 
<font face="Times New Roman, serif"><font style="font-size: 12pt"><font face="Arial, serif"> En vista de que se ha iniciado carpeta de investigaci&oacute;n en contra del adolescente ________________________________, en la comisi&oacute;n del hecho que la Ley se&ntilde;ala una conducta tipificada como el delito de ______________, cometido en agravio de (v&iacute;ctima y/o ofendido), le solicito a usted, realice los siguientes actos de investigaci&oacute;n:<br /> 
___________________________________________________________________________________________________________________________________________________________________________Lo anterior con fundamento en el art&iacute;culo 21 de la Constituci&oacute;n Pol&iacute;tica de los Estados Unidos Mexicanos y los art&iacute;culos 61, 62, 63 70, 132 y 133 de la Ley del Sistema Integral de Justicia para Adolescentes del Estado de Coahuila.</font></font></font></p>
<p align="center" style="margin-left: 0.83in; margin-top: 0.19in; margin-bottom: 0.19in; line-height: 150%"> 
<br /> 
<br /> 
<br /> 
<font face="Times New Roman, serif"><font style="font-size: 12pt"><font face="Arial, serif">A T E N T A M E N T E<br /> 
AGENTE DEL MINISTERIO P&Uacute;BLICO ESPECIALIZADO</font></font></font></p>
<p align="center" style="margin-left: 0.83in; margin-top: 0.19in; margin-bottom: 0.19in; line-height: 150%"> 
<font face="Times New Roman, serif"><font style="font-size: 12pt"><font face="Arial, serif">LIC. </font></font></font></p>
<p align="center" style="margin-left: 0.83in; margin-top: 0.19in; margin-bottom: 0.19in; line-height: 150%"> 
<br /> 
&nbsp;</p>
<p style="margin-bottom: 0.14in; line-height: 150%"> 
<br /> 
&nbsp;</p>'
    ,
    'OFICIO DE INVESTIGACI0N POLICIAL (ADOLESCENTES)'
  )
  ,
  (
    'OFICIO ENTREGA DE COPIA A DEFENSOR',
    1611,
    '<p style="margin-left: 0.98in; margin-top: 0.19in; margin-bottom: 0.19in; line-height: 150%; text-align: center;"> 
&nbsp; &nbsp; &nbsp;&nbsp;<font face="Arial, serif"><font style="font-size: 12pt"><b>ASUNTO: SE ENTREGA COPIA AUTENTICA DE<br /> 
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; LA CARPETA DE INVESTIGACI&Oacute;N</b></font></font></p>
<p align="right" style="margin-left: 0.98in; margin-top: 0.19in; margin-bottom: 0.19in; line-height: 150%"> 
<font face="Arial, serif"><font style="font-size: 12pt">N&uacute;m. Carpeta de investigaci&oacute;n.:________________<br /> 
N&deg;. Expediente: </font></font><font face="Arial, serif"><font style="font-size: 12pt">__________________</font></font></p>
<p style="margin-top: 0.19in; margin-bottom: 0.19in; line-height: 150%"> 
<br /> 
&nbsp;</p>
<p style="margin-left: 0.98in; margin-top: 0.19in; margin-bottom: 0.19in; line-height: 150%"> 
<font face="Arial, serif"><font style="font-size: 12pt">LIC. _____________________________________________.<br /> 
DEFENSOR&nbsp; DE ___________________________________.<br /> 
P R E S E N T E.-</font></font></p>
<p align="right" style="margin-left: 0.98in; margin-top: 0.19in; margin-bottom: 0.19in; line-height: 150%"> 
<br /> 
<font face="Arial, serif"><font style="font-size: 12pt">Lugar y fecha: </font></font><font face="Arial, serif"><font style="font-size: 12pt">_____________________________</font></font></p>
<p align="justify" style="margin-left: 0.98in; margin-top: 0.19in; margin-bottom: 0.17in; line-height: 150%"> 
<br /> 
<font color="#000000"><font face="Arial, serif"><font style="font-size: 12pt">En cumplimiento a lo previsto en los numerales </font></font></font><font face="Arial, serif"><font style="font-size: 12pt">los art&iacute;culos 61, 62, 132 y 133 de la Ley del Sistema Integral de Justicia para Adolescentes del Estado de Coahuila, en relaci&oacute;n al art&iacute;culo</font></font><font color="#000000"><font face="Arial, serif"><font style="font-size: 12pt"> 219 del C&oacute;digo Nacional de Procedimientos Penales me permito hacer entrega en _____________________________ fojas &uacute;tiles, consistentes&nbsp; en la copia autentica de la carpeta de investigaci&oacute;n indicada al rubro, misma que contiene las actas y actuaciones practicadas dentro de la misma.&nbsp; &nbsp;<br /> 
<br /> 
Lo anterior para su oportuna lectura y preparaci&oacute;n a las audiencias correspondientes dentro de los t&eacute;rminos constitucionales.</font></font></font></p>
<p style="margin-left: 0.98in; margin-top: 0.19in; margin-bottom: 0.17in; line-height: 150%"> 
<br /> 
<br /> 
<font color="#000000"><font face="Arial, serif"><font style="font-size: 12pt">Sin m&aacute;s por el momento.<br /> 
</font></font></font><br /> 
&nbsp;</p>
<p align="center" style="margin-left: 0.98in; margin-top: 0.19in; margin-bottom: 0.19in; line-height: 150%"> 
<br /> 
<font color="#000000"><font face="Arial, serif"><font style="font-size: 12pt">A T E N T A M E N T E</font></font></font></p>
<p align="center" style="margin-left: 0.98in; margin-top: 0.19in; margin-bottom: 0.19in; line-height: 150%"> 
<a name="_GoBack"></a><br /> 
<font color="#000000"><font face="Arial, serif"><font style="font-size: 12pt"> AGENTE DEL MINISTERIO P&Uacute;BLICO ESPECIALIZADO</font></font></font></p>
<p align="center" style="margin-left: 0.98in; margin-top: 0.19in; margin-bottom: 0.19in; line-height: 150%"> 
<font color="#000000"><font face="Arial, serif"><font style="font-size: 12pt">LIC.</font></font></font><font face="Arial, serif"><font style="font-size: 12pt"> </font></font></p>
<p align="right" style="margin-bottom: 0in; line-height: 150%"> 
<font face="Arial, serif"><font style="font-size: 12pt">Recib&iacute; de Conformidad</font></font></p>
<p align="right" style="margin-bottom: 0in; line-height: 150%"> 
<font face="Arial, serif"><font style="font-size: 12pt">Fecha: ___________________</font></font></p>
<p align="right" style="margin-bottom: 0in; line-height: 150%"> 
<font face="Arial, serif"><font style="font-size: 12pt">Hora: ____________________</font></font></p>
<p style="margin-bottom: 0in; line-height: 150%"> 
&nbsp;</p>
<p align="right" style="margin-bottom: 0in; line-height: 150%"> 
<font face="Arial, serif"><font style="font-size: 12pt">Firma: __________________________</font></font></p>'
    ,
    'OFICIO ENTREGA DE COPIA A DEFENSOR'
  )
  ,
  (
    'OFICIO PARA SOLICITAR ATENCION A VICTIMA',
    1611,
    '<p align="center" style="margin-bottom: 0.14in; line-height: 150%"> 
<br /> 
&nbsp;</p>
<p align="right" style="margin-left: 0.83in; margin-top: 0.19in; margin-bottom: 0.19in; line-height: 150%"> 
<font face="Times New Roman, serif"><font style="font-size: 12pt"><font face="Arial, serif">NUMERO EXPEDIENTE: </font><b><font face="Arial, serif">______________________</font></b></font></font></p>
<p align="right" style="margin-left: 0.83in; margin-top: 0.19in; margin-bottom: 0.19in; line-height: 150%"> 
<font face="Times New Roman, serif"><font style="font-size: 12pt"><font face="Arial, serif">N.U.C : ______________________</font></font></font></p>
<p style="margin-left: 0.83in; margin-top: 0.19in; margin-bottom: 0.19in; line-height: 150%"> 
<br /> 
<font face="Times New Roman, serif"><font style="font-size: 12pt"><font face="Arial, serif">&nbsp;&nbsp; &nbsp;<br /> 
</font><font face="Arial, serif"><b>LIC. </b></font></font></font></p>
<p style="margin-left: 0.83in; margin-top: 0.19in; margin-bottom: 0.19in; line-height: 150%"> 
<a name="_GoBack"></a> <font face="Times New Roman, serif"><font style="font-size: 12pt"><font face="Arial, serif"><b>COORDINADOR DEL CENTRO DE ATENCI&Oacute;N A VICTIMAS</b></font></font></font></p>
<p style="margin-left: 0.83in; margin-top: 0.19in; margin-bottom: 0.19in; line-height: 150%"> 
<br /> 
<font face="Times New Roman, serif"><font style="font-size: 12pt"><font face="Arial, serif"><b>P R E S E N T E.-</b></font></font></font></p>
<p align="right" style="margin-left: 0.83in; margin-top: 0.19in; margin-bottom: 0.19in; line-height: 150%"> 
<br /> 
<font face="Times New Roman, serif"><font style="font-size: 12pt"><font face="Arial, serif">(Ciudad), (Estado), (Fecha)</font></font></font></p>
<p align="justify" style="margin-left: 0.83in; margin-top: 0.19in; margin-bottom: 0.17in; line-height: 150%"> 
<br /> 
<br /> 
<font face="Times New Roman, serif"><font style="font-size: 12pt"><font face="Arial, serif">Me permito solicitarle&nbsp; de la manera m&aacute;s atenta y de conformidad con lo establecido en </font><font color="#000000"><font face="Arial, serif">el art&iacute;culo 20 Constituci&oacute;n Pol&iacute;tica de los Estados Unidos Mexicanos, en relaci&oacute;n con los numerales 53, 621, 62, 63, </font></font><font face="Arial, serif">132 y 133 de la Ley del Sistema Integral de Justicia para Adolescentes del Estado de Coahuila, </font><font color="#000000"><font face="Arial, serif">as&iacute; como los art&iacute;culos 1, 2, 3, 4, 7, 8, y dem&aacute;s aplicables de la Ley General de Victimas</font></font><font face="Arial, serif">. Proporcione la atenci&oacute;n debida a&nbsp; ___________________________</font><b><font face="Arial, serif">,&nbsp;</font></b><font face="Arial, serif"> por ser v&iacute;ctima de un hecho que reviste el car&aacute;cter de una conducta tipificada como el delito: </font><b><font face="Arial, serif">___________________</font></b><font face="Arial, serif"> , y tomando en consideraci&oacute;n el grado de profesionalizaci&oacute;n del personal a su mando, se sirva&nbsp; brindarle el apoyo correspondiente e informar sobre el avance del tratamiento.</font></font></font></p>
<p align="justify" style="margin-left: 0.83in; margin-top: 0.19in; margin-bottom: 0.17in; line-height: 150%"> 
<br /> 
<br /> 
<br /> 
<font face="Times New Roman, serif"><font style="font-size: 12pt"><font face="Arial, serif">Sin otro en particular, reitero a Usted la seguridad de mi atenta y distinguida consideraci&oacute;n<br /> 
</font></font></font><br /> 
&nbsp;</p>
<p align="center" style="margin-left: 0.83in; margin-top: 0.19in; margin-bottom: 0.19in; line-height: 150%"> 
<br /> 
<font face="Times New Roman, serif"><font style="font-size: 12pt"><font face="Arial, serif"><b>A T E N T A M E N T E<br /> 
AGENTE DEL MINISTERIO P&Uacute;BLICO ESPECIALIZADO</b></font></font></font></p>
<p align="center" style="margin-left: 0.83in; margin-top: 0.19in; margin-bottom: 0.19in; line-height: 150%"> 
<br /> 
&nbsp;</p>
<p align="center" style="margin-left: 0.83in; margin-top: 0.19in; margin-bottom: 0.17in; line-height: 150%"> 
<font face="Times New Roman, serif"><font style="font-size: 12pt"><font face="Arial, serif"><b>LIC. </b></font></font></font></p>
<p style="margin-bottom: 0.14in; line-height: 150%"> 
<br /> 
&nbsp;</p>'
    ,
    'OFICIO PARA SOLICITAR ATENCION A VICTIMA'
  )
  ,
  (
    'PARA CARTA DE NO ROBO',
    1611,
    '<p style="margin-bottom: 0.14in; line-height: 150%; text-align: center;"> 
<font color="#0070c0"><font face="Arial, serif"><b>ASUNTO: SE SOLICITA INFORMACION </b></font></font></p>
<p align="right" style="margin-left: 0.83in; margin-top: 0.19in; margin-bottom: 0.19in; line-height: 150%"> 
<font face="Times New Roman, serif"><font style="font-size: 12pt"><font face="Arial, serif"><font style="font-size: 11pt">N&uacute;m. Carpeta de investigaci&oacute;n:&nbsp;_____________________</font></font></font></font></p>
<p align="right" style="margin-left: 0.83in; margin-top: 0.19in; margin-bottom: 0.19in; line-height: 150%"> 
<font face="Times New Roman, serif"><font style="font-size: 12pt"><font face="Arial, serif"><font style="font-size: 11pt">NUC:_____________________________<br /> 
</font></font></font></font><br /> 
&nbsp;</p>
<p align="right" style="margin-left: 0.83in; margin-top: 0.19in; margin-bottom: 0.19in; line-height: 150%"> 
<br /> 
<font face="Times New Roman, serif"><font style="font-size: 12pt"><font face="Arial, serif"><font style="font-size: 11pt">&nbsp;&nbsp;&nbsp;&nbsp; ASUNTO:&nbsp;SE SOLICITA INFORME</font></font></font></font></p>
<p style="margin-left: 0.83in; margin-top: 0.19in; margin-bottom: 0.19in; line-height: 150%"> 
<br /> 
<font face="Times New Roman, serif"><font style="font-size: 12pt"><font color="#000000"><font face="Arial, serif"><font style="font-size: 11pt"><b>ENCARGADO DEL SISTEMA DE CAPTURA NACIONAL<br /> 
P R E S E N T E.-</b></font></font></font></font></font></p>
<p align="right" style="margin-left: 0.83in; margin-top: 0.19in; margin-bottom: 0.19in; line-height: 150%"> 
<br /> 
<font face="Times New Roman, serif"><font style="font-size: 12pt"><font face="Arial, serif"><font style="font-size: 11pt">(Ciudad), (Estado), (Fecha)</font></font></font></font></p>
<p align="justify" style="margin-left: 0.83in; margin-top: 0.19in; margin-bottom: 0.17in; line-height: 150%"> 
<br /> 
<br /> 
<font face="Times New Roman, serif"><font style="font-size: 12pt"><font face="Arial, serif"><font style="font-size: 11pt">Me permito solicitarle&nbsp; de la manera m&aacute;s atenta y de conformidad con lo establecido en </font></font><font color="#000000"><font face="Arial, serif"><font style="font-size: 11pt">el art&iacute;culo 21 Constituci&oacute;n Pol&iacute;tica de los Estados Unidos Mexicanos, numerales 61, 62, 63, 132 y 133 de la Ley del sistema Integral de Justicia para Adolescentes as&iacute; como 1, 2, 3, 5, 23, 25 y dem&aacute;s aplicables de la Ley &nbsp;General del Sistema Nacional de Seguridad P&uacute;blica, en relaci&oacute;n con el Convenio de Colaboraci&oacute;n Especifico para el Intercambio de Informaci&oacute;n sobre Veh&iacute;culos Robados y el Procedimiento para su Recuperaci&oacute;n y Devoluci&oacute;n</font></font></font><font color="#0070c0"><font face="Arial, serif"><font style="font-size: 11pt"><b>,</b></font></font></font><font face="Arial, serif"><font style="font-size: 11pt"> instruya a personal a su digno Cargo a efecto de que me sea proporcionada la informaci&oacute;n consistente en si cuenta con reporte de ROBO&nbsp; el veh&iacute;culo con las siguientes caracter&iacute;sticas<br /> 
Marca&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; _________________________<br /> 
Tipo&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; _________________________<br /> 
Modelo&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; _________________________<br /> 
Serie&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; _________________________<br /> 
Motor&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; _________________________<br /> 
Placas&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; _________________________<br /> 
<br /> 
Lo anterior, en virtud de que la informaci&oacute;n es necesaria para la debida integraci&oacute;n de la carpeta de investigaci&oacute;n mencionada al rubro.<br /> 
<br /> 
Sin otro en particular, reitero a Usted la seguridad de mi atenta y distinguida consideraci&oacute;n</font></font></font></font></p>
<p align="center" style="margin-left: 0.83in; margin-top: 0.19in; margin-bottom: 0.19in; line-height: 150%"> 
<a name="_GoBack"></a><br /> 
<br /> 
<font face="Times New Roman, serif"><font style="font-size: 12pt"><font face="Arial, serif"><font style="font-size: 11pt"> A T E N T A M E N T E<br /> 
AGENTE DEL MINISTERIO P&Uacute;BLICO</font></font></font></font></p>
<p align="center" style="margin-left: 0.83in; margin-top: 0.19in; margin-bottom: 0.19in; line-height: 150%"> 
<font face="Times New Roman, serif"><font style="font-size: 12pt"><font face="Arial, serif"><font style="font-size: 11pt">(NOMBRE)<br /> 
(AGENCIA USUARIO)</font></font></font></font></p>
<p align="center" style="margin-left: 0.83in; margin-top: 0.19in; margin-bottom: 0.19in; line-height: 150%"> 
<br /> 
&nbsp;</p>
<p style="margin-bottom: 0.14in; line-height: 150%"> 
<br /> 
&nbsp;</p>'
    ,
    'PARA CARTA DE NO ROBO'
  )
  ,
  (
    'PERITAJE URGENTE (ADOLESCENTES)',
    1611,
    '<p style="margin-bottom: 0.14in; line-height: 150%; text-align: center;"> 
<a name="_GoBack"></a> <font face="Arial, serif"><font style="font-size: 12pt">ASUNTO: SOLICITUD DE PERITAJE URGENTE</font></font></p>
<p align="right" style="margin-left: 0.83in; margin-top: 0.19in; margin-bottom: 0.19in; line-height: 150%"> 
<br /> 
&nbsp;</p>
<p align="right" style="margin-left: 0.83in; margin-top: 0.19in; margin-bottom: 0.19in; line-height: 150%"> 
<font face="Times New Roman, serif"><font style="font-size: 12pt"><font face="Arial, serif">EXPEDIENTE NUMERO: _______________<br /> 
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; N.U.C : _______________<br /> 
<br /> 
</font></font></font><br /> 
&nbsp;</p>
<p align="right" style="margin-left: 0.83in; margin-top: 0.19in; margin-bottom: 0.19in; line-height: 150%"> 
<font face="Times New Roman, serif"><font style="font-size: 12pt"><font face="Arial, serif">ASUNTO: SE SOLICITA PERITAJE URGENTE &nbsp;<br /> 
POR EXISTIR PERSONA DETENIDA</font></font></font></p>
<p style="margin-left: 0.83in; margin-top: 0.19in; margin-bottom: 0.19in; line-height: 150%"> 
<br /> 
<br /> 
<font face="Times New Roman, serif"><font style="font-size: 12pt"><font face="Arial, serif"><b>PERITO _______________________<br /> 
EN MATERIA DE ________________<br /> 
P R E S E N T E.-</b></font></font></font></p>
<p align="right" style="margin-left: 0.83in; margin-top: 0.19in; margin-bottom: 0.19in; line-height: 150%"> 
<font face="Times New Roman, serif"><font style="font-size: 12pt"><font face="Arial, serif">(Ciudad), (Estado), (Fecha)</font></font></font></p>
<p style="margin-left: 0.83in; margin-top: 0.19in; margin-bottom: 0.19in; line-height: 150%"> 
<br /> 
&nbsp;</p>
<p align="justify" style="margin-left: 0.83in; margin-top: 0.19in; margin-bottom: 0.19in; line-height: 150%"> 
<font face="Times New Roman, serif"><font style="font-size: 12pt"><font face="Arial, serif">En vista de que se ha iniciado carpeta de investigaci&oacute;n en contra del adolescente ___________________________, por su probable participaci&oacute;n en la comisi&oacute;n de hechos que la Ley se&ntilde;ala como el delito de _____________, cometido en agravio de _____________________________, y con fundamento en lo </font><font color="#000000"><font face="Arial, serif">establecido el numeral 16 de la Constituci&oacute;n Pol&iacute;tica de los Estados Unidos Mexicanos en relaci&oacute;n a los art&iacute;culos 61, 62, 63, </font></font><font face="Arial, serif">132 y 133 de la Ley del Sistema Integral de Justicia para Adolescentes del Estado de Coahuila</font><font color="#000000"><font face="Arial, serif">,&nbsp; se le designa como perito con la finalidad&nbsp; de que realice peritaje en la materia de ____________________________________, toda vez que (justificar la necesidad del dictamen).<br /> 
<br /> 
Dictamen que deber&aacute; emitir en un t&eacute;rmino de ____________ horas por existir persona detenida.</font></font></font></font></p>
<p style="margin-left: 0.83in; margin-top: 0.19in; margin-bottom: 0.17in; line-height: 150%"> 
<br /> 
<br /> 
<font face="Times New Roman, serif"><font style="font-size: 12pt"><font face="Arial, serif">&nbsp; &nbsp;</font></font></font></p>
<p align="center" style="margin-left: 0.83in; margin-top: 0.19in; margin-bottom: 0.19in; line-height: 150%"> 
<font face="Times New Roman, serif"><font style="font-size: 12pt"><font face="Arial, serif">A T E N T A M E N T E<br /> 
AGENTE DEL MINISTERIO P&Uacute;BLICO ESPECIALIZADO</font></font></font></p>
<p align="center" style="margin-left: 0.83in; margin-top: 0.19in; margin-bottom: 0.19in; line-height: 150%"> 
&nbsp;<font face="Times New Roman, serif"><font style="font-size: 12pt"><b><font face="Arial, serif">LIC.___________________________</font></b></font></font></p>
<p style="margin-left: 0.83in; margin-top: 0.19in; margin-bottom: 0.19in; line-height: 150%"> 
<br /> 
&nbsp;</p>
<p align="center" style="margin-bottom: 0.14in; line-height: 150%"> 
<br /> 
&nbsp;</p>
<p align="center" style="margin-bottom: 0.14in; line-height: 150%"> 
<br /> 
&nbsp;</p>'
    ,
    'PERITAJE URGENTE (ADOLESCENTES)'
  )
  ,
  (
    'RATIFICACION DE DENUNCIA',
    1611,
    '<p align="right" style="margin-left: 0.83in; margin-top: 0.19in; margin-bottom: 0.19in; line-height: 150%"> 
<br /> 
&nbsp;</p>
<p style="margin-left: 0.83in; margin-top: 0.19in; margin-bottom: 0.19in; line-height: 150%; text-align: center;"> 
<font face="Times New Roman, serif"><font style="font-size: 12pt"><b><font face="Arial, serif">RATIFICACI&Oacute;N DE DENUNCIA Y/O QUERELLA DE PERSONA F&Iacute;SICA</font></b></font></font></p>
<p align="right" style="margin-left: 0.83in; margin-top: 0.19in; margin-bottom: 0.19in; line-height: 150%"> 
<font face="Times New Roman, serif"><font style="font-size: 12pt"><b><font face="Arial, serif">C.&nbsp; _______________________________________</font></b><br /> 
<font face="Arial, serif"><b> </b></font><b><font face="Arial, serif">FECHA: d&iacute;a /mes / a&ntilde;o</font></b><br /> 
<font face="Arial, serif"><b> </b></font><b><font face="Arial, serif">&nbsp;HORA:</font></b></font></font></p>
<p align="right" style="margin-left: 0.83in; margin-top: 0.19in; margin-bottom: 0.17in; line-height: 150%"> 
&nbsp;<font face="Times New Roman, serif"><font style="font-size: 12pt"><b><font face="Arial, serif">Expediente:&nbsp; </font></b><font face="Arial, serif">___________________</font></font></font></p>
<p align="right" style="margin-left: 0.83in; margin-top: 0.19in; margin-bottom: 0.17in; line-height: 150%"> 
<font face="Times New Roman, serif"><font style="font-size: 12pt"><b><font face="Arial, serif">NUC:</font></b><font face="Arial, serif">____________________</font></font></font></p>
<p align="justify" style="margin-left: 0.83in; margin-top: 0.19in; margin-bottom: 0.19in; line-height: 150%"> 
<a name="_GoBack"></a> <font face="Times New Roman, serif"><font style="font-size: 12pt"><font face="Arial, serif">En LA CIUDAD DE _____________-, DEL ESTADO DE COAHUILA DE ZARAGOZA, a las&nbsp; ____________ HORAS CON ____ MINUTOS DEL D&Iacute;A DE HOY ____ D&Iacute;A DEL MES DE ____ DEL A&Ntilde;O ____, legalmente constituido en audiencia el LIC. &nbsp;(Usuario), Agente del Ministerio P&uacute;blico de la ________________________________-, de la Procuradur&iacute;a General de Justicia del Estado, con fundamento en lo establecido por </font><font color="#000000"><font face="Arial, serif">el art&iacute;culo 21 de la Constituci&oacute;n Pol&iacute;tica de los Estados Unidos Mexicanos, en relaci&oacute;n con los art&iacute;culos 61, 62, 63, 132 y 133 de la Ley del Sistema Integral de Justicia de Adolescentes</font></font><font face="Arial, serif">, comparece ante esta Representaci&oacute;n Social el </font><b><font face="Arial, serif">C. denunciante &nbsp;persona que </font></b><font face="Arial, serif">comparece voluntariamente y se identifica con </font><b><font face="Arial, serif">_______</font></b><font face="Arial, serif">. Acto continuo se procede a explicar a los comparecientes la trascendencia de la diligencia en que interviene y se le hace saber las penas en que incurren las personas que se conducen con falsedad al declarar ante una autoridad en ejercicio de sus funciones como es el delito de Perjurio o Falsedad en Declaraciones y los delitos de Falsas Denuncias o Querellas, que disponen los art&iacute;culos 237 y 240 del C&oacute;digo Penal vigente en el Estado, y previa protesta para que se conduzca con la verdad en lo que va a declarar, manifiesta el compareciente: que protesto conducirme con verdad, y en cuanto a mis generales: me llamo</font><b><font face="Arial, serif">&nbsp;Nombre del denunciante</font></b><font face="Arial, serif">, de nacionalidad _____, de ___ A&Ntilde;OS de edad, estado civil _____, con instrucci&oacute;n _____, y por lo tanto SI s&eacute; leer y escribir, de ocupaci&oacute;n ______, originario, y vecino de ____ &nbsp;con domicilio en la calle ____, Tel&eacute;fono _____. Enseguida, se le hace saber al denunciante, ofendido o v&iacute;ctima que tiene los siguientes derechos: se procede a enterar de los derechos que </font><font face="Arial, serif"><span lang="es-ES">la Constituci&oacute;n Pol&iacute;tica de los Estados Unidos Mexicanos, la Constituci&oacute;n Pol&iacute;tica del Estado de Coahuila, los tratados y otras leyes secundarias que de aqu&eacute;llas emanen, y el art&iacute;culo 53 de la </span></font><font face="Arial, serif">Ley del Sistema Integral de Justicia para Adolescentes del Estado de Coahuila, otorgan a la v&iacute;ctima u ofendido los siguientes derechos:</font></font></font></p>
<p align="justify" style="margin-left: 0.83in; margin-top: 0.19in; margin-bottom: 0.19in; line-height: 150%"> 
<font face="Times New Roman, serif"><font style="font-size: 12pt"><font face="Arial, serif"><b>I.</b></font><font face="Arial, serif"> A ser informados desde la primera ocasi&oacute;n en que se tenga contacto con ellos, acerca de los derechos que en su favor le reconoce la Constituci&oacute;n Pol&iacute;tica de los Estados Unidos Mexicanos, los Tratados Internacionales celebrados y aprobados por el Estado mexicano, esta ley y dem&aacute;s disposiciones aplicables; </font><font face="Arial, serif"><b>II.</b></font><font face="Arial, serif"> A recibir trato sin discriminaci&oacute;n, a fin de evitar que se atente contra la dignidad humana y se anulen o menoscaben sus derechos y libertades, por lo que la protecci&oacute;n de sus derechos se har&aacute; sin distinci&oacute;n alguna; </font><font face="Arial, serif"><b>III.</b></font><font face="Arial, serif"> A acceder a la justicia de manera pronta, gratuita e imparcial respecto de sus denuncias o querellas; </font><font face="Arial, serif"><b>IV.</b></font><font face="Arial, serif"> A ser tratado con respeto y dignidad; </font><font face="Arial, serif"><b>V.</b></font><font face="Arial, serif"> A que se le reconozca su calidad de parte durante todo el procedimiento; </font><font face="Arial, serif"><b>VI.</b></font><font face="Arial, serif"> A que el Ministerio P&uacute;blico y sus auxiliares, as&iacute; como las autoridades jurisdiccionales le faciliten el acceso a la justicia y le presenten los servicios que constitucionalmente tienen encomendados con legalidad, honradez, lealtad, imparcialidad, profesionalismo, eficiencia y eficacia, y con la debida diligencia; </font><font face="Arial, serif"><b>VII.</b></font><font face="Arial, serif"> A contar con informaci&oacute;n sobre los derechos que en su beneficio existan, como ser atendidos por personal del mismo sexo o del sexo que la v&iacute;ctima; </font><font face="Arial, serif"><b>VIII.</b></font><font face="Arial, serif"> A comunicarse, inmediatamente despu&eacute;s de haberse cometido la conducta tipificada como delito, con un familiar y/o con su asesor jur&iacute;dico; </font><font face="Arial, serif"><b>IX. </b></font><font face="Arial, serif">A ser informados, cuando lo soliciten, sobre el tr&aacute;mite del proceso por su asesor jur&iacute;dico, el Ministerio P&uacute;blico o, en su caso, por el juez; </font><font face="Arial, serif"><b>X. </b></font><font face="Arial, serif">A recibir, desde la comisi&oacute;n de la conducta tipificada como delito, atenci&oacute;n m&eacute;dica y psicol&oacute;gica de urgencia; </font><font face="Arial, serif"><b>XI.</b></font><font face="Arial, serif"> A contar con un asesor jur&iacute;dico gratuito en cualquier etapa del proceso, en los t&eacute;rminos de la legislaci&oacute;n aplicable; </font><font face="Arial, serif"><b>XII. </b></font><font face="Arial, serif">A promover y participar en los mecanismos alternos de soluci&oacute;n de controversias; </font><font face="Arial, serif"><b>XIII.</b></font><font face="Arial, serif"> A recibir gratuitamente la asistencia de un int&eacute;rprete o traductor desde la denuncia hasta la conclusi&oacute;n del proceso, cuando la v&iacute;ctima u ofendido pertenezcan a un grupo &eacute;tnico o pueblo ind&iacute;gena o no conozca o no comprenda el idioma espa&ntilde;ol; </font><font face="Arial, serif"><b>XIV.</b></font><font face="Arial, serif"> En caso de tener discapacidad, a que se realicen los ajustes al proceso que sean necesarios para salvaguardar sus derechos; </font><font face="Arial, serif"><b>XV. </b></font><font face="Arial, serif">A que se le proporcione asistencia migratoria cuando tenga otra nacionalidad; </font><font face="Arial, serif"><b>XVI.</b></font><font face="Arial, serif"> A que se le reciban todos los datos o elementos de prueba con los que cuente, tanto en la investigaci&oacute;n como en el proceso y que se desahoguen las actuaciones correspondientes; </font><font face="Arial, serif"><b>XVII. </b></font><font face="Arial, serif">A intervenir en el proceso por s&iacute; o a trav&eacute;s de su asesor jur&iacute;dico, e interponer los recursos correspondientes, aunque no se haya constituido como coadyuvante del Ministerio P&uacute;blico; </font><font face="Arial, serif"><b>XVIII. </b></font><font face="Arial, serif">A constituirse en acusadores coadyuvantes del Ministerio P&uacute;blico en los plazos y condiciones que establece esta ley; </font><font face="Arial, serif"><b>XIX. </b></font><font face="Arial, serif">A que se le provea protecci&oacute;n cuando exista riesgo para su vida o integridad personal; </font><font face="Arial, serif"><b>XX.</b></font><font face="Arial, serif"> A solicitar la realizaci&oacute;n de actos de investigaci&oacute;n que en su caso correspondan, salvo que el Misterio P&uacute;blico considere que no es necesario, debiendo en ese caso fundar y motivar su negativa; </font><font face="Arial, serif"><b>XXI. </b></font><font face="Arial, serif">A recibir atenci&oacute;n m&eacute;dica y psicol&oacute;gica, o a ser canalizado a instituciones que le proporcionen estos servicios, as&iacute; como a recibir protecci&oacute;n especial de su integridad f&iacute;sica y ps&iacute;quica cuando as&iacute; lo solicite, o cuando se trate de delitos que as&iacute; lo requieran; </font><font face="Arial, serif"><b>XXII.</b></font><font face="Arial, serif"> A solicitar las medidas de protecci&oacute;n, providencias precautorias y medidas cautelares que prevea la ley para su seguridad y auxilio; </font><font face="Arial, serif"><b>XXIII. </b></font><font face="Arial, serif">A solicitar el traslado de la autoridad al lugar en donde se encuentre, para ser interrogada o participar en el acto para el cual fue citada, cuando por su edad, enfermedad grave o por alguna imposibilidad f&iacute;sica o psicol&oacute;gica se dificulte su comparecencia, a cuyo fin deber&aacute; requerir la dispensa, por s&iacute; o por un tercero, con anticipaci&oacute;n; </font><font face="Arial, serif"><b>XXIV. </b></font><font face="Arial, serif">A impugnar por s&iacute; o por medio de su representante, las omisiones o negligencias que cometa el Ministerio P&uacute;blico en el desempe&ntilde;o de sus funciones de investigaci&oacute;n; </font><font face="Arial, serif"><b>XXV. </b></font><font face="Arial, serif">A tener acceso a los registros de la investigaci&oacute;n durante el proceso, as&iacute; como a obtener copia gratuita de &eacute;stos, salvo que la informaci&oacute;n est&eacute; sujeta a reserva as&iacute; determinada por el juez; </font><font face="Arial, serif"><b>XXVI. </b></font><font face="Arial, serif">A ser restituido en sus derechos, cuando &eacute;stos est&eacute;n acreditados; </font><font face="Arial, serif"><b>XXVII.</b></font><font face="Arial, serif"> A exigir y recibir la reparaci&oacute;n del da&ntilde;o por el adolescente acusado o terceros obligados, pudiendo solicitarlo directamente al juez, sin perjuicio de que el Ministerio P&uacute;blico lo solicite. En los casos en que sea procedente, el Ministerio P&uacute;blico estar&aacute; obligado a solicitar la reparaci&oacute;n del da&ntilde;o y el juzgador no podr&aacute; absolver al adolescente de dicha reparaci&oacute;n si ha emitido una resoluci&oacute;n definitiva condenatoria; </font><font face="Arial, serif"><b>XXVIII.</b></font><font face="Arial, serif"> Al resguardo de su identidad y dem&aacute;s datos personales cuando sean menores de edad, se trate de delitos de violaci&oacute;n contra la libertad y el normal desarrollo psicosexual, violencia familiar, secuestro, trata de personas o cuando a criterio del juez sea necesario para su protecci&oacute;n, salvaguardando en todo caso los derechos de la defensa; </font><font face="Arial, serif"><b>XXIX. </b></font><font face="Arial, serif">A ser notificado del desistimiento de la acci&oacute;n de remisi&oacute;n y de todas las resoluciones que finalicen el proceso; </font><font face="Arial, serif"><b>XXX. </b></font><font face="Arial, serif">A solicitar la reapertura del proceso cuando se haya decretado su suspensi&oacute;n; </font><font face="Arial, serif"><b>XXXI.</b></font><font face="Arial, serif">A impugnar toda decisi&oacute;n sobre el no ejercicio de la acci&oacute;n de remisi&oacute;n; </font><font face="Arial, serif"><b>XXXII.</b></font><font face="Arial, serif"> A presentar acci&oacute;n de remisi&oacute;n particular conforme a las formalidades previstas y, en su caso, a desistirse de la misma; </font><font face="Arial, serif"><b>XXXIII.</b></font><font face="Arial, serif"> A ser informado del significado y consecuencias jur&iacute;dicas del otorgamiento del perd&oacute;n en los casos que proceda; y </font><font face="Arial, serif"><b>XXXIV.</b></font><font face="Arial, serif"> Las dem&aacute;s que se contengan en la Constituci&oacute;n Pol&iacute;tica de los Estados Unidos Mexicanos, los Tratados Internacionales celebrados y aprobados por el Estado mexicano, esta ley y dem&aacute;s leyes aplicables. Acto&nbsp; seguido, una vez que conoce de los derechos y obligaciones que le asisten en la averiguaci&oacute;n previa penal e interrogado sobre el motivo de su comparecencia los declarantes: -------------------------------------------------------------------------------- M A N I F E S T A -------------Que comparece ante esta Representaci&oacute;n Social en forma libre y voluntaria a fin de&nbsp; Ratificar en todas y cada uno de sus t&eacute;rminos de la Denuncia y/o </font><b><font face="Arial, serif">Querella de fecha _______ por el Delito de (delito), en contra de&nbsp; probable responsable _______QUIEN TIENE SU DOMICILIO EN LA CALLE domicilio del probable.- </font></b><font face="Arial, serif">Que es todo lo que tengo que manifestar, firmando al margen los que en ella intervinieron y se asienta en forma para debida constancia. </font></font></font></p>
<p style="margin-left: 0.83in; margin-top: 0.19in; margin-bottom: 0.19in; line-height: 150%"> 
<br /> 
&nbsp;</p>
<p align="center" style="margin-left: 0.83in; margin-top: 0.19in; margin-bottom: 0.19in; line-height: 150%"> 
<font face="Times New Roman, serif"><font style="font-size: 12pt"><b><font face="Arial, serif">AGENTE DEL MINISTERIO PUBLICO ESPECIALIZADO</font></b></font></font></p>
<p style="margin-top: 0.19in; margin-bottom: 0.19in; line-height: 150%"> 
<br /> 
&nbsp;</p>
<p align="center" style="margin-left: 0.83in; margin-top: 0.19in; margin-bottom: 0.19in; line-height: 150%"> 
<font face="Times New Roman, serif"><font style="font-size: 12pt"><b><font face="Arial, serif">LIC.&nbsp; __________________________</font></b></font></font></p>
<p style="margin-left: 0.83in; margin-top: 0.19in; margin-bottom: 0.19in; line-height: 150%"> 
<br /> 
&nbsp;</p>
<p style="margin-left: 0.83in; margin-top: 0.19in; margin-bottom: 0.19in; line-height: 150%"> 
<br /> 
&nbsp;</p>
<p style="margin-left: 0.83in; margin-top: 0.19in; margin-bottom: 0.19in; line-height: 150%"> 
<br /> 
&nbsp;</p>
<p style="margin-left: 0.83in; margin-top: 0.19in; margin-bottom: 0.19in; line-height: 150%"> 
<br /> 
&nbsp;</p>
<p style="margin-left: 0.83in; margin-top: 0.19in; margin-bottom: 0.19in; line-height: 150%"> 
<br /> 
&nbsp;</p>
<p style="margin-left: 0.83in; margin-top: 0.19in; margin-bottom: 0.19in; line-height: 150%"> 
<br /> 
&nbsp;</p>
<p style="margin-bottom: 0.14in; line-height: 150%"> 
<br /> 
&nbsp;</p>'
    ,
    'RATIFICACION DE DENUNCIA'
  )
  ,
  (
    'SOLICITUD DE COPIA DE EXPEDIENTE CLINICO (ADOLESCENTES)',
    1611,
    '<p align="right" style="margin-bottom: 0.14in; line-height: 150%"> 
<font face="Arial, serif"><font style="font-size: 12pt">SOLICITUD DE COPIA DE EXPEDIENTE CL&Iacute;NICO </font></font></p>
<p align="right" style="margin-left: 2.46in; margin-top: 0.19in; margin-bottom: 0.19in; line-height: 150%"> 
&nbsp;&nbsp;&nbsp;&nbsp; <font face="Times New Roman, serif"><font style="font-size: 12pt"><b><font face="Arial, serif">ASUNTO:&nbsp;SE SOLICITA COPIA DE EXPEDIENTE CL&Iacute;NICO</font></b></font></font></p>
<p align="right" style="margin-left: 3.64in; margin-top: 0.19in; margin-bottom: 0.19in; line-height: 150%"> 
<font face="Times New Roman, serif"><font style="font-size: 12pt"><b><font face="Arial, serif">N.U.C : ______________________</font></b></font></font></p>
<p align="right" style="margin-left: 0.69in; margin-top: 0.19in; margin-bottom: 0.19in; line-height: 150%"> 
<font face="Times New Roman, serif"><font style="font-size: 12pt"><b><font face="Arial, serif">Monclova, ____________ a ____________</font></b></font></font></p>
<p align="right" style="margin-left: 3.64in; margin-top: 0.19in; margin-bottom: 0.19in; line-height: 150%"> 
&nbsp;</p>
<p style="margin-left: 0.69in; margin-top: 0.19in; margin-bottom: 0.19in; line-height: 150%"> 
<font face="Times New Roman, serif"><font style="font-size: 12pt"><b><font face="Arial, serif">REPRESENTANTE LEGAL </font></b></font></font></p>
<p style="margin-left: 0.69in; margin-top: 0.19in; margin-bottom: 0.19in; line-height: 150%"> 
<font face="Times New Roman, serif"><font style="font-size: 12pt"><b><font face="Arial, serif">DE LA CL&Iacute;NICA____________________</font></b></font></font></p>
<p style="margin-left: 0.69in; margin-top: 0.19in; margin-bottom: 0.19in; line-height: 150%"> 
<font face="Times New Roman, serif"><font style="font-size: 12pt"><b><font face="Arial, serif">P R E S E N T E.-</font></b></font></font></p>
<p align="right" style="margin-left: 0.69in; margin-top: 0.19in; margin-bottom: 0.19in; line-height: 150%"> 
<br /> 
&nbsp;</p>
<p align="justify" style="margin-left: 0.69in; margin-top: 0.19in; margin-bottom: 0.19in; line-height: 150%"> 
<font face="Times New Roman, serif"><font style="font-size: 12pt"><font face="Arial, serif">Me permito solicitarle&nbsp; de la manera m&aacute;s atenta y de conformidad con lo establecido en </font><font color="#000000"><font face="Arial, serif">el art&iacute;culo 21 Constituci&oacute;n Pol&iacute;tica de los Estados Unidos Mexicanos, en relaci&oacute;n con los numerales 61, 62, 63, </font></font><font face="Arial, serif">132 y 133 de la Ley del Sistema Integral de Justicia para Adolescentes del Estado de Coahuila</font><font color="#000000"><font face="Arial, serif">, instruya</font></font><font face="Arial, serif"> al personal a su digno Cargo a efecto de que me sea proporcionada copia certificada de las constancias que conforman el expediente cl&iacute;nico con su respectivo resumen de _________________________, con n&uacute;mero de seguridad social _________________________quien estuvo internado en el &aacute;rea de_________________ en fecha___________________ . </font></font></font></p>
<p align="justify" style="margin-left: 0.69in; margin-top: 0.19in; margin-bottom: 0.19in; line-height: 150%"> 
<font face="Times New Roman, serif"><font style="font-size: 12pt"><font face="Arial, serif">As&iacute; mismo, le hago de su conocimiento que la informaci&oacute;n solicitada debe proporcionarse en un plazo no mayor de ___________ contados a partir de la recepci&oacute;n del presente, envi&aacute;ndola a las oficinas que ocupa esta Representaci&oacute;n Social ubicada en _________________________________de la ciudad de________________, en caso de incumplir sin causa de justificaci&oacute;n se le impondr&aacute; la medida de apremio&nbsp; que para tal efecto determine la autoridad correspondiente.&nbsp;</font></font></font></p>
<p align="justify" style="margin-left: 0.69in; margin-top: 0.19in; margin-bottom: 0.19in; line-height: 150%"> 
<font face="Times New Roman, serif"><font style="font-size: 12pt"><font face="Arial, serif">Lo anterior, en virtud de que la informaci&oacute;n es necesaria para la debida integraci&oacute;n de la carpeta de investigaci&oacute;n mencionada al rubro.</font></font></font></p>
<p align="justify" style="margin-left: 0.69in; margin-top: 0.19in; margin-bottom: 0.19in; line-height: 150%"> 
<font face="Times New Roman, serif"><font style="font-size: 12pt"><font face="Arial, serif">Sin otro en particular, reitero a Usted la seguridad de mi atenta y distinguida consideraci&oacute;n</font></font></font></p>
<p align="center" style="margin-left: 0.69in; margin-top: 0.19in; margin-bottom: 0.19in; line-height: 150%"> 
<font face="Times New Roman, serif"><font style="font-size: 12pt"><b><font face="Arial, serif">A T E N T A M E N T E</font></b></font></font></p>
<p align="center" style="margin-left: 0.69in; margin-top: 0.19in; margin-bottom: 0.19in; line-height: 150%"> 
<br /> 
&nbsp;</p>
<p align="center" style="margin-left: 0.69in; margin-top: 0.19in; margin-bottom: 0.19in; line-height: 150%"> 
<font face="Times New Roman, serif"><font style="font-size: 12pt"><b><font face="Arial, serif">AGENTE DEL MINISTERIO P&Uacute;BLICO ESPECIALIZADO</font></b></font></font></p>
<p align="center" style="margin-bottom: 0.14in; line-height: 150%"> 
<br /> 
&nbsp;</p>
<p align="center" style="margin-bottom: 0.14in; line-height: 150%"> 
<a name="_GoBack"></a> <font face="Arial, serif"><font style="font-size: 12pt"><b>LIC. </b></font></font></p>
<p style="margin-bottom: 0.14in; line-height: 150%"> 
<br /> 
&nbsp;</p>'
    ,
    'SOLICITUD DE COPIA DE EXPEDIENTE CLINICO (ADOLESCENTES)'
  )
  ,
  (
    'SOLICITUD DE DICTAMEN PERICIAL (ADOLESCENTES)',
    1611,
    '<p align="center" style="margin-bottom: 0.14in; line-height: 150%"> 
<font color="#0070c0"><font face="Arial, serif"><b>SOLICITUD DE DICTAMEN PERICIAL</b></font></font></p>
<p align="right" style="margin-left: 3.33in; margin-top: 0.19in; margin-bottom: 0.19in; line-height: 150%"> 
<font face="Times New Roman, serif"><font style="font-size: 12pt"><font face="Arial, serif"><font style="font-size: 11pt">N.U.C : ____________</font></font></font></font></p>
<p align="right" style="margin-left: 3.33in; margin-top: 0.19in; margin-bottom: 0.19in; line-height: 150%"> 
<font face="Times New Roman, serif"><font style="font-size: 12pt"><font face="Arial, serif"><font style="font-size: 11pt">EXPEDIENTE NUMERO: </font></font><b><font face="Arial, serif"><font style="font-size: 11pt">____________</font></font></b></font></font></p>
<p align="right" style="margin-left: 2.92in; margin-top: 0.19in; margin-bottom: 0.19in; line-height: 150%"> 
&nbsp;<font face="Times New Roman, serif"><font style="font-size: 12pt"><font face="Arial, serif"><font style="font-size: 11pt">ASUNTO:</font></font><b><font face="Arial, serif"><font style="font-size: 11pt"> SE SOLICITA PERITAJE</font></font></b></font></font></p>
<p style="margin-left: 0.83in; margin-top: 0.19in; margin-bottom: 0.19in; line-height: 150%"> 
&nbsp;</p>
<p style="margin-left: 0.83in; margin-top: 0.19in; margin-bottom: 0.19in; line-height: 150%"> 
<font face="Times New Roman, serif"><font style="font-size: 12pt"><b><font face="Arial, serif"><font style="font-size: 11pt">PERITO _______________________</font></font></b></font></font></p>
<p style="margin-left: 0.83in; margin-top: 0.19in; margin-bottom: 0.19in; line-height: 150%"> 
<font face="Times New Roman, serif"><font style="font-size: 12pt"><b><font face="Arial, serif"><font style="font-size: 11pt">EN MATERIA DE ________________ </font></font></b></font></font></p>
<p style="margin-left: 0.83in; margin-top: 0.19in; margin-bottom: 0.19in; line-height: 150%"> 
<font face="Times New Roman, serif"><font style="font-size: 12pt"><b><font face="Arial, serif"><font style="font-size: 11pt">P R E S E N T E.-</font></font></b></font></font></p>
<p align="right" style="margin-left: 0.83in; margin-top: 0.19in; margin-bottom: 0.19in; line-height: 150%"> 
<font face="Times New Roman, serif"><font style="font-size: 12pt"><b><font face="Arial, serif"><font style="font-size: 11pt">Lugar y fecha: ___________________, Coahuila a _________ de __________ de __________</font></font></b></font></font></p>
<p align="right" style="margin-left: 0.83in; margin-top: 0.19in; margin-bottom: 0.19in; line-height: 150%"> 
&nbsp;</p>
<p align="justify" style="margin-left: 0.83in; margin-top: 0.19in; margin-bottom: 0.19in; line-height: 150%"> 
<font face="Times New Roman, serif"><font style="font-size: 12pt"><font face="Arial, serif"><font style="font-size: 11pt">En vista de que se ha iniciado carpeta de investigaci&oacute;n en contra del adolescente__________________________, por su probable participaci&oacute;n en la comisi&oacute;n de hechos que la Ley se&ntilde;ala como el delito de ________________, cometido en agravio de ________________, y con fundamento en lo </font></font><font color="#000000"><font face="Arial, serif"><font style="font-size: 11pt">establecido</font></font></font><b><font color="#000000"><font face="Arial, serif"><font style="font-size: 11pt"> </font></font></font></b><font color="#000000"><font face="Arial, serif"><font style="font-size: 11pt">los art&iacute;culos 61, 62, 63, 132 y 133 de la Ley del Sistema Integral de Justicia de Adolescentes del Estado de Coahuila</font></font></font><b><font color="#000000"><font face="Arial, serif"><font style="font-size: 11pt">,&nbsp; </font></font></font></b><font color="#000000"><font face="Arial, serif"><font style="font-size: 11pt">se le designa como perito co</font></font></font><font face="Arial, serif"><font style="font-size: 11pt">n la finalidad&nbsp; de que realice peritaje en la materia de ____________________________________, toda vez que (justificar la necesidad del dictamen).</font></font></font></font></p>
<p style="margin-left: 0.83in; margin-top: 0.19in; margin-bottom: 0.19in; line-height: 150%"> 
<font face="Times New Roman, serif"><font style="font-size: 12pt"><font face="Arial, serif"><font style="font-size: 11pt">Dictamen que deber&aacute; emitir a la brevedad.</font></font></font></font></p>
<p style="margin-left: 0.83in; margin-top: 0.19in; margin-bottom: 0.19in; line-height: 150%"> 
&nbsp;&nbsp;</p>
<p style="margin-left: 0.83in; margin-top: 0.19in; margin-bottom: 0.19in; line-height: 150%"> 
<br /> 
&nbsp;</p>
<p align="center" style="margin-left: 0.83in; margin-top: 0.19in; margin-bottom: 0.19in; line-height: 150%"> 
<font face="Times New Roman, serif"><font style="font-size: 12pt"><b><font face="Arial, serif"><font style="font-size: 11pt">A T E N T A M E N T E</font></font></b></font></font></p>
<p align="center" style="margin-left: 0.83in; margin-top: 0.19in; margin-bottom: 0.19in; line-height: 150%"> 
<a name="_GoBack"></a> <font face="Times New Roman, serif"><font style="font-size: 12pt"><b><font face="Arial, serif"><font style="font-size: 11pt">AGENTE DEL MINISTERIO P&Uacute;BLICO ESPECIALIZADO</font></font></b></font></font></p>
<p align="center" style="margin-left: 0.83in; margin-bottom: 0.14in; line-height: 150%"> 
<b><font face="Arial, serif">Lic.</font></b><font face="Arial, serif"><b> </b></font><font face="Arial, serif">___________________________</font></p>
<p style="margin-bottom: 0.14in; line-height: 150%"> 
<br /> 
&nbsp;</p>'
    ,
    'SOLICITUD DE DICTAMEN PERICIAL (ADOLESCENTES)'
  )
  ,
  (
    'SOLICITUD DE INTERNAMIENTO (ADOLESCENTES)',
    1611,
    '<p align="right" style="margin-bottom: 0.14in; line-height: 150%"> 
<font face="Arial, serif"><b>SOLICITUD DE INTERNAMIENTO</b></font></p>
<p align="right" style="margin-left: 0.83in; margin-top: 0.19in; margin-bottom: 0.19in; line-height: 150%"> 
&nbsp;</p>
<p align="right" style="margin-left: 0.83in; margin-top: 0.19in; margin-bottom: 0.19in; line-height: 150%"> 
<font face="Times New Roman, serif"><font style="font-size: 12pt"><font face="Arial, serif"><font style="font-size: 11pt">EXPEDIENTE NUMERO: ______________________<br /> 
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; N.U.C : ______________________<br /> 
&nbsp; ASUNTO: SOLICITUD DE INTERNAMIENTO</font></font></font></font></p>
<p style="margin-left: 0.83in; margin-top: 0.19in; margin-bottom: 0.19in; line-height: 150%"> 
<br /> 
<br /> 
<font face="Times New Roman, serif"><font style="font-size: 12pt"><font face="Arial, serif"><font style="font-size: 11pt">COMANDANTE DE LA POLIC&Iacute;A INVESTIGADORA<br /> 
P R E S E N T E.-</font></font></font></font></p>
<p align="right" style="margin-left: 0.83in; margin-top: 0.19in; margin-bottom: 0.17in; line-height: 150%"> 
<font face="Times New Roman, serif"><font style="font-size: 12pt"><font face="Arial, serif"><font style="font-size: 11pt">(Ciudad), (Estado), (Fecha)</font></font></font></font></p>
<p align="justify" style="margin-left: 0.83in; margin-top: 0.19in; margin-bottom: 0.17in; line-height: 150%"> 
<br /> 
<br /> 
<font face="Times New Roman, serif"><font style="font-size: 12pt"><font face="Arial, serif"><font style="font-size: 11pt">En vista de que fue puesto a disposici&oacute;n de esta Representaci&oacute;n Social, el adolescente _____________________________, en calidad de detenido, solicito a usted que el mismo contin&uacute;e internado en el &aacute;rea de considerados que para tal efecto este designada y con las condiciones aptas para los adolescentes, lo anterior hasta en tanto se resuelva la situaci&oacute;n jur&iacute;dica.<br /> 
<br /> 
Lo anterior con fundamento en </font></font><font color="#000000"><font face="Arial, serif"><font style="font-size: 11pt">el art&iacute;culo 21 de la Constituci&oacute;n Pol&iacute;tica de los Estados Unidos Mexicanos y&nbsp; 61, 62, 63, </font></font></font><font face="Arial, serif"><font style="font-size: 11pt">132 y 133 de la Ley del Sistema Integral de Justicia para Adolescentes del Estado de Coahuila.</font></font></font></font></p>
<p align="center" style="margin-left: 0.83in; margin-top: 0.19in; margin-bottom: 0.19in; line-height: 150%"> 
<br /> 
&nbsp;</p>
<p align="center" style="margin-left: 0.83in; margin-top: 0.19in; margin-bottom: 0.19in; line-height: 150%"> 
<br /> 
<br /> 
<font face="Times New Roman, serif"><font style="font-size: 12pt"><font face="Arial, serif"><font style="font-size: 11pt">A T E N T A M E N T E<br /> 
AGENTE DEL MINISTERIO P&Uacute;BLICO ESPECIALIZADO</font></font></font></font></p>
<p align="center" style="margin-left: 0.83in; margin-top: 0.19in; margin-bottom: 0.19in; line-height: 150%"> 
<a name="_GoBack"></a> <font face="Times New Roman, serif"><font style="font-size: 12pt"><font face="Arial, serif"><font style="font-size: 11pt">LIC. </font></font></font></font></p>
<p align="center" style="margin-left: 0.83in; margin-top: 0.19in; margin-bottom: 0.19in; line-height: 150%"> 
<br /> 
<br /> 
&nbsp;</p>
<p style="margin-bottom: 0.14in; line-height: 150%"> 
<br /> 
&nbsp;</p>'
    ,
    'SOLICITUD DE INTERNAMIENTO (ADOLESCENTES)'
  )
  ,
  (
    'SOLICITUD DE PERITAJE  EN MATERIA DE VALUACI0N',
    1611,
    '<p align="center" style="margin-bottom: 0.14in; line-height: 150%"> 
<font face="Arial, serif"><font style="font-size: 12pt"><b>SOLICITUD DE PERITAJE EN MATERIA DE VALUACI&Oacute;N </b></font></font></p>
<p align="right" style="margin-left: 0.83in; margin-top: 0.19in; margin-bottom: 0.19in; line-height: 150%"> 
<font face="Times New Roman, serif"><font style="font-size: 12pt"><font face="Arial, serif">N&uacute;m. Carpeta de investigaci&oacute;n:&nbsp;_____________________</font></font></font></p>
<p align="right" style="margin-left: 0.83in; margin-top: 0.19in; margin-bottom: 0.19in; line-height: 150%"> 
<font face="Times New Roman, serif"><font style="font-size: 12pt"><font face="Arial, serif">NUC:_____________________________<br /> 
</font></font></font><br /> 
&nbsp;</p>
<p style="margin-left: 0.83in; margin-top: 0.19in; margin-bottom: 0.19in; line-height: 150%"> 
<font face="Times New Roman, serif"><font style="font-size: 12pt"><font face="Arial, serif">ASUNTO: SE SOLICITA PERITAJE &nbsp;<br /> 
<br /> 
<br /> 
</font><br /> 
<font face="Arial, serif"><b> PERITO:<br /> 
EN MATERIA DE VALUACI&Oacute;N<br /> 
PRESENTE</b></font><font face="Arial, serif">.-</font></font></font></p>
<p align="right" style="margin-left: 0.83in; margin-top: 0.19in; margin-bottom: 0.19in; line-height: 150%"> 
<a name="_GoBack"></a><br /> 
<font face="Times New Roman, serif"><font style="font-size: 12pt"><font face="Arial, serif"> ___, Coahuila de Zaragoza a ___de___de _____.</font></font></font></p>
<p align="justify" style="margin-left: 0.83in; margin-top: 0.19in; margin-bottom: 0.17in; line-height: 150%"> 
<br /> 
<br /> 
<br /> 
<font face="Times New Roman, serif"><font style="font-size: 12pt"><font face="Arial, serif">&nbsp;&nbsp; &nbsp;En vista de que se ha iniciado carpeta de investigaci&oacute;n en contra del adolescente_____ por su probable participaci&oacute;n en la comisi&oacute;n de los hechos que la ley se&ntilde;ala como una conducta tipificada como el delito de ______________________cometido en agravio de ___________________ los art&iacute;culos 61, 62. 63, 132 y 133 de la Ley del Sistema Integral de Justicia para Adolescentes del Estado de Coahuila, en relaci&oacute;n con los numerales</font><font color="#000000"><font face="Arial, serif"> </font></font><font face="Arial, serif">con fundamento en lo establecido por </font><font color="#000000"><font face="Arial, serif">los art&iacute;culos 127, 131, 272 y dem&aacute;s aplicables del C&oacute;digo Nacional de Procedimientos Penales,</font></font><font face="Arial, serif"> se le designa como perito con la finalidad&nbsp; de que realice peritaje en materia de valuaci&oacute;n, toda vez que se requiere saber el precio actual en el mercado de ______<br /> 
<br /> 
Dictamen que deber&aacute; emitir a la brevedad.</font></font></font></p>
<p align="center" style="margin-left: 0.83in; margin-top: 0.19in; margin-bottom: 0.19in; line-height: 150%"> 
<br /> 
<br /> 
<font face="Times New Roman, serif"><font style="font-size: 12pt"><font face="Arial, serif"><b>ATENTAMENTE.<br /> 
AGENTE DEL MINISTERIO P&Uacute;BLICO ESPECIALIZADO<br /> 
<br /> 
<br /> 
<br /> 
LIC. _________________________</b></font></font></font></p>
<p align="center" style="margin-bottom: 0.14in; line-height: 150%"> 
<br /> 
&nbsp;</p>
<p align="center" style="margin-bottom: 0.14in; line-height: 150%"> 
<br /> 
&nbsp;</p>
<p style="margin-bottom: 0.14in; line-height: 150%"> 
<br /> 
&nbsp;</p>'
    ,
    'SOLICITUD DE PERITAJE  EN MATERIA DE VALUACI0N'
  )
  ,
  (
    'SOLICITUD DE PERITAJE DE CRIMINALISTICA DE CAMPO (ADOLESCENTES)',
    1611,
    '<p align="center" style="margin-left: 0.83in; margin-top: 0.19in; margin-bottom: 0.19in; line-height: 150%"> 
<br /> 
&nbsp;</p>
<p align="right" style="margin-bottom: 0.14in; line-height: 150%"> 
<font color="#000000"><font face="Arial, serif"><font style="font-size: 12pt">SOLICITUD DE PERITAJE DE CRIMINAL&Iacute;STICA DE CAMPO</font></font></font></p>
<p align="right" style="margin-left: 0.83in; margin-top: 0.19in; margin-bottom: 0.17in; line-height: 150%"> 
<br /> 
&nbsp;</p>
<p align="right" style="margin-left: 0.83in; margin-top: 0.19in; margin-bottom: 0.17in; line-height: 150%"> 
<font face="Times New Roman, serif"><font style="font-size: 12pt"><font face="Arial, serif">EXPEDIENTE NUMERO: __________________<br /> 
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; N.U.C : __________________<br /> 
&nbsp; ASUNTO: SE SOLICITA PERITAJE</font></font></font></p>
<p style="margin-left: 0.83in; margin-top: 0.19in; margin-bottom: 0.19in; line-height: 150%"> 
<br /> 
<font face="Times New Roman, serif"><font style="font-size: 12pt"><font face="Arial, serif"><b>PERITO _______________________<br /> 
EN MATERIA DE ________________<br /> 
P R E S E N T E.-</b></font></font></font></p>
<p align="right" style="margin-left: 0.83in; margin-top: 0.19in; margin-bottom: 0.19in; line-height: 150%"> 
<font face="Times New Roman, serif"><font style="font-size: 12pt"><font face="Arial, serif">(Ciudad), (Estado), (Fecha)</font></font></font></p>
<p style="margin-left: 0.83in; margin-top: 0.19in; margin-bottom: 0.19in; line-height: 150%"> 
<br /> 
&nbsp;</p>
<p align="justify" style="margin-left: 0.83in; margin-top: 0.19in; margin-bottom: 0.19in; line-height: 150%"> 
<font face="Times New Roman, serif"><font style="font-size: 12pt"><font face="Arial, serif">En vista de que se ha iniciado carpeta de investigaci&oacute;n en contra del adolescente ___________________________, por su probable participaci&oacute;n en la comisi&oacute;n de hechos que la Ley se&ntilde;ala como el delito de ______________, cometido en agravio de _____________, y con fundamento en lo establecido </font><font color="#000000"><font face="Arial, serif">los art&iacute;culos 61,62, 63, </font></font><font face="Arial, serif">132 y 133 de la Ley del Sistema Integral de Justicia para Adolescentes del Estado de Coahuila</font><font color="#000000"><font face="Arial, serif">,</font></font><font face="Arial, serif">&nbsp; se le designa como perito con la finalidad&nbsp; de que realice peritaje en la materia de CRIMINAL&Iacute;STICA DE CAMPO, POR LO QUE SE INSTRUYE SE CONSTITUYE EN EL DOMICILIO UBICADO&nbsp; EN CALLE ____ NUMERO____ COLONIA____ DE ESTA CIUDAD DE&nbsp; ______, COAHUILA FIN DE QUE REALICE PLANIMETR&Iacute;A DE LUGAR DE LOS HECHOS, LOCALICE Y RECOLECTE EVIDENCIA. Dictamen que deber&aacute; emitir a la brevedad.</font></font></font></p>
<p style="margin-left: 0.83in; margin-top: 0.19in; margin-bottom: 0.17in; line-height: 150%"> 
<br /> 
<font face="Times New Roman, serif"><font style="font-size: 12pt"><font face="Arial, serif">Dictamen que deber&aacute; emitir a la brevedad.<br /> 
&nbsp; &nbsp;</font></font></font></p>
<p style="margin-left: 0.83in; margin-top: 0.19in; margin-bottom: 0.19in; line-height: 150%"> 
<br /> 
&nbsp;</p>
<p align="center" style="margin-left: 0.83in; margin-top: 0.19in; margin-bottom: 0.19in; line-height: 150%"> 
<font face="Times New Roman, serif"><font style="font-size: 12pt"><font face="Arial, serif">A T E N T A M E N T E<br /> 
AGENTE DEL MINISTERIO P&Uacute;BLICO ESPECIALIZADO</font></font></font></p>
<p align="center" style="margin-left: 0.83in; margin-top: 0.19in; margin-bottom: 0.19in; line-height: 150%"> 
<a name="_GoBack"></a><br /> 
<font face="Times New Roman, serif"><font style="font-size: 12pt"><font face="Arial, serif"> </font><b><font face="Arial, serif">LIC.</font></b></font></font></p>
<p style="margin-bottom: 0.14in; line-height: 150%"> 
<br /> 
&nbsp;</p>'
    ,
    'SOLICITUD DE PERITAJE DE CRIMINALISTICA DE CAMPO (ADOLESCENTES)'
  )
  ,
  (
    'SOLICITUD DE VIDEOGRABACION',
    1611,
    '<p align="right" style="margin-bottom: 0.14in; line-height: 150%"> 
<font face="Arial, serif"><font style="font-size: 12pt">ASUNTO: SE SOLICITA &nbsp;VIDEOGRABACI&Oacute;N</font></font></p>
<p align="right" style="margin-left: 3.93in; margin-top: 0.19in; margin-bottom: 0.19in; line-height: 150%;"> 
<br /> 
&nbsp;</p>
<p align="right" style="margin-left: 3.93in; margin-top: 0.19in; margin-bottom: 0.19in; line-height: 150%"> 
<font face="Times New Roman, serif"><font style="font-size: 12pt"><font face="Arial, serif">N.U.C : __________________</font></font></font></p>
<p align="right" style="margin-left: 3.93in; margin-top: 0.19in; margin-bottom: 0.19in; line-height: 150%"> 
<font face="Times New Roman, serif"><font style="font-size: 12pt"><font face="Arial, serif">(Ciudad), (Estado), (Fecha</font><b><font face="Arial, serif">)</font></b></font></font></p>
<p style="margin-left: 0.98in; margin-top: 0.19in; margin-bottom: 0.19in; line-height: 150%"> 
<br /> 
&nbsp;</p>
<p style="margin-left: 0.98in; margin-top: 0.19in; margin-bottom: 0.19in; line-height: 150%"> 
<font face="Times New Roman, serif"><font style="font-size: 12pt"><b><font face="Arial, serif">REPRESENTANTE LEGAL DE __________________</font></b></font></font></p>
<p style="margin-left: 0.98in; margin-top: 0.19in; margin-bottom: 0.19in; line-height: 150%"> 
<font face="Times New Roman, serif"><font style="font-size: 12pt"><b><font face="Arial, serif">P R E S E N T E.-</font></b></font></font></p>
<p align="right" style="margin-left: 0.98in; margin-top: 0.19in; margin-bottom: 0.19in; line-height: 150%"> 
&nbsp;</p>
<p align="justify" style="margin-left: 0.98in; margin-top: 0.19in; margin-bottom: 0.19in; line-height: 150%"> 
<font face="Times New Roman, serif"><font style="font-size: 12pt"><font face="Arial, serif">Me permito solicitarle&nbsp; de la manera m&aacute;s atenta y de conformidad con lo establecido </font><font color="#000000"><font face="Arial, serif">en el art&iacute;culo 21 Constituci&oacute;n Pol&iacute;tica de los Estados Unidos Mexicanos, en relaci&oacute;n con los numerales 61, 62, 63 , </font></font><font face="Arial, serif">132 y 133 de la Ley del Sistema Integral de Justicia para Adolescentes del Estado de Coahuila</font><font color="#000000"><font face="Arial, serif">,</font></font><font face="Arial, serif"> instruya al personal a su digno Cargo a efecto de que me sea proporcionada copia de la videograbaci&oacute;n de las c&aacute;maras de circuito cerrado de _____________, ubicada en __________________________de la colonia de __________________, de la ciudad de _______________ espec&iacute;ficamente de fecha (s)&nbsp; ________________________ en el horario comprendido entre ______________________&nbsp; y las _____________________ horas.&nbsp;</font></font></font></p>
<p align="justify" style="margin-left: 0.98in; margin-top: 0.19in; margin-bottom: 0.19in; line-height: 150%"> 
<font face="Times New Roman, serif"><font style="font-size: 12pt"><font face="Arial, serif">As&iacute; mismo, anexo al presente&nbsp; ________________________ (disco compacto, o memoria)&nbsp; a efecto de que me sea proporcionado&nbsp; la videograbaci&oacute;n, en virtud de que la informaci&oacute;n es necesaria para la debida integraci&oacute;n de la carpeta de investigaci&oacute;n mencionada al rubro.</font></font></font></p>
<p align="justify" style="margin-left: 0.98in; margin-top: 0.19in; margin-bottom: 0.19in; line-height: 150%"> 
<font face="Times New Roman, serif"><font style="font-size: 12pt"><font face="Arial, serif">Sin otro en particular, reitero a Usted la seguridad de mi atenta y distinguida consideraci&oacute;n.</font></font></font></p>
<p style="margin-left: 0.98in; margin-top: 0.19in; margin-bottom: 0.19in; line-height: 150%"> 
<br /> 
&nbsp;</p>
<p align="center" style="margin-left: 0.98in; margin-top: 0.19in; margin-bottom: 0.19in; line-height: 150%"> 
<font face="Times New Roman, serif"><font style="font-size: 12pt"><b><font face="Arial, serif">A T E N T A M E N T E</font></b></font></font></p>
<p align="center" style="margin-left: 0.98in; margin-top: 0.19in; margin-bottom: 0.19in; line-height: 150%"> 
<font face="Times New Roman, serif"><font style="font-size: 12pt"><b><font face="Arial, serif">AGENTE DEL MINISTERIO P&Uacute;BLICO ESPECIALIZADO</font></b></font></font></p>
<p align="center" style="margin-left: 0.98in; margin-top: 0.19in; margin-bottom: 0.19in; line-height: 150%"> 
<a name="_GoBack"></a> <font face="Times New Roman, serif"><font style="font-size: 12pt"><font face="Arial, serif">LIC. </font></font></font></p>
<p align="center" style="margin-bottom: 0.14in; line-height: 150%"> 
<br /> 
&nbsp;</p>
<p align="center" style="margin-bottom: 0.14in; line-height: 150%"> 
<br /> 
&nbsp;</p>'
    ,
    'SOLICITUD DE VIDEOGRABACION'
  );
