| ![Imagem Esquerda](img/ipl.png) | ![Imagem Direita](img/encomenda.jpg) |
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
  ```
- **Response**:
  ```json
  {
    "token": "jwt_token",
    "expires_in": 3600
  }
  ```
<br />

### 2. **Operadores**
#### `GET /operators`
Um Administrador consulta a listagem de todos os Operadores Registados.

- **URL**: `/operators`
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
  ```
<br />

#### `GET /operators/{username}`

Um Administrador consulta o perfil de um Operador.

- **URL**: `/operators/{username}`
- **Método**: `GET`
- **Headers**: 
  - `Content-Type`: `application/json`

- **Body**:
  ```json
  {
    "username": "DinisRX"
  }
  ```
- **Response**:
  ```json
  {
    "email":"Dinis@gmail.com",
    "name":"Dinis Roxo",
    "password":"password",
    "username":"DinisRX"
  }
  ```
<br />

#### `POST /operators`
Um Operador efetua o registo na plataforma do sistema.

- **URL**: `/operators`
- **Método**: `POST`
- **Headers**: 
  - `Content-Type`: `application/json`
- **Body**:
  ```json
  {
    "email": "ana@mail.com",
    "name": "Ana",
    "username": "Ana",
    "password": "password"
  }
  ```
- **Response**:
  ```json
  {
    "email": "ana@mail.com",
    "name": "Ana",
    "password": "password",
    "username": "Ana"
  }
  ```

### 3. **managers**
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
  ```
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
  ```
- **Response**:
  ```json
  {
    "email":"Rui@gmail.com",
    "name":"Rui Manager",
    "password":"password",
    "username":"RuiM100"
  }
  ```
<br />

#### `POST /managers`
Um Gestor efetua o registo na plataforma do sistema.

- **URL**: `/managers`
- **Método**: `POST`
- **Headers**: 
  - `Content-Type`: `application/json`
- **Body**:
  ```json
  {
    "email": "margarida@mail.com",
    "name": "Margarida",
    "username": "Margarida",
    "password": "password"
  }
  ```
- **Response**:
  ```json
  {
    "email": "margarida@mail.com",
    "name": "Margarida",
    "password": "password",
    "username": "Margarida"
  }
  ```
<br />

#### `GET /managers/{username}/orders`

Um Gestor consulta todas as encomendas a si associadas.

- **URL**: `/managers/{username}/orders`
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
    "code": 3,
    "deliveryDate": "2024-10-18T13:38:32.823Z[UTC]",
    "purchaseDate": "2024-10-18T13:38:32.823Z[UTC]",
    "username": "Goncalo",
    "state": "In Distribution",
    "usernameOperator": "DinisRX"
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
  ```
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
  ```
- **Response**:
  ```json
  {
    "address": "Rua das Igrejas",
    "city": "Leiria",
    "country": "PT",
    "email": "goncalo@gmail.com",
    "name": "Gonçalo",
    "nif": 123123123,
    "orders": [
        {
            "code": 1372122800,
            "deliveryDate": "2024-10-22T15:47:16.764Z[UTC]",
            "purchaseDate": "2024-10-22T15:47:16.764Z[UTC]",
            "state": "Shipped",
            "username": "Goncalo",
            "usernameOperator": "GoncaloF00"
        },
        {
            "code": 1413200289,
            "deliveryDate": "2024-10-22T15:47:16.764Z[UTC]",
            "purchaseDate": "2024-10-22T15:47:16.764Z[UTC]",
            "state": "Delivered",
            "username": "Goncalo",
            "usernameOperator": "DinisRX"
        }
    ],
    "password": "password",
    "postalCode": "2565-834",
    "username": "Goncalo"
  }
  ```
<br />

#### `POST /clients`
Um Cliente efetua o registo na plataforma do sistema.

- **URL**: `/clients`
- **Método**: `POST`
- **Headers**: 
  - `Content-Type`: `application/json`
- **Body**:
  ```json
  {
    "username": "john",
    "email": "johndoe@mail.com",
    "password": "jd",
    "name": "John Doe",
    "courseCode": 1,
    "nif": 444555666,
    "postalCode": "2565-834",
    "country": "PT",
    "city": "Leiria",
    "address": "Rua x"
  }
  ```
- **Response**:
  ```json
  {
    "address": "Rua x",
    "city": "Leiria",
    "country": "PT",
    "email": "johndoe@mail.com",
    "name": "John Doe",
    "nif": 444555666,
    "password": "jd",
    "postalCode": "2565-834",
    "username": "john"
  }
  ```
