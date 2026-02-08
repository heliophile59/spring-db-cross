# sb-mysql-ec2-private-connect
Spring Boot app connects to MySQL database on another EC2 via private IP.

Overview
This project documents my learning journey of building and deploying a Spring Boot CRUD application using AWS infrastructure. The application was developed locally, integrated with databases, and deployed using multiple AWS EC2 instances to simulate real-world production architecture.

Technologies Used
Java
Spring Boot
REST APIs (CRUD operations)
AWS EC2
AWS RDS
AWS ECS (learning focus)
MySQL / PostgreSQL (RDS)
Security Groups (AWS)

Learning & Implementation Steps

1. Spring Boot CRUD Application (Local Setup)
Created a Spring Boot CRUD application.
Connected the application to a local database.
Verified that all CRUD operations (Create, Read, Update, Delete) were working correctly.

2. AWS RDS Integration
Created an RDS instance in AWS.
Updated the Spring Boot application configuration to connect to the RDS database.
Successfully tested database connectivity and CRUD operations using AWS RDS.

3. Deployment on AWS EC2
Packaged the Spring Boot application as a JAR file.
Launched an EC2 instance on AWS.
Deployed and ran the JAR file on the EC2 instance.
Configured AWS Security Groups to allow:
Application access from the EC2 instance
Verified successful communication between the application running on EC2 and the RDS database.

4. Two-EC2 Architecture (Spring Boot + MySQL)
Create a two-EC2 architecture using AWS Free Tier:
  One EC2 for Spring Boot Application
  One EC2 for MySQL Database
The Spring Boot application connects to the MySQL database using private IP communication.

Steps for implementing Point 4 : 
Step 4.1:Launch EC2 Instances
DB EC2 (MySQL)
AMI: Ubuntu 22.04 LTS
Instance Type: t2.micro
Security Group:
SSH (22) → Your IP
MySQL (3306) → Spring Boot EC2 private IP
Spring Boot EC2 (Application)
AMI: Ubuntu 22.04 LTS
Instance Type: t2.micro
Security Group:
SSH (22) → Your IP
Application Port (8081) → Your IP

Why:
Separating the application and database simulates a real-world production architecture.

Step 4.2: Configure MySQL on DB EC2
Install MySQL
sudo apt update
sudo apt install mysql-server -y
sudo systemctl start mysql
sudo systemctl enable mysql
Enable Remote Access
Edit MySQL configuration:
sudo nano /etc/mysql/mysql.conf.d/mysqld.cnf
Update:
bind-address = 0.0.0.0
skip-name-resolve
Restart MySQL:
sudo systemctl restart mysql

Why:
bind-address = 0.0.0.0 → allows remote connections
skip-name-resolve → avoids DNS-related connection issues

Step 4.3: Create Database and User
CREATE DATABASE world;
CREATE USER 'appuser'@'{PRIVATE_IP_APP}' IDENTIFIED BY 'password123';
GRANT ALL PRIVILEGES ON world.* TO 'appuser'@'{PRIVATE_IP_APP}';
FLUSH PRIVILEGES;

Why:

Database created specifically for the application
User restricted to Spring Boot EC2 private IP for security

Step 4.4: Configure Spring Boot EC2
Install Java
sudo apt update
sudo apt install openjdk-17-jdk -y
java -version
Copy Spring Boot JAR
Use scp from local machine or
Pull from GitHub repository

Update application.properties
spring.datasource.url=jdbc:mysql://{PRIVATE_IP_DB}:3306/world
spring.datasource.username=appuser
spring.datasource.password=password123
spring.jpa.hibernate.ddl-auto=create
spring.jpa.show-sql=true

Why:
Spring Boot connects to MySQL using the private IP of DB EC2.

Step 4.5: Test Connectivity
From Spring Boot EC2:
mysql -h {PRIVATE_IP_DB} -u appuser -p
nc -zv {PRIVATE_IP_DB} 3306

✅ Connection succeeded.

Why:
Always verify database connectivity before starting the application.

Step 4.6: Run the Application
java -jar app.jar
Application starts on port 8081
Tables are created automatically in MySQL
CRUD APIs are ready to use

Key Learnings
Private IP should always be used for EC2-to-EC2 communication
Security Groups must be tightly restricted
MySQL skip-name-resolve avoids common connection issues
App and DB separation mirrors real production environments
Correct DB credentials are critical for successful startup
Database connectivity between EC2 and RDS




