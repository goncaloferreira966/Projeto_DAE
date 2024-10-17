# Projeto DAE - `Primeira Entrega`
## API Documentation

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

Autentica um User e retorna um token JWT.

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
  
---
### `Powered By`
#### Gonçalo Ferreira `2222051`
#### Guilherme Cruz   `2223281`
#### Dinis Roxo       `2222313`

---

### `Cliente`
#### Professor João Ferreira


---
