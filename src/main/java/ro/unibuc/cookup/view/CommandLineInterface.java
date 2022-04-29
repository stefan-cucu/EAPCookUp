package ro.unibuc.cookup.view;

import ro.unibuc.cookup.domain.Delivery;
import ro.unibuc.cookup.domain.Supermarket;
import ro.unibuc.cookup.domain.persons.Courier;
import ro.unibuc.cookup.domain.persons.PaymentCard;
import ro.unibuc.cookup.domain.persons.Person;
import ro.unibuc.cookup.domain.persons.User;
import ro.unibuc.cookup.domain.products.*;
import ro.unibuc.cookup.service.DeliveryServices;
import ro.unibuc.cookup.service.PersonServices;
import ro.unibuc.cookup.service.ProductServices;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Scanner;

public class CommandLineInterface {

    private Scanner s = new Scanner(System.in);

    private DeliveryServices deliveryServices = new DeliveryServices();
    private PersonServices personServices = new PersonServices();
    private ProductServices productServices = new ProductServices();

    public static void main(String args[]) {
        CommandLineInterface cli = new CommandLineInterface();
//        cli.loadMockData();

        System.out.println("\n" +
                " _____                _     _   _         _ \n" +
                "/  __ \\              | |   | | | |       | |\n" +
                "| /  \\/  ___    ___  | | __| | | | _ __  | |\n" +
                "| |     / _ \\  / _ \\ | |/ /| | | || '_ \\ | |\n" +
                "| \\__/\\| (_) || (_) ||   < | |_| || |_) ||_|\n" +
                " \\____/ \\___/  \\___/ |_|\\_\\ \\___/ | .__/ (_)\n" +
                "                                  | |       \n" +
                "                                  |_|       ");

        while (true) {
            cli.showMenu();
            int option = cli.readOption(9);
            cli.execute(option);
        }
    }

    private void showMenu() {
        System.out.println("|==========================================|");
        System.out.println("\n" +
                "1. Add a new product\n" +
                "2. Add a new person\n" +
                "3. Add a new recipe\n" +
                "4. Add a new supermarket\n" +
                "5. Modify supermarket stock\n" +
                "6. Create new order\n" +
                "7. Finish order\n" +
                "8. Reports\n" +
                "0. Exit");
        System.out.println("Option: ");
    }

    private int readOption(int max) {
        try {
            int option = Integer.parseInt(s.nextLine());
            if (option >= 0 && option <= max) {
                return option;
            }
        } catch (Exception invalid) {
            // nothing to do, as it's handled below
        }
        System.out.println("Invalid option. Try again");
        return readOption(max);
    }

    private void execute(int option) {
        switch (option) {
            case 1:
                try {
                    addProduct();
                }
                catch (Exception e) {
                    System.out.println("Error: " + e.getMessage());
                }
                break;
            case 2:
                try {
                    addPerson();
                }
                catch (Exception e) {
                    System.out.println("Error: " + e.getMessage());
                }
                break;
            case 3:
                try {
                    addRecipe();
                }
                catch (Exception e) {
                    System.out.println("Error: " + e.getMessage());
                }
                break;
            case 4:
                try {
                    addSupermarket();
                }
                catch (Exception e) {
                    System.out.println("Error: " + e.getMessage());
                }
                break;
            case 5:
                try {
                    modifySupermarketStock();
                }
                catch (Exception e) {
                    System.out.println("Error: " + e.getMessage());
                }
                break;
            case 6:
                try {
                    createOrder();
                }
                catch (Exception e) {
                    System.out.println("Error: " + e.getMessage());
                }
                break;
            case 7:
                try {
                    finishOrder();
                }
                catch (Exception e) {
                    System.out.println("Error: " + e.getMessage());
                }
                break;
            case 8:
                reports();
                break;
            case 0:
                System.exit(0);
                break;
            default:
                System.out.println("Invalid option");
        }
    }

