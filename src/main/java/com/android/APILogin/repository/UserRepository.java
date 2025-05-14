package com.android.APILogin.repository;

import com.android.APILogin.dto.request.UserInfoDto;
import com.android.APILogin.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.swing.text.html.Option;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(@Param("email") String email);

    @Query("SELECT new com.android.APILogin.dto.request.UserInfoDto(" +
            "u.userId, u.name, u.address, u.avatar, " +
            "COUNT(distinct d.docId), " +
            "COALESCE(SUM(od.quantity), 0L), " +
            "COUNT(distinct r.document.docId)) " +
            "FROM User u " +
            "LEFT JOIN Document d ON d.user.userId = u.userId " +
            "LEFT JOIN OrderDetail od ON od.document.docId = d.docId " +
            "LEFT JOIN Review r ON r.document.docId = d.docId " +
            "WHERE u.userId = :id " +
            "GROUP BY u.userId, u.name, u.address, u.avatar")
    Optional<UserInfoDto> findUserInfoByDocumentId(@Param("id") Long id);

    boolean existsByEmail(@Param("email") String email);

    Optional<User> findByEmailAndPhone(@Param("email") String email, @Param("phoneNumber") String phoneNumber);

    Optional<User> findByUserId(Long id);
}
