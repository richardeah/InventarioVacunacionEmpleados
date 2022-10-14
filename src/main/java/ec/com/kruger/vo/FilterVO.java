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
 * Clase para administrar objeto del filtro.
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class FilterVO implements Serializable {

    private static final long serialVersionUID = 1L;
    private Boolean hasVaccine;
    private String vaccineType;
    private String vaccineDateIni;
    @JsonIgnore
    private Date vaccineDateIniFormatter;
    private String vaccineDateEnd;
    @JsonIgnore
    private Date vaccineDateEndFormatter;

}
