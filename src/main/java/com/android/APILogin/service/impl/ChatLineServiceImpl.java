package com.android.APILogin.service.impl;

import com.android.APILogin.dto.mapper.ChatLineMapper;
import com.android.APILogin.dto.request.ChatLineDto;
import com.android.APILogin.entity.ChatLine;
import com.android.APILogin.entity.Conversation;
import com.android.APILogin.entity.FileChatLine;
import com.android.APILogin.entity.User;
import com.android.APILogin.enums.ChatStatus;
import com.android.APILogin.enums.ChatType;
import com.android.APILogin.enums.FileType;
import com.android.APILogin.repository.ChatLineRepository;
import com.android.APILogin.repository.ConversationRepository;
import com.android.APILogin.repository.FileChatLineRepository;
import com.android.APILogin.repository.UserRepository;
import com.android.APILogin.service.ChatLineService;
import com.android.APILogin.service.CloudinaryService;
import com.android.APILogin.utils.FileUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.core.io.Resource;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class ChatLineServiceImpl implements ChatLineService {
    private final ChatLineRepository chatLineRepository;
    private final ChatLineMapper chatLineMapper;
    private final ConversationRepository conversationRepository;
    private final UserRepository userRepository;
    private final CloudinaryService cloudinaryService;
    private final FileUtils fileUtils;
    private final FileChatLineRepository fileChatLineRepository;


    @Override
    public List<ChatLineDto> getChatLinesByConversationId(Long conId) {
        List<ChatLine> chatLines = chatLineRepository.findByConversationConId(conId);
        return chatLineMapper.toDtoList(chatLines);
    }

    //xu ly tin nhan
    @Override
    public ChatLineDto handleMessage(ChatLineDto messageDto, List<MultipartFile> files) {
        User user = userRepository.findById(messageDto.getUserId()).orElseThrow();
        Conversation conversation = conversationRepository.findById(messageDto.getConversationId()).orElseThrow();

        ChatLine newChatLine = new ChatLine();
        newChatLine.setUser(user);
        newChatLine.setConversation(conversation);
        newChatLine.setContent(messageDto.getContent());
        newChatLine.setChatStatus(ChatStatus.DELIVERED);
        newChatLine.setSendAt(messageDto.getSendAt());
        newChatLine.setChatLineParentId(messageDto.getChatLineParentId());
        newChatLine.setChatType(messageDto.getChatType());

        //luu hinh
        if(messageDto.getChatType() == ChatType.IMAGES && files != null && files.size() > 0) {
            List<FileChatLine> listFile = new ArrayList<>();
            for(MultipartFile file : files){
                String fileUrl = cloudinaryService.uploadFile(file, "chat_images");

                FileChatLine fileChatLine = new FileChatLine();
                fileChatLine.setFileUrl(fileUrl);
                fileChatLine.setFileType(FileType.IMAGE);
                fileChatLine.setChatLine(newChatLine);

                listFile.add(fileChatLine);
            }
            newChatLine.setFileChatLines(listFile);
        }
        //luu file
        else if (messageDto.getChatType() == ChatType.FILE && files != null && files.size() > 0){
            try {
                FileChatLine fileChatLine = new FileChatLine();
                String filePath = fileUtils.saveFile(files.get(0), "file_chat");
//                System.out.println(files.get(0).getSize());

                //set file, name, url
                // Lấy kích thước file theo byte
                fileChatLine.setSize(files.get(0).getSize());
                fileChatLine.setName(files.get(0).getOriginalFilename());
                fileChatLine.setFileUrl(filePath);
                fileChatLine.setFileType(FileType.DOCX);
                //setChatLine
                fileChatLine.setChatLine(newChatLine);

                newChatLine.setFileChatLines(new ArrayList<>(){
                    {
                        add(fileChatLine);
                    }
                });
            }catch (IOException e){
                e.printStackTrace();
            }
        }

        //luu chat line
        chatLineRepository.save(newChatLine);

        return chatLineMapper.toDto(newChatLine);
    }

    //
    public String uploadFile(MultipartFile file)  {
        // Định nghĩa thư mục lưu file, ví dụ: "uploads"
        String uploadDirectory = "uploads";
        try {
            return fileUtils.saveFile(file, uploadDirectory);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public Resource downloadFile(String filePath) {
        return fileUtils.getFile(filePath);
    }

    public Resource getFileById(Long fileId) {
        Optional<FileChatLine> fileOpt =  fileChatLineRepository.findById(fileId);
        if(fileOpt.isPresent()){
            return fileUtils.getFile(fileOpt.get().getFileUrl());
        }
        return null;
    }
}
