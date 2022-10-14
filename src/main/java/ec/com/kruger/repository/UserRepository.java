package ec.com.kruger.repository;

import ec.com.kruger.entity.EmployeeEntity;
import ec.com.kruger.entity.UserEntity;
import ec.com.kruger.vo.EmployeeVO;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.stereotype.Repository;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaUpdate;
import javax.persistence.criteria.Root;
import javax.persistence.metamodel.EntityType;
import java.util.Date;

import static com.querydsl.core.types.Projections.bean;
import static ec.com.kruger.entity.QUserEntity.userEntity;

/**
 *
 */
@Repository
public class UserRepository extends QuerydslRepositorySupport {

    public UserRepository() {
        super(UserEntity.class);
    }

    /**
     * Metodo para crear usuarios.
     *
     * @param userEntity
     */
    public void createUser(UserEntity userEntity) {
        this.getEntityManager().persist(userEntity);
    }

    /**
     * Metodo para buscar un usuario nombre.
     *
     * @param userName
     * @return
     */

    public EmployeeVO findUserByName(String userName) {
        return from(userEntity)
                .select(bean(EmployeeVO.class,
                        userEntity.userId,
                        userEntity.userName,
                        userEntity.password))
                .where(userEntity.userName.eq(userName).and(userEntity.status.eq(true))).fetchFirst();
    }

    /**
     * Metodo para eliminar un usuario.
     *
     * @param employeeId
     * @param userId
     */
    public void deleteUser(Long employeeId, String userId) {
        EntityType<UserEntity> entityType = getEntityManager().getMetamodel().entity(UserEntity.class);
        CriteriaBuilder criteriaBuilder = getEntityManager().getCriteriaBuilder();
        CriteriaUpdate<UserEntity> criteriaUpdate = criteriaBuilder.createCriteriaUpdate(UserEntity.class);
        Root<UserEntity> query = criteriaUpdate.from(UserEntity.class);
        criteriaUpdate
                .set(query.get(entityType.getSingularAttribute("status", Boolean.class)), false)
                .set(query.get(entityType.getSingularAttribute("modifiedUserId", String.class)), userId)
                .set(query.get(entityType.getSingularAttribute("modifiedDate", Date.class)), new Date())
                .where(criteriaBuilder.equal(query.get(entityType.getSingularAttribute("employeeId")), employeeId));
        getEntityManager().createQuery(criteriaUpdate).executeUpdate();
    }

}
