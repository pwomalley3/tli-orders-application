# TLI Orders Application

#### Problem Statement

Create REST API for an order management application to be consumed by an Angular front-end.

Orders represent requests for the purchase of different products represented as line items on an order.

#### Application Set Up

This repository contains a Spring Boot application built with Maven. There is a `pom.xml` with some dependencies to get
you started. There are no strict requirements for the libraries that are to be used. You may use other libraries as
long as the approach used is consistent across all supported operations.

The `application.properties` includes some default settings:

* `server.port`: 8085 (This may be changed as needed)
* `spring.datasource`: H2 configuration
  * Once the app is running, an H2 console can be viewed in the browser: `http://localhost:8085/h2-console/`

There are starter schema and data in the `resources` folder in the `data.sql` and `schema.sql` files. Please add any 
schema and data changes in these files.

Please make a fork of this repository to hold your solution and send a link to your fork once development is complete.

#### Requirements

* Order: Each order should have an order id, status, and line items along with a date placed timestamp
* Line Item: Each line item should have a number, name of the product, price, and quantity
  * Number should be unique only within each order
  * Price should not be negative
  * Quantity should be at least 1
  * A line item should not exist without an order
* We should be able to place an order, cancel an order, and view an order
* Orders that are In Transit or Delivered cannot be canceled
* We should be able to change the quantity of a line item on an existing order
* We should be able to remove a line item from an existing order

#### Supported Operations

* VIEW ORDER
* PLACE ORDER
* CANCEL ORDER
* CHANGE QUANTITY
* REMOVE LINE ITEM

Please use consistent naming conventions for each endpoint.

**Output**

Each operation should return a view of the order along with its line items.

##### PLACE ORDER EXAMPLE

**Sample Input**

```
{
  "items": [
    {
      "name": "Widget",
      "price": 20.00,
      "quantity": 1
    },
    {
      "name": "Widget",
      "price": 35.00,
      "quantity": 2
    }
  ]
}
```

**Sample Output**
```
{
  "id": 6,
  "status": "New",
  "lineItems": [
    {
      "number": "1",
      "orderId": 6,
      "name": "Widget",
      "price": 20.00,
      "quantity": 1
    },
    {
      "number": "2",
      "orderId": 6,
      "name": "Widget",
      "price": 35.00,
      "quantity": 2
    }
  ]
}
```

#### Expectations

* The application should be built using Spring Boot and Maven
* The application should be a REST API supporting JSON inputs and outputs 
* The application should be functional
* The application should be able to accommodate new requirements
* Code should be modular and readable
* Code should address separation of concerns
* Code should be unit tested

**Optional**

These should only be done if there is time left.

_Please Note_: these requirements may need some additional schema.

* Add support for attaching an order to a customer
* Add ability to view all orders for a customer 