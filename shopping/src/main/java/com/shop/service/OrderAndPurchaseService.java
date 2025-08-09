package com.shop.service;

import com.shop.client.BankingClient;
import com.shop.client.PaymentRequest;
import com.shop.dto.AddToCart;
import com.shop.dto.PlaceOrder;
import com.shop.dto.ViewCart;
import com.shop.exception.ProductNotFoundException;
import com.shop.model.Order;
import com.shop.model.OrderItem;
import com.shop.model.OrderStatus;
import com.shop.model.Product;
import com.shop.repository.OrderItemRepository;
import com.shop.repository.OrderRepository;
import com.shop.repository.ProductRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class OrderAndPurchaseService {
    @Autowired
    private BankingClient bankingClient;
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private OrderItemRepository orderItemRepository;

    public List<Product> getAllProducts(){
        return productRepository.findAll();
    }

    @Transactional
    public String addItemToCart(List<AddToCart> c, Long userId){

        c.stream().forEach(e -> {
            productRepository.findById(e.getProductId())
                    .orElseThrow(() -> new ProductNotFoundException("Product ID not found: " + e.getProductId()));
        });

        Order save = orderRepository.save(new Order(userId, new Date()));
        Long orderId = save.getId();
     c.forEach(e-> {
         Product product = productRepository.findById(e.getProductId()).get();
         double totalPrice = product.getPrice() * e.getQuantity();
         OrderItem orderItem = new OrderItem(orderId, e.getProductId(), e.getQuantity(), totalPrice, OrderStatus.CART, new Date());
         orderItemRepository.save(orderItem);

     });
        return "Items added to cart";
    }
    @Transactional
    public List<ViewCart> viewCart(Long userId){
        List<Order> orders = orderRepository.findByUserId(userId);
        List<OrderItem> orderedItems = new ArrayList<>();
        //check if the user has entry in order table
        if(!orders.isEmpty()){
            orderedItems= orders.stream().flatMap(order ->
                    orderItemRepository.findByOrderIdAndStatus(order.getId(), OrderStatus.CART).stream()).collect(Collectors.toList());
            List<ViewCart> collect = orderedItems.stream().filter(e->e.getStatus()==OrderStatus.CART).map(e -> {
                String prodName = productRepository.findById(e.getProductId()).get().getName();
                int quantity = e.getQuantity();

                Double totalPrice = e.getTotalPrice();
                return new ViewCart(prodName, quantity, totalPrice);

            }).collect(Collectors.toList());

            return collect;
          }
        return null;


    }
    @Transactional
    public String placeMyOrderAndPay(PlaceOrder p){
        Double amountToPay = 0.0;
        List<Order> orders = orderRepository.findByUserId(p.getUserId());

        if( !orders.isEmpty()){
            List<OrderItem> allOrderedItems = orders.stream().flatMap(order ->
                    orderItemRepository.findByOrderIdAndStatus(order.getId(), OrderStatus.CART).
                            stream()).toList();

            //calculate price
            for (Long productId: p.getProductIds()){
                double totalForProduct  = allOrderedItems.stream().filter(e -> e.getProductId().equals(productId)).mapToDouble(OrderItem::getTotalPrice).sum();
                amountToPay += totalForProduct;
            }

            //interact with banking application
            PaymentRequest payment = new PaymentRequest(p.getAccountNo(), amountToPay, "E-commerce order payment");
            bankingClient.transfer(payment);

            //after successful payment, change the status to "PURCHASED"
            allOrderedItems.stream().forEach(e -> e.setStatus(OrderStatus.PURCHASED));
            orderItemRepository.saveAll(allOrderedItems);
            return "Payment successful";
        }
        return "No order found and nothing purchased";
        }

}
