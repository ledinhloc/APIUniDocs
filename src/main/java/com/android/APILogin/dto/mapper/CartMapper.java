package com.android.APILogin.dto.mapper;

import com.android.APILogin.dto.request.CartDto;
import com.android.APILogin.dto.response.DocumentDto;
import com.android.APILogin.entity.Cart;
import com.android.APILogin.entity.Document;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring")
public interface CartMapper {
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    CartDto toCartDto(Cart cart);
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Cart toCart(CartDto cartDto);
}
