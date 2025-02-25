# User Stories

| US                                                                 | Story Points |
|--------------------------------------------------------------------|--------------|
| US01 - Register new user                                           | 6            |
| US02 - Log In                                                      | 6            |
| US03 - Create category                                             | 5            |
| US04 - View categories list                                        | 2            |
| US05 - Update category                                             | 5            |
| US06 - Delete category                                             | 5            |
| US07 - Create an advertisement                                     | 10           |
| US08 - View advertisements list                                    | 3            |
| US09 - View active advertisements list                             | 3            |
| US10 - View closed advertisement list                              | 3            |
| US11 - View advertisement                                          | 3            |
| US12 - Search advertisements                                       | 12           |
| US13 - Update advertisement                                        | 9            |
| US14 - Deactivate advertisement                                    | 9            |
| US15 - View my published advertisements                            | 5            |
| US16 - Create ReservationAttempt on advertisement                  | 6            |
| US17 - View ReservationAttempt                                     | 5            |
| US18 - View my list of ReservationAttempts                         | 5            |
| US19 - View ReservationAttempts on my advertisements               | 10           |
| US20 - Updates ReservationAttempt status                           | 10           |
| US21 - Create tip                                                  | 5            |
| US22 - View tips                                                   | 2            |
| US23 - View tip                                                    | 2            |
| US24 - View my favorite tips                                       | 3            |
| US25 - Update tip                                                  | 5            |
| US26 - Delete Tip                                                  | 2            |
| US27 - Create interaction (like or favorite a tip)                 | 10           |
| US28 - Update interaction (like, unlike, favorite, unfavorite tip) | 12           |


## US01 - Register new user
- **Endpoint**: `POST /signup`
- **Description**: As an unregistered user, I want to register in the application to access restricted features.
- **Permissions/Role**: Unregistered user
- **Acceptance Criteria**:
    - The email must be unique.
    - There must be secure password validation.

**Note**: A admin will already be pre-created by the system, and only new clients registrations will be allowed. No additional admins can be registered.

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
- **Description**: As an Admin, I want to create new categories so that users can classify their advertisements properly.
- **Permissions/Role**: admin
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
- **Description**: As an admin, I want to update an existing category name so that it remains relevant and accurate.
- **Permissions/Role**: Admin
- **Acceptance Criteria**:
  - The category designation must be unique and not empty.
  - The category designation must be between 5 and 50 characters

---

## US06 - Delete category
- **Endpoint**: `DELETE /categories/{id}`
- **Description**: As an admin, I want to delete a category so that outdated or irrelevant categories are removed.
- **Permissions/Role**: Admin
- **Acceptance Criteria**:
  - A category can only be deleted if there are no items associated with it.
  - The system should request confirmation

---

## US07 - Create an advertisement
- **Endpoint**: `POST /advertisements`
- **Description**: As a client, I want to create an advertisement so that I can donate an item.
- **Permissions/Role**: Client
- **Acceptance Criteria**:
  - Advertisement status is "active" by default.
  - Advertisement date is automatically created with the current date.
  - The user must provide the item details when creating an advertisement.
  - The system must create the associated item together with the advertisement.
  - The advertisement must not exist without an associated item.


---

## US08 - View advertisements list
- **Endpoint**: `GET /advertisements`
- **Description**: As an admin, I want to list the advertisements so that I can easily manage and oversee the advertisements in the system.
- **Permissions/Role**: Admin
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
- **Description**: As an admin, I want to list the closed advertisements so that I can easily manage and oversee the items in the system.
- **Permissions/Role**: Admin
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
- **Endpoint**: `/advertisement/active?category=[arg1]&keyword=[arg2]&municipality=[arg3]`
- **Description**: As a user, I want to search for active advertisements by category, keyword, and municipality so that I can better find the items I’m interested in.
- **Permissions/Role**: All
- **Acceptance Criteria**:
    - The list displays key details for each advertisement.
    - The list is paginated or scrollable if it contains a large number of items (optional).

