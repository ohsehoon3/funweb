
-- student 테이블 생성
CREATE TABLE student (
	id       VARCHAR(30) PRIMARY KEY,
	name     VARCHAR(30),
	kor      INT,
	math     INT,
	eng      INT,
	reg_date TIMESTAMP
);

-- student 테이블 제거
DROP TABLE student;


-- GROUP BY 복습
	-- 성기준 사원수
	SELECT gender, COUNT(emp_no)
	FROM employees
	GROUP BY gender;

	-- 연도기준 채용 사원수
	SELECT LEFT(hire_date, 4), COUNT(*)
	FROM employees
	GROUP BY LEFT(hire_date, 4);

-- JOIN 복습
	-- 사원기준 최대 급여액
	SELECT s.emp_no, 
		   CONCAT (e.first_name, ' ', e.last_name) emp_name, 
		   MAX(s.salary) max_salary
	FROM salaries s
		LEFT OUTER JOIN employees e ON s.emp_no = e.emp_no
	GROUP BY s.emp_no;

	-- 부서(이름)기준 사원 수
	SELECT de.dept_no, 
		   d.dept_name, 
		   COUNT(de.emp_no) count
	FROM dept_emp de
		LEFT OUTER JOIN departments d ON de.dept_no = d.dept_no
	GROUP BY de.dept_no;

	-- 사원기준 최근 급여액
	SELECT s.emp_no, 
		   CONCAT (e.first_name, ' ', e.last_name) emp_name,
		   s.salary
	FROM salaries s 
		LEFT OUTER JOIN employees e ON s.emp_no = e.emp_no
	WHERE s.from_date = (SELECT MAX(from_date)
						FROM salaries
						WHERE emp_no = s.emp_no)
	GROUP BY emp_no;

-- IN 
	-- uuid기준 검색1
	SELECT *
	FROM attachfile
	WHERE uuid = '4ae2b87c-915b-4f9c-a428-7a9ad77e5f2e' 
	OR uuid = 'b08dfc16-2699-4e8b-9b14-d4fa92a2c4f4';
	
	-- uuid기준 검색2
	SELECT *
	FROM attachfile
	WHERE uuid IN ('4ae2b87c-915b-4f9c-a428-7a9ad77e5f2e', 
				   'b08dfc16-2699-4e8b-9b14-d4fa92a2c4f4');

	-- uuid기준 삭제
	DELETE
	FROM attachfile
	WHERE uuid IN ('4ae2b87c-915b-4f9c-a428-7a9ad77e5f2e', 
				   'b08dfc16-2699-4e8b-9b14-d4fa92a2c4f4');


