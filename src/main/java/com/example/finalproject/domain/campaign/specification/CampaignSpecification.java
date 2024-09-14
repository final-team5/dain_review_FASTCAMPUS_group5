package com.example.finalproject.domain.campaign.specification;

import com.example.finalproject.domain.campaign.entity.Campaign;
import org.springframework.data.jpa.domain.Specification;

public class CampaignSpecification {

    public static Specification<Campaign> findByCampaignStatus(Integer status) {
        return ((root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("status"), status));
    }

    public static Specification<Campaign> findBySearchWordInAll(String searchWord) {
        return ((root, query, criteriaBuilder) ->
                criteriaBuilder
                        .or(
                                criteriaBuilder.like(root.get("title"), "%" + searchWord + "%"),
                                criteriaBuilder.like(root.join("user").get("email"), "%" + searchWord + "%"),
                                criteriaBuilder.like(root.join("user").get("phone"), "%" + searchWord + "%")
                        )
        );
    }

    public static Specification<Campaign> findBySearchWordInTitle(String searchWord) {
        return ((root, query, criteriaBuilder) ->
                criteriaBuilder.like(root.get("title"), "%" + searchWord + "%"));
    }

    public static Specification<Campaign> findBySearchWordInUserEmail(String searchWord) {
        return ((root, query, criteriaBuilder) ->
                criteriaBuilder.like(root.join("user").get("email"), "%" + searchWord + "%"));
    }

    public static Specification<Campaign> findBySearchWordInUserPhone(String searchWord) {
        return ((root, query, criteriaBuilder) ->
                criteriaBuilder.like(root.join("user").get("phone"), "%" + searchWord + "%"));
    }
}