---

## US13 - Update advertisement
- **Endpoint**: `PUT /advertisements/{id}`
- **Description**: As a client (advertisement owner), I want to update advertisement’s title, designation and status so that it remains relevant and accurate.
- **Permissions/Role**: Client (advertisement owner)
- **Acceptance Criteria**:
    - Only advertisements with active status can be updated.
    - Only title, designation and status can be updated
    - Advertisement status can only be updated to closed, but if there are ReservationAttempts with status pending or accepted their status change to rejected.

---

## US14 - Deactivate an advertisement
- **Endpoint**: `PATCH /advertisements/{id}/deactivate`
- **Description**: As an admin I want to deactivate an advertisement so that inappropriate advertisements can be removed from visibility.
- **Permissions/Role**: Admin
- **Acceptance Criteria**:
    - Advertisement that have a ReservationAttempt with status donated cannot be deactivated. 
    - The system must ask for confirmation before deactivating an advertisement. 
    - The system should display a success message upon successful deactivation.

---

## US15 - View my published advertisements
- **Endpoint**: `GET /advertisements/users/{userId}`
- **Description**: As a client, I want to see a list of my published advertisements so that I can manage them if necessary
- **Permissions/Role**: Client
- **Acceptance Criteria**:
  - The client should be able to view all their published advertisements (active or closed) ordered by descending date.
  - The list is paginated or scrollable if it contains a large number of items (optional).

---

## US16 - Create ReservationAttempt on advertisement
- **Endpoint**: `POST /ReservationAttempts`
- **Description**: As a client, I want to create a ReservationAttempt on an advertisement so that I can ReservationAttempt the item being donated.
- **Permissions/Role**: Client
- **Acceptance Criteria**:
    - The ReservationAttempt must be made to an advertisement with active status.
    - The system should notify the advertisement owner when a ReservationAttempt is made.
    - A client cannot create multiple ReservationAttempts for the same advertisement.
    - The owner of the advertisement cannot be able to made ReservationAttempt to their own advertisements.

---

## US17 - View ReservationAttempt
- **Endpoint**: `GET reservationAttempts/{id}`
- **Description**: As a client, I want to view the details of a ReservationAttempt so that I can track its status and relevant information.
- **Permissions/Role**: Client (ReservationAttempter or advertisement owner)
- **Acceptance Criteria**:
    - The ReservationAttempter can view the details of their own ReservationAttempts.
    - The advertisement owner can view ReservationAttempts made to their advertisements.

---

## US18 - View my list of ReservationAttempts
- **Endpoint**: `GET /reservationAttempts?clientId={clientId}`
- **Description**: As a client, I want to request all reservations attempts done by clientId
- **Permissions/Role**: Client
- **Acceptance Criteria**:
    - The client should be able to view a list of ReservationAttempts they have made on advertisements ordered by descending date.
    - Each ReservationAttempt should display its status.
    - Clicking on a ReservationAttempt should show its details and the associated advertisement.

---

## US19 - View ReservationAttempts on my advertisements
- **Endpoint**: `GET /reservationAttempts/advertisement?ownerId={clientId}`
- **Description**: As a client (advertisement owner), I want to see the list of ReservationAttempts made on my advertisements so that I can track interest and respond accordingly.
- **Permissions/Role**: Client (advertisement owner)
- **Acceptance Criteria**:
    - The client should be able to view all ReservationAttempts received for each of their advertisements.
    - Clicking on a ReservationAttempt should allow the client to respond (accept/reject).

---

