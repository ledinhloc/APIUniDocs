package com.android.APILogin.dto.mapper;


import com.android.APILogin.dto.request.ChatLineDto;
import com.android.APILogin.entity.ChatLine;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ChatLineMapper {
    @Mapping(source = "user.userId", target = "userId")
    @Mapping(source = "conversation.conId", target = "conversationId")
    ChatLineDto toDto(ChatLine chatLine);

    List<ChatLineDto> toDtoList(List<ChatLine> chatLines);
}
