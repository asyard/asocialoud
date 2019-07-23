import axios from 'axios'

const SERVER_URL = 'http://localhost:8070/api/members';

const instance = axios.create({
    baseURL: SERVER_URL,
    timeout: 1000,
    //headers: {
    //    'Access-Control-Allow-Origin': 'http://localhost:8070'
    //}
});




export default {
    // (C)reate
    createNew: (loginNameText, realNameText, emailText, passwordText) => instance.post('/', {loginName: loginNameText, realName: realNameText, email: emailText, password: passwordText}),

    login: (loginNameText, passwordText) => instance.post('/login', {loginName: loginNameText, password: passwordText}),

    // (R)ead
    getAll: () => instance.get('/', {
        transformResponse: [function (data) {
            return data? JSON.parse(data) : data;
        }]
    }),
    // (U)pdate
    updateByUserName: (userName, realNameText, emailTxt) => instance.put('/'+userName, {realName: realNameText, email: emailTxt}),

    // (D)elete
    removeForId: (id) => instance.delete('/'+id),

    retrieveByUserName: (userName) => instance.get('/'+userName),

}

