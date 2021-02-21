# Projeto: Voto eletrônico - Microservices com Spring, Java e Android

Objetivos:
* Resolver o problema proposto descrito neste documento: https://docs.google.com/document/d/1d_37ad31UitsoQAjgzWmOIP7epdXIadxEU251O_uZF0
* Aplicar os meus conhecimentos adquiridos durante a minha carreira profissional e conhecimentos mais recentes da arquitetura de microserviços com Java e Spring
* Ir um pouco além da simples resolução do problema proposto, para demonstrar meus conhecimentos técnicos a respeito das ferramentas Spring para aplicação de um cenário mais completo do uso dos microserviços na prática do dia a dia

# Documentação técnica

*Disponível* aqui: https://docs.google.com/document/d/1Kv4q6g8wr-M_GVvyE1oFSk3Y7SK-HPkSRDKIkoPUXmY

## Configuração do Postman

    Importe o arquivo: "./postman/microservices-spring-java.postman_collection.json"

## Procedimento para ligar o servidor de banco de dados

    No terminal do sistema operacional, entre no diretório "docker" e digite o comando: "./up-dev-mysql.sh".

## Procedimento para desligar o servidor de banco de dados

    No terminal do sistema operacional, entre no diretório "docker" e digite o comando: "./down-environment.sh".

## Acesso Swagger > via Gateway

  * http://localhost:8080/swagger-ui/index.html
  * http://localhost:8080/v2/api-docs
