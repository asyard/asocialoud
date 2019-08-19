import axios from 'axios'

const SERVER_URL = 'http://localhost:8060/api/feeds';

const instance = axios.create({
    baseURL: SERVER_URL,
    timeout: 10000,
});

export default {
    getFeedsOf: (memberId) => instance.get('/of/'+memberId, {
        transformResponse: [function (data) {
            return data? JSON.parse(data) : data;
        }]
    }),

    getFeedsOfFollowing: (memberIdList) => instance.get('/followingsof/'+memberIdList, {
        transformResponse: [function (data) {
            return data? JSON.parse(data) : data;
        }]
    }),

    getCurrentTime() {
        var d = new Date();
        var curr_date = d.getDate();
        var curr_month = d.getMonth() + 1; //Months are zero based
        var curr_year = d.getFullYear();
        var curr_hour = d.getHours();
        var curr_min = d.getMinutes();
        var curr_sec = d.getSeconds();

        return curr_year + '-' + curr_month + '-' + curr_date + ' ' + curr_hour + ':' + curr_min + ':' + curr_sec;
    },

    addFeed: (memberIdVal, feedContent) => instance.post('/create/', { memberId : memberIdVal, text : feedContent}, {
        transformResponse: [function (data) {
            return data? JSON.parse(data) : data;
        }]
    })

}

