CREATE TABLE Aluno (
codigo INT PRIMARY KEY NOT NULL,
nome varchar(255) NOT NULL,
email VARCHAR(255) NOT NULL,
senha char(60) NOT NULL,
prontuario VARCHAR(255) NOT NULL,
curso VARCHAR(255) NOT NULL,
telefone VARCHAR(255) NOT NULL
);

CREATE TABLE Orientador (
codigo INT PRIMARY KEY NOT NULL,
nome VARCHAR(255) NOT NULL,
email VARCHAR(255) NOT NULL,
senha char(60) NOT NULL,
prontuario VARCHAR(11)
);

CREATE TABLE Coordenador (
codigo INT PRIMARY KEY NOT NULL,
nome VARCHAR(255) NOT NULL,
email VARCHAR(255) NOT NULL,
senha char(60) NOT NULL,
prontuario VARCHAR(11)
);

CREATE TABLE Estagio (
codigo INT PRIMARY KEY NOT NULL,
obrigatorio BOOLEAN NOT NULL,
carga_diaria INT NOT NULL,
data_inicio DATE NOT NULL,
data_termino DATE NOT NULL,
status VARCHAR(255) NOT NULL,  
codigo_aluno INT NOT NULL,
codigo_professor INT NOT NULL,
codigo_coordenador INT NOT NULL,
FOREIGN KEY (codigo_aluno) REFERENCES Aluno(codigo),
FOREIGN KEY (codigo_coordenador) REFERENCES Coordenador(codigo)
);

CREATE TABLE Alerta (
codigo INT PRIMARY KEY NOT NULL,
tipo VARCHAR(255) NOT NULL,
mensagem VARCHAR(255) NOT NULL,
vencimento TIMESTAMP NOT NULL,
codigo_estagio INT NOT NULL,
FOREIGN KEY (codigo_estagio) REFERENCES Estagio(codigo)
);

CREATE TABLE Empresa (
codigo INT PRIMARY KEY NOT NULL,
nome_fantasia VARCHAR(255) NOT NULL,
razao_social VARCHAR(255) NOT NULL,
cnpj VARCHAR(15) NOT NULL,
email VARCHAR(255) NOT NULL,
telefone VARCHAR(15) NOT NULL,
codigo_estagio INT NOT NULL,
FOREIGN KEY (codigo_estagio) REFERENCES Estagio(codigo)
);

CREATE TABLE Endereco (
codigo INT PRIMARY KEY NOT NULL,
pais VARCHAR(255) NOT NULL,
estado VARCHAR(255) NOT NULL,
cidade VARCHAR(255) NOT NULL,
bairro VARCHAR(255) NOT NULL,
logradouro VARCHAR(255) NOT NULL,
numero VARCHAR(10) NOT NULL,
cep VARCHAR(9) NOT NULL,
codigo_empresa INT NOT NULL,
FOREIGN KEY (codigo_empresa) REFERENCES Empresa(codigo)
);
