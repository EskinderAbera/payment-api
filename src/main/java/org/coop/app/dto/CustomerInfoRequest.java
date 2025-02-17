package org.coop.app.dto;

import lombok.Data;

@Data
public class CustomerInfoRequest {
    private CustomerInfoRequestBody CustomerInfoRequest;

    @Data
    public static class CustomerInfoRequestBody {
        private ESBHeader ESBHeader;
        private CusomerInfo CusomerInfo;
    }

    @Data
    public static class ESBHeader {
        private String serviceCode;
        private String channel;
        private String Service_name;
        private String Message_Id;
    }

    @Data
    public static class CusomerInfo {
        private String AccountId;
    }
}
