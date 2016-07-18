ALTER TABLE rol ADD esCoordinacion BIT not null DEFAULT 0  ;

--Actualiza el campo de esCoordinacion solo para las coordinaciones de PG--
update Rol
set   esCoordinacion=1
where Rol_id in (select  Rol_id 
				 from rol 
				 where  ConfInstitucion_id=1  
						and (cDescripcionRol like '%coor%' or cNombreRol like '%coor%') )


