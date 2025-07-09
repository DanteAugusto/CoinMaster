# CoinMaster

Linguagem de Programação e Stack de desenvolvimento: Java e Spring

## Integrantes

Dante Augusto Bezerra Pinto [(DanteAugusto)](https://github.com/DanteAugusto)

Paulo Vitor Lima Borges [(PauloVLB)](https://github.com/PauloVLB)

## Instruções para execução do projeto

### Pré-requisitos
- Java 21
- Maven 3.8.6

1. **Clone o repositório**:
   ```bash
   git clone https://github.com/DanteAugusto/CoinMaster
   ```
2. **Acesse o diretório do projeto**:
   ```bash
    cd CoinMaster/coinmaster
    ```
3. **Execute com maven**:
    ```bash
    mvn spring-boot:run
    ```

4. **Acesse a aplicação**:
    - Abra seu cliente de API favorito (Postman, Insomnia, etc.) e faça requisições para os endpoints disponíveis.

## Como rodar a aplicação usando Docker
```bash
docker pull paulovlb/coinmaster:latest

docker run -p 8080:8080 paulovlb/coinmaster:latest
```
Acesse: http://localhost:8080

[Ver imagem no Docker Hub](https://hub.docker.com/r/paulovlb/coinmaster)
