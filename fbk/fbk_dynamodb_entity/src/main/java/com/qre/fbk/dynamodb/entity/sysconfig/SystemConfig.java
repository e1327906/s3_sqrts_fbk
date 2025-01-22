package com.qre.fbk.dynamodb.entity.sysconfig;

import com.qre.fbk.dynamodb.entity.base.DbFieldName;
import lombok.*;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbAttribute;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbBean;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbPartitionKey;

import java.util.Date;

@Builder@Data
@NoArgsConstructor
@AllArgsConstructor
@DynamoDbBean
public class SystemConfig {

    private String propertyId;

    private String propertyValue;

    private String propertyDesc;

    private Short modifyInd;

    private Date createdDatetime;

    private String createdUser;

    private Date updatedDatetime;

    private String updatedUser;

    private String terminalId;

    @DynamoDbPartitionKey
    @DynamoDbAttribute(DbFieldName.PROP_ID)
    public String getPropertyId() {
        return propertyId;
    }

    public void setPropertyId(String propertyId) {
        this.propertyId = propertyId;
    }
}
