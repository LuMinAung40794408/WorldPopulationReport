# World Population Report System

A Java-based DevOps project for generating world population reports using SQL queries and object-oriented design.  
This system retrieves and displays population data for **countries, cities, regions, and languages**, organized and ranked by population.  
It demonstrates professional software engineering practices using **Scrum methodology**, **Git branching strategy**, and **continuous integration**.

## Requirements Implementation Status

32 requirements of 32 have been implemented, which is **100%** completed.

> This reflects the current progress of the World Population Report System based on the official project criteria.
---

* **Master Build Status** ![Master Build Status](https://img.shields.io/github/actions/workflow/status/LuMinAung40794408/WorldPopulationReport/main.yml?branch=master)
* **Develop Build Status** ![Develop Build Status](https://img.shields.io/github/actions/workflow/status/LuMinAung40794408/WorldPopulationReport/main.yml?branch=develop)
* **License** ![GitHub license](https://img.shields.io/github/license/LuMinAung40794408/WorldPopulationReport)
* **Release** [![Releases](https://img.shields.io/github/release/LuMinAung40794408/WorldPopulationReport/all.svg?style=flat-square)](https://github.com/LuMinAung40794408/WorldPopulationReport/releases)
* [![codecov](https://codecov.io/gh/LuMinAung40794408/WorldPopulationReport/graph/badge.svg?token=PUA2YDA3R2)](https://codecov.io/gh/LuMinAung40794408/WorldPopulationReport)


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


## Requirements Verification



| ID | Name                                                                                                                                                            | Met | Screenshot |
|----|-----------------------------------------------------------------------------------------------------------------------------------------------------------------|-----|------------|
| 1  | All the countries in the world organised by largest population to smallest.                                                                                     | Yes | ![1.All the countries in the world organised by largest population to smallest..jpg](Screenshots/1.All%20the%20countries%20in%20the%20world%20organised%20by%20largest%20population%20to%20smallest..jpg) |
| 2  | All the countries in a continent organised by largest population to smallest.                                                                                   | Yes | ![2. All the countries in a continent organised by largest population to smallest..jpg](Screenshots/2.%20All%20the%20countries%20in%20a%20continent%20organised%20by%20largest%20population%20to%20smallest..jpg) |
| 3  | All the countries in a region organised by largest population to smallest.                                                                                      | Yes | ![3. All the countries in a region organised by largest population to smallest..jpg](Screenshots/3.%20All%20the%20countries%20in%20a%20region%20organised%20by%20largest%20population%20to%20smallest..jpg) |
| 4  | The top N populated countries in the world where N is provided by the user.                                                                                     | Yes |![4. The top N populated countries in the world where N is provided by the user..jpg](Screenshots/4.%20The%20top%20N%20populated%20countries%20in%20the%20world%20where%20N%20is%20provided%20by%20the%20user..jpg)  |
| 5  | The top N populated countries in a continent where N is provided by the user.                                                                                   | Yes |![5. The top N populated countries in a continent where N is provided by the user..jpg](Screenshots/5.%20The%20top%20N%20populated%20countries%20in%20a%20continent%20where%20N%20is%20provided%20by%20the%20user..jpg)  |
| 6  | The top N populated countries in a region where N is provided by the user.                                                                                      | Yes |![6. The top N populated countries in a region where N is provided by the user..jpg](Screenshots/6.%20The%20top%20N%20populated%20countries%20in%20a%20region%20where%20N%20is%20provided%20by%20the%20user..jpg)  |
| 7  | All the cities in the world organised by largest population to smallest.                                                                                        | Yes |![7. All the cities in the world organised by largest population to smallest..jpg](Screenshots/7.%20All%20the%20cities%20in%20the%20world%20organised%20by%20largest%20population%20to%20smallest..jpg)  |
| 8  | All the cities in a continent organised by largest population to smallest.                                                                                      | Yes |![8. All the cities in a continent organised by largest population to smallest..jpg](Screenshots/8.%20All%20the%20cities%20in%20a%20continent%20organised%20by%20largest%20population%20to%20smallest..jpg)  |
| 9  | All the cities in a region organised by largest population to smallest.                                                                                         | Yes | ![9. All the cities in a region organised by largest population to smallest..jpg](Screenshots/9.%20All%20the%20cities%20in%20a%20region%20organised%20by%20largest%20population%20to%20smallest..jpg) |
| 10 | All the cities in a country organised by largest population to smallest.                                                                                        | Yes |![10. All the cities in a country organised by largest population to smallest..jpg](Screenshots/10.%20All%20the%20cities%20in%20a%20country%20organised%20by%20largest%20population%20to%20smallest..jpg)  |
| 11 | All the cities in a district organised by largest population to smallest.                                                                                       | Yes |![11. All the cities in a district organised by largest population to smallest..jpg](Screenshots/11.%20All%20the%20cities%20in%20a%20district%20organised%20by%20largest%20population%20to%20smallest..jpg)  |
| 12 | The top N populated cities in the world where N is provided by the user.                                                                                        | Yes |![12. The top N populated cities in the world where N is provided by the user..jpg](Screenshots/12.%20The%20top%20N%20populated%20cities%20in%20the%20world%20where%20N%20is%20provided%20by%20the%20user..jpg)  |
| 13 | The top N populated cities in a continent where N is provided by the user.                                                                                      | Yes | ![13. The top N populated cities in a continent where N is provided by the user..jpg](Screenshots/13.%20The%20top%20N%20populated%20cities%20in%20a%20continent%20where%20N%20is%20provided%20by%20the%20user..jpg) |
| 14 | The top N populated cities in a region where N is provided by the user.                                                                                         | Yes |![14. The top N populated cities in a region where N is provided by the user..jpg](Screenshots/14.%20The%20top%20N%20populated%20cities%20in%20a%20region%20where%20N%20is%20provided%20by%20the%20user..jpg)  |
| 15 | The top N populated cities in a country where N is provided by the user.                                                                                        | Yes |![15. The top N populated cities in a country where N is provided by the user..jpg](Screenshots/15.%20The%20top%20N%20populated%20cities%20in%20a%20country%20where%20N%20is%20provided%20by%20the%20user..jpg)  |
| 16 | The top N populated cities in a district where N is provided by the user.                                                                                       | Yes |![16. The top N populated cities in a district where N is provided by the user..jpg](Screenshots/16.%20The%20top%20N%20populated%20cities%20in%20a%20district%20where%20N%20is%20provided%20by%20the%20user..jpg)  |
| 17 | All the capital cities in the world organised by largest population to smallest.                                                                                | Yes |![17. All the capital cities in the world organised by largest population to smallest..jpg](Screenshots/17.%20All%20the%20capital%20cities%20in%20the%20world%20organised%20by%20largest%20population%20to%20smallest..jpg)  |
| 18 | All the capital cities in a continent organised by largest population to smallest.                                                                              | Yes |![18. All the capital cities in a continent organised by largest population to smallest..jpg](Screenshots/18.%20All%20the%20capital%20cities%20in%20a%20continent%20organised%20by%20largest%20population%20to%20smallest..jpg)  |
| 19 | All the capital cities in a region organised by largest to smallest.                                                                                            | Yes |![19. All the capital cities in a region organised by largest to smallest..jpg](Screenshots/19.%20All%20the%20capital%20cities%20in%20a%20region%20organised%20by%20largest%20to%20smallest..jpg)  |
| 20 | The top N populated capital cities in the world where N is provided by the user.                                                                                | Yes |![20. The top N populated capital cities in the world where N is provided by the user..jpg](Screenshots/20.%20The%20top%20N%20populated%20capital%20cities%20in%20the%20world%20where%20N%20is%20provided%20by%20the%20user..jpg)  |
| 21 | The top N populated capital cities in a continent where N is provided by the user.                                                                              | Yes |![21. The top N populated capital cities in a continent where N is provided by the user..jpg](Screenshots/21.%20The%20top%20N%20populated%20capital%20cities%20in%20a%20continent%20where%20N%20is%20provided%20by%20the%20user..jpg)  |
| 22 | The top N populated capital cities in a region where N is provided by the user.                                                                                 | Yes |![22. The top N populated capital cities in a region where N is provided by the user..jpg](Screenshots/22.%20The%20top%20N%20populated%20capital%20cities%20in%20a%20region%20where%20N%20is%20provided%20by%20the%20user..jpg)  |
| 23 | The population of the world.                                                                                                                                    | Yes |![23. The population of the world..jpg](Screenshots/23.%20The%20population%20of%20the%20world..jpg)  |
| 24 | The population of a continent.                                                                                                                                  | Yes |![24. The population of a continent..jpg](Screenshots/24.%20The%20population%20of%20a%20continent..jpg)  |
| 25 | The population of a region.                                                                                                                                     | Yes |![25. The population of a region..jpg](Screenshots/25.%20The%20population%20of%20a%20region..jpg)  |
| 26 | The population of a country.                                                                                                                                    | Yes |![26. The population of a country..jpg](Screenshots/26.%20The%20population%20of%20a%20country..jpg)  |
| 27 | The population of a district.                                                                                                                                   | Yes |![27. The population of a district..jpg](Screenshots/27.%20The%20population%20of%20a%20district..jpg)  |
| 28 | The population of a city.                                                                                                                                       | Yes |![28. The population of a city..jpg](Screenshots/28.%20The%20population%20of%20a%20city..jpg)  |
| 29 | The population of people, people living in cities, and people not living in cities in each continent.                                                           | Yes |![29. The population of people, people living in cities, and people not living in cities in each continent..jpg](Screenshots/29.%20The%20population%20of%20people%2C%20people%20living%20in%20cities%2C%20and%20people%20not%20living%20in%20cities%20in%20each%20continent..jpg) |
| 30 | The population of people, people living in cities, and people not living in cities in each region.                                                              | Yes |![30. The population of people, people living in cities, and people not living in cities in each region..jpg](Screenshots/30.%20The%20population%20of%20people%2C%20people%20living%20in%20cities%2C%20and%20people%20not%20living%20in%20cities%20in%20each%20region..jpg)  |
| 31 | The population of people, people living in cities, and people not living in cities in each country.                                                             | Yes |![31. The population of people, people living in cities, and people not living in cities in each country..jpg](Screenshots/31.%20The%20population%20of%20people%2C%20people%20living%20in%20cities%2C%20and%20people%20not%20living%20in%20cities%20in%20each%20country..jpg)  |
| 32 | Number of speakers of selected languages (Chinese, English, Hindi, Spanish, Arabic) ordered from largest to smallest, including percentage of world population. | Yes | ![32. Number of speakers of selected languages (Chinese, English, Hindi, Spanish, Arabic) ordered from largest to smallest, including percentage of world population..jpg](Screenshots/32.%20Number%20of%20speakers%20of%20selected%20languages%20%28Chinese%2C%20English%2C%20Hindi%2C%20Spanish%2C%20Arabic%29%20ordered%20from%20largest%20to%20smallest%2C%20including%20percentage%20of%20world%20population..jpg) |
## Code of Conduct

Please read our [Code of Conduct](CODE_OF_CONDUCT.md) to understand the standards and expectations for team collaboration and communication.
