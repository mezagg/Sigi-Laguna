delete from usuariorol
delete from usuario
delete from funcionario

DBCC CHECKIDENT  ('usuario',RESEED,0)
DBCC CHECKIDENT  ('usuariorol',RESEED,0)
DBCC CHECKIDENT  ('funcionario',RESEED,0)

exec i_Funcionario_sp 'administrador1', 'administrador', 'administrador', 'M', 4413, 4413, 42, 4413, 9, 1, NULL, 9, 'administrador', 36