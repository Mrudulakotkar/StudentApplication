package net.studentdetails.springboot.Controller;

import jakarta.persistence.Id;
import net.studentdetails.springboot.Repository.StudentRepository;
import net.studentdetails.springboot.exception.ResourceNotFoundException;
import net.studentdetails.springboot.model.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin("*")
@RestController
@RequestMapping("/api/v1/student")
public class StudentController {
    @Autowired
    private StudentRepository StudentRepository;

    @GetMapping
    public List<Student> getAllStudent(){
        return StudentRepository.findAll();
    }

    // build create student REST API
    @PostMapping
    public Student createStudent(@RequestBody Student student) {
        return StudentRepository.save(student);
    }


    @GetMapping("{rollno}")
    public ResponseEntity<Student> getStudentById(@PathVariable  long rollno){
        Student student = StudentRepository.findById(rollno)
                .orElseThrow(() -> new ResourceNotFoundException("Student not exist with id:" + rollno));
        return ResponseEntity.ok(student);
}

    @PutMapping("{rollno}")
    public ResponseEntity<Student> updateStudent(@PathVariable long rollno,@RequestBody Student studentDetails) {
        Student updateStudent = StudentRepository.findById(rollno)
                .orElseThrow(() -> new ResourceNotFoundException("Employee not exist with id: " + rollno));

        updateStudent.setFirstName(studentDetails.getFirstName());
        updateStudent.setLastName(studentDetails.getLastName());
        updateStudent.setCity(studentDetails.getCity());
        updateStudent.setRollno(studentDetails.getRollno());

       StudentRepository.save(updateStudent);

        return ResponseEntity.ok(updateStudent);
    }

    @DeleteMapping("{rollno}")
    public ResponseEntity<HttpStatus> deleteStudent(@PathVariable long rollno){

        Student student = StudentRepository.findById(rollno)
                .orElseThrow(() -> new ResourceNotFoundException("Student not exist with id: " + rollno));

        StudentRepository.delete(student);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);

    }
}

