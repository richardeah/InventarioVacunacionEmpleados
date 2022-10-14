package ec.com.kruger.repository;

import com.querydsl.core.BooleanBuilder;
import ec.com.kruger.entity.EmployeeEntity;
import static com.querydsl.core.types.Projections.bean;
import static ec.com.kruger.entity.QEmployeeEntity.employeeEntity;
import ec.com.kruger.vo.EmployeeVO;
import ec.com.kruger.vo.FilterVO;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.stereotype.Repository;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaUpdate;
import javax.persistence.criteria.Root;
import javax.persistence.metamodel.EntityType;
import java.util.Date;
import java.util.List;

/**
 *
 */
@Repository
public class EmployeeRepository extends QuerydslRepositorySupport {

    public EmployeeRepository() {
        super(EmployeeEntity.class);
    }

    /**
     * Metodo para listar empleados.
     * @param  filterVO
     * @return
     */
    public List<EmployeeVO> findAll(FilterVO filterVO) {

        BooleanBuilder where = new BooleanBuilder();
        where.and(employeeEntity.status.eq(true));

        if (filterVO.getHasVaccine() != null) {
            where.and(employeeEntity.hasVaccine.eq(filterVO.getHasVaccine()));
        }

        if (filterVO.getVaccineType() != null && filterVO.getVaccineType() != "") {
            where.and(employeeEntity.vaccineType.eq(filterVO.getVaccineType()));
        }

        if (filterVO.getVaccineDateIniFormatter() != null && filterVO.getVaccineDateEndFormatter() != null) {
            where.and(employeeEntity.vaccineDate.between(filterVO.getVaccineDateIniFormatter(), filterVO.getVaccineDateEndFormatter()));
        }

        return from(employeeEntity).select(bean(EmployeeVO.class,
                employeeEntity.employeeId,
                employeeEntity.documentNumber,
                employeeEntity.firstName,
                employeeEntity.lastName,
                employeeEntity.email,
                employeeEntity.birthDate,
                employeeEntity.mobilePhone,
                employeeEntity.addressHome
        )).where(where).fetch();
    }

    /**
     * Metodo para crear un empleado.
     *
     * @param employeeEntity
     */

    public void createEmployee(EmployeeEntity employeeEntity) {

        this.getEntityManager().persist(employeeEntity);
    }

    /**
     * Metodo para eliminar un empleado.
     *
     * @param employeeId
     * @param userId
     */
    public void deleteEmployee(Long employeeId, String userId) {
        EntityType<EmployeeEntity> entityType = getEntityManager().getMetamodel().entity(EmployeeEntity.class);
        CriteriaBuilder criteriaBuilder = getEntityManager().getCriteriaBuilder();
        CriteriaUpdate<EmployeeEntity> criteriaUpdate = criteriaBuilder.createCriteriaUpdate(EmployeeEntity.class);
        Root<EmployeeEntity> query = criteriaUpdate.from(EmployeeEntity.class);
        criteriaUpdate
                .set(query.get(entityType.getSingularAttribute("status", Boolean.class)), false)
                .set(query.get(entityType.getSingularAttribute("modifiedUserId", String.class)), userId)
                .set(query.get(entityType.getSingularAttribute("modifiedDate", Date.class)), new Date())
                .where(criteriaBuilder.equal(query.get(entityType.getSingularAttribute("employeeId")), employeeId));
        getEntityManager().createQuery(criteriaUpdate).executeUpdate();
    }

    /**
     * Metodo para actualizar un empleado.
     *
     * @param employeeEntity
     */
    public void updateEmployee(EmployeeEntity employeeEntity) {
        EntityType<EmployeeEntity> entityType = getEntityManager().getMetamodel().entity(EmployeeEntity.class);
        CriteriaBuilder criteriaBuilder = getEntityManager().getCriteriaBuilder();
        CriteriaUpdate<EmployeeEntity> criteriaUpdate = criteriaBuilder.createCriteriaUpdate(EmployeeEntity.class);
        Root<EmployeeEntity> query = criteriaUpdate.from(EmployeeEntity.class);
        criteriaUpdate
                .set(query.get(entityType.getSingularAttribute("documentNumber", String.class)), employeeEntity.getDocumentNumber())
                .set(query.get(entityType.getSingularAttribute("firstName", String.class)), employeeEntity.getFirstName())
                .set(query.get(entityType.getSingularAttribute("lastName", String.class)), employeeEntity.getLastName())
                .set(query.get(entityType.getSingularAttribute("email", String.class)), employeeEntity.getEmail())
                .set(query.get(entityType.getSingularAttribute("birthDate", Date.class)), employeeEntity.getBirthDate())
                .set(query.get(entityType.getSingularAttribute("addressHome", String.class)), employeeEntity.getAddressHome())
                .set(query.get(entityType.getSingularAttribute("mobilePhone", String.class)), employeeEntity.getMobilePhone())
                .set(query.get(entityType.getSingularAttribute("hasVaccine", Boolean.class)), employeeEntity.getHasVaccine())
                .set(query.get(entityType.getSingularAttribute("vaccineType", String.class)), employeeEntity.getVaccineType())
                .set(query.get(entityType.getSingularAttribute("vaccineDate", Date.class)), employeeEntity.getVaccineDate())
                .set(query.get(entityType.getSingularAttribute("vaccineNumber", Integer.class)), employeeEntity.getVaccineNumber())
                .set(query.get(entityType.getSingularAttribute("modifiedUserId", String.class)), employeeEntity.getModifiedUserId())
                .set(query.get(entityType.getSingularAttribute("modifiedDate", Date.class)), new Date())
                .where(criteriaBuilder.equal(query.get(entityType.getSingularAttribute("employeeId")), employeeEntity.getEmployeeId()));
        getEntityManager().createQuery(criteriaUpdate).executeUpdate();
    }

    /**
     * Metodo para buscar un empleado por id.
     *
     * @param employeeId
     * @return
     */
    public EmployeeVO findEmployee(Long employeeId) {
        return from(employeeEntity)
                .select(bean(EmployeeVO.class,
                        employeeEntity.employeeId,
                        employeeEntity.documentNumber,
                        employeeEntity.firstName,
                        employeeEntity.lastName,
                        employeeEntity.email,
                        employeeEntity.birthDate,
                        employeeEntity.mobilePhone,
                        employeeEntity.addressHome))
                .where(employeeEntity.employeeId.eq(employeeId).and(employeeEntity.status.eq(true))).fetchFirst();
    }


}
