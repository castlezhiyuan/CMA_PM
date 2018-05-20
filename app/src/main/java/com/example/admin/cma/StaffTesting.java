package com.example.admin.cma;

import java.io.Serializable;

/**
 * Created by 王国新 on 2018/5/20.
 */

public class StaffTesting implements Serializable {
    private String key;
    private String name;     //考核名称
    private String time;     //考核时间
    private String content;  //考核内容
    private String result;   //考核结果
    private String person;   //负责人
    private String auditor;  //审核人
    private String auditTime;//审核时间

    public StaffTesting(String name,String time,String content,String result,String person, String auditor,String auditTime){
        this.name = name;
        this.time = time;
        this.content = content;
        this.result = result;
        this.person =person;
        this.auditor = auditor;
        this.auditTime = auditTime;
    }

    public String getKey() {
        return key;
    }

    public String getName() {
        return name;
    }

    public String getTime() {
        return time;
    }

    public String getContent() {
        return content;
    }

    public String getResult() {
        return result;
    }

    public String getPerson() {
        return person;
    }

    public String getAuditor() {
        return auditor;
    }

    public String getAuditTime() {
        return auditTime;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public void setPerson(String person) {
        this.person = person;
    }

    public void setAuditor(String auditor) {
        this.auditor = auditor;
    }

    public void setAuditTime(String auditTime) {
        this.auditTime = auditTime;
    }
}
