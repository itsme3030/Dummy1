package com.example.AffiliateAdda.Repository;


import com.example.AffiliateAdda.Model.UserDetail;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserDetailRepository extends JpaRepository<UserDetail, Long> {
}