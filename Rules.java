package voyanta.ui.pageobjects;

/**
 * Created by Reshma.shaik on 06/10/2015.
 */
public class Rules {

    private String field;
    private String fieldName;
    private String operator;


    private String value;
    private String brtype;
    private String messageType;
    private String message;

    private String ruleName;
    private String objectType;
    private String providerOption;
    private String provider;


    public Rules createRule() {
        return this;
    }

    public Rules withfield(String field) {
        this.field = field;
        return this;
    }

    public Rules withfieldname(String fieldName) {
        this.fieldName = fieldName;
        return this;
    }

    public Rules withOperator(String operator) {
        this.operator = operator;
        return this;
    }

    public Rules withValue(String value) {
        this.value = value;
        return this;
    }

    public Rules withbrtype(String brtype) {
        this.brtype = brtype;
        return this;
    }

    public Rules withMessageType(String messageType) {
        this.messageType = messageType;
        return this;
    }

    public Rules withMessage(String message) {
        this.message = message;
        return this;
    }

    public String getField() {
        return field;
    }

    public String getFieldName() {
        return fieldName;
    }

    public String getOperator() {
        return operator;
    }

    public String getValue() {
        return value;
    }

    public String getBrtype() {
        return brtype;
    }

    public String getMessageType() {
        return messageType;
    }

    public String getMessage() {
        return message;
    }

    public Rules withName(String ruleName) {
        this.ruleName = ruleName;
        return this;
    }

    public Rules withObjectType(String objectType) {
        this.objectType = objectType;
        return this;
    }

    public Rules withproviderOption(String providerOption) {
        this.providerOption = providerOption;
        return this;
    }

    public Rules withprovider(String provider) {
        this.provider = provider;
        return this;
    }

    public String getRuleName() {
        return ruleName;
    }

}



