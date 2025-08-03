# Resolução 03 - : Gerenciamento de Autores e Obras

## Swagger

http://localhost:8080/swagger-ui/index.html#/

## Entrada e saída de dados

- JSON de entrada, LoginRequestDTO.

```json
{
    "username": "admin",
    "password": "12345"
}
```
- JSON de entrada, AutorRequestDTO.

```json
{ 
    "nome": "Franz Kafka",
    "sexo": "Masculino",
    "email": "franz@kafka.com",
    "dataNascimento": "03/07/1883",
    "pais": "Áustria",
    "cpf": "",
    "obraEntityList": [
        {
            "nome": "A Metamorfose",
            "descricao": "A Metamorfose é uma novela escrita por Franz Kafka, publicada pela primeira vez em 1915. Veio a ser o texto mais conhecido, estudado e citado da obra de Kafka.",
            "dataPublicacao": "01/10/1915"
        }
    ]
}
```

- JSON de entrada, ObraRequestDTO.

```json
{
    "nome": "Romeu e Julieta",
    "descricao": "Romeu e Julieta é uma tragédia de William Shakespeare que narra a história de amor proibido entre dois jovens de famílias rivais, os Montecchio e os Capuleto, em Verona",
    "dataPublicacao": "01/01/1597"
}
```
- JSON de saída, LoginResponseDTO (admin).
```json
{
    "id": 1,
    "username": "admin",
    "password": "$2a$10$bXNOEQQa.nkUSgr5Y10ZjOeZNJOfpjM5wBGVHT3X2kjUX2tNDu6u.",
    "roles": [
        "WORKER",
        "ADMIN"
    ]
}
```

JSON de saída, LoginResponseDTO (worker).

```json
{
    "id": 1,
    "username": "worker",
    "password": "$2a$10$tVjQHI7MVgLWtanh6z78gu8g7HBxoL0MjFcYQPJ5N.GARuSPmgUPm",
    "roles": [
        "WORKER"
    ]
}
```
JSON de saída, AutorResponseDTO.

```json
{
    "id": 1,
    "nome": "Franz Kafka",
    "sexo": "Masculino",
    "email": "franz@kafka.com",
    "dataNascimento": "1883-07-03T00:00:00.000+00:00",
    "pais": "Áustria",
    "cpf": "",
    "obraEntityList": [
        {
            "id": 1,
            "nome": "A Metamorfose",
            "descricao": "A Metamorfose é uma novela escrita por Franz Kafka, publicada pela primeira vez em 1915. Veio a ser o texto mais conhecido, estudado e citado da obra de Kafka.",
            "dataPublicacao": "1915-10-01T00:00:00.000+00:00"
        }
    ]
}
```
JSON de saída, ObraResponseDTO.

```json
{
    "id": 1,
    "nome": "Romeu e Julieta",
    "descricao": "Romeu e Julieta é uma tragédia de William Shakespeare que narra a história de amor proibido entre dois jovens de famílias rivais, os Montecchio e os Capuleto, em Verona",
    "dataPublicacao": "1597-01-01T00:00:00.000+00:00"
}
```
## Endpoints da aplicação 

### Autenticação

| Método | URL | Descrição | Autenticação | Autoridade
|---|---|---|---|---|
| `POST` | localhost:8080/login/create/admin | Cria um usuário com a autoridade de ADMIN e WORKER, recebendo em seu body de requisição um LoginRequestDTO. A requisição pode retornar um status code 400 caso o nome de usuário já esteja registrado. Em caso de sucesso, a requisição retorna um LoginResponseDTO (admin). | não | nenhuma
| `POST` | localhost:8080/login/create/worker | Cria um usuário com a autoridade de WORKER, recebendo em seu body de requisição um LoginRequestDTO. A requisição pode retornar um status code 400 caso o nome de usuário já esteja registrado. Em caso de sucesso, a requisição retorna um LoginResponseDTO (WORKER). | não | nenhuma
| `POST` | localhost:8080/login | Realiza a autenticação do usuário, recebendo em seu body de requisição um LoginRequestDTO. A requisição pode retornar um status code 401 caso as credenciais sejam inválidas. Em caso de sucesso, a requisição retorna um bearer token. | não | nenhuma

- Exceções de autenticação
  
| Código | Causa |
|---|---|
| 401 | Credenciais inválidas 
| 401 | "Falha na autenticação" 
| 403 | "Acesso negado!, Você não tem permissão para acessar a rota '/author/delete/1' com suas autoridades: '[WORKER]' " 


### Obra literária

