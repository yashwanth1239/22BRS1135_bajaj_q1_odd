Bajaj Finserv Health - Spring Boot Webhook Challenge
Overview
Spring Boot application that automatically processes webhook requests, solves SQL problems, and submits solutions using JWT authentication.
Solution Details
## Personal Details Used
**Note:** The application uses my actual personal details instead of placeholder data("john doe", REG1247,"john@example .com") to demonstrate genuine API interaction:

- **Name:** Vasireddy Yashwanth Kumar
- **Registration Number:** 22BRS1135 
- **Email:** yashwanthkumarvasireddy67@gmail.com

**This proves authentic webhook API calls were made with real student credentials rather than dummy data.**
Registration Number: 22BRS1135 (Odd → Question 1)
Problem: Find highest salary not paid on 1st day of month with employee details

SQL Query
sqlSELECT 
    p.AMOUNT as SALARY,
    CONCAT(e.FIRST_NAME, ' ', e.LAST_NAME) as NAME,
    TIMESTAMPDIFF(YEAR, e.DOB, CURDATE()) as AGE,
    d.DEPARTMENT_NAME
FROM PAYMENTS p
JOIN EMPLOYEE e ON p.EMP_ID = e.EMP_ID
JOIN DEPARTMENT d ON e.DEPARTMENT = d.DEPARTMENT_ID
WHERE DAY(p.PAYMENT_TIME) != 1
ORDER BY p.AMOUNT DESC
LIMIT 1
How to Run

Clone repo: git clone https://github.com/yashwanth1239/22BRS1135_bajaj_q1_odd.git
Run: java -jar bajaj_odd_q1-0.0.1-SNAPSHOT.jar

Expected Output
Webhook generated successfully!
SQL Query generated for Question 1 (odd regNo)
Solution submitted successfully!
Response: {"success":true,"message":"Webhook processed successfully"}
Technologies

Spring Boot 3.5.5
Java 17
RestTemplate
JWT Authentication

Downloads
JAR File
Status
✅ Completed Successfully - API confirmed solution acceptance.
<img width="1162" height="453" alt="image" src="https://github.com/user-attachments/assets/3f2f9cba-5410-49c7-ba61-3bc726d3ba5a" />
