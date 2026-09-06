# 🚀 API Spring Boot — Serviços com RabbitMQ & Mailtrap

Uma API RESTful desenvolvida com **Java e Spring Boot**, utilizando **PostgreSQL** para persistência de dados, **RabbitMQ** para mensageria assíncrona (gerenciamento de filas) e **Mailtrap** para envio de e-mails/códigos de validação. A aplicação está totalmente configurada para ser executada localmente via Docker ou em nuvem na plataforma **Railway**.

---

## 📌 Sumário
- [Arquitetura & Tecnologias](#-arquitetura--tecnologias)
- [Funcionalidades Principais](#-funcionalidades-principais)
- [Pré-requisitos](#-pré-requisitos)
- [Configuração do Ambiente e Variáveis](#-configuração-do-ambiente-e-variáveis)
- [Como Rodar Localmente (Docker)](#-como-rodar-localmente-docker)
- [Executando a Aplicação](#-executando-a-aplicação)
- [Estrutura da API & Endpoints](#-estrutura-da-api--endpoints)
- [Deploy no Railway](#-deploy-no-railway)

---

## 🛠 Arquitetura & Tecnologias

O projeto utiliza as seguintes tecnologias e frameworks:

* **Java 17+** / **Spring Boot 3.x**
* **Spring Data JPA / Hibernate**: Persistência e manipulação do banco de dados.
* **PostgreSQL**: Banco de dados relacional.
* **Spring AMQP & RabbitMQ**: Processamento assíncrono de eventos e filas de mensagens.
* **Spring Mail & Mailtrap**: Disparo de e-mails e envio de códigos de validação.
* **Docker & Docker Compose**: Containerização do ecossistema de microsserviços/dependências.
* **Railway**: Hospedagem em nuvem dos serviços e banco de dados.

---

## ⚙️ Funcionalidades Principais

* **Gestão e Processamento de Requisições**: Endpoints REST otimizados com suporte a buscas parametrizadas e rotas dinâmicas (`@PathVariable`).
* **Mensageria com RabbitMQ**: Envio e consumo de mensagens em filas para desacoplamento de serviços e processamento assíncrono.
* **Envio de E-mails**: Notificações automáticas e envio de códigos de validação via SMTP/Mailtrap.
* **Integração com Banco Relacional**: Criação e atualização automática dos schemas via Hibernate DDL.

---

## 📋 Pré-requisitos

Para rodar este projeto na sua máquina, você precisará de:

* [JDK 17+](https://www.oracle.com/java/technologies/downloads/) instalada.
* [Maven](https://maven.apache.org/) instalado (ou usar o `mvnw` incluído no projeto).
* [Docker Desktop](https://www.docker.com/products/docker-desktop/) rodando para subir as dependências (PostgreSQL e RabbitMQ).
* Uma conta no [Mailtrap](https://mailtrap.io/) para obter as credenciais de SMTP.
* Um cliente HTTP para testes (como **Insomnia** ou **Postman**).

---

## 🔑 Configuração do Ambiente e Variáveis

A aplicação utiliza variáveis de ambiente para dinamizar o suporte entre o ambiente local e a produção.

## ☁️ Deploy no Railway

* A aplicação está configurada para deploy simplificado na plataforma Railway.

📄 Licença
Este projeto está sob a licença MIT. Veja o arquivo LICENSE para mais detalhes.