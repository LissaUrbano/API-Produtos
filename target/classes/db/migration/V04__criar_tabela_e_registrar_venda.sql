CREATE TABLE vendas (
	id BIGINT(20) PRIMARY KEY AUTO_INCREMENT,
	id_cliente BIGINT(20) NOT NULL,
	data_compra DATE NOT NULL,
	total_compra DECIMAL(30) NOT NULL,
	id_fornecedor BIGINT(20) NOT NULL,
	FOREIGN KEY (id_cliente) REFERENCES clientes(id),
	FOREIGN KEY (id_fornecedor) REFERENCES fornecedores(id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

INSERT INTO vendas (id_cliente, data_compra, total_compra, id_fornecedor) values ( 
	3,
	'2020-12-01', 
	603.00,
	1
),
(
	1,
	'2020-12-01', 
	2999.99,
	2
),
(
	4,
	'2020-12-01', 
	5148.98,
	3
);
