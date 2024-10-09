# README

## Endpoints


### PrincipalDepositController

- **GET api/principalDeposit**
   - Descripción: Obtiene todos los depositos principales
   - Respuesta: Lista de depositos principales

- **POST api/principalDeposit**
     - Descripción: Crea un nuevo deposito principal.
     - Cuerpo de la solicitud: Datos del nuevo deposito principal.
    ```json
    {
        "name": "Nombre",
        "password": "contraseñaSegura"
    }
    ```
    - Respuesta: Orden creada.

### ComunalDepositController

- **GET api/comunalDeposit**
    - Descripción: Obtiene todos los depositos comunales
    - Respuesta: Lista de depositos comunales

- **POST api/comunalDeposit**
    - Descripción: Crea un nuevo deposito comunal.
     - Cuerpo de la solicitud: Datos del nuevo deposito comunal.
    ```json
    {
        "name": "Nombre",
        "password": "contraseñaSegura"
    }
    ```
    - Respuesta: Deposito creado creada.


### OrdenController

- **GET api/orden**
    - Descripción: Obtiene todas las órdenes.
    - Respuesta: Lista de órdenes.

- **POST api/orden**
    - Descripción: Crea una nueva orden.
     - Cuerpo de la solicitud: Datos de la nueva orden.
        ```json
        {
          "principalDepositId": 1,
          "items": [
            {
              "nombre": "MADERA",
              "cantidad": 5
            },
            {
              "nombre": "CARTÓN",
              "cantidad": 10
            }
          ]
        }
        ```
    - Respuesta: Orden creada.



