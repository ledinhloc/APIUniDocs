package com.android.APILogin.service.impl;

import com.android.APILogin.dto.mapper.CartMapper;
import com.android.APILogin.dto.request.CartDto;
import com.android.APILogin.entity.Cart;
import com.android.APILogin.entity.Document;
import com.android.APILogin.entity.User;
import com.android.APILogin.repository.CartRepository;
import com.android.APILogin.repository.DocumentRepository;
import com.android.APILogin.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CartServiceImpl {
    private final CartRepository cartRepository;
    private final UserRepository userRepository;
    private final DocumentRepository documentRepository;
//    private final CartMapper cartMapper;

    public List<CartDto> getCartByUserId(Long userId) {
        List<CartDto> carts = cartRepository.getCartByUserId(userId);
        if (carts == null) {
            return null;
        }
        return carts;
    }

    public CartDto addOrUpdateCart(CartDto cartDto) {
        User user = userRepository.findById(cartDto.getUserId()).orElseThrow(() -> new RuntimeException("User not found"));
        Document document = documentRepository.findById(cartDto.getDocId()).orElseThrow(() -> new RuntimeException("Document not found"));
        Optional<Cart> cartOtp = cartRepository.findByUserIdAndProductId(cartDto.getUserId(), cartDto.getDocId());
        Cart cart;
        if(cartOtp.isPresent()) {
            cart = cartOtp.get();
            cart.setQuantity(cartDto.getQuantity() + cart.getQuantity());
            cartRepository.save(cart);
        }
        else{
            cart = Cart.builder()
                    .quantity(cartDto.getQuantity())
                    .user(user)
                    .document(document)
                    .build();
            cartRepository.save(cart);
        }

        CartDto dto = CartDto.builder().cartId(cartDto.getCartId())
                .quantity(cart.getQuantity())
                .docId(cartDto.getDocId())
                .docName(cartDto.getDocName())
                .docImageUrl(cartDto.getDocImageUrl())
                .sellPrice(cartDto.getSellPrice())
                .userId(cartDto.getUserId())
                .build();

        return dto;
    }

    public CartDto updateCart(CartDto cart) {
        Cart cart1 =cartRepository.findById(cart.getCartId()).orElseThrow(() -> new RuntimeException("Cart not found"));
        cart1.setQuantity(cart.getQuantity());
        cartRepository.save(cart1);
        return cart;
    }

    public void deleteCartItem(Long cartId) {
        if(cartRepository.existsById(cartId)) {
            cartRepository.deleteById(cartId);
        }
    }

    public void deleteAllCarts(Long userId) {
        List<Cart> cart = cartRepository.findByUser_UserId(userId);
        if(cart != null) {
            cartRepository.deleteAllByCartUserId(userId);
        }

    }


}
