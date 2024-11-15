# assessment
Este projeto implementa um sistema de microserviços usando Spring Boot, REST, gRPC, e banco de dados PostgreSQL, com suporte a Gradle como sistema de build e Docker para containerização. A orquestração dos serviços é feita com Minikube para ambientes Kubernetes.

# Visão Geral
O servicoa é o serviço com endpoint em REST. Para rodá-lo é necessário ter instalado o Java 21, o banco PostgreSQL e o Docker para a conteinerização. Esse serviço está organizado com um controller, um repository e um model para a Informação.

O projeto consiste em três serviços principais:

Serviço REST: Disponibiliza informações por meio de endpoints REST e realiza persistência no PostgreSQL.
Serviço gRPC: Fornece endpoints gRPC para comunicação e também realiza persistência no PostgreSQL.
Serviço Consumidor: Consome os dois serviços anteriores e retorna uma resposta combinada ao usuário.

# Pré Requisitos
Antes de executar o projeto, certifique-se de ter as ferramentas a seguir instaladas:

Java 21
Gradle
Docker
Minikube
PostgreSQL
tomcat

Para testar localmente:
Postman
grpcurl

# Configuração do Banco de Dados
Crie dois bancos no PostgreSQL para os serviços REST e gRPC. Substitua as credenciais no application.properties de cada serviço.

# Execução
Agora as aplicações estão prontas para ser executadas. Execute ./gradlew bootRun no diretório de cada service. Certifique-se de que as ports necessárias nos arquivos properties estão disponíveis. Agora você pode usar o postman e o grpcurl para fazer requisições e ver as informações.