| Método | URL | Descrição | Autenticação | Autoridade
|---|---|---|---|---|
| `POST` | localhost:8080/author/works/create | Realiza o cadastro de uma obra literária, recebendo em seu body de requisição um ObraRequestDTO. A requisição pode retornar um status code 400 caso o usuário informe valores inválidos.  Em caso de sucesso, a requisição retorna um status code 200 e um body de ObraResponseDTO. | sim | WORKER, ADMIN
| `GET` | localhost:8080/author/works/page/all |  Realiza a busca de todas as obras registradas com paginação, para informar a página e quantos elementos são exibidos, o endpoint recebe dois request param: int page e int size. A requisição pode retornar um status code 500 caso o servidor esteja com problemas. Em caso de sucesso, a requisição retorna um status code 200 e uma lista de ObraResponseDTO | sim | WORKER, ADMIN
| `GET` | localhost:8080/author/works/name | Realiza a busca de obras literária com base no nome do autor informado via request param(author="author"). A requisição pode retornar um status code 404 caso o nome do autor não seja encontrado. Em caso de sucesso, a requisição retorna um status code 200 e uma lista de ObraResponseDTO. | sim | WORKER, ADMIN
| `PUT` | localhost:8080/author/works/update/{id} | Realiza a atualização de obras literárias através do seu id informado via path variable e um body de ObraRequestDTO. A requisição pode retornar um status code 404 caso o id não seja encontrado. Em caso de sucesso, a requisição retorna um status code 200 e um body de ObraResponseDTO atualizado. | sim | WORKER, ADMIN
| `DELETE` | localhost:8080/author/works/delete/{id} | Realiza a deleção de obras literárias através do seu id informado via path variable. A requisição pode retornar um status code 404 caso o id não seja encontrado. Em caso de sucesso, a requisição não retorna nenhum valor e um status code 200. | sim | WORKER, ADMIN

### Autor

| Método | URL | Descrição | Autenticação | Autoridade
|---|---|---|---|---|
| `POST` | localhost:8080/author/create | Realiza o cadastro de um autor, recebendo em seu body de requisição um AutorRequestDTO. A requisição pode retornar um status code 400 caso o usuário informe campos inválidos na requisição ou se o usuário não informar um CPF válido caso o autor seja brasileiro. Em caso de sucesso, a requisição retorna um status code 200 e um AutorResponseDTO. | sim | ADMIN
| `GET` | localhost:8080/author/find/name | Realiza a busca de um autor com base em seu nome informado via request param(name="name"). A requisição pode retornar um status code 404 caso o nome do autor não seja encontrado. Em caso de sucesso, a requisição retorna um status code 200 e um body de AutorResponseDTO. | sim | WORKER, ADMIN
| `GET` | localhost:8080/author/find/all/page | Realiza a busca de todos os autores registrados com paginação, para informar a página e quantos elementos são exibidos, o endpoint recebe dois request param: int page e int size. A requisição pode retornar um status code 500 caso o servidor esteja com problemas. Em caso de sucesso, a requisição retorna um status code 200 e uma lista de AutorResponseDTO | sim | WORKER, ADMIN
| `GET` | localhost:8080/author/find/all | Realiza a busca de todos os autores registrados. A requisição pode retornar um status code 500 caso o servidor esteja com problemas. Em caso de sucesso, a requisição retorna um status code 200 e uma lista de AutorResponseDTO. | sim | WORKER, ADMIN
| `PUT` | localhost:8080/author/update/{id} | Realiza a atualização de autores através do seu id informado via path variable e um body de AutorRequestDTO. A requisição pode retornar um status code 404 caso o id não seja encontrado e um status code 400 caso o CPF informado esteja vazio, ou seja, inválido. Em caso de sucesso, a requisição retorna um status code 200 e um body de AutorResponseDTO atualizado. | sim | ADMIN
| `DELETE` | localhost:8080/author/delete/{id} | Realiza a deleção de autores através do seu id informado via path variable. A requisição pode retornar um status code 404 caso o id não seja encontrado. Em caso de sucesso, a requisição não retorna nenhum valor e um status code 200. | sim | ADMIN


# Docker

- Para rodar o projeto é preciso criar a rede: "minha-rede" ou alterar o docker-compose.yml
  
- docker-compose para baixar o projeto do docker hub: https://hub.docker.com/r/math012i/autores-obras-app/tags

```yaml
services:
  db:
    image: postgres:latest
    container_name: db
    environment:
      - POSTGRES_DB=db_obras
      - POSTGRES_USER=postgres
      - POSTGRES_PASSWORD=1234
    ports:
      - "5432:5432"
    volumes:
      - postgres_data:/var/lib/postgresql/data
    networks:
      - minha-rede
    healthcheck:
      test: [ "CMD-SHELL", "pg_isready -U postgres" ]
      interval: 5s
      timeout: 5s
      retries: 10

  autores-obras:
    image: math012i/autores-obras-app:latest
    ports:
      - "8080:8080"
    environment:
      - DB_DATABASE=db_obras
      - DB_USERNAME=postgres
      - DB_PASSWORD=1234
      - SPRING_DATASOURCE_URL=jdbc:postgresql://db:5432/
      - SPRING_JPA_HIBERNATE_DDL_AUTO=update
      - SECRET_KEY=9o272zB2IsEwv6gprXKVvCJM483PPCp0TI6p3Mf3jmMRBOfoWPrgxsvAdhhcM0Wb
    depends_on:
      db:
        condition: service_healthy
    networks:
      - minha-rede
volumes:
  postgres_data:

networks:
  minha-rede:
    external: true
```
