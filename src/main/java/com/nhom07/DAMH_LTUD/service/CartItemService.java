package com.nhom07.DAMH_LTUD.service;

import com.nhom07.DAMH_LTUD.model.CartItemVM;
import jakarta.transaction.Transactional;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CartItemService {
    private final List<CartItemVM> cartItems = new ArrayList<>();

    public List<CartItemVM> getCartItems() {
        return cartItems;
    }
}
