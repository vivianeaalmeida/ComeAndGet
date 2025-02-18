# US12 - Search advertisements

## 1. Requirements Engineering

### 1.1. User Story Description

As a user, I want to search for active advertisements by category, keyword, and location so that I can better find the items I’m interested in.

### 1.2. Customer Specifications and Clarifications

**From the specifications document:**

> The system must allow users to search advertisements using keywords, category, and municipality.
> Only active advertisements should be displayed in the search results.

**From the client clarifications:**

> **Question:**
>
> **Answer:**

### 1.3. Acceptance Criteria

* **AC1:** The list displays key details for each advertisement, such as product name, category, condition, status.
* **AC2:** The list is paginated or scrollable if it contains a large number of items (optional).

### 1.4. Found out Dependencies

* US09 - View active advertisements list

### 1.5 Input and Output Data

**Input Data:**

* Typed data:
  * keywords
  * location

* Selected data:
  * category

**Output Data:**

* List of advertisements matching the search criteria, including: Product name, Category, Condition, Status (always "Active"), Location.
* If no advertisements match, show message: "No results found."

### 1.6. System Sequence Diagram (SSD)

![System Sequence Diagram](US006-SSD.svg)

### 1.7 Other Relevant Remarks

* 