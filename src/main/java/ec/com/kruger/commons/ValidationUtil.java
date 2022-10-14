package ec.com.kruger.commons;

import ec.com.kruger.vo.EmployeeVO;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Clase utilitaria para metodos de validacion.
 */
public final class ValidationUtil {

    /**
     * Metodo para validar numero de cedula.
     *
     * @param documentNumber
     * @return
     */
    public static Boolean validateDocumentNumber(String documentNumber) {

        if(documentNumber == null || documentNumber == ""){
            return false;
        }

        int total = 0;
        int longDocument = 10;
        int[] coefficients = {2, 1, 2, 1, 2, 1, 2, 1, 2};
        int provincesNumber = 24;
        int thirdDigit = 6;
        if (documentNumber.matches("[0-9]*") && documentNumber.length() == longDocument) {
            int province = Integer.parseInt(documentNumber.charAt(0) + "" + documentNumber.charAt(1));
            int threeDigit = Integer.parseInt(documentNumber.charAt(2) + "");
            if ((province > 0 && province <= provincesNumber) && threeDigit < thirdDigit) {
                int checkDigit = Integer.parseInt(documentNumber.charAt(9) + "");
                for (int i = 0; i < coefficients.length; i++) {
                    int valor = Integer.parseInt(coefficients[i] + "") * Integer.parseInt(documentNumber.charAt(i) + "");
                    total = valor >= 10 ? total + (valor - 9) : total + valor;
                }
                int checkedDigit = total >= 10 ? (total % 10) != 0 ? 10 - (total % 10) : (total % 10) : total;
                if (checkedDigit == checkDigit) {
                    return true;
                }
            }
            return false;
        }
        return false;
    }

    /**
     * Metodo para validar nombre o apellido.
     *
     * @param name
     * @return
     */
    public static Boolean validateName(String name) {

        if(name == null || name == ""){
            return false;
        }

        Pattern pat = Pattern.compile("^(?!.* (?: |$))[A-Z][a-z ]+$");
        Matcher mat = pat.matcher(name);
        if (mat.matches()) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * Metodo para validar email.
     *
     * @param email
     * @return
     */
    public static Boolean validateEmail(String email) {

        if(email == null || email == ""){
            return false;
        }

        String emailREGEX = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
        Pattern pattern = Pattern.compile(emailREGEX);
        if (email == null) {
            return false;
        }
        return pattern.matcher(email).matches();
    }

    /**
     * Metodo para generar un usuario.
     *
     * @param firstName
     * @param lastName
     * @return
     */
    public static String generateUserName(String firstName, String lastName) {
        return firstName.substring(0, 1).toLowerCase()
                .concat(lastName);
    }

    /**
     * Metodo para generar una contraseña.
     *
     * @return
     */
    public static String generatePassword() {
        return String.valueOf(new Date().getTime());
    }

    /**
     * Formate una fecha de string a date.
     * @param date
     * @return
     */
    public static Date formatterDate(String date){

        if(date == null || date == ""){
            return null;
        }

        ZoneId defaultZoneId = ZoneId.systemDefault();
        DateTimeFormatter formatter_1 = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate localDate = LocalDate.parse(date, formatter_1);
        Date d = Date.from(localDate.atStartOfDay(defaultZoneId).toInstant());
        return d;
    }

    /**
     * valida los campos requeridos de la vacuna.
     * @param employeeVO
     * @param result
     * @return
     */
    public static Boolean validateVaccineFields(EmployeeVO employeeVO, StringBuilder result) {
        Boolean isValid = true;
        if (employeeVO.getHasVaccine()) {
            if (employeeVO.getVaccineType() == null || employeeVO.getVaccineType() == "") {
                result.append("El tipo de vacuna es requerido.").append("\n");
                isValid = false;
            }
            if (employeeVO.getVaccineDate() == null) {
                result.append("La fecha de vacuna es requerida.").append("\n");
                isValid = false;
            }
            if (employeeVO.getVaccineNumber() == null) {
                result.append("El numero de vacuna es requerido.").append("\n");
                isValid = false;
            }
        }else {
            employeeVO.setVaccineType(null);
            employeeVO.setVaccineDate(null);
            employeeVO.setVaccineNumber(null);
            isValid = true;
        }
        return isValid;
    }

    /**
     * Valida los campos generales del empleado.
     * @param result
     * @param employeeVO
     * @return
     */
    public static Boolean validateGeneralFields(StringBuilder result, EmployeeVO employeeVO) {

        Boolean isValidFirstName = true;
        Boolean isValidLastName = true;
        Boolean isValidEmail = true;
        Boolean isValidDocumentNumber = true;

        if (employeeVO.getFirstName() == null || employeeVO.getFirstName() == "") {
            result.append("El nombre es requerido.").append("\n");
            isValidFirstName = false;
        }else{
            if(!ValidationUtil.validateName(employeeVO.getFirstName())){
                result.append("El nombre no es valido.").append("\n");
                isValidFirstName = false;
            }
        }

        if (employeeVO.getFirstName() == null || employeeVO.getLastName() == "") {
            result.append("El apellido es requerido.").append("\n");
            isValidLastName = false;
        }else{
            if(!ValidationUtil.validateName(employeeVO.getLastName())){
                result.append("El apellido no es valido.").append("\n");
                isValidLastName = false;
            }
        }

        if (employeeVO.getEmail() == null || employeeVO.getEmail() == "") {
            result.append("El email es requerido.").append("\n");
            isValidEmail = false;
        }else{
            if(!ValidationUtil.validateEmail(employeeVO.getEmail())){
                result.append("El email no es valido.").append("\n");
                isValidEmail = false;
            }
        }

        if (employeeVO.getDocumentNumber() == null || employeeVO.getDocumentNumber() == "") {
            result.append("La cedula es requerida.").append("\n");
            isValidDocumentNumber = false;
        }else{
            if(!ValidationUtil.validateDocumentNumber(employeeVO.getDocumentNumber())){
                result.append("La cedula no es valida.").append("\n");
                isValidDocumentNumber = false;
            }
        }

        if (!isValidFirstName || !isValidLastName || !isValidEmail || !isValidDocumentNumber) {
            return false;
        }else {
            return true;
        }
    }

}

