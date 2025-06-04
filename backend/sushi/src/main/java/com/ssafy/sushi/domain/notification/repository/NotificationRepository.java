package com.ssafy.sushi.domain.notification.repository;


import com.ssafy.sushi.domain.notification.entity.Notification;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface NotificationRepository extends JpaRepository <Notification, Integer> {
    Page<Notification> findNotificationByUserIdAndIsReadFalse(Integer userId, Pageable pageable);

    boolean existsByUserIdAndIsReadFalse(Integer userId);

    @Modifying
    @Query("UPDATE Notification n SET n.isRead = true WHERE n.user.id = :userId AND n.isRead = false")
    void markAllNotificationAsRead(Integer userId);
}