    private void addProduct() throws IllegalArgumentException {
        String type;
        System.out.println("Enter product type[ingredient/tool]: ");
        while (true) {
            String productType = s.nextLine();
            if(productType.equals("ingredient") || productType.equals("tool")) {
                type = productType;
                break;
            }
            else {
                System.out.println("Invalid product type");
            }
        }
        System.out.println("Enter product name: ");
        String name = s.nextLine();

        System.out.println("Enter product description: ");
        String description = s.nextLine();

        System.out.println("Enter product brand: ");
        String brand = s.nextLine();

        System.out.println("Enter product price: ");
        float price = Float.parseFloat(s.nextLine());

        System.out.println("Enter product manufacturing date[yyyy-MM-dd]: ");
        Date manufacturingDate;
        while (true) {
            try {
                manufacturingDate = new SimpleDateFormat("yyyy-MM-dd").parse(s.nextLine());
                break;
            }
            catch (ParseException e) {
                System.out.println("Invalid date format. Try again");
            }
        }

        if(type.equals("ingredient")) {
            System.out.println("Enter product storage type[FRIDGE/FREEZER/WARMER]: ");
            String storageType;
            while (true) {
                storageType = s.nextLine();
                if (storageType.equals("FRIDGE") || storageType.equals("FREEZER") || storageType.equals("WARMER")) {
                    break;
                }
                else {
                    System.out.println("Invalid storage type");
                }
            }
            System.out.println("Enter product expiration date[yyyy-MM-dd]: ");
            Date expirationDate;
            while (true) {
                try {
                    expirationDate = new SimpleDateFormat("yyyy-MM-dd").parse(s.nextLine());
                    break;
                }
                catch (ParseException e) {
                    System.out.println("Invalid date format. Try again");
                }
            }
            System.out.println("Enter product country of origin: ");
            String countryOfOrigin = s.nextLine();

            productServices.addIngredient(name, description, price, manufacturingDate, brand, storageType, expirationDate, countryOfOrigin);
        }

        else {
            System.out.println("Enter product material: ");
            String material = s.nextLine();

            System.out.println("Enter product category: ");
            String category = s.nextLine();

            System.out.println("Enter product profit rate: ");
            float profitRate = Float.parseFloat(s.nextLine());

            productServices.addTool(name, description, price, manufacturingDate, brand, material, category, profitRate);
        }
    }

    private void addPerson() throws IllegalArgumentException {
        System.out.println("Enter person type[user/courier]: ");
        String personType;
        while (true) {
            String type = s.nextLine();
            if(type.equals("user") || type.equals("courier")) {
                personType = type;
                break;
            }
            else {
                System.out.println("Invalid person type");
            }
        }

        System.out.println("Enter person first name: ");
        String firstName = s.nextLine();

        System.out.println("Enter person last name: ");
        String lastName = s.nextLine();

        System.out.println("Enter person email: ");
        String email = s.nextLine();

        System.out.println("Enter person phone number: ");
        String phoneNumber = s.nextLine();

        System.out.println("Enter person address: ");
        String address = s.nextLine();

        if(personType.equals("user")) {
            System.out.println("Enter person password: ");
            String password = s.nextLine();

            System.out.println("Add card?[y/n]: ");
            while (true) {
                String answer = s.nextLine();
                if(answer.equals("y")) {
                    break;
                }
                else if(answer.equals("n")) {
                    personServices.createUser(firstName, lastName, email, phoneNumber, address, password);
                    return;
                }
                else {
                    System.out.println("Invalid answer");
                }
            }

            System.out.println("Enter card number: ");
            String cardNumber = s.nextLine();

            System.out.println("Enter card name: ");
            String cardName = s.nextLine();

            System.out.println("Enter card expiration date: ");
            String expirationDate = s.nextLine();

            System.out.println("Enter card CVV: ");
            String cvv = s.nextLine();

            personServices.createUser(firstName, lastName, email, phoneNumber, address, password);
            personServices.addCardToUser(personServices.getUsersSize() - 1, cardNumber, cardName, expirationDate, cvv);
        }
        else {
            System.out.println("Enter person salary: ");
            float salary = Float.parseFloat(s.nextLine());

            System.out.println("Enter person nationality: ");
            String nationality = s.nextLine();

            System.out.println("Enter person employment date[yyyy-MM-dd]:");
            Date employmentDate;
            while (true) {
                try {
                    employmentDate = new SimpleDateFormat("yyyy-MM-dd").parse(s.nextLine());
                    break;
                }
                catch (ParseException e) {
                    System.out.println("Invalid date format. Try again");
                }
            }
            personServices.createCourier(firstName, lastName, email, phoneNumber, address, salary, nationality, employmentDate);
        }
    }

