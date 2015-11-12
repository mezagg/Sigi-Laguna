use [coa-pgsaltillocurso]

create table ConfUsuarioCatDiscriminante
(
Usuario_id decimal(18,0) not null,
catDiscriminante_id decimal(18,0) not null,
primary key(Usuario_id,catDiscriminante_id)
);

alter table ConfUsuarioCatDiscriminante
add constraint 
  fk_ConfUsuarioCatDiscriminante_Usuario foreign key(Usuario_id) references Usuario(Usuario_id);
  
alter table ConfUsuarioCatDiscriminante
add constraint 
  fk_ConfUsuarioCatDiscriminante_CatDiscriminante foreign key(catDiscriminante_id) references CatDiscriminante(catDiscriminante_id);
  
select * from ConfUsuarioCatDiscriminante


  