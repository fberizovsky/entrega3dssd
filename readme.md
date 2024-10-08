# README

## Endpoints


### PrincipalDepositController

- **GET api/principalDeposit**
    - Descripción: Obtiene todas las órdenes.
    - Respuesta: Lista de órdenes.

- **POST api/principalDeposit**
    - Descripción: Crea una nueva orden.
     - Cuerpo de la solicitud: Datos de la nueva orden.
    ```json
    {
        "name": "Nombre",
        "password": "contraseñaSegura"
    }
    ```
    - Respuesta: Orden creada.

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



