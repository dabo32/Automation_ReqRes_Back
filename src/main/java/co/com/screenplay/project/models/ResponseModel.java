package co.com.screenplay.project.models;

import lombok.Data;

@Data
public class ResponseModel {
    private int statusCode;
    private String responseBody;
}