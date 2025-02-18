# US14 - Deactivate advertisement

## 1. Requirements Engineering

### 1.1. User Story Description

As a manager I want to deactivate an advertisement so that inappropriate advertisements can be removed from visibility.

### 1.2. Customer Specifications and Clarifications

**From the specifications document:**

- **Endpoint**: `PATCH /advertisements/{id}/deactivate`
- **Description**: As a manager I want to deactivate an advertisement so that inappropriate advertisements can be removed from visibility.
- **Permissions/Role**: Manager
- **Acceptance Criteria**:
  - Advertisement that have a ReservationAttempt with status donated cannot be deactivated.
> Deactivate advertisements should no longer appear in searches or listings.

**From the client clarifications:**

> **Question:**
>
> **Answer:**

### 1.3. Acceptance Criteria

* **AC1:** Advertisement that have a ReservationAttempt with status donated cannot be deactivated.
* **AC2:** The system must ask for confirmation before deleting an advertisement.
* **AC3:** The system should display a success message upon successful deletion.

### 1.4. Found out Dependencies

* N/A

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