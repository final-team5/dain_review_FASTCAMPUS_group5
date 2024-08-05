package com.example.finalproject.domain.campaign.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "campaign_preference")
@Entity
public class CampaignPreference {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer seq;
}
