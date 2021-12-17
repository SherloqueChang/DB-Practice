# 思路

前端采用 VUE 实现，大框架没有问题，主要是基于业务逻辑进行相应的实现；

前后端通信按照原来的axios，主要是参数的设置，针对性修改；

后端主要的问题在于，原项目实现是在init()方法调用的时候一次性执行了数据库登录以及数据的设置，我们期望的实现是通过login输入的用户名以及密码来作为判断依据，选择相应的具有特定权限的用户进行数据库的登录操作（不太可行，通过前端限制）；其他主要的工作应该就是响应函数的处理（SQL语句的编写），这里需要注意业务和数据隔离的原则。

# 配置

vscode插件：

![1639642070127](E:%5Cchangwenhui%5C%E5%A4%A7%E5%9B%9B%E4%B8%8A%5C%E6%95%B0%E6%8D%AE%E5%BA%93%E6%A6%82%E8%AE%BA%5C%E4%BD%9C%E4%B8%9A%5C%E5%AE%9E%E4%B9%A0%5CHIMS%5C1639642070127.png)

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

# 问题

路由传参和读取的部分可能有问题，`params`的设置需要更改；

药品的逻辑处理还没有想好，目前的处理是名称和数量分别看作字符串，用分隔符分开各类药品，然后前后端交互的时候就根据分隔符来获取各类药品的名称以及相应的数量；

管理员对用户的修改只是简单地复用了`UserInfo`的内容，没有涉及到数据库全部信息的修改；

# 进度

前端基本的页面都已编写完成，但很多地方都只是做了简单化处理，同时也有很多较细节的地方存在着bug（因为不能与后端协同所以不方便调试）

后端主要的工作就是针对所有的`this.$axios`的`post`和`get`请求编写相应的处理函数，`get`请求直接通过`@RequestParam`获取传参，返回的`map`对象，`key`值可以作为一个标识性质的内容用于前端逻辑判断，`value`的话则是一个实例化对象，对应于定义的`Java`类（不过这里为了省事，可以传回一个列表之类的数据结构，在前端直接用索引去读取值，这时数据的顺序就很重要）；

`post`方法应该不需要去特地定义相关的类，发现也可以通过`params`配置向后端传递的参数，相应的捕获方法应该也和`get`差不多