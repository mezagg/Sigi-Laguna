use [coa-pgsaltillocurso]
----------------------------------------------- **COMPROBAR SI YA ESTA REGISTRADA LA ACTUACION** --------------------------------------------------
select * from valor where cvalor like '%SOLICITUD DE VIDEOGRABACION%' -- Nos interesa el campocatalogo_id = 69 ya q es el tipoActividad 
select * from Forma where cNombre LIKE '%SOLICITUD DE VIDEOGRABACION%'   
---------------------------------------------------------------------------------------------------------------------------------------------------
--1. En caso de que no exista la actividad se registra con un usuario de tipo administrador (pantallas en word)
--2. En caso de que no exista la forma se registra con un usuario de tipo administrador (pantallas en word)

-- **Se dio de alta la Jerarquia organizacional: 74 | UIADO |	Unidad de Investigación de Delitos Cometidos por Adolescentes
select * from JerarquiaOrganizacional where JerarquiaOrganizacional_id = 74

-- **NOTA: SE REALIZO UPDATE DE LA JO PARA REALIZAR PRUEBAS SE ASIGNO LA JO 44(Atención Temprana Penal) A LAS ACTUACIONES
Update ConfActividadDocumento set JerarquiaOrganizacional_id=44  where JerarquiaOrganizacional_id=74

--3 Se inserta la jo
insert into (jerarquiaresponsable_id, cabreviatura, cnombre, tipojerarquia_val)
values (1,'UIADO','Unidad de Investigación de Delitos Cometidos por Adolescentes',2015);	

--4 Se registra la actuación
insert into ConfActividadDocumento (igrupoactividad, busaeditor, tipoactividad_val, tipodocumento_val, JerarquiaOrganizacional_id, 
  bmuestracombo, estadocambioexpediente_val, forma_id, categoriaactividad_val)
values 
  (1712,1,99324,89,74,1,1712,978,2595),
  (1712,1,99326,89,74,1,1712,	979,2595),
  (1712,1,99330,89,74,1,1712,	980,2595),
  (1712,1,20146,89,74,1,1712,	806,2595),
  (1712,1,99331,89,74,1,1712,	981,2595),
  (1712,1,20150,89,74,1,1712,	810,2595),
  (1712,1,519,89,74,1,1712,	983,2595),
  (1712,1,20226,89,74,1,1712,	989,2595),
  (1712,1,99333,89,74,1,1712,	990,2595),
  (1712,1,99339,89,74,1,1712,	984,2595),
  (1712,1,99343,89,74,1,1712,	985,2595),
  (1712,1,99345,89,74,1,1712,	986,2595),
  (1712,1,99347,89,74,1,1712,	987,2595),
  (1712,1,99349,89,74,1,1712,	988,2595),
  (1712,1,99351,89,74,1,1712,	991,2595),
  (1712,1,99353,89,74,1,1712,	992,2595),
  (1712,1,2326,89,74,1,1712,	994,2595),
  (1712,1,99355,89,74,1,1712,	995,2595),
  (1712,1,99357,89,74,1,1712,	996,2595),
  (1712,1,99359,89,74,1,1712,	997,2595),
  (1712,1,99361,89,74,1,1712,	998,2595),
  (1712,1,99363,89,74,1,1712,	999,2595),
  (1712,1,20186,89,74,1,1712,	1000,2595),
  (1712,1,20222,89,74,1,1712,	1001,2595),
  (1712,1,20192,89,74,1,1712,	1002,2595),
  (1712,1,99365,89,74,1,1712,	1003,2595),
  (1712,1,20232,89,74,1,1712,	1005,2595),
  (1712,1,99367,89,74,1,1712,	1006,2595);
  
  
-- 5 Se llena la tabla ConfActividadUIE
select * from ConfActividadUIE

insert into ConfActividadUIE
values
  (2,519,1),
  (2,2326,1),
  (2,20146,1),
  (2,20150,1),
  (2,20186,1),
  (2,20192,1),
  (2,20222,1),
  (2,20226,1),
  (2,20232,1),
  (2,99324,1),
  (2,99326,1),
  (2,99330,1),
  (2,99331,1),
  (2,99333,1),
  (2,99339,1),
  (2,99343,1),
  (2,99345,1),
  (2,99347,1),
  (2,99349,1),
  (2,99351,1),
  (2,99353,1),
  (2,99355,1),
  (2,99357,1),
  (2,99359,1),
  (2,99361,1),
  (2,99363,1),
  (2,99365,1),
  (2,99367,1);
  
  ----------------------------------------------- **AGREGAR UN ROL PARA ADOLESCSENTES** ------------------------------------------------------------

insert into Rol (cnombrerol, cdescripcionrol,besactivo,confinstitucion_id,jerarquiaorganizacional_id)
values
  ('adolescentes','Unidad de Investigación de Delitos Cometidos por Adolescentes',1,1,74);
  
  
  