<br />

#### `GET /clients/{username}/orders`

Um Cliente consulta todas as suas encomendas realizadas

- **URL**: `/clients/{username}/orders`
- **Método**: `GET`
- **Headers**: 
  - `Content-Type`: `application/json`

- **Body**:
  ```json
  {
    "username": "Goncalo"
  }
  ```
- **Response**:
  ```json
  {
    "code": 3,
    "deliveryDate": "2024-10-18T13:38:32.823Z[UTC]",
    "purchaseDate": "2024-10-18T13:38:32.823Z[UTC]",
    "username": "Goncalo",
    "state": "In Distribution",
    "usernameOperator": "DinisRX"
  }
  ```
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
      "username": "Guilherme",
      "state": "In Distribution",
      "usernameOperator": "DinisRX"
    },
    {
      "code": 2,
      "deliveryDate": "2024-10-18T13:38:32.823Z[UTC]",
      "purchaseDate": "2024-10-18T13:38:32.823Z[UTC]",
      "username": "Guilherme",
      "state": "In Distribution",
      "usernameOperator": "DinisRX"
    },
    {
      "code": 3,
      "deliveryDate": "2024-10-18T13:38:32.823Z[UTC]",
      "purchaseDate": "2024-10-18T13:38:32.823Z[UTC]",
      "username": "Goncalo",
      "state": "In Distribution",
      "usernameOperator": "DinisRX"
    }
  }
  ```
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
    "code": 1258464486
  }
  ```
- **Response**:
  ```json
  {
    "code": 1258464486,
    "deliveryDate": "2024-10-24T23:16:00.949Z[UTC]",
    "purchaseDate": "2024-10-24T23:16:00.949Z[UTC]",
    "state": "Pending",
    "username": "Guilherme",
    "usernameOperator": "DinisRX",
    "volumes": [
      {
        "creationDate": "2024-10-24 23:16:00.991",
        "id": 1494305280,
        "state": "Minor damage"
      }
    ]
  }
  ```
<br />

#### `GET /orders/{code}/volume/{id}`

Um Gestor consulta uma Encomenda efetuada e quere verificar um volume. Este método pode também ser usado por um Cliente que deseje consultar uma Encomenda efetuada por si. 

- **URL**: `/orders/{code}/volume/{id}`
- **Método**: `GET`
- **Headers**: 
  - `Content-Type`: `application/json`

- **Body**:
  ```json
  {
    "creationDate": "2024-10-24 23:16:00.991",
    "id": 1494305280,
    "products": [
      {
        "available": true,
        "description": "Sprite",
        "haveSensor": false,
        "id": 3,
        "image": "sprite.jpg",
        "name": "Sprite",
        "price": 1.5,
        "quantity": 100
      },
      {
        "available": true,
        "description": "Fanta",
        "haveSensor": false,
        "id": 4,
        "image": "fanta.jpg",
        "name": "Fanta",
        "price": 1.5,
        "quantity": 100
      }
    ],
    "sensors": [
      {
        "busy": false,
        "currentValue": 20,
        "expedition": false,
        "id": 3,
        "type": "Temperature"
      },
      {
        "busy": false,
        "currentValue": 20,
        "expedition": false,
        "id": 23,
        "type": "humidity"
      }
    ],
    "state": "Minor damage"
  }
  ```
<br />

#### `POST /orders`
Um Cliente efetua uma Encomenda e a mesma é criada em sistema.

- **URL**: `/orders`
- **Método**: `POST`
- **Headers**: 
  - `Content-Type`: `application/json`
- **Body**:
  ```json
  {
    "code": 1930548778,
    "deliveryDate": "2024-10-20T23:02:22.383Z[UTC]",
    "purchaseDate": "2024-10-20T23:02:22.383Z[UTC]",
    "state": "Pending",
    "username": "Guilherme",
    "usernameOperator": "DinisRX"
  }
  ```
- **Response**:
  ```json
  {
    "code": 1930548778,
    "deliveryDate": "2024-10-20T23:02:22.383Z[UTC]",
    "purchaseDate": "2024-10-20T23:02:22.383Z[UTC]",
    "state": "Pending",
    "username": "Guilherme",
    "usernameOperator": "DinisRX"
  }
  ```
<br />

### 6. **Products**

#### `Get /products`
Um utilizador consulta todos os produtos na página de e-commerce.

- **URL**: `/products`
- **Método**: `GET`
- **Headers**: 
  - `Content-Type`: `application/json`
