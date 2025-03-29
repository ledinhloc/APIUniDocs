package com.android.APILogin.repository;

import com.android.APILogin.entity.Conversation;
import com.android.APILogin.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import com.android.APILogin.dto.response.ConversationOverviewDto;

import java.util.List;

public interface ConversationRepository extends JpaRepository<Conversation, Long> {

    /*@Query("SELECT new com.android.APILogin.dto.response.ConversationOverviewDto(" +
            "c.conId, " +
            "CASE WHEN c.isGroup = true THEN c.conName " +
            "     ELSE (SELECT u2.name FROM Participant p2 JOIN p2.user u2 " +
            "           WHERE p2.conversation = c AND u2 <> :currentUser) END, " +
            "(SELECT COUNT(cl) FROM ChatLine cl " +
            "   WHERE cl.conversation = c AND cl.createdAt > " +
            "         (SELECT COALESCE(MAX(s.seenAt), '1970-01-01T00:00:00') FROM Seen s " +
            "          WHERE s.chatLine = cl AND s.user = :currentUser)), " +
            "(SELECT MAX(cl2.createdAt) FROM ChatLine cl2 WHERE cl2.conversation = c), " +
            "(SELECT cl3.content FROM ChatLine cl3 WHERE cl3.conversation = c ORDER BY cl3.createdAt DESC), " +
            "CASE WHEN c.isGroup = true THEN c.image " +
            "     ELSE (SELECT u3.avatar FROM Participant p3 JOIN p3.user u3 " +
            "           WHERE p3.conversation = c AND u3 <> :currentUser) END" +
            ") " +
            "FROM Conversation c " +
            "JOIN c.participants p " +
            "WHERE EXISTS (SELECT part.user FROM Participant part WHERE part.conversation = c AND part.user = :currentUser) " +
            "GROUP BY c.conId, c.conName, c.isGroup, c.image")
    List<ConversationOverviewDto> findConversationsOverview(@Param("currentUser") User currentUser);*/
}
