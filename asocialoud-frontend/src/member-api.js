import axios from 'axios'

const SERVER_URL = 'http://localhost:8020/membersrv/api/members';

const instance = axios.create({
    baseURL: SERVER_URL,
    timeout: 10000,
    //headers: {
    //    'Access-Control-Allow-Origin': 'http://localhost:8070'
    //}
    headers: {
        'Content-Type': 'application/json',
        'Authorization': localStorage.getItem('vuex') ? 'Bearer ' + JSON.parse(localStorage.getItem('vuex')).token : ''
    }

});




export default {
    // (C)reate
    createNew: (loginNameText, realNameText, emailText, passwordText) => instance.post('/create', {loginName: loginNameText, realName: realNameText, email: emailText, password: passwordText}),

    login: (loginNameText, passwordText) => instance.post('/login', {username: loginNameText, password: passwordText}),

    // (R)ead
    getAll: () => instance.get('/', {
        transformResponse: [function (data) {
            return data? JSON.parse(data) : data;
        }]
    }),
    // (U)pdate
    updateByUserName: (userName, realNameText, emailTxt) => instance.patch('/'+userName, {realName: realNameText, email: emailTxt}),

    // (D)elete
    //removeForId: (id) => instance.delete('/'+id),

    removeForUserName: (userName) => instance.delete('/'+userName),

    retrieveByUserName: (userName) => instance.get('/'+userName),

    retrieveById: (userId) => instance.get('/id/'+userId),

    getFiltered: (userName) => instance.get('/search/'+userName, {
        transformResponse: [function (data) {
            return data? JSON.parse(data) : data;
        }]
    })

}

