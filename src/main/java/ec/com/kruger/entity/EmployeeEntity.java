package ec.com.kruger.entity;


import lombok.*;
import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * Clase para administrar las entidades del empleado.
 */

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "rhtemployee")
public class EmployeeEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(generator = "rhsecemployee", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "rhsecemployee", sequenceName = "rhsecemployee", allocationSize = 1)
    @Column(name = "employee_id")
    private Long employeeId;

    @Column(name = "document_number")
    private String documentNumber;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "email")
    private String email;

    @Column(name = "birth_date")
    private Date birthDate;

    @Column(name = "address_home")
    private String addressHome;

    @Column(name = "mobile_phone")
    private String mobilePhone;

    @Column(name = "has_vaccine")
    private Boolean hasVaccine;

    @Column(name = "vaccine_type")
    private String vaccineType;

    @Column(name = "vaccine_date")
    private Date vaccineDate;

    @Column(name = "vaccine_number")
    private Integer vaccineNumber;

    @Column(name = "status")
    private Boolean status;

    @Column(name = "create_user_id")
    private String createUserId;

    @Column(name = "create_date")
    private Date createdDate;

    @Column(name = "modified_user_id")
    private String modifiedUserId;

    @Column(name = "modified_date")
    private Date modifiedDate;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "employeeEntity")
    private List<UserEntity> userEntities;

}
