CREATE TABLE clientes (
	id BIGINT(20) PRIMARY KEY AUTO_INCREMENT,
	nome VARCHAR(30) NOT NULL,
	email VARCHAR(30) NOT NULL,
	senha VARCHAR(20) NOT NULL,
	documento VARCHAR(13) NOT NULL,
	data_cadastro DATE NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

INSERT INTO clientes (nome, email, senha, documento, data_cadastro) values 
('João Silva', 'joohsilva@gmail', '1001', '4891251577', '2017-06-10'),
('Lucas Abrel', 'lu.abrel@gmail', '1002', '9591221783', '2020-07-15'),
('Amanda Moraes', 'amandinha2@gmail', '1003', '2591236578', '2019-10-25'),
('Vitoria Amadeu', 'vit.amadeu@gmail', '1004', '4891251577', '2017-06-10'),
('Fernando Vieira', 'vieiraFer3@gmail', '1005', '4826251841', '2018-02-18');
