package com.example.finalproject.domain.campaign.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "result_report")
public class ResultReport {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @OneToOne
    @JoinColumn(name = "campaign_id", referencedColumnName = "id")
    private Campaign campaign;

    private String reportId;

    @Column(name = "total_participants")
    private Integer totalParticipants;

    @Column(name = "completed_participants")
    private Integer completedParticipants;

    @Column(name = "start_date")
    private Date startDate;

    @Column(name = "end_date")
    private Date endDate;

    @Column(name = "reach")
    private Integer reach;

    @Column(name = "engagement")
    private Integer engagement;

    @Column(name = "conversion")
    private Integer conversion;

    @Column(columnDefinition = "TEXT")
    private String summary;

    @Column(name = "created_at")
    private Date createdAt;
}