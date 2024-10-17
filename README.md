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
<br />

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
<br />

### 2. **Administradores**
<br />

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

- **URL**: `/administrators`
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

- **URL**: `/managers`
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
<br />

---
### `Powered By`
#### `2222051` Gonçalo Ferreira 
#### `2223281` Guilherme Cruz   
#### `2222313` Dinis Roxo       

---

### `Cliente`
#### Professor João Ferreira


---
![Imagem Esquerda](img/github.png)