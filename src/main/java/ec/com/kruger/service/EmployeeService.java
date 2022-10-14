package ec.com.kruger.service;

import ec.com.kruger.commons.ValidationUtil;
import ec.com.kruger.entity.EmployeeEntity;
import ec.com.kruger.entity.UserEntity;
import ec.com.kruger.repository.EmployeeRepository;
import ec.com.kruger.repository.UserRepository;
import ec.com.kruger.vo.EmployeeVO;
import ec.com.kruger.vo.FilterVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;

/**
 * Clase para implementar metodos de servicios empleado.
 */
@Service
@Slf4j
@Transactional
public class EmployeeService {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private UserRepository userRepository;


    /**
     * Metodo para listar empleados.
     *
     * @param filterVO
     */
    public List<EmployeeVO> findAll(FilterVO filterVO) {
        return this.employeeRepository.findAll(filterVO);
    }

    /**
     * Metodo para crear o actualizar un empleado.
     *
     * @param employeeVO
     */
    public String createOrupdateEmployee(EmployeeVO employeeVO) {
        try {

            StringBuilder result = new StringBuilder();

            if (employeeVO.getEmployeeId() != null) {
                EmployeeVO employeeVOFound = this.employeeRepository.findEmployee(employeeVO.getEmployeeId());
                if (employeeVOFound == null) {
                    return "La informacion no se actualizo, por que el empleado no existe.";
                }
            }

            if (ValidationUtil.validateGeneralFields(result, employeeVO)) {

                //validar campos requeridos de la vacuna
                if (!ValidationUtil.validateVaccineFields(employeeVO, result)) {
                    return result.toString();
                }

                EmployeeEntity employeeEntity = EmployeeEntity.builder()
                        .employeeId(employeeVO.getEmployeeId())
                        .documentNumber(employeeVO.getDocumentNumber())
                        .firstName(employeeVO.getFirstName())
                        .lastName(employeeVO.getLastName())
                        .email(employeeVO.getEmail())
                        .birthDate(employeeVO.getBirthDate())
                        .addressHome(employeeVO.getAddressHome())
                        .mobilePhone(employeeVO.getMobilePhone())
                        .hasVaccine(employeeVO.getHasVaccine())
                        .vaccineType(employeeVO.getVaccineType())
                        .vaccineDate(employeeVO.getVaccineDate())
                        .vaccineNumber(employeeVO.getVaccineNumber())
                        .status(true)
                        .createUserId(employeeVO.getCreateUserId())
                        .createdDate(new Date())
                        .build();

                if (employeeEntity.getEmployeeId() == null) {

                    //crear el empleado
                    this.employeeRepository.createEmployee(employeeEntity);

                    //instanciar el usuario y contraseña
                    UserEntity userEntity = UserEntity.builder()
                            .userName(ValidationUtil.generateUserName(employeeEntity.getFirstName(), employeeEntity.getLastName()))
                            .password(ValidationUtil.generatePassword())
                            .createdDate(new Date())
                            .employeeId(employeeEntity.getEmployeeId())
                            .createUserId(employeeEntity.getCreateUserId())
                            .status(true)
                            .build();
                    
                    //crear el usuario en la bdd
                    this.userRepository.createUser(userEntity);
                    return "La informacion se registro exitosamente.";
                } else {
                    this.employeeRepository.updateEmployee(employeeEntity);
                    return "La informacion se actualizo exitosamente.";
                }

            } else {
                ValidationUtil.validateVaccineFields(employeeVO, result);
                return result.toString();
            }
        } catch (Exception e) {
            log.error("Ocurrio un error en: {}", e.getMessage());
            return e.getMessage();
        }
    }


    /**
     * Metodo para eliminar un empleado.
     *
     * @param employeeId
     * @param userId
     */
    public String deleteEmployee(Long employeeId, String userId) {
        try {
            EmployeeVO employeeVO = this.employeeRepository.findEmployee(employeeId);
            if (employeeVO == null) {
                return "No se pudo eliminar el empleado, por que no existe.";
            } else {
                this.employeeRepository.deleteEmployee(employeeId, userId);
                this.userRepository.deleteUser(employeeId, userId);
                return "El empleado se elimino exitosamente.";
            }

        } catch (Exception e) {
            log.error("Ocurrio un error en: {}", e.getMessage());
            return e.getMessage();
        }
    }

    /**
     * Metodo para buscar un empleado.
     *
     * @param employeeId
     * @return
     */
    public EmployeeVO findEmployee(Long employeeId) {
        EmployeeVO employeeVO = this.employeeRepository.findEmployee(employeeId);
        if (employeeVO == null) {
            employeeVO = EmployeeVO.builder()
                    .message("No se encontro informacion de empleado")
                    .build();
        }
        return employeeVO;
    }
}
