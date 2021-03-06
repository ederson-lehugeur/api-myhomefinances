INSERT INTO CATEGORIA(NOME, COMPLEMENTO) VALUES('Operação Bancária', 'Operações de Saque, Depósito, Transferências, etc.');
INSERT INTO CATEGORIA(NOME, COMPLEMENTO) VALUES('Casa', null);
INSERT INTO CATEGORIA(NOME, COMPLEMENTO) VALUES('Educação', null);
INSERT INTO CATEGORIA(NOME, COMPLEMENTO) VALUES('Eletrônicos', null);
INSERT INTO CATEGORIA(NOME, COMPLEMENTO) VALUES('Lazer', null);
INSERT INTO CATEGORIA(NOME, COMPLEMENTO) VALUES('Outros', null);
INSERT INTO CATEGORIA(NOME, COMPLEMENTO) VALUES('Restaurante', null);
INSERT INTO CATEGORIA(NOME, COMPLEMENTO) VALUES('Saúde', null);
INSERT INTO CATEGORIA(NOME, COMPLEMENTO) VALUES('Serviços', null);
INSERT INTO CATEGORIA(NOME, COMPLEMENTO) VALUES('Supermercado', null);
INSERT INTO CATEGORIA(NOME, COMPLEMENTO) VALUES('Transporte', null);
INSERT INTO CATEGORIA(NOME, COMPLEMENTO) VALUES('Vestuário', null);
INSERT INTO CATEGORIA(NOME, COMPLEMENTO) VALUES('Viagem', null);

INSERT INTO USUARIO(NOME, SOBRENOME, EMAIL, SENHA, DATA_HORA_CRIACAO, RESET_TOKEN, TOKEN_EXPIRATION_DATETIME)
	VALUES('Admin', 'Admin', 'admin@myhomefinances.com', '$2a$10$VsLSsK65i7UVSW3c/SS9Vexv.sdizQTQHQgyrQ5BuD4gPKwfo94Bi', '2021-01-24 23:23:20.229', NULL, NULL);

INSERT INTO USUARIO(NOME, SOBRENOME, EMAIL, SENHA, DATA_HORA_CRIACAO, RESET_TOKEN, TOKEN_EXPIRATION_DATETIME)
	VALUES('Ederson', 'Lehugeur', 'eder.lehugeur@gmail.com', '$2a$10$VsLSsK65i7UVSW3c/SS9Vexv.sdizQTQHQgyrQ5BuD4gPKwfo94Bi', '2021-01-24 23:23:20.229', NULL, NULL);

INSERT INTO PERFIL(NOME) VALUES('ROLE_ADMIN');
INSERT INTO PERFIL(NOME) VALUES('ROLE_USER');

INSERT INTO USUARIO_PERFIL(USUARIO_ID, PERFIL_ID) VALUES(1, 1);
INSERT INTO USUARIO_PERFIL(USUARIO_ID, PERFIL_ID) VALUES(1, 2);
INSERT INTO USUARIO_PERFIL(USUARIO_ID, PERFIL_ID) VALUES(2, 2);

INSERT INTO ITEM(NOME, COMPLEMENTO, DATA_HORA_CRIACAO, CATEGORIA_ID, USUARIO_ID)
	VALUES('Saldo inicial', NULL, '2021-01-24 23:23:20.229', 1, 2);
INSERT INTO ITEM(NOME, COMPLEMENTO, DATA_HORA_CRIACAO, CATEGORIA_ID, USUARIO_ID)
	VALUES('Saque', NULL, '2021-01-24 23:23:20.229', 1, 2);
INSERT INTO ITEM(NOME, COMPLEMENTO, DATA_HORA_CRIACAO, CATEGORIA_ID, USUARIO_ID)
	VALUES('Depósito', NULL, '2021-01-24 23:23:20.229', 1, 2);
INSERT INTO ITEM(NOME, COMPLEMENTO, DATA_HORA_CRIACAO, CATEGORIA_ID, USUARIO_ID)
	VALUES('Transferência', NULL, '2021-01-24 23:23:20.229', 1, 2);
INSERT INTO ITEM(NOME, COMPLEMENTO, DATA_HORA_CRIACAO, CATEGORIA_ID, USUARIO_ID)
	VALUES('Salário', NULL, '2021-01-24 23:23:20.229', 1, 2);

INSERT INTO TIPO_REGISTRO(NOME, EH_REGISTRO_DE_SAIDA) VALUES('Entrada', 0);
INSERT INTO TIPO_REGISTRO(NOME, EH_REGISTRO_DE_SAIDA) VALUES('Saída', 1);

INSERT INTO SALDO(SALDO, DATA_HORA_CRIACAO, USUARIO_ID) VALUES(0.0, '2021-01-24 23:23:20.229', 1);
INSERT INTO SALDO(SALDO, DATA_HORA_CRIACAO, USUARIO_ID) VALUES(0.0, '2021-01-24 23:23:20.229', 2);

INSERT INTO BANCO(CODIGO, NOME) VALUES(001, 'Banco do Brasil S.A.');
INSERT INTO BANCO(CODIGO, NOME) VALUES(033, 'Banco Santander (Brasil) S.A.');
INSERT INTO BANCO(CODIGO, NOME) VALUES(104, 'Caixa Econômica Federal');
INSERT INTO BANCO(CODIGO, NOME) VALUES(237, 'Banco Bradesco S.A.');
INSERT INTO BANCO(CODIGO, NOME) VALUES(341, 'Banco Itaú S.A.');
INSERT INTO BANCO(CODIGO, NOME) VALUES(356, 'Banco Real S.A. (antigo)');
INSERT INTO BANCO(CODIGO, NOME) VALUES(389, 'Banco Mercantil do Brasil S.A.');
INSERT INTO BANCO(CODIGO, NOME) VALUES(399, 'HSBC Bank Brasil S.A. – Banco Múltiplo');
INSERT INTO BANCO(CODIGO, NOME) VALUES(422, 'Banco Safra S.A.');
INSERT INTO BANCO(CODIGO, NOME) VALUES(453, 'Banco Rural S.A.');
INSERT INTO BANCO(CODIGO, NOME) VALUES(633, 'Banco Rendimento S.A.');
INSERT INTO BANCO(CODIGO, NOME) VALUES(652, 'Itaú Unibanco Holding S.A.');
INSERT INTO BANCO(CODIGO, NOME) VALUES(745, 'Banco Citibank S.A.');
INSERT INTO BANCO(CODIGO, NOME) VALUES(260, 'Nu Pagamentos S.A.');
INSERT INTO BANCO(CODIGO, NOME) VALUES(336, 'Banco C6 S.A');
INSERT INTO BANCO(CODIGO, NOME) VALUES(077, 'Banco Inter S.A.');

INSERT INTO TIPO_CONTA(NOME) VALUES('Conta-corrente');
INSERT INTO TIPO_CONTA(NOME) VALUES('Conta poupança');
INSERT INTO TIPO_CONTA(NOME) VALUES('Conta-salário');
INSERT INTO TIPO_CONTA(NOME) VALUES('Conta universitária');
INSERT INTO TIPO_CONTA(NOME) VALUES('Conta digital');

INSERT INTO CONTA(BANCO_ID, TIPO_CONTA_ID, USUARIO_ID) VALUES(14, 5, 2);

INSERT INTO SALDO_BANCARIO(SALDO, DATA_HORA_CRIACAO, CONTA_ID) VALUES(0.0, '2021-01-24 23:23:20.229', 1);