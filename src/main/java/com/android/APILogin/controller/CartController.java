package com.android.APILogin.controller;

import com.android.APILogin.dto.request.CartDto;
import com.android.APILogin.dto.response.ResponseData;
import com.android.APILogin.service.impl.CartServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("api/cart")
public class CartController {
    @Autowired
    private CartServiceImpl cartServiceImpl;

    @GetMapping("/user/{userId}")
    public ResponseData<List<CartDto>> getCartByUserId(@PathVariable Long userId) {
        List<CartDto> carts = cartServiceImpl.getCartByUserId(userId);
        if(carts == null) {
            return new ResponseData<>(HttpStatus.NOT_FOUND.value(), "cart not found", carts);
        }
        else{
            return new ResponseData<>(HttpStatus.OK.value(), "cart found", carts);
        }
    }

    @PostMapping("/add-update")
    public ResponseData<CartDto> addOrUpdate(@RequestBody CartDto cart) {
        CartDto dto = cartServiceImpl.addOrUpdateCart(cart);
        if(dto == null) {
            return new ResponseData<>(HttpStatus.NOT_FOUND.value(), "Successfully", dto);
        }
        else{
            return new ResponseData<>(HttpStatus.OK.value(), "cart found", dto);
        }
    }

    @PostMapping("/update")
    public ResponseData<CartDto> updateCart(@RequestBody CartDto cart) {
        CartDto dto = cartServiceImpl.updateCart(cart);
        if(dto == null) {
            return new ResponseData<>(HttpStatus.NOT_FOUND.value(), "Successfully", dto);
        }
        else{
            return new ResponseData<>(HttpStatus.OK.value(), "cart found", dto);
        }
    }

    @DeleteMapping("/remove/{cartId}")
    public ResponseData<Void> removeCart(@PathVariable Long cartId) {
        try {
            cartServiceImpl.deleteCartItem(cartId);
            return new ResponseData<>(HttpStatus.OK.value(), "Delete successfully", null);
        } catch (Exception ex) {
            return new ResponseData<>(HttpStatus.NOT_FOUND.value(), "cart not found: " +ex.getMessage(), null);
        }
    }

    @DeleteMapping("/remove/all/{userId}")
    public ResponseData<Void> removeAll(@PathVariable Long userId) {
        try {
            cartServiceImpl.deleteAllCarts(userId);
            return new ResponseData<>(HttpStatus.OK.value(), "Delete successfully", null);
        } catch (Exception ex) {
            return new ResponseData<>(HttpStatus.NOT_FOUND.value(), "cart not found: " +ex.getMessage(), null);
        }
    }
}
