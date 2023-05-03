package com.zoe.repositories;

import com.zoe.models.Recipients;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;
@Repository
public interface RecipientsRepository extends JpaRepository<Recipients, UUID> {
}