- **Response**:
  ```json
  [
    {
      "available": true,
      "description": "Coca-Cola",
      "haveSensor": false,
      "id": 1,
      "image": "coca-cola.jpg",
      "name": "Coca-Cola",
      "price": 1.5,
      "quantity": 0,
      "warehouseName": "Leiria"
    },
    ...,
    {
      "available": true,
      "description": "Sumol",
      "haveSensor": false,
      "id": 6,
      "image": "sumol.jpg",
      "name": "Sumol",
      "price": 1.5,
      "quantity": 100,
      "warehouseName": "Leiria"
    }
  ]
  ```
<br/>

#### `Get /products/{id}`

Um utilizador consulta um produto especifico.

- **URL**: `/products/{id}`
- **Método**: `GET`
- **Headers**: 
  - `Content-Type`: `application/json`

- **Body**:
  ```json
  {
    "id": "2"
  }
  ```
- **Response**:
  ```json
  {
    "available": true,
    "description": "Pepsi",
    "haveSensor": false,
    "id": 2,
    "image": "pepsi.jpg",
    "name": "Pepsi",
    "price": 1.5,
    "quantity": 2,
    "warehouseName": "Leiria"
  }
  ```
<br />

#### `Get /products/{name}`
Um utilizador consulta todos os produtos que contenham a palavra {name}.
- **URL**: `/products/{name}`
- **Método**: `GET`
- **Headers**: 
  - `Content-Type`: `application/json`
- **Body**:
  ```json
  {
    "name": "S"
  }
  ```
- **Response**:
  ```json
  [
    {
      "available": true,
      "description": "Sprite",
      "haveSensor": false,
      "id": 3,
      "image": "sprite.jpg",
      "name": "Sprite",
      "price": 1.5,
      "quantity": 100,
      "warehouseName": "Leiria"
    },
    ...,
    {
      "available": true,
      "description": "Sumol",
      "haveSensor": false,
      "id": 6,
      "image": "sumol.jpg",
      "name": "Sumol",
      "price": 1.5,
      "quantity": 100,
      "warehouseName": "Leiria"
    }
  ]
  ```
<br />

#### `GET /products/{id}/details`
Um utilizador consulta os detalhes de um repectivo produto.
- **URL**: `/products/{id}/details`
- **Método**: `GET`
- **Headers**: 
  - `Content-Type`: `application/json`
- **Body**:
  ```json
  {
    "id": "1"
  }
  ```
- **Response**
  ```json
  {
  "available": true,
  "description": "Coca-Cola",
  "haveSensor": false,
  "id": 1,
  "image": "coca-cola.jpg",
  "name": "Coca-Cola",
  "price": 1.5,
  "quantity": 0,
  "supplier": {
      "email": "safari@mail.com",
      "name": "Safari, LDA",
      "password": "qwerty",
      "username": "safari"
    },
    "warehouse": {
      "address": "Rua das Igrejas",
      "city": "Leiria",
      "name": "Leiria",
      "postalCode": "2565-834"
    }
  }
  ```
<br />

### 7. **Warehouses**
#### `Get /warehouses`
Um utilizador consulta os warehouses existentes.
- **URL**: `/warehouses`
- **Método**: `GET`
- **Headers**: 
  - `Content-Type`: `application/json`
- **Response**:
  ```json
  [
    {
      "address": "Rua das Igrejas",
      "city": "Leiria",
      "name": "Leiria",
      "postalCode": "2565-834"
    },
    ...,
    {
      "address": "Rua das Igrejas",
      "city": "Porto",
      "name": "Porto",
      "postalCode": "2565-834"
    }
  ]
  ```
<br />

#### `Get /warehouses/{name}`
Um utilizador consulta os dados de um warehouse especifico.
- **URL**: `/warehouses/{name}`
- **Método**: `GET`
- **Headers**: 
  - `Content-Type`: `application/json`
- **Body**:
  ```json
  {
    "name": "Leiria"
  }
  ```
- **Response**:
  ```json
  {
    "address": "Rua das Igrejas",
    "city": "Leiria",
    "name": "Leiria",
    "postalCode": "2565-834"
  }
  ```
<br />

#### `Get /warehouses/{name}/products`
Um utilizador consulta todos os produtos que exitem num determindo warehouse.
- **URL**: `/warehouses/{name}/products`
- **Método**: `GET`
- **Headers**: 
  - `Content-Type`: `application/json`
- **Body**:
  ```json
  {
    "name": "Leiria"
  }
  ```
