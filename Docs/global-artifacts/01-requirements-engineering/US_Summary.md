# User Stories

- US01 - Register new user
- US02 - Log In
- US03 - Create category
- US04 - View categories list
- US05 - Update category
- US06 - Delete category
- US07 - Create an advertisement
- US08 - View advertisements list
- US09 - View available advertisements list
- US10 - View pending advertisement list
- US11 - View advertisement details
- US12 - Search advertisements
- US13 - Update advertisement
- US14 - Delete advertisement
- US15 - Create request
- US16 - View request detail
- US17 - View list of requests made by the user
- US18 - View list of active requests received by the user
- US19 - Updates request status

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
  - The category name must be unique.
  - The system should validate that the category name is not empty.

---

## US04 - View categories list
- **Endpoint**: `GET /categories`
- **Description**: As a user, I want to view a list of all available categories so that I can classify advertisements correctly and search by item category.
- **Permissions/Role**: Client
- **Acceptance Criteria**:
  - The system should return a list of categories with their respective names.

---

## US05 - Update category
- **Endpoint**: `PUT /categories/{id}`
- **Description**: As a manager, I want to update an existing category name so that it remains relevant and accurate.
- **Permissions/Role**: Manager
- **Acceptance Criteria**:
  - The category name must be unique and not empty.

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
    - Status advertisement is “available” by default.

---

## US08 - View advertisements list
- **Endpoint**: `GET /advertisements`
- **Description**: As a manager, I want to list the advertisements so that I can easily manage and oversee the advertisements in the system.
- **Permissions/Role**: Manager
- **Acceptance Criteria**:
    - The list displays key details for each advertisement.
    - The list is paginated or scrollable if it contains a large number of items (optional).

---

## US09 - View available advertisements list
- **Endpoint**: `GET /advertisements/active`
- **Description**: As a user, I want to view the list of active advertisements so that I can explore the items that are available for donation.
- **Permissions/Role**: All
- **Acceptance Criteria**:
    - The list displays key details for each product, such as product name, category, condition, and donation method.
    - The list is paginated or scrollable if it contains a large number of items (optional).

---

## US10 - View pending advertisement list
- **Endpoint**: `GET /advertisements/pending`
- **Description**: As a manager, I want to list the pending advertisements so that I can easily manage and oversee the items in the system.
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
    - The page must contain all the advertisement details and the contact of the donor (only registered users can get the contact).

---

## US12 - Search advertisements
- **Endpoint**: `/advertisement/available?category=[arg1]&keyword=[arg2]&location=[arg3]`
- **Description**: As a user, I want to search for available advertisements by category, keyword, and location so that I can better find the items I’m interested in.
- **Permissions/Role**: All
- **Acceptance Criteria**:
    - The list displays key details for each advertisement, such as product name, category, condition, status.
    - The list is paginated or scrollable if it contains a large number of items (optional).

---

## US13 - Update advertisement
- **Endpoint**: `PUT /advertisements/{id}`
- **Description**: As a client, I want to update advertisement’s name, location, and end date.
- **Permissions/Role**: Client (the creator)
- **Acceptance Criteria**:
    - Only advertisements with available status can be updated.
    - Advertisements with closed status have the possibility to extend the end date and reopen the advertisement.

---

## US14 - Delete advertisement
- **Endpoint**: `DELETE /advertisements/{id}`
- **Description**: As a client and a manager, I want to delete an advertisement.
- **Permissions/Role**: Client (the creator) / Manager
- **Acceptance Criteria**:
    - Only advertisements with active status can be deleted.

---

## US15 - Create donation request
- **Endpoint**: `POST /advertisements/{id}/donation-requests`
- **Description**: As a client, I want to create a donation request on an advertisement so that I can request the item being donated.
- **Permissions/Role**: Client
- **Acceptance Criteria**:
    - The donation request must be linked to an advertisement with active status.
    - The system should notify the advertisement owner when a request is made.
    - A client cannot create multiple donation requests for the same advertisement.

---

## US16 - View donation request detail
- **Endpoint**: `GET /advertisements/{adId}/donation-requests/{requestId}`
- **Description**: As a client, I want to view the details of a donation request so that I can track its status and relevant information.
- **Permissions/Role**: Client (requester or advertisement owner)
- **Acceptance Criteria**:
    - The requester can view the details of their own donation requests.
    - The advertisement owner can view donation requests made to their advertisements.
    - The response must include the request status, creation date, and associated advertisement details.

---

## US17 - View list of donation requests made by the user
- **Endpoint**: `GET /users/{userId}/donation-requests`
- **Description**: As a client, I want to view the list of donation requests I have made so that I can track their status and manage the requests.
- **Permissions/Role**: Client
- **Acceptance Criteria**:
    - The client (receiver) can view all donation requests they have made.
    - The system must return a list of donation requests, including the request status, creation date, and associated advertisement details.
    - If no donation requests have been made by the user, the system must return an empty list.

---

## US18 - View list of active donation requests received by the user
- **Endpoint**: `GET /users/{userId}/advertisements/donation-requests/active`
- **Description**: As a client (advertisement owner), I want to view all active donation requests made to my advertisements so that I can respond to the requests.
- **Permissions/Role**: Client (advertisement owner)
- **Acceptance Criteria**:
    - The advertisement owner can view all active donation requests made to their advertisements.
    - The system must return a list of active donation requests, including the request status, creation date, and associated advertisement details.
    - If no active donation requests exist, the system must return an empty list.

---

## US19 - Update request status
- **Endpoint**: `PATCH /advertisements/{id}/donation-requests/{id}/status`
- **Description**: As a client (requester or advertisement owner), I want to change a request so that I can canceled my request or decline/accepted/conclude a request made to my advertisement.
- **Permissions/Role**: Client (requester or advertisement owner)
- **Acceptance Criteria**:
    - The only editable field is the request status.
    - Only the requester or the advertisement owner can update the status of the request.
    - The system should notify the involved parties when the status changes.
    - When the status is changed to finished the advertisement status is changed to finished.
---