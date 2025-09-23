# Mapua-Inventory
# This is a placeholder ReadMe
## For testing the entity relationships using Postman API
### Create a borrow record (new user) This test works when doing it from Angular
Method: POST  
URL: http://localhost:8080/api/borrow-records  
Headers: Content-Type: application/json  
Body (raw JSON):  
{  
  "borrower": {  
    "serialNumber": "SN-001",  
    "studentName": "Juan Dela Cruz",  
    "studentNumber": "2025-001"  
  },  
  "item": {  
    "id": 1  
  },  
  "borrowedAt": "2025-09-23T10:00:00",  
  "returnedAt": null,  
  "remarks": ""  
}  

### Create a borrow record (existing user)
Method: POST  
URL: http://localhost:8080/api/borrow-records  
Headers: Content-Type: application/json  
Body (raw JSON):  
{  
  "borrower": {  
    "serialNumber": "SN-001"  
  },  
  "item": {  
    "id": 5  
  },  
  "borrowedAt": "2025-09-23T01:00:00",  
  "returnedAt": null,  
  "remarks": ""  
}  

### Return an item
Method: POST  
URL: http://localhost:8080/api/borrow-records/{borrowRecordId}/return  
Headers: Content-Type: application/json  
Body (optional):  
{  
  "remarks": "Returned on time"  
}  

## Errors and Working Features of the App currently
### Errors:
- Creating a borrow-record with an existing user is not functional from Angular  
- Creating a borrow-record with an existing user using Postman API returns an infinite loop response however it does successfully create the record in the database  
- Returning the item from Angular has not been implemented  
- Returning the item using Postman API successfully completes the "returnedAt" and "remarks" columns in the database however it returns an infinite loop response  

### Working Features:
- Creating an equipment-type from Angular  
- Creating an item from Angular  
- Borrowing an item as a new Borrower from Angular  