- **Response**:
  ```json
  {
    "address": "Rua das Igrejas",
    "city": "Leiria",
    "name": "Leiria",
    "postalCode": "2565-834",
    "products": [
      {
        "available": true,
        "description": "Coca-Cola",
        "haveSensor": false,
        "id": 1,
        "image": "coca-cola.jpg",
        "name": "Coca-Cola",
        "price": 1.5,
        "quantity": 0
      },
      ...,
      {
        "available": true,
        "description": "Sprite",
        "haveSensor": false,
        "id": 3,
        "image": "sprite.jpg",
        "name": "Sprite",
        "price": 1.5,
        "quantity": 100
      }
    ]
  }
  ```
<br />

#### `Get /warehouses/{name}/products/{id}`
Um utilizador consulta um determinado produto {id} num determinado warehouse {name}.
- **URL**: `/warehouses/{name}/products/{id}`
- **Método**: `GET`
- **Headers**: 
  - `Content-Type`: `application/json`
- **Body**:
  ```json
  {
    "name": "Leiria",
    "id": 1
  }
  ```
- **Response**:
  ```json
  {
    "address": "Rua das Igrejas",
    "city": "Leiria",
    "name": "Leiria",
    "postalCode": "2565-834",
    "products": [
      {
        "available": true,
        "description": "Coca-Cola",
        "haveSensor": false,
        "id": 1,
        "image": "coca-cola.jpg",
        "name": "Coca-Cola",
        "price": 1.5,
        "quantity": 0,
        "supplier": {
          "email": "safari@mail.com",
          "name": "Safari, LDA",
          "password": "qwerty",
          "username": "safari"
        }
      }
    ]
  }
  ```
<br />

### 8. **Suppliers**
#### `GET /suppliers`
Um utilizador consulta os Suppliers existentes
 **URL**: `/suppliers`
- **Método**: `GET`
- **Headers**: 
  - `Content-Type`: `application/json`
- **Response**:
  ```json
  [
    {
      "email": "drinklfa@mail.com",
      "name": "DrinkLFA, LDA",
      "password": "qwerty",
      "username": "drinkLFA"
    },
    ...,
    {
      "email": "safari@mail.com",
      "name": "Safari, LDA",
      "password": "qwerty",
      "username": "safari"
    }
  ]
  ```
<br />

#### `GET /suppliers/{name}` ou `/suppliers/{name}/products`
Um utilizador consulta um determinado Supplier e os seus respectivos produtos
 - **URL:** `/suppliers/{name}`
- **Método**: `GET`
- **Headers:** 
  - `Content-Type`: `application/json`
- **Body:**
  ```json
  {
    "name": "safari"
  }
  ```
- **Response:**
  ```json
  {
    "email": "safari@mail.com",
    "name": "Safari, LDA",
    "password": "qwerty",
    "products": [
      {
        "available": true,
        "description": "Coca-Cola",
        "haveSensor": false,
        "id": 1,
        "image": "coca-cola.jpg",
        "name": "Coca-Cola",
        "price": 1.5,
        "quantity": 0
      },
      ...,
      {
        "available": true,
        "description": "Sumol",
        "haveSensor": false,
        "id": 6,
        "image": "sumol.jpg",
        "name": "Sumol",
        "price": 1.5,
        "quantity": 100
      }
    ],
    "username": "safari"
  }
  ```
<br />

#### `GET /suppliers/{name}/products/{id}`

Um utilizador efetua a consulta de um produto identificado por {id}, pertencente a um fornecedor designado por {name}.
- **URL:** `/suppliers/{name}/products/{id}`
- **Método**: `GET`
- **Headers:** 
  - `Content-Type`: `application/json`
- **Body:**
  ```json
  {
    "name": "safari"
  }
  ```
- **Response:**
  ```json
  {
    "email": "safari@mail.com",
    "name": "Safari, LDA",
    "password": "qwerty",
    "products": [
      {
        "available": true,
        "description": "Pepsi",
        "haveSensor": false,
        "id": 2,
        "image": "pepsi.jpg",
        "name": "Pepsi",
        "price": 1.5,
        "quantity": 2
      }
    ],
    "username": "safari"
  }
  ```
<br />

#### `GET /suppliers/{name}/products/name/{string}`
Um utilizador consulta diversos produtos com a designação {string}, pertencentes ao fornecedor identificado por {name}.
 - **URL:** `/suppliers/{name}`
- **Método**: `GET`
- **Headers:** 
  - `Content-Type`: `application/json`
- **Body:**
  ```json
  {
    "name": "safari",
    "string": "S"
  }
  ```
