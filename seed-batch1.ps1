$ErrorActionPreference = "Stop"

function Get-Q($text, $opts, $correct) {
    $options = @()
    for ($i = 0; $i -lt $opts.Count; $i++) {
        $options += @{ optionNumber = $i + 1; text = $opts[$i] }
    }
    return @{ questionText = $text; options = $options; correctOptionNumber = $correct }
}

function Add-Questions($quizId, $questions) {
    $body = @{ quizId = $quizId; questions = $questions } | ConvertTo-Json -Depth 5
    $resp = Invoke-RestMethod -Uri "http://localhost:8080/api/admin/addQuestions" -Method POST -ContentType "application/json" -Body $body
    Write-Host "Quiz $quizId -> $($resp.message)"
}

# ================= QUIZ 1: Java Fundamentals =================
$q1 = @(
    (Get-Q "What is JVM?" @("Java Virtual Machine","Java Variable Method","Java Version Manager","Java Visual Mechanism") 1),
    (Get-Q "Which keyword is used to inherit a class?" @("implements","extends","inherits","super") 2),
    (Get-Q "What is the default value of int in Java?" @("0","null","1","undefined") 1),
    (Get-Q "Which is NOT an OOP concept?" @("Encapsulation","Polymorphism","Compilation","Inheritance") 3),
    (Get-Q "What does 'final' keyword mean for a variable?" @("Value cannot change","Variable is static","Variable is global","Variable is private") 1),
    (Get-Q "Which method is the entry point of a Java program?" @("start()","init()","main()","run()") 3),
    (Get-Q "Is Java platform independent?" @("Yes, due to JVM bytecode","No","Only on Windows","Only with JDK 17") 1),
    (Get-Q "What is a constructor?" @("A special method to initialize objects","A destructor","A static block","An interface method") 1),
    (Get-Q "Which operator compares object references?" @("==","equals()","compare()","===") 1),
    (Get-Q "What is the size of int in Java?" @("16 bits","32 bits","64 bits","8 bits") 2)
)
Add-Questions 11 $q1

# ================= QUIZ 2: Spring Boot Basics =================
$q2 = @(
    (Get-Q "Which annotation starts a Spring Boot application?" @("@SpringBootApplication","@SpringBoot","@Main","@Start") 1),
    (Get-Q "Where do we configure Spring Boot settings?" @("application.properties","web.xml","pom.xml","config.xml") 1),
    (Get-Q "Which annotation creates a REST controller?" @("@RestController","@Controller","@Service","@Repository") 1),
    (Get-Q "What is the default port of Spring Boot?" @("8080","80","3306","9090") 1),
    (Get-Q "Which dependency embeds Tomcat?" @("spring-boot-starter-web","spring-boot-starter-test","mysql-connector","lombok") 1),
    (Get-Q "What does @Autowired do?" @("Injects dependency automatically","Creates a bean manually","Registers a URL","Starts the app") 1),
    (Get-Q "Which annotation maps HTTP GET requests?" @("@GetMapping","@PostMaping","@PutMapping","@DeleteMapping") 1),
    (Get-Q "What file manages dependencies in Spring Boot?" @("pom.xml","build.gradle only","package.json","settings.xml") 1),
    (Get-Q "What is Spring Initializr used for?" @("Bootstrapping new projects","Deploying apps","Writing SQL","Testing APIs") 1),
    (Get-Q "Which annotation marks a service class?" @("@Service","@Entity","@Table","@Bean") 1)
)
Add-Questions 12 $q2

# ================= QUIZ 3: Hibernate ORM =================
$q3 = @(
    (Get-Q "What does ORM stand for?" @("Object Relational Mapping","Object Remote Model","Oracle Runtime Manager","Object Record Mapping") 1),
    (Get-Q "Which annotation marks a JPA entity?" @("@Entity","@Model","@Data","@Class") 1),
    (Get-Q "What is HQL?" @("Hibernate Query Language","Hyper Query Link","High Quality Language","Hibernate Quick Load") 1),
    (Get-Q "Which annotation defines primary key?" @("@Id","@Key","@Primary","@PK") 1),
    (Get-Q "What is lazy loading?" @("Data loaded only when needed","Data never loads","Fast loading always","Loading twice") 1),
    (Get-Q "Which method saves an entity?" @("save()","store()","insert()","commit()") 1),
    (Get-Q "What is a session in Hibernate?" @("Unit of work with DB","HTTP session","User login","Cache server") 1),
    (Get-Q "Which cascade type deletes children?" @("CascadeType.ALL or REMOVE","CascadeType.SAVE","CascadeType.LOAD","None") 1),
    (Get-Q "What does ddl-auto=update do?" @("Updates schema on startup","Deletes DB","Nothing","Only creates tables once") 1),
    (Get-Q "Which interface is used for CRUD in Spring Data JPA?" @("JpaRepository","HttpRepository","DBHandler","SqlMapper") 1)
)
Add-Questions 13 $q3

# ================= QUIZ 4: REST API Design =================
$q4 = @(
    (Get-Q "What does REST stand for?" @("Representational State Transfer","Remote Execution System Transfer","Rapid Enterprise Service Tech","Relational State Transport") 1),
    (Get-Q "Which HTTP method creates a resource?" @("POST","GET","DELETE","OPTIONS") 1),
    (Get-Q "Which status code means success?" @("200","404","500","301") 1),
    (Get-Q "Which status code means not found?" @("404","200","201","400") 1),
    (Get-Q "PUT is used for?" @("Update resource","Create only","Delete only","Fetch only") 1),
    (Get-Q "Which format is most common for REST payloads?" @("JSON","XML only","CSV","Plain text") 1),
    (Get-Q "What does stateless mean in REST?" @("Server stores no client session state","No internet needed","Client is offline","Data never changes") 1),
    (Get-Q "Which status code indicates server error?" @("500","400","200","302") 1),
    (Get-Q "GET requests should be?" @("Safe and idempotent","Always destructive","Encrypted only","Cached forever") 1),
    (Get-Q "What is an endpoint?" @("A URL that exposes an operation","A database table","A CSS style","A build tool") 1)
)
Add-Questions 14 $q4

# ================= QUIZ 5: MySQL Database =================
$q5 = @(
    (Get-Q "Which statement fetches data?" @("SELECT","UPDATE","INSERT","DROP") 1),
    (Get-Q "What is a primary key?" @("Unique row identifier","Any column","Foreign reference","An index type only") 1),
    (Get-Q "Which clause filters rows?" @("WHERE","GROUP BY only","ORDER BY","LIMIT") 1),
    (Get-Q "JOIN combines data from?" @("Multiple tables","One table only","Two databases","Stored procedures") 1),
    (Get-Q "Default MySQL port is?" @("3306","8080","1521","5432") 1),
    (Get-Q "Which removes all rows fast without logging individually?" @("TRUNCATE","DELETE one by one","DROP","ALTER") 1),
    (Get-Q "COUNT(*) returns?" @("Number of rows","Sum of values","Average","First row") 1),
    (Get-Q "A foreign key references?" @("Primary key of another table","Any random column","Itself only","Views only") 1),
    (Get-Q "Which command sorts results?" @("ORDER BY","GROUP BY","WHERE","HAVING") 1),
    (Get-Q "Normalization reduces?" @("Data redundancy","Speed","Storage cost to zero","Joins count to zero") 1)
)
Add-Questions 15 $q5

Write-Host "BATCH 1 DONE (Quizzes 1-5)"
