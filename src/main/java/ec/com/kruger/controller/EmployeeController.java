package ec.com.kruger.controller;


import ec.com.kruger.commons.ValidationUtil;
import ec.com.kruger.service.EmployeeService;
import ec.com.kruger.vo.EmployeeVO;
import ec.com.kruger.vo.FilterVO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

/**
 * Controlador para la administración de los web service del empleado.
 */

@RestController
@RequestMapping("/api/public/employee")
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    /**
     *  Servicio para encontrar a todas los empleados.
     * @param filterVO
     * @return
     */
    @Operation(summary = "Lista a todos los empleados", description = "Servicio para listar todos los empleados")
    @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Peticion con filtros de busqueda", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = FilterVO.class))}, required = true)
    @ApiResponse(responseCode = "200", description = "Se encontro una lista de empleados con exito", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = EmployeeVO.class))})
    @PostMapping ("/findAll")
    public ResponseEntity<List<EmployeeVO>> findAll(@RequestBody FilterVO filterVO) {
        try {
            Date dateIniFormatter = ValidationUtil.formatterDate(filterVO.getVaccineDateIni());
            filterVO.setVaccineDateIniFormatter(dateIniFormatter);

            Date dateEndFormatter = ValidationUtil.formatterDate(filterVO.getVaccineDateEnd());
            filterVO.setVaccineDateEndFormatter(dateEndFormatter);

            List<EmployeeVO> employeeVOList = this.employeeService.findAll(filterVO);
            return new ResponseEntity<>(employeeVOList, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Servicio para crear una empleado.
     *
     * @param employeeVO
     * @return
     */
    @Operation(summary = "Ingresa toda la información de un nuevo empleado", description = "Servicio para ingresar  información de un nuevo empleado")
    @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Peticion con filtros de busqueda", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = EmployeeVO.class))}, required = true)
    @ApiResponse(responseCode = "200", description = "Se creo un nuevo empleado con exito", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = EmployeeVO.class))})
    @PostMapping("/createEmployee")
    public ResponseEntity<String> createEmployee(@RequestBody EmployeeVO employeeVO) {
        try {
            String result = this.employeeService.createOrupdateEmployee(employeeVO);
            return new ResponseEntity<>(result, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Servicio para eliminar un empleado.
     *
     * @param employeeId
     * @return
     */
    @Operation(summary = "Elimina un empleado", description = "Servicio para eliminar un empleado")
    @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Peticion con filtros de busqueda", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = EmployeeVO.class))}, required = true)
    @ApiResponse(responseCode = "200", description = "Se elimino un empleado con exito", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = EmployeeVO.class))})
    @DeleteMapping("/deleteEmployee/{id}/{userId}")
    public ResponseEntity<String> deleteEmployee(@PathVariable("id") Long employeeId, @PathVariable("userId") String userId) {
        try {
            String result = this.employeeService.deleteEmployee(employeeId, userId);
            return new ResponseEntity<>(result, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Ocurrio un error", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Servicio para actualizar un empleado.
     *
     * @param personVO
     * @return
     */
    @Operation(summary = "Actualiza la información de un empleado", description = "Servicio para actualizar  información de un empleado")
    @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Peticion con filtros de busqueda", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = EmployeeVO.class))}, required = true)
    @ApiResponse(responseCode = "200", description = "Se actualizo un empleado con exito", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = EmployeeVO.class))})
    @PostMapping("/updateEmployee")
    public ResponseEntity<String> updateEmployee(@RequestBody EmployeeVO personVO) {
        try {
            String result = this.employeeService.createOrupdateEmployee(personVO);
            return new ResponseEntity<>(result, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Servicio para buscar un empleado por id.
     *
     * @param id
     * @return
     */
    @Operation(summary = "Obtiene la informacion de un empleado por id", description = "Servicio para obtenber informacion de un empleado por id")
    @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Peticion con filtros de busqueda", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = Long.class))}, required = true)
    @ApiResponse(responseCode = "200", description = "La consulta se realizo exitosamente", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = EmployeeVO.class))})
    @GetMapping("/findById/{id}")
    public ResponseEntity<EmployeeVO> findById(@PathVariable("id") Long id) {
        try {
            EmployeeVO personVO = this.employeeService.findEmployee(id);
            return new ResponseEntity<>(personVO, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }



}
