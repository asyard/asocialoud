import axios from 'axios'

const SERVER_URL = 'http://localhost:8070/api/follow';

const instance = axios.create({
    baseURL: SERVER_URL,
    timeout: 1000,
    //headers: {
    //    'Access-Control-Allow-Origin': 'http://localhost:8070'
    //}
});




export default {
    getFollowing: (userName) => instance.get('/of/'+userName, {
        transformResponse: [function (data) {
            return data? JSON.parse(data) : data;
        }]
    }),

    addFollowing: (userName, userNameToFollow) => instance.post('/add/'+userNameToFollow, { loginName : userName}, {
        transformResponse: [function (data) {
            return data? JSON.parse(data) : data;
        }]
    }),

    removeFollowing: (userName, userNameToUnFollow) => instance.post('/remove/'+userNameToUnFollow, { loginName : userName}),

    getFollowers: (userName) => instance.get('/has/'+userName, {
        transformResponse: [function (data) {
            return data? JSON.parse(data) : data;
        }]
    })

}

