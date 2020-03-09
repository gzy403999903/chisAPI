package com.chisapp.modules.member.bean;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.hibernate.validator.constraints.Length;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;

public class MemberHealth implements Serializable {
    @NotNull
    private Integer mrmMemberId;

    @NotNull
    private Integer sysDictTypeId;

    private Integer sysDictId;

    @Length(max = 50)
    private String diagnosisDetail;

    @JsonFormat(pattern="yyyy-MM-dd")
    @DateTimeFormat(pattern="yyyy-MM-dd")
    private Date diagnosisDate;

    public Integer getMrmMemberId() {
        return mrmMemberId;
    }

    public void setMrmMemberId(Integer mrmMemberId) {
        this.mrmMemberId = mrmMemberId;
    }

    public Integer getSysDictTypeId() {
        return sysDictTypeId;
    }

    public void setSysDictTypeId(Integer sysDictTypeId) {
        this.sysDictTypeId = sysDictTypeId;
    }

    public Integer getSysDictId() {
        return sysDictId;
    }

    public void setSysDictId(Integer sysDictId) {
        this.sysDictId = sysDictId;
    }

    public String getDiagnosisDetail() {
        return diagnosisDetail;
    }

    public void setDiagnosisDetail(String diagnosisDetail) {
        this.diagnosisDetail = diagnosisDetail == null ? null : diagnosisDetail.trim();
    }

    public Date getDiagnosisDate() {
        return diagnosisDate;
    }

    public void setDiagnosisDate(Date diagnosisDate) {
        this.diagnosisDate = diagnosisDate;
    }
}
