package org.coop.app.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true) // Ignore unknown fields to prevent errors
public class CustomerInfoResponse {

    public static class CustomerInfo {
        @JsonProperty("lockedAmount")
        private double lockedAmount;

        @JsonProperty("IBAN")
        private String iban;

        @JsonProperty("clearedBalance")
        private double clearedBalance;

        @JsonProperty("gender")
        private String gender;

        @JsonProperty("activeBranch")
        private String activeBranch;

        @JsonProperty("displayName")
        private String displayName;

        @JsonProperty("lastInterestCredited")
        private String lastInterestCredited;

        @JsonProperty("companyReference")
        private String companyReference;

        @JsonProperty("categoryName")
        private String categoryName;

        @JsonProperty("productName")
        private String productName;

        @JsonProperty("customerName")
        private String customerName;

        @JsonProperty("availableBalance")
        private double availableBalance;

        @JsonProperty("accountStatus")
        private String accountStatus;

        @JsonProperty("accountId")
        private String accountId;

        @JsonProperty("availableLimit")
        private String availableLimit;

        @JsonProperty("availableFunds")
        private double availableFunds;

        @JsonProperty("workingBalance")
        private double workingBalance;

        @JsonProperty("onlineActualBalance")
        private double onlineActualBalance;

        @JsonProperty("customerId")
        private String customerId;

        @JsonProperty("currencyId")
        private String currencyId;

        @JsonProperty("customerDetails")
        private List<CustomerDetails> customerDetails;

        @JsonProperty("openingDate")
        private String openingDate;

        @JsonProperty("categoryId")
        private String categoryId;

        @JsonProperty("maritalStatus")
        private String maritalStatus;

        public double getLockedAmount() {
            return lockedAmount;
        }

        public String getIban() {
            return iban;
        }

        public double getClearedBalance() {
            return clearedBalance;
        }

        public String getGender() {
            return gender;
        }

        public String getActiveBranch() {
            return activeBranch;
        }

        public String getDisplayName() {
            return displayName;
        }

        public String getLastInterestCredited() {
            return lastInterestCredited;
        }

        public String getCompanyReference() {
            return companyReference;
        }

        public String getCategoryName() {
            return categoryName;
        }

        public String getProductName() {
            return productName;
        }

        public String getCustomerName() {
            return customerName;
        }

        public double getAvailableBalance() {
            return availableBalance;
        }

        public String getAccountStatus() {
            return accountStatus;
        }

        public String getAccountId() {
            return accountId;
        }

        public String getAvailableLimit() {
            return availableLimit;
        }

        public double getAvailableFunds() {
            return availableFunds;
        }

        public double getWorkingBalance() {
            return workingBalance;
        }

        public double getOnlineActualBalance() {
            return onlineActualBalance;
        }

        public String getCustomerId() {
            return customerId;
        }

        public String getCurrencyId() {
            return currencyId;
        }

        public List<CustomerDetails> getCustomerDetails() {
            return customerDetails;
        }

        public String getOpeningDate() {
            return openingDate;
        }

        public String getCategoryId() {
            return categoryId;
        }

        public String getMaritalStatus() {
            return maritalStatus;
        }
    }

    public static class CustomerDetails {
        @JsonProperty("firstName")
        private String firstName;

        @JsonProperty("lastName")
        private String lastName;

        @JsonProperty("customerType")
        private String customerType;

        @JsonProperty("phoneNumber")
        private String phoneNumber;

        @JsonProperty("roleDisplayName")
        private String roleDisplayName;

        @JsonProperty("street")
        private String street;

        @JsonProperty("beneficialOwner")
        private String beneficialOwner;

        @JsonProperty("dateOfBirth")
        private String dateOfBirth;

        @JsonProperty("townCountry")
        private String townCountry;

        @JsonProperty("email")
        private String email;

        @JsonProperty("maritalStatus")
        private String maritalStatus;

        @JsonProperty("customer")
        private String customer;

        public String getFirstName() {
            return firstName;
        }

        public String getLastName() {
            return lastName;
        }

        public String getCustomerType() {
            return customerType;
        }

        public String getPhoneNumber() {
            return phoneNumber;
        }

        public String getRoleDisplayName() {
            return roleDisplayName;
        }

        public String getStreet() {
            return street;
        }

        public String getBeneficialOwner() {
            return beneficialOwner;
        }

        public String getDateOfBirth() {
            return dateOfBirth;
        }

        public String getTownCountry() {
            return townCountry;
        }

        public String getEmail() {
            return email;
        }

        public String getMaritalStatus() {
            return maritalStatus;
        }

        public String getCustomer() {
            return customer;
        }
    }

}
