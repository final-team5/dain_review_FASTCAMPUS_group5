package com.example.finalproject.domain.campaign.entity;

import com.example.finalproject.domain.user.entity.User;
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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_seq")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "campaign_seq")
    private Campaign campaign;

    public CampaignPreference(User user, Campaign campaign) {
        this.user = user;
        this.campaign = campaign;
    }

    public static CampaignPreference of(User user, Campaign campaign) {
        return new CampaignPreference(user, campaign);
    }
}
