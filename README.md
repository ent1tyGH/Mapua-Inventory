# Mapua-Inventory
# This is a placeholder ReadMe
## The contents are for testing the entity relationships using Postman API
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
  "remarks": "Testing existing borrower"  
}  

### Return an item
Method: POST  
URL: http://localhost:8080/api/borrow-records/{borrowRecordId}/return  
Headers: Content-Type: application/json  
Body (optional):  
{  
  "remarks": "Returned on time"  
}  
