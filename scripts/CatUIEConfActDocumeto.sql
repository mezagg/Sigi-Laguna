use [coa-pgsaltillocurso]

create table CatUIEConfActDocumeto
(
catUIE_id decimal(18,0) not null,
confActividadDocumento_id decimal(18,0) not null,
primary key(catUIE_id,confActividadDocumento_id)
);

alter table CatUIEConfActDocumeto
add constraint 
  fk_CatUIEConfActDocumeto_CatUIEspecializada foreign key(catUIE_id) references CatUIEspecializada(catUIE_id);
  
alter table CatUIEConfActDocumeto
add constraint 
  fk_CatUIEConfActDocumeto_ConfActividadDocumento foreign key(confActividadDocumento_id) references ConfActividadDocumento(confActividadDocumento_id);

