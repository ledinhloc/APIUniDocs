package com.android.APILogin.repository;

import com.android.APILogin.entity.Conversation;
import com.android.APILogin.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import com.android.APILogin.dto.response.ConversationOverviewDto;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ConversationRepository extends JpaRepository<Conversation, Long> {

    @Query("SELECT new com.android.APILogin.dto.response.ConversationOverviewDto(" +
            " c.conId, " +
            " CASE WHEN  c.numMember = 2 THEN " +
            "  (SELECT u.name FROM Participant p JOIN p.user u " +
            "        WHERE p.conversation = c AND u. userId<> :userId)" +
            "  ELSE c.conName END,  " +
            "   (SELECT COUNT(cl) FROM ChatLine cl " +
            "        WHERE cl.conversation = c " +
            "          AND cl.chatStatus = 'SENT' " +
            "          AND cl.user.userId <> :userId), " +
            "   (SELECT MAX(cl2.sendAt) FROM ChatLine cl2 " +
            "        WHERE cl2.conversation = c), " +
            "   (SELECT cl3.content FROM ChatLine cl3 " +
            "        WHERE cl3.conversation = c " +
            "        ORDER BY cl3.sendAt DESC LIMIT 1), " +
            "   CASE WHEN c.numMember = 2 THEN " +
            "       (SELECT u.avatar FROM Participant p JOIN p.user u " +
            "        WHERE p.conversation = c AND u.userId <> :userId) " +
            "       ELSE c.image END" +
            ")"+
            "FROM Conversation c "+
            "JOIN Participant p ON p.conversation = c " +
            "WHERE p.user.userId = :userId " +
            "GROUP BY c.conId, c.conName, c.numMember, c.image"
    )
    List<ConversationOverviewDto> findConversationsOverview(@Param("userId") Long userId);
}
