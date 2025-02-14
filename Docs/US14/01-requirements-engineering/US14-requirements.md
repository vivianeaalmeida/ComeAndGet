# US14 - Delete advertisements

## 1. Requirements Engineering

### 1.1. User Story Description

As a client and a manager, I want to delete an advertisement, so that it is no longer available in the system.

### 1.2. Customer Specifications and Clarifications

**From the specifications document:**

> The system must allow clients to delete their own advertisements.
> The system must allow managers to delete any advertisement if necessary.
> Deleted advertisements should no longer appear in searches or listings.

**From the client clarifications:**

> **Question:**
>
> **Answer:**

### 1.3. Acceptance Criteria

* **AC1:** Only advertisements with active status can be deleted.
* **AC2:** The system must ask for confirmation before deleting an advertisement.
* **AC3:** The system should display a success message upon successful deletion.

### 1.4. Found out Dependencies

* US08 - View advertisements list (to ensure deleted ads no longer appear).
* US09 - View active advertisements list (as only active ads can be deleted).

### 1.5 Input and Output Data

**Input Data:**

* Selected Advertisement (id)

**Output Data:**

* Success message: "Advertisement deleted successfully."
* Failure messages:
  "Advertisement not found."
  "Only active advertisements can be deleted."

### 1.6. System Sequence Diagram (SSD)

![System Sequence Diagram](US006-SSD.svg)

### 1.7 Other Relevant Remarks

* 