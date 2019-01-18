package io.kirikcoders.bitcse.Tools;
/**
 * Created by Akash on 17-Jan-19.
 */

public class FacultyModel {

    private String designation;
    private String emailid;
    private String fid;
    private String name;
    private String phoneno;
    private String qualification;
    private String tag;
    private String image;


    public FacultyModel() {
    }

    public FacultyModel(String designation, String emailid, String fid, String name, String phoneno, String qualification, String tag, String image) {
        this.designation = designation;
        this.emailid = emailid;
        this.fid = fid;
        this.name = name;
        this.phoneno = phoneno;
        this.qualification = qualification;
        this.tag = tag;
        this.image = image;
    }

    public String getDesignation() {
        return designation;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }

    public String getEmailid() {
        return emailid;
    }

    public void setEmailid(String emailid) {
        this.emailid = emailid;
    }

    public String getFid() {
        return fid;
    }

    public void setFid(String fid) {
        this.fid = fid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhoneno() {
        return phoneno;
    }

    public void setPhoneno(String phoneno) {
        this.phoneno = phoneno;
    }

    public String getQualification() {
        return qualification;
    }

    public void setQualification(String qualification) {
        this.qualification = qualification;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