    private void addRecipe() throws IllegalArgumentException {
        System.out.println("Enter recipe name:");
        String name = s.nextLine();

        System.out.println("Enter number of recipe ingredinets:");
        int numberOfIngredients = Integer.parseInt(s.nextLine());

        HashMap<Ingredient, Integer> ingredients = new HashMap<Ingredient, Integer>();
        for(int i = 0; i < numberOfIngredients; i++) {
            System.out.println("Enter ingredient[" + i + "] index:");
            int index = Integer.parseInt(s.nextLine());

            System.out.println("Enter ingredient[" + i + "] quantity:");
            int quantity = Integer.parseInt(s.nextLine());

            try {
                ingredients.put(productServices.getIngredient(index), quantity);
            } catch (Exception e) {
                System.out.println("Invalid ingredient index! Try again");
                i--;
            }
        }

        System.out.println("Enter recipe description:");
        String description = s.nextLine();

        System.out.println("Enter recipe time estimate:");
        float timeEstimate = Float.parseFloat(s.nextLine());

        System.out.println("Enter recipe profit rate:");
        float profitRate = Float.parseFloat(s.nextLine());

        productServices.addRecipe(ingredients, name, description, timeEstimate, profitRate);
    }

    private void addSupermarket() throws IllegalArgumentException {
        System.out.println("Enter supermarket name:");
        String name = s.nextLine();

        System.out.println("Enter supermarket address:");
        String address = s.nextLine();

        System.out.println("Enter supermarket nr of products:");
        int nrOfProducts = Integer.parseInt(s.nextLine());

        HashMap<Product, Integer> products = new HashMap<Product, Integer>();
        for(int i = 0; i < nrOfProducts; i++) {
            System.out.println("Enter product[" + i + "] index:");
            int index = Integer.parseInt(s.nextLine());

            System.out.println("Enter product[" + i + "] quantity:");
            int quantity = Integer.parseInt(s.nextLine());

            try {
                products.put(productServices.getProduct(index), quantity);
            }
            catch (Exception e) {
                System.out.println("Invalid product index! Try again");
            }
        }
        deliveryServices.addSupermarket(name, address, products);
    }

    private void modifySupermarketStock() throws IllegalStateException {
        if(deliveryServices.getSupermarkets().size() == 0) {
            throw new IllegalStateException("No supermarkets in the system!");
        }
        Supermarket supermarket;
        System.out.println("Enter supermarket id:");
        while(true){
            int id = Integer.parseInt(s.nextLine());
            try {
                supermarket = deliveryServices.getSupermarket(id);
                break;
            }
            catch (Exception e) {
                System.out.println("Invalid supermarket id! Try again");
            }
        }

        System.out.println("Enter number of products:");
        int nrOfProducts = Integer.parseInt(s.nextLine());

        HashMap<Product, Integer> products = new HashMap<Product, Integer>();
        for(int i = 0; i < nrOfProducts; i++) {
            System.out.println("Enter product[" + i + "] index:");
            int indexProduct = Integer.parseInt(s.nextLine());

            System.out.println("Enter product[" + i + "] quantity:");
            int quantity = Integer.parseInt(s.nextLine());

            try {
                products.put(productServices.getProduct(indexProduct), quantity);
            }
            catch (Exception e) {
                System.out.println("Invalid product index! Try again");
            }
        }
        deliveryServices.updateSupermarketStock(supermarket, products);
    }

