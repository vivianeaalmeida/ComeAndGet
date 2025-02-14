# US16 - Create a request

## 3. Design

### 3.1. Rationale

| Interaction ID | Question: Which class is responsible for... | Answer            | Justification (with patterns)                                                                                 |
|:---------------|:--------------------------------------------|:------------------|:--------------------------------------------------------------------------------------------------------------|
| Step 1         | ... interacting with the actor?             | RequestUI         | Pure Fabrication: there is no reason to assign this responsibility to any existing class in the Domain Model. |
|                | ... coordinating the US?                    | RequestController | Controller                                                                                                    |
| Step 2         | ... instantiating requests?                 | RequestService    | Creator (Rule 2): RequestService instantiates Requests.                                                       |
| Step 2         | ... recording request?                      | RequestRepository | Pure Fabrication: RequestRepository records Requests.                                                         |
|                | ... showing operation success?              | RequestUI         | IE: is responsible for user interactions.                                                                     |

### Systematization ##

According to the taken rationale, the conceptual classes promoted to software classes are:

* Request
* Item

Other software classes (i.e. Pure Fabrication) identified:

* RequestUI
* RequestController
* RequestRepository
* RequestService

## 3.2. Sequence Diagram (SD)

### View Requests List Full SD

This diagram shows the full sequence of interactions between the classes involved in the realization of this user story.

![Sequence Diagram - Full](US16-SD.svg)


## 3.3. Class Diagram (CD)

![Class Diagram](US16-CD.svg)