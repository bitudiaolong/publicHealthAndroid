package com.gc.basicpublicdoctor.bean;

import java.util.List;

public class SubmitSignRequest {
    String doctorToken;
    String userUid;
    List<SubmitSign> submitSignList;

    public String getDoctorToken() {
        return doctorToken;
    }

    public void setDoctorToken(String doctorToken) {
        this.doctorToken = doctorToken;
    }

    public String getUserUid() {
        return userUid;
    }

    public void setUserUid(String userUid) {
        this.userUid = userUid;
    }

    public List<SubmitSign> getSubmitSignList() {
        return submitSignList;
    }

    public void setSubmitSignList(List<SubmitSign> submitSignList) {
        this.submitSignList = submitSignList;
    }
}
