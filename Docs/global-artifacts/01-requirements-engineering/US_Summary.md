# User Stories

- US01 - Register new user *
- US02 - Log In *
- US03 - Create category *
- US04 - View categories list
- US05 - Update category
- US06 - Delete category
- US07 - Create an advertisement *
- US08 - View advertisements list *
- US09 - View active advertisements list
- US10 - View closed advertisement list
- US11 - View advertisement *
- US12 - Search advertisements *
- US13 - Update advertisement *
- US14 - Delete/inactive advertisement
- US15 - View my published advertisements
- US16 - Create ReservationAttempt on advertisement
- US17 - View ReservationAttempt
- US18 - View my list of ReservationAttempts
- US19 - View ReservationAttempts on my advertisements
- US20 - Updates ReservationAttempt status *

## US01 - Register new user
- **Endpoint**: `POST /signup`
- **Description**: As an unregistered user, I want to register in the application to access restricted features.
- **Permissions/Role**: Unregistered user
- **Acceptance Criteria**:
    - The email must be unique.
    - There must be secure password validation.

**Note**: A manager will already be pre-created by the system, and only new clients registrations will be allowed. No additional managers can be registered.

---

## US02 - Log In
- **Endpoint**: `POST /signin`
- **Description**: As an unauthenticated user, I want to log in to the application to access restricted functionalities.
- **Permissions/Role**: Unregistered user
- **Acceptance Criteria**:
    - Login must be done with email and password.
    - There should be feedback for invalid credentials.

---


## US03 - Create category
- **Endpoint**: `POST /categories`
- **Description**: As a manager, I want to create new categories so that users can classify their advertisements properly.
- **Permissions/Role**: Manager
- **Acceptance Criteria**:
  - The category name must be unique and not empty
  - The category designation must be between 5 and 50 characters

---

## US04 - View categories list
- **Endpoint**: `GET /categories`
- **Description**: As a user, I want to view a list of all available categories so that I can classify advertisements correctly and search by item category.
- **Permissions/Role**: All
- **Acceptance Criteria**:
  - N/A.

---

## US05 - Update category
- **Endpoint**: `PUT /categories/{id}`
- **Description**: As a manager, I want to update an existing category name so that it remains relevant and accurate.
- **Permissions/Role**: Manager
- **Acceptance Criteria**:
  - The category name must be unique and not empty.
  - The category designation must be between 5 and 50 characters

---

## US06 - Delete category
- **Endpoint**: `DELETE /categories/{id}`
- **Description**: As a manager, I want to delete a category so that outdated or irrelevant categories are removed.
- **Permissions/Role**: Manager
- **Acceptance Criteria**:
  - A category can only be deleted if there are no items in an advertisement associated with it.

---

## US07 - Create an advertisement
- **Endpoint**: `POST /advertisements`
- **Description**: As a client, I want to create an advertisement so that I can donate an item.
- **Permissions/Role**: Client
- **Acceptance Criteria**:
  - Advertisement Status is "available" by default.
  - Advertisement date is automatically created with the current date.
  - The user must provide the item details when creating an advertisement.
  - The system must create the associated item together with the advertisement.
  - The advertisement must not exist without an associated item.

---

## US08 - View advertisements list
- **Endpoint**: `GET /advertisements`
- **Description**: As a manager, I want to list the advertisements so that I can easily manage and oversee the advertisements in the system.
- **Permissions/Role**: Manager
- **Acceptance Criteria**:
    - The list displays key details for each advertisement.
    - The list is paginated or scrollable if it contains a large number of items (optional).

---

## US09 - View active advertisements list
- **Endpoint**: `GET /advertisements/active`
- **Description**: As a user, I want to view the list of active advertisements so that I can explore the items that are available for donation.
- **Permissions/Role**: All
- **Acceptance Criteria**:
    - The list displays key details for each advertisement.
    - The list is paginated or scrollable if it contains a large number of items (optional).

---

## US10 - View closed advertisement list
- **Endpoint**: `GET /advertisements/closed`
- **Description**: As a manager, I want to list the closed advertisements so that I can easily manage and oversee the items in the system.
- **Permissions/Role**: Manager
- **Acceptance Criteria**:
    - The list displays key details for each advertisement.
    - The list is paginated or scrollable if it contains a large number of items (optional).

---

## US11 - View advertisement details
- **Endpoint**: `GET /advertisements/{id}`
- **Description**: As a user, I want to view more details about a specific advertisement so that I can get additional information.
- **Permissions/Role**: All
- **Acceptance Criteria**:
    - N/A

---

## US12 - Search advertisements
- **Endpoint**: `/advertisement/available?category=[arg1]&keyword=[arg2]&municipality=[arg3]`
- **Description**: As a user, I want to search for active advertisements by category, keyword, and municipality so that I can better find the items I’m interested in.
- **Permissions/Role**: All
- **Acceptance Criteria**:
    - The list displays key details for each advertisement.
    - The list is paginated or scrollable if it contains a large number of items (optional).

