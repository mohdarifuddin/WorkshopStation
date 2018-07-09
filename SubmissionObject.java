package voyanta.ui.pageobjects;

public class SubmissionObject {
    private String nameValue;
    private String notesValue;
    private String typeValue;
    private String submittedTime;
    private String validationStatus;
    private String approvalStatus;
    private String submissionID;
    private String deleteLocator;
    private String reviewLocator;

    public String getName() {
        return nameValue;
    }

    public void setName(String name) {
        nameValue = name;
    }


    public String getType() {
        return typeValue;
    }

    public void setType(String type) {
        typeValue = type;
    }

    public String getSubmittedTime() {
        return submittedTime;
    }

    public void setSubmittedTime(String timeValue) {
        submittedTime = timeValue;
    }

    public String getNote() {
        return notesValue;
    }

    public void setNote(String note) {
        notesValue = note;
    }

    public String getValidationStatus() {
        return validationStatus;
    }

    public void setValidationStatus(String validation) {
        validationStatus = validation;
    }

    public String getApprovalStatus() {
        return approvalStatus;
    }

    public void setApprovalStatus(String approval) {
        approvalStatus = approval;
    }


    public String getSubmissionID() {
        return submissionID;
    }

    public void setSubmissionID(String ID) {
        submissionID = ID;
    }

    public String getDelete() {
        return deleteLocator;
    }

    public void setDeleteLocator(String delete) {
        deleteLocator = delete;
    }

    public String getReview() {
        return reviewLocator;
    }

    public void setReviewLocator(String review) {
        reviewLocator = review;
    }
}
