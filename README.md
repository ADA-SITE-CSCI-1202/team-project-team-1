[![Review Assignment Due Date](https://classroom.github.com/assets/deadline-readme-button-24ddc0f5d75046c5622901739e7c5dd533143b0c8e959d652212380cedb1ea36.svg)](https://classroom.github.com/a/4zK3HDh5)

![Uml](umlDiagram.jpg)

## UML Diagram of Project
```java
@startuml

package "Models" {
    class Book {
        - Title: String
        - Author: String
        - Rating: float
        - Reviews: List<Review>
        - Status: boolean
        - TimeSpent: int
        - StartDate: DateTime
        - EndDate: DateTime

        + getTitle(): String
        + setTitle(title: String): void
        + getAuthor(): String
        + setAuthor(author: String): void
        + getRating(): int
        + setRating(rating: int): void
        + getReviews(): List<Review>
        + setReviews(reviews: List<Review>): void
        + getStatus(): bool
        + setStatus(status: bool): void
        + getTimeSpent(): int
        + setTimeSpent(timeSpent: int): void
        + getStartDate(): Date
        + setStartDate(startDate: Date): void
        + getEndDate(): Date
        + setEndDate(endDate: Date): void
    }

    class Review {
        - User: User
        - Content: String

        + getUser(): User
        + setUser(user: User): void
        + getContent(): String
        + setContent(content: String): void
    }

    class User {
        - Username: String
        - Password: String
        - isAdmin: boolean

        + getUsername(): String
        + setUsername(username: String): void
        + getPassword(): String
        + setPassword(password: String): void
    }
}

interface ILogin {
    + user: User
    + Login(User): boolean
}

interface IRegister {
    + user: User
    + Register(User): boolean
}

ILogin <|.. IRegister : extends

@enduml
```

In Login System package’s IdandPassword.java class, we read Users.csv file and assign it to a hashmap. And with this class’ getLoginInfo() method, we can get that hashmap.
Whenever user input username and password, it is checked whether this username and password is in this hashmap. If yes, and if login and password equal to “admin”, then adminView is opened, else if they equal to user, userView is opened. If username is correct but password is wrong, program warns user that password is wrong. There are some exceptional(error) cases as well:

In this JFrame, user tries to sign new UserName and Password to the Database. But there are exceptional cases and some requirements:
•	Username should be between 5 and 15 characters
•	Password should contain at least 8 characters, including at least 1 Uppercase, 1 Lowercase letter, and 1 number 
•	The second and the first passwords should be the same
•	The most import: Username should be new
•	All the blanks should be entered

if any of them is not handled, then error and corresponding JOptionPanes appears. Each of them has custom classes extending an exception and initializing its JOptionPane.
