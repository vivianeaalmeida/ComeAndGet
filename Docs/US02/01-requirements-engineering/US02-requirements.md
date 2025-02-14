# US02 - Login

## 1. Requirements Engineering

### 1.1. User Story Description

As an unauthenticated user, I want to log in to the application to access restricted functionalities.

### 1.2. Customer Specifications and Clarifications 

**From the specifications document:**

> The login process must require a valid email and password combination.
> If the credentials are incorrect, the system must provide appropriate feedback to the user.
> Upon successful login, the system should establish a session for the user. 

**From the client clarifications:**

> **Question:**
>
> **Answer:**

### 1.3. Acceptance Criteria

* **AC1:** Each client is identified by an automatic sequence-generated number.
* **AC2:** The email must be unique for each client.

### 1.4. Found out Dependencies

* There is no found out dependencies.

### 1.5 Input and Output Data

**Input Data:**

* Typed data:
    * email
    * password
	
* Selected data:
    * 

**Output Data:**

* Success: User is authenticated, and a session is created.
* Failure: Error message indicating incorrect credentials.

### 1.6. System Sequence Diagram (SSD)

![System Sequence Diagram](US006-SSD.svg)

**_Other alternatives might exist._**

### 1.7 Other Relevant Remarks

* If a user forgets their password, they must use the password recovery process.