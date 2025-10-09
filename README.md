# World Population Report System

A Java-based DevOps project for generating world population reports using SQL queries and object-oriented design.  
This system retrieves and displays population data for **countries, cities, regions, and languages**, organized and ranked by population.  
It demonstrates professional software engineering practices using **Scrum methodology**, **Git branching strategy**, and **continuous integration**.

---

* **Master Build Status** ![Master Build Status](https://img.shields.io/github/actions/workflow/status/LuMinAung40794408/WorldPopulationReport/main.yml?branch=master)
* **Develop Build Status** ![Develop Build Status](https://img.shields.io/github/actions/workflow/status/LuMinAung40794408/WorldPopulationReport/main.yml?branch=develop)
* **License** ![GitHub license](https://img.shields.io/github/license/LuMinAung40794408/WorldPopulationReport)
* **Release** ![GitHub release (latest by date)](https://img.shields.io/github/v/release/LuMinAung40794408/WorldPopulationReport)


##  Team Members

| Student ID | Name | Role          | Feature Responsibility              |
|-------------|------|---------------|-------------------------------------|
| **40794408** | Lu Min Aung | Scrum Master / Developer | Population Report / Git Management  |
| **40794418** | Yu Ya Ko Ko | Developer     | Language Report / Use Case          |
| **40794444** | Phone Myat Kyaw | Developer     | City Report / Queries               |
| **40779661** | Ann Min Nyo | Product Owner / Developer | Population Report / Backlog and Sprint |
| **40794512** | Zayar Than Htike | Developer / Tester | Capital Report / Testing and Documentation |
| **40794374** | Thu Ta Minn Lu | Developer     | Country Report / Plant UML          |


### Branching Strategy
- **main** → Production-ready, stable code
- **develop** → Integration branch for all new features
- **release** → Final testing before merging into `main`
- **feature/** → Individual features developed by team members
- **hotfix** → Urgent bug fixes merged directly into `main` and `develop`

---

##  Setup Instructions

###  Prerequisites
- **Java JDK 17+**
- **MySQL Server**
- **Apache Maven**
- **IntelliJ IDEA** (recommended IDE)

###  How to Run the Application

1. **Clone the repository**
   ```bash
   git clone https://github.com/LuMinAung40794408/WorldPopulationReport.git
   cd WorldPopulationReport

2. mvn compile and package 

   mvn exec:java - mainClass="com.group12.report.App"

3. a.connect("localhost:3306", 33060, "world", "root", "password");


## 🤝 Code of Conduct

Please read our [Code of Conduct](CODE_OF_CONDUCT.md) to understand the standards and expectations for team collaboration and communication.
