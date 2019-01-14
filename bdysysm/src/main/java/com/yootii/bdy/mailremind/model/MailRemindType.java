package com.yootii.bdy.mailremind.model;

public class MailRemindType {
    private Integer reTypeId;

    private Integer remindType;

    private String remindDesc;

    public Integer getReTypeId() {
        return reTypeId;
    }

    public void setReTypeId(Integer reTypeId) {
        this.reTypeId = reTypeId;
    }

    public Integer getRemindType() {
        return remindType;
    }

    public void setRemindType(Integer remindType) {
        this.remindType = remindType;
    }

    public String getRemindDesc() {
        return remindDesc;
    }

    public void setRemindDesc(String remindDesc) {
        this.remindDesc = remindDesc == null ? null : remindDesc.trim();
    }
}