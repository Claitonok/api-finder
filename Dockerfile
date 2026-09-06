# Antes de criar a imagem docker!!

# RODAR ESSE COMANDO NA RAIS DO SEU PROJETO 
# No powershell

# .\mvnw.cmd package -DskipTests
# Esse comando cria a Arquivo jar do seu projeto ou atualiza!!

# http://localhost:8080/swagger-ui/index.html


# ------------------------------------------------#
# CRIAR IMAGEM DO PROJETO
# docker build -t project-nome .  
# ------------------------------------------------#
# RODAR O CONTEINER DO PROJETO
# docker run -d -p 8080:8080 --name conteiner-nome project-nome
# ------------------------------------------------#

# Runtime
# FROM eclipse-temurin:21-jre-alpine

# WORKDIR /app

# COPY target/*.jar finderSystem.jar
# EXPOSE 8080

# ENTRYPOINT ["java","-jar","finderSystem.jar"]




#Deploy

FROM maven:3.9.6-eclipse-temurin-21 AS build
WORKDIR /app

# # Primeiro copia apenas o pom.xml para baixar as dependências (faz cache)
COPY pom.xml .
RUN mvn dependency:go-offline

# # AGORA copia a pasta src inteira para dentro do container
COPY src ./src

# # Build do projeto
RUN mvn clean package -DskipTests

# # Estágio final (Execução)
FROM eclipse-temurin:21-jre
WORKDIR /app
COPY --from=build /app/target/*.jar app.jar

# EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]