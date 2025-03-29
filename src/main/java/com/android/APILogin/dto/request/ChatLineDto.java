package com.android.APILogin.dto.request;

import com.android.APILogin.entity.FileChatLine;
import com.android.APILogin.entity.Seen;
import com.android.APILogin.enums.ChatStatus;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ChatLineDto implements Serializable {
    private Long chatLineId;
    private Long chatLineParentId;
    private String content;
    private ChatStatus chatStatus;

    //@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSSSS")
    private LocalDateTime sendAt;
    private Long userId;
    private Long conversationId;
    List<FileChatLine> fileChatLines;
    List<Seen> seens;
}