## US20 - Update ReservationAttempt status
- **Endpoint**: `PATCH /reservationAttempts/{reservationId}/status`
- **Description**: As a client, I want to change a ReservationAttempt status so that I can cancel my ReservationAttempt or rejected/accepted/donated a ReservationAttempt made to my advertisement.
- **Permissions/Role**: Client (ReservationAttempter or advertisement creator)
- **Acceptance Criteria**:
    - The only editable field is the ReservationAttempt status.**
    - Only the ReservationAttempter or the advertisement owner can update the status of the ReservationAttempt.**
    - When the status is changed to "donated" the advertisement status is changed to "closed".
    - It must only exist a ReservationAttempt with status donated per advertisement.
    - ReservationAttempt with donated, canceled and rejected status cannot be changed by anyone.**
    - The ReservationAttempter can change is ReservationAttempt to "canceled".**
    - The advertisement owner can change the pending ReservationAttempts to rejected. It can also change a pending ReservationAttempt to accepted, but it must only exist one accepted ReservationAttempt per advertisement.**
    - Changing a ReservationAttempt to donated closes the corresponding advertisement (advertisement status must be updated to closed)
---

## US21 - Create tip
- **Endpoint**: `POST /tips`
- **Description**: As an admin, I want to create an ecological tip so that all users can view and benefit from it.
- **Permissions/Role**: Admin
- **Acceptance Criteria**:
  - The tip must have a title and content, both required fields. 
  - The number of likes and favorites should be initialized to zero but will be updated through US27 (Create Interaction) and US28 (Update Interaction).
---

## US22 - View tips
- **Endpoint**: `GET /tips`
- **Description**: As a user, I want to view ecological tips so that I can benefit from useful and sustainable information.
- **Permissions/Role**: All
- **Acceptance Criteria**:
  - The system should return a list of ecological tips visible to all users.
  - Each tip in the list should display the title
---

## US23 - View tip details
- **Endpoint**: `GET /tips/{id}`
- **Description**: As a user, I want to view the details of an individual ecological tip so that I can benefit from useful and sustainable information.
- **Permissions/Role**: All
- **Acceptance Criteria**:
  - The system should return the details of a specific ecological tip identified by the provided id.
  - The tip's title and content should be displayed.
  - The number of likes and favorites for each tip should be displayed.
---

## US24 - View my favorite tips
- **Endpoint**: `GET /tips/users/{id}`
- **Description**: As a user, I want to view a list of my favorite ecological tips so that I can access the tips I find most useful and sustainable.
- **Permissions/Role**: All
- **Acceptance Criteria**:
  - The system should return a list of ecological tips that the user has marked as favorites.
  - The tip's title and content should be displayed.
  - The number of likes and favorites for each tip should be displayed.
---

## US25 - Update tip
- **Endpoint**: `UPDATE /tips/{id}`
- **Description**: As an admin, I want to update an ecological tip so that it remains accurate and relevant
- **Permissions/Role**: Admin
- **Acceptance Criteria**:
  - Only the title and content can be updated
---

## US26 - Delete tip
- **Endpoint**: `DELETE /tips/{id}`
- **Description**: As an admin, I want to delete an ecological tip so that outdated, incorrect, or irrelevant tips can be removed from the platform.
- **Permissions/Role**: Admin
- **Acceptance Criteria**:
  - The system should request confirmation
  - The system should display a success message after the tip is successfully deleted.
---

## US27 - Create interaction (like or favorite a tip)
- **Endpoint**: `POST /interactions`
- **Description**: As a client, I want to interact with ecological tips by either liking or favoriting them, so that I can show my appreciation and organize the tips that I find useful.
- **Permissions/Role**: Client
- **Acceptance Criteria**:
  - The request body should contain the client ID, tip ID, and booleans for favorite and like.
---

## US28 - Update interaction (like, unlike, favorite, unfavorite tip)
- **Endpoint**: `PUT /interactions`
- **Description**: As a client, I want to update my interactions (like, unlike, favorite, unfavorite) on ecological tips, so that I can modify my preferences
- **Permissions/Role**: Client
- **Acceptance Criteria**:
  - The request body should include the interaction ID, client ID, tip ID, and updated values for favorite and like.
  - The system should remove the like or favorite if set to false (i.e., if the user chooses to "unlike" or "unfavorite").
  - The system should add the like or favorite if set to true (i.e., if the user chooses to "like" or "favorite" the tip).