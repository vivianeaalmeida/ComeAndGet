# US08 - View all advertisements list

## 1. Requirements Engineering

### 1.1. User Story Description

As a manager, I want to request the list of all advertisements.

### 1.2. Customer Specifications and Clarifications 

**From the specifications document:**

> Each advertisement must have a designation, an initial date, a municipality, a status and the item values. 


**From the client clarifications:**

> **Question:**
>
> **Answer:**

### 1.3. Acceptance Criteria

* **AC1:** All advertisements must be on the list, regardless of whether they are open or closed.
* **AC2:** The user must be logged in and authenticated with admin role.


### 1.4. Found out Dependencies

* This user story depends on US01 - Log in, as only the manager can list all advertisements.

### 1.5 Input and Output Data

**Input Data:**
	
* Selected data:

**Output Data:**

* AdrvertisementDTO[] ("All advertisements list")

### 1.6. System Sequence Diagram (SSD)

![System Sequence Diagram](US07-SSD.svg)


### 1.7 Other Relevant Remarks

* 