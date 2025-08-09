import com.shop.client.BankingClient;
import com.shop.dto.AddToCart;
import com.shop.dto.PlaceOrder;
import com.shop.dto.ViewCart;
import com.shop.exception.ProductNotFoundException;
import com.shop.exception.UserNotFoundException;
import com.shop.model.*;
import com.shop.repository.OrderItemRepository;
import com.shop.repository.OrderRepository;
import com.shop.repository.ProductRepository;
import com.shop.service.OrderAndPurchaseService;
import lombok.AllArgsConstructor;
import org.apache.logging.log4j.core.util.Assert;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;


import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
public class OrderAndPurchaseTest {
    @Mock
    private BankingClient bankingClient;
    @Mock
    private ProductRepository productRepository;
    @Mock
    private OrderRepository orderRepository;
    @Mock
    private OrderItemRepository orderItemRepository;
    @InjectMocks
    OrderAndPurchaseService service;

    @Test
    public void getAllProducts(){
        List<Product> products = new ArrayList<>();
        when(productRepository.findAll()).thenReturn(products);
        List<Product> allProducts = service.getAllProducts();
        Assert.isNonEmpty(allProducts);
    }

    @Test
    public void addNonExistingProductToCart(){

        List<AddToCart> cart = List.of(new AddToCart(1L, 5));
        when(productRepository.findById(1L)).thenReturn(Optional.empty());
        assertThrows(ProductNotFoundException.class,()->{
            service.addItemToCart(cart, 9L);
        });
    }

    @Test
    public void addProductToCart(){
        List<AddToCart> cart = List.of(new AddToCart(1L, 5));
        Order order = new Order(8L, 1l, new Date());
        List<Product> products = List.of(new Product(1L,"Mobile","Android",10000.78));
        when(productRepository.findById(1L)).thenReturn(Optional.ofNullable(products.get(0)));
        when(orderRepository.save(any(Order.class))).thenReturn(order);
        String msg= service.addItemToCart(cart, 5L);
        verify(orderRepository).save(any(Order.class));
        verify(orderItemRepository).save(any(OrderItem.class));
        assertEquals("Items added to cart",msg);
    }

    @Test
    public void viewCartWhenNoItemsInTheCart(){
        when(orderRepository.findByUserId(2L)).thenReturn( new ArrayList<>());
        List<ViewCart> viewCarts = service.viewCart(2L);
        assertEquals(null,viewCarts);
    }

    public void mockCommonObjects(){
        Order order1 = new Order(2L, 5L, new Date());
        Order order2 = new Order(3L, 5L, new Date());
        OrderItem orderItem1 = new OrderItem(2L,1L,3,100.88, OrderStatus.CART,new Date());
        OrderItem orderItem2 =  new OrderItem(3L,1L,3,100.88, OrderStatus.CART,new Date());
        when(orderRepository.findByUserId(5L)).thenReturn(List.of(order1, order2));
        when(orderItemRepository.findByOrderIdAndStatus(2L,OrderStatus.CART)).thenReturn(List.of(orderItem1));
        when(orderItemRepository.findByOrderIdAndStatus(3L,OrderStatus.CART)).thenReturn(List.of(orderItem2));

    }

    @Test
    public void viewCart(){
        List<Product> products = List.of(new Product(1L,"Mobile","Android",10000.78));
        when(productRepository.findById(1L)).thenReturn(Optional.ofNullable(products.get(0)));
        mockCommonObjects();
       List<ViewCart> viewCarts = service.viewCart(5L);
        assertNotNull(viewCarts);

    }

    @Test
    public void placeMyOrderAndPay(){
        mockCommonObjects();
        String msg = service.placeMyOrderAndPay(new PlaceOrder(5L, 7348354567L, List.of(1L)));
        assertEquals("Payment successful",msg);

    }

    @Test
    public void negativePlaceOrderScenario(){
        when(orderRepository.findByUserId(5L)).thenReturn(Collections.emptyList());
        String msg = service.placeMyOrderAndPay(new PlaceOrder(5L, 7348354567L, List.of(1L)));
        assertEquals("No order found and nothing purchased",msg);

    }
}
