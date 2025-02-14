# US14 - Delete Advertisement

## 3. Design

### 3.1. Rationale

| Interaction ID | Question: Which class is responsible for...    | Answer                  | Justification (with patterns)                                                                                                                                                |
|:---------------|:-----------------------------------------------|:------------------------|:-----------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| Step 1         | ... interacting with the actor?                | AdvertisementUI         | Pure Fabrication: there is no reason to assign this responsibility to any existing class in the Domain Model.                                                                |
|                | ... coordinating the US?                       | AdvertisementController | Controller                                                                                                                                                                   |
| Step 2         | ... requesting confirmation?                   | AdvertisementUI         | IE: is responsible for handling user interactions.                                                                                                                           |
| Step 3         | ...confirming the operation?                   | AdvertisementUI         | IE: The UI manages user actions and forwards them to the controller.                                                                                                         |
| Step 4         | ... delegating deletion to the service layer?  | AdvertisementService    | The controller delegates business logic to the service layer.                                                                                                                |
| Step 5         | ... performing the business logic of deletion? | AdvertisementService    | The service layer contains business logic and validates the operation.                                                                                                       |
| Step 6         | ... confirming the successful deletion?        | AdvertisementController | The controller returns the appropriate response to the UI.                                                                                                                   | 
| Step 7         | ... displaying success messages to the user?   | AdvertisementUI         | IE (Information Expert): The UI is responsible for communicating the results of the operation                                                                                |

### Systematization ##

According to the taken rationale, the conceptual classes promoted to software classes are:

* Advertisement

Other software classes (Pure Fabrication) identified:

* AdvertisementUI
* AdvertisementController
* AdvertisementService
* AdvertisementRepository


## 3.2. Sequence Diagram (SD)

![Sequence Diagram](US03-SD.svg)

## 3.3. Class Diagram (CD)

![Class Diagram](US03-CD.svg)