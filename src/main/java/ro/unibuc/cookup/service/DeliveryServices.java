package ro.unibuc.cookup.service;

import ro.unibuc.cookup.domain.Delivery;
import ro.unibuc.cookup.domain.Supermarket;
import ro.unibuc.cookup.domain.persons.Courier;
import ro.unibuc.cookup.domain.persons.User;
import ro.unibuc.cookup.domain.products.Product;
import ro.unibuc.cookup.domain.products.Purchasable;
import ro.unibuc.cookup.domain.products.Recipe;
import ro.unibuc.cookup.persistence.DeliveryRepository;
import ro.unibuc.cookup.persistence.SupermarketRepository;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

public class DeliveryServices {
    private DeliveryRepository deliveryRepository = new DeliveryRepository();
    private SupermarketRepository supermarketRepository = new SupermarketRepository();

    public void addDelivery(User user, Courier courier, Date date, HashMap<Purchasable, Integer> purchases){
        if(user == null || courier == null || date == null || purchases == null){
            throw new IllegalArgumentException("Arguments cannot be null");
        }
        HashMap<Product, Integer> products = new HashMap<Product, Integer>();
        for(Purchasable purchasable : purchases.keySet()){
            if(purchasable instanceof Product){
                products.put((Product) purchasable, purchases.get(purchasable));
            }
            else {
                Recipe recipe = (Recipe) purchasable;
                for(Product product : recipe.getIngredients().keySet()){
                    if(products.containsKey(product)){
                        products.put(product, products.get(product) + recipe.getIngredients().get(product) * purchases.get(purchasable));
                    }
                    else{
                        products.put(product, recipe.getIngredients().get(product) * purchases.get(purchasable));
                    }
                }
            }
        }
        Supermarket supermarket = supermarketRepository.findSupermarketWithProducts(products);
        if(supermarket == null){
            throw new IllegalArgumentException("No supermarket found with the given products");
        }
        deliveryRepository.add(new Delivery(user, courier, date, "IN_PROGRESS", products, supermarket));
    }

    public void addSupermarket(String name, String address, HashMap<Product, Integer> products){
        if(name == null || address == null){
            throw new IllegalArgumentException("Arguments cannot be null");
        }
        if(products == null){
            products = new HashMap<Product, Integer>();
        }
        supermarketRepository.add(new Supermarket(name, address, products));
    }

    public void updateSupermarketStock(Supermarket supermarket, HashMap<Product, Integer> products){
        if(supermarket == null || products == null){
            throw new IllegalArgumentException("Arguments cannot be null");
        }
        Supermarket copy = supermarket;
        for(Product product : products.keySet()){
            if(copy.getProducts().containsKey(product)){
                copy.getProducts().put(product, copy.getProducts().get(product) + products.get(product));
            }
            else{
                copy.getProducts().put(product, products.get(product));
            }
        }
        supermarketRepository.update(supermarket, copy);
    }

    public void finishDelivery(Delivery delivery){
        if(delivery == null){
            throw new IllegalArgumentException("Argument cannot be null");
        }
        Delivery copy = delivery;
        copy.setDeliveryStatus("DELIVERED");
        deliveryRepository.update(delivery, copy);
    }

    public void deleteDelivery(Delivery delivery){
        if(delivery == null){
            throw new IllegalArgumentException("Argument cannot be null");
        }
        deliveryRepository.delete(delivery);
    }

    public void deleteSupermarket(Supermarket supermarket){
        if(supermarket == null){
            throw new IllegalArgumentException("Argument cannot be null");
        }
        supermarketRepository.delete(supermarket);
    }

    public Delivery getDelivery(int id){
        return deliveryRepository.findById(id);
    }

    public Supermarket getSupermarket(int id){
        return supermarketRepository.findById(id);
    }

    public ArrayList<Delivery> getDeliveries(){
        return deliveryRepository.getAll();
    }

    public ArrayList<Supermarket> getSupermarkets(){
        return supermarketRepository.getAll();
    }
}
