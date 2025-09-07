package com.example.AffiliateAdda.Repository;

import com.example.AffiliateAdda.Model.MonthlyTracker;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.sound.midi.Track;
import java.util.Optional;

public interface MonthlyTrackerRepository extends JpaRepository<MonthlyTracker, Long> {
    Optional<MonthlyTracker> findByTrackerAndMonth(Track tracker, String month);
}
