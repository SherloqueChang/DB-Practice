# 思路

前端采用 VUE 实现，大框架没有问题，主要是基于业务逻辑进行相应的实现；

前后端通信按照原来的axios，主要是参数的设置，针对性修改；

后端主要的问题在于，原项目实现是在init()方法调用的时候一次性执行了数据库登录以及数据的设置，我们期望的实现是通过login输入的用户名以及密码来作为判断依据，选择相应的具有特定权限的用户进行数据库的登录操作（不太可行，通过前端限制）；其他主要的工作应该就是响应函数的处理（SQL语句的编写），这里需要注意业务和数据隔离的原则。

# 配置

新建一个`WeiMingHospital`名称的数据库，在打开的`.sql`文件中把下面`设计`部分的代码粘贴进去挨个执行，完成本地数据库的创建；

在后端项目的`application.propeties`中把`password`改为自己的密码，其他不用修改；

vs code 应该会自动识别依赖，先执行`HimsAppication.java`中的`main`函数，如果不能正常运行的话就需要配置`maven`环境；

后端运行起来后Web端的功能就可以正常响应了

# 设计

```sql
CREATE TABLE user(  
    id VARCHAR(50) NOT NULL PRIMARY KEY COMMENT 'Primary Key, login id',
    pwd VARCHAR(50) NOT NULL COMMENT 'password',
    name VARCHAR(50) COMMENT 'name of the patient',
    birthdate DATE COMMENT 'birthdate of the patient',
    idcard VARCHAR(50) COMMENT 'ID card number of the patient',
    gender ENUM('male','female') COMMENT 'gender of the patient',
    phone VARCHAR(50) COMMENT 'phone number of the patient',
    email VARCHAR(50) COMMENT 'email of the patient, can be NULL',
    u_type ENUM('patient','doctor','leader','admin')
) DEFAULT CHARSET UTF8 COMMENT '';

CREATE TABLE department( 
    name VARCHAR(50) NOT NULL PRIMARY KEY COMMENT 'Name of department',
    leader_id VARCHAR(50) COMMENT 'Leader id of the department',
    dept_description VARCHAR(255) COMMENT 'Description of the department'
) DEFAULT CHARSET UTF8 COMMENT '';

CREATE TABLE doctor(
    id VARCHAR(50) NOT NULL COMMENT 'Primary Key, login id',
    department VARCHAR(50) COMMENT 'department name of the doctor, can be NULL',
    graduate_school VARCHAR(255) NOT NULL COMMENT 'graduate_school',
    job_title VARCHAR(50) NOT NULL COMMENT 'job title',
    specialties VARCHAR(255) COMMENT 'specialties'
    #FOREIGN KEY (department) REFERENCES department(name) ON DELETE SET NULL
)DEFAULT CHARSET UTF8 COMMENT '';

#ALTER TABLE department ADD CONSTRAINT CS
#    FOREIGN KEY (leader_id) REFERENCES doctor(id) ON DELETE SET NULL;

#ALTER TABLE department DROP CONSTRAINT CS;
#flush privileges ;



CREATE TABLE medicine(
    name VARCHAR(50) NOT NULL PRIMARY KEY COMMENT 'medicine name'
) DEFAULT CHARSET UTF8 COMMENT '';

CREATE TABLE prescription(
    doctor_id VARCHAR(50) NOT NULL COMMENT 'id of the doctor',
    patient_id VARCHAR(50) NOT NULL COMMENT 'id of the patient',
    pres_date DATE NOT NULL COMMENT 'date of the prescription',
    medicine_name VARCHAR(50) NOT NULL COMMENT 'medicine id',
    medicine_num int NOT NULL COMMENT 'number of the madicine'
    #FOREIGN KEY (doctor_id) REFERENCES doctor(id) ON DELETE NO ACTION,
    #FOREIGN KEY (patient_id) REFERENCES patient(id) ON DELETE NO ACTION,
    #FOREIGN KEY (medicine_name) REFERENCES medicine(name) ON DELETE NO ACTION
) DEFAULT CHARSET UTF8 COMMENT '';

CREATE TABLE Coronavirus_Survey_Form(
    patient_id VARCHAR(50) NOT NULL COMMENT 'Who fills the table',
    fill_time DATETIME NOT NULL COMMENT 'time of the form finished',
    name VARCHAR(50) NOT NULL COMMENT 'name of the patient',
    gender ENUM('male','female') NOT NULL COMMENT 'gender of the patient',
    idcard VARCHAR(50) NOT NULL COMMENT 'ID card number of the patient',
    phone VARCHAR(50) NOT NULL COMMENT 'phone number of the patient',
    address VARCHAR(255) NOT NULL COMMENT 'address of the patient',
    whether_14days_fever ENUM('yes', 'no') NOT NULL COMMENT 'whether the patient has fever in the past 14 days',
    fever_info VARCHAR(255) COMMENT 'information of the patient about fever, can be NULL',
    whether_14days_area ENUM('yes', 'no') NOT NULL COMMENT 'Whether there is a history of residence in high-risk areas within 14 days',
    area_info VARCHAR(255) COMMENT 'information of the patient about high-risk areas, can be NULL',
    whether_14days_contact ENUM('yes', 'no') NOT NULL COMMENT 'Whether there is a history of close contact with patients with coronavirus in 14 days',
    contact_info VARCHAR(255) COMMENT 'information of the patient about contact, can be NULL',
    whether_14days_contact_area ENUM('yes', 'no') NOT NULL COMMENT 'Whether there is close contact with people from medium-to-high-risk areas or overseas within 14 days',
    contact_area_info VARCHAR(255) COMMENT 'information of the patient about contact with people from medium-to-high-risk areas or overseas, can be NULL'     
    #FOREIGN KEY (patient_id) REFERENCES patient(id) ON DELETE NO ACTION
) DEFAULT CHARSET UTF8 COMMENT '';

CREATE TABLE num_appointed(
    doctor_id VARCHAR(50) NOT NULL COMMENT 'id of the doctor',
    appointment_date date NOT NULL COMMENT 'date of the record',
    num int NOT NULL COMMENT 'number of patient already appointed'
    #FOREIGN KEY (doctor_id) REFERENCES doctor(id) ON DELETE CASCADE
) DEFAULT CHARSET UTF8 COMMENT '';

CREATE TABLE appointed_info(
    doctor_id VARCHAR(50) NOT NULL COMMENT 'id of the doctor',
    appointment_date date NOT NULL COMMENT 'date of the record',
    department VARCHAR(50) NOT NULL COMMENT 'department name',
    patient_id VARCHAR(50) NOT NULL COMMENT 'id of the patient',
    appointment_status ENUM('Todo','Done') NOT NULL COMMENT 'status of the appointment'
    #FOREIGN KEY (doctor_id) REFERENCES doctor(id) ON DELETE CASCADE,
    #FOREIGN KEY (patient_id) REFERENCES patient(id) ON DELETE CASCADE
) DEFAULT CHARSET UTF8 COMMENT '';

CREATE TABLE patient_history (
    patient_id VARCHAR(50) NOT NULL COMMENT 'id of the patient',
    doctor_id VARCHAR(50) NOT NULL COMMENT 'id of the doctor',
    treat_date DATE NOT NULL COMMENT 'date of the treatment',
    treat_issue VARCHAR(200) NOT NULL COMMENT 'comment of the doctor',
    allergens VARCHAR(200) NOT NULL COMMENT 'allergens'
    #FOREIGN KEY (doctor_id) REFERENCES doctor(id) ON DELETE CASCADE,
    #FOREIGN KEY (patient_id) REFERENCES patient(id) ON DELETE CASCADE
) DEFAULT CHARSET UTF8 COMMENT '';

INSERT INTO user(id, pwd, name, birthdate, idcard, gender, phone, email, u_type) VALUES
    ('patient_1', '123456', 'Alice', '1999-10-21', '110101199003075672', 'male', '13673183017', 'keajo@sina.com', 'patient');


INSERT INTO medicine(name) VALUES
('盘尼西林'),('胰岛素'),('感冒软胶囊'),('阿莫西林'),('布洛芬'),('头孢拉定');

INSERT INTO user(id, pwd, name, birthdate, idcard, gender, phone, email, u_type) VALUES
('patient_0','123456','许洁','1970-03-05','260257696184556740','male','15250420736','iELWWtk1bp@163.com','patient'),
('patient_1','123456','居梅','1971-09-01','901470558715503816','male','18518198333','O7XX8gypG@qq.com','patient'),
('patient_2','123456','胡秀梅','1972-08-20','366917378552141665','male','17563818580','nKwclSz2b@126.com','patient'),
('patient_3','123456','王鹏','1973-10-22','992054715332884198','male','18839832244','m5FDGqM@189.com','patient'),
('patient_4','123456','张东','1974-11-23','847968411146988453','male','13387502172','C3eihzjOp2@qq.com','patient'),
('patient_5','123456','刘娜','1975-04-21','917793855498840806','male','13579988542','tOh5n8R8UJ@qq.com','patient'),
('patient_6','123456','明晶','1976-09-17','695540694701683237','male','14597788113','o3uMI4B@qq.com','patient'),
('patient_7','123456','赵亮','1977-02-01','732669868594512144','male','14526817801','yTgT@163.com','patient'),
('patient_8','123456','何淑兰','1978-02-23','134602792143300299','male','18448822335','qbHIbtjqqw@163.com','patient'),
('patient_9','123456','徐秀芳','1979-05-25','903604877903673342','male','17734712774','uYNCzX@163.com','patient'),
('patient_10','123456','朱晶','1980-03-11','991400750666841155','female','17581877118','evLduqKW7@qq.com','patient'),
('patient_11','123456','丁莉','1981-12-18','974193335288657463','female','14594092942','FLiOI98@163.com','patient'),
('patient_12','123456','康柳','1982-03-14','330530442884122354','female','18958794851','exZ2@189.com','patient'),
('patient_13','123456','钟秀荣','1983-06-28','324684551069741282','female','13168660074','zZDGq3AHx@189.com','patient'),
('patient_14','123456','孔宇','1984-11-28','209404071572082370','female','15185032141','KNCMjXnH4v@126.com','patient'),
('patient_15','123456','唐鑫','1985-03-18','941491167429870060','female','18973926099','ZTDDL0G22d@126.com','patient'),
('patient_16','123456','王琴','1986-04-21','636097330419334982','female','15066577644','vVWFG2Iq5@qq.com','patient'),
('patient_17','123456','邹云','1987-03-07','434117793600523104','female','15387376897','AkgINH@qq.com','patient'),
('patient_18','123456','叶莉','1988-03-07','336354224170920555','female','15512990614','1zBS@189.com','patient'),
('patient_19','123456','苗淑英','1989-12-24','359382517084969076','female','14756410348','GDhDoD6@189.com','patient'),
('patient_20','123456','梁利','1990-09-01','296840048396371758','female','17837379057','Hg4LYhGtw@qq.com','patient');

INSERT INTO user(id, pwd, name, birthdate, idcard, gender, phone, email, u_type) VALUES
('doctor_0','123456','胡英','1970-12-12','628467450802777538','male','18697413175','KD8z@qq.com','doctor'),
('doctor_1','123456','陈平','1971-11-08','607000945111179023','male','17381542623','c2ymsup@qq.com','doctor'),
('doctor_2','123456','黄玉英','1972-07-06','765491670370761754','male','18859655126','W2JekWBN7t@qq.com','doctor'),
('doctor_3','123456','尤淑华','1973-02-16','944340060065317220','male','14912652788','ISGep1@163.com','doctor'),
('doctor_4','123456','唐波','1974-06-02','940150755170446573','male','15379854738','IYx1@qq.com','doctor'),
('doctor_5','123456','邓伟','1975-04-25','880358642081559180','male','15655263219','G8kHqpMEcw@163.com','doctor'),
('doctor_6','123456','任梅','1976-09-25','185221618301954229','male','14987844999','P2HiU@qq.com','doctor'),
('doctor_7','123456','赵桂芳','1977-06-26','689302954786220191','male','13770124683','jsAATiJOP@163.com','doctor'),
('doctor_8','123456','王佳','1978-05-10','538116742538312483','female','15537853707','x6vwe@qq.com','doctor'),
('doctor_9','123456','芦东','1979-01-27','933420296412375319','female','13660375162','qVZVoO@qq.com','doctor'),
('doctor_10','123456','桑娟','1980-09-24','998557510062935564','female','17026920583','Cr3W7@163.com','doctor'),
('doctor_11','123456','郑波','1981-06-11','798408810571537324','female','13715446342','FNJDG15@189.com','doctor'),
('doctor_12','123456','王莹','1982-11-17','999344313710452606','female','14792258746','nTWwLWi@qq.com','doctor'),
('doctor_13','123456','程岩','1983-01-04','970933886805506682','female','17088531599','Cb1D1PLs6@189.com','doctor'),
('doctor_14','123456','陈玉珍','1984-04-01','409149555922238501','female','18587676456','kj5GLR@189.com','doctor'),
('doctor_15','123456','李娟','1985-04-04','932660963646907176','female','15562690370','sx8v@126.com','doctor');

INSERT INTO doctor (id, department, graduate_school, job_title, specialties) VALUES
('doctor_0','内科','北京大学','医师','xxx'),
('doctor_1','内科','清华大学','药师','xxx'),
('doctor_2','内科','南开大学','技师','xxx'),
('doctor_3','儿科','中国人民大学','医师','xxx'),
('doctor_4','儿科','复旦大学','药师','xxx'),
('doctor_5','儿科','上海交通大学','技师','xxx'),
('doctor_6','妇产科','同济大学','医师','xxx'),
('doctor_7','妇产科','天津大学','药师','xxx'),
('doctor_8','妇产科','南京大学','技师','xxx'),
('doctor_9','外科','四川大学','医师','xxx'),
('doctor_10','外科','西安交通大学','药师','xxx'),
('doctor_11','外科','华南理工大学','技师','xxx'),
('doctor_12','发热门诊','浙江大学','医师','xxx'),
('doctor_13','发热门诊','西北工业大学','药师','xxx'),
('doctor_14','发热门诊','吉林大学','技师','xxx'),
('doctor_15', '发热门诊', '南京大学', '药师', 'xxx');

INSERT INTO department(name, leader_id, dept_description) VALUES
('内科','doctor_0','xxx'),
('儿科','doctor_3','xxx'),
('妇产科','doctor_6','xxx'),
('外科','doctor_9','xxx'),
('发热门诊','doctor_12','xxx');

INSERT INTO appointed_info(doctor_id, appointment_date, department, patient_id,  appointment_status) VALUES
('doctor_9','2020-04-08','内科','patient_0','Done'),
('doctor_4','2020-10-14','儿科','patient_0','Done'),
('doctor_9','2020-03-08','妇产科','patient_0','Done'),
('doctor_1','2020-01-17','内科','patient_1','Done'),
('doctor_3','2020-01-01','儿科','patient_1','Done'),
('doctor_3','2020-04-07','妇产科','patient_1','Done'),
('doctor_0','2020-03-18','内科','patient_2','Done'),
('doctor_3','2020-08-28','儿科','patient_2','Done'),
('doctor_14','2020-10-11','妇产科','patient_2','Done'),
('doctor_4','2020-01-21','内科','patient_3','Done'),
('doctor_10','2020-02-13','儿科','patient_3','Done'),
('doctor_6','2020-12-04','妇产科','patient_3','Done'),
('doctor_10','2020-06-05','内科','patient_4','Done'),
('doctor_6','2020-12-21','儿科','patient_4','Done'),
('doctor_12','2020-07-23','妇产科','patient_4','Done'),
('doctor_13','2020-12-22','内科','patient_5','Done'),
('doctor_7','2020-07-21','儿科','patient_5','Done'),
('doctor_7','2020-01-01','妇产科','patient_5','Done'),
('doctor_3','2020-10-19','内科','patient_6','Done'),
('doctor_1','2020-04-13','儿科','patient_6','Done'),
('doctor_5','2020-02-08','妇产科','patient_6','Done'),
('doctor_12','2020-12-15','内科','patient_7','Done'),
('doctor_13','2020-01-27','儿科','patient_7','Done'),
('doctor_15','2020-09-20','妇产科','patient_7','Done'),
('doctor_1','2020-11-07','内科','patient_8','Done'),
('doctor_10','2020-05-08','儿科','patient_8','Done'),
('doctor_12','2020-06-06','妇产科','patient_8','Done'),
('doctor_9','2020-12-23','内科','patient_9','Done'),
('doctor_6','2020-08-24','儿科','patient_9','Done'),
('doctor_5','2020-10-23','妇产科','patient_9','Done'),
('doctor_5','2020-03-17','儿科','patient_10','Done'),
('doctor_6','2020-11-08','妇产科','patient_10','Done'),
('doctor_5','2020-11-06','外科','patient_10','Done'),
('doctor_11','2020-01-14','儿科','patient_11','Done'),
('doctor_9','2020-04-26','妇产科','patient_11','Done'),
('doctor_14','2020-09-04','外科','patient_11','Done'),
('doctor_10','2020-04-04','儿科','patient_12','Done'),
('doctor_8','2020-01-08','妇产科','patient_12','Done'),
('doctor_1','2020-05-14','外科','patient_12','Done'),
('doctor_11','2020-02-04','儿科','patient_13','Done'),
('doctor_2','2020-06-10','妇产科','patient_13','Done'),
('doctor_9','2020-03-20','外科','patient_13','Done'),
('doctor_11','2020-11-02','儿科','patient_14','Done'),
('doctor_11','2020-12-18','妇产科','patient_14','Done'),
('doctor_14','2020-12-01','外科','patient_14','Done'),
('doctor_9','2020-04-12','儿科','patient_15','Done'),
('doctor_6','2020-01-10','妇产科','patient_15','Done'),
('doctor_0','2020-04-21','外科','patient_15','Done'),
('doctor_5','2020-01-20','儿科','patient_16','Done'),
('doctor_2','2020-05-17','妇产科','patient_16','Done'),
('doctor_9','2020-09-28','外科','patient_16','Done'),
('doctor_14','2020-06-07','儿科','patient_17','Done'),
('doctor_10','2020-12-27','妇产科','patient_17','Done'),
('doctor_10','2020-12-23','外科','patient_17','Done'),
('doctor_6','2020-01-26','儿科','patient_18','Done'),
('doctor_5','2020-03-07','妇产科','patient_18','Done'),
('doctor_0','2020-12-09','外科','patient_18','Done'),
('doctor_15','2020-02-04','儿科','patient_19','Done'),
('doctor_11','2020-12-25','妇产科','patient_19','Done'),
('doctor_1','2020-12-18','外科','patient_19','Done'),
('doctor_11','2020-10-05','儿科','patient_20','Done'),
('doctor_2','2020-11-01','妇产科','patient_20','Done'),
('doctor_2','2020-06-24','外科','patient_20','Done');

INSERT INTO patient_history(patient_id, doctor_id, treat_date, treat_issue, allergens) VALUES
('patient_0','doctor_9','2020-04-08','xxx','xxx'),
('patient_0','doctor_4','2020-10-14','xxx','xxx'),
('patient_0','doctor_9','2020-03-08','xxx','xxx'),
('patient_1','doctor_1','2020-01-17','xxx','xxx'),
('patient_1','doctor_3','2020-01-01','xxx','xxx'),
('patient_1','doctor_3','2020-04-07','xxx','xxx'),
('patient_2','doctor_0','2020-03-18','xxx','xxx'),
('patient_2','doctor_3','2020-08-28','xxx','xxx'),
('patient_2','doctor_14','2020-10-11','xxx','xxx'),
('patient_3','doctor_4','2020-01-21','xxx','xxx'),
('patient_3','doctor_10','2020-02-13','xxx','xxx'),
('patient_3','doctor_6','2020-12-04','xxx','xxx'),
('patient_4','doctor_10','2020-06-05','xxx','xxx'),
('patient_4','doctor_6','2020-12-21','xxx','xxx'),
('patient_4','doctor_12','2020-07-23','xxx','xxx'),
('patient_5','doctor_13','2020-12-22','xxx','xxx'),
('patient_5','doctor_7','2020-07-21','xxx','xxx'),
('patient_5','doctor_7','2020-01-01','xxx','xxx'),
('patient_6','doctor_3','2020-10-19','xxx','xxx'),
('patient_6','doctor_1','2020-04-13','xxx','xxx'),
('patient_6','doctor_5','2020-02-08','xxx','xxx'),
('patient_7','doctor_12','2020-12-15','xxx','xxx'),
('patient_7','doctor_13','2020-01-27','xxx','xxx'),
('patient_7','doctor_15','2020-09-20','xxx','xxx'),
('patient_8','doctor_1','2020-11-07','xxx','xxx'),
('patient_8','doctor_10','2020-05-08','xxx','xxx'),
('patient_8','doctor_12','2020-06-06','xxx','xxx'),
('patient_9','doctor_9','2020-12-23','xxx','xxx'),
('patient_9','doctor_6','2020-08-24','xxx','xxx'),
('patient_9','doctor_5','2020-10-23','xxx','xxx'),
('patient_10','doctor_5','2020-03-17','xxx','xxx'),
('patient_10','doctor_6','2020-11-08','xxx','xxx'),
('patient_10','doctor_5','2020-11-06','xxx','xxx'),
('patient_11','doctor_11','2020-01-14','xxx','xxx'),
('patient_11','doctor_9','2020-04-26','xxx','xxx'),
('patient_11','doctor_14','2020-09-04','xxx','xxx'),
('patient_12','doctor_10','2020-04-04','xxx','xxx'),
('patient_12','doctor_8','2020-01-08','xxx','xxx'),
('patient_12','doctor_1','2020-05-14','xxx','xxx'),
('patient_13','doctor_11','2020-02-04','xxx','xxx'),
('patient_13','doctor_2','2020-06-10','xxx','xxx'),
('patient_13','doctor_9','2020-03-20','xxx','xxx'),
('patient_14','doctor_11','2020-11-02','xxx','xxx'),
('patient_14','doctor_11','2020-12-18','xxx','xxx'),
('patient_14','doctor_14','2020-12-01','xxx','xxx'),
('patient_15','doctor_9','2020-04-12','xxx','xxx'),
('patient_15','doctor_6','2020-01-10','xxx','xxx'),
('patient_15','doctor_0','2020-04-21','xxx','xxx'),
('patient_16','doctor_5','2020-01-20','xxx','xxx'),
('patient_16','doctor_2','2020-05-17','xxx','xxx'),
('patient_16','doctor_9','2020-09-28','xxx','xxx'),
('patient_17','doctor_14','2020-06-07','xxx','xxx'),
('patient_17','doctor_10','2020-12-27','xxx','xxx'),
('patient_17','doctor_10','2020-12-23','xxx','xxx'),
('patient_18','doctor_6','2020-01-26','xxx','xxx'),
('patient_18','doctor_5','2020-03-07','xxx','xxx'),
('patient_18','doctor_0','2020-12-09','xxx','xxx'),
('patient_19','doctor_15','2020-02-04','xxx','xxx'),
('patient_19','doctor_11','2020-12-25','xxx','xxx'),
('patient_19','doctor_1','2020-12-18','xxx','xxx'),
('patient_20','doctor_11','2020-10-05','xxx','xxx'),
('patient_20','doctor_2','2020-11-01','xxx','xxx'),
('patient_20','doctor_2','2020-06-24','xxx','xxx');

INSERT INTO prescription(doctor_id, patient_id, pres_date, medicine_name, medicine_num) VALUES
('doctor_9','patient_0','2020-04-08','阿莫西林','2'),
('doctor_4','patient_0','2020-10-14','感冒软胶囊','2'),
('doctor_9','patient_0','2020-03-08','阿莫西林','2'),
('doctor_1','patient_1','2020-01-17','头孢拉定','2'),
('doctor_3','patient_1','2020-01-01','感冒软胶囊','2'),
('doctor_3','patient_1','2020-04-07','头孢拉定','2'),
('doctor_0','patient_2','2020-03-18','头孢拉定','2'),
('doctor_3','patient_2','2020-08-28','阿莫西林','2'),
('doctor_14','patient_2','2020-10-11','感冒软胶囊','2'),
('doctor_4','patient_3','2020-01-21','布洛芬','2'),
('doctor_10','patient_3','2020-02-13','盘尼西林','2'),
('doctor_6','patient_3','2020-12-04','胰岛素','2'),
('doctor_10','patient_4','2020-06-05','阿莫西林','2'),
('doctor_6','patient_4','2020-12-21','阿莫西林','2'),
('doctor_12','patient_4','2020-07-23','感冒软胶囊','2'),
('doctor_13','patient_5','2020-12-22','头孢拉定','2'),
('doctor_7','patient_5','2020-07-21','头孢拉定','2'),
('doctor_7','patient_5','2020-01-01','头孢拉定','2'),
('doctor_3','patient_6','2020-10-19','阿莫西林','2'),
('doctor_1','patient_6','2020-04-13','头孢拉定','2'),
('doctor_5','patient_6','2020-02-08','阿莫西林','2'),
('doctor_12','patient_7','2020-12-15','胰岛素','2'),
('doctor_13','patient_7','2020-01-27','布洛芬','2'),
('doctor_15','patient_7','2020-09-20','盘尼西林','2'),
('doctor_1','patient_8','2020-11-07','阿莫西林','2'),
('doctor_10','patient_8','2020-05-08','布洛芬','2'),
('doctor_12','patient_8','2020-06-06','胰岛素','2'),
('doctor_9','patient_9','2020-12-23','盘尼西林','2'),
('doctor_6','patient_9','2020-08-24','阿莫西林','2'),
('doctor_5','patient_9','2020-10-23','布洛芬','2'),
('doctor_5','patient_10','2020-03-17','胰岛素','2'),
('doctor_6','patient_10','2020-11-08','阿莫西林','2'),
('doctor_5','patient_10','2020-11-06','阿莫西林','2'),
('doctor_11','patient_11','2020-01-14','头孢拉定','2'),
('doctor_9','patient_11','2020-04-26','胰岛素','2'),
('doctor_14','patient_11','2020-09-04','盘尼西林','2'),
('doctor_10','patient_12','2020-04-04','布洛芬','2'),
('doctor_8','patient_12','2020-01-08','阿莫西林','2'),
('doctor_1','patient_12','2020-05-14','胰岛素','2'),
('doctor_11','patient_13','2020-02-04','布洛芬','2'),
('doctor_2','patient_13','2020-06-10','盘尼西林','2'),
('doctor_9','patient_13','2020-03-20','感冒软胶囊','2'),
('doctor_11','patient_14','2020-11-02','盘尼西林','2'),
('doctor_11','patient_14','2020-12-18','布洛芬','2'),
('doctor_14','patient_14','2020-12-01','盘尼西林','2'),
('doctor_9','patient_15','2020-04-12','头孢拉定','2'),
('doctor_6','patient_15','2020-01-10','盘尼西林','2'),
('doctor_0','patient_15','2020-04-21','胰岛素','2'),
('doctor_5','patient_16','2020-01-20','阿莫西林','2'),
('doctor_2','patient_16','2020-05-17','头孢拉定','2'),
('doctor_9','patient_16','2020-09-28','感冒软胶囊','2'),
('doctor_14','patient_17','2020-06-07','盘尼西林','2'),
('doctor_10','patient_17','2020-12-27','胰岛素','2'),
('doctor_10','patient_17','2020-12-23','感冒软胶囊','2'),
('doctor_6','patient_18','2020-01-26','盘尼西林','2'),
('doctor_5','patient_18','2020-03-07','头孢拉定','2'),
('doctor_0','patient_18','2020-12-09','头孢拉定','2'),
('doctor_15','patient_19','2020-02-04','头孢拉定','2'),
('doctor_11','patient_19','2020-12-25','胰岛素','2'),
('doctor_1','patient_19','2020-12-18','头孢拉定','2'),
('doctor_11','patient_20','2020-10-05','感冒软胶囊','2'),
('doctor_2','patient_20','2020-11-01','阿莫西林','2'),
('doctor_2','patient_20','2020-06-24','胰岛素','2');

INSERT INTO num_appointed(doctor_id, appointment_date, num) VALUES
('doctor_9','2020-04-08','1'),
('doctor_4','2020-10-14','1'),
('doctor_9','2020-03-08','1'),
('doctor_1','2020-01-17','1'),
('doctor_3','2020-01-01','1'),
('doctor_3','2020-04-07','1'),
('doctor_0','2020-03-18','1'),
('doctor_3','2020-08-28','1'),
('doctor_14','2020-10-11','1'),
('doctor_4','2020-01-21','1'),
('doctor_10','2020-02-13','1'),
('doctor_6','2020-12-04','1'),
('doctor_10','2020-06-05','1'),
('doctor_6','2020-12-21','1'),
('doctor_12','2020-07-23','1'),
('doctor_13','2020-12-22','1'),
('doctor_7','2020-07-21','1'),
('doctor_7','2020-01-01','1'),
('doctor_3','2020-10-19','1'),
('doctor_1','2020-04-13','1'),
('doctor_5','2020-02-08','1'),
('doctor_12','2020-12-15','1'),
('doctor_13','2020-01-27','1'),
('doctor_15','2020-09-20','1'),
('doctor_1','2020-11-07','1'),
('doctor_10','2020-05-08','1'),
('doctor_12','2020-06-06','1'),
('doctor_9','2020-12-23','1'),
('doctor_6','2020-08-24','1'),
('doctor_5','2020-10-23','1'),
('doctor_5','2020-03-17','1'),
('doctor_6','2020-11-08','1'),
('doctor_5','2020-11-06','1'),
('doctor_11','2020-01-14','1'),
('doctor_9','2020-04-26','1'),
('doctor_14','2020-09-04','1'),
('doctor_10','2020-04-04','1'),
('doctor_8','2020-01-08','1'),
('doctor_1','2020-05-14','1'),
('doctor_11','2020-02-04','1'),
('doctor_2','2020-06-10','1'),
('doctor_9','2020-03-20','1'),
('doctor_11','2020-11-02','1'),
('doctor_11','2020-12-18','1'),
('doctor_14','2020-12-01','1'),
('doctor_9','2020-04-12','1'),
('doctor_6','2020-01-10','1'),
('doctor_0','2020-04-21','1'),
('doctor_5','2020-01-20','1'),
('doctor_2','2020-05-17','1'),
('doctor_9','2020-09-28','1'),
('doctor_14','2020-06-07','1'),
('doctor_10','2020-12-27','1'),
('doctor_10','2020-12-23','1'),
('doctor_6','2020-01-26','1'),
('doctor_5','2020-03-07','1'),
('doctor_0','2020-12-09','1'),
('doctor_15','2020-02-04','1'),
('doctor_11','2020-12-25','1'),
('doctor_1','2020-12-18','1'),
('doctor_11','2020-10-05','1'),
('doctor_2','2020-11-01','1'),
('doctor_2','2020-06-24','1');
```

