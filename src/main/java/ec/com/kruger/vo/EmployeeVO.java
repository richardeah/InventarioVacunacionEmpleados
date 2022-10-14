package ec.com.kruger.vo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
 * Clase para administrar objeto del empleado.
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class EmployeeVO implements Serializable {

    private static final long serialVersionUID = 1L;

    @JsonIgnore
    private Long employeeId;
    private String documentNumber;
    private String firstName;
    private String lastName;
    private String email;
    private Date birthDate;
    private String addressHome;
    private String mobilePhone;
    private Boolean hasVaccine;
    private String vaccineType;
    private Date vaccineDate;
    private Integer vaccineNumber;
    @JsonIgnore
    private Boolean status;
    private String createUserId;
    @JsonIgnore
    private Date createdDate;
    private String modifiedUserId;
    @JsonIgnore
    private Date modifiedDate;
    @JsonIgnore
    private String message;
}
