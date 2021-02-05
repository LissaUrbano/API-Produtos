CREATE TABLE fornecedores (
	id BIGINT(20) PRIMARY KEY AUTO_INCREMENT,
	nome VARCHAR(30) NOT NULL,
	cnpj VARCHAR(16) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

INSERT INTO fornecedores (nome, cnpj) values 
('HAVAN', '15896324585746'),
('CASAS BAHIA', '86996324526584'),
('MAGAZINE LUIZA', '56896844225787'),
('ZOOM', '88496324365777'),
('KABUM', '82896144585964');