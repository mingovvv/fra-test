package com.fra.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@Entity
@Table(name = "cde_cq_channel")
public class Channel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "AUTH_YN", length = 1)
    private String authYn;

    @Column(name = "COMMENT_YN", length = 1)
    private String commentYn;

    @Column(name = "TTS_YN", length = 1)
    private String ttsYn;

    @Column(name = "USE_YN", length = 1)
    private String useYn;

    @Column(name = "ACTIVATE_DT", columnDefinition = "datetime(6)")
    private LocalDateTime activateDt;

    @Column(name = "CREATED_DT", nullable = false, columnDefinition = "datetime(6)")
    private LocalDateTime createdDt;

    @Column(name = "DEACTIVATE_DT", columnDefinition = "datetime(6)")
    private LocalDateTime deactivateDt;

    @Column(name = "UPDATED_DT", columnDefinition = "datetime(6)")
    private LocalDateTime updatedDt;

    @Column(name = "INSERT_DATE", nullable = false, columnDefinition = "datetime(6)")
    private LocalDateTime insertDate;

    @Column(name = "DEPARTMENT", length = 20)
    private String department;

    @Column(name = "MANAGER", length = 20)
    private String manager;

    @Column(name = "CLIENT_NM", nullable = false, length = 30)
    private String clientNm;

    @Column(name = "CHANNEL_ID", nullable = false, length = 50)
    private String channelId;

    @Column(name = "CREATED_ID", nullable = false, length = 50)
    private String createdId;

    @Column(name = "SERVICE_NM", nullable = false, length = 50)
    private String serviceNm;

    @Column(name = "UPDATED_ID", length = 50)
    private String updatedId;

    @Column(name = "CHANNEL_KEY", length = 200)
    private String channelKey;

    @Column(name = "CHANNEL_SECRET", length = 200)
    private String channelSecret;
}

