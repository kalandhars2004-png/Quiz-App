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

# ================= QUIZ 6: Java Collections =================
$q6 = @(
    (Get-Q "Which collection does NOT allow duplicates?" @("Set","List","Queue","ArrayList") 1),
    (Get-Q "Default capacity of ArrayList?" @("10","16","8","32") 1),
    (Get-Q "Which is thread-safe?" @("Vector","ArrayList","LinkedList","HashSet") 1),
    (Get-Q "HashMap allows null key?" @("Yes, one null key","No","Two null keys","Only with TreeMap") 1),
    (Get-Q "Which maintains insertion order?" @("LinkedHashSet","HashSet","TreeSet","Hashtable") 1),
    (Get-Q "Which sorts elements automatically?" @("TreeMap","HashMap","LinkedHashMap","ArrayList") 1),
    (Get-Q "Iterator is used to?" @("Traverse collections","Sort lists only","Copy arrays","Delete classes") 1),
    (Get-Q "Which interface does Queue extend?" @("Collection","Map","Set","List") 1),
    (Get-Q "What is the difference between List and Set?" @("List allows duplicates, Set does not","No difference","Set is ordered","List cannot grow") 1),
    (Get-Q "Collections.sort() works on?" @("List","Set","Map","Queue") 1)
)
Add-Questions 16 $q6

# ================= QUIZ 7: Microservices =================
$q7 = @(
    (Get-Q "Microservices break an app into?" @("Small independent services","One big module","Single database process","Monolithic layers") 1),
    (Get-Q "Service discovery helps with?" @("Locating service instances","Writing SQL","UI styling","Compiling code") 1),
    (Get-Q "API Gateway acts as?" @("Single entry point","Database proxy","File server","Test runner") 1),
    (Get-Q "Which pattern isolates failures?" @("Circuit Breaker","Singleton","Factory","Builder") 1),
    (Get-Q "Each microservice ideally has?" @("Its own database","Shared single DB","No database","One global session") 1),
    (Get-Q "Communication between services commonly uses?" @("REST or messaging","Fax","Bluetooth","Serial port") 1),
    (Get-Q "Spring Cloud provides?" @("Microservices tooling","CSS frameworks","Image editors","SQL GUIs") 1),
    (Get-Q "Load balancing distributes?" @("Traffic across instances","Code commits","Disk space","Memory chips") 1),
    (Get-Q "Config server centralizes?" @("Configuration management","User passwords in code","Logs","Databases") 1),
    (Get-Q "Saga pattern manages?" @("Distributed transactions","Frontend routing","CSS layout","Git branches") 1)
)
Add-Questions 17 $q7

# ================= QUIZ 8: Spring Security =================
$q8 = @(
    (Get-Q "JWT stands for?" @("JSON Web Token","Java Web Tool","JavaScript Wire Transfer","Joint Web Test") 1),
    (Get-Q "Authentication verifies?" @("Who you are","What you can do","Network speed","DB size") 1),
    (Get-Q "Authorization decides?" @("What you can access","Your password strength","Your IP","Server location") 1),
    (Get-Q "BCrypt is used for?" @("Password hashing","Encryption of URLs","Compression","Logging") 1),
    (Get-Q "Which filter chain class secures requests?" @("SecurityFilterChain","CorsFilterChain","AuthChain","WebFilter") 1),
    (Get-Q "Stateless JWT means server?" @("Stores no session","Saves all sessions","Uses cookies only","Needs DB per request") 1),
    (Get-Q "@EnableWebSecurity enables?" @("Spring Security config","REST endpoints","JPA repos","Dev tools") 1),
    (Get-Q "401 status means?" @("Unauthorized","Forbidden","Not found","Success") 1),
    (Get-Q "403 status means?" @("Forbidden - no permission","Not logged in","Bad request","Server error") 1),
    (Get-Q "CSRF protection guards against?" @("Cross-site request forgery","SQL injection only","DDoS","XSS only") 1)
)
Add-Questions 18 $q8

# ================= QUIZ 9: Docker Basics =================
$q9 = @(
    (Get-Q "A container runs from?" @("An image","A JVM","A VM snapshot","A jar file directly") 1),
    (Get-Q "Dockerfile instruction for base image?" @("FROM","RUN","COPY","ENTRYPOINT") 1),
    (Get-Q "docker build creates?" @("An image","A container","A volume","A network") 1),
    (Get-Q "docker run does?" @("Starts a container","Builds image","Pushes image","Deletes volume") 1),
    (Get-Q "Which command lists containers?" @("docker ps","docker list","docker show","docker get") 1),
    (Get-Q "Volumes are used for?" @("Persistent data","Networking","Building images","CPU limits") 1),
    (Get-Q "docker-compose manages?" @("Multi-container apps","Single process only","Kubernetes clusters","Git repos") 1),
    (Get-Q "EXPOSE in Dockerfile?" @("Documents the port","Opens firewall","Publishes port always","Stops traffic") 1),
    (Get-Q "Registry stores?" @("Images","Containers","Volumes","Networks") 1),
    (Get-Q "Container vs VM: containers share?" @("Host OS kernel","Full guest OS","Hypervisor","BIOS") 1)
)
Add-Questions 19 $q9

# ================= QUIZ 10: Git & GitHub =================
$q10 = @(
    (Get-Q "git init does?" @("Initializes a repo","Clones a repo","Commits changes","Pushes code") 1),
    (Get-Q "Which stages changes?" @("git add","git commit","git push","git pull") 1),
    (Get-Q "git clone copies?" @("Remote repo locally","Stash to branch","Commit to log","Tag to HEAD") 1),
    (Get-Q "Branch is used to?" @("Isolate development lines","Delete history","Merge automatically","Encrypt files") 1),
    (Get-Q "git merge combines?" @("Two branches","Two users","Two remotes","Two stashes") 1),
    (Get-Q ".gitignore file does?" @("Excludes files from tracking","Compresses repo","Signs commits","Creates tags") 1),
    (Get-Q "git pull = ?" @("fetch + merge","push + commit","clone + init","add + stash") 1),
    (Get-Q "Conflict happens when?" @("Same lines changed separately","Repo is new","Branch deleted","Commit unsigned") 1),
    (Get-Q "PR (Pull Request) is for?" @("Reviewing before merging","Deleting branches","Renaming files","Reverting tags") 1),
    (Get-Q "git log shows?" @("Commit history","Working tree","Stash list","Remote URLs") 1)
)
Add-Questions 20 $q10

Write-Host "BATCH 2 DONE (Quizzes 6-10)"
