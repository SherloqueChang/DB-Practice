# 思路

前端采用 VUE 实现，大框架没有问题，主要是基于业务逻辑进行相应的实现；

前后端通信按照原来的axios，主要是参数的设置，针对性修改；

后端主要的问题在于，原项目实现是在init()方法调用的时候一次性执行了数据库登录以及数据的设置，我们期望的实现是通过login输入的用户名以及密码来作为判断依据，选择相应的具有特定权限的用户进行数据库的登录操作（不太可行，通过前端限制）；其他主要的工作应该就是响应函数的处理（SQL语句的编写），这里需要注意业务和数据隔离的原则。

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
    leader_id VARCHAR(50) COMMENT 'Leader id of the department'
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

CREATE TABLE Medical_records(
    doctor_id VARCHAR(50) NOT NULL COMMENT 'id of the doctor',
    patient_id VARCHAR(50) NOT NULL COMMENT 'id of the patient',
    see_doctor_date DATE NOT NULL COMMENT 'date of the the record',
    issue VARCHAR(200) NOT NULL COMMENT 'issue of the patient',
    diagnosed_disease VARCHAR(200) COMMENT 'The doctor diagnosed the disease',
    allergens VARCHAR(200) COMMENT 'determined allergens of this record',
    #FOREIGN KEY (doctor_id) REFERENCES doctor(id) ON DELETE NO ACTION,
    #FOREIGN KEY (patient_id) REFERENCES patient(id) ON DELETE NO ACTION,
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
```



在登录界面添加病人注册界面的router，设置submitForm方法向数据库插入数据

关于用户登录问题，由于sprint boot采用的数据库连接机制，不太方便设置不同用户的登录方式，所以考虑通过前端功能界面对用户操作权限进行限制

后端处理主要是`GetMapping`和`PostMapping`的处理逻辑，按照sprint boot的架构，`@Repository`是数据层，直接和数据库进行数据交互；`@Service`是服务层，接受`@Controller`发来的请求，调用数据层的接口进行操作（隔离机制）

## 前端

依据全局性的`this.$store.state.user`判断用户类型，从而进行相应的页面显示（`v-if`）

路由的首字母小写，其他都大写（camel）

# 问题

前后端数据交互格式，特别是前端收到response的数据后如何绑定到组件上进行可视化，以及数据属性如何定义；

后端处理逻辑的详细过程，特别是登录逻辑的实现，需要修改的部分比较多；如何控制数据库的登录访问及退出

在init()方法调用完成之后和数据库的连接就中断了，那么后面的过程是如何访问数据库的？（在datasource中配置相关信息，启动sprintboot项目时会自动把数据源信息注入到JdbcTemplate中，只需要在使用的地方Autowired注入JdbcTemplate即可）	

# 笔记

## VUE 语法

vue components 是可复用的组件，通过v-if设置显示的内容，可以作为统一的界面；

v-model实现表单输入和应用状态之间的双向绑定；

vue实例创建时，将data对象中的所有property加入到vue的响应系统中，值改变时视图产生响应更新；

生命周期钩子，如created，在一个实例创建后执行代码；

`v-bind` 指令可以用于响应式地更新 HTML attribute；

# 进度

登录、注册以及患者的相关页面框架已经基本完成（专门的发热门诊预约还没有设计）；

框架的部分先到这里为止，需要对前端的工作进行更细致的检查和修改，特别是在数据格式（比如日期）、异常流程处理上要多加考虑；

尝试添加一些简单的数据进行项目的测试