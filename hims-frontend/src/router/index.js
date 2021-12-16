import Vue from 'vue'
import Router from 'vue-router'

import Login from '@/pages/Login'
import Register from '@/pages/Register'
import WorkerDataPanel from '@/pages/WorkerDataPanel'
import PatientDataPanel from '@/pages/PatientDataPanel'
import BedDataPanel from '@/pages/BedDataPanel'
import WorkerInfo from '@/pages/WorkerInfo'
import UserInfo from '@/pages/UserInfo'
import PatientInfo from '@/pages/PatientInfo'
import NatDataPanel from '@/pages/NatDataPanel'
import NatInfo from '@/pages/NatInfo'
import DailyReportInfo from '@/pages/DailyReportInfo'
import DailyReportDataPanel from '@/pages/DailyReportDataPanel'
import Messages from '@/pages/Messages'
import PatientPanel from '../pages/PatientPanel'
import AppointmentInfo from '../pages/AppointmentInfo'
import RegisterInfo from '../pages/RegisterInfo'
import MedicalInfo from '../pages/MedicalInfo'
import AppointmentOnline from '../pages/AppointmentOnline'
import DoctorInfo from '../pages/DoctorInfo'
import FeverAppointment from '../pages/FeverAppointment'
import HistoryPatient from '../pages/HistoryPatient'

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
      path: '/patientPanel',
      name: 'PatientPanel',
      component: PatientPanel
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
    // ////////////////////////////////
    {
      path: '/workerDataPanel',
      name: 'WorkerDataPanel',
      component: WorkerDataPanel
    },
    {
      path: '/patientDataPanel',
      name: 'PatientDataPanel',
      component: PatientDataPanel
    },
    {
      path: '/patientDataPanel/:w_id&:w_name',
      name: 'PatientDataPanel',
      component: PatientDataPanel
    },
    {
      path: '/bedDataPanel',
      name: 'BedDataPanel',
      component: BedDataPanel
    },
    {
      path: '/workerInfo',
      name: 'WorkerInfo',
      component: WorkerInfo
    },
    {
      path: '/workerInfo/:w_id',
      name: 'WorkerInfo',
      component: WorkerInfo
    },
    {
      path: '/patientInfo',
      name: 'PatientInfo',
      component: PatientInfo
    },
    {
      path: '/patientInfo/:p_id',
      name: 'PatientInfo',
      component: PatientInfo
    },
    {
      path: '/natDataPanel/:p_id&:p_name',
      name: 'NatDataPanel',
      component: NatDataPanel
    },
    {
      path: '/natInfo/:p_id&:p_name/:r_id',
      name: 'NatInfo',
      component: NatInfo
    },
    {
      path: '/drDataPanel/:p_id&:p_name',
      name: 'DailyReportDataPanel',
      component: DailyReportDataPanel
    },
    {
      path: '/drInfo/:p_id&:p_name',
      name: 'DailyReportInfo',
      component: DailyReportInfo
    },
    {
      path: '/userInfo',
      name: 'UserInfo',
      component: UserInfo
    },
    {
      path: '/messages',
      name: 'Messages',
      component: Messages
    },
    {
      path: '*',
      redirect: '/'
    }
  ]
})
