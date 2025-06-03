# Layered Arhcitecture

# Content
- [Layered Arhcitecture](#layered-arhcitecture)
- [Content](#content)
- [Layered](#layered)

# Layered

* Database:  Veritabanının tanımlanması
* Entities: Veritabanı nesnelerinin ve özelliklerinin tanımlandığı katman
* Data Access Layer: Veritabanı nesnelerine erişim için gerekli tanımlamaların yapıldığı katman
* Business: İş kurallarının tanımlandığı katman
* Core: Ortak kullanımların tanımlandığı katman
* Web Api: End-Point tanımlamlarının yapıldığı katman

```mermaid
graph TD
    subgraph "Presentation Layer"
        A[Web UI]
        B[Mobile App]
        C[API Controllers]
        D[REST Endpoints]
    end
    
    subgraph "Business Layer"
        E[Business Services]
        F[Domain Models]
        G[Business Rules]
        H[Validation Logic]
    end
    
    subgraph "Data Access Layer"
        I[Repository Pattern]
        J[Data Mappers]
        K[ORM/Entity Framework]
        L[Database Connections]
    end
    
    subgraph "Database Layer"
        M[(Primary Database)]
        N[(Cache Database)]
        O[(External APIs)]
    end
    
    %% Connections between layers
    A --> E
    B --> E
    C --> E
    D --> E
    
    E --> I
    F --> I
    G --> I
    H --> I
    
    I --> M
    J --> M
    K --> M
    L --> N
    
    K --> O
    
    %% Styling
    classDef presentationLayer fill:#e1f5fe,stroke:#01579b,stroke-width:2px
    classDef businessLayer fill:#f3e5f5,stroke:#4a148c,stroke-width:2px
    classDef dataLayer fill:#e8f5e8,stroke:#1b5e20,stroke-width:2px
    classDef databaseLayer fill:#fff3e0,stroke:#e65100,stroke-width:2px
    
    class A,B,C,D presentationLayer
    class E,F,G,H businessLayer
    class I,J,K,L dataLayer
    class M,N,O databaseLayer
```