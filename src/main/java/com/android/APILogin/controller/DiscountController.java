package com.android.APILogin.controller;

import com.android.APILogin.dto.request.DiscountDto;
import com.android.APILogin.dto.response.ResponseData;
import com.android.APILogin.service.DiscountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/discount")
public class DiscountController {
    @Autowired
    private DiscountService discountService;

    @GetMapping("/scope")
    public ResponseData<List<DiscountDto>> getDiscountByScope(
            @RequestParam(required = false) List<Long> userIds,
            @RequestParam(required = false) List<Long> categoryIds,
            @RequestParam(required = false) List<Long> documentIds){
        List<DiscountDto> discounts = discountService.findByScopeAndScopeId(
                userIds != null ? userIds : List.of(),
                categoryIds != null ? categoryIds : List.of(),
                documentIds != null ? documentIds : List.of()
        );
        if(discounts==null || discounts.isEmpty()){
            return new ResponseData<>(HttpStatus.NOT_FOUND.value(), "Not found discount", discounts);
        }
        else{
            return new ResponseData<>(HttpStatus.OK.value(), "Found discount", discounts);
        }
    }

    @PostMapping("/{userId}/addDiscount")
    public ResponseData<Long> addDiscount(@PathVariable Long userId,@RequestBody DiscountDto discountDto){
        Long discountId =  discountService.addDiscount(discountDto, userId);
        return new ResponseData<>(HttpStatus.OK.value(), "Added discount",discountId);
    }
}
