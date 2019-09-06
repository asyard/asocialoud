import axios from 'axios'

const SERVER_URL = 'http://localhost:8020/feedsrv/api/feeds';

const instance = axios.create({
    baseURL: SERVER_URL,
    timeout: 10000,
});

export default {
    getFeedsOf: (memberId, page) => instance.get('/of/' + memberId + '?start=' + page, {
        transformResponse: [function (data) {
            return data ? JSON.parse(data) : data;
        }]
    }),

    getFeedsOfFollowing: (memberIdList, page) => instance.get('/followingsof/'+memberIdList+'?start=' + page, {
        transformResponse: [function (data) {
            return data? JSON.parse(data) : data;
        }]
    }),

    addFeed: (memberIdVal, feedContent) => instance.post('/create/', { memberId : memberIdVal, text : feedContent}, {
        transformResponse: [function (data) {
            return data? JSON.parse(data) : data;
        }]
    })

}

