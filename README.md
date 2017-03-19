#Car Advert REST API
A RESTful web-service to create, view, modify and delete car adverts.

**View all car adverts**
----
  Returns json data for all car adverts.

* **URL**

  /car/adverts

* **Method:**
  
  `GET`
  
*  **Query String Params**
   `sortby=[field_name]`
   
   If `sortby` query param is not specified, results will be sorted by car advert id.

* **Data Params**

 None

* **Success Response:**
 
  * **Code:** 200 <br />
    **Content:** `[
		      {
		      "id": 88,
		      "title": "Title for advert 88",
		      "fuelType": "diesel",
		      "price": 20000,
		      "isNew": false,
		      "mileage": 1453,
		      "firstRegistration": "2014-05-05"
		   },
		      {
		      "id": 199,
		      "title": "car advert 199",
		      "fuelType": "gasoline",
		      "price": 19000,
		      "isNew": true
		   }
]`

* **Sample Calls:**

 `GET /car/adverts`
 `GET /car/adverts?sortby=price`

**View a single car advert**
----
  Returns json data for a single car advert.

* **URL**

  /car/adverts/:id

* **Method:**
  
  `GET`
  
* **Success Response:**
 
  * **Code:** 200 
    * **Content:** `
		      {
		      "id": 88,
		      "title": "Title for advert 88",
		      "fuelType": "diesel",
		      "price": 20000,
		      "isNew": false,
		      "mileage": 1453,
		      "firstRegistration": "2014-05-05"
		   }`
* **Error Response:**
 
  * **Code:** 404 
  *  **Explanation:** No car advert with given id was found.

* **Sample Calls:**

 `GET /car/adverts/1`
