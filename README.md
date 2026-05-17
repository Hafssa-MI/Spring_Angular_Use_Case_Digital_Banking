# Digital Banking Backend Application

## Description du projet
This project is a robust backend system for a Digital Banking application built with **Spring Boot**. It manages customers, different types of bank accounts (Current and Saving), and tracks all financial operations.

---

##  Progrès de developpement 

###  Phase 1: Entités
-  **Commit:** Création des entités et setup JPA pour le MOR.
- [x] Création des entités Customer, BankAccount, AccountOperation, CurrentAccount, SavingAccount.
- [x] Ajout des Anotations JPA; @Entity, @ID, @OneToMany, @ManyToOne 
- [x] Effectuation d'héritage avec SingleTable méthode et les Annotations @Ineritance, @DiscriminatorColumn, @DiscriminatorValue


###  Phase 2: Repositories
-  **Commit:** Création des Repository.
- [x] Création des repositories CustomerRepository, BankAccountRepository, AccountOperationRepository.
- [x] Ajout de l'annotation @Enumerated pour voir les enumerations en String dans le tableau
- [x] Tester la création des tableaux en créeons dans le @Bean CommandLineRunner.
- [x] Utilistion de mysql au lieu de h2 database


###  Phase 3: Services
- **Commit:** Création des Services.
- [x] Création des services interfaces et leurs implémentation : BankAccountService, BankAccountServiceImpl
- [x] Ajout de l'annotation @Service et @Transactional de springframework
- [x] Injection des dépendances avec un constructeur @AllArgsConstructor de lombok, Ajout de la journalisation @Slf4j de lombok
- [x] Respect des règles metiers en ajoutant les Exceptions dans le package Exceptions : CustomerNotFoundException(surveillé) ...


###  Phase 4: DTO
- **Commit:** Création des DTOs.
- [x] Création des Classes DTO (CustomerDTO..) contenant seulement les attributs dont on a besoin
- [x] Utilisation juste de l'annotation @Data
- [x] Création des mappers (BankAccountMapperImpl..) et Ajout de l'attribut Mapper dans le Service
- [x] Utilisation du BeanUtils pour le mapping Objet en Objet DTO
- [x] Ajout de Swagger.


###  Phase 5: Sécurité
- **Commit:** Configuration de la sécurité.
- [x] Création du package Security
- [x] Création d'une class SecurityConfig avec les annotation @Configuration @EnableWebSecurity et des @Bean méthodes : InMemoryUserDetailManager pour spécifier les utilistauers qui ont droit d'accès, PasswordEncoder qui retourne BCryptPasswordEncoder, SecurityFilterChain pour établir la sécurité Stateless donc on désactive le csrf et l'authorization request de type auth2 d'ou l'obligation de créer un JWTEncoder et JWTDecoder Beans
- [x] Création de la classe SecurityController pour les méthodes du Mapping avec les annotation @RestController et @RequestMapping
- [x] Ajout du CrossConfigurationSource dans le SecurityConfig


###  Phase 6: ChatbotAI avec Telegram
- **Commit:** Configuration du chatbot ai avec Telegram.
- [x] Ajout des dependencies
- [x] Création du AIAgent et MCP Server et connexion avec MCP Client
- [x] Ajout du ChatController
- [x] Ajout du TelegramBot
- [x] Liaison avec front en angular et ajout du rag et utilisation du ollama pour lembedding et groq pour le chat

---


### Prerequis
* Java JDK 17 or higher
* Maven 3.6+
* IntelliJ IDEA 

