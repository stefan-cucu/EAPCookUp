# Proiect EAP - CookUp!

CookUp is an app that offers users recipe meal kits and the tools required to make cooking easier.

## Project Structure

### Domain

The project contains the following entities:

- Person: represents a generic person
    - User: represents a user of the app
    - Courier: represents one of our couriers
- Product: represents a generic product
    - Ingredient: an ingredient used in recipes
    - Tool: a purchasable tool
- Recipe: purchasable recipe kit
- PaymentCard: the user's current payment card
- Delivery: an order assigned to a courier
- Supermarket: source of products for deliveries

### Persistence

Classes that hold data about the app's entities.

- GenericRepository<T>: interface implemented by all repositories
- CourierRepository: repository for couriers
- DeliveryRepository: repository for deliveries
- ProductRepository: repository for products
- RecipeRepository: repository for recipes
- SupermarketRepository: repository for supermarkets
- UserRepository: repository for users

### Services

The project's services have been split into three categories:
- DeliveryService: handles the delivery of products and supermarket stocks
- PersonService: handles the creation and management of users & couriers
- ProductService: handles the creation and management of products

### View

Class that facilitates the direct interaction with the user through the console.

The following actions and queries are available:
- Add a new ingredient
- Add a new tool
- Create a new user account
- Create a new courier account
- Create a new recipe
- Add a new supermarket
- Add new stock to a supermarket
- Create a new order
- Mark a delivery as finished
- Show the contents of repositories

