package ec.com.kruger.entity;


import lombok.*;
import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * Clase para administrar las entidades usuario.
 */
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "rhtuser")
public class UserEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(generator = "rhsecuser", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "rhsecuser", sequenceName = "rhsecuser", allocationSize = 1)
    @Column(name = "user_id")
    private Long userId;

    @Column(name = "employee_id")
    private Long employeeId;

    @Column(name = "user_name")
    private String userName;

    @Column(name = "password")
    private String password;

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

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "employee_id", referencedColumnName = "employee_id", insertable = false, updatable = false)
    private EmployeeEntity employeeEntity;

    public Long getId() {
        return userId;
    }

    ;
}
