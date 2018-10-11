# Car Adverts REST API
A RESTful web-service to create, view, modify and delete car adverts. Used Play Framework for the first time. MongoDB is used for persistence.

**View all car adverts**
----
  Returns json data for all car adverts..

* **URL**
  `/car/adverts`

* **Method:**
  `GET`
  
*  **Query String Params**
   
   `sortby=[field_name]`

   If `sortby` query param is not specified, results will be sorted by car advert id. [field_name] is case insensitive.
* **Data Params**
    None

* **Success Response:**
 
  * **Code:** 200 (Ok)
  
   * **Content:** 
    ```json
    	[
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
		 ]
	```

* **Sample Calls:**

  `GET /car/adverts`
  
  `GET /car/adverts?sortby=price`

**View a single car advert**
----
  Returns json data for a single car advert.

* **URL**
  `/car/adverts/:id`

* **Method:**
  `GET`
  
* **Success Response:**
  * **Code:** 200 
  
  * **Content:** 
  ```json
		      {
			      "id": 88,
			      "title": "Title for advert 88",
			      "fuelType": "diesel",
			      "price": 20000,
			      "isNew": false,
			      "mileage": 1453,
			      "firstRegistration": "2014-05-05"
		   	}
    ```
		   
* **Error Response:**
  * **Code:** 404 (Not found)
  
  *  **Explanation:** No car advert with given id was found.

* **Sample Calls:**
 `GET /car/adverts/1`

 **Create a car advert**
----
  Creates a new car advert.

* **URL**
  `/car/adverts`

* **Method:**
  `POST`
  
*  **Query String Params**
   None

* **Data Params**

  ```json
  		{
			      "id": 88,
			      "title": "Title for advert 88",
			      "fuelType": "diesel",
			      "price": 20000,
			      "isNew": false,
			      "mileage": 1453,
			      "firstRegistration": "2014-05-05"
			   }
   ```

* **Success Response:**
 
  * **Code:** 201 (Created)
  
   * **Content:** 

   ```json
   	{
		  "id": 88,
		  "title": "Title for advert 88",
		  "fuelType": "diesel",
		  "price": 20000,
		  "isNew": false,
		  "mileage": 1453,
		  "firstRegistration": "2014-05-05"
	       }
   ```

* **Error Response**

  * **Code:** 400 (Bad request)
  
  * **Explanation:** This is returned if json is invalid or cannot be parsed.

  * **Code:** 422 (Unprocessable entity)
  
  * **Explanation:** Validation failed.
  
  * **Content:**
  ```json
  {
	  "validation_errors": [
	   "Id must be a positive number",
	   "Price cannot be negative"
	  ]
  }
  ```

 **Modify a car advert**
----
  Modifies an existing car advert.

* **URL**
  `/car/adverts/:id`

* **Method:**
  `PUT`
  
*  **Query String Params**
   None

* **Data Params**
 
 ```json
		  {
		      "id": 88,
		      "title": "Title for advert 88",
		      "fuelType": "diesel",
		      "price": 9999,
		      "isNew": false,
		      "mileage": 1453,
		      "firstRegistration": "2014-05-05"
		   }
 ```

* **Success Response:**
 
  * **Code:** 200 (Ok)
  
    **Content:** 

    ```json
      {
          "id": 88,
          "title": "Title for advert 88",
          "fuelType": "diesel",
          "price": 9999,
          "isNew": false,
          "mileage": 1453,
          "firstRegistration": "2014-05-05"
       }
    ```

* **Error Response**

  * **Code:** 404 (Not found)
  
  * **Explanation:** This is returned if a car advert with given id is not found.

  * **Code:** 400 (Bad request)
  
  * **Explanation:** This is returned if json is invalid or cannot be parsed.

  * **Code:** 422 (Unprocessable entity)
  
  * **Explanation:** Validation failed.
  
  * **Content:**
  ```json
  {
	  "validation_errors": [
	   "Id must be a positive number",
	   "Price cannot be negative"
	  ]
  }
  ```

 **Delete a car advert**
----
  Deletes an existing car advert.

* **URL**
  `/car/adverts/:id`

* **Method:**
  `DELETE`
  
*  **Query String Params**
   None

* **Data Params**
None

* **Success Response:**
 
  * **Code:** 204 (No content)
  
    **Content:** None

* **Error Response**

  * **Code:** 404 (Not found)
  
  * **Explanation:** This is returned if a car advert with given id is not found.

