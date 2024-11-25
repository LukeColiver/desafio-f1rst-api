# Use uma imagem do OpenJDK 17 baseada em Debian slim
FROM openjdk:17-slim

# Atualize os pacotes e instale o Maven
RUN apt-get update && apt-get install -y wget && \
    wget https://archive.apache.org/dist/maven/maven-3/3.8.6/binaries/apache-maven-3.8.6-bin.tar.gz && \
    tar xzvf apache-maven-3.8.6-bin.tar.gz -C /opt && \
    ln -s /opt/apache-maven-3.8.6 /opt/maven && \
    ln -s /opt/maven/bin/mvn /usr/bin/mvn && \
    rm -f apache-maven-3.8.6-bin.tar.gz

# Defina o diretório de trabalho dentro do contêiner
WORKDIR /app

# Copie o pom.xml para o contêiner
COPY pom.xml .

# Baixe as dependências do Maven
RUN mvn dependency:go-offline

# Copie o código-fonte do projeto
COPY src /app/src

# Execute o build do Maven
RUN mvn clean install

# Exponha a porta que a aplicação Spring Boot vai usar
EXPOSE 8080

# Comando para iniciar o Spring Boot
CMD ["mvn", "spring-boot:run"]
