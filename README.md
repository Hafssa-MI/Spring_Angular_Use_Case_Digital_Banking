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


---


### Prerequis
* Java JDK 17 or higher
* Maven 3.6+
* IntelliJ IDEA 

