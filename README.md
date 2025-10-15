# World Population Report System

A Java-based DevOps project for generating world population reports using SQL queries and object-oriented design.  
This system retrieves and displays population data for **countries, cities, regions, and languages**, organized and ranked by population.  
It demonstrates professional software engineering practices using **Scrum methodology**, **Git branching strategy**, and **continuous integration**.

## Requirements Implementation Status

21 requirements of 32 have been implemented, which is **65.6%**.

> This reflects the current progress of the World Population Report System based on the official project criteria.
---

* **Master Build Status** ![Master Build Status](https://img.shields.io/github/actions/workflow/status/LuMinAung40794408/WorldPopulationReport/main.yml?branch=master)
* **Develop Build Status** ![Develop Build Status](https://img.shields.io/github/actions/workflow/status/LuMinAung40794408/WorldPopulationReport/main.yml?branch=develop)
* **License** ![GitHub license](https://img.shields.io/github/license/LuMinAung40794408/WorldPopulationReport)
* **Release** [![Releases](https://img.shields.io/github/release/LuMinAung40794408/WorldPopulationReport/all.svg?style=flat-square)](https://github.com/LuMinAung40794408/WorldPopulationReport/releases)


##  Team Members

| Student ID | Name                                                        | Role          | Feature Responsibility                     |
|-------------|-------------------------------------------------------------|---------------|--------------------------------------------|
| **40794408** | [Lu Min Aung](https://github.com/LuMinAung40794408)         | Scrum Master / Developer | Queries / Git Management                   |
| **40794418** | [Yu Ya Ko Ko](https://github.com/40794418yuyakoko)          | Developer     | Language Report / Use Case                 |
| **40794444** | [Phone Myat Kyaw](https://github.com/40794444PhoneMyatKyaw) | Developer     | City Report / Queries                      |
| **40779661** | [Ann Min Nyo](https://github.com/40779661AnnMinNyo)         | Product Owner / Developer | Population Report / Backlog and Sprint     |
| **40794512** | [Zayar Than Htike](https://github.com/ZayarThanHtike-stu)   | Developer / Tester | Capital Report / Testing and Documentation |
| **40794374** | [Thu Ta Minn Lu](https://github.com/ThuTaMinnLu40794374)    | Developer     | Country Report / Plant UML                 |


### Branching Strategy
- **main** → Production-ready, stable code
- **develop** → Integration branch for all new features
- **release** → Final testing before merging into `main`
- **feature/** → Individual features developed by team members
- **hotfix** → Urgent bug fixes merged directly into `main` and `develop`

### Packages
- **com.group12.report** -> Main Application Classes
- **com.group12.report.data_access** -> Accessing Data
- **com.group12.report.models** -> Data models (Country, City, Capital, Language, Population)
- **com.group12.report.reports** -> Displaying Output

### Features
- Generate population reports for **Countries, Cities, and Regions**
- Filter data by **Continent, Region and Language**
- Display ** Populated Countries, Capital Cities, Cities, or Regions**
- Modular Java design for scalability and maintainability
- **SQL integration** for accurate real-time data retrieval
- Supports **multiple report views** (World, Continent, Region, Capital Cities)
- **Unit testing** and verification scripts to ensure reliable outputs
- Compatible with **Docker** for environment consistency
---

## Tools
- Intellij IDEA (IDE)
- Maven for build and dependency management
- GitHub for version control
- Docker for containerized database and application


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

2. **Build The Project**
      mvn compile and package 

      mvn exec:java - mainClass="com.group12.report.App"

3. a.connect("localhost:3306", 33060, "world", "root", "password");

## Usage

1. Launch the application and select the desired report type
2. Reports can be filtered by desired types.
3. Outputs are displayed in the console with formatted tables

# Contribution Guidelines

Thank you for your interest in contributing to the **World Population Report System**!
To maintain code quality, consistency, and smooth collaboration, please follow these step-by-step guidelines.

---

## Step 1: Set Up Your Local Environment

1. Ensure you have the following installed:

    * **Java JDK 17+**
    * **MySQL Server**
    * **Apache Maven**
    * **IntelliJ IDEA** (or any preferred Java IDE)
2. Clone the repository:

```bash
git clone https://github.com/LuMinAung40794408/WorldPopulationReport.git
cd WorldPopulationReport
```

3. Build the project to verify setup:

```bash
mvn compile package
```

---

## Step 2: Synchronize with the Main Repository

1. Switch to the `develop` branch:

```bash
git checkout develop
```

2. Pull the latest changes:

```bash
git pull origin develop
```

---

## Step 3: Create a Feature Branch

1. Create a new branch for your task or report:

```bash
git checkout -b feature/<your-feature-name>
```

*Example:* `feature/capital-report`
2. Work **only** in this branch.
3. Commit frequently with clear messages:

```bash
git add .
git commit -m "Add Capital Population Report feature"
```

---

## Step 4: Push Your Branch to GitHub

```bash
git push origin feature/<your-feature-name>
```

---

## Step 5: Submit a Pull Request (PR)

1. Go to GitHub and create a **PR from your feature branch to `develop`**.
2. Include a **clear title and description**:

    * What feature or fix you implemented
    * Any related issue or task
    * Steps to test the changes
3. Request **at least one team member** for review.

---

## Step 6: Review and Merge

1. Address any **feedback** or requested changes.
2. Once approved, **merge the PR into `develop`**.
3. Delete your feature branch to keep the repository clean:

```bash
git branch -d feature/<your-feature-name>
git push origin --delete feature/<your-feature-name>
```

---

## Step 7: Update Your Local Branch

After merging, sync your local `develop` branch:

```bash
git checkout develop
git pull origin develop
```

---

## Step 8: Follow the Code of Conduct

* Maintain professional and respectful communication.
* Ask questions or clarify requirements early.
* Document assumptions, bugs, or design decisions in the code or project wiki.

---

 Following these steps ensures **smooth collaboration, high-quality code, and successful project delivery**.


## Code of Conduct

Please read our [Code of Conduct](CODE_OF_CONDUCT.md) to understand the standards and expectations for team collaboration and communication.
