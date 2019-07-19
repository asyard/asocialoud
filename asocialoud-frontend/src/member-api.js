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
    createNew: (loginNameText, realNameText) => instance.post('/', {loginName: loginNameText, realName: realNameText}),

    login: (loginNameText, realNameText) => instance.post('/login', {loginName: loginNameText, realName: realNameText}),

    // (R)ead
    getAll: () => instance.get('/', {
        transformResponse: [function (data) {
            return data? JSON.parse(data) : data;
        }]
    }),
    // (U)pdate
    updateForId: (id, loginNameText, realNameText) => instance.put('/'+id, {loginName: loginNameText, realName: realNameText}),
    // (D)elete
    removeForId: (id) => instance.delete('/'+id),

    hello() {
        return instance.get('/id/1')
    }
}