---

## US13 - Update advertisement
- **Endpoint**: `PUT /advertisements/{id}`
- **Description**: As a client, I want to update advertisement’s title, designation and status so that it remains relevant and accurate.
- **Permissions/Role**: Client (the creator)
- **Acceptance Criteria**:
    - Only advertisements with active status can be updated.
    - Only title, designation and status can be updated
    - Advertisement status can only be updated to closed.
    - Advertisement status can be updated to closed, but if there are ReservationAttempts with status pending or accepted their status have to change to rejected.

---

## US14 - Deactivate an advertisement
- **Endpoint**: `PATCH /advertisements/{id}/deactivate`
- **Description**: As a manager I want to deactivate an advertisement so that inappropriate advertisements can be removed from visibility.
- **Permissions/Role**: Manager
- **Acceptance Criteria**:
    - Advertisement that have a ReservationAttempt with status donated cannot be deactivated. 
    - The system must ask for confirmation before deleting an advertisement. 
    - The system should display a success message upon successful deletion.


---

## US15 - View my published advertisements
- **Endpoint**: `GET /users/{userId}/advertisements`
- **Description**: As a client, I want to see a list of my published advertisements so that I can manage them if necessary
- **Permissions/Role**: Client
- **Acceptance Criteria**:
  - The client should be able to view all their published advertisements (active or closed) ordered by descending date.
  - The list is paginated or scrollable if it contains a large number of items (optional).

---

## US16 - Create ReservationAttempt on advertisement
- **Endpoint**: `POST /advertisements/{id}/ReservationAttempts`
- **Description**: As a client, I want to create a ReservationAttempt on an advertisement so that I can ReservationAttempt the item being donated.
- **Permissions/Role**: Client
- **Acceptance Criteria**:
    - The ReservationAttempt must be made to an advertisement with active status.
    - The system should notify the advertisement owner when a ReservationAttempt is made.
    - A client cannot create multiple ReservationAttempts for the same advertisement.
    - The owner of the advertisement cannot be able to made ReservationAttempt to their own advertisements.

---

## US17 - View ReservationAttempt
- **Endpoint**: `GET /advertisements/{adId}/ReservationAttempts/{ReservationAttemptId}`
- **Description**: As a client, I want to view the details of a ReservationAttempt so that I can track its status and relevant information.
- **Permissions/Role**: Client (ReservationAttempter or advertisement owner)
- **Acceptance Criteria**:
    - The ReservationAttempter can view the details of their own ReservationAttempts.
    - The advertisement owner can view ReservationAttempts made to their advertisements.

---

## US18 - View my list of ReservationAttempts
- **Endpoint**: `GET /users/{userId}/ReservationAttempts`
- **Description**: As a client, I want to view the list of ReservationAttempts I have made on advertisements so that I can track their status and manage them
- **Permissions/Role**: Client
- **Acceptance Criteria**:
    - The client should be able to view a list of ReservationAttempts they have made on advertisements ordered by descending date.
    - Each ReservationAttempt should display its status.
    - Clicking on a ReservationAttempt should show its details and the associated advertisement.

---

## US19 - View ReservationAttempts on my advertisements
- **Endpoint**: `GET /users/{userId}/advertisements/ReservationAttempts`
- **Description**: As a client (advertisement owner), I want to see the list of ReservationAttempts made on my advertisements so that I can track interest and respond accordingly.
- **Permissions/Role**: Client (advertisement owner)
- **Acceptance Criteria**:
    - The client should be able to view all ReservationAttempts received for each of their advertisements.
    - Clicking on a ReservationAttempt should allow the client to respond (accept/reject).

---

## US20 - Update ReservationAttempt status
- **Endpoint**: `PATCH /advertisements/{id}/ReservationAttempts/{id}/status`
- **Description**: As a client, I want to change a ReservationAttempt so that I can canceled my ReservationAttempt or decline/accepted/conclude a ReservationAttempt made to my advertisement.
- **Permissions/Role**: Client (ReservationAttempter or advertisement creator)
- **Acceptance Criteria**:
    - The only editable field is the ReservationAttempt status.
    - Only the ReservationAttempter or the advertisement owner can update the status of the ReservationAttempt.
    - The system should notify the involved parties when the status changes.
    - When the status is changed to finished the advertisement status is changed to finished.
    - It must only exist a ReservationAttempt with status donated per advertisement.
    - ReservationAttempt with donated, canceled and rejected status cannot be changed by anyone.
    - The ReservationAttempter can change is ReservationAttempt to canceled.
    - The advertisement owner can change the pending ReservationAttempts to rejected. It can also change a pending ReservationAttempt to accepted, but it must only exist one accepted ReservationAttempt per advertisement.
    - Changing a ReservationAttempt to donated closes the corresponding advertisement (advertisement status must be updated to closed)
---