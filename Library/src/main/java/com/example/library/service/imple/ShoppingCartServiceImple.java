
package com.example.library.service.imple;

import com.example.library.dto.ProductDto;
import com.example.library.model.*;
import com.example.library.repository.CartItemRepository;
import com.example.library.repository.ShoppingCartRepository;
import com.example.library.service.CustomerService;
import com.example.library.service.ShoppingCartService;

import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class ShoppingCartServiceImple implements ShoppingCartService {
    private CustomerService customerService;
    private CartItemRepository cartItemRepository;
    private ShoppingCartRepository shoppingCartRepository;

    public ShoppingCartServiceImple(CustomerService customerService,CartItemRepository cartItemRepository,ShoppingCartRepository shoppingCartRepository) {
        this.customerService = customerService;
//        this.sizeService = sizeService;
        this.cartItemRepository =cartItemRepository;
        this.shoppingCartRepository=shoppingCartRepository;
    }

//    @Override
//    public ShoppingCart addItemToCart(ProductDto productDto, int quantity, String username) {
//
//        Customer customer=customerService.findByEmail(username);
//        ShoppingCart shoppingCart=customer.getCart();
//        if(shoppingCart==null){
//            shoppingCart=new ShoppingCart();
//        }
////        Size size= sizeService.findById(sizeId);
//        Set<CartItem>cartItemList=shoppingCart.getCartItems();
//        CartItem cartItem=find(cartItemList,productDto.getId());
//        System.out.println(productDto.getId());
//        Product product=transfer(productDto);
//        System.out.println(product);
//
//        double  unitPrice=0;
//
//        if(productDto.getSalesPrice()==0){
//            unitPrice=productDto.getCostPrice();
//        }else{
//            unitPrice=productDto.getSalesPrice();
//        }
//
//        int itemQuantity=0;
//        if(cartItemList==null){
//            cartItemList=new HashSet<>();
//            if(cartItemList==null){
//                cartItem=new CartItem();
//                cartItem.setProduct(product);
//                cartItem.setCart(shoppingCart);
//                cartItem.setQuantity(quantity);
////             cartItem.setSize(size.getName());
//                cartItem.setUnitPrice(unitPrice);
//                cartItem.setCart(shoppingCart);
//                cartItemList.add(cartItem);
//                cartItemRepository.save(cartItem);
//            }else{
//                itemQuantity=cartItem.getQuantity()+quantity;
//                cartItem.setQuantity(itemQuantity);
//                cartItemRepository.save(cartItem);
//            }
//        } else{
//            if(cartItem==null){
//                cartItem=new CartItem();
//                cartItem.setProduct(product);
//                cartItem.setCart(shoppingCart);
//                cartItem.setQuantity(quantity);
////                cartItem.setSize(size.getName());
//                cartItem.setUnitPrice(unitPrice);
//                cartItem.setCart(shoppingCart);
//                cartItemList.add(cartItem);
//                cartItemRepository.save(cartItem);
//            }
////            else if(size.getName().equals(cartItem.getSize())){
////                itemQuantity=cartItem.getQuantity()+quantity;
////                cartItem.setQuantity(itemQuantity);
////                cartItemRepository.save(cartItem);
////            }
//            else{
//                cartItem=new CartItem();
//                cartItem.setProduct(product);
//                cartItem.setCart(shoppingCart);
//                cartItem.setQuantity(quantity);
////                cartItem.setSize(size.getName());
//                cartItem.setUnitPrice(unitPrice);
//                cartItem.setCart(shoppingCart);
//                cartItemList.add(cartItem);
//                cartItemRepository.save(cartItem);
//            }
//        }
//        shoppingCart.setCartItems(cartItemList);
//
//        double totalPrice=totalPrice(shoppingCart.getCartItems());
//        int totalItem=totalItem(shoppingCart.getCartItems());
//
//        shoppingCart.setTotalPrice(totalPrice);
//        shoppingCart.setTotalItems(totalItem);
//        shoppingCart.setCustomer(customer);
//
//
//        return shoppingCartRepository.save(shoppingCart);
//    }
@Override
public ShoppingCart addItemToCart(ProductDto productDto, int quantity, String username) {
    Customer customer = customerService.findByEmail(username);
    ShoppingCart shoppingCart = customer.getCart();

    if (shoppingCart == null) {
        shoppingCart = new ShoppingCart();
        shoppingCart.setCustomer(customer);
    }

    Set<CartItem> cartItemList = shoppingCart.getCartItems();
    CartItem cartItem = findCartItemByProductId(cartItemList, productDto.getId());

    Product product = transfer(productDto);
    double unitPrice = (productDto.getSalesPrice() == 0) ? productDto.getCostPrice() : productDto.getSalesPrice();

    if (cartItem != null) {
        // Product is there; update the quantity
        int newQuantity = cartItem.getQuantity() + quantity;
        cartItem.setQuantity(newQuantity);
    } else {
        // Product is not in the cart; create a new CartItem
        CartItem newCartItem = new CartItem();
        newCartItem.setProduct(product);
        newCartItem.setCart(shoppingCart);
        newCartItem.setQuantity(quantity);
        newCartItem.setUnitPrice(unitPrice);
        cartItemList.add(newCartItem);
    }

    // Update the total price and total items in the shopping cart
    updateShoppingCartTotals(shoppingCart);

    shoppingCart.setCartItems(cartItemList);
    shoppingCartRepository.save(shoppingCart);
    return shoppingCart;
}

    private CartItem findCartItemByProductId(Set<CartItem> cartItems, long productId) {
        for (CartItem cartItem : cartItems) {
            if (cartItem.getProduct().getId() == productId) {
                return cartItem;
            }
        }
        return null; // Product is not in the cart
    }

    private void updateShoppingCartTotals(ShoppingCart shoppingCart) {
        double totalPrice = 0.0;
        int totalItems = 0;

        for (CartItem cartItem : shoppingCart.getCartItems()) {
            totalPrice += cartItem.getQuantity() * cartItem.getUnitPrice();
            totalItems += cartItem.getQuantity();
        }

        shoppingCart.setTotalPrice(totalPrice);
        shoppingCart.setTotalItems(totalItems);
    }

    @Override
    public ShoppingCart updateCart(ProductDto productDto, int quantity, String username, Long cart_Item_id) {
        Customer customer=customerService.findByEmail(username);
        ShoppingCart shoppingCart=customer.getCart();
        Set<CartItem>cartItemList=shoppingCart.getCartItems();
        CartItem item=find(cartItemList, productDto.getId(), cart_Item_id);

        int itemQuantity=quantity;
//        if(size_id!=0){
//            Size size=sizeService.findById(size_id);
//            item.setSize(size.getName());
//        }
        item.setQuantity(itemQuantity);
        cartItemRepository.save(item);
        shoppingCart.setCartItems(cartItemList);
        int totalItem= totalItem(cartItemList);
        double totalprice=totalPrice(cartItemList);
        shoppingCart.setTotalPrice(totalprice);
        shoppingCart.setTotalItems(totalItem);
        return shoppingCartRepository.save(shoppingCart);
    }

    @Override
    public ShoppingCart removeItemFromCart(ProductDto productDto, String username) {
        Customer customer=customerService.findByEmail(username);
        ShoppingCart shoppingCart=customer.getCart();
        Set<CartItem>  cartItemList=shoppingCart.getCartItems();
        CartItem item=find(cartItemList,productDto.getId());
//        long id=item.getId();
//
//        cartItemRepository.deleteById(id);
//        return null;
        cartItemList.remove(item);
        cartItemRepository.deleteById(item.getId());
        double totalPrice = totalPrice(cartItemList);
        int totalItem = totalItem(cartItemList);
        shoppingCart.setCartItems(cartItemList);
        shoppingCart.setTotalPrice(totalPrice);
        shoppingCart.setTotalItems(totalItem);
        if(cartItemList.isEmpty()){
            shoppingCart.setCustomer(null);
            shoppingCart.getCartItems().clear();
            shoppingCart.setTotalPrice(0);
            shoppingCart.setTotalItems(0);
        }
        return shoppingCartRepository.save(shoppingCart);

    }

    @Override
    public ShoppingCart deleteCartById(Long id) {
        return null;
    }

    private CartItem find(Set<CartItem>cartItems,long productId, String size){
        if(cartItems==null){
            return null;
        }
        CartItem cartItem=null;
        for(CartItem item:cartItems){
            if(item.getProduct().getId()==productId && size.equals(item.getSize())){
                cartItem=item;
            }
        }
        return cartItem;
    }

    private Product transfer(ProductDto productDto){
        Product  product=new Product();
        product.setId(productDto.getId());
        product.setName(productDto.getName());
//        product.setCurrentQuantity(productDto.getCurrentQuantity());
        product.setCostPrice(productDto.getCostPrice());
        product.setSalePrice(product.getSalePrice());
//        product.setShortDescription(productDto.getShortDescription());
//        product.setLongDescription(productDto.getLongDescription());
        product.setImages(productDto.getImage());
//        product.setColors(productDto.getColors());
//        product.setBrand(productDto.getBrand());
        product.set_activated(productDto.isActivated());
        product.setCategory(productDto.getCategory());
        return product;

    }
    private double totalPrice(Set<CartItem>cartItemList){
        double totalprice=0.0;
        for(CartItem item:cartItemList){
            totalprice+=item.getUnitPrice()*item.getQuantity();
        }
        return totalprice;
    }

    private int totalItem(Set<CartItem>cartItemlist){
        int totalItem=0;
        for(CartItem item:cartItemlist){
            totalItem+=item.getQuantity();
        }return totalItem;
    }
    private CartItem find(Set<CartItem>cartItems,long productId,long cart_Item_Id){
        if(cartItems==null){
            return null;
        }
        CartItem cartItem=null;
        for(CartItem item:cartItems){
            if(item.getProduct().getId()==productId && item.getId()==cart_Item_Id){
                cartItem=item;

            }
        }return cartItem;
    }

    private CartItem find(Set<CartItem> cartItems, long productId) {
        if (cartItems == null) {
            return null;
        }
        CartItem cartItem = null;
        for (CartItem item : cartItems) {
            if (item.getProduct().getId() == productId) {
                cartItem = item;
            }
        }
        return cartItem;
    }
}
