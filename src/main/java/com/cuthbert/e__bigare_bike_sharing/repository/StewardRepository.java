package com.cuthbert.e__bigare_bike_sharing.repository;

import com.cuthbert.e__bigare_bike_sharing.model.Steward;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface StewardRepository extends JpaRepository<Steward, UUID> {
}
