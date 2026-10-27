package com.cuthbert.e__bigare_bike_sharing.repository;

import com.cuthbert.e__bigare_bike_sharing.model.Rider;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface RiderRepository extends JpaRepository<Rider, UUID> {
    boolean existsByRiderCode(String riderCode);

    boolean existsByPhone(String phone);

    // B1: Find riders by lastName, case-insensitive, ordered by firstName Ascending
    List<Rider> findByLastNameIgnoreCaseOrderByFirstNameAsc(String lastName);
}
