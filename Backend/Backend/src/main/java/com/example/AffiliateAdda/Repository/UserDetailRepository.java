package com.example.AffiliateAdda.Repository;


import com.example.AffiliateAdda.Model.UserDetails;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserDetailRepository extends JpaRepository<UserDetails, Long> {
}