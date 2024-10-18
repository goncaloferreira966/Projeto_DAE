| ![Imagem Esquerda](img/ipl.png) | ![Imagem Direita](LOGO AQUI) |
|:---------------------------------------:|:---------------------------------------:|
# Projeto DAE - `Primeira Entrega`
## `API Documentation`

---

### `WeDelivery`

Esta API permite responder a todo o tipo de Pedidos necessários ao negócio da empresa WeDelivery, empresa ligada ao serviço de transporte e armazenamento de embalagens e produtos. Fornece endpoints para todas as funcionalidades da nossa Aplicação WEB.

---

#### Base URL
#### `http://Localhost:8080/wedelivery/api`

---

## Endpoints

### 1. **Autenticação**
#### `POST /auth/login`

Um User inicia sessão e recebe, para sua segurança, um token JWT.

- **URL**: `/auth/login`
- **Método**: `POST`
- **Headers**: 
  - `Content-Type`: `application/json`
- **Body**:
  ```json
  {
    "email": "user@example.com",
    "password": "password"
  }
- **Response**:
  ```json
  {
    "token": "jwt_token",
    "expires_in": 3600
  }
<br />

### 2. **Administradores**
#### `GET /administrators`

Um Administrador consulta a listagem de todos os Administradores Registados.

- **URL**: `/administrators`
- **Método**: `GET`
- **Headers**: 
  - `Content-Type`: `application/json`
- **Response**:
  ```json
  {
    {
      "email":"Dinis@gmail.com",
      "name":"Dinis Roxo",
      "password":"password",
      "username":"DinisRX"
    },
    {
      "email":"Goncalo@gmail.com",
      "name":"Goncalo Ferreira",
      "password":"password",
      "username":"GoncaloF00"
    }
  }
<br />

#### `GET /administrators/{username}`

Um Administrador consulta o perfil de um Administrador.

- **URL**: `/administrators/{username}`
- **Método**: `GET`
- **Headers**: 
  - `Content-Type`: `application/json`

- **Body**:
  ```json
  {
    "username": "DinisRX"
  }
- **Response**:
  ```json
  {
    "email":"Dinis@gmail.com",
    "name":"Dinis Roxo",
    "password":"password",
    "username":"DinisRX"
  }
<br />

### 3. **Gestores**
#### `GET /managers`

Um Administrador consulta a listagem de todos os Gestores Registados.

- **URL**: `/managers`
- **Método**: `GET`
- **Headers**: 
  - `Content-Type`: `application/json`
- **Response**:
  ```json
  {
    {
      "email":"Rui@gmail.com",
      "name":"Rui Manager",
      "password":"password",
      "username":"RuiM100"
    },
    {
      "email":"Manager@gmail.com",
      "name":"André Silva",
      "password":"password",
      "username":"AndreS10"
    }
  }
<br />

#### `GET /managers/{username}`

Um Administrador consulta o perfil de um Gestor.

- **URL**: `/managers/{username}`
- **Método**: `GET`
- **Headers**: 
  - `Content-Type`: `application/json`

- **Body**:
  ```json
  {
    "username": "Rui"
  }
- **Response**:
  ```json
  {
    "email":"Rui@gmail.com",
    "name":"Rui Manager",
    "password":"password",
    "username":"RuiM100"
  }
<br />

### 4. **Clientes**
#### `GET /clients`
Um Administrador consulta a listagem de todos os Clientes Registados.

- **URL**: `/clients`
- **Método**: `GET`
- **Headers**: 
  - `Content-Type`: `application/json`
- **Response**:
  ```json
  {
   {
      "address": "Rua das Igrejas",
      "city": "Leiria",
      "country": "PT",
      "email": "guilherme@gmail.com",
      "name": "Guilherme",
      "nif": 123123124,
      "password": "password",
      "postalCode": "2565-834",
      "username": "Guilherme"
    },
    {
      "address": "Rua das Igrejas",
      "city": "Leiria",
      "country": "PT",
      "email": "cr7@gmail.com",
      "name": "Papai Cris",
      "nif": 123123125,
      "password": "password",
      "postalCode": "2565-834",
      "username": "Cristiano"
    }
  }
<br />

#### `GET /clients/{username}`

Um Administrador consulta o perfil de um Cliente.

- **URL**: `/clients/{username}`
- **Método**: `GET`
- **Headers**: 
  - `Content-Type`: `application/json`

- **Body**:
  ```json
  {
    "username": "Goncalo"
  }
- **Response**:
  ```json
  {
    "address": "Rua das Igrejas",
    "city": "Leiria",
    "country": "PT",
    "email": "goncalo@gmail.com",
    "name": "Gonçalo",
    "nif": 123123123,
    "password": "password",
    "postalCode": "2565-834",
    "username": "Goncalo"
  }
<br />

### 5. **Encomendas**
#### `GET /orders`
Um Gestor consulta a listagem de todos as Encomendas efetuadas.

- **URL**: `/orders`
- **Método**: `GET`
- **Headers**: 
  - `Content-Type`: `application/json`
- **Response**:
  ```json
  {
   {
      "code": 1,
      "deliveryDate": "2024-10-18T13:38:32.822Z[UTC]",
      "purchaseDate": "2024-10-18T13:38:32.822Z[UTC]",
      "username": "Guilherme"
    },
    {
      "code": 2,
      "deliveryDate": "2024-10-18T13:38:32.823Z[UTC]",
      "purchaseDate": "2024-10-18T13:38:32.823Z[UTC]",
      "username": "Guilherme"
    },
    {
      "code": 3,
      "deliveryDate": "2024-10-18T13:38:32.823Z[UTC]",
      "purchaseDate": "2024-10-18T13:38:32.823Z[UTC]",
      "username": "Goncalo"
    }
  }
<br />

#### `GET /orders/{code}`

Um Gestor consulta uma Encomenda efetuada. Este método pode também ser usado por um Cliente que deseje consultar uma Encomenda efetuada por si. 

- **URL**: `/orders/{code}`
- **Método**: `GET`
- **Headers**: 
  - `Content-Type`: `application/json`

- **Body**:
  ```json
  {
    "code": "1"
  }
- **Response**:
  ```json
  {
    "code": 1,
    "deliveryDate": "2024-10-18T13:38:32.822Z[UTC]",
    "purchaseDate": "2024-10-18T13:38:32.822Z[UTC]",
    "username": "Guilherme"
  }
<br />

#### `GET /orders/client/{username}`

Um Cliente consulta todas as suas encomendas realizadas

- **URL**: `/orders/client/{username}`
- **Método**: `GET`
- **Headers**: 
  - `Content-Type`: `application/json`

- **Body**:
  ```json
  {
    "username": "Goncalo"
  }
- **Response**:
  ```json
  {
    "code": 3,
    "deliveryDate": "2024-10-18T13:38:32.823Z[UTC]",
    "purchaseDate": "2024-10-18T13:38:32.823Z[UTC]",
    "username": "Goncalo"
  }
<br />

---
### `Powered By`
#### `2222051` Gonçalo Ferreira 
#### `2223281` Guilherme Cruz   
#### `2222313` Dinis Roxo       

---

### `Cliente`
#### `Professor` João Ferreira


---
![Imagem Esquerda](img/github.png)