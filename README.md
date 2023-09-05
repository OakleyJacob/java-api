# Java Enterprise Foundations Project Requirements

## Project Description

### What is it?
A restful API that allows users to submit and track requests for reimbursement.
### How does it do this?
Tech Stack:
- **Java 8** - Primary Coding Language
- **Apache Maven** – Dependency Manager
- **JDBC** – Java Connectivity
- **Javalin** – Web Framework
- **JSON Web Tokens** – Authenticates Users
- **JUnit** – Testing Tool
- **Mockito** – Mocking Framework For Testing


### Project Design Specifications and Documents

##### Relational Data Model - 3rd Normal Form
![Relational Model](https://raw.githubusercontent.com/221114-Java-React/Elias-Gonzalez-P1/main/reimbursementProgram/images/relationalModel.png)

##### What can users do?

 A user my apply to be added to the system, once added by an admin they can:
 - create a ticket for reimbursement
 - view/sort their tickets
 - update their pending tickets

A user who has been promoted to manager may:
- do all of the above
- approve/deny reimbursement tickets
- view/sort other user's tickets
- view list of users

A user who has been promoted to admin may:
- do all of the above
- approve/delete applicants from the system
- update a user's password
- deactivate a user

##### System Use Case Diagrams
![System Use Case Diagrams](https://raw.githubusercontent.com/220207-java-enterprise/assignments/main/foundations-project/imgs/ERS%20Use%20Case%20Diagram.png)

##### Reimbursment Status State Flow
![Reimbursment Status State Flow](https://raw.githubusercontent.com/220207-java-enterprise/assignments/main/foundations-project/imgs/ERS%20State%20Flow%20Diagram.png)

##### System Logic Flow
![System Logic Flow](https://lucid.app/publicSegments/view/1d79dc45-495b-4d4f-98bb-74dc6ba6f294/image.png)

### Restful API Requirements
- Use a Uniform Interface
- Client-server based
- Stateless operations
- Allows for caching
- Layered System
- Code on Demand
### Richardson Maturity Model

![Richardson Maturity Model](https://restfulapi.net/wp-content/uploads/Richardson-Maturity-Model-300x249.jpg)

Credit to both Ashley Chancellor and Elias Gonzales, their READMEs were great resources to use while building my own. 