    private void createOrder() throws IllegalArgumentException {
        System.out.println("Enter user id:");
        int userId;
        while(true) {
            userId = Integer.parseInt(s.nextLine());
            if(personServices.checkUserExists(userId)) {
                break;
            }
            else {
                System.out.println("Invalid user id! Try again");
            }
        }

        System.out.println("Enter number of purchases:");
        int nrOfPurchases = Integer.parseInt(s.nextLine());

        HashMap<Purchasable, Integer> cart = new HashMap<Purchasable, Integer>();
        for(int i = 0; i < nrOfPurchases; i++) {
            System.out.println("Enter purchase type[tool/recipe]:");
            String type;
            while (true) {
                type = s.nextLine();
                if (!type.equals("tool") && !type.equals("recipe")) {
                    System.out.println("Invalid purchase type! Try again");
                    continue;
                }
                break;
            }
            if(type.equals("tool")) {
                while (true){
                    System.out.println("Enter tool index:");
                    int index = Integer.parseInt(s.nextLine());

                    System.out.println("Enter tool quantity:");
                    int quantity = Integer.parseInt(s.nextLine());

                    try {
                        cart.put(productServices.getTool(index), quantity);
                        break;
                    }
                    catch (Exception e) {
                        System.out.println("Invalid tool index! Try again");
                    }
                }
            }
            else {
                while (true){
                    System.out.println("Enter recipe index:");
                    int index = Integer.parseInt(s.nextLine());

                    System.out.println("Enter recipe quantity:");
                    int quantity = Integer.parseInt(s.nextLine());

                    try {
                        cart.put(productServices.getRecipe(index), quantity);
                        break;
                    }
                    catch (Exception e) {
                        System.out.println("Invalid recipe index! Try again");
                    }
                }
            }
        }
        Courier courier = personServices.getAvailableCourier();
        try {
            deliveryServices.addDelivery(personServices.getUser(userId), courier, new Date(), cart);
        }
        catch (Exception e) {
            // Make sure that the courier is made available again
            personServices.setCourierAvailable(courier.getPersonId());
            throw e;
        }
    }

    private void finishOrder() throws IllegalStateException {
        if(deliveryServices.getDeliveries().size() == 0) {
            throw new IllegalStateException("There are no deliveries to finish!");
        }
        Delivery delivery;
        System.out.println("Enter order id:");
        while (true){
            try {
                int id = Integer.parseInt(s.nextLine());
                delivery = deliveryServices.getDelivery(id);
                break;
            }
            catch (Exception e) {
                System.out.println("Invalid order id! Try again");
            }
        }
        personServices.setCourierAvailable(delivery.getCourier().getPersonId());
        deliveryServices.finishDelivery(delivery);
    }

