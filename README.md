<h1> Spring Cloud OpenFeign Kullanarak Servisler Arası İletişim </h1>



<p>OpenFeign, mikroservis mimarilerinde servisler arası iletişimi kolaylaştıran bir araçtır. Bu aracın sunduğu özellikler, yazılım geliştiricilere daha okunabilir, modüler ve konfigürasyonel olarak esnek bir şekilde servis çağrıları yapma imkanı tanır.
<hr>

<p> Spring Cloud OpenFeign uygulama örneğinde aşağıdaki servisler arası veri aktarımı söz konusu olacaktır.
<ul>       

<li>  Student   </li>
<li>   StudentDetail    </li>


</ul>

<h3> Student Service </h3>

```
@RestController
public class StudentController {

    @GetMapping("firat-university")
    public List<String> getFindByFiratUniversity(){
        return Arrays.asList("Burak","Ahmet","Mehmet","Veli");
    }

    @GetMapping("istanbul-university")
    public List<String> getFindByIstanbulUniversity(){
        return Arrays.asList("Ali","Hakkı","Buğra","Kaya");
    }
}


```

<h3> StudentDetail Service </h3>

<p> Open Feign'i kullanmak için bir interface yapacağız ve bu interface içerisinde bağlanmak istediğimiz servis url'i ve kullanmak istediğimiz metotları yazarak endpointleri belirlememiz gerekiyor

```
@FeignClient(value = "student", url = "http://localhost:8081")
public interface StudentApi {
    @GetMapping("/firat-university")
    List<String> getFindByFiratUniversity();

    @GetMapping("/istanbul-university")
    List<String> getFindByIstanbulUniversity();
}

```
<p> <bold> FeignClient </bold> ile birlikte servis adını ve url tanımlaması yapıyoruz
Interface'de metot tanımlamaları yaparken <bold> @GetMapping </bold> ile Student servisindeki endpoint adreslerine gideceğini söylemiş oluyoruz.
<br>
StudentDto oluşturalım ve Student servisinden gelen değerleri üniversitelere göre tutalım


```
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class StudentDto {
    private String schoolName;
    private String budget;
    private List<String> students;
}

```
Student Detail Controller'imizi oluşturalım

```
@RestController
public class StudentDetailController {

    private final StudentApi studentApi;

    public StudentDetailController(StudentApi studentApi) {
        this.studentApi = studentApi;
    }

    @GetMapping("/get-firat-student")
    public ResponseEntity getFiratStudent() {

        StudentDto studentDto =StudentDto.builder().schoolName("FIRAT UNIVERSITY")
                .budget("2500")
                .students(studentApi.getFindByFiratUniversity()).build();
    

        return ResponseEntity.ok().body(studentDto);
    }   
    
    @GetMapping("/get-istanbul-student")
    public ResponseEntity getIstanbulStudent() {

        StudentDto studentDto = StudentDto.builder().schoolName("ISTANBUL UNIVERSITY")
                .budget("3500")
                .students(studentApi.getFindByIstanbulUniversity()).build();
    

        return ResponseEntity.ok().body(studentDto);
    }
}

```




StudentDetail servisimizi çağırdığımızda aşağıdaki gibi bir yanıt alacağız



```
{
  "schoolName": "FIRAT UNIVERSITY",
  "budget": "3500",
  "students": [
    "Ali",
    "Hakkı",
    "Buğra",
    "Kaya"
  ]
}

```
ve 

```
{
  "schoolName": "ISTANBUL UNIVERSITY",
  "budget": "2500",
  "students": [
    "Burak",
    "Ahmet",
    "Mehmet",
    "Veli"
  ]
}

```
