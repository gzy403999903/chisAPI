package com.chisapp.modules.doctorworkstation.bean;

import org.hibernate.validator.constraints.Length;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.Future;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;

public class VisitRecord implements Serializable {
    private Integer id;

    @NotNull
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date appointmentDate;

    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date finishDate;

    @NotNull
    private Integer mrmMemberId;

    @NotNull
    private Integer dwtClinicalHistoryId;

    @NotNull
    private Integer phase = 1;

    @Length(max = 200)
    private String visitContent;

    private Integer sysClinicId;

    private Integer creatorId;

    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date creationDate;

    @NotNull
    private Boolean finished = false;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getAppointmentDate() {
        return appointmentDate;
    }

    public void setAppointmentDate(Date appointmentDate) {
        this.appointmentDate = appointmentDate;
    }

    public Date getFinishDate() {
        return finishDate;
    }

    public void setFinishDate(Date finishDate) {
        this.finishDate = finishDate;
    }

    public Integer getMrmMemberId() {
        return mrmMemberId;
    }

    public void setMrmMemberId(Integer mrmMemberId) {
        this.mrmMemberId = mrmMemberId;
    }

    public Integer getDwtClinicalHistoryId() {
        return dwtClinicalHistoryId;
    }

    public void setDwtClinicalHistoryId(Integer dwtClinicalHistoryId) {
        this.dwtClinicalHistoryId = dwtClinicalHistoryId;
    }

    public Integer getPhase() {
        return phase;
    }

    public void setPhase(Integer phase) {
        this.phase = phase;
    }

    public String getVisitContent() {
        return visitContent;
    }

    public void setVisitContent(String visitContent) {
        this.visitContent = visitContent == null ? null : visitContent.trim();
    }

    public Integer getSysClinicId() {
        return sysClinicId;
    }

    public void setSysClinicId(Integer sysClinicId) {
        this.sysClinicId = sysClinicId;
    }

    public Integer getCreatorId() {
        return creatorId;
    }

    public void setCreatorId(Integer creatorId) {
        this.creatorId = creatorId;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public Boolean getFinished() {
        return finished;
    }

    public void setFinished(Boolean finished) {
        this.finished = finished;
    }
}
