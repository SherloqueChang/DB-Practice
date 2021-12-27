import Vue from 'vue'
import Router from 'vue-router'

import Login from '@/pages/Login'
import Register from '@/pages/Register'
import UserInfo from '@/pages/UserInfo'
import AppointmentInfo from '../pages/AppointmentInfo'
import RegisterInfo from '../pages/RegisterInfo'
import MedicalInfo from '../pages/MedicalInfo'
import AppointmentOnline from '../pages/AppointmentOnline'
import DoctorInfo from '../pages/DoctorInfo'
import FeverAppointment from '../pages/FeverAppointment'
import HistoryPatient from '../pages/HistoryPatient'
import MedicalRecForm from '../pages/MedicalRecForm'
import PrescriptionForm from '../pages/PrescriptionForm'
import AppointmentToday from '../pages/AppointmentToday'
import DoctorMedicalHistory from '../pages/DoctorMedicalHistory'
import DoctorPrescription from '../pages/DoctorPrescription'
import DepartmentMng from '../pages/DepartmentMng'
import MedicineMng from '../pages/MedicineMng'
import PatientMng from '../pages/PatientMng'
import DoctorMng from '../pages/DoctorMng'
import DepartmentMng_admin from '../pages/DepartmentMng_admin'
import PatientInfo from '../pages/PatientInfo'

Vue.use(Router)

export default new Router({
  mode: 'history',
  base: process.env.BASE_URL,
  routes: [
    {
      path: '/',
      name: 'Login',
      component: Login
    },
    {
      path: '/register',
      name: 'Register',
      component: Register
    },
    {
      path: '/registerInfo',
      name: 'RegisterInfo',
      component: RegisterInfo
    },
    {
      path: '/appointmentInfo',
      name: 'AppointmentInfo',
      component: AppointmentInfo
    },
    {
      path: '/medicalInfo',
      name: 'MedicalInfo',
      component: MedicalInfo
    },
    {
      path: '/appointmentOnline',
      name: 'AppointmentOnline',
      component: AppointmentOnline
    },
    {
      path: '/doctorInfo/:d_id',
      name: 'DoctorInfo',
      component: DoctorInfo
    },
    {
      path: '/feverAppointment',
      name: 'FeverAppointment',
      component: FeverAppointment
    },
    {
      path: '/historyPatient',
      name: 'HistoryPatient',
      component: HistoryPatient
    },
    {
      path: '/patientInfo/:p_id',
      name: 'PatientInfo',
      component: PatientInfo
    },
    {
      path: '/appointmentToday',
      name: 'AppointmentToday',
      component: AppointmentToday
    },
    {
      path: '/medicalRecForm/:p_id',
      name: 'MedicalRecForm',
      component: MedicalRecForm
    },
    {
      path: '/prescriptionForm/:p_id',
      name: 'PrescriptionForm',
      component: PrescriptionForm
    },
    {
      path: '/departmentMng',
      name: 'DepartmentMng',
      component: DepartmentMng
    },
    {
      path: '/departmentMng/:d_id',
      name: 'DepartmentMng',
      component: DepartmentMng
    },
    {
      path: '/checkDoctorMedicalHistory',
      name: 'DoctorMedicalHistory',
      component: DoctorMedicalHistory
    },
    {
      path: '/checkDoctorPrescription',
      name: 'DoctorPrescription',
      component: DoctorPrescription
    },
    {
      path: '/medicineMng',
      name: 'MedicineMng',
      component: MedicineMng
    },
    {
      path: '/patientMng',
      name: 'PatientMng',
      component: PatientMng
    },
    {
      path: '/doctorMng',
      name: 'DoctorMng',
      component: DoctorMng
    },
    {
      path: '/departmentMng_admin',
      name: 'DepartmentMng_admin',
      component: DepartmentMng_admin
    },
    {
      path: '/userInfo',
      name: 'UserInfo',
      component: UserInfo
    },
    {
      path: '*',
      redirect: '/'
    }
  ]
})