在登录界面添加病人注册界面的router，设置submitForm方法向数据库插入数据

关于用户登录问题，由于sprint boot采用的数据库连接机制，不太方便设置不同用户的登录方式，所以考虑通过前端功能界面对用户操作权限进行限制

后端处理主要是`GetMapping`和`PostMapping`的处理逻辑，按照sprint boot的架构，`@Repository`是数据层，直接和数据库进行数据交互；`@Service`是服务层，接受`@Controller`发来的请求，调用数据层的接口进行操作（隔离机制）

# 问题

路由传参和读取的部分可能有问题，`params`的设置需要更改；

药品的逻辑处理还没有想好，目前的处理是名称和数量分别看作字符串，用分隔符分开各类药品，然后前后端交互的时候就根据分隔符来获取各类药品的名称以及相应的数量；

管理员对用户的修改只是简单地复用了`UserInfo`的内容，没有涉及到数据库全部信息的修改；

# 进度

## 前端

前端基本的页面都已编写完成，但很多地方都只是做了简单化处理，同时也有很多较细节的地方存在着bug（因为不能与后端协同所以不方便调试）

后端主要的工作就是针对所有的`this.$axios`的`post`和`get`请求编写相应的处理函数，`get`请求直接通过`@RequestParam`获取传参，返回的`map`对象，`key`值可以作为一个标识性质的内容用于前端逻辑判断，`value`的话则是一个实例化对象，对应于定义的`Java`类（不过这里为了省事，可以传回一个列表之类的数据结构，在前端直接用索引去读取值，这时数据的顺序就很重要）；

