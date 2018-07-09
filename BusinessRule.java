package voyanta.ui.pageobjects;

/**
 * Created by sriramangajala on 01/08/2014.
 */
public class BusinessRule {

    private String field;
    private String fieldName;
    private String operator;


    private String value;
    private String brType;
    private String messageType;
    private String message;


    private String ruleName;
    private String objectType;
    private String providerOption;
    private String provider;

    public BusinessRule createRule() {

        return this;
    }

    public BusinessRule withfield(String field) {
        this.field = field;
        return this;
    }

    public BusinessRule withfieldName(String fieldName) {
        this.fieldName = fieldName;
        return this;
    }

    public BusinessRule withOperator(String operator) {
        this.operator = operator;
        return this;
    }

    public BusinessRule withValue(String value) {
        this.value = value;
        return this;
    }

    public BusinessRule withbrType(String brType) {
        this.brType = brType;
        return this;
    }

    public BusinessRule withMessageType(String messageType) {
        this.messageType = messageType;
        return this;
    }

    public BusinessRule withMessage(String message) {
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

    public String getBrType() {
        return brType;
    }

    public String getMessageType() {
        return messageType;
    }

    public String getMessage() {
        return message;
    }

    public BusinessRule withName(String ruleName) {
        this.ruleName = ruleName;
        return this;
    }

    public BusinessRule withObjectType(String objectType) {

        this.objectType = objectType;
        return this;

    }

    public BusinessRule withproviderOption(String providerOption) {
        this.providerOption = providerOption;
        return this;
    }

    public BusinessRule withprovider(String provider) {
        this.provider = provider;
        return this;
    }

    public String getRuleName() {
        return ruleName;
    }

    public String getObjectType() {
        return objectType;
    }

    public String getProviderOption() {
        return providerOption;
    }

    public String getProvider() {
        return provider;
    }

//    public BusinessRule withField(String field) {
//        this.f
//    }
}
