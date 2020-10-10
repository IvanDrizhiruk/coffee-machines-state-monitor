# Coffee Machines State Monitor

Project for learning approaches and tools of testing

#Available urls
- POST http://localhost:8080/event-registrar/coffee-machine/{coffeeMachineId}/cup-produced
    - body: CupDTO
- GET http://localhost:8080/event-registrar/coffee-machine/{coffeeMachineId}/line-service
    - body: CoffeeMachineLineServiceDTO
- GET http://localhost:8080/coffee-machine/all/status