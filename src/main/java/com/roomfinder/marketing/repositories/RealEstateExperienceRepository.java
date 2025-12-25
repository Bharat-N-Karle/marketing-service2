package com.roomfinder.marketing.repositories;

import com.roomfinder.marketing.repositories.entities.RealEstateExperienceEntity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RealEstateExperienceRepository extends MongoRepository<RealEstateExperienceEntity, String> {



}
