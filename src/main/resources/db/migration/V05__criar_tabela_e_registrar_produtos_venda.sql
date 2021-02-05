CREATE TABLE produtos_venda (
	id_venda BIGINT(20) NOT NULL,
	id_produto BIGINT(20) NOT NULL,
	FOREIGN KEY (id_venda) REFERENCES vendas(id),
	FOREIGN KEY (id_produto) REFERENCES produtos(id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

INSERT INTO produtos_venda (id_venda, id_produto) values 
( 1, 2),
( 2, 5),
( 3, 3),
( 3, 6);