    private void reports() {
        System.out.println("|==========================================|");
        System.out.println("\n" +
                "1. Show all products\n" +
                "2. Show all persons\n" +
                "3. Show all recipes\n" +
                "4. Show all supermarkets\n" +
                "5. Show all orders\n" +
                "0. Back\n");
        System.out.println("Option: ");
        int option = readOption(5);
        switch (option) {
            case 1:
                for(Product p : productServices.getAllProducts()) {
                    if(p instanceof Ingredient) {
                        System.out.println("[Ingredient]" + p.getProductId() + " " + p.getName() + " " +
                                p.getDescription() + " " + p.getBrand() + " " + p.getPrice() + " " + p.getManufacturingDate()
                                + " " + ((Ingredient) p).getStorageType() + " " + ((Ingredient) p).getExpirationDate()
                                + " " + ((Ingredient) p).getCountryOfOrigin());
                    }
                    else {
                        System.out.println("[Tool]" + p.getProductId() + " " + p.getName() + " " +
                                p.getDescription() + " " + p.getBrand() + " " + p.getPrice() + " " + p.getManufacturingDate()
                                + " " + ((Tool) p).getMaterial() + " " + ((Tool) p).getMaterial());
                    }
                }
                break;
            case 2:
                for(User u : personServices.getUsers()) {
                    System.out.println("[User]" + u.getPersonId() + " " + u.getFirstName() + " " + u.getLastName() + " " + u.getEmail() + " " + u.getPhone()
                            + " " + u.getAddress() + " " + u.getPassword());
                }
                for (Courier c : personServices.getCouriers()) {
                    System.out.println("[Courier]" + c.getPersonId() + " " + c.getFirstName() + " " + c.getLastName() + " " + c.getEmail() + " " + c.getPhone()
                            + " " + c.getAddress() + " " + c.getSalary() + " " + c.getNationality() + " " + c.getEmploymentDate()
                            + " " + c.getStatus().name());
                }
                break;
            case 3:
                for(Recipe r : productServices.getAllRecipes()) {
                    System.out.println("[Recipe]:" + r.getName() + " " + r.getRecipeId());
                    System.out.println("Description: " + r.getDescription());
                    System.out.println("Time estimate: " + r.getTimeEstimate());
                    System.out.println("Ingredients: ");
                    for(Ingredient p : r.getIngredients().keySet()) {
                        System.out.println("[Ingredient]" + p.getProductId() + " " + p.getName() + " " +
                                p.getDescription() + " " + p.getBrand() + " " + p.getPrice() + " " + p.getManufacturingDate()
                                + " " + ((Ingredient) p).getStorageType() + " " + ((Ingredient) p).getExpirationDate()
                                + " " + ((Ingredient) p).getCountryOfOrigin());
                        System.out.println("Quantity: " + r.getIngredients().get(p));
                        System.out.println("");
                    }
                }
                break;
            case 4:
                for(Supermarket s : deliveryServices.getSupermarkets()) {
                    System.out.println("[Supermarket]" + s.getName() + " " + s.getSupermarketId());
                    System.out.println("Address: " + s.getAddress());
                    System.out.println("Products: ");
                    for(Product p : s.getProducts().keySet()) {
                        if(p instanceof Ingredient) {
                            System.out.println("[Ingredient]" + p.getProductId() + " " + p.getName() + " " +
                                    p.getDescription() + " " + p.getBrand() + " " + p.getPrice() + " " + p.getManufacturingDate()
                                    + " " + ((Ingredient) p).getStorageType() + " " + ((Ingredient) p).getExpirationDate()
                                    + " " + ((Ingredient) p).getCountryOfOrigin());
                        }
                        else {
                            System.out.println("[Tool]" + p.getProductId() + " " + p.getName() + " " +
                                    p.getDescription() + " " + p.getBrand() + " " + p.getPrice() + " " + p.getManufacturingDate()
                                    + " " + ((Tool) p).getMaterial() + " " + ((Tool) p).getMaterial());
                        }
                        System.out.println("Quantity: " + s.getProducts().get(p));
                    }
                }
                break;
            case 5:
                for(Delivery d : deliveryServices.getDeliveries()) {
                    System.out.println("[Delivery]" + d.getDeliveryId());
                    System.out.println("[User]" + d.getUser().getPersonId() + " " + d.getUser().getFirstName() + " " + d.getUser().getLastName());
                    System.out.println("[Courier]" + d.getCourier().getPersonId() + " " + d.getCourier().getFirstName() + " " + d.getCourier().getLastName());
                    System.out.println("[Supermarket]" + d.getSupermarket().getSupermarketId() + " " + d.getSupermarket().getName());
                    System.out.println("Date: " + d.getDate());
                    System.out.println("Status: " + d.getDeliveryStatus());
                    System.out.println("Products: ");
                    for(Product p : d.getProducts().keySet()) {
                        if(p instanceof Ingredient) {
                            System.out.println("[Ingredient]" + p.getProductId() + " " + p.getName() + " " +
                                    p.getDescription() + " " + p.getBrand() + " " + p.getPrice() + " " + p.getManufacturingDate()
                                    + " " + ((Ingredient) p).getStorageType() + " " + ((Ingredient) p).getExpirationDate()
                                    + " " + ((Ingredient) p).getCountryOfOrigin());
                        }
                        else {
                            System.out.println("[Tool]" + p.getProductId() + " " + p.getName() + " " +
                                    p.getDescription() + " " + p.getBrand() + " " + p.getPrice() + " " + p.getManufacturingDate()
                                    + " " + ((Tool) p).getMaterial() + " " + ((Tool) p).getMaterial());
                        }
                        System.out.println("Quantity: " + d.getProducts().get(p));
                    }
                }
                break;
            case 0:
                break;
        }
    }

    private void loadMockData() {
        productServices.addIngredient("Mar", "Un mar", 10, new Date(), "Mere SRL", "FREEZER", new Date(), "Romania");
        productServices.addTool("Bol", "Un bol", 10, new Date(), "Boluri SRL", "Lemn", "Recipiente", 0.2f);

//        personServices.createUser("Laurentiu", "Pascal", "laurpascal@gmail.com", "+407294201", "Str Plopilor 20", "pass");
//        personServices.createCourier("Ion", "Ionescu", "ionionescu@gmail.com", "+407294202", "Str Plopilor 40", 3000, "Romana", new Date());

//        HashMap<Ingredient, Integer> ingredients = new HashMap<>();
//        ingredients.put(productServices.getIngredient(1001), 1);
//        productServices.addRecipe(ingredients, "Placinta", "Placinta de mere", 15, 0.2f);
//
//        HashMap<Product, Integer> products = new HashMap<>();
//        products.put(productServices.getProduct(1001), 3);
//        products.put(productServices.getProduct(1002), 1);
//        deliveryServices.addSupermarket("Supermarket 1", "Str Plopilor 2", products);
    }
}