- **Response:**
  ```json
  {
    "email": "safari@mail.com",
    "name": "Safari, LDA",
    "password": "qwerty",
    "products": [
      {
        "available": true,
        "description": "Sprite",
        "haveSensor": false,
        "id": 3,
        "image": "sprite.jpg",
        "name": "Sprite",
        "price": 1.5,
        "quantity": 100
      },
      ...,
      {
        "available": true,
        "description": "Sumol",
        "haveSensor": false,
        "id": 6,
        "image": "sumol.jpg",
        "name": "Sumol",
        "price": 1.5,
        "quantity": 100
      }
    ],
    "username": "safari"
  }
  ```
<br />

### 9. **Volume**
#### `GET /volumes`
Um utilizador consulta os volumes existentes
- **URL:** `/volume`
- **Método**: `GET`
- **Headers:** 
  - `Content-Type`: `application/json`
- **Response:**
  ```json
  [
    {
      "creationDate": "2024-10-24 00:00:00.0",
      "id": 121270755,
      "state": "Significant damage"
    },
    ...,
    {
      "creationDate": "2024-10-24 16:01:54.077",
      "id": 1019509487,
      "state": "Moderate damage"
    }
  ]
  ```
<br />

#### `GET /volumes/{id}`
Um utilizador consulta os volumes existentes
- **URL:** `/volume/{id}`
- **Método**: `GET`
- **Headers:** 
  - `Content-Type`: `application/json`
- **Body:**
  ```json
  {
    "id": 1058107426
  }
  ```
- **Response:**
  ```json
    {
    "creationDate": "2024-10-24 20:36:54.968",
    "id": 1058107426,
    "products": [
      {
        "available": true,
        "description": "7up",
        "haveSensor": false,
        "id": 5,
        "image": "7up.jpg",
        "name": "7up",
        "price": 1.5,
        "quantity": 100
      },
      ...,
      {
        "available": true,
        "description": "Sumol",
        "haveSensor": false,
        "id": 6,
        "image": "sumol.jpg",
        "name": "Sumol",
        "price": 1.5,
        "quantity": 100
      }
    ],
    "sensors": [
      {
        "busy": false,
        "currentValue": 20,
        "expedition": false,
        "id": 5,
        "type": "Temperature"
      },
      ...,
      {
        "busy": false,
        "currentValue": 20,
        "expedition": false,
        "id": 25,
        "type": "humidity"
      }
    ],
    "state": "Moderate damage"
  }
  ```
<br />

#### `GET /volumes/{id}/details`
Um utilizador consulta os volumes existentes
- **URL:** `/volume/{id}/details`
- **Método**: `GET`
- **Headers:** 
  - `Content-Type`: `application/json`
- **Body:**
  ```json
  {
    "id": 1058107426
  }
  ```
- **Response:**
  ```json
  {
    "creationDate": "2024-10-24 20:36:54.968",
    "id": 1058107426,
    "products": [
      {
        "available": true,
        "description": "7up",
        "haveSensor": false,
        "id": 5,
        "image": "7up.jpg",
        "name": "7up",
        "price": 1.5,
        "quantity": 100,
        "supplier": {
          "email": "safari@mail.com",
          "name": "Safari, LDA",
          "password": "qwerty",
          "username": "safari"
        },
        "warehouse": {
          "address": "Rua das Igrejas",
          "city": "Leiria",
          "name": "Leiria",
          "postalCode": "2565-834"
        }
      },
      {
        "available": true,
        "description": "Sumol",
        "haveSensor": false,
        "id": 6,
        "image": "sumol.jpg",
        "name": "Sumol",
        "price": 1.5,
        "quantity": 100,
        "supplier": {
          "email": "safari@mail.com",
          "name": "Safari, LDA",
          "password": "qwerty",
          "username": "safari"
        },
        "warehouse": {
          "address": "Rua das Igrejas",
          "city": "Leiria",
          "name": "Leiria",
          "postalCode": "2565-834"
        }
      }
    ],
    "sensors": [
      {
        "busy": false,
        "currentValue": 20,
        "expedition": false,
        "id": 5,
        "type": "Temperature"
      },
      {
        "busy": false,
        "currentValue": 20,
        "expedition": false,
        "id": 25,
        "type": "humidity"
      }
    ],
    "state": "Moderate damage"
  }
  ```

---

## **Credits**
#### `Powered By`
##### `2222051` Gonçalo Ferreira 
##### `2223281` Guilherme Cruz   
##### `2222313` Dinis Roxo       

---

#### `Cliente`
##### `Professor` João Ferreira


---
![Imagem Esquerda](img/github.png)