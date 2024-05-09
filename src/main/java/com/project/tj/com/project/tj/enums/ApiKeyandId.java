package com.project.tj.com.project.tj.enums;


public enum ApiKeyandId {
    API_KEY_MYPROJECT("Ybkvekfbks73890kevv78e903bgkef", "eibgjieegh678904tjkbvks7890bkfhvksa");


    private final String apiKey;
    private final String id;

    ApiKeyandId(String apiKey, String id) {
        this.apiKey = apiKey;
        this.id = id;
    }

    public String getApiKey() {
        return apiKey;
    }

    public String getId() {
        return id;
    }
}
