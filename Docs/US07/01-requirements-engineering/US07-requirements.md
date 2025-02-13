# US07 - Create an advertisement

## 1. Requirements Engineering

### 1.1. User Story Description

As a Client I want to create an advertisement for an item I wish to donate so that potential recipients can request it.

### 1.2. Customer Specifications and Clarifications 

**From the specifications document:**

> When creating an advertisement, it must have a unique identifier, the ownerId, the itemId, the initialDate, the finalDate, the address.
> The advertisement status is "active" by default and depending on some actions, it is automatically managed by the system.

**From the client clarifications:**

> **Question:**
> Is it possible to have multiple images in an advertisement?
> 
> **Answer:**
> Yes, it is possible to have multiple images in an advertisement.

### 1.3. Acceptance Criteria

* **AC1:** The advertisement status is active, pending or closed.
* **AC2:** Status advertisement is “active” by default.
* **AC3:** The advertisement must have a system-generated unique identifier.
* **AC4:** The advertisement must contain all mandatory fields before being registered
* **AC5:** The advertisement can have up to 5 images. 

### 1.4. Found out Dependencies

* This user story depends on the user story US02 - Log in.
* This user story depends on the user story US04 - View categories list.

### 1.5 Input and Output Data

**Input Data:**

* Automatically obtained data:
    * user id

* Typed data:
    * owner id
    * item id
    * initial date
    * final date
    * address
    * images

* Generated data:
    * unique identifier
    * status

**Output Data:**

* Confirmation message (e.g., "Registration successful.")
* Success or failure response with error messages (e.g., "Error when registering an advertisement", "Invalid image format")

### 1.6. System Sequence Diagram (SSD)

![System Sequence Diagram](US006-SSD.svg)

**_Other alternatives might exist._**

### 1.7 Other Relevant Remarks

* An advertisement can only be edited if its status is "available".
* Users cannot manually change the status of an advertisement; it is managed by the system.
* The system should validate the format of the address before registering an advertisement.