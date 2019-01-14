package com.yootii.bdy.preference.model;

public class PreferenceField {
    private Integer preferenceId;

    private String dataType;

    private String preferenceName;

    private String description;

    private String defaultStringValue;

    private String defaultIntValue;

    private String preferenceType;

    private String format;

    public Integer getPreferenceId() {
        return preferenceId;
    }

    public void setPreferenceId(Integer preferenceId) {
        this.preferenceId = preferenceId;
    }

    public String getDataType() {
        return dataType;
    }

    public void setDataType(String dataType) {
        this.dataType = dataType == null ? null : dataType.trim();
    }

    public String getPreferenceName() {
        return preferenceName;
    }

    public void setPreferenceName(String preferenceName) {
        this.preferenceName = preferenceName == null ? null : preferenceName.trim();
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description == null ? null : description.trim();
    }

    public String getDefaultStringValue() {
        return defaultStringValue;
    }

    public void setDefaultStringValue(String defaultStringValue) {
        this.defaultStringValue = defaultStringValue == null ? null : defaultStringValue.trim();
    }

    public String getDefaultIntValue() {
        return defaultIntValue;
    }

    public void setDefaultIntValue(String defaultIntValue) {
        this.defaultIntValue = defaultIntValue == null ? null : defaultIntValue.trim();
    }

    public String getPreferenceType() {
        return preferenceType;
    }

    public void setPreferenceType(String preferenceType) {
        this.preferenceType = preferenceType == null ? null : preferenceType.trim();
    }

    public String getFormat() {
        return format;
    }

    public void setFormat(String format) {
        this.format = format == null ? null : format.trim();
    }
}