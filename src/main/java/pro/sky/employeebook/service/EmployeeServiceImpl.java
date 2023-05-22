package pro.sky.employeebook.service;

import org.springframework.stereotype.Service;
import pro.sky.employeebook.exception.EmployeeAlreadyAddedException;
import pro.sky.employeebook.exception.EmployeeNotFoundException;
import pro.sky.employeebook.exception.StorageFullException;
import pro.sky.employeebook.model.Employee;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    private final Map<String,Employee> employees;
    private final int MAX_NUMBER_OF_EMPLOYEES = 6;

    public int getMAX_NUMBER_OF_EMPLOYEES() {

        return MAX_NUMBER_OF_EMPLOYEES;
    }

    public  EmployeeServiceImpl() {
        this.employees = new HashMap<>();
    }
    @Override
    public Employee add(String firstName, String lastName) {
        if(this.employees.size()>=this.getMAX_NUMBER_OF_EMPLOYEES()) {
            throw new StorageFullException();
        }
        Employee employee = new Employee(firstName, lastName);
        String key = lastName + firstName;
        if (employees.containsKey(key)) {
            throw new EmployeeAlreadyAddedException();
        }
        employees.put(key, employee);
        return employee;
    }

    @Override
    public Employee remove(String firstName, String lastName) {
        Employee employee = new Employee(firstName, lastName);
        String key = lastName + firstName;
        if (employees.remove(key, employee)) {
            return employee;
        }
        throw new EmployeeNotFoundException();
    }

    @Override
    public Employee find(String firstName, String lastName) {
        Employee employee = new Employee(firstName, lastName);
        String key = lastName + firstName;
        if (employees.containsKey(key)) {
            return employee;
        }
        throw new EmployeeNotFoundException();
    }

    @Override
    public Map<String, Employee> list() {
       return new HashMap<>(employees);
    }


}
