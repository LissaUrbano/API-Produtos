CREATE TABLE produtos (
	id BIGINT(20) PRIMARY KEY AUTO_INCREMENT,
	nome VARCHAR(50) NOT NULL,
	codigo_produto VARCHAR(30) NOT NULL,
	valor DECIMAL(30) NOT NULL,
	promocao BOOLEAN NOT NULL,
	valor_promo DECIMAL(30),
	categoria VARCHAR(30) NOT NULL,
	imagem VARCHAR(30),
	quantidade BIGINT(20) NOT NULL,
	id_fornecedor  BIGINT(20) NOT NULL,
	FOREIGN KEY (id_fornecedor) REFERENCES fornecedores(id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

INSERT INTO produtos (nome, codigo_produto, valor, promocao, valor_promo, categoria, imagem, quantidade, id_fornecedor) values 
('Laptop Dell', 'ABC010101', 3500.90, 1, 2999.99, 'eletronico', 'laptopDell.jpg', 1000, 5),
('Mouse', 'ABC010102', 189.90, 1, 145.00, 'acessórios', 'mouse.jpg', 800, 1),
('Celular Android', 'ABC010103', 2500.00, 1, 1899.99, 'telefonia', 'celular.jpg', 500, 3),
('teclado', 'ABC010104', 209.00, 0, null, 'acessórios', 'teclado.jpg', 200, 4),
('Mouse Pad', 'ABC010105', 249.00, 0, null, 'acessórios', 'mousepad.jpg', 300, 2),
('cabo', 'AS15', 50.00, 1, 49.99, 'cabo', 'cabo.jpg', 100, 3);