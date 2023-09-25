package Model;

public class UserInformationModel {
    String userId, userName, userGmail, certificateName;
    int practiceComplete,examComplete;
    boolean certificateStatus, isGirl;

    public UserInformationModel() {
        // Default constructor required for Firebase
    }

    public String getCertificateName() {
        return certificateName;
    }

    public void setCertificateName(String certificateName) {
        this.certificateName = certificateName;
    }

    public boolean isGirl() {
        return isGirl;
    }

    public void setGirl(boolean girl) {
        isGirl = girl;
    }

    public UserInformationModel(String userId, String userName, String userGmail, int practiceComplete, int examComplete, boolean certificateStatus, String certificateName, boolean isGirl) {
        this.userId = userId;
        this.userName = userName;
        this.userGmail = userGmail;
        this.practiceComplete = practiceComplete;
        this.examComplete = examComplete;
        this.certificateStatus = certificateStatus;
        this.certificateName = certificateName;
        this.isGirl = isGirl;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserGmail() {
        return userGmail;
    }

    public void setUserGmail(String userGmail) {
        this.userGmail = userGmail;
    }



    public int getPracticeComplete() {
        return practiceComplete;
    }

    public void setPracticeComplete(int practiceComplete) {
        this.practiceComplete = practiceComplete;
    }

    public int getExamComplete() {
        return examComplete;
    }

    public void setExamComplete(int examComplete) {
        this.examComplete = examComplete;
    }

    public boolean isCertificateStatus() {
        return certificateStatus;
    }

    public void setCertificateStatus(boolean certificateStatus) {
        this.certificateStatus = certificateStatus;
    }

}
