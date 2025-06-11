# User Panel

We will focus on basic User Panel functions.

# Content
- [User Panel](#user-panel)
- [Content](#content)
- [To-dos](#to-dos)
- [Requirement Gathering](#requirement-gathering)
- [Software Design](#software-design)
  - [Monolithic](#monolithic)
  - [Layered Architecture](#layered-architecture)
  - [ER Diagram](#er-diagram)
  - [Data Flow Diagram](#data-flow-diagram)
- [Install](#install)

# To-dos

*  [x] Temel isterlerin oluşturulması, gereksinimlerin netleştirilmesi
*  [x] Tasarım
   *  [x] Sistem Mimarisi
   *  [x] Veritabanı
*  [ ] Kodlama
   *  [ ] Geliştime Ortamı (Backend-Frontend)
   *  [ ] Kodlama Standartı
   *  [ ] Versiyon Kontrolü
*  [ ] Test
   *  [ ] Birim Testlerinin oluşturulması
*  [ ] Dağıtım
   *  [ ] Projenin canlıya alınması

# Requirement Gathering

Kullanıcıların sisteme güvenli bir şekilde kayıt olup giriş yapabileceği bir kullanıcı paneli oluşturmak. Kullanıcı sistem içerisinde kendine ait bilgileri görüntüleyebilme, bazı bilgilerini güncelleyebilme gibi özelliklere sahip olmalı. Gelişmiş özellikler ilerleyen süreçler içerisinde eklenecektir.

# Software Design

## Monolithic

```mermaid
graph TB
    subgraph "Client Layer"
        A[Web Frontend<br/>React/Angular/Vue]
        B[Mobile App<br/>React Native/Flutter]
        C[API Client<br/>Postman/REST Client]
    end
    
    subgraph "Spring Boot Monolithic Application"
        subgraph "Web API Layer (Controllers)"
            D[AuthController<br/>login, register, logout]
            E[UserController<br/>profile, update]
            F[RoleController<br/>CRUD operations]
            G[PermissionController<br/>CRUD operations]
            H[UserManagementController<br/>assign roles/permissions]
            I[SessionController<br/>session management]
        end
        
        subgraph "Security Layer"
            J[Spring Security<br/>Authentication & Authorization]
            K[JWT Token Provider<br/>token generation/validation]
            L[Custom Permission Evaluator<br/>method-level security]
            M[JWT Authentication Filter<br/>request filtering]
        end
        
        subgraph "Business Layer (Services)"
            N[UserService<br/>user operations]
            O[RoleService<br/>role management]
            P[PermissionService<br/>permission management]
            Q[UserSessionService<br/>session tracking]
            R[PasswordResetService<br/>password recovery]
            S[EmailService<br/>notification sending]
        end
        
        subgraph "Data Access Layer (Repositories)"
            T[UserRepository]
            U[RoleRepository]
            V[PermissionRepository]
            W[UserRoleRepository]
            X[RolePermissionRepository]
            Y[UserPermissionRepository]
            Z[SessionRepository]
            AA[PasswordResetTokenRepository]
        end
        
        subgraph "Core Layer"
            AB[Result Classes<br/>Success/Error Results]
            AC[Utilities<br/>Helper methods]
            AD[Validation<br/>Data validation]
            AE[Exception Handling<br/>Global error handling]
        end
        
        subgraph "Entity Layer"
            AF[User Entity]
            AG[Role Entity]
            AH[Permission Entity]
            AI[UserRole Entity]
            AJ[RolePermission Entity]
            AK[UserPermission Entity]
            AL[UserProfile Entity]
            AM[UserSession Entity]
            AN[PasswordResetToken Entity]
        end
        
        subgraph "Configuration Layer"
            AO[Security Config<br/>JWT, CORS, Method Security]
            AP[Database Config<br/>JPA, Hibernate]
            AQ[Data Loader<br/>Initial data setup]
            AR[Scheduled Tasks<br/>Cleanup operations]
        end
    end
    
    subgraph "Database Layer"
        AS[(H2/MySQL Database<br/>All tables in single DB)]
    end
    
    subgraph "External Services"
        AT[Email Server<br/>SMTP for notifications]
        AU[File Storage<br/>Profile images]
    end
    
    %% Client to API connections
    A --> D
    A --> E
    A --> F
    A --> G
    A --> H
    A --> I
    B --> D
    B --> E
    B --> I
    C --> D
    C --> E
    C --> F
    C --> G
    C --> H
    
    %% API to Security
    D --> J
    E --> J
    F --> J
    G --> J
    H --> J
    I --> J
    
    %% Security components
    J --> K
    J --> L
    J --> M
    
    %% API to Business
    D --> N
    D --> Q
    D --> R
    E --> N
    F --> O
    G --> P
    H --> N
    H --> O
    H --> P
    I --> Q
    
    %% Business to Data Access
    N --> T
    N --> W
    N --> Y
    O --> U
    O --> X
    P --> V
    Q --> Z
    R --> AA
    S --> T
    
    %% Business to Core
    N --> AB
    O --> AB
    P --> AB
    Q --> AB
    R --> AB
    
    %% Data Access to Entities
    T --> AF
    U --> AG
    V --> AH
    W --> AI
    X --> AJ
    Y --> AK
    Z --> AM
    AA --> AN
    
    %% Entities to Database
    AF --> AS
    AG --> AS
    AH --> AS
    AI --> AS
    AJ --> AS
    AK --> AS
    AL --> AS
    AM --> AS
    AN --> AS
    
    %% Configuration
    AO -.-> J
    AP -.-> AS
    AQ -.-> AS
    AR -.-> Q
    AR -.-> R
    
    %% External Services
    S --> AT
    N --> AU
    
    %% Core to all layers
    AB -.-> D
    AB -.-> E
    AB -.-> F
    AB -.-> G
    AB -.-> H
    AB -.-> I
    
    classDef clientLayer fill:#e1f5fe
    classDef apiLayer fill:#f3e5f5
    classDef securityLayer fill:#fff3e0
    classDef businessLayer fill:#e8f5e8
    classDef dataLayer fill:#fce4ec
    classDef entityLayer fill:#f1f8e9
    classDef configLayer fill:#fff8e1
    classDef databaseLayer fill:#e3f2fd
    classDef externalLayer fill:#fafafa
    
    class A,B,C clientLayer
    class D,E,F,G,H,I apiLayer
    class J,K,L,M securityLayer
    class N,O,P,Q,R,S businessLayer
    class T,U,V,W,X,Y,Z,AA dataLayer
    class AF,AG,AH,AI,AJ,AK,AL,AM,AN entityLayer
    class AO,AP,AQ,AR configLayer
    class AS databaseLayer
    class AT,AU externalLayer
```

## Layered Architecture

```
┌─────────────────────────────────────┐
│          Web API Layer              │ ← Controllers
├─────────────────────────────────────┤
│         Security Layer              │ ← JWT, Permissions
├─────────────────────────────────────┤
│        Business Layer               │ ← Services
├─────────────────────────────────────┤
│       Data Access Layer             │ ← Repositories
├─────────────────────────────────────┤
│         Entity Layer                │ ← JPA Entities
├─────────────────────────────────────┤
│          Core Layer                 │ ← Utilities, Results
└─────────────────────────────────────┘

```

## ER Diagram

```mermaid
erDiagram
    USER {
        Long id PK
        String username UK
        String email UK
        String firstName
        String lastName
        String phoneNumber
        Boolean isActive
        Boolean emailVerified
        DateTime createdAt
        DateTime updatedAt
    }
    
    USER_PASSWORD {
        Long id PK
        Long userId FK
        String passwordHash
        String salt
        DateTime createdAt
        DateTime expiresAt
        Boolean isActive
    }
    
    USER_SESSION {
        Long id PK
        Long userId FK
        String sessionToken UK
        String ipAddress
        String userAgent
        String deviceType
        String browser
        String operatingSystem
        String location
        DateTime createdAt
        DateTime lastAccessedAt
        DateTime expiresAt
        Boolean isActive
        DateTime logoutAt
        String logoutReason
    }
    
    USER_ROLE {
        Long id PK
        Long userId FK
        Long roleId FK
        DateTime assignedAt
        Boolean isActive
    }
    
    ROLE {
        Long id PK
        String name UK
        String description
        Boolean isActive
        DateTime createdAt
    }
    
    ROLE_PERMISSION {
        Long id PK
        Long roleId FK
        Long permissionId FK
        DateTime assignedAt
        Boolean isActive
    }
    
    USER_PERMISSION {
        Long id PK
        Long userId FK
        Long permissionId FK
        DateTime assignedAt
        Boolean isActive
        String grantedBy
    }
    
    PERMISSION {
        Long id PK
        String name UK
        String description
        String category
        Boolean isActive
        DateTime createdAt
    }
    
    USER_PROFILE {
        Long id PK
        Long userId FK
        String profileImage
        String bio
        String address
        String city
        String country
        Date birthDate
        String gender
        DateTime createdAt
        DateTime updatedAt
    }
    
    PASSWORD_RESET_TOKEN {
        Long id PK
        String token UK
        Long userId FK
        DateTime expiryDate
        Boolean isUsed
        DateTime createdAt
    }
    
    EMAIL_VERIFICATION_TOKEN {
        Long id PK
        String token UK
        Long userId FK
        DateTime expiryDate
        Boolean isUsed
        DateTime createdAt
    }

    USER ||--o{ USER_PASSWORD : "has"
    USER ||--o{ USER_SESSION : "creates"
    USER ||--o{ USER_ROLE : "has"
    USER_ROLE }o--|| ROLE : "refers to"
    ROLE ||--o{ ROLE_PERMISSION : "has"
    ROLE_PERMISSION }o--|| PERMISSION : "refers to"
    USER ||--o{ USER_PERMISSION : "has direct"
    USER_PERMISSION }o--|| PERMISSION : "refers to"
    USER ||--|| USER_PROFILE : "has"
    USER ||--o{ PASSWORD_RESET_TOKEN : "generates"
    USER ||--o{ EMAIL_VERIFICATION_TOKEN : "generates"
```

## Data Flow Diagram

```mermaid
sequenceDiagram
    participant Client
    participant Controller
    participant Security
    participant Service
    participant Repository
    participant Database
    participant ExternalService
    
    Note over Client,ExternalService: User Registration Flow
    
    Client->>Controller: POST /api/auth/register
    Controller->>Security: Validate request
    Security-->>Controller: Request validated
    Controller->>Service: UserService.register()
    Service->>Repository: Check username/email exists
    Repository->>Database: SELECT query
    Database-->>Repository: Result
    Repository-->>Service: User exists check
    
    alt User doesn't exist
        Service->>Service: Encode password
        Service->>Repository: Save new user
        Repository->>Database: INSERT user
        Database-->>Repository: User saved
        Service->>Repository: Assign default role
        Repository->>Database: INSERT user_role
        Database-->>Repository: Role assigned
        Service->>Repository: Create user profile
        Repository->>Database: INSERT user_profile
        Database-->>Repository: Profile created
        Service-->>Controller: Success result
        Controller-->>Client: 201 Created
    else User exists
        Service-->>Controller: Error result
        Controller-->>Client: 400 Bad Request
    end
    
    Note over Client,ExternalService: User Login Flow
    
    Client->>Controller: POST /api/auth/login
    Controller->>Security: Validate credentials
    Service->>Repository: Find user by username
    Repository->>Database: SELECT user
    Database-->>Repository: User data
    Repository-->>Service: User found
    Service->>Service: Verify password
    
    alt Valid credentials
        Service->>Security: Generate JWT token
        Security-->>Service: JWT token
        Service->>Repository: Create user session
        Repository->>Database: INSERT user_session
        Database-->>Repository: Session created
        Service-->>Controller: Auth response with token
        Controller-->>Client: 200 OK + token
    else Invalid credentials
        Service-->>Controller: Error result
        Controller-->>Client: 401 Unauthorized
    end
    
    Note over Client,ExternalService: Protected Resource Access
    
    Client->>Controller: GET /api/users/profile (with JWT)
    Controller->>Security: Validate JWT token
    Security->>Security: Extract user ID from token
    Security->>Service: Check user permissions
    Service->>Repository: Get user permissions
    Repository->>Database: SELECT permissions via roles/direct
    Database-->>Repository: Permission list
    Repository-->>Service: User permissions
    Service-->>Security: Permission check result
    
    alt Has permission
        Security-->>Controller: Access granted
        Controller->>Service: UserService.getUserProfile()
        Service->>Repository: Get user data
        Repository->>Database: SELECT user + profile
        Database-->>Repository: User data
        Repository-->>Service: User profile
        Service-->>Controller: Success result
        Controller-->>Client: 200 OK + user data
    else No permission
        Security-->>Controller: Access denied
        Controller-->>Client: 403 Forbidden
    end
    
    Note over Client,ExternalService: Password Reset Flow
    
    Client->>Controller: POST /api/auth/forgot-password
    Controller->>Service: PasswordResetService.initiate()
    Service->>Repository: Find user by email
    Repository->>Database: SELECT user
    Database-->>Repository: User data
    Repository-->>Service: User found
    Service->>Service: Generate reset token
    Service->>Repository: Save reset token
    Repository->>Database: INSERT password_reset_token
    Database-->>Repository: Token saved
    Service->>ExternalService: Send reset email
    ExternalService-->>Service: Email sent
    Service-->>Controller: Success result
    Controller-->>Client: 200 OK
    
    Note over Client,ExternalService: Role Assignment Flow
    
    Client->>Controller: POST /api/user-management/users/{id}/roles/{roleId}
    Controller->>Security: Check admin permissions
    Security->>Service: Verify ROLE_ASSIGN permission
    Service->>Repository: Get user permissions
    Repository->>Database: Permission check
    Database-->>Repository: Permission result
    Repository-->>Service: Permission verified
    
    alt Has admin permission
        Service->>Repository: Assign role to user
        Repository->>Database: INSERT user_role
        Database-->>Repository: Role assigned
        Service-->>Controller: Success result
        Controller-->>Client: 200 OK
    else No admin permission
        Service-->>Controller: Access denied
        Controller-->>Client: 403 Forbidden
    end
```

# Install

Download Project from Spring Initializr

![](./photos/1_image.png)