`post`方法应该不需要去特地定义相关的类，发现也可以通过`params`配置向后端传递的参数，相应的捕获方法应该也和`get`差不多

## 后端

后端架构基本上是按照sprint boot的架构来的，`@Repository`是数据层，直接和数据库进行数据交互；`@Service`是服务层，接受`@Controller`发来的请求，调用数据层的接口进行操作（隔离机制）

# TODO

## 前端

医生在编辑病人处方的时候依据传入参数，将对应种类数量的药品信息填写提交，在查看和修改处方的页面中单纯把表中每条数据（一个元组对应只有一种药品）作为表格的一行内容 - 采用设定上限的办法（当前最多是4种药品）

管理员功能尚待完善，包括

- 设置科室的科长

- 增删科室、医生

- 对用户信息的修改需要更完整的个人信息

## 后端

### 后端已完成的工作：

* 用户注册
* 用户登录
* 用户查询&修改个人信息
* 病人查询个人病历
* 病人查询个人处方

TODO    

* 病人查询医生及诊室信息
* 病人新建预约
* 病人新建流凋表
* 医生部分
* ddd

### 后端说明

* 预约部分单独模块
* 处方和病历在medical中

---

数据创建：$\ge$20个患者且每个患者至少3次预约记录，$\ge$3个科室且每个科室至少3名医生

使用`SQL`实现：

- 数据查询

- 一个触发器

- 用户权限管理（和页面的功能有区别）

`MapReduce`对生成的患者访问信息日志记录进行操作
